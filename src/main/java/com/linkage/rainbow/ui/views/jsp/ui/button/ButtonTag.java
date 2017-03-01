package com.linkage.rainbow.ui.views.jsp.ui.button;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.button.Button;
import com.linkage.rainbow.ui.components.date.Date;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class ButtonTag extends UITagBase {
	
	private String id ;
	private String name ;
	private String title ;
	private String text ;
	private String shape ;
	private String onclick ;
	private String disabled ;
	public ButtonTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.button");
	}

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		// TODO Auto-generated method stub
		return new Button(stack, req, res);
	}


	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Button button = (Button) component;
		button.setTitle(title);
		button.setId(id);
		button.setText(text);
		button.setShape(shape);
		button.setName(name) ;
		button.setOnclick(onclick);
		button.setDisabled(disabled==null?"false":disabled);
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



 

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
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



	public void setText(String text) {
		this.text = text;
	}



	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getShape() {
		return shape;
	}



	public void setShape(String shape) {
		this.shape = shape;
	}

}
