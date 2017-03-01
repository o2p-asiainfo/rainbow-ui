package com.linkage.rainbow.ui.views.jsp.ui.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import com.linkage.rainbow.ui.components.report.FlashChart;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * LineFlashChartTag<br>
 * 图形标签类提供对图形标签中属性的操作
 * <p>
 * @version 1.0
 * @author 陈亮 2009-09-15
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:陈亮 修改时间:2009-09-15<br>
 * 修改内容:新建
 * <hr>
 */
public class LineFlashChartTag extends AbstractUITag{
	

	private String chartType;//图形类型
	private String value;//折线数据集
	private String rowTitle;//行标题
	private String colNames;//取得数据的字段名,用逗号分隔.例:colNames="title,value"
	private String rightValue;//数据实例对象
	private String rightRowTitle;//行标题
	private String rightColNames;//取得数据的字段名,用逗号分隔.例:colNames="title,value"
	private String width;//图片宽度
	private String height;//图片高度
	private String alpha;//图片透明度 
	private String color;//图形模块颜色
	private String backColor;//背景颜色
	private String backAlpha;//背景色透明度
	private String background ;//背景图
	private String borderColor;//边框颜色
	private String borderAlpha;//边框透明度
	private String plotType;//图形类型2D还是3D
	private String precious;// 图形区域信息数字精确度
	private String percentPrecious;//百分比精确度
	private String linkUrl;//超链接
	private String linkTarget;//超链接对象
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
	
	private String showPlot;//是否显示图形区域的文字
	private String plotFlag;//图形区域文字格式
	private String plotTitle;//如果不采用预选的格式,图形指向的提示信息可自定义显示格式
//	private String plotBackColor;
	private String plotFontSize;//图形区域文字大小
	private String plotFontColor;//图形区域文字颜色
//	private String plotFontType;

	private String showToolTip;//是否显示提示信息
//	private String toolTipFlag;
//	private String toolTipTitle;
	
	

	private String showXLine;//是否显示x轴辅助线
	private String showYLine;//是否显示y轴辅助线
	private String xlineColor;//x轴辅助线颜色
	private String ylineColor;//y轴辅助线颜色
	private String plotOrientation;//Xy轴切换, 默认为vertical，horizontal可供选择

	private String xtitle;//水平标题
	private String xtitleFontColor;//水平标题颜色
	private String xtitleFontSize;//水平标题大小
	private String xtitleFontType;//水平标题字体
	private String xfontColor;//水平文字颜色
	private String xfontSize;//水平文字大小
	private String xfontType;//水平文字字体
	private String xposition;//水平文字位置
	private String xrotate; //水平文字倾斜度(可选范围0~90)
	private String ytitle;//垂直标题
	private String ytitleFontColor;//垂直标题颜色
	private String ytitleFontSize;//垂直标题大小
	private String ytitleFontType;//垂直标题字体
	private String yfontColor;//垂直文字颜色
	private String yfontSize;//垂直文字大小
	private String yfontType;//垂直文字字体
	private String yposition;//垂直文字位置
	private String yrotate;//垂直文字倾斜度(可选范围0~90)
	private String showLineAverage;//是否显示平均线
	private String lineAverageFontColor;//平均线颜色
	private String lineAverageFontSize; //平均线字体大小
	private String lineAverageFontType;//平均线字体
	private String lineAverageLineColor;//平均线颜色
	
	private String lineVisible;//线条是否可见
	private String lineShapeVisible;//线条节点形状是否可见
	private String lineShapeFilled;//线条节点是否填充
	private String lineColor;//线条颜色
	private String oblique;//倾斜度

	private String showXAxes;//显示横坐标
	private String showYAxes;//显示纵坐标
	private String yleftType;//左边坐标显示类型,分:line, stacked, 100% stacked
	private String yrightType;//右边坐标显示类型,分:line, stacked, 100% stacked
	private String yleftLineAlpha;//左边坐标数据连接线透明度
	private String yrightLineAlpha;//右边坐标数据连接线透明度
	private String yleftLineWidth;//左边坐标数据连接线宽度
	private String yrightLineWidth;//右边坐标数据连接线宽度

