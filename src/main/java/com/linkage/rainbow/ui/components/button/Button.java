package com.linkage.rainbow.ui.components.button;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * Button按钮组件类<br>
 * 这里是类的描述区域，概括出该的主要功能或者类的使用范围、注意事项等
 * <p>
 * @version 1.0
 * @author laozhu Jul 4, 2013
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */
@StrutsTag(name = "button", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.button.ButtonTag", description = "按钮组件类")
public class Button extends UIComponentBase {
	
	/**
	 * 定义按钮标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "button/button"; 
	/**
	 * 定义按钮标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "button/button-close"; 
	
	
	private String id ;
	private String name ;
	private String title ;
	private String text ;
	private String shape ;
	private String onclick ;
	private String disabled ;
	public Button(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		if(id != null)
			addParameter("id",  findString(id));
		if(name != null)
			addParameter("name",  findString(name));
		if(title != null)
			addParameter("title",  findString(title));
		if(text != null)
			addParameter("text",  findString(text));
		if(shape != null)
			addParameter("shape",  findString(shape));
		if(onclick != null)
			addParameter("onclick",  findString(onclick));
		if(disabled != null)
			addParameter("disabled",  findString(disabled));
	 }
	
	
	@Override
	public String getDefaultOpenTemplate() {
		 
		return OPEN_TEMPLATE;
	}

	@Override
	protected String getDefaultTemplate() {
		return CLOSE_TEMPLATE;
	}

	 @Override
    public void setTemplate(String template) {
    	if(template != null && template.trim().length()>0) {
	        this.template = "button/"+template+"/button-close";
	        this.setOpenTemplate("button/"+template+"/button");
    	} else {
    		template = null;
    	}
    }
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	 

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

}
