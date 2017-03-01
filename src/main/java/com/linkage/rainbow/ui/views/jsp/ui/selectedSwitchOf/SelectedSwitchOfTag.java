package com.linkage.rainbow.ui.views.jsp.ui.selectedSwitchOf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.SelectedSwitchOf.SelectedSwitchOf;
import com.linkage.rainbow.ui.components.switchOf.SwitchOf;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class SelectedSwitchOfTag extends AbstractUITag {

	private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String valueAttr;
    private String skin="blue";
    private String change;
    private String classCss;
    private String state;

	public SelectedSwitchOfTag() {
		DefaultSettings.setDefaultValue(this, "rainbow.ui.selectedSwitchOf");
	}

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		return new SelectedSwitchOf(stack, request, response);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		SelectedSwitchOf selectedSwitchOf = (SelectedSwitchOf) component;
		selectedSwitchOf.setId(id);
		selectedSwitchOf.setValueAttr(valueAttr);
		selectedSwitchOf.setName(name);
		selectedSwitchOf.setSkin(skin);
		selectedSwitchOf.setChange(change);
		selectedSwitchOf.setClassCss(classCss);
		selectedSwitchOf.setState(state);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
	public String getValueAttr() {
		return valueAttr;
	}

	public void setValueAttr(String valueAttr) {
		this.valueAttr = valueAttr;
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
