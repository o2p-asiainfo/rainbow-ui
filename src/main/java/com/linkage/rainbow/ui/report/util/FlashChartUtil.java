package com.linkage.rainbow.ui.report.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;

import com.linkage.rainbow.ui.report.util.dto.Graph;
import com.linkage.rainbow.ui.report.util.dto.Guide;
import com.linkage.rainbow.ui.report.util.dto.JBarChart;
import com.linkage.rainbow.ui.report.util.dto.JLineChart;
import com.linkage.rainbow.ui.report.util.dto.JPieChart;
import com.linkage.rainbow.ui.report.util.dto.Series;
import com.linkage.rainbow.ui.report.util.dto.Graph.Dot;
import com.linkage.rainbow.ui.report.util.dto.JPieChart.Slice;
import com.linkage.rainbow.ui.report.util.dto.Series.Serie;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.linkage.rainbow.util.StringUtil;
import com.opensymphony.xwork2.util.TextUtils; 

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

public class FlashChartUtil {

	

	
	/**
	 * 生成饼图
	 * @param result  数据列表
	 * @param title   饼块标题
	 * @param value   值
	 * @param url     钻取的url
	 * @param urlId   url的标识ID
	 * @return
	 */
	public static String getPieChartXML(List list,String colNameStr,String url){
		/********************************设置图表数据*********************************************/
		String xml = "";
//		如果没有设置,则按KEY,VALUE字段名来取
		String colName[] = null;
		if(colNameStr != null && colNameStr.length()>0){
			colName = colNameStr.split(",");
		}else{
			colName = new String[2] ;
			colName[0]="KEY";
			colName[1]="VALUE";
			if(list.size()>0){
				Map mapTmp=(Map) list.get(0);
				if(!mapTmp.keySet().contains(colName[0]))
					colName[0]="TITLE";
			}
		}
		JPieChart pie=new JPieChart();
		if(list !=null){
			for (int i = 0; i < list.size(); i++) {
				Map mapTmp=(Map) list.get(i);
	        	Slice slice=new Slice();
	        	slice.setTitle(mapTmp.get(colName[0]).toString());
	        	slice.setValue(new Integer(mapTmp.get(colName[1]).toString()));
	        	if(mapTmp.keySet().contains("PULL_OUT")){
	        		slice.setPull_out(mapTmp.get("PULL_OUT")!=null?mapTmp.get("PULL_OUT").toString():null);
	        	}
	        	if(mapTmp.keySet().contains("COLOR")){
	        		slice.setColor(mapTmp.get("COLOR")!=null?mapTmp.get("COLOR").toString():null);
	        	}
	        	if(mapTmp.keySet().contains("URL")){
	        		slice.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
	        	}else if(url!=null ){
	        		slice.setUrl(getUrl(colName,url,mapTmp));
	        	}
	        		
	        	if(mapTmp.keySet().contains("DESCRIPTION")){
	        		slice.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
	        	}
	        	if(mapTmp.keySet().contains("ALPHA")){
	        		slice.setAlpha(mapTmp.get("ALPHA")!=null?mapTmp.get("ALPHA").toString():null);
	        	}
	        	if(mapTmp.keySet().contains("LABEL_RADIUS")){
	        		slice.setLabel_radius(mapTmp.get("LABEL_RADIUS")!=null?mapTmp.get("LABEL_RADIUS").toString():null);
	        	}
	        	if(mapTmp.keySet().contains("PATTERN")){
	        		slice.setPattern(mapTmp.get("PATTERN")!=null?mapTmp.get("PATTERN").toString():null);
	        	}
	        	if(mapTmp.keySet().contains("PATTERN_COLOR")){
	        		slice.setPattern_color(mapTmp.get("PATTERN_COLOR")!=null?mapTmp.get("PATTERN_COLOR").toString():null);
	        	}
	        	pie.addSlices(slice);				
			}
		}
		pie.setPull_outForMax("true");	//最大值，pullout
	
		xml = pie.getXML();
		
		/********************************设置图表数据*********************************************/
        return xml;
	}
	
