package com.linkage.rainbow.ui.components.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 数据表格组件
 * @author zhaixh
 * @date 2011-3-20下午05:55:58
 * @version 1.0
 */
public class Grid extends ClosingUIBean {
	
	private static final String OPEN_TEMPLATE = "grid/grid";
	
	private static final String CLOSE_TEMPLATE = "grid/grid-close";
	
	private String title;//The datagrid panel title text. String 
	private String width;// The width of datagrid width. number
	private String height;// The height of datagrid height. number
	private String skin;// The skin of datagrid. String
	private String border;// True to show datagrid panel border. true/false
	private String columns;// The datagrid columns config object, see column properties for more details. Array[[]]
	private String frozenColumns;// Same as the columns property, but the these columns will be frozen on left. Array[[]]
	private String striped;// True to stripe the rows. true/false
	private String method;// The method type to request remote data. String
	private String nowrap;// True to display data in one line. true/false
	private String idField;// Indicate which field is an identity field. String
	private String url;// A URL to request data from remote site. String
	private String loadMsg;// When loading data from remote site, show a prompt message. String
	private String pagination;// True to show a pagination toolbar on datagrid bottom. true/false
	private String rownumbers;// True to show a row number column. true/false
	private String singleSelect;// True to allow selecting only one row. true/false
	private String fit;// True to set size to fit it's parent container. true/false
	private String pageNumber;// When set pagination property, initialize the page number. number
	private String pageSize;// When set pagination property, initialize the page size. number
	private String pageList;// When set pagination property, initialize the page size selecting list. Array[]
	private String queryParams;// When request remote data, sending additional parameters also. Object{}
	private String sortName;// Defines which column can be sorted. String
	private String sortOrder;// Defines the column sort order, can only be 'asc' or 'desc'.  asc/desc
	private String fitColumns;

	
	public Grid(ValueStack stack, HttpServletRequest request,
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
		if (width != null) {
			addParameter("width", findString(width));
		}
		if (height != null) {
			addParameter("height", findString(height));
		}
		if (title != null) {
			addParameter("title", findString(title));
		}
		if (skin != null) {
			addParameter("skin", findString(skin));
		}
		if (border != null) {
			addParameter("border", findString(border));
		}
		if (columns != null) {
			addParameter("columns", findString(columns));
		}
		if (frozenColumns != null) {
			addParameter("frozenColumns", findString(frozenColumns));
		}
		if (striped != null) {
			addParameter("striped", findString(striped));
		}
		if (method != null) {
			addParameter("method", findString(method));
		}
		if (nowrap != null) {
			addParameter("nowrap", findString(nowrap));
		}
		if (idField != null) {
			addParameter("idField", findString(idField));
		}
		if (url != null) {
			addParameter("url", findString(url));
		}
		if (loadMsg != null) {
			addParameter("loadMsg", findString(loadMsg));
		}
		if (pagination != null) {
			addParameter("pagination", findString(pagination));
		}
		if (rownumbers != null) {
			addParameter("rownumbers", findString(rownumbers));
		}
		if (singleSelect != null) {
			addParameter("singleSelect", findString(singleSelect));
		}
		if (fit != null) {
			addParameter("fit", findString(fit));
		}
		if (pageNumber != null) {
			addParameter("pageNumber", findString(pageNumber));
		}
		if (pageSize != null) {
			addParameter("pageSize", findString(pageSize));
		}
		if (pageList != null) {
			addParameter("pageList", findString(pageList));
		}
		if (queryParams != null) {
			addParameter("queryParams", findString(queryParams));
		}
		if (sortName != null) {
			addParameter("sortName", findString(sortName));
		}
		if (sortOrder != null) {
			addParameter("sortOrder", findString(sortOrder));
		}
		if (fitColumns != null) {
			addParameter("fitColumns", findString(fitColumns));
		}
		addParameter("tagUtil", new ComponentUtil(request));
	}
	
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "grid/" + template + "/grid-close";
			this.setOpenTemplate("grid/" + template + "/grid");
		} else {
			template = null;
		}
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

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getFrozenColumns() {
		return frozenColumns;
	}

	public void setFrozenColumns(String frozenColumns) {
		this.frozenColumns = frozenColumns;
	}

	public String getStriped() {
		return striped;
	}

	public void setStriped(String striped) {
		this.striped = striped;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getNowrap() {
		return nowrap;
	}

	public void setNowrap(String nowrap) {
		this.nowrap = nowrap;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLoadMsg() {
		return loadMsg;
	}

	public void setLoadMsg(String loadMsg) {
		this.loadMsg = loadMsg;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getRownumbers() {
		return rownumbers;
	}

	public void setRownumbers(String rownumbers) {
		this.rownumbers = rownumbers;
	}

	public String getSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(String singleSelect) {
		this.singleSelect = singleSelect;
	}

	public String getFit() {
		return fit;
	}

	public void setFit(String fit) {
		this.fit = fit;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageList() {
		return pageList;
	}

	public void setPageList(String pageList) {
		this.pageList = pageList;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFitColumns() {
		return fitColumns;
	}

	public void setFitColumns(String fitColumns) {
		this.fitColumns = fitColumns;
	}

}
