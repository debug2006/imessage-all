package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;

public class ReportInfo implements Serializable{

	/**
	 */
	private static final long serialVersionUID = -1663889315058027631L;

	private String uid;

	private String respondentId;

	private String reportReason;

	private String reportDesc;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRespondentId() {
		return respondentId;
	}

	public void setRespondentId(String respondentId) {
		this.respondentId = respondentId;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
