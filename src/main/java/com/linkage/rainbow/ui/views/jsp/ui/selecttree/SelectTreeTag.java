package com.linkage.rainbow.ui.views.jsp.ui.selecttree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.selecttree.SelectTree;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * TreeTag<br>
 * 树标签类提供对选择树组件类中属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class SelectTreeTag extends AbstractUITag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;                   //树的id
	private String width;                //树的宽度
	private String height;               //树的宽度
	private String style;                //树的样式
	private String params;               //树的参数
	private String method;               //树调用的类/方法
	private String parentnode;           //父亲节点字段名
	private String inputname;            //input框的name,id
	private String inputvalue;           //input框的value
	private String name;                 //hidden的name,id
	private String mode;                 //checkbox的选择方式
	private String skin;                 //皮肤
	private String showSecond;           //是否显示第2个复选框
	private String template;
	private String secondText;           //第2个复选框的文本
	private String secondName;           //第2个复选框的隐藏表单域
	private String initmethod;           //初始化方法
	private String initparams;           //初始化参数
	private String searchmethod;         //模糊查询方法
	private String searchparams;         //模糊查询参数
	private String openRoad;             //默认展开路径
	private String value;                //第1个选项默认值
	private String secondValue;          //第2个复选框默认值
	
	private String inputdisabled;        //input框是否不可置
	private String readOnly;             //input是否置为只读
	
	private String showAlert;            //是否弹出提示层
	private String showClear;            //是否显示清除按钮
	private String showSelectAll;        //是否显示全选按钮
	
	private String cols;                 //列数
	private String rows;				 //行数
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public SelectTreeTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.selecttree");
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
		return new SelectTree(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		SelectTree tree=(SelectTree)component;
		tree.setId(id);
		tree.setWidth(width);
		tree.setHeight(height);
		tree.setStyle(style);
		tree.setParams(params);
		tree.setMethod(method);
		tree.setParentnode(parentnode);
		tree.setInputname(inputname);
		tree.setName(name);
		tree.setMode(mode);
		tree.setSkin(skin);
		tree.setTemplate(template);
		tree.setShowSecond(showSecond);
		tree.setSecondText(secondText);
		tree.setSecondName(secondName);
		tree.setInitmethod(initmethod);
		tree.setInitparams(initparams);
		tree.setSearchmethod(searchmethod);
		tree.setSearchparams(searchparams);
		tree.setOpenRoad(openRoad);
		tree.setValue(value);
		tree.setSecondValue(secondValue);
		tree.setInputvalue(inputvalue);
		
		tree.setInputdisabled(inputdisabled);
		tree.setReadOnly(readOnly);
		
		tree.setShowAlert(showAlert);
		tree.setShowClear(showClear);
		tree.setShowSelectAll(showSelectAll);
		
		tree.setCols(cols);
		tree.setRows(rows);
		
	}

	
	/**
	 * 设置树节点查询的参数
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 设置input框的name
	 * @param inputname the inputname to set
	 */
	public void setInputname(String inputname) {
		this.inputname = inputname;
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
	 * 设置模板信息
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 设置第一个隐藏表单域的name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * 设置第一个隐藏表单域的value
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 设置第2个隐藏表单域的name
	 * @param secondName the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	/**
	 * 设置第2个隐藏表单域的value
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	/**
	 * 设置第2个checkbox的文本
	 * @param secondText the secondText to set
	 */
	public void setSecondText(String secondText) {
		this.secondText = secondText;
	}

	/**
	 * 是否显示第2个checkbox
	 * @param showSecond the showSecond to set
	 */
	public void setShowSecond(String showSecond) {
		this.showSecond = showSecond;
	}

	/**
	 * 设置装载树的div的宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置装载树的div的高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * 设置树的标识
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 设置装载树的div的样式
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * 当树的模板为checkbox时 点击checkbox时的模式
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * 设置树的皮肤
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}
	/**
	 * 设置查询树节点的初始化方法
	 * @param initmethod the initmethod to set
	 */
	public void setInitmethod(String initmethod) {
		this.initmethod = initmethod;
	}
	/**
	 * 设置查询树节点的初始化参数
	 * @param initparams the initparams to set
	 */
	public void setInitparams(String initparams) {
		this.initparams = initparams;
	}
	/**
	 * 设置模糊查询方法
	 * @param searchmethod the searchmethod to set
	 */
	public void setSearchmethod(String searchmethod) {
		this.searchmethod = searchmethod;
	}
	/**
	 * 设置模糊查询参数
	 * @param searchparams the searchparams to set
	 */
	public void setSearchparams(String searchparams) {
		this.searchparams = searchparams;
	}
	/**
	 * 设置树节点展开路径
	 * @param openRoad the openRoad to set
	 */
	public void setOpenRoad(String openRoad) {
		this.openRoad = openRoad;
	}
	/**
	 * 设置input框的value
	 * @param inputvalue the inputvalue to set
	 */
	public void setInputvalue(String inputvalue) {
		this.inputvalue = inputvalue;
	}
	/**
	 * 是否将input框置为不可用
	 * @param inputdisabled the inputdisabled to set
	 */
	public void setInputdisabled(String inputdisabled) {
		this.inputdisabled = inputdisabled;
	}
	/**
	 * 是否将input设为只读
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 * 设置是否显示弹出层按钮
	 * @param showAlert
	 */
	public void setShowAlert(String showAlert) {
		this.showAlert = showAlert;
	}
	
	/**
	 * 设置是否显示清除按钮
	 * @param showClear
	 */
	public void setShowClear(String showClear) {
		this.showClear = showClear;
	}
	/**
	 * 设置是否显示全选按钮
	 * @param showSelectAll
	 */
	public void setShowSelectAll(String showSelectAll) {
		this.showSelectAll = showSelectAll;
	}
	
	/**
	 * 设置textarea的列数
	 * @param cols
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}
	
	/**
	 * 设置textarea的行数
	 * @param rows
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}
	
	
	
	
}
