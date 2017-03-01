package com.linkage.rainbow.ui.views.jsp.ui.gridEasy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.gridEasy.Grid;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class GridTag extends AbstractUITag{
	
	private static final long serialVersionUID = 1L;
	
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
	public GridTag() {
		DefaultSettings.setDefaultValue(this, "rainbow.ui.grid");
	}
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		return new Grid(stack, request, response);
	}
    
	public String getOnClickCell() {
		return onClickCell;
	}

	public void setOnClickCell(String onClickCell) {
		this.onClickCell = onClickCell;
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

	@Override
	protected void populateParams() {
		super.populateParams();
		Grid grid = (Grid) component;
		grid.setSkin(skin);
		grid.setAddMethod(addMethod);
		grid.setSearchMethod(searchMethod);
		grid.setDeleteMethod(deleteMethod);
		grid.setUpdateMethod(updateMethod);
		grid.setRownumbers(rownumbers);
		grid.setTitle(title);
		grid.setMethod(method);
		grid.setIconCls(iconCls);
		grid.setWidth(width);
		grid.setHeight(height);
		grid.setCollapsible(collapsible);
		grid.setUrl(url);
		grid.setStriped(striped);
		grid.setSortName(sortName);
		grid.setFit(fit);
		grid.setSortOrder(sortOrder);
		grid.setResizable(resizable);
		grid.setRemoteSort(remoteSort);
		grid.setSingleSelect(singleSelect);
		grid.setFrozenColumns(frozenColumns);
		grid.setColumns(columns);
		grid.setPageList(pageList);
		grid.setPageSize(pageSize);
		grid.setPagination(pagination);
		grid.setToolbar(toolbar);
		grid.setOnClickCell(onClickCell);
		grid.setQueryParams(queryParams);
		grid.setQueryParamsData(queryParamsData);
		grid.setToolbarEn(toolbarEn);
		grid.setPaginationEn(paginationEn);
		grid.setFitHeight(fitHeight);
		grid.setPageInfo(pageInfo);
		grid.setPageInfoEn(pageInfoEn);
		grid.setExportMethod(exportMethod);
	}

	
}
