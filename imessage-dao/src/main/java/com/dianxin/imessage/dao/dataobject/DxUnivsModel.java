package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:43:14 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxUnivsModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * ${prop.comment}
     */
    private Integer id;  
    
       
   /**
     * ${prop.comment}
     */
    private String name;  
    
       
   /**
     * ${prop.comment}
     */
    private Integer pid;  
    
   	
      
   /**
    * get ${prop.comment}
    */
	public Integer getId(){
	   return id;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setId(Integer id){
	   this.id=id;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getName(){
	   return name;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setName(String name){
	   this.name=name;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public Integer getPid(){
	   return pid;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setPid(Integer pid){
	   this.pid=pid;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}