package com.dianxin.imessage.common.xmpp.extend;

import org.jivesoftware.smack.packet.ExtensionElement;

/**
 * 
 * @author b_fatty
 * 
 * 好友申请扩展标签
 *
 */
public class InviteFriendExtension implements ExtensionElement{

	private final String elementName = "invite-friend-info";		//标签名称
	
	private String attachInfo;		//附带信息
	
	private String userName;		//申请人昵称
	
	private String headPath;		//头像地址
	
	@Override
	public String getElementName() {
		
		return elementName;
	}

	@Override
	public CharSequence toXML() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<");
		sb.append(elementName);
		sb.append(">");
		
		sb.append("<");
		sb.append("attach-info");
		sb.append(">");
		sb.append(attachInfo);
		sb.append("</");
		sb.append("attach-info");
		sb.append(">");
		
		sb.append("<");
		sb.append("user-name");
		sb.append(">");
		sb.append(userName);
		sb.append("</");
		sb.append("user-name");
		sb.append(">");
		
		sb.append("<");
		sb.append("head-path");
		sb.append(">");
		sb.append(headPath);
		sb.append("</");
		sb.append("head-path");
		sb.append(">");
		
		sb.append("</");
		sb.append(elementName);
		sb.append(">");
		return sb.toString();
	}

	@Override
	public String getNamespace() {
		return elementName;
	}

	public String getAttachInfo() {
		return attachInfo;
	}

	public void setAttachInfo(String attachInfo) {
		this.attachInfo = attachInfo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}
}
