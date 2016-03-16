package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.common.spring.SpringUtil;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

import javax.annotation.Resource;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/1
 * Time: 20:37
 */
@Resource
public class InterestRule {
//    @Autowired
//    private IInterestBiz iInterestBiz;

    public double getScoreByInt(DxUserModel user,DxUserModel toUser)
    {
        double score = 0.0;
        Integer count = 0;
        try
        {

            IInterestBiz iInterestBiz = (IInterestBiz) SpringUtil.getBean("interestBiz");
            if(null!=iInterestBiz)
            {
                count = iInterestBiz.commonInterestNum(user.getUid(),toUser.getUid());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        score = count*1;
        return score;
    }


}
