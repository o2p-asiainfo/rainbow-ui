package com.linkage.rainbow.ui.views.jsp.ui.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.validator.Validators;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * ValidateTag<br>
 * 验证组件,用户纯客户端校验<br>
 * 此标签用于输入公共的校验脚本与包含各字段验证标签.
 * <p>
 * @version 1.0
 * <hr>
 */

public class ValidatorsTag extends AbstractUITag {

	private String formId; //表单ID
	private String validateAlert ;//验证提示方式
	private String validatorGroup ; //校验组,可按不同组进行校验

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
	public ValidatorsTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.validators");
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
		return new Validators(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Validators validators = (Validators) component;
		validators.setFormId(formId);
		validators.setValidateAlert(validateAlert);
		validators.setValidatorGroup(validatorGroup);
	}

	/**
	 * 取得验证提示方式
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

	/**
	 * 获取表单id
	 * @return String
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * 设置表单id
	 * @param formId
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * 获取待验证的控件组
	 * @return String
	 */
	public String getValidatorGroup() {
		return validatorGroup;
	}

	/**
	 * 设置待验证的控件组
	 * @param validatorGroup
	 */
	public void setValidatorGroup(String validatorGroup) {
		this.validatorGroup = validatorGroup;
	}




}
