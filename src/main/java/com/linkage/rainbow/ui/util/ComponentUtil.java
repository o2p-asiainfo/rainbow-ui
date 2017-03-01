package com.linkage.rainbow.ui.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.linkage.rainbow.util.config.DefaultSettings;





/**
 * struts2组件相关工具类<br>
 * <p>
 * @version 1.0
 * @author 陈亮 2009-4-1
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:  陈亮  修改时间:2009-4-1<br>       
 *    修改内容:新建
 * <hr>
 */

public class ComponentUtil {

	public final  static String SCRIPT_RESOURCES_CONTEXT="SCRIPT_RESOURCES_CONTEXT";
	public final  static String STYLE_RESOURCES_CONTEXT="STYLE_RESOURCES_CONTEXT";
	public final  static String OTHER_RESOURCES_CONTEXT="OTHER_RESOURCES_CONTEXT";
	private String contextPath = "/";
	private ServletRequest request;
//	public ComponentUtil(String contextPath){
//		this.contextPath = contextPath;
//	}
	public ComponentUtil(HttpServletRequest request){
		this.request = request;
		this.contextPath = request.getContextPath();
	}
	/**
	 * 输出webapp中的脚本、样式表等资源对应的URL路径,一个页面最多写一次.
	 * 
	 * @param inlineScript
	 *            输出webapp中的脚本资源对应的URL路径
	 * @throws IOException
	 */
	public  String writeScript(String inlineScript){
		return writeScript(inlineScript,null);
	}
	/**
	 * 输出webapp中的脚本、样式表等资源对应的URL路径,一个页面最多写一次.
	 * 
	 * @param inlineScript
	 *            输出webapp中的脚本资源对应的URL路径
	 * @throws IOException
	 */
	public  String writeScript(String inlineScript,String charset){
		Set scriptResources =(Set)request.getAttribute(SCRIPT_RESOURCES_CONTEXT);
		if(scriptResources == null){
			scriptResources = new HashSet();
			request.setAttribute(SCRIPT_RESOURCES_CONTEXT,scriptResources);
		}
		String result = "";
		// Set.add() returns true only if item was added to the set
		// and returns false if item was already present in the set
		if (scriptResources.add(contextPath+inlineScript)) {
			result ="<script type=\"text/javascript\" src=\""+contextPath+inlineScript+"\" "+(charset!=null&&charset.trim().length()>0?" charset=\""+charset+"\"":"")+"></script>";
		}
		return result;
	}
	
	/**
	 * 输出webapp中的脚本、样式表等资源对应的URL路径,一个页面最多写一次.
	 * 
	 * @param context
	 * @param inlineStyle
	 *            输出webapp中的样式表资源对应的URL路径
	 * @throws IOException
	 */
	public  String writeStyle(String inlineStyle){
		Set scriptResources =(Set)request.getAttribute(STYLE_RESOURCES_CONTEXT);
		if(scriptResources == null){
			scriptResources = new HashSet();
			request.setAttribute(STYLE_RESOURCES_CONTEXT,scriptResources);
		}

		String result = "";
		// Set.add() returns true only if item was added to the set
		// and returns false if item was already present in the set
		if (scriptResources.add(contextPath+inlineStyle)) {
			result ="<link rel=\"stylesheet\" type=\"text/css\" href=\""+contextPath+inlineStyle+"\">";
		}
		return result;
	}
	
	/**
	 * 根据指定的Key来输入内容,保证相关key的内容在一个页面只输入一次
	 * @param key
	 * @param inlineStr
	 * @return
	 */
	public  String writeByKey(String key,String inlineStr){
		Set resources =(Set)request.getAttribute(OTHER_RESOURCES_CONTEXT);
		if(resources == null){
			resources = new HashSet();
			request.setAttribute(OTHER_RESOURCES_CONTEXT,resources);
		}

		String result = "";
		// Set.add() returns true only if item was added to the set
		// and returns false if item was already present in the set
		if (resources.add(key)) {
			result =inlineStr;
		}
		return result;
	}

	/**
	 * 根据指定的Key来判断,是否可输入内容,保证相关key的内容在一个页面只输入一次
	 * @param key
	 * @return
	 */
	public  boolean checkByKey(String key){
		Set resources =(Set)request.getAttribute(OTHER_RESOURCES_CONTEXT);
		if(resources == null){
			resources = new HashSet();
			request.setAttribute(OTHER_RESOURCES_CONTEXT,resources);
		}

		boolean result= false;
		// Set.add() returns true only if item was added to the set
		// and returns false if item was already present in the set
		if (resources.add(key)) {
			result =true;
		}
		return result;
	}
	
	/**
	 * 根据配置文件中的键 获取值
	 * @param key
	 * @return String
	 */
	static public String getProperty(String key) {
		 return DefaultSettings.getProperty(key);
	}
}
