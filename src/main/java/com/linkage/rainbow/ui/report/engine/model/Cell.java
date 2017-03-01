package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.expr.DBVariable;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;

/**
 * 表示单元格<br>
 * 用于定义一个单元格的信息
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-1
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 陈亮   修改时间:2009-6-1<br>       
 *    修改内容:新建
 * <hr>
 */
public class Cell implements Serializable, Cloneable{
	
	/**对应的行信息*/
	protected Range row;
	/**对应的列信息*/
	protected Range col;
	
	/**宽度 */
	protected String width = "";
	/**高度 */
	protected String height = "";
	
	/** 表格的HTML的样式属性 */
	protected String style = "";
	/** 表格的HTML的样式表 */
	protected String styleClass = "";
	/**前影色 */	
	protected String color=null;
	/**背影图 */	
	protected String background=null;
	/**背影色 */	
	protected String bgcolor=null;
	
	/** 背景颜色 */	
	protected HSSFColor fillBackgroundColor  = null;
	
	/** 单击事件 */	
	protected String onclick;
	/**双击事件 */	
	protected String ondblclick;
	/**鼠标移出事件 */	
	protected String onmouseout;
	/**鼠标点击事件 */	
	protected String onmousedown;
	/**鼠标移入事件 */	
	protected String onmouseover;

	
	
    /** 字体 **/
	protected HSSFFont font = null;
	/** 字体颜色 **/
	protected HSSFColor fontColor = null;
	
	/**数据格式**/
	protected String dataFormat = null;

    /**单元格内容对齐样式 start**/
	 /**水平对齐样式,字符串表示**/
    protected String align =""; //
    /**水平对齐样式,数值表示**/
    protected int alignment =0; //水平对齐样式,数值表示
    /**水平对齐样式,字符串表示**/
    protected String valign =""; //水平对齐样式,字符串表示
    /**垂直对齐样式**/
    protected int verticalAlignment =1; //垂直对齐样式
	/**
	 * 单元的跨行数，对应HTML中
	 * < TD >的rowspan属性
	 */
    protected int rowSpan = 1;
    

	/**
	 * 单元的跨列数，对应HTML中
	 * < TD >的colspan属性
	 */
    protected int colSpan = 1;
    
    /**是否自动换行，用于Ｅxcel模块*/
    protected boolean wrapText =true; 
    /**是否不允许换行的属性，用于标签定义*/
    protected boolean noWrap = false; 


    /**缩进值*/
    protected int indention;//

    /**单元格内容对齐样式 end**/

	/** 扩展方向* */
	private String extDirection = "";
	
    /**边框样式 start**/
	/**下边框*/
    protected int borderBottom = -1; //
    /**下边框颜色*/
    protected HSSFColor borderBottomColor ; //
    /**顶边框*/
    protected int borderTop = -1; //
    /**顶边框颜色*/
    protected HSSFColor borderTopColor ; //
    /**右边框*/
    protected int borderRight = -1; //
    /**右边框颜色*/
    protected HSSFColor borderRightColor ; //
    /**左边框*/
    protected int borderLeft = -1; //
    /**左边框颜色*/
    protected HSSFColor borderLeftColor ; //

	/** 单元是否隐藏的属性，即在toHTMLCode生成的HTML文件中不显示 */
	private boolean isHidden = false;

	/** 单元的内容 */
	private Object content ;
	
	/** 显示内容,content 与 showContent的别分在于,
	 * 如数据集分组可能会有ID值与显示value,ID值则存放在 content,显示值放在showContent.
	 * content可能用于比较,showContent只做为显示时使用*/
	private Object showContent ;
	
	/** 默认值,如果content,与showContent 都为空时,显示默认值 */
	private  Object defaultContent; //
	
	/** 是否有显示内容,如果有,则报表输出时,输出显示内容,没有则输出内容.**/
	private boolean isHaveShowContent = false;

	/** 链接*/
	protected String link;
	/** 计算结果值的表达式 */
	private Expr linkExpr; //
	/** 链接提交窗口 */
	protected String linkTarget;
	/** 链接提示 */
	protected String linkTitle;



	/** 对应的扩展后区域* */
	private Field extField;
	/** 是否为空单元格* */
	private boolean isBlank = false;


