package com.linkage.rainbow.ui.views.jsp.ui.panel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.panel.Panel;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * EasyUIPanelTag<br>
 * 用于将面板组件的标签属性赋予给模板的组件类
 * chenwei
 * @version 1.0
 */
public class PanelTag extends UITagBase{
	private static final long serialVersionUID = -1446587149118257164L;
	private String skin = "default";//组件皮肤：默认为蓝色
	private String width;   //面板宽度
	private String height;  //面板高度
	private String title;   //面板标题
	private String collapsible; //面板是否可折叠
	private String minimizable; //面板是否可最小化
	private String maximizable; //面板是否可最大化
	private String closable;    //面板是否可关闭
	private String tools;       //是否使用面板最定义工具条
	private String iconCls;     //标题栏上图标样式
	private String handlerFunc; //自定义工具条上处理函数(譬如：新增面板、删除面板操作)
	private String fit;         //是否填充以适应其父容器
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public PanelTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.easyuiPanel");
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
		return new Panel(stack, req, resp);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		Panel easyuiPanel = (Panel) component;
		easyuiPanel.setSkin(skin);
		easyuiPanel.setWidth(width);
		easyuiPanel.setHeight(height);
		easyuiPanel.setTitle(title);
		easyuiPanel.setCollapsible(collapsible);
		easyuiPanel.setMinimizable(minimizable);
		easyuiPanel.setMaximizable(maximizable);
		easyuiPanel.setClosable(closable);
		easyuiPanel.setTools(tools);
		easyuiPanel.setFit(fit);
		easyuiPanel.setIconCls(iconCls);
		easyuiPanel.setHandlerFunc(handlerFunc);
	}
	
	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCollapsible() {
		return collapsible;
	}

	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	public String getMinimizable() {
		return minimizable;
	}

	public void setMinimizable(String minimizable) {
		this.minimizable = minimizable;
	}

	public String getMaximizable() {
		return maximizable;
	}

	public void setMaximizable(String maximizable) {
		this.maximizable = maximizable;
	}

	public String getClosable() {
		return closable;
	}

	public void setClosable(String closable) {
		this.closable = closable;
	}

	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getHandlerFunc() {
		return handlerFunc;
	}

	public void setHandlerFunc(String handlerFunc) {
		this.handlerFunc = handlerFunc;
	}

	public String getFit() {
		return fit;
	}

	public void setFit(String fit) {
		this.fit = fit;
	}

}