	private String indicator;//是否显示坐标提供器,可选(line, column,xy)
	private String indicatorLineColor;//坐标提供器线条颜色
	private String indicatorTextColor;//坐标提供器字体颜色
	private String indicatorTextSize;//坐标提供器字体大小
	private String yleftVerticalLines;//左边坐标数据是否需要垂直连接线
	private String yrightVerticalLines;//右边坐标数据是否需要垂直连接线
	private String verticalLinesWidth;//垂直连接线宽度

	private String startOnAxis;//是否从坐标开始显示数据
	private String yleftShapeType;//左边坐标线条结点类型
	private String yleftShapeSize;//左边坐标线条结点大小,默认为10
	private String yleftFillAlpha;//左边坐标数据线与坐标区域填充的透明度,默认为0,即不填充
	private String yrightShapeType;//右边坐标线条结点类型
	private String yrightShapeSize;//右边坐标线条结点大小,默认为10
	private String yrightFillAlpha;//左边坐标数据线与坐标区域填充的透明度,默认为0,即不填充
	private String xValueStart;//X轴坐标开始值
	private String xValueInterval;//X轴坐标刻度的间隔大小
	private String yleftBalloonText;//左边坐标数据汽泡显示格式,默认为{value}
	private String yrightBalloonText;//右边坐标数据汽泡显示格式,默认为{value}
	
	private String settingsFile;//生成的文件信息
	 
	/**
	 * 设置组件默认值
	 */
	public LineFlashChartTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.lineFlashChart");
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
		chart.setChartType("LINE_CHART");
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
		chart.setBackColor(backColor);
		chart.setBackAlpha(backAlpha);
		chart.setBackground(background);
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
//		chart.setToolTipFlag(toolTipFlag);
//		chart.setToolTipTitle(toolTipTitle);

		
	
		chart.setShowXLine(showXLine);
		chart.setShowYLine(showYLine);
		chart.setXlineColor(xlineColor);
		chart.setYlineColor(ylineColor);
		chart.setPlotOrientation(plotOrientation);
		chart.setShowXAxes(showXAxes);
		chart.setShowYAxes(showYAxes);
		chart.setXtitle(xtitle);
		chart.setXtitleFontColor(xtitleFontColor);
		chart.setXtitleFontSize(xtitleFontSize);
		chart.setXtitleFontType(xtitleFontType);
		chart.setXfontColor(xfontColor);
		chart.setXfontSize(xfontSize);
		chart.setXfontType(xfontType);
		chart.setXposition(xposition);
		chart.setXrotate(xrotate);
		chart.setYfontColor(yfontColor);
		chart.setYfontSize(yfontSize);
		chart.setYfontType(yfontType);
		chart.setYposition(yposition);
		chart.setYtitle(ytitle);
		chart.setYtitleFontColor(ytitleFontColor);
		chart.setYtitleFontSize(ytitleFontSize);
		chart.setYtitleFontType(ytitleFontType);
		chart.setYrotate(yrotate);
		chart.setShowLineAverage(showLineAverage);
		chart.setLineAverageFontColor(lineAverageFontColor);
		chart.setLineAverageFontSize(lineAverageFontSize);
		chart.setLineAverageFontType(lineAverageFontType);
		chart.setLineAverageLineColor(lineAverageLineColor);
		
		chart.setLineVisible(lineVisible);
		chart.setLineShapeVisible(lineShapeVisible);
		chart.setLineShapeFilled(lineShapeFilled);
		chart.setLineColor(lineColor);
		chart.setOblique(oblique);
		
		chart.setYleftType(yleftType);
		chart.setYrightType(yrightType);
		chart.setYleftLineAlpha(yleftLineAlpha);
		chart.setYrightLineAlpha(yrightLineAlpha);
		chart.setYleftLineWidth(yleftLineWidth);
		chart.setYrightLineWidth(yrightLineWidth);

		chart.setIndicator(indicator);
		chart.setIndicatorLineColor(indicatorLineColor);
		chart.setIndicatorTextColor(indicatorTextColor);
		chart.setIndicatorTextSize(indicatorTextSize);
		chart.setYleftVerticalLines(yleftVerticalLines);
		chart.setYrightVerticalLines(yrightVerticalLines);
		chart.setVerticalLinesWidth(verticalLinesWidth);

