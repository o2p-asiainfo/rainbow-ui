package com.linkage.rainbow.ui.views.jsp.ui.slider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import com.linkage.rainbow.ui.components.slider.Slider;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class SliderTag extends AbstractUITag{

	/**
	 * wanglm7
	 * 2013-07-09 17:57:38
	 */
	private static final long serialVersionUID = 1L;

	private String from ; 
	private String to ; 
	private String step ; 
	private String dimension ; 
	private String skin ; 
	private String scale ; 
	private String limits ; 
	private String callback ; 
	private String defaultvalue ;
	private String divclass; 
	
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		
		return new Slider(arg0,arg1,arg2);
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		Slider slider = (Slider) component ;
		slider.setFrom(from);
		slider.setTo(to);
		slider.setStep(step);
		slider.setDimension(dimension);
		slider.setSkin(skin);
		slider.setScale(scale);
		slider.setLimits(limits);
		slider.setCallback(callback);
		slider.setDefaultvalue(defaultvalue);
		slider.setDivclass(divclass);
	}
	
	public SliderTag() {
		DefaultSettings.setDefaultValue(this,"rainbow.ui.slider");
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getDivclass() {
		return divclass;
	}

	public void setDivclass(String divclass) {
		this.divclass = divclass;
	}

	public String getStep() {
		return step;
	}


	public void setStep(String step) {
		this.step = step;
	}


	public String getDimension() {
		return dimension;
	}


	public void setDimension(String dimension) {
		this.dimension = dimension;
	}


	public String getSkin() {
		return skin;
	}


	public void setSkin(String skin) {
		this.skin = skin;
	}


	public String getScale() {
		return scale;
	}


	public void setScale(String scale) {
		this.scale = scale;
	}


	public String getLimits() {
		return limits;
	}


	public void setLimits(String limits) {
		this.limits = limits;
	}


	public String getCallback() {
		return callback;
	}


	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

}