	/** 钻取方式* */
	private String drillType ;
	/** 钻取时的条件* */
	private String drillTerm; 
	/** 钻取有效条件判断,当条件为true时,才能钻取* */
	private boolean drillAvai;

	/** 单元格别名* */
	private String alias;
//	private String id;
	
	private Expr expr; //计算结果值的表达式
	private boolean isExpr;//是否为需要表达式计算的单元格
	private boolean isExtended;//是否已扩展过

	private Events events = new Events();
	
	//关系的数据对象,用于链接表达式等使用
	private Object relDBObj;
	//与其他单元格关联的数据对象,用于多数据源关联.
	private List relDBVar = new ArrayList();
	
	private String imgPath;//斜线表头图片路径

	/** 表达式 */

	
	
	private Expr styleExpr; //计算样式的表达式
	private Expr styleClassExpr; //计算样式表的表达式
	private Expr backgroundExpr; //计算背影图的表达式
	private Expr colorExpr; //计算前影色的表达式
	private Expr bgcolorExpr; //计算背影色的表达式
	private Expr dataFormatExpr; //数据格式表达式
	
	private Expr onclickExpr; //计算单击事件的表达式
	private Expr ondblclickExpr; //计算双击事件的表达式
	private Expr onmouseoutExpr; //计算鼠标移出事件的表达式
	private Expr onmousedownExpr; //计算鼠标点击事件的表达式
	private Expr onmouseoverExpr; //计算鼠标移入事件的表达式
	
	private String cellType; //单元格类型,在olap中使用.分cross-heading,column-heading,column-heading-sum,row-heading,row-heading-sum,cell,cell-col-sum,cell-row-sum,cell-cross-sum 
	private String title;//单元格提示信息
	private String rowTitle;//单元格对应的行头提示
	private String colTitle;//单元格对应的列头提示
	private String measureTitle;//单元格对应的指标名提示
	private Map drillInfo;//olap钻取信息
	private String toolTips;//工具提示层信息
	private String borderColor;//线条颜色
	
	/**
	 * 单元格类空构造函数
	 *
	 */
	public Cell() {
		
	}

	/**
	 * 取得此单元格对应的扩展后区域
	 * @return 此单元格对应的扩展后区域
	 * @see Field
	 */
	public Field getExtField() {
		return extField;
	}

	/**
	 * 设置此单元格对应的扩展后区域
	 * @param extField 此单元格对应的扩展后区域
	 * @see Field
	 */
	public void setExtField(Field extField) {
		this.extField = extField;
	}

	/**
	 * 取得扩展方向 
	 * @return 扩展方向
	 */
	public String getExtDirection() {
		return extDirection;
	}

	/**
	 * 设置扩展方向
	 * @param extDirection 扩展方向
	 */
	public void setExtDirection(String extDirection) {
		this.extDirection = extDirection;
	}
//
//	public String getFieldType() {
//		return fieldType;
//	}
//
//	public void setFieldType(String fieldType) {
//		this.fieldType = fieldType;
//	}





	/**
	 * 单元格类构造函数
	 * @param str
	 *            单元格内容
	 */
	public Cell(String str) {
		super();
		this.content = str;
	}

	/**
	 * 获得单元的内容
	 * 
	 * @return　单元的内容
	 */
	public Object getContent() {
		return this.content;
	}

	/**
	 * 设置单元的内容
	 * 
	 * @param o　单元的内容
	 */
	public void setContent(Object o) {
		this.content = o;
	}

	/**
	 * 设置单元的跨行数
	 * 
	 * @param i　单元的跨行数
	 */
	public void setRowSpan(int i) {
		this.rowSpan = i;
	}

	/**
	 * 设置单元的跨行数
	 * 
	 * @param i　单元的跨行数
	 */
	public void setColSpan(int i) {
		this.colSpan = i;
	}


	/**
	 * 获得单元的跨行数
	 * 
	 * @return int　单元的跨行数
	 */
	public int getRowSpan() {
		return this.rowSpan;
	}

	/**
	 * 获得单元的跨列数
	 * 
	 * @return int　单元的跨行数
	 */
	public int getColSpan() {
		return this.colSpan;
	}


