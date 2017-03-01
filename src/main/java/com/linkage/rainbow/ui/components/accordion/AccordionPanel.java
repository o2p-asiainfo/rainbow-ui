package com.linkage.rainbow.ui.components.accordion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;

public class AccordionPanel extends UIComponentBase{
	
	 
	/**
	 * 定义layout组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "accordion/simple/accordionPanel";
	/**
	 * 定义layout组件的结束模板
	 */
	public static final String TEMPLATE = "accordion/simple/accordionPanel-close";
	String templateShort ;
	
	private String split; //是否是否分隔栏
	private String iconCls; //标题图标样式
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Layout
	 */
	public AccordionPanel(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}
	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
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
	@StrutsTagAttribute(description = "The template (other than default) to use for rendering the element")
	@Override
	public void setTemplate(String template) {
		this.templateShort=template;
		if (template != null && template.trim().length() > 0) {
			this.template = "accordion/" + template + "/accordionPanel-close";
			this.setOpenTemplate("accordion/" + template + "/accordionPanel");
		} else {
			template = null;
		}
	}
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();		
		if (split != null) 
			addParameter("split", findString(split));
		if (iconCls != null) 
			addParameter("iconCls", findString(iconCls));
	}

	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	


}
