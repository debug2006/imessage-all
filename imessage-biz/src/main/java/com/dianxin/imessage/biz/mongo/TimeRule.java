package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.common.lbs.data.MongoPosition;

/**
 * 最近在附近出现的时间排序：
 * 1分钟以内（包括1分钟），25分；1-5分钟，23分；5-10分钟，20分；10-20分钟，18分；20-30分钟，16分；
 * 30分钟-1小时，14分；1小时-5小时，10分；5小时-10小时，6分；10-24小时 3分；24小时以上，0分
 * 【时间这个因素也很重要，其他app都会根据时间进行排序，
 * 我们假设在本地1分钟以内出现的用户重要性等同于1个24小时之前出现在本地的异性用户，
 * 24小时以上的赋予0分，与24小时就焚掉的调性一致；需要显示时间】
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/1
 * Time: 10:25
 */
public class TimeRule {

    /**
     * 根据时间出现的顺序 获取该人的分数
     * @param mongoPosition
     * @return
     */
    public static double getScoreByTime(MongoPosition mongoPosition)
    {
        double score = 0;
        int timemins = mongoPosition.getMin();
        if(timemins<=1)
        {
            score = 25;
        }
        else if(1<timemins && timemins<=5)
        {
            score = 23;
        }
        else if(5<timemins && timemins<=10)
        {
            score = 20;
        }
        else if(10<timemins && timemins<=20)
        {
            score = 18;
        }
        else if(20<timemins && timemins<=30)
        {
            score = 16;
        }
        else if(30<timemins && timemins<=60)
        {
            score = 14;
        }
        else if(60<timemins && timemins<=300)
        {
            score = 10;
        }
        else if(300<timemins && timemins<=600)
        {
            score = 6;
        }
        else if(600<timemins && timemins<1440)
        {
            score = 3;
        }
        else
        {
            score = 0;
        }
        return score;
    }
}
