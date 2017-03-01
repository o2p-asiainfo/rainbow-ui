package com.linkage.rainbow.ui.components.selectedtree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;
import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "selectedtree", tldTagClass = "com.linkage.rainbow.ui.views.jsp.ui.selectedtree.SelectedTreeTag", description = "被选中下拉树菜单组件类")
public class SelectedTree extends UIComponentBase{

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
	
	/**
	 * 定义selectedtree组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "selectedtree/simple/selectedtree";
	/**
	 * 定义selectedtree组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "selectedtree/simple/selectedtree-close";

	
	public SelectedTree(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
		System.out.println("已经进入selectedtree组件类方法");
	}

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		if(method != null)
			addParameter("method",findString(method));
		if(chkStyle != null)
			addParameter("chkStyle",findString(chkStyle));
		if(cascadeCheck != null)
			addParameter("cascadeCheck",findString(cascadeCheck));
		if(onlyLeafCheck != null)
			addParameter("onlyLeafCheck",findString(onlyLeafCheck));
		if(params != null)
			addParameter("params",findString(params));
		if(operationName != null)
			addParameter("operationName",findString(operationName));
		if(textId != null)
			addParameter("textId",findString(textId));
		if(textclass != null)
			addParameter("textclass",findString(textclass));
		if(handleName != null)
			addParameter("handleName",findString(handleName));
		if(divclass != null)
			addParameter("divclass",findString(divclass));
		
		
		if (skin != null) {
			addParameter("skin", findString(skin));
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
		
		if (value != null) {
			addParameter("value", findString(value));
		}
		if (path != null) {
			addParameter("path", findString(path));
		}
		if (divWidth != null) {
			addParameter("divWidth", findString(divWidth));
		}
		if (divHeight != null) {
			addParameter("divHeight", findString(divHeight));
		}
		if (textWidth != null) {
			addParameter("textWidth", findString(textWidth));
		}
		if (textHeight != null) {
			addParameter("textHeight", findString(textHeight));
		}
		if (saveIdTextName != null) {
			addParameter("saveIdTextName", findString(saveIdTextName));
		}
		if (saveIdTextId != null) {
			addParameter("saveIdTextId", findString(saveIdTextId));
		}
		if (idvalue != null) {
			addParameter("idvalue", findString(idvalue));
		}
		if (initId != null) {
			addParameter("initId", findString(initId));
		}
		addParameter("tagUtil", new ComponentUtil(request));
	}
	
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	@Override
	protected String getDefaultTemplate() {
		return CLOSE_TEMPLATE;
	}
	
	/**
	 * 指定模板
	 * @author 陈亮 2009-02-11
	 * @param template 模板名. 例:一模板存放的完整路径是
	 *        template/xhtml/date/WdatePicker/date.ftl 其中: template
	 *        为模板存放主目录,对应的标签属性是: templateDir
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
	 *        xhtml 为主题名称,对应的标签属性是: theme
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值. .ftl
	 *        为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
	 *        date/WdatePicker/date 为模板名称, 对应的标签属性是: template 与 openTemplate
	 *        ,如果标签没有设置,则通过此类的getDefaultOpenTemplate()
	 *        与getDefaultTemplate()分别取得标签的开始模板与结束模板.
	 *        便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
	 *        一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date
	 *        目录下再建立子目录,如:WdatePicker,来区分不同的模板实现. 在标签调用时,不需要分别设置template 与
	 *        openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
	 */
	public void setTemplate(String template) {
		if(template != null && template.trim().length()>0) {
			this.template = "selectedtree/"+template+"/selectedtree-close";
		    this.setOpenTemplate("selectedtree/"+template+"/selectedtree");
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
