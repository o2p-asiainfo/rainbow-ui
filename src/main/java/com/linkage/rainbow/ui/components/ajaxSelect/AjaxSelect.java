
package com.linkage.rainbow.ui.components.ajaxSelect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * AjaxSelect.java<br>
 * 该类用于ajax的select级联 将值设置给模板的中间过渡类
 * <p>
 * @version 1.0
 * <hr>
 * 修改记录 
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class AjaxSelect extends ClosingUIBean { 
	
	/**
	 * 定义级联组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "ajaxSelect/ajaxSelect";//级联select标签模版
	/**
	 * 定义级联组件的结束模板 
	 */
	public static final String CLOSE_TEMPLATE = "ajaxSelect/ajaxSelect-close";//级联select标签模版
	
	private String sourceid;//源控件id
	private String targetid;//目标控件id
	private String method;//ajax级联的方法
	private String params;//其他参数
	private String errormessage;//级联错误提示信息
	private String addEmptyOption;//是否增加空项
	private String otherElements;//关联的其他控件
	
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return AjaxSelect
	 */
	public AjaxSelect(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(sourceid != null)
			addParameter("sourceid",  findString(sourceid));
		if(targetid != null)
			addParameter("targetid",  findString(targetid));
		if(method != null)
			addParameter("method",  findString(method));
		if(params != null)
			addParameter("params",  findString(params));
		if(errormessage != null)
			addParameter("errormessage",  findString(errormessage));
		if(addEmptyOption != null)
			addParameter("addEmptyOption",  findValue(addEmptyOption,Boolean.class));
		if(otherElements != null)
			addParameter("otherElements", findString(otherElements));
		
		addParameter("tagUtil", new ComponentUtil(request));
	}

	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}
	
	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		// TODO Auto-generated method stub
		return CLOSE_TEMPLATE;
	}
	
	/**
	 * 对应设置标签的template属性
	 * @param template --模板名称
	 * @return void
	 */
	public void setTemplate(String template) {		
	    if(template != null && template.trim().length()>0) {
	    	this.template = "ajaxSelect/"+template+"/ajaxSelect-close";
		    this.setOpenTemplate("ajaxSelect/"+template+"/ajaxSelect");
	    } else {
	    	template = null;
	    }
	}

	
	/**
	 * 设置对应的源控件
	 * @param sourceid --源控件的id
	 * @return void
	 */
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	/**
	 * 设置对应的目标控件
	 * @param targetid --目标控件的id
	 * @return void
	 */
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	/**
	 * 设置级联获取数据的方法
	 * 例 method="beanid.methodname"
	 * @param String --方法名称
	 * @return void
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 其他附加参数
	 * 例 name:susan;age:11
	 * @param params --字符串
	 * @return void
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 出错提示
	 * @param errormessage 
	 * @return void
	 */
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	/**
	 * 是否增加空项
	 * @param addEmptyOption --true/false
	 * @return void
	 */
	public void setAddEmptyOption(String addEmptyOption) {
		this.addEmptyOption = addEmptyOption;
	}

	/**
	 * 其他关联的控件id 多个用","隔开
	 * @param otherElements
	 * @return void
	 */
	public void setOtherElements(String otherElements) {
		this.otherElements = otherElements;
	}

	

	
}
