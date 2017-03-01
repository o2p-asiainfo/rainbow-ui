package com.linkage.rainbow.ui.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.interceptor.SessionAware;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.ui.paginaction.Pagination;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.linkage.rainbow.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 类名称<br>
 * action基础类，提供一些常用方法的封装
 * <p>
 * @version 1.0
 * <hr>
 */

public class BaseAction extends ActionSupport implements  SessionAware {
	private Logger log = Logger.getLog(this.getClass());
	private String encoding;
	private static final long serialVersionUID = 1L;
	public Map session;	

	protected List actionScripts = new ArrayList(); //记录开始数
	//	操作后确定的按钮
	//	HashMap button = new HashMap();
	//	button.put("name","按钮名");
	//	button.put("url","链接地址");
	private List buttons = null;
	
    private ComponentUtil tagUtil ;
	
	/**
	 * 给action提供获取response
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();		
	}
	
	/**
	 * 给action提供获取request
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
		
	}
	
	/**
	 * 给action提供获取session
	 * @return HttpSession
	 */
	public HttpSession getSession(){		
		return getRequest().getSession();
	}
	
	/**
	 * 提供获取参数的方法--不推荐使用
	 * @param paramname
	 * @return Object
	 */
	public Object getParam(String paramname){
		if(paramname==null){
			return "";
		}
		return getRequest().getParameter(paramname);
		
	}
	
	/**
	 * 获取脚本列表
	 * @return List
	 */
	public List getActionScripts() {
		return actionScripts;
	}
	/**
	 * 设置脚本列表
	 * @param actionScripts
	 */
	public void setActionScripts(List actionScripts) {
		this.actionScripts = actionScripts;
	}
	/**
	 * 增加脚本列表
	 * @param script
	 */
	public void addActionScript(String script){
		actionScripts.add(script);
	}
	
	/**
	 * 清除按钮
	 */
	public void clearButton(){
		buttons =new ArrayList();
	}
	/**
	 * 增加页面按钮
	 * @param name
	 * @param url
	 */
	public void addButton(String name,String url){
		if(buttons==null)
			buttons =new ArrayList();
		HashMap button = new HashMap();
		button.put("name",name);
		button.put("url",url);
		buttons.add(button);
	}
	
	/**
	 * 增加页面按钮
	 * @param button
	 */
	public void addButton(Map button){
		if(buttons==null)
			buttons =new ArrayList();
		buttons.add(button);
	}
	
	/**
	 * 获取页面按钮集合
	 * @return List
	 */
	
    public List getButtons() {
		return buttons;
	}

	public void setButtons(List buttons) {
		this.buttons = buttons;
	}

	/**
	 * 根据struts.properties文件的系统编码设置该属性
	 * @param encoding
	 */
	@Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
	/**
	 * 获取系统编码类型
	 * @return String
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 设置查询参数
	 * @param page
	 * @param strpage
	 */
	public void setPageParameters(Pagination page,String strpage){
		Map param = ActionContext.getContext().getParameters();
		
		//判断是否加下拉框
		if(getSelectPerPage(strpage)==-1){
		}else{
			page.setPageRecord(getSelectPerPage(strpage));
		}
		
		StringBuffer hidden = new StringBuffer();
		
		Iterator paramIt = param.keySet().iterator();
		
		//String paramStr = "";
		while (paramIt.hasNext()){
			String paramName = (String)paramIt.next();
			String v[] = (String[])param.get(paramName);
			if(paramName.equals("page_currentPage_"+strpage)){
				try{
					if(Integer.parseInt(v[0])>page.getTotalPage()){
						page.setCurrentPage(page.getTotalPage());
					}else{
						page.setCurrentPage(Integer.parseInt(v[0]));
					}					
					continue;
				}catch(Exception ex){
					page.setCurrentPage(1);
					continue;
				}
			}
			if(paramName.equals("page_queryflag_"+strpage)){
				continue;
			}
			if(paramName.toLowerCase().equals("submit")){
				continue;
			}
			
			//将参数转为隐含域,用于翻页进使用.
			if(!paramName.equals("page_currentPage_"+strpage)
			   &&!paramName.equals("page_queryflag_"+strpage)
			   &&!paramName.equals("page_selectPerPage_"+strpage)
			   &&!paramName.equals("title"+strpage)
			   &&!paramName.equals("excelcolumn"+strpage)
			   &&!paramName.equals("startIndex"+strpage)
			   &&!paramName.equals("method"+strpage)
			   &&!paramName.equals("excelTitle"+strpage)
			   &&!paramName.equals("exportMode"+strpage)
			   &&!paramName.equals("queryStringInfo")){
				for(int i=0;i<v.length;i++){
					String value = freemarker.template.utility.StringUtil.HTMLEnc(v[i]);
					hidden.append("<Input Type=\"hidden\" id=\""+paramName+"\" name=\""+paramName+"\" value=\""+ value + "\">");
				}
			}
		}
		page.setCondition(hidden.toString());
	}
	
