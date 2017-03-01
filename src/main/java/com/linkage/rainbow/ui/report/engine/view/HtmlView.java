package com.linkage.rainbow.ui.report.engine.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.ReportEngine;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.model.PropertyDefine;
import com.linkage.rainbow.ui.report.engine.model.Range;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 将报表以HTML的格式输出
 * 
 * @version 1.0
 * @author 陈亮 2009-06-10
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-06-10<br>
 *         修改内容:新建
 *         <hr>
 *
 */
public class HtmlView {
	
	private static Log log = LogFactory.getLog(HtmlView.class);
	Report report[] = null;
	Map para = null;
	String reportid = "";
	boolean isFullHtml = false;
    boolean isPinpoint = false;
    boolean isScrolling = false; //是否冻结表头
    ComponentUtil componentUtil;
    
    private String colGroupTmp;
    
    /**
     * 根据报表请求类与报表参数构造
     * @param report  报表请求类
     * @param para 报表参数
     */
	public HtmlView(Report report[],Map para ) {
		
		this.report = report;
		this.para = new HashMap();
		if(report.length>0){
			if(report[0].getPara()!=null)
				this.para.putAll(report[0].getPara());
		}
		if(para!=null)
			this.para.putAll(para);
		
		isFullHtml = this.para.get("isFullHtml")!= null && this.para.get("isFullHtml").equals("true");
		if(this.para.get("isTag")!= null && this.para.get("isTag").equals("true")){
			isFullHtml = false;
		}
		if(this.para.get("request") != null)
			componentUtil = new ComponentUtil((HttpServletRequest)this.para.get("request"));
		
		if(this.para.get("scrolling") != null && this.para.get("scrolling").equals("true")){
			isScrolling = true;
		}
		
		//		生成页面时,报表的ID值,报表的相关HTML对象,以 报表ID 为前缀进行命名.如form名为: 报表ID_from,table ID值为:报表ID_tab 等.
		if(report[0].getReportId() != null)
			reportid = report[0].getReportId();
		else if(para.get("old_report") != null)
			reportid = "fr_"+para.get("old_report").toString().replace('.','_');
		else if(para.get("report") != null)
			reportid = "fr_"+para.get("report").toString().replace('.','_');
		else 
			reportid = "fr_"+System.currentTimeMillis();
		this.para.put("report",reportid);
		
	}

	public HtmlView() {
	}

	/**
	 * 设置报表请求类
	 * @param report 报表请求类
	 */
	public void setReport(Report report[]) {
		this.report = report;
	}

	/**
	 * 输出报表
	 * @param outStream 输出流
	 * @return 返回的html
	 */
	public String print(OutputStream outStream) {
		String reulst = null;
		
		/** 判断是否查无记录 **/
		boolean isNoRecord = true;
		for(int i=0;i<report.length;i++){
			if(report[i].getDsMap().size()>0){
				Iterator iterator = (report[i].getDsMap()).values().iterator();
				while(iterator.hasNext()){
					CachedRowSet rs = (CachedRowSet)iterator.next();
					if(rs.getRow()>0){
						isNoRecord = false;
						break;
					}
				}
			}
			if(!isNoRecord)
				break;
		}
		//System.out.println(" para.get(noRecordMsg):"+ para.get("noRecordMsg"));
		if(isNoRecord && para.get("noRecordMsg") != null && para.get("noRecordMsg").toString().trim().length()>0){
			//输出查无记录信息
			if(outStream != null){
				PrintWriter out = new PrintWriter(outStream);
				out.println(para.get("noRecordMsg").toString());
			}else {
				StringWriter sw = new StringWriter();
				sw.write(para.get("noRecordMsg").toString());
				reulst = sw.toString();
			}
		}else{
			if(outStream != null){
				PrintWriter out = new PrintWriter(outStream);
				if(isScrolling)
					printScrolling(out);//冻结表头
				else
					print(out);
			}else {
				StringWriter sw = new StringWriter();
				if(isScrolling)
					printScrolling(sw);//冻结表头
				else
					print(sw);
				reulst = sw.toString();
			}
		}
		return reulst;
	}
	