	public static String getLineChartXML(Map map,List list,String lineNameStr,String colNameStr,String url,String yleftShapeType,
			String yleftFillAlphaStr){
		return getLineChartXML(map,list,null,lineNameStr,null,colNameStr,null,url,yleftShapeType,null,null,null,yleftFillAlphaStr,null,null,null,null);
	}
	public static String getLineChartXML(Map map,List list,String lineNameStr,String colNameStr,String url,String yleftShapeType,
			String yleftFillAlphaStr,String showLineAverageStr,String lineAverageLineColor,String preciousStr){
		return getLineChartXML(map,list,null,lineNameStr,null,colNameStr,null,url,yleftShapeType,null,null,null,yleftFillAlphaStr,null,showLineAverageStr,lineAverageLineColor,preciousStr);
	}
	public static String getLineChartXML(Map map,List list,List rightList,String lineNameStr,String rightLineNameStr,String colNameStr,String rightColNameStr,String url,String yleftShapeType,String yrightShapeType,String yleftShapeSize,String yrightShapeSize,String yleftFillAlphaStr,String yrightFillAlphaStr,String showLineAverageStr,String lineAverageLineColor,String preciousStr){
		return getLineChartXML(map,list,rightList,lineNameStr,rightLineNameStr,colNameStr,rightColNameStr,url,yleftShapeType, yrightShapeType,yleftShapeSize,yrightShapeSize,yleftFillAlphaStr,yrightFillAlphaStr,showLineAverageStr,lineAverageLineColor,preciousStr,null,null);
	}
	/**
	 * 生成曲线图
	 * @param result  数据列表
	 * @param lineName   曲线名称数组
	 * @param colName    曲线数据对应的字段名称（第一条数据为x轴对应的字段名称）
	 * @param color      曲线颜色数组
	 * @param text       曲线点展现的内容配置  ({title} {value} {series} {description} {percents})
	 * @param url        钻取的url
	 * @param urlId      url的标识ID
	 * @return
	 */
	public static String getLineChartXML(Map map,List list,List rightList,String lineNameStr,String rightLineNameStr,
										String colNameStr,String rightColNameStr,String url,
										String yleftShapeType,String yrightShapeType,String yleftShapeSize,String yrightShapeSize,
										String yleftFillAlphaStr,String yrightFillAlphaStr,
										String showLineAverageStr,String lineAverageLineColor,
										String preciousStr,String xValueStart,String xValueInterval){
		String xml = "";
		
		int yLeftGraphSize =0;
		int yRightGraphSize =0;
		String lineName[] = null;
		if(lineNameStr != null && lineNameStr.length()>0){
			lineName = lineNameStr.split(",");
		}
		String colName[] = null;
		if(colNameStr != null && colNameStr.length()>0){
			colName = colNameStr.split(",");
		}
		int iXValueStart=-1,iXValueInterval=-1;
		if(xValueInterval != null)
			iXValueInterval = Integer.parseInt(xValueInterval);
		if(xValueStart != null)
			iXValueStart = Integer.parseInt(xValueStart);
			
		JLineChart line = new JLineChart();
		Series series = new Series();
		line.addSeries(series);
		Map serieMap = new HashMap();
		double sumvalue=0d;
		int rowCount=0;
		if(list != null){
			if(lineName !=null){
				yLeftGraphSize = lineName.length;
				for (int i = 0; i < lineName.length; i++) {
					Graph graph=new Graph();
					graph.setTitle(lineName[i]);
					graph.setGid(""+(i+1));
					graph.setBullet(yleftShapeType);
					graph.setBullet_size(yleftShapeSize==null?"10":yleftShapeSize);
					if(yleftFillAlphaStr!=null){
						try {
							double yleftFillAlpha = Double.parseDouble(yleftFillAlphaStr.toString());
							if(yleftFillAlpha<=1)
								yleftFillAlpha = yleftFillAlpha*100;
							graph.setFill_alpha(""+yleftFillAlpha);
						} catch (Exception e) {
						}
					}
					//if(colName!=null){graph.setColor(colName[i]);graph.setBalloon_color(colName[i]);graph.setBullet_color(colName[i]);}
					//if(text!=null)graph.setBalloon_text(text);
					Map dotMap = new HashMap();
					for (int j = 0; j < list.size(); j++) {
						Map mapTmp=(Map) list.get(j);
						String serieItem = null;
						if(colName != null){//如果有指定字段名
							serieItem = mapTmp.get(colName[0]).toString();
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(0);
		    				Object tempvalue = entry.getValue();
		    				serieItem = tempvalue.toString();
						}
						Serie ser=(Serie)serieMap.get(serieItem);
						if(i==0 &&ser==null){ 
							ser=new Serie();
							ser.setXid(new Integer(j).toString());
							ser.setValue(serieItem);
							if(mapTmp.keySet().contains("SHOW")){
								ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
				        	}else{
				        		if(iXValueInterval >0){
				        			if(xValueStart ==null && j==0){ //如果开始结点值没设置,则以第一个值为准.
				        				ser.setShow("true");
				        				try{iXValueStart = Integer.parseInt(ser.getValue());} catch (Exception e) {}
				        			}
				        			try {
				        				if( (Integer.parseInt(ser.getValue())-iXValueStart)%iXValueInterval ==0)
				        					ser.setShow("true");
									} catch (Exception e) {
									}
				        		}
				        	}
							if(mapTmp.keySet().contains("EVENT_START")){
								ser.setEvent_start(mapTmp.get("EVENT_START")!=null?mapTmp.get("EVENT_START").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_END")){
								ser.setEvent_end(mapTmp.get("EVENT_END")!=null?mapTmp.get("EVENT_END").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_TEXT_COLOR")){
								ser.setEvent_color(mapTmp.get("EVENT_TEXT_COLOR")!=null?mapTmp.get("EVENT_TEXT_COLOR").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_DESCRIPTION")){
								ser.setEvent_description(mapTmp.get("EVENT_DESCRIPTION")!=null?mapTmp.get("EVENT_DESCRIPTION").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_COLOR")){
								ser.setEvent_color(mapTmp.get("EVENT_COLOR")!=null?mapTmp.get("EVENT_COLOR").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_ALPHA")){
								ser.setEvent_alpha(mapTmp.get("EVENT_ALPHA")!=null?mapTmp.get("EVENT_ALPHA").toString():null);
				        	}
							series.addSeries(ser);
							serieMap.put(ser.getValue(),ser);
						}
						String value="";
						if(colName != null){//如果有指定字段名
							value=StringUtil.valueOf(mapTmp.get(colName[i+1])) ;
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(i+1);
		    				Object tempvalue = entry.getValue();
		    				value = StringUtil.valueOf(tempvalue);
						}
						Dot dot=(Dot)dotMap.get(serieItem);
						if(dot==null){
							dot=new Dot();
							dotMap.put(serieItem,dot);
							rowCount++;
							dot.setXid(ser.getXid());
							dot.setValue(value);
							if(mapTmp.keySet().contains("URL")){
				        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
				        	}else if(url!=null ){
				        		dot.setUrl(getUrl(colName,url,mapTmp));
				        	}
				        	if(mapTmp.keySet().contains("BULLET")){
				        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_COLOR")){
				        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_SIZE")){
				        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("DESCRIPTION")){
				        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
				        	}
							
							graph.addDots(dot);
						}else{
							dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
									+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
						}
						sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
					}
					
					line.addGraphs(graph);
				}
			}else{//数据按纵向存储
				if(colName == null){
					colName = new String[3];
					colName[0]="COL";
					colName[1]="ROW";
					colName[2]="VALUE";
				}
				Map graphMap = new HashMap();
				Map dotMap = new HashMap();
				for (int i = 0; i < list.size(); i++) {
					Map mapTmp=(Map) list.get(i);
					String serieItem = StringUtil.valueOf(mapTmp.get(colName[0]));
					String statItem = StringUtil.valueOf(mapTmp.get(colName[1]));
					String value = StringUtil.valueOf(mapTmp.get(colName[2]));
					Serie ser=(Serie)serieMap.get(serieItem);
					if(ser==null){ 
						ser=new Serie();
						ser.setXid(""+serieMap.size());
						ser.setValue(serieItem);
						if(mapTmp.keySet().contains("SHOW")){
							ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
			        	}else{
			        		if(iXValueInterval >0){
			        			if(xValueStart ==null && i==0){ //如果开始结点值没设置,则以第一个值为准.
			        				ser.setShow("true");
			        				try{iXValueStart = Integer.parseInt(ser.getValue());} catch (Exception e) {}
			        			}
			        			try {
			        				if( (Integer.parseInt(ser.getValue())-iXValueStart)%iXValueInterval ==0)
			        					ser.setShow("true");
								} catch (Exception e) {
								}
			        		}
			        	}
						if(mapTmp.keySet().contains("EVENT_START")){
							ser.setEvent_start(mapTmp.get("EVENT_START")!=null?mapTmp.get("EVENT_START").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_END")){
							ser.setEvent_end(mapTmp.get("EVENT_END")!=null?mapTmp.get("EVENT_END").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_TEXT_COLOR")){
							ser.setEvent_color(mapTmp.get("EVENT_TEXT_COLOR")!=null?mapTmp.get("EVENT_TEXT_COLOR").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_DESCRIPTION")){
							ser.setEvent_description(mapTmp.get("EVENT_DESCRIPTION")!=null?mapTmp.get("EVENT_DESCRIPTION").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_COLOR")){
							ser.setEvent_color(mapTmp.get("EVENT_COLOR")!=null?mapTmp.get("EVENT_COLOR").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_ALPHA")){
							ser.setEvent_alpha(mapTmp.get("EVENT_ALPHA")!=null?mapTmp.get("EVENT_ALPHA").toString():null);
			        	}
						series.addSeries(ser);
						serieMap.put(serieItem,ser);
					}
					Graph graph=(Graph)graphMap.get(statItem);
					if(graph==null){
						graph=new Graph();
						graph.setTitle(statItem);
						yLeftGraphSize++;
						graph.setGid(""+yLeftGraphSize);
						graph.setBullet(yleftShapeType);
						graph.setBullet_size(yleftShapeSize==null?"10":yleftShapeSize);
						if(yleftFillAlphaStr!=null){
							try {
								double yleftFillAlpha = Double.parseDouble(yleftFillAlphaStr);
								if(yleftFillAlpha<=1)
									yleftFillAlpha = yleftFillAlpha*100;
								graph.setFill_alpha(""+yleftFillAlpha);
							} catch (Exception e) {
							}
						}
						graphMap.put(statItem,graph);
						line.addGraphs(graph);
					}
					Dot dot=(Dot)dotMap.get(serieItem+"_"+statItem);
					if(dot==null){
						dot=new Dot();
						dotMap.put(serieItem+"_"+statItem,dot);
						rowCount++;
						
						dot.setXid(ser.getXid()); 
						dot.setValue(value);
						
						
						if(mapTmp.keySet().contains("URL")){
			        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
			        	}else if(url!=null ){
			        		dot.setUrl(getUrl(colName,url,mapTmp));
			        	}
			        	if(mapTmp.keySet().contains("BULLET")){
			        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_COLOR")){
			        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_SIZE")){
			        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("DESCRIPTION")){
			        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
			        	}
						graph.addDots(dot);
					}else{
						dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
								+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
					}
					sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
					
				}
			}
		}
		if(rightList !=null){//右边坐标数据不为空
			
		
			String rightLineName[] = null;
			if(rightLineNameStr != null && rightLineNameStr.length()>0){
				rightLineName = rightLineNameStr.split(",");
			}
			String rightColName[] = null;
			if(rightColNameStr != null && rightColNameStr.length()>0){
				rightColName = rightColNameStr.split(",");
			}
			if(rightLineName !=null){
				yRightGraphSize = rightLineName.length;
				for (int i = 0; i < rightLineName.length; i++) {
					Graph graph=new Graph();
					graph.setTitle(lineName[i]);
					graph.setBullet("round_outlined");
					graph.setGid(""+(i+yLeftGraphSize));
					graph.setBullet(yrightShapeType);
					graph.setBullet_size(yrightShapeSize==null?"10":yrightShapeSize);
					if(yrightFillAlphaStr!=null){
						try {
							double yrightFillAlpha = Double.parseDouble(yrightFillAlphaStr);
							if(yrightFillAlpha<=1)
								yrightFillAlpha = yrightFillAlpha*100;
							graph.setFill_alpha(""+yrightFillAlpha);
						} catch (Exception e) {
						}
					}
					//if(colName!=null){graph.setColor(colName[i]);graph.setBalloon_color(colName[i]);graph.setBullet_color(colName[i]);}
					//if(text!=null)graph.setBalloon_text(text);
					Map dotMap = new HashMap();
					for (int j = 0; j < rightList.size(); j++) {
						Map mapTmp=(Map) rightList.get(j);
						
						String serieItem = null;
						if(colName != null){//如果有指定字段名
							serieItem = mapTmp.get(colName[0]).toString();
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(0);
		    				Object tempvalue = entry.getValue();
		    				serieItem = tempvalue.toString();
						}
						Serie ser=(Serie)serieMap.get(serieItem);
						if(i==0 &&ser==null){ 
							ser=new Serie();
							ser.setXid(new Integer(j).toString());
							ser.setValue(serieItem);
							if(mapTmp.keySet().contains("SHOW")){
								ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
				        	}else{
				        		if(iXValueInterval >0){
				        			if(xValueStart ==null && j==0){ //如果开始结点值没设置,则以第一个值为准.
				        				ser.setShow("true");
				        				try{iXValueStart = Integer.parseInt(ser.getValue());} catch (Exception e) {}
				        			}
				        			try {
				        				if( (Integer.parseInt(ser.getValue())-iXValueStart)%iXValueInterval ==0)
				        					ser.setShow("true");
									} catch (Exception e) {
									}
				        		}
				        	}
							if(mapTmp.keySet().contains("EVENT_START")){
								ser.setEvent_start(mapTmp.get("EVENT_START")!=null?mapTmp.get("EVENT_START").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_END")){
								ser.setEvent_end(mapTmp.get("EVENT_END")!=null?mapTmp.get("EVENT_END").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_TEXT_COLOR")){
								ser.setEvent_color(mapTmp.get("EVENT_TEXT_COLOR")!=null?mapTmp.get("EVENT_TEXT_COLOR").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_DESCRIPTION")){
								ser.setEvent_description(mapTmp.get("EVENT_DESCRIPTION")!=null?mapTmp.get("EVENT_DESCRIPTION").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_COLOR")){
								ser.setEvent_color(mapTmp.get("EVENT_COLOR")!=null?mapTmp.get("EVENT_COLOR").toString():null);
				        	}
							if(mapTmp.keySet().contains("EVENT_ALPHA")){
								ser.setEvent_alpha(mapTmp.get("EVENT_ALPHA")!=null?mapTmp.get("EVENT_ALPHA").toString():null);
				        	}
							series.addSeries(ser);
							serieMap.put(ser.getValue(),ser);
						}
						String value="";
						if(colName != null){//如果有指定字段名
							value=StringUtil.valueOf(mapTmp.get(colName[i+1])) ;
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(i+1);
		    				Object tempvalue = entry.getValue();
		    				value = StringUtil.valueOf(tempvalue);
						}
						Dot dot=(Dot)dotMap.get(serieItem);
						if(dot==null){
							dot=new Dot();
							dotMap.put(serieItem,dot);
							rowCount++;
							
							dot.setXid(ser.getXid());
							dot.setValue(value);
							if(mapTmp.keySet().contains("URL")){
				        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
				        	}else if(url!=null ){
				        		dot.setUrl(getUrl(colName,url,mapTmp));
				        	}
				        	if(mapTmp.keySet().contains("BULLET")){
				        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_COLOR")){
				        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_SIZE")){
				        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("DESCRIPTION")){
				        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
				        	}
							graph.addDots(dot);
						}else{
							dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
									+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
						}
						
						sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
						
						
					}
					line.addGraphs(graph);
				}
			}else{//数据按纵向存储
				if(rightLineName == null){
					rightLineName = new String[3];
					rightLineName[0]="COL";
					rightLineName[1]="ROW";
					rightLineName[2]="VALUE";
				}
				Map graphMap = new HashMap();
				Map dotMap = new HashMap();
				for (int i = 0; i < rightList.size(); i++) {
					Map mapTmp=(Map) rightList.get(i);
					String serieItem = StringUtil.valueOf(mapTmp.get(colName[0]));
					String statItem = StringUtil.valueOf(mapTmp.get(colName[1]));
					String value = StringUtil.valueOf(mapTmp.get(colName[2]));
					Serie ser=(Serie)serieMap.get(serieItem);
					if(ser==null){ 
						ser=new Serie();
						ser.setXid(""+serieMap.size());
						ser.setValue(serieItem);
						if(mapTmp.keySet().contains("SHOW")){
							ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
			        	}else{
			        		if(iXValueInterval >0){
			        			if(xValueStart ==null && i==0){ //如果开始结点值没设置,则以第一个值为准.
			        				ser.setShow("true");
			        				try{iXValueStart = Integer.parseInt(ser.getValue());} catch (Exception e) {}
			        			}
			        			try {
			        				if( (Integer.parseInt(ser.getValue())-iXValueStart)%iXValueInterval ==0)
			        					ser.setShow("true");
								} catch (Exception e) {
								}
			        		}
			        	}
						if(mapTmp.keySet().contains("EVENT_START")){
							ser.setEvent_start(mapTmp.get("EVENT_START")!=null?mapTmp.get("EVENT_START").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_END")){
							ser.setEvent_end(mapTmp.get("EVENT_END")!=null?mapTmp.get("EVENT_END").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_TEXT_COLOR")){
							ser.setEvent_color(mapTmp.get("EVENT_TEXT_COLOR")!=null?mapTmp.get("EVENT_TEXT_COLOR").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_DESCRIPTION")){
							ser.setEvent_description(mapTmp.get("EVENT_DESCRIPTION")!=null?mapTmp.get("EVENT_DESCRIPTION").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_COLOR")){
							ser.setEvent_color(mapTmp.get("EVENT_COLOR")!=null?mapTmp.get("EVENT_COLOR").toString():null);
			        	}
						if(mapTmp.keySet().contains("EVENT_ALPHA")){
							ser.setEvent_alpha(mapTmp.get("EVENT_ALPHA")!=null?mapTmp.get("EVENT_ALPHA").toString():null);
			        	}
						series.addSeries(ser);
						serieMap.put(serieItem,ser);
					}
					Graph graph=(Graph)graphMap.get(statItem);
					if(graph==null){
						graph=new Graph();
						graph.setTitle(statItem);
						yRightGraphSize++;
						graph.setGid(""+(yRightGraphSize+yLeftGraphSize));
						graph.setBullet(yrightShapeType);
						graph.setBullet_size(yrightShapeSize==null?"10":yrightShapeSize);
						if(yrightFillAlphaStr!=null){
							try {
								double yrightFillAlpha = Double.parseDouble(yrightFillAlphaStr);
								if(yrightFillAlpha<=1)
									yrightFillAlpha = yrightFillAlpha*100;
								graph.setFill_alpha(""+yrightFillAlpha);
							} catch (Exception e) {
							}
						}
						graphMap.put(statItem,graph);
						line.addGraphs(graph);
					}
					Dot dot=(Dot)dotMap.get(serieItem+"_"+statItem);
					if(dot==null){
						dot=new Dot();
						dotMap.put(serieItem+"_"+statItem,dot);
						rowCount++;
						
						dot.setXid(ser.getXid()); 
						dot.setValue(value);

						if(mapTmp.keySet().contains("URL")){
			        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
			        	}else if(url!=null ){
			        		dot.setUrl(getUrl(colName,url,mapTmp));
			        	}
			        	if(mapTmp.keySet().contains("BULLET")){
			        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_COLOR")){
			        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_SIZE")){
			        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("DESCRIPTION")){
			        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
			        	}
						graph.addDots(dot);
					}else{
						dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
								+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
					}
					sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
				}
			}
		}
		
		map.put("yLeftGraphSize",yLeftGraphSize);
		map.put("yRightGraphSize",yRightGraphSize);
		boolean showLineAverage=false;
		double avgValue=0d;
		if(showLineAverageStr!=null){
			showLineAverage=showLineAverageStr.equals("true")?true:false;
		}
		if(showLineAverage&&rowCount!=0){
			avgValue=sumvalue/rowCount;
			NumberFormat nf = NumberFormat.getInstance();
			int precious = 2;
			if(preciousStr!=null){
				try {
					precious = Integer.parseInt(preciousStr);
				} catch (Exception e) {}
			}
			nf.setMaximumFractionDigits(precious);
			String avgValueStr = nf.format(avgValue);
			Guide guide = new Guide();
			
			guide.setStart_value(avgValueStr);
			if(lineAverageLineColor!=null){
				guide.setColor(lineAverageLineColor);
			}
			guide.setInside("true");
			guide.setTitle("平均值:"+avgValueStr);
			line.addGuide(guide);
		}
		
		xml = line.getXML();
        return xml;
	}

	public static String getBarChartXML(List list,List rightList,String lineNameStr,String rightLineNameStr,String colNameStr,String rightColNameStr,String url,String yleftShapeType,String yrightShapeType,String yleftFillAlphaStr,String yrightFillAlphaStr,String showBarAverageStr,String barAverageLineColor,String preciousStr){
		String xml = "";
		String lineName[] = null;
		if(lineNameStr != null && lineNameStr.length()>0){
			lineName = lineNameStr.split(",");
		}
	
		String colName[] = null;
		if(colNameStr != null && colNameStr.length()>0){
			colName = colNameStr.split(",");
		}
		String colors[]="#FF6600,#FCD202,#B0DE09,#0D8ECF,#2A0CD0,#CD0D74,#CC0000,#00CC00,#0000CC,#DDDDDD,#999999,#333333,#990000".split(",");
		JBarChart bar = new JBarChart();
		Series series = new Series();
		bar.addSeries(series);
		Map serieMap = new HashMap();
		double sumvalue=0d;
		int rowCount=0;
		if(list != null){
			if(lineName !=null){
				for (int i = 0; i < lineName.length; i++) {
					
					Graph graph=new Graph();
					graph.setTitle(lineName[i]);
					graph.setBullet("round_outlined");
					graph.setBullet_size("10");
					graph.setLine_width("2");
	//				graph.setGid("1");
					graph.setBullet( yleftShapeType);
					if(yleftFillAlphaStr!=null){
						try {
							double yleftFillAlpha = Double.parseDouble(yleftFillAlphaStr);
							if(yleftFillAlpha<=1)
								yleftFillAlpha = yleftFillAlpha*100;
							graph.setFill_alpha(""+yleftFillAlpha);
						} catch (Exception e) {
						}
					}
					//if(colName!=null){graph.setColor(colName[i]);graph.setBalloon_color(colName[i]);graph.setBullet_color(colName[i]);}
					//if(text!=null)graph.setBalloon_text(text);
					Map dotMap = new HashMap();
					for (int j = 0; j < list.size(); j++) {
						Map mapTmp=(Map) list.get(j);
						String serieItem = null;
						if(colName != null){//如果有指定字段名
							serieItem = mapTmp.get(colName[0]).toString();
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(0);
		    				Object tempvalue = entry.getValue();
		    				serieItem = tempvalue.toString();
						}
						Serie ser=(Serie)serieMap.get(serieItem);
						if(i==0 &&ser==null){ 
							ser=new Serie();
							ser.setXid(new Integer(j).toString());
							ser.setValue(serieItem);
							if(mapTmp.keySet().contains("SHOW")){
								ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
				        	}
							if(mapTmp.keySet().contains("BG_COLOR")){
								ser.setBg_color(mapTmp.get("BG_COLOR")!=null?mapTmp.get("BG_COLOR").toString():null);
				        	}
							if(mapTmp.keySet().contains("BG_ALPHA")){
								ser.setBg_alpha(mapTmp.get("BG_ALPHA")!=null?mapTmp.get("BG_ALPHA").toString():null);
				        	}
							series.addSeries(ser);
							serieMap.put(ser.getValue(),ser);
						}
						
						String value="";
						if(colName != null){//如果有指定字段名
							value=StringUtil.valueOf(mapTmp.get(colName[i+1])) ;
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(i+1);
		    				Object tempvalue = entry.getValue();
		    				value = StringUtil.valueOf(tempvalue);
						}
						Dot dot=(Dot)dotMap.get(serieItem);
						if(dot==null){
							dot=new Dot();
							dotMap.put(serieItem,dot);
							rowCount++;
							
							dot.setXid(ser.getXid());
							dot.setValue(value);
							if(mapTmp.keySet().contains("URL")){
				        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
				        	}else if(url!=null ){
				        		dot.setUrl(getUrl(colName,url,mapTmp));
				        	}
				        	if(mapTmp.keySet().contains("COLOR")){
				        		dot.setColor(mapTmp.get("COLOR")!=null?mapTmp.get("COLOR").toString():null);
				        	}else{
				        		if(colName != null &&colName.length==2) 
				        			dot.setColor(colors[i%colors.length]);
				        	}
				        	if(mapTmp.keySet().contains("GRADIENT_FILL_COLORS")){
				        		dot.setGradient_fill_colors(mapTmp.get("GRADIENT_FILL_COLORS")!=null?mapTmp.get("GRADIENT_FILL_COLORS").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("START")){
				        		dot.setStart(mapTmp.get("START")!=null?mapTmp.get("START").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET")){
				        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_COLOR")){
				        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_SIZE")){
				        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("DESCRIPTION")){
				        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("PATTERN")){
				        		dot.setPattern(mapTmp.get("PATTERN")!=null?mapTmp.get("PATTERN").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("PATTERN_COLOR")){
				        		dot.setPattern_color(mapTmp.get("PATTERN_COLOR")!=null?mapTmp.get("PATTERN_COLOR").toString():null);
				        	}
							graph.addDots(dot);
						}else{
							dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
									+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
						}
						sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
					}
					
					bar.addGraphs(graph);
				}
			}else{//数据按纵向存储
				if(colName == null){
					colName = new String[3];
					colName[0]="COL";
					colName[1]="ROW";
					colName[2]="VALUE";
				}
				
				Map graphMap = new HashMap();
				Map dotMap = new HashMap();
				for (int i = 0; i < list.size(); i++) {
					Map mapTmp=(Map) list.get(i);
					String serieItem = StringUtil.valueOf(mapTmp.get(colName[0]));
					String statItem ="";
					String value ="";
					if(colName.length==2){
						value = StringUtil.valueOf(mapTmp.get(colName[1]));
					}else{
						statItem = StringUtil.valueOf(mapTmp.get(colName[1]));
						value = StringUtil.valueOf(mapTmp.get(colName[2]));
					}
					
					
					Serie ser=(Serie)serieMap.get(serieItem);
					if(ser==null){ 
						ser=new Serie();
						ser.setXid(""+serieMap.size());
						ser.setValue(serieItem);
						if(mapTmp.keySet().contains("SHOW")){
							ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
			        	}
						if(mapTmp.keySet().contains("BG_COLOR")){
							ser.setBg_color(mapTmp.get("BG_COLOR")!=null?mapTmp.get("BG_COLOR").toString():null);
			        	}
						if(mapTmp.keySet().contains("BG_ALPHA")){
							ser.setBg_alpha(mapTmp.get("BG_ALPHA")!=null?mapTmp.get("BG_ALPHA").toString():null);
			        	}
						series.addSeries(ser);
						serieMap.put(serieItem,ser);
					}
					Graph graph=(Graph)graphMap.get(statItem);
					if(graph==null){
						graph=new Graph();
						graph.setTitle(statItem);
						graph.setBullet("round_outlined");
						graph.setBullet_size("10");
						graph.setLine_width("2");
	//					graph.setGid("1");
						graph.setBullet( yleftShapeType);
						if(yleftFillAlphaStr!=null){
							try {
								double yleftFillAlpha = Double.parseDouble(yleftFillAlphaStr);
								if(yleftFillAlpha<=1)
									yleftFillAlpha = yleftFillAlpha*100;
								graph.setFill_alpha(""+yleftFillAlpha);
							} catch (Exception e) {
							}
						}
						graphMap.put(statItem,graph);
						bar.addGraphs(graph);
					}
					Dot dot=(Dot)dotMap.get(serieItem+"_"+statItem);
					if(dot==null){
						dot=new Dot();
						dotMap.put(serieItem+"_"+statItem,dot);
						rowCount++;
						
						dot.setXid(ser.getXid()); 
						if(mapTmp.keySet().contains("URL")){
			        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
			        	}else if(url!=null ){
			        		dot.setUrl(getUrl(colName,url,mapTmp));
			        	}
						if(mapTmp.keySet().contains("COLOR")){
			        		dot.setColor(mapTmp.get("COLOR")!=null?mapTmp.get("COLOR").toString():null);
			        	}else{
			        		if(colName.length==2) 
			        			dot.setColor(colors[i%colors.length]);
			        	}
						if(mapTmp.keySet().contains("GRADIENT_FILL_COLORS")){
			        		dot.setGradient_fill_colors(mapTmp.get("GRADIENT_FILL_COLORS")!=null?mapTmp.get("GRADIENT_FILL_COLORS").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("START")){
			        		dot.setStart(mapTmp.get("START")!=null?mapTmp.get("START").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET")){
			        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_COLOR")){
			        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_SIZE")){
			        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("DESCRIPTION")){
			        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("PATTERN")){
			        		dot.setPattern(mapTmp.get("PATTERN")!=null?mapTmp.get("PATTERN").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("PATTERN_COLOR")){
			        		dot.setPattern_color(mapTmp.get("PATTERN_COLOR")!=null?mapTmp.get("PATTERN_COLOR").toString():null);
			        	}
						graph.addDots(dot);
						dot.setValue(value);
					}else{
						dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
								+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
					}
					sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
				
				}
			}
		}
		if(rightList !=null){//右边坐标数据不为空
			
			String rightLineName[] = null;
			if(rightLineNameStr != null && rightLineNameStr.length()>0){
				rightLineName = rightLineNameStr.split(",");
			}
			
			String rightColName[] = null;
			if(rightColNameStr != null && rightColNameStr.length()>0){
				rightColName = rightColNameStr.split(",");
			}
			if(rightLineName !=null){
				for (int i = 0; i < rightLineName.length; i++) {
					Graph graph=new Graph();
					graph.setTitle(lineName[i]);
					graph.setBullet("round_outlined");
					graph.setBullet_size("10");
					//graph.setLine_width("2");
					graph.setGid(""+(i+2));
					graph.setBullet( yrightShapeType);
					if(yrightFillAlphaStr!=null){
						try {
							double yrightFillAlpha = Double.parseDouble(yrightFillAlphaStr);
							if(yrightFillAlpha<=1)
								yrightFillAlpha = yrightFillAlpha*100;
							graph.setFill_alpha(""+yrightFillAlpha);
						} catch (Exception e) {
						}
					}
					//if(colName!=null){graph.setColor(colName[i]);graph.setBalloon_color(colName[i]);graph.setBullet_color(colName[i]);}
					//if(text!=null)graph.setBalloon_text(text);
					Map dotMap = new HashMap();
					for (int j = 0; j < rightList.size(); j++) {
						Map mapTmp=(Map) rightList.get(j);
						String serieItem = null;
						if(colName != null){//如果有指定字段名
							serieItem = mapTmp.get(colName[0]).toString();
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(0);
		    				Object tempvalue = entry.getValue();
		    				serieItem = tempvalue.toString();
						}
						Serie ser=(Serie)serieMap.get(serieItem);
						if(i==0 &&ser==null){ 
							ser=new Serie();
							ser.setXid(new Integer(j).toString());
							ser.setValue(serieItem);
							if(mapTmp.keySet().contains("SHOW")){
								ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
				        	}
							if(mapTmp.keySet().contains("BG_COLOR")){
								ser.setBg_color(mapTmp.get("BG_COLOR")!=null?mapTmp.get("BG_COLOR").toString():null);
				        	}
							if(mapTmp.keySet().contains("BG_ALPHA")){
								ser.setBg_alpha(mapTmp.get("BG_ALPHA")!=null?mapTmp.get("BG_ALPHA").toString():null);
				        	}
							series.addSeries(ser);
							serieMap.put(ser.getValue(),ser);
						}
						
						String value="";
						if(colName != null){//如果有指定字段名
							value=StringUtil.valueOf(mapTmp.get(colName[i+1])) ;
						}else{//没有指定字段名,按顺序显示
							List listTmp = new ArrayList(mapTmp.entrySet());
							Map.Entry entry = (Map.Entry) listTmp.get(i+1);
		    				Object tempvalue = entry.getValue();
		    				value = StringUtil.valueOf(tempvalue);
						}
						Dot dot=(Dot)dotMap.get(serieItem);
						if(dot==null){
							dot=new Dot();
							dotMap.put(serieItem,dot);
							rowCount++;
							
							dot.setXid(ser.getXid());
							dot.setValue(value);
						
							
							if(mapTmp.keySet().contains("URL")){
				        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
				        	}else if(url!=null ){
				        		dot.setUrl(getUrl(colName,url,mapTmp));
				        	}
							if(mapTmp.keySet().contains("COLOR")){
				        		dot.setColor(mapTmp.get("COLOR")!=null?mapTmp.get("COLOR").toString():null);
				        	}else{
				        		if(colName.length==2) 
				        			dot.setColor(colors[i%colors.length]);
				        	}
							if(mapTmp.keySet().contains("GRADIENT_FILL_COLORS")){
				        		dot.setGradient_fill_colors(mapTmp.get("GRADIENT_FILL_COLORS")!=null?mapTmp.get("GRADIENT_FILL_COLORS").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("START")){
				        		dot.setStart(mapTmp.get("START")!=null?mapTmp.get("START").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET")){
				        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_COLOR")){
				        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("BULLET_SIZE")){
				        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("DESCRIPTION")){
				        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("PATTERN")){
				        		dot.setPattern(mapTmp.get("PATTERN")!=null?mapTmp.get("PATTERN").toString():null);
				        	}
				        	if(mapTmp.keySet().contains("PATTERN_COLOR")){
				        		dot.setPattern_color(mapTmp.get("PATTERN_COLOR")!=null?mapTmp.get("PATTERN_COLOR").toString():null);
				        	}
				        	graph.addDots(dot);
						}else{
							dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
									+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
						}
				        sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
					}
					bar.addGraphs(graph);
				}
			}else{//数据按纵向存储
				if(rightLineName == null){
					rightLineName = new String[3];
					rightLineName[0]="COL";
					rightLineName[1]="ROW";
					rightLineName[2]="VALUE";
					
				}
				Map graphMap = new HashMap();
				Map dotMap = new HashMap();
				for (int i = 0; i < rightList.size(); i++) {
					Map mapTmp=(Map) rightList.get(i);
					String serieItem = StringUtil.valueOf(mapTmp.get(colName[0]));
					String statItem ="";
					String value = "";
					if(colName.length==2){
						value = StringUtil.valueOf(mapTmp.get(colName[1]));
					}else{
						statItem = StringUtil.valueOf(mapTmp.get(colName[1]));
						value = StringUtil.valueOf(mapTmp.get(colName[2]));
					}
					Serie ser=(Serie)serieMap.get(serieItem);
					if(ser==null){ 
						ser=new Serie();
						ser.setXid(""+serieMap.size());
						ser.setValue(serieItem);
						if(mapTmp.keySet().contains("SHOW")){
							ser.setShow(mapTmp.get("SHOW")!=null?mapTmp.get("SHOW").toString():null);
			        	}
						if(mapTmp.keySet().contains("BG_COLOR")){
							ser.setBg_color(mapTmp.get("BG_COLOR")!=null?mapTmp.get("BG_COLOR").toString():null);
			        	}
						if(mapTmp.keySet().contains("BG_ALPHA")){
							ser.setBg_alpha(mapTmp.get("BG_ALPHA")!=null?mapTmp.get("BG_ALPHA").toString():null);
			        	}
						series.addSeries(ser);
						serieMap.put(serieItem,ser);
					}
					Graph graph=(Graph)graphMap.get(statItem);
					if(graph==null){
						graph=new Graph();
						graph.setTitle(statItem);
						graph.setBullet("round_outlined");
						graph.setBullet_size("10");
						//graph.setLine_width("2");
						graph.setGid(""+(graphMap.size()+2));
						graph.setBullet( yrightShapeType);
						if(yrightFillAlphaStr!=null){
							try {
								double yrightFillAlpha = Double.parseDouble(yrightFillAlphaStr);
								if(yrightFillAlpha<=1)
									yrightFillAlpha = yrightFillAlpha*100;
								graph.setFill_alpha(""+yrightFillAlpha);
							} catch (Exception e) {
							}
						}
						graphMap.put(statItem,graph);
						bar.addGraphs(graph);
					}
					Dot dot=(Dot)dotMap.get(serieItem+"_"+statItem);
					if(dot==null){
						dot=new Dot();
						dotMap.put(serieItem+"_"+statItem,dot);
						rowCount++;
						
						dot.setXid(ser.getXid()); 
						if(mapTmp.keySet().contains("URL")){
			        		dot.setUrl(mapTmp.get("URL")!=null?mapTmp.get("URL").toString():null);
			        	}else if(url!=null ){
			        		dot.setUrl(getUrl(colName,url,mapTmp));
			        	}
						if(mapTmp.keySet().contains("COLOR")){
			        		dot.setColor(mapTmp.get("COLOR")!=null?mapTmp.get("COLOR").toString():null);
			        	}else{
			        		if(colName.length==2) 
			        			dot.setColor(colors[i%colors.length]);
			        	}
						if(mapTmp.keySet().contains("GRADIENT_FILL_COLORS")){
			        		dot.setGradient_fill_colors(mapTmp.get("GRADIENT_FILL_COLORS")!=null?mapTmp.get("GRADIENT_FILL_COLORS").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("START")){
			        		dot.setStart(mapTmp.get("START")!=null?mapTmp.get("START").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET")){
			        		dot.setBullet(mapTmp.get("BULLET")!=null?mapTmp.get("BULLET").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_COLOR")){
			        		dot.setBullet_color(mapTmp.get("BULLET_COLOR")!=null?mapTmp.get("BULLET_COLOR").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("BULLET_SIZE")){
			        		dot.setBullet_size(mapTmp.get("BULLET_SIZE")!=null?mapTmp.get("BULLET_SIZE").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("DESCRIPTION")){
			        		dot.setDescription(mapTmp.get("DESCRIPTION")!=null?mapTmp.get("DESCRIPTION").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("PATTERN")){
			        		dot.setPattern(mapTmp.get("PATTERN")!=null?mapTmp.get("PATTERN").toString():null);
			        	}
			        	if(mapTmp.keySet().contains("PATTERN_COLOR")){
			        		dot.setPattern_color(mapTmp.get("PATTERN_COLOR")!=null?mapTmp.get("PATTERN_COLOR").toString():null);
			        	}
						graph.addDots(dot);
						dot.setValue(value);
					}else{
						dot.setValue(StringUtil.valueOf(Double.parseDouble(value==null||value.equals("")?"0":value)
								+Double.parseDouble(dot.getValue()==null||dot.getValue().equals("")?"0":dot.getValue())));
					}
					sumvalue=sumvalue+Double.parseDouble(value==null||value.equals("")?"0":value);
				}
			}
		}
		
		boolean showLineAverage=false;
		double avgValue=0d;
		if(showBarAverageStr!=null){
			showLineAverage=showBarAverageStr.equals("true")?true:false;
		}
		if(showLineAverage&&rowCount!=0){
			avgValue=sumvalue/rowCount;
			NumberFormat nf = NumberFormat.getInstance();
			int precious = 2;
			if(preciousStr!=null){
				try {
					precious = Integer.parseInt(preciousStr);
				} catch (Exception e) {}
			}
			nf.setMaximumFractionDigits(precious);
			String avgValueStr = nf.format(avgValue);
			Guide guide = new Guide();
			
			guide.setStart_value(avgValueStr);
			if(barAverageLineColor!=null){
				guide.setColor(barAverageLineColor);
			}
			guide.setInside("true");
			guide.setTitle("平均值:"+avgValueStr);
			bar.addGuide(guide);
		}
		
		xml = bar.getXML();
		return xml;
	}	
	
	/**
	 * 生成柱状图
	 * @param para
	 * @return
	 */
	public static String pieChart(Map map){
		map.put("chartType","PIE_CHART");
		String width="500"; 
		String height="300";
		//宽度设置
		if(map.get("width") != null){
			width = map.get("width").toString();
		}else{
			map.put("width",width);
		}
		//高度设置
		if(map.get("height") != null){
			height = map.get("height").toString();
		}else{
			map.put("height",height);
		}
		//背景色
		String backColor="#FFFFFF";
		if(map.get("backColor")!=null)
			backColor=map.get("backColor").toString();
		
		/********************************设置图表数据*********************************************/
		String colNameStr = (String) map.get("colNames");//如果没有设置,则按KEY,VALUE字段名来取
		String url = map.get("linkUrl")!=null? map.get("linkUrl").toString():null;
		List list = (List) map.get("value");
		String xml =  getPieChartXML(list,colNameStr,url);
		xml = TextUtils.htmlEncode(xml.replaceAll("%","%25").replaceAll("&","%26"));
		
		/********************************设置图表数据*********************************************/
		
		String chartSettings = getPieSettings(map);
		HttpServletRequest request = getRequest(map);
		String path = request.getContextPath();
		ComponentUtil ComponentUtil = new ComponentUtil(request);
		String settingsFile = map.get("settingsFile")!=null? map.get("settingsFile").toString():path+"/struts/simple/report/resource/js/amcharts/ampie/ampie_settings.xml";
		
		/********************************生成HTML*********************************************/
		String html=ComponentUtil.writeScript("/struts/simple/report/resource/js/amcharts/swfobject.js")
		+"<div id=\""+map.get("id")+"\"></div>"
		+"<script type=\"text/javascript\">"
		+"var so = new SWFObject(\""+path+"/struts/simple/report/resource/js/amcharts/ampie/ampie.swf\", \"ampie\", \""+width+"\", \""+height+"\", \"8\", \""+backColor+"\");"
		+"so.addParam(\"wmode\", \"transparent\");"
		+"so.addVariable(\"path\", \""+path+"/struts/simple/report/resource/js/amcharts/ampie/\");  "
		+"so.addVariable(\"settings_file\", encodeURIComponent(\""+settingsFile+"\"));"
		+"so.addVariable(\"additional_chart_settings\", \""+chartSettings+"\");"
		+"so.addVariable(\"chart_data\", \""+xml+"\");"
		+"so.addVariable(\"loading_settings\", \"加载数据中。。。\");"
		+"so.addVariable(\"preloader_color\",\"#999999\");"
		+"so.write(\""+map.get("id")+"\");"
		+"</script>";
		/********************************生成HTML*********************************************/
        return html;
	}	
	
	static private HttpServletRequest getRequest(Map map){
		HttpServletRequest request=null;
		if(map.get("request") != null){
			request = (HttpServletRequest)map.get("request");
		}
//		else{
//			request = ServletActionContext.getRequest();
//		}
		return request;
	}
	
	/**
	 * 生成柱状图
	 * @param para
	 * @return
	 */
	public static String lineChart(Map map){
		map.put("chartType","LINE_CHART");
		String width="500"; 
		String height="300";
		//宽度设置
		if(map.get("width") != null){
			width = map.get("width").toString();
		}else{
			map.put("width",width);
		}
		//高度设置
		if(map.get("height") != null){
			height = map.get("height").toString();
		}else{
			map.put("height",height);
		}
		
		
	        
		//背景色
		String backColor="#FFFFFF";
		if(map.get("backColor")!=null)
			backColor=map.get("backColor").toString();
		
		/********************************设置图表数据*********************************************/
		String xml = "";
		List list = (List) map.get("value");
		List rightList = (List) map.get("rightValue");//右边坐标数据
		
		String lineNameStr = (String) map.get("rowTitle");//指标名称设置,例:访问数,IP数
		String colNameStr = (String) map.get("colNames");//取数字段设置,例:title,visitcount,ipcount
		String rightLineNameStr = (String) map.get("rightRowTitle");//指标名称设置,例:访问数,IP数
		String rightColNameStr = (String) map.get("rightColNames");//取数字段设置,例:title,visitcount,ipcount
		String url = map.get("linkUrl")!=null? map.get("linkUrl").toString():null;

		String yleftShapeType = map.get("yleftShapeType")!=null? map.get("yleftShapeType").toString():null;
		String yrightShapeType = map.get("yrightShapeType")!=null? map.get("yrightShapeType").toString():null;
		String yleftShapeSize = map.get("yleftShapeSize")!=null? map.get("yleftShapeSize").toString():null;
		String yrightShapeSize = map.get("yrightShapeSize")!=null? map.get("yrightShapeSize").toString():null;
		String  yleftFillAlphaStr = map.get("yleftFillAlpha")!=null? map.get("yleftFillAlpha").toString():null;
		String yrightFillAlphaStr = map.get("yrightFillAlpha")!=null? map.get("yrightFillAlpha").toString():null;
		String showLineAverageStr = map.get("showLineAverage")!=null? map.get("showLineAverage").toString():null;
		String lineAverageLineColor = map.get("lineAverageLineColor")!=null? map.get("lineAverageLineColor").toString():null;
		String preciousStr = map.get("precious")!=null? map.get("precious").toString():null;
		xml = getLineChartXML(map, list,rightList,lineNameStr,rightLineNameStr, colNameStr, rightColNameStr, url, yleftShapeType, yrightShapeType,yleftShapeSize,yrightShapeSize, yleftFillAlphaStr, yrightFillAlphaStr, showLineAverageStr, lineAverageLineColor, preciousStr);
		xml = TextUtils.htmlEncode(xml.replaceAll("%","%25").replaceAll("&","%26"));
		/********************************设置图表数据 end*********************************************/
		
		String chartSettings = getLineSettings(map);
		HttpServletRequest request = getRequest(map);
		String path = request.getContextPath();
		ComponentUtil ComponentUtil = new ComponentUtil(request);
		String settingsFile = map.get("settingsFile")!=null? map.get("settingsFile").toString():path+"/struts/simple/report/resource/js/amcharts/amline/amline_settings.xml";
		
		/********************************生成HTML*********************************************/
		String html=ComponentUtil.writeScript("/struts/simple/report/resource/js/amcharts/swfobject.js")
		+"<div id=\""+map.get("id")+"\"></div>"
		+"<script type=\"text/javascript\">"
		+"var so = new SWFObject(\""+path+"/struts/simple/report/resource/js/amcharts/amline/amline.swf\", \"amline\", \""+width+"\", \""+height+"\", \"8\", \""+backColor+"\");"
		+"so.addParam(\"wmode\", \"transparent\");"
		+"so.addVariable(\"path\", \""+path+"/struts/simple/report/resource/js/amcharts/amline/\");  "
		+"so.addVariable(\"settings_file\", encodeURIComponent(\""+settingsFile+"\"));"
		+"so.addVariable(\"additional_chart_settings\", \""+chartSettings+"\");"
		+"so.addVariable(\"chart_data\", \""+xml+"\");"
		+"so.addVariable(\"loading_settings\", \"加载数据中。。。\");"
		+"so.addVariable(\"preloader_color\",\"#999999\");"
		+"so.write(\""+map.get("id")+"\");"
		+"</script>";
		/********************************生成HTML*********************************************/
        return html;
	}	
	
	
	
	/**
	 * 生成柱状图
	 * @param para
	 * @return
	 */
	public static String barChart(Map map){
		map.put("chartType","BAR_CHART");
		String width="500"; 
		String height="300";
		//宽度设置
		if(map.get("width") != null){
			width = map.get("width").toString();
		}else{
			map.put("width",width);
		}
		//高度设置
		if(map.get("height") != null){
			height = map.get("height").toString();
		}else{
			map.put("height",height);
		}
		
		
	        
		//背景色
		String backColor="#FFFFFF";
		if(map.get("backColor")!=null)
			backColor=map.get("backColor").toString();
		
		/********************************设置图表数据*********************************************/
		String xml = "";
		List list = (List) map.get("value");
		List rightList = (List) map.get("rightValue");//右边坐标数据
		
		String lineNameStr = (String) map.get("rowTitle");//指标名称设置,例:访问数,IP数
		String colNameStr = (String) map.get("colNames");//取数字段设置,例:title,visitcount,ipcount
		String rightLineNameStr = (String) map.get("rightRowTitle");//指标名称设置,例:访问数,IP数
		String rightColNameStr = (String) map.get("rightColNames");//取数字段设置,例:title,visitcount,ipcount
		String url = map.get("linkUrl")!=null? map.get("linkUrl").toString():null;

		String yleftShapeType = map.get("yleftShapeType")!=null? map.get("yleftShapeType").toString():null;
		String yrightShapeType = map.get("yrightShapeType")!=null? map.get("yrightShapeType").toString():null;
		String yleftFillAlphaStr = map.get("yleftFillAlpha")!=null? map.get("yleftFillAlpha").toString():null;
		String yrightFillAlphaStr = map.get("yrightFillAlpha")!=null? map.get("yrightFillAlpha").toString():null;
		String showBarAverageStr = map.get("showBarAverage")!=null? map.get("showBarAverage").toString():null;
		String barAverageLineColor = map.get("barAverageLineColor")!=null? map.get("barAverageLineColor").toString():null;
		String preciousStr = map.get("precious")!=null? map.get("precious").toString():null;
		xml = getBarChartXML( list,rightList,lineNameStr,rightLineNameStr, colNameStr, rightColNameStr, url, yleftShapeType, yrightShapeType, yleftFillAlphaStr, yrightFillAlphaStr, showBarAverageStr, barAverageLineColor, preciousStr);
		xml = TextUtils.htmlEncode(xml.replaceAll("%","%25").replaceAll("&","%26"));
		/********************************设置图表数据*********************************************/
		
		String chartSettings = getBarSettings(map);
		HttpServletRequest request = getRequest(map);
		String path = request.getContextPath();
		ComponentUtil ComponentUtil = new ComponentUtil(request);
		String settingsFile = map.get("settingsFile")!=null? map.get("settingsFile").toString():path+"/struts/simple/report/resource/js/amcharts/amcolumn/amcolumn_settings.xml";
		
		/********************************生成HTML*********************************************/
		String html=ComponentUtil.writeScript("/struts/simple/report/resource/js/amcharts/swfobject.js")
		+"<div id=\""+map.get("id")+"\"></div>"
		+"<script type=\"text/javascript\">"
		+"var so = new SWFObject(\""+path+"/struts/simple/report/resource/js/amcharts/amcolumn/amcolumn.swf\", \"amcolumn\", \""+width+"\", \""+height+"\", \"8\", \""+backColor+"\");"
		+"so.addParam(\"wmode\", \"transparent\");"
		+"so.addVariable(\"path\", \""+path+"/struts/simple/report/resource/js/amcharts/amcolumn/\");  "
		+"so.addVariable(\"settings_file\", encodeURIComponent(\""+settingsFile+"\"));"
		+"so.addVariable(\"additional_chart_settings\", \""+chartSettings+"\");"
		+"so.addVariable(\"chart_data\", \""+xml+"\");"
		+"so.addVariable(\"loading_settings\", \"加载数据中。。。\");"
		+"so.addVariable(\"preloader_color\",\"#999999\");"
		+"so.write(\""+map.get("id")+"\");"
		+"</script>";
		/********************************生成HTML*********************************************/
        return html;
	}	

	
	private static String getUrl(String colName[],String oldUrl,Map row){
		StringBuffer url = new StringBuffer();
		if(oldUrl != null){
			url.append(oldUrl);
			if(oldUrl.indexOf("?")<0)
				url.append("?");
			else
				url.append("&");
			if(colName != null){
				for (int i = 0; i < colName.length; i++) {
					if(i>0)
						url.append("&");
					url.append(colName[i]).append("=").append(row.get(colName[i]));
				}
			}else{//没有字段名,则按顺序显示.
				Iterator it = ((Map)row).entrySet().iterator(); 
				int i=0;
    			while (it.hasNext()) { 
    				Map.Entry entry = (Map.Entry) it.next(); 
    				Object key = entry.getKey(); 
    				Object tempvalue = entry.getValue();
    				if(i>0)
						url.append("&");
					url.append(key).append("=").append(tempvalue);
    				i++;
    			}
			}
		}
		return url.toString();
	}
	
	/**
	 * 取得饼图设置
	 * @param map
	 * @return
	 */
	private static String getPieSettings(Map map){
		StringBuffer xml = new StringBuffer();
		xml.append("<settings>");
		xml.append(getSettingsBase(map)); //取得图表的基本设置
		xml.append(getSettingsBackground(map)); //取得图表的背景色设置
		
		xml.append(getSettingsStrings(map)); //取得图表提示字符串设置
		xml.append(getSettingsLabels(map)); //取得图表标题设置
		xml.append(getSettingsLegend(map)); //取得图表的子标题设置
		xml.append(getSettingsPie(map)); //取得饼图设置
		xml.append(getSettingsAnimation(map)); //取得饼图的动画效果设置
		xml.append(getSettingsDataLabels(map)); //显示指向信息
		xml.append(getSettingsBalloon(map)); //显示汽泡设置
		xml.append(getSettingsExpImg(map)); //取得图表导出设置
		xml.append("</settings>");
		return TextUtils.htmlEncode(xml.toString().replaceAll("%","%25").replaceAll("&","%26"));
	}
	
	private static String getLineSettings(Map map){
		StringBuffer xml = new StringBuffer();
		xml.append("<settings>");
		xml.append(getSettingsBase(map)); //取得图表的基本设置
		xml.append(getSettingsBackground(map)); //取得图表的背景色设置
		xml.append(getSettingsLineGrid(map)); //取得图表网格设置
		xml.append(getSettingsStrings(map)); //取得图表提示字符串设置
		xml.append(getSettingsLabels(map)); //取得图表标题设置
		xml.append(getSettingsLegend(map)); //取得图表的子标题设置
		xml.append(getSettingsLineAxes(map)); //取得图表的X轴Y轴设置
		xml.append(getSettingsLineValues(map)); //取得图表的X轴Y轴设置
		xml.append(getSettingsBalloon(map)); //显示汽泡设置
		xml.append(getSettingsVerticalLines(map)); //垂直连接线设置
		xml.append(getSettingsIndicator(map)); //显示坐标提供器设置
		xml.append(getSettingsExpImg(map)); //取得图表导出设置
		xml.append("</settings>");
		return TextUtils.htmlEncode(xml.toString().replaceAll("%","%25").replaceAll("&","%26"));
	}
	
	private static String getBarSettings(Map map){
		StringBuffer xml = new StringBuffer();
		xml.append("<settings>");
		xml.append(getSettingsBase(map)); //取得图表的基本设置
		xml.append(getSettingsColumn(map)); //取得柱状图设置
		xml.append(getSettingsBackground(map)); //取得图表的背景色设置
		xml.append(getSettingsBarGrid(map)); //取得图表网格设置
		xml.append(getSettingsStrings(map)); //取得图表提示字符串设置
		xml.append(getSettingsLabels(map)); //取得图表标题设置
		xml.append(getSettingsLegend(map)); //取得图表的子标题设置
		xml.append(getSettingsBarAxes(map)); //取得图表的X轴Y轴设置
		xml.append(getSettingsBarValues(map)); //取得图表的X轴Y轴设置
		xml.append(getSettingsBalloon(map)); //显示汽泡设置
		xml.append(getSettingsVerticalLines(map)); //垂直连接线设置
		xml.append(getSettingsIndicator(map)); //显示坐标提供器设置
		xml.append(getSettingsExpImg(map)); //取得图表导出设置
		xml.append("</settings>");
		return TextUtils.htmlEncode(xml.toString().replaceAll("%","%25").replaceAll("&","%26"));
	}
	
	/**
	 * 取得图表导出设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsExpImg(Map map){
		StringBuffer xml = new StringBuffer();
		HttpServletRequest request = getRequest(map);
		String path = request.getContextPath();
		xml.append("<export_as_image>");
		xml.append("<file>");
		xml.append(path).append("/comm/servlet.shtml?servlet_name=exportImage");
		xml.append("</file>");
		xml.append("</export_as_image>");
		return xml.toString();
	}

	/**
	 * 柱状图设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsColumn(Map map){
		StringBuffer xml = new StringBuffer();


		//是否需要垂直连接线
		if(map.get("barType")!=null){
			String barType = map.get("barType").toString();
			if(barType.equals("simple")||barType.equals("group"))
				barType = "clustered";
			if(barType.equals("stack"))
				barType = "stacked";
			xml.append("<type>").append(map.get("barType")).append("</type>");
		}
		

		//柱状宽度
		if(map.get("barWidth")!=null){
			xml.append("<width>").append(map.get("barWidth")).append("</width>");
		}

		//柱子之间的距离
		if(map.get("barMargin")!=null){
			xml.append("<spacing>").append(map.get("barMargin")).append("</spacing>");
		}


		//透明度调节，调节范围(0-1)，默认为1
		xml.append(getAlpha(map.get("alpha"),"alpha"));
		

		//是否在柱子上显示数据
		if(map.get("showDataTitle")!=null&&map.get("showDataTitle").equals("true")){
			if(map.get("dataTitle")!=null){
				xml.append("<data_labels><![CDATA[").append(map.get("dataTitle")).append("]]></data_labels>");
			}else{
				xml.append("<data_labels><![CDATA[{value}]]></data_labels>");
			}

			//数据在柱状内 inside/outside
			if(map.get("dataTitlePosition")!=null){
				xml.append("<data_labels_position>").append(map.get("dataTitlePosition")).append("</data_labels_position>");
			}

			//数据在柱状内 inside/outside
			if(map.get("dataTitleFontSize")!=null){
				xml.append("<data_labels_text_size>").append(map.get("dataTitleFontSize")).append("</data_labels_text_size>");
			}
			//数据在柱状内 inside/outside
			if(map.get("dataTitleFontColor")!=null){
				xml.append("<data_labels_text_color>").append(map.get("dataTitleFontColor")).append("</data_labels_text_color>");
			}
		}

		//是否显示汽泡
		if(map.get("showToolTip")!=null){
			if(map.get("showToolTip").toString().equals("true")){
				if(map.get("toolTipFlag")!=null ){
					String toolTipFlag = map.get("toolTipFlag").toString();
					if(toolTipFlag.equals("showTitle")){
						xml.append("<balloon_text><![CDATA[{title}]]></balloon_text>");
					}else if(toolTipFlag.equals("showValue")){
						xml.append("<balloon_text><![CDATA[{value}]]></balloon_text>");
					}else if(toolTipFlag.equals("showPercent")){
						xml.append("<balloon_text><![CDATA[{percents}%]]></balloon_text>");
					}else if(toolTipFlag.equals("showTitlePercent")){
						xml.append("<balloon_text><![CDATA[{title}<br>{percents}%]]></balloon_text>");
					}else if(toolTipFlag.equals("showTitleValue")){
						xml.append("<balloon_text><![CDATA[{title}<br>{value}]]></balloon_text>");
					}
				}else if(map.get("toolTipTitle")!=null ){
					xml.append("<balloon_text><![CDATA[").append(map.get("toolTipTitle")).append("]]></balloon_text>");
				}
			} else{
				xml.append("<balloon_text><![CDATA[{}]]></balloon_text>");
			}
		}

		//链接提交窗口
		if(map.get("linkTarget")!=null){
			xml.append("<link_target>").append(map.get("linkTarget")).append("</link_target>");
		}


		//柱状图生长动画效果时长,秒
		if(map.get("growTime")!=null){
			xml.append("<grow_time>").append(map.get("growTime")).append("</grow_time>");
		}


		//按顺序生长还是同时生长,true为按顺序生长,false为同时生长
		if(map.get("sequencedGrow")!=null){
			xml.append("<sequenced_grow>").append(map.get("sequencedGrow")).append("</sequenced_grow>");
		}


		//可选的动画效果:
		if(map.get("growEffect")!=null){
			xml.append("<grow_effect>").append(map.get("growEffect")).append("</grow_effect>");
		}

		if(xml.length()>0){
			xml.insert(0,"<column>");
			xml.append("</column>");
		}
		return xml.toString();
	}
	
	private static String getAlpha(Object alphaStr,String tag){
		StringBuffer xml = new StringBuffer();
		if(alphaStr != null){
			try {
				double alpha = Double.parseDouble(alphaStr.toString());
				if(alpha<=1)
					alpha = alpha*100;
				xml.append("<").append(tag).append(">").append(alpha).append("</").append(tag).append(">");
			} catch (Exception e) {
			}
		}
		return xml.toString();
	}
	
	/**
	 * 显示坐标提供器设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsIndicator(Map map){
		StringBuffer xml = new StringBuffer();

		if(map.get("indicator")!=null&& !map.get("indicator").toString().equals("false")){
			HttpServletRequest request = null;
			if(map.get("request") != null){
				request = (HttpServletRequest)map.get("request");
			}
//			else{
//				request = ServletActionContext.getRequest();
//			}
//			String path = request.getContextPath();
			xml.append("<plugins><plugin file=\"plugins/value_indicator.swf\" position=\"above\">");
			xml.append("<chart_type>").append(map.get("indicator").toString().equals("true")?"line":map.get("indicator")).append("</chart_type>");

			if(map.get("indicatorLineColor")!=null){
				xml.append("<line_color>").append(map.get("indicatorLineColor")).append("</line_color>");
			}
			if(map.get("indicatorTextColor")!=null){
				xml.append("<text_color>").append(map.get("indicatorTextColor")).append("</text_color>");
			}else{
				xml.append("<text_color>#000000</text_color>");
			}
			if(map.get("indicatorTextSize")!=null){
				xml.append("<text_size>").append(map.get("indicatorTextSize")).append("</text_size>");
			}else{
				xml.append("<text_size>12</text_size>");
			}
			if(map.get("precious")!=null){
				xml.append("<precision>").append(map.get("precious")).append("</precision>");
			}else{
				xml.append("<precision>2</precision>");
			}
			xml.append("<axis>left</axis>");
			
			xml.append("</plugin></plugins>");
		}
		return xml.toString();
	}
	/**
	 * 取得垂直连接线设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsVerticalLines(Map map){
		StringBuffer xml = new StringBuffer();
		String xmlStr = "";
		StringBuffer rightXml = new StringBuffer();
		Integer yLeftGraphSize=(Integer)map.get("yLeftGraphSize");
		Integer yRightGraphSize=(Integer)map.get("yRightGraphSize");
		if(yLeftGraphSize ==null)
			yLeftGraphSize = 0;
		if(yRightGraphSize ==null)
			yRightGraphSize = 0;
		//是否需要垂直连接线
		if(map.get("yleftVerticalLines")!=null&&map.get("yleftVerticalLines").toString().equalsIgnoreCase("true")){
			xml.append("<vertical_lines>").append(map.get("yleftVerticalLines")).append("</vertical_lines>");
//			//垂直连接线宽度
//			if(map.get("verticalLinesWidth")!=null){
//				xml.append("<vertical_lines><width>").append(map.get("verticalLinesWidth")).append("</width></vertical_lines>");
//			}
		}

		//线条透明度
		xml.append(getAlpha(map.get("yleftLineAlpha"),"line_alpha"));

		//线条宽度,默认为2
		if(map.get("yleftLineWidth")!=null){
			xml.append("<line_width>").append(map.get("yleftLineWidth")).append("</line_width>");
		}else{
			xml.append("<line_width>2</line_width>");
		}
		
		//汽泡内显示内容
		if(map.get("yleftBalloonText")!=null){
			xml.append("<balloon_text><![CDATA[").append(map.get("yleftBalloonText")).append("]]></balloon_text>");
		}
		//默认结点类型为有外线纹圆圈
		if(map.get("yleftShapeType")==null)
			xml.append("<bullet>round_outlined</bullet>");
		
		//xml.append("<data_labels><![CDATA[mytest{title}]]></data_labels>");
		
		
		for(int i=1;i<=yLeftGraphSize;i++){
			xmlStr+="<graph gid=\""+i+"\"><axis>left</axis>" + xml+"</graph>";
		}
		
		//是否需要垂直连接线
		if(map.get("yrightVerticalLines")!=null&&map.get("yrightVerticalLines").toString().equalsIgnoreCase("true")){
			rightXml.append("<vertical_lines>").append(map.get("yrightVerticalLines")).append("</vertical_lines>");
			//垂直连接线宽度
//			if(map.get("verticalLinesWidth")!=null){
//				rightXml.append("<vertical_lines><width>").append(map.get("verticalLinesWidth")).append("</width></vertical_lines>");
//			}
		}

		
		//线条透明度
		rightXml.append(getAlpha(map.get("yrightLineAlpha"),"line_alpha"));

		//线条宽度,默认为2
		if(map.get("yrightLineWidth")!=null){
			rightXml.append("<line_width>").append(map.get("yrightLineWidth")).append("</line_width>");
		}else{
			xml.append("<line_width>2</line_width>");
		}
		//汽泡内显示内容
		if(map.get("yrightBalloonText")!=null){
			rightXml.append("<balloon_text><![CDATA[").append(map.get("yrightBalloonText")).append("]]></balloon_text>");
		}
		
		//默认结点类型为有外线纹圆圈
		if(map.get("yrightShapeType")==null)
			rightXml.append("<bullet>round_outlined</bullet>");
		
		for(int i=1;i<=yLeftGraphSize;i++){
			xmlStr+="<graph gid=\""+(yLeftGraphSize+i)+"\"><axis>right</axis>" + rightXml+"</graph>";
		}
		xmlStr="<graphs>" + xmlStr+"</graphs>";
		
	
		
		return xmlStr;
	}
	/**
	 * 取得饼图设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsPie(Map map){
		StringBuffer xml = new StringBuffer();

		int pieRadius=100;
		int width=500;
		int height=300;
		boolean isWidthPercent = false;
		boolean isHeightPercent = false;
		if(map.get("width") != null){
			if(map.get("width").toString().indexOf("%")>-1)
				isWidthPercent = true;
			else{
				try {
					width = Integer.parseInt(map.get("width").toString());
				} catch (Exception e) {
				}
			}
		}
		if(map.get("height") != null){
			if(map.get("height").toString().indexOf("%")>-1)
				isHeightPercent = true;
			else{
				try {
					height = Integer.parseInt(map.get("height").toString());
				} catch (Exception e) {
				}
			}
		}
			
		//饼图外半径
		if(map.get("pieRadius")!=null){
			try {
				xml.append("<radius>").append(map.get("pieRadius")).append("</radius>");
				pieRadius = Integer.parseInt(map.get("pieRadius").toString().replaceAll("%",""));
			} catch (Exception e) {
			}
		}

		//饼图内半径,默认为零
		if(map.get("pieInnerRadius")!=null){
			xml.append("<inner_radius>").append(map.get("pieInnerRadius")).append("</inner_radius>");
		}
		
		
		if(map.get("plotType")!=null && map.get("plotType").equals("2D")){
			xml.append("<height>0</height>");
		}else	//饼图的高度
		if(map.get("pieHeight")!=null){
			xml.append("<height>").append(map.get("pieHeight")).append("</height>");
		}

		//饼图的视角,0~90
		if(map.get("pieAngle")!=null){
			xml.append("<angle>").append(map.get("pieAngle")).append("</angle>");
		}

		//饼图的颜色
		if(map.get("color")!=null){
			xml.append("<colors>[").append(map.get("color")).append("]</colors>");
		}

		//链接提交窗口
		if(map.get("linkTarget")!=null){
			xml.append("<link_target>").append(map.get("linkTarget")).append("</link_target>");
		}

		//透明度调节，调节范围(0-100)
		xml.append(getAlpha(map.get("alpha"),"alpha"));

		
		
		//		子标题位置
		boolean isShowSubTitle = map.get("showSubTitle") !=null && map.get("showSubTitle").toString().equalsIgnoreCase("true");
		if(isShowSubTitle){
			if(map.get("subTitlePosition")==null){
				map.put("subTitlePosition","bottom");
			}
				String subTitlePosition = map.get("subTitlePosition").toString();
				int subTitleWidth = 150;
				if(map.get("subTitleWidth")!=null){
					try {
						subTitleWidth =Integer.parseInt(map.get("subTitleWidth").toString());
					} catch (Exception e) {
					}
				}
				int iTopTitle = 0;
				if(map.get("title")!=null){
					iTopTitle = 30;
				}
		
				//位置(left,top,right,bottom),默认为bottom
				if(subTitlePosition.equals("left")){
					if(isWidthPercent)
						xml.append("<x>").append("75%").append("</x>");	
					else
						xml.append("<x>").append(10+subTitleWidth+(width-10-subTitleWidth)/2).append("</x>");	
					if(isHeightPercent)
						xml.append("<y>").append(iTopTitle>0?"55%":"50%").append("</y>");	
					else
						xml.append("<y>").append(10+iTopTitle+(height-10-iTopTitle)/2).append("</y>");	
				}else if(subTitlePosition.equals("right")){
					if(isWidthPercent)
						xml.append("<x>").append("35%").append("</x>");	
					else
						xml.append("<x>").append(10+(width-10-subTitleWidth)/2).append("</x>");	
					if(isHeightPercent)
						xml.append("<y>").append(iTopTitle>0?"55%":"50%").append("</y>");	
					else
						xml.append("<y>").append(10+iTopTitle+(height-10-iTopTitle)/2).append("</y>");	
				}else if(subTitlePosition.equals("top")){
					xml.append("<x>50%</x>");
					if(isHeightPercent)
						xml.append("<y>").append(iTopTitle>0?"70%":"60%").append("</y>");	
					else
						xml.append("<y>").append(60+iTopTitle+(height-60-iTopTitle)/2).append("</y>");	
				}else{
					xml.append("<x>50%</x>");
					if(isHeightPercent)
						xml.append("<y>").append(iTopTitle>0?"40%":"35%").append("</y>");	
					else
						xml.append("<y>").append(10+iTopTitle+(height-10-iTopTitle-80)/2).append("</y>");	
				}
			
		}else {
			xml.append("<x>50%</x>");
			xml.append("<y>50%</y>");	
		}
		
		if(xml.length()>0){
			xml.insert(0,"<pie>");
			xml.append("</pie>");
		}
		return xml.toString();
	}

	
	/**
	 * 显示指向信息
	 * @param map
	 * @return
	 */
	private static  String getSettingsDataLabels(Map map){
		StringBuffer xml = new StringBuffer();

		//是否显示指向信息 (true/false)
		if(map.get("showPlot")!=null && map.get("showPlot").toString().equals("true")){
			if(map.get("plotFlag")!=null ){
				String plotFlag = map.get("plotFlag").toString();
				if(plotFlag.equals("showTitle")){
					xml.append("<show><![CDATA[{title}]]></show>");
				}else if(plotFlag.equals("showValue")){
					xml.append("<show><![CDATA[{value}]]></show>");
				}else if(plotFlag.equals("showPercent")){
					xml.append("<show><![CDATA[{percents}%]]></show>");
				}else if(plotFlag.equals("showTitlePercent")){
					xml.append("<show><![CDATA[{title}:{percents}%]]></show>");
				}else if(plotFlag.equals("showTitleValue")){
					xml.append("<show><![CDATA[{title}:{value}]]></show>");
				}
			}else if(map.get("plotTitle")!=null ){
				xml.append("<show><![CDATA[").append(map.get("plotTitle")).append("]]></show>");
			}else{
				xml.append("<show><![CDATA[{title}:{percents}%]]></show>");
			}
			if(map.get("plotFontSize")!=null ){
				xml.append("<text_size>").append(map.get("plotFontSize")).append("</text_size>");
			}
			if(map.get("plotFontColor")!=null ){
				xml.append("<text_color>").append(map.get("plotFontColor")).append("</text_color>");
			}
			
		}
		
		if(xml.length()>0){
			xml.insert(0,"<data_labels>");
			xml.append("</data_labels>");
		}
		return xml.toString();
	}

	/**
	 * 取得饼图的动画效果设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsAnimation(Map map){
		StringBuffer xml = new StringBuffer();

		//动画效果时长,秒
		if(map.get("animationStartTime")!=null){
			xml.append("<start_time>").append(map.get("animationStartTime")).append("</start_time>");
		}
		//可选的动画效果:
		if(map.get("animationStartEffect")!=null){
			xml.append("<start_effect>").append(map.get("animationStartEffect")).append("</start_effect>");
		}

		//饼图开始收缩前大小,用百分比,如120%
		if(map.get("animationStartRadius")!=null){
			xml.append("<start_radius>").append(map.get("animationStartRadius")).append("</start_radius>");
		}
		//饼图开始收缩前透明度.调节范围(0-100)
		xml.append(getAlpha(map.get("animationStartAlpha"),"start_alpha"));
		
		if(xml.length()>0){
			xml.insert(0,"<animation>");
			xml.append("</animation>");
		}
		return xml.toString();
	}
	
	/**
	 * 取得图表的X轴Y轴设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsLineAxes(Map map){
		StringBuffer xml = new StringBuffer();
		

		//水平文字颜色
		if(map.get("yleftType")!=null){
			xml.append("<y_left><type>").append(map.get("yleftType")).append("</type></y_left>");
		}
		//水平文字大小
		if(map.get("yrightType")!=null){
			xml.append("<y_right><type>").append(map.get("yrightType")).append("</type></y_right>");
		}
		
		if(xml.length()>0){
			xml.insert(0,"<axes>");
			xml.append("</axes>");
		}
		return xml.toString();
	}
	/**
	 * 取得图表的X轴Y轴设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsBarAxes(Map map){
		StringBuffer xml = new StringBuffer();
		
		//显示横坐标
		if(map.get("showXAxes")!=null&& map.get("showXAxes").equals("false")){
			xml.append("<category><alpha>0</alpha></category>");
		}
		

		//显示纵坐标
		if(map.get("showYAxes")!=null&& map.get("showYAxes").equals("false")){
			xml.append("<value><alpha>0</alpha></value>");
		}
		
		if(xml.length()>0){
			xml.insert(0,"<axes>");
			xml.append("</axes>");
		}
		return xml.toString();
	}
	

	/**
	 * 取得图表的X轴Y轴设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsLineValues(Map map){
		StringBuffer xml = new StringBuffer();
		

		//显示横坐标
		if(map.get("showXAxes")!=null){
			xml.append("<enabled>").append(map.get("showXAxes")).append("</enabled>");
		}
		//水平文字颜色
		if(map.get("xfontColor")!=null){
			xml.append("<color>").append(map.get("xfontColor")).append("</color>");
		}
		//水平文字大小
		if(map.get("xfontSize")!=null){
			xml.append("<text_size>").append(map.get("xfontSize")).append("</text_size>");
		}
		//水平文字位置 默认bottom 可选项 top/bottom
		if(map.get("xposition")!=null && (map.get("xposition").equals("top")|| map.get("xposition").equals("inside"))){
			xml.append("<inside>true</inside>");
		}
		
		//水平文字倾斜度
		if(map.get("xrotate")!=null){
			xml.append("<rotate>").append(map.get("xrotate")).append("</rotate>");
		}
		if(xml.length()>0){
			xml.insert(0,"<x>");
			xml.append("</x>");
		}
		
		StringBuffer xmlY = new StringBuffer();

		//显示纵坐标
		if(map.get("showYAxes")!=null){
			xmlY.append("<enabled>").append(map.get("showYAxes")).append("</enabled>");
		}
		//垂直文字颜色
		if(map.get("yfontColor")!=null){
			xmlY.append("<color>").append(map.get("yfontColor")).append("</color>");
		}
		//垂直文字大小
		if(map.get("yfontSize")!=null){
			xmlY.append("<text_size>").append(map.get("yfontSize")).append("</text_size>");
		}
		//垂直文字位置 默认bottom 可选项 top/bottom
		if(map.get("yposition")!=null && (map.get("yposition").equals("right")||map.get("yposition").equals("inside"))){
			xmlY.append("<inside>true</inside>");
		}
		
		//垂直文字倾斜度
		if(map.get("yrotate")!=null){
			xmlY.append("<rotate>").append(map.get("yrotate")).append("</rotate>");
		}
		if(xmlY.length()>0){
			xml.append("<y_left>").append(xmlY).append("</y_left>");
			xml.append("<y_right>").append(xmlY).append("</y_right>");
		}
		
		
		if(xml.length()>0){
			xml.insert(0,"<values>");
			xml.append("</values>");
		}
		return xml.toString();
	}
	

	/**
	 * 取得图表的X轴Y轴设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsBarValues(Map map){
		StringBuffer xml = new StringBuffer();
		
		
		//显示横坐标
		if(map.get("showXAxes")!=null){
			xml.append("<enabled>").append(map.get("showXAxes")).append("</enabled>");
		}
		//水平文字颜色
		if(map.get("xfontColor")!=null){
			xml.append("<color>").append(map.get("xfontColor")).append("</color>");
		}
		//水平文字大小
		if(map.get("xfontSize")!=null){
			xml.append("<text_size>").append(map.get("xfontSize")).append("</text_size>");
		}
		//水平文字位置 默认bottom 可选项 top/bottom
		if(map.get("xposition")!=null && (map.get("xposition").equals("top")||map.get("xposition").equals("inside"))){
			xml.append("<inside>true</inside>");
		}
		
		//水平文字倾斜度
		if(map.get("xrotate")!=null){
			xml.append("<rotate>").append(map.get("xrotate")).append("</rotate>");
		}
		if(xml.length()>0){
			xml.insert(0,"<category>");
			xml.append("</category>");
		}
		
		StringBuffer xmlY = new StringBuffer();


		//显示纵坐标
		if(map.get("showYAxes")!=null){
			xmlY.append("<enabled>").append(map.get("showYAxes")).append("</enabled>");
		}
		//垂直文字颜色
		if(map.get("yfontColor")!=null){
			xmlY.append("<color>").append(map.get("yfontColor")).append("</color>");
		}
		//垂直文字大小
		if(map.get("yfontSize")!=null){
			xmlY.append("<text_size>").append(map.get("yfontSize")).append("</text_size>");
		}
		//垂直文字位置 默认bottom 可选项 top/bottom
		if(map.get("yposition")!=null && (map.get("yposition").equals("right")||map.get("yposition").equals("inside"))){
			xmlY.append("<inside>true</inside>");
		}
		
		//垂直文字倾斜度
		if(map.get("yrotate")!=null){
			xmlY.append("<rotate>").append(map.get("yrotate")).append("</rotate>");
		}
		if(xmlY.length()>0){
			xml.append("<value>").append(xmlY).append("</value>");
		}
		
		
		if(xml.length()>0){
			xml.insert(0,"<values>");
			xml.append("</values>");
		}
		return xml.toString();
	}
	/**
	 * 取得图表的子标题设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsLegend(Map map){
		StringBuffer xml = new StringBuffer();
		StringBuffer plotXml = new StringBuffer();
		
		//是否显示子标题
		if(map.get("showSubTitle")!=null){
			xml.append("<enabled>").append(map.get("showSubTitle")).append("</enabled>");
		}
		
		int iTopTitle = 0;
		if(map.get("title")!=null){
			iTopTitle = 30;
		}
		
		if(!(map.get("showSubTitle")!=null && map.get("showSubTitle").toString().equalsIgnoreCase("false"))){
			//子标题大小 
			if(map.get("subTitleFontSize")!=null){
				xml.append("<text_size>").append(map.get("subTitleFontSize")).append("</text_size>");
			}
			//子标题颜色
			if(map.get("subTitleFontColor")!=null){
				xml.append("<text_color>").append(map.get("subTitleFontColor")).append("</text_color>");
			}
	
			//子标题背景色
			if(map.get("subTitleBackColor")!=null){
				xml.append("<color>").append(map.get("subTitleBackColor")).append("</color>");
			}
			//子标题位置
			if(map.get("subTitlePosition")==null){
				map.put("subTitlePosition","bottom");
			}
			if(map.get("subTitlePosition")!=null){
				String subTitlePosition = map.get("subTitlePosition").toString();
				
				int subTitleWidth = 150;
				if(map.get("subTitleWidth")!=null){
					try {
						subTitleWidth =Integer.parseInt(map.get("subTitleWidth").toString());
					} catch (Exception e) {
					}
				}
				
				//位置(left,top,right,bottom),默认为bottom
				if(subTitlePosition.equals("left")){
					xml.append("<x>10</x>");
					xml.append("<y>").append(10+iTopTitle).append("</y>");
					if(map.get("subTitleMaxColumns") != null)
						xml.append("<max_columns>").append(map.get("subTitleMaxColumns")).append("</max_columns>");
					else
						xml.append("<max_columns>1</max_columns>");
					xml.append("<width>").append(subTitleWidth).append("</width>");
					if(!map.get("chartType").equals("PIE_CHART"))
						plotXml.append("<plot_area><margins><left>").append(50+subTitleWidth).append("</left><top>").append(30+iTopTitle).append("</top><right>30</right><bottom>50</bottom></margins></plot_area>");
				}else if(subTitlePosition.equals("right")){
					
					if(map.get("subTitleMaxColumns") != null)
						xml.append("<max_columns>").append(map.get("subTitleMaxColumns")).append("</max_columns>");
					else
						xml.append("<max_columns>1</max_columns>");
					
					String width = map.get("width").toString();
					int iWidth=0;
					String plotRight="";
					if(width.indexOf("%")>-1){
						width = width.replaceAll("%","");
						iWidth = Integer.parseInt(width);
						plotRight = "18%"; //plotRight = ""+(iWidth*0.18)+"%";
						xml.append("<x>84%</x>");//xml.append("<x>").append(iWidth*0.84).append("%</x>");
						if(map.get("subTitleWidth") != null)
							xml.append("<width>").append(subTitleWidth).append("</width>");
						else
							xml.append("<width>15%</width>"); //xml.append("<width>").append(iWidth*0.15).append("%</width>");
					}else{
						width = width.toUpperCase().replaceAll("PX","");
						iWidth = Integer.parseInt(width);
						plotRight = ""+(subTitleWidth+50);
						xml.append("<x>").append(iWidth-subTitleWidth-20).append("</x>");
						if(map.get("subTitleWidth") != null)
							xml.append("<width>").append(subTitleWidth).append("</width>");
						else
							xml.append("<width>150</width>");
					}
					xml.append("<y>").append(10+iTopTitle).append("</y>");
					if(!map.get("chartType").equals("PIE_CHART"))
						plotXml.append("<plot_area><margins><left>50</left><top>").append(30+iTopTitle).append("</top><right>").append(plotRight).append("</right> <bottom>50</bottom></margins></plot_area>");
					
				}else if(subTitlePosition.equals("top")){
					if(map.get("subTitleMaxColumns") != null)
						xml.append("<max_columns>").append(map.get("subTitleMaxColumns")).append("</max_columns>");
					xml.append("<y>").append(10+iTopTitle).append("</y>");
					if(map.get("subTitleWidth") != null)
						xml.append("<width>").append(subTitleWidth).append("</width>");
					if(!map.get("chartType").equals("PIE_CHART"))
						plotXml.append("<plot_area><margins><left>50</left><top>").append(70+iTopTitle).append("</top><right>30</right><bottom>50</bottom></margins></plot_area>");
					
				}else{
					if(map.get("subTitleMaxColumns") != null)
						xml.append("<max_columns>").append(map.get("subTitleMaxColumns")).append("</max_columns>");
					if(map.get("subTitleWidth") != null)
						xml.append("<width>").append(subTitleWidth).append("</width>");
				}
				
			}
			//子标题内容
			if(map.get("subTitleFlag")!=null){
				String subTitleFlag = map.get("subTitleFlag").toString();
				if(subTitleFlag.equals("showValue")){
					xml.append("<values><text><![CDATA[: {value}]]></text></values>");
				}else if(subTitleFlag.equals("showPercent")){
					xml.append("<values><text><![CDATA[: {percents}%]]></text></values>");
				}	
			}else if(map.get("subTitle")!=null){
				xml.append("<values><text><![CDATA[").append(map.get("subTitle")).append("]]></text></values>");
			}
		
		}else{
			if(!map.get("chartType").equals("PIE_CHART"))
				plotXml.append("<plot_area><margins><left>50</left><top>").append(30+iTopTitle).append("</top><right>30</right><bottom>50</bottom></margins></plot_area>");

		}
		
		if(xml.length()>0){
			xml.insert(0,"<legend>");
			xml.append("</legend>");
		}
		return xml.toString()+plotXml.toString();
	}
	
	
	/**
	 * 取得图表的汽泡设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsBalloon(Map map){
		StringBuffer xml = new StringBuffer();
		
		//是否显示汽泡
		if(map.get("showToolTip")!=null){
			xml.append("<enabled>").append(map.get("showToolTip")).append("</enabled>");
		}
		

		if(map.get("toolTipFlag")!=null ){
			String toolTipFlag = map.get("toolTipFlag").toString();
			if(toolTipFlag.equals("showTitle")){
				xml.append("<show><![CDATA[{title}]]></show>");
			}else if(toolTipFlag.equals("showValue")){
				xml.append("<show><![CDATA[{value}]]></show>");
			}else if(toolTipFlag.equals("showPercent")){
				xml.append("<show><![CDATA[{percents}%]]></show>");
			}else if(toolTipFlag.equals("showTitlePercent")){
				xml.append("<show><![CDATA[{title}<br>{percents}%]]></show>");
			}else if(toolTipFlag.equals("showTitleValue")){
				xml.append("<show><![CDATA[{title}<br>{value}]]></show>");
			}
		}else if(map.get("toolTipTitle")!=null ){
			xml.append("<show><![CDATA[").append(map.get("toolTipTitle")).append("]]></show>");
		}


		//显示提示信息背景透明度
		xml.append(getAlpha(map.get("toolTipAlpha"),"alpha"));


		//显示提示信息字体颜色
		if(map.get("toolTipFontColor")!=null){
			xml.append("<text_color>").append(map.get("toolTipFontColor")).append("</text_color>");
		}

		//显示提示信息字体大小
		if(map.get("toolTipFontSize")!=null){
			xml.append("<text_size>").append(map.get("toolTipFontSize")).append("</text_size>");
		}

		//显示提示信息的边框线条宽度
		if(map.get("toolTipBorderWidth")!=null){
			xml.append("<border_width>").append(map.get("toolTipBorderWidth")).append("</border_width>");
		}

		//显示提示信息边框线条透明度
		xml.append(getAlpha(map.get("toolTipBorderAlpha"),"border_alpha"));

		//显示提示信息边框线条颜色
		if(map.get("toolTipBorderColor")!=null){
			xml.append("<border_color>").append(map.get("toolTipBorderColor")).append("</border_color>");
		}
		
		//显示提示信息框的圆角大小
		if(map.get("toolTipCornerRadius")!=null){
			xml.append("<corner_radius>").append(map.get("toolTipCornerRadius")).append("</corner_radius>");
		}
		
		
		if(xml.length()>0){
			xml.insert(0,"<balloon>");
			xml.append("</balloon>");
		}
		return xml.toString();
	}
	/**
	 * 取得图表的基本设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsBase(Map map){
		StringBuffer xml = new StringBuffer();
		int precious=2;
	
		//小数点位数
		if(map.get("precious")!=null){
			try{
				precious=Integer.parseInt(map.get("precious").toString());
			}catch(Exception ex){
				System.out.println("精确度设置有误precious，已用默认保留2位小数位处理");
			}
		}
		if(map.get("precious")!=null){
			xml.append("<precision>").append(precious).append("</precision>");
		}
		
		//链接提交窗口
		if(map.get("linkTarget")!=null){
			xml.append("<link_target>").append(map.get("linkTarget")).append("</link_target>");
		}
		
		// 设置柱状颜色
		if (map.get("color") != null) {
			xml.append("<colors>[").append(map.get("color")).append("]</colors>");
		}

		// 设置字体
		if (map.get("titleFontType") != null) {
			xml.append("<font>").append(map.get("titleFontType")).append("</font>");
		}

		// 是否从坐标开始显示数据
		if (map.get("startOnAxis") != null) {
			xml.append("<start_on_axis>").append(map.get("startOnAxis")).append("</start_on_axis>");
		}
		

		// 图形类型(2D,3D)，默认为2D
		if (map.get("chartType").equals("BAR_CHART")) {

			// Xy轴切换, 默认为vertical，horizontal可供选择
			if (map.get("plotOrientation") != null && map.get("plotOrientation").equals("horizontal")) {
				xml.append("<type>bar</type>");
			}
			
			if (map.get("plotType") != null && map.get("plotType").equals("2D")){
				xml.append("<depth>0</depth>");
			}else if(map.get("depth")!=null ){ //柱体深度
				xml.append("<depth>").append(map.get("depth")).append("</depth>");
			}

			// 角度
			if (map.get("angle") != null) {
				xml.append("<angle>").append(map.get("angle")).append("</angle>");
			}
			
		}

		
		return xml.toString();
	}
	
	
	/**
	 * 取得图表的背景色设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsBackground(Map map){
		StringBuffer xml = new StringBuffer();
		//设置背景色
		if (map.get("backColor") != null) {
			xml.append("<color>").append(map.get("backColor")).append("</color>");
		}
		//设置背景色透明度
		if (map.get("backAlpha") != null) {
			xml.append("<alpha>").append(map.get("backAlpha")).append("</alpha>");
		}
		//设置背景图,用绝对路径
		if (map.get("background") != null) {
			xml.append("<file>../../../../../../../../").append(map.get("background")).append("</file>");
		}

		//边框颜色
		if (map.get("borderColor") != null) {
			xml.append("<border_color>").append(map.get("borderColor")).append("</border_color>");
		}

		//边框透明度
		if (map.get("borderAlpha") != null) {
			xml.append("<border_alpha>").append(map.get("borderAlpha")).append("</border_alpha>");
		}
		
		if(xml.length()>0){
			xml.insert(0,"<background>");
			xml.append("</background>");
		}
		return xml.toString();
	}
	
	/**
	 * 取得图表网格设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsLineGrid(Map map){
		StringBuffer xml = new StringBuffer();
		
		
		//设置X轴是否显示网格线,与颜色
		if (map.get("showXLine") != null||map.get("xlineColor") != null) {
			xml.append("<x>");
			if (map.get("showXLine") != null) 
				xml.append("<enabled>").append(map.get("showXLine")).append("</enabled>");
			if (map.get("xlineColor") != null) 
				xml.append("<color>").append(map.get("xlineColor")).append("</color>");
			xml.append("</x>");
		}
		
		//设置Y轴是否显示网格线,与颜色
		if (map.get("showYLine") != null||map.get("ylineColor") != null) {
			xml.append("<y_left>");
			if (map.get("showYLine") != null) 
				xml.append("<enabled>").append(map.get("showYLine")).append("</enabled>");
			if (map.get("ylineColor") != null) 
				xml.append("<color>").append(map.get("ylineColor")).append("</color>");
			xml.append("</y_left>");
			
			xml.append("<y_right>");
			if (map.get("showYLine") != null) 
				xml.append("<enabled>").append(map.get("showYLine")).append("</enabled>");
			if (map.get("ylineColor") != null) 
				xml.append("<color>").append(map.get("ylineColor")).append("</color>");
			xml.append("</y_right>");
		}
		if(xml.length()>0){
			xml.insert(0,"<grid>");
			xml.append("</grid>");
		}
		return xml.toString();
	}
	
	/**
	 * 取得图表网格设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsBarGrid(Map map){
		StringBuffer xml = new StringBuffer();
		
		
		//设置X轴是否显示网格线,与颜色
		if (map.get("showXLine") != null||map.get("xlineColor") != null) {
			xml.append("<category>");
			if (map.get("showXLine") != null && map.get("showXLine").equals("false")) 
				xml.append("<alpha>0</alpha>");
			if (map.get("xlineColor") != null) 
				xml.append("<color>").append(map.get("xlineColor")).append("</color>");
			xml.append("</category>");
		}
		
		//设置Y轴是否显示网格线,与颜色
		if (map.get("showYLine") != null||map.get("ylineColor") != null) {
			xml.append("<value>");
			if (map.get("showYLine") != null && map.get("showYLine").equals("false")) 
				xml.append("<alpha>0</alpha>");
			if (map.get("ylineColor") != null) 
				xml.append("<color>").append(map.get("ylineColor")).append("</color>");
			xml.append("</value>");

		}
		if(xml.length()>0){
			xml.insert(0,"<grid>");
			xml.append("</grid>");
		}
		return xml.toString();
	}
	
	/**
	 * 取得图表提示字符串设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsStrings(Map map){
		StringBuffer xml = new StringBuffer();

		//设置柱状颜色
		if (map.get("noDataMessage") != null) {
			xml.append("<no_data>").append(map.get("noDataMessage")).append("</no_data>");
			xml.append("<error_in_data_file>").append(map.get("noDataMessage")).append("</error_in_data_file>");
		}
		if(xml.length()>0){
			xml.insert(0,"<strings>");
			xml.append("</strings>");
		}
		
		return xml.toString();
	}
	
	
	/**
	 * 取得图表标题设置
	 * @param map
	 * @return
	 */
	private static  String getSettingsLabels(Map map){
		StringBuffer xml = new StringBuffer();

		//标题
		if (map.get("title") != null) {
			xml.append("<text><![CDATA[").append(map.get("title")).append("]]></text>");
		}
		//标题字体大小
		if (map.get("titleFontSize") != null) {
			xml.append("<text_size>").append(map.get("titleFontSize")).append("</text_size>");
		}
		//标题颜色
		if (map.get("titleFontColor") != null) {
			xml.append("<text_color>").append(map.get("titleFontColor")).append("</text_color>");
		}
		if(xml.length()>0){
			xml.insert(0,"<labels><label lid=\"0\"><x>0</x><y>10</y>");
			xml.append("</label></labels>");
		}
		return xml.toString();
	}
	
	public static void main(String[] args) {
/*		FlashChartUtil a = new FlashChartUtil();
	    List result = new ArrayList();
		Map lnk=new LinkedHashMap();
		lnk.put("title", "区域一");
		lnk.put("visitcount", "120.312");
		lnk.put("ipcount", "333.4353453");
		result.add(lnk);
		lnk=new LinkedHashMap();
		lnk.put("title", "区域二");
		lnk.put("visitcount", "222.3453453");
		lnk.put("ipcount", "234.456456");
		result.add(lnk);
		
		
		Map para = new HashMap();
		para.put("value",result);
		String lineName[] = {"访问数","IP数"};
		para.put("lineName",lineName);
		String colName[] = {"title","visitcount","ipcount"};
		para.put("colName",colName);
		
		para.put("id","mytest");
		String xml = a.lineChart(para);
		System.out.println(xml);*/
		
	}
}

