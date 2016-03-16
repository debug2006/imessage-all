package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;

public class FriendsId implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -8250463241273541509L;
	String[] friendsId;

	public String[] getFriendsId() {
		return friendsId;
	}

	public void setFriendsId(String[] friendsId) {
		this.friendsId = friendsId;
	}

}