	/**
	 * 返回一个克隆对象。
	 */
	public Object clone() {
        Cell result = null;
		try { 
		    result = (Cell)super.clone();
		    result.events = (Events)events.clone();
		} catch (Exception e) { 
		    // assert false;
		}
        return result;
	}

	/**
	 * 获得是否允许换行的属性
	 * 
	 * @return　是否允许换行的属性
	 */
	public boolean getNoWrap() {
		return noWrap;
	}

	/**
	 * 设置是否允许换行的属性
	 * 　
	 * @param noWrap　是否允许换行的属性
	 */
	public void setNoWrap(boolean noWrap) {
		this.noWrap = noWrap;
		this.wrapText=!noWrap;
	}

//	/**
//	 * 获得HTML中的样式
//	 * 
//	 * @return
//	 */
//	public String getCssStyle() {
//		return cssStyle;
//	}
//
//	/**
//	 * 设置HTML中的样式
//	 * 
//	 * @param cssStyle
//	 */
//	public void setCssStyle(String cssStyle) {
//		this.cssStyle = cssStyle;
//	}

//	public String getExpression() {
//		return expression;
//	}
//
//	public void setExpression(String expression) {
//		this.expression = expression;
//	}

	/**
	 * 输出单元为字符串
	 */
	public String toString() {
		return  (showContent == null ? "" : showContent.toString())+"<"+ (content == null ? "" : content.toString())+">";
	}

//	public DBGroup getGroup() {
//		return group;
//	}
//
//	public void setGroup(DBGroup group) {
//		this.group = group;
//	}

	/**
	 * 是否隐藏
	 * @return 是否隐藏
	 */
	public boolean isHidden() {
		return isHidden;
	}

	/**
	 * 是否隐藏
	 * @param isHidden 是否隐藏
	 */
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * 是否为空单元格
	 * @return 是否为空单元格
	 */
	public boolean isBlank() {
		return isBlank;
	}

	/**
	 * 设置为空单元格
	 * @param isBlank 是否为空单元格
	 */
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

	/**
	 * 取得单元格显示内容
	 * @return 单元格显示内容
	 */
	public Object getShowContent() {
		
		if(isHaveShowContent)
			return showContent;
		else 
			return content;
	}

	/**
	 * 设置单元格显示内容
	 * @param showContent 单元格显示内容
	 */
	public void setShowContent(Object showContent) {
		isHaveShowContent = true;
		this.showContent = showContent;
	}

	/**
	 * 取得钻取时的条件
	 * @return　钻取时的条件
	 */
	public String getDrillTerm() {
		return drillTerm;
	}

	/**
	 * 设置钻取时的条件
	 * @param drillTerm 钻取时的条件
	 */
	public void setDrillTerm(String drillTerm) {
		this.drillTerm = drillTerm;
	}
	/**
	 * 设置钻取时的条件
	 * @param drillTerm 钻取时的条件
	 */
	public void setDrillTerm(Object drillTerm) {
		if(drillTerm != null)
			this.drillTerm = drillTerm.toString();
		else
			this.drillTerm = null;
	}

	/**
	 * 取得钻取方式方法,1树形钻取，2替代钻取，3前两种都支持
	 * @return 钻取方式方法,1树形钻取，2替代钻取，3前两种都支持
	 */
	public String getDrillType() {
		return drillType;
	}

	/**
	 * 设置钻取方式方法,1树形钻取，2替代钻取，3前两种都支持
	 * @param drillType 钻取方式方法,1树形钻取，2替代钻取，3前两种都支持
	 */
	public void setDrillType(String drillType) {
		this.drillType = drillType;
	}

	/**
	 * 取得　钻取有效条件判断,当条件为true时,才能钻取
	 * @return 钻取有效条件判断,当条件为true时,才能钻取
	 */
	public boolean getDrillAvai() {
		return drillAvai;
	}

	/**
	 * 设置钻取有效条件判断,当条件为true时,才能钻取
	 * @param drillAvai 钻取有效条件判断,当条件为true时,才能钻取
	 */
	public void setDrillAvai(boolean drillAvai) {
		this.drillAvai = drillAvai;
	}

	/**
	 * 取得 计算结果值的表达式
	 * @return 计算结果值的表达式
	 * @see Expr
	 */
	public Expr getExpr() {
		return expr;
	}

