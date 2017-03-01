package com.linkage.rainbow.ui.report.engine.core.expr;
import com.linkage.rainbow.ui.report.engine.model.ReportException;

/**
 * not操作
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
public class Not extends Operator
{

    public Not()
    {
        pri = 13;
    }
    /**
     * 对操作符的左计算树与右计算树进行 not 计算
     */
    public Object calculate()
    {
        if(right == null)
            throw new ReportException("not操作缺少右操作数");
        Object value = right.calculate();
        
        if(value instanceof Boolean)
        {
            if(Boolean.TRUE.equals(value))
                return Boolean.FALSE;
            else
                return Boolean.TRUE;
        } else
        {
            throw new ReportException("not操作的右操作数应为布尔值");
        }
    }

    /**
     * 取得表达式字符串
     */
    public String getExp()
    {
        if(right == null)
            throw new ReportException("not操作缺少右操作数");
        else
            return "(!" + right.getExp() + ")";
    }

}
