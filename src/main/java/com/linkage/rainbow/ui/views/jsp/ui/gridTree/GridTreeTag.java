package com.linkage.rainbow.ui.views.jsp.ui.gridTree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.gridTree.GridTree;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class GridTreeTag extends AbstractUITag{
		
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
		private String addMethod;
		private String updateMethod;
		private String deleteMethod;
		private String searchMethod;
		private String animate;
		private String idField;
		private String treeField;
		private String fitColumns;
		private String autoRowHeight;
		private String onAfterEdit;
		
		
		public GridTreeTag() {
			DefaultSettings.setDefaultValue(this, "rainbow.ui.gridTree");
		}
		
		@Override
		public Component getBean(ValueStack stack, HttpServletRequest request,
				HttpServletResponse response) {
			return new GridTree(stack, request, response);
		}
	    
		public String getSkin() {
			return skin;
		}

		public void setSkin(String skin) {
			this.skin = skin;
		}

		public String getOnAfterEdit() {
			return onAfterEdit;
		}

		public void setOnAfterEdit(String onAfterEdit) {
			this.onAfterEdit = onAfterEdit;
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
		
		public String getAnimate() {
			return animate;
		}

		public void setAnimate(String animate) {
			this.animate = animate;
		}

		public String getIdField() {
			return idField;
		}

		public void setIdField(String idField) {
			this.idField = idField;
		}

		public String getTreeField() {
			return treeField;
		}

		public void setTreeField(String treeField) {
			this.treeField = treeField;
		}

		public String getFitColumns() {
			return fitColumns;
		}

		public void setFitColumns(String fitColumns) {
			this.fitColumns = fitColumns;
		}

		public String getAutoRowHeight() {
			return autoRowHeight;
		}

		public void setAutoRowHeight(String autoRowHeight) {
			this.autoRowHeight = autoRowHeight;
		}

		@Override
		protected void populateParams() {
			super.populateParams();
			GridTree gridTree = (GridTree) component;
			gridTree.setSkin(skin);
			gridTree.setAddMethod(addMethod);
			gridTree.setSearchMethod(searchMethod);
			gridTree.setDeleteMethod(deleteMethod);
			gridTree.setUpdateMethod(updateMethod);
			gridTree.setRownumbers(rownumbers);
			gridTree.setTitle(title);
			gridTree.setMethod(method);
			gridTree.setIconCls(iconCls);
			gridTree.setWidth(width);
			gridTree.setHeight(height);
			gridTree.setCollapsible(collapsible);
			gridTree.setUrl(url);
			gridTree.setStriped(striped);
			gridTree.setSortName(sortName);
			gridTree.setFit(fit);
			gridTree.setSortOrder(sortOrder);
			gridTree.setResizable(resizable);
			gridTree.setRemoteSort(remoteSort);
			gridTree.setSingleSelect(singleSelect);
			gridTree.setFrozenColumns(frozenColumns);
			gridTree.setColumns(columns);
			gridTree.setPageList(pageList);
			gridTree.setPageSize(pageSize);
			gridTree.setPagination(pagination);
			gridTree.setToolbar(toolbar);
			gridTree.setAnimate(animate);
			gridTree.setIdField(idField);
			gridTree.setTreeField(treeField);
			gridTree.setFitColumns(fitColumns);
			gridTree.setAutoRowHeight(autoRowHeight);
			gridTree.setOnAfterEdit(onAfterEdit);
		}

		
}
