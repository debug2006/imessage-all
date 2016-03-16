package com.dianxin.imessage.biz.config;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 〈上传处理〉
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IFileUpload {

	String uploadFile(MultipartFile file);

}
