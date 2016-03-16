package com.dianxin.imessage.dao.dataobject.config;

import java.util.List;

import com.dianxin.imessage.dao.base.SearchResultModel;

public class SchoolNameList extends SearchResultModel {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 学校名称列表
	 */
	private List<String> schoolNames;

	public List<String> getSchoolNames() {
		return schoolNames;
	}

	public void setSchoolNames(List<String> schoolNames) {
		this.schoolNames = schoolNames;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}
}
