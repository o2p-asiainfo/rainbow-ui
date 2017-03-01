package template.simple.page.simple;

/**
 * 类名称<br>
 * 导出excel文件
 * <p>
 * @version 1.0
 * @author  Apr 27, 2009
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 修改时间:<br>
 * 修改内容:
 * <hr>
 */

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.util.StringUtil;;

public class ExportPage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ExportPage.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sysencoding=org.apache.struts2.config.DefaultSettings.get("struts.i18n.encoding");
		response.setCharacterEncoding(sysencoding);
		response.setContentType("application/x-msdownload;charset="+sysencoding);
		String pageobj = StringUtil.valueOf(request.getParameter("pageobj"));
		String hiddenPage = StringUtil.valueOf(request.getParameter("currentContent"+pageobj));
		String excelTitle=java.net.URLDecoder.decode(request.getParameter("excelTitle"+pageobj), "utf-8");
		if(excelTitle==null||excelTitle.equals("")){
			excelTitle = "excel";
		}
		response.setHeader("Content-Disposition", "attachment; filename="
				+ StringUtil.toUtf8String(excelTitle) + ".xls");
		
		ExportPage.exportText(response.getOutputStream(), hiddenPage , sysencoding);
	}
	
	//导出excel，同时对数字列进行处理
	public static void exportText (OutputStream os, String page, String code) {
		StringBuffer content = new StringBuffer();
		//ie/ff获取的innerHTML不同 因此先进行替换
		page = page.replace("<thead", "<THEAD").replace("</thead>", "</THEAD>").replace("<tr", "<TR")
				.replace("</tr>", "</TR>").replace("<tbody", "<TBODY>").replace("</tbody>", "</TBODY>")
				.replace("<td", "<TD").replace("</td>", "</TD>").replace("<th", "<TH").replace("</th>", "</TH>");
		
		//替换换行字符
		page = page.replaceAll("\\n","").replaceAll("\\r","");
		//替换脚本
		page = page.replaceAll("<[S,s][C,c][R,r][I,i][P,p][T,t]>.*?</[S,s][C,c][R,r][I,i][P,p][T,t]>","");
		
		//分2步处理 表头跟表体的处理
		String head = ""; //标题信息
		String body = ""; //内容信息
		int collength = 0;//列数
		head = StringUtil.valueOf(page.split("</THEAD>")[0]).trim();
		body = StringUtil.valueOf(page.split("</THEAD>")[1]).trim();
		collength = head.replace("</TR>", "").trim().split("</TH>").length;
		
		ArrayList as = new ArrayList();//要对字段进行处理的列
		ArrayList al = new ArrayList();//不要导出的字段
		String[] headInfo = head.replace("</TR>", "").trim().split("</TH>");
		for(int i = 0;i<headInfo.length;i++){
			if(headInfo[i].indexOf("费")>= 0||headInfo[i].indexOf("(分)")>=0||headInfo[i].indexOf("(元)")>=0||
					headInfo[i].indexOf("数")>=0||headInfo[i].indexOf("总")>=0||headInfo[i].indexOf("合计")>=0||
					headInfo[i].indexOf("金")>=0||headInfo[i].indexOf("收")>=0){
				as.add(i);
			}
			if(headInfo[i].toLowerCase().indexOf("type=\"checkbox\"")!=-1||headInfo[i].toLowerCase().indexOf("选择")!=-1
					||headInfo[i].toLowerCase().indexOf("type=\"radio\"")!=-1
					||headInfo[i].toLowerCase().indexOf("type=checkbox")!=-1
					||headInfo[i].toLowerCase().indexOf("type=radio")!=-1){
				al.add(i);
			}
		}
		
		
		//有结果的时候进行处理
		if(body.indexOf("查无记录")==-1){
			StringBuffer tm = new StringBuffer();
			boolean mark = false;//是否是第一列,第一列要特别处理<tr>
			boolean tag = false;
			//是否有不导出的列进行处理
			if(al.size()==0){
				tm.append(head+"</THEAD>");
			}else{
				for(int i = 0;i<collength;i++){
					tag = false;
					for(int j = 0;j<al.size();j++){
						if(al.get(j).equals(i)){
							tag = true;
						}
					}
					//是第一列有选择按钮
					if(tag&&i==0){
						tm.append("<THEAD><TR>");
						continue;
					}else if(tag&&i!=0){
						continue;
					}else if(tag==false){
						tm.append(head.replace("</TR>", "").trim().split("</TH>")[i]+"</TH>");
					}
				}
				tm.append("</THEAD>");
			}
			boolean flag = false;
			boolean exp = true;
			for(int i = 0;i<body.split("</TD>").length-1;i++){
				flag = false;
				exp = true;
				for(int j=0;j<as.size();j++){
					if(as.get(j).equals(i%collength)){
						flag = true;
					}
				}
				for(int k=0;k<al.size();k++){
					if(al.get(k).equals(i%collength)||(body.split("</TD>")[i].toLowerCase().indexOf("type=\"checkbox\"")!=-1
							||body.split("</TD>")[i].toLowerCase().indexOf("type=\"radio\"")!=-1
							||body.split("</TD>")[i].toLowerCase().indexOf("type=radio")!=-1
							||body.split("</TD>")[i].toLowerCase().indexOf("type=checkbox")!=-1)){
						exp = false;
					}
				}
				if(!exp==true&&i==0){
					tm.append("<TBODY><TR>");
				}else if(!exp==true&&i!=0){
					continue;
				}else if(exp){
					if(flag){
						tm.append(body.split("</TD>")[i]+"</TD>");
					}else{						
						tm.append(body.split("</TD>")[i]+""+"</TD>");
					}
				}
				if((i+1)%collength==0){
					tm.append("</TR>");
				}
			}
			tm.append(body.split("</TD>")[body.split("</TD>").length-1]);
			content.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+code+"\"></head><body>"+
					"<table border=1>"+ tm +"</table>");
		}else{
			content.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+code+"\"></head><body>"+
			"<table border=1>"+ page +"</table>");
			content.append("</body></html>");
		}
		try{
			os.write(content.toString().getBytes());
			os.close();
		}catch(Exception e){
			log.error(e.getStackTrace());
		}
	}
}
