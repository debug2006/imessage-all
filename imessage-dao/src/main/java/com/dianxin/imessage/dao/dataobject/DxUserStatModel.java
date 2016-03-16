package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Feb 22 23:18:56 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxUserStatModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * 用户uid
     */
    private Integer uid;  
    
       
   /**
     * 对方uid
     */
    private Integer otherUid;  
    
       
   /**
     * 发送纸条数
     */
    private Integer sendCount;  
    
       
   /**
     * 接收纸条数
     */
    private Integer revCount;  
    
       
   /**
     * 调戏纸条君已达多少天
     */
    private Integer dayCount;  
    
       
   /**
     * 创建时间
     */
    private java.sql.Timestamp createDate;  
    
       
   /**
     * 最后修改时间
     */
    private java.sql.Timestamp modifyDate;  
    
   	
      
   /**
    * get 自增id
    */
	public Integer getId(){
	   return id;
	}
   
	/**
	 * set 自增id
	 */   
	public void setId(Integer id){
	   this.id=id;
	}
	
      
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
    * get 对方uid
    */
	public Integer getOtherUid(){
	   return otherUid;
	}
   
	/**
	 * set 对方uid
	 */   
	public void setOtherUid(Integer otherUid){
	   this.otherUid=otherUid;
	}
	
      
   /**
    * get 发送纸条数
    */
	public Integer getSendCount(){
	   return sendCount;
	}
   
	/**
	 * set 发送纸条数
	 */   
	public void setSendCount(Integer sendCount){
	   this.sendCount=sendCount;
	}
	
      
   /**
    * get 接收纸条数
    */
	public Integer getRevCount(){
	   return revCount;
	}
   
	/**
	 * set 接收纸条数
	 */   
	public void setRevCount(Integer revCount){
	   this.revCount=revCount;
	}
	
      
   /**
    * get 调戏纸条君已达多少天
    */
	public Integer getDayCount(){
	   return dayCount;
	}
   
	/**
	 * set 调戏纸条君已达多少天
	 */   
	public void setDayCount(Integer dayCount){
	   this.dayCount=dayCount;
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
    * get 最后修改时间
    */
	public java.sql.Timestamp getModifyDate(){
	   return modifyDate;
	}
   
	/**
	 * set 最后修改时间
	 */   
	public void setModifyDate(java.sql.Timestamp modifyDate){
	   this.modifyDate=modifyDate;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}