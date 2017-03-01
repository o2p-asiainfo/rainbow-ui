package com.linkage.rainbow.ui.components.accordion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

public class Accordion extends UIComponentBase{

	/**
	 * 定义菜单的起始模杄1�7
	 */
	public static final String OPEN_TEMPLATE = "accordion/simple/accordion";//菜单标签模版
	/**
	 * 定义菜单标签的结束模杄1�7
	 */
	public static final String CLOSE_TEMPLATE = "accordion/simple/accordion-close";//菜单结束标签模版


	private String fit;

	String templateShort ;
	
	public Accordion(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
//		System.out.println("进入accordion的组件类");
	}
	


    /**
     * 指定模板
     * @author 陈亮 2009-02-11
     * @param template 模板各1�7.
     * 侄1�7丄1�7模板存放的完整路径是 template/xhtml/date/WdatePicker/date.ftl
     * 其中:
     * template 为模板存放主目录,对应的标签属性是: templateDir ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认倄1�7.
     * xhtml    为主题名秄1�7,对应的标签属性是: theme ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认倄1�7.
     * .ftl     为模板文件的后缀各1�7,取struts.propertites文件中配置的struts.ui.templateSuffix默认倄1�7.
     * date/WdatePicker/date 为模板名秄1�7,
     *          对应的标签属性是: template 丄1�7 openTemplate ,如果标签没有设置,则�1�7�过此类的getDefaultOpenTemplate() 与getDefaultTemplate()分别取得标签的开始模板与结束模板.
     * 便于模板存放的目录清晰与标签的调用方弄1�7,丄1�7个标签对应一个目录存傄1�7,例日期标签对应模板的目录为template/xhtml/date
     * 丄1�7个标签可能有种实现方弄1�7,即多个模板来实现,则在template/xhtml/date 目录下再建立子目彄1�7,妄1�7WdatePicker,来区分不同的模板实现.
     * 在标签调用时,不需要分别设置template 丄1�7 openTemplate指定新一组模杄1�7,只需要设置template倄1�7,传入子目录名秄1�7 WdatePicker 即可.
     */
    @StrutsTagAttribute(description="The template (other than default) to use for rendering the element")
    @Override
    public void setTemplate(String template) {
    	if(template != null && template.trim().length()>0) {
	        this.template = "accordion/"+template+"/accordion-close";
	        this.setOpenTemplate("accordion/"+template+"/accordion");
    	} else {
    		template = null;
    	}
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
	 * 将标签类的属性设置予组件类，提供予模板调甄1�7br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (fit != null)  
			addParameter("fit", findValue(fit));

	}
	public String getFit() {
		return fit;
	}
	public void setFit(String fit) {
		this.fit = fit;
	}


	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		return CLOSE_TEMPLATE;
	}
	
}
