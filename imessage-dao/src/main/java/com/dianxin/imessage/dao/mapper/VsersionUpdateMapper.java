package com.dianxin.imessage.dao.mapper;

import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxVsersionUpdateModel;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由Mapper映射文件来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 12:53:56 CST 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface VsersionUpdateMapper<T> extends BaseMapper<T>{

    public DxVsersionUpdateModel queryVersionByParm(@Param(value = "deviceType") Integer deviceType) throws Exception;
}
