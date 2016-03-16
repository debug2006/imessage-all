package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:27:13 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxInterestAuditModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * 兴趣描述
     */
    private String interest;  
    
       
   /**
     * 审核状态 1:未审核 2审核通过 3审核不通过
     */
    private Integer auditStatus;  
    
       
   /**
     * 用户uid
     */
    private Integer uid;  
    
       
   /**
     * 创建时间
     */
    private java.sql.Timestamp createDate;  
    
       
   /**
     * 修改时间
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
    * get 兴趣描述
    */
	public String getInterest(){
	   return interest;
	}
   
	/**
	 * set 兴趣描述
	 */   
	public void setInterest(String interest){
	   this.interest=interest;
	}
	
      
   /**
    * get 审核状态 1:未审核 2审核通过 3审核不通过
    */
	public Integer getAuditStatus(){
	   return auditStatus;
	}
   
	/**
	 * set 审核状态 1:未审核 2审核通过 3审核不通过
	 */   
	public void setAuditStatus(Integer auditStatus){
	   this.auditStatus=auditStatus;
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