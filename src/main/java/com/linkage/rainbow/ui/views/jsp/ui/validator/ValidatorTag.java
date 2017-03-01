package com.linkage.rainbow.ui.views.jsp.ui.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.validator.Validator;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * FieldValidateTag<br>
 * 表单验证组件,用户纯客户端校验<br>
 * 输入一个字段验证的javascript,必须被验证组件(Validate)包含
 * <p>
 * @version 1.0
 * <hr>
 */

public class ValidatorTag extends AbstractUITag {

	private String fieldId;//表单名
	private String validatorType ;//验证类型
	private String message ;//错误时提示信息
	private String onShowMessage ;//初始显示信息
	private String onFocusMessage ;//焦点聚集时提示信息
	private String messageKey ;//提示信息Key
	private String maxLength ;//最大长度
	private String minLength ;//最小长度
	private String trim ;//是否前后去空
	private String expression ;//表达式
	private String minInclusive ;//大于等于的最小值
	private String maxInclusive ;//小于等于的最大值
	private String minExclusive ;//大于的最小值
	private String maxExclusive ;//小于的最大值
	private String min ;//大于等于的最小值
	private String max ;//小于等于的最小值
	private String ajaxUrl; //ajax验证对应的URL
	private String ajaxDataType; //ajax返回的数据类型，分text,html,xml,json
	/*对应的远程方法名,根据由springBeanId.方法名(参数信息),可以精简为只输入springBeanId.方法名
	    springBeanId.方法名(参数名:参数值,参数名:参数值) 
	     	此方法必须为入参为Map,Map中的值除标签设置时参数外,自动增加 fieldId中设置为表单ID=当前输入的值
	    springBeanId.方法名(参数值,参数值) 
	    	此方法入参为(标签中设置的参数个数+1)的为String 的入参,最后一个入参将传入 表单中当前输入的值
	 */
	private String ajaxMethods;
	private String ajaxButton; //当进行ajax验证时,需要对哪一个接交按钮进行disable
	
	private String required ;

	/**
	 * 获取当进行ajax验证时,需要对哪一个接交按钮进行disable
	 * @return
	 */
	public String getAjaxButton() {
		return ajaxButton;
	}

	/**
	 * 设置当进行ajax验证时,需要对哪一个接交按钮进行disable
	 * @param ajaxButton
	 */
	public void setAjaxButton(String ajaxButton) {
		this.ajaxButton = ajaxButton;
	}

	/**
	 * 获取ajax返回的数据类型，分text,html,xml,json
	 * @return String
	 */
	public String getAjaxDataType() {
		return ajaxDataType;
	}

	/**
	 * 设置ajax返回的数据类型，分text,html,xml,json
	 * @param ajaxDataType
	 */
	public void setAjaxDataType(String ajaxDataType) {
		this.ajaxDataType = ajaxDataType;
	}

	/**
	 * 获取ajax验证对应的URL
	 * @return String
	 */
	public String getAjaxUrl() {
		return ajaxUrl;
	}

