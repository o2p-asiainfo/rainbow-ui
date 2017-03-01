package com.linkage.rainbow.ui.components.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.util.URLEncryption;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 字段验证组件,用户纯客户端校验<br>
 * 输入一个字段验证的javascript,必须被验证组件(Validate)包含
 * <p>
 * @version 1.0
 * <hr>
 */

public class Validator extends ClosingUIBean {
	/**
	 * 定义validator组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "validator/validator";//验证标签模版
	/**
	 * 定义validator组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "validator/validator-close";//验证结束标签模版

	
	private String fieldId;//表单ID
	private String required; //是否必须填写
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
	private String maxInclusive ;//小于等于的最小值
	private String minExclusive ;//大于的最小值
	private String maxExclusive ;//小于的最小值
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
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Validator
	 */
	public Validator(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
 
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		
		super.evaluateExtraParams();
		
		if (fieldId != null) {
			addParameter("fieldId", findString(fieldId));
			addParameter("fieldName", findString(fieldId));
		}
		if (required != null) 
			addParameter("required",  findValue(required, Boolean.class));
		if (validatorType != null) 
			addParameter("validatorType", findString(validatorType));
		if (message != null) 
			addParameter("message", findString(message));
		if (onShowMessage != null) 
			addParameter("onShowMessage", findString(onShowMessage));
		if (onFocusMessage != null) {
			addParameter("onFocusMessage", findString(onFocusMessage));
		} else if (message != null){
		    addParameter("onFocusMessage", findString(message));
		}
		if (messageKey != null) 
			addParameter("messageKey", findString(messageKey));
		if (maxLength != null) 
			addParameter("maxLength", findValue(maxLength, Integer.class));
		if (minLength != null) 
			addParameter("minLength", findValue(minLength, Integer.class));
		if (trim != null) 
			addParameter("trim", findValue(trim, Boolean.class));
		if (expression != null) 
			addParameter("expression", findString(expression));
		if (minInclusive != null) 
			addParameter("minInclusive", findString(minInclusive));
		if (maxInclusive != null) 
			addParameter("maxInclusive", findString(maxInclusive));
		if (minExclusive != null) 
			addParameter("minExclusive", findString(minExclusive));
		if (maxExclusive != null) 
			addParameter("maxExclusive", findString(maxExclusive));
		if(validatorType != null && validatorType.equals("date")){
			if (min != null) 
				addParameter("min", findString(min));
			if (max != null) 
				addParameter("max", findString(max));
		} else {
			if (min != null) 
				addParameter("min", findValue(min, Integer.class));
			if (max != null) 
				addParameter("max", findValue(max, Integer.class));
		}
		
		if (ajaxUrl != null) 
			addParameter("ajaxUrl", findString(ajaxUrl));
		
		if (ajaxDataType != null) 
			addParameter("ajaxDataType", findString(ajaxDataType));
		
		if(ajaxMethods != null){
			String tmp = findString(ajaxMethods) ;
			tmp = URLEncryption.encodeURL(tmp);
			addParameter("ajaxMethods",tmp );
		}
		if(ajaxButton != null)
			addParameter("ajaxButton", findString(ajaxButton));
		
		//		校验组
		//String validatorGroup = null;
		
		//将单个字段验证信息全部加入到上级标签Validators中。
		Validators validators =  (Validators)findAncestor(Validators.class);
		if(validators != null){
			//validatorGroup = validators.getValidatorGroup();
//			if(validatorGroup != null)
//				addParameter("validatorGroup",  validatorGroup);
			validators.addValidator(this.getParameters());
			
		}

		
	}
	

	/**
	 * 获取验证标签模版
	 * @param 
	 * @return String 验证标签模版
	 */
	@Override
	public String getDefaultOpenTemplate() {
		
		return OPEN_TEMPLATE;
	}

	/**
	 * 获取验证标签结束模版
	 * @param
	 * @return String 验证标签结束模版
	 */
	@Override
	protected String getDefaultTemplate() {
		
		return CLOSE_TEMPLATE;
		
	}
	
    /**
     * 指定模板
     * @author 陈亮 2009-02-11
     * @param template 模板名.
     * 例:一模板存放的完整路径是 template/xhtml/date/WdatePicker/date.ftl
     * 其中:
     * template 为模板存放主目录,对应的标签属性是: templateDir ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
     * xhtml    为主题名称,对应的标签属性是: theme ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值.
     * .ftl     为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
     * date/WdatePicker/date 为模板名称,
     *          对应的标签属性是: template 与 openTemplate ,如果标签没有设置,则通过此类的getDefaultOpenTemplate() 与getDefaultTemplate()分别取得标签的开始模板与结束模板.
     * 便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
     * 一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date 目录下再建立子目录,如:WdatePicker,来区分不同的模板实现.
     * 在标签调用时,不需要分别设置template 与 openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
     */
    @StrutsTagAttribute(description="The template (other than default) to use for rendering the element")
    @Override
    public void setTemplate(String template) {
    	if(template != null && template.trim().length()>0) {
	        this.template = "validator/"+template+"/validator-close";
	        this.setOpenTemplate("validator/"+template+"/validator");
    	} else {
    		template = null;
    	}
    }

    /**
     * 获取校验表达式
     * @return
     */
	public String getExpression() {
		return expression;
	}

	/**
	 * 设置校验表达式
	 * @param expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * 获取表单id
	 * @return String
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * 设置表单id
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
	 * 获取小于的最小值
	 * @return String
	 */
	public String getMaxExclusive() {
		return maxExclusive;
	}

	/**
	 * 设置小于的最小值
	 * @param maxExclusive
	 */
	public void setMaxExclusive(String maxExclusive) {
		this.maxExclusive = maxExclusive;
	}
	
	/**
	 * 获取小于等于的最小值
	 * @return String
	 */
	public String getMaxInclusive() {
		return maxInclusive;
	}

	/**
	 * 设置小于等于的最小值
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
	 * 获取出错提示信息
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置出错提示信息
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
	 * 是否前后去空
	 * @return String
	 */
	public String getTrim() {
		return trim;
	}

	/**
	 * 设置是否前后去空
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
	 * 获取是否必须填写
	 * @return String
	 */
	public String getRequired() {
		return required;
	}

	/**
	 * 设置是否必须填写
	 * @paramrequired
	 */
	public void setRequired(String required) {
		this.required = required;
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
	 * 获取ajax验证方法
	 * @return String
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
	 * 获取当进行ajax验证时,需要对哪一个接交按钮进行disable
	 * @return String
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

	
}