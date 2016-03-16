package com.dianxin.imessage.dao.base;

import java.io.Serializable;

import com.dianxin.imessage.dao.util.page.PageUtil;

/**
 * 
 * <br>
 * <b>功能：</b>模型-处理分页查询功能<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b>2016/1/12<br>
 * <b>版权所有：<b>dianxin.com<br>
 */
public class BaseModel implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     * <br>
     * <b>功能：</b>分页实现<br>
     * <b>作者：</b>kai.fantasy<br>
     * <b>日期：</b> 2016/1/12<br>
     * @param navigate
     */
	private PageUtil pageUtil=new PageUtil();

	public PageUtil getPageUtil() {
		return pageUtil;
	}
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}
	
}

