package com.dianxin.imessage.biz.book.impl;

import com.dianxin.imessage.biz.book.IRecommendBiz;
import com.dianxin.imessage.biz.mongo.MongoBase;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.lbs.LbsLogic;
import com.dianxin.imessage.common.lbs.MongoDBManager;
import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.lbs.data.UserInfo;
import com.dianxin.imessage.common.util.ValidatorTools;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.mapper.ActivityStatMapper;
import com.dianxin.imessage.dao.mapper.UserMapper;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/1/29
 * Time: 8:20
 */
@Service
public class RecommendBiz extends MongoBase implements IRecommendBiz {

    @Autowired
    private ActivityStatMapper activityStatMapper;

    @Autowired
    private UserMapper userMapper;

    private static final  int WEEK_COUNT = 160;
    private static final  int NONE_COUNT = 40;
    private static final  int COUNT = 200;

    private static final int RECOMMEND_COUNT = 5;

    /**
     * 上传经纬度
     * @param uid
     * @param lon
     * @param lat
     * @return
     */
    public String upLoadPoisition(String uid,String lon,String lat)
    {
        String errorCode = ErrCodeEnum.NORMAL.value;

        //正则校验
        if(!ValidatorTools.isUserUid(uid))
        {
            return ErrCodeEnum.UID_FORMAT_ERR.value;
        }
        if(!ValidatorTools.isJingweidu(lon,lat))
        {
            return  ErrCodeEnum.MONGODB_JINGWEIDU_ERR.value;
        }


        //逻辑改下 新增mongio 存轨迹的大表 trail position 大表 插入的时候判断是否该用户已经有记录，如果有则删除，插入一条新的
        Document doc = new Document();
        List<Double> gps = new ArrayList<Double>();
        gps.add(Double.valueOf(lon));
        gps.add(Double.valueOf(lat));
        doc.put("uid", uid);
        doc.append("gps", gps);
        doc.append("dateTime", new Date());

        MongoCursor<Document> cursor1 = getColl_position().find(Filters.eq("uid", uid)).iterator();
        synchronized (cursor1) {
            while (cursor1.hasNext()) {
                org.bson.Document _doc = (Document) cursor1.next();
                String _id = _doc.get("_id").toString();
                MongoDBManager.instance.deleteById(getColl_position(), _id);
            }
            cursor1.close();
            try {
                getColl_position().insertOne(doc);
                getColl_trail().insertOne(doc);
            } catch (Exception e) {
                e.printStackTrace();
                return ErrCodeEnum.MONGODB_ERR.value;
            }
            return errorCode;
        }

    }

    /**
     * 根据uid 空间查询 100公里范围的200人 进到远
     * @param
     * @return
     */
    public List<MongoPosition> getRecommendListFromLbs(UserInfo info)
    {
        return LbsLogic.getLbs(info.getLat(), info.getLon());
    }


