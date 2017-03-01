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

public class SelectedTreeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(SelectedTreeAction.class);
	
	private List<Object> returnList = new ArrayList<Object>();
	
	//当路径为 1,2,3;1,2这样重复的路径时，避免加载重复的 节点 ID
	private List<String> list = new ArrayList<String>() ;
	
	private void writeResponse(JSONArray jsonArray){
		HttpServletResponse response = this.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null ;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}  
		if(out!=null&&jsonArray!=null)
		out.print(jsonArray);
		if(out!=null){
			out.flush();
			out.close();
		}
	}
	
	public String getSelectedTreeData() throws Exception {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
		try {
			HttpServletRequest re = this.getRequest();
			Map para = getRequestParaMap();

			String method = (String) para.get("method");
			String value = (String) para.get("idvalue"); //需要勾选的 叶子节点ID 
			String path = (String) para.get("path");  //需要打开的 父节点 ID
			String id = (String) para.get("attrId");
			String pid = (String) para.get("attrPid");
			String name = (String) para.get("attrName");
			String isParent = (String) para.get("attrIsParent");
			String open = (String) para.get("attrOpen");
			String chkDisabled = (String) para.get("attrChkDisabled");
			String checked = (String) para.get("attrChecked");
			String icon = (String) para.get("attrIcon");
			String idValue = re.getParameter(id); // 异步加载子节点的父节点ID值
			
			if(idValue == null || "null".equals(idValue)){
				
				String params = id+":0"; //为0时查根节点,第一次进来先查一次根节点 
				
				List<Object> objList = getDataMap(params, method, ctx);
				List<Object> oldObjList = objList ; //保存根节点
				
				this.createList(objList , id ,pid , name , isParent, open , chkDisabled , checked , icon ,value);
				String[] pathArrs = path.split(";") ;
				String[] values = value.split(";") ;
				
				for(int j=0;j<pathArrs.length;j++){
					
					objList = oldObjList ; //这层循环 是要从 根节点中获取
					
					String[] paths = pathArrs[j].split(",") ;
					
					for(int i=0;i<paths.length;i++){
						
						/*if (objList.get(0) instanceof Map) { */
							params = id+":"+paths[i] ;
							objList = getDataMap(params, method, ctx);
							if(exist(paths[i])){
								this.createList(objList , id ,pid , name , isParent, open , chkDisabled , checked , icon ,values[j]);	
							}
					/*	}else{
							Object obj = (Object)objList.get((Integer.parseInt(p) - 1));
							Class cls = obj.getClass();
							String idVal = "get"+this.toUpperCaseFirstOne(id);
							Method meth1 = cls.getMethod(idVal, new Class[] {});
							Object obj1 = meth1.invoke(obj, new Object[] {});
							String nidVal =obj1.toString();
							
							params = id+":"+nidVal ;
							objList = getDataMap(params, method, ctx);
							if(exist(nidVal)){
								this.createList(objList , id ,pid , name , isParent, open , chkDisabled , checked , icon ,value);	
							}
						}*/
					}
					
				}
			}else{
				//点击没有打开的 父节点时  触发 
				String params = id+":"+idValue;
				List<Object> objList = getDataMap(params, method, ctx);
				
				this.createList(objList , id ,pid , name , isParent, open , chkDisabled , checked , icon ,"-100,-1000");
			}
			
			JSONArray jsonArr = JSONArray.fromObject(returnList);
			writeResponse(jsonArr);
			
		} catch (Exception e) {
			throw e ;
		}
		return SUCCESS;
	}

	private void createList(List<Object> objList, String id, String pid,String name, String isParent, String open, String chkDisabled,
			String checked, String icon , String dvalue) throws Exception {
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
					
					String[] values = dvalue.split(",") ;
					
					for(String value : values){
						if(value.trim().equals(newmap.get(id)+"")){
							map.put("checked", true);
							break ; 
						}else{
							map.put("checked", newmap.get(checked));
						}
					}
					
					map.put("isParent", newmap.get(isParent));
					map.put("open", true);
					map.put("chkDisabled", newmap.get(chkDisabled));
					map.put("icon", newmap.get(icon));
					
					returnList.add(map);
				}else{  
					Object obj = (Object)objList.get(i);
					Class cls = obj.getClass();
					String nnid = "";
					
					// 必要属性 start  
					if(id != null){
						String idMethodName = "get"+this.toUpperCaseFirstOne(id);
						Method meth1 = cls.getMethod(idMethodName, new Class[] {});
						Object obj1 = meth1.invoke(obj, new Object[] {});
						String nid =obj1.toString(); 
						nnid = nid ;
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
					
					// 必要属性 end
					
					
					try{
						
						map.put("open", true);
						
						if(chkDisabled != null){
							String chkDisabledMethodName = "get"+this.toUpperCaseFirstOne(chkDisabled);
							Method meth5 = cls.getMethod(chkDisabledMethodName, new Class[] {});
							Object obj5 = meth5.invoke(obj, new Object[] {});
							String nchkDisabled =obj5.toString(); 
							map.put("chkDisabled",nchkDisabled);
						}
						
						String[] values = dvalue.split(",") ;
						
						for(String value : values){
							if(value.trim().equals(nnid)){
								map.put("checked", true);
								break ; 
							}else{
								map.put("checked",false);
							}
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
	
	public boolean exist(String id){
		
		if(list.contains(id)){
			return false ;
		}else{
			list.add(id) ;
			return true ;  
		}
		
	}
}
