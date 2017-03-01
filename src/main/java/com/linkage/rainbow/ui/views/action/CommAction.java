package com.linkage.rainbow.ui.views.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import template.simple.ajaxSelect.resource.AjaxSelect;
//import template.simple.autoComplete.resource.AutoComplete;
//import template.simple.page.simple.ExcelExport;
//import template.simple.page.simple.SaveParameter;
//import template.simple.randomImg.resource.RandomImg;
//import template.simple.selecttree.radio.resource.SelectTree;
//import template.simple.sqlDebug.SqlDebug;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.struts.BaseAction;
import com.linkage.rainbow.ui.views.jsp.ui.validator.ValidatorRMI;


/**
 * CommAction<br>
 * 公共组件通过用的Action,所有公共组件中需要访问的页面通过此action进行分发.
 * comm/jsp.shtml?servlet_name=jsp名称
 * 通过此链接显示JSP页面
 * comm/servlet.shtml?servlet_name=servlet名称
 * 通过此链接处理servlet的页面请求
 * comm/ftl.shtml?servlet_name=freemarker模板名称
 * 通过此链接处理freemarker的页面请求
 * <p>
 * @version 1.0
 * <hr>
 */
public class CommAction extends BaseAction { 
	
	/**
	 * jsp,servlet,ftl名称
	 */
	private String servlet_name; 
	private static Log log = LogFactory.getLog(CommAction.class);
	/**
	 * 要跳转的页面路径,相于WebRoot
	 */
	private String forwardUrl;
	 
	
//    static SelectTree selecttree = new SelectTree();
//    static AutoComplete autocomplete = new AutoComplete();
//    static RandomImg randomimg = new RandomImg();
//    static AjaxSelect ajaxselect=new AjaxSelect();
    static ValidatorRMI validatorRMI = new ValidatorRMI();
//    static ExcelExport excelexport = new ExcelExport();
//    static SqlDebug sqlDebug = new SqlDebug();
//    //static ExportPage exportpage = new ExportPage();
//    static SaveParameter savepar = new SaveParameter();
    
    static HttpServlet reportServlet;
    static HttpServlet exportImage;
	public String execute() {
		return SUCCESS; 
	}
	
	/**
	 * 取得要跳转的页面路径,相于WebRoot
	 * @return
	 */
	public String getForwardUrl() {
		return forwardUrl;
	}

	/**
	 * 设置要跳转的页面路径,相于WebRoot
	 * @param forwardUrl
	 */
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	
	/**
	 * 取得jsp,servlet,ftl名称
	 * @return
	 */
	public String getServlet_name() {
		return servlet_name;
	}

	/**
	 * 设置jsp,servlet,ftl名称
	 * @param servlet_name
	 */
	public void setServlet_name(String servlet_name) {
		this.servlet_name = servlet_name;
	}

	

	/**
	 * 处理JSP类的页面请求
	 * comm/jsp.shtml?servlet_name=jsp名称
	 * jsp存放有路径为WEB-INF\classes\template下,
	 * jsp名称是指相对此存放路径的路径与文件名.路径分隔用.号分隔,例
	 * 页面存放在:
	 * 		/WEB-INF/classes/template/simple/sqlDebug/sql_debug.jsp
	 * 则浏览器中通过以下地址访问:
	 * 		comm/jsp.shtml?servlet_name=simple.sqlDebug.sql_debug
	 * @return
	 */
	public String jsp() { 
		servlet_name = servlet_name.replaceAll("\\.","/"); 
		forwardUrl = "/WEB-INF/classes/template/"+servlet_name+".jsp";
		return SUCCESS;
	}
	
	/**
	 * 处理servlet的页面请求
	 * comm/servlet.shtml?servlet_name=servlet名称
	 * servlet名称通过此方法预先定义,根据各名称分别调用相关servlet进行处理请求
	 * @return
	 */
	public String servlet(){
		try {
			
			if(servlet_name.equals("selecttree")){
//	    		selecttree.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("autocomplete")){
//	    		autocomplete.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("randomimg")){
//	    		randomimg.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("ajaxselect")){
//	    		ajaxselect.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("validatorRMI")){
	    		validatorRMI.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("sqlDebug")){
//	    		sqlDebug.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("excelexport")){
//	    		excelexport.doPost(getRequest(),getResponse());
	    	}else if(servlet_name.equals("report")){//UI报表servlet
	    		if(reportServlet ==null)
	    			reportServlet = (HttpServlet)Class.forName("com.linkage.rainbow.views.jsp.ui.report.ReportServlet").newInstance();
	    		Class argsClass[] = new Class[2];
	    		argsClass[0] = HttpServletRequest.class;
	    		argsClass[1] = HttpServletResponse.class;
	    		java.lang.reflect.Method method = null;
	    		method = reportServlet.getClass().getMethod("doPost", argsClass);
    			Object[] parameters = new Object[2];
    			parameters[0] = getRequest();
    			parameters[1] = getResponse();
    			method.invoke(reportServlet, parameters);
	    	}else if(servlet_name.equals("exportImage")){//UI报表amcharts图表导出
	    		if(exportImage ==null)
	    			exportImage = (HttpServlet)Class.forName("com.linkage.rainbow.views.jsp.ui.report.ExportImage").newInstance();
	    		Class argsClass[] = new Class[2];
	    		argsClass[0] = HttpServletRequest.class;
	    		argsClass[1] = HttpServletResponse.class;
	    		java.lang.reflect.Method method = null;
	    		method = exportImage.getClass().getMethod("doPost", argsClass);
    			Object[] parameters = new Object[2];
    			parameters[0] = getRequest();
    			parameters[1] = getResponse();
    			method.invoke(exportImage, parameters);
	    	}
//	    	else if(servlet_name.equals("savepar")){
//	    		savepar.doPost(getRequest(),getResponse());
//	    	}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		
		return SUCCESS;
	}
	 
	/**
	 * 处理freemarker的页面请求
	 * comm/ftl.shtml?servlet_name=freemarker模板名称
	 * freemarker模板存放有路径为WEB-INF\classes\template下,
	 * freemarker模板名称是指相对此存放路径的路径与文件名.路径分隔用.号分隔,例
	 * freemarker模板页面存放在:
	 * 		/WEB-INF/classes/template/simple/sqlDebug/sql_debug.ftl
	 * 则浏览器中通过以下地址访问:
	 * 		comm/ft.lshtml?servlet_name=simple.sqlDebug.sql_debug
	 * @return
	 */
	public String ftl() {
		servlet_name = servlet_name.replaceAll("\\.","/"); 
		forwardUrl = "/WEB-INF/classes/template/"+servlet_name+".ftl";
		return SUCCESS;
	}
}
