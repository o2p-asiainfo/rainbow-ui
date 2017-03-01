package com.linkage.rainbow.ui.views.jsp.ui.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.grid.GridColumn;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class GridColumnTag extends AbstractUITag {

	private static final long serialVersionUID = 1L;
	
	private String index;
	private String title;//The column title text. String
	private String field;//The column field name. String
	private String width;//The width of column. number
	private String rowspan;//Indicate how many rows a cell should take up. number
	private String colspan;//Indicate how many columns a cell should take up. number
	private String align;//Indicate how to align the column data. 'left','right','center' can be used.
	private String sortable;//True to allow the column can be sorted. true/false
	private String checkbox;//True to show a checkbox. true/false
	private String formatter;//

	public GridColumnTag() {
		DefaultSettings.setDefaultValue(this, "rainbow.ui.grid");
	}
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		return new GridColumn(stack, request, response);
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		GridColumn col = (GridColumn) component;
		col.setIndex(index);
		col.setWidth(width);
		col.setTitle(title);
		col.setField(field);
		col.setAlign(align);
		col.setRowspan(rowspan);
		col.setColspan(colspan);
		col.setSortable(sortable);
		col.setCheckbox(checkbox);
		col.setFormatter(formatter);
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
