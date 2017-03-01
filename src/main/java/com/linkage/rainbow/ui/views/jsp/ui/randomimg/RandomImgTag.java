package com.linkage.rainbow.ui.views.jsp.ui.randomimg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.randomimg.RandomImg;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * TreeTag<br>
 * 验证码标签类提供对验证码标签中属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class RandomImgTag extends AbstractUITag {
	private String width;
	private String height;
	private String type;
	private String codelength;
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public RandomImgTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.randomImg");
	}
	
	/**
	 * 实现接口类 继承父类的基本信息
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new RandomImg(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		
		RandomImg img=(RandomImg)component;
		img.setWidth(width);
		img.setHeight(height);
		img.setType(type);
		img.setCodelength(codelength);
	}

	/**
	 * 设置图片的宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置图片的高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置图片的类型
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 设置验证码字数
	 * @param codelength the codelength to set
	 */
	public void setCodelength(String codelength) {
		this.codelength = codelength;
	}
	

	
	
}
