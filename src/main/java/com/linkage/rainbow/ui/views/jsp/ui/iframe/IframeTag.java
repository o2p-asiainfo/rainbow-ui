package com.linkage.rainbow.ui.views.jsp.ui.iframe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.iframe.Iframe;
import com.linkage.rainbow.ui.components.textarea.Textarea;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class IframeTag extends UITagBase {
    private String divId ;
	private String divTitle ;
	private String divWidth ;
	private String divHeight ;
	private String iframeId ;
	private String iframeSrc;
	private String iframeScrolling ;
	private String frameborder;
	private String iframeWidth ;
	private String iframeHeight ;
	private String skin ;
	private String closed;
	
	public IframeTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.iframe");
	}
		
		
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new Iframe(stack, req, res);
	}
	
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();
		Iframe iframe = (Iframe) component;
		iframe.setDivId(divId);
		iframe.setDivTitle(divTitle);
		iframe.setDivWidth(divWidth==null?String.valueOf(Integer.valueOf(iframeWidth)+4):divWidth);
		iframe.setDivHeight(divHeight==null?String.valueOf(Integer.valueOf(iframeHeight)+35):divHeight);
		iframe.setIframeId(iframeId);
		iframe.setIframeSrc(iframeSrc);
		iframe.setIframeScrolling(iframeScrolling);
		iframe.setFrameborder(frameborder) ;
		iframe.setIframeWidth(iframeWidth);
		iframe.setIframeHeight(iframeHeight);
		iframe.setSkin(skin) ;
		iframe.setClosed((closed!=null&&"false".equals(closed))?"false":"true");
	}
	
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getDivTitle() {
		return divTitle;
	}
	public void setDivTitle(String divTitle) {
		this.divTitle = divTitle;
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
	public String getIframeId() {
		return iframeId;
	}
	public void setIframeId(String iframeId) {
		this.iframeId = iframeId;
	}
	public String getIframeSrc() {
		return iframeSrc;
	}
	public void setIframeSrc(String iframeSrc) {
		this.iframeSrc = iframeSrc;
	}
	public String getIframeScrolling() {
		return iframeScrolling;
	}
	public void setIframeScrolling(String iframeScrolling) {
		this.iframeScrolling = iframeScrolling;
	}
	public String getFrameborder() {
		return frameborder;
	}
	public void setFrameborder(String frameborder) {
		this.frameborder = frameborder;
	}
	public String getIframeWidth() {
		return iframeWidth;
	}
	public void setIframeWidth(String iframeWidth) {
		this.iframeWidth = iframeWidth;
	}
	public String getIframeHeight() {
		return iframeHeight;
	}
	public void setIframeHeight(String iframeHeight) {
		this.iframeHeight = iframeHeight;
	}


	public String getSkin() {
		return skin;
	}


	public void setSkin(String skin) {
		this.skin = skin;
	}


	public String getClosed() {
		return closed;
	}


	public void setClosed(String closed) {
		this.closed = closed;
	}

}
