package com.dianxin.imessage.biz.chat;

import com.dianxin.imessage.dao.dataobject.book.Member;

import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/2
 * Time: 10:44
 */
public interface IMultiChatBiz {
    /**
     * 创建多人聊天群
     *
     * @return
     * @throws Exception
     */
    int queryRoomsByUser(Integer uid) throws Exception;

    /**
     * 授予管理员权限
     * @param list
     * @param roomName
     * @return
     * @throws Exception
     */
    Boolean grantAdmin(Integer uid,List<String> list,String roomName) throws Exception;


    /**
     * 取消管理员权限
     * @param list
     * @param roomName
     * @return
     * @throws Exception
     */
    Boolean canncelAdmin(Integer uid,List<String> list,String roomName) throws Exception;

    /**
     * 创建多人聊天群
     *
     * @return
     * @throws Exception
     */
    Boolean createMultiChat(Integer uid, String roomName) throws Exception;


    /**
     * 添加一个用户到群
     * @param uid
     * @param roomName
     * @return
     * @throws Exception
     */
    Boolean addMember(Integer uid,List<Member> list,String roomName) throws Exception;


    /**
     * 踢群用户
     * @param roomName
     * @param nickName
     * @param reason
     * @return
     * @throws Exception
     */
    Boolean kickMember(String uid,String roomName,String nickName,String reason) throws Exception;


    /**
     * 查询群成员列表
     * @param uid
     * @param roomName
     * @return
     * @throws Exception
     */
    List<Member> queryMemberList(Integer uid,String roomName) throws Exception;


    /**
     * 更改群内的 nickname
     * @param uid
     * @param roomName
     * @param nickName
     * @return
     * @throws Exception
     */
    Boolean changeNickName(String uid,String roomName,String nickName) throws Exception;

}
