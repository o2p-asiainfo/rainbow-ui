package com.linkage.rainbow.ui.views.jsp.ui.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import com.linkage.rainbow.ui.components.report.FlashChart;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * PieFlashChartTag<br>
 * 图形标签类提供对图形标签中属性的操作
 * <p>
 * @version 1.0
 * @author 陈亮 2009-09-09
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:陈亮 修改时间:2009-09-09<br>
 * 修改内容:新建
 * <hr>
 */
public class PieFlashChartTag extends AbstractUITag{
	

	private String chartType;//图形类型
	private String value;//饼图的数据集
	private String colNames;//取得数据的字段名,用逗号分隔.例:colNames="title,value"
	private String width;//图形宽度
	private String height;//图形高度
	private String alpha;//图形透明度
	private String color;//饼块颜色
	private String backColor;//图形背景色
	private String backAlpha;//图形透明度
	private String background;//图形背景色
	private String borderColor;//边框颜色
	private String borderAlpha;//边框透明度
	private String plotType;//图形类型2D/3D
	private String precious;//精确度
	private String percentPrecious;//百分比精确度
	private String linkUrl;//超链接
	private String linkTarget;//连接目标
	private String noDataMessage;//无数据提示
	
	private String title;//标题
//	private String titlePosition;
	private String titleFontSize;//标题大小
	private String titleFontType;//标题字体
	private String titleFontColor;//标题颜色
	private String titleBackColor;//标题背景色
	
	private String showSubTitle;//是否显示副标题
	private String subTitleFlag;//副标题数据格式
	private String subTitle;//副标题
	private String subTitlePosition;//副标题位置	
	private String subTitleFontSize;//副标题大小
//	private String subTitleFontType;
	private String subTitleFontColor;//副标题颜色
	private String subTitleBackColor;//副标题背景色
	private String subTitleWidth;//副标题宽度
	private String subTitleMaxColumns;//副标题每行最多列数
	
	private String showPlot;//是否显示图形区域文字
	private String plotFlag;//图形区域数据格式
	private String plotTitle;//如果不采用预选的格式,图形指向的提示信息可自定义显示格式
//	private String plotBackColor;
	private String plotFontSize;//图形区域文字大小
	private String plotFontColor;//图形区域文字颜色
//	private String plotFontType;

	private String showToolTip;//是否显示提示信息
	private String toolTipFlag;//提示信息数据格式
	private String toolTipTitle;//如果不采用预选的格式,是否显示鼠标移入的提示信息可自定义显示格式
	private String toolTipAlpha;//提示信息透明度
	private String toolTipFontColor;//提示信息颜色
	private String toolTipFontSize;//提示信息文字大小
	private String toolTipBorderWidth;//提示信息边框宽度
	private String toolTipBorderAlpha;//提示信息透明度
	private String toolTipBorderColor;//提示信息边框颜色
	private String toolTipCornerRadius;//提示信息框的圆角大小
	
	
	
	//饼图附加属性
	private String pieRadius;//饼图外半径
	private String pieInnerRadius;//饼图内半径,默认为零
	private String pieHeight;//饼图的高度
	private String pieAngle;//饼图的视角,0~90
	private String animationStartTime;//动画效果时长,秒
	private String animationStartEffect;//可选的动画效果:bounce, 有回弹效果 regular, 普通的 strong与regular效果基本相似,速度前期非常快
	private String animationStartRadius;//饼图开始收缩前大小,用百分比,如120%
	private String animationStartAlpha;//饼图开始收缩前透明度
	
	private String settingsFile;//生成的文件信息
	
