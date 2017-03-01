package com.linkage.rainbow.ui.views.jsp.ui.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.util.StringUtil;
import com.linkage.rainbow.util.URLEncryption;

/**
 * ajax远程方法调用<br>
 * 通过此servlet,动调调用spring中某个bean的方法.
 * 接收到request参数包括:
 * 1.ajaxMethods 
 * 	 对应的远程方法名,根据由springBeanId.方法名(参数信息),
 *   如果是入参为Map:springBeanId.方法名(参数名:参数值,参数名:参数值) 
 *	 如果入参为String: springBeanId.方法名(参数值,参数值)
 * 2.ajaxFieldName ajax表单名,程序中根据此表单名取此表单的值.
 * <p> 
 * @version 1.0
 *         <hr>
 */

public class ValidatorRMI extends HttpServlet {
	
	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/xml; charset=GBK");

		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		
		// 远程方法信息,规格为 springBeanId.方法名(参数名:参数值;参数名:参数值)
		String ajaxMethods = request.getParameter("ajaxMethods");
		ajaxMethods = URLEncryption.decodeURL(ajaxMethods);
		// 需要ajax校验的表单ID名称
		String ajaxFieldName = request.getParameter("ajaxFieldName");
		// 需要ajax校验的表单当前输入的值 
		//String ajaxFieldValue = new String(StringUtil.valueOf(request.getParameter(ajaxFieldName)).getBytes("GBK"),"UTF-8") ;//request.getParameter(ajaxFieldName);
		String ajaxFieldValue ="";
		if (request.getParameter(ajaxFieldName) != null){
			ajaxFieldValue = java.net.URLDecoder.decode(request.getParameter(ajaxFieldName), "UTF-8");
		}
		Object result = null;
		if (ajaxMethods != null) {
			
			//取得bean id,方法名,参数值
			String springBeanId = ajaxMethods.substring(0, ajaxMethods
					.indexOf("."));
			String methodName = ajaxMethods.substring(ajaxMethods
					.indexOf(".")+1);
			String para = "";
			String paraArray[] = null;
			boolean isMap =true;
			if (methodName.indexOf("(") > -1) {
				para = methodName.substring(methodName.indexOf("(")+1, methodName
						.indexOf(")"));
				if(para.indexOf(":")==-1)
					isMap =false;
				methodName = methodName.substring(0, methodName.indexOf("("));
				if (para.trim().length() > 0) {
					//paraArray = para.split(",");
					paraArray = StringUtils.tokenizeToStringArray(para, ";,\t\n");
				}
			}
			//取得spring上下文
			WebApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(request.getSession()
							.getServletContext());
			// 取得spring bean实例
			Object bean = ctx.getBean(springBeanId);
			if(isMap){
				try {
					result = doMap(bean,methodName,paraArray,ajaxFieldName,ajaxFieldValue);
				} catch (Exception e) {
					try {
						result = doString(bean,methodName,paraArray,ajaxFieldName,ajaxFieldValue);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}else {
				try {
					result = doString(bean,methodName,paraArray,ajaxFieldName,ajaxFieldValue);
				} catch (Exception e) {
					try {
						result = doMap(bean,methodName,paraArray,ajaxFieldName,ajaxFieldValue);
					} catch (Exception e2) {
					}
					
				}
			}
		}
//		System.out.println("ajax result"+result);
		
		response.getWriter().println(StringUtil.valueOf(result));
		response.getWriter().flush();
		
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
	
	/**
	 * 动态调用入参为Map的方法
	 * @param bean
	 * @param methodName
	 * @param paraArray
	 * @param ajaxFieldName
	 * @param ajaxFieldValue
	 * @return
	 * @throws Exception
	 */
	private Object doMap(Object bean,String methodName,String[] paraArray,String ajaxFieldName,String ajaxFieldValue) throws Exception{
		Class argsClass[] = new Class[1];
		argsClass[0] = Map.class;
		java.lang.reflect.Method method = null;
		Object result = null;

			method = bean.getClass().getMethod(methodName, argsClass);
			Object[] parameters = new Object[1];
			Map map = new HashMap();
			map.put(ajaxFieldName, ajaxFieldValue);
			if (paraArray != null) {
				for (int i = 0; i < paraArray.length; i++) {
					String tmp[] = paraArray[i].split(":");
					map.put(tmp[0], tmp[1]);
				}
			}
			parameters[0] = map;
			result = method.invoke(bean, parameters);
		return result;
	}
	
	/**
	 * 动态调用入参为String的方法
	 * @param bean
	 * @param methodName
	 * @param paraArray
	 * @param ajaxFieldName
	 * @param ajaxFieldValue
	 * @return
	 * @throws Exception
	 */
	private Object doString(Object bean,String methodName,String[] paraArray,String ajaxFieldName,String ajaxFieldValue) throws Exception{
		Class argsClass[] = new Class[1];
		argsClass[0] = Map.class;
		java.lang.reflect.Method method = null;
		Object result = null;
		
		int iParaArrayLength = paraArray != null ? paraArray.length : 0;
		argsClass = new Class[iParaArrayLength + 1];
		for (int i = 0; i < iParaArrayLength + 1; i++)
			argsClass[i] = String.class;


		method = bean.getClass().getMethod(methodName, argsClass);
		Object[] parameters = new Object[iParaArrayLength + 1];
		for (int i = 0; i < iParaArrayLength; i++) {
			parameters[i] = paraArray[i];
		}
		parameters[iParaArrayLength ] = ajaxFieldValue;

		result = method.invoke(bean, parameters);

		return result;
	}
}
