package com.linkage.rainbow.ui.report.engine.core.expr;

import com.linkage.rainbow.ui.report.engine.core.Sign;

/**
 * 变量基类
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
public abstract class Variable extends Sign{

	/**
	 * 返回变量的结果值
	 * @return  变量的结果值
	 */
	public abstract Object getValue();

}
