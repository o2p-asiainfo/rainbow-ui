package com.linkage.rainbow.ui.paginaction;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONArray;

import org.omg.CORBA.Request;

import com.linkage.rainbow.ui.struts.BaseAction;
import com.linkage.rainbow.util.config.DefaultSettings;


/**
 * <p></p>
 * 该类用于存储分页对象
 *
 * @version 	0.0.1
 */
public class Pagination extends BaseAction implements Serializable {
    protected int currentPage;        //当前是第几页
    protected int totalRecord;        //总的记录数
    protected int totalPage;          //总的页数
    protected String condition="";    //设置查询参数
    protected int queryFlag;          //是否是第1次查询
    protected int pageRecord=20;      //默认每页20
    protected int pageStart;          //起始记录
    protected int rows;
    protected int page;
    protected String previous;

    public Pagination(){
        currentPage = 1;
        totalRecord = 0;
        totalPage = 0;
        condition = "";
        queryFlag = -1;
        pageStart = 1;
        DefaultSettings.setDefaultValue(this,"comm.paginaction");
        
    }

	/**
	 * 设置当前页
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取当前页
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		if(currentPage<1){
			return 1;
		}else if(currentPage>getTotalPage()){
			return totalPage;
		}else{
			return currentPage;
		}
	}

	/**
	 * 获取每页记录数
	 * @return the pageRecord
	 */
	public int getPageRecord() {
		return pageRecord;
	}

	/**
	 * 设置每页记录数
	 * @param pageRecord the pageRecord to set
	 */
	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	/**
	 * 设置每页记录数
	 * @param pageRecord the pageRecord to set
	 */
	public void setPageRecord(String pageRecord) {
		this.pageRecord = Integer.parseInt(pageRecord);
	}
	/**
	 * 返回总记录数
	 * @return the totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * 设置总记录数
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(int totalRecord) {
		this.queryFlag=totalRecord;
		this.totalRecord = totalRecord;
	}

	/**
	 * 返回总页数
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage = ( (totalRecord + getPageRecord()) - 1) / getPageRecord();
	}

	/**
	 * 设置总页数
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 获取查询条件
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 设置查询条件
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 返回查询标识
	 * @return the queryFlag
	 */
	public int getQueryFlag() {
		return queryFlag;
	}

	/**
	 * 设置查询标识
	 * @param queryFlag the queryFlag to set
	 */
	public void setQueryFlag(int queryFlag) {
		this.queryFlag = queryFlag;
	}

	/**
	 * 获取起始记录数
	 * @return the pageStart
	 */
	public int getPageStart() {
		return (getCurrentPage()-1)*getPageRecord()+1;
	}

	/**
	 * 获取起始记录数
	 * @return the pageStart
	 */
	public int getPageEnd() {
		if(currentPage==totalPage){
			return this.totalRecord;
		} else {
			return getCurrentPage()*getPageRecord();
		}
	}

	
	/**
	 * 设置起始记录数
	 * @param pageStart the pageStart to set
	 */
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	
	
	
    
    
    public static void main(String[] args) {
    	Pagination a = new Pagination();
    	System.out.println(a.getPageRecord());
	}
    
    public int getRows(){
       rows = Integer.parseInt(getRequest().getParameter("rows"));
       return rows  ;
    }
    public int getPage(){
        page = Integer.parseInt(getRequest().getParameter("page"));
        return page;
    }
    public String getPrevious(){
    	previous =  getRequest().getParameter("previous");
        return previous;
    }
    
    public JSONArray convertJson(List list){
    	return  JSONArray.fromObject(list);
    }
    
}
