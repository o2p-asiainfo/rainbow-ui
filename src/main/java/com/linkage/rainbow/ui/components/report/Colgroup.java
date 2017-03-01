package com.linkage.rainbow.ui.components.report;




import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Colgroup<br>
 * 列分组,用于包含col标签
 * <p>
 * @version 1.0
 * @author 陈亮 2009-07-30
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-07-30<br>
 *         修改内容:新建
 *         <hr>
 */
@StrutsTag(name = "golgroup", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.report.GolgroupTag", description = "Render an HTML Table")
public class Colgroup extends ClosingUIBean {
	/**
	 * 定义列分组组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "report/golgroup";
	/**
	 * 定义列分组组件的结束模板
	 */
	public static final String TEMPLATE = "report/golgroup-close";
	String templateShort ;

	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Colgroup
	 */
	public Colgroup(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
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
		return TEMPLATE;
	}


	/**
	 * 将属性信息设置到模板
	 * @param writer
	 * @return boolean
	 */
	@Override
    public boolean start(Writer writer) {
    	//evaluateParams();
    	return true;
    }
    
	
	/**
	 * 形成报表请求类,调用报表引擎,生成报表输出
	 * @param writer
	 * @param body
	 * @return boolean
	 */
	@Override
    public boolean end(Writer writer, String body) {
		return false;
    }
    
	/**
	 * 设置标签中的属性到模板
	 */
	protected void evaluateExtraParams() {

	}


}
