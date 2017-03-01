package com.linkage.rainbow.ui.components.slider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;
import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "slider", tldTagClass = "com.linkage.rainbow.ui.views.jsp.ui.slider.SliderTag", description = "滑块组件类")
public class Slider extends UIComponentBase{

	private String from ; 
	private String to ; 
	private String step ; 
	private String dimension ; 
	private String skin ; 
	private String scale ; 
	private String limits ; 
	private String callback ; 
	private String defaultvalue ; 
	private String divclass; 
	
	/**
	 * 定义ztree组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "slider/simple/slider";//滑块标签模版
	/**
	 * 定义ztree组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "slider/simple/slider-close";//滑块标签模版

	
	public Slider(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
//		System.out.println("已经进入slider组件类方法");
	}

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		if(from != null)
			addParameter("from",findString(from));
		if(to != null)
			addParameter("to",findString(to));
		if(step != null)
			addParameter("step",findString(step));
		if(dimension != null)
			addParameter("dimension",findString(dimension));
		if(skin != null)
			addParameter("skin",findString(skin));
		if(scale != null)
			addParameter("scale",findString(scale));
		if(limits != null)
			addParameter("limits",findString(limits));
		if(callback != null)
			addParameter("callback",findString(callback));
		if(defaultvalue != null)
			addParameter("defaultvalue",findString(defaultvalue));
		if(divclass != null)
			addParameter("divclass",findString(divclass));
		
		addParameter("tagUtil", new ComponentUtil(request));
	}
	
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

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
			this.template = "slider/"+template+"/slider-close";
		    this.setOpenTemplate("slider/"+template+"/slider");
	    } else {
	    	template = null;
	    }
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDivclass() {
		return divclass;
	}

	public void setDivclass(String divclass) {
		this.divclass = divclass;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

}
