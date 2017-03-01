package com.linkage.rainbow.ui.views.jsp.ui.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;


import  com.linkage.rainbow.ui.components.report.Col;
import  com.linkage.rainbow.ui.components.report.Tr;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * ColTag<br>
 * 被colgroup标签包含,用于设置列类型,列宽度等
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

public class ColTag extends AbstractUITag {
	
	protected String areaType;
	//列宽度
	protected String width;
	//样式
	protected String style;
	//样式表
	protected String styleClass;
	//背影图
	protected String background;
	//背影色
	protected String bgcolor;
	

	/**
	 * 标签的构造函数中,增加根据配置文件初始化默认值
	 * 默认配置文件路径为:com/linkage/rainbow/ui/default.properties
	 * 项目组使用时,修改的配置文件路径为:comm.properties
	 * 1.配置文件中UI组件默认值设置的参数名设置规则为comm.ui.为前缀,
	 *   接下来是标签文件中定义的标签名,
	 *   最后是标签文件中定义的此标签的属性名
	 *   例：
	 *   comm.ui.date.template=WdatePicker
	 * 2.在标签类构造函数中通过调用：
	 * DefaultSettings.setDefaultValue(this,"comm.ui.标签名");
	 * 实现此标签的默认值设置
	 * @author 陈亮 2009-3-18 
	 */
	public ColTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.col");
	}

	/**
	 * 获取内存中的组件类
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new Col(stack, req, res);
	}

	/**
	 * 将标签类的属性设置给组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Col comp = (Col) component;
		comp.setAreaType(areaType);
		comp.setWidth(width);
		
		comp.setStyle(style);
		comp.setStyleClass(styleClass);
		comp.setBackground(background);
		comp.setBgcolor(bgcolor);
		comp.setOnclick(onclick);
		comp.setOndblclick(ondblclick);
		comp.setBackground(background);
		comp.setOnmouseover(onmouseover);
		comp.setOnmouseout(onmouseout);
		comp.setOnmousedown(onmousedown);
	}

	/**
	 * 获取区域类型
	 * @return String
	 */
	public String getAreaType() {
		return areaType;
	}

	/**
	 * 设置区域类型
	 * @param areaType
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	/**
	 * 获取背景
	 * @return String
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置背景
	 * @param background
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * 获取背景色
	 * @return String
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	/**
	 * 设置背景色
	 * @param bgcolor
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	/**
	 * 获取单击事件
	 * @return String
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * 设置单击事件
	 * @param onclick
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * 获取双击事件
	 * @return String
	 */
	public String getOndblclick() {
		return ondblclick;
	}

	/**
	 * 获取双击事件
	 * @param ondblclick
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * 获取鼠标点击事件 
	 * @return String
	 */
	public String getOnmousedown() {
		return onmousedown;
	}

	/**
	 * 设置鼠标点击事件
	 * @param onmousedown
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * 获取鼠标移出列的事件
	 * @return String
	 */
	public String getOnmouseout() {
		return onmouseout;
	}
	
	/**
	 * 设置鼠标移出列的事件
	 * @param onmouseout
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * 获取鼠标移动到列的事件
	 * @return String
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * 设置鼠标移动到列的事件
	 * @param onmouseover
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * 获取列样式
	 * @return String
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置列样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取列样式表
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置列样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 获取列宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置列宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	



}
