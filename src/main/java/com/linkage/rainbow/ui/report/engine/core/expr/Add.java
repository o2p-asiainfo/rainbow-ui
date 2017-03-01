package com.linkage.rainbow.ui.report.engine.core.expr;

import java.util.List;

import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;
/**
 * 相加操作
 * <p>
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
public class Add extends Operator
{

    public Add()
    {
        pri = 10;
    }
    /**
     * 对操作符的左计算树与右计算树进行相加计算
     */
    public Object calculate()
    {
        if(right == null)
            throw new ReportException("加操作缺少右操作数");
        if(left == null)
            return right.calculate();
        Object o1 = left.calculate();
        Object o2 = right.calculate();
        List list = null;
        Object obj = null;
        boolean b = true;
        if((o1 instanceof List) && !(o2 instanceof List))
        {
            list = (List)o1;
            obj = o2;
            b = true;
        } else
        if(!(o1 instanceof List) && (o2 instanceof List))
        {
            list = (List)o2;
            obj = o1;
            b = false;
        }
        if(list != null)
        {
            int i = 0;
            for(int size = list.size(); i < size; i++)
            {
                Object tmp = list.get(i);

                    list.set(i, b ? MathUtil.add(tmp, obj) : MathUtil.add(obj, tmp));
            }

            return list;
        } else
        {
            return MathUtil.add(o1, o2);
        }
    }

    /**
     * 取得表达式字符串
     */
    public String getExp()
    {
        if(right == null)
            throw new ReportException("加操作缺少右操作数");
        if(left == null)
            return right.getExp();
        else
            return "(" + left.getExp() + "+" + right.getExp() + ")";
    }

}
