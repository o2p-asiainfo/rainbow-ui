package com.linkage.rainbow.ui.views.jsp.ui.report;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import  com.linkage.rainbow.ui.components.report.Table;
import com.linkage.rainbow.util.config.DefaultSettings;
//import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * GridTag<br>
 * 数据表格显示组件,此组件与 GridHeader、GridHeaderCell、GridRow、GridCol组合使用。
 * <p>
 * @version 1.0
 * @author 陈亮 2009-04-8
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-04-8<br>
 *         修改内容:新建
 *         2、修改人员:陈亮 修改时间:2009-06-10<br>
 *         修改内容:新建skin属性
 *         <hr>
 */

public class TableTag extends AbstractUITag {

	//皮肤
	private String skin;
	
	/**数据集 */
	protected String ds ;
	
	/**宽度 */
	protected String width ;
	
	/**高度 */
	protected String height;
	
	/** 表格的HTML的样式属性 */
	protected String style ;
	
	/** 表格的HTML的样式表 */
	protected String styleClass ;
	/** 表格的单元格边距 */
	protected String cellpadding ;
	/** 表格的单元格间距 */
	protected String cellspacing ;
	/** 表格的过框明细 */
	protected String border;
	/** 表格的过框颜色 */
	protected String bordercolor ;
	/** 表格的对齐方式 */
	protected String align ;
	/** 表格的背影图 */
	protected String background ;
	/** 表格的背影色 */
	protected String bgcolor;
	
	/**是否超过高度宽带时冻结表头**/
	protected String scrolling;
	
	protected String exportBuffXls ;//对于一个页面有多个表格导出时,除最后一个外,需设置buffXls为true.
	protected String exportMultiTableInSheet;//对于一个页面多个表格导出时,各个表格分别存放在excel中的Sheet中
	protected String exportXlsSheetName;//excel中Sheet的名称

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
	public TableTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.table");
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
		return new Table(stack, req, res);
	}

	/**
	 * 继承父类的属性信息，并将标签类的属性设置给组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Table comp = (Table) component;
		if(ds != null)
			comp.setDs(ds);
		
		if(width != null)
			comp.setWidth(width);
		if(height != null)
			comp.setHeight(height);
		if(style != null)
			comp.setStyle(style);
		if(styleClass != null)
			comp.setStyleClass(styleClass);
		if(cellpadding != null)
			comp.setCellpadding(cellpadding);
		if(cellspacing != null)
			comp.setCellspacing(cellspacing);
		if(border != null)
			comp.setBorder(border);
		if(bordercolor != null)
			comp.setBordercolor(bordercolor);
		if(align != null)
			comp.setAlign(align);
		if(background != null)
			comp.setBackground(background);
		if(bgcolor != null)
			comp.setBgcolor(bgcolor);
		if(scrolling != null)
			comp.setScrolling(scrolling);
		if(exportBuffXls != null)
			comp.setExportBuffXls(exportBuffXls);
		if(exportMultiTableInSheet != null)
			comp.setExportMultiTableInSheet(exportMultiTableInSheet);
		if(exportXlsSheetName != null)
			comp.setExportXlsSheetName(exportXlsSheetName);
		
		//将当前行设置到值栈中,第一行从下标0开始
		getStack().getContext().put("commReportRowIndex",new Integer(-1));
	}

	
//	public int doStartTag() throws JspException {
//		super.doStartTag();
//		
//        return EVAL_BODY_INCLUDE;
//	}

	/**
	 * 获取对齐方式
	 * @return String
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * 设置对齐方式
	 * @param align
	 */
	public void setAlign(String align) {
		this.align = align;
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
	 * 获取边框
	 * @return String
	 */
	public String getBorder() {
		return border;
	}

	/**
	 * 设置边框
	 * @param border
	 */
	public void setBorder(String border) {
		this.border = border;
	}

	/**
	 * 获取边框色
	 * @return String
	 */
	public String getBordercolor() {
		return bordercolor;
	}

	/**
	 * 设置边框色
	 * @param bordercolor
	 */
	public void setBordercolor(String bordercolor) {
		this.bordercolor = bordercolor;
	}

	/**
	 * 获取表格的单元格边距
	 * @return String
	 */
	public String getCellpadding() {
		return cellpadding;
	}

	/**
	 * 设置表格的单元格边距
	 * @param cellpadding
	 */
	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	/**
	 * 获取表格的单元格间距
	 * @return String
	 */
	public String getCellspacing() {
		return cellspacing;
	}

	/**
	 * 设置表格的单元格间距
	 * @param cellspacing
	 */
	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	/**
	 * 获取表格高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置表格高度
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取皮肤
	 * @return String
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置皮肤
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 获取样式
	 * @return String
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取样式表
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 获取表格宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置表格宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 获取数据源
	 * @return String
	 */
	public String getDs() {
		return ds;
	}

	/**
	 * 设置数据源
	 * @param ds
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}

	/**
	 * 返回滚动条信息
	 * @return String
	 */
	public String getScrolling() {
		return scrolling;
	}

	/**
	 * 设置滚动条状态
	 * @param scrolling
	 */
	public void setScrolling(String scrolling) {
		this.scrolling = scrolling;
	}

	/**
	 * 获取/对于一个页面有多个表格导出时,除最后一个外,需设置buffXls为true.
	 * @return String
	 */
	public String getExportBuffXls() {
		return exportBuffXls;
	}

	/**
	 * 设置/对于一个页面有多个表格导出时,除最后一个外,需设置buffXls为true.
	 * @param exportBuffXls
	 */
	public void setExportBuffXls(String exportBuffXls) {
		this.exportBuffXls = exportBuffXls;
	}

	/**
	 * 返回对于一个页面多个表格导出时,各个表格分别存放在excel中的Sheet中
	 * @return String
	 */
	public String getExportMultiTableInSheet() {
		return exportMultiTableInSheet;
	}

	/**
	 * 设置对于一个页面多个表格导出时,各个表格分别存放在excel中的Sheet中
	 * @param exportMultiTableInSheet
	 */
	public void setExportMultiTableInSheet(String exportMultiTableInSheet) {
		this.exportMultiTableInSheet = exportMultiTableInSheet;
	}

	/**
	 * 获取excel中Sheet的名称
	 * @return String
	 */
	public String getExportXlsSheetName() {
		return exportXlsSheetName;
	}

	/**
	 * 设置excel中Sheet的名称
	 * @param exportXlsSheetName
	 */
	public void setExportXlsSheetName(String exportXlsSheetName) {
		this.exportXlsSheetName = exportXlsSheetName;
	}




}
