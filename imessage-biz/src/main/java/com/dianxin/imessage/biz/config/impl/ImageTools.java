package com.dianxin.imessage.biz.config.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.config.IImageTools;
import com.dianxin.imessage.common.util.DateUtil;
import com.dianxin.imessage.common.util.PropertiesUtil;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * 图片处理工具<br>
 * 图片的压缩，缩放等处理
 *
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class ImageTools implements IImageTools {

	@Override
	public File compImageSize(InputStream inputStream, String fileName, String newFileNamePrefix, int width, int high) {

		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		String ctxPath = getCtxPath(fileName);

		// 创建文件夹
		File ctxPathfile = new File(ctxPath);
		if (!ctxPathfile.exists()) {
			ctxPathfile.mkdirs();
		}

		String newFileName = newFileNamePrefix + "_" + high + "x" + high + "." + fileExt;

		File uploadFile = new File(ctxPath + newFileName);

		try {
			// keepAspectRatio(false) 默认是按照比例缩放的
			Thumbnails.of(inputStream).forceSize(width, high).toFile(uploadFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uploadFile;
	}

	private String getCtxPath(String fileName) {
		final String DX_APP_PRTPERTIES_PATH = PropertiesUtil.DX_APP_PRTPERTIES_PATH;

		// "D:/image" + "/";
		String ctxPath = PropertiesUtil.getPropertiesValue(DX_APP_PRTPERTIES_PATH, "image.tmp.path");

		String currDate = DateUtil.DateToString(new Date(), DateUtil.yyyyMMdd);

		ctxPath += File.separator + currDate + File.separator;
		return ctxPath;
	}

	public String getNewFileNamePrefix() {

		// 重命名文件
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileNamePrefix = df.format(new Date()) + "_" + new Random().nextInt(1000);

		return newFileNamePrefix;
	}

	@Override
	public File getImageFile(DiskFileItem diskFileItem) {

		File file = diskFileItem.getStoreLocation();

		File newFile = null;

		String name = file.getName();
		if (name.endsWith("tmp")) {

			newFile = new File(file.getParent() + "/" + name.substring(0, name.indexOf(".tmp")) + ".jpg");

			try {
				FileUtils.copyFile(file, newFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newFile;
	}

	@Override
	public File compImage(InputStream inputStream, String fileName, String newFileNamePrefix) {
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		String ctxPath = getCtxPath(fileName);

		// 创建文件夹
		File ctxPathfile = new File(ctxPath);
		if (!ctxPathfile.exists()) {
			ctxPathfile.mkdirs();
		}

		String newFileName = newFileNamePrefix + "." + fileExt;

		File uploadFile = new File(ctxPath + newFileName);

		try {

			Thumbnails.of(inputStream).scale(1f).outputQuality(0.25f).outputFormat("jpg").toFile(uploadFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uploadFile;
	}

}
