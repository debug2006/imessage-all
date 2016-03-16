package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:11:38 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxUserModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 用户uid
     */
    private Integer uid;  
    
       
   /**
     * 用户名（昵称）
     */
    private String userName;  
    
       
   /**
     * 手机号码
     */
    private Long telphone;  
    
       
   /**
     * 密码
     */
    private String password;  
    
       
   /**
     * 真名
     */
    private String realName;  
    
       
   /**
     * 邮箱
     */
    private String email;  
    
       
   /**
     * 性别 1：男  2：女
     */
    private Integer sex;  
    
       
   /**
     * 性取向 1：男 2：女性 3：双性
     */
    private Integer sexual;  
    
       
   /**
     * 个人签名
     */
    private String sign;  
    
       
   /**
     * 年-月-日
     */
    private String birthday;  
    
       
   /**
     * 1:android 2：ios 3：h5 4:web
     */
    private Integer regSource;  
    
       
   /**
     * 客户端版本
     */
    private String regVersion;  
    
       
   /**
     * 职业-一级分类
     */
    private String jobClassA;  
    
       
   /**
     * 职业-二级分类
     */
    private String jobClassB;  
    
       
   /**
     * 职业-三级分类
     */
    private String jobClassC;  
    
       
   /**
     * 学校-一级分类
     */
    private String schoolClassA;  
    
       
   /**
     * 学校-二级分类
     */
    private String schoolClassB;  
    
       
   /**
     * 学校-三级分类
     */
    private String schoolClassC;  
    
       
   /**
     * 星座
     */
    private String constellation;  
    
       
   /**
     * 1开始白羊金牛双子巨蟹狮子处女天枰天蝎射手摩羯水瓶双鱼
     */
    private String constellationType;  
    
       
   /**
     * 国家码
     */
    private String districtNumbe;  
    
       
   /**
     * 是否删除 1：有效 2：注销
     */
    private Integer isDel;  
    
       
   /**
     * 年龄
     */
    private Integer age;  
    
       
   /**
     * 纸条号 数字+英文
     */
    private String userNum;  
    
       
   /**
     * 纸条号 系统自动生成
     */
    private String userGenNum;  
    
       
   /**
     * 个人二维码图片地址
     */
    private String dimCode;  
    
       
   /**
     * 资料完成度  例如90%  值为90
     */
    private Integer completeDegree;  
    
       
   /**
     * 通过手机号搜索到我 1:默认 可以 2：不能
     */
    private Integer searchTelFalg;  
    
       
   /**
     * 通过纸条号搜索到我 1:默认 可以 2：不能
     */
    private Integer searchNumFalg;  
    
       
   /**
     * 创建时间
     */
    private java.sql.Timestamp createDate;  
    
       
   /**
     * 修改时间
     */
    private java.sql.Timestamp modifyDate;  
    
   	
      
   /**
    * get 用户uid
    */
	public Integer getUid(){
	   return uid;
	}
   
	/**
	 * set 用户uid
	 */   
	public void setUid(Integer uid){
	   this.uid=uid;
	}
	
      
   /**
    * get 用户名（昵称）
    */
	public String getUserName(){
	   return userName;
	}
   
	/**
	 * set 用户名（昵称）
	 */   
	public void setUserName(String userName){
	   this.userName=userName;
	}
	
      
   /**
    * get 手机号码
    */
	public Long getTelphone(){
	   return telphone;
	}
   
	/**
	 * set 手机号码
	 */   
	public void setTelphone(Long telphone){
	   this.telphone=telphone;
	}
	
      
   /**
    * get 密码
    */
	public String getPassword(){
	   return password;
	}
   
	/**
	 * set 密码
	 */   
	public void setPassword(String password){
	   this.password=password;
	}
	
      
   /**
    * get 真名
    */
	public String getRealName(){
	   return realName;
	}
   
	/**
	 * set 真名
	 */   
	public void setRealName(String realName){
	   this.realName=realName;
	}
	
      
   /**
    * get 邮箱
    */
	public String getEmail(){
	   return email;
	}
   
	/**
	 * set 邮箱
	 */   
	public void setEmail(String email){
	   this.email=email;
	}
	
      
   /**
    * get 性别 1：男  2：女
    */
	public Integer getSex(){
	   return sex;
	}
   
	/**
	 * set 性别 1：男  2：女
	 */   
	public void setSex(Integer sex){
	   this.sex=sex;
	}
	
      
   /**
    * get 性取向 1：男 2：女性 3：双性
    */
	public Integer getSexual(){
	   return sexual;
	}
   
	/**
	 * set 性取向 1：男 2：女性 3：双性
	 */   
	public void setSexual(Integer sexual){
	   this.sexual=sexual;
	}
	
      
   /**
    * get 个人签名
    */
	public String getSign(){
	   return sign;
	}
   
	/**
	 * set 个人签名
	 */   
	public void setSign(String sign){
	   this.sign=sign;
	}
	
      
   /**
    * get 年-月-日
    */
	public String getBirthday(){
	   return birthday;
	}
   
	/**
	 * set 年-月-日
	 */   
	public void setBirthday(String birthday){
	   this.birthday=birthday;
	}
	
      
   /**
    * get 1:android 2：ios 3：h5 4:web
    */
	public Integer getRegSource(){
	   return regSource;
	}
   
	/**
	 * set 1:android 2：ios 3：h5 4:web
	 */   
	public void setRegSource(Integer regSource){
	   this.regSource=regSource;
	}
	
      
   /**
    * get 客户端版本
    */
	public String getRegVersion(){
	   return regVersion;
	}
   
	/**
	 * set 客户端版本
	 */   
	public void setRegVersion(String regVersion){
	   this.regVersion=regVersion;
	}
	
      
   /**
    * get 职业-一级分类
    */
	public String getJobClassA(){
	   return jobClassA;
	}
   
	/**
	 * set 职业-一级分类
	 */   
	public void setJobClassA(String jobClassA){
	   this.jobClassA=jobClassA;
	}
	
      
   /**
    * get 职业-二级分类
    */
	public String getJobClassB(){
	   return jobClassB;
	}
   
	/**
	 * set 职业-二级分类
	 */   
	public void setJobClassB(String jobClassB){
	   this.jobClassB=jobClassB;
	}
	
      
   /**
    * get 职业-三级分类
    */
	public String getJobClassC(){
	   return jobClassC;
	}
   
	/**
	 * set 职业-三级分类
	 */   
	public void setJobClassC(String jobClassC){
	   this.jobClassC=jobClassC;
	}
	
      
   /**
    * get 学校-一级分类
    */
	public String getSchoolClassA(){
	   return schoolClassA;
	}
   
	/**
	 * set 学校-一级分类
	 */   
	public void setSchoolClassA(String schoolClassA){
	   this.schoolClassA=schoolClassA;
	}
	
      
   /**
    * get 学校-二级分类
    */
	public String getSchoolClassB(){
	   return schoolClassB;
	}
   
	/**
	 * set 学校-二级分类
	 */   
	public void setSchoolClassB(String schoolClassB){
	   this.schoolClassB=schoolClassB;
	}
	
      
   /**
    * get 学校-三级分类
    */
	public String getSchoolClassC(){
	   return schoolClassC;
	}
   
	/**
	 * set 学校-三级分类
	 */   
	public void setSchoolClassC(String schoolClassC){
	   this.schoolClassC=schoolClassC;
	}
	
      
   /**
    * get 星座
    */
	public String getConstellation(){
	   return constellation;
	}
   
	/**
	 * set 星座
	 */   
	public void setConstellation(String constellation){
	   this.constellation=constellation;
	}
	
      
   /**
    * get 1开始白羊金牛双子巨蟹狮子处女天枰天蝎射手摩羯水瓶双鱼
    */
	public String getConstellationType(){
	   return constellationType;
	}
   
	/**
	 * set 1开始白羊金牛双子巨蟹狮子处女天枰天蝎射手摩羯水瓶双鱼
	 */   
	public void setConstellationType(String constellationType){
	   this.constellationType=constellationType;
	}
	
      
   /**
    * get 国家码
    */
	public String getDistrictNumbe(){
	   return districtNumbe;
	}
   
	/**
	 * set 国家码
	 */   
	public void setDistrictNumbe(String districtNumbe){
	   this.districtNumbe=districtNumbe;
	}
	
      
   /**
    * get 是否删除 1：有效 2：注销
    */
	public Integer getIsDel(){
	   return isDel;
	}
   
	/**
	 * set 是否删除 1：有效 2：注销
	 */   
	public void setIsDel(Integer isDel){
	   this.isDel=isDel;
	}
	
      
   /**
    * get 年龄
    */
	public Integer getAge(){
	   return age;
	}
   
	/**
	 * set 年龄
	 */   
	public void setAge(Integer age){
	   this.age=age;
	}
	
      
   /**
    * get 纸条号 数字+英文
    */
	public String getUserNum(){
	   return userNum;
	}
   
	/**
	 * set 纸条号 数字+英文
	 */   
	public void setUserNum(String userNum){
	   this.userNum=userNum;
	}
	
      
   /**
    * get 纸条号 系统自动生成
    */
	public String getUserGenNum(){
	   return userGenNum;
	}
   
	/**
	 * set 纸条号 系统自动生成
	 */   
	public void setUserGenNum(String userGenNum){
	   this.userGenNum=userGenNum;
	}
	
      
   /**
    * get 个人二维码图片地址
    */
	public String getDimCode(){
	   return dimCode;
	}
   
	/**
	 * set 个人二维码图片地址
	 */   
	public void setDimCode(String dimCode){
	   this.dimCode=dimCode;
	}
	
      
   /**
    * get 资料完成度  例如90%  值为90
    */
	public Integer getCompleteDegree(){
	   return completeDegree;
	}
   
	/**
	 * set 资料完成度  例如90%  值为90
	 */   
	public void setCompleteDegree(Integer completeDegree){
	   this.completeDegree=completeDegree;
	}
	
      
   /**
    * get 通过手机号搜索到我 1:默认 可以 2：不能
    */
	public Integer getSearchTelFalg(){
	   return searchTelFalg;
	}
   
	/**
	 * set 通过手机号搜索到我 1:默认 可以 2：不能
	 */   
	public void setSearchTelFalg(Integer searchTelFalg){
	   this.searchTelFalg=searchTelFalg;
	}
	
      
   /**
    * get 通过纸条号搜索到我 1:默认 可以 2：不能
    */
	public Integer getSearchNumFalg(){
	   return searchNumFalg;
	}
   
	/**
	 * set 通过纸条号搜索到我 1:默认 可以 2：不能
	 */   
	public void setSearchNumFalg(Integer searchNumFalg){
	   this.searchNumFalg=searchNumFalg;
	}
	
      
   /**
    * get 创建时间
    */
	public java.sql.Timestamp getCreateDate(){
	   return createDate;
	}
   
	/**
	 * set 创建时间
	 */   
	public void setCreateDate(java.sql.Timestamp createDate){
	   this.createDate=createDate;
	}
	
      
   /**
    * get 修改时间
    */
	public java.sql.Timestamp getModifyDate(){
	   return modifyDate;
	}
   
	/**
	 * set 修改时间
	 */   
	public void setModifyDate(java.sql.Timestamp modifyDate){
	   this.modifyDate=modifyDate;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}