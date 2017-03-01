package com.linkage.rainbow.ui.views.jsp.ui.autoComplete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.autoComplete.AutoComplete;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * AutoCompleteTag<br>
 * 自动填充标签类提供对自动填充组件类属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class AutoCompleteTag extends UITagBase {
	private String method;
	private String params;
	private String errormessage;
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public AutoCompleteTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.autoComplete");
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
		return new AutoComplete(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		AutoComplete select=(AutoComplete)component;

		select.setMethod(method);
		select.setParams(params);
		select.setErrormessage(errormessage);
	}

	/**
	 * 设置模糊查询方法
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 设置模糊查询参数
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 设置错误提示信息
	 * @param errormessage the errormessage to set
	 */
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
}
