package com.linkage.rainbow.ui.components.autoComplete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * AutoComplete.java<br>
 * 该类用于自动填充信息 将值设置给模板的中间过渡类
 * <p>
 * @version 1.0
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class AutoComplete  extends UIComponentBase {
	
	/**
	 * 定义自动填充标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "autoComplete/autoComplete";//自动填充标签模版
	/**
	 * 定义自动填充标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "autoComplete/autoComplete-close";//自动填充标签模版
	
	private String method;
	private String params;
	private String errormessage;
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param ValueStack --值栈对象 
	 * @param HttpServletRequest --http请求
	 * @param HttpServletResponse --http回复
	 * @return AutoComplete
	 */
	public AutoComplete(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(method != null)
			addParameter("method",  findString(method));
		if(params != null)
			addParameter("params",  findString(params));
		if(errormessage != null)
			addParameter("errormessage",  findString(errormessage));

	}
	
	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}
	
	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		// TODO Auto-generated method stub
		return CLOSE_TEMPLATE;
	}
	
	/**
	 *  对应设置标签的template属性
	 * @param template --模板名称
	 * @return void
	 */
	public void setTemplate(String template) {		
	    if(template != null && template.trim().length()>0) {
	    	this.template = "autoComplete/"+template+"/autoComplete-close";
		    this.setOpenTemplate("autoComplete/"+template+"/autoComplete");
	    } else {
	    	template = null;
	    }
	}
	


	/**
	 * 设置模糊查询获取数据的方法
	 * 例 method="beanid.methodname"
	 * @param method --方法名称
	 * @return void
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 其他附加参数
	 * 例 name:susan;age:11
	 * @param params --字符串
	 * @return void
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 出错提示
	 * @param errormessage 
	 * @return void
	 */
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
}
