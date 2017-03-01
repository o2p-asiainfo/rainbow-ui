package com.linkage.rainbow.ui.components.gridTree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class GridTreeColumn extends ClosingUIBean{
	
	private static final String OPEN_TEMPLATE = "gridTree/gridColumn";
	
	private static final String CLOSE_TEMPLATE = "gridTree/gridColumn-close";
	private String skin = "blue";
	private String index;
	private String title;
	private String field;
	private String hidden;
	private String width;
	private String formatter;
	private String align;
	private String editor;
	

	
	public GridTreeColumn(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
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
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (skin != null) {
			addParameter("skin", skin);
		}
		if (index != null) {
			addParameter("index", index);
		}
		if (editor != null) {
			addParameter("editor", editor);
		}
		if (align != null) {
			addParameter("align", align);
		}
		if (title != null) {
			addParameter("title", title);
		}
		if (field != null) {
			addParameter("field", field);
		}
		if (hidden != null) {
			addParameter("hidden", hidden);
		}
		if (width != null) {
			addParameter("width", width);
		}
		if (formatter != null) {
			addParameter("formatter", formatter);
		}
	
	}
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "gridTree/" + template + "/gridColumn-close";
			this.setOpenTemplate("gridTree/" + template + "/gridColumn");
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

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getIndex() {
		return index;
	}
    
	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
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

	public static String getOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	public static String getCloseTemplate() {
		return CLOSE_TEMPLATE;
	}

}
