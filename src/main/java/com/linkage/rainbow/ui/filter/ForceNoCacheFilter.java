package com.linkage.rainbow.ui.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>用于的使 Browser 不缓存页面的过滤器</p>
 *
 * @version 	0.0.1
 */
public class ForceNoCacheFilter implements Filter{
	/**
	 * 过滤器使页面不缓存
	 * @param request
	 * @param response
	 * @param filterChain
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
		((HttpServletResponse) response).setHeader("Pragma", "no-cache");
		((HttpServletResponse) response).setDateHeader("Expires", -1);
		filterChain.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
