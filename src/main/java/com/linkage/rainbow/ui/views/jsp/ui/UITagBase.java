package com.linkage.rainbow.ui.views.jsp.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.components.layout.Layout;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 所有UI标签父类<br>
 * <p>
 * @version 1.0
 * <hr>
 */

public abstract class UITagBase extends AbstractUITag {

	//渲染目标
	protected String renderTo;
//	皮肤
	protected String skin;
	
	protected String width;
	protected String height;
	protected String border;
	
	//class
	protected String styleClass;
	//style
	protected String style;

	


	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		UIComponentBase comp = (UIComponentBase) component;
		//从会话中取得本用户皮肤的设置
		Map session = com.opensymphony.xwork2.ActionContext.getContext().getSession();
		String session_skin = null;
		if(session != null){
			session_skin = (String)session.get("rainbow.ui.skin");
		}		
		if( (skin==null ||skin.startsWith("default:")) && session_skin != null && session_skin.trim().length()>0){
			comp.setSkin(session_skin);//setBeanValue(bean,"skin",skin);
		}else {
			if (skin != null && skin.startsWith("default:"))
				skin = skin.substring(8);
			comp.setSkin(skin);
		}
		comp.setWidth(width);
		comp.setHeight(height);
		comp.setBorder(border);
		comp.setStyleClass(styleClass);
		comp.setStyle(style);
		comp.setRenderTo(renderTo);
		
	}


	/**
	 * 获取皮肤
	 * @return String
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置皮肤
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getStyle() {
		return style;
	}

	/**
	 * 设置版面样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取版面样式表
	 * @return
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置版面样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 获取版面高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置版面高度
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取版面宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置版面宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	public String getRenderTo() {
		return renderTo;
	}

	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}


	public String getBorder() {
		return border;
	}


	public void setBorder(String border) {
		this.border = border;
	}
	
	
}
