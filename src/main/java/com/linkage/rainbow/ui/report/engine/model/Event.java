/*
 * @(#)Event.java        2009-6-10
 *
 *
 */

package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;

/**
 * 单个事件类<br>
 * 当单元格扩展时,对其他单元格有影响的作事件通知.
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-10
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class Event implements Serializable, Cloneable{
	//事件发起单元格
	private Cell srcCell;
	//事件发起单元格坐标
	private Coordinate srcCoordinate ;
	//事件内容
	private Object eventValue;
	//是否为纵向扩展的事件
	private boolean isLengthways;
	
	
	/**
	 * 取得事件内容,如分组对象DBGropu,行对象BaseRow
	 * @return 事件内容,如分组对象DBGropu,行对象BaseRow
	 * @see DBGroup BaseRow
	 */
	public Object getEventValue() {
		return eventValue;
	}
	/**
	 * 设置事件内容,如分组对象DBGropu,行对象BaseRow
	 * @param eventValue 事件内容,如分组对象DBGropu,行对象BaseRow
	 * @see DBGroup BaseRow
	 */
	public void setEventValue(Object eventValue) {
		this.eventValue = eventValue;
	}
	/**
	 * 取得事件源单元格
	 * @return 事件源单元格
	 */
	public Cell getSrcCell() {
		return srcCell;
	}
	/**
	 * 设置事件源单元格
	 * @param srcCell 事件源单元格
	 */
	public void setSrcCell(Cell srcCell) {
		this.srcCell = srcCell;
	}
	/**
	 * 取得事件源单元格坐标
	 * @return 事件源单元格坐标
	 */
	public Coordinate getSrcCoordinate() {
		return srcCoordinate;
	}
	/**
	 * 设置事件源单元格坐标
	 * @param srcCoordinate 事件源单元格坐标
	 */
	public void setSrcCoordinate(Coordinate srcCoordinate) {
		this.srcCoordinate = srcCoordinate;
	}
	
	/**
	 * 是否为纵向扩展的事件
	 * @return 是否为纵向扩展的事件
	 */
	public boolean isLengthways() {
		return isLengthways;
	}
	/**
	 * 设置是否为纵向扩展的事件
	 * @param isLengthways 是否为纵向扩展的事件
	 */
	public void setLengthways(boolean isLengthways) {
		this.isLengthways = isLengthways;
	}
	
	public String toString(){
		return eventValue!=null ? eventValue.toString():null;
	}
	

}
