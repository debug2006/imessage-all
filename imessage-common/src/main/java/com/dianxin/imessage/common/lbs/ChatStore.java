package com.dianxin.imessage.common.lbs;

import com.dianxin.imessage.common.lbs.data.MongoChat;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/27
 * Time: 18:13
 */
public class ChatStore {

    private static MongoCollection<Document> coll_chat = null;
    private static Logger logger = LoggerFactory.getLogger(ChatStore.class);
    static
    {
        String dbName = MongoDBManager.DB_NAME;
        String chat = MongoDBManager.COLL_CHAT;

        coll_chat  = MongoDBManager.instance.getCollection(dbName,chat);
    }

    public static void storeMessage(MongoChat mongoChat)
    {
        try
        {
            Document doc = new Document();
            doc.put("from", mongoChat.getFrom());
            doc.append("to", mongoChat.getTo());
            doc.append("body", mongoChat.getBody());
            doc.append("time", new Date());

            coll_chat.insertOne(doc);
        }catch (Exception e)
        {
            logger.error("mongodb storeMessage error {}", e);
            e.printStackTrace();
        }


    }
}
