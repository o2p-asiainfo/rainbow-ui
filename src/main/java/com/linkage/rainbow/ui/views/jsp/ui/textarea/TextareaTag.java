package com.linkage.rainbow.ui.views.jsp.ui.textarea;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import com.linkage.rainbow.ui.components.textarea.Textarea;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class TextareaTag extends UITagBase {
	private String id ;
	private String name ; 
	private String value ;
	private String width ;
	private String height;
	private String skin ;
	public TextareaTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.textarea");
	}
	
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new Textarea(stack, req, res);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();
		Textarea textarea = (Textarea) component;
		textarea.setId(id);
		textarea.setName(name) ;
		textarea.setValue(value) ;
		textarea.setWidth(width);
		textarea.setHeight(height);
		textarea.setSkin(skin) ;
	}
	
	
	public String getSkin() {
		return skin;
	}


	public void setSkin(String skin) {
		this.skin = skin;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

}
