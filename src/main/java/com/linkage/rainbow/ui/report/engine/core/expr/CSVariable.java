package com.linkage.rainbow.ui.report.engine.core.expr;

import com.linkage.rainbow.ui.report.engine.model.Coordinate;

/**
 * 单元格变量
 * 
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
public class CSVariable extends Variable {

	// 报表定义中的坐标,字符串表示,如A1
	private String oldCoor;
	//报表定义中的坐标
	private Coordinate coordinate;

	// 变量当前值.
	private Object value;

	public CSVariable() {

	}

	/**
	 * 根据坐标表达式，建立单元格变量
	 * @param oldCoor  坐标表达式,类似excel的单元格表达式，如A1
	 */
	public CSVariable(String oldCoor) {
		this.oldCoor = oldCoor;
		coordinate = new Coordinate(oldCoor);

	}

	/**
	 * 计算单元变量的值
	 */
	public Object calculate() {
		return this.value;

	}

	/**
	 * 取得表达式
	 */
	public String getExp() {
		return this.oldCoor;
	}

	/**
	 * 取得单元格变量值
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * 取得单元格坐标
	 * @return 单元格坐标
	 */
	public String getOldCoor() {
		return oldCoor;
	}

	/**
	 * 设置单元格坐标
	 * @param oldCoor 单元格坐标
	 */
	public void setOldCoor(String oldCoor) {
		this.oldCoor = oldCoor;
	}

	/**
	 * 设置单元格值
	 * @param value 单元格值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 取得单元格坐标
	 * @return 单元格坐标
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}


}
