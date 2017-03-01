package com.linkage.rainbow.ui.components.report;



import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.report.engine.core.ReportEngine;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * grid<br>
 * 数据表格显示组件,此组件与 GridHeader、GridHeaderCell、GridRow、GridCol组合使用。
 * <p>
 * @version 1.0
 * @author 陈亮 2009-04-8
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-04-8<br>
 *         修改内容:新建
 *         <hr>
 */
@StrutsTag(name = "grid", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.grid.GridTag", description = "Render an HTML Table")
public class Table extends ClosingUIBean {
	/**
	 * 定义表格组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "report/table";
	/**
	 * 定义表格组件的结束模板 
	 */
	public static final String TEMPLATE = "report/table-close";
	
	private static Log log = LogFactory.getLog(Table.class);
	String templateShort ;
	//皮肤
	private String skin;
	

	/**数据集 */
	protected String ds ;
	
	/**宽度 */
	protected String width;
	
	/**高度 */
	protected String height;
	
	/** 表格的HTML的样式属性 */
	protected String style ;
	
	/** 表格的HTML的样式表 */
	protected String styleClass;
	/** 表格的单元格边距 */
	protected String cellpadding;
	/** 表格的单元格间距 */
	protected String cellspacing;
	/** 表格的过框明细 */
	protected String border;
	/** 表格的过框颜色 */
	protected String bordercolor;
	/** 表格的对齐方式 */
	protected String align ;
	/** 表格的背影图 */
	protected String background ;
	/** 表格的背影色 */
	protected String bgcolor ;
	
	/**是否超过高度宽带时冻结表头**/
	protected String scrolling;
	
	protected Report report = new Report();
	//对应的扩展报表
	protected CellSet defCS = new CellSet(100,100);

	protected String exportBuffXls ;//对于一个页面有多个表格导出时,除最后一个外,需设置buffXls为true.
	protected String exportMultiTableInSheet;//对于一个页面多个表格导出时,各个表格分别存放在excel中的Sheet中
	protected String exportXlsSheetName;//excel中Sheet的名称
	
	protected String noRecordMsg; //是否查无记录
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Table
	 */
	public Table(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	/**
	 * 将组件类的信息设置到模板
	 * @param writer
	 * @return boolean
	 */
	@Override
    public boolean start(Writer writer) {
    	//evaluateParams();
		evaluateExtraParams();
    	return true;
    }
    
	
	/**
	 * 形成报表请求类,调用报表引擎,生成报表输出
	 * @param writer
	 * @param body
	 * @return boolean
	 */
	@Override
    public boolean end(Writer writer, String body) {
		
		//设置报表定义类
		report.setDefCS(defCS);
		//设置报表参数
		Map para = getRequestParaMap(request);
		para.put("contextPath",request.getContextPath());
		para.put("requestURL",request.getRequestURL());
		para.put("request",request);
		if(exportBuffXls != null)
			para.put("exportBuffXls",findString(exportBuffXls));
		if(exportMultiTableInSheet != null)
			para.put("exportMultiTableInSheet",findString(exportMultiTableInSheet));
		if(scrolling != null)
			para.put("scrolling",findString(scrolling)); //是否冻结表头
		if(exportXlsSheetName !=null )
			para.put("exportXlsSheetName",findString(exportXlsSheetName)); //excel中Sheet的名称
		
		if(noRecordMsg !=null )
			para.put("noRecordMsg",findString(noRecordMsg)); //查无记录时,显示信息
		
		report.setPara(para);
		//调用报表引擎,生成HTML. 
		ReportEngine engine = new ReportEngine();
		//if (format != null) {
		String format = (String)para.get("format");
		try {
			
			if (format != null&&format.equals("xls")) {
				para.put("basePath",request.getRealPath(""));//文档存放路径
				String xls = engine.run(report);
//				System.out.println("xls :"+xls);
				//writer.write(xls);
				if(exportBuffXls != null&& findString(exportBuffXls) !=null && findString(exportBuffXls).toString().equalsIgnoreCase("true")){
				}else{
//					System.out.println("xls url:"+xls);
					response.sendRedirect(xls);
					//xls ="<script language='javascript'>document.body.display='none'; self.location='"+xls+"';</script>";
					//writer.write(xls);
				}
				//http://localhost:800/rp/doc/report/report_1251421631875.xls
			}else if (format != null&&format.equals("drill")) {
				para.put("basePath",request.getRealPath(""));//文档存放路径
				String xml = engine.run(report);
				//writer.write(xls);
//				System.out.println("xml:"+xml);
				response.sendRedirect(xml);
				//http://localhost:800/rp/doc/report/report_1251421631875.xls
			}else if(format !=null&& format.equals("pdf")){
				para.put("basePath",request.getRealPath(""));//文档存放路径
				String pdf = engine.run(report);
//				System.out.println("pdf:"+pdf);
				response.sendRedirect(pdf);				
			}else if(format !=null&& format.equals("doc")){
				para.put("basePath",request.getRealPath(""));//文档存放路径
				String doc = engine.run(report);
//				System.out.println("doc:"+doc);
				response.sendRedirect(doc);					
			}else{
				String html = engine.run(report);
				writer.write(html);
			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		
		
        return false;
    }
    
	/**
	 * 将request 中的参数转换为Map
	 * @param request
	 * @return Map
	 */
	private Map getRequestParaMap(HttpServletRequest request){
 		java.util.Map para = new java.util.HashMap(request.getParameterMap());
		
		java.util.Iterator ite = para.keySet().iterator();

		while (ite.hasNext()) {
			String name = (String) ite.next();
			Object obj = para.get(name);
			if(obj instanceof String[]) {
				String value[] = (String[]) obj;
				if (value != null && value.length == 1) {
					String newValue = value[0];
					para.put(name, newValue);
				}
			}
			//System.out.println("key:" + name + "value:" + para.get(name));
		}
		return para;
 	}

	/**
	 * 将标签类的属性设置到组件类
	 */
	protected void evaluateExtraParams() {
		
		if(id != null)
			report.setReportId(findString(id));
		
		if(ds != null){
			//取得数据集,多个数据集采用,号分隔.
			String dss[] = ds.split(",");
			for(int i=0;i<dss.length;i++){
				int asindex = dss[i].indexOf(" as ");
				if(asindex>0 && asindex<dss[i].length()){
					List list = (List)findValue(dss[i].substring(0,asindex).trim(),List.class);
					report.setDs(dss[i].substring(asindex+4).trim(),list);
				}else {
					if(asindex>0)
						dss[i] = dss[i].substring(0,asindex);
					List list =(List)findValue(dss[i].trim(),List.class);
					report.setDs(dss[i].trim(),list);
				}
			}
		}
		if(width != null)
			defCS.setWidth(findString(width));
		if(height != null)
			defCS.setHeight(findString(height));
		if(style != null)
			defCS.setStyle(findString(style));
		if(styleClass != null)
			defCS.setStyleClass(findString(styleClass));
		if(cellpadding != null)
			defCS.setCellpadding((Integer)findValue(cellpadding,Integer.class));
		if(cellspacing != null)
			defCS.setCellspacing((Integer)findValue(cellspacing,Integer.class));
		if(border != null)
			defCS.setBorder(findString(border));
		if(bordercolor != null)
			defCS.setBordercolor(findString(bordercolor));
		if(align != null)
			defCS.setAlign(findString(align));
		if(background != null)
			defCS.setBackground(findString(background));
		if(bgcolor != null)
			defCS.setBgcolor(findString(bgcolor));
		
	}

	/**
	 * 获取皮肤
	 * @return String
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置皮肤
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 获取水平对齐方式
	 * @return String
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * 设置水平对齐方式
	 * @param align
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * 获取背景
	 * @return String
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置背景
	 * @param background
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * 获取背景色
	 * @return String
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	/**
	 * 设置背景色
	 * @param bgcolor
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	/**
	 * 获取边框
	 * @return String
	 */
	public String getBorder() {
		return border;
	}

	/**
	 * 设置边框
	 * @param border
	 */
	public void setBorder(String border) {
		this.border = border;
	}

	/**
	 * 获取边框颜色
	 * @return String
	 */
	public String getBordercolor() {
		return bordercolor;
	}

	/**
	 * 设置边框颜色
	 * @param bordercolor
	 */
	public void setBordercolor(String bordercolor) {
		this.bordercolor = bordercolor;
	}


	/**
	 * 获取表格高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置表格高度
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取表格样式
	 * @return String
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置表格样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取表格样式表
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置表格样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 该属性暂时没有用到 
	 * @deprecated
	 * @return String
	 */
	public String getTemplateShort() {
		return templateShort;
	}

	/**
	 * 该属性暂时没有用到 
	 * @deprecated
	 * @param templateShort
	 */
	public void setTemplateShort(String templateShort) {
		this.templateShort = templateShort;
	}

	/**
	 * 获取表格宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置表格宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 获取单元格边距
	 * @return String
	 */
	public String getCellpadding() {
		return cellpadding;
	}

	/**
	 * 设置单元格边距
	 * @param cellpadding
	 */
	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	/**
	 * 获取单元格间距
	 * @return String
	 */
	public String getCellspacing() {
		return cellspacing;
	}

	/**
	 * 设置单元格间距
	 * @param cellspacing
	 */
	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}
	
	/**
	 * 获取对应的扩展报表
	 * @return CellSet
	 */
	public CellSet getDefCS() {
		return defCS;
	}

	/**
	 * 设置对应的扩展报表
	 * @param defCS
	 */
	public void setDefCS(CellSet defCS) {
		this.defCS = defCS;
	}

	/**
	 * 获取数据集
	 * @return String
	 */
	public String getDs() {
		return ds;
	}

	/**
	 * 设置数据集
	 * @param ds
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}

	/**
	 * 获取是否超过高度宽带时冻结表头
	 * @return String
	 */
	public String getScrolling() {
		return scrolling;
	}

	/**
	 * 设置是否超过高度宽带时冻结表头
	 * @param scrolling
	 */
	public void setScrolling(String scrolling) {
		this.scrolling = scrolling;
	}

	/**
	 * 获取对于一个页面有多个表格导出时,除最后一个外,需设置buffXls为true
	 * @return String
	 */
	public String getExportBuffXls() {
		return exportBuffXls;
	}

	/**
	 * 设置对于一个页面有多个表格导出时,除最后一个外,需设置buffXls为true
	 * @param exportBuffXls
	 */
	public void setExportBuffXls(String exportBuffXls) {
		this.exportBuffXls = exportBuffXls;
	}

	/**
	 * 获取对于一个页面多个表格导出时,各个表格分别存放在excel中的Sheet中
	 * @return String
	 */
	public String getExportMultiTableInSheet() {
		return exportMultiTableInSheet;
	}

	/**
	 * 设置对于一个页面多个表格导出时,各个表格分别存放在excel中的Sheet中
	 * @param exportMultiTableInSheet
	 */
	public void setExportMultiTableInSheet(String exportMultiTableInSheet) {
		this.exportMultiTableInSheet = exportMultiTableInSheet;
	}

	/**
	 * 获取excel中Sheet的名称
	 * @return String
	 */
	public String getExportXlsSheetName() {
		return exportXlsSheetName;
	}

	/**
	 * 设置excel中Sheet的名称
	 * @param exportXlsSheetName
	 */
	public void setExportXlsSheetName(String exportXlsSheetName) {
		this.exportXlsSheetName = exportXlsSheetName;
	}


	/**
	 * 获取无记录的提示信息
	 * @return String
	 */
	public String getNoRecordMsg() {
		return noRecordMsg;
	}

	/**
	 * 设置无记录提示信息
	 * @param noRecordMsg
	 */
	public void setNoRecordMsg(String noRecordMsg) {
		this.noRecordMsg = noRecordMsg;
	}



}
