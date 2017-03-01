package com.linkage.rainbow.ui.views.jsp.ui.report;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * reportServet<br>
 * <p> 
 * @version 1.0
 * @author 陈亮 2009-08-26
 *         <hr>
 *         修改记录  
 *         <hr>
 *         1、修改人员: 陈亮 修改时间:2009-08-26<br>
 *         修改内容:新建
 *         <hr>
 */

public class ReportServlet extends HttpServlet {
	
	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	
		//		设置页面不缓存
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		
		request.setCharacterEncoding("GBK"); 
		String doType = request.getParameter("doType");
		if(doType != null){
			if(doType.equals("xls")  ) {//excel下载
				response.setContentType("application/x-msdownload; charset=gb2312");
				response.setHeader ("Content-Disposition", "attachment; filename=\"filename.xls\"");
				String xlsName = request.getParameter("xlsName");
				String path= request.getRealPath("doc");
//				System.out.println(path);
			}else{
				
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
	

}
