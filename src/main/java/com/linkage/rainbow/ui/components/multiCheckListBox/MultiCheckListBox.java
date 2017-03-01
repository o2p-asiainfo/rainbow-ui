package com.linkage.rainbow.ui.components.multiCheckListBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ListUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * MultiCheckListBox<br>
 * 用于将下拉多选组件的标签属性赋予给模板的组件类
 * <p>
 * @version 1.0
 */
public class MultiCheckListBox extends ListUIBean {
	/**
	 * 定义multiCheckListBox组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "multiCheckListBox/multiCheckListBox";// 多选下拉框标签模版
	/**
	 * 定义multiCheckListBox组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "multiCheckListBox/multiCheckListBox-close";// 多选下拉框结束标签模版

	private String skin;// 组件皮肤：默认为墨绿色
	private String layerWidth;
	private String layerHeight;
	private String rows;
	private String cols;
	private String charTypeValue;
	private String checkAllValueSetBlank;
	private String isCheckAll;
	private String javascript;

	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return MultiCheckListBox
	 */
	public MultiCheckListBox(ValueStack stack, HttpServletRequest request,
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
		addParameter("tagUtil", new ComponentUtil(request));
		addParameter("allLabel", "全部"); //解决在utf-8环境下乱码问题,模板内文件不显示中文.
		addParameter("allSelectLabel", "全选"); //解决在utf-8环境下乱码问题,模板内文件不显示中文.
		if (skin != null) 
			addParameter("skin", findString(skin));
		if (layerWidth != null) 
			addParameter("layerWidth", findString(layerWidth));
		if (layerHeight != null) 
			addParameter("layerHeight", findString(layerHeight));
		//addParameter("value", value);
		if (rows != null) 
			addParameter("rows", findString(rows));
		if (cols != null) 
			addParameter("cols", findString(cols));
		if (charTypeValue != null) 
			addParameter("charTypeValue", findString(charTypeValue));
		if (checkAllValueSetBlank != null) 
			addParameter("checkAllValueSetBlank", findString(checkAllValueSetBlank));
		if (isCheckAll != null) 
			addParameter("isCheckAll", findString(isCheckAll));
		if (javascript != null) 
			addParameter("javascript", findString(javascript));
		
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
		if (template != null && template.trim().length() > 0) {
			this.template = "multiCheckListBox/" + template
					+ "/multiCheckListBox";
			
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
		
		return OPEN_TEMPLATE;
	}


	/**
	 * 设置皮肤属性
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}



	/**
	 * 设置组件的宽度
	 * @param layerWidth the layerWidth to set
	 */
	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	/**
	 * 设置组件的高度
	 * @param layerHeight the layerHeight to set
	 */
	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}

	/**
	 * 设置文本框的行数
	 * @param rows the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	/**
	 * 设置文本框的列数
	 * @param cols the cols to set
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * 设置左右列表框组件返回值是否为字符串类型;true为字符串型(String),false为数值型;默认为（false）
	 * @param charTypeValue the charTypeValue to set
	 */
	public void setCharTypeValue(String charTypeValue) {
		this.charTypeValue = charTypeValue;
	}

	/**
	 * 多选下拉框组件全选时返回值是否为'ALL';true 为'ALL'、false为所有选中值用','隔开的字符串；默认为（true）
	 * @param checkAllValueSetBlank the checkAllValueSetBlank to set
	 */
	public void setCheckAllValueSetBlank(String checkAllValueSetBlank) {
		this.checkAllValueSetBlank = checkAllValueSetBlank;
	}

	/**
	 * 是否是全选
	 * @param isCheckAll the isCheckAll to set
	 */
	public void setIsCheckAll(String isCheckAll) {
		this.isCheckAll = isCheckAll;
	}

	/**
	 * 设置textarea的脚本事件
	 * @param javascript the javascript to set
	 */
	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}
}
