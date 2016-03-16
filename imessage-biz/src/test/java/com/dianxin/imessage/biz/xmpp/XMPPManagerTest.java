package com.dianxin.imessage.biz.xmpp;

import org.junit.Test;

import com.dianxin.imessage.chat.XMPPManager;

public class XMPPManagerTest {

	@Test
	public void test() {
		
		XMPPManager xmppManager = XMPPManager.getInstance();

		// TODO 从缓存从获取链接
			xmppManager.connect("13922222222", "123");
			
			String buddyJID = "13912345678";
			String buddyName = "13912345678";

			xmppManager.createEntry(buddyJID, buddyName);
			xmppManager.sendMessage("Hi 聊天测试~", "13912345678@localhost/Spark");
		
	}

}
