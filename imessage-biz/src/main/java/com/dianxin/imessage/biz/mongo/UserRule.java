package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.spring.SpringUtil;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/4
 * Time: 11:41
 */
public class UserRule {

    /**
     * 处理user的业务规则
     * @param user
     * @param toUser
     * @return
     */
    public static void handleUser(MongoPosition mongoPosition,DxUserModel user,DxUserModel toUser)
    {
        boolean result = false;
        int count = 0; //兴趣相同个数
        String insterestStr = ""; //具体相同兴趣
        try
        {

            IUserBiz userBiz = (IUserBiz) SpringUtil.getBean("userBiz");
            if(null!= userBiz)
            {
                result = userBiz.isFirend(Integer.valueOf(user.getUid()), Integer.valueOf(toUser.getUid()));
            }

            if(result)
                mongoPosition.setUserType("0"); //好友
            else
                mongoPosition.setUserType("1");  //非好友

            mongoPosition.setUserNum(toUser.getUserNum());
            mongoPosition.setSex(null!=toUser.getSex()?String.valueOf(toUser.getSex()):"0");

            //获取2者相同兴趣
            IInterestBiz iInterestBiz = (IInterestBiz) SpringUtil.getBean("interestBiz");
            if(null != iInterestBiz) {
                count = iInterestBiz.commonInterestNum(user.getUid(), toUser.getUid());
                mongoPosition.setSameInt(count);

                //获取相同兴趣的 字符串
                insterestStr = iInterestBiz.commonInterestName(user.getUid(), toUser.getUid());
                mongoPosition.setInterestNum(null != insterestStr ? insterestStr : "");
            }

            //获取用户头像上传地址
            IUserImageBiz userImageBiz = (IUserImageBiz) SpringUtil.getBean("userImageBiz");
            if(null != userImageBiz)
            {
                DxUserImageModel imageModel = userImageBiz.getUserImage(Integer.valueOf(toUser.getUid()));
                if(null!=imageModel)
                {
                    mongoPosition.setHeadUrl(imageModel.getFilePath());
                }
            }

            //nickname
            mongoPosition.setNickName(toUser.getUserName());


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
