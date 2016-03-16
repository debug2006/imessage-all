package com.dianxin.imessage.web.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianxin.imessage.biz.config.IAbilityBiz;

@Controller
@RequestMapping("/book/")
public class DxSuperpowerController {

	@Autowired
	private IAbilityBiz abilityBiz;

}
