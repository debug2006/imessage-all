package com.dianxin.imessage.common.lbs.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/1/31
 * Time: 17:20
 */
public class MongoPosition implements Serializable{

    private static final long serialVersionUID = 5547431148038721103L;
    private String objId;
    private Integer uid;
    private Double distance;
    private String distanceStr;
    private Date date;
    private String interestNum; //具体相同兴趣描述
    private String userNum;
    private String userType;  //0:好友 1:不是好友
    private String headUrl;  //头像地址
    private Double score;  //该用户总分
    private int min;    //当前时间与插入时间 所间隔的分钟数
    private int sameInt;  //兴趣相同的个数
    private String nickName;
    private String fit;  //星座匹配度
    private String sex;  //用户性别 性别 1：男  2：女',  0为未填

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSameInt() {
        return sameInt;
    }

    public void setSameInt(int sameInt) {
        this.sameInt = sameInt;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getDistanceStr() {
        return distanceStr;
    }

    public void setDistanceStr(String distanceStr) {
        this.distanceStr = distanceStr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getInterestNum() {
        return interestNum;
    }

    public void setInterestNum(String interestNum) {
        this.interestNum = interestNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
