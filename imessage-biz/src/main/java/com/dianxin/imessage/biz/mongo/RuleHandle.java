package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.util.page.PageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/1/31
 * Time: 21:31
 */
public class RuleHandle {

    private static InterestRule interestRule = new InterestRule();

    public static String LBS_PREFIX = "lbs_";
    public static final int LBS_TIMEOUT = 60;  //过期时间为3600s

    public static String INTEREST_PREFIX = "interest_";  //兴趣
    public static final int INTEREST_TIMEOUT = 60;  //过期时间为3600s

    public static final int PAGE_VIEW = 12; //一页展示多少记录

    private static final int RATIO = 5000; //分母系数

    /**
     * 周边人版本*****************规则过滤
     * 1/范围过滤
     * 2/性取向过滤
     * 3/兴趣过滤
     * 4/最近在附近出现的时间过滤
     * 5/星座过滤
     * @param mongoPosition
     */
    public static  MongoPosition handleForLbs(MongoPosition mongoPosition,DxUserModel user,DxUserModel toUser)
    {
        double score = 0;

        //范围过滤
        double rangeScore = RangeRule.rangeFilter(mongoPosition);

        //性取向过滤
        double sexScore = SexRule.getScoreBySex(mongoPosition,user,toUser);

        //兴趣过滤
        double instScore = interestRule.getScoreByInt(user,toUser);

        //根据间隔分钟 计算该项得分
        double timescore = TimeRule.getScoreByTime(mongoPosition);

        //星座过滤
        double conScore = ConstellationRule.getScoreByConstellation(mongoPosition,user,toUser);

        //user 业务处理
        UserRule.handleUser(mongoPosition,user,toUser);

        score = sexScore + timescore + conScore + instScore;

        score = rangeScore + score/RATIO;

        mongoPosition.setScore(score);
        return mongoPosition;

    }


    /**
     * 兴趣匹配 版本  ***************规则过滤
     * 1/范围过滤
     * 2/性取向过滤
     * 3/兴趣过滤
     * 4/最近在附近出现的时间过滤
     * 5/星座过滤
     * @param mongoPosition
     */
    public static  MongoPosition handleForInterst(MongoPosition mongoPosition,DxUserModel user,DxUserModel toUser)
    {
        double score = 0;

        //范围过滤
        double rangeScore = RangeRule.rangeFilter(mongoPosition, user, toUser);

        //性取向过滤
        double sexScore = SexRule.getScoreBySex(mongoPosition,user,toUser);

        //兴趣过滤
        double instScore = interestRule.getScoreByInt(user,toUser);

        //根据间隔分钟 计算该项得分
        //double timescore = TimeRule.getScoreByTime(mongoPosition);

        //星座过滤
        double conScore = ConstellationRule.getScoreByConstellation(mongoPosition,user,toUser);

        //user 业务处理
        UserRule.handleUser(mongoPosition,user,toUser);

        score = sexScore  + conScore + rangeScore;

        score = instScore + score/RATIO;

        mongoPosition.setScore(score);
        return mongoPosition;

    }


    /**
     * 存储获取的lbs记录
     * 思路：按照分页的形式 存sublist
     * @param positionList
     * @param uid
     * @throws Exception
     */
    public static List<MongoPosition> groupList(MemcachedFactory cacheFactory,List<MongoPosition> positionList,String uid,int flag,String pageNum) throws Exception
    {

        String prefix = "";
        int timeout = 0;
        if(flag == 1)
        {
            prefix = RuleHandle.INTEREST_PREFIX;
            timeout = RuleHandle.INTEREST_TIMEOUT;
        }
        else if(flag == 0)
        {
            prefix = RuleHandle.LBS_PREFIX;
            timeout = RuleHandle.LBS_TIMEOUT;
        }
        PageUtil page = new PageUtil();
        page.setPageSize(PAGE_VIEW);  //一页显示20
        page.setRowCount(positionList.size()); //总记录数

        int pageCount = page.getPageCount();  //得到总页数

        //按照分组 存入缓存
        Memcached cache = cacheFactory.createMemcached();

        //存缓存的时候 清理缓存
        for(int i=1;i<=pageCount;i++)
        {
            cache.delete(prefix + uid+ "_"+String.valueOf(i));
        }

        for(int i=1;i<=pageCount;i++)
        {
            page.setPageId(i); //设置当前第几页  从而获取 当前页起始记录+结束记录

            int start = 0;
            int end = 0;
            int mod = page.getRowCount()%PAGE_VIEW;

            if(page.getRowCount()<=PAGE_VIEW)
            {
                end = page.getRowCount();
            }
            else
            {
                if(mod == 0)
                {
                    end = PAGE_VIEW*i-1;

                }
                else if(mod > 0)
                {
                    if(i<pageCount)
                    {
                        end = PAGE_VIEW*i-1;
                    }else if(i == pageCount)
                    {
                        end = page.getRowCount()-1;
                    }
                }
            }

            List<MongoPosition> newList = new ArrayList<MongoPosition>();  //sublist 不能序列化
            for(int j = page.getPageOffset();j<end;j++)
            {
                newList.add((MongoPosition)positionList.get(j));
            }
            cache.set(prefix + uid+ "_"+String.valueOf(i),newList , timeout);
        }

        List<MongoPosition> positionCache = (List<MongoPosition>)cache.get(prefix + uid + "_"+ pageNum);
        if(null != positionCache && positionCache.size() > 0)
        {
            return positionCache;
        }

        return null;

    }



}
