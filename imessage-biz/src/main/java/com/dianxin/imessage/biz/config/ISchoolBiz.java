package com.dianxin.imessage.biz.config;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxSchoolModel;
import com.dianxin.imessage.dao.dataobject.config.SchoolNameList;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由SchoolServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:42:04 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface ISchoolBiz extends IBaseBiz<DxSchoolModel> {

	SchoolNameList querySchools(String inputDesc, Integer pageId, Integer pageSize);

}
