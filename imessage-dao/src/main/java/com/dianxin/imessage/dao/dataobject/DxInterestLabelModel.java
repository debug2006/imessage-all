package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 08:55:39 CST 2016 <br>
 * 
 * @return id
 */
public class DxInterestLabelModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 标签id
     */
    private Integer labelId;  
    
       
   /**
     * 兴趣名称
     */
    private Integer interestId;  
    
   	
      
   /**
    * get 标签id
    */
	public Integer getLabelId(){
	   return labelId;
	}
   
	/**
	 * set 标签id
	 */   
	public void setLabelId(Integer labelId){
	   this.labelId=labelId;
	}
	
      
   /**
    * get 兴趣名称
    */
	public Integer getInterestId(){
	   return interestId;
	}
   
	/**
	 * set 兴趣名称
	 */   
	public void setInterestId(Integer interestId){
	   this.interestId=interestId;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}