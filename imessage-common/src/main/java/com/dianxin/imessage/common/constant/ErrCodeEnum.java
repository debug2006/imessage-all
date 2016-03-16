package com.dianxin.imessage.common.constant;

public enum ErrCodeEnum {

	/** 无异常 */
	NORMAL("0000"),
	/** 手机号不存在 */
	PHONE_NOT_EXIST("1000"),
	/** 手机号格式错误 */
	PHONE_FORMAT_ERR("1001"),
	/** 登录账号校验失败，非手机号，纸条号 */
	LOGIN_ACCOUNT_INVALID("1002"),
	/** 登录密码错误 */
	PASSWORD_ERROR("1003"),
	/**UID格式错误*/
	UID_FORMAT_ERR("1004"),
	/** 用户不存在 */
	USER_NOT_EXIST("1005"),
	/** 手机已注册 */
	PHONE_IS_EXIST("1007"),
	/**不是好友 */
	NOT_FRIEND("1006"),
	/**限制登录（半小时内不能支持登录）*/
	HAS_LIMITED_LOGIN("1008"),
	/**注册失败*/
	REGISTRATION_FAILED("1009"),
	/**服务端已是好友 openfire不是好友*/
	NOT_OPENFIRE_FIREND("1010"),
	/** 密码在memcache中没有找到  请重新登录*/
	NOT_FOUND_PWD_MEM("1011"),
	/** 好友验证超时*/
	INVITE_FRIEND_TIMEOUT("1012"),
	/**好友验证次数超过上限*/
	INVITE_FRIEND_COUNT("1013"),
	/**没有获取到地址记录*/
	ADDR_NOTHING("1014"),
	/** 短信发送异常 */
	SMS_SEND_ERR("2000"),
	/** 验证码验证失败 */
	VERIFY_CODE_FAIL("3000"),
	/** TOKEN验证失败 */
	TOKEN_CHECK_FAIL("4000"),
	/** memcached异常 */
	MEMCACHED_ERR("5000"),
	/** MONGODB异常 */
	MONGODB_ERR("6000"),
	/** MONGODB 上传经纬度异常 */
	MONGODB_JINGWEIDU_ERR("6001"),
	/** MONGODB  uid 或者经纬度为null*/
	MONGODB_PARAMER_NULL("6002"),
	/** MONGODB 周边没有人 */
	MONGODB_LBS_ERR("6010"),
	/** 服务异常 */
	SERVICE_ERROR("9999"),
	/** 标签信息失败 */
	SERVICE_TAG_ERROR("7001"),



	/**群聊天  创建失败**/
	MULTI_CHAT_ERROR("8001"),
	/**群聊天  添加群成员失败**/
	MULTI_CHAT_ADDMEMEBR("8002"),
	/**群聊天  没有群成员或者查询失败**/
	MULTI_CHAT_QUERYMEMEBR_ERROR("8003"),
	/**群聊天  系统只允许创建5个群**/
	MULTI_CHAT_CREATE_ERROR("8004"),
	/**群聊天  授予管理员权限失败**/
	MULTI_CHAT_GRANT_ERROR("8005"),
	/**群聊天  取消管理员权限失败**/
	MULTI_CHAT_CANNCEL_ERROR("8006"),
	/**群聊天  踢出群成员失败**/
	MULTI_CHAT_KICKMEMEBR("8007"),
	/**群聊天  修改nickname失败**/
	MULTI_CHAT_CHANGEMEMEBR("8008"),








	/** 违背的数据完整性约束 */
	DATA_VIOLATIO_NEXCEPTION("9001"),
	
	/** 违背的数据完整性约束 */
	PARAMETER_VALIDATION_FAILURE("9002"),
	;

	public final String value;

	private ErrCodeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
