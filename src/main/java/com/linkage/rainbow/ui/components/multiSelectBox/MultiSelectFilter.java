package com.linkage.rainbow.ui.components.multiSelectBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ListUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * MultiSelectBox<br>
 * 用于将下拉多选组件的标签属性赋予给模板的组件类--另新增根据输入关键字模糊匹配功能
 * chenwei
 * @version 1.0
 */
public class MultiSelectFilter extends ListUIBean{
	/**
	 * 定义multiSelectFilter组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "multiSelectBox/multiSelectFilter";// 多选下拉框filter标签模版
	/**
	 * 定义multiSelectFilter组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "multiSelectBox/multiSelectFilter-close";// 多选下拉框结束标签模版

	private String skin = "default";//组件皮肤：默认为蓝色
	private String style ;            //组件下拉框宽度
	private String header;            //组件默认显示全选与非全选层
	private String selectedList;      //组件下拉文本框默认显示选取值的个数
	private String noneSelectedText; //组件下拉文本框默认显示文本信息
	private String checkAllText;     //设置全选文本信息
	private String uncheckAllText;   //设置非全选文本信息
	private String layerWidth;       //组件选项栏的宽度
	private String method;           //异步load下拉框可选值方法
	private String params;           //调用springbena.方法名时传递的入参
	private String loadMode;         //加载下拉列表模式
	private String label;            //filter框文字标签
	private String filterWidth;      //filter框宽度
	private String placeholder;      //filter框里初始化显示的提示信息
	private String autoReset;        //是否失去焦点后清空filter框输入的关键字
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return MultiSelectFilter
	 */
	public MultiSelectFilter(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	public void evaluateExtraParams() {
		super.throwExceptionOnNullValueAttribute = false;
		super.evaluateExtraParams();
		addParameter("tagUtil", new ComponentUtil(request));
		if (skin != null) 
			addParameter("skin", findString(skin));
		if (style != null) 
			addParameter("style", findString(style));
		if (header != null) 
			addParameter("header", findString(header));
		if (selectedList != null) 
			addParameter("selectedList", findString(selectedList));
		if (noneSelectedText != null) 
			addParameter("noneSelectedText", findString(noneSelectedText));
		if (checkAllText != null) 
			addParameter("checkAllText", findString(checkAllText));
		if (uncheckAllText != null) 
			addParameter("uncheckAllText", findString(uncheckAllText));
		if (layerWidth != null) 
			addParameter("layerWidth", findString(layerWidth));
		if (method != null)
			addParameter("method", findString(method));
		if (params != null)
			addParameter("params", findString(params));
		if (loadMode != null)
			addParameter("loadMode",  findValue(loadMode,Boolean.class));
		if (label != null)
			addParameter("label", findString(label));
		if (filterWidth != null)
			addParameter("filterWidth", findString(filterWidth));
		if (placeholder != null)
			addParameter("placeholder", findString(placeholder));
		if (autoReset != null)
			addParameter("autoReset", findValue(autoReset,Boolean.class));
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
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "multiSelectBox/" + template
					+ "/multiSelectFilter";
		} else {
			template = null;
		}
	}
	
	/**
	 * 继承自基类，并重写，用于获取开始模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		return OPEN_TEMPLATE;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFilterWidth() {
		return filterWidth;
	}

	public void setFilterWidth(String filterWidth) {
		this.filterWidth = filterWidth;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getAutoReset() {
		return autoReset;
	}

	public void setAutoReset(String autoReset) {
		this.autoReset = autoReset;
	}
}
