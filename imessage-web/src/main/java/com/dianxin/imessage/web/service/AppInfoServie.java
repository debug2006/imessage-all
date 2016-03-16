package com.dianxin.imessage.web.service;

import com.dianxin.imessage.biz.config.IVsersionUpdateBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.DxVsersionUpdateModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ==============================================================================
 * Copyright (c) 2015 by www.dianxin.com, All rights reserved.
 * ==============================================================================
 * This software is the confidential and proprietary information of
 * tencent.com, Inc. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with dianxin.com, Inc.
 * ------------------------------------------------------------------------------
 * <p/>
 * Author: chong.qin
 * Date: 2016/3/11
 * Description:
 * Nothing.
 * Function List:
 * 1. Nothing.
 * History:
 * 1. Nothing.
 * ==============================================================================
 */
@Controller
@RequestMapping("/config/")
public class AppInfoServie {

	private Logger logger = LoggerFactory.getLogger(AppInfoServie.class);
	
	@Autowired
	private IVsersionUpdateBiz iVsersionUpdateBiz;
	
	/**
	 * 获取app最新版本,跟目前的版本进行对比
	 * 1、如果是最新版本则返回  2、如果非最新版本，返回最新版本号和url
	 * @return
	 */
	@RequestMapping(value = "appInfo/{uid}",produces="text/html;charset=UTF-8",method= RequestMethod.GET)
	public @ResponseBody String getLateVer(@DESParam  String uid,Integer deviceType,String version){
		ResultModel<DxVsersionUpdateModel> result = new ResultModel<DxVsersionUpdateModel>();
		if(StringUtils.isBlank(uid)||StringUtils.isBlank(String.valueOf(deviceType))||StringUtils.isBlank(version))
		{
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
			return result.toJSON();
		}

		//获取最新更新版本信息
		DxVsersionUpdateModel dxVsersionUpdateModel = iVsersionUpdateBiz.queryVersion(deviceType);
		if(null==dxVsersionUpdateModel){
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(ErrCodeEnum.SERVICE_ERROR.value);
			return result.toJSON();
		}
		String versionNum = dxVsersionUpdateModel.getVersionNum();
		if(version.equals(versionNum)){
			return result.toJSON();  //相等说明 已经是最新版本
		}else{
			result.setData(dxVsersionUpdateModel);
			return result.toJSON();
		}
	}
}
