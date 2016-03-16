package com.dianxin.imessage.biz.config;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;

/**
 * 
 * 图片处理工具<br>
 * 图片的压缩，缩放等处理
 *
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IImageTools {

	/**
	 * 
	 * 功能描述: 按指定宽，高，对图片进行缩放<br>
	 * 〈功能详细描述〉
	 *
	 * @param inputStream
	 * @param width
	 *            宽
	 * @param high
	 *            高
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	File compImageSize(InputStream inputStream, String fileName, String newFileNamePrefix, int width, int high);

	/**
	 * 
	 * 图片尺寸不变，压缩图片文件大小
	 * 
	 * @param inputStream
	 * @param fileName
	 * @param newFileNamePrefix
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	File compImage(InputStream inputStream, String fileName, String newFileNamePrefix);

	File getImageFile(DiskFileItem diskFileItem);

	String getNewFileNamePrefix();

}
