package com.dianxin.imessage.common.xmpp;

import com.alibaba.druid.util.StringUtils;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.exception.MemcachedException;
import com.dianxin.imessage.common.lbs.ChatStore;
import com.dianxin.imessage.common.lbs.data.MongoChat;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.Roster.SubscriptionMode;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.vcardtemp.provider.VCardProvider;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 
 * @author b_fatty
 *
 *         xmpp链接对象
 */
public class XmppManager {

	private Logger logger = LoggerFactory.getLogger(XmppManager.class);

	private int uid; // 用户id

	private AbstractXMPPConnection connection; // xmpp连接对象

	private ChatManager chatManager; // 会话管理 管理该连接的所有会话

	private MultiUserChatManager multiUserChatManager; // 多人群管理

	private MultiUserChat muc;

	private Roster roster;

	private VCardManager vCardManager;

	private VCardProvider vCardProvider;

	protected Date loadPoolDate; // 加载到池的时间

	protected MemcachedFactory cacheFactory;

	public static String BLACKLIST_NAME = "小黑屋";

	public static String BIRTHDAYREMINDERLIST = "sys_Birthday_Reminders";

	/** 黑名单组 */
	private RosterGroup blackListGroup;

	/** 生日提醒组 */
	private RosterGroup birthdayReminderGroup;

	protected XmppManager(Integer uid, String password, AbstractXMPPConnection connection) {
		this.uid = uid;
		this.connection = connection;

		// 登录
		login(connection, uid, password);

		// 处理离线消息（获取离线消息需要在离线的状态下，否则获取不到离线消息）
		List<Message> offlineMsgs = getOfflineMsgs();
		for (Message msg : offlineMsgs) {
			String from = msg.getFrom();
			String body = msg.getBody();

			if (null != body && null != from) {
				logger.debug("{} received offline message {} from {}", uid, body, from);
			}
		}

		// 设置为在线状态
		setPresenceType(Presence.Type.available);

		vCardManager = VCardManager.getInstanceFor(connection);

		multiUserChatManager = MultiUserChatManager.getInstanceFor(connection);

		initChat(connection);
		initRoster(connection);
		regStanzaListener(connection);
	}

	/**
	 * 获取离线消息
	 */
	private List<Message> getOfflineMsgs() {

		List<Message> offlineMsgs = null;

		OfflineMessageManager offlineMessageManager = new OfflineMessageManager(this.connection);

		try {
			offlineMsgs = offlineMessageManager.getMessages();
			// 获取完离线消息以后清除掉离线消息 避免重复获取
			offlineMessageManager.deleteMessages();
		} catch (NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}

		return offlineMsgs;
	}

	/**
	 * 会话管理及监听初始化 （监听在线信息）
	 */
	private void initChat(AbstractXMPPConnection connection) {

		chatManager = ChatManager.getInstanceFor(connection);

		chatManager.addChatListener(new ChatManagerListener() {

			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {

				chat.addMessageListener(new ChatMessageListener() {

					@Override
					public void processMessage(Chat chat, Message message) {

						String from = message.getFrom();
						String body = message.getBody();

						if (null != body && null != from) {
							// 消息入mongo
							MongoChat chat_ = new MongoChat(from, String.valueOf(uid), body);
							ChatStore.storeMessage(chat_);

							logger.debug("{} received message {} from {}", uid, body, from);
						}
					}
				});
			}
		});
	}

	/**
	 * 初始化Roster及其监听
	 */
	private void initRoster(AbstractXMPPConnection connection) {
		roster = Roster.getInstanceFor(connection);

		// 设置为手动处理订阅请求
		roster.setSubscriptionMode(SubscriptionMode.manual);

		roster.addRosterListener(new RosterListener() {

			@Override
			public void entriesAdded(Collection<String> addresses) {
				// TODO Auto-generated method stub
				logger.debug("Roster listener entriesAdded...");
			}

			@Override
			public void entriesUpdated(Collection<String> addresses) {
				logger.debug("Roster listener entriesUpdated...");
				for (String address : addresses) {
					logger.debug("address is {}", address);
				}

			}

			@Override
			public void entriesDeleted(Collection<String> addresses) {
				// TODO Auto-generated method stub
				logger.debug("Roster listener entriesDeleted...");
			}

			@Override
			public void presenceChanged(Presence presence) {
				// TODO Auto-generated method stub
				logger.debug("Roster listener presenceChanged...");
			}

		});
	}