	/**
	 * 设置 计算结果值的表达式
	 * @param expr 计算结果值的表达式
	 * @see Expr
	 */
	public void setExpr(Expr expr) {
		this.expr = expr;
		if(expr != null)
			isExpr = true;
	}
	
	/**
	 * 取得Excel下边框颜色
	 * @return Excel下边框颜色
	 */
	public HSSFColor getBorderBottomColor() {
		return borderBottomColor;
	}
	/**
	 * 设置Excel下边框颜色
	 * @param borderBottomColor Excel下边框颜色
	 */
	public void setBorderBottomColor(HSSFColor borderBottomColor) {
		this.borderBottomColor = borderBottomColor;
	}

	/**
	 * 取得Excel左边框颜色
	 * @return Excel左边框颜色
	 */
	public HSSFColor getBorderLeftColor() {
		return borderLeftColor;
	}

	/**
	 * 设置Excel左边框颜色
	 * @param borderLeftColor Excel左边框颜色
	 */
	public void setBorderLeftColor(HSSFColor borderLeftColor) {
		this.borderLeftColor = borderLeftColor;
	}

	/**
	 * 取得Excel右边框颜色
	 * @return Excel右边框颜色
	 */
	public HSSFColor getBorderRightColor() {
		return borderRightColor;
	}

	/**
	 * 设置 Excel右边框颜色
	 * @param borderRightColor Excel右边框颜色
	 */
	public void setBorderRightColor(HSSFColor borderRightColor) {
		this.borderRightColor = borderRightColor;
	}

	/**
	 * 取得Excel顶边框颜色
	 * @return Excel顶边框颜色
	 */
	public HSSFColor getBorderTopColor() {
		return borderTopColor;
	}

	/**
	 * 设置Excel顶边框颜色
	 * @param borderTopColor Excel顶边框颜色
	 */
	public void setBorderTopColor(HSSFColor borderTopColor) {
		this.borderTopColor = borderTopColor;
	}

	/**边框样式 end**/



	/**
	 * 获得用百分比表示的高度。
	 * 
	 * @return 获得用百分比表示的高度
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置用百分比表示的高度
	 * 
	 * @param height 获得用百分比表示的高度
	 */
	public void setHeight(int height) {
		this.height = ""+height;
	}
	/**
	 * 设置用百分比表示的高度
	 * 
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 取得下边框大小
	 * @return 下边框大小
	 */
	public int getBorderBottom() {
		return borderBottom;
	}

	/**
	 * 设置下边框大小
	 * @param borderBottom 下边框大小
	 */
	public void setBorderBottom(int borderBottom) {
		this.borderBottom = borderBottom;
	}

	/**
	 * 取得左边框大小
	 * @return 左边框大小
	 */
	public int getBorderLeft() {
		return borderLeft;
	}

	/**
	 * 设置左边框大小
	 * @param borderLeft 左边框大小
	 */
	public void setBorderLeft(int borderLeft) {
		this.borderLeft = borderLeft;
	}

	/**
	 * 取得右边框大小
	 * @return 右边框大小
	 */
	public int getBorderRight() {
		return borderRight;
	}

	/**
	 * 设置右边框大小
	 * @param borderRight 右边框大小
	 */
	public void setBorderRight(int borderRight) {
		this.borderRight = borderRight;
	}

	/**
	 * 取得顶边框大小
	 * @return 顶边框大小
	 */
	public int getBorderTop() {
		return borderTop;
	}

	/**
	 * 设置顶边框大小
	 * @param borderTop 顶边框大小
	 */
	public void setBorderTop(int borderTop) {
		this.borderTop = borderTop;
	}


	/**
	 * 取得对齐方式
	 * @return 对齐方式
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * 设置excel对齐方式
	 * @param alignment excel对齐方式
	 */
	public void setAlignment(int alignment) {
		this.alignment = alignment;
		this.align = PropertyDefine.alignNumToHtml(alignment);
	}
	
	/**
	 * 取得excel对齐方式
	 * @return excel对齐方式
	 */
	public int getAlignment() {
		return this.alignment;
	}

	/**
	 * 设置对齐方式
	 * @param align 对齐方式
	 */
	public void setAlign(String align) {
		this.align = align;
		this.alignment = PropertyDefine.alignHtmlToNum(align);
	}


