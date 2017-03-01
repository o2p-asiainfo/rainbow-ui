/*
 * @(#)ReportEngine        2009-6-1
 *
 *
 */
package com.linkage.rainbow.ui.report.engine.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.engine.util.Properties;
import com.linkage.rainbow.ui.report.engine.core.expr.CSVariable;
import com.linkage.rainbow.ui.report.engine.core.expr.DBVariable;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Coordinate;
import com.linkage.rainbow.ui.report.engine.model.Event;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.model.Range;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.DirectionUtil;
import com.linkage.rainbow.ui.report.engine.util.IbatisUtil;
import com.linkage.rainbow.ui.report.engine.util.Resources;
import com.linkage.rainbow.ui.report.engine.view.ExcelView;
import com.linkage.rainbow.ui.report.engine.view.HtmlView;
import com.linkage.rainbow.ui.report.engine.view.HtmlViewDrill;
import com.linkage.rainbow.ui.report.engine.view.PdfView;
import com.linkage.rainbow.ui.report.engine.view.DocView;

/**
 * 报表引擎<br>
 * 负责接收报表参数，解析报表定义，建立与运算公式表达式，扩展报表，输入报表等<p>
 * @version 1.0
 * @author 陈亮 2009-6-1
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 陈亮   修改时间:2009-6-1<br>       
 *    修改内容:新建
 * <hr>
 */
public class ReportEngine {
	
	/**报表引擎初始化配置属性*/
	private static Properties prop;
	private static Log log = LogFactory.getLog(ReportEngine.class);

	/** 报表引擎初始化*/
	static {
		try {
			init();
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new RuntimeException(
					"Error initializing ReportEngine class. Cause:" + e);
		}
	}
	/**
	 * 取得报表引擎初始化配置属性
	 * @return
	 */
	public static Properties getProp() {
		return prop;
	}
	/**
	 * 报表引擎初始化
	 * 
	 * @throws Exception
	 */
	private static void init() throws Exception {
		System.out.println("init");
		// 取得报表引擎配置文件
		String resource = "com/linkage/rainbow/ui/default.properties";
		String resource2= "com/linkage/rainbow/ui/report/engine/report-config.properties";
		prop = new Properties();
		prop.load(resource);
		
		String customResource = "comm.properties";
		 //项目组自定义配置文件
		InputStream in = null;
        try {
        	in = Resources.getResourceAsStream(resource2);
  	        Properties customProp = new Properties();
  			customProp.load(in);
  			for (Iterator i = customProp.keySet().iterator(); i.hasNext(); ) {
  	            String nameTmp = (String) i.next();
  	            prop.setProperty(nameTmp, customProp.getProperty(nameTmp));
  	        }
  			
	        in = Resources.getResourceAsStream(customResource);
	        customProp = new Properties();
			customProp.load(in);
			for (Iterator i = customProp.keySet().iterator(); i.hasNext(); ) {
	            String nameTmp = (String) i.next();
	            prop.setProperty(nameTmp, customProp.getProperty(nameTmp));
	        }
        } catch (IOException e) {
        	 //System.out.println("WARN: Could not load " + customResource + e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException io) {
                }
            }
        }

		// 如果有配置ibatis，则初始化ibatis
//		if (prop.get("ibatis_init_class") != null
//				|| prop.get("ibatis_init_xml") != null) {
//			IbatisUtil.init(prop);
//		} 

	}
	
	/**
	 * 运行报表
	 * 
	 * @param para
	 * @param out
	 */
