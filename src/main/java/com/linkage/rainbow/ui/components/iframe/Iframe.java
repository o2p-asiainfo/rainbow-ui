package com.linkage.rainbow.ui.components.iframe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;

public class Iframe extends UIComponentBase {
	/**
	 * 定义弹出层标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "iframe/iframe"; 
	/**
	 * 定义弹出层标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "iframe/iframe-close"; 
 
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
	public Iframe(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
//		System.out.println("已经进入弹出层组件类");
	}
	
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
          if(divId != null)
			addParameter("divId",  findString(divId));
          if(divTitle != null)
  			addParameter("divTitle",  findString(divTitle));
          if(divWidth != null)
  			addParameter("divWidth",  findString(divWidth));
          if(divHeight != null)
  			addParameter("divHeight",  findString(divHeight));
          if(iframeId != null)
  			addParameter("iframeId",  findString(iframeId));
          if(iframeSrc != null)
  			addParameter("iframeSrc",  findString(iframeSrc));
          if(iframeScrolling != null)
  			addParameter("iframeScrolling",  findString(iframeScrolling));
          if(frameborder != null)
  			addParameter("frameborder",  findString(frameborder));
          if(iframeWidth != null)
  			addParameter("iframeWidth",  findString(iframeWidth));
          if(iframeHeight != null)
  			addParameter("iframeHeight",  findString(iframeHeight));
          if(skin != null)
    		 addParameter("skin",  findString(skin));
          if(closed != null)
     		 addParameter("closed",  findString(closed));
           
          
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
	    public void setTemplate(String template) {
	    	if(template != null && template.trim().length()>0) {
		        this.template = "iframe/"+template+"/iframe-close";
		        this.setOpenTemplate("iframe/"+template+"/iframe");
	    	} else {
	    		template = null;
	    	}
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