	/**
	 * 取得上下对齐方式
	 * @return 上下对齐方式
	 */
	public String getValign(){
		return valign;
	}
	/**
	 * 设置上下对齐方式
	 * @param valign 上下对齐方式
	 */
	public void setValign(String valign){
		this.valign=valign;
		this.verticalAlignment = PropertyDefine.valignHtmlToNum(valign);
	}
	/**
	 * 取得Excel上下对齐方式
	 * @return　Excel上下对齐方式
	 */
	public int getVerticalAlignment() {
		return verticalAlignment;
	}


	/**
	 * 设置Excel上下对齐方式
	 * @param verticalAlignment Excel上下对齐方式
	 */
	public void setVerticalAlignment(int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		this.valign = PropertyDefine.valignNumToHtml(verticalAlignment);
	}





	/**
	 * 取得excel背景色
	 * @return excel背景色
	 */
	public HSSFColor getFillBackgroundColor () {
		return fillBackgroundColor ;
	}


	/**
	 * 设置excel背景色
	 * @param fillBackgroundColor excel背景色
	 */
	public void setFillBackgroundColor (HSSFColor fillBackgroundColor ) {
		this.fillBackgroundColor  = fillBackgroundColor ;
	}


	/**
	 * 取得Excel字体
	 * @return Excel字体
	 */
	public HSSFFont getFont() {
		return font;
	}


	/**
	 * 设置Excel字体
	 * @param font Excel字体
	 */
	public void setFont(HSSFFont font) {
		this.font = font;
	}


	/**
	 * 取得数据格式串
	 * @return 数据格式串
	 */
	public String getDataFormat() {
		return dataFormat;
	}


	/**
	 * 设置数据格式串
	 * @param dataFormat 数据格式串
	 */
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	/**
	 * 取得excel字体颜色
	 * @return excel字体颜色
	 */
	public HSSFColor getFontColor() {
		return fontColor;
	}
	/**
	 * 设置excel字体颜色
	 * @param fontColor excel字体颜色
	 */
	public void setFontColor(HSSFColor fontColor) {
		this.fontColor = fontColor;
	}
	/**
	 * 取得单元格宽度
	 * @return 单元格宽度
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * 设置单元格宽度
	 * @param width 单元格宽度
	 */
	public void setWidth(int width) {
		this.width = ""+width;
	}
	/**
	 * 设置单元格宽度
	 * @param width 单元格宽度
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 取得单元格格式表
	 * @return 单元格格式表
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * 设置单元格格式表
	 * @param styleClass 单元格格式表
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 取得缩进个数
	 * @return 缩进个数
	 */
	public int getIndention() {
		return indention;
	}

	/**
	 * 设置缩进个数
	 * @param indention 缩进个数
	 */
	public void setIndention(int indention) {
		this.indention = indention;
	}

	/**
	 * 取得Excel是否自动换行
	 * @return Excel是否自动换行
	 */
	public boolean isWrapText() {
		return wrapText;
	}

