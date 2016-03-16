package com.dianxin.imessage.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里是公用的 由Mapper配置文件实现<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> 2015-1-12 <br>
 * <b>版权所有：<b>dianxin.com(C)<br>
 */
public interface BaseMapper<T> {
	/*****************CRUD操作********************/
	public T selectByPrimaryKey(Object key) throws Exception;
	public Integer updateByPrimaryKey(T t) throws Exception;
	public Integer deleteByPrimaryKey(Object key) throws Exception;
	public Integer insert(T t) throws Exception;
	public Integer insertReturn(T t) throws Exception;
	
	public Integer deleteByEntity(T entity) throws Exception;
	//public Integer deleteByMap()
	public List<T> selectBySql(@Param(value = "sql")String sql) throws Exception;
	public Integer updateBySql(@Param(value = "sql")String sql) throws Exception;
	public Integer deleteBySql(@Param(value = "sql")String sql) throws Exception;
	public Integer insertBySql(@Param(value = "sql")String sql) throws Exception;
	/***********************分页查询操作************************/
	public Integer selectByModelCount(T  model) throws Exception;
	public List<T> selectByModel(T model) throws Exception;
	public Integer selectByMapPagingCount(Map<?,?> map) throws java.sql.SQLException;
	public List<T> selectByMapPaging(Map<?,?> map) throws java.sql.SQLException;
	/***********************查询不分页*************************/
	public List<T> selectByEntiry(T entity) throws Exception;
	public Integer selectByMapCount(Map<?, ?>  map) throws Exception;
	public List<T> selectByMap(Map<?, ?>  map) throws Exception;
		/************************** 图表 **************************/
	public List<Map<?,?>> charts(Map<?,?> map) throws Exception;
	/**************************递归查询***********************/
	public List<T> selectByChild(T model) throws Exception;
}

