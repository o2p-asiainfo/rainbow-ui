package com.linkage.rainbow.ui.components.warn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;

public class Warn extends UIComponentBase {
	/**
	 * 提示框标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "warn/warn"; 
	/**
	 * 提示框标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "warn/warn-close"; 
	
	private String skin ;
	 
	public Warn(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
//		System.out.println("已经进入提示框组件类");
	}
	
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
          if(skin != null)
			addParameter("skin",  findString(skin));
         
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
		        this.template = "warn/"+template+"/warn-close";
		        this.setOpenTemplate("warn/"+template+"/warn");
	    	} else {
	    		template = null;
	    	}
	    }

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}


 
}
