package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/15
 * Time: 9:59
 */
public class MulitiMemberInfo implements Serializable{

    private static final long serialVersionUID = 9138446985354874995L;
    private Integer uid;

    private List<Member> list;

    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public List<Member> getList() {
        return list;
    }

    public void setList(List<Member> list) {
        this.list = list;
    }
}
