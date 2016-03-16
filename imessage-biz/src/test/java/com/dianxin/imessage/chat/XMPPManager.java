package com.dianxin.imessage.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMPPManager {

	private Logger logger = LoggerFactory.getLogger(XMPPManager.class);

	private final String serverAddress = "127.0.0.1"; // Your server address or
														// IP
	public static final String serverName = "localhost"; // xmpp name or your

	public static final Integer serverPort = 5222;
	// server name
	private XMPPState state = XMPPState.NOT_CONNECTED;
	private XMPPTCPConnectionConfiguration.Builder configBuilder;
	private static XMPPManager instance;
	final AbstractXMPPConnection connection;

	private ChatManager chatManager;

	private ChatMessageListener messageListener;

	/**
	 * 登录名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;

	private XMPPManager() {
		configBuilder = XMPPTCPConnectionConfiguration.builder();
		configBuilder.setHost(serverAddress);
		configBuilder.setPort(serverPort);
		configBuilder.setServiceName(serverName);
		// 没有证书,需要设置SecurityMode.disabled
		configBuilder.setSecurityMode(SecurityMode.disabled);
		configBuilder.setDebuggerEnabled(true);

		connection = new XMPPTCPConnection(configBuilder.build());

	}

	public static XMPPManager getInstance() {
		if (instance == null) {
			instance = new XMPPManager();
		}
		return instance;
	}

	public XMPPState getState() {
		return state;
	}

	public void connect(final String username, final String password) {
		this.username = username;
		this.password = password;
		reConnect();
	}

	public void destroy() {
		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}
	}

	public void reConnect() {
		try {
			connection.connect();

			chatManager = ChatManager.getInstanceFor(connection);

			messageListener = new MyMessageListener();

			logger.debug("连接openfire服务成功,127.0.0.1:5222");

			state = XMPPState.CONNECTED;

			connection.login(username, password);

			setStatus(true, "I am online");

		} catch (SmackException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (IOException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (XMPPException e) {
			logger.error("{} reConnect 待补充", username, e);
		}

	}

	public void reThreadConnect() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					connection.connect();

					chatManager = ChatManager.getInstanceFor(connection);

					messageListener = new MyMessageListener();

					logger.debug("连接openfire服务成功,127.0.0.1:5222");
					state = XMPPState.CONNECTED;
					connection.login(username, password);
					setStatus(true, "I am online");

				} catch (XMPPException e) {
					logger.error("{ } 失败", username, e);
				} catch (SmackException e) {
					logger.error("{ } 失败", username, e);
				} catch (IOException e) {
					logger.error("{} reConnect 待补充", username, e);
				}
			}
		});
		thread.start();
	}

	public void setStatus(boolean available, String status) {

		Presence.Type type = available ? Presence.Type.available : Presence.Type.unavailable;
		Presence presence = new Presence(type);
		presence.setStatus(status);
		logger.debug(username + ",登陆成功");
		try {

			connection.sendStanza(presence);

		} catch (NotConnectedException e) {
			logger.error("{} reConnect 待补充", username, e);
		}

	}

	public void sendMessage(String message, String buddyJID) {
		logger.debug("Sending mesage {} to user {}", message, buddyJID);

		Chat chat = chatManager.createChat(buddyJID, messageListener);
		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			logger.error("{} reConnect 待补充", username, e);
		}
	}

	public Roster getRoster() {

		Roster roster = null;
		if (null != connection) {
			roster = Roster.getInstanceFor(connection);
		}
		return roster;
	}

	public void createEntry(String user, String name) {
		logger.debug("createEntry for buddy {} with name {}", user, name);
		Roster roster = Roster.getInstanceFor(connection);

		try {
			roster.createEntry(user, name, null);
		} catch (NotLoggedInException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (NoResponseException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (XMPPErrorException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (NotConnectedException e) {
			logger.error("{} reConnect 待补充", username, e);
		}
	}

	/**
	 * 获取所有组
	 * 
	 * @param roster
	 * @return 所有组集合
	 */
	public static List<RosterGroup> getGroups(Roster roster) {
		List<RosterGroup> grouplist = new ArrayList<RosterGroup>();
		Collection<RosterGroup> rosterGroup = roster.getGroups();
		Iterator<RosterGroup> i = rosterGroup.iterator();
		while (i.hasNext()) {
			grouplist.add(i.next());
		}
		return grouplist;
	}

	/**
	 * 添加一个分组
	 * 
	 * @param roster
	 * @param groupName
	 * @return
	 */
	public static boolean addGroup(Roster roster, String groupName) {
		try {
			roster.createGroup(groupName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取某个组里面的所有好友
	 * 
	 * @param roster
	 * @param groupName
	 *            组名
	 * @return
	 */
	public static List<RosterEntry> getEntriesByGroup(Roster roster, String groupName) {
		List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
		RosterGroup rosterGroup = roster.getGroup(groupName);
		Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
		Iterator<RosterEntry> i = rosterEntry.iterator();
		while (i.hasNext()) {
			Entrieslist.add(i.next());
		}
		return Entrieslist;
	}

	/*
	 * 获取所有好友信息
	 * 
	 * @param roster
	 * 
	 * @return
	 */
	public static List<RosterEntry> getAllEntries(Roster roster) {
		List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
		Collection<RosterEntry> rosterEntry = roster.getEntries();
		Iterator<RosterEntry> i = rosterEntry.iterator();
		while (i.hasNext()) {
			Entrieslist.add(i.next());
		}
		return Entrieslist;
	}

	/**
	 * 获取用户VCard信息
	 * 
	 * @param connection
	 * @param user
	 * @return
	 * @throws XMPPException
	 */
	public static VCard getUserVCard(XMPPConnection connection, String user) throws XMPPException {
		VCard vcard = new VCard();
		// vcard.load(connection, user);
		// VCard.
		return vcard;
	}

	/**
	 * 添加好友 无分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @return
	 */
	public static boolean addUser(Roster roster, String userName, String name) {
		try {
			roster.createEntry(userName, name, null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加好友 有分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @param groupName
	 * @return
	 */
	public static boolean addUser(Roster roster, String userName, String name, String groupName) {
		try {
			roster.createEntry(userName, name, new String[] { groupName });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除好友
	 * 
	 * @param roster
	 * @param userName
	 * @return
	 */
	public static boolean removeUser(Roster roster, String userName) {
		try {
			if (userName.contains("@")) {
				userName = userName.split("@")[0];
			}

			RosterEntry entry = roster.getEntry(userName);
			System.out.println("删除好友：" + userName);
			System.out.println("User." + roster.getEntry(userName) == null);
			roster.removeEntry(entry);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 设置好友昵称
	 * 
	 * @param buddyJID
	 * @param niceName
	 */
	public void setEntryNiceName(String buddyJID, String niceName) {

		Roster roster = getRoster();

		RosterEntry rosterEntry = roster.getEntry(buddyJID);

		try {
			rosterEntry.setName(niceName);

			logger.debug("setEntryNiceName for buddy {} with name {}", buddyJID, niceName);
		} catch (NotConnectedException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (NoResponseException e) {
			logger.error("{} reConnect 待补充", username, e);
		} catch (XMPPErrorException e) {
			logger.error("{} reConnect 待补充", username, e);
		}

	}

	class MyMessageListener implements ChatMessageListener {

		@Override
		public void processMessage(Chat chat, Message message) {
			// String from = message.getFrom().substring(0,
			// message.getFrom().indexOf("@"));
			String from = message.getFrom();
			String body = message.getBody();

			logger.debug("Received message {} from {}", body, from);
		}

	}
}