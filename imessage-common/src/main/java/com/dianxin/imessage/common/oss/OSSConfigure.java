package com.dianxin.imessage.common.oss;

import com.dianxin.imessage.common.util.PropertiesUtil;

public class OSSConfigure {

	/**
	 * endpoint
	 */
	private String endpoint;

	/**
	 * 链接OSS ID
	 */
	private String accessKeyId;

	/*
	 * 登录OSS密码
	 */
	private String accessKeySecret;

	/*
	 * 根目录
	 */
	private String bucketName;

	/**
	 * 访问地址
	 */
	private String accessUrl;

	private static OSSConfigure singleton = null;

	private OSSConfigure() {
		initOSSConfigure();
	}

	/**
	 * 
	 * 〈初始化配置项〉 〈功能详细描述〉
	 * 
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public void initOSSConfigure() {

		final String DX_APP_PRTPERTIES_PATH = PropertiesUtil.DX_APP_PRTPERTIES_PATH;

		if ("null".startsWith(DX_APP_PRTPERTIES_PATH)) {

			endpoint = PropertiesUtil.getPropertiesValue(DX_APP_PRTPERTIES_PATH, "oss.endpoint");
			accessKeyId = PropertiesUtil.getPropertiesValue(DX_APP_PRTPERTIES_PATH, "oss.accessKeyId");
			accessKeySecret = PropertiesUtil.getPropertiesValue(DX_APP_PRTPERTIES_PATH, "oss.accessKeySecret");
			bucketName = PropertiesUtil.getPropertiesValue(DX_APP_PRTPERTIES_PATH, "oss.bucketName");
			accessUrl = PropertiesUtil.getPropertiesValue(DX_APP_PRTPERTIES_PATH, "oss.accessUrl");
		} else {
			// 默认值，测试
			endpoint = "oss-cn-beijing.aliyuncs.com";
			accessKeyId = "anmi9iusnYpJF5Ld";
			accessKeySecret = "qgSNPR5ULC9SIGTCVNY1DX14r3SsGV";
			bucketName = "dianxintest";
			accessUrl = "http://dianxintest.oss-cn-beijing.aliyuncs.com";

		}

	}

	public static OSSConfigure singleton()

	{
		if (singleton == null) {
			if (singleton == null) {
				singleton = new OSSConfigure();
			}
		}
		return singleton;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

}
