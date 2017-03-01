package com.linkage.rainbow.ui.views.jsp.ui.accordion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.components.accordion.Accordion;
import com.linkage.rainbow.ui.components.date.Date;
import com.linkage.rainbow.ui.components.layout.Layout;

import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

public class AccordionTag extends UITagBase{

	private String fit;
	
	
	/**
	 * 标签的构造函数中,增加根据配置文件初始化默认值
	 * 默认配置文件路径为:com/linkage/rainbow/uidufault.properties
	 * 项目组使用时,修改的配置文件路径为:comm.properties
	 * 1.配置文件中UI组件默认值设置的参数名设置规则为rainbow.ui.为前缀,
	 *   接下来是标签文件中定义的标签名,
	 *   最后是标签文件中定义的此标签的属性名
	 *   例：
	 *   rainbow.ui.date.template=WdatePicker
	 * 2.在标签类构造函数中通过调用：
	 * DefaultSettings.setDefaultValue(this,"rainbow.ui.标签名");
	 * 实现此标签的默认值设置
	 * @author 陈亮 2009-3-18 
	 */
	public AccordionTag(){
		//System.out.println("sfdsfdsafs");
		DefaultSettings.setDefaultValue(this,"rainbow.ui.accordion");
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
		//System.out.println("sfdsfdsafs");
		return new Accordion(stack, req, res);
	}
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Accordion comp = (Accordion) component;
		comp.setFit(fit);
	}
	public String getFit() {
		return fit;
	}
	public void setFit(String fit) {
		this.fit = fit;
	}
	
}
