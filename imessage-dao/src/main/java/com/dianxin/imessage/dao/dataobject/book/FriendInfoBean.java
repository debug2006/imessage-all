package com.dianxin.imessage.dao.dataobject.book;

import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.dianxin.imessage.dao.dataobject.DxAbilityModel;
import com.dianxin.imessage.dao.dataobject.DxInterestModel;
import com.dianxin.imessage.dao.dataobject.DxUserAddrModel;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.DxUserStatModel;

/**
 * 
 * @author b_fatty
 * 
 * 好友详情Bean	用户封装用户好友的详情信息
 *
 */
public class FriendInfoBean {

	/**	用户id */
	private Integer uid;
	
	/** 发出纸条数  */
	private Integer sendCount;
	
	/** 收到的纸条数 */
	private Integer revcount;
	
	/** 用户昵称 */
	private String nickname;
	
	/** 用户个性签名 */
   	private String signature;
   	
   	/** 纸条号 */
   	private String userNum;
   	
   	/** 性别 1：男  2：女 */
  	private Integer	sex;
  	
  	/** 性取向 性取向 1：男 2：女性 3：双性 */
   	private Integer sexual;
   	
   	/** 星座  */
  	private String constellation;
  	
  	/** 学校编码组成 */
   	private String school;
   	
   	/** 对应数据库职业编码组成 */
   	private String job;
   	
   	/** 常出没地址 */
   	private String addr;
   	
   	/** 兴趣标签组 */
   	private String interests; 
   	
   	/** 头像存储路径 */
   	private String headUrl;
   	
   	/** 生日 */
   	private String birthday;
   	
   	/**1：冻结时间  2：召唤风暴 3：穿越*/
   	private String ability;
   	
   	/**	超能力描述*/
   	private String abilityDesc;
   	
   	/**是否黑名单*/
   	private boolean isBlack;
   	
   	/**是否开启生日提醒*/
   	private boolean isBirthdayReminder;
   	
   	/**备注名*/
   	private String remackName;
   	
   	/**标签名*/
   	private String labelName;
   	
   	
   	public String getRemackName() {
		return remackName;
	}

	public void setRemackName(String remackName) {
		this.remackName = remackName;
	}

	public boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}

	public boolean getIsBirthdayReminder() {
		return isBirthdayReminder;
	}

	public void setIsBirthdayReminder(boolean isBirthdayReminder) {
		this.isBirthdayReminder = isBirthdayReminder;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

   	public String getAbilityDesc() {
		return abilityDesc;
	}

	public void setAbilityDesc(String abilityDesc) {
		this.abilityDesc = abilityDesc;
	}

	/** 1:是好友  0：不是好友*/
   	private Integer userType;

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getRevcount() {
		return revcount;
	}

	public void setRevcount(Integer revcount) {
		this.revcount = revcount;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSexual() {
		return sexual;
	}

	public void setSexual(Integer sexual) {
		this.sexual = sexual;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}  
	
	/**
	 * 填充user信息
	 * @param user
	 */
	public void fillUser(DxUserModel user){
		if(user != null){
			
			setUid(user.getUid());
			setNickname(user.getUserName());
			setSignature(user.getSign());
			setUserNum(user.getUserNum());
			setSex(user.getSex());
			setSexual(user.getSexual());
			setConstellation(getConstellation());
			if(user.getSchoolClassA() != null){
				setSchool(user.getSchoolClassA());
			}
			if(user.getSchoolClassB() != null ){
				if(StringUtils.isEmpty(getSchool())){
					setSchool(user.getSchoolClassB());
				}else{
					setSchool(getSchool() + "," + user.getSchoolClassB());
				}
			}
			if(user.getSchoolClassC() != null ){
				if(StringUtils.isEmpty(getSchool())){
					setSchool(user.getSchoolClassC());
				}else{
					setSchool(getSchool() + "," + user.getSchoolClassC());
				}
			}
			if(user.getJobClassA()!=null){
				setJob(user.getJobClassA());
			}
			if(user.getJobClassB()!=null){
				if(StringUtils.isEmpty(getJob())){
					setJob(user.getJobClassB());
				}else{
					setJob(getJob() + "," + user.getJobClassB());
				}
			}
			if(user.getJobClassC()!=null){
				if(StringUtils.isEmpty(getJob())){
					setJob(user.getJobClassC());
				}else{
					setJob(getJob() + "," + user.getJobClassC());
				}
			}
			setBirthday(user.getBirthday());
		}
	}
	
	/**
	 * 填充stat信息
	 * @param stat
	 */
	public void fillStat(DxUserStatModel stat){
		
		if(stat != null){
			
			setSendCount(stat.getSendCount());
			setRevcount(stat.getRevCount());
		}
	}
	
	/**
	 * 填充兴趣	
	 * @param interes
	 */
	public void fillInterest(List<DxInterestModel> interes){
		
		if(interes != null){
			String str = null;
			for(DxInterestModel i : interes){
				if(StringUtils.isEmpty(str)){
					str = i.getInterest();
				}else{
					str = str + "&" + i.getInterest();
				}
			}
			
			setInterests(str);
		}
	}
	
	/**
	 * 填充头像
	 */
	public void fillUserImage(DxUserImageModel image){
		if(image != null){
			setHeadUrl(image.getFilePath());
		}
	}
	
	/**
	 * 填充超能力
	 */
	public void fillAbility(List<DxAbilityModel> list){
		
		String abilites = null;
		String desc = null;
		if(list != null){
			for(DxAbilityModel ability : list){
				if(StringUtils.isEmpty(abilites)){
					if(ability!=null){
						abilites = ability.getAbilityName();
					}
				}else{
					if(ability!=null){
						abilites = abilites + "&" + ability.getAbilityName();
					}
				}
				if(StringUtils.isEmpty(desc)){
					if(ability!=null){
						desc = ability.getAbilityDesc();
					}
				}else{
					if(ability!=null){
						desc = desc + "&" + ability.getAbilityDesc();
					}
				}
			}
		}
		
		setAbility(abilites);
		setAbilityDesc(desc);
	}
	
	/**
	 * 填充地址
	 */
	public void fillAddr (List<DxUserAddrModel> addrs){
		
		String addr = null;
		
		if(null != addrs && addrs.size() >0){
			
			for(DxUserAddrModel model : addrs){
				if(addr == null){
					addr = model.getAddrDetail();
				}else{
					addr = addr + "&" + model.getAddrDetail();
				}
			}
		}
		
		setAddr(addr);
	}
}
