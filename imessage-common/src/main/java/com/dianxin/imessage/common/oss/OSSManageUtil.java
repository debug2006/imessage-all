package com.dianxin.imessage.common.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * 
 * 〈对OSS服务器进行上传/删除等的处理 〉 〈功能详细描述〉
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OSSManageUtil {

	private static Logger log = LoggerFactory.getLogger(OSSManageUtil.class);

	/**
	 * 
	 * 〈上传OSS服务器文件〉 〈功能详细描述〉
	 * 
	 * @param ossConfigure
	 * @param file
	 * @param remotePath
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public static String uploadFile(OSSConfigure ossConfigure, File file, String remotePath) {

		// 获取OSS client
		OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(),
				ossConfigure.getAccessKeySecret());

		String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/") + "/";
		try {
			InputStream fileContent = new FileInputStream(file);

			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(fileContent.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(contentType(file.getName().substring(file.getName().lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + file.getName());
			// 上传文件
			ossClient.putObject(ossConfigure.getBucketName(), remoteFilePath + file.getName(), fileContent,
					objectMetadata);
			if (file.exists()) {
				file.delete();
			}
			log.debug(ossConfigure.getAccessUrl() + "/" + remoteFilePath + file.getName());
		} catch (FileNotFoundException e) {
			log.error("uploadFile is error", e);
		} catch (IOException e) {
			log.error("uploadFile is error", e);
		} finally {
			ossClient.shutdown();
			if (file.exists()) {
				file.delete();
			}
		}

		return ossConfigure.getAccessUrl() + "/" + remoteFilePath + file.getName();
	}

	/**
	 * 
	 * 〈根据key删除OSS服务器上的文件〉 〈功能详细描述〉
	 * 
	 * @param ossConfigure
	 * @param filePath
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public static void deleteFile(OSSConfigure ossConfigure, String filePath) {
		OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(),
				ossConfigure.getAccessKeySecret());
		ossClient.deleteObject(ossConfigure.getBucketName(), filePath);
		// ossClient.shutdown();
	}

	/**
	 * 
	 * 〈判断OSS服务文件上传时文件的contentType〉 〈功能详细描述〉
	 * 
	 * @param FilenameExtension
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public static String contentType(String FilenameExtension) {
		if (FilenameExtension.endsWith("BMP") || FilenameExtension.endsWith("bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.endsWith("GIF") || FilenameExtension.endsWith("gif")) {
			return "image/gif";
		}
		if (FilenameExtension.endsWith("JPEG") || FilenameExtension.endsWith("jpeg")
				|| FilenameExtension.endsWith("JPG") || FilenameExtension.endsWith("jpg")
				|| FilenameExtension.endsWith("PNG") || FilenameExtension.endsWith("png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.endsWith("HTML") || FilenameExtension.endsWith("html")) {
			return "text/html";
		}
		if (FilenameExtension.endsWith("TXT") || FilenameExtension.endsWith("txt")) {
			return "text/plain";
		}
		if (FilenameExtension.endsWith("VSD") || FilenameExtension.endsWith("vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.endsWith("PPTX") || FilenameExtension.endsWith("pptx")
				|| FilenameExtension.endsWith("PPT") || FilenameExtension.endsWith("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.endsWith("DOCX") || FilenameExtension.endsWith("docx")
				|| FilenameExtension.endsWith("DOC") || FilenameExtension.endsWith("doc")) {
			return "application/msword";
		}
		if (FilenameExtension.endsWith("XML") || FilenameExtension.endsWith("xml")) {
			return "text/xml";
		}
		return "text/html";
	}
}