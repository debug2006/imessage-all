package com.dianxin.imessage.common.xmpp;

import java.awt.Image;
import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.util.ServicesEncodingUtils;

/**
 * 
 * @author b_fatty
 * 
 * xmpp连接对象工厂
 *
 */
public class XmppManagerFactory {

	private Logger log = LoggerFactory.getLogger(XmppManagerFactory.class);
	
	//openfire服务地址
	private static final String SERVER_ADDRESS = "101.200.146.5";
	//openfire端口号
	private static final Integer SERVER_PORT = 5222;
	//服务名（openfire所在物理机的计算机机名）
	private static final String SERVER_NAME = "iZ25e30c0klZ";
	//来源
	private static final String RESOURCE = "WEBSERVER";
	
	/**
	 * 创建一个XmppManager
	 * @param uid
	 * @param password
	 * @return
	 */
	public static XmppManager createrXmppManager(final Integer uid,final String password,MemcachedFactory cacheFactory){
		
		AbstractXMPPConnection connection = createrConnection();
		
		try {
			connection.connect();
			XmppManager manager = new XmppManager(uid,password,connection);
			manager.cacheFactory = cacheFactory;
			return manager;
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 创建一个openfire连接
	 */
	private static AbstractXMPPConnection createrConnection(){
		XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
		configBuilder.setHost(SERVER_ADDRESS);
		configBuilder.setPort(SERVER_PORT);
		configBuilder.setServiceName(SERVER_NAME);
		configBuilder.setResource(RESOURCE);
		// 没有证书,需要设置SecurityMode.disabled
		configBuilder.setSecurityMode(SecurityMode.disabled);
		configBuilder.setDebuggerEnabled(true);
		configBuilder.setSendPresence(false);	//设置为离线
		final AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
		
		return connection;
	}
	
	public static void main(String[] args) throws NoResponseException, NotConnectedException, XMPPException, IOException {
		
		//1016558716	30a34daa2d5e223a5d7b6a96124dc3e6
		//1092338113	83b361aa9bac59212213bd2d41c64b50
		
		/*String pwd = ServicesEncodingUtils.encrypt("12345678");
		System.out.println(pwd);
		
		XmppManager m = createrXmppManager(1092338113,"83b361aa9bac59212213bd2d41c64b50");
		
		System.out.println(m.getEntry(1016558716).getName());*/
		
	}
}
