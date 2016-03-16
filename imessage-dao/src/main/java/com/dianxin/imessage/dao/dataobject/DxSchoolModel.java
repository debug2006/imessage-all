package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:42:04 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxSchoolModel extends BaseModel{
	
	private static final long serialVersionUID = 1L;

       
   /**
     * ${prop.comment}
     */
    private Long id;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolname;  
    
       
   /**
     * ${prop.comment}
     */
    private String schooladdress;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolphone;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolphone2;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolpostalcode;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolwebsite;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolxueduan;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolprovince;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolqu;  
    
       
   /**
     * ${prop.comment}
     */
    private String schoolxian;  
    
       
   /**
     * ${prop.comment}
     */
    private Integer page;  
    
   	
      
   /**
    * get ${prop.comment}
    */
	public Long getId(){
	   return id;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setId(Long id){
	   this.id=id;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolname(){
	   return schoolname;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolname(String schoolname){
	   this.schoolname=schoolname;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchooladdress(){
	   return schooladdress;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchooladdress(String schooladdress){
	   this.schooladdress=schooladdress;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolphone(){
	   return schoolphone;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolphone(String schoolphone){
	   this.schoolphone=schoolphone;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolphone2(){
	   return schoolphone2;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolphone2(String schoolphone2){
	   this.schoolphone2=schoolphone2;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolpostalcode(){
	   return schoolpostalcode;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolpostalcode(String schoolpostalcode){
	   this.schoolpostalcode=schoolpostalcode;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolwebsite(){
	   return schoolwebsite;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolwebsite(String schoolwebsite){
	   this.schoolwebsite=schoolwebsite;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolxueduan(){
	   return schoolxueduan;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolxueduan(String schoolxueduan){
	   this.schoolxueduan=schoolxueduan;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolprovince(){
	   return schoolprovince;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolprovince(String schoolprovince){
	   this.schoolprovince=schoolprovince;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolqu(){
	   return schoolqu;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolqu(String schoolqu){
	   this.schoolqu=schoolqu;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public String getSchoolxian(){
	   return schoolxian;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setSchoolxian(String schoolxian){
	   this.schoolxian=schoolxian;
	}
	
      
   /**
    * get ${prop.comment}
    */
	public Integer getPage(){
	   return page;
	}
   
	/**
	 * set ${prop.comment}
	 */   
	public void setPage(Integer page){
	   this.page=page;
	}
	
       public String toString(){
	   return com.alibaba.fastjson.JSON.toJSONString(this);
    }
	
}