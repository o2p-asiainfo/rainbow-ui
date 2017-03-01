package com.linkage.rainbow.ui.views.jsp.ui.combotree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.comboTree.ComboTree;
import com.linkage.rainbow.ui.components.tree.Tree;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * TreeTag<br>
 * 树标签类提供对树形组件类中属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class ComboTreeTag extends UITagBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String method;
	private String params;
	private String parentnode;	
	private String cascadeCheck;
	private String onlyLeafCheck;
	private String multiple;
	private String showSecond;
	private String secondText;
	private String secondName;
	private String initmethod;
	private String initparams;
	private String openRoad;
	private String secondValue;
	private String panelWidth;
	private String panelHeight;
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public ComboTreeTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.combotree");
//		System.out.println("已经进入标签类");
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
		return new ComboTree(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		ComboTree tree=(ComboTree)component;
		tree.setMethod(method);
		tree.setParams(params);
		tree.setParentnode(parentnode);
		tree.setCascadeCheck(cascadeCheck);
		tree.setOnlyLeafCheck(onlyLeafCheck);
		tree.setMultiple(multiple);
		tree.setShowSecond(showSecond);
		tree.setSecondText(secondText);
		tree.setSecondName(secondName);
		tree.setInitmethod(initmethod);
		tree.setInitparams(initparams);
		tree.setOpenRoad(openRoad);
		tree.setSecondValue(secondValue);
		tree.setPanelWidth(panelWidth);
		tree.setPanelHeight(panelHeight);
	}


	

	/**
	 * 设置查询节点的参数
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	



	

	/**
	 * 设置树节点的查询方法
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}


	/**
	 * 设置父节点对应的数据库字段
	 * @param parentnode the parentnode to set
	 */
	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}


	/**
	 * 设置是否显示第2个checkbox
	 * @param showSecond the showSecond to set
	 */
	public void setShowSecond(String showSecond) {
		this.showSecond = showSecond;
	}

	/**
	 * 设置第2个checkbox的文本
	 * @param secondText the secondText to set
	 */
	public void setSecondText(String secondText) {
		this.secondText = secondText;
	}

	/**
	 * 设置第2个隐藏表单域的name
	 * @param secondName the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	/**
	 * 设置初始化查询节点的方法
	 * @param initmethod the initmethod to set
	 */
	public void setInitmethod(String initmethod) {
		this.initmethod = initmethod;
	}

	/**
	 * 设置初始化查询节点的参数
	 * @param initparams the initparams to set
	 */
	public void setInitparams(String initparams) {
		this.initparams = initparams;
	}

	/**
	 * 设置查询展开路径
	 * @param openRoad the openRoad to set
	 */
	public void setOpenRoad(String openRoad) {
		this.openRoad = openRoad;
	}


	/**
	 * 设置第2个隐藏表单域的值
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	public void setCascadeCheck(String cascadeCheck) {
		this.cascadeCheck = cascadeCheck;
	}

	public void setOnlyLeafCheck(String onlyLeafCheck) {
		this.onlyLeafCheck = onlyLeafCheck;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public void setPanelWidth(String panelWidth) {
		this.panelWidth = panelWidth;
	}

	public void setPanelHeight(String panelHeight) {
		this.panelHeight = panelHeight;
	}

	

	

	
	
}
