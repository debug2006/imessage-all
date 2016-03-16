package com.dianxin.imessage.common.lbs;

import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.util.DateUtil;
import com.mongodb.BasicDBObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/4
 * Time: 9:04
 */
public class LbsLogic {

    /** 100公里 单位 米*/
    public static Integer LBS_DISTANCE = 100000;

    /**200人 阈值*/
    public static Integer LBS_PEOPLES = 200;

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param long2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：米
     */
    public static double getDistance(double long1, double lat1, double long2,
                                  double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }


    /**
     * 获取100公里以内200人
     * @param
     * @param lat
     * @param lon
     * @return
     */
    public static List<MongoPosition> getLbs(String lat,String lon)
    {
        BasicDBObject myCmd = new BasicDBObject();
        myCmd.append("geoNear", MongoDBManager.instance.COLL_POSITION);
        double[] loc = {Double.valueOf(lon),Double.valueOf(lat)};
        myCmd.append("near", loc);
        myCmd.append("spherical", true);
        myCmd.append("maxDistance", (double) LBS_DISTANCE/6378000 );
        myCmd.append("distanceMultiplier",6378000);
        System.out.println(myCmd);

        Document myResults = null;
        try
        {
            //获取记录
            myResults = MongoDBManager.instance.getDB(MongoDBManager.DB_NAME).runCommand(myCmd);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        //封装为系统集合
        List list = new ArrayList<MongoPosition>();

        //结果集
        Collection<Object> objects =  myResults.values();

        Object[] objects_ = objects.toArray();
        //返回的经纬度记录集
        ArrayList list_ = (ArrayList)objects_[1];

        for (int i = 0; i < list_.size(); i++) {
            Document doc = (Document)list_.get(i);
            Double dis = (Double)doc.get("dis");
            Document doc_ = (Document)doc.get("obj");

            //计算相隔天数 超过2天就去除
            Date dateTime = doc_.getDate("dateTime");

            String nowTime = DateUtil.getLocalTimeFromUTC(new Date());
            String dateTimeStr = DateUtil.getLocalTimeFromUTC(dateTime);
            int min = 0;
            try
            {
                //获取 当前时间和经纬度时间 间隔的分钟
                min = DateUtil.getMinuteDiff(dateTimeStr,nowTime);

            }catch (Exception e)
            {
                min = 10000000;  //异常的情况 标示不需要展示
                e.printStackTrace();
            }
            int day = DateUtil.calculateNumberOfDays(dateTimeStr);
            if(day>2)continue;

            MongoPosition position = new MongoPosition();
            position.setDistance(dis);
            position.setDate(dateTime);
            position.setUid(Integer.valueOf(doc_.get("uid").toString()));
            position.setMin(min);

            list.add(position);
        }

        //200人限制
        int count = LBS_PEOPLES;
        if(list.size()<=LBS_PEOPLES)
        {
            return list;
        }

        return list.subList(0,LBS_PEOPLES-1);
    }
}
