package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:30:53 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxPositionModel extends BaseModel{
	
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
     * 经度
     */
    private String longitude;  
    
       
   /**
     * 纬度
     */
    private String latitude;  
    
       
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
    * get 经度
    */
	public String getLongitude(){
	   return longitude;
	}
   
	/**
	 * set 经度
	 */   
	public void setLongitude(String longitude){
	   this.longitude=longitude;
	}
	
      
   /**
    * get 纬度
    */
	public String getLatitude(){
	   return latitude;
	}
   
	/**
	 * set 纬度
	 */   
	public void setLatitude(String latitude){
	   this.latitude=latitude;
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