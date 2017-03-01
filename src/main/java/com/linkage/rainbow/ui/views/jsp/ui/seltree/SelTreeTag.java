package com.linkage.rainbow.ui.views.jsp.ui.seltree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import com.linkage.rainbow.ui.components.seltree.SelTree;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class SelTreeTag extends AbstractUITag{

	/**
	 * @author wanglm7
	 * @createTime 2013-07-04 14:21:05
	 * @desc Ztree下拉树组件
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
	private String divWidth ;
	private String divHeight ;
	private String textWidth ;
	private String textHeight ;
	private String saveIdTextName;
	private String saveIdTextId;
	
	
	/**
	 * 标签的构造函数中,增加根据配置文件初始化默认值
	 * 默认配置文件路径为:com/linkage/rainbow/uidufault.properties
	 * 项目组使用时,修改的配置文件路径为:comm.properties
	 * 1.配置文件中UI组件默认值设置的参数名设置规则为rainbow.ui.为前缀,
	 *   接下来是标签文件中定义的标签名,
	 *   最后是标签文件中定义的此标签的属性名
	 *   例：
	 *   rainbow.ui.date.template=WdatePicker
	 * 2.在标签类构造函数中通过调用：
	 * DefaultSettings.setDefaultValue(this,"rainbow.ui.标签名");
	 * 实现此标签的默认值设置
	 * @author 陈亮 2009-3-18 
	 */
	public SelTreeTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.seltree");
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		SelTree tree = (SelTree)component ;
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
		tree.setDivHeight(divHeight);
		tree.setDivWidth(divWidth);
		tree.setTextHeight(textHeight);
		tree.setTextWidth(textWidth);
		tree.setSaveIdTextId(saveIdTextId);
		tree.setSaveIdTextName(saveIdTextName) ;
	}
	
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		return new SelTree(arg0,arg1,arg2);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getChkStyle() {
		return chkStyle;
	}

	public void setChkStyle(String chkStyle) {
		this.chkStyle = chkStyle;
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

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
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

	public String getTextclass() {
		return textclass;
	}

	public void setTextclass(String textclass) {
		this.textclass = textclass;
	}

	public String getDivclass() {
		return divclass;
	}

	public void setDivclass(String divclass) {
		this.divclass = divclass;
	}

}
