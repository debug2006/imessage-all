package com.dianxin.imessage.chat;

import java.util.List;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

public class XmppTest {

	public static void main(String[] args) {
		XMPPManager xmppManager = XMPPManager.getInstance();

		xmppManager.connect("13905150196", "12345678");

		String buddyJID = "13912345678";
		String buddyName = "13912345678";

		xmppManager.createEntry(buddyJID, buddyName);
		
		Roster roster =xmppManager.getRoster();

		List<RosterEntry> myFrieds = XMPPManager.getAllEntries(roster);

		
		xmppManager.setEntryNiceName(buddyJID, "nicename1234567");
		
		//

		Presence presence = roster.getPresence(buddyJID);

		xmppManager.sendMessage("Hi 聊天测试~", "13912345678@localhost/Spark");

		// xmppManager.destroy();
	}

}
