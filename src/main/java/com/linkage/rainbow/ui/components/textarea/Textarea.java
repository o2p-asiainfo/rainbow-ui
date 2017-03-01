package com.linkage.rainbow.ui.components.textarea;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;

public class Textarea extends UIComponentBase {

	public static final String OPEN_TEMPLATE = "textarea/textarea";
	 
	public static final String CLOSE_TEMPLATE = "textarea/textarea-close";
	
	
	private String id ;
	private String name ; 
	private String value ;
	private String width ;
	private String height;
	private String skin ;
	
	public Textarea(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
//		System.out.println("已经进入文本域组件类");
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
		if(width != null)
			addParameter("width",  findString(width));
		if(height != null)
			addParameter("height",  findString(height));
		if(value != null)
			addParameter("value",  findString(value));
		if(skin != null)
			addParameter("skin",  findString(skin));
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
		        this.template = "textarea/"+template+"/textarea-close";
		        this.setOpenTemplate("textarea/"+template+"/textarea");
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

	public String getSkin() {
		return skin;
	}


	public void setSkin(String skin) {
		this.skin = skin;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
