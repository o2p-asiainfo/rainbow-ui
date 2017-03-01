package com.linkage.rainbow.ui.views.jsp.ui.multiSelectBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractRequiredListTag;

import com.linkage.rainbow.ui.components.multiSelectBox.MultiSelectBox;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * MultiSelectBox<br>
 * 用于将下拉多选组件的标签属性赋予给模板的组件类
 * chenwei
 * @version 1.0
 */
public class MultiSelectBoxTag extends AbstractRequiredListTag{

	private static final long serialVersionUID = 1L;
	private String skin = "blue";//组件皮肤：默认为蓝色
	private String style = "width:160px;font-size:12px;background:#FFFFFF";//组件下拉框宽度,字体大小,下拉框按钮背景色
	private String header = "true";      //组件默认显示全选与非全选层
	private String selectedList = "2";   //组件下拉文本框默认显示选取值的个数
	private String noneSelectedText = ""; //组件下拉文本框默认显示文本信息
	private String checkAllText = "Check";//设置全选文本信息
	private String uncheckAllText = "Uncheck";//设置非全选文本信息
	private String layerWidth = "width:170px;font-size:12px;";//组件选项栏的宽度
	private String method;           //异步load下拉框可选值方法
	private String params;           //调用springbena.方法名时传递的入参
	private String loadMode;         //加载下拉列表模式
	private String width;
	private String layerHeight;
	
	/**
	 * 调用配置文件 设置默认值ֵ
	 */
	public MultiSelectBoxTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.multiSelectBox");
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
		return new MultiSelectBox(stack, req, resp);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		MultiSelectBox multiSelectBox = (MultiSelectBox) component;
		multiSelectBox.setStyle(style);
		multiSelectBox.setHeader(header);
		multiSelectBox.setSelectedList(selectedList);
		multiSelectBox.setNoneSelectedText(noneSelectedText);
		multiSelectBox.setCheckAllText(checkAllText);
		multiSelectBox.setUncheckAllText(uncheckAllText);
		multiSelectBox.setLayerWidth(layerWidth);
		multiSelectBox.setSkin(skin);
		multiSelectBox.setMethod(method);
		multiSelectBox.setParams(params);
		multiSelectBox.setLoadMode(loadMode);
		multiSelectBox.setWidth(width);
		multiSelectBox.setLayerHeight(layerHeight);
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}

	public String getNoneSelectedText() {
		return noneSelectedText;
	}

	public void setNoneSelectedText(String noneSelectedText) {
		this.noneSelectedText = noneSelectedText;
	}

	public String getCheckAllText() {
		return checkAllText;
	}

	public void setCheckAllText(String checkAllText) {
		this.checkAllText = checkAllText;
	}

	public String getUncheckAllText() {
		return uncheckAllText;
	}

	public void setUncheckAllText(String uncheckAllText) {
		this.uncheckAllText = uncheckAllText;
	}

	public String getLayerWidth() {
		return layerWidth;
	}

	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
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
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}
}
