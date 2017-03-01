package com.linkage.rainbow.ui.report.engine.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.ReportEngine;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 
 * 将报表以Excel的格式输出<br>
 * <p>
 * @version 1.0
 * @author cl 2009-7-25
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */
public class ExcelView {
	
	private static Log log = LogFactory.getLog(ExcelView.class);
	Report report[] = null;
	Map para = null;
	String reportid = "";
    boolean isPinpoint = false;
    /**
     * 根据报表请求类与报表参数构造
     * @param report  报表请求类
     * @param para 报表参数
     */
	public ExcelView(Report report[],Map para ) {
		this.report = report;
		this.para = new HashMap();
		if(report.length>0){
			if(report[0].getPara()!=null)
				this.para.putAll(report[0].getPara());
		}
		if(para!=null)
			this.para.putAll(para);
	}

	public ExcelView() {
	}

	/**
	 * 设置报表请求类
	 * @param report 报表请求类
	 */
	public void setReport(Report report[]) {
		this.report = report;
	}

	/**
	 * 将报表以Excel的格式输出
	 * @param out
	 */
	public String print(OutputStream outStream) {
		String html="";
		try {
			/** 判断是否查无记录 **/
			boolean isNoRecord = true;
			if(!ArrayUtils.isEmpty(report))
			for(int i=0;i<report.length;i++){
				if(report[i].getDsMap().size()>0){
					Iterator<?> iterator = (report[i].getDsMap()).values().iterator();
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
			boolean isBuffXls = para.get("exportBuffXls") != null && para.get("exportBuffXls").toString().equalsIgnoreCase("true");
			boolean isExportMultiTableInSheet = !(para.get("exportMultiTableInSheet") != null && para.get("exportMultiTableInSheet").toString().equalsIgnoreCase("false"));
			HttpServletRequest request = (HttpServletRequest)para.get("request");
			if(request != null && request.getAttribute("comm.report.xlsName") !=null){
				fileName = (String)request.getAttribute("comm.report.xlsName");
			}else{
				fileName = para.get("xlsName")!=null?(String)para.get("xlsName")+System.currentTimeMillis():"report_"+System.currentTimeMillis();
			}
			if(isBuffXls){
				request.setAttribute("comm.report.xlsName",fileName);
			}

			html=para.get("contextPath")+ "/doc/report/"+fileName+".xls";
			fileName = fileName+".xls";
			File file = new File(dir,fileName);
			if(!file.exists()){
				file.createNewFile();
				FileOutputStream fo = new FileOutputStream(file); 
				HSSFWorkbook newWorkbook = new HSSFWorkbook();
				
				if (report != null&&!isNoRecord) {
					for (int i = 0; i < report.length; i++) {
						print(i,report[i], newWorkbook,(String)para.get("exportXlsSheetName"));
					}
				}
				
				newWorkbook.write(fo);
				fo.close();
			}else{
				// 解析EXCEL,追加新报表进入
				InputStream in= new FileInputStream(file);
				HSSFWorkbook wb = getXls(in);
				try {in.close();} catch (Exception e) {}
				int size = wb.getNumberOfSheets();
				if (report != null&&!isNoRecord) {
					for (int i = 0; i < report.length; i++) {
						print(size,i,report[i], wb,(String)para.get("exportXlsSheetName"));
					}
				}
				FileOutputStream fo = new FileOutputStream(file);
				wb.write(fo);
				fo.close();
			}
			//html="<script language='javascript'>window.location.href='"+html+"';</script>";
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return html;
	}

	/**
	 * 根据文件取得EXCEL工作薄
	 * 
	 * @param file 文件流
	 * @return EXCEL工作薄
	 */
	private HSSFWorkbook getXls(InputStream file) {
		HSSFWorkbook hssfworkbook = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(file);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			hssfworkbook = wb;
//			int is = wb.getNumberOfNames();
//			System.out.println("is:"+is);
//			for(int i=0;i<is ;i++ ){
//				System.out.println(wb.getNameName(i));
//			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		} finally {
			if (file != null)
				try {
					file.close();
				} catch (Exception e) {
				}

		}
		return hssfworkbook;
	}

	/**
	 * 将报表以Excel的格式输出
	 * @param iReportIdx
	 * @param report
	 * @param newWorkbook
	 * @param exportXlsSheetName
	 * @throws IOException
	 */
	protected void print(int iReportIdx,Report report,HSSFWorkbook newWorkbook,String exportXlsSheetName) throws IOException{
		print(0,iReportIdx,report,newWorkbook,exportXlsSheetName);
	}
	/**
	 * 将报表以Excel的格式输出
	 * @param iStartSheet
	 * @param iReportIdx
	 * @param report
	 * @param newWorkbook
	 * @param exportXlsSheetName
	 * @throws IOException
	 */
	protected void print(int iStartSheet,int iReportIdx,Report report,HSSFWorkbook newWorkbook,String exportXlsSheetName) throws IOException{
		if(exportXlsSheetName ==null || exportXlsSheetName.trim().length()==0)
			exportXlsSheetName="Sheet"+(iStartSheet+iReportIdx+1);
		HSSFSheet sheet = newWorkbook.getSheet(exportXlsSheetName);
		if(sheet==null)
			sheet=	newWorkbook.createSheet(exportXlsSheetName);
		CellSet cs = report.getNewCS();
		int sheetStartRowNum = sheet.getLastRowNum()==0?0:sheet.getLastRowNum()+1;
		
		String pinpoint = (String)para.get("pinpoint"); 
		boolean isPinpoint = true;
		if(pinpoint != null && pinpoint.toLowerCase().equals("false")){
			isPinpoint = false;
		}
		if (cs != null && cs.getRow() > 0) {
			
			
			for (int i = 0; i < cs.getRow(); i++) {
				int sheetRowIndex = sheetStartRowNum+i;
				HSSFRow hssfRow= sheet.getRow(sheetRowIndex);
				if(hssfRow == null)
					hssfRow=sheet.createRow(sheetRowIndex);
				boolean isSetHeight = false;
				for (int j = 0; j < cs.getColumn(); j++) {
					Cell cell = cs.getCell(i, j);			
					if (!cs.isMerge(i, j)) {
						if (cell != null) {
							int height=0;
							int width=0;
							if(isPinpoint){
								//设置单元格高度
								//if(!isSetHeight && cell.getHeight()>0){
								if(cell.getRow() !=null){
									try {
										height =Integer.parseInt(cell.getRow().getHeight());
									} catch (Exception e) {
										// TODO: handle exception
									}
								}else{
									if(!isSetHeight && cell.getHeight()!= null && !cell.getHeight().equals("")){
										try {
											height =Integer.parseInt(cell.getHeight());
										} catch (Exception e) {
											// TODO: handle exception
										}
									}
								}
								if(height>0){
									hssfRow.setHeight((short)(height*14));
									isSetHeight = true;
								}
								//设置单元格宽度
//								if(cell.getWidth()>0)
								if(cell.getCol() !=null){
									try {
										width =Integer.parseInt(cell.getCol().getWidth());
									} catch (Exception e) {
										// TODO: handle exception
									}
								}else{
									if( cell.getWidth()!= null && !cell.getWidth().equals("")){
										try {
											width =Integer.parseInt(cell.getWidth());
										} catch (Exception e) {
											// TODO: handle exception
										}
									}
								}
								if(width>0){
									sheet.setColumnWidth((short)j,(short)(width*35));
								}
							
							}
							
								
							//新建EXCEL单元格
							HSSFCell  hssfCell = hssfRow.getCell((short)j) ;
							if (hssfCell==null) hssfCell=hssfRow.createCell((short)j);
						
				            //设置单元格样式
							HSSFCellStyle hssfCellStyle = cellStyle2ExcelStyle(newWorkbook,cs,i,j);
							hssfCell.setCellStyle(hssfCellStyle);
							
							if(cell.getImgPath() != null) {//如果单元格内容为图片
								FileOutputStream fileOut = null; 
					            BufferedImage bufferImg = null;

								// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
								ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
								bufferImg = ImageIO.read(new File(cell
										.getImgPath()));
								ImageIO.write(bufferImg, "png", byteArrayOut);

								// HSSFRow row = sheet1.createRow(2);
								HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
								
								HSSFClientAnchor anchor = new HSSFClientAnchor(
										0, 0, 0, 0, (short) j, sheetRowIndex,
										(short) (j+cell.getColSpan()),(sheetRowIndex+cell.getRowSpan()) );
								anchor.setAnchorType(2);
								// 插入图片
								patriarch.createPicture(anchor, newWorkbook
										.addPicture(byteArrayOut.toByteArray(),
												HSSFWorkbook.PICTURE_TYPE_PNG)); 
						        
						     
							}else{
								//设置单元格内容
								setHssfSellValue(cell,hssfCell);
							}
							
							//设置单元格合并
							if (cell.getRowSpan()>1 || cell.getColSpan()>1){ 
								Region region = new Region(sheetRowIndex,(short)j,sheetRowIndex+cell.getRowSpan()-1,(short)(j+cell.getColSpan()-1));
								sheet.addMergedRegion(region);
								for(int row=0; row<cell.getRowSpan();row++){
									HSSFRow hssfRowTmp= sheet.getRow(sheetRowIndex+row);
									if(hssfRowTmp == null)
										hssfRowTmp=sheet.createRow(sheetRowIndex+row);
									for(int col=0; col<cell.getColSpan();col++){
										if(row==0 && col==0) continue;
										HSSFCell  hssfCellTmp = hssfRowTmp.getCell((short)(j+col)) ;
										if (hssfCellTmp==null) hssfCellTmp=hssfRowTmp.createCell((short)(j+col));
										hssfCellTmp.setCellStyle(hssfCellStyle);
										hssfCellTmp.setCellType(HSSFCell.CELL_TYPE_BLANK);
										
									}
								}
							}
						}
					}

				}

			}

		}
	}

	/**
	 * 设置单元格内容
	 * @param cell
	 * @param hssfCell
	 */
	protected void setHssfSellValue(Cell cell,HSSFCell  hssfCell ){
		//单元格内容
		
		Object obj = cell.getShowContent();
		if(obj == null) //如果为空,则取默认值
			obj = cell.getDefaultContent(); 
		if (obj == null){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		}else if (obj instanceof Double){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			hssfCell.setCellValue(((Double)obj).doubleValue());
		}else if (obj instanceof Integer){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			hssfCell.setCellValue(((Integer)obj).doubleValue());
		}else if (obj instanceof Long){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			hssfCell.setCellValue(((Long)obj).doubleValue());
		}else if (obj instanceof Boolean){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
			hssfCell.setCellValue(((Boolean)obj).booleanValue());
		}else if (obj instanceof BigDecimal){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			hssfCell.setCellValue(((BigDecimal)obj).doubleValue());
		}else if (obj instanceof Short){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			hssfCell.setCellValue(((Short)obj).doubleValue());
		}else if (obj instanceof Byte){
			hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			hssfCell.setCellValue(((Byte)obj).doubleValue());
		}else{
			String content =obj.toString();
			content = formatContent(content);
			if(MathUtil.isDigit(content)){
				try {
					hssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					hssfCell.setCellValue( Double.parseDouble(content) );
				} catch (Exception e) {
					hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					hssfCell.setCellValue(content);
				}
			} else {
				hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				hssfCell.setCellValue(content);
			}
		}
		//System.out.println("hssfCell.value="+hssfCell.getStringCellValue());
//		System.out.println("hssfCell.value="+hssfCell.toString());
	}

	/**
	 * 格式化单元格内容
	 * @param content 单元格内容
	 * @return 格式化单元格内容
	 */
	protected String formatContent(String content){
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
	 * 将单元样式转换为EXCEL单元格样式
	 * 
	 * @return
	 */
	protected HSSFCellStyle cellStyle2ExcelStyle(HSSFWorkbook newWorkbook,CellSet cs, int i, int j) {

		HSSFCellStyle excelStyle = newWorkbook.createCellStyle();
		
		
		
		Cell cell = cs.getCell(i, j);
//		字体
//		HSSFFont font =  newWorkbook.createFont();
//		font.setBoldweight(cell.getFont().getBoldweight());
//		font.setColor(cell.getFont().getColor());
//		font.setFontHeight(cell.getFont().getFontHeight());
//		font.setFontHeightInPoints(cell.getFont().getFontHeightInPoints());
//		font.setFontName(cell.getFont().getFontName());
//		font.setItalic(cell.getFont().getItalic());
//		font.setStrikeout(cell.getFont().getStrikeout());
//		font.setTypeOffset(cell.getFont().getTypeOffset());
//		font.setUnderline(cell.getFont().getUnderline());
//		excelStyle.setFont(font); 
		
//		数据格式
		String dataFormat=null;
		if(cell.getDataFormatExpr() != null){
			Object dataFormatTmp= getExprResult(cell,cell.getDataFormatExpr());
			if(dataFormatTmp != null&& dataFormatTmp.toString().trim().length()>0)
				dataFormat = dataFormatTmp.toString().trim();
		}else if(cell.getDataFormat() != null && cell.getDataFormat().length()>0){
			dataFormat = cell.getDataFormat();
		}
		if(dataFormat != null){
			short excelDataFormat = newWorkbook.createDataFormat().getFormat(dataFormat);
			excelStyle.setDataFormat(excelDataFormat);
		}
//		背影色
		
		//excelStyle.setFillBackgroundColor(cell.getFillBackgroundColor().getIndex());
					
		
//		边框样式

		excelStyle.setBorderBottom((short)(cell.getBorderBottom()>-1?cell.getBorderBottom():1));
		excelStyle.setBorderTop((short)(cell.getBorderTop()>-1?cell.getBorderTop():1));
		excelStyle.setBorderLeft((short)(cell.getBorderLeft()>-1?cell.getBorderLeft():1));
		excelStyle.setBorderRight((short)(cell.getBorderRight()>-1?cell.getBorderRight():1));
		
//		excelStyle.setBottomBorderColor(cell.getBorderBottomColor().getIndex());
//		excelStyle.setTopBorderColor((short)(cell.getBorderTopColor().getIndex()));
//		excelStyle.setLeftBorderColor((short)(cell.getBorderLeftColor().getIndex()));
//		excelStyle.setRightBorderColor((short)(cell.getBorderRightColor().getIndex()));
			
		//对齐样式
		excelStyle.setAlignment((short)cell.getAlignment());
		excelStyle.setVerticalAlignment(excelStyle.getVerticalAlignment());
		excelStyle.setIndention(excelStyle.getIndention()); //缩进值
				
		//是否自动换行
		excelStyle.setWrapText(cell.isWrapText());

		return excelStyle;
	}
	

	/**
	 * 取得表达式结果
	 * @param cell
	 * @param content
	 * @return
	 */
	protected Object getExprResult(Cell cell,Expr expr){
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


	public static void main(String[] args) {
		//Pattern p = Pattern.compile("0+");
		//Pattern p = Pattern.compile("#?,?#?#?0.?0*_");
		String content = "abc&nbsp;abc";
		content = content.replaceAll("&nbsp;","");
		System.out.println(content);
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
		
		String s = "adsfsa,dfsfda";
		if(s.equals(".+,.+")){
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
}
