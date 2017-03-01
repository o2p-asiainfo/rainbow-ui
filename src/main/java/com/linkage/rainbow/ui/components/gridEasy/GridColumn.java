package com.linkage.rainbow.ui.components.gridEasy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class GridColumn extends ClosingUIBean{
	
	private static final String OPEN_TEMPLATE = "gridEasy/gridColumn";
	
	private static final String CLOSE_TEMPLATE = "gridEasy/gridColumn-close";
	private String skin = "blue";
	private String index;
	private String title;
	private String field;
	private String hidden;
	private String width;
	private String formatter;
	private String align;
	private String formatterMethod;
	
	public GridColumn(ValueStack stack, HttpServletRequest request,
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
			addParameter("skin", findString(skin));
		}
		if (index != null) {
			addParameter("index", findString(index));
		}
		if (align != null) {
			addParameter("align", findString(align));
		}
		if (title != null) {
			addParameter("title", findString(title));
		}
		if (field != null) {
			addParameter("field", findString(field));
		}
		if (hidden != null) {
			addParameter("hidden", findString(hidden));
		}
		if (width != null) {
			addParameter("width", findString(width));
		}
		if (formatter != null) {
			addParameter("formatter", findString(formatter));
		}
		if (formatterMethod != null) {
			addParameter("formatterMethod", findString(formatterMethod));
		}
	}
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "gridEasy/" + template + "/gridColumn-close";
			this.setOpenTemplate("gridEasy/" + template + "/gridColumn");
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

	public static String getOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	public static String getCloseTemplate() {
		return CLOSE_TEMPLATE;
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
