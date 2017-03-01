package com.linkage.rainbow.ui.views.action;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONObject;

import com.linkage.rainbow.ui.struts.BaseAction;

public class ChartAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(ChartAction.class);
	private String method;
	private String params;
	private String queryParams;
	private String seriesCode;
	
	public String getChartData() {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			List<Map<String,String>> list = getDataList(queryParams, method, ctx);
			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i).get("pressure"));
			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String,String>> getDataList(String params,String method,WebApplicationContext ctx) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		if (params != null && params.length() != 0) {
			if (params.indexOf(",") == -1) {
				map.put(params.split(":")[0], params.split(":").length > 1 ? params.split(":")[1] : "");
			} else {
				String[] paramArr = params.split(",");
				for (int i = 0; i < paramArr.length; i++) {
					map.put(paramArr[i].split(":")[0], paramArr[i].split(":").length > 1 ? paramArr[i].split(":")[1] : "");
				}
			}
		}
		Class cls = ctx.getBean(method.split("\\.")[0]).getClass();
		String getMethodName = method.split("\\.")[1];
		Method meth;
		Object obj;
		if (map.size() > 0) {
			meth = cls.getMethod(getMethodName, new Class[] {map.getClass()});
			obj = meth.invoke(ctx.getBean(method.split("\\.")[0]), new Object[] {map});
		} else {
			meth = cls.getMethod(getMethodName, new Class[] {});
			obj = meth.invoke(ctx.getBean(method.split("\\.")[0]), new Object[] {});
		}
		return (List<Map<String, String>>) obj;
	}
	
	private void writeResponse(JSONObject jsonObject) throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();  
		out.print(jsonObject);
		out.flush();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

}
