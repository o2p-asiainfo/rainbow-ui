package com.linkage.rainbow.ui.components.randomimg;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * RandomImg<br>
 * 将标签属性转接给模板的中间过度类
 * <p>
 * @version 1.0
 */
public class RandomImg extends ClosingUIBean {
	
	/**
	 * 定义randomImg组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "randomImg/randomImg-close";//验证码标签模版
	/**
	 * 定义randomImg组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "randomImg/randomImg";//验证码标签模版
	
	private String width;
	private String height;
	private String type;
	private String codelength;

	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return RandomImg
	 */
	public RandomImg(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		
		if(width != null)
			addParameter("width",  findString(width));
		if(height != null)
			addParameter("height",  findString(height));
		if(type != null)
			addParameter("type",  findString(type));
		if(codelength != null)
			addParameter("codelength",  findString(codelength));
		addParameter("tagUtil", new ComponentUtil(request));
//		Map formMap = (Map)getParameters().get("form");
//		if(formMap !=null){
//			System.out.println("action:"+formMap.get("action"));
//		}
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
		// TODO Auto-generated method stub
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
	    	this.template = "randomImg/"+template+"/randomImg";
		    this.setOpenTemplate("randomImg/"+template+"/randomImg-close");
	    } else {
	    	template = null;
	    }
	}

	/**
	 * 设置验证码图片的宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置验证码图片的高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置验证码的类型
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 设置验证码中字数长度
	 * @param codelength the codelength to set
	 */
	public void setCodelength(String codelength) {
		this.codelength = codelength;
	}
	
}
