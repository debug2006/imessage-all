package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Wed Feb 03 05:49:27 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxReportModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * 举报人uid
     */
    private Integer uid;  
    
       
   /**
     * 被举报人uid
     */
    private Integer beUid;  
    
       
   /**
     * 举报内容
     */
    private String reportContent;  
    
       
   /**
     * 举报理由 1：  2：
     */
    private Integer reportReason;  
    
       
   /**
     * 处理状态 1：提交 2：处理中 3：已处理 4：
     */
    private Integer status;  
    
       
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
    * get 举报人uid
    */
	public Integer getUid(){
	   return uid;
	}
   
	/**
	 * set 举报人uid
	 */   
	public void setUid(Integer uid){
	   this.uid=uid;
	}
	
      
   /**
    * get 被举报人uid
    */
	public Integer getBeUid(){
	   return beUid;
	}
   
	/**
	 * set 被举报人uid
	 */   
	public void setBeUid(Integer beUid){
	   this.beUid=beUid;
	}
	
      
   /**
    * get 举报内容
    */
	public String getReportContent(){
	   return reportContent;
	}
   
	/**
	 * set 举报内容
	 */   
	public void setReportContent(String reportContent){
	   this.reportContent=reportContent;
	}
	
      
   /**
    * get 举报理由 1：  2：
    */
	public Integer getReportReason(){
	   return reportReason;
	}
   
	/**
	 * set 举报理由 1：  2：
	 */   
	public void setReportReason(Integer reportReason){
	   this.reportReason=reportReason;
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