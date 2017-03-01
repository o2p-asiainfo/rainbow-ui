package com.linkage.rainbow.ui.views.jsp.ui.page;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.linkage.rainbow.ui.components.page.Page;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.linkage.rainbow.ui.paginaction.Pagination;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页标签<br>
 * <p>
 * @version 1.0
 * <hr>
 */
public class PageTag extends AbstractUITag {
	
	//private String url;         //action对应的url
	private String id;          //action对应的分页对象
	private String skin;        //默认为绿色皮肤
	private String forGridId;   //用于导出excel的参数
	private String excelMethod; //导出excel调用的方法
	private String savePara;	//是否保存参数
	private String excelTitle;  //导出的excel名称
	private String exportMode;  //导出excel的方式\
	private String randnum = "";     //生成随机数
	private String columnName;  //生成列名
	private String databaseColumnName; 
	private String showSelect;  //是否显示下拉框
	private String selectPerPage;//下拉框每页显示多少条     
	private String selectCol;    //选择导出的列
	
	private String exportCurrentPage;//导出当前页
	
	
	//是否根据时间段来设置导出
	private String exportByWorkingHours;
	private String workingHours; 
	private String exportPageRecords;

	private String showGotoButton;//是否显示跳转按钮
	
	/**
	 * 设置列标题
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * 设置数据库对应字段
	 * @param databaseColumnName the databaseColumnName to set
	 */
	public void setDatabaseColumnName(String databaseColumnName) {
		this.databaseColumnName = databaseColumnName;
	}
	/**
	 * 调用配置文件 设置默认值
	 */
	public PageTag(){
	    
	    
		DefaultSettings.setDefaultValue(this,"rainbow.ui.page");
	}
	

	/**
	 * 实现接口类 继承父类的基本信息
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new Page(stack, req, res);
	}
	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		Page part=(Page)component;
		part.setId(id);
		//part.setUrl(url);
		part.setSkin(skin);
		part.setSavePara(savePara);
		part.setForGridId(forGridId);
		part.setExcelMethod(excelMethod);
		part.setExcelTitle(excelTitle);
		part.setExportMode(exportMode);
		part.setRandnum(randnum);
		part.setColumnName(columnName);
		part.setDatabaseColumnName(databaseColumnName);
		part.setShowSelect(showSelect);
		part.setSelectPerPage(selectPerPage);
		part.setSelectCol(selectCol);
		
		part.setExportCurrentPage(exportCurrentPage);
		part.setWorkingHours(workingHours);
		part.setExportByWorkingHours(exportByWorkingHours);
		part.setExportPageRecords(exportPageRecords);
		part.setShowGotoButton(showGotoButton);
	}
	
	/**
	 * 将action中的分页对象设置到内存
	 * @return int
	 */
	public int doStartTag() throws JspException {
		super.doStartTag();
		
		//OgnlValueStack stack = (OgnlValueStack)getStack();
		Pagination tmppage = (Pagination) findValue(id);
		getStack().getContext().put(id, tmppage);
		return SKIP_BODY;
	}
	
	
	/**
	 * @param url the url to set
	 */
//	public void setUrl(String url) {
//		if(url.length()>1){
//			int index=url.indexOf("?");
//			//if(index>-1)
//				url=url.substring(0,index);
//		}
//		this.url = url;
//	}
	
	/**
	 * 设置action对应的分页对象实例
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置分页皮肤
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}
	/**
	 * 获取是否保存参数
	 * @return String
	 */
	public String getSavePara() {
		return savePara;
	}
	/**
	 * 设置是否保存参数
	 * @param savePara
	 */
	public void setSavePara(String savePara) {
		this.savePara = savePara;
	}
	/**
	 * action对应的分页对象实例
	 * @return String
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 返回皮肤
	 * @return String
	 */
	public String getSkin() {
		return skin;
	}
	/**
	 * 设置绑定的grid
	 * @param forGridId the forGridId to set
	 */
	public void setForGridId(String forGridId) {
		this.forGridId = forGridId;
	}
	/**
	 * 设置excel导出的方法
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
	 * 设置导出方式
	 * @param exportMode the exportMode to set
	 */
	public void setExportMode(String exportMode) {
		this.exportMode = exportMode;
	}
	/**
	 * 获取随机码
	 * @return the randnum
	 */
	public String getRandnum() {
		return randnum;
	}
	/**
	 * 设置是否显示下拉框
	 * @param showSelect the showSelect to set
	 */
	public void setShowSelect(String showSelect) {
		this.showSelect = showSelect;
	}
	/**
	 * 设置每页显示多少条
	 * @param selectPerPage the selectPerPage to set
	 */
	public void setSelectPerPage(String selectPerPage) {
		this.selectPerPage = selectPerPage;
	}
	
	/**
	 * 设置是否选择列
	 * @param selectCol
	 */
	public void setSelectCol(String selectCol) {
		this.selectCol = selectCol;
	}
	
	/**
	 * 设置是否导出当前页
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
	 * 是否需要跳转按钮
	 * @param showGotoButton
	 */
	public void setShowGotoButton(String showGotoButton) {
		this.showGotoButton = showGotoButton;
	}
	public void setRandnum(String randnum) {
		this.randnum = randnum;
	}
	
}
