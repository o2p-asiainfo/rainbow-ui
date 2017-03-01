package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;

/**
 * 报表请求类<br>
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-1
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 陈亮   修改时间:2009-6-1<br>       
 *    修改内容:新建
 * <hr>
 */
public class Report implements Serializable, Cloneable{

	/**报表ID*/
	protected String reportId;

	/**数据源配置信息*/
	protected Map dsDef = new HashMap();

	/** 记录集*/
	protected Map ds = new HashMap();

	/** 未扩展前单元格列表*/
	protected CellSet defCS = null;

	/**扩展后单元格列表*/
	protected CellSet newCS = null;

	
	/**报表查询参数 */
	protected Map para = new HashMap();

	/**
	 * 取得报表查询参数
	 * @return 报表查询参数
	 */
	public Map getPara() {
		return para;
	}

	/**
	 * 设置报表查询参数
	 * @param para 报表查询参数
	 */
	public void setPara(Map para) {
		this.para = para;
	}

	/**
	 * 设置报表查询参数
	 * @param id 报表查询参数ID
	 * @param value　报表查询参数值
	 */
	public void setPara(String id, Object value) {
		this.para.put(id, value);
	}

	/**
	 * 根据参数ID取得报表查询参数
	 * @param id 报表查询参数ID
	 * @return 报表查询参数值
	 */
	public Object getPara(String id) {
		return this.para.get(id);
	}

	public Report() {
	}

	/**
	 * 设置报表记录集配置信息
	 * @param strDsName
	 * @param value
	 */
	public void setDsDef(String strDsName, String value) {
		dsDef.put(strDsName, value);

	}

	/**
	 * 取得报表记录集配置信息
	 * @param strDsName
	 * @return
	 */
	public String getDsDef(String strDsName) {
		return (String) dsDef.get(strDsName);

	}

	/**
	 * 设置报表记录集
	 * @param strDsName 记录集名称
	 * @param value 记录集
	 */
	public void setDs(String strDsName, List value) {
		ds.put(strDsName, value);
 
	}
	/**
	 * 设置报表记录集
	 * @param strDsName 记录集名称
	 * @param value 记录集
	 */
	public void setDs(String strDsName, CachedRowSet value) {
		ds.put(strDsName, value);

	}

	/**
	 * 取得报表记录集
	 * @param strDsName 记录集名称
	 * @return 记录集
	 */
	public CachedRowSet getDs(String strDsName) {
		return (CachedRowSet) ds.get(strDsName);

	}
	/**
	 * 取得扩展后表格
	 * @return 扩展后表格
	 * @see CellSet
	 */
	public CellSet getNewCS() {
		return newCS;
	}

	/**
	 * 设置扩展后表格
	 * @param newCS 扩展后表格
	 * @see CellSet
	 */
	public void setNewCS(CellSet newCS) {
		this.newCS = newCS;
		if (this.defCS != null)
			this.defCS.setExtCS(newCS);
	}

	/**
	 * 取得报表定义
	 * @return 报表定义
	 */
	public CellSet getDefCS() {
		return defCS;
	}

	/**
	 * 设置报表定义
	 * @param defCS 报表定义
	 */
	public void setDefCS(CellSet defCS) {
		this.defCS = defCS;
		this.defCS.trim();
		this.newCS =(CellSet)defCS.clone();
		this.newCS.setExtCS(defCS);
		this.defCS.setExtCS(newCS);
		
		for (int i = 0; i < defCS.getRow(); i++) {
			for (int j = 0; j < defCS.getColumn(); j++) {
				Cell oldCell = defCS.getCell(i, j);
				Cell newCell = newCS.getCell(i, j);
				if (oldCell != null) {
					oldCell.setExtField(new Field(i,j));
					newCell.setExtField(new Field(i,j));
				}
			}
		}
		
//		this.newCS = new CellSet(defCS.getRow(), defCS.getColumn());
//		this.newCS.setExtCS(defCS);
//		this.newCS.setSheets_index(defCS.getSheets_index());

		
		//复制格式至新表格,因扩展时,格式要自动继承前一单元格格式.
		
//		for (int i = 0; i < defCS.getRow(); i++) {
//			for (int j = 0; j < defCS.getColumn(); j++) {
//				Cell cell = defCS.getCell(i, j);
//				if (cell != null) {
//					Cell newCell = (Cell) cell.clone();
//					newCell.setContent("");
//					newCell.setBlank(true);
//					newCell.setExtField(new Field(i,j));
//					newCS.setCell(i, j, newCell);
//				}
//			}
//		}

	}

	/**
	 * 取得记录集Ｍap,按记录集名称=>记录集存储
	 * @return 记录集Ｍap,按记录集名称=>记录集存储
	 */
	public Map getDsMap() {
		return ds;
	}

	/**
	 * 取得报表ID
	 * @return 报表ID
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * 设置报表ID
	 * @param reportId 报表ID
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}