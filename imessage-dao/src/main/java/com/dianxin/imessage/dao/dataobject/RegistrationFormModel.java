package com.dianxin.imessage.dao.dataobject;

public class RegistrationFormModel {

	/** -------------------注册时，用户填写信息 -------------------------- */

	/*
	 * 国家码
	 */
	private String districtNumbe;

	/*
	 * 手机号码
	 */
	// @Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$",message =
	// "手机号码格式错误")
	private Long telphone;

	/*
	 * 用户昵称
	 */
	// @Length(max = 60,message = "最长为60个字符，一个汉字包含两个字符")
	private String userName;

	/*
	 * 用户密码
	 */
	private String password;

	/*
	 * 用户超能力ID
	 */
	private String abilityId;

	/*
	 * 头像地址
	 */
	private String photoPath;

	/** ----------------------注册时，用户设备信息，及纸条君版本 -------------------------- */

	/*
	 * 注册来源 1:android 2：ios 3：h5 4:web
	 */
	private Integer regSource;

	/*
	 * 注册时，客户的版本
	 */
	private String regVersion;

	/*
	 * 系统名称 (手机系统名称)
	 */
	private String sysName;

	/*
	 * 系统版本号 (手机系统)
	 */
	private String sysVersion;

	/*
	 * 版本描述
	 */
	private String vesionDesc;

	/*
	 * 手机IMEI
	 */
	private String imei;

	/*
	 * 手机型号
	 */
	private String model;

	public String getDistrictNumbe() {
		return districtNumbe;
	}

	public void setDistrictNumbe(String districtNumbe) {
		this.districtNumbe = districtNumbe;
	}

	public Long getTelphone() {
		return telphone;
	}

	public void setTelphone(Long telphone) {
		this.telphone = telphone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}

	public Integer getRegSource() {
		return regSource;
	}

	public void setRegSource(Integer regSource) {
		this.regSource = regSource;
	}

	public String getRegVersion() {
		return regVersion;
	}

	public void setRegVersion(String regVersion) {
		this.regVersion = regVersion;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getVesionDesc() {
		return vesionDesc;
	}

	public void setVesionDesc(String vesionDesc) {
		this.vesionDesc = vesionDesc;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}
}
