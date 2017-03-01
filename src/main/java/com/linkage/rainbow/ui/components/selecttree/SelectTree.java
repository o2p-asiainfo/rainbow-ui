package com.linkage.rainbow.ui.components.selecttree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * SelectTree<br>
 * 树形组件类，主要提供对树形组件的属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */
@StrutsTag(name = "tree", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.tree.SelectTreeTag", description = "选择树菜单组件类")
public class SelectTree extends ClosingUIBean {
	/**
	 * 定义selecttree组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "selecttree/tree";//树标签模版
	/**
	 * 定义selecttree组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "selecttree/tree-close";//树标签模版
	
	private String id;            //树的id
	private String width;         //树的宽度
	private String height;        //树的高度
	private String style;         //树的样式
	private String params;   	  //请求参数
	private String method;  	  //调用的类、方法
	private String parentnode;    //父亲节点字段名
	private String inputname;     //input框的name
	private String inputvalue;    //input框的值
	private String name;          //隐藏表单域的name
	private String mode;          //checkbox选择方式
	private String skin;          //皮肤 
	private String showSecond;    //是否显示第2个复选框
	private String secondText;    //第2个复选框的文本
	private String secondName;    //第2个复选框对应的name
	private String initmethod;    //初始化方法
	private String initparams;    //初始化参数
	private String searchmethod;  //模糊查询方法
	private String searchparams;  //模糊查询参数
	private String openRoad;      //展开路径
	private String value;         //默认值
	private String secondValue;   //第2个复选框对应的value
	
	private String inputdisabled; //input置为不可用
	private String readOnly;      //input置为只读
	
	private String showAlert;    //input框边是否显示弹出关闭层按钮
	private String showClear;    //input框边是否显示清除按钮
	private String showSelectAll;//input框边是否显示全选按钮
	
	private String cols;         //textarea的列数
	private String rows;         //textarea的行数
	
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return SelectTree
	 */
	public SelectTree(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("tagUtil", new ComponentUtil(request));
		if(id != null)
			addParameter("id",findString(id));
		if(width != null)
			addParameter("width",findString(width));
		if(height != null)
			addParameter("height",findString(height));
		if(style != null)
			addParameter("style",findString(style));
		if(params != null)
			addParameter("params",findString(params));
		if(method != null)
			addParameter("method",findString(method));
		if(inputname != null)
			addParameter("inputname",findString(inputname));
		if(inputvalue != null)
			addParameter("inputvalue",findString(inputvalue));
		if(name != null)
			addParameter("name",findString(name));
		if(mode != null)
			addParameter("mode",findString(mode));
		if(skin != null)
			addParameter("skin",findString(skin));
		if(parentnode != null )
			addParameter("parentnode",findString(parentnode));
		if(showSecond != null)
			addParameter("showSecond",findString(showSecond));
		
		if(secondText != null)
			addParameter("secondText",  findString(secondText));
		if(secondName != null)
			addParameter("secondName",findString(secondName));
		if(initmethod != null)
			addParameter("initmethod",findString(initmethod));
		if(initparams != null)
			addParameter("initparams",findString(initparams));
		if(searchmethod != null)
			addParameter("searchmethod",findString(searchmethod));
		if(searchparams != null)
			addParameter("searchparams",  findString(searchparams));
		if(openRoad!=null)
			addParameter("openRoad",findString(openRoad));
		if(value!=null)
			addParameter("value",findString(value));
		if(secondValue != null)
			addParameter("secondValue",findString(secondValue));
		if(inputdisabled != null)
			addParameter("inputdisabled",findString(inputdisabled));
		if(readOnly !=null )
			addParameter("readOnly",findString(readOnly));
		
		if(showAlert !=null )
			addParameter("showAlert",findString(showAlert));
		if(showClear !=null )
			addParameter("showClear",findString(showClear));
		if(showSelectAll !=null )
			addParameter("showSelectAll",findString(showSelectAll));
		
		if(cols !=null )
			addParameter("cols",findString(cols));
		if(rows !=null )
			addParameter("rows",findString(rows));
		
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
			this.template = "selecttree/"+template+"/tree-close";
		    this.setOpenTemplate("selecttree/"+template+"/tree");
	    } else {
	    	template = null;
	    }
		
		 
	     
	}

	/**
	 * 设置生成树节点的方法
	 * @param methods the methods to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 设置生成树的参数
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 设置文本框的name
	 * @param inputname the inputname to set
	 */
	public void setInputname(String inputname) {
		this.inputname = inputname;
	}

	/**
	 * 设置父节点字段名
	 * @param parentnode the parentnode to set
	 */
	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}

	

	/**
	 * 设置隐藏表单域的name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * 设置隐藏表单域的value
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
	 * 设置第2个文本内容
	 * @param secondText the secondText to set
	 */
	public void setSecondText(String secondText) {
		this.secondText = secondText;
	}

	/**
	 * 设置是否显示第2个复选框
	 * @param showSecond the showSecond to set
	 */
	public void setShowSecond(String showSecond) {
		this.showSecond = showSecond;
	}

	/**
	 * 设置div的宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置div的高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置标识
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置层的样式
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	

	/**
	 * 设置checkbox模式的选择模式
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
	 * 设置初始化方法
	 * @param initmethod the initmethod to set
	 */
	public void setInitmethod(String initmethod) {
		this.initmethod = initmethod;
	}

	/**
	 * 设置初始化参数
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
	 * 设置展开路径
	 * @param openRoad the openRoad to set
	 */
	public void setOpenRoad(String openRoad) {
		this.openRoad = openRoad;
	}

	/**
	 * 设置input的值
	 * @param inputvalue the inputvalue to set
	 */
	public void setInputvalue(String inputvalue) {
		if(inputvalue==null){
			this.inputvalue = inputname!=null?(findValue(inputname)==null?"":((String)findValue(inputname))):"";
		}else{
			this.inputvalue = inputvalue;
		}
		
	}

	/**
	 * 设置input的disabled属性
	 * @param inputdisabled the inputdisabled to set
	 */
	public void setInputdisabled(String inputdisabled) {
		this.inputdisabled = inputdisabled;
	}

	/**
	 * 设置是否弹出层图片
	 * @param showAlert
	 */
	public void setShowAlert(String showAlert) {
		this.showAlert = showAlert;
	}

	/**
	 * 设置input是否是只读
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * 设置是否展示清空按钮
	 * @param showClear
	 */
	public void setShowClear(String showClear) {
		this.showClear = showClear;
	}

	/**
	 * 设置是否展示全选按钮
	 * @param showSelectAll
	 */
	public void setShowSelectAll(String showSelectAll) {
		this.showSelectAll = showSelectAll;
	}

	/**
	 * 设置列数
	 * @param cols
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * 设置行数
	 * @param rows
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}


	

	

	
}
