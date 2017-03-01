package com.linkage.rainbow.ui.views.jsp.ui.date;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.date.Date;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * DateTag<br>
 * 日期标签类提供对日期标签中属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class DateTag extends UITagBase {
    

	private String isShowWeek ;//是否显示周
	private String readOnly;//组件是否只读
	private String dateFmt;//日期格式
	private String minDate;//最小日期
	private String maxDate;//最大日期
	private String disabled;//禁止更改日期
	private String script;  //自定义事件
	private String triggerEvent;//日期控件弹出的触发事件,默认是onclick ,可选onfocus
	private String lang;//语言

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
	public DateTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.date");
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
		return new Date(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Date date = (Date) component;
		date.setIsShowWeek(isShowWeek);
		date.setReadOnly(readOnly);
		date.setDateFmt(dateFmt);
		date.setMaxDate(maxDate);
		date.setMinDate(minDate);
		date.setDisabled(disabled);
		date.setScript(script);
		date.setTriggerEvent(triggerEvent);
		date.setLang(lang);
	}


	/**
	 * 设置是否显示周
	 * @param isShowWeek 是否显示周
	 * @return void
	 */
	public void setIsShowWeek(String isShowWeek) {
		this.isShowWeek = isShowWeek;
	}

	/**
	 * 设置是否只读
	 * @param readOnly 是否只读
	 * @return void
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * 设置日期显示的格式
	 * @param dateFmt 日期格式
	 * @return void
	 */
	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}

	
	/**
	 * 设置日期组件最小日期
	 * @param minDate 最小日期
	 * @return void
	 */
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	
	/**
	 * 设置日期组件禁止更改日期
	 * @param disabled 禁止更改日期
	 * @return void
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * 设置input框的脚本
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * 获取弹出日历的关联事件
	 * @return String
	 */
	public String getTriggerEvent() {
		return triggerEvent;
	}

	/**
	 * 设置弹出日历的关联事件
	 * @param triggerEvent
	 */
	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
	
	/**
	 * 设置日历的最大日期时间
	 * @param maxDate
	 */
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	
	public static void main(String[] args) {
	    MessageFormat formatter = new MessageFormat("");   
	    String template = "{0}元包{2}条";   
	    Object[] messageArguments = {   
		    "1", "元","2","条"}; 
	    formatter.applyPattern(template);   
	    String output = formatter.format(messageArguments);   
	    System.out.println(output) ;



	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
