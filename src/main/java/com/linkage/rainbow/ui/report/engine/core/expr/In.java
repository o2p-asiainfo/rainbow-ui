package com.linkage.rainbow.ui.report.engine.core.expr;
import java.util.List;

import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/*in操作
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
public class In extends Operator	{

	    public In()
	    {
	        pri = 7;
	    }
	    /**
	     * 对操作符的左计算树与右计算树进行 in 计算
	     */
	    public Object calculate()
	    {
	        if(left == null)
	            throw new ReportException("in操作缺少左操作数");
	        Object value1 = left.calculate();
	        if(right == null)
	            throw new ReportException("in操作缺少右操作数");
	        Object value2 = right.calculate();
	        if((value1 instanceof List) && (value2 instanceof List))
	        {
	            List list1 = (List)value1;
	            List list2 = (List)value2;
	            for(int i = 0; i < list1.size(); i++)
	            {
	                if(!listEquals(list2, list1.get(i)))
	                    return Boolean.FALSE;
	            }

	            return Boolean.TRUE;
	        }
	        if((value1 instanceof List) && !(value2 instanceof List))
	        {
	            List list1 = (List)value1;
	            for(int i = 0; i < list1.size(); i++)
	            {
	                if(!MathUtil.equals(list1.get(i), value2))
	                    return Boolean.FALSE;
	            }

	            return Boolean.TRUE;
	        }
	        if(!(value1 instanceof List) && (value2 instanceof List))
	        {
	            if(listEquals((List)value2, value1))
	                return Boolean.TRUE;
	        } else
	        {
	            if(MathUtil.equals(value1, value2))
	                return Boolean.TRUE;
	        }
	        return Boolean.FALSE;
	    }

	    private boolean listEquals(List list, Object o)
	    {
	        for(int i = 0; i < list.size(); i++)
	        {
	            if(MathUtil.equals(list.get(i), o))
	                return true;
	        }

	        return false;
	    }

	    /**
	     * 取得表达式字符串
	     */
	    public String getExp()
	    {
	        throw new ReportException("非法操作符in");
	    }
}
