package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;

/**
 * cellset中的一块区域。<br>
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
public class Field extends Coordinate implements Serializable, Cloneable{

	private int endCol;

	private int endRow;

	/**
	 * 空构造,区域范围为(0,0,0,0)
	 *
	 */
	public Field() {
	}

	/**
	 * 根据坐标类构造,区域范围为(坐标行位置,坐标列位置,坐标行位置,坐标列位置)
	 *
	 */
	public Field(Coordinate coordinate) {
		this.startCol = coordinate.getStartColumn();
		this.startRow = coordinate.getStartRow();
		this.endCol = coordinate.getStartColumn();
		this.endRow = coordinate.getStartRow();
	}
	/**
	 * 根据指定行与列位置构造,区域范围为(指定行位置,指定列位置,指定行位置,指定列位置)
	 *
	 */
	public Field(int startRow, int startCol) {
		this.startCol = startCol;
		this.startRow = startRow;
		this.endCol = startCol;
		this.endRow = startRow;
	}
	/**
	 * 根据指定开始行，开始列位置,结束行位置,结束列位置构造,<br>
	 * 区域范围为(开始行位置,开始列位置,结束行位置,结束列位置)
	 *
	 */
	public Field(int startRow, int startCol, int endRow, int endCol) {
		this.startCol = startCol;
		this.startRow = startRow;
		this.endCol = endCol;
		this.endRow = endRow;
	}
	/**
	 * 重新修改区域，根据指定开始行，开始列位置,结束行位置,结束列位置设置区域,<br>
	 * 区域范围为(开始行位置,开始列位置,结束行位置,结束列位置)
	 *
	 */
	public void setAll(int startRow, int startCol, int endRow, int endCol) {
		this.startCol = startCol;
		this.startRow = startRow;
		this.endCol = endCol;
		this.endRow = endRow;
	}

	/**
	 * 取得结束列位置
	 * @return 结束列位置
	 */
	public int getEndColumn() {
		return endCol;
	}

	/**
	 * 取得结束行位置
	 * @return 结束行位置
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * 设置结束列位置 
	 * @param col 结束列位置
	 */
	public void setEndColumn(int col) {
		this.endCol = col;
	}

	/**
	 * 设置结束行位置
	 * @param row 结束行位置
	 */
	public void setEndRow(int row) {
		this.endRow = row;
	}

	/**
	 * 判断两个区域对象是否相等
	 * 如果修改的是坐标对象(Coordinate)，则只判断开始行与开始列进行比较．
	 * @see Coordinate
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Field){
			try {
				Field f = (Field) obj;
				if (this.startRow == f.getStartRow()
						&& this.startCol == f.getStartColumn()
						&& this.endRow == f.getEndRow()
						&& this.endCol == f.getEndColumn()) {
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(obj instanceof Coordinate){
			return super.equals(obj);
		}
		
		
		return false;
	}

	public String toString() {
		return "(" + startRow + "," + startCol + "," + endRow + "," + endCol
				+ ")";
	}

	/**
	 * 范围偏移,横向偏移
	 * 
	 * @param colOffset
	 *            横向偏移量
	 */
	public void setColOffset(int colOffset) {
		this.startCol += colOffset;
		this.endCol += colOffset;
	}

	/**
	 * 范围偏移,纵向偏移
	 * 
	 * @param rowOffset
	 *            纵向偏移量
	 */
	public void setRowOffset(int rowOffset) {
		this.startRow += rowOffset;
		this.endRow += rowOffset;
	}
	
	/**
	 * 判断是否区域包含
	 * @param row 开始坐标的行位置
	 * @param col 开始坐标的列位置
	 * @return 是否区域包含
	 */
	public boolean contain(int row, int col) {
		return row >= startRow && row <= endRow && col >= startCol
				&& col <= endCol ? true : false;
	}
	/**
	 * 判断是否区域包含
	 * @param  coor 开始坐标位置
	 * @return 是否区域包含
	 */
	public boolean contain(Coordinate coor) {
		return coor != null && coor.getStartRow() >= startRow
				&& coor.getStartRow() <= endRow
				&& coor.getStartColumn() >= startCol
				&& coor.getStartColumn() <= endCol ? true : false;
	}
	/**
	 * 判断是否区域包含
	 * @param field 在判断的区域
	 * @return 是否区域包含
	 */
	public boolean contain(Field field) {
		return field != null && field.getStartRow() >= startRow
				&& field.getStartRow() <= endRow
				&& field.getStartColumn() >= startCol
				&& field.getStartColumn() <= endCol
				&& field.getEndRow() >= startRow && field.getEndRow() <= endRow
				&& field.getEndColumn() >= startCol
				&& field.getEndColumn() <= endCol ? true : false;
	}
	
	/**
	 * 计算交叉区域
	 * 
	 * @param other 其他区域
	 * @return 两个区域的交叉区域
	 */
	public  Field crossField(Field other) {
		Field newField = null;

		int iStartRow = other.getStartRow() >= getStartRow()?other.getStartRow():getStartRow();
		int iEndRow = other.getEndRow() <= getEndRow()?other.getEndRow():getEndRow();
		
		int iStartColumn = other.getStartColumn() >= getStartColumn()?other.getStartColumn():getStartColumn();
		int iEndColumn = other.getEndColumn() <= getEndColumn()?other.getEndColumn():getEndColumn();
		
		if(iStartRow<=iEndRow && iStartColumn<=iEndColumn){
			newField = new Field(iStartRow,iStartColumn,iEndRow,iEndColumn);
		}
		
		return newField;
	}
	

	

	public static void main(String[] args) {
		Field a = new Field(5,0,10,2);
		Field b = new Field(0,0,2,10);
		Field c = a.crossField(b);
		System.out.println(c);
	}
}
