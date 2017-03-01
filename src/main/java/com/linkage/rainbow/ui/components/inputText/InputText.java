package com.linkage.rainbow.ui.components.inputText;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;

public class InputText extends UIComponentBase {
	 
	public static final String OPEN_TEMPLATE = "inputText/inputText";// 
	public static final String CLOSE_TEMPLATE = "inputText/inputText-close";// 
	
	private String id ;
	private String name ;
	private String textSize ;
	private String value ;
	private String type ;
	private String readonly ;
	private String disabled ;
	private String skin ;
	private String style;
	public InputText(ValueStack stack, HttpServletRequest request,
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
		if(textSize != null)
			addParameter("textSize",  findString(textSize));
		if(value != null)
			addParameter("value",  findString(value));
		if(readonly != null)
			addParameter("readonly",  findString(readonly));
		if(disabled != null)
			addParameter("disabled",  findString(disabled));
		if(skin != null)
			addParameter("skin",  findString(skin));
		if(type != null)
			addParameter("type",  findString(type));
		
		if(onchange != null)
			addParameter("onchange",  findString(onchange));
		if(onkeyup != null)
			addParameter("onkeyup",  findString(onkeyup));
		if(onblur != null)
			addParameter("onblur",  findString(onblur));
		if(onfocus != null)
			addParameter("onfocus",  findString(onfocus));
		if(style != null)
			addParameter("style",  findString(style));
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
		        this.template = "inputText/"+template+"/inputText-close";
		        this.setOpenTemplate("inputText/"+template+"/inputText");
	    	} else {
	    		template = null;
	    	}
	    }

	 
 
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getTextSize() {
		return textSize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setTextSize(String textSize) {
		this.textSize = textSize;
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

	 
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

}
