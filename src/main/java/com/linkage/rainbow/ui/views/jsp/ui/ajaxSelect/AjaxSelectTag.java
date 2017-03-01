package com.linkage.rainbow.ui.views.jsp.ui.ajaxSelect;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.ajaxSelect.AjaxSelect;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * AjaxSelectTag<br>
 * 级联select标签类提供对级联组件类属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class AjaxSelectTag extends AbstractUITag {
	private String sourceid;
	private String targetid;
	private String method;
	private String params;
	private String errormessage;
	//是否自动增加一个空选项
	private String addEmptyOption;
	private String otherElements;
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public AjaxSelectTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.ajaxSelect");
	}
	
	/**
	 * 实现接口类 继承父类的基本信息
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new AjaxSelect(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		AjaxSelect select=(AjaxSelect)component;
		
		select.setSourceid(sourceid);
		select.setTargetid(targetid);
		select.setMethod(method);
		select.setParams(params);
		select.setErrormessage(errormessage);
		select.setAddEmptyOption(addEmptyOption);
		select.setOtherElements(otherElements);
	}

	/**
	 * 源控件id
	 * @param sourceid the sourceid to set
	 */
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	/**
	 * 目标控件id
	 * @param targetid the targetid to set
	 */
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	/**
	 * ajax级联方法
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 参数信息
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 错误信息
	 * @param errormessage the errormessage to set
	 */
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	/**
	 * 是否增加空项
	 * @param addEmptyOption
	 */
	public void setAddEmptyOption(String addEmptyOption) {
		this.addEmptyOption = addEmptyOption;
	}

	/**
	 * @param 其他控件id组合 用","分隔
	 * @param otherElements the otherElements to set
	 */
	public void setOtherElements(String otherElements) {
		this.otherElements = otherElements;
	}

		
}
