package com.linkage.rainbow.ui.components.comboTree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * ComboTree<br>
 * 树形组件类，主要提供对树形组件的属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */
@StrutsTag(name = "tree", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.tree.TreeTag", description = "树菜单组件类")
public class ComboTree extends UIComponentBase {
	/**
	 * 定义tree组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "combotree/simple/combotree";//树标签模版
	/**
	 * 定义tree组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "combotree/simple/combotree-close";//树标签模版

	private String method;       //调用的类、方法
	private String params;       //请求参数
	private String parentnode;   //父集节点字段名
	private String cascadeCheck;
	private String onlyLeafCheck;
	private String multiple;
	private String showSecond;   //显示第2个复选框
	private String secondText;   //第2个复选框的文本
	private String secondName;   //第2个复选框对应的name
	private String initmethod;   //树初始化的方法
	private String initparams;   //树初始化的参数
	private String openRoad;     //打开路径
	private String secondValue; //第2个选择框的默认值
	private String panelWidth;
	private String panelHeight;
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Tree
	 */
	public ComboTree(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		
		super(stack, request, response);
//		System.out.println("我已经进入combotree组件类方法");
	}

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		if(method != null)
			addParameter("method",findString(method));
		if(params != null)
			addParameter("params",findString(params));
		if(parentnode != null)
			addParameter("parentnode",findString(parentnode));
		if(multiple != null)
			addParameter("multiple",findString(multiple));
		if(cascadeCheck != null)
			addParameter("cascadeCheck",findString(cascadeCheck));
		if(onlyLeafCheck != null)
			addParameter("onlyLeafCheck",findString(onlyLeafCheck));
		if(showSecond != null)
			addParameter("showSecond",showSecond);
		if(secondText != null)
			addParameter("secondText",  findString(secondText));
		if(secondName != null)
			addParameter("secondName",findString(secondName));
		if(initmethod != null)
			addParameter("initmethod",findString(initmethod));
		if(initparams != null)
			addParameter("initparams",findString(initparams));
		if(openRoad != null)
			addParameter("openRoad",findString(openRoad));
		if(secondValue != null)
			addParameter("secondValue",findString(secondValue));
		if (panelWidth != null) 
			addParameter("panelWidth", findString(panelWidth));
		if (panelHeight != null) 
			addParameter("panelHeight", findString(panelHeight));
	}

	/**
	 * 获取树标签模版
	 * @author  Jan 7, 2009
	 * @param 
	 * @return String 树标签模版
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	/**
	 * 获取树标签模版
	 * @author  Jan 7, 2009
	 * @param 
	 * @return String 树标签结束模版
	 */
	@Override
	protected String getDefaultTemplate() {
		return CLOSE_TEMPLATE;
	}



	/**
	 * 设置参数
	 * @param param the param to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	

	/**
	 * 设置获取树节点的方法名
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	

	/**
	 * 设置父节点字段名
	 * @param parentnode the parentnode to set
	 */
	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}

	/**
	 * 指定模板
	 * @author 陈亮 2009-02-11
	 * @param template 模板名. 例:一模板存放的完整路径是
	 *        template/xhtml/date/WdatePicker/date.ftl 其中: template
	 *        为模板存放主目录,对应的标签属性是: templateDir
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
	 *        xhtml 为主题名称,对应的标签属性是: theme
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值. .ftl
	 *        为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
	 *        date/WdatePicker/date 为模板名称, 对应的标签属性是: template 与 openTemplate
	 *        ,如果标签没有设置,则通过此类的getDefaultOpenTemplate()
	 *        与getDefaultTemplate()分别取得标签的开始模板与结束模板.
	 *        便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
	 *        一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date
	 *        目录下再建立子目录,如:WdatePicker,来区分不同的模板实现. 在标签调用时,不需要分别设置template 与
	 *        openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
	 */
	public void setTemplate(String template) {
		if(template != null && template.trim().length()>0) {
			this.template = "combotree/"+template+"/combotree-close";
		    this.setOpenTemplate("combotree/"+template+"/combotree");
	    } else {
	    	template = null;
	    }
		 
	     
	}




	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	/**
	 * 设置是否显示第2个checkbox
	 * @param showSecond the showSecond to set
	 */
	public void setShowSecond(String showSecond) {
		this.showSecond = showSecond;
	}

	/**
	 * 设置第2个复选框的文字
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
	 * 设置初始化方法名
	 * @param initmethod the initmethod to set
	 */
	public void setInitmethod(String initmethod) {
		this.initmethod = initmethod;
	}

	/**
	 * 设置初始化方法的参数
	 * @param initparams the initparams to set
	 */
	public void setInitparams(String initparams) {
		this.initparams = initparams;
	}

	/**
	 * 设置展开路径
	 * @param openRoad the openRoad to set
	 */
	public void setOpenRoad(String openRoad) {
		this.openRoad = openRoad;
	}

	/**
	 * 设置第2个隐藏表单域的value
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

	public void setPanelWidth(String panelWidth) {
		this.panelWidth = panelWidth;
	}

	public void setPanelHeight(String panelHeight) {
		this.panelHeight = panelHeight;
	}

}
