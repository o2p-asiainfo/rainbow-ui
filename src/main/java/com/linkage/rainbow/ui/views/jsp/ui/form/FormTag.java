package com.linkage.rainbow.ui.views.jsp.ui.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.linkage.rainbow.ui.components.form.Form;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * FormTag<br>
 * form标签类提供对form标签中属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class FormTag extends org.apache.struts2.views.jsp.ui.FormTag {

	private String validateAlert ;//验证提示方式

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
	public FormTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.form");
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
		return new Form(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		 super.populateParams();
	     Form form = ((Form) component);
	     form.setValidateAlert(validateAlert);
	}

	/**
	 * 获取验证提示方式
	 * @return
	 */
	public String getValidateAlert() {
		return validateAlert;
	}

	/**
	 * 设置验证提示方式
	 * @param validateAlert
	 */
	public void setValidateAlert(String validateAlert) {
		this.validateAlert = validateAlert;
	}



}
