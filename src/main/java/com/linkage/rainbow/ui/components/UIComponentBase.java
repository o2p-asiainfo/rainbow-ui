package com.linkage.rainbow.ui.components;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * UI组件基类<br>
 * <p>
 * @version 1.0
 * <hr>
 */

public abstract class UIComponentBase extends ClosingUIBean {
	//渲染目标
	protected String renderTo;
	//皮肤
	protected String skin;
	protected String width;
	protected String height;
	protected String border;
	//class
	protected String styleClass;
	//style
	protected String style;

	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Layout
	 */
	public UIComponentBase(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		//增加javascript、样式表加载工具类
		addParameter("tagUtil", new ComponentUtil(request));
		if (renderTo != null)
			addParameter("renderTo", findString(renderTo));
		if (style != null) 
			addParameter("style", findString(style));
		if (styleClass != null) 
			addParameter("styleClass", findString(styleClass));
		
		if (skin != null) 
			addParameter("skin", findString(skin));
		if (width != null) 
			addParameter("width", findString(width));
		if (height != null) 
			addParameter("height", findString(height));
		if (border != null) 
			addParameter("border", findString(border));

	}


	/**
	 * 获取版面的皮肤
	 * @return String
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置版面的皮肤
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 获取版面样式
	 * @return String
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置版面的样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取版面的样式表
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置版面的样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}


	/**
	 * 获取版面的高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置版面的高度
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取版面的宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置版面的宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}


	public String getRenderTo() {
		return renderTo;
	}


	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}


	public String getBorder() {
		return border;
	}


	public void setBorder(String border) {
		this.border = border;
	}
	
	
}
