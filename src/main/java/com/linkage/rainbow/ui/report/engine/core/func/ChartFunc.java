package com.linkage.rainbow.ui.report.engine.core.func;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;

import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.linkage.rainbow.ui.report.engine.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.ImageMapUtilities;
import org.jfree.chart.imagemap.OverLIBToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.ToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.URLTagFragmentGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
 
/**
 * 图表函数, 语法： chart(chartTypeExp,[colExp],titleExp,...) <br>
 * 参数说明：<br>
 * chartTypeExp 图表类型 <br>
 * [colExp]数据字段表达式,要用[]括起来,各个字段用,号分开 titleExp 图表标题
 * @version 1.0
 * @author 陈亮 2009-06-1
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-06-1<br>
 *         修改内容:新建
 *         <hr>
 *
 */
public class ChartFunc extends Func { 

	private static Log log = LogFactory.getLog(ChartFunc.class);
	/* 图表类型 start */
	/** 饼图 */
	public final static String PIE_CHART = "piechart";// 
	/** 3D饼图 */
	public final static String PIE_CHART_3D = "piechart3d";// 
	/** 3D环图 */
	public final static String RING_CHART = "ringchart";// 
	/** 柱图 */
	public final static String BAR_CHART = "barchart";// 
	/** 3D柱图 */
	public final static String BAR_CHART_3D = "barchart3d";// 
	/** 折线图 */
	public final static String LINE_CHART = "linechart";// 
	/** 3D折线图 */
	public final static String LINE_CHART_3D = "linechart3d";// 
	/** 柱图与线图复合图表 */
	public final static String BAR_LINE_CHART = "barlinechart";// 
	/**  3D柱图与线图复合图表 */
	public final static String BAR_LINE_CHART_3D = "barlinechart3d";//
	/** 3D柱图与3d线图复合图表 */
	public final static String BAR_LINE_CHART_3D_3D = "barlinechart3d3d";// 

	/* 图表类型 end */

	private static final String NO_DATA_MESSAGE = "当前的图表无数据!";

	private static final Font NO_DATA_MESSAGE_FONT = new Font("宋体", Font.PLAIN,
			12);

	private static final Color NO_DATA_MESSAGE_COLOR = Color.red;

	private Double avgValue = null;
	private Double minValue = null;
	private Double maxValue = null;
	

	private int colCounts = 0;
	
	private String dsNameTmp = null;

	public ChartFunc() {

	}

	static class CustomBarRenderer extends BarRenderer {

		public Paint getItemPaint(int i, int j) {

			Paint returnPaint = null;
			if (isRow) {
				returnPaint = colors[i % colors.length];
			} else {
				returnPaint = colors[j % colors.length];
			}

			return returnPaint;
		}

		private Paint colors[];

		private boolean isRow = false;

		public CustomBarRenderer(Paint apaint[]) {
			super();
			colors = apaint;
		}

		public CustomBarRenderer(Paint apaint[], boolean isRow) {
			super();
			this.colors = apaint;
			this.isRow = isRow;
		}
	}

	static class CustomBarRenderer3D extends BarRenderer3D {
		public Paint getItemPaint(int i, int j) {

			Paint returnPaint = null;
			if (isRow) {
				returnPaint = colors[i % colors.length];
			} else {
				returnPaint = colors[j % colors.length];
			}

			return returnPaint;
		}

		private Paint colors[];

		private boolean isRow = false;

		public CustomBarRenderer3D(Paint apaint[]) {
			super();
			colors = apaint;
		}

		public CustomBarRenderer3D(Paint apaint[], boolean isRow) {
			super();
			this.colors = apaint;
			this.isRow = isRow;
		}
	}

	static class WindowURLTagFragmentGenerator implements
			URLTagFragmentGenerator {
		private String target;

		public WindowURLTagFragmentGenerator() {

		}

		public WindowURLTagFragmentGenerator(String target) {
			this.target = target;
		}

		/**
		 * 生成在新窗口展现的map.
		 * 
		 * @param urlText
		 *            the URL.
		 * @return 格式化的链接脚本
		 */

		public String generateURLFragment(String urlText) {
			return " href=\""
					+ urlText
					+ "\" "
					+ (target != null && target.trim().length() > 0 ? " target=\""
							+ target + "\""
							: "");
		}
	}

	/**
	 * 生成图表计算.
	 */
	public Object calculate() {
		String result =null; //返回生成图片的路径
		String chartType = para.length >= 2 && para[0] != null ? para[0].trim()
				.toLowerCase() : null;

		// 取得对应的未扩展前的单元格
		Cell newCell = newCS.getCurrCell();

		if (chartType != null) {
			String chart = "";
			if (chartType.equals(PIE_CHART)) {
				chart = pieChart(para);
			} else if (chartType.equals(PIE_CHART_3D)) {
				chart = pieChart3D(para);
			} else if (chartType.equals(RING_CHART)) {
				chart = ringChart(para);
			} else if (chartType.equals(BAR_CHART)) {
				chart = barChart(para, false);
			} else if (chartType.equals(BAR_CHART_3D)) {
				chart = barChart(para, true);
			} else if (chartType.equals(LINE_CHART)) {
				chart = lineChart(para,false);
			}else if (chartType.equals(LINE_CHART_3D)) {
				chart = lineChart(para,true);
			}else if (chartType.equals(BAR_LINE_CHART)) {
				chart = BarLineChart(para,false,false);
			}else if (chartType.equals(BAR_LINE_CHART_3D)) {
				chart = BarLineChart(para,true,false);
			} else if (chartType.equals(BAR_LINE_CHART_3D_3D)) {
				chart = BarLineChart(para,true,true);
			} 
			result = chart;
		}
		return result;
	}



