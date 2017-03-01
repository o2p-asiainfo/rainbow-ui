package com.linkage.rainbow.ui.views.jsp.ui.selectedtree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import com.linkage.rainbow.ui.components.selectedtree.SelectedTree;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class SelectedTreeTag extends AbstractUITag{

	/**
	 * @author wanglm7
	 * @createTime 2013-07-04 14:21:05
	 * @desc Ztree已被选中的下拉树组件
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String method;
	private String params;
	private String chkStyle;
	private String cascadeCheck;
	private String onlyLeafCheck;
	private String operationName ;
	private String textId ;
	private String textclass ;
	private String handleName ;
	private String divclass ;
	private String attrId ; 
	private String attrPid ; 
	private String attrName ; 
	private String attrOpen ; 
	private String attrChkDisabled ; 
	private String attrChecked ; 
	private String attrIsParent ;
	private String attrIcon ;
	private String skin ;
	private String value ;
	private String path ;
	
	private String divWidth ;
	private String divHeight ;
	private String textWidth ;
	private String textHeight ;
	private String saveIdTextName;
	private String saveIdTextId;
	private String idvalue ; 
	private String initId ;
	
	
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		return new SelectedTree(arg0,arg1,arg2);
	}

	
	public SelectedTreeTag() {
		DefaultSettings.setDefaultValue(this,"rainbow.ui.selectedtree");
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		SelectedTree tree = (SelectedTree)component ;
		tree.setMethod(method);
		tree.setParams(params);
		tree.setChkStyle(chkStyle);
		tree.setCascadeCheck(cascadeCheck);
		tree.setOnlyLeafCheck(onlyLeafCheck);
		tree.setOperationName(operationName);
		tree.setTextId(textId);
		tree.setTextclass(textclass);
		tree.setHandleName(handleName);
		tree.setDivclass(divclass);
		
		tree.setSkin(skin);
		tree.setAttrId(attrId) ;
		tree.setAttrPid(attrPid) ;
		tree.setAttrName(attrName) ;
		tree.setAttrOpen(attrOpen) ;
		tree.setAttrChkDisabled(attrChkDisabled) ;
		tree.setAttrChecked(attrChecked) ;
		tree.setAttrIsParent(attrIsParent);
		tree.setAttrIcon(attrIcon);
		tree.setValue(value) ;
		tree.setPath(path) ;
		tree.setDivHeight(divHeight);
		tree.setDivWidth(divWidth);
		tree.setTextHeight(textHeight);
		tree.setTextWidth(textWidth);
		tree.setSaveIdTextId(saveIdTextId);
		tree.setSaveIdTextName(saveIdTextName) ;
		tree.setIdvalue(idvalue);
		tree.setInitId(initId) ;
	}
	
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getChkStyle() {
		return chkStyle;
	}

	public void setChkStyle(String chkStyle) {
		this.chkStyle = chkStyle;
	}

	public String getDivWidth() {
		return divWidth;
	}


	public void setDivWidth(String divWidth) {
		this.divWidth = divWidth;
	}


	public String getDivHeight() {
		return divHeight;
	}


	public void setDivHeight(String divHeight) {
		this.divHeight = divHeight;
	}


	public String getTextWidth() {
		return textWidth;
	}


	public void setTextWidth(String textWidth) {
		this.textWidth = textWidth;
	}


	public String getTextHeight() {
		return textHeight;
	}


	public void setTextHeight(String textHeight) {
		this.textHeight = textHeight;
	}


	public String getSaveIdTextName() {
		return saveIdTextName;
	}


	public void setSaveIdTextName(String saveIdTextName) {
		this.saveIdTextName = saveIdTextName;
	}


	public String getSaveIdTextId() {
		return saveIdTextId;
	}


	public void setSaveIdTextId(String saveIdTextId) {
		this.saveIdTextId = saveIdTextId;
	}


	public String getCascadeCheck() {
		return cascadeCheck;
	}

	public void setCascadeCheck(String cascadeCheck) {
		this.cascadeCheck = cascadeCheck;
	}

	public String getOnlyLeafCheck() {
		return onlyLeafCheck;
	}

	public void setOnlyLeafCheck(String onlyLeafCheck) {
		this.onlyLeafCheck = onlyLeafCheck;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getTextId() {
		return textId;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public String getTextclass() {
		return textclass;
	}

	public void setTextclass(String textclass) {
		this.textclass = textclass;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getDivclass() {
		return divclass;
	}

	public void setDivclass(String divclass) {
		this.divclass = divclass;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrPid() {
		return attrPid;
	}

	public void setAttrPid(String attrPid) {
		this.attrPid = attrPid;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrOpen() {
		return attrOpen;
	}

	public void setAttrOpen(String attrOpen) {
		this.attrOpen = attrOpen;
	}

	public String getAttrChkDisabled() {
		return attrChkDisabled;
	}

	public void setAttrChkDisabled(String attrChkDisabled) {
		this.attrChkDisabled = attrChkDisabled;
	}

	public String getAttrChecked() {
		return attrChecked;
	}

	public void setAttrChecked(String attrChecked) {
		this.attrChecked = attrChecked;
	}

	public String getAttrIsParent() {
		return attrIsParent;
	}

	public void setAttrIsParent(String attrIsParent) {
		this.attrIsParent = attrIsParent;
	}

	public String getAttrIcon() {
		return attrIcon;
	}

	public void setAttrIcon(String attrIcon) {
		this.attrIcon = attrIcon;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getIdvalue() {
		return idvalue;
	}


	public void setIdvalue(String idvalue) {
		this.idvalue = idvalue;
	}


	public String getInitId() {
		return initId;
	}


	public void setInitId(String initId) {
		this.initId = initId;
	}

	
}