	/**
	 * 注册StanzaListener监听器
	 * 
	 */
	private void regStanzaListener(AbstractXMPPConnection connection) {
		StanzaListener packetListener = new StanzaListener() {

			@Override
			public void processPacket(Stanza packet) throws NotConnectedException {

				if (packet instanceof Presence) {

					// TODO 手机端如果需要服务端代理在这里添加
					switch (((Presence) packet).getType()) {
					case subscribe: // 订阅申请（有人加我为好友）
						logger.debug("subscribe...");
						break;
					case subscribed: // 同意订阅（对方同意加我为好友）
						logger.debug("subscribed...");

						String fidStr = packet.getFrom().split("@")[0];

						Memcached memcached = null;
						try {
							memcached = cacheFactory.createMemcached();
							String groupNameAndNickName = (String) memcached.get("IF_G_" + fidStr + "_" + uid);
							if (!StringUtils.isEmpty(groupNameAndNickName)) {
								String group = groupNameAndNickName.split("&")[0];
								String nickName = groupNameAndNickName.split("&")[1];
								addUser(packet.getFrom(), nickName, group);
							}
							memcached.delete("IF_G_" + fidStr + "_" + uid);
						} catch (MemcachedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						break;
					case unsubscribe: // 删除好友（对象已经删除了我）
						logger.debug("unsubscribe...");
						break;
					case unsubscribed: // 拒绝加为好友
						logger.debug("unsubscribed...");
						break;
					default:

						break;
					}
				}
			}
		};

		StanzaFilter packetFilter = new AndFilter(new StanzaTypeFilter(Presence.class)); // 好友订阅请求监听
		connection.addAsyncStanzaListener(packetListener, packetFilter); // 监听来自服务端数据包
		// connection.addPacketSendingListener(packetListener, packetFilter);
		// //监听自己发送的数据包
	}

	/**
	 * 登录openfier
	 */
	private void login(AbstractXMPPConnection connection, Integer uid, String password) {

		try {
			connection.login(uid.toString(), password);
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 销毁对象
	 */
	protected void destroy() {

		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}
		connection = null;
		chatManager = null;
	}

	/**
	 * 确保和openfire是链接的
	 * 
	 * @throws XMPPException
	 * @throws IOException
	 * @throws SmackException
	 */
	private void conn() {

		if (!connection.isConnected()) { // 如果连接断开了 重连下
			try {
				connection.connect();
			} catch (SmackException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}

	/***********************************************************************
	 * 
	 * by b_fatty
	 * 
	 * 华丽丽的分割线
	 * 
	 * 上面为该类的初始化信息、配置、监听等
	 * 
	 * 下面为对外暴露的方法
	 * 
	 ***********************************************************************/

	public void createEntry(String user, String name) {
		conn();
		logger.debug("createEntry for buddy {} with name {}", user, name);
		Roster roster = Roster.getInstanceFor(connection);

		try {
			roster.createEntry(user, name, null);
		} catch (NotLoggedInException e) {
			logger.error("{} reConnect 待补充", uid, e);
		} catch (NoResponseException e) {
			logger.error("{} reConnect 待补充", uid, e);
		} catch (XMPPErrorException e) {
			logger.error("{} reConnect 待补充", uid, e);
		} catch (NotConnectedException e) {
			logger.error("{} reConnect 待补充", uid, e);
		}
	}

	/**
	 * 获取所有组,(黑名单组,生日提醒组，不返回)
	 * 
	 * @param roster
	 * @return 所有组集合
	 */
	public List<RosterGroup> getGroups() {
		conn();
		List<RosterGroup> grouplist = new ArrayList<RosterGroup>();
		Collection<RosterGroup> rosterGroup = roster.getGroups();
		Iterator<RosterGroup> i = rosterGroup.iterator();
		while (i.hasNext()) {
			grouplist.add(i.next());
		}

		if (!grouplist.isEmpty()) {
			for (int j = 0; j < grouplist.size(); j++) {

				if (BLACKLIST_NAME.equals(grouplist.get(j).getName())
						|| BIRTHDAYREMINDERLIST.equals(grouplist.get(j).getName())) {
					grouplist.remove(j);
				}
			}

		}
		return grouplist;
	}

	/**
	 * 添加一个分组,只少要填加1个好友
	 * 
	 * @param roster
	 * @param list
	 * @param groupName
	 * @return
	 */
	public void addGroup(List<String> friendsUid, String groupName) {
		conn();
		try {
			roster.createGroup(groupName);

			RosterGroup newRosterGroup = roster.getGroup(groupName);

			for (String fuid : friendsUid) {
				newRosterGroup.addEntry(getEntry(Integer.valueOf(fuid)));
			}

			logger.debug("addGroup {0}", groupName);
			// return addUser(roster, "13955555555", "13955555555", groupName);

		} catch (Exception e) {
			logger.error("{} addGroup 待补充", groupName, e);
		}
	}

	/**
	 * 更新分组,获取老的分组信息，清空里面的用户，建立新分组，添加成员
	 * 
	 * @param roster
	 * @param list
	 * @param groupName
	 * @return
	 */
	public void updateGroup(List<String> list, String groupName, String oldGroupName) {
		conn();
		try {

			// 移除旧的标签组
			this.removeGroup(oldGroupName);

			// 获取标签组信息
			RosterGroup oldRosterGroup = roster.getGroup(oldGroupName);

			if (null != oldRosterGroup) {
				Thread.sleep(50);
			}

			// 添加新标签组
			this.addGroup(list, groupName);

			logger.debug("updateGroup  oldGroupName:{0},newGroupName:{1}", oldGroupName, groupName);

		} catch (Exception e) {
			logger.error("{} updateGroup 待补充", groupName, e);
		}
	}

	/**
	 * 移除标签组
	 * 
	 * @param roster
	 * @param groupName
	 */
	public void removeGroup(String groupName) {
		conn();
		try {
			// 获取标签组信息
			RosterGroup rosterGroup = roster.getGroup(groupName);

			List<RosterEntry> groupMembers = getEntriesByGroup(rosterGroup.getName());

			// 删除组内原有的成员
			for (RosterEntry groupMember : groupMembers) {
				rosterGroup.removeEntry(groupMember);
			}
			logger.debug("removeGroup {0}", groupName);
		} catch (NoResponseException e) {
			logger.error("{} removeGroup 待补充", groupName, e);
		} catch (XMPPErrorException e) {
			logger.error("{} removeGroup 待补充", groupName, e);
		} catch (NotConnectedException e) {
			logger.error("{} removeGroup 待补充", groupName, e);
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
	public List<RosterEntry> getEntriesByGroup(String groupName) {
		conn();
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
	public List<RosterEntry> getAllEntries() {
		conn();
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
	 * @throws NotConnectedException
	 * @throws NoResponseException
	 */
	public VCard getUserVCard(Integer uid) throws XMPPException, NoResponseException, NotConnectedException {
		// VCard vcard = new VCard();
		// vcard.load(connection, user);
		conn();
		VCard vcard = vCardManager.loadVCard(uid.toString() + "@" + connection.getServiceName());
		return vcard;
	}

	/**
	 * 获取用户头像
	 * 
	 * @throws XMPPException
	 * @throws NotConnectedException
	 * @throws NoResponseException
	 * @throws IOException
	 */
	public Image getUserPhoto(Integer uid)
			throws NoResponseException, NotConnectedException, XMPPException, IOException {
		conn();
		Image image = null;

		VCard vcard = getUserVCard(uid);

		if (vcard == null || vcard.getAvatar() == null) {
			return null;
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(vcard.getAvatar());

		image = ImageIO.read(bais);

		return image;
	}

	/**
	 * 添加好友 无分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @return
	 */
	public boolean addUser(String userName, String name) {
		conn();
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
	public boolean addUser(String userName, String name, String groupName) {
		conn();
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
	public boolean removeUser(String[] buddyJIDs) {
		conn();
		try {

			for (String buddyJID : buddyJIDs) {
				RosterEntry rosterEntry = getEntry(Integer.valueOf(buddyJID));
				System.out.println("删除好友：" + buddyJID);
				System.out.println("User." + getEntry(Integer.valueOf(buddyJID)) == null);
				roster.removeEntry(rosterEntry);
			}

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
	public void setEntryNiceName(Integer fid, String niceName) {
		conn();
		RosterEntry rosterEntry = getEntry(fid);

		try {
			if (!StringUtils.isEmpty(niceName) && !niceName.equals(rosterEntry.getName())) {
				rosterEntry.setName(niceName);
			}

			logger.debug("setEntryNiceName for buddy {} with name {}", fid, niceName);
		} catch (NotConnectedException e) {
			logger.error("{} reConnect 待补充", uid, e);
		} catch (NoResponseException e) {
			logger.error("{} reConnect 待补充", uid, e);
		} catch (XMPPErrorException e) {
			logger.error("{} reConnect 待补充", uid, e);
		}
	}

	/**
	 * 获取某个好友信息
	 */
	public RosterEntry getEntry(Integer fid) {
		conn();
		return roster.getEntry(fid.toString() + "@" + connection.getServiceName());
	}

	/**
	 * 发送数据包
	 * 
	 * @throws NotConnectedException
	 */
	public void sendPacket(Presence.Type type, Integer fid) throws NotConnectedException {
		conn();
		Presence subscription = new Presence(type);
		subscription.setTo(fid.toString() + "@" + connection.getServiceName());
		connection.sendStanza(subscription);
	}

	/**
	 * 发送数据包
	 * 
	 * @throws NotConnectedException
	 */
	public void sendPacket(Presence.Type type, Integer fid, List<ExtensionElement> elements)
			throws NotConnectedException {
		conn();
		Presence subscription = new Presence(type);
		subscription.setTo(fid.toString() + "@" + connection.getServiceName());
		if (null != elements) {
			subscription.addExtensions(elements);
		}
		connection.sendStanza(subscription);
	}

	/**
	 * 获取 Roster
	 * 
	 * @return
	 */
	public Roster getRoster() {

		if (null != roster) {
			return roster;
		}
		if (null != connection) {
			roster = Roster.getInstanceFor(connection);
		}
		return roster;
	}

	/**
	 * 创建会话
	 * 
	 * @param fid
	 * @return
	 */
	public Chat createrChat(Integer fid) {
		conn();
		return this.chatManager.createChat(fid.toString() + "@" + this.connection.getServiceName());
	}

	/**
	 * 发送消息
	 */
	public void sendMsg(Chat chat, Message message) {
		conn();
		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(Chat chat, String message) {
		conn();
		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置状态
	 */
	public void setPresenceType(Presence.Type type) {
		conn();
		Presence presence = new Presence(type);
		try {
			this.connection.sendStanza(presence);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加用户到黑名单组
	 * 
	 * @param uid
	 *            用户ID
	 * @param blackUid
	 *            黑名单ID
	 * @return
	 */
	public boolean addBirthdayReminderList(Integer uid, String fUid) {

		// 没有黑名单，则创建
		if (null == birthdayReminderGroup) {
			birthdayReminderGroup = roster.createGroup(BIRTHDAYREMINDERLIST);
		}

		try {
			blackListGroup.addEntry(getEntry(Integer.valueOf(fUid)));
		} catch (NoResponseException e) {
			logger.error("{} addBirthdayReminderList 待补充", uid, e);
			return false;
		} catch (XMPPErrorException e) {
			logger.error("{} addBirthdayReminderList 待补充", uid, e);
			return false;
		} catch (NotConnectedException e) {
			logger.error("{} addBirthdayReminderList 待补充", uid, e);
			return false;
		}

		return true;
	}

	/**
	 * 从到黑名单组移除用户
	 * 
	 * @param uid
	 *            用户ID
	 * @param blackUid
	 *            黑名单ID
	 * @return
	 */
	public boolean removeBirthdayReminderList(Integer uid, String fUid) {

		// 删除时，没有则直接返回成功
		if (null == birthdayReminderGroup) {
			return true;
		}

		try {
			birthdayReminderGroup.removeEntry(getEntry(Integer.valueOf(fUid)));
		} catch (NoResponseException e) {
			logger.error("{} removeBirthdayReminderList 待补充", uid, e);
			return false;
		} catch (XMPPErrorException e) {
			logger.error("{} removeBirthdayReminderList 待补充", uid, e);
			return false;
		} catch (NotConnectedException e) {
			logger.error("{} removeBirthdayReminderList 待补充", uid, e);
			return false;
		}

		return true;
	}

	/**
	 * 添加用户到黑名单组
	 * 
	 * @param uid
	 *            用户ID
	 * @param blackUid
	 *            黑名单ID
	 * @return
	 */
	public boolean addBlacklist(Integer uid, String blackUid) {

		// 没有黑名单，则创建
		if (null == blackListGroup) {
			blackListGroup = roster.createGroup(BLACKLIST_NAME);
		}

		try {
			blackListGroup.addEntry(getEntry(Integer.valueOf(blackUid)));
		} catch (NoResponseException e) {
			logger.error("{} addBlacklist 待补充", uid, e);
			return false;
		} catch (XMPPErrorException e) {
			logger.error("{} addBlacklist 待补充", uid, e);
			return false;
		} catch (NotConnectedException e) {
			logger.error("{} addBlacklist 待补充", uid, e);
			return false;
		}

		return true;
	}

	/**
	 * 从到黑名单组移除用户
	 * 
	 * @param uid
	 *            用户ID
	 * @param blackUid
	 *            黑名单ID
	 * @return
	 */
	public boolean removeBlacklist(Integer uid, String blackUid) {

		// 没有黑名单，则直接返回成功
		if (null == blackListGroup) {
			return true;
		}

		try {
			blackListGroup.removeEntry(getEntry(Integer.valueOf(blackUid)));
		} catch (NoResponseException e) {
			logger.error("{} addBlacklist 待补充", uid, e);
			return false;
		} catch (XMPPErrorException e) {
			logger.error("{} addBlacklist 待补充", uid, e);
			return false;
		} catch (NotConnectedException e) {
			logger.error("{} addBlacklist 待补充", uid, e);
			return false;
		}

		return true;
	}

	/**
	 * 获取用户uid
	 */
	public static Integer getUserUid(RosterEntry entry) {
		if (entry != null) {
			String user = entry.getUser();
			if (!StringUtils.isEmpty(user)) {
				return Integer.parseInt(user.split("@")[0]);
			}
		}
		return null;
	}

	/**
	 * 获取好友数量
	 */
	public Integer getFirendsNum() {

		return roster.getEntryCount();

	}

	/**
	 * 获取该用户所有创建的群聊天
	 * @return
	 */
	public Set<String> queryRooms(){
		Set<String> set = null;
		try {
			set = multiUserChatManager.getJoinedRooms();
		}catch (Exception e)
		{
			logger.error("{} queryRooms error {}", uid, e);
		}

		return set;
	}

	/**
	 * 创建多人聊天群
	 */
	public boolean createMultiChat(String roomName) {
		boolean isSuceess = false;
		if (org.apache.commons.lang3.StringUtils.isBlank(roomName)) {
			return isSuceess;
		}
		try {
			muc = multiUserChatManager.getMultiUserChat(roomName);
			muc.create(String.valueOf(uid));
			// 发送一个空表单配置这显示我们想要一个instant room
			muc.sendConfigurationForm(new Form(DataForm.Type.submit));

			if (null != muc) {
				isSuceess = true;
			} else {
				isSuceess = false;
			}
		} catch (Exception e) {
			logger.error("{} createMultiChat error {} roomName {}", uid, e, roomName);
		}

		return isSuceess;
	}



	/**
	 * 踢出群成员
	 */
	public boolean kickMember(String roomName,String nickName,String reason) {
		boolean isSuceess = false;
		if (org.apache.commons.lang3.StringUtils.isBlank(roomName)||org.apache.commons.lang3.StringUtils.isBlank(nickName)) {
			return isSuceess;
		}
		try {
			muc = multiUserChatManager.getMultiUserChat(roomName);
			muc.kickParticipant(nickName,reason);

			if (null != muc) {
				isSuceess = true;
			} else {
				isSuceess = false;
			}
		} catch (Exception e) {
			logger.error("{} kickMember error {} roomName {} nickName {}", uid, e, roomName,nickName);
		}

		return isSuceess;
	}

	/**
	 * 踢出群成员
	 */
	public boolean changeNickName(String roomName,String nickName) {
		boolean isSuceess = false;
		if (org.apache.commons.lang3.StringUtils.isBlank(roomName)||org.apache.commons.lang3.StringUtils.isBlank(nickName)) {
			return isSuceess;
		}
		try {
			muc = multiUserChatManager.getMultiUserChat(roomName);
			muc.changeNickname(nickName);

			if (null != muc) {
				isSuceess = true;
			} else {
				isSuceess = false;
			}
		} catch (Exception e) {
			logger.error("{} changeNickName error {} roomName {} nickName {}", uid, e, roomName,nickName);
		}

		return isSuceess;
	}


	/**
	 * 授予某些人管理员权限
	 * @param ids
	 * @param roomName
	 * @return
	 */
	public boolean grantAdmin(List<String> ids,String roomName){
		boolean isSuccess = false;
		if (null==ids||ids.size()<=0||org.apache.commons.lang3.StringUtils.isBlank(roomName)) {
			return isSuccess;
		}
		try {
			// 如果没有创建房间则先创建 多人群聊对象
			muc = multiUserChatManager.getMultiUserChat(roomName);
			muc.grantAdmin(ids);
			isSuccess = true;
		} catch (Exception e) {
			logger.error("{} grantAdmin error {} roomName {}", uid, e, roomName);
		}

		return isSuccess;
	}


	/**
	 * 授予某些人管理员权限
	 * @param ids
	 * @param roomName
	 * @return
	 */
	public boolean canncelAdmin(List<String> ids,String roomName){
		boolean isSuccess = false;
		if (null==ids||ids.size()<=0||org.apache.commons.lang3.StringUtils.isBlank(roomName)) {
			return isSuccess;
		}
		try {
			// 如果没有创建房间则先创建 多人群聊对象
			muc = multiUserChatManager.getMultiUserChat(roomName);
			muc.revokeAdmin(ids);
			isSuccess = true;
		} catch (Exception e) {
			logger.error("{} canncelAdmin error {} roomName {}", uid, e, roomName);
		}

		return isSuccess;
	}

	/**
	 * 添加一个用户到群聊
	 * 
	 * @param uid
	 * @param roomName
	 */
	public boolean joinMultiUserChat(Integer[] uid, String roomName) {
		boolean isSuccess = false;
		if (null == uid || org.apache.commons.lang3.StringUtils.isBlank(roomName)) {
			return isSuccess;
		}
		try {
			// 如果没有创建房间则先创建 多人群聊对象
			muc = multiUserChatManager.getMultiUserChat(roomName);
			for (Integer integer : uid) {
				muc.join(String.valueOf(integer));
			}
			isSuccess = true;
		} catch (Exception e) {
			logger.error("{} joinMultiUserChat error {} roomName {}", uid, e, roomName);
		}
		return isSuccess;

	}

	/**
	 * 获取该聊天室的所有成员
	 * 
	 * @param roomName
	 * @return
	 */
	public List<Affiliate> getMembersByMultiChat(String roomName) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(roomName)) {
			return null;
		}
		List<Affiliate> members = null;

		try {
			muc = multiUserChatManager.getMultiUserChat(roomName);
			members = muc.getMembers();

		} catch (Exception e) {
			logger.error("{} getMembersByMultiChat error {} roomName {}", uid, e, roomName);
		}

		return members;
	}

	/**
	 * 修改聊天主题
	 * 
	 * @param uid
	 * @param roomName
	 */
	public boolean changeMultiRoomSubject(String subject, String roomName) {
		boolean isSuccess = false;
		if (org.apache.commons.lang3.StringUtils.isBlank(subject)
				|| org.apache.commons.lang3.StringUtils.isBlank(roomName)) {
			return isSuccess;
		}
		try {
			// 如果没有创建房间则先创建 多人群聊对象
			muc = multiUserChatManager.getMultiUserChat(roomName);
			muc.changeSubject(subject);
			isSuccess = true;
		} catch (Exception e) {
			logger.error("{} changeMultiRoomSubject error {} roomName {}", uid, e, roomName);
		}
		return isSuccess;

	}

	/**
	 * 
	 * 功能描述: 设置在线状态，个人动态（心情）<br>
	 * 〈功能详细描述〉
	 *
	 * @param available
	 * @param status
	 * @param uid
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void setStatus(boolean available, String status, String uid) {
		conn();
		Presence.Type type = available ? Presence.Type.available : Presence.Type.unavailable;
		Presence presence = new Presence(type);
		presence.setStatus(status);
		logger.debug("{} available is {},status is {}", uid, available, status);
		try {

			connection.sendStanza(presence);

		} catch (NotConnectedException e) {
			logger.error("{} available is {},status is {}", uid, available, status);
		}

	}
}
