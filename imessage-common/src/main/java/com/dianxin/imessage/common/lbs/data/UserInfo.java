package com.dianxin.imessage.common.lbs.data;

import java.io.Serializable;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/4
 * Time: 10:24
 */
public class UserInfo implements Serializable{
    private static final long serialVersionUID = -8698552510770150120L;
    private String lat;
    private String lon;
    private String uid;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
