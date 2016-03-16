package com.dianxin.imessage.biz.config;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserAddrModel;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由UserAddrServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:34:53 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IUserAddrBiz  extends IBaseBiz<DxUserAddrModel> {
    /**
     * 插入一条记录
     * @param dxUserAddrModel
     * @return
     */
    boolean addRecord(DxUserAddrModel dxUserAddrModel);

    /**
     * 更新一条记录
     * @param dxUserAddrModel
     * @return
     */
    boolean updateRecord(DxUserAddrModel dxUserAddrModel);


    /**
     * 根据uid 查询所有地址记录
     * @param uid
     * @return
     */
    List<DxUserAddrModel> queryList(Integer uid);
}
