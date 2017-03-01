package com.linkage.rainbow.ui.views.jsp.ui.comboBox;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.ajaxSelect.AjaxSelect;
import com.linkage.rainbow.ui.components.combobox.Combobox;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * AjaxSelectTag<br>
 * 级联select标签类提供对级联组件类属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class ComboboxTag extends UITagBase {
	private String textField;
	private String multiple;
	private String valueField;
	private String method;
	private String params;
	private String panelWidth;
	private String panelHeight;
	/**
	 * 调用配置文件 设置默认值
	 */
	public ComboboxTag(){
		//System.out.println("已经进入标签类");
		DefaultSettings.setDefaultValue(this,"rainbow.ui.combobox");
		
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
		return new Combobox(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
	//	System.out.println("nnnnnnnnnnn"+url);
		Combobox comboBox=(Combobox)component;

		comboBox.setTextField(textField);
		comboBox.setMultiple(multiple);
		comboBox.setPanelWidth(panelWidth);
		comboBox.setPanelHeight(panelHeight);
		comboBox.setValueField(valueField);
		comboBox.setMethod(method);
		comboBox.setParams(params);
		

	}



	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getPanelHeight() {
		return panelHeight;
	}

	public void setPanelHeight(String panelHeight) {
		this.panelHeight = panelHeight;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getPanelWidth() {
		return panelWidth;
	}

	public void setPanelWidth(String panelWidth) {
		this.panelWidth = panelWidth;
	}




		
}
