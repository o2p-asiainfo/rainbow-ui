package com.linkage.rainbow.ui.views.jsp.ui.warn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.iframe.Iframe;
import com.linkage.rainbow.ui.components.warn.Warn;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class WarnTag extends UITagBase {
	
	private String skin ;
	 
	
	
	public WarnTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.warn");
	}
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		 return new Warn(stack, req, res);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();
		Warn warn = (Warn) component;
		warn.setSkin(skin) ;
		 
	}
	 
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}

}
