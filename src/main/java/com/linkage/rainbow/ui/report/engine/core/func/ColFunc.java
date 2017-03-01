package com.linkage.rainbow.ui.report.engine.core.func;


import com.linkage.rainbow.ui.report.engine.core.Func;


/**
 * 函数说明：col函数,取得当前在列表中是第几列,从1开始记 <br>
 * 语法： col() <br>
 * 参数说明：<br>
 * 无参数 <br>
 * @version 1.0
 * @author 陈亮 2009-08-10
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-08-10<br>
 *         修改内容:新建
 *         <hr>
 *
 */
public class ColFunc extends Func {

	
	
	/**
	 * col函数计算
	 */
	public Object calculate() {
		//newCS.getColIndex() 是以0开始计.
		//col函数,取得当前在列表中是第几列,从1开始记 
		return this.newCS.getColIndex()+1;
	}
	
	

	/**
	 * 取得表达式字符串
	 * 
	 * @return 表达式字符串
	 */
	public String getExp(){
		return null;
	}
 
}
