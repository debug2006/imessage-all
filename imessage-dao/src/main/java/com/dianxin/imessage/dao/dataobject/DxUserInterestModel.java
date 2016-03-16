package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:37:27 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxUserInterestModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 兴趣id
     */
    private Integer interestId;  
    
       
   /**
     * 用户uid
     */
    private Integer uid;  
    
       
   /**
     * ${prop.comment}
     */
    private Integer KEY;  
    
   	
      
   /**
    * get 兴趣id
    */
	public Integer getInterestId(){
	   return interestId;
	}
   
	/**
	 * set 兴趣id
	 */   
	public void setInterestId(Integer interestId){
	   this.interestId=interestId;
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
    * get ${prop.comment}
    */
	public Integer getKEY(){
	   return KEY;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setKEY(Integer KEY){
	   this.KEY=KEY;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}