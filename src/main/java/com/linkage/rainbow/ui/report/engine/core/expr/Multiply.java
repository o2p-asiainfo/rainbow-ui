package com.linkage.rainbow.ui.report.engine.core.expr;
import java.util.List;

import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/**
 * 相乘操作
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
public class Multiply extends Operator
{

    public Multiply()
    {
        pri = 11;
    }
    /**
     * 对操作符的左计算树与右计算树进行 相乘 计算
     */
    public Object calculate()
    {
        if(left == null)
            throw new ReportException("乘操作缺少左操作数");
        if(right == null)
            throw new ReportException("乘操作缺少右操作数");
        Object o1 = left.calculate();
        Object o2 = right.calculate();
        List list = null;
        Object obj = null;
        if((o1 instanceof List) && !(o2 instanceof List))
        {
            list = (List)o1;
            obj = o2;
        } else
        if(!(o1 instanceof List) && (o2 instanceof List))
        {
            list = (List)o2;
            obj = o1;
        }
        if(list != null)
        {
            int i = 0;
            for(int size = list.size(); i < size; i++)
            {
                Object tmp = list.get(i);
               
                    list.set(i, MathUtil.multiply(tmp, obj));
            }

            return list;
        } else
        {
            return MathUtil.multiply(o1, o2);
        }
    }

    /**
     * 取得表达式字符串
     */
    public String getExp()
    {
        if(left == null)
            throw new ReportException("乘操作缺少左操作数");
        if(right == null)
            throw new ReportException("乘操作缺少右操作数");
        else
            return "(" + left.getExp() + "*" + right.getExp() + ")";
    }


}
