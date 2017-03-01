package com.linkage.rainbow.ui.report.engine.core.expr;

import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;

/**
 * 数据记录集变量
 * @version 1.0
 * @author 陈亮 2009-06-1
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-06-1<br>
 *         修改内容:新建
 *         <hr>
 *
 */
public class DBVariable  extends Variable{
	
	private String dsName;
	private String colName;
	private CachedRowSet ds;
	private Report report;
	private Object value;
	private boolean isValue;
	
	/**
	 * 空构造
	 *
	 */
	public DBVariable(){
		
	}
	/**
	 * 取得列名
	 * @return 列名
	 */
    public String getColName() {
		return colName;
	}
    /**
     * 设置列名
     * @param colName 列名
     */ 
	public void setColName(String colName) {
		this.colName = colName;
	}
	/**
	 * 取得记录集
	 * @return　记录集
	 */
	public CachedRowSet getDs() {
		return ds;
	}
	/**
	 * 设置记录集 
	 * @param ds 记录集
	 */
	public void setDs(CachedRowSet ds) {
		this.ds = ds;
	}
	/**
	 * 取得记录集名称
	 * @return 记录集名称
	 */
	public String getDsName() {
		return dsName;
	}
	/**
	 * 设置记录集名称
	 * @param dsName 记录集名称
	 */
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	/**
	 * 根据记录集名称，列名，报表对象构造
	 * @param dsName 记录集名称
	 * @param colName 列名
	 * @param report 报表对象
	 * @see Report
	 */
	public DBVariable(String dsName,String colName,Report report)
    {
    	this.dsName = dsName;
    	this.colName = colName;
    	this.report = report;
    
    }
	/**
	 * 根据记录集名称，列名，记录集对象构造
	 * @param dsName 记录集名称
	 * @param colName 列名
	 * @param ds 记录集对象
	 * @see CachedRowSet
	 */
	public DBVariable(String dsName,String colName,CachedRowSet ds)
    {
    	this.dsName = dsName;
    	this.colName = colName;
    	this.ds = ds;
    
    }
	
	/**
	 * 计算数据记录集变量结果值
	 */
    public Object calculate()
    {
    	Object resultObj = null;
    	try {
    		if(isValue){
    			resultObj = value;
    		}else {
	    		if(ds != null)
	    			resultObj = ds.getObject(colName);
	    		else {
	    			if(report != null){
	    				ds = report.getDs(dsName);
	    				if(ds != null)
	    					resultObj = ds.getObject(colName);
	    			} 
	    		}
    		}
		} catch (Exception e) {
		}
       return resultObj;
    }
    
    /**
     * 取得表达式字符串
     */
    public String getExp(){
    	return dsName+ "." + colName;
    }
    
    /**
     * 取得数据记录集变量值
     */
	public  Object getValue(){
		return calculate();
	}
	/**
	 * 设置数据记录集变量值
	 * @param value 数据记录集变量值
	 */
	public void setValue(Object value) {
		isValue = true;
		this.value = value;
	}
}
