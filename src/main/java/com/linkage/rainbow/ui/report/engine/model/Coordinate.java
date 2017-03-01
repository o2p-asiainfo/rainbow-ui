package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;

import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/**
 * 坐标,cellset中的一个单元格位置<br>
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-1
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 陈亮   修改时间:2009-6-1<br>       
 *    修改内容:新建
 * <hr>
 */
public class Coordinate implements Serializable, Cloneable{

	/**列位置 */
	protected int startCol;
	/**行位置 */
	protected int startRow;

	public Coordinate() {

	}

	/**
	 * 行使用数字，列使用A~Z字母
	 * 例：A1
	 * @param str
	 */
	public Coordinate(String str) {
		char c[] = str.toCharArray();
		int row;
		int col;
		boolean isHave = false;
		try {
			
			for (int i = 0; i < c.length; i++) {

				if (MathUtil.isDigit(c[i])) {
					col = MathUtil.to10(str.substring(0, i));
					row = Integer.parseInt(str.substring(i));
					this.startRow = row - 1;
					this.startCol = col - 1;
					isHave = true;
					break;
				}
			}
			
			

		} catch (Exception e) {
			throw new ReportException(str + "坐标表达不合法");
		}
		if(!isHave ){
			throw new ReportException(str + "坐标表达不合法");
		}
	}
	/**
	 * 根据行列位置构造一个坐标对象
	 * @param startRow 行位置
	 * @param startCol 列位置
	 */
	public Coordinate(int startRow, int startCol) {
		this.startCol = startCol;
		this.startRow = startRow;
	}

	/**
	 * 取得列位置
	 * @return 列位置
	 */
	public int getStartColumn() {
		return startCol;
	}

	/**
	 * 设置列位置
	 * @param startCol 列位置
	 */
	public void setStartColumn(int startCol) {
		this.startCol = startCol;
	}

	/**
	 * 取得行位置
	 * @return 行位置
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * 设置行位置
	 * @param startRow 行位置
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public static void main(String args[]) {

		Coordinate Coordinate = new Coordinate("counts");
		System.out.println(Coordinate.getStartColumn());

	}

	/**
	 * 判断两个坐标类是否相等
	 */
	public boolean equals(Object obj) {
		try {
			if (obj != null) {
				Coordinate f = (Coordinate) obj;
				if (this.startRow == f.getStartRow()
						&& this.startCol == f.getStartColumn()) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	/**
	 * 比较位置大小,
	 * 返回1表达本位置>other位置
	 * 返回0表达本位置=other位置
	 * 返回-1表达本位置<other位置
	 * @param other
	 * @return
	 */
	public int compareTo(Coordinate other){
		int iResult = -1;
		if(getStartRow() >other.getStartRow()){
			iResult = 1;
		}else if(getStartRow() ==other.getStartRow()){
			if(getStartColumn() ==other.getStartColumn()){
				iResult = 0;
			}else if(getStartColumn() >other.getStartColumn()){
				iResult =1;
			}else{
				iResult =-1;
			}
		}else {
			iResult = -1;
		}
		return iResult;
	}

	/**
	 * 转成Excel的单元格位置表达字符串，例：A1
	 * @return
	 */
	public String toExcelCoor() {
		return MathUtil.to26(startCol + 1) + (startRow + 1);

	}

	
	public String toString() {
		return "(" + startRow + "," + startCol + ")";
	}
}
