package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Mar 07 22:34:01 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxActivityStatModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 自增id
     */
    private Integer id;  
    
       
   /**
     * uid
     */
    private Integer uid;  
    
       
   /**
     * 打开次数
     */
    private Integer times;  
    
       
   /**
     * 统计日期
     */
    private java.util.Date statDate;  
    
   	
      
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
    * get uid
    */
	public Integer getUid(){
	   return uid;
	}
   
	/**
	 * set uid
	 */   
	public void setUid(Integer uid){
	   this.uid=uid;
	}
	
      
   /**
    * get 打开次数
    */
	public Integer getTimes(){
	   return times;
	}
   
	/**
	 * set 打开次数
	 */   
	public void setTimes(Integer times){
	   this.times=times;
	}
	
      
   /**
    * get 统计日期
    */
	public java.util.Date getStatDate(){
	   return statDate;
	}
   
	/**
	 * set 统计日期
	 */   
	public void setStatDate(java.util.Date statDate){
	   this.statDate=statDate;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}