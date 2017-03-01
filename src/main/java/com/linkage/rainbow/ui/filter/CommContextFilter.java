package com.linkage.rainbow.ui.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.linkage.rainbow.util.context.ContextHolder;
import com.linkage.rainbow.util.context.ContextImpl;

/**
 * CommContextFilter<br>
 * 将会话期信息设置到线程变量过滤器
 * <p>
 * @version 1.0
 * <hr>
 */
public class CommContextFilter implements Filter{
	protected FilterConfig filterConfig = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
    }
	
	/**
	 * 进行过滤操作
	 * @param request
	 * @param response
	 * @param chain
	 */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpSession session = null;
		
		if (request instanceof HttpServletRequest) {
///*********取消采用ContextUtil的方法设置SQL调试信息,改为采用通过struts2的ServletActionContext来取得会话期信息**************/
//			//取得会话
//			session = ((HttpServletRequest) request).getSession();
//
//			//将会话存到ContextHolder，以便传到aop
//			if(session!=null){
//				
//				//SQL调试设置到会话期
//				Object sqlDebugSession=session.getAttribute(SqlDebugUtil.SQL_DEBUG_SESSION); 
//				if(sqlDebugSession!=null){
//					ContextUtil.put(SqlDebugUtil.SQL_DEBUG_SESSION,sqlDebugSession);
//				}else{
//					sqlDebugSession = SqlDebugUtil.getSqlDebugSession();
//					session.setAttribute(SqlDebugUtil.SQL_DEBUG_SESSION,sqlDebugSession);
//					ContextUtil.put(SqlDebugUtil.SQL_DEBUG_SESSION,sqlDebugSession);
//				} 
//			}
			//线程变量重新初始化
			ContextHolder.setContext(new ContextImpl());
		}     
        chain.doFilter(request, response);
    }
    

    
    public void destroy() {
    }
}
