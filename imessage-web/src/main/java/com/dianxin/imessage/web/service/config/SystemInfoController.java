package com.dianxin.imessage.web.service.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.common.util.PropertiesUtil;
import com.dianxin.imessage.common.util.ResultModel;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

@Controller
@RequestMapping(value = "/config/")
public class SystemInfoController {

	/**
	 * 
	 * Functions and Introduction introduction.html
	 * 
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@RequestMapping(value = "displayPage/{pageCode}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String displayPage(@PathVariable String pageCode) {
		ResultModel<String> result = new ResultModel<String>();

		String pageValue = PropertiesUtil.getPropertiesValue(PropertiesUtil.DISPLAYHTML_CONF_PATH, pageCode);

		result.setData(pageValue);

		return result.toJSON();
	}

}