	/**
	 * 判断是否是第1次查询
	 * @param page
	 * @return int
	 */
	public int getQueryFlag(String page){
		if(getParam("page_queryflag_"+page)!=null){
			try{
				return Integer.parseInt(getParam("page_queryflag_"+page).toString().replace(",", ""));
			}catch(Exception ex){
				return -1;
			}
		}else{
			return -1;
		}
	}
	
	/**
	 * 判断是否设置了每页显示多少条记录
	 * @param page
	 * @return int
	 */
	public int getSelectPerPage(String page){
		if(getParam("page_selectPerPage_"+page)!=null){
			try{
				return Integer.parseInt(getParam("page_selectPerPage_"+page).toString().replace(",", ""));
			}catch(Exception ex){
				return -1;
			}
		}else{
			return -1;
		}
	}
	
	/**
	 * 将传递的参数生成隐藏表单域
	 * @param para
	 * @return String
	 */
	private String paraToHidden(Map para){
		StringBuffer hidden = new StringBuffer();
		for(Iterator it = para.keySet().iterator();it.hasNext();){
			String key = StringUtil.valueOf(it.next());
			if(!key.startsWith("page_currentPage_")&&key.startsWith("page_queryflag")){
				String value = StringUtil.valueOf(para.get(key));
				value = freemarker.template.utility.StringUtil.HTMLEnc(value);
				hidden.append("<Input Type=\"hidden\" id=\""+key+"\" name=\""+key+"\" value=\""+ value + "\">");
			}
		}
		return hidden.toString();
	}

	/**
	 * 将request 中的参数转换为Map
	 * @param request
	 * @return Map
	 */
	public Map getRequestParaMap(){
 		java.util.Map para = new java.util.HashMap(getRequest().getParameterMap());
		
		java.util.Iterator ite = para.keySet().iterator();

		while (ite.hasNext()) {
			String name = (String) ite.next();
			Object obj = para.get(name);
			if(obj instanceof String[]) {
				String value[] = (String[]) obj;
				if (value != null && value.length == 1) {
					String newValue = value[0];
					para.put(name, newValue);
				}
			}
			//System.out.println("key:" + name + "value:" + para.get(name));
		}
		return para;
 	}
	
	public String getLocaleName(){
	    String localeName = "";
	    try {
		Locale locale= getLocale();
		    if(locale != null){
			localeName =  "_"+getLocale().getLanguage()+"_"+getLocale().getCountry();
		    }
	    } catch (Exception e) {
	    }
	    
	    return localeName;
	}

	public  ComponentUtil getTagUtil(){
		if(tagUtil==null){
			tagUtil = new ComponentUtil(this.getRequest());
		}
		return tagUtil;
	}

	public void setSession(Map arg0) {
		this.session=arg0;		
	}

	protected void writeString(String xmlString) throws IOException {
		getResponse().setContentType("text/xml; charset=UTF-8");
		getResponse().setHeader("Cache-Control", "no-cache");
		PrintWriter out = getResponse().getWriter();
		out.write(xmlString);
		out.close();
	}
	
	public void addActionError(String anErrorMessage) {
		log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,anErrorMessage,null));
		super.addActionError(anErrorMessage);
    }
	
}
