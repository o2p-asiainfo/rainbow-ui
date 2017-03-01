package com.linkage.rainbow.ui.report.engine.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 固定报表servlet,用于处理前台参数,生成不同格式的报表返回. request 主要参数包括: report
 * 报表名称，相对于webapp中fixedreport的路径，子目录用 . 表示，不用加EXCEL的后缀名。 format 报表格式:htm、xls、pdf
 * 
 * @author cl
 * @deprecated
 */
public class FixedReportS extends HttpServlet {

	public FixedReportS() {

	}


	/**
	 * 固定报表展现页面,用于处理前台参数,生成不同格式的报表返回.
	 * 参数列表:
	 * report 报表名称.相对于webapp中fixedreport的路径，子目录用 . 表示，不用加EXCEL的后缀名
	 * format 报表输出格式.取值:html,xls
	 * union_sheets 合并各个工作表,放到一个table内.取值:true,false
	 * sheets_index 只列出指定的工作表,按0开始计算,可列出多个工作表,用,号分隔;例:sheets_index=0,1,3
	 * sheets_name 只列出指定的工作表,按名称指定,可列出多个工作表,用,号分隔;例:sheets_name=Sheet1,Sheet2,Sheet3
	 * ext_sytle 增加样式表文件,如果多个使用,号分隔.
	 * ext_script 增加脚本文件,如果多个使用,号分隔.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//设置页面不缓存
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		
		request.setCharacterEncoding("GBK"); 
		String format = request.getParameter("format");
		if(format != null && format.equals("xls")  ) {
			response.setContentType("application/x-msdownload; charset=gb2312");
			response.setHeader ("Content-Disposition", "attachment; filename=\"filename.xls\"");

		} else {
			response.setContentType("text/html;charset=GB2312");
		}

		String report = request.getParameter("report");

		if (report != null) {
			
			
			String path = request.getRealPath("");
			report = report.replace('.', '/');
			// 取得文件的绝对路径
			path = path + "/fixedreport/" + report + ".xls";
			path = path.replace('\\', '/');

			java.util.Map para = new java.util.HashMap(request.getParameterMap());
			// Map para = request.getParameterMap();
			para.remove("report");

			java.util.Iterator ite = para.keySet().iterator();

			while (ite.hasNext()) {
				String name = (String) ite.next();
				String value[] = (String[]) para.get(name);
				if (value != null && value.length == 1) {
					String newValue = value[0];
					para.put(name, newValue);
				}

				//System.out.println("key:" + name + "value:" + para.get(name));
			}

			para.put("report", path);
			para.put("format","htm");
			para.put("contextPath",request.getContextPath());
			
//			System.out.println("FixedReport para:"+para);
			 com.linkage.rainbow.ui.report.engine.core.ReportEngine.run(para, response.getOutputStream());
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);

	}

	public void destroy() {
	}
}
