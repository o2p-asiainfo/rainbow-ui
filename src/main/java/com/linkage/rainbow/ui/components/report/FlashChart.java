/*
 * @(#)FlashChart.java        2009-9-9
 *
 *
 */

package com.linkage.rainbow.ui.components.report;

import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.ClosingUIBean;

import com.linkage.rainbow.ui.report.util.FlashChartUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 类名称<br>
 * 这里是类的描述区域，概括出该的主要功能或者类的使用范围、注意事项等
 * <p>
 * @version 1.0
 * @author cl 2009-9-9
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class FlashChart extends ClosingUIBean {
	/**
	 * 定义图形标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "report/flashChart";//图形标签模版
	/**
	 * 定义图形标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "report/flashChart-close";//图形标签模版
	
	private static Log log = LogFactory.getLog(FlashChart.class);
	private String chartType;//图形类型
	private String value;
	private String rowTitle;
	private String colNames;
	private String rightValue;
	private String rightRowTitle;
	private String rightColNames;
	private String width;
	private String height;
	private String alpha;
	private String color;
	private String backColor;
	private String backAlpha;
	private String background;
	private String borderColor;
	private String borderAlpha;
	private String plotType;
	private String linkUrl;
	private String linkTarget;
	private String precious;
	private String percentPrecious;
	private String noDataMessage;
	
	private String title;
//	private String titlePosition;
	private String titleFontSize;
	private String titleFontType;
	private String titleFontColor;
	private String titleBackColor;
	
	
	private String showSubTitle;
	private String subTitlePosition;
	private String subTitleFlag;
	private String subTitle;
	private String subTitleFontSize;
//	private String subTitleFontType;
	private String subTitleFontColor;
	private String subTitleBackColor;
	private String subTitleWidth;
	private String subTitleMaxColumns;
	
	private String showPlot;
	private String plotFlag;
	private String plotTitle;
//	private String plotBackColor;
	private String plotFontSize;
	private String plotFontColor;
//	private String plotFontType;
	
	private String showToolTip;
	private String toolTipFlag;
	private String toolTipTitle;
	private String toolTipAlpha;
	private String toolTipFontColor;
	private String toolTipFontSize;
	private String toolTipBorderWidth;
	private String toolTipBorderAlpha;
	private String toolTipBorderColor;
	private String toolTipCornerRadius;
	
	//饼图附加属性
	private String pieRadius;
	private String pieInnerRadius;
	private String pieHeight;
	private String pieAngle;
	private String animationStartTime;
	private String animationStartEffect;
	private String animationStartRadius;
	private String animationStartAlpha;
	
	//柱状图附加属性
	private String showXAxes;
	private String showYAxes;
	private String xtitle;
	private String ytitle;
	private String plotOrientation;
	private String xtitleFontType;
	private String xtitleFontSize;
	private String xtitleFontColor;
	private String xfontType;
	private String xfontSize;
	private String xfontColor;
	private String xposition;
	private String xrotate;
	private String ytitleFontType;
	private String ytitleFontSize;
	private String ytitleFontColor;
	private String yfontType;
	private String yfontSize;
	private String yfontColor;
	private String yposition;
	private String yrotate;
	private String showRangeLine;
	private String rangeLineColor;
	private String showDataTitle;
	private String dataTitle;
	private String dataTitlePosition;
	private String dataTitleFontSize;
	private String dataTitleFontColor;
	private String barType;
	private String barWidth;
	private String barMargin;
	private String showBarAverage;
	private String barAverageFontType;
	private String barAverageFontSize;
	private String barAverageFontColor;
	private String barAverageLineColor;
	private String oblique;
	private String depth;
	private String angle;


	//折线图附加属性
	private String showXLine;
	private String showYLine;
	private String xlineColor;
	private String ylineColor;
	private String showLineAverage;
	private String lineAverageFontColor;
	private String lineAverageFontSize;
	private String lineAverageFontType;
	private String lineAverageLineColor;
	private String lineVisible;
	private String lineShapeVisible;
	private String lineShapeFilled;
	private String lineColor;
	
	private String yleftType;
	private String yrightType;
	private String yleftLineAlpha;
	private String yrightLineAlpha;
	private String yleftLineWidth;
	private String yrightLineWidth;
	
	private String indicator;
	private String indicatorLineColor;
	private String indicatorTextColor;
	private String indicatorTextSize;
	private String yleftVerticalLines;
	private String yrightVerticalLines;
	private String verticalLinesWidth;
	
	private String startOnAxis;
	private String yleftShapeType;
	private String yleftShapeSize;
	private String yleftFillAlpha;
	private String yrightShapeType;
	private String yrightShapeSize;
	private String yrightFillAlpha;
	private String xValueStart;
	private String xValueInterval;
	private String yleftBalloonText;
	private String yrightBalloonText;
	
	
	//复合图
	private String barValue;
	private String lineValue;
	private String barPlotType;
	private String linePlotType;
	private String barRowTitle;
	private String lineRowTitle;
	private String y1title;
	private String y1titleFontType;
	private String y1titleFontSize;
	private String y1titleFontColor;
	private String y1fontColor;
	private String y1fontSize;
	private String y1fontType;
	
	//曲线图的附加属性
	private String dateFormat;
	private String tickType;
	private String tickUnit;
	
	private String growTime;
	private String sequencedGrow;
	private String growEffect;
	
	private String settingsFile;
	
	
	public FlashChart(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(id!=null)
			addParameter("id",findString(id));
		if(chartType!=null)
			addParameter("chartType",findString(chartType));
		if(value!=null)
			addParameter("value",findValue(value,List.class));
		if(rightValue!=null)
			addParameter("rightValue",findValue(rightValue,List.class));
		
		if(rightRowTitle!=null)
			addParameter("rightRowTitle",rightRowTitle);
		if(rightColNames!=null)
			addParameter("rightColNames",rightColNames);
		if(rowTitle!=null)
			addParameter("rowTitle",rowTitle);
		if(colNames!=null)
			addParameter("colNames",colNames);
		if(width!=null)
			addParameter("width",findString(width));
		if(height!=null)
			addParameter("height",findString(height));
		if(alpha!=null)
			addParameter("alpha",findString(alpha));
		if(color!=null)
			addParameter("color",findString(color));
		if(backColor!=null)
			addParameter("backColor",findString(backColor));
		if(backAlpha!=null)
			addParameter("backAlpha",findString(backAlpha));
		
		if(background!=null)
			addParameter("background",findString(background));
		if(borderColor!=null)
			addParameter("borderColor",findString(borderColor));
		if(borderAlpha!=null)
			addParameter("borderAlpha",findString(borderAlpha));
		if(plotType!=null)
			addParameter("plotType",findString(plotType));
		if(precious!=null)
			addParameter("precious",findString(precious));
		if(percentPrecious!=null)
			addParameter("percentPrecious",findString(percentPrecious));
		if(linkUrl!=null)
			addParameter("linkUrl",findString(linkUrl));
		if(linkTarget!=null)
			addParameter("linkTarget",findString(linkTarget));
		if(noDataMessage!=null)
			addParameter("noDataMessage",findString(noDataMessage));
		
		
		if(title!=null)
			addParameter("title",findString(title));
//		if(titlePosition!=null)
//			addParameter("titlePosition",findString(titlePosition));
		if(titleFontSize!=null)
			addParameter("titleFontSize",findString(titleFontSize));
		if(titleFontType!=null)
			addParameter("titleFontType",findString(titleFontType));
		if(titleFontColor!=null)
			addParameter("titleFontColor",findString(titleFontColor));
		if(titleBackColor!=null)
			addParameter("titleBackColor",findString(titleBackColor));
		
		
		if(showSubTitle!=null)
			addParameter("showSubTitle",findString(showSubTitle));
		if(subTitleFlag!=null)
			addParameter("subTitleFlag",findString(subTitleFlag));
		if(subTitle!=null)
			addParameter("subTitle",findString(subTitle));
		if(subTitlePosition!=null)
			addParameter("subTitlePosition",findString(subTitlePosition));
		if(subTitleFontSize!=null)
			addParameter("subTitleFontSize",findString(subTitleFontSize));
//		if(subTitleFontType!=null)
//			addParameter("subTitleFontType",findString(subTitleFontType));
		if(subTitleFontColor!=null)
			addParameter("subTitleFontColor",findString(subTitleFontColor));
		if(subTitleBackColor!=null)
			addParameter("subTitleBackColor",findString(subTitleBackColor));
		if(subTitleWidth!=null)
			addParameter("subTitleWidth",findString(subTitleWidth));
		if(subTitleMaxColumns!=null)
			addParameter("subTitleMaxColumns",findString(subTitleMaxColumns));
		
		
		
		
		if(showPlot!=null)
			addParameter("showPlot",findString(showPlot));
		if(plotFlag!=null)
			addParameter("plotFlag",findString(plotFlag));
		if(plotTitle!=null)
			addParameter("plotTitle",findString(plotTitle));
//		if(plotBackColor!=null)
//			addParameter("plotBackColor",findString(plotBackColor));
		if(plotFontSize!=null)
			addParameter("plotFontSize",findString(plotFontSize));
		if(plotFontColor!=null)
			addParameter("plotFontColor",findString(plotFontColor));
//		if(plotFontType!=null)
//			addParameter("plotFontType",findString(plotFontType));
		
	
		if(showToolTip!=null)
			addParameter("showToolTip",findString(showToolTip));
		if(toolTipFlag!=null)
			addParameter("toolTipFlag",findString(toolTipFlag));
		if(toolTipTitle!=null)
			addParameter("toolTipTitle",findString(toolTipTitle));
		if(toolTipAlpha!=null)
			addParameter("toolTipAlpha",findString(toolTipAlpha));
		if(toolTipFontColor!=null)
			addParameter("toolTipFontColor",findString(toolTipFontColor));
		if(toolTipFontSize!=null)
			addParameter("toolTipFontSize",findString(toolTipFontSize));
		if(toolTipBorderWidth!=null)
			addParameter("toolTipBorderWidth",findString(toolTipBorderWidth));
		if(toolTipBorderAlpha!=null)
			addParameter("toolTipBorderAlpha",findString(toolTipBorderAlpha));
		if(toolTipBorderColor!=null)
			addParameter("toolTipBorderColor",findString(toolTipBorderColor));
		if(toolTipCornerRadius!=null)
			addParameter("toolTipCornerRadius",findString(toolTipCornerRadius));
		
		//饼图附加属性
		if(pieRadius!=null)
			addParameter("pieRadius",findString(pieRadius));
		if(pieInnerRadius!=null)
			addParameter("pieInnerRadius",findString(pieInnerRadius));
		if(pieHeight!=null)
			addParameter("pieHeight",findString(pieHeight));
		if(pieAngle!=null)
			addParameter("pieAngle",findString(pieAngle));
		if(animationStartTime!=null)
			addParameter("animationStartTime",findString(animationStartTime));
		if(animationStartEffect!=null)
			addParameter("animationStartEffect",findString(animationStartEffect));
		if(animationStartRadius!=null)
			addParameter("animationStartRadius",findString(animationStartRadius));
		if(animationStartAlpha!=null)
			addParameter("animationStartAlpha",findString(animationStartAlpha));
	
		
		//柱状图附加属性设置
		if(showXAxes!=null)
			addParameter("showXAxes",findString(showXAxes));
		if(showYAxes!=null)
			addParameter("showYAxes",findString(showYAxes));
		if(xtitle!=null)
			addParameter("xtitle",findString(xtitle));
		if(ytitle!=null)
			addParameter("ytitle",findString(ytitle));
		if(plotOrientation!=null)
			addParameter("plotOrientation",findString(plotOrientation));
		if(xtitleFontType!=null)
			addParameter("xtitleFontType",findString(xtitleFontType));
		if(xtitleFontSize!=null)
			addParameter("xtitleFontSize",findString(xtitleFontSize));
		if(xtitleFontColor!=null)
			addParameter("xtitleFontColor",findString(xtitleFontColor));
		if(xfontType!=null)
			addParameter("xfontType",findString(xfontType));
		if(xfontSize!=null)
			addParameter("xfontSize",findString(xfontSize));
		if(xfontColor!=null)
			addParameter("xfontColor",findString(xfontColor));
		if(xposition!=null)
			addParameter("xposition",findString(xposition));
		if(xrotate !=null)
			addParameter("xrotate",findString(xrotate));
		if(ytitleFontType!=null)
			addParameter("ytitleFontType",findString(ytitleFontType));
		if(ytitleFontSize!=null)
			addParameter("ytitleFontSize",findString(ytitleFontSize));
		if(ytitleFontColor!=null)
			addParameter("ytitleFontColor",findString(ytitleFontColor));
		if(yfontType!=null)
			addParameter("yfontType",findString(yfontType));
		if(yfontSize!=null)
			addParameter("yfontSize",findString(yfontSize));
		if(yfontColor!=null)
			addParameter("yfontColor",findString(yfontColor));
		if(yposition!=null)
			addParameter("yposition",findString(yposition));
		if(yrotate !=null)
			addParameter("yrotate",findString(yrotate));
		if(showRangeLine!=null)
			addParameter("showRangeLine",findString(showRangeLine));
		if(rangeLineColor!=null)
			addParameter("rangeLineColor",findString(rangeLineColor));
		if(showDataTitle!=null)
			addParameter("showDataTitle",findString(showDataTitle));
		if(dataTitle!=null)
			addParameter("dataTitle",findString(dataTitle));
		
		if(dataTitlePosition!=null)
			addParameter("dataTitlePosition",findString(dataTitlePosition));
		if(dataTitleFontSize!=null)
			addParameter("dataTitleFontSize",findString(dataTitleFontSize));
		if(dataTitleFontColor!=null)
			addParameter("dataTitleFontColor",findString(dataTitleFontColor));
		if(barType!=null)
			addParameter("barType",findString(barType));
		if(barWidth!=null)
			addParameter("barWidth",findString(barWidth));
		if(barMargin!=null)
			addParameter("barMargin",findString(barMargin));
		if(showBarAverage!=null)
			addParameter("showBarAverage",findString(showBarAverage));
		if(barAverageFontType!=null)
			addParameter("barAverageFontType",findString(barAverageFontType));
		if(barAverageFontSize!=null)
			addParameter("barAverageFontSize",findString(barAverageFontSize));
		if(barAverageFontColor!=null)
			addParameter("barAverageFontColor",findString(barAverageFontColor));
		if(barAverageLineColor!=null)
			addParameter("barAverageLineColor",findString(barAverageLineColor));
		if(oblique!=null)
			addParameter("oblique",findString(oblique));
		if(growTime!=null)
			addParameter("growTime",findString(growTime));
		if(sequencedGrow!=null)
			addParameter("sequencedGrow",findString(sequencedGrow));
		if(growEffect!=null)
			addParameter("growEffect",findString(growEffect));
		if(depth!=null)
			addParameter("depth",findString(depth));
		if(angle!=null)
			addParameter("angle",findString(angle));

		//折线图附加属性
		if(showXLine!=null)
			addParameter("showXLine",findString(showXLine));
		if(showYLine!=null)
			addParameter("showYLine",findString(showYLine));
		if(xlineColor!=null)
			addParameter("xlineColor",findString(xlineColor));
		if(ylineColor!=null)
			addParameter("ylineColor",findString(ylineColor));
		if(showLineAverage!=null)
			addParameter("showLineAverage",findString(showLineAverage));
		if(lineAverageFontColor!=null)
			addParameter("lineAverageFontColor",findString(lineAverageFontColor));
		if(lineAverageFontSize!=null)
			addParameter("lineAverageFontSize",findString(lineAverageFontSize));
		if(lineAverageFontType!=null)
			addParameter("lineAverageFontType",findString(lineAverageFontType));
		if(lineAverageLineColor!=null)
			addParameter("lineAverageLineColor",findString(lineAverageLineColor));
		if(lineVisible!=null)
			addParameter("lineVisible",findString(lineVisible));
		if(lineShapeVisible!=null)
			addParameter("lineShapeVisible",findString(lineShapeVisible));
		if(lineShapeFilled!=null)
			addParameter("lineShapeFilled",findString(lineShapeFilled));
		if(lineColor!=null)
			addParameter("lineColor",findString(lineColor));
		
		if(yleftType!=null)
			addParameter("yleftType",findString(yleftType));
		if(yrightType!=null)
			addParameter("yrightType",findString(yrightType));
		if(yleftLineAlpha!=null)
			addParameter("yleftLineAlpha",findString(yleftLineAlpha));
		if(yrightLineAlpha!=null)
			addParameter("yrightLineAlpha",findString(yrightLineAlpha));
		if(yleftLineWidth!=null)
			addParameter("yleftLineWidth",findString(yleftLineWidth));
		if(yrightLineWidth!=null)
			addParameter("yrightLineWidth",findString(yrightLineWidth));
		if(indicator!=null)
			addParameter("indicator",findString(indicator));
		if(indicatorLineColor!=null)
			addParameter("indicatorLineColor",findString(indicatorLineColor));
		if(indicatorTextColor!=null)
			addParameter("indicatorTextColor",findString(indicatorTextColor));
		if(indicatorTextSize!=null)
			addParameter("indicatorTextSize",findString(indicatorTextSize));
		if(yleftVerticalLines!=null)
			addParameter("yleftVerticalLines",findString(yleftVerticalLines));
		if(yrightVerticalLines!=null)
			addParameter("yrightVerticalLines",findString(yrightVerticalLines));
		if(verticalLinesWidth!=null)
			addParameter("verticalLinesWidth",findString(verticalLinesWidth));

		if(startOnAxis!=null)
			addParameter("startOnAxis",findString(startOnAxis));
		if(yleftShapeType!=null)
			addParameter("yleftShapeType",findString(yleftShapeType));
		if(yleftShapeSize!=null)
			addParameter("yleftShapeSize",findString(yleftShapeSize));
		if(yleftFillAlpha!=null)
			addParameter("yleftFillAlpha",findString(yleftFillAlpha));
		if(yrightShapeType!=null)
			addParameter("yrightShapeType",findString(yrightShapeType));
		if(yrightShapeSize!=null)
			addParameter("yrightShapeSize",findString(yrightShapeSize));
		if(yrightFillAlpha!=null)
			addParameter("yrightFillAlpha",findString(yrightFillAlpha));
		
		if(xValueStart!=null)
			addParameter("xValueStart",findString(xValueStart));
		if(xValueInterval!=null)
			addParameter("xValueInterval",findString(xValueInterval));
		
		if(yleftBalloonText!=null)
			addParameter("yleftBalloonText",findString(yleftBalloonText));
		if(yrightBalloonText!=null)
			addParameter("yrightBalloonText",findString(yrightBalloonText));
		//复合图
		if(barValue!=null)
			addParameter("barValue",findString(barValue));
		if(lineValue!=null)
			addParameter("lineValue",findString(lineValue));
		if(barRowTitle!=null)
			addParameter("barRowTitle",findString(barRowTitle));
		if(lineRowTitle!=null)
			addParameter("lineRowTitle",findString(lineRowTitle));
		if(barPlotType!=null)
			addParameter("barPlotType",findString(barPlotType));
		if(linePlotType!=null)
			addParameter("linePlotType",findString(linePlotType));
		
		
		
		if(y1title!=null)
			addParameter("y1title",findString(y1title));
		if(y1titleFontType!=null)
			addParameter("y1titleFontType",findString(y1titleFontType));
		if(y1titleFontSize!=null)
			addParameter("y1titleFontSize",findString(y1titleFontSize));
		if(y1titleFontColor!=null)
			addParameter("y1titleFontColor",findString(y1titleFontColor));
		if(y1fontColor!=null)
			addParameter("y1fontColor",findString(y1fontColor));
		if(y1fontSize!=null)
			addParameter("y1fontSize",findString(y1fontSize));
		if(y1fontType!=null)
			addParameter("y1fontType",findString(y1fontType));
		
		//曲线图的附加属性
		if(dateFormat!=null)
			addParameter("dateFormat",findString(dateFormat));
		if(tickType!=null)
			addParameter("tickType",findString(tickType));
		if(tickUnit!=null)
			addParameter("tickUnit",findString(tickUnit));
		
		if(settingsFile!=null)
			addParameter("settingsFile",findString(settingsFile));
		
	}

	
	
	
	
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}
	
	@Override
	protected String getDefaultTemplate() {
		// TODO Auto-generated method stub
		return CLOSE_TEMPLATE;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {		
	    if(template != null && template.trim().length()>0) {
	    	this.template = "report/"+template+"/flashChart-close";
		    this.setOpenTemplate("report/"+template+"/flashChart");
	    } else {
	    	template = null;
	    }
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public boolean start(Writer writer) {
    	//evaluateParams();
		evaluateExtraParams();
    	return true;
    }
    
	
	/**
	 * 形成报表请求类,调用报表引擎,生成报表输出
	 */
	@Override
    public boolean end(Writer writer, String body) {
		String html="";
		try {
			Map params = getParameters();
			params.put("request",request);
			FlashChartUtil chart=new FlashChartUtil();
			if(chartType.equals("PIE_CHART")){
				
				html=chart.pieChart(params);                  //饼图
			}else if(chartType.equals("BAR_CHART")){
				html=chart.barChart(params);
			}else if(chartType.equals("LINE_CHART")){//折线图
				html=chart.lineChart(params);
			}
			writer.write(html);
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
        return false;
    }
	


	/**
	 * @param chartType the chartType to set
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @param plotType the plotType to set
	 */
	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @param showSubTitle the showSubTitle to set
	 */
	public void setShowSubTitle(String showSubTitle) {
		this.showSubTitle = showSubTitle;
	}

	/**
	 * @param subTitlePosition the subTitlePosition to set
	 */
	public void setSubTitlePosition(String subTitlePosition) {
		this.subTitlePosition = subTitlePosition;
	}

	/**
	 * @param linkUrl the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}



	/**
	 * @param backColor the backColor to set
	 */
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	/**
	 * @param titleFontSize the titleFontSize to set
	 */
	public void setTitleFontSize(String titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	/**
	 * @param titleFontType the titleFontType to set
	 */
	public void setTitleFontType(String titleFontType) {
		this.titleFontType = titleFontType;
	}

	/**
	 * @param titleFontColor the titleFontColor to set
	 */
	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	/**
	 * @param subTitleFontSize the subTitleFontSize to set
	 */
	public void setSubTitleFontSize(String subTitleFontSize) {
		this.subTitleFontSize = subTitleFontSize;
	}


	/**
	 * @param subTitleFontColor the subTitleFontColor to set
	 */
	public void setSubTitleFontColor(String subTitleFontColor) {
		this.subTitleFontColor = subTitleFontColor;
	}

	/**
	 * @param subTitleBackColor the subTitleBackColor to set
	 */
	public void setSubTitleBackColor(String subTitleBackColor) {
		this.subTitleBackColor = subTitleBackColor;
	}

	
	/**
	 * @param noDataMessage the noDataMessage to set
	 */
	public void setNoDataMessage(String noDataMessage) {
		this.noDataMessage = noDataMessage;
	}

	/**
	 * @param plotFontColor the plotFontColor to set
	 */
	public void setPlotFontColor(String plotFontColor) {
		this.plotFontColor = plotFontColor;
	}

	/**
	 * @param plotFontSize the plotFontSize to set
	 */
	public void setPlotFontSize(String plotFontSize) {
		this.plotFontSize = plotFontSize;
	}


	/**
	 * @param subTitleFlag the subTitleFlag to set
	 */
	public void setSubTitleFlag(String subTitleFlag) {
		this.subTitleFlag = subTitleFlag;
	}

	/**
	 * @param precious the precious to set
	 */
	public void setPrecious(String precious) {
		this.precious = precious;
	}

	/**
	 * @param plotFlag the plotFlag to set
	 */
	public void setPlotFlag(String plotFlag) {
		this.plotFlag = plotFlag;
	}

	/**
	 * @param toolTipFlag the toolTipFlag to set
	 */
	public void setToolTipFlag(String toolTipFlag) {
		this.toolTipFlag = toolTipFlag;
	}

	/**
	 * @param showPlot the showPlot to set
	 */
	public void setShowPlot(String showPlot) {
		this.showPlot = showPlot;
	}

	/**
	 * @param showToolTip the showToolTip to set
	 */
	public void setShowToolTip(String showToolTip) {
		this.showToolTip = showToolTip;
	}

	/**
	 * @param xtitle the xtitle to set
	 */
	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	/**
	 * @param ytitle the ytitle to set
	 */
	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	/**
	 * @param titleBackColor the titleBackColor to set
	 */
	public void setTitleBackColor(String titleBackColor) {
		this.titleBackColor = titleBackColor;
	}

	/**
	 * @param plotOrientation the plotOrientation to set
	 */
	public void setPlotOrientation(String plotOrientation) {
		this.plotOrientation = plotOrientation;
	}

	/**
	 * @param xtitleFontType the xtitleFontType to set
	 */
	public void setXtitleFontType(String xtitleFontType) {
		this.xtitleFontType = xtitleFontType;
	}

	/**
	 * @param xtitleFontSize the xtitleFontSize to set
	 */
	public void setXtitleFontSize(String xtitleFontSize) {
		this.xtitleFontSize = xtitleFontSize;
	}

	/**
	 * @param xfontType the xfontType to set
	 */
	public void setXfontType(String xfontType) {
		this.xfontType = xfontType;
	}

	/**
	 * @param xfontSize the xfontSize to set
	 */
	public void setXfontSize(String xfontSize) {
		this.xfontSize = xfontSize;
	}

	/**
	 * @param xtitleFontColor the xtitleFontColor to set
	 */
	public void setXtitleFontColor(String xtitleFontColor) {
		this.xtitleFontColor = xtitleFontColor;
	}

	/**
	 * @param xfontColor the xfontColor to set
	 */
	public void setXfontColor(String xfontColor) {
		this.xfontColor = xfontColor;
	}

	/**
	 * @param ytitleFontType the ytitleFontType to set
	 */
	public void setYtitleFontType(String ytitleFontType) {
		this.ytitleFontType = ytitleFontType;
	}

	/**
	 * @param ytitleFontSize the ytitleFontSize to set
	 */
	public void setYtitleFontSize(String ytitleFontSize) {
		this.ytitleFontSize = ytitleFontSize;
	}

	/**
	 * @param ytitleFontColor the ytitleFontColor to set
	 */
	public void setYtitleFontColor(String ytitleFontColor) {
		this.ytitleFontColor = ytitleFontColor;
	}

	/**
	 * @param yfontType the yfontType to set
	 */
	public void setYfontType(String yfontType) {
		this.yfontType = yfontType;
	}

	/**
	 * @param yfontSize the yfontSize to set
	 */
	public void setYfontSize(String yfontSize) {
		this.yfontSize = yfontSize;
	}

	/**
	 * @param yfontColor the yfontColor to set
	 */
	public void setYfontColor(String yfontColor) {
		this.yfontColor = yfontColor;
	}

	/**
	 * @param xposition the xposition to set
	 */
	public void setXposition(String xposition) {
		this.xposition = xposition;
	}

	/**
	 * @param yposition the yposition to set
	 */
	public void setYposition(String yposition) {
		this.yposition = yposition;
	}

	/**
	 * @param showRangeLine the showRangeLine to set
	 */
	public void setShowRangeLine(String showRangeLine) {
		this.showRangeLine = showRangeLine;
	}

	/**
	 * @param rangeLineColor the rangeLineColor to set
	 */
	public void setRangeLineColor(String rangeLineColor) {
		this.rangeLineColor = rangeLineColor;
	}

	/**
	 * @param showDataTitle the showDataTitle to set
	 */
	public void setShowDataTitle(String showDataTitle) {
		this.showDataTitle = showDataTitle;
	}

	/**
	 * @param dataTilePosition the dataTilePosition to set
	 */
	public void setDataTitlePosition(String dataTitlePosition) {
		this.dataTitlePosition = dataTitlePosition;
	}

	/**
	 * @param barType the barType to set
	 */
	public void setBarType(String barType) {
		this.barType = barType;
	}

	/**
	 * @param barWidth the barWidth to set
	 */
	public void setBarWidth(String barWidth) {
		this.barWidth = barWidth;
	}

	/**
	 * @param barMargin the barMargin to set
	 */
	public void setBarMargin(String barMargin) {
		this.barMargin = barMargin;
	}

	/**
	 * @param showBarAverage the showBarAverage to set
	 */
	public void setShowBarAverage(String showBarAverage) {
		this.showBarAverage = showBarAverage;
	}

	/**
	 * @param barAverageFontType the barAverageFontType to set
	 */
	public void setBarAverageFontType(String barAverageFontType) {
		this.barAverageFontType = barAverageFontType;
	}

	/**
	 * @param barAverageFontSize the barAverageFontSize to set
	 */
	public void setBarAverageFontSize(String barAverageFontSize) {
		this.barAverageFontSize = barAverageFontSize;
	}

	/**
	 * @param barAverageFontColor the barAverageFontColor to set
	 */
	public void setBarAverageFontColor(String barAverageFontColor) {
		this.barAverageFontColor = barAverageFontColor;
	}

	/**
	 * @param barAverageLineColor the barAverageLineColor to set
	 */
	public void setBarAverageLineColor(String barAverageLineColor) {
		this.barAverageLineColor = barAverageLineColor;
	}

	/**
	 * @param showXLine the showXLine to set
	 */
	public void setShowXLine(String showXLine) {
		this.showXLine = showXLine;
	}

	/**
	 * @param showYLine the showYLine to set
	 */
	public void setShowYLine(String showYLine) {
		this.showYLine = showYLine;
	}

	/**
	 * @param xlineColor the xlineColor to set
	 */
	public void setXlineColor(String xlineColor) {
		this.xlineColor = xlineColor;
	}

	/**
	 * @param ylineColor the ylineColor to set
	 */
	public void setYlineColor(String ylineColor) {
		this.ylineColor = ylineColor;
	}

	/**
	 * @param showLineAverage the showLineAverage to set
	 */
	public void setShowLineAverage(String showLineAverage) {
		this.showLineAverage = showLineAverage;
	}

	/**
	 * @param lineAverageFontColor the lineAverageFontColor to set
	 */
	public void setLineAverageFontColor(String lineAverageFontColor) {
		this.lineAverageFontColor = lineAverageFontColor;
	}

	/**
	 * @param lineAverageFontSize the lineAverageFontSize to set
	 */
	public void setLineAverageFontSize(String lineAverageFontSize) {
		this.lineAverageFontSize = lineAverageFontSize;
	}

	/**
	 * @param lineAverageFontType the lineAverageFontType to set
	 */
	public void setLineAverageFontType(String lineAverageFontType) {
		this.lineAverageFontType = lineAverageFontType;
	}

	/**
	 * @param lineAverageLineColor the lineAverageLineColor to set
	 */
	public void setLineAverageLineColor(String lineAverageLineColor) {
		this.lineAverageLineColor = lineAverageLineColor;
	}

	/**
	 * @param rowTitle the rowTitle to set
	 */
	public void setRowTitle(String rowTitle) {
		this.rowTitle = rowTitle;
	}

	/**
	 * @param lineVisible the lineVisible to set
	 */
	public void setLineVisible(String lineVisible) {
		this.lineVisible = lineVisible;
	}

	/**
	 * @param lineShapeVisible the lineShapeVisible to set
	 */
	public void setLineShapeVisible(String lineShapeVisible) {
		this.lineShapeVisible = lineShapeVisible;
	}

	/**
	 * @param lineShapeFilled the lineShapeFilled to set
	 */
	public void setLineShapeFilled(String lineShapeFilled) {
		this.lineShapeFilled = lineShapeFilled;
	}

	/**
	 * @param lineColor the lineColor to set
	 */
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * @param oblique the oblique to set
	 */
	public void setOblique(String oblique) {
		this.oblique = oblique;
	}

	/**
	 * @param barValue the barValue to set
	 */
	public void setBarValue(String barValue) {
		this.barValue = barValue;
	}

	/**
	 * @param lineValue the lineValue to set
	 */
	public void setLineValue(String lineValue) {
		this.lineValue = lineValue;
	}

	/**
	 * @param barPlotType the barPlotType to set
	 */
	public void setBarPlotType(String barPlotType) {
		this.barPlotType = barPlotType;
	}

	/**
	 * @param linePlotType the linePlotType to set
	 */
	public void setLinePlotType(String linePlotType) {
		this.linePlotType = linePlotType;
	}

	/**
	 * @param barRowTitle the barRowTitle to set
	 */
	public void setBarRowTitle(String barRowTitle) {
		this.barRowTitle = barRowTitle;
	}

	/**
	 * @param lineRowTitle the lineRowTitle to set
	 */
	public void setLineRowTitle(String lineRowTitle) {
		this.lineRowTitle = lineRowTitle;
	}

	/**
	 * @param y1title the y1title to set
	 */
	public void setY1title(String y1title) {
		this.y1title = y1title;
	}

	/**
	 * @param fontType the y1titleFontType to set
	 */
	public void setY1titleFontType(String fontType) {
		y1titleFontType = fontType;
	}

	/**
	 * @param fontSize the y1titleFontSize to set
	 */
	public void setY1titleFontSize(String fontSize) {
		y1titleFontSize = fontSize;
	}

	/**
	 * @param fontColor the y1titleFontColor to set
	 */
	public void setY1titleFontColor(String fontColor) {
		y1titleFontColor = fontColor;
	}

	/**
	 * @param color the y1fontColor to set
	 */
	public void setY1fontColor(String color) {
		y1fontColor = color;
	}

	/**
	 * @param size the y1fontSize to set
	 */
	public void setY1fontSize(String size) {
		y1fontSize = size;
	}

	/**
	 * @param type the y1fontType to set
	 */
	public void setY1fontType(String type) {
		y1fontType = type;
	}

	/**
	 * @param percentPrecious the percentPrecious to set
	 */
	public void setPercentPrecious(String percentPrecious) {
		this.percentPrecious = percentPrecious;
	}

	/**
	 * @param dataFormat the dataFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @param tickType the tickType to set
	 */
	public void setTickType(String tickType) {
		this.tickType = tickType;
	}

	/**
	 * @param tickUnit the tickUnit to set
	 */
	public void setTickUnit(String tickUnit) {
		this.tickUnit = tickUnit;
	}

	public String getAnimationStartAlpha() {
		return animationStartAlpha;
	}

	public void setAnimationStartAlpha(String animationStartAlpha) {
		this.animationStartAlpha = animationStartAlpha;
	}

	public String getAnimationStartEffect() {
		return animationStartEffect;
	}

	public void setAnimationStartEffect(String animationStartEffect) {
		this.animationStartEffect = animationStartEffect;
	}

	public String getAnimationStartRadius() {
		return animationStartRadius;
	}

	public void setAnimationStartRadius(String animationStartRadius) {
		this.animationStartRadius = animationStartRadius;
	}

	public String getAnimationStartTime() {
		return animationStartTime;
	}

	public void setAnimationStartTime(String animationStartTime) {
		this.animationStartTime = animationStartTime;
	}

	public String getColNames() {
		return colNames;
	}

	public void setColNames(String colNames) {
		this.colNames = colNames;
	}



	public String getSettingsFile() {
		return settingsFile;
	}

	public void setSettingsFile(String settingsFile) {
		this.settingsFile = settingsFile;
	}

	public String getLinkTarget() {
		return linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public String getPieAngle() {
		return pieAngle;
	}

	public void setPieAngle(String pieAngle) {
		this.pieAngle = pieAngle;
	}

	public String getPieHeight() {
		return pieHeight;
	}

	public void setPieHeight(String pieHeight) {
		this.pieHeight = pieHeight;
	}

	public String getPieInnerRadius() {
		return pieInnerRadius;
	}

	public void setPieInnerRadius(String pieInnerRadius) {
		this.pieInnerRadius = pieInnerRadius;
	}

	public String getPieRadius() {
		return pieRadius;
	}

	public void setPieRadius(String pieRadius) {
		this.pieRadius = pieRadius;
	}

	public String getAlpha() {
		return alpha;
	}

	public String getBackColor() {
		return backColor;
	}

	public String getBarAverageFontColor() {
		return barAverageFontColor;
	}

	public String getBarAverageFontSize() {
		return barAverageFontSize;
	}

	public String getBarAverageFontType() {
		return barAverageFontType;
	}

	public String getBarAverageLineColor() {
		return barAverageLineColor;
	}

	public String getBarMargin() {
		return barMargin;
	}

	public String getBarPlotType() {
		return barPlotType;
	}

	public String getBarRowTitle() {
		return barRowTitle;
	}

	public String getBarType() {
		return barType;
	}

	public String getBarValue() {
		return barValue;
	}

	public String getBarWidth() {
		return barWidth;
	}

	public String getChartType() {
		return chartType;
	}

	public String getColor() {
		return color;
	}

	public String getDataTitlePosition() {
		return dataTitlePosition;
	}

	public String getDateFormat() {
		return dateFormat;
	}


	public String getHeight() {
		return height;
	}



	public String getLineAverageFontColor() {
		return lineAverageFontColor;
	}

	public String getLineAverageFontSize() {
		return lineAverageFontSize;
	}

	public String getLineAverageFontType() {
		return lineAverageFontType;
	}

	public String getLineAverageLineColor() {
		return lineAverageLineColor;
	}

	public String getLineColor() {
		return lineColor;
	}

	public String getLinePlotType() {
		return linePlotType;
	}

	public String getLineRowTitle() {
		return lineRowTitle;
	}

	public String getLineShapeFilled() {
		return lineShapeFilled;
	}

	public String getLineShapeVisible() {
		return lineShapeVisible;
	}

	public String getLineValue() {
		return lineValue;
	}

	public String getLineVisible() {
		return lineVisible;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public String getNoDataMessage() {
		return noDataMessage;
	}

	public String getOblique() {
		return oblique;
	}

	public String getPercentPrecious() {
		return percentPrecious;
	}


	public String getPlotFlag() {
		return plotFlag;
	}

	public String getPlotFontColor() {
		return plotFontColor;
	}

	public String getPlotFontSize() {
		return plotFontSize;
	}



	public String getPlotOrientation() {
		return plotOrientation;
	}

	public String getPlotType() {
		return plotType;
	}

	public String getPrecious() {
		return precious;
	}

	public String getRangeLineColor() {
		return rangeLineColor;
	}

	public String getRowTitle() {
		return rowTitle;
	}

	public String getShowBarAverage() {
		return showBarAverage;
	}

	public String getShowDataTitle() {
		return showDataTitle;
	}

	public String getShowLineAverage() {
		return showLineAverage;
	}

	public String getShowPlot() {
		return showPlot;
	}

	public String getShowRangeLine() {
		return showRangeLine;
	}

	public String getShowSubTitle() {
		return showSubTitle;
	}

	public String getShowToolTip() {
		return showToolTip;
	}

	public String getShowXLine() {
		return showXLine;
	}

	public String getShowYLine() {
		return showYLine;
	}

	public String getSubTitleBackColor() {
		return subTitleBackColor;
	}

	public String getSubTitleFlag() {
		return subTitleFlag;
	}

	public String getSubTitleFontColor() {
		return subTitleFontColor;
	}

	public String getSubTitleFontSize() {
		return subTitleFontSize;
	}



	public String getSubTitlePosition() {
		return subTitlePosition;
	}

	public String getTickType() {
		return tickType;
	}

	public String getTickUnit() {
		return tickUnit;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleBackColor() {
		return titleBackColor;
	}

	public String getTitleFontColor() {
		return titleFontColor;
	}

	public String getTitleFontSize() {
		return titleFontSize;
	}

	public String getTitleFontType() {
		return titleFontType;
	}

	public String getToolTipFlag() {
		return toolTipFlag;
	}

	public String getValue() {
		return value;
	}

	public String getWidth() {
		return width;
	}

	public String getXfontColor() {
		return xfontColor;
	}

	public String getXfontSize() {
		return xfontSize;
	}

	public String getXfontType() {
		return xfontType;
	}

	public String getXlineColor() {
		return xlineColor;
	}

	public String getXposition() {
		return xposition;
	}

	public String getXtitle() {
		return xtitle;
	}

	public String getXtitleFontColor() {
		return xtitleFontColor;
	}

	public String getXtitleFontSize() {
		return xtitleFontSize;
	}

	public String getXtitleFontType() {
		return xtitleFontType;
	}

	public String getY1fontColor() {
		return y1fontColor;
	}

	public String getY1fontSize() {
		return y1fontSize;
	}

	public String getY1fontType() {
		return y1fontType;
	}

	public String getY1title() {
		return y1title;
	}

	public String getY1titleFontColor() {
		return y1titleFontColor;
	}

	public String getY1titleFontSize() {
		return y1titleFontSize;
	}

	public String getY1titleFontType() {
		return y1titleFontType;
	}

	public String getYfontColor() {
		return yfontColor;
	}

	public String getYfontSize() {
		return yfontSize;
	}

	public String getYfontType() {
		return yfontType;
	}

	public String getYlineColor() {
		return ylineColor;
	}

	public String getYposition() {
		return yposition;
	}

	public String getYtitle() {
		return ytitle;
	}

	public String getYtitleFontColor() {
		return ytitleFontColor;
	}

	public String getYtitleFontSize() {
		return ytitleFontSize;
	}

	public String getYtitleFontType() {
		return ytitleFontType;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSubTitleWidth() {
		return subTitleWidth;
	}

	public void setSubTitleWidth(String subTitleWidth) {
		this.subTitleWidth = subTitleWidth;
	}

	public String getPlotTitle() {
		return plotTitle;
	}

	public void setPlotTitle(String plotTitle) {
		this.plotTitle = plotTitle;
	}

	public String getToolTipTitle() {
		return toolTipTitle;
	}

	public void setToolTipTitle(String toolTipTitle) {
		this.toolTipTitle = toolTipTitle;
	}

	public String getSubTitleMaxColumns() {
		return subTitleMaxColumns;
	}

	public void setSubTitleMaxColumns(String subTitleMaxColumns) {
		this.subTitleMaxColumns = subTitleMaxColumns;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getXrotate() {
		return xrotate;
	}

	public void setXrotate(String xrotate) {
		this.xrotate = xrotate;
	}

	public String getYrotate() {
		return yrotate;
	}

	public void setYrotate(String yrotate) {
		this.yrotate = yrotate;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getIndicatorLineColor() {
		return indicatorLineColor;
	}

	public void setIndicatorLineColor(String indicatorLineColor) {
		this.indicatorLineColor = indicatorLineColor;
	}

	public String getIndicatorTextColor() {
		return indicatorTextColor;
	}

	public void setIndicatorTextColor(String indicatorTextColor) {
		this.indicatorTextColor = indicatorTextColor;
	}

	public String getIndicatorTextSize() {
		return indicatorTextSize;
	}

	public void setIndicatorTextSize(String indicatorTextSize) {
		this.indicatorTextSize = indicatorTextSize;
	}



	public String getVerticalLinesWidth() {
		return verticalLinesWidth;
	}

	public void setVerticalLinesWidth(String verticalLinesWidth) {
		this.verticalLinesWidth = verticalLinesWidth;
	}

	public String getYleftVerticalLines() {
		return yleftVerticalLines;
	}

	public void setYleftVerticalLines(String yleftVerticalLines) {
		this.yleftVerticalLines = yleftVerticalLines;
	}

	public String getYrightVerticalLines() {
		return yrightVerticalLines;
	}

	public void setYrightVerticalLines(String yrightVerticalLines) {
		this.yrightVerticalLines = yrightVerticalLines;
	}

	public String getYleftType() {
		return yleftType;
	}

	public void setYleftType(String yleftType) {
		this.yleftType = yleftType;
	}

	public String getYrightType() {
		return yrightType;
	}

	public void setYrightType(String yrightType) {
		this.yrightType = yrightType;
	}

	public String getStartOnAxis() {
		return startOnAxis;
	}

	public void setStartOnAxis(String startOnAxis) {
		this.startOnAxis = startOnAxis;
	}

	public String getYleftFillAlpha() {
		return yleftFillAlpha;
	}

	public void setYleftFillAlpha(String yleftFillAlpha) {
		this.yleftFillAlpha = yleftFillAlpha;
	}

	public String getYleftShapeType() {
		return yleftShapeType;
	}

	public void setYleftShapeType(String yleftShapeType) {
		this.yleftShapeType = yleftShapeType;
	}

	public String getYrightFillAlpha() {
		return yrightFillAlpha;
	}

	public void setYrightFillAlpha(String yrightFillAlpha) {
		this.yrightFillAlpha = yrightFillAlpha;
	}

	public String getYrightShapeType() {
		return yrightShapeType;
	}

	public void setYrightShapeType(String yrightShapeType) {
		this.yrightShapeType = yrightShapeType;
	}

	public String getRightValue() {
		return rightValue;
	}

	public void setRightValue(String rightValue) {
		this.rightValue = rightValue;
	}

	public String getRightColNames() {
		return rightColNames;
	}

	public void setRightColNames(String rightColNames) {
		this.rightColNames = rightColNames;
	}

	public String getRightRowTitle() {
		return rightRowTitle;
	}

	public void setRightRowTitle(String rightRowTitle) {
		this.rightRowTitle = rightRowTitle;
	}

	public String getYleftLineAlpha() {
		return yleftLineAlpha;
	}

	public void setYleftLineAlpha(String yleftLineAlpha) {
		this.yleftLineAlpha = yleftLineAlpha;
	}

	public String getYleftLineWidth() {
		return yleftLineWidth;
	}

	public void setYleftLineWidth(String yleftLineWidth) {
		this.yleftLineWidth = yleftLineWidth;
	}

	public String getYrightLineAlpha() {
		return yrightLineAlpha;
	}

	public void setYrightLineAlpha(String yrightLineAlpha) {
		this.yrightLineAlpha = yrightLineAlpha;
	}

	public String getYrightLineWidth() {
		return yrightLineWidth;
	}

	public void setYrightLineWidth(String yrightLineWidth) {
		this.yrightLineWidth = yrightLineWidth;
	}

	public String getDataTitle() {
		return dataTitle;
	}

	public void setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
	}

	public String getGrowEffect() {
		return growEffect;
	}

	public void setGrowEffect(String growEffect) {
		this.growEffect = growEffect;
	}

	public String getGrowTime() {
		return growTime;
	}

	public void setGrowTime(String growTime) {
		this.growTime = growTime;
	}

	public String getSequencedGrow() {
		return sequencedGrow;
	}

	public void setSequencedGrow(String sequencedGrow) {
		this.sequencedGrow = sequencedGrow;
	}

	public String getShowXAxes() {
		return showXAxes;
	}

	public void setShowXAxes(String showXAxes) {
		this.showXAxes = showXAxes;
	}

	public String getShowYAxes() {
		return showYAxes;
	}

	public void setShowYAxes(String showYAxes) {
		this.showYAxes = showYAxes;
	}

	public String getBorderAlpha() {
		return borderAlpha;
	}

	public void setBorderAlpha(String borderAlpha) {
		this.borderAlpha = borderAlpha;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getToolTipAlpha() {
		return toolTipAlpha;
	}

	public void setToolTipAlpha(String toolTipAlpha) {
		this.toolTipAlpha = toolTipAlpha;
	}

	public String getToolTipBorderAlpha() {
		return toolTipBorderAlpha;
	}

	public void setToolTipBorderAlpha(String toolTipBorderAlpha) {
		this.toolTipBorderAlpha = toolTipBorderAlpha;
	}

	public String getToolTipBorderColor() {
		return toolTipBorderColor;
	}

	public void setToolTipBorderColor(String toolTipBorderColor) {
		this.toolTipBorderColor = toolTipBorderColor;
	}

	public String getToolTipBorderWidth() {
		return toolTipBorderWidth;
	}

	public void setToolTipBorderWidth(String toolTipBorderWidth) {
		this.toolTipBorderWidth = toolTipBorderWidth;
	}

	public String getToolTipFontColor() {
		return toolTipFontColor;
	}

	public void setToolTipFontColor(String toolTipFontColor) {
		this.toolTipFontColor = toolTipFontColor;
	}

	public String getToolTipFontSize() {
		return toolTipFontSize;
	}

	public void setToolTipFontSize(String toolTipFontSize) {
		this.toolTipFontSize = toolTipFontSize;
	}

	public String getToolTipCornerRadius() {
		return toolTipCornerRadius;
	}

	public void setToolTipCornerRadius(String toolTipCornerRadius) {
		this.toolTipCornerRadius = toolTipCornerRadius;
	}

	public String getDataTitleFontColor() {
		return dataTitleFontColor;
	}

	public void setDataTitleFontColor(String dataTitleFontColor) {
		this.dataTitleFontColor = dataTitleFontColor;
	}

	public String getDataTitleFontSize() {
		return dataTitleFontSize;
	}

	public void setDataTitleFontSize(String dataTitleFontSize) {
		this.dataTitleFontSize = dataTitleFontSize;
	}

	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getXValueInterval() {
		return xValueInterval;
	}

	public void setXValueInterval(String valueInterval) {
		xValueInterval = valueInterval;
	}

	public String getXValueStart() {
		return xValueStart;
	}

	public void setXValueStart(String valueStart) {
		xValueStart = valueStart;
	}

	public String getYleftShapeSize() {
		return yleftShapeSize;
	}

	public void setYleftShapeSize(String yleftShapeSize) {
		this.yleftShapeSize = yleftShapeSize;
	}

	public String getYrightShapeSize() {
		return yrightShapeSize;
	}

	public void setYrightShapeSize(String yrightShapeSize) {
		this.yrightShapeSize = yrightShapeSize;
	}

	public String getYleftBalloonText() {
		return yleftBalloonText;
	}

	public void setYleftBalloonText(String yleftBalloonText) {
		this.yleftBalloonText = yleftBalloonText;
	}

	public String getYrightBalloonText() {
		return yrightBalloonText;
	}

	public void setYrightBalloonText(String yrightBalloonText) {
		this.yrightBalloonText = yrightBalloonText;
	}

	public String getBackAlpha() {
		return backAlpha;
	}

	public void setBackAlpha(String backAlpha) {
		this.backAlpha = backAlpha;
	}



	
}