	/**
	 * 取得子参数
	 * 
	 * @param colName
	 * @return
	 */
	private String[] getSubPara(String para, boolean isDbCol) {
		String subParas[] = null;
		if (isDbCol)
			colCounts = 0;
		if (para != null && para.trim().length() > 0) {
			para = para.trim();
			try {
				int length = para.indexOf("]");
				if (length < 0) {
					length = para.length();
				}
				para = para.substring(para.indexOf("[") + 1, length);
				subParas = para.split(",");
				if (isDbCol) {
					dsNameTmp = null;
					for (int i = 0; i < subParas.length; i++) {
						if(i == 0)
							dsNameTmp = subParas[i].substring(0,subParas[i]
							           								.lastIndexOf(".") );
						subParas[i] = subParas[i].substring(subParas[i]
								.lastIndexOf(".") + 1);
					}
					colCounts = subParas.length;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return subParas;
	}

	/**
	 * 将CachedRowSet数据转为DefaultPieDataset
	 * 
	 * @param crs
	 * @param colName
	 * @return
	 */
	private DefaultPieDataset rowSet2PieDataset(CachedRowSet crs, String colName) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		if (colName != null) {
			String colNames[] = getSubPara(colName, true);
			try {
				if (colNames.length >= 2) {
					crs.beforeFirst();
					while (crs.next()){
						try {
							pieDataset.setValue(crs.getString(colNames[0]), crs.getDouble(colNames[1]));
						} catch (Exception e) {
						}
						
						
					}
				}
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
		}
		return pieDataset;
	}

	private DefaultCategoryDataset rowSet2CategoryDataset(String colName) {
		return rowSet2CategoryDataset(colName, false);
	}
	
	private DefaultCategoryDataset rowSet2CategoryDataset(String colName, boolean isSumAvgValue) {
		return rowSet2CategoryDataset(colName, isSumAvgValue,false);
	}

	/**
	 * 将CachedRowSet数据转为DefaultCategoryDataset
	 * 
	 * @param crs
	 * @param colName
	 * @param sumAvgValue
	 *            是否算平均值
	 * @return
	 */
	private DefaultCategoryDataset rowSet2CategoryDataset(String colName, boolean isSumAvgValue,boolean isSumMaxMin) {
		
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		avgValue = null;
		minValue = null;
		maxValue = null;
		
		double minValueTmp = 0d;
		double maxValueTmp = 0d;
		if (colName != null) {
			String colNames[] = getSubPara(colName, true);
			CachedRowSet crs = (CachedRowSet)getDsMap().get(dsNameTmp);
			try {
				if (colNames.length >= 2) {
					crs.beforeFirst();
					double allValue = 0d;
					int counts = 0;
					while (crs.next()) {
						try {
							double currValue =  crs.getDouble(colNames[0]);
							
							if (colNames.length == 2) {
								defaultcategorydataset.setValue(currValue, "", crs
										.getString(colNames[1]));
							} else {
								defaultcategorydataset.setValue(currValue, crs
										.getString(colNames[1]), crs
										.getString(colNames[2]));
							}
							if (isSumAvgValue) {
								allValue = allValue + currValue;
								counts++;
							}
							if(isSumMaxMin){
								if(minValue == null){
									minValue = new Double(currValue);
									maxValue = new Double(currValue);
									minValueTmp = currValue;
									maxValueTmp = currValue;
								} else {
									if(currValue<minValueTmp){
										minValueTmp = currValue;
									}
									if(currValue>maxValueTmp){
										maxValueTmp = currValue;
									}
								}
							}
						} catch (Exception e) {
						}
					}
						
					if (isSumAvgValue && counts != 0) {
						avgValue = new Double(allValue / counts);
					}
					if(isSumMaxMin && minValue!= null){
						minValue = new Double(minValueTmp);
						maxValue = new Double(maxValueTmp);
					}
				}
			} catch (Exception e) {
			}
		}
		return defaultcategorydataset;
	}

	/**
	 * 设置饼图中各成份分离程度
	 * 
	 * @param explode
	 * @param plot
	 * @param ds
	 */
	private void setExplodePercent(String explode[], PiePlot plot) {
		if (explode != null) {
			try {
				if (explode.length > 1 && explode[0].equals("-1")) { // -1表示所有成份都分离
					double explodePercent = 0.1D;
					try {
						explodePercent = Double.parseDouble(explode[1]);
					} catch (Exception e) {
					}
					for (int i = 0; i < ds.getRow(); i++) {
						plot.setExplodePercent(i, explodePercent);
					}
				} else {
					for (int i = 0; i < explode.length; i = i + 2) {
						try {
							int index = Integer.parseInt(explode[i]);
							double explodePercent = Double
									.parseDouble(explode[i + 1]);
							plot.setExplodePercent(index, explodePercent);
						} catch (Exception e) {
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 饼图
	 * 
	 * @param args[1]
	 *            记录集字段名
	 * @param args[2]
	 *            图表标题[图表标题,显示位置(left,right,top,bottom)]
	 * @param args[3]
	 *            宽度
	 * @param args[4]
	 *            高度
	 * @param args[5]
	 *            是否显示图例[是否显示,显示位置(left,right,top,bottom)]
	 * @param args[6]
	 *            图表热点链接[图表热点链接,热点链接目标窗口]
	 * @param args[7]
	 *            数据标志[是否显示标题,是否显示数值,是否显示%]
	 * @param args[8]
	 *            字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
	 * @param args[9]
	 *            自定义颜色[#33333,#...]
	 * @param args[10]
	 *            背景颜色
	 * @param args[11]
	 *            分离[分离序号,分离程度...]
	 * 
	 * @param args
	 */
	private String pieChart(String args[]) {
		String chartInfo = "";
		String filename = "";
		try {

			/* 取得各参数 */
			int argsLength = args.length;
			// 图表标题[图表标题,显示位置(left,right,top,bottom)]
			String chartTitle =null;
			String titlePosition = null;			
			if (argsLength > 2) {
				String subParas[] = getSubPara(args[2]);
				if (subParas != null) {
					chartTitle = subParas.length > 0 ? subParas[0]:null;
					titlePosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}
			if(chartTitle == null) chartTitle="";
			// 宽度高度
			int width = 400;
			int height = 300;
			try {
				if (argsLength > 3 && args[3].trim().length() > 0) {
					width = Integer.parseInt(args[3]);
				}
			} catch (Exception e) {
			}
			try {
				if (argsLength > 4 && args[4].trim().length() > 0) {
					height = Integer.parseInt(args[4]);
				}
			} catch (Exception e) {
			}
			// 是否显示图例[是否显示,显示位置(left,right,top,bottom)]
			boolean showLegend = false;
			String  legendPosition = null;			
			if (argsLength > 5) {
				String subParas[] = getSubPara(args[5]);
				if (subParas != null) {
					showLegend = subParas.length > 0 && subParas[0].trim().toLowerCase().equals("true")? true:false;
					legendPosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}

			// 图表热点链接[图表热点链接,热点链接目标窗口]
			String mapURL = null;
			String target = null;
			if (argsLength > 6) {
				String subParas[] = getSubPara(args[6]);
				if (subParas != null) {
					mapURL = subParas.length > 0 ? subParas[0] : null;
					target = subParas.length > 1 ? subParas[1] : null;
					if(mapURL != null && mapURL.length()>=2 ){
						if((mapURL.startsWith("\"")&& mapURL.endsWith("\"")) || (mapURL.startsWith("'")&& mapURL.endsWith("'"))) {
							mapURL = mapURL.substring(1,mapURL.length()-1);
						}
					}
				}
			}
			// 数据标志[是否显示标题,是否显示数值,是否显示%]
			boolean showDataTitle = true;
			boolean showDataValue = true;
			boolean showDataPercent = false;
			if (argsLength > 7) {
				String subParas[] = getSubPara(args[7]);
				if (subParas != null) {
					showDataTitle = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("false") ? false
							: true;
					showDataValue = subParas.length > 1
							&& subParas[1].trim().toLowerCase().equals("false") ? false
							: true;
					showDataPercent = subParas.length > 2
							&& subParas[2].trim().toLowerCase().equals("true") ? true
							: false;
				}
			}
			// 字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
			// 数据标志字体
			String fontType = "宋体";
			int fontSize = 12;
			String fontColor = null;
			// 图表标题字体
			String chartFontType = "宋体";
			int chartFontSize = 18;
			String chartFontColor = null;
			// 图例字体
			String legendFontType = "宋体";
			int legendFontSize = 12;
			String legendFontColor = null;
			// 抗锯齿
			boolean isAntialiasing = false;
			if (argsLength > 8) {
				String subParas[] = getSubPara(args[8]);
				if (subParas != null) {
					fontType = subParas.length > 0
							&& subParas[0].trim().length() > 0 ? subParas[0]
							: "宋体";
					try {
						fontSize = subParas.length > 1 ? Integer
								.parseInt(subParas[1]) : 12;
					} catch (Exception e) {
					}
					fontColor = subParas.length > 2 ? subParas[2] : null;
					chartFontType = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: fontType;
					try {
						chartFontSize = subParas.length > 4 ? Integer
								.parseInt(subParas[4]) : 18;
					} catch (Exception e) {
					}
					chartFontColor = subParas.length > 5 ? subParas[5]
							: fontColor;

					legendFontType = subParas.length > 6
							&& subParas[6].trim().length() > 0 ? subParas[6]
							: fontType;
					try {
						legendFontSize = subParas.length > 7 ? Integer
								.parseInt(subParas[7]) : 12;
					} catch (Exception e) {
					}
					legendFontColor = subParas.length > 8 ? subParas[8]
							: fontColor;

					isAntialiasing = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 9
							&& subParas[9].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
				}
			}
			// 自定义颜色
			String customColors[] = null;
			customColors = getSubPara(argsLength > 9 ? args[9] : null);
			// 背景色
			String backColor = null;
			backColor = argsLength > 10 ? args[10] : null;
			// 分离[分离序号,分离程度...]
			String explode[] = null;
			explode = getSubPara(argsLength > 11 ? args[11] : null);

			/* 取得各参数 end */

			// 取CachedRowSet 转换 为饼图数据集
			DefaultPieDataset pieDataset = rowSet2PieDataset(ds, args[1]);

			// 创建图表对象
			PiePlot plot = new PiePlot(pieDataset);
			setNoDataMessage(plot);

			// 创建图表的MAP热点链接
			if (mapURL != null && mapURL.trim().length() > 0) {
				plot.setURLGenerator(new StandardPieURLGenerator(mapURL));
			}

			// 数据标志[是否显示标题,是否显示数值,是否显示%]
			if (showDataTitle || showDataValue || showDataPercent) {
				String dataLabel = "";
				if (showDataTitle)
					dataLabel += " {0}";
				if (showDataValue)
					dataLabel += " = {1}";
				if (showDataPercent)
					dataLabel += " ({2})";
				dataLabel = dataLabel.trim();
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
						dataLabel));
			}
			// 字体
			Font plotLabelFont = new Font(fontType, Font.PLAIN, fontSize);
			Font chartLabelFont = new Font(chartFontType, Font.BOLD,
					chartFontSize);
			Font legendLabelFont = new Font(legendFontType, Font.PLAIN,
					legendFontSize);
			plot.setLabelFont(plotLabelFont);
			plot.setLabelPaint(StringUtils.getColor(fontColor, Color.BLACK));

			// 自定义颜色
			if (customColors != null) {
				for (int i = 0; i < customColors.length; i++) {
					Color color = StringUtils.getColor(customColors[i]);
					if (color != null)
						plot.setSectionPaint(i, color);
				}
			}
			// 分离[分离序号,分离程度...]
			setExplodePercent(explode, plot);

			// 生成图表
			JFreeChart chart = new JFreeChart(chartTitle, chartLabelFont, plot,
					showLegend);
			TextTitle title = new TextTitle(chartTitle);
			title.setPaint(StringUtils.getColor(chartFontColor, Color.BLACK));
			title.setFont(chartLabelFont);
			if(titlePosition != null ){
				if(titlePosition.equals("right"))
					title.setPosition(RectangleEdge.RIGHT);
				else if(titlePosition.equals("top"))
					title.setPosition(RectangleEdge.TOP);
				else if(titlePosition.equals("left"))
					title.setPosition(RectangleEdge.LEFT);
				else if(titlePosition.equals("bottom"))
					title.setPosition(RectangleEdge.BOTTOM);
			}
			
			chart.setTitle(title);
			if(chart.getLegend() != null ){
				if(legendPosition != null ){
					if(legendPosition.equals("right"))
						chart.getLegend().setPosition(RectangleEdge.RIGHT);
					else if(legendPosition.equals("top"))
						chart.getLegend().setPosition(RectangleEdge.TOP);
					else if(legendPosition.equals("left"))
						chart.getLegend().setPosition(RectangleEdge.LEFT);
					else if(legendPosition.equals("bottom"))
							chart.getLegend().setPosition(RectangleEdge.BOTTOM);
				}
				chart.getLegend().setItemFont(legendLabelFont);
				chart.getLegend().setItemPaint(
						StringUtils.getColor(legendFontColor, Color.BLACK));
			}
			// 设置图表的背景色
			chart.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			plot.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			if(chart.getLegend() != null)
				chart.getLegend().setBackgroundPaint(
						StringUtils.getColor(backColor, Color.white));

			// 抗锯齿功能
			if (isAntialiasing) {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}

			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 把图片写入临时目录并输出
			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, null);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			writeImageMap(pw, filename, info, false, target);
			// 图表信息,包括图片与热点
			chartInfo = report.getPara().get("contextPath")
					+ "/servlet/DisplayChart?filename=" + filename;
			chartInfo = " <img src=\"" + chartInfo + "\" width=" + width
					+ " height=" + height + " border=0 usemap=\"#" + filename
					+ "\">";
			chartInfo = sw.getBuffer() + chartInfo;

		} catch (Exception e) {
			chartInfo = "异常:" + e.toString();
			log.error(e.getStackTrace());
		}

		return chartInfo;
	}

	/**
	 * 3D饼图
	 * 
	 * @param args[1]
	 *            记录集字段名
	 * @param args[2]
	 *            图表标题[图表标题,显示位置(left,right,top,bottom)]
	 * @param args[3]
	 *            宽度
	 * @param args[4]
	 *            高度
	 * @param args[5]
	 *            是否显示图例[是否显示,显示位置(left,right,top,bottom)]
	 * @param args[6]
	 *            图表热点链接[图表热点链接,热点链接目标窗口]
	 * @param args[7]
	 *            数据标志[是否显示标题,是否显示数值,是否显示%]
	 * @param args[8]
	 *            字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
	 * @param args[9]
	 *            自定义颜色[#33333,#...]
	 * @param args[10]
	 *            背景颜色
	 * @param args[11]
	 *            透明度
	 * @param args[12]
	 *            分离[分离序号,分离程度...]
	 * 
	 * 
	 * @param args
	 */
	private String pieChart3D(String args[]) {
		String chartInfo = "";
		String filename = "";
		try {

			/* 取得各参数 */
			int argsLength = args.length;
			// 图表标题[图表标题,显示位置(left,right,top,bottom)]
			String chartTitle =null;
			String titlePosition = null;			
			if (argsLength > 2) {
				String subParas[] = getSubPara(args[2]);
				if (subParas != null) {
					chartTitle = subParas.length > 0 ? subParas[0]:null;
					titlePosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}
			if(chartTitle == null) chartTitle="";
			
			// 宽度高度
			int width = 400;
			int height = 300;
			try {
				if (argsLength > 3 && args[3].trim().length() > 0) {
					width = Integer.parseInt(args[3]);
				}
			} catch (Exception e) {
			}
			try {
				if (argsLength > 4 && args[4].trim().length() > 0) {
					height = Integer.parseInt(args[4]);
				}
			} catch (Exception e) {
			}
			// 是否显示图例[是否显示,显示位置(left,right,top,bottom)]
			boolean showLegend = false;
			String  legendPosition = null;			
			if (argsLength > 5) {
				String subParas[] = getSubPara(args[5]);
				if (subParas != null) {
					showLegend = subParas.length > 0 && subParas[0].trim().toLowerCase().equals("true")? true:false;
					legendPosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}

			// 图表热点链接[图表热点链接,热点链接目标窗口]
			String mapURL = null;
			String target = null;
			if (argsLength > 6) {
				String subParas[] = getSubPara(args[6]);
				if (subParas != null) {
					mapURL = subParas.length > 0 ? subParas[0] : null;
					target = subParas.length > 1 ? subParas[1] : null;
					if(mapURL != null && mapURL.length()>=2 ){
						if((mapURL.startsWith("\"")&& mapURL.endsWith("\"")) || (mapURL.startsWith("'")&& mapURL.endsWith("'"))) {
							mapURL = mapURL.substring(1,mapURL.length()-1);
						}
					}
				}
			}
			// 数据标志[是否显示标题,是否显示数值,是否显示%]
			boolean showDataTitle = true;
			boolean showDataValue = true;
			boolean showDataPercent = false;
			if (argsLength > 7) {
				String subParas[] = getSubPara(args[7]);
				if (subParas != null) {
					showDataTitle = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("false") ? false
							: true;
					showDataValue = subParas.length > 1
							&& subParas[1].trim().toLowerCase().equals("false") ? false
							: true;
					showDataPercent = subParas.length > 2
							&& subParas[2].trim().toLowerCase().equals("true") ? true
							: false;
				}
			}
			// 字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
			// 数据标志字体
			String fontType = "宋体";
			int fontSize = 12;
			String fontColor = null;
			// 图表标题字体
			String chartFontType = "宋体";
			int chartFontSize = 18;
			String chartFontColor = null;
			// 图例字体
			String legendFontType = "宋体";
			int legendFontSize = 12;
			String legendFontColor = null;
			// 抗锯齿
			boolean isAntialiasing = false;
			if (argsLength > 8) {
				String subParas[] = getSubPara(args[8]);
				if (subParas != null) {
					fontType = subParas.length > 0
							&& subParas[0].trim().length() > 0 ? subParas[0]
							: "宋体";
					try {
						fontSize = subParas.length > 1 ? Integer
								.parseInt(subParas[1]) : 12;
					} catch (Exception e) {
					}
					fontColor = subParas.length > 2 ? subParas[2] : null;
					chartFontType = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: fontType;
					try {
						chartFontSize = subParas.length > 4 ? Integer
								.parseInt(subParas[4]) : 18;
					} catch (Exception e) {
					}
					chartFontColor = subParas.length > 5 ? subParas[5]
							: fontColor;

					legendFontType = subParas.length > 6
							&& subParas[6].trim().length() > 0 ? subParas[6]
							: fontType;
					try {
						legendFontSize = subParas.length > 7 ? Integer
								.parseInt(subParas[7]) : 12;
					} catch (Exception e) {
					}
					legendFontColor = subParas.length > 8 ? subParas[8]
							: fontColor;

					isAntialiasing = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 9
							&& subParas[9].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
				}
			}
			// 自定义颜色
			String customColors[] = null;
			customColors = getSubPara(argsLength > 9 ? args[9] : null);

			// 背景色
			String backColor = null;
			backColor = argsLength > 10 ? args[10] : null;
			// 透明度
			Float alpha = null;
			try {
				if (argsLength > 11 && args[11].trim().length() > 0) {
					alpha = Float.valueOf(args[11]);
				}
			} catch (Exception e) {
			}
			// 分离[分离序号,分离程度...]
			String explode[] = null;
			explode = getSubPara(argsLength > 12 ? args[12] : null);

			/* 取得各参数 end */

			// 取CachedRowSet 转换 为饼图数据集
			DefaultPieDataset pieDataset = rowSet2PieDataset(ds, args[1]);

			// 创建图表对象
			PiePlot3D plot = new PiePlot3D(pieDataset);
			setNoDataMessage(plot);

			// 创建图表的MAP热点链接
			if (mapURL != null && mapURL.trim().length() > 0) {
				plot.setURLGenerator(new StandardPieURLGenerator(mapURL));
			}

			// 数据标志[是否显示标题,是否显示数值,是否显示%]
			if (showDataTitle || showDataValue || showDataPercent) {
				String dataLabel = "";
				if (showDataTitle)
					dataLabel += " {0}";
				if (showDataValue)
					dataLabel += " = {1}";
				if (showDataPercent)
					dataLabel += " ({2})";
				dataLabel = dataLabel.trim();
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
						dataLabel));
			}
			// 字体
			Font plotLabelFont = new Font(fontType, Font.PLAIN, fontSize);
			Font chartLabelFont = new Font(chartFontType, Font.BOLD,
					chartFontSize);
			Font legendLabelFont = new Font(legendFontType, Font.PLAIN,
					legendFontSize);
			plot.setLabelFont(plotLabelFont);
			plot.setLabelPaint(StringUtils.getColor(fontColor, Color.black));

			// 自定义颜色
			if (customColors != null) {
				for (int i = 0; i < customColors.length; i++) {
					Color color = StringUtils.getColor(customColors[i]);
					if (color != null)
						plot.setSectionPaint(i, color);
				}
			}
			// 分离[分离序号,分离程度...]
			setExplodePercent(explode, plot);

			// 设置图表透明度
			if (alpha != null) {
				plot.setForegroundAlpha(alpha.floatValue());
			}

			// 生成图表
			JFreeChart chart = new JFreeChart(chartTitle, chartLabelFont, plot,
					showLegend);
			TextTitle title = new TextTitle(chartTitle);
			title.setPaint(StringUtils.getColor(chartFontColor, Color.BLACK));
			title.setFont(chartLabelFont);
			if(titlePosition != null ){
				if(titlePosition.equals("right"))
					title.setPosition(RectangleEdge.RIGHT);
				else if(titlePosition.equals("top"))
					title.setPosition(RectangleEdge.TOP);
				else if(titlePosition.equals("left"))
					title.setPosition(RectangleEdge.LEFT);
				else if(titlePosition.equals("bottom"))
					title.setPosition(RectangleEdge.BOTTOM);
			}
			
			chart.setTitle(title);
			if(chart.getLegend() != null){
				if(legendPosition != null ){
					if(legendPosition.equals("right"))
						chart.getLegend().setPosition(RectangleEdge.RIGHT);
					else if(legendPosition.equals("top"))
						chart.getLegend().setPosition(RectangleEdge.TOP);
					else if(legendPosition.equals("left"))
						chart.getLegend().setPosition(RectangleEdge.LEFT);
					else if(legendPosition.equals("bottom"))
							chart.getLegend().setPosition(RectangleEdge.BOTTOM);
				}
				chart.getLegend().setItemFont(legendLabelFont);
				chart.getLegend().setItemPaint(
						StringUtils.getColor(legendFontColor, Color.black));
			}
			// 设置图表的背景色
			chart.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			plot.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			if(chart.getLegend() != null)
				chart.getLegend().setBackgroundPaint(
						StringUtils.getColor(backColor, Color.white));

			// 抗锯齿功能
			if (isAntialiasing) {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}

			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 把图片写入临时目录并输出
			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, null);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			writeImageMap(pw, filename, info, false, target);
			// 图表信息,包括图片与热点
			chartInfo = report.getPara().get("contextPath")
					+ "/servlet/DisplayChart?filename=" + filename;
			chartInfo = " <img src=\"" + chartInfo + "\" width=" + width
					+ " height=" + height + " border=0 usemap=\"#" + filename
					+ "\">";
			chartInfo = sw.getBuffer() + chartInfo;

		} catch (Exception e) {
			chartInfo = "异常:" + e.toString();
			log.error(e.getStackTrace());
		}

		return chartInfo;
	}

	/**
	 * 环形图
	 * 
	 * @param args[1]
	 *            记录集字段名
	 * @param args[2]
	 *            图表标题[图表标题,显示位置(left,right,top,bottom)]
	 * @param args[3]
	 *            宽度
	 * @param args[4]
	 *            高度
	 * @param args[5]
	 *            是否显示图例[是否显示,显示位置(left,right,top,bottom)]
	 * @param args[6]
	 *            图表热点链接[图表热点链接,热点链接目标窗口]
	 * @param args[7]
	 *            数据标志[是否显示标题,是否显示数值,是否显示%]
	 * @param args[8]
	 *            字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
	 * @param args[9]
	 *            自定义颜色[#33333,#...]
	 * @param args[10]
	 *            背景颜色
	 * @param args[11]
	 *            圆环椭圆度
	 * @param args[12]
	 *            分离[分离序号,分离程度...]
	 * 
	 * 
	 * @param args
	 */
	private String ringChart(String args[]) {
		String chartInfo = "";
		String filename = "";
		try {

			/* 取得各参数 */
			int argsLength = args.length;
			// 图表标题[图表标题,显示位置(left,right,top,bottom)]
			String chartTitle =null;
			String titlePosition = null;			
			if (argsLength > 2) {
				String subParas[] = getSubPara(args[2]);
				if (subParas != null) {
					chartTitle = subParas.length > 0 ? subParas[0]:null;
					titlePosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}
			if(chartTitle == null) chartTitle="";
			// 宽度高度
			int width = 400;
			int height = 300;
			try {
				if (argsLength > 3 && args[3].trim().length() > 0) {
					width = Integer.parseInt(args[3]);
				}
			} catch (Exception e) {
			}
			try {
				if (argsLength > 4 && args[4].trim().length() > 0) {
					height = Integer.parseInt(args[4]);
				}
			} catch (Exception e) {
			}
			// 是否显示图例[是否显示,显示位置(left,right,top,bottom)]
			boolean showLegend = false;
			String  legendPosition = null;			
			if (argsLength > 5) {
				String subParas[] = getSubPara(args[5]);
				if (subParas != null) {
					showLegend = subParas.length > 0 && subParas[0].trim().toLowerCase().equals("true")? true:false;
					legendPosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}

			// 图表热点链接[图表热点链接,热点链接目标窗口]
			String mapURL = null;
			String target = null;
			if (argsLength > 6) {
				String subParas[] = getSubPara(args[6]);
				if (subParas != null) {
					mapURL = subParas.length > 0 ? subParas[0] : null;
					target = subParas.length > 1 ? subParas[1] : null;
					if(mapURL != null && mapURL.length()>=2 ){
						if((mapURL.startsWith("\"")&& mapURL.endsWith("\"")) || (mapURL.startsWith("'")&& mapURL.endsWith("'"))) {
							mapURL = mapURL.substring(1,mapURL.length()-1);
						}
					}
				}
			}
			// 数据标志[是否显示标题,是否显示数值,是否显示%]
			boolean showDataTitle = true;
			boolean showDataValue = true;
			boolean showDataPercent = false;
			if (argsLength > 7) {
				String subParas[] = getSubPara(args[7]);
				if (subParas != null) {
					showDataTitle = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("false") ? false
							: true;
					showDataValue = subParas.length > 1
							&& subParas[1].trim().toLowerCase().equals("false") ? false
							: true;
					showDataPercent = subParas.length > 2
							&& subParas[2].trim().toLowerCase().equals("true") ? true
							: false;
				}
			}
			// 字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
			// 数据标志字体
			String fontType = "宋体";
			int fontSize = 12;
			String fontColor = null;
			// 图表标题字体
			String chartFontType = "宋体";
			int chartFontSize = 18;
			String chartFontColor = null;
			// 图例字体
			String legendFontType = "宋体";
			int legendFontSize = 12;
			String legendFontColor = null;
			// 抗锯齿
			boolean isAntialiasing = false;
			if (argsLength > 8) {
				String subParas[] = getSubPara(args[8]);
				if (subParas != null) {
					fontType = subParas.length > 0
							&& subParas[0].trim().length() > 0 ? subParas[0]
							: "宋体";
					try {
						fontSize = subParas.length > 1 ? Integer
								.parseInt(subParas[1]) : 12;
					} catch (Exception e) {
					}
					fontColor = subParas.length > 2 ? subParas[2] : null;
					chartFontType = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: fontType;
					try {
						chartFontSize = subParas.length > 4 ? Integer
								.parseInt(subParas[4]) : 18;
					} catch (Exception e) {
					}
					chartFontColor = subParas.length > 5 ? subParas[5]
							: fontColor;

					legendFontType = subParas.length > 6
							&& subParas[6].trim().length() > 0 ? subParas[6]
							: fontType;
					try {
						legendFontSize = subParas.length > 7 ? Integer
								.parseInt(subParas[7]) : 12;
					} catch (Exception e) {
					}
					legendFontColor = subParas.length > 8 ? subParas[8]
							: fontColor;

					isAntialiasing = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 9
							&& subParas[9].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
				}
			}
			// 自定义颜色
			String customColors[] = null;
			customColors = getSubPara(argsLength > 9 ? args[9] : null);

			// 背景色
			String backColor = null;
			backColor = argsLength > 10 ? args[10] : null;
			// 圆环椭圆度
			Float gap = null;
			try {
				if (argsLength > 11 && args[11].trim().length() > 0) {
					gap = Float.valueOf(args[11]);
				}
			} catch (Exception e) {
			}
			// 分离[分离序号,分离程度...]
			String explode[] = null;
			explode = getSubPara(argsLength > 12 ? args[12] : null);

			/* 取得各参数 end */

			// 取CachedRowSet 转换 为饼图数据集
			DefaultPieDataset pieDataset = rowSet2PieDataset(ds, args[1]);

			// 创建图表对象
			RingPlot plot = new RingPlot(pieDataset);
			setNoDataMessage(plot);

			// 创建图表的MAP热点链接
			if (mapURL != null && mapURL.trim().length() > 0) {
				plot.setURLGenerator(new StandardPieURLGenerator(mapURL));
			}

			// 数据标志[是否显示标题,是否显示数值,是否显示%]
			if (showDataTitle || showDataValue || showDataPercent) {
				String dataLabel = "";
				if (showDataTitle)
					dataLabel += " {0}";
				if (showDataValue)
					dataLabel += " = {1}";
				if (showDataPercent)
					dataLabel += " ({2})";
				dataLabel = dataLabel.trim();
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
						dataLabel));
			}
			// 字体
			Font plotLabelFont = new Font(fontType, Font.PLAIN, fontSize);
			Font chartLabelFont = new Font(chartFontType, Font.BOLD,
					chartFontSize);
			Font legendLabelFont = new Font(legendFontType, Font.PLAIN,
					legendFontSize);
			plot.setLabelFont(plotLabelFont);
			plot.setLabelPaint(StringUtils.getColor(fontColor, Color.BLACK));

			// 自定义颜色
			if (customColors != null) {
				for (int i = 0; i < customColors.length; i++) {
					Color color = StringUtils.getColor(customColors[i]);
					if (color != null)
						plot.setSectionPaint(i, color);
				}
			}
			// 分离[分离序号,分离程度...]
			setExplodePercent(explode, plot);

			// 设置圆环椭圆度
			if (gap != null) {
				plot.setCircular(false);
				plot.setLabelGap(gap.floatValue());
			}

			// 生成图表
			JFreeChart chart = new JFreeChart(chartTitle, chartLabelFont, plot,
					showLegend);
			TextTitle title = new TextTitle(chartTitle);
			title.setPaint(StringUtils.getColor(chartFontColor, Color.black));
			title.setFont(chartLabelFont);
			if(titlePosition != null ){
				if(titlePosition.equals("right"))
					title.setPosition(RectangleEdge.RIGHT);
				else if(titlePosition.equals("top"))
					title.setPosition(RectangleEdge.TOP);
				else if(titlePosition.equals("left"))
					title.setPosition(RectangleEdge.LEFT);
				else if(titlePosition.equals("bottom"))
					title.setPosition(RectangleEdge.BOTTOM);
			}
			chart.setTitle(title);
			if(chart.getLegend() != null ){
				if(legendPosition != null ){
					if(legendPosition.equals("right"))
						chart.getLegend().setPosition(RectangleEdge.RIGHT);
					else if(legendPosition.equals("top"))
						chart.getLegend().setPosition(RectangleEdge.TOP);
					else if(legendPosition.equals("left"))
						chart.getLegend().setPosition(RectangleEdge.LEFT);
					else if(legendPosition.equals("bottom"))
							chart.getLegend().setPosition(RectangleEdge.BOTTOM);
				}
				chart.getLegend().setItemFont(legendLabelFont);
				chart.getLegend().setItemPaint(
						StringUtils.getColor(legendFontColor, Color.black));
			}

			// 设置图表的背景色
			chart.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			plot.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			if(chart.getLegend() != null)
				chart.getLegend().setBackgroundPaint(
						StringUtils.getColor(backColor, Color.white));

			// 抗锯齿功能
			if (isAntialiasing) {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}

			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 把图片写入临时目录并输出
			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, null);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			writeImageMap(pw, filename, info, false, target);
			// 图表信息,包括图片与热点
			chartInfo = report.getPara().get("contextPath")
					+ "/servlet/DisplayChart?filename=" + filename;
			chartInfo = " <img src=\"" + chartInfo + "\" width=" + width
					+ " height=" + height + " border=0 usemap=\"#" + filename
					+ "\">";
			chartInfo = sw.getBuffer() + chartInfo;

		} catch (Exception e) {
			chartInfo = "异常:" + e.toString();
			log.error(e.getStackTrace());
		}

		return chartInfo;
	}

	/**
	 * 柱图
	 * 
	 * @param args[1]
	 *            记录集字段名
	 * @param args[2]
	 *            图表标题[图表标题,显示位置(left,right,top,bottom)]
	 * @param args[3]
	 *            宽度
	 * @param args[4]
	 *            高度
	 * @param args[5]
	 *            是否显示图例[是否显示,显示位置(left,right,top,bottom)]
	 * @param args[6]
	 *            图表热点链接[图表热点链接,热点链接目标窗口]
	 * @param args[7]
	 *            X轴名称[X轴名称,标题倾斜度]
	 * @param args[8]
	 *            Y轴名称
	 * @param args[9]
	 *            XY轴垂直还是水平标志
	 * @param args[10]
	 *            透明度
	 * @param args[11]
	 *            柱子上是否显示数值[是否显示,是否柱子内部显示,显示角度,颜色]
	 * @param args[12]
	 *            是否算平均值[是否算平均值,字体,字号,字体颜色,线条颜色,格式 如 平均值:value]
	 * @param args[13]
	 *            字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
	 * @param args[14]
	 *            自定义颜色[#33333,#...]
	 * @param args[15]
	 *            背景颜色
	 * @param arga[16]
	 *            柱子显示方式,1正常显示,2包含的方式显示多柱,3是否高低颜色不同显示多柱
	 * 
	 * @return
	 */
	private String barChart(String args[], boolean is3D) {
		String chartInfo = "";
		String filename = "";
		try {

			/* 取得各参数 */
			int argsLength = args.length;
			// 图表标题[图表标题,显示位置(left,right,top,bottom)]
			String chartTitle =null;
			String titlePosition = null;			
			if (argsLength > 2) {
				String subParas[] = getSubPara(args[2]);
				if (subParas != null) {
					chartTitle = subParas.length > 0 ? subParas[0]:null;
					titlePosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}
			if(chartTitle == null) chartTitle="";
			// 宽度高度
			int width = 400;
			int height = 300;
			String widthStr = "400";
			String heightStr = "300";
			try {
				if (argsLength > 3 && args[3].trim().length() > 0) {
					widthStr = args[3];
					width = Integer.parseInt(args[3]);
				}
			} catch (Exception e) {
			}
			try {
				if (argsLength > 4 && args[4].trim().length() > 0) {
					heightStr = args[4];
					height = Integer.parseInt(args[4]);
				}
			} catch (Exception e) {
			}
			// 是否显示图例[是否显示,显示位置(left,right,top,bottom)]
			boolean showLegend = false;
			String  legendPosition = null;			
			if (argsLength > 5) {
				String subParas[] = getSubPara(args[5]);
				if (subParas != null) {
					showLegend = subParas.length > 0 && subParas[0].trim().toLowerCase().equals("true")? true:false;
					legendPosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}

			// 图表热点链接[图表热点链接,热点链接目标窗口]
			String mapURL = null;
			String target = null;
			if (argsLength > 6) {
				String subParas[] = getSubPara(args[6]);
				if (subParas != null) {
					mapURL = subParas.length > 0 ? subParas[0] : null;
					target = subParas.length > 1 ? subParas[1] : null;
					if(mapURL != null && mapURL.length()>=2 ){
						if((mapURL.startsWith("\"")&& mapURL.endsWith("\"")) || (mapURL.startsWith("'")&& mapURL.endsWith("'"))) {
							mapURL = mapURL.substring(1,mapURL.length()-1);
						}
					}
				}
			}
			// X轴名称
			String x_axisName = null;
			Double x_axisPosition = null;
			if (argsLength > 7) {
				String subParas[] = getSubPara(args[7]);
				if (subParas != null) {
					x_axisName = subParas.length > 0 ? subParas[0] : null;
					try {
						x_axisPosition = subParas.length > 1
								&& subParas[1].trim().length() > 0 ? new Double(
								subParas[1])
								: null;
					} catch (Exception e) {
					}
				}
			}
			// Y轴名称
			String y_axisName = argsLength > 8 ? args[8] : null;

			// XY轴垂直还是水平标志
			int orientation = 1;
			try {
				if (argsLength > 9 && args[9].trim().length() > 0) {
					orientation = Integer.parseInt(args[9]);
				}
			} catch (Exception e) {
			}
			// 透明度
			Float alpha = null;
			try {
				if (argsLength > 10 && args[10].trim().length() > 0) {
					alpha = Float.valueOf(args[10]);
				}
			} catch (Exception e) {
			}
			// 柱子上是否显示数值[是否显示,是否柱子内部显示,显示角度,颜色]
			boolean showDataTitle = true;
			boolean showDateInside = false;
			double showDataPosition = 0D;
			String showDataColor = null;
			if (argsLength > 11) {
				String subParas[] = getSubPara(args[11]);
				if (subParas != null) {
					showDataTitle = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("false") ? false
							: true;
					showDateInside = subParas.length > 1
							&& subParas[1].trim().toLowerCase().equals("true") ? true
							: false;
					try {
						showDataPosition = subParas.length > 2
								&& subParas[2].trim().length() > 0 ? Double
								.parseDouble(subParas[2]) : showDataPosition;
					} catch (Exception e) {
					}
					showDataColor = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: null;
				}
			}
			// 是否算平均值[是否算平均值,字体,字号,字体颜色,线条颜色,格式 如 平均值:value]
			boolean isSumAvgValue = false;
			String avgFontType = "宋体";
			int avgFontSize = 12;
			String avgFontColor = null;
			String avgLineColor = null;
			String avgFormat = null;
			if (argsLength > 12) {
				String subParas[] = getSubPara(args[12]);
				if (subParas != null) {
					isSumAvgValue = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("true") ? true
							: false;
					avgFontType = subParas.length > 1
							&& subParas[1].trim().length() > 0 ? subParas[1]
							: "宋体";
					try {
						avgFontSize = subParas.length > 2 ? Integer
								.parseInt(subParas[2]) : 12;
					} catch (Exception e) {
					}
					avgFontColor = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: null;
					avgLineColor = subParas.length > 4
							&& subParas[4].trim().length() > 0 ? subParas[4]
							: null;
					avgFormat = subParas.length > 5
							&& subParas[5].trim().length() > 0 ? subParas[5]
							: null;
				}
			}
			// 字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
			// 数据标志字体
			String fontType = "宋体";
			int fontSize = 12;
			String fontColor = null;
			// 图表标题字体
			String chartFontType = "宋体";
			int chartFontSize = 18;
			String chartFontColor = null;
			// 图例字体
			String legendFontType = "宋体";
			int legendFontSize = 12;
			String legendFontColor = null;
			// 抗锯齿
			boolean isAntialiasing = false;
			if (argsLength > 13) {
				String subParas[] = getSubPara(args[13]);
				if (subParas != null) {
					fontType = subParas.length > 0
							&& subParas[0].trim().length() > 0 ? subParas[0]
							: "宋体";
					try {
						fontSize = subParas.length > 1 ? Integer
								.parseInt(subParas[1]) : 12;
					} catch (Exception e) {
					}
					fontColor = subParas.length > 2 ? subParas[2] : null;
					chartFontType = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: fontType;
					try {
						chartFontSize = subParas.length > 4 ? Integer
								.parseInt(subParas[4]) : 18;
					} catch (Exception e) {
					}
					chartFontColor = subParas.length > 5 ? subParas[5]
							: fontColor;

					legendFontType = subParas.length > 6
							&& subParas[6].trim().length() > 0 ? subParas[6]
							: fontType;
					try {
						legendFontSize = subParas.length > 7 ? Integer
								.parseInt(subParas[7]) : 12;
					} catch (Exception e) {
					}
					legendFontColor = subParas.length > 8 ? subParas[8]
							: fontColor;

					isAntialiasing = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 9
							&& subParas[9].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
				}
			}
			// 自定义颜色
			String customColors[] = null;
			customColors = getSubPara(argsLength > 14 ? args[14] : null);

			// 背景色
			String backColor = null;
			backColor = argsLength > 15 ? args[15] : null;
			// 柱子显示方式,1正常显示,2包含的方式显示多柱,3是否高低颜色不同显示多柱
			String barType = null;
			barType = argsLength > 16 ? args[16] : null;

			/* 取得各参数 end */
			DefaultCategoryDataset defaultcategorydataset = rowSet2CategoryDataset(
					 para[1], isSumAvgValue);

			// X轴名称
			CategoryAxis categoryAxis = null;
			if (is3D)
				categoryAxis = new CategoryAxis3D(x_axisName);
			else
				categoryAxis = new CategoryAxis(x_axisName);
			// Y轴名称
			ValueAxis valueAxis = null;
			if (is3D){
				valueAxis = new NumberAxis3D(y_axisName);
				//数据轴上（右）边距,比率值
				valueAxis.setUpperMargin(0.1);
			}
			else
				valueAxis = new NumberAxis(y_axisName);
			CategoryItemRenderer renderer = null;
			// 自定义颜色[#33333,#...]
			if (colCounts < 3) {
				Paint apaint[] = null;
				if (customColors != null && customColors.length > 0) {
					apaint = createPaint(customColors);
				} else {
					apaint = createPaint();
				}
				if (is3D)
					renderer = new CustomBarRenderer3D(apaint);
				else
					renderer = new CustomBarRenderer(apaint);
			} else {
				if (customColors != null && customColors.length > 0) {
					Paint apaint[] = createPaint(customColors);
					if (is3D)
						renderer = new CustomBarRenderer3D(apaint, true);
					else
						renderer = new CustomBarRenderer(apaint, true);
				} else {
					// 柱子显示方式,1正常显示,2包含的方式显示多柱,3是否高低颜色不同显示多柱
					if (barType != null && barType.equals("2")) {
						renderer = new LayeredBarRenderer();
						((LayeredBarRenderer) renderer)
								.setDrawBarOutline(false);
					} else if (barType != null && barType.equals("3")) {
						if (is3D) {
							renderer = new StackedBarRenderer3D();
							((StackedBarRenderer3D) renderer)
									.setDrawBarOutline(false);
							((StackedBarRenderer3D) renderer)
									.setItemLabelsVisible(true);
							((StackedBarRenderer3D) renderer)
									.setSeriesItemLabelGenerator(
											0,
											new StandardCategoryItemLabelGenerator());
						} else {
							renderer = new StackedBarRenderer();
							((StackedBarRenderer) renderer)
									.setDrawBarOutline(false);
							((StackedBarRenderer) renderer)
									.setItemLabelsVisible(true);
							((StackedBarRenderer) renderer)
									.setSeriesItemLabelGenerator(
											0,
											new StandardCategoryItemLabelGenerator());
						}
					} else {
						if (is3D)
							renderer = new BarRenderer3D();
						else
							renderer = new BarRenderer();
					}
				}
			}
			if (orientation == 0) {
				ItemLabelPosition position1 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
				renderer.setPositiveItemLabelPosition(position1);
				ItemLabelPosition position2 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER_RIGHT);
				renderer.setNegativeItemLabelPosition(position2);
			} else {
				ItemLabelPosition position1 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
				renderer.setPositiveItemLabelPosition(position1);
				ItemLabelPosition position2 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
				renderer.setNegativeItemLabelPosition(position2);
			}
			if(colCounts == 2)
				renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
						"{1} = {2}", NumberFormat.getInstance()));
			else 
				renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());


			CategoryPlot plot = new CategoryPlot(defaultcategorydataset,
					categoryAxis, valueAxis, renderer);
			if (orientation == 0) {
				plot.setOrientation(PlotOrientation.HORIZONTAL);
			} else {
				plot.setOrientation(PlotOrientation.VERTICAL);
			}

			JFreeChart chart = new JFreeChart(chartTitle,
					JFreeChart.DEFAULT_TITLE_FONT, plot, showLegend);
			setNoDataMessage(plot);

			// 创建图表的MAP热点链接
			BarRenderer barrenderer = (BarRenderer) plot.getRenderer();
			if (mapURL != null && mapURL.trim().length() > 0) {
				barrenderer
						.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
								mapURL));
			}
			// 设置图表透明度
			if (alpha != null) {
				plot.setForegroundAlpha(alpha.floatValue());
			}
			// 柱子上是否显示数值[是否显示,是否柱子内部显示,显示角度,颜色]
			if (showDataTitle) {
				barrenderer.setItemLabelsVisible(true);
				if(is3D){
					//数据标签的与数据点的偏移
					barrenderer.setItemLabelAnchorOffset(15);					
					barrenderer
							.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("  {2}", NumberFormat.getInstance()));
				} else {
					barrenderer
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				}
				
				
				ItemLabelPosition itemLabelPosition = null;
				if (showDateInside) {
					if ((showDataPosition >= 1.5 && showDataPosition <= 1.6)
							|| (showDataPosition >= -1.6 && showDataPosition <= -1.5)) {
						itemLabelPosition = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.CENTER_RIGHT,
								TextAnchor.CENTER_RIGHT, showDataPosition);
						ItemLabelPosition itemlabelposition1 = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.CENTER_RIGHT,
								TextAnchor.CENTER_RIGHT, showDataPosition);
						barrenderer
								.setPositiveItemLabelPositionFallback(itemlabelposition1);
					} else {
						itemLabelPosition = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.TOP_CENTER, TextAnchor.CENTER,
								showDataPosition);
						ItemLabelPosition itemlabelposition1 = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.TOP_CENTER, TextAnchor.CENTER,
								showDataPosition);
						barrenderer
								.setPositiveItemLabelPositionFallback(itemlabelposition1);
					}

				} else {					
					itemLabelPosition = new ItemLabelPosition(
							ItemLabelAnchor.OUTSIDE12,
							TextAnchor.HALF_ASCENT_CENTER, TextAnchor.HALF_ASCENT_CENTER,
							showDataPosition);

				}

				barrenderer.setPositiveItemLabelPosition(itemLabelPosition);
				barrenderer.setItemLabelPaint(StringUtils.getColor(showDataColor,
						Color.BLACK));
				
			}