//	public static void run(Map para, OutputStream outStream) {
//		PrintWriter out = new PrintWriter(outStream);
//		run(para,out);
//	}
	
	
	/**
	 * 运行报表
	 * 
	 * @param report 报表请求类
	 * @return 返回报表Html
	 */
	public static String run(Report report) {
		Report r[] = new Report[1];
		r[0] = report;
		return run(r);
	}
	
	/**
	 * 运行报表
	 * 
	 * @param report 报表请求类 
	 * @param out 报表结果输出流
	 * @return 
	 */
	public static String run(Report report, OutputStream out) {
		Report r[] = new Report[1];
		r[0] = report;
		return run(r,out);
	}
	
	
	/**
	 * 运行报表
	 * 
	 * @param report 报表请求类 
	 * @return 返回报表Html
	 */
	public static String run(Report report[]) {
		return run(report,null);
	}
	
	/**
	 * 运行报表
	 * @param report 报表请求类 
	 * @param out 报表结果输出流
	 * @return 
	 */
	public static String run(Report report[], OutputStream out) {
		String reulst=null;
		try {
			
			long pre = System.currentTimeMillis();
			createDB(report);
			long curr = System.currentTimeMillis();
			long time = (curr - pre) / 1000;
			pre = curr;
//			System.out.println("产生记录集:" + time);
			// 计算报表
			operation(report,null);
	
			curr = System.currentTimeMillis();
			time = (curr - pre) / 1000;
//			System.out.println("计算报表:" + time);
	
			// 输出报表
			reulst = print(report, out, null);
	
			pre = curr;
			curr = System.currentTimeMillis();
			time = (curr - pre) / 1000;
//			System.out.println("输出报表:" + time);
		
		} catch (Exception e) {
			//System.out.println("aaaaaaaaaaaaaaaaaa");
			log.error(e.getStackTrace());
			try {
				PrintWriter pout = new PrintWriter(out);
				pout.println(e.toString());
				pout.flush();
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
		}
		return reulst;
	}
	/**
	 * 运行报表
	 * 
	 * @param para 报表参数
	 * @return 返回报表Html
	 */
	public static String run(Map para) {
		return run(para,null);
	}
	/**
	 * 运行报表
	 * 
	 * @param para  报表参数
	 * @param out 输出流
	 * @return 返回报表Html
	 */
	//public static void run(Map para, Writer out) {
	public static String run(Map para, OutputStream out) {
		try {
		
			long pre = System.currentTimeMillis();
			// 取得报表路径
			String reportName = (String) para.get("report");
			// 生成报表定义
			Report report[] = read(reportName, para);
			long curr = System.currentTimeMillis();
			long time = (curr - pre) / 1000;
//			System.out.println("生成报表定义:" + time);
	
			// 计算报表
			operation(report, para);
	
			pre = curr;
			curr = System.currentTimeMillis();
			time = (curr - pre) / 1000;
//			System.out.println("计算报表:" + time);
	
			// 输出报表
			print(report, out, para);
	
			pre = curr;
			curr = System.currentTimeMillis();
			time = (curr - pre) / 1000;
//			System.out.println("输出报表:" + time);
		
		} catch (Exception e) {
			//System.out.println("aaaaaaaaaaaaaaaaaa");
			log.error(e.getStackTrace());
			try {
				PrintWriter pout = new PrintWriter(out);
				pout.println(e.toString());
				pout.flush();
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 从配置文件中读取报表定义,并取得记录集
	 * 
	 * @param fileName
	 * @param para
	 * @deprecated
	 */
	public static Report[] read(String fileName, Map para)
			throws ReportException {
		Report report[] = null;
		try {
//			report = new ExcelParse().createCSFromXls(fileName, para);
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new ReportException(e.getMessage());
		}

		return report;
	}


	

	/**
	 * 报表运算
	 * 
	 * @param report
	 * @return
	 */
	private static Report[] operation(Report reports[], Map globalPara) {
		if (reports != null) {
			for (int currReport = 0; currReport < reports.length; currReport++) {
				Report report = reports[currReport];
				Map para = new HashMap();
				if(globalPara!= null)
					para.putAll(globalPara);
				if(report.getPara() != null)
					para.putAll(report.getPara());
				
				createExpr(report);
				CellSet newCS = report.getNewCS(); // 生成最终报表的单元格列表
                int xBulge = -1;
                int bulgeNorm = -1;
				for (int row = 0; row < newCS.getRow(); row++) { 
//					if(row==2)
//						System.out.println(row);
					for (int col = 0; col < newCS.getColumn(); col++) {
						//System.out.println(newCS.toString());
						newCS.setRowIndex(row); 
						newCS.setColIndex(col); 
						Cell cell = newCS.getCurrCell();
						if(cell!=null && !cell.isExtended() ){
							if( cell.isExpr()){
								Expr expr = cell.getExpr();
								Object result = null;
								try {
									result= expr.calculate();
								} catch (Exception e) {
									result = e.toString();
								}
								if(result instanceof Object[]){
									extendCellSet(newCS,row,col,(Object[])result);
								}else if(result instanceof List){
									extendCellSet(newCS,row,col,(List)result);	
								}else{
									Object tmpResult[]={result};
									extendCellSet(newCS,row,col,tmpResult);	//cell.setContent(result);
								}
							} else {//非表达式,则替换@变量后输出
								String value = cell.getContent() != null ? cell
								.getContent().toString() : "";
								cell.setContent(setVar(value,para));
							}
						}
					}
				}
				//System.out.println(report.getNewCS());
			}
		}
		return reports;
	}

	/**
	 * 建立表达式实例
	 * @return
	 */
	private static Report createExpr(Report report){
		CellSet newCS = report.getNewCS(); // 生成最终报表的单元格列表
		for (int i = 0; i < newCS.getRow(); i++) {
			for (int j = 0; j < newCS.getColumn(); j++) {
				Cell cell = newCS.getCell(i, j);
				/********************************************内容表达式***************************************************************/
				//内容表达式
				String value = cell != null
						&& cell.getContent() != null ? cell
						.getContent().toString() : "";
				if (isExpr(value)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(value),report);
						cell.setExpr(e);
						
						//设置关联数据表达式
						int iDbFuncListSize = e.getDbFuncList().size();
						for(int k=0;k<iDbFuncListSize;k++){
							DBFunc dbFunc = (DBFunc)e.getDbFuncList().get(k);
							if(dbFunc.getFilterExp()!= null){
								List csVarList = dbFunc.getFilterExp().getCsVarList();
								for(int l=0;l<csVarList.size();l++){
									CSVariable csVariable = (CSVariable)csVarList.get(l);
									Cell relCell = newCS.getCell( csVariable.getCoordinate());
									Sign sign= csVariable.fSign.left;
									if(sign instanceof DBVariable)
										relCell.addRelDBVar((DBVariable)sign);
								}
							}
						}
					} catch (Exception e) {
						log.error(e.getStackTrace());
						cell.setContent(e.toString());
					}
				}
				/********************************************链接表达式***************************************************************/
				//链接表达式
				String link = cell != null
						&& cell.getLink() != null ? cell
						.getLink().toString() : "";
				if (isExpr(link)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(link),report);
						cell.setLinkExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}
				/********************************************样式表达式***************************************************************/
				//样式表达式
				String style = cell != null
						&& cell.getStyle() != null ? cell
						.getStyle().toString() : "";
				if (isExpr(style)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(style),report);
						cell.setStyleExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}

				//样式表表达式
				String styleClass = cell != null
						&& cell.getStyleClass() != null ? cell
						.getStyleClass().toString() : "";
				if (isExpr(styleClass)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(styleClass),report);
						cell.setStyleClassExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}

				//前景色表达式
				String color = cell != null
						&& cell.getColor() != null ? cell
						.getColor().toString() : "";
				if (isExpr(color)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(color),report);
						cell.setColorExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}
				
				//背景图表达式
				String background = cell != null
						&& cell.getBackground() != null ? cell
						.getBackground().toString() : "";
				if (isExpr(background)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(background),report);
						cell.setBackgroundExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}
				
				//背景色表达式
				String bgcolor = cell != null
						&& cell.getBgcolor() != null ? cell
						.getBgcolor().toString() : "";
				if (isExpr(bgcolor)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(bgcolor),report);
						cell.setBgcolorExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}
				
				//数据格式表达式
				String dataFormat = cell != null
				&& cell.getDataFormat() != null ? cell
				.getDataFormat().toString() : "";
				if (isExpr(dataFormat)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(dataFormat),report);
						cell.setDataFormatExpr(e);
					} catch (Exception e) {
						cell.setContent(e.toString());
					}
				}
				
				/********************************************事件表达式***************************************************************/
				//计算单击事件的表达式
				String onclick = cell != null
						&& cell.getOnclick() != null ? cell
						.getOnclick().toString() : "";
				if (isExpr(onclick)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onclick),report);
						cell.setOnclickExpr(e);
					} catch (Exception e) {
						cell.setOnclick(e.toString());
					}
				}

				//计算双击事件的表达式
				String ondblclick = cell != null
						&& cell.getOndblclick() != null ? cell
						.getOndblclick().toString() : "";
				if (isExpr(ondblclick)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(ondblclick),report);
						cell.setOndblclickExpr(e);
					} catch (Exception e) {
						cell.setOndblclick(e.toString());
					}
				}

				//计算鼠标移出事件的表达式
				String onmouseout = cell != null
						&& cell.getOnmouseout() != null ? cell
						.getOnmouseout().toString() : "";
				if (isExpr(onmouseout)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onmouseout),report);
						cell.setOnmouseoutExpr(e);
					} catch (Exception e) {
						cell.setOnmouseout(e.toString());
					}
				}

				//计算鼠标点击事件的表达式
				String onmousedown = cell != null
						&& cell.getOnmousedown() != null ? cell
						.getOnmousedown().toString() : "";
				if (isExpr(onmousedown)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onmousedown),report);
						cell.setOnmousedownExpr(e);
					} catch (Exception e) {
						cell.setOnmousedown(e.toString());
					}
				}

				//计算鼠标移入事件的表达式
				String onmouseover = cell != null
						&& cell.getOnmouseover() != null ? cell
						.getOnmouseover().toString() : "";
				if (isExpr(onmouseover)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onmouseover),report);
						cell.setOnmouseoverExpr(e);
					} catch (Exception e) {
						cell.setOnmouseover(e.toString());
					}
				}
				
			}
		}
		
		//建立行定义与列定义表达式
		createRangeExpr(report,newCS.getRowRangeList());
		createRangeExpr(report,newCS.getColRangeList());
		
		
		return report;
	}
	
	//建立行定义与列定义表达式
	private static void createRangeExpr(Report report ,List rowRangeList){

		if(rowRangeList != null){
			int iSize = rowRangeList.size();
			for(int i=0;i<iSize;i++){
				Range range = (Range)rowRangeList.get(i);
				
				/********************************************样式表达式***************************************************************/
				//样式表达式
				String style = range != null
						&& range.getStyle() != null ? range
						.getStyle().toString() : "";
				if (isExpr(style)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(style),report);
						range.setStyleExpr(e);
					} catch (Exception e) {
						range.setStyle(e.toString());
					}
				}

				//样式表表达式
				String styleClass = range != null
						&& range.getStyleClass() != null ? range
						.getStyleClass().toString() : "";
				if (isExpr(styleClass)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(styleClass),report);
						range.setStyleClassExpr(e);
					} catch (Exception e) {
						range.setStyleClass(e.toString());
					}
				}

				//背景图表达式
				String background = range != null
						&& range.getBackground() != null ? range
						.getBackground().toString() : "";
				if (isExpr(background)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(background),report);
						range.setBackgroundExpr(e);
					} catch (Exception e) {
						range.setBackground(e.toString());
					}
				}
				
				//背景色表达式
				String bgcolor = range != null
						&& range.getBgcolor() != null ? range
						.getBgcolor().toString() : "";
				if (isExpr(bgcolor)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(bgcolor),report);
						range.setBgcolorExpr(e);
					} catch (Exception e) {
						range.setBgcolor(e.toString());
					}
				}
				/********************************************事件表达式***************************************************************/
				//计算单击事件的表达式
				String onclick = range != null
						&& range.getOnclick() != null ? range
						.getOnclick().toString() : "";
				if (isExpr(onclick)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onclick),report);
						range.setOnclickExpr(e);
					} catch (Exception e) {
						range.setOnclick(e.toString());
					}
				}

				//计算双击事件的表达式
				String ondblclick = range != null
						&& range.getOndblclick() != null ? range
						.getOndblclick().toString() : "";
				if (isExpr(ondblclick)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(ondblclick),report);
						range.setOndblclickExpr(e);
					} catch (Exception e) {
						range.setOndblclick(e.toString());
					}
				}

				//计算鼠标移出事件的表达式
				String onmouseout = range != null
						&& range.getOnmouseout() != null ? range
						.getOnmouseout().toString() : "";
				if (isExpr(onmouseout)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onmouseout),report);
						range.setOnmouseoutExpr(e);
					} catch (Exception e) {
						range.setOnmouseout(e.toString());
					}
				}

				//计算鼠标点击事件的表达式
				String onmousedown = range != null
						&& range.getOnmousedown() != null ? range
						.getOnmousedown().toString() : "";
				if (isExpr(onmousedown)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onmousedown),report);
						range.setOnmousedownExpr(e);
					} catch (Exception e) {
						range.setOnmousedown(e.toString());
					}
				}

				//计算鼠标移入事件的表达式
				String onmouseover = range != null
						&& range.getOnmouseover() != null ? range
						.getOnmouseover().toString() : "";
				if (isExpr(onmouseover)) {//判断字符是否为表达式
					try {
						Expr e = new Expr(getExprStr(onmouseover),report);
						range.setOnmouseoverExpr(e);
					} catch (Exception e) {
						range.setOnmouseover(e.toString());
					}
				}
			}
		}
	}
	
	/**
	 * 取得扩展的最大范围
	 * @return
	 */
	private static Field getExtentField(CellSet newCS,int row,int col){
		
		int iStartRow=0;
		int iEndRow = newCS.getRow()-1;
		for(int i=row;i>=0;i--){
			Cell cell = newCS.getCell(i,0);
			if(cell!=null){
				if(cell.getRow() != null && cell.getRow().getAreaType() != null && cell.getRow().getAreaType().equals("separate")){
					iStartRow = i+1;
					break;
				}
			}
		}
		
		for(int i=row;i<newCS.getRow();i++){
			Cell cell = newCS.getCell(i,0);
			if(cell!=null){
				if(cell.getRow() != null && cell.getRow().getAreaType() != null && cell.getRow().getAreaType().equals("separate")){
					iEndRow = i-1;
					break;
				}
			}
		}
		Field field = new Field(iStartRow,newCS.getColumn(),iEndRow,newCS.getColumn());
		return field;
	}
	/**
	 * 扩展单元格
	 * @param newCS
	 */
	private static void extendCellSet(CellSet newCS,int row,int col,Object result[]){
		int iLength = result.length;
		Cell cell = newCS.getCell(row, col);
		
		String extDirectionExp = DirectionUtil.getDirection(cell.getExtDirection());
		boolean isLengthways = extDirectionExp.equals("y")?true:false; //是否为纵向
		String dsName=null;
		
		long pre = System.currentTimeMillis();
		long curr=0l ;
		long time =0l;
		
		if(iLength==0){
			cell.setContent(null);
			return;
		}
		Field extentField = null;
		if(iLength>1){
			if(isLengthways){//如果有纵向扩展,则加行
				newCS.insertRow(row+cell.getRowSpan(),(iLength-1)*cell.getRowSpan());
			}else {//如果有横向扩展,则加列
				extentField = getExtentField(newCS,row,col);
				newCS.insertColumn(extentField.getStartRow(),extentField.getEndRow(), col+cell.getColSpan(),(iLength-1)*cell.getColSpan());
			}
		}
		
		for(int i=0;i<iLength;i++){
			if(i>0&& i%10000==0){
				curr = System.currentTimeMillis();
				time = (curr - pre) / 1000;
//				System.out.println("10000使用时间:" + time);
				pre = System.currentTimeMillis();
			}
				
			//克隆原有单元格后,设置单元格值
			Cell newCell = (Cell)cell.clone();
			Object eventObj = null;
			if(result[i] instanceof Object[]){
				Object[] array = (Object[])result[i];
				if(array.length>0)
					newCell.setContent(array[0]);
				if(array.length>1)
					newCell.setShowContent(array[1]);
			}else if(result[i] instanceof DBGroup){ //为分组值
				DBGroup group =(DBGroup)result[i];
				eventObj = group;
				newCell.setContent(group.getValue());
				newCell.setShowContent(group.getShowvalue());
				dsName = group.getDs().getDsName();
				newCell.setRelDBObj(group);
				group.setDrill(newCell);
			}else if(result[i] instanceof BaseRow){ //为分组值
				BaseRow baseRow =(BaseRow)result[i];
				eventObj = baseRow;
				newCell.setContent(baseRow.getValue());
				newCell.setShowContent(baseRow.getShowvalue());
				dsName = baseRow.getDs().getDsName();
				newCell.setRelDBObj(baseRow);
			}else {
				newCell.setContent(result[i]);
			}
			newCell.setExtended(true);
//			if(i>0){
//				if(isLengthways){//如果有纵向扩展,则加行
//					newCS.insertRow(row+(i*cell.getRowSpan()),cell.getRowSpan());
//				}else {//如果有横向扩展,则加列
//					newCS.insertColumn(col+(i*cell.getColSpan()),cell.getColSpan());
//				}
//			}
			//如果此单元格为交叉单元格,则不作事件通知
			if(!cell.getEvents().isCross(dsName)){
				Event event = new Event();
				event.setEventValue(eventObj);
				event.setLengthways(isLengthways);
				event.setSrcCoordinate(newCell.getExtField());
				event.setSrcCell(newCell);
				if(isLengthways){//如果有纵向扩展,则加行
					setEvent(newCS,cell,isLengthways,row+(i*newCell.getRowSpan()),row+(i*newCell.getRowSpan())+newCell.getRowSpan(),col+newCell.getColSpan(),newCS.getColumn(),event);
				}else {//如果有横向扩展,则加列
					int iEndRow = newCS.getRow();
					if(extentField != null)//是否有分隔行,分隔行以后不做事件通知
						iEndRow = extentField.getEndRow()+1;
					setEvent(newCS,cell,isLengthways,row+newCell.getRowSpan(),iEndRow,col+(i*newCell.getColSpan()),col+(i*newCell.getColSpan())+newCell.getColSpan(),event);
				}
			}
			
			
			if(isLengthways){//设置新单元格位置
				newCS.setCell(row+(i*cell.getRowSpan()),col,newCell);
			}else {
				newCS.setCell(row,col+(i*cell.getColSpan()),newCell);
			}
			
		}
	}
	
	private static void setEvent(CellSet newCS,Cell cell,boolean isLengthways,int startRow,int endRow,int startCol,int endCol,Event event ){

		for(int i=startRow;i<endRow;i++){
			for(int j=startCol;j<endCol;j++){
				Cell tmpCell = newCS.getCell(i,j);
				if(tmpCell !=null){
					if(!cell.getExtField().equals(tmpCell.getExtField()))
						tmpCell.addEvent(event);
				}
			}
		}
	}
	private static void extendCellSet(CellSet newCS,int row,int col,List result){
		extendCellSet(newCS,row,col,result.toArray());
	}
	/**
	 * 取得关联的主单元格坐标
	 * 
	 * @return
	 */
	private static String getHostCellCoor(CellSet oldCS, int x, int y) {
		String strCoor = null;

		//for (int i = y - 1; i >= 0; i--) {
			Coordinate mergeCellCoor = oldCS.getMergeCellCoor(x, y - 1);
			if (mergeCellCoor != null && mergeCellCoor.getStartRow()<x) {
				strCoor = mergeCellCoor.toExcelCoor();
//				break;
			}
		//}
		if (strCoor == null) {
//			for (int i = y + 1; i < oldCS.getColumn(); i++) {
				mergeCellCoor = oldCS.getMergeCellCoor(x, y + 1);
				if (mergeCellCoor != null&& mergeCellCoor.getStartRow()<x) {
					strCoor = mergeCellCoor.toExcelCoor();
//					break;
				}
//			}
		}
		return null;
	}

	private static boolean isExpr(String strContent) {
		boolean isExpr = false;
		if (strContent != null && (
				(strContent.trim().startsWith("${")&& strContent.trim().endsWith("}"))
				||strContent.trim().startsWith("=") 
			)) {
			isExpr = true;
		}
		return isExpr;
	}
	
	private static String getExprStr(String strContent) {
		String exprStr = "";
		if (strContent != null ){
			if(strContent.trim().startsWith("${")&& strContent.trim().endsWith("}")){
				exprStr = strContent.substring(2,strContent.length()-1);
			} else if(strContent.trim().startsWith("=")) {
				exprStr = strContent.substring(1);
			}
		}
		return exprStr;
	}
