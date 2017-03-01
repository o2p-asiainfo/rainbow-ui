package template.simple.page.simple;



import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.paginaction.Pagination;
import com.linkage.rainbow.util.MathUtil;
import com.linkage.rainbow.util.StringUtil;
import com.linkage.rainbow.util.context.ContextUtil;

/**
 * �����<br>
 * ����excel�ļ�
 * <p>
 * @version 1.0
 * <hr>
 * �޸ļ�¼
 * <hr>
 * 1���޸���Ա: ���� �޸�ʱ��:2009-1-28<br>
 * �޸�����: �ı�����ʱ�����ӽ�"�ţ��滻Ϊ�����Խ����ʱ"�������λ�����⡣.replaceAll("\"", "��")
 * 
 * <hr>
 */
public class ExcelExport extends HttpServlet {
	private static Log log = LogFactory.getLog(ExcelExport.class);
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * ִ�е�������
	 * @param request
	 * @param response
	 * @throws ServletException,IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sysencoding=org.apache.struts2.config.DefaultSettings.get("struts.i18n.encoding");
		String random = request.getParameter("random");
		if(request.getSession().getAttribute(random)==null){
			return;
		}
		HashMap mps = (HashMap)request.getSession().getAttribute(random);
		
		String pageobj = StringUtil.valueOf(mps.get("pageobj"));
		String method = StringUtil.valueOf(mps.get("method"));
		String excelTitle =StringUtil.valueOf(mps.get("excelTitle"));
		String exportMode = StringUtil.valueOf(mps.get("exportMode"));
		int startIndex = MathUtil.objToInt(mps.get("startIndex"));
		String pageRecord = StringUtil.valueOf(mps.get("pageRecord"));
		int current = MathUtil.objToInt(mps.get("current"));
//		response.setContentType("application/x-msdownload;charset=utf-8");
		response.setCharacterEncoding(sysencoding);
//		String fileName = URLEncoder.encode(excelTitle+".xls", "UTF-8");
//		if (fileName.length() > 150) {
//            String guessCharset = request.getLocale().toString(); /*���request��locale �ó����ܵı��룬���Ĳ���ϵͳͨ����gb2312*/
//            fileName = new String(fileName.getBytes(guessCharset), "ISO8859-1"); 
//        }
//
//		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		//String title = new String(StringUtil.valueOf(request.getParameter("title")).getBytes("ISO8859-1"),sysencoding);
		response.setContentType("application/x-download");//����Ϊ���� 
		String filenamedisplay = new String((excelTitle+".xls").getBytes(),"iso8859-1"); 
		response.setHeader("Content-disposition","attachment; filename=" + filenamedisplay); 

		String title = StringUtil.valueOf(mps.get("title"));
		String column = StringUtil.valueOf(mps.get("column"));
		//�̳�spring��ȡlist
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		List list = ExcelExport.getList(mps, method, ctx, startIndex,pageRecord,current);
		
		/**ȡ�ô����̱߳��������*/
		List lr = (List)ContextUtil.get("executeSqlList");
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		
		
