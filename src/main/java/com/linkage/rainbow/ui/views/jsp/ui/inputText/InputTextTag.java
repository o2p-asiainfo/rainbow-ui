package com.linkage.rainbow.ui.views.jsp.ui.inputText;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
  
import com.linkage.rainbow.ui.components.inputText.InputText;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class InputTextTag extends UITagBase {
	private String id ;
	private String name ;
	private String textSize ;
	private String value ;
	private String readonly ;
	private String disabled ;
	private String skin ;
    private String type ;
    private String style;
	public InputTextTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.inputText");
	}
	
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		// TODO Auto-generated method stub
		return new InputText(stack, req, res);
	}
	
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();
        InputText inputText = (InputText) component;
	    inputText.setId(id);
		inputText.setName(name) ;
		inputText.setTextSize(textSize);
		inputText.setValue(value) ;
		inputText.setReadonly(readonly) ;
		inputText.setDisabled(disabled) ;
		inputText.setSkin(skin) ;
		inputText.setType(type) ;
		inputText.setOnchange(onchange);
		inputText.setOnkeyup(onkeyup);
		inputText.setOnblur(onblur);
		inputText.setOnfocus(onfocus);
		inputText.setStyle(style);
	}
	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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
   
	public String getSkin() {
		return skin;
	}


	public void setSkin(String skin) {
		this.skin = skin;
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


	public String getTextSize() {
		return textSize;
	}


	public void setTextSize(String textSize) {
		this.textSize = textSize;
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
