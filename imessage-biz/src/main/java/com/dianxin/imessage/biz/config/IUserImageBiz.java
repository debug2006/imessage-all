package com.dianxin.imessage.biz.config;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由UserImageServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 30 15:22:21 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IUserImageBiz extends IBaseBiz<DxUserImageModel> {

    /**
     * 根据uid获取用户头像
     */
    DxUserImageModel getUserImage(Integer uid) throws Exception;

    /**
     * 
     * 功能描述: 设置用户的头像 <br>
     * 〈功能详细描述〉
     *
     * @param uid UID
     * @param photoPaht 头像地址
     * @return 
     */
    String setUserPhoto(Integer uid, String photoPath);
}