	/**
	 * 设置ajax验证对应的URL
	 * @param ajaxUrl
	 */
	public void setAjaxUrl(String ajaxUrl) {
		this.ajaxUrl = ajaxUrl;
	}

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
	public ValidatorTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.validator");
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
		return new Validator(stack, req, res);
	}
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Validator validator = (Validator) component;
		validator.setFieldId(fieldId);
		validator.setValidatorType(validatorType);
		validator.setMessage(message);
		validator.setOnShowMessage(onShowMessage);
		validator.setOnFocusMessage(onFocusMessage);
		validator.setMessageKey(messageKey);
		validator.setMaxLength(maxLength);
		validator.setMinLength(minLength);
		validator.setTrim(trim);
		validator.setExpression(expression);
		validator.setMinInclusive(minInclusive);
		validator.setMaxInclusive(maxInclusive);
		validator.setMinExclusive(minExclusive);
		validator.setMaxExclusive(maxExclusive);
		validator.setMin(min);
		validator.setMax(max);
		validator.setAjaxUrl(ajaxUrl);
		validator.setAjaxDataType(ajaxDataType);
		validator.setAjaxMethods(ajaxMethods);
		validator.setAjaxButton(ajaxButton);
		validator.setRequired(required);
	}

	/**
	 * 获取验证表达式
	 * @return String
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * 设置验证表达式
	 * @param expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}


	/**
	 * 获取表单名
	 * @return String
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * 设置表单名
	 * @param fieldId
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 获取小于等于的最小值
	 * @return String
	 */
	public String getMax() {
		return max;
	}

	/**
	 * 设置小于等于的最小值
	 * @param max
	 */
	public void setMax(String max) {
		this.max = max;
	}

	/**
	 * 获取小于的最大值
	 * @return String
	 */
	public String getMaxExclusive() {
		return maxExclusive;
	}

	/**
	 * 设置小于的最大值
	 * @param maxExclusive
	 */
	public void setMaxExclusive(String maxExclusive) {
		this.maxExclusive = maxExclusive;
	}

	/**
	 * 设置小于等于的最大值
	 * @return String
	 */
	public String getMaxInclusive() {
		return maxInclusive;
	}

	/**
	 * 设置小于等于的最大值
	 * @param maxInclusive
	 */
	public void setMaxInclusive(String maxInclusive) {
		this.maxInclusive = maxInclusive;
	}

	/**
	 * 获取最大长度
	 * @return String
	 */
	public String getMaxLength() {
		return maxLength;
	}

	/**
	 * 设置最大长度
	 * @param maxLength
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 获取提示信息
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置提示信息
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取提示信息Key
	 * @return String
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * 设置提示信息Key
	 * @param messageKey
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * 获取大于等于的最小值
	 * @return String
	 */
	public String getMin() {
		return min;
	}

	/**
	 * 设置大于等于的最小值
	 * @param min
	 */
	public void setMin(String min) {
		this.min = min;
	}

	/**
	 * 获取大于的最小值
	 * @return String
	 */
	public String getMinExclusive() {
		return minExclusive;
	}

	/**
	 * 设置大于的最小值
	 * @param minExclusive
	 */
	public void setMinExclusive(String minExclusive) {
		this.minExclusive = minExclusive;
	}

	/**
	 * 获取大于等于的最小值
	 * @return String
	 */
	public String getMinInclusive() {
		return minInclusive;
	}

	/**
	 * 设置大于等于的最小值
	 * @param minInclusive
	 */
	public void setMinInclusive(String minInclusive) {
		this.minInclusive = minInclusive;
	}

	/**
	 * 获取最小长度
	 * @return String
	 */
	public String getMinLength() {
		return minLength;
	}

	/**
	 * 设置最小长度
	 * @param minLength
	 */
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	/**
	 * 获取是否清除左右空格
	 * @return
	 */
	public String getTrim() {
		return trim;
	}

	/**
	 * 设置是否清除左右空格
	 * @param trim
	 */
	public void setTrim(String trim) {
		this.trim = trim;
	}

	/**
	 * 获取验证类型
	 * @return String
	 */
	public String getValidatorType() {
		return validatorType;
	}

	/**
	 * 设置验证类型
	 * @param validatorType
	 */
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}

	/**
	 * 获取ajax验证方法
	 * @return
	 */
	public String getAjaxMethods() {
		return ajaxMethods;
	}

	/**
	 * 设置ajax验证方法
	 * @param ajaxMethods
	 */
	public void setAjaxMethods(String ajaxMethods) {
		this.ajaxMethods = ajaxMethods;
	}

	/**
	 * @return the onShowMessage
	 */
	public String getOnShowMessage() {
	    return onShowMessage;
	}

	/**
	 * @param onShowMessage the onShowMessage to set
	 */
	public void setOnShowMessage(String onShowMessage) {
	    this.onShowMessage = onShowMessage;
	}

	/**
	 * @return the onFocusMessage
	 */
	public String getOnFocusMessage() {
	    return onFocusMessage;
	}

	/**
	 * @param onFocusMessage the onFocusMessage to set
	 */
	public void setOnFocusMessage(String onFocusMessage) {
	    this.onFocusMessage = onFocusMessage;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

}
