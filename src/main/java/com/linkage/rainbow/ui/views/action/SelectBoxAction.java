package com.linkage.rainbow.ui.views.action;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.struts.BaseAction;
/**
 * SelectBoxAction<br>
 * 异步load下拉框可选值
 * chenwei
 * @version 1.0
 */
public class SelectBoxAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(SelectBoxAction.class);
	public String getSelectBoxData(){
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			Map para = getRequestParaMap();
			String method = (String) para.get("method");
			String keyStr = (String) para.get("listKey");
			String valStr = (String) para.get("listValue");
			
			para.remove("method");
			List<Object> returnList = new ArrayList<Object>();
			List<Object> objList = getDataMap(para, method, ctx);
			if (objList!=null && objList.size()>0){
				Map<String,Object> map = null;
				for (int i=0;i<objList.size();i++){
					 map = new HashMap<String,Object>();
					if (objList.get(0) instanceof Map) { //Map结构原数据
						Map newmap = (Map)objList.get(i);
						map.put("listKey", newmap.get(keyStr));
						map.put("listValue", newmap.get(valStr));
						returnList.add(map);
					}else{  //javabean结构的原始数据
						Object obj = (Object)objList.get(i);
						Class cls = obj.getClass();
						String keyMethodName = "get"+this.toUpperCaseFirstOne(keyStr);
						String valMethodName = "get"+this.toUpperCaseFirstOne(valStr);
						Method meth1 = cls.getMethod(keyMethodName, new Class[] {});
						Object obj1 = meth1.invoke(obj, new Object[] {});
						Method meth2 = cls.getMethod(valMethodName, new Class[] {});
						Object obj2 = meth2.invoke(obj, new Object[] {});
						String key =obj1.toString(); 
						String value =obj2.toString();
						if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
							map.put("listKey", key); 
							map.put("listValue", value);
							returnList.add(map);
						}
					}
				}
			}
			JSONArray jsonArr = JSONArray.fromObject(returnList);
			returnList = null;
			StringBuffer sb = new StringBuffer();
	    	sb.append("{\"rows\":").append(jsonArr.toString()).append("}");
			
			HttpServletResponse response = this.getResponse();
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setCharacterEncoding("UTF-8");
			PrintWriter  write = response.getWriter();
			write.println(sb.toString());
			write.close();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> getDataMap(Map para,String method,WebApplicationContext ctx) throws Exception {
		String params = (String)para.get("params");
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
		if (para != null && para.size() > 0) {
			meth = cls.getMethod(getMethodName, new Class[] {para.getClass()});
			obj = meth.invoke(ctx.getBean(method.split("\\.")[0]), new Object[] {map});
		} else {
			meth = cls.getMethod(getMethodName, new Class[] {});
			obj = meth.invoke(ctx.getBean(method.split("\\.")[0]), new Object[] {});
		}
		return (List<Object>) obj;
	}
	
	public  String toUpperCaseFirstOne(String s){        
		if(Character.isUpperCase(s.charAt(0)))            
			return s;        
		else            
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();    
	}
}
