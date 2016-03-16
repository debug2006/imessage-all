package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:09:02 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxAbilityModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * 超能力名称
     */
    private String abilityName;  
    
       
   /**
     * 超能力说明
     */
    private String abilityDesc;  
    
       
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
    * get 超能力名称
    */
	public String getAbilityName(){
	   return abilityName;
	}
   
	/**
	 * set 超能力名称
	 */   
	public void setAbilityName(String abilityName){
	   this.abilityName=abilityName;
	}
	
      
   /**
    * get 超能力说明
    */
	public String getAbilityDesc(){
	   return abilityDesc;
	}
   
	/**
	 * set 超能力说明
	 */   
	public void setAbilityDesc(String abilityDesc){
	   this.abilityDesc=abilityDesc;
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