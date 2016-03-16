package com.dianxin.imessage.dao.base;

import java.io.Serializable;

import com.dianxin.imessage.dao.util.page.PageInfo;

/**
 * 
 * BaseModel 的简化，分页结果
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SearchResultModel implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private PageInfo pageInfo = new PageInfo();

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}
