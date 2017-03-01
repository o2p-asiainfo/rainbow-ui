package com.linkage.rainbow.ui.views.jsp.ui.gridEasy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.gridEasy.GridColumn;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class GridColumnTag extends AbstractUITag{
	private static final long serialVersionUID = 1L;
	
	private String skin = "blue";
	private String index;
	private String title;
	private String field;
	private String hidden;
	private String width;
	private String formatter;
	private String align;
	private String formatterMethod;
	
	public GridColumnTag() {
		DefaultSettings.setDefaultValue(this, "rainbow.ui.grid");
	}
	
	public Component getBean(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		return new GridColumn(stack, request, response);
	}
	

	protected void populateParams() {
		super.populateParams();
		GridColumn col = (GridColumn) component;
		col.setSkin(skin);
		col.setIndex(index);
		col.setTitle(title);
		col.setField(field);
		col.setWidth(width);	
		col.setHidden(hidden);
		col.setAlign(align);
		col.setFormatter(formatter);
		col.setFormatterMethod(formatterMethod);
	}
	
	
	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getFormatterMethod() {
		return formatterMethod;
	}

	public void setFormatterMethod(String formatterMethod) {
		this.formatterMethod = formatterMethod;
	}
	
	
	
}
