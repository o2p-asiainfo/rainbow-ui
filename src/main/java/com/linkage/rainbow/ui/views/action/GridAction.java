package com.linkage.rainbow.ui.views.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.components.gridEasy.JsonData;
import com.linkage.rainbow.ui.struts.BaseAction;

public class GridAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(GridAction.class);
	private void writeResponse(JSONObject jsonObject) throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();  
		out.print(jsonObject);
		out.flush();
	}

	
	@SuppressWarnings("unchecked")
	public String getGridData() {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			Map para = getRequestParaMap();
			String method = (String) para.get("method");
			para.remove("method");
			Map<String,Object> map = getDataMap(para, method, ctx);
			JSONObject jsonObject = new JSONObject();
			List<Object> dataList = (List<Object>) map.get("dataList");
			jsonObject.put("total", map.get("total"));
			jsonObject.put("rows", dataList);
			writeResponse(jsonObject);
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public void getGridEasyData() {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			Map para = getRequestParaMap();
			String method = (String) para.get("method");
			para.remove("method");
			Map<String,Object> map = getDataMap(para, method, ctx);
			List<Object> dataList = (List<Object>) map.get("dataList");
			JsonData jsonData = new JsonData();
			jsonData.setTotal(Integer.parseInt(map.get("total").toString()));
			jsonData.setRows(dataList);
			JSONObject jsonObject = JSONObject.fromObject(jsonData);
			writeResponse(jsonObject);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getCause());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getGridTreeData() {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			Map para = getRequestParaMap();
			String method = (String) para.get("method");
			para.remove("method");
			Map<String,Object> map = getDataMap(para, method, ctx);
			JsonData jsonData = new JsonData();
			
			if(null!=map&&null!=map.get("total")){
				List<Object> dataList = (List<Object>) map.get("dataList");			
				jsonData.setTotal(Integer.parseInt(map.get("total").toString()));
				jsonData.setRows(dataList);
				JSONObject jsonObject = JSONObject.fromObject(jsonData);
				writeResponse(jsonObject);
			}else{
				if(map.get("dataList")!=null)
				getResponse().getWriter().print(map.get("dataList").toString());
				else
					getResponse().getWriter().print("no data output!");
			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}finally{
			try {
				getResponse().getWriter().close();
			} catch (IOException e) {
				log.error(e.getStackTrace());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> getDataMap(Map para,String method,WebApplicationContext ctx) throws Exception {
		Class cls = ctx.getBean(method.split("\\.")[0]).getClass();
		String getMethodName = method.split("\\.")[1];
		Method meth;
		Object obj;
		if (para != null && para.size() > 0) {
			meth = cls.getMethod(getMethodName, new Class[] {Map.class});
			obj = meth.invoke(ctx.getBean(method.split("\\.")[0]), new Object[] {para});
		} else {
			meth = cls.getMethod(getMethodName, new Class[] {});
			obj = meth.invoke(ctx.getBean(method.split("\\.")[0]), new Object[] {});
		}
		return (Map<String,Object>) obj;
	}

}
