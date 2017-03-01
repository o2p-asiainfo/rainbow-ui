package com.linkage.rainbow.ui.components.date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Date<br>
 * 日期组件类，主要提供对日期组件的属性的操作
 * <p>
 * @version 1.0
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:   修改时间:<br>       
 *   修改内容:
 * <hr>
 */
@StrutsTag(name = "date", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.date.DateTag", description = "日期组件类")
public class Date extends UIComponentBase {
	/**
	 * 定义日期标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "date/date";//日期标签模版
	/**
	 * 定义日期标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "date/date-close";//日期结束标签模版


	private String isShowWeek ;//是否显示周
	private String readOnly;//是否只读
	private String dateFmt;//日期格式
	private String minDate;//最小日期
	private String maxDate;//最大日期
	private String disabled;//禁止更改日期
	private String script;  //客户端自定义事件
	private String triggerEvent;//日期控件弹出的触发事件,默认是onclick ,可选onfocus
	private String lang;//日期控件弹出的触发事件,默认是onclick ,可选onfocus
	



	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param ValueStack --值栈对象 
	 * @param HttpServletRequest --http请求
	 * @param HttpServletResponse --http回复
	 * @return Date
	 */
	public Date(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		if(isShowWeek != null)
			addParameter("isShowWeek",  findString(isShowWeek));
		if(readOnly != null)
			addParameter("readOnly",  findString(readOnly));
		if(dateFmt != null)
			addParameter("dateFmt",  findString(dateFmt));
		if(minDate != null)
			addParameter("minDate",  findString(minDate));
		if(maxDate != null)
			addParameter("maxDate",  findString(maxDate));
		if(disabled != null)
			addParameter("disabled",  findString(disabled));
		if(lang != null)
			addParameter("lang",  findString(lang));
		
		if(width==null || width.trim().length()==0){
			int iLength = dateFmt!=null && dateFmt.trim().length()>0?dateFmt.trim().length():10;
			width=""+(8*iLength+16)+"px";
			addParameter("width", width);
		}else {
			addParameter("width",  findString(width));
		}
		
		if(script != null)
			addParameter("script",  findString(script));
		if(triggerEvent != null)
			addParameter("triggerEvent",  findString(triggerEvent));
		
	}
	

	/**
	 * 获取日期标签模版
	 * @return String 日期标签模版
	 */
	@Override
	public String getDefaultOpenTemplate() {
		
		return OPEN_TEMPLATE;
	}

	/**
	 * 获取日期标签结束模版
	 * @return String 日期标签结束模版
	 */
	@Override
	protected String getDefaultTemplate() {
		
		return CLOSE_TEMPLATE;
		
	}
	
    /**
     * 指定模板
     * @author 陈亮 2009-02-11
     * @param template 模板名.
     * 例:一模板存放的完整路径是 template/xhtml/date/WdatePicker/date.ftl
     * 其中:
     * template 为模板存放主目录,对应的标签属性是: templateDir ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
     * xhtml    为主题名称,对应的标签属性是: theme ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值.
     * .ftl     为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
     * date/WdatePicker/date 为模板名称,
     *          对应的标签属性是: template 与 openTemplate ,如果标签没有设置,则通过此类的getDefaultOpenTemplate() 与getDefaultTemplate()分别取得标签的开始模板与结束模板.
     * 便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
     * 一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date 目录下再建立子目录,如:WdatePicker,来区分不同的模板实现.
     * 在标签调用时,不需要分别设置template 与 openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
     */
    @StrutsTagAttribute(description="The template (other than default) to use for rendering the element")
    @Override
    public void setTemplate(String template) {
    	if(template != null && template.trim().length()>0) {
	        this.template = "date/"+template+"/date-close";
	        this.setOpenTemplate("date/"+template+"/date");
    	} else {
    		template = null;
    	}
    }


	
	/**
	 * 设置是否显示周
	 * @param isShowWeek --是否显示周
	 * @return void
	 */
	@StrutsTagAttribute(description = "是否显示周", type = "String")
	public void setIsShowWeek(String isShowWeek) {
		this.isShowWeek = isShowWeek;
	}

	/**
	 * 设置是否只读
	 * @param readOnly --是否只读
	 * @return void
	 */
	@StrutsTagAttribute(description = "是否只读", type = "String")
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * 设置日期格式
	 * @param dateFmt --日期格式
	 * @return void
	 */
	@StrutsTagAttribute(description = "日期组件格式", type = "String")
	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}

	

	/**
	 * 设置日期组件最大日期
	 * @param maxDate --最大日期
	 * @return void
	 */
	@StrutsTagAttribute(description = "日期组件最大日期", type = "String")
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	/**
	 * 设置日期组件最小日期
	 * @param minDate --最小日期
	 * @return void
	 */
	@StrutsTagAttribute(description = "日期组件最小日期", type = "String")
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	
	/**
	 * 设置日期组件禁止更改日期
	 * @param disabled --禁止更改日期
	 * @return void
	 */
	@StrutsTagAttribute(description = "禁止更改日期", type = "String")
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * 给input框增加自定义事件
	 * 例 onclick="alert('a');"
	 * @param script --方法名称
	 * @return void
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * 获取触发事件
	 * @return String
	 */
	public String getTriggerEvent() {
		return triggerEvent;
	}
	
	/**
	 * 获取触发事件
	 * @param triggerEvent
	 * @return void
	 */
	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
	
}
