package com.dianxin.imessage.web.core.extend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.dianxin.imessage.common.util.RSAKeyStore;

/**
 * 对用户提交的参数解密
 * 
 * @author b_fatty
 * Date 2015/12/23
 *
 */

public class RSAHttpServletRequest extends HttpServletRequestWrapper{

	private RSAKeyStore store;
	
	public RSAHttpServletRequest(HttpServletRequest request) {
		super(request);
	}
	
	public void setStroe(RSAKeyStore store){
		this.store = store;
	}
	@Override
	public String getParameter(String name) {
		if(StringUtils.isNotEmpty(super.getParameter(name))){
			store.decrypt(super.getParameter(name).replace(" ", "+"));
		}
		return null;
	}
	
	@Override
	public Map<String,String[]> getParameterMap() {
		@SuppressWarnings("unchecked")
		Map<String,String[]> supperMap = super.getParameterMap();
		Map<String,String[]> map = new HashMap<String, String[]>();
		for(Map.Entry<String, String[]> e : supperMap.entrySet()){
			if(null!=e.getValue()&&e.getValue().length>0){
				String[] s = new String[e.getValue().length];
				for(int i=0;i<s.length;i++){
					s[i] = store.decrypt(e.getValue()[i].replace(" ", "+"));
				}
				map.put(e.getKey(), s);
			}
		}
		return map;
	}
	@Override
	public String[] getParameterValues(String name) {
		String[] supperValues = super.getParameterValues(name);
		String[] values = null;
		if(null!=supperValues&&supperValues.length>0){
			values = new String[supperValues.length];
			for(int i=0;i<values.length;i++){
				values[i] = store.decrypt(supperValues[i].replace(" ", "+"));
			}
		}
		return values;
	}
}
