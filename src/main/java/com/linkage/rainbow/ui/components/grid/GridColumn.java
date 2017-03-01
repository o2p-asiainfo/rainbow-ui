package com.linkage.rainbow.ui.components.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class GridColumn extends ClosingUIBean {
	
	private static final String OPEN_TEMPLATE = "grid/gridColumn";
	
	private static final String CLOSE_TEMPLATE = "grid/gridColumn-close";
	
	private String index;
	private String title;//The column title text. String
	private String field;//The column field name. String
	private String width;//The width of column. number
	private String rowspan;//Indicate how many rows a cell should take up. number
	private String colspan;//Indicate how many columns a cell should take up. number
	private String align;//Indicate how to align the column data. 'left','right','center' can be used.
	private String sortable;//True to allow the column can be sorted. true/false
	private String checkbox;//True to show a checkbox. true/false
	private String formatter;

	public GridColumn(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (title != null) {
			addParameter("title", findString(title));
		}
		if (field != null) {
			addParameter("field", findString(field));
		}
		if (width != null) {
			addParameter("width", findString(width));
		}
		if (rowspan != null) {
			addParameter("rowspan", findString(rowspan));
		}
		if (colspan != null) {
			addParameter("colspan", findString(colspan));
		}
		if (align != null) {
			addParameter("align", findString(align));
		}
		if (sortable != null) {
			addParameter("sortable", findString(sortable));
		}
		if (checkbox != null) {
			addParameter("checkbox", findString(checkbox));
		}
		if (formatter != null) {
			addParameter("formatter", findString(formatter));
		}
		if (index != null) {
			addParameter("index", index);
		}
	
	}
	
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "grid/" + template + "/gridColumn-close";
			this.setOpenTemplate("grid/" + template + "/gridColumn");
		} else {
			template = null;
		}
	}

	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	@Override
	protected String getDefaultTemplate() {
		return CLOSE_TEMPLATE;
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
