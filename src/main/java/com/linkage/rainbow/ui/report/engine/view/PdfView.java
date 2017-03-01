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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;

public class PdfView {
	private static Log log = LogFactory.getLog(PdfView.class);
	Report report[] = null;
	Map para = null;
	String reportid = "";
    boolean isPinpoint = false;
    
    public PdfView(){
    	
    }
    /**
     * 根据报表请求类与报表参数构造
     * @param report  报表请求类
     * @param para 报表参数
     */    
    public PdfView(Report report[],Map para){
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
			if(request != null && request.getAttribute("comm.report.pdfName") !=null){
				fileName = (String)request.getAttribute("comm.report.pdfName");
			}else{
				fileName = para.get("pdfName")!=null?(String)para.get("pdfName")+System.currentTimeMillis():"report_"+System.currentTimeMillis();
			}			
			html=para.get("contextPath")+ "/doc/report/"+fileName+".pdf";
			fileName = fileName+".pdf";		
			File file = new File(dir,fileName);
			
			Document document = new Document(PageSize.A4);
			PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(file));						
			print(0,report[0], document,writer,para);
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
	protected void print(int iReportIdx,Report report,Document document,PdfWriter writer,Map para) throws IOException,DocumentException{
		print(0,iReportIdx,report,document,writer,para);
	}	
    
	/**
	 * 将报表以pdf的格式输出
	 * @param iStartSheet
	 * @param iReportIdx
	 * @param report
	 * @param document
	 * @throws IOException
	 */	
	protected void print(int iStartSheet, int iReportIdx, Report report,
	    Document document, PdfWriter writer, Map para) throws IOException,
	    DocumentException {
        	CellSet cs = report.getNewCS();
        	String pinpoint = (String) para.get("pinpoint");
        	boolean isPinpoint = true;
        	if (pinpoint != null && pinpoint.toLowerCase().equals("false")) {
        	    isPinpoint = false;
        	}
        	if (!document.isOpen())
        	    document.open();
        
        	BaseFont bfChinese = null;
        	String fontFileName = (String) para.get("basePath") + "/common/fonts/simsun.ttc";
        	File file = new File(fontFileName);
        	if (file.exists()) {
        	    bfChinese = BaseFont.createFont(fontFileName + ",1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        	} else {
        	    bfChinese = BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        	}
        	// BaseFont bfChinese = BaseFont.createFont(
        	// "c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H,
        	// BaseFont.EMBEDDED);
        	// BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
        	// "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        	// BaseFont bfChinese = BaseFont.createFont("STSong-Light,1",
        	// "UniGB-UCS2-H",
        	// BaseFont.NOT_EMBEDDED);
        
        	Font fontChinese = new Font(bfChinese, 8, Font.NORMAL, BaseColor.BLACK);
        	if (cs != null && cs.getRow() > 0) {
        	    PdfPTable pdfTable = new PdfPTable(cs.getColumn());
        	    for (int i = 0; i < cs.getRow(); i++) {
        		for (int j = 0; j < cs.getColumn(); j++) {
        		    Cell cell = cs.getCell(i, j);
        		    int height = 0;
        		    int width = 0;
        		    if (!cs.isMerge(i, j)) {
        			if (cell != null) {
        			    if (isPinpoint) {
        				if (cell.getRow() != null) {
        				    try {
        					height = Integer.parseInt(cell.getRow().getHeight());
        				    } catch (Exception e) {}
        				} else {
        				    if (cell.getHeight() != null&& !cell.getHeight().equals("")) {
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
        				    if (cell.getWidth() != null&& !cell.getWidth().equals("")) {
        					try {
        					    width = Integer.parseInt(cell.getWidth());
        					} catch (Exception e) {}
        				    }
        				}
        			    }
        			    if (width > 0) {
        
        			    }
        			}
        
        			if (cell.getImgPath() != null) {// 如果单元格内容为图片
        			    Image img = Image.getInstance(cell.getImgPath());
        			    PdfPCell pdfCell;
        			    pdfCell = new PdfPCell(new Phrase("", fontChinese));
        			    pdfCell.setPadding(1);
        			    Image imgArray[] = new Image[1];
        			    imgArray[0] = img;
        			    if (cell.getColSpan() > 1)
        				pdfCell.setColspan(cell.getColSpan());
        			    if (cell.getRowSpan() > 1)
        				pdfCell.setRowspan(cell.getRowSpan());
        			    pdfCell
        				    .setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
        			    pdfCell
        				    .setVerticalAlignment(Element.ALIGN_JUSTIFIED_ALL);
        			    pdfCell.setImage(imgArray[0]);
        			    pdfTable.addCell(pdfCell);
        			} else {
        			    // 设置单元格内容
        			    PdfPCell pdfCell = new PdfPCell(new Phrase(
        				    formatContent(cell), fontChinese));
        			    if (cell.getColSpan() > 1)
        				pdfCell.setColspan(cell.getColSpan());
        			    if (cell.getRowSpan() > 1)
        				pdfCell.setRowspan(cell.getRowSpan());
        			    pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        			    pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        			    pdfTable.addCell(pdfCell);
        			}
        		    }
        		}
        	    }
        	    try {
        		document.add(pdfTable);
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
	
	/**
	 * 设置报表请求类
	 * @param report 报表请求类
	 */
	public void setReport(Report report[]) {
		this.report = report;
	}
	
}
