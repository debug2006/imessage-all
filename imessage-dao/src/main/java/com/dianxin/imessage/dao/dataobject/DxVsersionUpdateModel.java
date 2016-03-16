package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 12:53:56 CST 2016 <br>
 * 
 * @return id
 */
public class DxVsersionUpdateModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * 版本号
     */
    private String versionNum;  
    
       
   /**
     * 设备类型
     */
    private Integer deviceType;  
    
       
   /**
     * 该版本的升级地址
     */
    private String url;  
    
       
   /**
     * 版本描述
     */
    private String vesionDesc;  
    
       
   /**
     * 修复内容列表
     */
    private String fixedList;  
    
       
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
    * get 版本号
    */
	public String getVersionNum(){
	   return versionNum;
	}
   
	/**
	 * set 版本号
	 */   
	public void setVersionNum(String versionNum){
	   this.versionNum=versionNum;
	}
	
      
   /**
    * get 设备类型
    */
	public Integer getDeviceType(){
	   return deviceType;
	}
   
	/**
	 * set 设备类型
	 */   
	public void setDeviceType(Integer deviceType){
	   this.deviceType=deviceType;
	}
	
      
   /**
    * get 该版本的升级地址
    */
	public String getUrl(){
	   return url;
	}
   
	/**
	 * set 该版本的升级地址
	 */   
	public void setUrl(String url){
	   this.url=url;
	}
	
      
   /**
    * get 版本描述
    */
	public String getVesionDesc(){
	   return vesionDesc;
	}
   
	/**
	 * set 版本描述
	 */   
	public void setVesionDesc(String vesionDesc){
	   this.vesionDesc=vesionDesc;
	}
	
      
   /**
    * get 修复内容列表
    */
	public String getFixedList(){
	   return fixedList;
	}
   
	/**
	 * set 修复内容列表
	 */   
	public void setFixedList(String fixedList){
	   this.fixedList=fixedList;
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