	/**
	 * 设置Excel是否自动换行
	 * @param wrapText Excel是否自动换行
	 */
	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
		this.noWrap=!wrapText;
	}

	/**
	 * 取得背景图
	 * @return 背景图
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置背景图
	 * @param background 背景图
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * 取得背景色
	 * @return 背景色
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	/**
	 * 设置背景色
	 * @param bgcolor 背景色
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	/**
	 * 取得对应的列对象
	 * @return 对应的列对象
	 */
	public Range getCol() {
		return col;
	}

	/**
	 * 设置对应的列对象
	 * @param col 对应的列对象
	 */
	public void setCol(Range col) {
		this.col = col;
	}

	/**
	 * 取得对应的行对象
	 * @return 对应的行对象
	 */
	public Range getRow() {
		return row;
	}

	/**
	 * 设置对应的行对象
	 * @param row 对应的行对象
	 */
	public void setRow(Range row) {
		this.row = row;
	}

	/**
	 * 取得单元格样式
	 * @return 单元格样式
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置单元格样式
	 * @param style　单元格样式
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 取得单元格链接
	 * @return 单元格链接
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置单元格链接
	 * @param link 单元格链接
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 取得单元格链接提交窗口名
	 * @return　单元格链接提交窗口名
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * 设置单元格链接提交窗口名
	 * @param linkTarget 单元格链接提交窗口名
	 */
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	/**
	 * 取得单元onclick事件
	 * @return 单元onclick事件
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * 设置单元onclick事件
	 * @param onclick 单元onclick事件
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * 取得单元ondblclick事件
	 * @return 单元ondblclick事件
	 */
	public String getOndblclick() {
		return ondblclick;
	}

	/**
	 * 设置单元ondblclick事件
	 * @param ondblclick 单元ondblclick事件
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * 取得单元onmousedown事件
	 * @return 单元onmousedown事件
	 */
	public String getOnmousedown() {
		return onmousedown;
	}

	/**
	 * 设置单元onmousedown事件
	 * @param onmousedown 单元onmousedown事件
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * 取得单元onmouseout事件
	 * @return 单元onmouseout事件
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * 设置单元onmouseout事件
	 * @param onmouseout 单元onmouseout事件
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * 取得单元onmouseover事件
	 * @return 单元onmouseover事件
	 */
	public String getOnmouseover() {
		return onmouseover;
	}
	
	/**
	 * 设置单元onmouseover事件 
	 * @param onmouseover 单元onmouseover事件
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * 判断单元格内容是否为表达式
	 * @return 单元格内容是否为表达式
	 */
	public boolean isExpr() {
		return isExpr;
	}

	/**
	 * 设置单元格内容是否为表达式
	 * @param isExpr 单元格内容是否为表达式
	 */
	public void setExpr(boolean isExpr) {
		this.isExpr = isExpr;
	}

	/**
	 * 是否已扩展过
	 * @return 是否已扩展过
	 */
	public boolean isExtended() {
		return isExtended;
	}

	/**
	 * 设置是否已扩展过
	 * @param isExtended 是否已扩展过
	 */
	public void setExtended(boolean isExtended) {
		this.isExtended = isExtended;
	}

