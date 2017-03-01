package com.linkage.rainbow.ui.views.jsp.ui.tabpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.tabpage.TabPageDiv;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * TabPageDivTag<br>
 * EasyUI标签页div标签类提供对标签页div组件类属性的操作
 * chenwei
 * @version 1.0
 */
public class TabPageDivTag extends UITagBase {
	private static final long serialVersionUID = -3577091651339008735L;

	private String title;  //每个标签页标题
	private String href;
	private String pageid;
	private String tabid;
	private String loadMode;
	private String closable;//是否可关闭本标签页
	private String iconCls;
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public TabPageDivTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.tabpagediv");
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
		return new TabPageDiv(stack, req, res);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		TabPageDiv div=(TabPageDiv)component;
		div.setTabid(tabid);
		div.setPageid(pageid);
		div.setHref(href);
		div.setTitle(title);
		div.setTabid(tabid);
		div.setLoadMode(loadMode);
		div.setClosable(closable);
		div.setIconCls(iconCls);
	}

	/**
	 * 设置标签页标题
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置标签页对应的url
	 * @param url the url to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * 设置标签页父控件对应的id
	 * @param pageid the pageid to set
	 */
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	/**
	 * 设置标签页的id
	 * @param tabid the tabid to set
	 */
	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	/**
	 * 设置标签页的模式 是iframe或ajax
	 * @param mode the mode to set
	 */
	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}

	/**
	 * 设置是否有关闭按钮
	 * @param enableTabClose
	 */
	public void setClosable(String closable) {
		this.closable = closable;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
