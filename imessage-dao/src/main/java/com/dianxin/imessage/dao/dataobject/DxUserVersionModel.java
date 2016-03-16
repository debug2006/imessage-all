package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Tue Jan 19 00:11:13 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxUserVersionModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	private Integer id;

	/**
	 * 用户uid
	 */
	private Integer uid;

	/**
	 * 系统名称
	 */
	private String sysName;

	/**
	 * 系统版本号
	 */
	private String sysVersion;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 版本描述
	 */
	private String vesionDesc;

	/**
	 * 设备标识
	 */
	private String imei;

	/**
	 * 手机型号
	 */
	private String model;

	/**
	 * 创建时间
	 */
	private java.sql.Timestamp createDate;

	/**
	 * 修改时间
	 */
	private java.sql.Timestamp modifyDate;

	/**
	 * get 自增id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * set 自增id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * get 用户uid
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * set 用户uid
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * get 系统名称
	 */
	public String getSysName() {
		return sysName;
	}

	/**
	 * set 系统名称
	 */
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	/**
	 * get 系统版本号
	 */
	public String getSysVersion() {
		return sysVersion;
	}

	/**
	 * set 系统版本号
	 */
	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	/**
	 * get 版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * set 版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * get 版本描述
	 */
	public String getVesionDesc() {
		return vesionDesc;
	}

	/**
	 * set 版本描述
	 */
	public void setVesionDesc(String vesionDesc) {
		this.vesionDesc = vesionDesc;
	}

	/**
	 * get 设备标识
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * set 设备标识
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * get 手机型号
	 */
	public String getModel() {
		return model;
	}

	/**
	 * set 手机型号
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * get 创建时间
	 */
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * set 创建时间
	 */
	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * get 修改时间
	 */
	public java.sql.Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * set 修改时间
	 */
	public void setModifyDate(java.sql.Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}