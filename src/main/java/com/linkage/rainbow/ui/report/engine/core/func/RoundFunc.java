package com.linkage.rainbow.ui.report.engine.core.func;

import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/**
 * 函数说明：四舍五入操作. <br>
 * 语法： ruond(valueExp,placesExp) <br>
 * 参数说明：<br>
 * valueExp 原数值<br>
 * placesExp 四舍五入的位置<br>
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
public class RoundFunc extends Func
{
	
	Expr valueExp = null;
    public RoundFunc()
    {
    }
    /**
     * 四舍五入计算
     */
    public Object calculate()
    {
    	if( valueExp == null){
	        if(para == null ||para.length == 0)
	            throw new ReportException("round函数参数列表为空");
	        
	        
	        valueExp = new Expr(para[0],report,dsName);
    	}
    	String placesExpStr = para.length > 1 && para[1] != null ? para[1].trim(): "0";
    	int iPlaces = 0;
    	try {
    		iPlaces = Integer.parseInt(placesExpStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
        Object result1 = valueExp.calculate();
        Object sumResult = MathUtil.round(result1,iPlaces);

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
