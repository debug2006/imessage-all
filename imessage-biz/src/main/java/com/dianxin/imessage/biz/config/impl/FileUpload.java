package com.dianxin.imessage.biz.config.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dianxin.imessage.biz.config.IFileUpload;
import com.dianxin.imessage.biz.config.IImageTools;
import com.dianxin.imessage.common.oss.OSSConfigure;
import com.dianxin.imessage.common.oss.OSSManageUtil;
import com.dianxin.imessage.common.util.DateUtil;

/**
 * 
 * 〈上传处理〉
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class FileUpload implements IFileUpload {

	private Logger logger = LoggerFactory.getLogger(FileUpload.class);

	@Autowired
	private IImageTools imageTools;

	/***
	 * 上传处理
	 */
	@Override
	public String uploadFile(MultipartFile multipartFile) {

		File uploadcompImage = null;

		File uploadZoomImage = null;

		String newFileNamePrefix = imageTools.getNewFileNamePrefix();

		try {
			// 图片尺寸不变，压缩图片文件的体积
			uploadcompImage = imageTools.compImage(multipartFile.getInputStream(), multipartFile.getOriginalFilename(),
					newFileNamePrefix);

			// 压缩图片指定的大小
			// TODO 350 * 350 可配置
			uploadZoomImage = imageTools.compImageSize(multipartFile.getInputStream(),
					multipartFile.getOriginalFilename(), newFileNamePrefix, 350, 350);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OSSConfigure ossConfigure = OSSConfigure.singleton();

		String currDate = DateUtil.DateToString(new Date(), DateUtil.yyyyMMdd);

		String showPath = OSSManageUtil.uploadFile(ossConfigure, uploadcompImage, "picture" + "/" + currDate);

		OSSManageUtil.uploadFile(ossConfigure, uploadZoomImage, "picture" + "/" + currDate);

		logger.info("showPath {}", showPath);

		return showPath;
	}

}
