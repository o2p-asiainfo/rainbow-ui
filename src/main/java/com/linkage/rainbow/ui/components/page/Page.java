package com.linkage.rainbow.ui.components.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.components.Form;

import com.linkage.rainbow.ui.paginaction.Pagination;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.linkage.rainbow.util.DateUtil;
import com.linkage.rainbow.util.StringUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页标签<br>
 * <p>
 * @version 1.0
 * <hr>
 */
public class Page extends ClosingUIBean {
	/**
	 * 定义page组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "page/pagepart-close";//分页标签模版
	/**
	 * 定义page组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "page/pagepart";//分页标签模版
	
	private static Log log = LogFactory.getLog(Page.class);
	
	private String id;
	//private String url;
	private String skin;
	private String savePara;
	private String forGridId;
	private String excelMethod;
	private String excelTitle;
	private String exportMode;
	private String randnum;
	private String columnName;
	private String databaseColumnName; 
	private String showSelect;
	private String selectPerPage;
	private String selectCol;
	
	private String exportCurrentPage;
	
	//是否根据时间段来设置导出
	private String exportByWorkingHours;
	private String workingHours; 
	private String exportPageRecords;
	private String showGotoButton;//是否显示跳转按钮

	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Page
	 */
	public Page(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(id != null)
		addParameter("id",id);
		if(forGridId != null)
		addParameter("forGridId",findString(forGridId));
		if(excelMethod!=null)
		addParameter("excelMethod",findString(excelMethod));
		if (excelTitle != null) 
			addParameter("excelTitle", findString(excelTitle));
		if(exportMode != null)
		addParameter("exportMode",findString(exportMode));
		if(randnum != null)
		addParameter("randnum",findString(randnum));
		if(columnName!=null)
		addParameter("columnName",findString(columnName));
		if(databaseColumnName!=null)
			addParameter("databaseColumnName",findString(databaseColumnName));
		//addParameter("url",url);
		if(skin != null)
			addParameter("skin",findString(skin));
		if(savePara != null)
			addParameter("savePara",findValue(savePara,Boolean.class));
		if(showSelect != null)
			addParameter("showSelect",findValue(showSelect,Boolean.class));
		if(selectPerPage != null){
			addParameter("selectPerPage",findString(selectPerPage));
			List ls = new ArrayList();
			String[] selectpage = findString(selectPerPage).split(",");
			for(int i=0;i<selectpage.length;i++){
				ls.add(selectpage[i]);
			}
			addParameter("selectlist",ls);
		}
		
		if(exportCurrentPage!=null){
			addParameter("exportCurrentPage",findValue(exportCurrentPage,Boolean.class));
		}
		int iExportPageRecords = 65535;
		if(exportPageRecords!=null){
			try {iExportPageRecords =(Integer)findValue(exportPageRecords,Integer.class);} catch (Exception e) {}
		}
		addParameter("exportPageRecords",iExportPageRecords);
		
		
		//是否根据上班时间段导出
		boolean isExportByWorkingHours =false;
		if(exportByWorkingHours !=null){
			try {isExportByWorkingHours = (Boolean)findValue(exportByWorkingHours,Boolean.class);} catch (Exception e) {}			
		}
		
		if(isExportByWorkingHours){
			//判断当前是否是上班时间,workingHours格式为24小时制,多个时间用,号分隔,例:8:00~12:00,14:00~18:00
			boolean isInWorkingHours = false;
			String workingHoursTmp = "";
			String currTime="";
			if(workingHours != null){
				workingHoursTmp = findString(workingHours);
				if(workingHoursTmp!=null && workingHours.length()>0){
					Date currDate = new Date();
					currTime = DateUtil.convDateToString(currDate,"HH:mm");
					String workingHoursArray[] = workingHoursTmp.split(",");
					for(int i=0;i<workingHoursArray.length;i++){
						try {
							String hoursArray[] = workingHoursArray[i].split("~");
							if(inTime(currDate,hoursArray[0],hoursArray[1])){//是否在指定时间段内
								isInWorkingHours = true;
								break;
							}
						} catch (Exception e) {}	
					}
				}
			}
			addParameter("isInWorkingHours",isInWorkingHours);
			if(isInWorkingHours){
				addParameter("exportCurrentPage",true);
				addParameter("inWorkingHoursMsg","上班时间("+workingHoursTmp+")只能导出当前页数据.当前时间:"+currTime);
			}
		}
		
		
		if(selectCol != null)
			addParameter("selectCol",findValue(selectCol,Boolean.class));
		
		/**
		 * 对查询url中的参数进行处理
		 */
		String queryString = request.getQueryString();
		if(queryString!= null){
			try {
				/**
				 * 需要转码的
				 */
				if(!ContainsCn(queryString)){
					queryString =new String(queryString.getBytes("iso-8859-1"),"GBK");
				}
				addParameter("actionurl",queryString);
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
			
		}
		
		
		addParameter("tagUtil", new ComponentUtil(request));
//		Map formMap = (Map)getParameters().get("form");
//		if(formMap !=null){
//			System.out.println("action:"+formMap.get("action"));
//		}

		//是否此分面标签已被包含在。
		Form form =  (Form)findAncestor(Form.class);
		if(form != null){
			addParameter("haveForm", true);
			if(savePara==null)
				addParameter("savePara",false);
		}else {
			if(savePara==null)
				addParameter("savePara",true);
		}
		
		//addParameter("first_label", "第一页");
		addParameter("RamCodeId","tmp_"+StringUtil.getRamCode(12));
		if(showGotoButton != null)
			addParameter("showGotoButton",findValue(showGotoButton,Boolean.class));
		 Map params = getParameters();
//		 System.out.println("params:"+params);
	}

	
	/**
	 * 是否在时间段内
	 * @param date 当前时间
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return 是否在时间段内
	 */
	public static boolean inTime(Date date,String beginTime,String endTime){
		boolean isInTime = false;
		try {
			String dateStr = DateUtil.convDateToString(date,"yyyy-MM-dd")+" ";
			Date begin  = DateUtil.stringToDate(dateStr+beginTime,"yyyy-MM-dd HH:mm");
			Date end  = DateUtil.stringToDate(dateStr+endTime,"yyyy-MM-dd HH:mm");
			if(date.getTime()>begin.getTime()&&date.getTime()<end.getTime() )
				isInTime = true;
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		
		return isInTime;
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
		// TODO Auto-generated method stub
		return CLOSE_TEMPLATE;
	}
	
	/**
	 * 指定模板
	 * @author 陈亮 2009-02-11
	 * @param template 模板名. 例:一模板存放的完整路径是
	 *        template/xhtml/date/WdatePicker/date.ftl 其中: template
	 *        为模板存放主目录,对应的标签属性是: templateDir
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
	 *        xhtml 为主题名称,对应的标签属性是: theme
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值. .ftl
	 *        为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
	 *        date/WdatePicker/date 为模板名称, 对应的标签属性是: template 与 openTemplate
	 *        ,如果标签没有设置,则通过此类的getDefaultOpenTemplate()
	 *        与getDefaultTemplate()分别取得标签的开始模板与结束模板.
	 *        便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
	 *        一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date
	 *        目录下再建立子目录,如:WdatePicker,来区分不同的模板实现. 在标签调用时,不需要分别设置template 与
	 *        openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
	 */
	public void setTemplate(String template) {		
	    if(template != null && template.trim().length()>0) {
	    	this.template = "page/"+template+"/pagepart";
		    this.setOpenTemplate("page/"+template+"/pagepart-close");
	    } else {
	    	template = null;
	    }
	}
	
	/**
	 * 设置分页的标识 对应action中分页对象的实例名称
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param url the url to set
	 */
//	public void setUrl(String url) {
//		this.url = url;
//	}

	/**
	 * 分页的皮肤
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 获取debu图标信息
	 * @return
	 */
	public String getSqlDebugIcon(){
		String icon = "";
//		try {
//			icon =  com.linkage.rainbow.ui.util.sqlDebug.SqlDebugUtil.getIcon(request);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		return icon;
	}

	/**
	 * 获取是否保存请求的参数
	 * @return String
	 */
	public String getSavePara() {
		return savePara;
	}

	/**
	 * 设置是否保存请求的参数
	 * @param savePara
	 */
	public void setSavePara(String savePara) {
		this.savePara = savePara;
	}

	/**
	 * 获取分页对象标识
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 获取皮肤
	 * @return String
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置是否绑定grid的id  若无绑定则无法导出excel
	 * @param forGridId the forGridId to set
	 */
	public void setForGridId(String forGridId) {
		this.forGridId = forGridId;
	}

	/**
	 * 设置导出excel的方法
	 * @param excelMethod the excelMethod to set
	 */
	public void setExcelMethod(String excelMethod) {
		this.excelMethod = excelMethod;
	}

	/**
	 * 设置excel的标题
	 * @param excelTitle the excelTitle to set
	 */
	public void setExcelTitle(String excelTitle) {
		this.excelTitle = excelTitle;
	}

	/**
	 * 设置导出模式
	 * @param exportMode the exportMode to set
	 */
	public void setExportMode(String exportMode) {
		this.exportMode = exportMode;
	}

	/**
	 * 生成随机数
	 * @param randnum the randnum to set
	 */
	public void setRandnum(String randnum) {
		this.randnum = String.valueOf((int)(Math.random()*1000000));
	}

	/**
	 * 设置文本列名
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * 设置数据库列名
	 * @param databaseColumnName the databaseColumnName to set
	 */
	public void setDatabaseColumnName(String databaseColumnName) {
		this.databaseColumnName = databaseColumnName;
	}

	/**
	 * 设置是否显示下拉框
	 * @param showSelect the showSelect to set
	 */
	public void setShowSelect(String showSelect) {
		this.showSelect = showSelect;
	}

	/**
	 * 设置每页记录数
	 * @param perPage the perPage to set
	 */
	public void setSelectPerPage(String selectPerPage) {
		this.selectPerPage = selectPerPage;
	}

	/**
	 * 设置是否是要导出的excel列
	 * @param selectCol
	 */
	public void setSelectCol(String selectCol) {
		this.selectCol = selectCol;
	}

	/**
	 * 设置是否导出当前页图标
	 * @param exportCurrentPage
	 */
	public void setExportCurrentPage(String exportCurrentPage) {
		this.exportCurrentPage = exportCurrentPage;
	}


	/**
	 * 设置上班时间段,24小时制,多个时间用,号分隔,例:8:00~12:00,14:00~18:00
	 * 上班时间段时只导出本页数据.
	 * @param workingHours
	 */
	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}
	
	/**
	 * 是否根据上班时间段来导出
	 * @param exportByWorkingHours
	 */
	public void setExportByWorkingHours(String exportByWorkingHours) {
		this.exportByWorkingHours = exportByWorkingHours;
	}
	
	/**
	 * 导出时第页多少条
	 * @param exportPageRecords
	 */
	public void setExportPageRecords(String exportPageRecords) {
		this.exportPageRecords = exportPageRecords;
	}
	/**
	 * 判断是否有乱码
	 * @param str
	 * @return boolean
	 */
	public static boolean ContainsCn(String str){
		boolean flag = false;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while(m.find()){
			flag = true;
		}
		return flag;
		
	}
	
	
	/**
	 * 设置是否需要跳转按钮
	 * @param showGotoButton
	 */
	public void setShowGotoButton(String showGotoButton) {
		this.showGotoButton = showGotoButton;
	}
	
	
	public static void main(String[] args) {
		System.out.print(Page.inTime(new Date(),"8:30","16:00"));
		
	}
}