//	private static void print(Report reports[], OutputStream outStream, Map para) {
//		PrintWriter out = new PrintWriter(outStream);
//		print(reports,out,para);
//	}
//	private static void print(Report reports[], Writer out, Map para) {
	

				
	private static String print(Report reports[], OutputStream out, Map globalPara) {
		String reulst=null;
		String format =null;
		if(globalPara != null)
			format = (String) globalPara.get("format");
		else
			format = (String) (reports[0].getPara().get("format"));
		if (format != null) {
			format = format.trim().toLowerCase();
			if (format.equals("htm") || format.equals("html")) {
				HtmlView htmlView = new HtmlView(reports, globalPara);
				reulst = htmlView.print(out);
				//System.out.println(reulst);
			}else if (format.equals("drill")) {
				HtmlViewDrill htmlViewDrill = new HtmlViewDrill(reports, globalPara);
				reulst = htmlViewDrill.print(out);
				//System.out.println(reulst);
			}else if (format.equals("xls")) {
				ExcelView excelView = new ExcelView(reports, globalPara);
				reulst = excelView.print(out);
				//System.out.println(reulst);
			}else if (format.equals("pdf")){
				PdfView pdfView=new PdfView(reports,globalPara);
				reulst = pdfView.print(out);
				//System.out.println(reulst);
			}else if (format.equals("doc")){
				DocView docView=new DocView(reports,globalPara);
				reulst = docView.print(out);
				//System.out.println(reulst);				
			}
		} else {
			HtmlView htmlView = new HtmlView(reports, globalPara);
			reulst = htmlView.print(out);
		}
		return reulst;
	}
	
	/**
	 * 取得单元格中定义的参数
	 * @param obj
	 * @param para
	 * @return
	 */
	private static Object setVar(Object obj, Map para){
		Object newContent = null;
		if(obj != null&&obj instanceof String){
			String content = obj.toString();
			newContent = content;
			int index = content.indexOf("@");
			int length = content.length();
			while( index>-1 && index+1<length ){
			  	if (!isForeAftQuot(content,index)){
			  		
			  		String varName = null;
			  		String varNameEnd = "";
			  		for(int i = index+1;i<length;i++ ){
			  			if(content.charAt(i) == ' ' ||content.charAt(i) == '@'){
			  				varName = content.substring(index+1,i);
			  				if(content.charAt(i) == ' ')
			  					varNameEnd = " ";
			  				break;
			  			}
			  		}
			  		if(varName == null)
			  			varName = content.substring(index+1);
			  		
			  		String str = (String)para.get(varName);
	  				if(str == null)
	  				  str = "";
	  				newContent = ((String)newContent).replaceAll("@"+varName+varNameEnd,str);
			  		
			  		
			  	} 
			  	
			  	content = content.substring(index+1);
			  	length = content.length();
				index = content.indexOf("&");
			}
		} else {
			newContent = obj;
		}
		
		return newContent;
	}
	
	/**
	 * 判断是否前后为引号
	 * @return
	 */
	private static boolean isForeAftQuot(String str,int index){
		if(index -1 >=0 && index+1<str.length()){
			if(str.charAt(index -1)== '\''  && str.charAt(index+1)=='\'')  {
				return true;
			}
			if(str.charAt(index -1 )=='\"' && str.charAt(index+1)=='\"' ){
				return true;
			}
		}
		return false;
	}

	private static void createDB(Report reports[]) {
		if(reports != null){
			for(int s=0;s<reports.length;s++){
				Report report=reports[s];
				CellSet cs = report.getDefCS();
				Map dsMap = report.getDsMap();
				for(Iterator it =dsMap.keySet().iterator();it.hasNext();){
					String key = (String)it.next();
					Object obj = dsMap.get(key);
					if (obj instanceof List) {
						List list = (List) obj;
						CachedRowSet rs = new CachedRowSet();
						rs.setDsName(key);
						rs.populate(list);
						report.setDs(key,rs);					
					}
				}
			}
		}

	}
	/**
	 * 根据SQL定义生成记录集．
	 * 
	 * @param report
	 * @param sql
	 */
