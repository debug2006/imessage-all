package com.dianxin.imessage.biz.config;
       
import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxVsersionUpdateModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由VsersionUpdateServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 12:53:56 CST 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IVsersionUpdateBiz  extends IBaseBiz<DxVsersionUpdateModel> {

    /**
     * 根据devicetype 设备类型 获取最新版本信息，进行对比
     * @return
     */
    DxVsersionUpdateModel queryVersion(Integer deviceType);

}
