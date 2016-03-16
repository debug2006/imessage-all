package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 16:45:11 CST 2016 <br>
 * 
 * @return id
 */
public class DxUserAddrModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * 用户地址id
     */
    private Integer addrId;  
    
       
   /**
     * 用户uid
     */
    private Integer uid;  
    
       
   /**
     * 省
     */
    private String province;  
    
       
   /**
     * 市
     */
    private String city;  
    
       
   /**
     * 区
     */
    private String area;  
    
       
   /**
     * 第一 第二 第三
     */
    private Integer rank;  
    
       
   /**
     * 详细地址
     */
    private String addrDetail;  
    
       
   /**
     * 创建时间
     */
    private java.sql.Timestamp createDate;  
    
       
   /**
     * 修改时间
     */
    private java.sql.Timestamp modifyDate;  
    
   	
      
   /**
    * get 用户地址id
    */
	public Integer getAddrId(){
	   return addrId;
	}
   
	/**
	 * set 用户地址id
	 */   
	public void setAddrId(Integer addrId){
	   this.addrId=addrId;
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
    * get 省
    */
	public String getProvince(){
	   return province;
	}
   
	/**
	 * set 省
	 */   
	public void setProvince(String province){
	   this.province=province;
	}
	
      
   /**
    * get 市
    */
	public String getCity(){
	   return city;
	}
   
	/**
	 * set 市
	 */   
	public void setCity(String city){
	   this.city=city;
	}
	
      
   /**
    * get 区
    */
	public String getArea(){
	   return area;
	}
   
	/**
	 * set 区
	 */   
	public void setArea(String area){
	   this.area=area;
	}
	
      
   /**
    * get 第一 第二 第三
    */
	public Integer getRank(){
	   return rank;
	}
   
	/**
	 * set 第一 第二 第三
	 */   
	public void setRank(Integer rank){
	   this.rank=rank;
	}
	
      
   /**
    * get 详细地址
    */
	public String getAddrDetail(){
	   return addrDetail;
	}
   
	/**
	 * set 详细地址
	 */   
	public void setAddrDetail(String addrDetail){
	   this.addrDetail=addrDetail;
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