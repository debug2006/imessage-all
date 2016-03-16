package com.dianxin.imessage.biz.config;
       
import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxActivityStatModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由ActivityStatServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Mar 07 22:34:01 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IActivityStatBiz  extends IBaseBiz<DxActivityStatModel> {

    boolean addActivityTimes(Integer uid);

}
