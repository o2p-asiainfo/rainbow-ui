package com.linkage.rainbow.ui.report.engine.view;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.util.StringUtil;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Coordinate;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.model.PropertyDefine;
import com.linkage.rainbow.ui.report.engine.model.Report;

/**
 * 
 * HTML报表树形钻取时,ajax调用时输出的下级统计报表<br>
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
public class HtmlViewDrill extends HtmlView{
	
	private static Log log = LogFactory.getLog(HtmlViewDrill.class);
    /**
     * 根据报表请求类与报表参数构造
     * @param report  报表请求类
     * @param para 报表参数
     */
	public HtmlViewDrill(Report report[],Map para ) {
		super(report,para);
		
	}
	
	/**
	 * 输出报表
	 */
	@Override
	public void print(Writer writer) { 
		String html="";
		try {
			File dir = new File((String)para.get("basePath"));
			if(!dir.exists()){
				dir.mkdir();
			}
			String fileName = "report_drill_"+System.currentTimeMillis()+"_"+StringUtil.getRamCode(3);
			html=para.get("contextPath")+ "/doc/report/"+fileName+".html";
			fileName = para.get("basePath")+"/doc/report/"+fileName+".html";
			File file = new File(fileName);
			file.createNewFile();
			FileOutputStream fo = new FileOutputStream(file); 
			PrintWriter out = new PrintWriter(fo);
			printFile(out);
			fo.close();
			//html="<script language='javascript'>window.location.href='"+html+"';</script>";
			writer.write(html);
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		
		//return html;
	}
	/**
	 * 将报表转为html
	 * @param out
	 */
	public void printFile(PrintWriter out) {
		//PrintWriter out = new PrintWriter(writer);
		
		try {
			String contextPath = (String)para.get("contextPath");
			//生成页面时,报表的ID值,报表的相关HTML对象,以 报表ID 为前缀进行命名.如form名为: 报表ID_from,table ID值为:报表ID_tab 等.
			reportid =para.get("report").toString().replace('.','_');
			
			String parent_tr_id =(String)para.get("parent_tr_id");
			String drill_direction = (String)para.get("drill_direction");

			
			out.print("<table>");
			if (report != null && report.length>0) {
				CellSet csOld = report[0].getDefCS();
				CellSet cs = report[0].getNewCS();
				Coordinate drillCoordinate = new Coordinate(drill_direction);
				Cell oldCell =  csOld.getCell(drillCoordinate);
				int iStartRow = -1,iStartCol=-1;
				for (int i = drillCoordinate.getStartRow() ,t=0; i <= cs.getRow(); i++,t++) {
					boolean isBreak=false;
					for(int j=0;j<cs.getColumn();j++){
						Cell cell = cs.getCell(i,j);
						if(cell.getExtField().equals(drillCoordinate)){
							iStartRow = i;
							iStartCol = j;
							isBreak =true;
							break;
						}
					}
					if(isBreak)
						break;
				}
				for (int i =iStartRow ,t=0; i <= cs.getRow(); i++,t++) {
					boolean isBreak=false;
					StringBuffer trId = new StringBuffer().append(parent_tr_id).append("_").append(t);
					
					for(int j=iStartCol;j<cs.getColumn();j++){
						Cell cell = cs.getCell(i, j);
						
						if(j==iStartCol){
							Coordinate fCoordinate = null;//取得被归并的单元格坐标
							if(cell==null){
								fCoordinate = cs.getMergeCellCoor(i,j);
							}
							if((fCoordinate !=null&& cs.getCell(fCoordinate) != null && cs.getCell(fCoordinate).getExtField().equals(drillCoordinate)) ||(cell != null && cell.getExtField().equals(drillCoordinate))){
								out.print("<tr id='"+trId+"'>");
							}else{
								isBreak =true;
								break;
							}
						}
						
						if (!cs.isMerge(i, j)) {
							if (cell != null) {
								//单元格跨度设置
								StringBuffer span = new StringBuffer().append(" colspan='").append(cell.getColSpan()).append("' rowspan='").append(cell.getRowSpan()).append("'");

								//单元格内容
								String content = getDataFormat(cell);
								content = drill(cell,content,trId.toString());
								
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
								out.print("<td " + span + style + mouseEvent+"><![CDATA["+content + "]]></td>");
								
							} else {
								out.print("<td><![CDATA[&nbsp;]]></td>");
							}
						}
					}
					if(isBreak)
						break;
					out.println("</tr>");
				}
				
//				Field field = oldCell.getExtField();
//				for (int i = field.getStartRow() ,t=0; i <= field.getEndRow(); i++,t++) {
//					StringBuffer trId = new StringBuffer().append(parent_tr_id).append("_").append(t);
//					out.print("<tr id='"+trId+"'>");
//					for (int j = 0; j < cs.getColumn(); j++) {
//						Cell cell = cs.getCell(i, j);
//						if (!cs.isMerge(i, j)) {
//							if (cell != null) {
//								//单元格跨度设置
//								StringBuffer span = new StringBuffer().append(" colspan='").append(cell.getColSpan()).append("' rowspan='").append(cell.getRowSpan()).append("'");
//
//								//单元格内容
//								String content = getDataFormat(cell);
//								content = drill(cell,content,trId.toString());
//								
//								//单元样式设置
//								String style = cellStyle2HtmlStyle(cs, i, j);
//								if(cell.getIndention()>0){ //缩进样式
//									if(cell.getAlignment() == PropertyDefine.ALIGN_RIGHT){ //右缩进
//										content = content+getIndent(cell.getIndention());
//									} else { //左缩进
//										content = getIndent(cell.getIndention())+content;
//									}
//								}
//								
//								//输出一个单元格信息
//								out.print("<td " + span + style + "><![CDATA["+content + "]]></td>");
//							} else {
//								out.print("<td>&nbsp;</td>");
//							}
//						}
//					}
//					out.println("</tr>");
//				}
				
				
			}
			out.println( "</table>");

			out.flush();

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
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
	protected String drill(Cell cell,String content ,String trId){
		if(cell.getDrillType() != null && content != null ){
			String contextPath = (String)para.get("contextPath");

			if(cell.getDrillType().equals("2")||cell.getDrillType().equals("3") && cell.getDrillAvai()==true){
				content = "<a href=\"javascript:fr_drill_2('"+contextPath+"','"+reportid+"','"+cell.getDrillTerm()+"')\" >"+ content+"</a>";
			}
			if(cell.getDrillType().equals("1")||cell.getDrillType().equals("3")){
				String drill1 = "";
				if(cell.getDrillAvai()==true)
				    drill1 = "<img id=\""+trId+"_img\" state=\"close\" style=\"CURSOR: hand\" onClick=\"fr_drill_1('"+contextPath+"','"+reportid+"','format=drill&sheets_index="+para.get("sheets_index")+"&drill_direction="+cell.getExtField().toExcelCoor()+"&"+cell.getDrillTerm()+"','"+trId+"',this);\" src=\""+contextPath+"/struts/simple/report/resource/images/tree_close.gif\" >";
				else {
				    drill1 = "<img id=\""+trId+"_img\" src=\""+contextPath+"/struts/simple/report/resource/images/tree_leaf.gif\" >";
				}
				content = drill1 + content;
			}
		}
		return content;
	}

}
