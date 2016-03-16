package com.dianxin.imessage.web.service.book;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.biz.book.IRecommendBiz;
import com.dianxin.imessage.biz.config.IActivityStatBiz;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.biz.mongo.RuleHandle;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.lbs.data.UserInfo;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.common.util.ValidatorTools;
import com.dianxin.imessage.dao.dataobject.DxInterestModel;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/1/29
 * Time: 8:16
 */
@Controller
@RequestMapping("/book/")
public class DxLbsController {
    private Logger logger = LoggerFactory.getLogger(DxLbsController.class);

    @Autowired
    private IRecommendBiz recommendBiz;

    @Autowired
    private IUserBiz userBiz;

    @Autowired
    private MemcachedFactory cacheFactory;

    @Autowired
    private IActivityStatBiz iActivityStatBiz;

    @Autowired
    private IInterestBiz iInterestBiz;

    @Autowired
    private IUserImageBiz iUserImageBiz;

    @RequestMapping(value = "activity/{uid}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String upLoadAppTimes(@DESParam("uid")String uid)
    {
        ResultModel<String> result = new ResultModel<String>();
        if(!StringUtils.isNotBlank(uid))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        boolean flag = iActivityStatBiz.addActivityTimes(Integer.valueOf(uid));
        if(flag)
        {
            return result.toJSON();
        }
        else
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.SERVICE_ERROR.value);
            return result.toJSON();
        }

    }

    @RequestMapping(value = "position/{uid}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String upLoadPosition(@DESParam("uid")String uid, String lat, String lon)
    {
        logger.debug("upLoadPosition start {0}", uid);
        ResultModel<String> result = new ResultModel<String>();
        if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(lat) && !StringUtils.isNotBlank(lon))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_PARAMER_NULL.value);
            return result.toJSON();
        }

        String errCode = recommendBiz.upLoadPoisition(uid,lon,lat);

        if (!ErrCodeEnum.NORMAL.value.equals(errCode)) {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(errCode);
        }
        return result.toJSON();
    }

    /**
     * 1、通过mongodb获取200人的列表，100公里范围
     * 2、过滤掉超过2天的人,小于2天的 计算时间
     * 3、业务规则计分统计，排序
     * 4、通过uid获取头像路径
     * 5、通过uid对比计算出兴趣匹配个数
     * 6、缓存到mem，进行分组
     * @param uid
     * @param page
     * @return
     */
    @RequestMapping(value = "nearbyFriends/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String nearbyFriends(@DESParam("uid")String uid, String page, String lat, String lon)
    {
        if(StringUtils.isBlank(page))
        {
            page = "1";
        }

        ResultModel<List<MongoPosition>> result = new ResultModel<List<MongoPosition>>();
        if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(lat) && !StringUtils.isNotBlank(lon))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_PARAMER_NULL.value);
            return result.toJSON();
        }

        //正则校验
        if(!ValidatorTools.isUserUid(uid))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.UID_FORMAT_ERR.value);
            return result.toJSON();
        }
        if(!ValidatorTools.isJingweidu(lon,lat))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_JINGWEIDU_ERR.value);
            return result.toJSON();
        }

        //从缓存读取 数据，没有则走mongodb流程 TODO
        try
        {
            Memcached cache = cacheFactory.createMemcached();
            List<MongoPosition> positionCache = (List<MongoPosition>)cache.get(RuleHandle.LBS_PREFIX + uid + "_"+ page);
            if(null != positionCache && positionCache.size() > 0)
            {
                result.setData(positionCache);
                return result.toJSON();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }

        logger.debug("nearbyFriends start {0}", uid);
        //mongodb获取100公里以内 200人上限 2天以内的从进到远的列表
        UserInfo info = new UserInfo();
        info.setLat(lat);
        info.setLon(lon);
        List<MongoPosition> list = recommendBiz.getRecommendListFromLbs(info);

        if(null == list || list.size() == 0)
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }

        DxUserModel user;   //本人
        DxUserModel toUser;  //周边陌生人

        List<MongoPosition> list_ = new ArrayList<MongoPosition>();
        try
        {
            user = userBiz.getUserByKey(Integer.valueOf(uid));
            for(MongoPosition mongoPosition : list)
            {
                if(uid.equals(String.valueOf(mongoPosition.getUid())))
                {
                    continue;
                }
                toUser = userBiz.getUserByKey(Integer.valueOf(mongoPosition.getUid()));
                if(null!=user&&null!=toUser)
                {
                    RuleHandle.handleForLbs(mongoPosition, user, toUser);
                    //业务规则处理完后，构建新的list
                    list_.add(mongoPosition);
                }
            }


            //按照分数 最终排序 TODO
            Comparator<MongoPosition> comparator = new Comparator<MongoPosition>() {
                @Override
                public int compare(MongoPosition o1, MongoPosition o2) {
                    if(o1.getScore() > o2.getScore())
                        return 1;
                    return 0;
                }
            };

            Collections.sort(list_,comparator);

            //临时存放memcached 设置失效时间 进行分组  TODO
            List<MongoPosition> dataList = RuleHandle.groupList(cacheFactory, list_, uid, 0,page);
            if(null == dataList||dataList.size()==0)
            {
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
                return result.toJSON();
            }
            result.setData(dataList);

        }catch (Exception e)
        {
            e.printStackTrace();
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }

        return result.toJSON();
    }


    /**
     * 推荐5个人的列表
     * @param uid
     * @return
     */
    @RequestMapping(value = "recommendInt/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String recommendInt(@DESParam("uid")String uid)
    {
        //1、当搜索不到全网兴趣用户的时候，按照推荐算法推荐5个人
        ResultModel<List<MongoPosition>> result = new ResultModel<List<MongoPosition>>();
        if(!StringUtils.isNotBlank(uid))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_PARAMER_NULL.value);
            return result.toJSON();
        }

        List<MongoPosition> mongoPositionList = new ArrayList<MongoPosition>();
        UserInfo info = new UserInfo();
        info.setUid(uid);
        try {
            DxUserModel user = userBiz.getUserByKey(Integer.valueOf(uid));

            if(null!=user)
            {
                List<DxUserModel> dataList = recommendBiz.getRecommendForFixed(user);
                if (null==dataList||dataList.size()==0)
                {
                    result.setResult(ResultModel.RESULT_FAIL_NUM);
                    result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
                    return result.toJSON();
                }
                for (DxUserModel dxUserModel : dataList)
                {
                    MongoPosition position = new MongoPosition();
                    position.setUid(dxUserModel.getUid());
                    position.setSex(null!=dxUserModel.getSex()?String.valueOf(dxUserModel.getSex()):"");
                    position.setUserNum(null!=dxUserModel.getUserNum()?dxUserModel.getUserNum():"");
                    boolean result_ = false;
                    if(null!= userBiz)
                    {
                        result_ = userBiz.isFirend(dxUserModel.getUid(), Integer.valueOf(uid));
                    }
                    if(result_)
                        position.setUserType("0"); //好友
                    else
                        position.setUserType("1");  //非好友

                    //获取兴趣列表
                    StringBuilder builder = new StringBuilder();
                    List<DxInterestModel> inList = iInterestBiz.getInterestByUid(dxUserModel.getUid());
                    if(null!=inList&&inList.size()>0) {
                        for (DxInterestModel interestModel : inList)
                        {
                            builder.append(",");
                            builder.append(interestModel.getInterest());
                        }
                    }

                    //获取用户头像上传地址
                    DxUserImageModel imageModel = iUserImageBiz.getUserImage(dxUserModel.getUid());
                    if(null!=imageModel)
                    {
                        position.setHeadUrl(imageModel.getFilePath());
                    }

                    mongoPositionList.add(position);
                }
                result.setData(mongoPositionList);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }
        return result.toJSON();
    }

}
