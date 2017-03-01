package com.linkage.rainbow.ui.components.combobox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.components.ListUIBean;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * ComboBox<br>
 * 用于将下拉多选组件的标签属性赋予给模板的组件类
 * <p>
 * @version 1.0
 */
public class Combobox extends UIComponentBase {
	/**
	 * 定义multiCheckListBox组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "combobox/simple/combobox";// 多选下拉框标签模版
	/**
	 * 定义multiCheckListBox组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "combobox/simple/combobox-close";// 多选下拉框结束标签模版


	private String textField;
	private String multiple;
	private String panelWidth;
	private String panelHeight;
	private String valueField;
	private String method;
	private String params;



	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return MultiCheckListBox
	 */
	public Combobox(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		
		super(stack, request, response);
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	public void evaluateExtraParams() {
		super.evaluateExtraParams();


		//addParameter("value", value);
		if(value!=null)
			addParameter("value",findString(value));
		if (textField != null) 
			addParameter("textField", findString(textField));
		if (multiple != null) 
			addParameter("multiple", findString(multiple));
		if (panelWidth != null) 
			addParameter("panelWidth", findString(panelWidth));
		if (panelHeight != null) 
			addParameter("panelHeight", findString(panelHeight));
		if (valueField != null) 
			addParameter("valueField", findString(valueField));
		if (method != null) 
			addParameter("method", findString(method));
		if (params != null) 
			addParameter("params", findString(params));

		
		 if (parameters.containsKey("value")) {
	            parameters.put("nameValue", parameters.get("value"));
	        } else {
	            if (evaluateNameValue()) {
	                final Class valueClazz = getValueClassType();

	                if (valueClazz != null) {
	                    if (value != null) {
	                        addParameter("nameValue", findValue(value, valueClazz));
	                    } else if (name != null) {
	                        String expr = name;
	                        if (altSyntax()) {
	                            expr = "%{" + expr + "}";
	                        }

	                        addParameter("nameValue", findValue(expr, valueClazz));
	                    }
	                } else {
	                    if (value != null && !value.equals("")) {
	                        //设置value默认值有错
	                    	if (value.startsWith("%{") && value.endsWith("}")) {
	                    		addParameter("nameValue", findValue(value));
	                    	}else{
	                    		addParameter("nameValue", value);
	                    	}
	                    } else if (name != null) {
	                        addParameter("nameValue", findValue(name));
	                    }
	                }
	            }
	        }
	}


	/**
	 * 指定模板
	 * @author 陈亮 2009-02-11
	 * @param template 模板名. 例:一模板存放的完整路径是
	 *        template/xhtml/date/WdatePicker/date.ftl 其中: template
	 *        为模板存放主目录,对应的标签属性是: templateDir
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
	 *        xhtml 为主题名称,对应的标签属性是: theme
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值. .ftl
	 *        为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
	 *        date/WdatePicker/date 为模板名称, 对应的标签属性是: template 与 openTemplate
	 *        ,如果标签没有设置,则通过此类的getDefaultOpenTemplate()
	 *        与getDefaultTemplate()分别取得标签的开始模板与结束模板.
	 *        便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
	 *        一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date
	 *        目录下再建立子目录,如:WdatePicker,来区分不同的模板实现. 在标签调用时,不需要分别设置template 与
	 *        openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
	 */
	@Override
	public void setTemplate(String template) {
	    if(template != null && template.trim().length()>0) {
	    	this.template = "combobox/"+template+"/combobox-close";
		    this.setOpenTemplate("combobox/"+template+"/combobox");
	    } else {
	    	template = null;
	    }
	}

	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		// TODO Auto-generated method stub
		
		return CLOSE_TEMPLATE;
	}




	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getPanelHeight() {
		return panelHeight;
	}

	public void setPanelHeight(String panelHeight) {
		this.panelHeight = panelHeight;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}



	@Override
	public String getDefaultOpenTemplate() {
		// TODO Auto-generated method stub
		return OPEN_TEMPLATE;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getPanelWidth() {
		return panelWidth;
	}

	public void setPanelWidth(String panelWidth) {
		this.panelWidth = panelWidth;
	}




}
