package com.dianxin.imessage.web.service.book;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.biz.chat.IUserStatBiz;
import com.dianxin.imessage.biz.config.IAbilityBiz;
import com.dianxin.imessage.biz.config.IUserAddrBiz;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.exception.userManager.UserException;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.common.xmpp.XmppManager;
import com.dianxin.imessage.dao.dataobject.*;
import com.dianxin.imessage.dao.dataobject.book.FriendInfoBean;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class DxFirendController {

	@Autowired
	private IUserBiz userBiz;
	
	@Autowired
	private IUserStatBiz userStatBiz;
	
	@Autowired
	private IInterestBiz interestBiz;
	
	@Autowired
	private IUserImageBiz userImageBiz;
	
	@Autowired
	private IAbilityBiz abilityBiz;

	@Autowired
	private MemcachedFactory cacheFactory;

	@Autowired
	private IUserAddrBiz userAddrBiz;
	/**
	 * 通讯录列表接口
	 * @param uid
	 * @param phones
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/bookList/{uid}/{phones}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String bookList(@DESParam Integer uid,@DESParam String phones) throws Exception{
		
		/*UserManageUtil.checkUid(uid);   //验证uid是否符合要求
*/		
		ResultModel<Map<String,Object>[]> result = new ResultModel<Map<String,Object>[]>();
		
		//判断是否为纸条君用户
		Map<String, Object>[] data = null;
		try {
			data = userBiz.isZTJUser(phones.split("\\|"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//判断是否为好友
		for(Map<String,Object> map : data){
			if(null!=map.get("uid")){
				if(userBiz.isFirend(uid, (Integer)map.get("uid"))){	//是好友
					map.put("userType", 0);
				}
			}
		}
		
		result.setData(data);
		
		return result.toJSON();
	}
	
	/**
	 * 好友申请接口
	 * @param uid
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/inviteFriend/{uid}/{fid}/{message}/{groupName}/{nickName}",produces = "text/html;charset=UTF-8",method=RequestMethod.POST)
	public @ResponseBody String inviteFriend(@DESParam Integer uid,@DESParam Integer fid ,@PathVariable String message,@PathVariable String groupName,@PathVariable String nickName) throws Exception{
		
		ResultModel result = new ResultModel();
		
		userBiz.inviteFriend(uid, fid,message, groupName, nickName);
		
		return result.toJSON();
	}
	
	/**
	 * 获取好友申请附加信息
	 */
	@RequestMapping(value="/inviteFriendInfo/{uid}/{fid}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String inviteFriendInfo(@DESParam Integer uid,@DESParam Integer fid) throws Exception{
		
		ResultModel<Map> result = new ResultModel<Map>();
		
		Map<String ,String> map = new HashMap<String, String>();
		
		Memcached memcache = cacheFactory.createMemcached();
		
		String str = (String) memcache.get("IF_" + uid + "_" + fid);
		
		if(str != null){
			String[] info = str.split("&");
			if(info.length == 3){
				map.put("attachInfo", info[0]);
				map.put("userName", info[1]);
				map.put("headPath", info[2]);
			}
		}
		
		result.setData(map);
		return result.toJSON();
	}
	
	/**
	 * 同意加为好友接口
	 * @param uid
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/applyFriend/{uid}/{fid}",produces = "text/html;charset=UTF-8",method=RequestMethod.POST)
	public @ResponseBody String applyFriend(@DESParam Integer uid,@DESParam Integer fid) throws Exception{
		
		ResultModel result = new ResultModel();
		
		userBiz.processInvite(uid, fid);
		
		Memcached memcache = cacheFactory.createMemcached();
		
		memcache.delete("IF_" + uid + "_" + fid);
		
		return result.toJSON();
	}
	
	@RequestMapping(value="/getUserInfo/{uid}/{fid}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String getUserInfo(@DESParam Integer uid, @DESParam Integer fid) throws Exception{
		
		ResultModel<FriendInfoBean> result = new ResultModel<FriendInfoBean>();
		
		DxUserModel user = userBiz.getUserByKey(fid);
		
		if(user != null){	
			FriendInfoBean bean = new FriendInfoBean();
			if(!userBiz.isFirend(uid, user.getUid())){
				bean.setUserType(0);
			}else{
				XmppManager manage = userBiz.getXmppManagerForPool(uid);
				bean.setUserType(1);
				RosterEntry entry = manage.getEntry(fid);
				if(entry==null){
					throw new UserException("not openfire friend", ErrCodeEnum.NOT_OPENFIRE_FIREND.value);
				}
				//好友备注
				bean.setRemackName(entry.getName());
				List<RosterGroup> groups = entry.getGroups();
				if(groups != null && groups.size()>0){
					if(XmppManager.BLACKLIST_NAME.equals(groups.get(0).getName())){
						//在黑名单中
						bean.setIsBlack(true);
					}else{
						//标签名
						bean.setLabelName(groups.get(0).getName());
					}
				}
			}
			
			bean.fillUser(user);
			
			DxUserStatModel stat =  userStatBiz.getUserStatByUidAndFid(uid, user.getUid());
			bean.fillStat(stat);
			
			List<DxInterestModel> interes = interestBiz.getInterestByUid(user.getUid());
			bean.fillInterest(interes);
			
			DxUserImageModel image = userImageBiz.getUserImage(user.getUid());
			bean.fillUserImage(image);
			
			List<DxAbilityModel> Abilites = abilityBiz.getDxAbilityModelList(user.getUid());
			bean.fillAbility(Abilites);
			
			List<DxUserAddrModel> addrs = userAddrBiz.queryList(uid);
			bean.fillAddr(addrs);
			
			result.setData(bean);
		}
		
		return result.toJSON();
	}
	
	/**
	 * 通过手机号或纸条号搜索用户
	 * @param uid
	 * @param param	手机号或者纸条号
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/searchUser/{uid}/{param}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String searchUser(@DESParam Integer uid, @DESParam String param) throws Exception{
		
		ResultModel<Map<String,Object>[]> result = new ResultModel<Map<String,Object>[]>();
		//ResultModel<Map<String,Object>> result = new ResultModel<Map<String,Object>>();
		
		//DxUserModel user = userBiz.searchUserByUserNumOrPhone(param);
		
		List<DxUserModel> users = userBiz.searchUserByUserNumOrPhone_(param);
		
		/*if(user != null){
			
			DxUserImageModel image = userImageBiz.getUserImage(user.getUid());
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userName", user.getUserName());
			map.put("userNum", user.getUserNum());
			map.put("headPhoto", null);
			map.put("uid", user.getUid());
			if(image!=null){
				map.put("headPhoto", image.getFilePath());
			}
			
			result.setData(map);
		}*/
		
		if(users!=null&&users.size()>0){
			
			Map<String,Object>[] maps = new HashMap[users.size()];
			
			for(int i=0; i<users.size(); i++){
				DxUserImageModel image = userImageBiz.getUserImage(users.get(i).getUid());
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userName", users.get(i).getUserName());
				map.put("userNum", users.get(i).getUserNum());
				map.put("headPhoto", null);
				map.put("uid", users.get(i).getUid());
				if(image!=null){
					map.put("headPhoto", image.getFilePath());
				}
				
				map.put("isFirend", userBiz.isFirend(uid, users.get(i).getUid()));
				
				maps[i] = map;
			}
			
			result.setData(maps);
		}
		
		return result.toJSON();
	}
	
	/**
	 * 获取二维码
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDimCode/{uid}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String getDimCode(@DESParam Integer uid) throws Exception{
		
		ResultModel<Map<String,Object>> result = new ResultModel<Map<String,Object>>();
		
		DxUserModel user = userBiz.getUserByKey(uid);
		
		if(user == null){
			throw new UserException("user not exist", ErrCodeEnum.USER_NOT_EXIST.value);
		}
		
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("dimCodrPath", user.getDimCode());
		result.setData(map);

		
		return result.toJSON();
	}
	
	/**
	 * 判断用户是否有兴趣
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/existInterest/{uid}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String existInterest(@DESParam Integer uid) throws Exception{
		
		ResultModel<Boolean> result = new ResultModel<Boolean>();
		
		DxUserModel user = userBiz.getUserByKey(uid);
		
		if(user == null){
			throw new UserException("user not exist", ErrCodeEnum.USER_NOT_EXIST.value);
		}
		
		List<DxInterestModel> interes = interestBiz.getInterestByUid(user.getUid());
		
		if(interes.size() > 0){
			result.setData(true);
		}else{
			result.setData(false);
		}
		
		return result.toJSON();
	}
	
	/**
	 * 好友列表
	 * @param uid
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/firendList/{uid}/{pagesize}/{pageindex}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String firendList(@DESParam("uid") Integer uid,@PathVariable("pagesize") Integer pageSize,@PathVariable("pageindex") Integer pageIndex) throws Exception{
		
		ResultModel<Map<String,Object>[]> result = new ResultModel<Map<String,Object>[]>();
		
		Map<String,Object>[] data = userBiz.getFirendList(uid, pageSize, pageIndex);
		
		if(data != null){
			for(Map<String,Object> map : data){
				DxUserImageModel image = userImageBiz.getUserImage((Integer)map.get("uid"));
				if(image == null){
					map.put("headPhoto", null);
				}else{
					map.put("headPhoto", image.getFilePath());
				}
			}
		}
		
		result.setData(data);
		
		return result.toJSON();
	}
	
	/**
	 * 获取好友数量
	 * @param uid
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/friendsNum/{uid}",produces = "text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String friendsNum(@DESParam Integer uid) throws Exception{
		
		ResultModel<Integer> result = new ResultModel<Integer>();
		
		Integer num =  userBiz.getFirendsNum(uid);
		
		result.setData(num);
		
		return result.toJSON();
	}
	




}
