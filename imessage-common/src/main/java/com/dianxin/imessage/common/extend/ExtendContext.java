package com.dianxin.imessage.common.extend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author b_fatty
 * 
 * 自定义一个上下文类
 * 每次请求进来将通过ExtendContextFilter过滤器将HttpServletRequest HttpServletResponse HttpSession 封装到该类中
 *
 * (spring aop中获取不到HttpServletResponse)
 * 
 * Date 2016/1/20
 */
public class ExtendContext {

	private static ThreadLocal<HttpServletRequest> requestLocal=new ThreadLocal<HttpServletRequest>();  
    private static ThreadLocal<HttpServletResponse> responseLocal=new ThreadLocal<HttpServletResponse>();  
      
    public static HttpServletRequest getRequest(){  
        return requestLocal.get();  
    }  
      
    public static void setRequest(HttpServletRequest request){  
        requestLocal.set(request);  
    }  
      
    public static HttpServletResponse getResponse(){  
        return responseLocal.get();  
    }  
      
    public static void setResponse(HttpServletResponse response){  
        responseLocal.set(response);  
    }  
      
    public static HttpSession getSession(){  
        return (HttpSession)(getRequest()).getSession();  
    }  
}
