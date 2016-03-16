package com.dianxin.imessage.dao.util.page;

import java.io.Serializable;

public class PageInfo implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageId = 1; // 当前页
	private Integer rowCount = 0; // 总行数
	private Integer pageSize = 10; // 页大小
	private Integer pageCount = 0; // 总页数

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

}
