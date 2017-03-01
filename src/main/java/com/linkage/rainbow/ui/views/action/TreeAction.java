package com.linkage.rainbow.ui.views.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.struts.BaseAction;

public class TreeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(TreeAction.class);
	
	private void writeResponse(JSONArray jsonArray){
		HttpServletResponse response = this.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null ;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		if(out!=null&&jsonArray!=null)
		out.print(jsonArray);
		if(out!=null){
			out.flush();
			out.close();
		}
	}
	
	public String getTreeData() throws Exception {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			HttpServletRequest re = this.getRequest();
			Map para = getRequestParaMap();

			String method = (String) para.get("method");
			//String params = (String) para.get("params");
			String id = (String) para.get("attrId");
			String pid = (String) para.get("attrPid");
			String name = (String) para.get("attrName");
			String isParent = (String) para.get("attrIsParent");
			
			String open = (String) para.get("attrOpen");
			String chkDisabled = (String) para.get("attrChkDisabled");
			String checked = (String) para.get("attrChecked");
			String icon = (String) para.get("attrIcon");
			
			String onlyLeafCheck = (String) para.get("onlyLeafCheck");
			
			String idValue = re.getParameter(id); // 异步加载子节点的父节点ID值
			String params = "";
			if(idValue == null || "null".equals(idValue)){
				params = id+":0"; //为0时查根节点
			}else{
				params = id+":"+idValue;
			}
			
			//--------传入前台页面参数值
			if(null!=para.get("params")){
				params += "," +  para.get("params");
			}
			
			List<Object> returnList = new ArrayList<Object>();
			List<Object> objList = getDataMap(params, method, ctx);
			if (objList!=null && objList.size()>0){
				Map<String,Object> map = null; 	
				for (int i=0;i<objList.size();i++){
					 map = new HashMap<String,Object>();
					if (objList.get(0) instanceof Map) { 
						Map newmap = (Map)objList.get(i);
						
						if("null".equals(String.valueOf(newmap.get(id))) || "null".equals(String.valueOf(newmap.get(pid))) || "null".equals(String.valueOf(newmap.get(name))) || "null".equals(String.valueOf(newmap.get(isParent)))){
							throw new Exception() ;
						}
						map.put("id", newmap.get(id));
						map.put("pId", newmap.get(pid));
						map.put("name", newmap.get(name));
						map.put("isParent", newmap.get(isParent));
						
						map.put("open", newmap.get(open));
						map.put("chkDisabled", newmap.get(chkDisabled));
						map.put("checked", newmap.get(checked));
						map.put("icon", newmap.get(icon));
						
						//增加只选中叶子节点属性
						map.put("nocheck", newmap.get(onlyLeafCheck));
						returnList.add(map);
					}else{  
						Object obj = (Object)objList.get(i);
						Class cls = obj.getClass();
						
						//必要属性  start
						if(id != null){
							String idMethodName = "get"+this.toUpperCaseFirstOne(id);
							Method meth1 = cls.getMethod(idMethodName, new Class[] {});
							Object obj1 = meth1.invoke(obj, new Object[] {});
							String nid =obj1.toString(); 
							map.put("id", nid);
						}
						
						if(pid != null){
							String pidMethodName = "get"+this.toUpperCaseFirstOne(pid);
							Method meth2 = cls.getMethod(pidMethodName, new Class[] {});
							Object obj2 = meth2.invoke(obj, new Object[] {});
							String npid =obj2.toString();
							map.put("pId", npid);
						}
						
						if(name != null){
							String nameMethodName = "get"+this.toUpperCaseFirstOne(name);
							Method meth3 = cls.getMethod(nameMethodName, new Class[] {});
							Object obj3 = meth3.invoke(obj, new Object[] {});
							String nname =obj3.toString(); 
							map.put("name", nname);
						}
						
						if(isParent != null){
							String isParentMethodName = "get"+this.toUpperCaseFirstOne(isParent);
							Method meth7 = cls.getMethod(isParentMethodName, new Class[] {});
							Object obj7 = meth7.invoke(obj, new Object[] {});
							String nisParentMethod =obj7.toString();
							map.put("isParent",nisParentMethod);
						}
						
						//必要属性  end
						
						
						try{
							if(open != null){
								String openMethodName = "get"+this.toUpperCaseFirstOne(open);
								Method meth4 = cls.getMethod(openMethodName, new Class[] {});
								Object obj4 = meth4.invoke(obj, new Object[] {});
								String nopen =obj4.toString();
								map.put("open", nopen);
							}
							
							if(chkDisabled != null){
								String chkDisabledMethodName = "get"+this.toUpperCaseFirstOne(chkDisabled);
								Method meth5 = cls.getMethod(chkDisabledMethodName, new Class[] {});
								Object obj5 = meth5.invoke(obj, new Object[] {});
								String nchkDisabled =obj5.toString(); 
								map.put("chkDisabled",nchkDisabled);
							}
							
							if(checked != null){
								String checkedMethodName = "get"+this.toUpperCaseFirstOne(checked);
								Method meth6 = cls.getMethod(checkedMethodName, new Class[] {});
								Object obj6 = meth6.invoke(obj, new Object[] {});
								String ncheckedMethod =obj6.toString();
								map.put("checked",ncheckedMethod);
							}
							
							if(icon != null){
								String iconMethodName = "get"+this.toUpperCaseFirstOne(icon);
								Method meth8 = cls.getMethod(iconMethodName, new Class[] {});
								Object obj8 = meth8.invoke(obj, new Object[] {});
								String niconMethod =obj8.toString();
								map.put("icon",niconMethod);
							}
						}catch (Exception e) {
							//记录错误信息 
						}
						
						returnList.add(map);
					}
				}
			}
			JSONArray jsonArr = JSONArray.fromObject(returnList);
			writeResponse(jsonArr);
			
		} catch (Exception e) {
//			throw e ;
			log.error(e.getStackTrace());
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private List<Object> getDataMap(String params,String method,WebApplicationContext ctx) throws Exception {
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
		if (map != null && map.size() > 0) {
			meth = cls.getMethod(getMethodName, new Class[] {map.getClass()});
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
