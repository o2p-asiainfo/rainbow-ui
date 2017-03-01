package com.linkage.rainbow.ui.report.engine.core.expr;

import com.linkage.rainbow.ui.report.engine.core.Sign;

/**
 * 操作符基类
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
public abstract class Operator extends Sign{
	
	
	/**
	 * 是否为操作符，返回true
	 */
    public boolean isOperator()
    {
        return true;
    }

}
