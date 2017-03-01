package template.simple.page.simple;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkage.rainbow.util.MathUtil;
import com.linkage.rainbow.util.StringUtil;

/**
 * 类名称<br>
 * 导出excel文件
 * <p>
 * @version 1.0
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 修改时间:<br>
 * 修改内容:
 * <hr>
 */
public class SaveParameter extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * 将请求的参数保存到session
	 * @param request
	 * @param response
	 * @throws ServletException,IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//将请求参数封装到session

		HashMap map = ExcelExport.getRequestParaMap(request);
		//分页对象
		String pageobj = StringUtil.valueOf(request.getParameter("pageobj"));
		map.put("pageobj", StringUtil.valueOf(request.getParameter("pageobj")));
		
		//url参数
		String queryStringInfo = request.getParameter("queryStringInfo"+pageobj);
		map = ExcelExport.putintoMap(map, queryStringInfo);
		
		//方法
		String method = StringUtil.valueOf(request.getParameter("method"+pageobj));
		map.put("method", method);
		
		//excel文件名
		String excelTitle =StringUtil.valueOf(request.getParameter("excelTitle"+pageobj));
		if (excelTitle.equals("")) { 
			excelTitle = "excel";
		}
		map.put("excelTitle", excelTitle);
		
		//导出模式
		String exportMode = StringUtil.valueOf(request.getParameter("exportMode"+pageobj));				
		map.put("exportMode", exportMode);
		
		//起始记录数
		int startIndex = MathUtil.objToInt(request.getParameter("startIndex"+pageobj));
		map.put("startIndex", startIndex);
		
		//当前页
		int current = MathUtil.objToInt(request.getParameter("current"+pageobj));
		map.put("current", current);
		
		//每页记录数
		String pageRecord = request.getParameter("pageRecord"+pageobj);
		map.put("pageRecord", pageRecord);
		
		//导出excel的页头
		String title = StringUtil.valueOf(request.getParameter("title"+pageobj));
		map.put("title", title);
		
		//导出excel对应的数据库字段名
		String column = StringUtil.valueOf(request.getParameter("excelcolumn"+pageobj));
		map.put("column", column);
		
		int random = (int)(Math.random()*1000000000);
		request.getSession().setAttribute(String.valueOf(random), map);
		response.sendRedirect(request.getContextPath()+"/comm/servlet.shtml?servlet_name=excelexport&random="+random);
	}
}
