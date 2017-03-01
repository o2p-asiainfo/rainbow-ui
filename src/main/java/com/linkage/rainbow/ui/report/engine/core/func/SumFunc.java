package com.linkage.rainbow.ui.report.engine.core.func;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Event;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;
/**
 * 合计操作
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
public class SumFunc extends Func
{
	Object sumResult = new Integer(0);
	Expr param1 = null;
    public SumFunc()
    {
    }
    /**
     * 合计计算
     */
    public Object calculate()
    {
    	if( param1 == null){
	        if(para == null ||para.length == 0)
	            throw new ReportException("sum函数参数列表为空");
	        
	        
	        param1 = new Expr(para[0],report,dsName);
    	}

        Object result1 = param1.calculate();
        sumResult = MathUtil.add(sumResult,result1);

        return sumResult;
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