//	private static void createDB(Report reports[]) {
//		if(reports != null){
//			for(int s=0;s<reports.length;s++){
//				Report report=reports[s];
//				CellSet cs = report.getDefCS();
//		
//				// 保存CellSet中有出现过的记录集，在处理时，只能在CellSet中有出现的记录集名称才生成．
//				Set dsNameSet = new java.util.LinkedHashSet();
//				// 保存某个记录集对应的分组类别:dsname-->List
//				Map groupSortMap = new HashMap();
//				// 某个坐标位置对应的分组信息．
//				Map coorToGroupSort = new HashMap();
//		
//				FuncParse funcParse = new FuncParse();
//		
//				
//				// 对CellSet进行遍历，分析取得出现的记录集名称与分组关系．
//				for (int i = 0; i < cs.getRow(); i++) {
//		//			if (i == 5)
//		//				i = 5;
//					for (int j = 0; j < cs.getColumn(); j++) {
//						Cell cell = cs.getCell(i, j);
//						if (cell != null) {
//							String strContent = cell.getContent() != null ? cell
//									.getContent().toString() : null;
//						if (isExpr(strContent)) {//判断字符是否为表达式
//								funcParse.setSrcContent(getExprStr(strContent));
//								if (funcParse.getDsName() != null) {
//									//dsNameSet.add(funcParse.getDsName());
//									dsNameSet.addAll(funcParse.getDsNameSet());
//									if (funcParse.getFunName() != null
//											&& funcParse.getFunName().equals("group")) {
//										String str = (String) getFGroupCoor(cs, i, j,
//												funcParse.getDsName(), coorToGroupSort);
//										if (str != null) {
//											String strs[] = str.split(",");
//											
//											List groupSort = (List) groupSortMap
//													.get(strs[0]);
//		//									List groupCol = (List) groupSort
//		//											.get(Integer.parseInt(strs[1]));
//											Set groupCol = (Set) groupSort
//											.get(Integer.parseInt(strs[1]));
//											groupCol.add(funcParse.getSubPara(0,0));
//		
//											coorToGroupSort.put(funcParse.getDsName()
//													+ "," + i + "," + j, funcParse
//													.getDsName()
//													+ ","
//													+ (groupSort.size()-1)
//													+ ","
//													+ (groupCol.size() - 1));
//										} else {
//		
//		//									List groupCol = new ArrayList();
//											Set groupCol = new java.util.LinkedHashSet();
//											groupCol.add(funcParse.getSubPara(0,0));
//											int groupSortSize = 0;
//											List groupSort = (List) groupSortMap
//													.get(funcParse.getDsName());
//											if (groupSort == null) {
//												groupSort = new ArrayList();
//											} else {
//												groupSortSize = groupSort.size();
//											}
//		
//											groupSort.add(groupCol);
//											groupSortMap.put(funcParse.getDsName(),
//													groupSort);
//		
//											coorToGroupSort.put(funcParse.getDsName()
//													+ "," + i + "," + j, funcParse
//													.getDsName()
//													+ "," + groupSortSize + ",0");
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//		
//				// 将各个记录对应的分组信息，组织成字符串，如:f_name,f_name_show(colBoxOff)prod_name,prod_name_show(rowBoxOff)day_id,day_id_show(colBoxOff)area_name,area_name_show
//				Map dsNameToGroup = new HashMap();
//				int dsNameSetSize = dsNameSet.size();
//				String strDsNames[] = new String[dsNameSetSize];
//				dsNameSet.toArray(strDsNames);
//				for (int i = 0; i < dsNameSetSize; i++) {
//					String strGroups = "";
//					// 取得对应的分组类别
//					List groupSort = (List) groupSortMap.get(strDsNames[i]);
//					if (groupSort != null) {
//		
//						int groupSortSize = groupSort.size();
//						for (int j = 0; j < groupSortSize; j++) {
//		//					List groupCol = (List) groupSort.get(j);
//							Set groupCol = (Set) groupSort.get(j);
//							int iGroupColSize = groupCol.size();
//							String strGroupCols[] = new String[iGroupColSize];
//							groupCol.toArray(strGroupCols);
//							
//							String strGroupCol = "";
//							for(int k =0;k<iGroupColSize;k++){
//								
//		//						String strOneCol = (String)groupCol.get(k);
//								String strOneCol = strGroupCols[k];
//								if(k==0){
//									strGroupCol += strOneCol;
//								} else {
//									strGroupCol += DBGroup.colBoxOff+strOneCol;
//								}
//								
//							}
//		//					String strGroupCol = groupCol.toString();
//		//					strGroupCol = strGroupCol.substring(1,
//		//							strGroupCol.length() - 1);
//							if (j != 0) {
//								strGroups = strGroups + DBGroup.rowBoxOff + strGroupCol;
//							} else {
//								strGroups = strGroupCol;
//							}
//		
//						}
//							
//					}
//					dsNameToGroup.put(strDsNames[i], strGroups);
//				}
//		
//				Map dsMap = report.getDsMap();
//				for(Iterator it =dsMap.keySet().iterator();it.hasNext();){
//					String key = (String)it.next();
//					Object obj = dsMap.get(key);
//					if (obj instanceof List) {
//						List list = (List) obj;
//						CachedRowSet rs = new CachedRowSet();
//						String strGroups = (String) dsNameToGroup.get(key);
//						if (strGroups != null) {
//							rs.setGroupCols(strGroups);
//						}
//						try {
//							rs.populate(list);
//						} catch (Exception e) {
//							throw new ReportException(e.toString());
//						}
//						report.setDs(key,rs);
//						
//						
//						
//						
//					}
//				}
//			}
//		}
//
//	}
//	
//	/**
//	 * 取得是否有对应的上级Group
//	 * 
//	 * @param cs
//	 * @param iRow
//	 * @param iCol
//	 * @param dsName
//	 * @param colToGroupSort
//	 * @return
//	 */
//	private static Object getFGroupCoor(CellSet cs, int iRow, int iCol, String dsName,
//			Map colToGroupSort) {
//
//		// 分别从左端与上端取得上级单元格，并根据上级单元格坐标，取得对应的group．
//		Field fField = cs.getFCellField(iRow, iCol, "x");
//		if (fField != null) {
//			String strKey = dsName + "," + fField.getStartRow() + ","
//					+ fField.getStartColumn();
//			if (colToGroupSort.get(strKey) != null) {
//				return colToGroupSort.get(strKey);
//			}
//		}
//		fField = cs.getFCellField(iRow, iCol, "y");
//		if (fField != null) {
//			String strKey = dsName + "," + fField.getStartRow() + ","
//					+ fField.getStartColumn();
//			if (colToGroupSort.get(strKey) != null) {
//				return colToGroupSort.get(strKey);
//			}
//		}
//
//		return null;
//	}
//	
	//纵向扩展例子
	private CellSet getRowList(){
		CellSet cellSet = new CellSet(10, 10);
		Cell cell = new Cell();
		cell.setContent("编号"); 
		cellSet.setCell(1, 1, cell);
		cell = new Cell();
		cell.setContent("姓名");
		cellSet.setCell(1, 2, cell);
		cell = new Cell();
		cell.setContent("学历");
		cellSet.setCell(1, 3, cell);
		
		cell = new Cell();
		cell.setContent("=ds.group(StartRow)"); 
//		cell.setRowSpan(4);
		cellSet.setCell(2, 1, cell);
		cell = new Cell();
		cell.setContent("=ds.group(StartColumn)");
//		cell.setRowSpan(3);
		cellSet.setCell(2, 2, cell);
		cell = new Cell();
		cell.setContent("=ds.group(EndRow)");
//		cell.setRowSpan(2);
		cellSet.setCell(2, 3, cell);
		cell = new Cell();
		cell.setContent("=ds.select(EndColumn)");
		cellSet.setCell(2, 4, cell);
		
//		cell = new Cell();
//		cell.setContent("总计");
//		cellSet.setCell(5, 2, cell);
//		
//		cell = new Cell();
//		cell.setContent("合计");
//		cellSet.setCell(4, 3, cell);
//		
//		cell = new Cell();
//		cell.setContent("小计");
//		cellSet.setCell(3, 4, cell);
		return cellSet;
	}
	//横向扩展例子
	private CellSet getColList(){
		CellSet cellSet = new CellSet(10, 10);
		Cell cell = new Cell();
		cell.setContent("编号"); 
		cellSet.setCell(1, 1, cell);
		cell = new Cell();
		cell.setContent("姓名");
		cellSet.setCell(2, 1, cell);
		cell = new Cell();
		cell.setContent("学历");
		cellSet.setCell(3, 1, cell);
		
		cell = new Cell();
		cell.setContent("=ds.group(StartRow)"); 
		cell.setExtDirection("x");
		cellSet.setCell(1, 2, cell);
		cell = new Cell();
		cell.setContent("=ds.group(StartColumn)");
		cell.setExtDirection("x");
		cellSet.setCell(2, 2, cell);
		cell = new Cell();
		cell.setContent("=ds.group(EndRow)");
		cell.setExtDirection("x");
		cellSet.setCell(3, 2, cell);
		cell = new Cell();
		cell.setContent("=ds.group(EndColumn)");
		cell.setExtDirection("x");
		cellSet.setCell(4, 2, cell);
		return cellSet;
	}
	
	//交叉例子
	private CellSet getCrossList(){
		CellSet cellSet = new CellSet(10, 10);
		Cell cell = new Cell();
		cell.setContent("编号"); 
		cellSet.setCell(1, 1, cell);
		cell = new Cell();
		cell.setContent("姓名");
		cellSet.setCell(1, 2, cell);
		
		cell = new Cell();
		cell.setContent("=ds.group(StartRow)"); 
		cellSet.setCell(2, 1, cell);
		cell = new Cell();
		cell.setContent("=ds.group(StartColumn)");
		cellSet.setCell(2, 2, cell);
		cell = new Cell();
		cell.setContent("=ds.group(EndRow)");
		cell.setExtDirection("x");
		cellSet.setCell(1, 3, cell);
		cell = new Cell();
		cell.setContent("=ds.sum(EndColumn)");
		cellSet.setCell(2, 3, cell);
		
		return cellSet;
	}
	
	public Report test(String fileName) { 

		Report report = new Report();
		try {
			
			long pre = System.currentTimeMillis();

			// 测试
//			CellSet cellSet =getColList();
//			CellSet cellSet =getRowList();
			CellSet cellSet =getCrossList();
			
			report.setDefCS(cellSet);
			
			
			List list =new ArrayList();
			for(int i=0;i<100;i++){
//				Map map=new HashMap();
//				map.put("col1",""+i);
//				map.put("col2","姓名"+i);
//				map.put("col3","学历"+i);
//				for(int j=4;j<6;j++){
//					map.put("col"+j,"学历"+i);
//				}
//				list.add(map);
				
//				Map map=new HashMap();
//				map.put("startRow",""+i);
//				map.put("startColumn",""+(i+1));
//				map.put("endRow",""+(i+2));
//				map.put("endColumn",""+(i+3));
//				list.add(map);
				
				Field f = new Field();
				f.setStartRow(i/10);
				f.setStartColumn(i/5);
				f.setEndRow(i/2);
				f.setEndColumn(i);
				list.add(f);
			}
			
			// 取得报表路径
			
			long curr = System.currentTimeMillis();
			long time = (curr - pre) ;
//			System.out.println("生成List时间:" + time);
			pre = curr;
			
			CachedRowSet rs = new CachedRowSet();
			rs.populate(list,"col1#col2#col3","#");
			report.setDs("ds",list);
			curr = System.currentTimeMillis();
			time = (curr - pre) ;
//			System.out.println("生成rs时间:" + time);
			pre = curr;
//			while(rs.next()){
//				System.out.println(rs.getString("col1")+"  "+rs.getString("col2")+"  "+rs.getString("col3"));
//			}
//			while(rs.next()){
//				System.out.println(rs.getString("startRow")+"  "+rs.getString("startColumn")+"  "+rs.getString("endRow")+" "+rs.getString("endColumn"));
//			}
			
			curr = System.currentTimeMillis();
			time = (curr - pre) ;
//			System.out.println("print时间:" + time);
			pre = curr;
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}

//		Report reports[] = new Report[1];
//		reports[0] = report;
//		System.out.println(report.getNewCS());
		return report;

	}

	public static void main(String args[]) {

		ReportEngine e = new ReportEngine();
		System.out.println("aaaaaaaaa");
		Report report = e.test("");
		String str = e.run(report);
		System.out.println("html:\n"+str);
		// try {
		// Report report[] = e.read("d://report.xls");
		// // Report report[] = e.test("");
		// e.operation(report);
		// CellSet cs = report[0].getNewCS();
		// System.out.println(report[0].getOldCS());
		// System.out.println(cs);
		// e.print(report,null);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }

	}


}