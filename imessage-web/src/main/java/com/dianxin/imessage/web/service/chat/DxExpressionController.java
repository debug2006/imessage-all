package com.dianxin.imessage.web.service.chat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dianxin.imessage.biz.chat.IUserselfExpressionBiz;
import com.dianxin.imessage.biz.config.IFileUpload;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.dao.dataobject.DxUserselfExpressionModel;

@Controller
@RequestMapping("/chat")
public class DxExpressionController {
	
	@Autowired
	private IFileUpload fileUpload;
	
	@Autowired
	private IUserselfExpressionBiz expressionBiz;
	
	/**
	 * 上传表情值oss 返回保存路径
	 * @throws Exception 
	 */
	@RequestMapping(value="/expression/{uid}",produces="text/html;charset=UTF-8",method=RequestMethod.POST)
	public @ResponseBody String uploadExpression(@DESParam Integer uid,MultipartFile expression) throws Exception{
		
		UserManageUtil.checkUid(uid);
		
		ResultModel<Map> result = new ResultModel<Map>();
		
		//将表情文件保存到oss
		String path = fileUpload.uploadFile(expression);
		
		//将表情路劲入库
		DxUserselfExpressionModel model = new DxUserselfExpressionModel();
		model.setCreateDate(new Timestamp(System.currentTimeMillis()));
		model.setFilePath(path);
		model.setModifyDate(new Timestamp(System.currentTimeMillis()));
		model.setUid(uid);
		Integer expressionId = expressionBiz.saveExpression(model);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("expressionId", expressionId);
		map.put("path", path);
		result.setData(map);
		
		return result.toJSON();
	}
	
	/**
	 * 删除表情
	 * @throws Exception 
	 */
	@RequestMapping(value="/expression/{uid}/{expressionId}",produces="text/html;charset=UTF-8",method=RequestMethod.DELETE)
	public @ResponseBody String delExpression(@DESParam Integer uid,@DESParam Integer expressionId) throws Exception{
		
		UserManageUtil.checkUid(uid);
		
		expressionBiz.delExpressionByKey(expressionId);
		
		ResultModel result = new ResultModel();
		
		return result.toJSON();
	}
	
	/**
	 * 获取表情列表
	 */
	@RequestMapping(value="/expressionList/{uid}",produces="text/html;charset=UTF-8",method=RequestMethod.GET)
	public @ResponseBody String getExpressionList(@DESParam Integer uid) throws Exception{
		
		UserManageUtil.checkUid(uid);
		
		Map<String,Object>[] maps = expressionBiz.getExpressionList(uid);
		
		ResultModel<Map<String,Object>[]> result = new ResultModel<Map<String,Object>[]>();
		result.setData(maps);
		
		return result.toJSON();
	}
}