			// 字体
			Font plotLabelFont = new Font(fontType, Font.PLAIN, fontSize);
			Font chartLabelFont = new Font(chartFontType, Font.BOLD,
					chartFontSize);
			Font legendLabelFont = new Font(legendFontType, Font.PLAIN,
					legendFontSize);
			// 设置图表标题字体
			TextTitle title = new TextTitle(chartTitle);
			title.setPaint(StringUtils.getColor(chartFontColor, Color.BLACK));
			title.setFont(chartLabelFont);
			if(titlePosition != null ){
				if(titlePosition.equals("right"))
					title.setPosition(RectangleEdge.RIGHT);
				else if(titlePosition.equals("top"))
					title.setPosition(RectangleEdge.TOP);
				else if(titlePosition.equals("left"))
					title.setPosition(RectangleEdge.LEFT);
				else if(titlePosition.equals("bottom"))
					title.setPosition(RectangleEdge.BOTTOM);
			}
			chart.setTitle(title);
			// 设置X轴Y轴字体
			NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
			numberaxis
					.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			numberaxis.setLabelFont(plotLabelFont);
			numberaxis.setTickLabelFont(plotLabelFont);
			numberaxis.setTickMarkPaint(StringUtils.getColor(fontColor, Color.black));
			numberaxis.setLabelPaint(StringUtils.getColor(fontColor, Color.black));
			numberaxis.setTickLabelPaint(StringUtils.getColor(fontColor, Color.black));
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(plotLabelFont);
			domainAxis.setTickLabelFont(plotLabelFont);
			domainAxis.setTickMarkPaint(StringUtils.getColor(fontColor, Color.black));
			domainAxis.setLabelPaint(StringUtils.getColor(fontColor, Color.black));
			domainAxis.setTickLabelPaint(StringUtils.getColor(fontColor, Color.black));
			// 设置图例的字体
			if (showLegend) {
				if(legendPosition != null ){
					if(legendPosition.equals("right"))
						chart.getLegend().setPosition(RectangleEdge.RIGHT);
					else if(legendPosition.equals("top"))
						chart.getLegend().setPosition(RectangleEdge.TOP);
					else if(legendPosition.equals("left"))
						chart.getLegend().setPosition(RectangleEdge.LEFT);
					else if(legendPosition.equals("bottom"))
							chart.getLegend().setPosition(RectangleEdge.BOTTOM);
				}
				chart.getLegend().setItemFont(legendLabelFont);
				chart.getLegend().setItemPaint(
						StringUtils.getColor(legendFontColor, Color.BLACK));
			}
			// 设置标题倾斜度，用来解决标题过长变成...的问题，但是字体变得相对难看
			if (x_axisPosition != null) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions
						.createDownRotationLabelPositions(x_axisPosition
								.doubleValue()));
			}

			// 设置图表的背景色
			chart.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			plot.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			if(chart.getLegend() != null)
				chart.getLegend().setBackgroundPaint(
						StringUtils.getColor(backColor, Color.white));
			// 抗锯齿功能
			if (isAntialiasing) {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}

			// 是否显示平均值
			if (isSumAvgValue && avgValue != null) {
				 
				ValueMarker valuemarker = createValueMarker(avgValue,
						avgFontType, avgFontSize, avgFontColor, avgLineColor,
						avgFormat);
				if(is3D)
					plot.addRangeMarker(valuemarker,Layer.BACKGROUND);
				else 
					plot.addRangeMarker(valuemarker,Layer.FOREGROUND);
			}

			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 把图片写入临时目录并输出
			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, null);

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			writeImageMap(pw, filename, info, false, target);

			chartInfo = report.getPara().get("contextPath")
					+ "/servlet/DisplayChart?filename=" + filename;
			chartInfo = " <img src=\"" + chartInfo + "\" width=\"" + widthStr
					+ "\" height=\"" + heightStr + "\" border=0 usemap=\"#" + filename
					+ "\">";
			chartInfo = sw.getBuffer() + chartInfo;

		} catch (Exception e) {
			chartInfo = "异常:" + e.toString();
			log.error(e.getStackTrace());
		}

		return chartInfo;
	}

	/**
	 * 线图
	 * 
	 * @param args[1]
	 *            记录集字段名
	 * @param args[2]
	 *            图表标题[图表标题,显示位置(left,right,top,bottom)]
	 * @param args[3]
	 *            宽度
	 * @param args[4]
	 *            高度
	 * @param args[5]
	 *            是否显示图例[是否显示,显示位置(left,right,top,bottom)]
	 * @param args[6]
	 *            图表热点链接[图表热点链接,热点链接目标窗口]
	 * @param args[7]
	 *            X轴名称[X轴名称,标题倾斜度]
	 * @param args[8]
	 *            Y轴名称
	 * @param args[9]
	 *            XY轴垂直还是水平标志
	 * @param args[10]
	 *            透明度
	 * @param args[11]
	 *            折线结点[是否显示数值,颜色,是否显示节点,节点是否颜色填充]
	 * @param args[12]
	 *            是否算平均值[是否算平均值,字体,字号,字体颜色,线条颜色,格式 如 平均值:value]
	 * @param args[13]
	 *            字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
	 * @param args[14]
	 *            背景颜色
	 * 
	 * @return
	 */
	private String lineChart(String args[], boolean is3D) {
		String chartInfo = "";
		String filename = "";
		try {

			/* 取得各参数 */
			int argsLength = args.length;
			// 图表标题[图表标题,显示位置(left,right,top,bottom)]
			String chartTitle =null;
			String titlePosition = null;			
			if (argsLength > 2) {
				String subParas[] = getSubPara(args[2]);
				if (subParas != null) {
					chartTitle = subParas.length > 0 ? subParas[0]:null;
					titlePosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}
			if(chartTitle == null) chartTitle="";
			// 宽度高度
			int width = 400;
			int height = 300;
			String widthStr = "400";
			String heightStr = "300";
			try {
				if (argsLength > 3 && args[3].trim().length() > 0) {
					widthStr = args[3];
					width = Integer.parseInt(args[3]);
				}
			} catch (Exception e) {
			}
			try {
				if (argsLength > 4 && args[4].trim().length() > 0) {
					heightStr = args[4];
					height = Integer.parseInt(args[4]);
				}
			} catch (Exception e) {
			}
			// 是否显示图例[是否显示,显示位置(left,right,top,bottom)]
			boolean showLegend = false;
			String  legendPosition = null;			
			if (argsLength > 5) {
				String subParas[] = getSubPara(args[5]);
				if (subParas != null) {
					showLegend = subParas.length > 0 && subParas[0].trim().toLowerCase().equals("true")? true:false;
					legendPosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}

			// 图表热点链接[图表热点链接,热点链接目标窗口]
			String mapURL = null;
			String target = null;
			if (argsLength > 6) {
				String subParas[] = getSubPara(args[6]);
				if (subParas != null) {
					mapURL = subParas.length > 0 ? subParas[0] : null;
					target = subParas.length > 1 ? subParas[1] : null;
					if(mapURL != null && mapURL.length()>=2 ){
						if((mapURL.startsWith("\"")&& mapURL.endsWith("\"")) || (mapURL.startsWith("'")&& mapURL.endsWith("'"))) {
							mapURL = mapURL.substring(1,mapURL.length()-1);
						}
					}
				}
			}
			// X轴名称
			String x_axisName = null;
			Double x_axisPosition = null;
			if (argsLength > 7) {
				String subParas[] = getSubPara(args[7]);
				if (subParas != null) {
					x_axisName = subParas.length > 0 ? subParas[0] : null;
					try {
						x_axisPosition = subParas.length > 1
								&& subParas[1].trim().length() > 0 ? new Double(
								subParas[1])
								: null;
					} catch (Exception e) {
					}
				}
			}
			// Y轴名称
			String y_axisName = argsLength > 8 ? args[8] : null;

			// XY轴垂直还是水平标志
			int orientation = 1;
			try {
				if (argsLength > 9 && args[9].trim().length() > 0) {
					orientation = Integer.parseInt(args[9]);
				}
			} catch (Exception e) {
			}
			// 透明度
			Float alpha = null;
			try {
				if (argsLength > 10 && args[10].trim().length() > 0) {
					alpha = Float.valueOf(args[10]);
				}
			} catch (Exception e) {
			}
			// 折线结点[是否显示数值,颜色,是否显示节点,节点是否颜色填充]
			boolean shapesVisible = true;
			boolean shapesFilled = true;
			boolean showDataTitle = true;
			String showDataColor = null;
			if (argsLength > 11) {
				String subParas[] = getSubPara(args[11]);
				if (subParas != null) {
					showDataTitle = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("false") ? false
							: true;
					showDataColor = subParas.length > 1
							&& subParas[1].trim().length() > 0 ? subParas[1]
							: null;
					shapesVisible = subParas.length > 2
							&& subParas[2].trim().toLowerCase().equals("false") ? false
							: true;
					shapesFilled = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("false") ? false
							: true;

				}
			}
			// 是否算平均值[是否算平均值,字体,字号,字体颜色,线条颜色,格式 如 平均值:value]
			boolean isSumAvgValue = false;
			String avgFontType = "宋体";
			int avgFontSize = 12;
			String avgFontColor = null;
			String avgLineColor = null;
			String avgFormat = null;
			if (argsLength > 12) {
				String subParas[] = getSubPara(args[12]);
				if (subParas != null) {
					isSumAvgValue = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("true") ? true
							: false;
					avgFontType = subParas.length > 1
							&& subParas[1].trim().length() > 0 ? subParas[1]
							: "宋体";
					try {
						avgFontSize = subParas.length > 2 ? Integer
								.parseInt(subParas[2]) : 12;
					} catch (Exception e) {
					}
					avgFontColor = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: null;
					avgLineColor = subParas.length > 4
							&& subParas[4].trim().length() > 0 ? subParas[4]
							: null;
					avgFormat = subParas.length > 5
							&& subParas[5].trim().length() > 0 ? subParas[5]
							: null;
				}
			}
			// 字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
			// 数据标志字体
			String fontType = "宋体";
			int fontSize = 12;
			String fontColor = null;
			// 图表标题字体
			String chartFontType = "宋体";
			int chartFontSize = 18;
			String chartFontColor = null;
			// 图例字体
			String legendFontType = "宋体";
			int legendFontSize = 12;
			String legendFontColor = null;
			// 抗锯齿
			boolean isAntialiasing = false;
			if (argsLength > 13) {
				String subParas[] = getSubPara(args[13]);
				if (subParas != null) {
					fontType = subParas.length > 0
							&& subParas[0].trim().length() > 0 ? subParas[0]
							: "宋体";
					try {
						fontSize = subParas.length > 1 ? Integer
								.parseInt(subParas[1]) : 12;
					} catch (Exception e) {
					}
					fontColor = subParas.length > 2 ? subParas[2] : null;
					chartFontType = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: fontType;
					try {
						chartFontSize = subParas.length > 4 ? Integer
								.parseInt(subParas[4]) : 18;
					} catch (Exception e) {
					}
					chartFontColor = subParas.length > 5 ? subParas[5]
							: fontColor;

					legendFontType = subParas.length > 6
							&& subParas[6].trim().length() > 0 ? subParas[6]
							: fontType;
					try {
						legendFontSize = subParas.length > 7 ? Integer
								.parseInt(subParas[7]) : 12;
					} catch (Exception e) {
					}
					legendFontColor = subParas.length > 8 ? subParas[8]
							: fontColor;

					isAntialiasing = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 9
							&& subParas[9].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
				}
			}
			// 背景色
			String backColor = null;
			backColor = argsLength > 14 ? args[14] : null;

			/* 取得各参数 end */
			
			
			DefaultCategoryDataset defaultcategorydataset = rowSet2CategoryDataset(
					 para[1], isSumAvgValue);

			// X轴名称
			CategoryAxis categoryAxis = null;
			if (is3D)
				categoryAxis = new CategoryAxis3D(x_axisName);
			else
				categoryAxis = new CategoryAxis(x_axisName);
			// Y轴名称
			ValueAxis valueAxis = null;
			if (is3D)
				valueAxis = new NumberAxis3D(y_axisName);
			else
				valueAxis = new NumberAxis(y_axisName);
			
			CategoryItemRenderer renderer = null;
			if (is3D)
				renderer = new LineRenderer3D();
			else
				renderer = new LineAndShapeRenderer(true, false);
			
			if(colCounts == 2)
				renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
						"{1} = {2}", NumberFormat.getInstance()));
			else 
				renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

			CategoryPlot plot = new CategoryPlot(defaultcategorydataset,
					categoryAxis, valueAxis, renderer);
			if (orientation == 0) {
				plot.setOrientation(PlotOrientation.HORIZONTAL);
			} else {
				plot.setOrientation(PlotOrientation.VERTICAL);
			}

			JFreeChart chart = new JFreeChart(chartTitle,
					JFreeChart.DEFAULT_TITLE_FONT, plot, showLegend);
			setNoDataMessage(plot);

			// 创建图表的MAP热点链接
			if (mapURL != null && mapURL.trim().length() > 0) {
				renderer
						.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
								mapURL));
			}
			// 设置图表透明度
			if (alpha != null) {
				plot.setForegroundAlpha(alpha.floatValue());
			}
			// 折线结点[是否显示数值,颜色,是否显示节点,节点是否颜色填充]
			if (!is3D) {
				LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
						.getRenderer();
				lineandshaperenderer.setShapesVisible(shapesVisible);
				lineandshaperenderer.setShapesFilled(shapesFilled);
				lineandshaperenderer.setDrawOutlines(true);
				lineandshaperenderer.setStroke(new BasicStroke(1F));
			}
			if (showDataTitle) {
				renderer
						.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setItemLabelsVisible(true);
				renderer
						.setItemLabelPaint(StringUtils.getColor(showDataColor, Color.BLACK));
			}

			// 字体
			Font plotLabelFont = new Font(fontType, Font.PLAIN, fontSize);
			Font chartLabelFont = new Font(chartFontType, Font.BOLD,
					chartFontSize);
			Font legendLabelFont = new Font(legendFontType, Font.PLAIN,
					legendFontSize);
			// 设置图表标题字体
			TextTitle title = new TextTitle(chartTitle);
			title.setPaint(StringUtils.getColor(chartFontColor, Color.BLACK));
			title.setFont(chartLabelFont);
			if(titlePosition != null ){
				if(titlePosition.equals("right"))
					title.setPosition(RectangleEdge.RIGHT);
				else if(titlePosition.equals("top"))
					title.setPosition(RectangleEdge.TOP);
				else if(titlePosition.equals("left"))
					title.setPosition(RectangleEdge.LEFT);
				else if(titlePosition.equals("bottom"))
					title.setPosition(RectangleEdge.BOTTOM);
			}
			chart.setTitle(title);
			// 设置X轴Y轴字体
			NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
			numberaxis
					.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			numberaxis.setLabelFont(plotLabelFont);
			numberaxis.setTickLabelFont(plotLabelFont);
			numberaxis.setTickMarkPaint(StringUtils.getColor(fontColor, Color.black));
			numberaxis.setLabelPaint(StringUtils.getColor(fontColor, Color.black));
			numberaxis.setTickLabelPaint(StringUtils.getColor(fontColor, Color.black));
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(plotLabelFont);
			domainAxis.setTickLabelFont(plotLabelFont);
			domainAxis.setTickMarkPaint(StringUtils.getColor(fontColor, Color.black));
			domainAxis.setLabelPaint(StringUtils.getColor(fontColor, Color.black));
			domainAxis.setTickLabelPaint(StringUtils.getColor(fontColor, Color.black));
			// 设置图例的字体
			if (showLegend) {
				if(legendPosition != null ){
					if(legendPosition.equals("right"))
						chart.getLegend().setPosition(RectangleEdge.RIGHT);
					else if(legendPosition.equals("top"))
						chart.getLegend().setPosition(RectangleEdge.TOP);
					else if(legendPosition.equals("left"))
						chart.getLegend().setPosition(RectangleEdge.LEFT);
					else if(legendPosition.equals("bottom"))
							chart.getLegend().setPosition(RectangleEdge.BOTTOM);
				}
				chart.getLegend().setItemFont(legendLabelFont);
				chart.getLegend().setItemPaint(
						StringUtils.getColor(legendFontColor, Color.BLACK));
			}
			// 设置标题倾斜度，用来解决标题过长变成...的问题，但是字体变得相对难看
			if (x_axisPosition != null) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions
						.createDownRotationLabelPositions(x_axisPosition
								.doubleValue()));
			}

			// 设置图表的背景色
			chart.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			plot.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			if(chart.getLegend() != null)
				chart.getLegend().setBackgroundPaint(
						StringUtils.getColor(backColor, Color.white));
			// 抗锯齿功能
			if (isAntialiasing) {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}

			// 是否显示平均值
			if (isSumAvgValue && avgValue != null) {
				ValueMarker valuemarker = createValueMarker(avgValue,
						avgFontType, avgFontSize, avgFontColor, avgLineColor,
						avgFormat);
				if(is3D)
					plot.addRangeMarker(valuemarker,Layer.BACKGROUND);
				else 
					plot.addRangeMarker(valuemarker,Layer.FOREGROUND);
			}

			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 把图片写入临时目录并输出
			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, null);

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			writeImageMap(pw, filename, info, false, target);

			chartInfo = report.getPara().get("contextPath")
					+ "/servlet/DisplayChart?filename=" + filename;
			chartInfo = " <img src=\"" + chartInfo + "\" width=\"" + widthStr
					+ "\" height=\"" + heightStr + "\" border=0 usemap=\"#" + filename
					+ "\">";
			chartInfo = sw.getBuffer() + chartInfo;

		} catch (Exception e) {
			chartInfo = "异常:" + e.toString();
			log.error(e.getStackTrace());
		}

		return chartInfo;
	}
	
	
	/**
	 * 复合报表
	 * 
	 * @param args[1]
	 *            *柱图记录集字段名,用[]括起来,各个字段用,号分开
	 * @param args[2]
	 *            *线图记录集字段名,用[]括起来,各个字段用,号分开
	 * @param args[3]
	 *            图表标题[图表标题,显示位置(left,right,top,bottom)]
	 * @param args[4]
	 *            宽度
	 * @param args[5]
	 *            高度
	 * @param args[6]
	 *            是否显示图例[是否显示,显示位置(left,right,top,bottom)]
	 * @param args[7]
	 *            图表热点链接[图表热点链接,热点链接目标窗口,线图图表热点链接]
	 * @param args[8]
	 *            X轴名称[X轴名称,标题倾斜度]
	 * @param args[9]
	 *            Y轴名称[Y轴名称,右边Y轴名称,两个Y轴数据显示的偏差度(取值范围-1~1,0表示正常显示,1表示右边Y轴的数值都显示在左边Y轴数值上面,-1则相反)]
	 * @param args[10]
	 *            XY轴垂直还是水平标志
	 * @param args[11]
	 *            透明度
	 * @param args[12]
	 *            柱子上是否显示数值[是否显示,是否柱子内部显示,显示角度,颜色,线图是否显示数值,颜色,是否显示节点,节点是否颜色填充]
	 * @param args[13]
	 *            是否算平均值[是否算平均值,字体,字号,字体颜色,线条颜色,格式 如 平均值:value,是否算平均值2,字体,字号,字体颜色,线条颜色,格式 如 平均值:value]
	 * @param args[14]
	 *            字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
	 * @param args[15]
	 *            自定义颜色[#33333,#...]
	 * @param args[16]
	 *            背景颜色
	 * @param arga[17]
	 *            柱子显示方式,1正常显示,2包含的方式显示多柱,3是否高低颜色不同显示多柱
	 * 
	 * @return
	 */
	private String BarLineChart(String args[], boolean is3D,boolean is3D2) {
		String chartInfo = "";
		String filename = "";
		try {

			/* 取得各参数 */
			String rowset = args[1];
			String rowset2 = args[2];
			int argsLength = args.length;
			// 图表标题[图表标题,显示位置(left,right,top,bottom)]
			String chartTitle =null;
			String titlePosition = null;			
			if (argsLength > 3) {
				String subParas[] = getSubPara(args[3]);
				if (subParas != null) {
					chartTitle = subParas.length > 0 ? subParas[0]:null;
					titlePosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}
			if(chartTitle == null) chartTitle="";
			// 宽度高度
			int width = 400;
			int height = 300;
			try {
				if (argsLength > 4 && args[4].trim().length() > 0) {
					width = Integer.parseInt(args[4]);
				}
			} catch (Exception e) {
			}
			try {
				if (argsLength > 5 && args[5].trim().length() > 0) {
					height = Integer.parseInt(args[5]);
				}
			} catch (Exception e) {
			}
			// 是否显示图例[是否显示,显示位置(left,right,top,bottom)]
			boolean showLegend = false;
			String  legendPosition = null;			
			if (argsLength > 6) {
				String subParas[] = getSubPara(args[6]);
				if (subParas != null) {
					showLegend = subParas.length > 0 && subParas[0].trim().toLowerCase().equals("true")? true:false;
					legendPosition = subParas.length > 1 ? subParas[1].trim().toLowerCase() : null;
				}
			}

			// 图表热点链接[图表热点链接,热点链接目标窗口,线图图表热点链接]
			String mapURL = null;
			String target = null;
			String mapURL2 = null;
			if (argsLength > 7) {
				String subParas[] = getSubPara(args[7]);
				if (subParas != null) {
					mapURL = subParas.length > 0 ? subParas[0] : null;
					target = subParas.length > 1 ? subParas[1] : null;
					mapURL2 = subParas.length > 2 ? subParas[2] : null;
					if(mapURL != null && mapURL.length()>=2 ){
						if((mapURL.startsWith("\"")&& mapURL.endsWith("\"")) || (mapURL.startsWith("'")&& mapURL.endsWith("'"))) {
							mapURL = mapURL.substring(1,mapURL.length()-1);
						}
					}
					if(mapURL2 != null && mapURL2.length()>=2 ){
						if((mapURL2.startsWith("\"")&& mapURL2.endsWith("\"")) || (mapURL2.startsWith("'")&& mapURL2.endsWith("'"))) {
							mapURL2 = mapURL2.substring(1,mapURL2.length()-1);
						}
					}
				}
			}
			// X轴名称
			String x_axisName = null;
			Double x_axisPosition = null;
			if (argsLength > 8) {
				String subParas[] = getSubPara(args[8]);
				if (subParas != null) {
					x_axisName = subParas.length > 0 ? subParas[0] : null;
					try {
						x_axisPosition = subParas.length > 1
								&& subParas[1].trim().length() > 0 ? new Double(
								subParas[1])
								: null;
					} catch (Exception e) {
					}
				}
			}
			// Y轴名称[Y轴名称,右边Y轴名称,两个Y轴数据显示的偏差度(取值范围-1~1,0表示正常显示,1表示右边Y轴的数值都显示在左边Y轴数值上面,-1则相反)]
			String y_axisName = null;
			String y_axisName2 = null;
			double y_axisShowWarp = 0D;
			if (argsLength > 9) { 
				String subParas[] = getSubPara(args[9]);
				if (subParas != null) {
					y_axisName = subParas.length > 0 ? subParas[0] : null;
					y_axisName2 = subParas.length > 1 ? subParas[1] : null;
					try {
						y_axisShowWarp = subParas.length > 2
								&& subParas[2].trim().length() > 0 ? Double.parseDouble(
								subParas[2])
								: 0D;
					} catch (Exception e) {
					}
				}
			}
//			System.out.println("y_axisShowWarp:"+y_axisShowWarp);
			// XY轴垂直还是水平标志
			int orientation = 1;
			try {
				if (argsLength > 10 && args[10].trim().length() > 0) {
					orientation = Integer.parseInt(args[10]);
				}
			} catch (Exception e) {
			}
			// 透明度
			Float alpha = null;
			try {
				if (argsLength > 11 && args[11].trim().length() > 0) {
					alpha = Float.valueOf(args[11]);
				}
			} catch (Exception e) {
			}
			// 柱子上是否显示数值[是否显示,是否柱子内部显示,显示角度,颜色,线图是否显示数值,颜色,是否显示节点,节点是否颜色填充]
			boolean showDataTitle = true;
			boolean showDateInside = false;
			double showDataPosition = 0D;
			String showDataColor = null;
			boolean showDataTitle2 = true;
			String showDataColor2 = null;
			boolean shapesVisible2 = true;
			boolean shapesFilled2 = true;
			if (argsLength > 12) {
				String subParas[] = getSubPara(args[12]);
				if (subParas != null) {
					showDataTitle = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("false") ? false
							: true;
					showDateInside = subParas.length > 1
							&& subParas[1].trim().toLowerCase().equals("true") ? true
							: false;
					try {
						showDataPosition = subParas.length > 2
								&& subParas[2].trim().length() > 0 ? Double
								.parseDouble(subParas[2]) : showDataPosition;
					} catch (Exception e) {
					}
					showDataColor = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: null;
							
					showDataTitle2 = subParas.length > 4
							&& subParas[4].trim().toLowerCase().equals("false") ? false
							: true;
					showDataColor2 = subParas.length > 5
							&& subParas[5].trim().length() > 0 ? subParas[5]
							: null;
					shapesVisible2 = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("false") ? false
							: true;
					shapesFilled2 = subParas.length > 7
							&& subParas[7].trim().toLowerCase().equals("false") ? false
							: true;
				}
			}
			// 是否算平均值[是否算平均值,字体,字号,字体颜色,线条颜色,格式 如 平均值:value,是否算平均值2,字体,字号,字体颜色,线条颜色,格式 如 平均值:value]
			boolean isSumAvgValue = false;
			String avgFontType = "宋体";
			int avgFontSize = 12;
			String avgFontColor = null;
			String avgLineColor = null;
			String avgFormat = null;
			
			boolean isSumAvgValue2 = false;
			String avgFontType2 = "宋体";
			int avgFontSize2 = 12;
			String avgFontColor2 = null;
			String avgLineColor2 = null;
			String avgFormat2 = null;
			if (argsLength > 13) {
				String subParas[] = getSubPara(args[13]);
				if (subParas != null) {
					isSumAvgValue = subParas.length > 0
							&& subParas[0].trim().toLowerCase().equals("true") ? true
							: false;
					avgFontType = subParas.length > 1
							&& subParas[1].trim().length() > 0 ? subParas[1]
							: "宋体";
					try {
						avgFontSize = subParas.length > 2 ? Integer
								.parseInt(subParas[2]) : 12;
					} catch (Exception e) {
					}
					avgFontColor = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: null;
					avgLineColor = subParas.length > 4
							&& subParas[4].trim().length() > 0 ? subParas[4]
							: null;
					avgFormat = subParas.length > 5
							&& subParas[5].trim().length() > 0 ? subParas[5]
							: null;
							
					isSumAvgValue2 = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: false;
					avgFontType2 = subParas.length > 7
							&& subParas[7].trim().length() > 0 ? subParas[7]
							: "宋体";
					try {
						avgFontSize2 = subParas.length > 8 ? Integer
								.parseInt(subParas[8]) : 12;
					} catch (Exception e) {
					}
					avgFontColor2 = subParas.length > 9
							&& subParas[9].trim().length() > 0 ? subParas[9]
							: null;
					avgLineColor2 = subParas.length > 10
							&& subParas[10].trim().length() > 0 ? subParas[10]
							: null;
					avgFormat2 = subParas.length > 11
							&& subParas[11].trim().length() > 0 ? subParas[11]
							: null;
				}
			}
			// 字体[数据标志字体,字号,颜色,图表标题字体,字号,颜色,图例字体,字号,颜色,是否抗锯齿]
			// 数据标志字体
			String fontType = "宋体";
			int fontSize = 12;
			String fontColor = null;
			// 图表标题字体
			String chartFontType = "宋体";
			int chartFontSize = 18;
			String chartFontColor = null;
			// 图例字体
			String legendFontType = "宋体";
			int legendFontSize = 12;
			String legendFontColor = null;
			// 抗锯齿
			boolean isAntialiasing = false;
			if (argsLength > 14) {
				String subParas[] = getSubPara(args[14]);
				if (subParas != null) {
					fontType = subParas.length > 0
							&& subParas[0].trim().length() > 0 ? subParas[0]
							: "宋体";
					try {
						fontSize = subParas.length > 1 ? Integer
								.parseInt(subParas[1]) : 12;
					} catch (Exception e) {
					}
					fontColor = subParas.length > 2 ? subParas[2] : null;
					chartFontType = subParas.length > 3
							&& subParas[3].trim().length() > 0 ? subParas[3]
							: fontType;
					try {
						chartFontSize = subParas.length > 4 ? Integer
								.parseInt(subParas[4]) : 18;
					} catch (Exception e) {
					}
					chartFontColor = subParas.length > 5 ? subParas[5]
							: fontColor;

					legendFontType = subParas.length > 6
							&& subParas[6].trim().length() > 0 ? subParas[6]
							: fontType;
					try {
						legendFontSize = subParas.length > 7 ? Integer
								.parseInt(subParas[7]) : 12;
					} catch (Exception e) {
					}
					legendFontColor = subParas.length > 8 ? subParas[8]
							: fontColor;

					isAntialiasing = subParas.length > 3
							&& subParas[3].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 6
							&& subParas[6].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
					isAntialiasing = subParas.length > 9
							&& subParas[9].trim().toLowerCase().equals("true") ? true
							: isAntialiasing;
				}
			}
			// 自定义颜色
			String customColors[] = null;
			customColors = getSubPara(argsLength > 15 ? args[15] : null);

			// 背景色
			String backColor = null;
			backColor = argsLength > 16 ? args[16] : null;
			// 柱子显示方式,1正常显示,2包含的方式显示多柱,3是否高低颜色不同显示多柱
			String barType = null;
			barType = argsLength > 17 ? args[17] : null;

			/* 取得各参数 end */
			
			int colCountsTmp1 ;//柱图记录集字段个数
			int colCountsTmp2 ;//线图记录集字段个数
			Double avgValueTmp1 = null;
			Double avgValueTmp2 = null;
			Double minValueTmp1 = null;
			Double maxValueTmp1 = null;
			Double minValueTmp2 = null;
			Double maxValueTmp2 = null;
			boolean isSumMinMax = false;
			if(y_axisShowWarp != 0){
				isSumMinMax = true;
			}
			DefaultCategoryDataset defaultcategorydataset = rowSet2CategoryDataset(
					 rowset, isSumAvgValue,isSumMinMax);
			avgValueTmp1 = avgValue;
			colCountsTmp1 = colCounts;
			if(y_axisShowWarp != 0){
				minValueTmp1 = minValue;
				maxValueTmp1 = maxValue;
			}
			DefaultCategoryDataset defaultcategorydataset2 = rowSet2CategoryDataset(
					 rowset2, isSumAvgValue2,isSumMinMax);
			avgValueTmp2 = avgValue;
			colCountsTmp2 = colCounts;
			if(y_axisShowWarp != 0){
				minValueTmp2 = minValue;
				maxValueTmp2 = maxValue;
			}

			// X轴名称
			CategoryAxis categoryAxis = null;
			if (is3D)
				categoryAxis = new CategoryAxis3D(x_axisName);
			else
				categoryAxis = new CategoryAxis(x_axisName);
			// Y轴名称
			ValueAxis valueAxis = null;
			ValueAxis valueAxis2 = null;
			if (is3D){
				valueAxis = new NumberAxis3D(y_axisName);
				valueAxis2 = new NumberAxis3D(y_axisName2);
				
			}
			else{
				valueAxis = new NumberAxis(y_axisName);
				valueAxis2 = new NumberAxis(y_axisName2);
			}
			//两个Y轴数据显示的偏差度(取值范围-1~1,0表示正常显示,1表示右边Y轴的数值都显示在左边Y轴数值上面,-1则相反)
			if(y_axisShowWarp != 0){
				if(minValueTmp1 != null && maxValueTmp1 != null && minValueTmp2 != null && maxValueTmp2 != null){
					if(y_axisShowWarp >0) {
						if(minValueTmp1.doubleValue() >0)
							valueAxis.setLowerBound(0);
						else
							valueAxis.setLowerBound(minValueTmp1.doubleValue());
						
						valueAxis.setUpperBound(maxValueTmp1.doubleValue() *(1+y_axisShowWarp));
						//valueAxis2.setLowerBound(maxValueTmp2.doubleValue()-(maxValueTmp2.doubleValue()-minValueTmp2.doubleValue())*(1+y_axisShowWarp));
						valueAxis2.setLowerBound(maxValueTmp2.doubleValue()*(0-y_axisShowWarp));
						//valueAxis2.setUpperBound(maxValueTmp2.doubleValue() + (maxValueTmp2.doubleValue()-minValueTmp2.doubleValue())*0.1);
						valueAxis2.setUpperBound(maxValueTmp2.doubleValue() * 1.1);
					} else {
						if(minValueTmp1.doubleValue() >0)
							valueAxis.setLowerBound(0);
						else
							valueAxis.setLowerBound(minValueTmp1.doubleValue());
						valueAxis.setUpperBound(maxValueTmp1.doubleValue() * 1.1);
						
						if(minValueTmp2.doubleValue() >0)
							valueAxis2.setLowerBound(0);
						else
							valueAxis2.setLowerBound(minValueTmp2.doubleValue());
						valueAxis2.setUpperBound(maxValueTmp2.doubleValue() *(1-y_axisShowWarp));
					}
					
				}
			}
			
			CategoryItemRenderer renderer = null;
			CategoryItemRenderer renderer2 = null;
			// 自定义颜色[#33333,#...]
			if (is3D2) {
				renderer2 = new LineRenderer3D();
			} else {
				renderer2 = new LineAndShapeRenderer(true, false);
			}
			if(colCountsTmp2 == 2)
				renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
						"{1} = {2}", NumberFormat.getInstance()));
			else 
				renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

			if (colCountsTmp1  < 3) {
				Paint apaint[] = null;
				if (customColors != null && customColors.length > 0) {
					apaint = createPaint(customColors);
				} else {
					apaint = createPaint();
				}
				if (is3D) {
					renderer = new CustomBarRenderer3D(apaint);
				}
				else{
					renderer = new CustomBarRenderer(apaint);
				}
			} else {
				if (customColors != null && customColors.length > 0) {
					Paint apaint[] = createPaint(customColors);
					if (is3D){
						renderer = new CustomBarRenderer3D(apaint, true);
					}
					else{
						renderer = new CustomBarRenderer(apaint, true);
					}
				} else {
					// 柱子显示方式,1正常显示,2包含的方式显示多柱,3是否高低颜色不同显示多柱
					if (barType != null && barType.equals("2")) {
						renderer = new LayeredBarRenderer();
						((LayeredBarRenderer) renderer)
								.setDrawBarOutline(false);
					} else if (barType != null && barType.equals("3")) {
						if (is3D) {
							renderer = new StackedBarRenderer3D();
							((StackedBarRenderer3D) renderer)
									.setDrawBarOutline(false);
							((StackedBarRenderer3D) renderer)
									.setItemLabelsVisible(true);
							((StackedBarRenderer3D) renderer)
									.setSeriesItemLabelGenerator(
											0,
											new StandardCategoryItemLabelGenerator());
						} else {
							renderer = new StackedBarRenderer();
							((StackedBarRenderer) renderer)
									.setDrawBarOutline(false);
							((StackedBarRenderer) renderer)
									.setItemLabelsVisible(true);
							((StackedBarRenderer) renderer)
									.setSeriesItemLabelGenerator(
											0,
											new StandardCategoryItemLabelGenerator());
						}
					} else {
						if (is3D)
							renderer = new BarRenderer3D();
						else
							renderer = new BarRenderer();
					}
				}
			}
			if (orientation == 0) {
				ItemLabelPosition position1 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
				renderer.setPositiveItemLabelPosition(position1);
				ItemLabelPosition position2 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER_RIGHT);
				renderer.setNegativeItemLabelPosition(position2);
			} else {
				ItemLabelPosition position1 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
				renderer.setPositiveItemLabelPosition(position1);
				ItemLabelPosition position2 = new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
				renderer.setNegativeItemLabelPosition(position2);
			}

			if(colCountsTmp1 == 2)
				renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
						"{1} = {2}", NumberFormat.getInstance()));
			else 
				renderer
						.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
			//设置复合图表
			CategoryPlot plot = new CategoryPlot(defaultcategorydataset,
					categoryAxis, valueAxis, renderer);
			plot.setRangeAxis(1,valueAxis2);
			plot.setDataset(1, defaultcategorydataset2);
			plot.setRenderer(1, renderer2);
			plot.mapDatasetToRangeAxis(1, 1);
			plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
			
			if (orientation == 0) {
				plot.setOrientation(PlotOrientation.HORIZONTAL);
			} else {
				plot.setOrientation(PlotOrientation.VERTICAL);
			}

			JFreeChart chart = new JFreeChart(chartTitle,
					JFreeChart.DEFAULT_TITLE_FONT, plot, showLegend);
			setNoDataMessage(plot);

			// 创建图表的MAP热点链接
			BarRenderer barrenderer = (BarRenderer) plot.getRenderer();
			if (mapURL != null && mapURL.trim().length() > 0) {
				barrenderer
						.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
								mapURL));
			}
			if (mapURL2 != null && mapURL2.trim().length() > 0) {
				renderer2
						.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
								mapURL2));
			}
			// 设置图表透明度
			if (alpha != null) {
				plot.setForegroundAlpha(alpha.floatValue());
			}
			// 柱子上是否显示数值[是否显示,是否柱子内部显示,显示角度,颜色]
			if (showDataTitle) {
				barrenderer
						.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				barrenderer.setItemLabelsVisible(true);
				ItemLabelPosition itemLabelPosition = null;
				if (showDateInside) {
					if ((showDataPosition >= 1.5 && showDataPosition <= 1.6)
							|| (showDataPosition >= -1.6 && showDataPosition <= -1.5)) {
						itemLabelPosition = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.CENTER_RIGHT,
								TextAnchor.CENTER_RIGHT, showDataPosition);
						ItemLabelPosition itemlabelposition1 = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.CENTER_RIGHT,
								TextAnchor.CENTER_RIGHT, showDataPosition);
						barrenderer
								.setPositiveItemLabelPositionFallback(itemlabelposition1);
					} else {
						itemLabelPosition = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.TOP_CENTER, TextAnchor.CENTER,
								showDataPosition);
						ItemLabelPosition itemlabelposition1 = new ItemLabelPosition(
								ItemLabelAnchor.INSIDE12,
								TextAnchor.TOP_CENTER, TextAnchor.CENTER,
								showDataPosition);
						barrenderer
								.setPositiveItemLabelPositionFallback(itemlabelposition1);
					}

				} else
					itemLabelPosition = new ItemLabelPosition(
							ItemLabelAnchor.OUTSIDE12,
							TextAnchor.BOTTOM_CENTER, TextAnchor.CENTER,
							showDataPosition);
				barrenderer.setPositiveItemLabelPosition(itemLabelPosition);
				barrenderer.setItemLabelPaint(StringUtils.getColor(showDataColor,
						Color.BLACK));
			}
			//折线结点[是否显示数值,颜色,是否显示节点,节点是否颜色填充]
			if (!is3D2) {
				LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
						.getRenderer(1);
				lineandshaperenderer.setShapesVisible(shapesVisible2);
				lineandshaperenderer.setShapesFilled(shapesFilled2);
				lineandshaperenderer.setDrawOutlines(true);
				lineandshaperenderer.setStroke(new BasicStroke(1F));
			}
			if (showDataTitle2) {
				renderer2
						.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer2.setItemLabelsVisible(true);
				renderer2
						.setItemLabelPaint(StringUtils.getColor(showDataColor2, Color.black));
			}

			// 字体
			Font plotLabelFont = new Font(fontType, Font.PLAIN, fontSize);
			Font chartLabelFont = new Font(chartFontType, Font.BOLD,
					chartFontSize);
			Font legendLabelFont = new Font(legendFontType, Font.PLAIN,
					legendFontSize);
			// 设置图表标题字体
			TextTitle title = new TextTitle(chartTitle);
			title.setPaint(StringUtils.getColor(chartFontColor, Color.black));
			title.setFont(chartLabelFont);
			if(titlePosition != null ){
				if(titlePosition.equals("right"))
					title.setPosition(RectangleEdge.RIGHT);
				else if(titlePosition.equals("top"))
					title.setPosition(RectangleEdge.TOP);
				else if(titlePosition.equals("left"))
					title.setPosition(RectangleEdge.LEFT);
				else if(titlePosition.equals("bottom"))
					title.setPosition(RectangleEdge.BOTTOM);
			}
			chart.setTitle(title);
			// 设置X轴Y轴字体
			NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
			numberaxis
					.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			numberaxis.setLabelFont(plotLabelFont);
			numberaxis.setTickLabelFont(plotLabelFont);
			numberaxis.setTickMarkPaint(StringUtils.getColor(fontColor, Color.black));
			numberaxis.setLabelPaint(StringUtils.getColor(fontColor, Color.black));
			numberaxis.setTickLabelPaint(StringUtils.getColor(fontColor, Color.black));
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(plotLabelFont);
			domainAxis.setTickLabelFont(plotLabelFont);
			domainAxis.setTickMarkPaint(StringUtils.getColor(fontColor, Color.black));
			domainAxis.setLabelPaint(StringUtils.getColor(fontColor, Color.black));
			domainAxis.setTickLabelPaint(StringUtils.getColor(fontColor, Color.black));
			// 设置图例的字体
			if (showLegend) {
				if(legendPosition != null ){
					if(legendPosition.equals("right"))
						chart.getLegend().setPosition(RectangleEdge.RIGHT);
					else if(legendPosition.equals("top"))
						chart.getLegend().setPosition(RectangleEdge.TOP);
					else if(legendPosition.equals("left"))
						chart.getLegend().setPosition(RectangleEdge.LEFT);
					else if(legendPosition.equals("bottom"))
							chart.getLegend().setPosition(RectangleEdge.BOTTOM);
				}
				chart.getLegend().setItemFont(legendLabelFont);
				chart.getLegend().setItemPaint(
						StringUtils.getColor(legendFontColor, Color.BLACK));
			}
			// 设置标题倾斜度，用来解决标题过长变成...的问题，但是字体变得相对难看
			if (x_axisPosition != null) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions
						.createDownRotationLabelPositions(x_axisPosition
								.doubleValue()));
			}
			// 设置图表的背景色
			chart.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			plot.setBackgroundPaint(StringUtils.getColor(backColor, Color.white));
			if(chart.getLegend() != null)
				chart.getLegend().setBackgroundPaint(
						StringUtils.getColor(backColor, Color.white));
			// 抗锯齿功能
			if (isAntialiasing) {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				chart.getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}

			// 是否显示平均值
			if (isSumAvgValue && avgValueTmp1 != null) {
				ValueMarker valuemarker = createValueMarker(avgValueTmp1,
						avgFontType, avgFontSize, avgFontColor, avgLineColor,
						avgFormat);
				if(is3D)
					plot.addRangeMarker(valuemarker,Layer.BACKGROUND);
				else 
					plot.addRangeMarker(valuemarker,Layer.FOREGROUND);

			}
			if (isSumAvgValue2 && avgValueTmp2 != null) {
				ValueMarker valuemarker = createValueMarker(avgValueTmp2,
						avgFontType2, avgFontSize2, avgFontColor2, avgLineColor2,
						avgFormat2,true);
				if(is3D2)
					plot.addRangeMarker(1,valuemarker,Layer.BACKGROUND);
				else 
					plot.addRangeMarker(1,valuemarker,Layer.FOREGROUND);
			}

			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 把图片写入临时目录并输出
			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, null);

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			writeImageMap(pw, filename, info, false, target);

			chartInfo = report.getPara().get("contextPath")
					+ "/servlet/DisplayChart?filename=" + filename;
			chartInfo = " <img src=\"" + chartInfo + "\" width=" + width
					+ " height=" + height + " border=0 usemap=\"#" + filename
					+ "\">";
			chartInfo = sw.getBuffer() + chartInfo;

		} catch (Exception e) {
			chartInfo = "异常:" + e.toString();
			log.error(e.getStackTrace());
		}

		return chartInfo;
	}
	/**
	 * Writes an image map to an output stream.
	 * 
	 * @param writer
	 *            the writer (<code>null</code> not permitted).
	 * @param name
	 *            the map name (<code>null</code> not permitted).
	 * @param info
	 *            the chart rendering info (<code>null</code> not permitted).
	 * @param useOverLibForToolTips
	 *            whether to use OverLIB for tooltips
	 *            (http://www.bosrup.com/web/overlib/).
	 * @throws IOException
	 *             if there are any I/O errors.
	 */
	private static void writeImageMap(PrintWriter writer, String name,
			ChartRenderingInfo info, boolean useOverLibForToolTips,
			String target) throws IOException {

		ToolTipTagFragmentGenerator toolTipTagFragmentGenerator = null;
		if (useOverLibForToolTips) {
			toolTipTagFragmentGenerator = new OverLIBToolTipTagFragmentGenerator();
		} else {
			toolTipTagFragmentGenerator = new StandardToolTipTagFragmentGenerator();
		}

		URLTagFragmentGenerator urlTagFragmentGenerator = null;

		urlTagFragmentGenerator = new WindowURLTagFragmentGenerator(target);
		ImageMapUtilities.writeImageMap(writer, name, info,
				toolTipTagFragmentGenerator, urlTagFragmentGenerator);
	}

	/**
	 * 当图表无数据时,显示自定义的提示
	 * 
	 * @param plot
	 *            Plot
	 */
	private static void setNoDataMessage(Plot plot) {
		plot.setNoDataMessage(NO_DATA_MESSAGE);
		plot.setNoDataMessageFont(NO_DATA_MESSAGE_FONT);
		plot.setNoDataMessagePaint(NO_DATA_MESSAGE_COLOR);
	}
	
	/**
	 * 划平均线
	 * 
	 * @param avgValue
	 *            avgValue 平均值
	 * @param avgFontType
	 *            字体
	 * @param avgFontSize
	 *            字号
	 * @param avgFontColor
	 *            字体颜色
	 * @param avgLineColor
	 *            线颜色
	 * @param avgFormat
	 *            格式 如 平均值:value
	 * @return
	 */
	private ValueMarker createValueMarker(Double avgValue, String avgFontType,
			int avgFontSize, String avgFontColor, String avgLineColor,
			String avgFormat) {
		return createValueMarker(avgValue,avgFontType,avgFontSize,avgFontColor,avgLineColor,avgFormat,false);
	}
	/**
	 * 划平均线
	 * 
	 * @param avgValue
	 *            avgValue 平均值
	 * @param avgFontType
	 *            字体
	 * @param avgFontSize
	 *            字号
	 * @param avgFontColor
	 *            字体颜色
	 * @param avgLineColor
	 *            线颜色
	 * @param avgFormat
	 *            格式 如 平均值:value
	 * @param isRight //文字在放在右边.
	 * @return
	 */
	private ValueMarker createValueMarker(Double avgValue, String avgFontType,
			int avgFontSize, String avgFontColor, String avgLineColor,
			String avgFormat,boolean isRight) {
		// 格式化平均值
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		String strAvgValue = nf.format(avgValue);
		ValueMarker valuemarker = new ValueMarker(avgValue.doubleValue());
		valuemarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);

		// 平均线颜色
		if (avgLineColor != null && avgLineColor.trim().length() > 0)
			valuemarker.setPaint(StringUtils.getColor(avgLineColor));
		valuemarker.setStroke(new BasicStroke(1.0F));
		// 平均值显示格式
		if (avgFormat != null && avgFormat.trim().length() > 0)
			if (avgFormat.indexOf("value") > -1)
				valuemarker
						.setLabel(avgFormat.replaceAll("value", strAvgValue));
			else
				valuemarker.setLabel(avgFormat + strAvgValue);
		else
			valuemarker.setLabel("平均值:" + strAvgValue);
		// 设置平均值显示字体,字体大小,颜色
		valuemarker
				.setLabelFont(new Font(avgFontType, Font.PLAIN, avgFontSize));
		if (avgFontColor != null && avgFontColor.trim().length() > 0)
			valuemarker.setLabelPaint(StringUtils.getColor(avgFontColor));

		//文字在放在左边还是右边.
		if(isRight){
			valuemarker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
			valuemarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
		} else {
			valuemarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
			valuemarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
		}


		return valuemarker;
	}

	/**
	 * 自定义颜色，给自定义的BAR图表类型使用
	 * 
	 * @return 颜色列表
	 */
	private Paint[] createPaint() {
		return createPaint(null);
	}

	/**
	 * 自定义颜色，给自定义的BAR图表类型使用
	 * 
	 * @return 颜色列表
	 */
	private Paint[] createPaint(String customColors[]) {

		if (customColors != null && customColors.length > 0) {
			Paint[] apaint = new Paint[customColors.length];
			for (int i = 0; i < customColors.length; i++) {
				apaint[i] = StringUtils.getColor(customColors[i], Color.WHITE);
			}
			return apaint;
		} else {
			Paint[] apaint = { Color.red, Color.blue,  Color.green,
					Color.yellow, new Color(255, 112, 255),
					new Color(138, 232, 232), new Color(255, 175, 175),
					new Color(128, 128, 128), new Color(192, 0, 0),
					new Color(0, 0, 192), new Color(0, 192, 0),
					new Color(192, 192, 0), new Color(192, 0, 192),
					new Color(0, 192, 192), new Color(64, 64, 64) };
			return apaint;
		}

	}

	/**
	 * 取得表达式字符串
	 * 
	 * @return 表达式字符串
	 */
	public String getExp() {
		return "";
	}


}
