package com.linkage.rainbow.ui.report.engine.core;

import java.util.ArrayList;
import java.util.List;

import com.linkage.rainbow.ui.report.engine.core.expr.CSVariable;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Event;
import com.linkage.rainbow.ui.report.engine.model.Events;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;



/**
 * 数据集函数基类
 * 所有数据集相关的函数如列表函数,分组函数,合计函数,计数函数等都继承此基类
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
public abstract class DBFunc extends Func
{  
	
	protected String strFilterExp = null;
	//过滤表达式
	protected Expr filterExp = null;

   // protected String paramStr;

    public DBFunc()  
    {
    }
    
    public DBFunc(String paramStr)
    {
    	//this.paramStr = paramStr;
    }

    protected boolean canOptimized()
    {
        return false; 
    }
    
	/**
	 * 设置函数参数.
	 * 
	 * @param para 函数参数
	 * @throws ReportException 报表异常
	 */
    @Override
	public void setParameter(String strPara) throws ReportException {
		para = FuncParse.getSubPara(strPara,false);
		//过滤表达式
		strFilterExp = null;
		if (para.length > 1 && para[1] != null && para[1].trim().length() > 0) {
			strFilterExp = para[1].trim();
			filterExp =new Expr(strFilterExp, this.report, this.dsName);
		}
	}
	

    /**
     * 取得过滤表达式
     * @return 过滤表达式
     */
	public Expr getFilterExp() {
		return filterExp;
	}

	/**
	 * 设置过滤表达式
	 * @param filterExp 过滤表达式
	 */
	public void setFilterExp(Expr filterExp) {
		this.filterExp = filterExp;
	}

    /**
     * 取得过滤表达式字符串
     * @return 过滤表达式字符串
     */
	public String getStrFilterExp() {
		return strFilterExp;
	}
	/**
	 * 设置过滤表达式字符串
	 * @param filterExp 过滤表达式字符串
	 */
	public void setStrFilterExp(String strFilterExp) {
		this.strFilterExp = strFilterExp;
	}


	/**
	 * 根据过滤表达式,对结果集进行筛选,取得符合条件的记录.
	 * @param list 过滤前的记录集
	 * @return 过滤后记录集
	 */
	protected List getFilterResult(List list){
		List result = new ArrayList();
		//		filterExp 过滤表达式
		if(list != null && list.size()>0 && filterExp != null){
			//取得单元格变量列表
			List csVarList = filterExp.getCsVarList();
			
			Cell cell = this.newCS.getCurrCell();
			Events events = cell.getEvents();
			
			int iSize = csVarList.size();
			for (int i = 0; i < iSize; i++) {
				CSVariable csVar = (CSVariable) csVarList.get(i);
				Event event = events.getEventBySrcCoordinate(csVar.getCoordinate());
				if(event != null){
					Object eventValue = null;
					if(event.getEventValue() instanceof DBGroup){
						eventValue = ((DBGroup)event.getEventValue()).getValue();
					}else if(event.getEventValue() instanceof BaseRow){
						eventValue = ((BaseRow)event.getEventValue()).getValue();
					}
					csVar.setValue(eventValue);
				}else {
					break;
				}
				
			}
			iSize = list.size();
			for (int i = 0; i < iSize; i++) {
				BaseRow baseRow = (BaseRow)list.get(i);
				baseRow.getDs().goTo(baseRow.getIndex());
				Object exprResult = filterExp.calculate();
				if(exprResult instanceof Boolean){
					if((Boolean)exprResult)
						result.add(baseRow);
				}else {
					result.add("过滤表达式错误,应该返回boolean类型的值:"+strFilterExp);
					break;
				}
			}
		}else {
			result = list;
		}
		
		return result;
	}
    
}
