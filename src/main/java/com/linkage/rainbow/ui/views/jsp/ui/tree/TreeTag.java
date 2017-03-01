package com.linkage.rainbow.ui.views.jsp.ui.tree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.tree.Tree;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class TreeTag extends AbstractUITag {

	private static final long serialVersionUID = 1L;
	
	private String method;
	private String chkStyle;
	private String cascadeCheck;
	private String onlyLeafCheck;
	private String params;
	private String parentnode;
	private String divclass;
	private String skin ;
	private String divHeight;
	private String divWidth;
	private String textId ;
	private String textName ;
	private String callBackMethod ;
	
	private String attrId ; 
	private String attrPid ; 
	private String attrName ; 
	private String attrOpen ; 
	private String attrChkDisabled ; 
	private String attrChecked ; 
	private String attrIsParent ;
	private String attrIcon ;
	
	public TreeTag() {
		DefaultSettings.setDefaultValue(this, "rainbow.ui.tree");
	}
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		return new Tree(stack, request, response);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		Tree tree = (Tree) component;
		tree.setMethod(method);
		tree.setChkStyle(chkStyle);
		tree.setCascadeCheck(cascadeCheck);
		tree.setOnlyLeafCheck(onlyLeafCheck);
		tree.setParams(params);
		tree.setParentnode(parentnode);
		tree.setDivclass(divclass);
		tree.setSkin(skin);
		tree.setDivHeight(divHeight);
		tree.setDivWidth(divWidth);
		tree.setAttrId(attrId) ;
		tree.setAttrPid(attrPid) ;
		tree.setAttrName(attrName) ;
		tree.setAttrOpen(attrOpen) ;
		tree.setAttrChkDisabled(attrChkDisabled) ;
		tree.setAttrChecked(attrChecked) ;
		tree.setAttrIsParent(attrIsParent);
		tree.setAttrIcon(attrIcon);
		tree.setTextId(textId) ;
		tree.setTextName(textName) ;
		tree.setCallBackMethod(callBackMethod) ;
		
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCallBackMethod() {
		return callBackMethod;
	}

	public void setCallBackMethod(String callBackMethod) {
		this.callBackMethod = callBackMethod;
	}

	public String getDivHeight() {
		return divHeight;
	}

	public void setDivHeight(String divHeight) {
		this.divHeight = divHeight;
	}

	public String getDivWidth() {
		return divWidth;
	}

	public void setDivWidth(String divWidth) {
		this.divWidth = divWidth;
	}

	public String getTextId() {
		return textId;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
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

	

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getParentnode() {
		return parentnode;
	}

	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}


	public String getDivclass() {
		return divclass;
	}

	public void setDivclass(String divclass) {
		this.divclass = divclass;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getChkStyle() {
		return chkStyle;
	}

	public void setChkStyle(String chkStyle) {
		this.chkStyle = chkStyle;
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
	
}
