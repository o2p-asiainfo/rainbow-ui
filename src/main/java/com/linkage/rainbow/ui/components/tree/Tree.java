package com.linkage.rainbow.ui.components.tree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 树形菜单组件
 * @author zhaixh
 * @date 2011-5-26上午10:16:37
 * @version 1.0
 * 
 * @update wanglm7
 */
public class Tree extends ClosingUIBean {
	
	private static final String OPEN_TEMPLATE = "tree/simple/tree";
		
	private static final String CLOSE_TEMPLATE = "tree/simple/tree-close";
	
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
	
	public Tree(ValueStack stack, HttpServletRequest request,
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
		if (method != null) {
			addParameter("method", findString(method));
		}
		if (cascadeCheck != null) {
			addParameter("cascadeCheck", findString(cascadeCheck));
		}
		if (onlyLeafCheck != null) {
			addParameter("onlyLeafCheck", findString(onlyLeafCheck));
		}
		if (chkStyle != null) {
			addParameter("chkStyle", findString(chkStyle));
		}
		if (params != null) {
			addParameter("params", findString(params));
		}
		if (parentnode != null) {
			addParameter("parentnode", findString(parentnode));
		}
		if (divclass != null) {
			addParameter("divclass", findString(divclass));
		}
		if (skin != null) {
			addParameter("skin", findString(skin));
		}
		
		if (divHeight != null) {
			addParameter("divHeight", findString(divHeight));
		}
		if (divWidth != null) {
			addParameter("divWidth", findString(divWidth));
		}
		
		if (attrId != null) {
			addParameter("attrId", findString(attrId));
		}
		if (attrPid != null) {
			addParameter("attrPid", findString(attrPid));
		}
		if (attrName != null) {
			addParameter("attrName", findString(attrName));
		}
		if (attrOpen != null) {
			addParameter("attrOpen", findString(attrOpen));
		}
		if (attrChkDisabled != null) {
			addParameter("attrChkDisabled", findString(attrChkDisabled));
		}
		if (attrChecked != null) {
			addParameter("attrChecked", findString(attrChecked));
		}
		if (attrIsParent != null) {
			addParameter("attrIsParent", findString(attrIsParent));
		}
		if (attrIcon != null) {
			addParameter("attrIcon", findString(attrIcon));
		}
		
		if (textId != null) {
			addParameter("textId", findString(textId));
		}
		if (textName != null) {
			addParameter("textName", findString(textName));
		}
		
		if (callBackMethod != null) {
			addParameter("callBackMethod", findString(callBackMethod));
		}
		addParameter("tagUtil", new ComponentUtil(request));
	}
	
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "tree/" + template + "/tree-close";
			this.setOpenTemplate("tree/" + template + "/tree");
		} else {
			template = null;
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTextId() {
		return textId;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public String getCallBackMethod() {
		return callBackMethod;
	}

	public void setCallBackMethod(String callBackMethod) {
		this.callBackMethod = callBackMethod;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
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
