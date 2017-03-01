package com.linkage.rainbow.ui.views.jsp.ui.selectMenu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractRequiredListTag;

import com.linkage.rainbow.ui.components.selectMenu.SelectMenu;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * SelectMenuTag<br>
 * 用于将下拉组件的标签属性赋予给模板的组件类
 * chenwei
 * @version 1.0
 */
public class SelectMenuTag extends AbstractRequiredListTag{

	private static final long serialVersionUID = 1L;
	private String skin ;//组件皮肤：默认为蓝色
	private String style = "width:170px;font-size:12px;background:#FFFFFF";//组件下拉框宽度,字体大小,下拉框按钮背景色
	private String onchange;//组件下拉框下拉事件
	private String layerWidth; //设置下拉框列表层 宽度 
	private String layerHeight;//设置下拉框列表层高度
	private String method;           //异步load下拉框可选值方法
	private String params;           //调用springbena.方法名时传递的入参
	private String loadMode;         //加载下拉列表模式 
	private String width;            //设置组件文本框宽度
	private String headerValue;
	private String emptyOption;
	private String disabled;

	public String getHeaderValue() {
		return headerValue;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public String getEmptyOption() {
		return emptyOption;
	}

	public void setEmptyOption(String emptyOption) {
		this.emptyOption = emptyOption;
	}

	/**
	 * 调用配置文件 设置默认值
	 */
	public SelectMenuTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.selectMenu");
	}
	
	/**
	 * 实现接口类 继承父类的基本信息
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,HttpServletResponse resp) {
		return new SelectMenu(stack, req, resp);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		SelectMenu selectMenu = (SelectMenu) component;
		selectMenu.setStyle(style);
		selectMenu.setSkin(skin);
		selectMenu.setOnchange(onchange);
		selectMenu.setLayerHeight(layerHeight);
		selectMenu.setLayerWidth(layerWidth);
		selectMenu.setParams(params);
		selectMenu.setLoadMode(loadMode);
		selectMenu.setMethod(method);
		selectMenu.setWidth(width);
		selectMenu.setHeaderValue(headerValue);
		selectMenu.setEmptyOption(emptyOption);
		selectMenu.setDisabled(disabled);
	}
	
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
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

	public String getLoadMode() {
		return loadMode;
	}

	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}

	public String getLayerWidth() {
		return layerWidth;
	}

	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
