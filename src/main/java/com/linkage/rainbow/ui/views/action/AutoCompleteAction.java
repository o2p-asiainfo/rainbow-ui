package com.linkage.rainbow.ui.views.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.struts.BaseAction;
import com.linkage.rainbow.util.StringUtil;

public class AutoCompleteAction extends BaseAction{
	
	private static Log log = LogFactory.getLog(AutoCompleteAction.class);

	public String execute()throws UnsupportedEncodingException {
		// System.out.println("mmmmmmmmmmmmmmmmmmmmmm");
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String method=StringUtil.valueOf(request.getParameter("method"));
		String sysencoding=org.apache.struts2.config.DefaultSettings.get("struts.i18n.encoding");
		request.setCharacterEncoding(sysencoding);
//		String params=new String(StringUtil.valueOf(request.getParameter("params")).getBytes("GBK"));
//		String key=new String(StringUtil.valueOf(request.getParameter("key")).getBytes("GBK"));
		String params = request.getParameter("params");
		if(params != null && !params.equals("")){
			params = java.net.URLDecoder.decode(request.getParameter("params"), "utf-8");
		}
		
		

		//参数分解完毕
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		List list=null;
		try{
			list=this.doHashMapList(params, method, ctx);
		}catch(Exception ex){
			try {
				list=this.doMapList(params, method, ctx);
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
		}

		
		try {
			
			
			response.setCharacterEncoding(sysencoding);
			response.setContentType("text/xml; charset=\""+sysencoding+"\"");
			response.setHeader("Cache-Control", "no-cache");
            
			//System.out.println(transToJson(list));
			response.getWriter().println(transToJson(list));
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}

	return null;
	}


	/**
	 * 动态调用spring的bean获取List,参数为HashMap
	 * @param params
	 * @param methods
	 * @param ctx
	 * @return List
	 * @throws Exception
	 */
	public  List doHashMapList(String params,String methods,WebApplicationContext ctx) throws Exception{		
		Object retobj=null;
		try{
			HashMap map = new HashMap();
			if(params.length()!=0){
				if(params.indexOf(";")==-1){
					map.put(params.split(":")[0], params.split(":").length>1?(params.split(":")[1]):"");
				}else{
					String[] allparams=params.split(";");
					for(int i=0;i<allparams.length;i++){
						if(allparams[i].length()!=0){
							map.put(allparams[i].split(":")[0], allparams[i].split(":").length>1?(allparams[i].split(":")[1]):"");
						}
					}
				}
			} 
		
			Class cls =ctx.getBean(methods.split("\\.")[0]).getClass();// Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
			Class partypes[] = new Class[1];
			partypes[0] = map.getClass();
			Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
			Object arglist[] = new Object[1];	
			arglist[0] = map;
			retobj = meth.invoke(ctx.getBean(methods.split("\\.")[0]), arglist);
		}catch(Exception ex){
			log.error(ex.getStackTrace());
			}		
		return (List)retobj;
	}
	
	/**
	 * 动态调用spring的bean获取List,参数为HashMap
	 * @param params
	 * @param methods
	 * @param ctx
	 * @return List
	 * @throws Exception
	 */
	public  List doMapList(String params,String methods,WebApplicationContext ctx) throws Exception{		
		Object retobj=null;
		try{
			Map map = new HashMap();
			if(params.length()!=0){
				if(params.indexOf(";")==-1){
					map.put(params.split(":")[0], params.split(":").length>1?(params.split(":")[1]):"");
				}else{
					String[] allparams=params.split(";");
					for(int i=0;i<allparams.length;i++){
						if(allparams[i].length()!=0){
							map.put(allparams[i].split(":")[0], allparams[i].split(":").length>1?(allparams[i].split(":")[1]):"");
						}
					}
				}
			}
			Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();//Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
			Class partypes[] = new Class[1];
			partypes[0] = map.getClass();
			Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
			Object arglist[] = new Object[1];	
			arglist[0] = map;
			retobj = meth.invoke(ctx.getBean(methods.split("\\.")[0]), arglist);
		}catch(Exception ex){
			log.error(ex.getStackTrace());
			}		
		return (List)retobj;
	}
	

	
	/**
	 * 判断节点是否在默认展开的节点里面
	 * @param openRoad
	 * @param node
	 * @return boolean
	 */
	public  boolean toOpen(String openRoad,String node){
		boolean flag=false;
		if(openRoad.equals("")){
		}else{
			if(openRoad.lastIndexOf(",")!=openRoad.length()-1){
				openRoad+=",";
			}
			openRoad=","+openRoad;
			node=","+node+",";
			if(openRoad.indexOf(node)>-1){
				flag=true;
			}else{
				flag=false;
			}
		}
		return flag;
	}
	
	/**
	 * 将查询的list转换为json格式的字符串
	 * 
	 */
	
	public  String transToJson(List list){
		
//		if(list != null &&list.size()>0){
//			
//			JSONArray jsonArray = null;
////			Map mapTree= new HashMap();
//		    for(int i =0;i<list.size();i++){
//		    	Object obj = list.get(i);
//		    	if(obj instanceof Map){
//		    		Map map =(HashMap)list.get(i);
//			    	String hava_sub = (String)map.get("hava_sub");
//			    	if(hava_sub.equals("0") || hava_sub.equals("")){
//			    		map.put("state", "open");
//			    	}else{
//			    		map.put("state", "closed");
//			    	}
//			    	jsonArray = JSONArray.fromObject(list);
//		    		
//		    	}else {
//		    		List new_list = new ArrayList();
//		    		Object id =null;
//		    		Object text=null;
//		    		Object hava_sub=null;
//					Class argsClass[] = new Class[0];
//					java.lang.reflect.Method method = null;
//					Object[] parameters = new Object[0];
//					Map map= new HashMap();
//					try {
//						method = obj.getClass().getMethod("getId", argsClass);
//						id = method.invoke(obj, parameters);
//			
//						method = obj.getClass().getMethod("getText", argsClass);
//						text = method.invoke(obj, parameters);
//						
//						method = obj.getClass().getMethod("getHave_Sub", argsClass);
//						hava_sub = method.invoke(obj, parameters);
//						
//						map.put("id", id.toString());
//						map.put("text", text.toString());
//					
//						if(hava_sub.equals("0") || hava_sub.equals("") || hava_sub == null){
//				    		map.put("state", "open");
//				    	}else{
//				    		map.put("state", "closed");
//				    	}
//						new_list.add(map);
//						
//						jsonArray = JSONArray.fromObject(new_list);
//					} catch (Exception e) {
//					}
//		    	}
//		    	
//		    	
//		    }
//	
//			
//	
////			List treeList = null;
//			//获取父节点
////			String f_node= (String)((HashMap)list.get(0)).get("id");
////
////			mapTree.put("id", id);
////			mapTree.put("text", "部门根节点");
////			mapTree.put("iconCls", "icon-ok");
////			mapTree.put("children", list);
//		
//
//
//		        System.out.println(jsonArray.toString());   
//		        return jsonArray.toString();
//			
//		}

		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
		
	}
}
