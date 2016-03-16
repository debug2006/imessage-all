package com.dianxin.imessage.common.lbs.data;

import java.io.Serializable;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/27
 * Time: 18:16
 */
public class MongoChat implements Serializable{
    private static final long serialVersionUID = -8272025937073447309L;
    private String from;
    private String to;
    private String body;


    public MongoChat(String from,String to,String body)
    {
        this.from = from;
        this.to = to;
        this.body = body;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
