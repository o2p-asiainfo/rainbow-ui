package com.linkage.rainbow.ui.views.jsp.ui.report;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.report.FlashChart;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * BarFlashChartTag<br>
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
public class BarFlashChartTag extends AbstractUITag{
	
	private String chartType;//图形类型
	private String plotType;//图形是2D/3D
	private String value;//图形数据集
	private String rowTitle;//行标题
	private String colNames;//列名
	private String rightValue;//数据对应的实例化对象
	private String rightRowTitle;//多组数据必须指定的字段
	private String rightColNames;//取得数据的字段名,用逗号分隔
	private String color;//柱状颜色
	private String backColor;//图形背景色
	private String backAlpha;//背景色透明度
	private String background ;//背景图
	private String borderColor;//边框颜色
	private String borderAlpha;//边框透明度
	private String alpha;//透明度设置
	private String precious;//数据精确度
	private String linkUrl;//超链接
	private String linkTarget;//超链接对象
	
	private String title;//标题
//	private String titlePosition;
	private String titleFontSize;//图形标题大小
	private String titleFontType;//图形标题字体
	private String titleFontColor;//图形标题颜色
	private String titleBackColor;//图形标题背景色
	
	private String showSubTitle;//是否显示副标题
	private String subTitleFlag;//副标题数据格式
	private String subTitle;//副标题内容
	private String subTitlePosition;//副标题位置	
	private String subTitleFontSize;//副标题大小
//	private String subTitleFontType;
	private String subTitleFontColor;//副标题字体颜色
	private String subTitleBackColor;//副标题背景色
	private String subTitleWidth;//副标题宽度
	private String subTitleMaxColumns;//子标题最多一行显示多少列
	
	private String showPlot;//是否显示图形区域文字
	private String plotFlag;//图形区域数据格式
	private String plotTitle;//如果不采用预选的格式,图形指向的提示信息可自定义显示格式
//	private String plotBackColor;
	private String plotFontSize;//图形区域文字大小
	private String plotFontColor;//图形区域文字颜色
//	private String plotFontType;

	private String showToolTip;//是否显示移动提示信息
	private String toolTipFlag;//移动上去提示信息显示样式
	private String toolTipTitle;//如果不采用预选的格式,是否显示鼠标移入的提示信息可自定义显示格式
	private String toolTipAlpha;//显示提示信息背景透明度
	private String toolTipFontColor;//显示提示信息字体颜色
	private String toolTipFontSize;//显示提示信息字体大小
	private String toolTipBorderWidth;//显示提示信息的边框线条宽度
	private String toolTipBorderAlpha;//显示提示信息边框线条透明度
	private String toolTipBorderColor;//显示提示信息边框线条颜色
	private String toolTipCornerRadius;//显示提示信息框的圆角大小
	
	private String showXLine;//是否显示x轴辅助线
	private String showYLine;//是否显示y轴辅助线
	private String xlineColor;//x轴辅助线颜色
	private String ylineColor;//y轴辅助线颜色
	
	private String showXAxes;//显示横坐标
	private String showYAxes;//显示纵坐标
	private String yleftType;//左边坐标显示类型,分:line, stacked, 100% stacked
	private String yrightType;//右边坐标显示类型,分:line, stacked, 100% stacked
	
	private String xtitle;//水平标题
	private String ytitle;//垂直标题
	private String xtitleFontType;//水平标题字体
	private String xtitleFontSize;//水平标题大小
	private String xtitleFontColor;//水平标题字体颜色
	private String xfontType;//水平文字字体
	private String xfontSize;//水平文字大小
	private String xfontColor;//水平文字颜色
	private String xposition;//水平文字位置
	private String xrotate;//水平文字倾斜度(可选范围0~90)
	private String ytitleFontType;//垂直区域文字类型
	private String ytitleFontSize;//垂直区域文字大小
	private String ytitleFontColor;//垂直区域文字颜色
	private String yfontType;//垂直区域文字
	private String yfontSize;//垂直区域文字大小
	private String yfontColor;//垂直区域文字颜色
	private String yposition;//垂直区域文字位置
	private String yrotate;//垂直文字倾斜度(可选范围0~90)
	private String rangeLineColor;//横虚线颜色 默认灰色
	private String showRangeLine;//是否显示虚线
	private String showDataTitle;//是否在柱子上显示数据
	private String dataTitle;//柱子上显示数据格式定义
	private String dataTitlePosition;//数据在柱子的位置
	private String dataTitleFontSize;//柱子上显示数据字体大小
	private String dataTitleFontColor;//柱子上显示数据字体颜色
	private String barType;//柱状类型
	private String barWidth;//柱状宽度
	private String barMargin;//柱间距离
	private String showBarAverage;//是否限制柱子平均值
	private String barAverageFontType;//平均值字体
	private String barAverageFontSize;//平均值文字大小
	private String barAverageFontColor;//平均值文字颜色
	private String barAverageLineColor;//平均线颜色
	private String oblique;//倾斜度
	private String depth;//柱体深度
	private String angle;//柱体的视角,0~90

	
	private String width;//图形宽度
	private String height;//图形高度


