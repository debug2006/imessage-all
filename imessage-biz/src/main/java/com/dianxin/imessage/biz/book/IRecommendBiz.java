package com.dianxin.imessage.biz.book;

import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.lbs.data.UserInfo;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/1/29
 * Time: 8:18
 */
public interface IRecommendBiz {
    /**
     * 经纬度上传
     * @param uid
     * @param lon
     * @param lat
     * @return
     */
    String upLoadPoisition(String uid,String lon,String lat);

    /**
     * 获取推荐的列表  从lbs取出来
     * @param UserInfo
     * @return
     */
    List<MongoPosition> getRecommendListFromLbs(UserInfo info);

    /**
     * 获取推荐的列表
     * @param UserInfo 从数据库取出来
     * @return
     */
    List<MongoPosition> getRecommendListFromDb(UserInfo info);

    /**
     * 全网随机推荐 5个人
     * @return
     */
    List<DxUserModel> getRecommendForFixed(DxUserModel userModel);
}
