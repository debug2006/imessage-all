package com.dianxin.imessage.web.service.book;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.biz.book.IRecommendBiz;
import com.dianxin.imessage.biz.mongo.RuleHandle;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.lbs.data.UserInfo;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.common.util.ValidatorTools;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/11
 * Time: 19:19
 */
@Controller
@RequestMapping("/book")
public class DxInterestController {
    @Autowired
    private MemcachedFactory cacheFactory;

    @Autowired
    private IRecommendBiz recommendBiz;

    @Autowired
    private IUserBiz userBiz;

    @Autowired
    private IInterestBiz interestBiz ;

    /**
     * 兴趣最搭	相同兴趣的好友按年龄排序
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/matchInterest/{uid}",produces = "text/html;charset=UTF-8",method= RequestMethod.GET)
    public @ResponseBody
    String matchInterest(@DESParam String uid,String page)throws Exception{
        if(StringUtils.isBlank(page)) {
            page = "1";
        }

        ResultModel<List<MongoPosition>> result = new ResultModel<List<MongoPosition>>();
        if(!StringUtils.isNotBlank(uid)) {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_PARAMER_NULL.value);
        }

        //正则校验
        if(!ValidatorTools.isUserUid(uid)) {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.UID_FORMAT_ERR.value);
            return result.toJSON();
        }

        try {
            Memcached cache = cacheFactory.createMemcached();
            //一、获取周边100公里范围内的人  缓存中有就直接返回
            List<MongoPosition> positionCache = (List<MongoPosition>)cache.get(RuleHandle.INTEREST_PREFIX + uid + "_"+ page);
            if(null != positionCache && positionCache.size() > 0) {
                result.setData(positionCache);
                return result.toJSON();
            }

        }catch (Exception e) {
            e.printStackTrace();
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }

        //TODO 周活跃用户/非周活跃用户   周活跃用户中随机筛选160人；非周活跃用户中随机筛选40人
        UserInfo info = new UserInfo();
        info.setUid(uid);
        List<MongoPosition> list = recommendBiz.getRecommendListFromDb(info);

        if(null == list || list.size() == 0) {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }

        DxUserModel user = null;   //本人
        DxUserModel toUser = null;  //周边陌生人

        List<MongoPosition> list_ = new ArrayList<MongoPosition>();

        try {
            user = userBiz.getUserByKey(Integer.valueOf(uid));
            for(MongoPosition mongoPosition : list) {
                if(uid.equals(String.valueOf(mongoPosition.getUid()))) {
                    continue;
                }
                toUser = userBiz.getUserByKey(Integer.valueOf(mongoPosition.getUid()));
                if(null!=user&&null!=toUser) {
                    RuleHandle.handleForInterst(mongoPosition, user, toUser);
                    //业务规则处理完后，构建新的list
                    list_.add(mongoPosition);
                }
            }


            //按照兴趣数量 最终排序
            Comparator<MongoPosition> comparator = new Comparator<MongoPosition>() {
                @Override
                public int compare(MongoPosition o1, MongoPosition o2) {
                    if(o1.getSameInt() > o2.getSameInt())
                        return 1;
                    return 0;
                }
            };

            Collections.sort(list_, comparator);

            //临时存放memcached 设置失效时间 进行分组
            List<MongoPosition> dataList = null;
            if(list_.size()>0){
                dataList = RuleHandle.groupList(cacheFactory, list_, uid, 1, page);
            }
            if(null == dataList||dataList.size()<=0) {
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
                return result.toJSON();
            }
            result.setData(dataList);

        }catch (Exception e) {
            e.printStackTrace();
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MONGODB_LBS_ERR.value);
            return result.toJSON();
        }
        return result.toJSON();
    }
    
    /**
     * 搜索兴趣
     * @author b_fatty
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/searchInterest/{uid}/{interestName}/{pagesize}/{pageindex}",produces = "text/html;charset=UTF-8",method= RequestMethod.GET)
    public @ResponseBody String searchInterest(@DESParam Integer uid,@PathVariable String interestName,@PathVariable Integer pagesize,@PathVariable Integer pageindex) throws Exception{
    	
    	UserManageUtil.checkUid(uid);
    	
    	ResultModel<Map[]> result = new ResultModel<Map[]>();
    	
    	Map[] maps = interestBiz.searchInteres(interestName, pagesize, pageindex);
    	
    	result.setData(maps);
    	
    	return result.toJSON();
    }
    
    /**
     * 保存兴趣
     * @author b_fatty
     * @throws Exception 
     */
    @RequestMapping(value="/interest/{uid}/{interestId}",produces = "text/html;charset=UTF-8",method= RequestMethod.POST)
    public @ResponseBody String saveInterest(@DESParam Integer uid,@PathVariable Integer interestId) throws Exception{
    	
    	ResultModel result = new ResultModel();
    	
    	interestBiz.saveUserInterest(uid, interestId);
    	
    	return result.toJSON();
    	
    }
    
    /**
     * 上传自定义兴趣
     * @author b_fatty
     * @throws Exception 
     */
    @RequestMapping(value="/newInterest/{uid}/{interestName}",produces = "text/html;charset=UTF-8",method= RequestMethod.POST)
    public @ResponseBody String uploadNewInterest(@DESParam Integer uid,@PathVariable String interestName) throws Exception{
    	
    	ResultModel result = new ResultModel();
    	interestBiz.saveInterestAudit(uid, interestName);
    	return result.toJSON();
    }
}