		chart.setStartOnAxis(startOnAxis);
		chart.setYleftShapeType(yleftShapeType);
		chart.setYleftShapeSize(yleftShapeSize);
		chart.setYleftFillAlpha(yleftFillAlpha);
		chart.setYrightShapeType(yrightShapeType);
		chart.setYrightShapeSize(yrightShapeSize); 
		chart.setYrightFillAlpha(yrightFillAlpha);
		chart.setXValueStart(xValueStart);
		chart.setXValueInterval(xValueInterval);
		chart.setYleftBalloonText(yleftBalloonText);
		chart.setYrightBalloonText(yrightBalloonText);
		chart.setSettingsFile(settingsFile);
	}

	/**
	 * 设置图形类型
	 * @param chartType the chartType to set
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}


	/**
	 * 设置折线数据集
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 设置无数据提示
	 * @param noDataMessage the noDataMessage to set
	 */
	public void setNoDataMessage(String noDataMessage) {
		this.noDataMessage = noDataMessage;
	}

	/**
	 * 设置图形类型2D还是3D
	 * @param plotType the plotType to set
	 */
	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}

	/**
	 * 设置标题
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置标题字体
	 * @param titleFontType the titleFontType to set
	 */
	public void setTitleFontType(String titleFontType) {
		this.titleFontType = titleFontType;
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
	 * 设置标题背景色
	 * @param titleBackColor the titleBackColor to set
	 */
	public void setTitleBackColor(String titleBackColor) {
		this.titleBackColor = titleBackColor;
	}



	/**
	 * 设置是否显示副标题
	 * @param showSubTitle the showSubTitle to set
	 */
	public void setShowSubTitle(String showSubTitle) {
		this.showSubTitle = showSubTitle;
	}



	/**
	 * 设置副标题大小
	 * @param subTitleFontSize the subTitleFontSize to set
	 */
	public void setSubTitleFontSize(String subTitleFontSize) {
		this.subTitleFontSize = subTitleFontSize;
	}

	/**
	 * 设置副标题颜色
	 * @param subTitleFontColor the subTitleFontColor to set
	 */
	public void setSubTitleFontColor(String subTitleFontColor) {
		this.subTitleFontColor = subTitleFontColor;
	}

	/**
	 * 设置副标题背景色
	 * @param subTitleBackColor the subTitleBackColor to set
	 */
	public void setSubTitleBackColor(String subTitleBackColor) {
		this.subTitleBackColor = subTitleBackColor;
	}

	/**
	 * 设置副标题位置	
	 * @param subTitlePosition the subTitlePosition to set
	 */
	public void setSubTitlePosition(String subTitlePosition) {
		this.subTitlePosition = subTitlePosition;
	}

	/**
	 * 设置背景颜色
	 * @param backColor the backColor to set
	 */
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}


	/**
	 * 设置水平标题
	 * @param xtitle the xtitle to set
	 */
	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	/**
	 * 设置水平标题颜色
	 * @param xtitleFontColor the xtitleFontColor to set
	 */
	public void setXtitleFontColor(String xtitleFontColor) {
		this.xtitleFontColor = xtitleFontColor;
	}

	/**
	 * 设置水平标题大小
	 * @param xtitleFontSize the xtitleFontSize to set
	 */
	public void setXtitleFontSize(String xtitleFontSize) {
		this.xtitleFontSize = xtitleFontSize;
	}

	/**
	 * 设置水平标题字体
	 * @param xtitleFontType the xtitleFontType to set
	 */
	public void setXtitleFontType(String xtitleFontType) {
		this.xtitleFontType = xtitleFontType;
	}

	/**
	 * 设置水平文字颜色
	 * @param xfontColor the xfontColor to set
	 */
	public void setXfontColor(String xfontColor) {
		this.xfontColor = xfontColor;
	}

	/**
	 * 设置水平文字大小
	 * @param xfontSize the xfontSize to set
	 */
	public void setXfontSize(String xfontSize) {
		this.xfontSize = xfontSize;
	}

	/**
	 * 设置水平文字字体
	 * @param xfontType the xfontType to set
	 */
	public void setXfontType(String xfontType) {
		this.xfontType = xfontType;
	}

	/**
	 * 设置水平文字位置
	 * @param xposition the xposition to set
	 */
	public void setXposition(String xposition) {
		this.xposition = xposition;
	}

	/**
	 * 设置垂直标题
	 * @param ytitle the ytitle to set
	 */
	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	/**
	 * 设置垂直标题颜色
	 * @param ytitleFontColor the ytitleFontColor to set
	 */
	public void setYtitleFontColor(String ytitleFontColor) {
		this.ytitleFontColor = ytitleFontColor;
	}

	/**
	 * 设置垂直标题大小
	 * @param ytitleFontSize the ytitleFontSize to set
	 */
	public void setYtitleFontSize(String ytitleFontSize) {
		this.ytitleFontSize = ytitleFontSize;
	}

	/**
	 * 设置垂直标题字体
	 * @param ytitleFontType the ytitleFontType to set
	 */
	public void setYtitleFontType(String ytitleFontType) {
		this.ytitleFontType = ytitleFontType;
	}

	/**
	 * 设置垂直文字颜色
	 * @param yfontColor the yfontColor to set
	 */
	public void setYfontColor(String yfontColor) {
		this.yfontColor = yfontColor;
	}

	/**
	 * 设置垂直文字大小
	 * @param yfontSize the yfontSize to set
	 */
	public void setYfontSize(String yfontSize) {
		this.yfontSize = yfontSize;
	}

	/**
	 * 设置垂直文字字体
	 * @param yfontType the yfontType to set
	 */
	public void setYfontType(String yfontType) {
		this.yfontType = yfontType;
	}

	/**
	 * 设置垂直文字位置
	 * @param yposition the yposition to set
	 */
	public void setYposition(String yposition) {
		this.yposition = yposition;
	}

	/**
	 * 设置是否显示x轴辅助线
	 * @param showXLine the showXLine to set
	 */
	public void setShowXLine(String showXLine) {
		this.showXLine = showXLine;
	}

	/**
	 * 设置是否显示y轴辅助线
	 * @param showYLine the showYLine to set
	 */
	public void setShowYLine(String showYLine) {
		this.showYLine = showYLine;
	}

	/**
	 * 设置x轴辅助线颜色
	 * @param xlineColor the xlineColor to set
	 */
	public void setXlineColor(String xlineColor) {
		this.xlineColor = xlineColor;
	}

	/**
	 * 设置y轴辅助线颜色
	 * @param ylineColor the ylineColor to set
	 */
	public void setYlineColor(String ylineColor) {
		this.ylineColor = ylineColor;
	}

	/**
	 * 设置Xy轴切换, 默认为vertical，horizontal可供选择
	 * @param plotOrientation the plotOrientation to set
	 */
	public void setPlotOrientation(String plotOrientation) {
		this.plotOrientation = plotOrientation;
	}

	/**
	 * 设置图片透明度 
	 * @param alpha the alpha to set
	 */
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	/**
	 * 设置是否显示平均线
	 * @param showLineAverage the showLineAverage to set
	 */
	public void setShowLineAverage(String showLineAverage) {
		this.showLineAverage = showLineAverage;
	}

	/**
	 * 设置平均线颜色
	 * @param lineAverageFontColor the lineAverageFontColor to set
	 */
	public void setLineAverageFontColor(String lineAverageFontColor) {
		this.lineAverageFontColor = lineAverageFontColor;
	}

	/**
	 * 设置平均线字体大小
	 * @param lineAverageFontSize the lineAverageFontSize to set
	 */
	public void setLineAverageFontSize(String lineAverageFontSize) {
		this.lineAverageFontSize = lineAverageFontSize;
	}

	/**
	 * 设置平均线字体
	 * @param lineAverageFontType the lineAverageFontType to set
	 */
	public void setLineAverageFontType(String lineAverageFontType) {
		this.lineAverageFontType = lineAverageFontType;
	}

	/**
	 * 设置平均线颜色
	 * @param lineAverageLineColor the lineAverageLineColor to set
	 */
	public void setLineAverageLineColor(String lineAverageLineColor) {
		this.lineAverageLineColor = lineAverageLineColor;
	}

	/**
	 * 设置行标题
	 * @param rowTitle the rowTitle to set
	 */
	public void setRowTitle(String rowTitle) {
		this.rowTitle = rowTitle;
	}

	/**
	 * 设置线条是否可见
	 * @param lineVisible the lineVisible to set
	 */
	public void setLineVisible(String lineVisible) {
		this.lineVisible = lineVisible;
	}

	/**
	 * 设置线条节点形状是否可见
	 * @param lineShapeVisible the lineShapeVisible to set
	 */
	public void setLineShapeVisible(String lineShapeVisible) {
		this.lineShapeVisible = lineShapeVisible;
	}

	/**
	 * 设置线条节点是否填充
	 * @param lineShapeFilled the lineShapeFilled to set
	 */
	public void setLineShapeFilled(String lineShapeFilled) {
		this.lineShapeFilled = lineShapeFilled;
	}

	/**
	 * 设置线条颜色
	 * @param lineColor the lineColor to set
	 */
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * 设置超链接
	 * @param linkUrl the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}


	/**
	 * 设置是否显示提示信息
	 * @param showToolTip the showToolTip to set
	 */
	public void setShowToolTip(String showToolTip) {
		this.showToolTip = showToolTip;
	}

	/**
	 * 设置是否显示图形区域的文字
	 * @param showPlot the showPlot to set
	 */
	public void setShowPlot(String showPlot) {
		this.showPlot = showPlot;
	}

	/**
	 * 设置图形区域文字大小
	 * @param plotFontSize the plotFontSize to set
	 */
	public void setPlotFontSize(String plotFontSize) {
		this.plotFontSize = plotFontSize;
	}

	/**
	 * 设置图形区域文字颜色
	 * @param plotFontColor the plotFontColor to set
	 */
	public void setPlotFontColor(String plotFontColor) {
		this.plotFontColor = plotFontColor;
	}



	/**
	 * 设置图形区域文字格式
	 * @param plotFlag the plotFlag to set
	 */
	public void setPlotFlag(String plotFlag) {
		this.plotFlag = plotFlag;
	}

	/**
	 * 设置精确度
	 * @param precious the precious to set
	 */
	public void setPrecious(String precious) {
		this.precious = precious;
	}



	/**
	 * 设置倾斜度
	 * @param oblique the oblique to set
	 */
	public void setOblique(String oblique) {
		this.oblique = oblique;
	}

	/**
	 * 设置图片宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置图片高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取取得数据的字段名,用逗号分隔.例:colNames="title,value"
	 * @return String
	 */
	public String getColNames() {
		return colNames;
	}

	/**
	 * 设置取得数据的字段名,用逗号分隔.例:colNames="title,value"
	 * @param colNames
	 */
	public void setColNames(String colNames) {
		this.colNames = colNames;
	}
	/**
	 * 获取图形模块颜色
	 * @return String
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * 设置图形模块颜色
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * 获取超链接对象
	 * @return String
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * 设置超链接对象
	 * @param linkTarget
	 */
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
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
	 * 获取图片透明度 
	 * @return String
	 */
	public String getAlpha() {
		return alpha;
	}
	/**
	 * 获取背景颜色
	 * @return String
	 */
	public String getBackColor() {
		return backColor;
	}
	/**
	 * 获取图片类型
	 * @return String
	 */
	public String getChartType() {
		return chartType;
	}

	/**
	 * 获取图片高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * 获取平均线颜色
	 * @return String
	 */
	public String getLineAverageFontColor() {
		return lineAverageFontColor;
	}
	/**
	 * 获取平均线字体大小
	 * @return String
	 */
	public String getLineAverageFontSize() {
		return lineAverageFontSize;
	}
	/**
	 * 获取平均线字体
	 * @return String
	 */
	public String getLineAverageFontType() {
		return lineAverageFontType;
	}
	/**
	 * 获取平均线颜色
	 * @return String
	 */
	public String getLineAverageLineColor() {
		return lineAverageLineColor;
	}
	/**
	 * 获取线条颜色
	 * @return String
	 */
	public String getLineColor() {
		return lineColor;
	}
	/**
	 * 获取线条节点是否填充
	 * @return String
	 */
	public String getLineShapeFilled() {
		return lineShapeFilled;
	}
	/**
	 * 获取线条节点形状是否可见
	 * @return String
	 */
	public String getLineShapeVisible() {
		return lineShapeVisible;
	}
	/**
	 * 获取线条是否可见
	 * @return String
	 */
	public String getLineVisible() {
		return lineVisible;
	}
	/**
	 * 获取超链接
	 * @return String
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
	/**
	 * 获取无数据提示
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
	 * 获取图形区域文字格式
	 * @return String
	 */
	public String getPlotFlag() {
		return plotFlag;
	}
	/**
	 * 获取图形区域文字颜色
	 * @return String
	 */
	public String getPlotFontColor() {
		return plotFontColor;
	}
	/**
	 * 获取图形区域文字大小
	 * @return String
	 */
	public String getPlotFontSize() {
		return plotFontSize;
	}
	/**
	 * 获取Xy轴切换, 默认为vertical，horizontal可供选择
	 * @return String
	 */
	public String getPlotOrientation() {
		return plotOrientation;
	}
	/**
	 * 获取图形类型2D还是3D
	 * @return String
	 */
	public String getPlotType() {
		return plotType;
	}
	/**
	 * 获取图形区域信息数字精确度
	 * @return String
	 */
	public String getPrecious() {
		return precious;
	}
	/**
	 * 获取行标题
	 * @return String
	 */
	public String getRowTitle() {
		return rowTitle;
	}
	/**
	 * 获取是否显示平均线
	 * @return String
	 */
	public String getShowLineAverage() {
		return showLineAverage;
	}
	/**
	 * 获取是否显示图形区域的文字
	 * @return String
	 */
	public String getShowPlot() {
		return showPlot;
	}
	/**
	 * 获取是否显示副标题
	 * @return String
	 */
	public String getShowSubTitle() {
		return showSubTitle;
	}
	/**
	 * 获取是否显示提示信息
	 * @return String
	 */
	public String getShowToolTip() {
		return showToolTip;
	}
	/**
	 * 获取是否显示x轴辅助线
	 * @return String
	 */
	public String getShowXLine() {
		return showXLine;
	}
	/**
	 * 获取是否显示y轴辅助线
	 * @return String
	 */
	public String getShowYLine() {
		return showYLine;
	}
	/**
	 * 获取副标题背景色
	 * @return String
	 */
	public String getSubTitleBackColor() {
		return subTitleBackColor;
	}
	/**
	 * 获取副标题颜色
	 * @return String
	 */
	public String getSubTitleFontColor() {
		return subTitleFontColor;
	}
	/**
	 * 获取副标题大小
	 * @return String
	 */
	public String getSubTitleFontSize() {
		return subTitleFontSize;
	}
	/**
	 * 获取副标题位置	
	 * @return String
	 */
	public String getSubTitlePosition() {
		return subTitlePosition;
	}
	/**
	 * 获取标题
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
	 * @return String
	 */
	public String getTitleFontType() {
		return titleFontType;
	}

	/**
	 * 获取折线数据集
	 * @return String
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 获取图片宽度
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
	 * @return String
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
	 * 获取x轴辅助线颜色
	 * @return String
	 */
	public String getXlineColor() {
		return xlineColor;
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
	 * @return String
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
	 * 获取垂直文字字体
	 * @return String
	 */
	public String getYfontType() {
		return yfontType;
	}
	/**
	 * 获取y轴辅助线颜色
	 * @return String
	 */
	public String getYlineColor() {
		return ylineColor;
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
	 * 获取背景图
	 * @return String
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置背景图
	 * @param background
	 */
	public void setBackground(String background) {
		this.background = background;
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
	 * 获取是否显示坐标提供器,可选(line, column,xy)
	 * @return String
	 */
	public String getIndicator() {
		return indicator;
	}

	/**
	 * 设置是否显示坐标提供器,可选(line, column,xy)
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
	 * 获取左边坐标显示类型,分:line, stacked, 100% stacked
	 * @return String
	 */
	public String getYleftType() {
		return yleftType;
	}

	/**
	 * 设置左边坐标显示类型,分:line, stacked, 100% stacked
	 * @param yleftType
	 */
	public void setYleftType(String yleftType) {
		this.yleftType = yleftType;
	}
	/**
	 * 获取右边坐标显示类型,分:line, stacked, 100% stacked
	 * @return String
	 */
	public String getYrightType() {
		return yrightType;
	}

	/**
	 * 设置右边坐标显示类型,分:line, stacked, 100% stacked
	 * @param yrightType
	 */
	public void setYrightType(String yrightType) {
		this.yrightType = yrightType;
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
	 * 获取是否从坐标开始显示数据
	 * @return String
	 */
	public String getStartOnAxis() {
		return startOnAxis;
	}

	/**
	 * 设置是否从坐标开始显示数据
	 * @param startOnAxis
	 */
	public void setStartOnAxis(String startOnAxis) {
		this.startOnAxis = startOnAxis;
	}
	/**
	 * 获取左边坐标数据线与坐标区域填充的透明度,默认为0,即不填充
	 * @return String
	 */
	public String getYleftFillAlpha() {
		return yleftFillAlpha;
	}

	/**
	 * 设置左边坐标数据线与坐标区域填充的透明度,默认为0,即不填充
	 * @param yleftFillAlpha
	 */
	public void setYleftFillAlpha(String yleftFillAlpha) {
		this.yleftFillAlpha = yleftFillAlpha;
	}
	/**
	 * 获取左边坐标线条结点类型
	 * @return String
	 */
	public String getYleftShapeType() {
		return yleftShapeType;
	}

	/**
	 * 设置左边坐标线条结点类型
	 * @param yleftShapeType
	 */
	public void setYleftShapeType(String yleftShapeType) {
		this.yleftShapeType = yleftShapeType;
	}
	/**
	 * 获取左边坐标数据线与坐标区域填充的透明度,默认为0,即不填充
	 * @return String
	 */
	public String getYrightFillAlpha() {
		return yrightFillAlpha;
	}

	/**
	 * 设置左边坐标数据线与坐标区域填充的透明度,默认为0,即不填充
	 * @param yrightFillAlpha
	 */
	public void setYrightFillAlpha(String yrightFillAlpha) {
		this.yrightFillAlpha = yrightFillAlpha;
	}
	/**
	 * 获取右边坐标线条结点类型
	 * @return String
	 */
	public String getYrightShapeType() {
		return yrightShapeType;
	}

	/**
	 * 设置右边坐标线条结点类型
	 * @param yrightShapeType
	 */
	public void setYrightShapeType(String yrightShapeType) {
		this.yrightShapeType = yrightShapeType;
	}
	/**
	 * 获取取得数据的字段名,用逗号分隔.例:colNames="title,value"
	 * @return String
	 */
	public String getRightColNames() {
		return rightColNames;
	}

	/**
	 * 设置取得数据的字段名,用逗号分隔.例:colNames="title,value"
	 * @param rightColNames
	 */
	public void setRightColNames(String rightColNames) {
		this.rightColNames = rightColNames;
	}
	/**
	 * 获取行标题
	 * @return String
	 */
	public String getRightRowTitle() {
		return rightRowTitle;
	}

	/**
	 * 设置行标题
	 * @param rightRowTitle
	 */
	public void setRightRowTitle(String rightRowTitle) {
		this.rightRowTitle = rightRowTitle;
	}
	/**
	 * 获取垂直连接线宽度
	 * @return String
	 */
	public String getVerticalLinesWidth() {
		return verticalLinesWidth;
	}

	/**
	 * 设置垂直连接线宽度
	 * @param verticalLinesWidth
	 */
	public void setVerticalLinesWidth(String verticalLinesWidth) {
		this.verticalLinesWidth = verticalLinesWidth;
	}
	/**
	 * 获取左边坐标数据是否需要垂直连接线
	 * @return String
	 */
	public String getYleftVerticalLines() {
		return yleftVerticalLines;
	}

	/**
	 * 设置左边坐标数据是否需要垂直连接线
	 * @param yleftVerticalLines
	 */
	public void setYleftVerticalLines(String yleftVerticalLines) {
		this.yleftVerticalLines = yleftVerticalLines;
	}
	/**
	 * 获取右边坐标数据是否需要垂直连接线
	 * @return String
	 */
	public String getYrightVerticalLines() {
		return yrightVerticalLines;
	}

	/**
	 * 设置右边坐标数据是否需要垂直连接线
	 * @param yrightVerticalLines
	 */
	public void setYrightVerticalLines(String yrightVerticalLines) {
		this.yrightVerticalLines = yrightVerticalLines;
	}
	/**
	 * 获取左边坐标数据连接线透明度
	 * @return String
	 */
	public String getYleftLineAlpha() {
		return yleftLineAlpha;
	}

	/**
	 * 设置左边坐标数据连接线透明度
	 * @param yleftLineAlpha
	 */
	public void setYleftLineAlpha(String yleftLineAlpha) {
		this.yleftLineAlpha = yleftLineAlpha;
	}
	/**
	 * 获取左边坐标数据连接线宽度
	 * @return String
	 */
	public String getYleftLineWidth() {
		return yleftLineWidth;
	}

	/**
	 * 设置左边坐标数据连接线宽度
	 * @param yleftLineWidth
	 */
	public void setYleftLineWidth(String yleftLineWidth) {
		this.yleftLineWidth = yleftLineWidth;
	}
	/**
	 * 获取左边坐标数据连接线透明度
	 * @return
	 */
	public String getYrightLineAlpha() {
		return yrightLineAlpha;
	}

	/**
	 * 设置右边坐标数据连接线透明度
	 * @param yrightLineAlpha
	 */
	public void setYrightLineAlpha(String yrightLineAlpha) {
		this.yrightLineAlpha = yrightLineAlpha;
	}
	/**
	 * 获取右边坐标数据连接线宽度
	 * @return
	 */
	public String getYrightLineWidth() {
		return yrightLineWidth;
	}

	/**
	 * 设置右边坐标数据连接线宽度
	 * @param yrightLineWidth
	 */
	public void setYrightLineWidth(String yrightLineWidth) {
		this.yrightLineWidth = yrightLineWidth;
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
	 * 获取生成文件信息
	 * @return String
	 */
	public String getSettingsFile() {
		return settingsFile;
	}

	/**
	 * 设置生成文件信息
	 * @param settingsFile
	 */
	public void setSettingsFile(String settingsFile) {
		this.settingsFile = settingsFile;
	}
	/**
	 * 获取X轴坐标刻度的间隔大小
	 * @return String
	 */
	public String getXValueInterval() {
		return xValueInterval;
	}

	/**
	 * 设置X轴坐标刻度的间隔大小
	 * @param valueInterval
	 */
	public void setXValueInterval(String valueInterval) {
		xValueInterval = valueInterval;
	}
	/**
	 * 获取X轴坐标开始值
	 * @return String
	 */
	public String getXValueStart() {
		return xValueStart;
	}

	/**
	 * 设置X轴坐标开始值
	 * @param valueStart
	 */
	public void setXValueStart(String valueStart) {
		xValueStart = valueStart;
	}
	/**
	 * 获取左边坐标线条结点大小,默认为10
	 * @return String
	 */
	public String getYleftShapeSize() {
		return yleftShapeSize;
	}

	/**
	 * 设置左边坐标线条结点大小,默认为10
	 * @param yleftShapeSize
	 */
	public void setYleftShapeSize(String yleftShapeSize) {
		this.yleftShapeSize = yleftShapeSize;
	}
	/**
	 * 获取右边坐标线条结点大小,默认为10
	 * @return String
	 */
	public String getYrightShapeSize() {
		return yrightShapeSize;
	}

	/**
	 * 设置右边坐标线条结点大小,默认为10
	 * @param yrightShapeSize
	 */
	public void setYrightShapeSize(String yrightShapeSize) {
		this.yrightShapeSize = yrightShapeSize;
	}
	/**
	 * 获取左边坐标数据汽泡显示格式,默认为{value}
	 * @return String
	 */
	public String getYleftBalloonText() {
		return yleftBalloonText;
	}

	/**
	 * 设置左边坐标数据汽泡显示格式,默认为{value}
	 * @param yleftBalloonText
	 */
	public void setYleftBalloonText(String yleftBalloonText) {
		this.yleftBalloonText = yleftBalloonText;
	}
	/**
	 * 获取右边坐标数据汽泡显示格式,默认为{value}
	 * @return String
	 */
	public String getYrightBalloonText() {
		return yrightBalloonText;
	}

	/**
	 * 设置右边坐标数据汽泡显示格式,默认为{value}
	 * @param yrightBalloonText
	 */
	public void setYrightBalloonText(String yrightBalloonText) {
		this.yrightBalloonText = yrightBalloonText;
	}
	/**
	 * 获取背景透明度
	 * @return String
	 */
	public String getBackAlpha() {
		return backAlpha;
	}

	/**
	 * 设置背景透明度
	 * @param backAlpha
	 */
	public void setBackAlpha(String backAlpha) {
		this.backAlpha = backAlpha;
	}


	

}
