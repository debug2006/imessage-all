package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.common.lbs.LbsLogic;
import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/1
 * Time: 10:41
 */
public class RangeRule extends MongoBase{

    /**
     * 范围过滤 ******* 周边人板块
     * @param mongoPosition
     */
    public static double rangeFilter(MongoPosition mongoPosition)
    {
        double distance = mongoPosition.getDistance();
        return getscore(mongoPosition,distance);

    }


    /**
     * 范围过滤 ******* 兴趣匹配板块
     * 分别获取mongo里面 当前的经纬度数据，再进行计算获取两者距离
     * @param mongoPosition
     * @param user
     * @param toUser
     * @return
     */
    public static double rangeFilter(MongoPosition mongoPosition,DxUserModel user,DxUserModel toUser)
    {
        double distance = 0d;
        double lon1= 0d;
        double lat1= 0d;
        double lon2= 0d;
        double lat2= 0d;

        MongoCursor<Document> cursor1 = getColl_position().find(Filters.eq("uid", user.getUid())).iterator();
        while (cursor1.hasNext()) {
            org.bson.Document _doc = (Document) cursor1.next();
            ArrayList gps = (ArrayList)_doc.get("gps");
            lon1 = (Double)gps.get(0);
            lat1 = (Double)gps.get(1);
        }
        cursor1.close();

        MongoCursor<Document> cursor2 = getColl_position().find(Filters.eq("uid", toUser.getUid())).iterator();
        while (cursor2.hasNext()) {
            org.bson.Document _doc = (Document) cursor2.next();
            ArrayList gps = (ArrayList)_doc.get("gps");
            lon2 = (Double)gps.get(0);
            lat2 = (Double)gps.get(1);
        }
        cursor2.close();

        distance = LbsLogic.getDistance(lon1,lat1,lon2,lat2);

        return getscore(mongoPosition,distance);

    }

    public static void main(String[] args)
    {
        double distance = 0d;
        double lon1= 0d;
        double lat1= 0d;
        double lon2= 0d;
        double lat2= 0d;

        MongoCursor<Document> cursor1 = getColl_position().find(Filters.eq("uid", "1093321307")).iterator();
        while (cursor1.hasNext()) {
            org.bson.Document _doc = (Document) cursor1.next();
            ArrayList gps = (ArrayList)_doc.get("gps");
            lon1 = (Double)gps.get(0);
            lat1 = (Double)gps.get(1);
        }
        cursor1.close();

        MongoCursor<Document> cursor2 = getColl_position().find(Filters.eq("uid", "1069195600")).iterator();
        while (cursor2.hasNext()) {
            org.bson.Document _doc = (Document) cursor2.next();
            ArrayList gps = (ArrayList)_doc.get("gps");
            lon2 = (Double)gps.get(0);
            lat2 = (Double)gps.get(1);
        }
        cursor2.close();

        distance = LbsLogic.getDistance(lon1,lat1,lon2,lat2);
    }

    /**
     * 具体计分算法
     * @param mongoPosition
     * @param distance
     * @return
     */
    private static double getscore(MongoPosition mongoPosition,double distance)
    {
        if(distance<200)
        {
            mongoPosition.setDistanceStr("<200m");
            return 17;
        }
        if(200<=distance && distance<500)
        {
            mongoPosition.setDistanceStr("<500m");
            return 16;
        }
        if(500<=distance && distance<1000)
        {
            mongoPosition.setDistanceStr("<1km");
            return 15;
        }
        if(1000<=distance && distance<1500)
        {
            mongoPosition.setDistanceStr("<1.5km");
            return 14;
        }
        if(1500<=distance && distance<2000)
        {
            mongoPosition.setDistanceStr("<2km");
            return 13;
        }
        if(2000<=distance && distance<5000)
        {
            mongoPosition.setDistanceStr("<5km");
            return 12;
        }
        if(5000<=distance && distance<10000)
        {
            mongoPosition.setDistanceStr("<10km");
            return 11;
        }
        if(10000<=distance && distance<20000)
        {
            mongoPosition.setDistanceStr("<20km");
            return 10;
        }
        if(20000<=distance && distance<30000)
        {
            mongoPosition.setDistanceStr("<30km");
            return 9;
        }
        if(30000<=distance && distance<40000)
        {
            mongoPosition.setDistanceStr("<40km");
            return 8;
        }
        if(40000<=distance && distance<50000)
        {
            mongoPosition.setDistanceStr("<50km");
            return 7;
        }
        if(50000<=distance && distance<60000)
        {
            mongoPosition.setDistanceStr("<60km");
            return 6;
        }
        if(60000<=distance && distance<70000)
        {
            mongoPosition.setDistanceStr("<70km");
            return 5;
        }
        if(70000<=distance && distance<80000)
        {
            mongoPosition.setDistanceStr("<80km");
            return 4;
        }
        if(80000<=distance && distance<90000)
        {
            mongoPosition.setDistanceStr("<90km");
            return 3;
        }
        if(90000<=distance && distance<100000)
        {
            mongoPosition.setDistanceStr("<100km");
            return 2;
        }

        return 1;
    }
}
