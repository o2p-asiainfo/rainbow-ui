package com.linkage.rainbow.ui.report.engine.view;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont; 
import com.lowagie.text.rtf.RtfWriter2; 

/**
 * 
 * DocView<br>
 * 将报表以Doc的格式输出
 * <p>
 * @version 1.0
 * @author 林　锋 2010-2-2
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */
public class DocView { 
	
	private static Log log = LogFactory.getLog(DocView.class);
	Report report[] = null;
	Map para = null;
	String reportid = "";
    boolean isPinpoint = false;
    
    /**
     * 
     *
     */
    public DocView(){
    	
    }
    
    /**
     * 根据报表请求类与报表参数构造
     * @param report 报表请求类
     * @param para 报表参数
     */
    public DocView(Report report[],Map para){
		this.report = report;
		this.para = new HashMap();
		if(report.length>0){
			if(report[0].getPara()!=null)
				this.para.putAll(report[0].getPara());
		}
		if(para!=null)
			this.para.putAll(para);    	
    }
    
	/**
	 * 将报表以pdf的格式输出
	 * @param out
	 */
	public String print(OutputStream outStream){
		String html="";
		try{
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
			
			File dir = new File((String)para.get("basePath")+"/doc/report/");
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			String fileName ="";
			HttpServletRequest request = (HttpServletRequest)para.get("request");
			if(request != null && request.getAttribute("comm.report.docName") !=null){
				fileName = (String)request.getAttribute("comm.report.docName");
			}else{
				fileName = para.get("docName")!=null?(String)para.get("docName")+System.currentTimeMillis():"report_"+System.currentTimeMillis();
			}			
			html=para.get("contextPath")+ "/doc/report/"+fileName+".doc";
			fileName =fileName+".doc";		
			File file = new File(dir,fileName);
			
			// 设置纸张大小
			com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
			// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
			RtfWriter2 writer=RtfWriter2.getInstance(document, new FileOutputStream(file));			
			print(0,report[0], document, writer);
		}catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return html;
	}
	
	/**
	 * 将报表以pdf的格式输出
	 * @param iReportIdx
	 * @param report
	 * @param document
	 * @throws IOException
	 */
	protected void print(int iReportIdx,Report report,com.lowagie.text.Document document,RtfWriter2 writer) throws IOException,DocumentException{
		print(0,iReportIdx,report,document,writer);
	}	
    
