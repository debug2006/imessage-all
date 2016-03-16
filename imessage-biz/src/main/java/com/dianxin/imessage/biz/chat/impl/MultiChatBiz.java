package com.dianxin.imessage.biz.chat.impl;

import com.dianxin.imessage.biz.chat.IMultiChatBiz;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.common.xmpp.XmppManager;
import com.dianxin.imessage.common.xmpp.XmppManagerPool;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.book.Member;
import com.dianxin.imessage.dao.mapper.UserMapper;
import org.jivesoftware.smackx.muc.Affiliate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/2
 * Time: 10:54
 */
@Service
public class MultiChatBiz implements IMultiChatBiz {
    private static Logger logger = LoggerFactory.getLogger(MultiChatBiz.class);

    @Autowired
    private XmppManagerPool xmppManagerPool;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    protected IUserImageBiz iUserImageBizl;

    @Override
    public Boolean createMultiChat(Integer uid, String roomName) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        return manager.createMultiChat(roomName);
    }

    @Override
    public int queryRoomsByUser(Integer uid) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        Set<String> set = manager.queryRooms();
        if(null!=set)
            return set.size();
        else
            return -1;
    }

    @Override
    public Boolean grantAdmin(Integer uid,List<String> list, String roomName) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        return manager.grantAdmin(list,roomName);
    }

    @Override
    public Boolean canncelAdmin(Integer uid,List<String> list, String roomName) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        return manager.canncelAdmin(list,roomName);
    }

    @Override
    public Boolean addMember(Integer uid, List<Member> list,String roomName) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        Integer[] ids = null;
        int count=0;
        for(Member member : list){
            if(member.getUid()!=null){
                ids[count] = member.getUid();
                ++count;
            }
        }
        return manager.joinMultiUserChat(ids,roomName);
    }


    @Override
    public Boolean kickMember(String uid,String roomName, String nickName, String reason) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        return manager.kickMember(roomName,nickName,reason);
    }


    @Override
    public Boolean changeNickName(String uid, String roomName, String nickName) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        return manager.changeNickName(roomName,nickName);
    }

    @Override
    public List<Member> queryMemberList(Integer uid, String roomName) throws Exception {
        XmppManager manager = xmppManagerPool.get(uid); // 从池中获取
        List<Affiliate> list = manager.getMembersByMultiChat(roomName);

        //转换 list
        List<Member> newList = new ArrayList<Member>();
        for (Affiliate affiliate: list){
            Member member = new Member();
            String jid = affiliate.getJid();
            DxUserModel model = (DxUserModel)userMapper.selectByPrimaryKey(jid);

            DxUserImageModel imageModel = iUserImageBizl.getUserImage(Integer.valueOf(jid));
            member.setUid(Integer.valueOf(jid));
            member.setNickName(affiliate.getNick());
            member.setRole(affiliate.getRole().name());
            member.setHeadUrl(imageModel.getFilePath());

            newList.add(member);
        }

        return newList;
    }
}
