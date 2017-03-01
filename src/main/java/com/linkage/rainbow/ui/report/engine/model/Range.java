
package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;

import com.linkage.rainbow.ui.report.engine.core.Expr;

/**
 * 行列<br>
 * 用于定义行列信息,如行列的类型(数据区,表头区,表尾区)
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

public class Range implements Serializable, Cloneable{

	/**行或列区域类型，分以下几种情况
		body:数据区
		head:表头区
		foot:表尾区
		separate:分隔线
	 */
	protected String areaType;
	/**列宽度*/
	protected String width;
	/**行高度*/
	protected String height;
	/**样式*/
	protected String style;
	/**样式表*/
	protected String styleClass;
	/**背影图*/
	protected String background;
	/**背影色*/
	protected String bgcolor;
	/**单击事件*/
	protected String onclick;
	/**双击事件*/
	protected String ondblclick;
	/**鼠标移出事件*/
	protected String onmouseout;
	/**鼠标点击事件*/
	protected String onmousedown;
	/**鼠标移入事件*/
	protected String onmouseover;
	
	private Expr styleExpr; //计算样式的表达式
	private Expr styleClassExpr; //计算样式表的表达式
	private Expr backgroundExpr; //计算背影图的表达式
	private Expr bgcolorExpr; //计算背影色的表达式
	
	private Expr onclickExpr; //计算单击事件的表达式
	private Expr ondblclickExpr; //计算双击事件的表达式
	private Expr onmouseoutExpr; //计算鼠标移出事件的表达式
	private Expr onmousedownExpr; //计算鼠标点击事件的表达式
	private Expr onmouseoverExpr; //计算鼠标移入事件的表达式
	
	/**
	 * 取得行或列区域类型
	 * @return 区域类型.分以下几种情况 <br>
	 *  body:数据区 <br>
	 * 	head:表头区 <br>
	 * 	foot:表尾区 <br>
	 * 	separate:分隔线 <br>
	 */
	public String getAreaType() {
		return areaType;
	}
	/**
	 * 设置区域类型
	 * @param areaType 区域类型.分以下几种情况 <br>
	 *  body:数据区 <br>
	 * 	head:表头区 <br>
	 * 	foot:表尾区 <br>
	 * 	separate:分隔线 <br>
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
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
	 * 取得背景颜色
	 * @return 背景颜色
	 */
	public String getBgcolor() {
		return bgcolor;
	}
	/**
	 * 设置背景颜色
	 * @param bgcolor 背景颜色
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	/**
	 * 取得高度
	 * @return 高度
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * 设置高度
	 * @param height 高度
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * 取得单击事件
	 * @return 单击事件
	 */
	public String getOnclick() {
		return onclick;
	}
	/**
	 * 设置单击事件
	 * @param onclick 单击事件
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	/**
	 * 取得双击事件
	 * @return 双击事件
	 */
	public String getOndblclick() {
		return ondblclick;
	}
	/**
	 * 设置双击事件
	 * @param ondblclick 双击事件
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}
	/**
	 * 取得鼠标点击事件
	 * @return 鼠标点击事件
	 */
	public String getOnmousedown() {
		return onmousedown;
	}
	/**
	 * 设置鼠标点击事件
	 * @param onmousedown 鼠标点击事件
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}
	/**
	 * 取得鼠标移出事件
	 * @return 鼠标移出事件
	 */
	public String getOnmouseout() {
		return onmouseout;
	}
	/**
	 * 设置鼠标移出事件
	 * @param onmouseout 鼠标移出事件
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	/**
	 * 取得鼠标移入事件
	 * @return 鼠标移入事件
	 */
	public String getOnmouseover() {
		return onmouseover;
	}
	/**
	 * 设置鼠标移入事件
	 * @param onmouseover 鼠标移入事件
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	/**
	 * 取得样式
	 * @return 样式
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * 设置样式
	 * @param style 样式
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * 取得样式表
	 * @return 样式表
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * 设置样式表
	 * @param styleClass 样式表
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	/**
	 * 取得宽度
	 * @return 宽度
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * 设置宽度
	 * @param width 宽度
	 */
	public void setWidth(String width) {
		this.width = width;
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
	 * 取得样式表表达式
	 * @return 样式表表达式
	 */
	public Expr getStyleClassExpr() {
		return styleClassExpr;
	}
	/**
	 * 设置样式表表达式
	 * @param styleClassExpr 样式表表达式
	 */
	public void setStyleClassExpr(Expr styleClassExpr) {
		this.styleClassExpr = styleClassExpr;
	}
	/**
	 * 取得样式表达式
	 * @return 样式表达式
	 */
	public Expr getStyleExpr() {
		return styleExpr;
	}
	/**
	 * 设置样式表达式
	 * @param styleExpr 样式表达式
	 */
	public void setStyleExpr(Expr styleExpr) {
		this.styleExpr = styleExpr;
	}
	/**
	 * 取得单击事件表达式
	 * @return 单击事件表达式
	 */
	public Expr getOnclickExpr() {
		return onclickExpr;
	}
	/**
	 * 设置单击事件表达式
	 * @param onclickExpr 单击事件表达式
	 */ 
	public void setOnclickExpr(Expr onclickExpr) {
		this.onclickExpr = onclickExpr;
	}
	/**
	 * 取得双击事件表达式
	 * @return 双击事件表达式
	 */
	public Expr getOndblclickExpr() {
		return ondblclickExpr;
	}
	/**
	 * 设置双击事件表达式
	 * @param ondblclickExpr 双击事件表达式
	 */
	public void setOndblclickExpr(Expr ondblclickExpr) {
		this.ondblclickExpr = ondblclickExpr;
	}
	/**
	 * 取得鼠标点击事件的表达式
	 * @return 鼠标点击事件的表达式
	 */
	public Expr getOnmousedownExpr() {
		return onmousedownExpr;
	}
	/**
	 * 设置鼠标点击事件的表达式
	 * @param onmousedownExpr 鼠标点击事件的表达式
	 */
	public void setOnmousedownExpr(Expr onmousedownExpr) {
		this.onmousedownExpr = onmousedownExpr;
	}
	/**
	 * 取得鼠标移出事件的表达式
	 * @return 鼠标移出事件的表达式
	 */
	public Expr getOnmouseoutExpr() {
		return onmouseoutExpr;
	}
	/**
	 * 设置鼠标移出事件的表达式
	 * @param onmouseoutExpr 鼠标移出事件的表达式
	 */
	public void setOnmouseoutExpr(Expr onmouseoutExpr) {
		this.onmouseoutExpr = onmouseoutExpr;
	}
	/**
	 * 取得鼠标移出事件的表达式
	 * @return 鼠标移出事件的表达式
	 */
	public Expr getOnmouseoverExpr() {
		return onmouseoverExpr;
	}
	/**
	 * 设置鼠标移出事件的表达式
	 * @param onmouseoverExpr 鼠标移出事件的表达式
	 */
	public void setOnmouseoverExpr(Expr onmouseoverExpr) {
		this.onmouseoverExpr = onmouseoverExpr;
	}
	
	
	
}
