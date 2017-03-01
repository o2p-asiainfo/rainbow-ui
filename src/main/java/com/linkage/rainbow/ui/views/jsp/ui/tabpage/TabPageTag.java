package com.linkage.rainbow.ui.views.jsp.ui.tabpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.tabpage.TabPage;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * TabPage<br>
 * EasyUI标签页组件类，主要提供对标签页的属性的操作
 * chenwei
 * @version 1.0
 */
public class TabPageTag extends UITagBase {
	private static final long serialVersionUID = -3811432869957110614L;
	private String tabid; //Tab控件ID
	private String loadMode; 
	private String activeid;
	private String tabPosition; //标签位置,可选的位置有四种，分别是：left、right、top、bottom 默认top
	private String closable;//是否可关闭标签页
	private String fit;
	private String onSelect; //tab切换时事件
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public TabPageTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.tabpage");
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
		return new TabPage(stack, req, res);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		TabPage tab=(TabPage)component;
		tab.setTabid(tabid);
		tab.setLoadMode(loadMode);
		tab.setActiveid(activeid);
		tab.setTabPosition(tabPosition);
		tab.setClosable(closable);
		tab.setFit(fit);
		tab.setOnSelect(onSelect);
	}

	/**
	 * 设置标签页对应的唯一标识
	 * @param tabid the tabid to set
	 */
	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	/**
	 * 设置模板
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 设置标签页的模式
	 * @param mode the mode to set
	 */
	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}

	/**
	 * 设置当前激活的标签页
	 * @param activeid the activeid to set
	 */
	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	/**
	 * 设置标签页的位置
	 * @param tabposition the tabposition to set
	 */
	public void setTabposition(String tabPosition) {
		this.tabPosition = tabPosition;
	}

	/**
	 * 设置是否显示关闭标签页的按钮
	 * @param enableTabClose
	 */
	public void setClosable(String closable) {
		this.closable = closable;
	}

	public void setFit(String fit) {
		this.fit = fit;
	}
	public String getTabPosition() {
		return tabPosition;
	}

	public void setTabPosition(String tabPosition) {
		this.tabPosition = tabPosition;
	}

	public String getTabid() {
		return tabid;
	}

	public String getLoadMode() {
		return loadMode;
	}

	public String getActiveid() {
		return activeid;
	}

	public String getClosable() {
		return closable;
	}

	public String getFit() {
		return fit;
	}
	public String getOnSelect() {
		return onSelect;
	}

	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}
}
