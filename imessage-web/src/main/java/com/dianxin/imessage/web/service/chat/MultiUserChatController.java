package com.dianxin.imessage.web.service.chat;

import com.dianxin.imessage.biz.chat.IMultiChatBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.book.Member;
import com.dianxin.imessage.dao.dataobject.book.MulitiMemberInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/2
 * Time: 9:40
 */
@Controller
@RequestMapping("/multiChat")
public class MultiUserChatController {
    private static final int CREATE_NUM= 5;  //一个人允许创建的群组数量

    @Autowired
    private IMultiChatBiz multiChatBiz;

    /**
     * 创建一个多人聊天群房间
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/newroom/{uid}",produces="text/html;charset=UTF-8",method= RequestMethod.POST)
    public @ResponseBody String newroom(@DESParam String uid,@DESParam String roomName) throws Exception
    {
        ResultModel<Map> result = new ResultModel<Map>();
        if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(roomName))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        int count = multiChatBiz.queryRoomsByUser(Integer.valueOf(uid));
        if(count>0&&count<CREATE_NUM){
            boolean flag = multiChatBiz.createMultiChat(Integer.valueOf(uid),roomName);
            if(!flag)
            {
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.MULTI_CHAT_ERROR.value);
            }
        }else{
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MULTI_CHAT_CREATE_ERROR.value);
        }
        return result.toJSON();

    }




    @RequestMapping(value="/changeNickName/{uid}",produces="text/html;charset=UTF-8",method= RequestMethod.PUT)
    public @ResponseBody String changeNickName(@DESParam String uid,@DESParam String roomName,String nickName) throws Exception
    {
        ResultModel<Map> result = new ResultModel<Map>();
        if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(roomName))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        boolean flag = multiChatBiz.changeNickName(uid, roomName, nickName);
        if(!flag)
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MULTI_CHAT_CHANGEMEMEBR.value);
        }
        return result.toJSON();

    }


    /**
     * 添加群成员
     * @param mulitiMemberInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/mulitiMember",produces="text/html;charset=UTF-8",method= RequestMethod.POST)
    public @ResponseBody String MulitiMember(@DESParam({"uid","roomName"})MulitiMemberInfo mulitiMemberInfo) throws Exception
    {
        ResultModel<Map> result = new ResultModel<Map>();
        List ids;
        if(null!=mulitiMemberInfo){
            ids = mulitiMemberInfo.getList();
            if(null==ids||ids.size()==0){
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
                return result.toJSON();
            }
            if(!StringUtils.isNotBlank(String.valueOf(mulitiMemberInfo.getUid())) && !StringUtils.isNotBlank(mulitiMemberInfo.getRoomName()))
            {
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
                return result.toJSON();
            }
        }else{
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        boolean flag = multiChatBiz.addMember(mulitiMemberInfo.getUid(), ids, mulitiMemberInfo.getRoomName());
        if(!flag)
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MULTI_CHAT_ADDMEMEBR.value);
        }
        return result.toJSON();

    }


    /**
     * 踢出群成员
     * @param uid
     * @param roomName
     * @param nickName
     * @param reason
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/kickMember",produces="text/html;charset=UTF-8",method= RequestMethod.POST)
    public @ResponseBody String kickMember(@DESParam String uid,@DESParam String roomName,@DESParam String nickName,String reason) throws Exception
    {
        ResultModel<Map> result = new ResultModel<Map>();
        if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(roomName))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        boolean flag = multiChatBiz.kickMember(uid, roomName, nickName, reason);
        if(!flag)
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MULTI_CHAT_KICKMEMEBR.value);
        }
        return result.toJSON();

    }


    /**
     * 获取群成员列表
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/mulitiMemberList/{uid}",produces="text/html;charset=UTF-8",method= RequestMethod.GET)
    public @ResponseBody String mulitiMemberList(@DESParam String uid,@DESParam String roomName) throws Exception
    {
        ResultModel<List<Member>> result = new ResultModel<List<Member>>();
        if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(roomName))
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        List<Member> list = multiChatBiz.queryMemberList(Integer.valueOf(uid), roomName);
        if(null!=list&&list.size()>0)
        {
            result.setData(list);
        }else{
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.MULTI_CHAT_QUERYMEMEBR_ERROR.value);
        }
        return result.toJSON();

    }


    /**
     * 授予管理员权限
     * @param mulitiMemberInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/grantAdmin/{opt}",produces="text/html;charset=UTF-8",method= RequestMethod.PUT)
    public @ResponseBody String grantAdmin(@PathVariable String opt,@DESParam({"uid","roomName"})MulitiMemberInfo mulitiMemberInfo) throws Exception
    {
        ResultModel<Map> result = new ResultModel<Map>();
        List ids;
        if(null!=mulitiMemberInfo){
            ids = mulitiMemberInfo.getList();
            if(null==ids||ids.size()==0){
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
                return result.toJSON();
            }
            if(!StringUtils.isNotBlank(String.valueOf(mulitiMemberInfo.getUid())) && !StringUtils.isNotBlank(mulitiMemberInfo.getRoomName()))
            {
                result.setResult(ResultModel.RESULT_FAIL_NUM);
                result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
                return result.toJSON();
            }
        }else{
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        List<Member> list = mulitiMemberInfo.getList();
        List<String> mlist = new ArrayList<String>();
        for(Member member:list){
            if(null!=member&&null!=member.getUid()){
                mlist.add(String.valueOf(member.getUid()));
            }
        }
        boolean ret = false;
        if(opt.equals("add")){
            ret = multiChatBiz.grantAdmin(mulitiMemberInfo.getUid(),mlist,mulitiMemberInfo.getRoomName());
        }else if(opt.equals("canncel")){
            ret = multiChatBiz.canncelAdmin(mulitiMemberInfo.getUid(),mlist,mulitiMemberInfo.getRoomName());
        }
        if(!ret){
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode("add".equals(opt)?ErrCodeEnum.MULTI_CHAT_GRANT_ERROR.value:ErrCodeEnum.MULTI_CHAT_CANNCEL_ERROR.value);
        }
        return result.toJSON();

    }
}