	/**
	 * 将报表以pdf的格式输出
	 * @param iStartSheet
	 * @param iReportIdx
	 * @param report
	 * @param document
	 * @throws IOException
	 */	
	protected void print(int iStartSheet,int iReportIdx,Report report,com.lowagie.text.Document document,RtfWriter2 writer) throws IOException,DocumentException{
		CellSet cs = report.getNewCS();
	String pinpoint = (String) para.get("pinpoint");
	boolean isPinpoint = true;
	if (pinpoint != null && pinpoint.toLowerCase().equals("false")) {
	    isPinpoint = false;
	}
	if (!document.isOpen())
	    document.open();
	/*
	 * BaseFont bfChinese = BaseFont.createFont(
	 * "c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H,
	 * BaseFont.EMBEDDED);
	 */
	BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
		"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	Font fontChinese = new Font(bfChinese, 8, Font.NORMAL);
	if (cs != null && cs.getRow() > 0) {
	    Table docTable = new Table(cs.getColumn());
	    for (int i = 0; i < cs.getRow(); i++) {
		for (int j = 0; j < cs.getColumn(); j++) {
		    Cell cell = cs.getCell(i, j);
		    int height = 0;
		    int width = 0;
		    int colSpan = 1;
		    int rowSpan = 1;
		    if (!cs.isMerge(i, j)) {//如果此单元格没有被合并
			if (cell != null) {
			    if (isPinpoint) {//是否按报表的宽度高度精确输出
				if (cell.getRow() != null) {
				    try {
					height = Integer.parseInt(cell.getRow().getHeight());
				    } catch (Exception e) {}
				} else {
				    if (cell.getHeight() != null && !cell.getHeight().equals("")) {
					try {
					    height = Integer.parseInt(cell.getHeight());
					} catch (Exception e) {}
				    }
				}
				// 设置单元格宽度
				if (cell.getCol() != null) {
				    try {
					width = Integer.parseInt(cell.getCol().getWidth());
				    } catch (Exception e) {}
				} else {
				    if (cell.getWidth() != null && !cell.getWidth().equals("")) {
					try {
					    width = Integer.parseInt(cell.getWidth());
					} catch (Exception e) {}
				    }
				}
			    }
			}

			if (cell.getImgPath() != null) {// 如果单元格内容为图片
			    Image img = Image.getInstance(cell.getImgPath());
			    com.lowagie.text.Cell docCell;
			    docCell = new com.lowagie.text.Cell(new Phrase(
				    "", fontChinese));
			    Image imgArray[] = new Image[1];
			    imgArray[0] = img;
			    if (cell.getColSpan() > 1) {
				docCell.setColspan(cell.getColSpan());
				colSpan = cell.getColSpan();
			    }
			    if (cell.getRowSpan() > 1) {
				docCell.setRowspan(cell.getRowSpan());
				rowSpan = cell.getRowSpan();
			    }
			    // docCell.setI(imgArray[0]);
			    if (cell.getWidth() != null && cell.getHeight() != null) {
				Table innerTable = new Table(1);
				img.setWidthPercentage((docTable.getWidth() / cs.getColumn())* colSpan);
				// if(imgWidth>0&&imgHeight>0)
				// img.scaleToFit(imgWidth, imgHeight);
			    }
			    docCell.add(img);
			    img.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
			    docCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
			    docCell.setVerticalAlignment(Element.ALIGN_JUSTIFIED_ALL);
			    docTable.addCell(docCell);
			} else {
			    // 设置单元格内容
			    com.lowagie.text.Cell docCell;
//			    if (cell != null && cell.getShowContent() != null) {
				docCell = new com.lowagie.text.Cell(new Phrase(
					formatContent(cell), fontChinese));
				if (cell.getColSpan() > 1)
				    docCell.setColspan(cell.getColSpan());
				if (cell.getRowSpan() > 1)
				    docCell.setRowspan(cell.getRowSpan());
				docCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				docCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			    } else {
//				docCell = new com.lowagie.text.Cell(new Phrase("0", fontChinese));
//				docCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				docCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			    }
			    docTable.addCell(docCell);
			}
		    }
		}

	    }
	    try {
		document.add(docTable);
	    } catch (Exception e) {
	    	log.error(e.getStackTrace());
	    }
	    document.close();
	}
	}
	
	
	
	/**
	 * 格式化单元格内容
	 * @param content 单元格内容
	 * @return 格式化单元格内容
	 */
	protected String formatContent(Cell cell ){
	    	String content = HtmlView.getDataFormat(cell);
		String newContent = content.toLowerCase();
		String tmp = null;
		int iStart = newContent.indexOf("<a") ;
		while(iStart>-1){
			tmp = newContent.substring(iStart);
			int iEnd = tmp.indexOf(">");
			int iEnd2 = tmp.indexOf("</a>");
			if(iEnd >-1 && iEnd2>-1 && iEnd<iEnd2){
				if(iEnd2+4<content.length()){
					content = content.substring(0,iStart)+content.substring(iStart+iEnd+1,iStart+iEnd2)+content.substring(iStart+iEnd2+4);
				} else {
					content = content.substring(0,iStart)+content.substring(iStart+iEnd+1,iStart+iEnd2);
				}
			}
			newContent = content.toLowerCase();
			iStart = newContent.indexOf("<a") ;
		}
		iStart = newContent.indexOf("<img") ;
		while(iStart>-1){
			tmp = newContent.substring(iStart);
			int iEnd = tmp.indexOf(">");
			if(iEnd >-1 ){
				if(iEnd+1<content.length()){
					content = content.substring(0,iStart)+content.substring(iStart+iEnd+1);
				} else {
					content = content.substring(0,iStart);
				}
			}
			newContent = content.toLowerCase();
			iStart = newContent.indexOf("<img") ;
		}
		content = content.replaceAll("&nbsp;","");
		
		return content;
	}	
    
	
	
	public void setReport(Report report[]) {
		this.report = report;
	}    
}
