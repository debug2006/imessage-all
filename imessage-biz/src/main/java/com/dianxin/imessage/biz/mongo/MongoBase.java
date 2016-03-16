package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.common.lbs.MongoDBManager;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/4
 * Time: 12:00
 */
public abstract class MongoBase {
    private static MongoCollection<Document> coll_position = null;
    private static MongoCollection<Document> coll_trail = null;

    static
    {
        String dbName = MongoDBManager.DB_NAME;
        String position = MongoDBManager.COLL_POSITION;
        String trail = MongoDBManager.COLL_TRAIL;
        coll_position = MongoDBManager.instance.getCollection(dbName, position);
        coll_trail = MongoDBManager.instance.getCollection(dbName, trail);
    }

    public static MongoCollection<Document> getColl_trail() {
        return coll_trail;
    }

    public static void setColl_trail(MongoCollection<Document> coll_trail) {
        MongoBase.coll_trail = coll_trail;
    }

    public static MongoCollection<Document> getColl_position() {
        return coll_position;
    }

    public static void setColl_position(MongoCollection<Document> coll_position) {
        MongoBase.coll_position = coll_position;
    }
}
