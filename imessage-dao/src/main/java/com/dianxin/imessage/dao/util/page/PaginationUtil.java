/**==============================================================================
 * Copyright (c) 2013 by www.wacai.com, All rights reserved.
 * ==============================================================================
 * This software is the confidential and proprietary information of 
 * wacai.com, Inc. ("Confidential Information"). You shall not disclose 
 * such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with wacai.com, Inc.
 * ------------------------------------------------------------------------------
 * File name: PaginationUtil.java
 * Author: riguangshi
 * Date: 2014年10月30日 下午4:07:18
 * Description:
 *      Nothing.
 * Function List:
 *      1. Nothing.
 * History: 
 *      1. Nothing.
 * ==============================================================================
 */

package com.dianxin.imessage.dao.util.page;

/**
 * 计算分页工具类
 *
 * @author chong.qin
 */
public class PaginationUtil {

	/**
	 * 计算页数
	 * @param rowCount
	 * @param pageSize
	 * @return
	 */
	public static int calPageCount(int rowCount,int pageSize){
		int pageCount=rowCount/pageSize+1;
		if(rowCount%pageSize==0)
			pageCount--;
		return pageCount;
	}
	/**
	 * 计算指定页的起始位置
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	public static int calPageOffset(int pageId, int pageSize){
		return (pageId-1)*pageSize;
	}
	/**
	 * 获取下一页编号
	 * @param pageId
	 * @param pageCount
	 * @return
	 */
	public int getNextPage(int pageId,int pageCount){
		return ((pageId+1) > pageCount)?pageCount:(pageId+1);
	}

	/**
	 * 获取上一页编号
	 * @param pageId
	 * @return
	 */
	public int getPrePage(int pageId){
		return ((pageId-1) < 1)?1:(pageId-1);
	}
}
