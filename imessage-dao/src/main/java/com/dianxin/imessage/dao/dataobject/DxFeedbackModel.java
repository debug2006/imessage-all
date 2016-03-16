package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:19:55 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxFeedbackModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * 反馈uid
     */
    private Integer uid;  
    
       
   /**
     * 反馈内容
     */
    private String feedbackContent;  
    
       
   /**
     * 处理状态 1：提交 2：处理中 3：已处理 4：
     */
    private Integer status;  
    
       
   /**
     * 反馈手机号码
     */
    private Long telphone;  
    
       
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
    * get 反馈uid
    */
	public Integer getUid(){
	   return uid;
	}
   
	/**
	 * set 反馈uid
	 */   
	public void setUid(Integer uid){
	   this.uid=uid;
	}
	
      
   /**
    * get 反馈内容
    */
	public String getFeedbackContent(){
	   return feedbackContent;
	}
   
	/**
	 * set 反馈内容
	 */   
	public void setFeedbackContent(String feedbackContent){
	   this.feedbackContent=feedbackContent;
	}
	
      
   /**
    * get 处理状态 1：提交 2：处理中 3：已处理 4：
    */
	public Integer getStatus(){
	   return status;
	}
   
	/**
	 * set 处理状态 1：提交 2：处理中 3：已处理 4：
	 */   
	public void setStatus(Integer status){
	   this.status=status;
	}
	
      
   /**
    * get 反馈手机号码
    */
	public Long getTelphone(){
	   return telphone;
	}
   
	/**
	 * set 反馈手机号码
	 */   
	public void setTelphone(Long telphone){
	   this.telphone=telphone;
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