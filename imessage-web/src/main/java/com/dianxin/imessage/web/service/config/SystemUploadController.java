package com.dianxin.imessage.web.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dianxin.imessage.biz.config.IFileUpload;
import com.dianxin.imessage.common.util.ResultModel;

/**
 * 
 * 上传图片功能
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping(value = "/config/")
public class SystemUploadController {

	private Logger logger = LoggerFactory.getLogger(SystemUploadController.class);

	@Autowired
	private IFileUpload fileUpload;

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public @ResponseBody String upload(@RequestParam("photo") MultipartFile file) {

		ResultModel<String> result = new ResultModel<String>();

		String filePath = fileUpload.uploadFile(file);
		logger.info("filePath:" + filePath);

		result.setData(filePath);

		return result.toJSON();
	}

}
