package com.dianxin.imessage.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/4
 * Time: 9:32
 */
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringUtil.applicationContext = context;
    }
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}
