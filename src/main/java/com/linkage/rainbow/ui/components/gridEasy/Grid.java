package com.linkage.rainbow.ui.components.gridEasy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

public class Grid extends ClosingUIBean{
	private static final String OPEN_TEMPLATE = "gridEasy/grid";
	
	private static final String CLOSE_TEMPLATE = "gridEasy/grid-close";
	
	private String skin = "blue";
	private String title;
	private String iconCls;
	private String width;
	private String height;
	private String collapsible;
	private String url;
	private String striped;
	private String sortName;
	private String fit;
	private String method;
	private String sortOrder;
	private String resizable;
	private String remoteSort;
	private String singleSelect;
	private String frozenColumns;
	private String columns;
	private String pageList;
	private String pageSize;
	private String rownumbers;
	private String pagination;
	private String toolbar;
	private String toolbarEn;
	private String addMethod;
	private String updateMethod;
	private String deleteMethod;
	private String searchMethod;
	private String onClickCell;
	private String queryParams;
	private String queryParamsData;
	private String paginationEn;
	private String fitHeight;
	private String pageInfo;
	private String pageInfoEn;
	private String exportMethod;
	
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
		if (skin != null) {
			addParameter("skin", findString(skin));
		}
		if (title != null) {
			addParameter("title", findString(title));
		}
		if (addMethod != null) {
			addParameter("addMethod", findString(addMethod));
		}
		if (updateMethod != null) {
			addParameter("updateMethod", findString(updateMethod));
		}
		if (deleteMethod != null) {
			addParameter("deleteMethod", findString(deleteMethod));
		}
		if (searchMethod != null) {
			addParameter("searchMethod", findString(searchMethod));
		}
		if (rownumbers != null) {
			addParameter("rownumbers", findString(rownumbers));
		}
		if (method != null) {
			addParameter("method", findString(method));
		}
		if (iconCls != null) {
			addParameter("iconCls", findString(iconCls));
		}
		if (width != null) {
			addParameter("width", findString(width));
		}
		if (height != null) {
			addParameter("height", findString(height));
		}
		if (collapsible != null) {
			addParameter("collapsible", findString(collapsible));
		}
		if (url != null) {
			addParameter("url", findString(url));
		}
		if (striped != null) {
			addParameter("striped", findString(striped));
		}
		if (sortName != null) {
			addParameter("sortName", findString(sortName));
		}
		if (fit != null) {
			addParameter("fit", findString(fit));
		}
		if (sortOrder != null) {
			addParameter("sortOrder", findString(sortOrder));
		}
		if (resizable != null) {
			addParameter("resizable", findString(resizable));
		}
		if (url != null) {
			addParameter("url", findString(url));
		}
		if (remoteSort != null) {
			addParameter("remoteSort", findString(remoteSort));
		}
		if (singleSelect != null) {
			addParameter("singleSelect", findString(singleSelect));
		}
		if (frozenColumns != null) {
			addParameter("frozenColumns", findString(frozenColumns));
		}
		if (columns != null) {
			addParameter("columns", findString(columns));
		}
		if (fit != null) {
			addParameter("fit", findString(fit));
		}
		if (pageList != null) {
			addParameter("pageList", findString(pageList));
		}
		if (pageSize != null) {
			addParameter("pageSize", findString(pageSize));
		}
		if (pageList != null) {
			addParameter("pageList", findString(pageList));
		}
		if (pagination != null) {
			addParameter("pagination", findString(pagination));
		}
		if (toolbar != null) {
			addParameter("toolbar", findString(toolbar));
		}
		if (onClickCell != null) {
			addParameter("onClickCell", findString(onClickCell));
		}
		if (queryParams != null) {
			addParameter("queryParams", findString(queryParams));
		}
		if (queryParamsData != null) {
			addParameter("queryParamsData", findString(queryParamsData));
		}
		if (toolbarEn != null) {
			addParameter("toolbarEn", findString(toolbarEn));
		}
		if (paginationEn != null) {
			addParameter("paginationEn", findString(paginationEn));
		}
		if (fitHeight != null) {
			addParameter("fitHeight", findString(fitHeight));
		}
		if (pageInfo != null) {
			addParameter("pageInfo", findString(pageInfo));
		}
		if (pageInfoEn != null) {
			addParameter("pageInfoEn", findString(pageInfoEn));
		}
		if (exportMethod != null) {
			addParameter("exportMethod", findString(exportMethod));
		}
		addParameter("tagUtil", new ComponentUtil(request));
	}
	
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "gridEasy/" + template + "/grid-close";
			this.setOpenTemplate("gridEasy/" + template + "/grid");
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


	public String getAddMethod() {
		return addMethod;
	}


	public void setAddMethod(String addMethod) {
		this.addMethod = addMethod;
	}


	public String getUpdateMethod() {
		return updateMethod;
	}


	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}


	public String getDeleteMethod() {
		return deleteMethod;
	}


	public void setDeleteMethod(String deleteMethod) {
		this.deleteMethod = deleteMethod;
	}


	public String getSearchMethod() {
		return searchMethod;
	}


	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}


	public String getRownumbers() {
		return rownumbers;
	}


	public void setRownumbers(String rownumbers) {
		this.rownumbers = rownumbers;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getIconCls() {
		return iconCls;
	}


	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
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


	public String getCollapsible() {
		return collapsible;
	}


	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getStriped() {
		return striped;
	}


	public void setStriped(String striped) {
		this.striped = striped;
	}


	public String getSortName() {
		return sortName;
	}


	public void setSortName(String sortName) {
		this.sortName = sortName;
	}


	public String getFit() {
		return fit;
	}


	public void setFit(String fit) {
		this.fit = fit;
	}


	public String getSortOrder() {
		return sortOrder;
	}


	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}


	public String getResizable() {
		return resizable;
	}


	public void setResizable(String resizable) {
		this.resizable = resizable;
	}


	public String getRemoteSort() {
		return remoteSort;
	}


	public void setRemoteSort(String remoteSort) {
		this.remoteSort = remoteSort;
	}


	public String getSingleSelect() {
		return singleSelect;
	}


	public void setSingleSelect(String singleSelect) {
		this.singleSelect = singleSelect;
	}


	public String getFrozenColumns() {
		return frozenColumns;
	}


	public void setFrozenColumns(String frozenColumns) {
		this.frozenColumns = frozenColumns;
	}


	public String getColumns() {
		return columns;
	}


	public void setColumns(String columns) {
		this.columns = columns;
	}


	public String getPageList() {
		return pageList;
	}


	public void setPageList(String pageList) {
		this.pageList = pageList;
	}


	public String getPageSize() {
		return pageSize;
	}


	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}


	public String getPagination() {
		return pagination;
	}


	public void setPagination(String pagination) {
		this.pagination = pagination;
	}


	public String getToolbar() {
		return toolbar;
	}


	public void setToolbar(String toolbar) {
		this.toolbar = toolbar;
	}


	public static String getOpenTemplate() {
		return OPEN_TEMPLATE;
	}


	public static String getCloseTemplate() {
		return CLOSE_TEMPLATE;
	}


	public String getOnClickCell() {
		return onClickCell;
	}


	public void setOnClickCell(String onClickCell) {
		this.onClickCell = onClickCell;
	}


	public String getQueryParams() {
		return queryParams;
	}


	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}


	public String getQueryParamsData() {
		return queryParamsData;
	}


	public void setQueryParamsData(String queryParamsData) {
		this.queryParamsData = queryParamsData;
	}


	public String getToolbarEn() {
		return toolbarEn;
	}


	public void setToolbarEn(String toolbarEn) {
		this.toolbarEn = toolbarEn;
	}


	public String getPaginationEn() {
		return paginationEn;
	}


	public void setPaginationEn(String paginationEn) {
		this.paginationEn = paginationEn;
	}


	public String getFitHeight() {
		return fitHeight;
	}


	public void setFitHeight(String fitHeight) {
		this.fitHeight = fitHeight;
	}


	public String getPageInfo() {
		return pageInfo;
	}


	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}


	public String getPageInfoEn() {
		return pageInfoEn;
	}


	public void setPageInfoEn(String pageInfoEn) {
		this.pageInfoEn = pageInfoEn;
	}


	public String getExportMethod() {
		return exportMethod;
	}


	public void setExportMethod(String exportMethod) {
		this.exportMethod = exportMethod;
	}
		
	
}