	private String plotOrientation;//是否水平倒置
	private String noDataMessage;//无数据时的提示信息

	private String hotMap;//是否显示热点
	private String plotBackColor;// 图形区域背景色

	
	
	private String growTime;//柱状图生长动画效果时长,秒
	private String sequencedGrow;//按顺序生长还是同时生长,true为按顺序生长,false为同时生长
	private String growEffect;//可选的动画效果
	private String indicator;//是否显示坐标提供器
	private String indicatorLineColor;//坐标提供器线条颜色
	private String indicatorTextColor;//坐标提供器字体颜色
	private String indicatorTextSize;//坐标提供器字体大小
	
	private String settingsFile;//自定义amChart配置文件

	/**
	 * 设置默认值
	 */
	public BarFlashChartTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.barchart");
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
		return new FlashChart(stack, req, res);
	}
	
	/**
	 * 副标题字体颜色
	 * @param subTitleFontColor the subTitleFontColor to set
	 */
	public void setSubTitleFontColor(String subTitleFontColor) {
		this.subTitleFontColor = subTitleFontColor;
	}

	/**
	 * 将标签属性设置给组件类
	 */
	protected void populateParams() {
		super.populateParams();
		FlashChart chart=(FlashChart)component;
		chart.setChartType("BAR_CHART");
		chart.setPlotType(plotType);
		chart.setValue(value);
		chart.setRowTitle(rowTitle);
		chart.setColNames(colNames);
		chart.setRightValue(rightValue); 
		chart.setRightRowTitle(rowTitle);
		chart.setRightColNames(colNames);
		
		chart.setWidth(width);
		chart.setHeight(height);
		chart.setAlpha(alpha);
		chart.setColor(color);
		chart.setBackground(background);
		chart.setBackColor(backColor);
		chart.setBackAlpha(backAlpha);
		chart.setBorderColor(borderColor);
		chart.setBorderAlpha(borderAlpha);
		chart.setLinkUrl(linkUrl);
		chart.setLinkTarget(linkTarget);
		chart.setPrecious(precious);
		chart.setNoDataMessage(noDataMessage);
		chart.setDepth(depth);
		chart.setAngle(angle);

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
//		chart.setPlotFlag(plotFlag);
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
		
		chart.setShowXLine(showXLine);
		chart.setShowYLine(showYLine);
		chart.setXlineColor(xlineColor);
		chart.setYlineColor(ylineColor);
		
		chart.setBarType(barType);
		chart.setBarWidth(barWidth);
		chart.setBarMargin(barMargin);
		
		chart.setShowXAxes(showXAxes);
		chart.setShowYAxes(showYAxes);

		chart.setXtitle(xtitle);
		chart.setYtitle(ytitle);
		chart.setXtitleFontType(xtitleFontType);
		chart.setXtitleFontSize(xtitleFontSize);
		chart.setXtitleFontColor(xtitleFontColor);
		chart.setXfontSize(xfontSize);
		chart.setXfontType(xfontType);
		chart.setXfontColor(xfontColor);
		chart.setXrotate(xrotate);
		chart.setYfontColor(yfontColor);
		chart.setYfontSize(yfontSize);
		chart.setYfontType(yfontType);
		chart.setYtitleFontColor(ytitleFontColor);
		chart.setYtitleFontSize(ytitleFontSize);
		chart.setYtitleFontType(ytitleFontType);
		chart.setXposition(xposition);
		chart.setYposition(yposition);
		chart.setYrotate(yrotate);
		chart.setShowRangeLine(showRangeLine);
		chart.setRangeLineColor(rangeLineColor);
		chart.setShowDataTitle(showDataTitle);
		chart.setDataTitle(dataTitle);
		chart.setDataTitlePosition(dataTitlePosition);
		chart.setDataTitleFontSize(dataTitleFontSize);
		chart.setDataTitleFontColor(dataTitleFontColor);
		
		chart.setShowBarAverage(showBarAverage);
		chart.setBarAverageFontType(barAverageFontType);
		chart.setBarAverageFontSize(barAverageFontSize);
		chart.setBarAverageFontColor(barAverageFontColor);
		chart.setBarAverageLineColor(barAverageLineColor);
		chart.setOblique(oblique);
		
		
	
		chart.setPlotOrientation(plotOrientation);
//		chart.setPlotFlag(plotFlag);
		chart.setYleftType(yleftType);
		chart.setYrightType(yrightType);
		
		chart.setSettingsFile(settingsFile);

		
		chart.setGrowTime(growTime);
		chart.setSequencedGrow(sequencedGrow);
		chart.setGrowEffect(growEffect);
		
		chart.setIndicator(indicator);
		chart.setIndicatorLineColor(indicatorLineColor);
		chart.setIndicatorTextColor(indicatorTextColor);
		chart.setIndicatorTextSize(indicatorTextSize);
	}

	/**
	 * 设置图形标题
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置水平标题
	 * @param xtitle the xtitle to set
	 */
	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	/**
	 * 设置垂直标题
	 * @param ytitle the ytitle to set
	 */
	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	/**
	 * 设置图形宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置图形高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置是否显示副标题
	 * @param showSubTitle the showSubTitle to set
	 */
	public void setShowSubTitle(String showSubTitle) {
		this.showSubTitle = showSubTitle;
	}

	/**
	 * 设置标题大小
	 * @param titleFontSize the titleFontSize to set
	 */
	public void setTitleFontSize(String titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	/**
	 * 设置标题颜色
	 * @param titleFontColor the titleFontColor to set
	 */
	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	/**
	 * 设置标题字体
	 * @param titleFontType the titleFontType to set
	 */
	public void setTitleFontType(String titleFontType) {
		this.titleFontType = titleFontType;
	}

	/**
	 * 设置标题背景色
	 * @param titleBackColor the titleBackColor to set
	 */
	public void setTitleBackColor(String titleBackColor) {
		this.titleBackColor = titleBackColor;
	}

	/**
	 * 设置副标题大小
	 * @param subTitleFontSize the subTitleFontSize to set
	 */
	public void setSubTitleFontSize(String subTitleFontSize) {
		this.subTitleFontSize = subTitleFontSize;
	}



	/**
	 * 设置副标题背景色
	 * @param subTitleBackColor the subTitleBackColor to set
	 */
	public void setSubTitleBackColor(String subTitleBackColor) {
		this.subTitleBackColor = subTitleBackColor;
	}



	/**
	 * 设置图形透明度
	 * @param alpha the alpha to set
	 */
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	/**
	 * 设置水平是否倒置
	 * @param plotOrientation the plotOrientation to set
	 */
	public void setPlotOrientation(String plotOrientation) {
		this.plotOrientation = plotOrientation;
	}

	/**
	 * 设置无数据时的提示信息
	 * @param noDataMessage the noDataMessage to set
	 */
	public void setNoDataMessage(String noDataMessage) {
		this.noDataMessage = noDataMessage;
	}

	/**
	 * 设置副标题位置
	 * @param subTitlePosition the subTitlePosition to set
	 */
	public void setSubTitlePosition(String subTitlePosition) {
		this.subTitlePosition = subTitlePosition;
	}

	/**
	 * 设置图形类型2D/3D
	 * @param plotType the plotType to set
	 */
	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}

	/**
	 * 设置超链接
	 * @param linkUrl the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/**
	 * 设置图形背景色
	 * @param plotBackColor the plotBackColor to set
	 */
	public void setPlotBackColor(String plotBackColor) {
		this.plotBackColor = plotBackColor;
	}

	/**
	 * 设置水平标题字体
	 * @param xtitleFontType the xtitleFontType to set
	 */
	public void setXtitleFontType(String xtitleFontType) {
		this.xtitleFontType = xtitleFontType;
	}

	/**
	 * 设置水平标题大小
	 * @param xtitleFontSize the xtitleFontSize to set
	 */
	public void setXtitleFontSize(String xtitleFontSize) {
		this.xtitleFontSize = xtitleFontSize;
	}

	/**
	 * 设置水平文字字体
	 * @param xfontType the xfontType to set
	 */
	public void setXfontType(String xfontType) {
		this.xfontType = xfontType;
	}

	/**
	 * 设置水平文字大小
	 * @param xfontSize the xfontSize to set
	 */
	public void setXfontSize(String xfontSize) {
		this.xfontSize = xfontSize;
	}

	/**
	 * 设置水平标题颜色
	 * @param xtitleFontColor the xtitleFontColor to set
	 */
	public void setXtitleFontColor(String xtitleFontColor) {
		this.xtitleFontColor = xtitleFontColor;
	}

	/**
	 * 设置水平文字颜色
	 * @param xfontColor the xfontColor to set
	 */
	public void setXfontColor(String xfontColor) {
		this.xfontColor = xfontColor;
	}

	/**
	 * 设置垂直标题字体
	 * @param ytitleFontType the ytitleFontType to set
	 */
	public void setYtitleFontType(String ytitleFontType) {
		this.ytitleFontType = ytitleFontType;
	}

	/**
	 * 设置垂直标题大小
	 * @param ytitleFontSize the ytitleFontSize to set
	 */
	public void setYtitleFontSize(String ytitleFontSize) {
		this.ytitleFontSize = ytitleFontSize;
	}

	/**
	 * 设置垂直标题颜色
	 * @param ytitleFontColor the ytitleFontColor to set
	 */
	public void setYtitleFontColor(String ytitleFontColor) {
		this.ytitleFontColor = ytitleFontColor;
	}

	/**
	 * 设置垂直文字字体
	 * @param yfontType the yfontType to set
	 */
	public void setYfontType(String yfontType) {
		this.yfontType = yfontType;
	}

	/**
	 * 设置垂直文字大小
	 * @param yfontSize the yfontSize to set
	 */
	public void setYfontSize(String yfontSize) {
		this.yfontSize = yfontSize;
	}

	/**
	 * 设置垂直文字颜色
	 * @param yfontColor the yfontColor to set
	 */
	public void setYfontColor(String yfontColor) {
		this.yfontColor = yfontColor;
	}

	/**
	 * 设置是否显示热点
	 * @param hotMap the hotMap to set
	 */
	public void setHotMap(String hotMap) {
		this.hotMap = hotMap;
	}

	/**
	 * 设置图形背景色
	 * @param backColor the backColor to set
	 */
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	/**
	 * 设置水平文字位置
	 * @param xposition the xposition to set
	 */
	public void setXposition(String xposition) {
		this.xposition = xposition;
	}

	/**
	 * 设置垂直文字位置
	 * @param yposition the yposition to set
	 */
	public void setYposition(String yposition) {
		this.yposition = yposition;
	}

	/**
	 * 设置辅助线颜色
	 * @param rangeLineColor the rangeLineColor to set
	 */
	public void setRangeLineColor(String rangeLineColor) {
		this.rangeLineColor = rangeLineColor;
	}

	/**
	 * 设置是否显示辅助线
	 * @param showRangeLine the showRangeLine to set
	 */
	public void setShowRangeLine(String showRangeLine) {
		this.showRangeLine = showRangeLine;
	}

	/**
	 * 设置是否在柱子上显示数据
	 * @param showDataTitle the showDataTitle to set
	 */
	public void setShowDataTitle(String showDataTitle) {
		this.showDataTitle = showDataTitle;
	}

	/**
	 * 设置柱子数据显示位置
	 * @param dataTilePosition the dataTilePosition to set
	 */
	public void setDataTitlePosition(String dataTitlePosition) {
		this.dataTitlePosition = dataTitlePosition;
	}

	/**
	 * 设置柱子类型
	 * @param barType the barType to set
	 */
	public void setBarType(String barType) {
		this.barType = barType;
	}

	/**
	 * 设置图形数据集
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 设置柱子宽度
	 * @param barWidth the barWidth to set
	 */
	public void setBarWidth(String barWidth) {
		this.barWidth = barWidth;
	}

	/**
	 * 设置柱子颜色
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 设置柱子间距
	 * @param barMargin the barMargin to set
	 */
	public void setBarMargin(String barMargin) {
		this.barMargin = barMargin;
	}

	/**
	 * 设置是否显示柱子平均线
	 * @param showBarAverage the showBarAverage to set
	 */
	public void setShowBarAverage(String showBarAverage) {
		this.showBarAverage = showBarAverage;
	}

	/**
	 * 设置平均线文字字体
	 * @param barAverageFontType the barAverageFontType to set
	 */
	public void setBarAverageFontType(String barAverageFontType) {
		this.barAverageFontType = barAverageFontType;
	}

	/**
	 * 设置平均线文字大小
	 * @param barAverageFontSize the barAverageFontSize to set
	 */
	public void setBarAverageFontSize(String barAverageFontSize) {
		this.barAverageFontSize = barAverageFontSize;
	}

	/**
	 * 设置平均线文字颜色
	 * @param barAverageFontColor the barAverageFontColor to set
	 */
	public void setBarAverageFontColor(String barAverageFontColor) {
		this.barAverageFontColor = barAverageFontColor;
	}

	/**
	 * 设置平均线颜色
	 * @param barAverageLineColor the barAverageLineColor to set
	 */
	public void setBarAverageLineColor(String barAverageLineColor) {
		this.barAverageLineColor = barAverageLineColor;
	}

	/**
	 * 设置行标题
	 * @param rowTitle the rowTitle to set
	 */
	public void setRowTitle(String rowTitle) {
		this.rowTitle = rowTitle;
	}

	/**
	 * 设置倾斜度
	 * @param oblique the oblique to set
	 */
	public void setOblique(String oblique) {
		this.oblique = oblique;
	}

	/**
	 * 设置精确度
	 * @param precious the precious to set
	 */
	public void setPrecious(String precious) {
		this.precious = precious;
	}

	/**
	 * 设置图形区域的信息显示样式
	 * @param plotFlag the plotFlag to set
	 */
	public void setPlotFlag(String plotFlag) {
		this.plotFlag = plotFlag;
	}

	/**
	 * 设置提示信息显示样式
	 * @param toolTipFlag the toolTipFlag to set
	 */
	public void setToolTipFlag(String toolTipFlag) {
		this.toolTipFlag = toolTipFlag;
	}

	/**
	 * 获取图形背景
	 * @return
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置图形背景
	 * @param background
	 */
	public void setBackground(String background) {
		this.background = background;
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
	 * 获取字段名
	 * @returnString 
	 */
	public String getColNames() {
		return colNames;
	}

	/**
	 * 设置字段名
	 * @param colNames
	 */
	public void setColNames(String colNames) {
		this.colNames = colNames;
	}

	/**
	 * 获取连接对象
	 * @return String
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * 设置连接对象
	 * @param linkTarget
	 */
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
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
	 * 获取图形指向的提示信息可自定义显示格式
	 * @returnString
	 */
	public String getPlotTitle() {
		return plotTitle;
	}

	/**
	 * 设置图形指向的提示信息可自定义显示格式
	 * @param plotTitle
	 */
	public void setPlotTitle(String plotTitle) {
		this.plotTitle = plotTitle;
	}

	/**
	 * 获取取得数据的字段名,用逗号分隔
	 * @return String
	 */
	public String getRightColNames() {
		return rightColNames;
	}

	/**
	 * 设置取得数据的字段名,用逗号分隔
	 * @param rightColNames
	 */
	public void setRightColNames(String rightColNames) {
		this.rightColNames = rightColNames;
	}

	/**
	 * 获取多组数据必须指定的字段
	 * @return String
	 */
	public String getRightRowTitle() {
		return rightRowTitle;
	}

	/**
	 * 设置多组数据必须指定的字段
	 * @param rightRowTitle
	 */
	public void setRightRowTitle(String rightRowTitle) {
		this.rightRowTitle = rightRowTitle;
	}

	/**
	 * 获取数据实例对象
	 * @return String
	 */
	public String getRightValue() {
		return rightValue;
	}

	/**
	 * 设置数据实例对象
	 * @param rightValue
	 */
	public void setRightValue(String rightValue) {
		this.rightValue = rightValue;
	}

	/**
	 * 获取是否在图形区域显示信息
	 * @return String
	 */
	public String getShowPlot() {
		return showPlot;
	}

	/**
	 * 设置是否在图形区域显示信息
	 * @param showPlot
	 */
	public void setShowPlot(String showPlot) {
		this.showPlot = showPlot;
	}

	/**
	 * 获取是否显示鼠标移动提示信息
	 * @return String
	 */
	public String getShowToolTip() {
		return showToolTip;
	}

	/**
	 * 设置是否显示鼠标移动提示信息
	 * @param showToolTip
	 */
	public void setShowToolTip(String showToolTip) {
		this.showToolTip = showToolTip;
	}

	/**
	 * 获取副标题
	 * @return String
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * 设置副标题
	 * @param subTitle
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	/**
	 * 获取副标题数据显示格式
	 * @return String
	 */
	public String getSubTitleFlag() {
		return subTitleFlag;
	}

	/**
	 * 设置副标题数据显示格式
	 * @param subTitleFlag
	 */
	public void setSubTitleFlag(String subTitleFlag) {
		this.subTitleFlag = subTitleFlag;
	}

	/**
	 * 获取副标题数据每行最多几列
	 * @return String
	 */
	public String getSubTitleMaxColumns() {
		return subTitleMaxColumns;
	}

	/**
	 * 设置副标题数据每行最多几列
	 * @param subTitleMaxColumns
	 */
	public void setSubTitleMaxColumns(String subTitleMaxColumns) {
		this.subTitleMaxColumns = subTitleMaxColumns;
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
	 * 获取左边坐标显示类型
	 * @return String
	 */
	public String getYleftType() {
		return yleftType;
	}

	/**
	 * 设置左边坐标显示类型
	 * @param yleftType
	 */
	public void setYleftType(String yleftType) {
		this.yleftType = yleftType;
	}

	/**
	 * 获取右边坐标显示类型
	 * @return String
	 */
	public String getYrightType() {
		return yrightType;
	}

	/**
	 * 设置右边坐标显示类型
	 * @param yrightType
	 */
	public void setYrightType(String yrightType) {
		this.yrightType = yrightType;
	}

	/**
	 * 获取透明度 
	 * @return String
	 */
	public String getAlpha() {
		return alpha;
	}

	/**
	 * 获取背景色
	 * @return
	 */
	public String getBackColor() {
		return backColor;
	}

	/**
	 * 获取平均线文字颜色
	 * @return String
	 */
	public String getBarAverageFontColor() {
		return barAverageFontColor;
	}

	/**
	 * 获取平均线文字大小
	 * @return String
	 */
	public String getBarAverageFontSize() {
		return barAverageFontSize;
	}

	/**
	 * 获取平均线文字字体
	 * @return String
	 */
	public String getBarAverageFontType() {
		return barAverageFontType;
	}

	/**
	 * 获取平均线颜色
	 * @return String
	 */
	public String getBarAverageLineColor() {
		return barAverageLineColor;
	}

	/**
	 * 获取柱间距离
	 * @return String
	 */
	public String getBarMargin() {
		return barMargin;
	}

	/**
	 * 获取柱子类型
	 * @return String
	 */
	public String getBarType() {
		return barType;
	}

	/**
	 * 获取柱子宽度
	 * @return String
	 */
	public String getBarWidth() {
		return barWidth;
	}

	/**
	 * 获取柱子颜色
	 * @return String
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 获取数据在柱子的位置
	 * @return String
	 */
	public String getDataTitlePosition() {
		return dataTitlePosition;
	}


	/**
	 * 获取自定义amChart配置文件
	 * @return String
	 */
	public String getSettingsFile() {
		return settingsFile;
	}

	/**
	 * 设置自定义amChart配置文件
	 * @param settingsFile
	 */
	public void setSettingsFile(String settingsFile) {
		this.settingsFile = settingsFile;
	}

	/**
	 * 获取高度
	 * @return String
	 */ 
	public String getHeight() {
		return height;
	}

	/**
	 * 获取是否有热点
	 * @return String
	 */
	public String getHotMap() {
		return hotMap;
	}

	/**
	 * 获取超链接
	 * @return String
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * 获取无数据显示提示信息
	 * @return String
	 */
	public String getNoDataMessage() {
		return noDataMessage;
	}

	/**
	 * 获取倾斜度
	 * @return String
	 */
	public String getOblique() {
		return oblique;
	}

	/**
	 * 获取提醒区域背景色
	 * @return String
	 */
	public String getPlotBackColor() {
		return plotBackColor;
	}

	/**
	 * 获取图形区域数据格式
	 * @return String
	 */
	public String getPlotFlag() {
		return plotFlag;
	}

	/**
	 * 获取图形是否可水平倒置
	 * @return String
	 */
	public String getPlotOrientation() {
		return plotOrientation;
	}

	/**
	 * 获取图形类型 2D/3D
	 * @return String
	 */
	public String getPlotType() {
		return plotType;
	}

	/**
	 * 获取精确度
	 * @return String
	 */
	public String getPrecious() {
		return precious;
	}

	/**
	 * 获取虚线颜色
	 * @return String
	 */
	public String getRangeLineColor() {
		return rangeLineColor;
	}

	/**
	 * 获取行标题
	 * @return String
	 */
	public String getRowTitle() {
		return rowTitle;
	}

	/**
	 * 获取显示是否显示平均线
	 * @return
	 */
	public String getShowBarAverage() {
		return showBarAverage;
	}

	/**
	 * 获取是否在柱子上显示数据
	 * @return
	 */
	public String getShowDataTitle() {
		return showDataTitle;
	}

	/**
	 * 获取是否显示虚线
	 * @return
	 */
	public String getShowRangeLine() {
		return showRangeLine;
	}

	/**
	 * 获取是否显示副标题
	 * @return String
	 */
	public String getShowSubTitle() {
		return showSubTitle;
	}

	/**
	 * 获取副标题背景色
	 * @return String
	 */
	public String getSubTitleBackColor() {
		return subTitleBackColor;
	}

	/**
	 * 获取副标题文字颜色
	 * @return String
	 */
	public String getSubTitleFontColor() {
		return subTitleFontColor;
	}

	/**
	 * 获取副标题大小
	 * @return
	 */
	public String getSubTitleFontSize() {
		return subTitleFontSize;
	}

	/**
	 * 获取放副标题位置
	 * @return String
	 */
	public String getSubTitlePosition() {
		return subTitlePosition;
	}

	/**
	 * 祸福标题
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 获取标题背景色
	 * @return String
	 */
	public String getTitleBackColor() {
		return titleBackColor;
	}

	/**
	 * 获取标题颜色
	 * @return String
	 */
	public String getTitleFontColor() {
		return titleFontColor;
	}

	/**
	 * 获取标题大小
	 * @return String
	 */
	public String getTitleFontSize() {
		return titleFontSize;
	}

	/**
	 * 获取标题字体
	 * @return
	 */
	public String getTitleFontType() {
		return titleFontType;
	}

	/**
	 * 获取提示信息格式
	 * @return String
	 */
	public String getToolTipFlag() {
		return toolTipFlag;
	}
	
	/**
	 * 获取数据集
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取图形宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 获取水平文字颜色
	 * @return String
	 */
	public String getXfontColor() {
		return xfontColor;
	}

	/**
	 * 获取水平文字大小
	 * @return
	 */
	public String getXfontSize() {
		return xfontSize;
	}

	/**
	 * 获取水平文字字体
	 * @return String
	 */
	public String getXfontType() {
		return xfontType;
	}

	/**
	 * 获取水平文字位置
	 * @return String
	 */
	public String getXposition() {
		return xposition;
	}

	/**
	 * 获取水平标题
	 * @return String
	 */
	public String getXtitle() {
		return xtitle;
	}

	/**
	 * 获取水平标题颜色
	 * @return String
	 */
	public String getXtitleFontColor() {
		return xtitleFontColor;
	}

	/**
	 * 获取水平标题大小
	 * @return
	 */
	public String getXtitleFontSize() {
		return xtitleFontSize;
	}

	/**
	 * 获取水平标题字体
	 * @return String
	 */
	public String getXtitleFontType() {
		return xtitleFontType;
	}

	/**
	 * 获取垂直文字颜色
	 * @return String
	 */
	public String getYfontColor() {
		return yfontColor;
	}

	/**
	 * 获取垂直文字大小
	 * @return String
	 */
	public String getYfontSize() {
		return yfontSize;
	}

	/**
	 * 获取垂直字体类型
	 * @return String
	 */
	public String getYfontType() {
		return yfontType;
	}

	/**
	 * 获取垂直文字位置
	 * @return String
	 */
	public String getYposition() {
		return yposition;
	}

	/**
	 * 获取垂直标题
	 * @return String
	 */
	public String getYtitle() {
		return ytitle;
	}

	/**
	 * 获取垂直标题颜色
	 * @return String
	 */
	public String getYtitleFontColor() {
		return ytitleFontColor;
	}

	/**
	 * 获取垂直标题大小
	 * @return String
	 */
	public String getYtitleFontSize() {
		return ytitleFontSize;
	}

	/**
	 * 获取垂直标题字体
	 * @return String
	 */
	public String getYtitleFontType() {
		return ytitleFontType;
	}

	/**
	 * 获取柱子上显示数据格式定义
	 * @return String
	 */
	public String getDataTitle() {
		return dataTitle;
	}

	/**
	 * 设置柱子上显示数据格式定义
	 * @param dataTitle
	 */
	public void setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
	}

	/**
	 * 获取是否显示x轴辅助线
	 * @return String
	 */
	public String getShowXLine() {
		return showXLine;
	}

	/**
	 * 设置是否显示x轴辅助线
	 * @param showXLine
	 */
	public void setShowXLine(String showXLine) {
		this.showXLine = showXLine;
	}

	/**
	 * 获取是否显示y轴辅助线
	 * @return String
	 */
	public String getShowYLine() {
		return showYLine;
	}

	/**
	 * 设置是否显示y轴辅助线
	 * @param showYLine
	 */
	public void setShowYLine(String showYLine) {
		this.showYLine = showYLine;
	}

	/**
	 * 获取x轴辅助线颜色
	 * @return String
	 */
	public String getXlineColor() {
		return xlineColor;
	}

	/**
	 * 设置x轴辅助线颜色
	 * @param xlineColor
	 */
	public void setXlineColor(String xlineColor) {
		this.xlineColor = xlineColor;
	}

	/**
	 * 获取y轴辅助线颜色
	 * @return String
	 */
	public String getYlineColor() {
		return ylineColor;
	}

	/**
	 * 设置y轴辅助线颜色
	 * @param ylineColor
	 */
	public void setYlineColor(String ylineColor) {
		this.ylineColor = ylineColor;
	}

	/**
	 * 获取图形提示信息
	 * @return String
	 */
	public String getToolTipTitle() {
		return toolTipTitle;
	}

	/**
	 * 设置图形提示信息
	 * @param toolTipTitle
	 */
	public void setToolTipTitle(String toolTipTitle) {
		this.toolTipTitle = toolTipTitle;
	}

	/**
	 * 获取可选的动画效果
	 * @return String
	 */
	public String getGrowEffect() {
		return growEffect;
	}

	/**
	 * 设置可选的动画效果
	 * @param growEffect
	 */
	public void setGrowEffect(String growEffect) {
		this.growEffect = growEffect;
	}

	/**
	 * 获取柱状图生长动画效果时长,秒
	 * @return String
	 */
	public String getGrowTime() {
		return growTime;
	}

	/**
	 * 设置柱状图生长动画效果时长,秒
	 * @param growTime
	 */
	public void setGrowTime(String growTime) {
		this.growTime = growTime;
	}

	/**
	 * 获取按顺序生长还是同时生长,true为按顺序生长,false为同时生长
	 * @return String
	 */
	public String getSequencedGrow() {
		return sequencedGrow;
	}

	/**
	 * 设置按顺序生长还是同时生长,true为按顺序生长,false为同时生长
	 * @param sequencedGrow
	 */
	public void setSequencedGrow(String sequencedGrow) {
		this.sequencedGrow = sequencedGrow;
	}

	/**
	 * 获取是否显示坐标提供器
	 * @return String
	 */
	public String getIndicator() {
		return indicator;
	}

	/**
	 * 设置是否显示坐标提供器
	 * @param indicator
	 */
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	/**
	 * 获取坐标提供器线条颜色
	 * @return String
	 */
	public String getIndicatorLineColor() {
		return indicatorLineColor;
	}

	/**
	 * 设置坐标提供器线条颜色
	 * @param indicatorLineColor
	 */
	public void setIndicatorLineColor(String indicatorLineColor) {
		this.indicatorLineColor = indicatorLineColor;
	}

	/**
	 * 获取坐标提供器字体颜色
	 * @return String
	 */
	public String getIndicatorTextColor() {
		return indicatorTextColor;
	}

	/**
	 * 设置坐标提供器字体颜色
	 * @param indicatorTextColor
	 */
	public void setIndicatorTextColor(String indicatorTextColor) {
		this.indicatorTextColor = indicatorTextColor;
	}

	/**
	 * 获取坐标提供器字体大小
	 * @return String
	 */
	public String getIndicatorTextSize() {
		return indicatorTextSize;
	}

	/**
	 * 设置坐标提供器字体大小
	 * @param indicatorTextSize
	 */
	public void setIndicatorTextSize(String indicatorTextSize) {
		this.indicatorTextSize = indicatorTextSize;
	}

	/**
	 * 获取显示横坐标
	 * @return String
	 */
	public String getShowXAxes() {
		return showXAxes;
	}

	/**
	 * 设置显示横坐标
	 * @param showXAxes
	 */
	public void setShowXAxes(String showXAxes) {
		this.showXAxes = showXAxes;
	}

	/**
	 * 获取显示纵坐标
	 * @return String
	 */
	public String getShowYAxes() {
		return showYAxes;
	}

	/**
	 * 设置显示纵坐标
	 * @param showYAxes
	 */
	public void setShowYAxes(String showYAxes) {
		this.showYAxes = showYAxes;
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
	 * 获取水平文字倾斜度(可选范围0~90)
	 * @return String
	 */
	public String getXrotate() {
		return xrotate;
	}

	/**
	 * 设置水平文字倾斜度(可选范围0~90)
	 * @param xrotate
	 */
	public void setXrotate(String xrotate) {
		this.xrotate = xrotate;
	}

	/**
	 * 获取垂直文字倾斜度(可选范围0~90)
	 * @return String
	 */
	public String getYrotate() {
		return yrotate;
	}

	/**
	 * 设置垂直文字倾斜度(可选范围0~90)
	 * @param yrotate
	 */
	public void setYrotate(String yrotate) {
		this.yrotate = yrotate;
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
	 * 获取提示信息边框透明度
	 * @return String
	 */
	public String getToolTipBorderAlpha() {
		return toolTipBorderAlpha;
	}

	/**
	 * 设置提示信息边框透明度
	 * @param toolTipBorderAlpha
	 */
	public void setToolTipBorderAlpha(String toolTipBorderAlpha) {
		this.toolTipBorderAlpha = toolTipBorderAlpha;
	}

	/**
	 * 获取提示信息边框色
	 * @return String
	 */
	public String getToolTipBorderColor() {
		return toolTipBorderColor;
	}

	/**
	 * 设置提示信息边框色
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
	 * 获取提示信息大小
	 * @return String
	 */
	public String getToolTipFontSize() {
		return toolTipFontSize;
	}

	/**
	 * 设置提示信息大小
	 * @param toolTipFontSize
	 */
	public void setToolTipFontSize(String toolTipFontSize) {
		this.toolTipFontSize = toolTipFontSize;
	}

	/**
	 * 获取提示信息圆角率
	 * @return
	 */
	public String getToolTipCornerRadius() {
		return toolTipCornerRadius;
	}

	/**
	 * 设置提示信息圆角率
	 * @param toolTipCornerRadius
	 */
	public void setToolTipCornerRadius(String toolTipCornerRadius) {
		this.toolTipCornerRadius = toolTipCornerRadius;
	}

	/**
	 * 获取柱子上显示数据字体颜色
	 * @return String
	 */
	public String getDataTitleFontColor() {
		return dataTitleFontColor;
	}

	/**
	 * 设置柱子上显示数据字体颜色
	 * @param dataTitleFontColor
	 */
	public void setDataTitleFontColor(String dataTitleFontColor) {
		this.dataTitleFontColor = dataTitleFontColor;
	}

	/**
	 * 获取柱子上显示数据字体大小
	 * @return String
	 */
	public String getDataTitleFontSize() {
		return dataTitleFontSize;
	}

	/**
	 * 设置柱子上显示数据字体大小
	 * @param dataTitleFontSize
	 */
	public void setDataTitleFontSize(String dataTitleFontSize) {
		this.dataTitleFontSize = dataTitleFontSize;
	}

	/**
	 * 获取图的视角,0~90
	 * @return String
	 */
	public String getAngle() {
		return angle;
	}

	/**
	 * 设置图的视角,0~90
	 * @param angle
	 */
	public void setAngle(String angle) {
		this.angle = angle;
	}

	/**
	 * 获取柱体深度
	 * @return String
	 */
	public String getDepth() {
		return depth;
	}

	/**
	 * 设置柱体深度
	 * @param depth
	 */
	public void setDepth(String depth) {
		this.depth = depth;
	}

	/**
	 * 获取背景色透明度
	 * @return String
	 */
	public String getBackAlpha() {
		return backAlpha;
	}

	/**
	 * 设置背景色透明度
	 * @param backAlpha
	 */
	public void setBackAlpha(String backAlpha) {
		this.backAlpha = backAlpha;
	}

}
