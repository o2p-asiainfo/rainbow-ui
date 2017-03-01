package com.linkage.rainbow.ui.components.SelectedSwitchOf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

public class SelectedSwitchOf extends ClosingUIBean {
	
	private static final String OPEN_TEMPLATE = "selectedSwitchOf/SelectedSwitchOf";
	
	private static final String CLOSE_TEMPLATE = "selectedSwitchOf/SelectedSwitchOf-close";
	
    private String id;
    private String valueAttr;
    private String name;
    private String skin="blue";
    private String change;
    private String classCss;
    private String state;
    

	
	public SelectedSwitchOf(ValueStack stack, HttpServletRequest request,
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
		if (id != null) {
			addParameter("id", findString(id));
		}
		if (change != null) {
			addParameter("change", findString(change));
		}
		if (classCss != null) {
			addParameter("classCss", findString(classCss));
		}
		if (state != null) {
			addParameter("state", findString(state));
		}
		if (name != null) {
			addParameter("name", findString(name));
		}
		if (valueAttr != null) {
			addParameter("valueAttr", findString(valueAttr));
		}
		if (skin != null) {
			addParameter("skin", findString(skin));
		}

		addParameter("tagUtil", new ComponentUtil(request));
	}
	
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "selectedSwitchOf/" + template + "/SelectedSwitchOf-close";
			this.setOpenTemplate("selectedSwitchOf/" + template + "/SelectedSwitchOf");
		} else {
			template = null;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

    

	public String getValueAttr() {
		return valueAttr;
	}

	public void setValueAttr(String valueAttr) {
		this.valueAttr = valueAttr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getClassCss() {
		return classCss;
	}

	public void setClassCss(String classCss) {
		this.classCss = classCss;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    


    

}