	/**
	 * 设置标签默认值
	 */
	public PieFlashChartTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.pieFlashChart");
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
		return new FlashChart(stack, req, res);
	}
	
	/**
	 * 将标签类属性赋值给组件类
	 */
	protected void populateParams() {
		super.populateParams();
		FlashChart chart=(FlashChart)component;
		chart.setChartType("PIE_CHART");
		chart.setValue(value);
		chart.setColNames(colNames);
		chart.setWidth(width);
		chart.setHeight(height);
		chart.setAlpha(alpha);
		chart.setColor(color);
		chart.setBackground(background);
		chart.setBackColor(backColor);
		chart.setBackAlpha(backAlpha);
		chart.setBorderColor(borderColor);
		chart.setBorderAlpha(borderAlpha);
		chart.setPlotType(plotType);
		chart.setLinkUrl(linkUrl);
		chart.setLinkTarget(linkTarget);
		chart.setPrecious(precious);
		chart.setPercentPrecious(percentPrecious);
		chart.setNoDataMessage(noDataMessage);
		
		chart.setTitle(title);
		chart.setTitleFontSize(titleFontSize);
		chart.setTitleFontType(titleFontType);
		chart.setTitleFontColor(titleFontColor);
		chart.setTitleBackColor(titleBackColor);
//		chart.setTitlePosition(titlePosition);
		
		chart.setShowSubTitle(showSubTitle);
		chart.setSubTitleFlag(subTitleFlag);
		chart.setSubTitle(subTitle);
		chart.setSubTitlePosition(subTitlePosition);
		chart.setSubTitleFontSize(subTitleFontSize);
//		chart.setSubTitleFontType(subTitleFontType);
		chart.setSubTitleFontColor(subTitleFontColor);
		chart.setSubTitleBackColor(subTitleBackColor);
		chart.setSubTitleWidth(subTitleWidth);
		chart.setSubTitleMaxColumns(subTitleMaxColumns);
		chart.setShowPlot(showPlot);
		chart.setPlotFlag(plotFlag);
		chart.setPlotTitle(plotTitle);
//		chart.setPlotBackColor(plotBackColor);
		chart.setPlotFontSize(plotFontSize);
		chart.setPlotFontColor(plotFontColor);
//		chart.setPlotFontType(plotFontType);
	
		chart.setShowToolTip(showToolTip);
		chart.setToolTipFlag(toolTipFlag);
		chart.setToolTipTitle(toolTipTitle);
		chart.setToolTipAlpha(toolTipAlpha);
		chart.setToolTipFontColor(toolTipFontColor);
		chart.setToolTipFontSize(toolTipFontSize);
		chart.setToolTipBorderWidth(toolTipBorderWidth);
		chart.setToolTipBorderAlpha(toolTipBorderAlpha);
		chart.setToolTipBorderColor(toolTipBorderColor);
		chart.setToolTipCornerRadius(toolTipCornerRadius);
		
		//饼图附加属性
		chart.setPieRadius(pieRadius);
		chart.setPieInnerRadius(pieInnerRadius);
		chart.setPieHeight(pieHeight);
		chart.setPieAngle(pieAngle);
		chart.setAnimationStartTime(animationStartTime);
		chart.setAnimationStartEffect(animationStartEffect);
		chart.setAnimationStartRadius(animationStartRadius);
		chart.setAnimationStartAlpha(animationStartAlpha);
		
		chart.setSettingsFile(settingsFile);
	}
	
	/**
	 * 获取图形透明度
	 * @return String
	 */
	public String getAlpha() {
		return alpha;
	}

	/**
	 * 设置图形透明度
	 * @param alpha
	 */
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	/**
	 * 获取饼图开始收缩前透明度
	 * @return String
	 */
	public String getAnimationStartAlpha() {
		return animationStartAlpha;
	}

	/**
	 * 设置饼图开始收缩前透明度
	 * @param animationStartAlpha
	 */
	public void setAnimationStartAlpha(String animationStartAlpha) {
		this.animationStartAlpha = animationStartAlpha;
	}

	/**
	 * 获取可选的动画效果:bounce, 有回弹效果 regular, 普通的 strong与regular效果基本相似,速度前期非常快
	 * @return String
	 */
	public String getAnimationStartEffect() {
		return animationStartEffect;
	}

	/**
	 * 设置可选的动画效果:bounce, 有回弹效果 regular, 普通的 strong与regular效果基本相似,速度前期非常快
	 * @param animationStartEffect
	 */
	public void setAnimationStartEffect(String animationStartEffect) {
		this.animationStartEffect = animationStartEffect;
	}

	/**
	 * 获取饼图开始收缩前大小,用百分比,如120%
	 * @return String
	 */
	public String getAnimationStartRadius() {
		return animationStartRadius;
	}

	/**
	 * 设置饼图开始收缩前大小,用百分比,如120%
	 * @param animationStartRadius
	 */
	public void setAnimationStartRadius(String animationStartRadius) {
		this.animationStartRadius = animationStartRadius;
	}

	/**
	 * 获取动画效果时长,秒
	 * @return String
	 */
	public String getAnimationStartTime() {
		return animationStartTime;
	}

	/**
	 * 设置动画效果时长,秒
	 * @param animationStartTime
	 */
	public void setAnimationStartTime(String animationStartTime) {
		this.animationStartTime = animationStartTime;
	}

	/**
	 * 获取背景色
	 * @return
	 */
	public String getBackColor() {
		return backColor;
	}

	/**
	 * 设置背景色
	 * @param backColor
	 */
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	/**
	 * 获取图形类型
	 * @return String
	 */
	public String getChartType() {
		return chartType;
	}

	/**
	 * 设置图形类型
	 * @param chartType
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	/**
	 * 获取数据的字段名,用逗号分隔.例:colNames="title,value"
	 * @return String
	 */
	public String getColNames() {
		return colNames;
	}

	/**
	 * 设置数据的字段名,用逗号分隔.例:colNames="title,value"
	 * @param colNames
	 */
	public void setColNames(String colNames) {
		this.colNames = colNames;
	}

	/**
	 * 获取饼块颜色
	 * @return String
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置饼块颜色
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 获取图形高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置图形高度
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取连接目标
	 * @return String
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * 设置连接目标
	 * @param linkTarget
	 */
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	/**
	 * 获取超链接
	 * @return String
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * 设置超链接
	 * @param linkUrl
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/**
	 * 获取无数据提示
	 * @return String
	 */
	public String getNoDataMessage() {
		return noDataMessage;
	}

	/**
	 * 设置无数据提示
	 * @param noDataMessage
	 */
	public void setNoDataMessage(String noDataMessage) {
		this.noDataMessage = noDataMessage;
	}

	/**
	 * 获取百分比精确度
	 * @return String
	 */
	public String getPercentPrecious() {
		return percentPrecious;
	}

	/**
	 * 设置百分比精确度
	 * @param percentPrecious
	 */
	public void setPercentPrecious(String percentPrecious) {
		this.percentPrecious = percentPrecious;
	}

	/**
	 * 获取饼图的视角,0~90
	 * @return String
	 */
	public String getPieAngle() {
		return pieAngle;
	}

	/**
	 * 设置饼图的视角,0~90
	 * @param pieAngle
	 */
	public void setPieAngle(String pieAngle) {
		this.pieAngle = pieAngle;
	}

	/**
	 * 获取饼图的高度
	 * @return String
	 */
	public String getPieHeight() {
		return pieHeight;
	}

	/**
	 * 设置饼图的高度
	 * @param pieHeight
	 */
	public void setPieHeight(String pieHeight) {
		this.pieHeight = pieHeight;
	}
	/**
	 * 获取饼图内半径,默认为零
	 * @return String
	 */
	public String getPieInnerRadius() {
		return pieInnerRadius;
	}

	/**
	 * 设置饼图内半径,默认为零
	 * @param pieInnerRadius
	 */
	public void setPieInnerRadius(String pieInnerRadius) {
		this.pieInnerRadius = pieInnerRadius;
	}
	/**
	 * 获取饼图外半径
	 * @return String
	 */
	public String getPieRadius() {
		return pieRadius;
	}

	/**
	 * 设置饼图外半径
	 * @param pieRadius
	 */
	public void setPieRadius(String pieRadius) {
		this.pieRadius = pieRadius;
	}
	/**
	 * 获取图形区域数据格式
	 * @return String
	 */
	public String getPlotFlag() {
		return plotFlag;
	}

	/**
	 * 设置图形区域数据格式
	 * @param plotFlag
	 */
	public void setPlotFlag(String plotFlag) {
		this.plotFlag = plotFlag;
	}
	/**
	 * 获取图形区域文字颜色
	 * @return String
	 */
	public String getPlotFontColor() {
		return plotFontColor;
	}

	/**
	 * 设置图形区域文字颜色
	 * @param plotFontColor
	 */
	public void setPlotFontColor(String plotFontColor) {
		this.plotFontColor = plotFontColor;
	}
	/**
	 * 获取图形区域文字大小
	 * @return String
	 */
	public String getPlotFontSize() {
		return plotFontSize;
	}

	/**
	 * 设置图形区域文字大小
	 * @param plotFontSize
	 */
	public void setPlotFontSize(String plotFontSize) {
		this.plotFontSize = plotFontSize;
	}
	/**
	 * 获取如果不采用预选的格式,图形指向的提示信息可自定义显示格式
	 * @return String
	 */
	public String getPlotTitle() {
		return plotTitle;
	}

	/**
	 * 设置如果不采用预选的格式,图形指向的提示信息可自定义显示格式
	 * @param plotTitle
	 */
	public void setPlotTitle(String plotTitle) {
		this.plotTitle = plotTitle;
	}
	/**
	 * 获取
	 * @return String
	 */
	public String getPlotType() {
		return plotType;
	}

	/**
	 * 设置
	 * @param plotType
	 */
	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}
	/**
	 * 获取精确度
	 * @return String
	 */
	public String getPrecious() {
		return precious;
	}

	/**
	 * 设置精确度
	 * @param precious
	 */
	public void setPrecious(String precious) {
		this.precious = precious;
	}
	/**
	 * 获取是否显示图形区域文字
	 * @return String
	 */
	public String getShowPlot() {
		return showPlot;
	}

	/**
	 * 设置是否显示图形区域文字
	 * @param showPlot
	 */
	public void setShowPlot(String showPlot) {
		this.showPlot = showPlot;
	}
	/**
	 * 获取是否显示副标题
	 * @return String
	 */
	public String getShowSubTitle() {
		return showSubTitle;
	}

	/**
	 * 设置是否显示副标题
	 * @param showSubTitle
	 */
	public void setShowSubTitle(String showSubTitle) {
		this.showSubTitle = showSubTitle;
	}
	/**
	 * 获取是否显示提示信息
	 * @return String
	 */
	public String getShowToolTip() {
		return showToolTip;
	}

	/**
	 * 设置是否显示提示信息
	 * @param showToolTip
	 */
	public void setShowToolTip(String showToolTip) {
		this.showToolTip = showToolTip;
	}
	/**
	 * 获取是否显示副标题
	 * @return String
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * 设置是否显示副标题
	 * @param subTitle
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	/**
	 * 获取副标题背景色
	 * @return String
	 */
	public String getSubTitleBackColor() {
		return subTitleBackColor;
	}

	/**
	 * 设置副标题背景色
	 * @param subTitleBackColor
	 */
	public void setSubTitleBackColor(String subTitleBackColor) {
		this.subTitleBackColor = subTitleBackColor;
	}
	/**
	 * 获取副标题数据格式
	 * @return String
	 */
	public String getSubTitleFlag() {
		return subTitleFlag;
	}

	/**
	 * 设置副标题数据格式
	 * @param subTitleFlag
	 */
	public void setSubTitleFlag(String subTitleFlag) {
		this.subTitleFlag = subTitleFlag;
	}
	/**
	 * 获取副标题颜色
	 * @return String
	 */
	public String getSubTitleFontColor() {
		return subTitleFontColor;
	}

	/**
	 * 设置副标题颜色
	 * @param subTitleFontColor
	 */
	public void setSubTitleFontColor(String subTitleFontColor) {
		this.subTitleFontColor = subTitleFontColor;
	}
	/**
	 * 获取副标题大小
	 * @return String
	 */
	public String getSubTitleFontSize() {
		return subTitleFontSize;
	}

	/**
	 * 设置副标题大小
	 * @param subTitleFontSize
	 */
	public void setSubTitleFontSize(String subTitleFontSize) {
		this.subTitleFontSize = subTitleFontSize;
	}
	/**
	 * 获取副标题位置	
	 * @return String
	 */
	public String getSubTitlePosition() {
		return subTitlePosition;
	}

	/**
	 * 设置副标题位置	
	 * @param subTitlePosition
	 */
	public void setSubTitlePosition(String subTitlePosition) {
		this.subTitlePosition = subTitlePosition;
	}
	/**
	 * 获取副标题宽度
	 * @return String
	 */
	public String getSubTitleWidth() {
		return subTitleWidth;
	}

	/**
	 * 设置副标题宽度
	 * @param subTitleWidth
	 */
	public void setSubTitleWidth(String subTitleWidth) {
		this.subTitleWidth = subTitleWidth;
	}
	/**
	 * 获取标题
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取标题背景色
	 * @return String
	 */
	public String getTitleBackColor() {
		return titleBackColor;
	}

	/**
	 * 设置标题背景色
	 * @param titleBackColor
	 */
	public void setTitleBackColor(String titleBackColor) {
		this.titleBackColor = titleBackColor;
	}
	/**
	 * 获取标题颜色
	 * @return String
	 */
	public String getTitleFontColor() {
		return titleFontColor;
	}

	/**
	 * 设置标题颜色
	 * @param titleFontColor
	 */
	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}
	/**
	 * 获取标题大小
	 * @return String
	 */
	public String getTitleFontSize() {
		return titleFontSize;
	}

	/**
	 * 设置标题大小
	 * @param titleFontSize
	 */
	public void setTitleFontSize(String titleFontSize) {
		this.titleFontSize = titleFontSize;
	}
	/**
	 * 获取标题字体
	 * @return String
	 */
	public String getTitleFontType() {
		return titleFontType;
	}

	/**
	 * 设置标题字体
	 * @param titleFontType
	 */
	public void setTitleFontType(String titleFontType) {
		this.titleFontType = titleFontType;
	}
	/**
	 * 获取提示信息数据格式
	 * @return String
	 */
	public String getToolTipFlag() {
		return toolTipFlag;
	}

	/**
	 * 设置提示信息数据格式
	 * @param toolTipFlag
	 */
	public void setToolTipFlag(String toolTipFlag) {
		this.toolTipFlag = toolTipFlag;
	}
	/**
	 * 获取如果不采用预选的格式,是否显示鼠标移入的提示信息可自定义显示格式
	 * @return String
	 */
	public String getToolTipTitle() {
		return toolTipTitle;
	}

	/**
	 * 设置如果不采用预选的格式,是否显示鼠标移入的提示信息可自定义显示格式
	 * @param toolTipTitle
	 */
	public void setToolTipTitle(String toolTipTitle) {
		this.toolTipTitle = toolTipTitle;
	}
	/**
	 * 获取饼图的数据集
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置饼图的数据集
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取图形宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置图形宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	/**
	 * 获取副标题每行最多列数
	 * @return String
	 */
	public String getSubTitleMaxColumns() {
		return subTitleMaxColumns;
	}

	/**
	 * 设置副标题每行最多列数
	 * @param subTitleMaxColumns
	 */
	public void setSubTitleMaxColumns(String subTitleMaxColumns) {
		this.subTitleMaxColumns = subTitleMaxColumns;
	}
	/**
	 * 获取图形背景色
	 * @return String
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置图形背景色
	 * @param background
	 */
	public void setBackground(String background) {
		this.background = background;
	}
	/**
	 * 获取边框透明度
	 * @return String
	 */
	public String getBorderAlpha() {
		return borderAlpha;
	}

	/**
	 * 设置边框透明度
	 * @param borderAlpha
	 */
	public void setBorderAlpha(String borderAlpha) {
		this.borderAlpha = borderAlpha;
	}
	/**
	 * 获取边框颜色
	 * @return String
	 */
	public String getBorderColor() {
		return borderColor;
	}

	/**
	 * 设置边框颜色
	 * @param borderColor
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	/**
	 * 获取提示信息透明度
	 * @return String
	 */
	public String getToolTipAlpha() {
		return toolTipAlpha;
	}

	/**
	 * 设置提示信息透明度
	 * @param toolTipAlpha
	 */
	public void setToolTipAlpha(String toolTipAlpha) {
		this.toolTipAlpha = toolTipAlpha;
	}
	/**
	 * 获取提示信息透明度
	 * @return String
	 */
	public String getToolTipBorderAlpha() {
		return toolTipBorderAlpha;
	}

	/**
	 * 设置提示信息透明度
	 * @param toolTipBorderAlpha
	 */
	public void setToolTipBorderAlpha(String toolTipBorderAlpha) {
		this.toolTipBorderAlpha = toolTipBorderAlpha;
	}
	/**
	 * 获取提示信息边框颜色
	 * @return String
	 */
	public String getToolTipBorderColor() {
		return toolTipBorderColor;
	}

	/**
	 * 设置提示信息边框颜色
	 * @param toolTipBorderColor
	 */
	public void setToolTipBorderColor(String toolTipBorderColor) {
		this.toolTipBorderColor = toolTipBorderColor;
	}
	/**
	 * 获取提示信息边框宽度
	 * @return String
	 */
	public String getToolTipBorderWidth() {
		return toolTipBorderWidth;
	}

	/**
	 * 设置提示信息边框宽度
	 * @param toolTipBorderWidth
	 */
	public void setToolTipBorderWidth(String toolTipBorderWidth) {
		this.toolTipBorderWidth = toolTipBorderWidth;
	}
	/**
	 * 获取提示信息框的圆角大小
	 * @return String
	 */
	public String getToolTipCornerRadius() {
		return toolTipCornerRadius;
	}

	/**
	 * 设置提示信息框的圆角大小
	 * @param toolTipCornerRadius
	 */
	public void setToolTipCornerRadius(String toolTipCornerRadius) {
		this.toolTipCornerRadius = toolTipCornerRadius;
	}
	/**
	 * 获取提示信息颜色
	 * @return String
	 */
	public String getToolTipFontColor() {
		return toolTipFontColor;
	}

	/**
	 * 设置提示信息颜色
	 * @param toolTipFontColor
	 */
	public void setToolTipFontColor(String toolTipFontColor) {
		this.toolTipFontColor = toolTipFontColor;
	}
	/**
	 * 获取提示信息文字大小
	 * @return String
	 */
	public String getToolTipFontSize() {
		return toolTipFontSize;
	}

	/**
	 * 设置提示信息文字大小
	 * @param toolTipFontSize
	 */
	public void setToolTipFontSize(String toolTipFontSize) {
		this.toolTipFontSize = toolTipFontSize;
	}
	/**
	 * 获取生成的文件信息
	 * @return String
	 */
	public String getSettingsFile() {
		return settingsFile;
	}

	/**
	 * 设置生成的文件信息
	 * @param settingsFile
	 */
	public void setSettingsFile(String settingsFile) {
		this.settingsFile = settingsFile;
	}
	/**
	 * 获取图形透明度
	 * @return String
	 */
	public String getBackAlpha() {
		return backAlpha;
	}

	/**
	 * 设置图形透明度
	 * @param backAlpha
	 */
	public void setBackAlpha(String backAlpha) {
		this.backAlpha = backAlpha;
	}


	
	
	
	
	
}
