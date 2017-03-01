package com.linkage.rainbow.ui.report.engine.core.expr;

import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/**
 * <= 操作
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
public class LessEquals extends Operator{
    public LessEquals()
    {
        pri = 8;
    }
    /**
     * 对操作符的左计算树与右计算树进行 <= 计算
     */
    public Object calculate()
    {
        if(left == null)
            throw new ReportException("不大于操作缺少左操作数");
        if(right == null)
            throw new ReportException("不大于操作缺少右操作数");
        if(MathUtil.compare(left.calculate(), right.calculate()) <= 0)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    /**
     * 取得表达式字符串
     */
    public String getExp()
    {
        if(left == null)
            throw new ReportException("不大于操作缺少左操作数");
        if(right == null)
            throw new ReportException("不大于操作缺少右操作数");
        else
            return "(" + left.getExp() + "<=" + right.getExp() + ")";
    }

 
}
