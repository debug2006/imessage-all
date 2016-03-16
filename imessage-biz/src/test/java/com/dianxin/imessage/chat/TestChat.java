package com.dianxin.imessage.chat;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class TestChat {

	public static void main(String[] args) {

		XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();

		configBuilder.setHost("127.0.0.1");
		configBuilder.setPort(5222);
		configBuilder.setServiceName("localhost");
		 //不加这行会报错，因为没有证书
		configBuilder.setSecurityMode(SecurityMode.disabled);
		configBuilder.setDebuggerEnabled(true);
		
		
		
		final AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					connection.connect();
					System.out.println("连接服务器成功@127.0.0.1:5222");
					connection.login("13912345678", "12345678");
					Presence presence = new Presence(Presence.Type.available);
					presence.setStatus("I am online");
					connection.sendStanza(presence);
					System.out.println("登陆成功");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

}
