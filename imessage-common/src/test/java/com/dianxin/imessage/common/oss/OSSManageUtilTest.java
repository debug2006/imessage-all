package com.dianxin.imessage.common.oss;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OSSManageUtilTest {

	File file = null;
	OSSConfigure ossConfigure = null;

	@Before
	public void setUp() throws Exception {
		file = new File("D:/pic/pic1.jpg");
		ossConfigure = OSSConfigure.singleton();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Test() {

		//String showAddress = OSSManageUtil.uploadFile(ossConfigure, file, "picture");
		//System.out.println("showAddress : " + showAddress);

		OSSManageUtil.deleteFile(ossConfigure, "picture/pic1.jpg");
	}

}