		//��������List��list����,���û��,��SQL���ֱ�Ӵ���ݿ�ȡ�����.
		if(list !=null && list.size()>0){
			if(exportMode.equals("jxl")){
				ExcelExport
					.exportExcel(response.getOutputStream(), list, title, column);
			}else{
				ExcelExport
				.exportText(response.getOutputStream(), list, title, column);
			}
		}else{
			ExcelExport.exportResult(response.getOutputStream(), lr,ds, title, column);
		}
		return;
		//���session
		//request.getSession().removeAttribute(random);
	}
	
	/**
	 * ��resultset��ʽ���excel
	 * @param os
	 * @param lr
	 * @param con
	 * @param title
	 * @param column
	 * @throws IOException
	 */
	public static void exportResult(OutputStream os,List lr,DataSource ds, String title,
			String column) throws IOException {
		String[] sheettitle = title.split(";title;");
		String[] sheetcolumn = column.split(";column;");
		String titles="";
		List ls = new ArrayList();
		//��ɱ���
		for (int i = 0; i < sheettitle.length; i++) {
			if (StringUtil.valueOf(sheettitle[i]).toString().indexOf(
			"��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("(��)") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("(Ԫ)") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("�ϼ�") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0) {
				ls.add(i);
			}			
			titles+=sheettitle[i].replaceAll("\\n", " ").replaceAll("\\t", " ")+"\t";
		}
		titles+="\n";
		os.write(titles.getBytes());
		Connection con = null;
		ResultSet rs = null;
		try{
			//con=DataSourceUtils.getConnection(ds);
			con=ds.getConnection();
			CommonDAO dao = new CommonDAO(con);
			if(lr!=null){
				try{
					for(int i=0;i<lr.size();i++){
						if(lr.get(i).toString().trim().toLowerCase().indexOf("update")!=-1||lr.get(i).toString().toLowerCase().indexOf("delete")!=-1){
							dao.exeModifySQL(lr.get(i).toString().trim());
						}else if(lr.get(i).toString().trim().toLowerCase().indexOf("insert")!=-1){
							dao.exeModifySQL(lr.get(i).toString().trim());
						}
	//					else if(lr.get(i).toString().trim().toLowerCase().startsWith("select")){
	//						rs = dao.getResultSet(lr.get(i).toString().trim());
	//					}
						else if((i==lr.size()-1)&&lr.get(i).toString().trim().toLowerCase().startsWith("select")){
							rs = dao.getResultSet(lr.get(i).toString().trim());
						}
					}
					con.commit();
				}catch(Exception ex){
					log.error(ex.getStackTrace());
//					try {
//						con.rollback();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
			int coloumlength = sheetcolumn.length;
			StringBuffer sb = new StringBuffer();
			if(rs!=null){
				while(rs.next()){
					sb.setLength(0);
					for(int j=0;j<coloumlength;j++){
						try{
							if(ls.contains(j)){
								sb.append(StringUtil.valueOf(rs.getString(sheetcolumn[j])).replaceAll("\\n", " ").replaceAll("\\r", " ").replaceAll("\\t", " ").replaceAll("\"", "��")+"\t");
							}else{
								sb.append(StringUtil.valueOf(rs.getString(sheetcolumn[j])).replaceAll("\\n", " ").replaceAll("\\r", " ").replaceAll("\\t", " ").replaceAll("\"", "��")+(char)127+"\t");
							}
						}catch(Exception e){
//							System.out.println("COL-NAME:" + sheetcolumn[j]+" "+e.toString());
							log.error(e.getStackTrace());
						}
					}
					sb.append("\n");
					os.write(sb.toString().getBytes());
				}	
			}
		} catch (SQLException e) {
			log.error(e.getStackTrace());
		}finally{
			try {
				if(rs !=null){rs.close();};
			} catch (SQLException e) {
			}
			try {
				if(con !=null){con.close();};
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * ���ı���ʽ��ɵ�excel
	 * @param os
	 * @param list
	 * @param title
	 * @param column
	 * @throws IOException
	 */
	public static void exportText(OutputStream os, List list, String title,
			String column) throws IOException {
		//Date date1 = new Date();
		//PrintStream p =new PrintStream(os);
		//Date date1 = new Date();
		String[] sheettitle = title.split(";title;");
		String[] sheetcolumn = column.split(";column;");
		String titles="";
		List ls = new ArrayList();
		//��ɱ���
		for (int i = 0; i < sheettitle.length; i++) {
			if (StringUtil.valueOf(sheettitle[i]).toString().indexOf(
			"��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("(��)") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("(Ԫ)") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("�ϼ�") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0
			|| StringUtil.valueOf(sheettitle[i]).toString()
					.indexOf("��") >= 0) {
				ls.add(i);
			}			
			titles+=sheettitle[i].replaceAll("\\n", " ").replaceAll("\\t", " ").replaceAll("\\r", " ").replaceAll("\"", "��")+"\t";
		}
		titles+="\n";
		os.write(titles.getBytes());
		//p.println();
		StringBuffer sb = new StringBuffer();
		
//		String str ="";
		//�������
//		Iterator it = list.iterator();
		//String content="";
		int coloumlength = sheetcolumn.length;
		for(int i=0;i<list.size();i++){
//			str="";
			sb.setLength(0);
			HashMap crdto = (HashMap) list.get(i);
			for(int j=0;j<coloumlength;j++){
				try{
					if(ls.contains(j)){
						sb.append(StringUtil.valueOf(crdto.get(sheetcolumn[j].toUpperCase())).replaceAll("\\n", " ").replaceAll("\\t", " ").replaceAll("\\r", " ").replaceAll("\"", "��")+"\t");
						//str+=StringUtil.valueOf(crdto.get(sheetcolumn[j].toUpperCase())).replaceAll("\\n", " ").replaceAll("\\t", " ")+"\t";
					}else{
						sb.append(StringUtil.valueOf(crdto.get(sheetcolumn[j].toUpperCase())).replaceAll("\\n", " ").replaceAll("\\t", " ").replaceAll("\\r", " ").replaceAll("\"", "��")+(char)127+"\t");
						//str+=StringUtil.valueOf(crdto.get(sheetcolumn[j].toUpperCase())).replaceAll("\\n", " ").replaceAll("\\t", " ")+(char)127+"\t";
					}
				}catch(Exception e){}
			}
			sb.append("\n");
//			str+="\n";
//			os.write(str.getBytes());
			os.write(sb.toString().getBytes());
			if(i%100==0){
				os.flush();
			}
			//os.write(result.getBytes());
		}
		
		try {
			//Date date2 = new Date();
			//System.err.println("export: " + (date2.getTime() - date1.getTime())+ " total milliseconds"); 
			//p.print(content);
			//String result = titles+"\n"+sb.toString();
			//os.write(result.getBytes());
			//p.close();
			//Date date2 = new Date();
			//Long t = date2.getTime() - date1.getTime();
			os.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getStackTrace());
		}finally{
			try{
				//p.close();
				os.close();
			}catch(Exception ex){
				
			}
		}
	}
	
	/**
	 * ��jxl��ʽ��ɵ�excel
	 * @param os
	 * @param list
	 * @param title
	 * @param column
	 */
	public static void exportExcel(OutputStream os, List list, String title,
			String column) {

		try {

			WritableWorkbook wbook = Workbook.createWorkbook(os); //����excel�ļ�

			WritableSheet wsheet = wbook.createSheet("Sheet1", 0); //���������

			//����Excel����

			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,

			WritableFont.BOLD, false,

			jxl.format.UnderlineStyle.NO_UNDERLINE,

			jxl.format.Colour.BLACK);

			WritableCellFormat titleFormat = new WritableCellFormat(wfont);

			String[] sheettitle = title.split(";title;");
			String[] sheetcolumn = column.split(";column;");

			//����Excel��ͷ

			for (int i = 0; i < sheettitle.length; i++) {

				Label excelTitle = new Label(i, 0, sheettitle[i], titleFormat);

				wsheet.addCell(excelTitle);

			}

			int c = 1; //����ѭ��ʱExcel���к�

			Iterator it = list.iterator();

			while (it.hasNext()) {

				HashMap crdto = (HashMap) it.next();

				for (int i = 0; i < sheetcolumn.length; i++) {
					try{
						//��������"��   ��  ��  �ϼ�  �� �� (Ԫ) (��) "�⼸������ʱ�������԰ѵ�Ԫ��ת��Ϊ��ֵ��
						if (StringUtil.valueOf(sheettitle[i]).toString().indexOf(
								"��") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("(��)") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("(Ԫ)") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("��") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("��") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("�ϼ�") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("��") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
										.indexOf("��") >= 0
								|| StringUtil.valueOf(sheettitle[i]).toString()
								.indexOf("��") >= 0) {
							if (StringUtil.valueOf(crdto.get(sheetcolumn[i]
									.toUpperCase())) != null
									&& StringUtil
											.valueOf(
													crdto.get(sheetcolumn[i]
															.toUpperCase()))
											.length() <= 15) {
								try {
									double tmp_number = Double
											.parseDouble(StringUtil.valueOf(crdto
													.get(sheetcolumn[i]
															.toUpperCase())));
									jxl.write.Number number = new jxl.write.Number(
											i, c, tmp_number);
									wsheet.addCell(number);
								} catch (Exception e) {
									jxl.write.Label lab = new jxl.write.Label(i, c,
											StringUtil.valueOf(crdto
													.get(sheetcolumn[i]
															.toUpperCase())));
									wsheet.addCell(lab);
								}
							}
						} else {
							wsheet.addCell(new Label(i, c, StringUtil.valueOf(crdto
									.get(sheetcolumn[i].toUpperCase()))));
						}
					}catch(Exception d){}
					
				}

				c++;

			}

			wbook.write(); //д���ļ�

			wbook.close();

			os.close();

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}

	}

	/**
	 * ����������װ��hashmap
	 * @param request
	 * @return HashMap
	 * @throws UnsupportedEncodingException
	 */
	public static HashMap getRequestParaMap(HttpServletRequest request)
			throws UnsupportedEncodingException {
		java.util.HashMap para = new java.util.HashMap(request
				.getParameterMap());
		java.util.Iterator ite = para.keySet().iterator();
		java.util.HashMap result = new java.util.HashMap();
		while (ite.hasNext()) {
			String name = (String) ite.next();
			if(name.equals("queryStringInfo")){
				continue;
			}
			Object obj = para.get(name);
			if (obj instanceof String[]) {
				if(name.trim().equals("")==false){
					name = name.indexOf(".")==-1?name:(name.split("\\.")[1]);
				}
				String value[] = (String[]) obj;
				if (value != null) {
//					String newValue = java.net.URLDecoder.decode(value[0],
//							"utf-8");
					String newValue = value[0];
					//System.out.println(newValue);
					result.put(name, newValue);
				}
			}
			//System.out.println("key:" + name + " value:" + para.get(name));
		}
		return result;
	}

	/**
	 * ��̬����spring��bean��ȡList
	 * @param map
	 * @param methods
	 * @param ctx
	 * @param Index
	 * @param pageRecord
	 * @param currentPage
	 * @return List
	 */
	public static List getList(HashMap map, String methods,
			WebApplicationContext ctx, int Index,String pageRecord, int currentPage) {
		map.put("isExportExcelSqlToThread", true);
		int record = 0;
		if(pageRecord==null||pageRecord.equals("")){
			record=65534;
		}else{
			record = MathUtil.objToInt(pageRecord);
		}
		Object retobj = null;
		try {
			//��̬���ò���Ϊ(HashMap , int , int)�ķ���
//			Class cls = Class.forName(ctx.getBean(methods.split("\\.")[0])
//					.getClass().getName());
			Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();
			Class partypes[] = new Class[3];
			partypes[0] = map.getClass();
			partypes[1] = Integer.TYPE;
			partypes[2] = Integer.TYPE;
			Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
			Object arglist[] = new Object[3];
			arglist[0] = map;
			arglist[1] = new Integer(Index); //�ò���ָ����������ʼҳ��
			arglist[2] = new Integer(record); //�ò���ָ��ÿҳ������¼��
			Object beanObj = ctx.getBean(methods.split("\\.")[0]);
			retobj = meth.invoke(beanObj, arglist);
		} catch (Exception ex) {
			try{
				//��̬���ò���Ϊ(HashMap , Pagination)�ķ���
				Pagination pg = new Pagination();
				if(currentPage!=0){
					pg.setCurrentPage(currentPage);
				}else{
					pg.setCurrentPage(Index/record+1);
				}
				pg.setPageRecord(record);
				pg.setPageStart(Index);
				Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();//Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
				Class partypes[] = new Class[2];
				partypes[0] = map.getClass();
				partypes[1] = pg.getClass();
				Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
				Object arglist[] = new Object[2];
				arglist[0] = map;
				arglist[1] = pg; 
				Object beanObj = ctx.getBean(methods.split("\\.")[0]);
				retobj = meth.invoke(beanObj, arglist);
			}catch(Exception e){
				try{
					//��̬���ò���Ϊ(Map , Pagination)�ķ���
					Pagination pg = new Pagination();
					if(currentPage!=0){
						pg.setCurrentPage(currentPage);
					}else{
						pg.setCurrentPage(Index/record+1);
					}
					pg.setPageRecord(record);
					pg.setPageStart(Index);
					Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();//Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
					Class partypes[] = new Class[2];
					partypes[0] = Map.class;
					partypes[1] = pg.getClass();
					Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
					Object arglist[] = new Object[2];
					arglist[0] = map;
					arglist[1] = pg; 
					//System.out.println(methods.split("\\.")[0]);
					Object beanObj = ctx.getBean(methods.split("\\.")[0]);
					retobj = meth.invoke(beanObj, arglist);
				}catch(Exception x){
					try{
						//��̬���ò���Ϊ(Map , int , int)�ķ���
						Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();//Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
						Class partypes[] = new Class[3];
						
						partypes[0] = Map.class;
						partypes[1] = Integer.TYPE;
						partypes[2] = Integer.TYPE;
						Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
						Object arglist[] = new Object[3];
						arglist[0] = map;
						arglist[1] = new Integer(Index); //�ò���ָ����������ʼҳ��
						arglist[2] = new Integer(record); //�ò���ָ��ÿҳ������¼��
						Object beanObj = ctx.getBean(methods.split("\\.")[0]);
						retobj = meth.invoke(beanObj, arglist);
					}catch(Exception p){
						try{
//							��̬���ò���Ϊ(Map)�ķ���,Map�����Ӳ���
							map.put("pageStart",Index);//�ò���ָ����������ʼҳ��
							map.put("pageRecord",record);//�ò���ָ��ÿҳ������¼��
							map.put("pageEnd",Index+record);
							
							
							Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();//Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
							Class partypes[] = new Class[1];
							
							partypes[0] = Map.class;
							Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
							Object arglist[] = new Object[1];
							arglist[0] = map;
							Object beanObj = ctx.getBean(methods.split("\\.")[0]);
							retobj = meth.invoke(beanObj, arglist);
						}catch(Exception pp){
//							System.out.println("��̬�������쳣:"+pp.getMessage());
							log.error(e.getStackTrace());
						}
					}
				}
				
			}
		}
		return (List) retobj;
	}
	
	/**
	 * ���Դ�Ĳ�����뵽excel��Map
	 * @param map
	 * @param queryInfo
	 * @return HashMap
	 */
	public static HashMap putintoMap(HashMap map , String queryInfo){
		if(queryInfo!=null && queryInfo.trim().equals("")==false){
			String []temps = queryInfo.split("&");
			for(int i=0;i<temps.length;i++){
				String str[]=temps[i].split("=");
				if(str.length>1){
					String name = str[0].indexOf(".")==-1?str[0]:(str[0].split("\\.")[1]);
					if(map.containsKey(str[0])){
						map.put(name, map.get(str[0])+","+str[1]);
					}else{
						map.put(name, str[1]);
					}
				}
			}
		}
		return map;
	}

	
}
