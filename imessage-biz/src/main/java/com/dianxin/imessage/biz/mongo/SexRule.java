package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/1
 * Time: 11:46
 */
public class SexRule {


    /**
     * 取向排名，满足条件给予25分的满分。例如，对于取向为女性的用户，陌生人中，女性用户自动获得25分
     * 【如果我们假设一个人去认识陌生人最大的动力是原始的性冲动，那么以异性为参照物，
     * 用某个条件大概相当于异性这个条件分值的比例来赋予分值】
     * @param position
     * @param user
     * @param toUser
     */
    public static double getScoreBySex(MongoPosition position,DxUserModel user,DxUserModel toUser)
    {
        //TODO
        double score = 0;

        int userSex = 0;
        int toUserSex = 0;

        if(null != user.getSexual()&&null!= toUser.getSex())
        {
            userSex = user.getSexual();
            toUserSex = toUser.getSex();

        }

        if(userSex!=0 && userSex == toUserSex)
        {
            score = 25;
        }

        return score;
    }
}