    /**
     * 周活跃用户/非周活跃用户
     * 周活跃用户中随机筛选160人；非周活跃用户中随机筛选40人
     * @param
     * @return
     */
    public List<MongoPosition> getRecommendListFromDb(UserInfo info)
    {
        //待构造的对象list
        List<MongoPosition> mongoPositionList = new ArrayList<MongoPosition>();

        //逻辑   1、获取周活用户uid列表   2、查询全网所有用户uid列表  3、得出非周活跃用户uid列表
        //4、假如周活跃用户<160 则取200-周为非周；总数<200则取总数
        try {
            List<String> weekList = activityStatMapper.queryActivityUserByWeek();
            List<DxUserModel> allUserList = userMapper.selectBySql("select uid from tbl_user where uid != 1001 and is_del =1");

            int weekCount = weekList.size();
            List<DxUserModel> weekUserList = new ArrayList<DxUserModel>();
            List<DxUserModel> noneWeekUserList = new ArrayList<DxUserModel>();
            //计算得出 周活跃和非周活跃list
            if(weekCount>0) {
                for (DxUserModel dxUserModel : allUserList) {
                    String uid = String.valueOf(dxUserModel.getUid());
                    if(!uid.equals("1001")&&!uid.equals(info.getUid())) {  //去掉admin用户
                        if (weekList.contains(uid)) {
                            weekUserList.add(dxUserModel);
                        } else {
                            noneWeekUserList.add(dxUserModel);
                        }
                    }
                }
            }
            int noneWeekCount = noneWeekUserList.size();

            int weekmust = 0;
            int noneweekmust = 0;
            weekCount = weekUserList.size();

            //if 周活跃人数小于160  则非周取200-周活跃的
            if(weekCount < WEEK_COUNT) {
                weekmust = weekCount;
                int noneCount = COUNT-weekCount;
                if(noneCount>=noneWeekCount) {
                    noneweekmust = noneWeekCount;
                }
            }
            else {
                if(noneWeekCount<NONE_COUNT) {
                    noneweekmust = noneWeekCount;
                }
                else{
                    noneweekmust = NONE_COUNT;
                }
                weekmust = WEEK_COUNT;
            }

            List<DxUserModel> l1 = randomTopic(weekUserList,weekmust);
            for(DxUserModel model : l1) {
                MongoPosition mongoPosition = new MongoPosition();
                mongoPosition.setUid(model.getUid());
                mongoPositionList.add(mongoPosition);
            }

            List<DxUserModel> l2 = randomTopic(noneWeekUserList,noneweekmust);
            for(DxUserModel model : l2) {
                MongoPosition mongoPosition = new MongoPosition();
                mongoPosition.setUid(model.getUid());
                mongoPositionList.add(mongoPosition);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mongoPositionList;
    }


    // 从List中随机出count个对象
    private List<DxUserModel> randomTopic(List<DxUserModel> list, int count) {
        // 创建一个长度为count(count<=list)的数组,用于存随机数
        int[] a = new int[count];
        // 利于此数组产生随机数
        int[] b = new int[list.size()];
        int size = list.size();

        // 取样填充至数组a满
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * size); // [0,size)
            int where = -1;
            for (int j = 0; j < b.length; j++) {
                if (b[j] != -1) {
                    where++;
                    if (where == num) {
                        b[j] = -1;
                        a[i] = j;
                    }
                }
            }
            size = size - 1;
        }
        // a填满后 将数据加载到rslist
        List<DxUserModel> rslist = new ArrayList<DxUserModel>();
        for (int i = 0; i < count; i++) {
            DxUserModel df = (DxUserModel) list.get(a[i]);
            rslist.add(df);
        }
        return rslist;
    }


    /**
     * 全网随机推荐5个人
     * @param
     * @return
     */
    @Override
    public List<DxUserModel> getRecommendForFixed(DxUserModel userModel) {
        if(null==userModel)
        {
            return null;
        }
        Integer sex = userModel.getSex();
        StringBuilder sql = new StringBuilder("select uid,sex,complete_degree from tbl_user where uid != 1001 and complete_degree >=50 and uid !="+userModel.getUid());
//        StringBuilder sql = new StringBuilder("select uid,sex,complete_degree from tbl_user where uid != 1001");

        //1. 随机推荐5个个人资料完成度50%以上的异性。
        //2. 如果用户自己没有填写性别，抽取5个资料完成度50%以上的女性用户进行推荐。
        //3. 一天只更新一次名单。
        if(null!=sex)
        {
            sql.append(" and sex ="+sex);
        }else
        {
            sql.append(" and sex = 2");
        }
        List<DxUserModel> allUserList = new ArrayList<DxUserModel>();
        try {
            allUserList = userMapper.selectBySql(sql.toString());
            if(null!=allUserList&&allUserList.size()>0)
            {
                if(allUserList.size()<RECOMMEND_COUNT)
                {
                    return allUserList;
                }
                allUserList = randomTopic(allUserList,RECOMMEND_COUNT);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return allUserList;
    }
}
