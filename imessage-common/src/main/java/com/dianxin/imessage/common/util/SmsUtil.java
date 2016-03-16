package com.dianxin.imessage.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 
 * 短信工具类（阿里大鱼）
 * 
 * @author b_fatty
 * Date 2016/1/14
 *
 */
public class SmsUtil {

	private static Logger log = LoggerFactory.getLogger(SmsUtil.class); 
	
	private static final String SMS_TYPE = "normal";
	
	//链接参数
	private static final String SERVER_URL = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.url");
	private static final String APP_KEY = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.appkey");
	private static final String APP_SECRET = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.appSecret");
	
	//模板参数
	private static final String TEMPLATE_IDENTITY = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.identity");;
	private static final String TEMPLATE_LOGIN = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.login");;
	private static final String TEMPLATE_LOGIN_ERR = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.loginerr");;
	private static final String TEMPLATE_REGISTER = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.register");;
	private static final String TEMPLATE_ACTIVITY = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.activity");;
	private static final String TEMPLATE_MOD_PWD = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.modpwd");;
	private static final String TEMPLATE_MOD_INFO = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.template.modinfo");;
	
	//签名参数
	private static final String SIGN_ACTIVITY = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.sign.activity");
	private static final String SIGN_MOD = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.sign.mod");
	private static final String SIGN_LOGIN = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.sign.login");
	private static final String SIGN_REGISTER = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.sign.register");
	private static final String SIGN_IDENTITY = PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "alidayu.sign.identity");
	
	private static TaobaoClient client = new DefaultTaobaoClient(SERVER_URL, APP_KEY, APP_SECRET);
	
	/**
	 * 根据短信模板发送短信
	 * @param 
	 * 	phone:手机号  可传多个号码，多个号码用,隔开
	 * 	templateCode：模板号
	 * 	extend：响应回传信息
	 * 	smsFreeSignName：签名
	 * 	smsParam:短信变量
	 */
	public static boolean sendByTemplate(String phone,String templateCode,String extend,String smsFreeSignName,String smsParam){
		
		log.debug("sendByTemplate is start ...");
		//TODO 验证手机号码是否合格
		
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(extend);
		req.setSmsType(SMS_TYPE);
		req.setSmsFreeSignName(smsFreeSignName);
		req.setSmsParamString(smsParam);
		req.setRecNum(phone);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		log.debug("errCode:" + (rsp.getErrorCode() == null?"0" : rsp.getErrorCode()));
		return rsp.getErrorCode() == null;
	}
	
	/**
	 * 注册发送验证码
	 */
	public static boolean sendRegister(String phone,String code){
		Map<String,String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("product", "纸条君");
		return sendByTemplate(phone,TEMPLATE_REGISTER,phone,SIGN_REGISTER,JSONUtil.toJSON(map));
	}
	
	/**
	 * 发送修改密码验证码
	 */
	public static boolean sendModPwd(String phone,String code){
		Map<String,String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("product", phone);
		return sendByTemplate(phone,TEMPLATE_MOD_PWD,phone,SIGN_MOD,JSONUtil.toJSON(map));
	}
}