	/**
	 * 输出报表为冻结报头报表
	 * @param out 输出流
	 */
	public void printScrolling(Writer writer) {
		PrintWriter out = new PrintWriter(writer);
		
		try {
			String contextPath = (String)para.get("contextPath");
			
			
			//如果放在iframe,则根据本页的高度自动调整iframe的高度
			StringBuffer javascript_toppage = new StringBuffer();
			if(componentUtil != null)
				javascript_toppage.append(componentUtil.writeScript("/struts/simple/report/resource/js/base.js"));
			String onload = "onload=\"toppage()\"";
			
			
			// 报表引擎配置的共用的样式表与脚本文件等外部资源.
			String ext_resource = "";
			if(ReportEngine.getProp() != null ){
				ext_resource = ReportEngine.getProp().getProperty("ext_resource");
				if(ext_resource == null)
					ext_resource = "";
			}
			//本报表增加的样式表.
			if(para.get("ext_style") != null){
				String extStyles[] = ((String)para.get("ext_style")).split(",");
				for(int i=0;i<extStyles.length;i++){
					if(extStyles[i].trim().length()>0){
						if(!extStyles[i].startsWith(contextPath)){
							extStyles[i] = contextPath + "/"+extStyles[i];
						}
						ext_resource += "<style type=\"text/css\" >@import url("+extStyles[i]+");</style> \n";
						//ext_resource += "<link rel=\"stylesheet\" type=\"text/css\" href=\""+extSytles[i]+"\">"; 
					}
				}
			}
			//本报表增加的脚本文件.
			if(para.get("ext_script") != null){
				String extScript[] = ((String)para.get("ext_script")).split(",");
				for(int i=0;i<extScript.length;i++){
					if(extScript[i].trim().length()>0)
						ext_resource += "<script language=\"javascript\" type=text/javascript src=\""+extScript[i]+"\"></SCRIPT>\n"; 
				}
			}
			
			//body样式
			StringBuffer bodyStyle = new StringBuffer()
				.append("<style type=\"text/css\"><!--  \n")
				.append("  body {margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px; \n");
			if(para.get("body_bg_color") != null)
				bodyStyle.append("background-color: ").append(para.get("body_bg_color")).append("; \n");
			if(para.get("body_bg_image") != null)
				bodyStyle.append("background-image: url(").append(para.get("body_bg_image")).append("); \n");
			bodyStyle.append("--> </style> \n");

			
			
			
			if(!isFullHtml)
				out.println(ext_resource+javascript_toppage);
			else
				out.println("<html><head><meta http-equiv=Content-Type content='text/html; charset=GBK'>"+ext_resource+bodyStyle+javascript_toppage+"</head><body "+onload+" >\n");

			
			String formStart = "<form method=POST name=\""+reportid+"_form\" id=\""+reportid+"_form\" action=\"\" target=\"_self\" style=\"margin: 0px\">";
			out.println(formStart);
			String hidden = getPara2Hidden();
			out.println(hidden);

			if (report != null) {
				for (int iReportIdx = 0; iReportIdx < report.length; iReportIdx++) {
					
					CellSet cs = report[iReportIdx].getNewCS();
					
					if (cs != null && cs.getRow() > 0) {
						out.print("<div id='"+reportid+"_scrollArea' style=\"overflow:hidden;text-align:left;") ;
						int tableWidth = getFieldWidth(cs,null)+22;
						int tableHeight = getFieldHeight(cs,null)+22;
						if(cs.getWidth() != null && cs.getWidth().length()>0 && !cs.getWidth().equals("100%")){
							int iCsWidhtTmp = -1;
							iCsWidhtTmp = Integer.parseInt(cs.getWidth().replaceAll("px",""));
							out.print(" width:"+(iCsWidhtTmp>-1&& tableWidth<iCsWidhtTmp?tableWidth:cs.getWidth())+";");
						}else{
							if(tableWidth >1000)
								out.print(" width:100%;");
							else
								out.print(" width:"+(tableWidth)+";");
						}
						if(cs.getHeight() != null && cs.getHeight().length()>0&& !cs.getHeight().equals("100%")){
							int iCsHeightTmp = -1;
							try{iCsHeightTmp = Integer.parseInt(cs.getHeight().replaceAll("px",""));}catch(Exception e){}
							out.print(" height:"+(iCsHeightTmp>-1&& tableHeight<iCsHeightTmp?tableHeight:cs.getHeight())+";");
						}else{
							out.print(" height:"+(tableHeight)+";");
						}
						out.print("\" onresize=\"_resizeScroll( '"+reportid+"' )\">");
						out.print("<table  cellspacing='"+cs.getCellspacing()+"' cellpadding='"+cs.getCellpadding()+"'");
						if(cs.getBorder() != null && cs.getBorder().length()>0)
							out.print(" border='"+cs.getBorder()+"'");
						if(cs.getAlign() != null && cs.getAlign().length()>0)
							out.print(" align='"+cs.getAlign()+"'");
						if(cs.getBackground() != null && cs.getBackground().length()>0)
							out.print(" background='"+cs.getBackground()+"'");
						if(cs.getBgcolor() != null && cs.getBgcolor().length()>0)
							out.print(" bgcolor='"+cs.getBgcolor()+"'");
						if(cs.getStyle() != null && cs.getStyle().length()>0)
							out.print(" style='"+cs.getStyle()+"'");
						if(cs.getStyleClass() != null && cs.getStyleClass().length()>0)
							out.print(" class='"+cs.getStyleClass()+"'");
						out.print(">");
						
						Field colHeadField = cs.findColHead(); //行标头
						Field rowHeadField = cs.findRowHead(); //列标头
						
						if(colHeadField != null || rowHeadField != null){
							if(colHeadField != null && rowHeadField != null){//交叉报表情况
								Field crossField = colHeadField.crossField(rowHeadField);
								out.print("<tr><td>");	
								//输出交叉表头
									out.print("<table id=\""+reportid+"_$_corner\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,crossField)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,crossField);
									out.print("</table>");
								out.print("</td><td>");	
								//输出列表头
									Field colHeadFieldNew = new Field(colHeadField.getStartRow(),crossField.getEndColumn()+1,crossField.getEndRow(),cs.getColumn()-1);
									out.print("<div id=\""+reportid+"_topdiv\" style=\"overflow:hidden\">");
									out.print("<table id=\""+reportid+"_$_top\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,colHeadFieldNew)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,colHeadFieldNew);
									out.print("</table></div>");
								out.print("</td></tr>");
								out.print("<tr><td  valign='top'>");	
								//输出行表头
									Field rowHeadFieldNew = new Field(crossField.getEndRow()+1,rowHeadField.getStartColumn(),cs.getRow()-1,crossField.getEndColumn());
									out.print("<div id=\""+reportid+"_leftdiv\" style=\"overflow:hidden\">");
									out.print("<table id=\""+reportid+"_$_left\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,rowHeadFieldNew)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,rowHeadFieldNew);
									out.print("</table></div>");
								out.print("</td><td>");	
								//输出数据区
									Field dataField = new Field(crossField.getEndRow()+1,crossField.getEndColumn()+1,cs.getRow()-1,cs.getColumn()-1);
									out.print("<div id=\""+reportid+"_contentdiv\" style=\"overflow:auto\" onscroll=\"_reportScroll( '"+reportid+"' )\">");
									out.print("<table id=\""+reportid+"\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,dataField)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,dataField);
									out.print("</table></div>");
								out.print("</td></tr>");
							}else if(rowHeadField != null ){//列表头
								out.print("<tr><td>");
									//输出列表头
									out.print("<div id=\""+reportid+"_topdiv\" style=\"overflow:hidden\">");
									out.print("<table id=\""+reportid+"_$_top\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,rowHeadField)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,rowHeadField);
									out.print("</table></div>");
								out.print("</td></tr>");
								out.print("<tr><td>");	
								
								//输出数据区
									Field dataField = new Field(rowHeadField.getEndRow()+1,0,cs.getRow()-1,cs.getColumn()-1);
									out.print("<div id=\""+reportid+"_contentdiv\" style=\"overflow:auto\" onscroll=\"_reportScroll( '"+reportid+"' )\">");
									out.print("<table id=\""+reportid+"\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,dataField)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,dataField);
									out.print("</table></div>");
								out.print("</td></tr>");
							}else {
								out.print("<tr><td  valign='top'>");
								//输出行表头
									out.print("<div id=\""+reportid+"_leftdiv\" style=\"overflow:hidden\">");
									out.print("<table id=\""+reportid+"_$_left\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,colHeadField)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,colHeadField);
									out.print("</table></div>");
								out.print("</td><td>");	
								//输出数据区
									Field dataField = new Field(0,colHeadField.getEndColumn()+1,cs.getRow()-1,cs.getColumn()-1);
									out.print("<div id=\""+reportid+"_contentdiv\" style=\"overflow:auto\" onscroll=\"_reportScroll( '"+reportid+"' )\">");
									out.print("<table id=\""+reportid+"\" cellSpacing=0 cellPadding=0 style=\"width:"+getFieldWidth(cs,dataField)+";table-layout:fixed;border-collapse:collapse\">");
									out.print(colGroupTmp);
									printField(out,iReportIdx,cs,dataField);
									out.print("</table></div>");
								out.print("</td></tr>");
								
							}
							
						} else {
							
							printField(out,iReportIdx,cs,null);
						}
						

						out.println( "</table>");
						out.println( "</div>");
						out.println( "</form>");
						//out.print("<script language=javascript>document.body.onload = _resizeScroll;</script>");
						out.print(componentUtil.writeScript("/struts/simple/resource/jquery.js"));
						out.print("<script language=javascript>$(document).ready(_resizeScroll)</script>");
						out.flush();

					}
					
				}
			}

			if(isFullHtml)
				out.println("</body></html>");

			out.flush();

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}
	
	private int getFieldWidth(CellSet cs,Field field){
		int width=0;
		colGroupTmp = ""; 
		if(field ==null){
			field = new Field(0,0,cs.getRow()-1,cs.getColumn()-1);
		}
		boolean isHaveCol = false;
		StringBuffer colGroup= new StringBuffer("<colgroup>");
		for(int j=field.getStartColumn();j<=field.getEndColumn();j++){//取每列的第一个非空行进行判断
			cs.setColIndex(j);
			for(int i=0;i<cs.getRow();i++){
				cs.setRowIndex(i);
				Cell cell =cs.getCell(i,j);
				if(cell != null){
					try {
						if(cell.getCol() != null){
							isHaveCol = true;
							String colSytle = rangeStyle2HtmlStyle(cell.getCol());
							//鼠标事件
							String rowMouseEvent = getRangeEvent(cell.getCol());
							colGroup.append("<col ").append(colSytle).append(rowMouseEvent).append("></col>");
							//colGroup.append("<col style=\"width:").append(widthTmp).append("px;\"></col>");
							
							
							if(cell.getCol().getWidth() != null && cell.getCol().getWidth().trim().length()>0){
								int widthTmp = Integer.parseInt(cell.getCol().getWidth());
								width = width+widthTmp;
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					break;
				}
			}

		}
		colGroup.append("</colGroup>");
		//if(width>0)
		if(isHaveCol)
			colGroupTmp = colGroup.toString();
		return width;
	}
	
	private int getFieldHeight(CellSet cs,Field field){
		int height=0;
		try {
		if(field ==null){
			field = new Field(0,0,cs.getRow()-1,cs.getColumn()-1);
		}
		for(int i=field.getStartRow();i<=field.getEndRow();i++){//取每行的第一个非空列进行判断
			for(int j=0;j<cs.getColumn();j++){
				Cell cell =cs.getCell(i,j);
				if(cell != null){
					
						int heightTmp =-1;
						if(cell.getRow() != null){
							heightTmp = Integer.parseInt(cell.getRow().getHeight());
						}
						if(heightTmp==-1)
							heightTmp = Integer.parseInt(cell.getHeight());
						if(heightTmp>-1)	
							height = height+heightTmp;
					
					break;
				}
			}

		}
		} catch (Exception e) {}
		return height;
	}
	

	/**
	 * 输出报表为冻结报头报表时,输出指定区域块
	 * @param out 输出流
	 * @param iReportIdx 第几个报表
	 * @param cs 表格对象
	 * @param field 区域块
	 */
	public void printField(PrintWriter out,int iReportIdx,CellSet cs,Field field) {
		if(field ==null){
			field = new Field(0,0,cs.getRow()-1,cs.getColumn()-1);
		}
		for (int i = field.getStartRow(); i <= field.getEndRow(); i++) {
			cs.setRowIndex(i);
			StringBuffer trId = new StringBuffer().append("fr_").append(iReportIdx).append("_").append(i);
			boolean isPringTr = false;
			
			
			for (int j = field.getStartColumn(); j <= field.getEndColumn(); j++) {
				cs.setColIndex(j);
				Cell cell = cs.getCell(i, j);
				if (!cs.isMerge(i, j)) {
					if (cell != null) {
						String heightTmpStr = "";
						int heightTmp =-1;
						if(cell.getRow() != null){
							try {heightTmp = Integer.parseInt(cell.getRow().getHeight());} catch (Exception e) {}
						}
						heightTmpStr = heightTmp>-1?" height='"+heightTmp+"' style=\"height:"+heightTmp+"px;\"":"";
						
						//单元格跨度设置
						StringBuffer span = new StringBuffer();
						if(cell.getColSpan()>1)
							span.append(" colspan='").append(cell.getColSpan()).append("'");
						if(cell.getRowSpan()>1 && cell.getColSpan()< (field.getEndColumn()-field.getStartColumn()+1))
							span.append(" rowspan='").append(cell.getRowSpan()).append("'");

						//单元格内容
						String content = getDataFormat(cell);
						content = drill(cell,content,cs.getSheets_index(),trId.toString());
						//单元样式设置
						String style = cellStyle2HtmlStyle(cs, i, j);
						
						if(cell.getLink()!= null && cell.getLink().length()>0){
							content = getLink(cell,content);
						}
						
						if(cell.getIndention()>0){ //缩进样式
							content = getIndent(cell,content,cell.getIndention());
						}
						
						
						
						if(cell.getRow()!= null)
						if(!isPringTr){
							out.print("<tr id='"+trId+"' "+heightTmpStr+" level='1' isload='false'>");
							isPringTr = true;
						}
						//输出一个单元格信息
						out.print("<td " + span + style + ">" + content
								+ "</td>");
					} else {
						if(!isPringTr){
							out.print("<tr id='"+trId+"' level='1' isload='false'>");
							isPringTr = true;
						}
						out.print("<td>&nbsp;</td>");
					}
				}
				
				

			}
			if(isPringTr){
				out.println("</tr>");
			}
			if(i%10==0){
				//out.println("</tbody><tbody>");
				out.flush();
				//System.out.print("out.flush()");
			}
		}
	}
	
	


	/**
	 * 输出报表为非冻结报头报表
	 * @param out 输出流
	 */
	public void print(Writer writer) {
		PrintWriter out = new PrintWriter(writer);
		
		try {
			String contextPath = (String)para.get("contextPath");
			
			//如果放在iframe,则根据本页的高度自动调整iframe的高度
			StringBuffer javascript_toppage = new StringBuffer();
			if(componentUtil != null)
				javascript_toppage.append(componentUtil.writeScript("/struts/simple/report/resource/js/base.js"));
			String onload = "onload=\"toppage()\"";
			
			
			// 报表引擎配置的共用的样式表与脚本文件等外部资源.
			String ext_resource = "";
			if(ReportEngine.getProp() != null ){
				ext_resource = ReportEngine.getProp().getProperty("ext_resource");
				if(ext_resource == null)
					ext_resource = "";
			}
			//本报表增加的样式表.
			if(para.get("ext_style") != null){
				String extStyles[] = ((String)para.get("ext_style")).split(",");
				for(int i=0;i<extStyles.length;i++){
					if(extStyles[i].trim().length()>0){
						if(!extStyles[i].startsWith(contextPath)){
							extStyles[i] = contextPath + "/"+extStyles[i];
						}
						ext_resource += "<style type=\"text/css\" >@import url("+extStyles[i]+");</style> \n";
						//ext_resource += "<link rel=\"stylesheet\" type=\"text/css\" href=\""+extSytles[i]+"\">"; 
					}
				}
			}
			//本报表增加的脚本文件.
			if(para.get("ext_script") != null){
				String extScript[] = ((String)para.get("ext_script")).split(",");
				for(int i=0;i<extScript.length;i++){
					if(extScript[i].trim().length()>0)
						ext_resource += "<script language=\"javascript\" type=text/javascript src=\""+extScript[i]+"\"></SCRIPT>\n"; 
				}
			}
			
			//body样式
			StringBuffer bodyStyle = new StringBuffer()
				.append("<style type=\"text/css\"><!--  \n")
				.append("  body {margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px; \n");
			if(para.get("body_bg_color") != null)
				bodyStyle.append("background-color: ").append(para.get("body_bg_color")).append("; \n");
			if(para.get("body_bg_image") != null)
				bodyStyle.append("background-image: url(").append(para.get("body_bg_image")).append("); \n");
			bodyStyle.append("--> </style> \n");

			
			
			
			if(!isFullHtml)
				out.println(ext_resource+javascript_toppage);
			else
				out.println("<html><head><meta http-equiv=Content-Type content='text/html; charset=GBK'>"+ext_resource+bodyStyle+javascript_toppage+"</head><body "+onload+" >\n");

			
			String reportWidth = (String)para.get("report_width");
			if(reportWidth != null && reportWidth.trim().length() >0){
				reportWidth = "width=\""+reportWidth+"\"";
			} else {
				String pinpoint = (String)para.get("pinpoint"); 
				if(pinpoint != null && pinpoint.toLowerCase().equals("true")){
					isPinpoint = true;
					reportWidth = "";
				} else {
					reportWidth = "width='100%'";
				}
				
			}
			
			String formStart = "<table "+reportWidth+" border='0' cellspacing='0' cellpadding='0'><tr><td><form method=POST name=\""+reportid+"_form\" id=\""+reportid+"_form\" action=\"\" target=\"_self\" style=\"margin: 0px\">";
			out.println(formStart);
			String hidden = getPara2Hidden();
			out.println(hidden);

			if (report != null) {
				String unionSheets = (String)para.get("union_sheets");

				boolean isUnionSheets = unionSheets != null && unionSheets.trim().toLowerCase().equals("true")?true:false;
				if(isUnionSheets){
					out.print("<table "+reportWidth+" border='0' cellspacing='0' cellpadding='0'>");
				}
				for (int i = 0; i < report.length; i++) {
					
					print(i,report[i], out,isUnionSheets,i==report.length-1,reportWidth);
					
				}
				if(isUnionSheets){

					out.println( "</table></form></td></tr></table>");
				} 
			}

			if(isFullHtml)
				out.println("</body></html>");

			out.flush();

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}
	/**
	 * 所有报表参数转为html中的隐含域
	 */
	protected String getPara2Hidden(){
		String hidden = "";
		StringBuffer hiddenBuf = new StringBuffer();

		hiddenBuf.append("<span id=\"").append(reportid).append("_span\" style=\"POSITION: absolute ;visibility: hidden;left: 0;top: 0;\">");
		if(para != null){
			java.util.Iterator ite = para.keySet().iterator();

			while (ite.hasNext()) {
				String name = (String) ite.next();
				if(name.equals("old_report")||name.equals("submit")||name.equals("start_num")||name.equals("end_num")||name.equals("format")||name.startsWith("pages_")||name.equals("xls_name")
						   ||name.endsWith("tree_area_backup")|| name.startsWith("page_currentPage_")||name.startsWith("page_queryflag_")||name.startsWith("page_selectPerPage_")
						   ||name.equals("excelcolumn")||name.equals("startIndex")||name.equals("method")||name.equals("excelTitle")||name.equals("exportMode")||name.equals("queryStringInfo")
						   ||name.equals("contextPath")||name.equals("request")||name.equals("requestURL")||name.equals("report")){
							continue;
				}
				Object obj = para.get(name);
				if(obj instanceof String[]) {
					String value[] = (String[]) obj;
					for(int i=0;i<value.length;i++){
						String newValue = value[i];
						hiddenBuf.append("<input type=hidden id=\"").append(reportid).append("_").append(name.replaceAll("\\.","_")).append("\" name=\"").append(name).append("\" value=\"").append(newValue).append("\">\n");
					}

				} else {
//					
//					if(name.equals("report")){
//						obj = para.get("old_report"); 
//					}
					hiddenBuf.append("<input type=hidden id=\"").append(reportid).append("_").append(name.replaceAll("\\.","_")).append("\" name=\"").append(name).append("\" value=\"").append(obj).append("\">\n");
				}
			}
			
		}
		hiddenBuf.append("</span>");
		hidden = hiddenBuf.toString();
		return hidden;
		
	}
	/**
	 * 输出报表为非冻结报头报表
	 * @param iReportIdx 第几个报表
	 * @param report 报表请求类
	 * @param out 输出流
	 * @param isUnionSheets 是否合并工作区输出
	 * @param isEnd 是否为最后一个工作流
	 * @param reportWidth 报表宽度
	 * @throws IOException
	 */
	protected void print(int iReportIdx,Report report, PrintWriter out,boolean isUnionSheets,boolean isEnd,String reportWidth) throws IOException{
		CellSet cs = report.getNewCS();
		
		if (cs != null && cs.getRow() > 0) {
			if(!isUnionSheets){
				out.print("<table cellspacing='"+cs.getCellspacing()+"' cellpadding='"+cs.getCellpadding()+"'");
				if(cs.getBorder() != null && cs.getBorder().length()>0)
					out.print(" border='"+cs.getBorder()+"'");
				if(cs.getAlign() != null && cs.getAlign().length()>0)
					out.print(" align='"+cs.getAlign()+"'");
				if(cs.getBackground() != null && cs.getBackground().length()>0)
					out.print(" background='"+cs.getBackground()+"'");
				if(cs.getBgcolor() != null && cs.getBgcolor().length()>0)
					out.print(" bgcolor='"+cs.getBgcolor()+"'");
				if(cs.getWidth() != null && cs.getWidth().length()>0)
					out.print(" width='"+cs.getWidth()+"'");
				if(cs.getHeight() != null && cs.getHeight().length()>0)
					out.print(" height='"+cs.getHeight()+"'");
				if(cs.getStyle() != null && cs.getStyle().length()>0)
					out.print(" style='"+cs.getStyle()+"'");
				if(cs.getStyleClass() != null && cs.getStyleClass().length()>0)
					out.print(" class='"+cs.getStyleClass()+"'");
				out.print(">");
				
				
				getFieldWidth(cs,null);
				out.print(colGroupTmp);
				
			}
			
			for (int i = 0; i < cs.getRow(); i++) {
				cs.setRowIndex(i);
				StringBuffer trId = new StringBuffer().append("fr_").append(iReportIdx).append("_").append(i);
				//tr样式
				String rowSytle = "";
				Range rowRange = cs.getRowRangeByIndex(i);
				if(rowRange != null)
					rowSytle = rangeStyle2HtmlStyle(rowRange);
				
				//tr鼠标事件
				String rowMouseEvent = getRangeEvent(rowRange);
				out.print("<tr id='"+trId+"' level='1' isload='false' "+rowSytle+rowMouseEvent+">");
				for (int j = 0; j < cs.getColumn(); j++) {
					cs.setColIndex(j);
					Cell cell = cs.getCell(i, j);
					if (!cs.isMerge(i, j)) {
						if (cell != null) {
							//单元格跨度设置
							StringBuffer span = new StringBuffer();
							if(cell.getColSpan()>1)
								span.append(" colspan='").append(cell.getColSpan()).append("'");
							if(cell.getRowSpan()>1)
								span.append(" rowspan='").append(cell.getRowSpan()).append("'");

							//单元格内容
							String content = getDataFormat(cell);
							content = drill(cell,content,cs.getSheets_index(),trId.toString());
							//单元样式设置
							String style = cellStyle2HtmlStyle(cs, i, j);
							
							if(cell.getLink()!= null && cell.getLink().length()>0){
								content = getLink(cell,content);
							}
							
							if(cell.getIndention()>0){ //缩进样式
								content = getIndent(cell,content,cell.getIndention());
							}
							//td鼠标事件
							String mouseEvent = getCellEvent(cell);
							//输出一个单元格信息
							out.print("<td " + span + style + mouseEvent+">" + content
									+ "</td>");
						} else {
							out.print("<td>&nbsp;</td>");
						}
					}
					
					

				}
				out.println("</tr>");
				if(i%10==0){
					//out.println("</tbody><tbody>");
					out.flush();
					//System.out.print("out.flush()");
				}
			}

			if(!isUnionSheets)
				out.println( "</table>");
			if(!isUnionSheets && isEnd)
				out.println( "</form>");
			if(!isUnionSheets && isEnd)
				out.println( "</td></tr></table>");
			out.flush();

		}

//		System.out.println(html);
	}

	/**
	 * 取得指定方向的上一单元格
	 * @param cs 表格对象
	 * @param row 当前单元格位置的行位置
	 * @param col 当前单元格位置的列位置
	 * @param direction 方向,x表示横向,y为纵向
	 * @return  取得上一单元格
	 */
	protected Cell getFCell(CellSet cs, int row, int col, String direction) {
		Cell cell = null;
		int iTmpXIndex = -1;
		int iTmpYIndex = -1;
		if (direction != null && direction.equals("y")) {
			if (row > -1 && col > 0) {
				iTmpXIndex = row;
				iTmpYIndex = col - 1;
			}
		} else {
			if (row > 0 && col > -1) {
				iTmpXIndex = row - 1;
				iTmpYIndex = col;
			}
		}

		if (iTmpXIndex > -1 && iTmpYIndex > -1) {
			for (int i = iTmpXIndex; i >= 0; i--) {
				boolean isBreak = false;
				for (int j = iTmpYIndex; j >= 0; j--) {
					Cell tmpCell = cs.getCell(i, j);
					if (tmpCell != null) {
						boolean isBlank = cs.isBlank(tmpCell);
						if (!isBlank
								|| (isBlank && !cs.isMerge(iTmpXIndex,
										iTmpYIndex))) {
							if (iTmpXIndex - i < tmpCell.getRowSpan()
									&& iTmpYIndex - j < tmpCell.getColSpan()) {
								cell = tmpCell;
								isBreak = true;
							}
							break;
						}
					}
				}
				if (isBreak)
					break;
			}
		}
		return cell;
	}

	/**
	 * 判断某个单元格,左端或顶端边框设置是否为none
	 * 
	 * @param cs 表格对象
	 * @param row  当前单元格位置的行位置
	 * @param col 当前单元格位置的列位置
	 * @param direction 方向,x表示横向,y为纵向
	 * @return
	 */
	protected boolean isStyleNone(CellSet cs, int row, int col, String direction) {
		boolean isStyleNone = false;

		Cell tmpCell = getFCell(cs, row, col, direction);
		if (tmpCell != null) {
			if (direction != null && direction.equals("y")) { // left
				if (tmpCell.getBorderRight() != PropertyDefine.BORDER_NONE) {
					isStyleNone = true;
				}
			} else { // top
				if (tmpCell.getBorderBottom() != PropertyDefine.BORDER_NONE) {
					isStyleNone = true;
				}
			}
		}

		return isStyleNone;
	}

	/**
	 * 将行样式转换为html
	 * @return
	 */
	protected String rangeStyle2HtmlStyle(Range range) {

		String htmlStyle ="";

		if(range.getWidth() != null && range.getWidth().length()>0){
			htmlStyle += " width:" + range.getWidth() + ";";
		}
		if(range.getHeight() != null && range.getHeight().length()>0){
			htmlStyle += " height:" + range.getHeight() + ";";
		}

		//单元格背景色
		htmlStyle += getRangeBackgroundColorStyle(range);
		
		if(range.getStyleExpr() != null){
			Object styleTmp= range.getStyleExpr().calculate();
			if(styleTmp != null&& styleTmp.toString().trim().length()>0)
				htmlStyle += styleTmp;
		}else if(range.getStyle() != null && range.getStyle().length()>0){
			htmlStyle += range.getStyle();
		}
		
		if(htmlStyle.length()>0)
			htmlStyle ="style=\""+htmlStyle+ "\"";
		
		if(range.getStyleClassExpr() != null){
			Object classTmp= range.getStyleClassExpr().calculate();
			if(classTmp != null&& classTmp.toString().trim().length()>0)
				htmlStyle += " class=\""+classTmp+"\"";;
		}else {
			if(range.getStyleClass() != null && range.getStyleClass().trim().length()>0)
				htmlStyle += " class=\""+range.getStyleClass()+"\"";
		}
		return htmlStyle;
	}
	
	/**
	 *  将单元格样式转换为html
	 * @return
	 */
	protected String cellStyle2HtmlStyle(CellSet cs, int i, int j) {

		String htmlStyle ="";// "style='";
		Cell cell = cs.getCell(i, j);

		//高度
//		//htmlStyle += " height=" +cell.getHeight();
		//if(cs.getWidth() >0){
//		if(cs.getWidth()!= null && !cs.getWidth().equals("")){
			//System.out.println("cs.getWidth():"+cs.getWidth() + "cell.getWidth():"+cell.getWidth());
			//int iWidth = cell.getWidth()*100/cs.getWidth();
			//System.out.println("cs.getWidth():"+cs.getWidth() + "cell.getWidth():"+cell.getWidth() + "iWidth:"+iWidth);
			//宽度
//			if(isPinpoint&&cell.getColSpan()==1 && cell.getWidth()!= null && !cell.getWidth().equals("")){
//				//int iWidth = cell.getWidth()==8 ? 72:cell.getWidth()/32;
//				htmlStyle += " width=\"" + cell.getWidth() + "\"";
//			}
//		}
//		System.out.println(" width=" + cell.getWidth() + " "+(cell.getWidth()/25));
		if(cell.getWidth() != null && cell.getWidth().length()>0){
			htmlStyle += " width:" + cell.getWidth() + ";";
		}
		if(cell.getHeight() != null && cell.getHeight().length()>0){
			htmlStyle += " height:" + cell.getHeight() + ";";
		}
		/** 边框样式 start**/
		if(cell.getBorderTop()>-1){
			if (isStyleNone(cs, i, j, "x")) {
				htmlStyle += " border-top:none ;";
			} else {
				
					htmlStyle += " border-top:"
						+ getBorderStyle(cell.getBorderTop(),cell.getBorderTopColor());
			}
		}
		if(cell.getBorderLeft()>-1){
			if (isStyleNone(cs, i, j, "y")) {
				htmlStyle += " border-left:none ;";
			} else {
				
					htmlStyle += " border-left:" + getBorderStyle(cell.getBorderLeft(),cell.getBorderLeftColor());
			}
		}
		if(cell.getBorderBottom()>-1)
			htmlStyle += " border-bottom:" + getBorderStyle(cell.getBorderBottom(),cell.getBorderBottomColor());
		if(cell.getBorderRight()>-1)
			htmlStyle += " border-right:" + getBorderStyle(cell.getBorderRight(),cell.getBorderRightColor());

		
		/** 边框样式 end**/
		
		//字体样式
		htmlStyle += getFontStyle(cell);
		
		//对齐样式
		htmlStyle += getAlignStyle(cell);
		//单元格背景色
		htmlStyle += getBackgroundColorStyle(cell);
		
		if(cell.getStyleExpr() != null){
			Object styleTmp= getExprResult(cell,cell.getStyleExpr());
			if(styleTmp != null&& styleTmp.toString().trim().length()>0)
				htmlStyle += styleTmp;
		}else if(cell.getStyle() != null && cell.getStyle().length()>0){
			htmlStyle += cell.getStyle();
		}
		
		if(htmlStyle.length()>0)
			htmlStyle =" style=\""+htmlStyle+ "\"";
		
		if(cell.getStyleClassExpr() != null){
			Object classTmp= getExprResult(cell,cell.getStyleClassExpr());
			if(classTmp != null&& classTmp.toString().trim().length()>0)
				htmlStyle += " class=\""+classTmp+"\"";;
		}else {
			if(cell.getStyleClass() != null && cell.getStyleClass().trim().length()>0)
				htmlStyle += " class=\""+cell.getStyleClass()+"\"";
		}
		//如果不是自动换行
		if(cell.isWrapText() == false){
			htmlStyle += " nowrap=\"nowrap\" ";
		}
		if(cell.getWidth() != null && cell.getWidth().length()>0){
			htmlStyle += " width=\"" + cell.getWidth() + "\"";
		}
		return htmlStyle;
	}
	
	/**
	 * 将单元格字体样式转换为html
	 * @param cell
	 * @return
	 */
	protected String getFontStyle(Cell cell){
		String fontStyle = "";
		
		//字体颜色
		if(cell.getFontColor()!= null) {
			fontStyle += "color:#"+getColor(cell.getFontColor())+";";
		}
		if(cell.getFont() != null) {
			//字体名称
			if(cell.getFont().getFontName() != null) {
				fontStyle += "font-family:"+cell.getFont().getFontName()+";";
			}
			//字体颜色
//			if(cell.getFontColor()!= null) {
//				fontStyle += "color:#"+getColor(cell.getFontColor())+";";
//			}
			
			//字号
			fontStyle += "font-size:"+cell.getFont().getFontHeightInPoints()+"pt;";
			//加粗
			fontStyle += "font-weight:"+cell.getFont().getBoldweight()+";";
			//斜体
			if(cell.getFont().getItalic())
				fontStyle += "font-style:italic;";
			
			//删除线
			if(cell.getFont().getStrikeout())
			    fontStyle += "text-decoration: line-through;";
			
			//是否为上标或下标
			cell.getFont().getTypeOffset();
			fontStyle += "";
			
			//下划线
			switch (cell.getFont().getUnderline()) {
			case HSSFFont.U_SINGLE: //
				fontStyle += "text-decoration:underline;text-underline-style:single;";
				break;
			case HSSFFont.U_DOUBLE: //
				fontStyle += "text-decoration:underline;text-underline-style:double;";
				break;
			case HSSFFont.U_SINGLE_ACCOUNTING: //
				fontStyle += "text-decoration:underline;text-underline-style:single-accounting;";
				break;
			case HSSFFont.U_DOUBLE_ACCOUNTING: //
				fontStyle += "text-decoration:underline;text-underline-style:double-accounting;";
				break;
			default:
			}
			
		}
		return fontStyle;
	}
	
	/**
	 * 单元格背景色样式
	 * @param cell
	 * @return
	 */
	protected String getBackgroundColorStyle(Cell cell){
		String backgroundColorStyle = "";
		//前景色
		if(cell.getColorExpr() != null){
			Object color= getExprResult(cell,cell.getColorExpr());
			if(color != null&& color.toString().trim().length()>0)
				backgroundColorStyle += " color:"+color+" ; ";
		}else if(cell.getColor() != null && cell.getColor().length()>0){
			backgroundColorStyle += " color:"+cell.getColor()+" ; ";
		}
		
		if(cell.getFillBackgroundColor() != null){
//			System.out.println("cell.getFillBackgroundColor():"+cell.getFillBackgroundColor().getHexString());
			backgroundColorStyle += " background:#"+getColor(cell.getFillBackgroundColor()) +" ; ";
//			System.out.println("backgroundColorStyle:"+backgroundColorStyle);
		}else {
			if(cell.getBgcolorExpr() != null){
				Object bgcolor= getExprResult(cell,cell.getBgcolorExpr());
				if(bgcolor != null&& bgcolor.toString().trim().length()>0)
					backgroundColorStyle += " background-color:"+bgcolor+" ; ";
			}else if(cell.getBgcolor() != null && cell.getBgcolor().length()>0){
				backgroundColorStyle += " background-color:"+cell.getBgcolor()+" ; ";
			}
			
			if(cell.getBackgroundExpr() != null){
				Object background= getExprResult(cell,cell.getBackgroundExpr());
				if(background != null&& background.toString().trim().length()>0 )
					backgroundColorStyle += " background:"+background+" ; ";
			}else if(cell.getBackground() != null && cell.getBackground().length()>0){
				backgroundColorStyle += " background:"+cell.getBackground()+" ; ";
			}
			
		}
		return backgroundColorStyle;
	}
	
	/**
	 * range背景色
	 * @param cell
	 * @return
	 */
	protected String getRangeBackgroundColorStyle(Range range){
		String backgroundColorStyle = "";

			if(range.getBgcolorExpr() != null){
				Object bgcolor= range.getBgcolorExpr().calculate();
				if(bgcolor != null&& bgcolor.toString().trim().length()>0)
					backgroundColorStyle += " background-color:"+bgcolor+" ; ";
			}else if(range.getBgcolor() != null && range.getBgcolor().length()>0){
				backgroundColorStyle += " background-color:"+range.getBgcolor()+" ; ";
			}
			
			if(range.getBackgroundExpr() != null){
				Object background= range.getBackgroundExpr().calculate();
				if(background != null&& background.toString().trim().length()>0 )
					backgroundColorStyle += " background:"+background+" ; ";
			}else if(range.getBackground() != null && range.getBackground().length()>0){
				backgroundColorStyle += " background:"+range.getBackground()+" ; ";
			}

		return backgroundColorStyle;
	}
	
	/**
	 * 对齐样式
	 * @param cell
	 * @return
	 */
	protected String getAlignStyle(Cell cell){
		String alignStyle = "";
		//水平对齐样式
		if(cell.getAlign()!=null && cell.getAlign().length()>0){ //如果标签中有设置水平对齐样式,则按标签的设置
			alignStyle = " text-align:"+cell.getAlign()+"; ";
		}else {//如果有没有标签中的设置,则取excel中的设置
			switch (cell.getAlignment()) {
			case PropertyDefine.ALIGN_GENERAL: //普通
				break;
			case PropertyDefine.ALIGN_LEFT: //左对齐
				alignStyle = " text-align:left; ";
				break;
			case PropertyDefine.ALIGN_CENTER: //居中
				alignStyle = " text-align:center; ";
				break;
			case PropertyDefine.ALIGN_RIGHT: //右对齐
				alignStyle = " text-align:right; ";
				break;
			case PropertyDefine.ALIGN_FILL: //填充
				alignStyle = " text-align:fill; ";
				break;
			case PropertyDefine.ALIGN_JUSTIFY: //两端对齐
				alignStyle = " text-align:justify; ";
				break;
			case PropertyDefine.ALIGN_CENTER_SELECTION: //跨列居中
				alignStyle = " text-align:center-across; ";
				break;
			default:
				
			}
		}
		 
		//垂直对齐样式
		if(cell.getValign()!=null && cell.getValign().length()>0){ //如果标签中有设置垂直对齐样式,则按标签的设置
			alignStyle = " vertical-align:"+cell.getValign()+"; ";
		}else {//如果有没有标签中的设置,则取excel中的设置
			switch (cell.getVerticalAlignment()) {
			case PropertyDefine.VERTICAL_TOP: //顶对齐
				alignStyle += " vertical-align:top; ";
				break;
			case PropertyDefine.VERTICAL_CENTER: //居中
				break;
			case PropertyDefine.VERTICAL_BOTTOM: //底对齐
				alignStyle += " vertical-align:bottom; ";
				break;
			case PropertyDefine.VERTICAL_JUSTIFY: //分散对齐
				alignStyle += " vertical-align:justify; ";
				break;
			default:
				
			}
		}
		
		//缩进值
//		if(cell.getIndention()>0)
//			alignStyle += " text-indent:"+cell.getIndention()+"; ";
		
		return alignStyle;
	}
	

	/**
	 * EXCEL边框样式与HTML样式对应关系
	 * 
	 * @param iBORDER 边框类型
	 * @param iColor 边框颜色
	 * @return
	 */
	protected String getBorderStyle(int iBORDER,HSSFColor color) {
		String htmlStyle = "";

		switch (iBORDER) {
		case PropertyDefine.BORDER_NONE:
			htmlStyle = " none ";
			break;
		case PropertyDefine.BORDER_THIN:
			htmlStyle = " 0.5pt solid ";
			break;
		case PropertyDefine.BORDER_MEDIUM:
			htmlStyle = " 1pt solid ";
			break;
		case PropertyDefine.BORDER_DASHED:
			htmlStyle = " 0.5pt dashed ";
			break;
		case PropertyDefine.BORDER_DOTTED:
			htmlStyle = " 0.5pt dotted ";
			break;
		case PropertyDefine.BORDER_THICK:
			htmlStyle = " 1.5pt solid ";
			break;
		case PropertyDefine.BORDER_DOUBLE:
			htmlStyle = " 2pt double ";
			break;
		case PropertyDefine.BORDER_HAIR:
			htmlStyle = " 0.5pt solid ";
			break;
		case PropertyDefine.BORDER_MEDIUM_DASHED:
			htmlStyle = " 1pt dashed ";
			break;
		case PropertyDefine.BORDER_DASH_DOT:
			htmlStyle = " 0.5pt dashed ";
			break;
		case PropertyDefine.BORDER_MEDIUM_DASH_DOT:
			htmlStyle = " 1pt dashed ";
			break;
		case PropertyDefine.BORDER_DASH_DOT_DOT:
			htmlStyle = " 0.5pt dashed ";
			break;
		case PropertyDefine.BORDER_MEDIUM_DASH_DOT_DOT:
			htmlStyle = " 1pt dashed ";
			break;
		case PropertyDefine.BORDER_SLANTED_DASH_DOT:
			htmlStyle = " 0.5pt dashed ";
			break;
		default:
			htmlStyle = " 0.5pt solid ";
		}

		if(color != null){
			htmlStyle += "#"+getColor(color) +"; ";
		} else {
			htmlStyle += "windowtext; " ;
		}
		
		return htmlStyle;
	}
	
	/**
	 * 取得颜色,转为#XXXXXX表示
	 * @param color
	 * @return
	 */
	protected String getColor(HSSFColor color){
		String strColor = "";
		if(color != null){
			for(int i=0;i<3;i++){ 
				String strTmpColor = MathUtil.to16(color.getTriplet()[i]);
				if(strTmpColor.length()<2){
					strTmpColor = "0"+strTmpColor;
				}
				strColor +=strTmpColor;
			}
		}
		
		return strColor;
	}
	
	/**
	 * 取得链接
	 * @param cell
	 * @param content
	 * @return
	 */
	protected String getLink(Cell cell ,String content){
		String html=content;
		if(cell.getContent() != null){ //对于为空的数据,不显示链接
			if(cell.getLinkExpr() != null){
				Object link= getExprResult(cell,cell.getLinkExpr());
				if(link != null&& link.toString().trim().length()>0)
					html="<a href=\""+link+"\" target=\""+(cell.getLinkTarget()!=null && cell.getLinkTarget().trim().length()>0?cell.getLinkTarget():"_self") + "\""+(cell.getLinkTitle()!=null && cell.getLinkTitle().trim().length()>0?" title=\""+cell.getLinkTitle()+"\"":"")+">"+content+"</a>";
			}else {
				if(cell.getLink()!=null && cell.getLink().trim().length()>0)
					html="<a href=\""+cell.getLink()+"\" target=\""+(cell.getLinkTarget()!=null && cell.getLinkTarget().trim().length()>0?cell.getLinkTarget():"_self")+ "\""+ (cell.getLinkTitle()!=null && cell.getLinkTitle().trim().length()>0?" title=\""+cell.getLinkTitle()+"\"":"")+">"+content+"</a>";
			}
		}
		return html;
	}
	
	/**
	 * 取得表达式结果
	 * @param cell
	 * @param content
	 * @return
	 */
	public static Object getExprResult(Cell cell,Expr expr){
		Object result=null;
		if(expr != null){
			if(cell.getRelDBObj() !=null){
				Object relDBObj =null ;
				if(cell.getRelDBObj() instanceof List){
					List list = (List)cell.getRelDBObj();
					if(list.size()>0)
						relDBObj = list.get(0);
				}else{
					relDBObj = cell.getRelDBObj();
				}
				if(relDBObj != null){
					if(relDBObj instanceof DBGroup){
						DBGroup group = (DBGroup)relDBObj;
						group.getDs().goTo(group.getDsCurrIdx());
					}else if(relDBObj instanceof BaseRow){
						BaseRow baseRow = (BaseRow)relDBObj;
						baseRow.getDs().goTo(baseRow.getIndex());
					}
				}
			}
			result=  expr.calculate();
		}
		return result;
	}
	
	/**
	 * 设置缩进位置
	 * @param cell 
	 * @param content
	 * @param indent
	 * @return
	 */
	protected String getIndent(Cell cell ,String content,int indent){
		String html="";
		if(cell.getAlign() != null && cell.getAlign().length()>0 ){
			if(cell.getAlign().equalsIgnoreCase("right")){
				html = content+getIndent(cell.getIndention());
			}else {
				html = getIndent(cell.getIndention())+content;
			}
		} else {
			if(cell.getIndention()>0){ //缩进样式
				if(cell.getAlignment() == PropertyDefine.ALIGN_RIGHT){ //右缩进
					html = content+getIndent(cell.getIndention());
				} else { //左缩进
					html = getIndent(cell.getIndention())+content;
				}
			}
		}
		return html;
	}
	
	/**
	 * 根据缩进个数生成N个空格
	 * @param indent
	 * @return
	 */
	protected String getIndent(int indent){
		String str = "";
		for(int i=0;i<indent;i++){
			str += "&nbsp;";
		}
		return str;
	}
	/**
	 * 匹配字符串
	 * @param pattern
	 * @param dataFormat
	 * @return
	 */
	public static boolean matcher(String pattern,String dataFormat){
		Pattern p = Pattern.compile(pattern);
		Matcher m=p.matcher(dataFormat); 
		boolean b = m.matches();
		return b;
	}
	
	/**
	 * 根据数据格式生成单元格内容.
	 * @param cell
	 * @return
	 */
	public static String getDataFormat(Cell cell){
		//Object content = cell.getContent();
		Object content = cell.getShowContent();
		if(content == null) //如果为空,则取默认值
			content = cell.getDefaultContent(); 
		Object newObj = null;
		if(content != null){
			//System.out.println("content.getClass():"+content.getClass());
			String dataFormat=null;
			if(cell.getDataFormatExpr() != null){
				Object dataFormatTmp= getExprResult(cell,cell.getDataFormatExpr());
				if(dataFormatTmp != null&& dataFormatTmp.toString().trim().length()>0)
					dataFormat = dataFormatTmp.toString().trim();
			}else if(cell.getDataFormat() != null && cell.getDataFormat().length()>0){
				dataFormat = cell.getDataFormat();
			}
			
			if(dataFormat == null || dataFormat.equals("General") ||dataFormat.equals("G/通用格式")){
				newObj = content;
			} else {
				if(MathUtil.isNumber(content)){//数字类型
					NumberFormat nf = NumberFormat.getInstance();
					String strTmp ;
					if(matcher(".*#?,?#?#?0\\.?0*_.*;\\[Red\\].*-#?,?#?#?0\\.?0*.*",dataFormat)||matcher(".*#?,?#?#?0\\.?0*_.*;\\[红色\\].*-#?,?#?#?0\\.?0*.*",dataFormat)
						||matcher(".*#?,?#?#?0\\.?0*;\\[Red\\].*-#?,?#?#?0\\.?0*.*",dataFormat)||matcher(".*#?,?#?#?0\\.?0*;\\[红色\\].*-#?,?#?#?0\\.?0*.*",dataFormat)){
						//0_ ;[Red]-0  0.00_ ;[Red]-0.00  #,##0.00_ ;[Red]-#,##0.00
						//￥#,##0.00_);[红色]￥-#,##0.00
						String endsWith = "";
						if( !dataFormat.endsWith("0")){
							endsWith =dataFormat.substring(dataFormat.length()-1);
							dataFormat = dataFormat.substring(0,dataFormat.length()-1);
							if(endsWith.equals("%"))
								content = MathUtil.multiply(content,100);
						}
						if(dataFormat.indexOf("#")>-1)
							nf.setGroupingUsed(true);
						else 
							nf.setGroupingUsed(false);
						if(dataFormat.indexOf(".")>-1){
							strTmp = dataFormat.replaceAll(".+\\.","");
							int iLength = strTmp.trim().length(); 
							nf.setMaximumFractionDigits(iLength); 
							nf.setMinimumFractionDigits(iLength);
						}else{
							nf.setMaximumFractionDigits(0);
						}
						
						if(MathUtil.isNegative((Number)content)){
							cell.setFontColor(new HSSFColor.RED());
						}
						newObj = nf.format(content)+endsWith;
						if( !dataFormat.startsWith("#")&&!dataFormat.startsWith("0")){
							newObj =dataFormat.substring(0,1)+newObj;
						}
					}else if(matcher(".*#?,?#?#?0\\.?0*_\\);\\[Red\\]\\(.*#?,?#?#?0\\.?0*\\).*",dataFormat)||matcher(".*#?,?#?#?0\\.?0*_\\);\\[红色\\]\\(.*#?,?#?#?0\\.?0*\\).*",dataFormat) ){
						//0_);[Red](0)  0.00_);[Red](0.00)  #,##0.00_);[Red](#,##0.00)
						//￥#,##0.00_);[红色]￥-#,##0.00
						String endsWith = "";
						if( !dataFormat.endsWith(")")){
							endsWith =dataFormat.substring(dataFormat.length()-1);
							dataFormat = dataFormat.substring(0,dataFormat.length()-1);
							if(endsWith.equals("%"))
								content = MathUtil.multiply(content,100);
						}
						if(dataFormat.indexOf("#")>-1)
							nf.setGroupingUsed(true);
						else 
							nf.setGroupingUsed(false);
						if(dataFormat.indexOf(".")>-1){
							strTmp = dataFormat.replaceAll(".+\\.","");
							int iLength = strTmp.trim().length()-1;
							nf.setMaximumFractionDigits(iLength);
							nf.setMinimumFractionDigits(iLength);
						}else{
							nf.setMaximumFractionDigits(0);
						}
						
						if(MathUtil.isNegative((Number)content)){
							cell.setFontColor(new HSSFColor.RED());
							newObj = "("+nf.format(content)+")"+endsWith;
						} else {
							newObj = nf.format(content)+endsWith;
						}
						if( !dataFormat.startsWith("#")&&!dataFormat.startsWith("0")){
							newObj =dataFormat.substring(0,1)+newObj;
						}
					}else if(matcher(".*#?,?#?#?0\\.?0*_\\);\\(.*#?,?#?#?0\\.?0*\\).*",dataFormat)){
						//0_);(0)  0.00_);(0.00\)  #,##0.00_);(#,##0.00)
						String endsWith = "";
						if( !dataFormat.endsWith(")")){
							endsWith =dataFormat.substring(dataFormat.length()-1);
							dataFormat = dataFormat.substring(0,dataFormat.length()-1);
							if(endsWith.equals("%"))
								content = MathUtil.multiply(content,100);
						}
						if(dataFormat.indexOf("#")>-1)
							nf.setGroupingUsed(true);
						else 
							nf.setGroupingUsed(false);
						if(dataFormat.indexOf(".")>-1){
							strTmp = dataFormat.replaceAll(".+\\.","");
							int iLength = strTmp.trim().length()-1;
							nf.setMaximumFractionDigits(iLength);
							nf.setMinimumFractionDigits(iLength);
						}else{
							nf.setMaximumFractionDigits(0);
						}
						
						if(MathUtil.isNegative((Number)content)){
							content = MathUtil.subtract(new Integer(0),content);
							newObj = "("+nf.format(content)+")"+endsWith;
						} else {
							newObj = nf.format(content)+endsWith;
						}
						if( !dataFormat.startsWith("#")&&!dataFormat.startsWith("0")){
							newObj =dataFormat.substring(0,1)+newObj;
						}
					}else if(matcher(".*#?,?#?#?0\\.?0*;\\[Red\\].*#?,?#?#?0\\.?0*.*",dataFormat)||matcher(".*#?,?#?#?0\\.?0*;\\[红色\\].*#?,?#?#?0\\.?0*.*",dataFormat)){
						//0;[Red]0  0.00;[Red]0.00  #,##0.00;[Red]#,##0.00
						String endsWith = "";
						if( !dataFormat.endsWith("0")){
							endsWith =dataFormat.substring(dataFormat.length()-1);
							dataFormat = dataFormat.substring(0,dataFormat.length()-1);
							if(endsWith.equals("%"))
								content = MathUtil.multiply(content,100);
						}
						if(dataFormat.indexOf("#")>-1)
							nf.setGroupingUsed(true);
						else 
							nf.setGroupingUsed(false);
						if(dataFormat.indexOf(".")>-1){
							strTmp = dataFormat.replaceAll(".+\\.","");
							int iLength = strTmp.trim().length();
							nf.setMaximumFractionDigits(iLength);
							nf.setMinimumFractionDigits(iLength);
						}else{
							nf.setMaximumFractionDigits(0);
						}
						
						if(MathUtil.isNegative((Number)content)){
							cell.setFontColor(new HSSFColor.RED());
							content = MathUtil.subtract(new Integer(0),content);
						}
						newObj = nf.format(content)+endsWith;
						if( !dataFormat.startsWith("#")&&!dataFormat.startsWith("0")){
							newObj =dataFormat.substring(0,1)+newObj;
						}
					}else  if(matcher(".*#?,?#?#?0\\.?0*_.*",dataFormat)){
						//0_  0.00_  #,##0.00_ 
						String endsWith = "";
						if( !dataFormat.endsWith("_")){
							endsWith =dataFormat.substring(dataFormat.length()-1);
							dataFormat = dataFormat.substring(0,dataFormat.length()-1);
							if(endsWith.equals("%"))
								content = MathUtil.multiply(content,100);
						}
						if(dataFormat.indexOf("#")>-1)
							nf.setGroupingUsed(true);
						else 
							nf.setGroupingUsed(false);
						if(dataFormat.indexOf(".")>-1){
							strTmp = dataFormat.replaceAll(".+\\.","");
							int iLength = strTmp.trim().length()-1;
							nf.setMaximumFractionDigits(iLength);
							nf.setMinimumFractionDigits(iLength);
						}else{
							nf.setMaximumFractionDigits(0);
						}
						newObj = nf.format(content)+endsWith;
						if( !dataFormat.startsWith("#")&&!dataFormat.startsWith("0")){
							newObj =dataFormat.substring(0,1)+newObj;
						}
					}else if(matcher(".*#?,?#?#?0\\.?0*.*",dataFormat)){
						//0  0.00  #,##0.00 
						String endsWith = "";
						if( !dataFormat.endsWith("0")){
							endsWith =dataFormat.substring(dataFormat.length()-1);
							dataFormat = dataFormat.substring(0,dataFormat.length()-1);
							if(endsWith.equals("%"))
								content = MathUtil.multiply(content,100);
						}
						if(dataFormat.indexOf("#")>-1)
							nf.setGroupingUsed(true);
						else 
							nf.setGroupingUsed(false);
						if(dataFormat.indexOf(".")>-1){
							strTmp = dataFormat.replaceAll(".+\\.","");
							int iLength = strTmp.trim().length();
							nf.setMaximumFractionDigits(iLength);
							nf.setMinimumFractionDigits(iLength);
						}else{
							nf.setMaximumFractionDigits(0);
						}
						newObj = nf.format(content)+endsWith;
						
						if( !dataFormat.startsWith("#")&&!dataFormat.startsWith("0")){
							newObj =dataFormat.substring(0,1)+newObj;
						}
					}else  {
						newObj = content;
					}
					
				} else if(content instanceof Date){
					
					dataFormat = dataFormat.replaceAll("Y","y"); 
					dataFormat = dataFormat.replaceAll("D","d"); 
					dataFormat = dataFormat.replaceAll("h","H");
					dataFormat = dataFormat.replaceAll("S","s");
					dataFormat = dataFormat.replaceAll("AM/PM","a");
					dataFormat = dataFormat.replaceAll("am/pm","a");
					dataFormat = dataFormat.replaceAll("上午/下午","a");
					dataFormat = dataFormat.replaceAll("m{3}","MMM"); 
					//如果只有单个m,则改为M
					String dataFormatTmp = "";
					for(int i=0;i<dataFormat.length();i++){
						char charTmp = dataFormat.charAt(i);
						boolean isHaveCharm = false;
						if(charTmp == 'm'){
							if((i ==0 || dataFormat.charAt(i-1) != 'm') 
									&& (i ==dataFormat.length()-1 || dataFormat.charAt(i+1)!= 'm' )){
								dataFormatTmp=dataFormatTmp+'M';
								isHaveCharm = true;
							}
						}
						if(!isHaveCharm)
							dataFormatTmp=dataFormatTmp+charTmp;
					}
					
					SimpleDateFormat df = new SimpleDateFormat(dataFormat);
					
					newObj = df.format(content) ;
				} else {
					newObj = content;
				}
			}
			
//			if(MathUtil.isNumber(content)){//数字类型
//				NumberFormat nf = NumberFormat.getInstance();
//				
//				nf.setMinimumFractionDigits(0);
//				newObj = nf.format(content);
//				newObj = content;
//			} else {
//				newObj = content;
//			}
			
		}
		newObj = newObj != null && newObj.toString() != null &&newObj.toString().trim().length()>0 ?
				newObj.toString():"&nbsp;";

		return newObj.toString() ;
	}
	
	/**
	 * 钻取内容显示
	 * @param cell
	 * @param content 单元格内容
	 * @param iReportIdx 当前工作表位置
	 * @param trId 所在行ID
	 * @param rowIdx oldCS中钻取单元格对应的行与列
	 * @param colIdx
	 * @return
	 */
	protected String drill(Cell cell,String content ,int sheets_index,String trId){
		if(cell.getDrillType() != null && content != null && cell.getDrillAvai()==true){
			String contextPath = (String)para.get("contextPath");
			String requestURL =para.get("requestURL")!=null?para.get("requestURL").toString():"";
			if(cell.getDrillType().equals("2")||cell.getDrillType().equals("3")){
				content = "<a href=\"javascript:fr_drill_2('"+contextPath+"','"+reportid+"','"+cell.getDrillTerm()+"')\" >"+ content+"</a>";
			}
			if(cell.getDrillType().equals("1")||cell.getDrillType().equals("3")){
				String drill1 = "<img id=\""+trId+"_img\" state=\"close\" style=\"CURSOR: hand\" onClick=\"fr_drill_1('"+contextPath+"','"+reportid+"','format=drill&sheets_index="+sheets_index+"&drill_direction="+cell.getExtField().toExcelCoor()+"&"+cell.getDrillTerm()+"','"+trId+"',this,'"+requestURL+"');\" src=\""+contextPath+"/struts/simple/report/resource/images/tree_close.gif\" >";
				content = drill1 + content;
			}
		}
		return content;
	}

	
	/**
	 * 取得单元格事件
	 * @param cell
	 * @return
	 */
	protected String getCellEvent(Cell cell){
		String html = "";
		
		//单击事件
		if(cell.getOnclickExpr() != null){
			Object result= getExprResult(cell,cell.getOnclickExpr());
			if(result != null)
				html+=" onclick=\""+result+"\" ";
		}else if(cell.getOnclick() != null && cell.getOnclick().trim().length()>0){
			html+=" onclick=\""+cell.getOnclick()+"\" ";
		}

		//双击事件
		if(cell.getOndblclickExpr() != null){
			Object result= getExprResult(cell,cell.getOndblclickExpr());
			if(result != null)
				html+=" ondblclick=\""+result+"\" ";
		}else if(cell.getOndblclick() != null && cell.getOndblclick().trim().length()>0){
			html+=" ondblclick=\""+cell.getOndblclick()+"\" ";
		}

		//鼠标移出事件
		if(cell.getOnmouseoutExpr() != null){
			Object result= getExprResult(cell,cell.getOnmouseoutExpr());
			if(result != null)
				html+=" onmouseout=\""+result+"\" ";
		}else if(cell.getOnmouseout() != null && cell.getOnmouseout().trim().length()>0){
			html+=" onmouseout=\""+cell.getOnmouseout()+"\" ";
		}

		//鼠标点击事件
		if(cell.getOnmousedownExpr() != null){
			Object result= getExprResult(cell,cell.getOnmousedownExpr());
			if(result != null)
				html+=" onmousedown=\""+result+"\" ";
		}else if(cell.getOnmousedown() != null && cell.getOnmousedown().trim().length()>0){
			html+=" onmousedown=\""+cell.getOnmousedown()+"\" ";
		}

		//鼠标移入事件
		if(cell.getOnmouseoverExpr() != null){
			Object result= getExprResult(cell,cell.getOnmouseoverExpr());
			if(result != null)
				html+=" onmouseover=\""+result+"\" ";
		}else if(cell.getOnmouseover() != null && cell.getOnmouseover().trim().length()>0){
			html+=" onmouseover=\""+cell.getOnmouseover()+"\" ";
		}
		return html;
	}
	
	/**
	 * 取得行,或列事件
	 * @param cell
	 * @return
	 */
	protected String getRangeEvent(Range range){
		String html = "";
		if(range != null){
			//单击事件
			if(range.getOnclickExpr() != null){
				Object result= range.getOnclickExpr().calculate();
				if(result != null)
					html=" onclick=\""+result+"\" ";
			}else if(range.getOnclick() != null && range.getOnclick().trim().length()>0){
				html=" onclick=\""+range.getOnclick()+"\" ";
			}
	
			//双击事件
			if(range.getOndblclickExpr() != null){
				Object result= range.getOndblclickExpr().calculate();
				if(result != null)
					html=" ondblclick=\""+result+"\" ";
			}else if(range.getOndblclick() != null && range.getOndblclick().trim().length()>0){
				html=" ondblclick=\""+range.getOndblclick()+"\" ";
			}
	
			//鼠标移出事件
			if(range.getOnmouseoutExpr() != null){
				Object result= range.getOnmouseoutExpr().calculate();
				if(result != null)
					html=" onmouseout=\""+result+"\" ";
			}else if(range.getOnmouseout() != null && range.getOnmouseout().trim().length()>0){
				html=" onmouseout=\""+range.getOnmouseout()+"\" ";
			}
	
			//鼠标点击事件
			if(range.getOnmousedownExpr() != null){
				Object result= range.getOnmousedownExpr().calculate();
				if(result != null)
					html=" onmousedown=\""+result+"\" ";
			}else if(range.getOnmousedown() != null && range.getOnmousedown().trim().length()>0){
				html=" onmousedown=\""+range.getOnmousedown()+"\" ";
			}
	
			//鼠标移入事件
			if(range.getOnmouseoverExpr() != null){
				Object result= range.getOnmouseoverExpr().calculate();
				if(result != null)
					html=" onmouseover=\""+result+"\" ";
			}else if(range.getOnmouseover() != null && range.getOnmouseover().trim().length()>0){
				html=" onmouseover=\""+range.getOnmouseover()+"\" ";
			}
		}
		return html;
	}
	
	public static void main(String[] args) {
		Date d = new Date();
//		Locale l = Locale.CHINA;
		
		String dataFormat ="yyyy-MM-dd HH:mm:ss";
		dataFormat = dataFormat.replaceAll("Y","y"); 
		dataFormat = dataFormat.replaceAll("D","d"); 
		dataFormat = dataFormat.replaceAll("h","H");
		dataFormat = dataFormat.replaceAll("S","s");
		dataFormat = dataFormat.replaceAll("AM/PM","a");
		dataFormat = dataFormat.replaceAll("am/pm","a");
		dataFormat = dataFormat.replaceAll("上午/下午","a");
		dataFormat = dataFormat.replaceAll("m{3}","MMM"); 
		String dataFormatTmp = "";
		for(int i=0;i<dataFormat.length();i++){
			char charTmp = dataFormat.charAt(i);
			boolean isHaveCharm = false;
			if(charTmp == 'm'){
				if((i ==0 || dataFormat.charAt(i-1) != 'm') 
						&& (i ==dataFormat.length()-1 || dataFormat.charAt(i+1)!= 'm' )){
					dataFormatTmp=dataFormatTmp+'M';
					isHaveCharm = true;
				}
			}
			if(!isHaveCharm)
				dataFormatTmp=dataFormatTmp+charTmp;
		}
		
		
		System.out.println("dataFormat:"+dataFormat);
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
		String strd =sdf.format(d);
		System.out.println("strd:"+strd);
		//Pattern p = Pattern.compile("0+");
		//Pattern p = Pattern.compile("#?,?#?#?0.?0*_");
		
		//0_);\(0\)  0.00_);\(0.00\)  #,##0.00_);\(#,##0.00\)
		Pattern p = Pattern.compile("#?,?#?#?0\\.?0*_\\);\\\\\\(#?,?#?#?0\\.?0*\\\\\\)");
		String str = "0_);\\(0\\)";
		Matcher m=p.matcher(str); 
		boolean b = m.matches();
		System.out.println(b+" "+str.replaceAll(".+\\.",""));
		
		 str = "0.00_);\\(0.00\\)";
		 m=p.matcher(str); 
		 b = m.matches();
		System.out.println(b+" "+str.replaceAll(".+\\.",""));
		
		 str = "#,##0.00_);\\(#,##0.00\\)";
		 m=p.matcher(str); 
		 b = m.matches();
		System.out.println(b+" "+str.replaceAll(".+\\.","").replaceAll("_.+",""));
		
//		NumberFormat nf = NumberFormat.getInstance();
//		nf.setGroupingUsed(true);
//		nf.setMaximumFractionDigits(0);
//		nf.setMinimumFractionDigits(0);
//		nf.setCurrency(Currency.getInstance(""));

//		String newObj = nf.format(new Double(-3232.1));
//		System.out.println(newObj);
		
//		System.out.println("isNegative:"+MathUtil.isNegative(new BigDecimal("-3443232.4342")));

		
		
//		DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.,l);
//		String strd =df.format(d);
//		System.out.println(strd);
		
		String s = "adsfsa,dfsfda";
		if(s.equals(".+,.+")){
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
}
