package com.linkage.rainbow.ui.views.action;

import java.io.IOException;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.struts.BaseAction;
import com.linkage.rainbow.util.StringUtil;

public class ComboboxAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ComboboxAction.class);
//	JSONArray jsonArray;
    private String results;
	
	public String execute() throws UnsupportedEncodingException {
		
//		System.out.println("我已经进入COMBOBOX树类");
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sysencoding=org.apache.struts2.config.DefaultSettings.get("struts.i18n.encoding");
		request.setCharacterEncoding(sysencoding);
		String method=StringUtil.valueOf(request.getParameter("method"));//调用的方法
		String params=StringUtil.valueOf(request.getParameter("params"));//参数
	
		


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
	 * 调用方法的参数替换
	 * @param param
	 * @param key
	 * @param value
	 * @return String
	 */
	public  String replacaParam(String param,String key,String value){
		String str="";
		String []temp=param.split(";");
		for(int i=0;i<temp.length;i++){
//			System.out.println("str1============"+temp[i].split(":")[0]);
//			System.out.println("str2============"+key);
			if(temp[i].split(":")[0].equals(key)==true){
				str=str+temp[i].split(":")[0]+":"+value+";";
//				System.out.println("str============"+str);
			}else{
				str=str+temp[i]+";";
			}
		}
		return str;
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
		JSONArray jsonArray = null;
		
		if(list != null &&list.size()>0){
			
			jsonArray = JSONArray.fromObject(list);
		    	
		    }
		        return jsonArray==null?"":jsonArray.toString();
	}
}
