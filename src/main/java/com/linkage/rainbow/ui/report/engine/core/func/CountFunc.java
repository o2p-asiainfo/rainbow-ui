package com.linkage.rainbow.ui.report.engine.core.func;

import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/**
 * count操作
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
public class CountFunc extends Func
{
	Object countResult = new Integer(0);
	Expr param1 = null;
    public CountFunc()
    {
    }
    /**
     * count操作计算
     */
    public Object calculate()
    {
    	if( param1 == null){
	        if(para == null ||para.length == 0)
	            throw new ReportException("count函数参数列表为空");
	        
	        
	        param1 = new Expr(para[0],report,dsName);
    	}

        Object result1 = param1.calculate();
        if(result1 != null){
        	countResult = MathUtil.add(countResult,new Integer(1));
        }
        
        return countResult;
    }
	/**
	 * 取得表达式字符串
	 * 
	 * @return 表达式字符串
	 */
    public String getExp()
    {
 
        return "";
    }

}