//	public void addEvent(Object eventObj,boolean isLengthways){
//		events.addEvent(eventObj,isLengthways);
//			
//	}
	/**
	 * 增加单元格对应的事件对象
	 * @param eventObj 事件对象
	 */
	public void addEvent(Event eventObj){
		events.addEvent(eventObj);
			
	}

	/**
	 * 取得此单元格所有事件对象
	 * @return 此单元格所有事件对象
	 */
	public Events getEvents() {
		return events;
	}

	/**
	 * 取得单元格别名
	 * @return 单元格别名
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * 设置单元格别名
	 * @param alias 单元格别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 取得关联的数据对象
	 * @return 关联的数据对象
	 */
	public Object getRelDBObj() {
		return relDBObj;
	}

	/**
	 * 设置关联的数据对象
	 * @param relDBObj 关联的数据对象
	 */
	public void setRelDBObj(Object relDBObj) {
		this.relDBObj = relDBObj;
	}

	/**
	 * 取得单元格链接表达式
	 * @return 单元格链接表达式
	 */
	public Expr getLinkExpr() {
		return linkExpr;
	}

	/**
	 * 设置单元格链接表达式
	 * @param linkExpr 单元格链接表达式
	 */
	public void setLinkExpr(Expr linkExpr) {
		this.linkExpr = linkExpr;
	}

	
	/**
	 * 增加关联的数据记录集变量
	 * @param dbVar　数据记录集变量
	 * @see DBVariable
	 */
	public void addRelDBVar(DBVariable dbVar) {
		int iSize = relDBVar.size();
		for(int i=0;i<iSize;i++){
			DBVariable dbVarTmp = (DBVariable)relDBVar.get(i);
			if(dbVarTmp.getDsName().equals(dbVar.getDsName())&& dbVarTmp.getColName().equals(dbVar.getColName())){
				if(dbVar.getExpr().getDbFuncList().size()>dbVarTmp.getExpr().getDbFuncList().size()){
					relDBVar.remove(i);
					relDBVar.add(dbVar);
				}
				return;
			}
		}
		relDBVar.add(dbVar);
	}
	
	/**
	 * 取得单元格关联的数据记录集变量
	 * @return 单元格关联的数据记录集变量
	 * @see DBVariable
	 */
	public List getRelDBVar() {
		return relDBVar;
	}

	/**
	 * 设置单元格关联的数据记录集变量
	 * @param relDBVar 单元格关联的数据记录集变量
	 */
	public void setRelDBVar(List relDBVar) {
		this.relDBVar = relDBVar;
	}

	/**
	 * 取得背景图表达式
	 * @return 背景图表达式
	 */
	public Expr getBackgroundExpr() {
		return backgroundExpr;
	}

	/**
	 * 设置背景图表达式
	 * @param backgroundExpr 背景图表达式
	 */
	public void setBackgroundExpr(Expr backgroundExpr) {
		this.backgroundExpr = backgroundExpr;
	}

	/**
	 * 取得背景色表达式
	 * @return 背景色表达式
	 */
	public Expr getBgcolorExpr() {
		return bgcolorExpr;
	}

	/**
	 * 设置背景色表达式
	 * @param bgcolorExpr 背景色表达式
	 */
	public void setBgcolorExpr(Expr bgcolorExpr) {
		this.bgcolorExpr = bgcolorExpr;
	}

	/**
	 * 取得单元格样式表表达式
	 * @return 单元格样式表表达式
	 */
	public Expr getStyleClassExpr() {
		return styleClassExpr;
	}

	/**
	 * 设置单元格样式表表达式
	 * @param styleClassExpr 单元格样式表表达式
	 */
	public void setStyleClassExpr(Expr styleClassExpr) {
		this.styleClassExpr = styleClassExpr;
	}

	/**
	 * 取得单元格样式表达式
	 * @return　单元格样式表达式
	 */
	public Expr getStyleExpr() {
		return styleExpr;
	}

	/**
	 * 设置单元格样式表达式 
	 * @param styleExpr 单元格样式表达式
	 */
	public void setStyleExpr(Expr styleExpr) {
		this.styleExpr = styleExpr;
	}

	/**
	 * 取得单元格onclick表达式
	 * @return 单元格onclick表达式
	 */
	public Expr getOnclickExpr() {
		return onclickExpr;
	}

	/**
	 * 设置单元格onclick表达式
	 * @param onclickExpr 单元格onclick表达式
	 */
	public void setOnclickExpr(Expr onclickExpr) {
		this.onclickExpr = onclickExpr;
	}

	/**
	 * 取得单元格ondblclick表达式
	 * @return 单元格ondblclick表达式
	 */
	public Expr getOndblclickExpr() {
		return ondblclickExpr;
	}

	/**
	 * 设置单元格ondblclick表达式
	 * @param ondblclickExpr 单元格ondblclick表达式
	 */
	public void setOndblclickExpr(Expr ondblclickExpr) {
		this.ondblclickExpr = ondblclickExpr;
	}

	/**
	 * 取得单元格onmousedown表达式
	 * @return 单元格onmousedown表达式
	 */
	public Expr getOnmousedownExpr() {
		return onmousedownExpr;
	}

	/**
	 * 设置单元格onmousedown表达式
	 * @param onmousedownExpr 单元格onmousedown表达式
	 */
	public void setOnmousedownExpr(Expr onmousedownExpr) {
		this.onmousedownExpr = onmousedownExpr;
	}

	/**
	 * 取得单元格onmouseout表达式
	 * @return　单元格onmouseout表达式
	 */
	public Expr getOnmouseoutExpr() {
		return onmouseoutExpr;
	}

	/**
	 * 设置单元格onmouseout表达式
	 * @param onmouseoutExpr 单元格onmouseout表达式
	 */
	public void setOnmouseoutExpr(Expr onmouseoutExpr) {
		this.onmouseoutExpr = onmouseoutExpr;
	}

	/**
	 * 取得单元格onmouseover表达式
	 * @return 单元格onmouseover表达式
	 */
	public Expr getOnmouseoverExpr() {
		return onmouseoverExpr;
	}

	/**
	 * 设置单元格onmouseover表达式
	 * @param onmouseoverExpr 单元格onmouseover表达式
	 */
	public void setOnmouseoverExpr(Expr onmouseoverExpr) {
		this.onmouseoverExpr = onmouseoverExpr;
	}

	/**
	 * 取得单元格前影色
	 * @return 单元格前影色
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置单元格前影色
	 * @param color 单元格前影色
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 取得单元格前影色表达式
	 * @return 单元格前影色表达式
	 */
	public Expr getColorExpr() {
		return colorExpr;
	}

	/**
	 * 设置单元格前影色表达式
	 * @param colorExpr 单元格前影色表达式
	 */
	public void setColorExpr(Expr colorExpr) {
		this.colorExpr = colorExpr;
	}

	/**
	 * 取得单元格链接提示
	 * @return 单元格链接提示
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * 设置单元格链接提示
	 * @param linkTitle 单元格链接提示
	 */
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * 取得斜线表头图片路径
	 * @return 斜线表头图片路径
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * 设置斜线表头图片路径
	 * @param imgPath 斜线表头图片路径
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * 取得单元格默认内容
	 * @return 单元格默认内容
	 */
	public Object getDefaultContent() {
		return defaultContent;
	}

	/**
	 * 设置单元格默认内容
	 * @param defaultContent 单元格默认内容
	 */
	public void setDefaultContent(Object defaultContent) {
		this.defaultContent = defaultContent;
	}

	/**
	 * 取得单元格格式串表达式
	 * @return 单元格格式串表达式
	 */
	public Expr getDataFormatExpr() {
		return dataFormatExpr;
	}

	/**
	 * 设置单元格格式串表达式
	 * @param dataFormatExpr 单元格格式串表达式
	 */
	public void setDataFormatExpr(Expr dataFormatExpr) {
		this.dataFormatExpr = dataFormatExpr;
	}

	/**
	 * 取得单元格类型,在olap中使用.分cross-heading,column-heading,column-heading-sum,row-heading,row-heading-sum,cell,cell-col-sum,cell-row-sum,cell-cross-sum 
	 * @return　 单元格类型
	 */
	public String getCellType() {
		return cellType;
	}

	/**
	 * 设置单元格类型,在olap中使用.分cross-heading,column-heading,column-heading-sum,row-heading,row-heading-sum,cell,cell-col-sum,cell-row-sum,cell-cross-sum 
	 * @param cellType 单元格类型
	 */
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}

	/**
	 * 取得单元格提示信息
	 * @return 单元格提示信息
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置单元格提示信息
	 * @param title 单元格提示信息
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 取得olap钻取信息
	 * @return　olap钻取信息
	 */
	public Map getDrillInfo() {
		return drillInfo;
	}

	/**
	 * 设置olap钻取信息
	 * @param drillInfo olap钻取信息
	 */
	public void setDrillInfo(Map drillInfo) {
		this.drillInfo = drillInfo;
	}

	/**
	 * 取得工具提示层信息
	 * @return 工具提示层信息
	 */
	public String getToolTips() {
		return toolTips;
	}

	/**
	 * 设置工具提示层信息
	 * @param toolTips 工具提示层信息
	 */
	public void setToolTips(String toolTips) {
		this.toolTips = toolTips;
	}

	/**
	 * 取得单元格对应的列头提示
	 * @return 单元格对应的列头提示
	 */
	public String getColTitle() {
		return colTitle;
	}

	/**
	 * 设置单元格对应的列头提示
	 * @param colTitle 单元格对应的列头提示
	 */
	public void setColTitle(String colTitle) {
		this.colTitle = colTitle;
	}

	/**
	 * 取得单元格对应的行头提示
	 * @return 单元格对应的行头提示
	 */
	public String getRowTitle() {
		return rowTitle;
	}

	/**
	 * 设置单元格对应的行头提示
	 * @param rowTitle 单元格对应的行头提示
	 */
	public void setRowTitle(String rowTitle) {
		this.rowTitle = rowTitle;
	}

	/**
	 * 取得单元格对应的指标名提示
	 * @return 单元格对应的指标名提示
	 */
	public String getMeasureTitle() {
		return measureTitle;
	}

	/**
	 * 设置单元格对应的指标名提示
	 * @param measureTitle 单元格对应的指标名提示
	 */
	public void setMeasureTitle(String measureTitle) {
		this.measureTitle = measureTitle;
	}

	/**
	 * 取得边框颜色
	 * @return 边框颜色
	 */
	public String getBorderColor() {
		return borderColor;
	}

	/**
	 * 设置边框颜色
	 * @param borderColor 边框颜色
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
	
	
}