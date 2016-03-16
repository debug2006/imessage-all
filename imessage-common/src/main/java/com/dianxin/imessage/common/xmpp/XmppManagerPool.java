package com.dianxin.imessage.common.xmpp;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianxin.imessage.common.cache.MemcachedFactory;

/**
 * 
 * @author b_fatty
 * 
 * Date 2016/1/26
 * 
 * XMPP连接池
 * 用于保存用户登录以后对应smack连接对象的保存
 * 保存时间1h
 *
 */
public class XmppManagerPool extends ConcurrentHashMap<Integer, XmppManager> implements InitializingBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(XmppManagerPool.class);

	private static final long TIME_OUT = 60*60*1000;		//过期时间设置为1h
	
	@Autowired
	private MemcachedFactory cacheFactory;
	
	@Override
	public XmppManager put(Integer key, XmppManager value) {
		value.loadPoolDate = new Date();
		return super.put(key, value);
	}
	
	@Override
	public XmppManager get(Object key) {
		XmppManager manager = super.get(key);
		/**
		 * 每次取出XmppManager时刷新加载时间
		 */
		if(null != manager){
			manager.loadPoolDate = new Date();
		}
		return manager;
	}
	
	/**
	 * 装载XmppManager到池中
	 * @param uid
	 * @param password
	 */
	public XmppManager loadXmppManager(Integer uid,String password){
		
		log.info("loading {} xmpp manager for pool..." , uid);
		
		XmppManager manager = XmppManagerFactory.createrXmppManager(uid, password,cacheFactory);
		
		put(uid, manager);
		
		return manager;
	}
	
	/**
	 * 清理任务
	 */
	private void clean(){
		
		TimerTask task = new TimerTask(){

			@Override
			public void run() {

				Date date = new Date();
				int count = 0;
				
				for(Map.Entry<Integer,XmppManager> entry : entrySet()){
					XmppManager manager = entry.getValue();
					if(manager == null ){	//值为空  直接清理掉
						remove(entry.getKey());
						count ++ ;
						return;
					}
					Date loadPoolDate = manager.loadPoolDate;
					if((date.getTime() - loadPoolDate.getTime()) > TIME_OUT){
						manager.destroy();		//销毁manager对象
						remove(entry.getKey());	//从池中清理
						count ++ ;
					}
				}
				
				log.info("Clean up {} out of XmppManager", count);
				
			}
			
		};
		
		Timer timer = new Timer();
		timer.schedule(task, TIME_OUT, TIME_OUT);	//1h后开始执行清理任务，并每隔1h执行一次
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		
		log.info("xmpp connect pool loaded");
		
		//连接池被spring加载以后开始执行clean任务
		clean();
	}
}
