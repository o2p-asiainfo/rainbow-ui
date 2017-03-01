package com.linkage.rainbow.ui.components.report;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.report.engine.model.Range;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * GridRow<br>
 * 数据表格行,此组件与 Grid 、GridCol组合使用。
 * <p>
 * @version 1.0
 * @author 陈亮 2009-04-9
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-04-9<br>
 *         修改内容:新建
 *         <hr>
 */
@StrutsTag(name = "gridRow", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.grid.GridRowTag", description = "Render an HTML Table")
public class Tr extends ClosingUIBean {
	/**
	 * 定义行组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "report/tr";
	/**
	 * 定义行组件的结束模板
	 */
	public static final String TEMPLATE = "report/tr-close";
	
	
	protected String areaType;
	//列宽度
	protected String width;
	//行高度
	protected String height;
	//样式
	protected String style;
	//样式表
	protected String styleClass;
	//背影图
	protected String background;
	//背影色
	protected String bgcolor;

	Range range = new Range();
	

	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Tr
	 */
	public Tr(ValueStack stack, HttpServletRequest request,
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
		evaluateExtraParams() ;
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
		super.evaluateExtraParams();
		if(width != null)
			range.setWidth(findString(width));
		if(height != null)
			range.setHeight(findString(height));
		if(style != null)
			range.setStyle(findString(style));
		if(styleClass != null)
			range.setStyleClass(findString(styleClass));
		if(background != null)
			range.setBackground(findString(background));
		if(bgcolor != null)
			range.setBgcolor(findString(bgcolor));
		if(areaType != null)
			range.setAreaType(findString(areaType));
	
		
		if(onclick != null)
			range.setOnclick(findString(onclick));
		if(ondblclick != null)
			range.setOndblclick(findString(ondblclick));
		if(onmouseout != null)
			range.setOnmouseout(findString(onmouseout));
		if(onmousedown != null)
			range.setOnmousedown(findString(onmousedown));
		if(onmouseover != null)
			range.setOnmouseover(findString(onmouseover));

		Table table =  (Table)findAncestor(Table.class);
		if(table != null){
			table.getDefCS().addRowRange(range);
		}
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
	 * 获取行高
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置行高
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
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
	 * 设置双击事件
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
	 * @return onmousedown
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * 获取鼠标移开行事件
	 * @return String
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * 设置鼠标移开行事件
	 * @param onmouseout
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * 获取鼠标移动到行的事件
	 * @return String
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * 设置鼠标移动到行的事件
	 * @param onmouseover
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * 获取行样式
	 * @return String
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置行样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取行样式表
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置行样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 获取行宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置行宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 获取行列信息
	 * @return Range
	 */
	public Range getRange() {
		return range;
	}

	/**
	 * 设置行列信息
	 * @param range
	 */
	public void setRange(Range range) {
		this.range = range;
	}




}
