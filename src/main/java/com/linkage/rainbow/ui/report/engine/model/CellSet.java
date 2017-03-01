package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表示表格<br>
 * 用于表格的定义,包括多行多列的单元格(Cell)对象
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

public class CellSet implements Serializable, Cloneable{

	/**宽度 */
	protected String width = "";
	
	/**高度 */
	protected String height = "";
	
	/** 表格的HTML的样式属性 */
	protected String style;// = "border-collapse:collapse;";
	
	/** 表格的HTML的样式表 */
	protected String styleClass = "";
	/** 冻结表头时的表格的HTML的样式表 */
	protected String styleClassScrolling = "";
	
	/** 表格的单元格边距 */
	protected int cellpadding = 0;
	/** 表格的单元格间距 */
	protected int cellspacing = 0;
	/** 表格的过框明细 */
	protected String border = "";
	/** 表格的过框颜色 */
	protected String bordercolor = "";
	/** 表格的对齐方式 */
	protected String align = "";
	/** 表格的背影图 */
	protected String background = "";
	/** 表格的背影色 */
	protected String bgcolor = "";
	
	/** 表格数据。包括行列，单元格 */
	protected Matrix matrix;
	
	/**对应的扩展报表 */
	protected CellSet extCS;

	/**当前坐标的行位置 */
	protected int rowIndex = -1;
	/**当前坐标的列位置 */
	protected int colIndex = -1;
	/**扩展后的行数 */
	protected int rowExtMax = -1;
	/**扩展后的列数 */
	protected int colExtMax = -1;
	
	/**表格ID,根据EXCEL的工作表位置(sheets_index)来保存,按1开始计算, */
	protected int sheets_index ;
	
	/**表格所有行定义信息 */
	protected List rowRangeList = new ArrayList() ;
	/**表格所有列定义信息 */
	protected List colRangeList = new ArrayList() ;

	/**
	 * 取得Excel工作区序号
	 * @return Excel工作区序号
	 */
	public int getSheets_index() {
		return sheets_index;
	}

	/**
	 * 设置Excel工作区序号
	 * @param sheets_index Excel工作区序号
	 */
	public void setSheets_index(int sheets_index) {
		this.sheets_index = sheets_index;
	}

	/**
	 * 取得扩展后的行数
	 * @return 扩展后的行数
	 */
	public int getRowExtMax() {
		return rowExtMax;
	}

	/**
	 * 设置扩展后的行数
	 * @param extMax 扩展后的行数
	 */
	public void setRowExtMax(int extMax) {
		rowExtMax = extMax;
	}

	/**
	 * 取得扩展后的列数
	 * @return 扩展后的列数
	 */
	public int getColExtMax() {
		return colExtMax;
	}

	/**
	 * 设置扩展后的列数
	 * @param extMax 扩展后的列数
	 */
	public void setColExtMax(int extMax) {
		colExtMax = extMax;
	}

	/**
	 * 表格对象空构造
	 *
	 */
	public CellSet() {
		// 设置默认值
		//this.width = "100%";
		matrix = new Matrix();
	}

	/**
	 * 根据当前单元格，取得指定方向的上级单元级扩展后占的位置
	 * 
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 　指定方向的上级单元级扩展后占的位置
	 * @see Field
	 */
	public Field getFExtField(String direction) {
		Field field = null;
		Cell cell = getFCell(direction);
		if (cell != null) {
			field = cell.getExtField();
		}

		return field;
	}

	/**
	 * 根据当前单元格，取得指定方向的上级单元级占的位置
	 * 
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 　指定方向的上级单元级占的位置
	 * @see Field
	 */
	public Field getFCellField(String direction) {
		return getFCellField(getCurrCoordinate(), direction);
	}

	/**
	 * 根据指定单元格，取得上级单元格占的位置
	 * 
	 * @param row 指定单元格的行位置
	 * @param column 指定单元格的列位置
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 指定单元格，取得上级单元格占的位置
	 * @see Field
	 */
	public Field getFCellField(int row, int column, String direction) {
		return getFCellField(new Coordinate(row, column), direction);
	}

	/**
	 * 取得上级单元格占的位置
	 * 
	 * @param coordinate  坐标
	 * @param direction
	 * @return
	 */
	/**
	 * 根据指定单元格，取得上级单元格占的位置
	 * 
	 * @param coordinate  指定单元格的坐标
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 指定单元格，取得上级单元格占的位置
	 * @see Field  Coordinate
	 * 
	 */
	public Field getFCellField(Coordinate coordinate, String direction) {
		Field field = null;
		int iTmpXIndex = -1;
		int iTmpYIndex = -1;
		if (direction != null && direction.equals("y")) {
			if (coordinate.getStartRow() > -1
					&& coordinate.getStartColumn() > 0) {
				iTmpXIndex = coordinate.getStartRow();
				iTmpYIndex = coordinate.getStartColumn() - 1;
				// cell = getCell(xIndex, yIndex-1);
			}
		} else {
			if (coordinate.getStartRow() > 0
					&& coordinate.getStartColumn() > -1) {
				iTmpXIndex = coordinate.getStartRow() - 1;
				iTmpYIndex = coordinate.getStartColumn();
				// cell = getCell(xIndex-1, yIndex);
			}
		}

		if (iTmpXIndex > -1 && iTmpYIndex > -1) {
			for (int i = iTmpXIndex; i >= 0; i--) {
				boolean isBreak = false;
				for (int j = iTmpYIndex; j >= 0; j--) {
					Cell tmpCell = getCell(i, j);
					// if (tmpCell != null) {
					if (!isBlank(tmpCell)) {
						if (iTmpXIndex - i < tmpCell.getRowSpan()
								&& iTmpYIndex - j < tmpCell.getColSpan()) {
							field = new Field(i, j, i + tmpCell.getRowSpan()
									- 1, j + tmpCell.getColSpan() - 1);
							isBreak = true;
						}
						break;
					}
				}
				if (isBreak)
					break;
			}
		}

		// if (iTmpXIndex > -1 && iTmpYIndex > -1) {
		// boolean b = false;
		// for (int i = iTmpXIndex; i >= 0; i--) {
		// Cell tmpCell = getCell(i, iTmpYIndex);
		// // if (tmpCell != null) {
		// if (!isBlank(tmpCell)) {
		// if (iTmpXIndex - i < tmpCell.getRowSpan()) {
		// field = new Field(i, iTmpYIndex, i
		// + tmpCell.getRowSpan() - 1, iTmpYIndex
		// + tmpCell.getColSpan() - 1);
		// b = true;
		// }
		// }
		// }
		//
		// if (b == false) {
		// for (int i = iTmpYIndex; i >= 0; i--) {
		// Cell tmpCell = getCell(iTmpXIndex, i);
		// // if (tmpCell != null) {
		// if (!isBlank(tmpCell)) {
		// if (iTmpYIndex - i < tmpCell.getColSpan()) {
		// field = new Field(iTmpXIndex, i, iTmpXIndex
		// + tmpCell.getRowSpan() - 1, i
		// + tmpCell.getColSpan() - 1);
		// }
		// break;
		// }
		// }
		// }
		// }
		return field;
	}

	/**
	 * 根据当前单元格，取得上级单元格对象
	 * 
	 * @param direction  行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return  上级单元格对象
	 * @see Cell
	 */
	public Cell getFCell(String direction) {
		return getFCell(getCurrCoordinate(), direction);
	}

	/**
	 * 根据指定单元格,取得上级单元格对象
	 * 
	 * @param row 指定单元格的行位置
	 * @param column 指定单元格的列位置
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 上级单元格对象
	 * @see Cell
	 */
	public Cell getFCell(int row, int column, String direction) {
		return getFCell(new Coordinate(row, column), direction);
	}

	/**
	 * 根据指定单元格,取得上级单元格所在的坐标
	 * 
	 * @param row 指定单元格的行位置
	 * @param column 指定单元格的列位置
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 上级单元格所在的坐标
	 * @see Coordinate
	 */
	public Coordinate getFCellCoordinate(int row, int column, String direction) {
		return getFCellCoordinate(new Coordinate(row, column), direction);
	}
	
	/**
	 * 根据指定单元格,取得上级单元格所在的坐标
	 * 
	 * @param coordinate  指定单元格的坐标
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 上级单元格所在的坐标
	 * @see Coordinate
	 */
	public Coordinate getFCellCoordinate(Coordinate coordinate, String direction) {
		Coordinate result = null;
		int iTmpXIndex = -1;
		int iTmpYIndex = -1;
		if (direction != null && direction.equals("y")) {
			if (coordinate.getStartRow() > -1
					&& coordinate.getStartColumn() > 0) {
				iTmpXIndex = coordinate.getStartRow();
				iTmpYIndex = coordinate.getStartColumn() - 1;
				// cell = getCell(xIndex, yIndex-1);
			}
		} else {
			if (coordinate.getStartRow() > 0
					&& coordinate.getStartColumn() > -1) {
				iTmpXIndex = coordinate.getStartRow() - 1;
				iTmpYIndex = coordinate.getStartColumn();
				// cell = getCell(xIndex-1, yIndex);
			}
		}

		if (iTmpXIndex > -1 && iTmpYIndex > -1) {
			for (int i = iTmpXIndex; i >= 0; i--) {
				boolean isBreak = false;
				for (int j = iTmpYIndex; j >= 0; j--) {
					Cell tmpCell = getCell(i, j);
					// if (tmpCell != null) {
					if (!isBlank(tmpCell)) {
						if (iTmpXIndex - i < tmpCell.getRowSpan()
								&& iTmpYIndex - j < tmpCell.getColSpan()) {
							result = new Coordinate(i,j);
							isBreak = true;
						}
						break;
					}
				}
				if (isBreak)
					break;
			}
		}

		return result;
	}

	/**
	 * 根据指定单元格,取得上级单元格对象
	 * 
	 * @param coordinate 指定单元格的坐标
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 上级单元格对象
	 */
	public Cell getFCell(Coordinate coordinate, String direction) {
		Cell cell = null;
		int iTmpXIndex = -1;
		int iTmpYIndex = -1;
		if (direction != null && direction.equals("y")) {
			if (coordinate.getStartRow() > -1
					&& coordinate.getStartColumn() > 0) {
				iTmpXIndex = coordinate.getStartRow();
				iTmpYIndex = coordinate.getStartColumn() - 1;
				// cell = getCell(xIndex, yIndex-1);
			}
		} else {
			if (coordinate.getStartRow() > 0
					&& coordinate.getStartColumn() > -1) {
				iTmpXIndex = coordinate.getStartRow() - 1;
				iTmpYIndex = coordinate.getStartColumn();
				// cell = getCell(xIndex-1, yIndex);
			}
		}

		if (iTmpXIndex > -1 && iTmpYIndex > -1) {
			for (int i = iTmpXIndex; i >= 0; i--) {
				boolean isBreak = false;
				for (int j = iTmpYIndex; j >= 0; j--) {
					Cell tmpCell = getCell(i, j);
					// if (tmpCell != null) {
					if (!isBlank(tmpCell)) {
						if (iTmpXIndex - i < tmpCell.getRowSpan()
								&& iTmpYIndex - j < tmpCell.getColSpan()) {
							cell = tmpCell;
							isBreak = true;
						}
						break;
					}
				}
				if (isBreak)
					break;
			}
		}

		// if (iTmpXIndex > -1 && iTmpYIndex > -1) {
		// boolean b = false;
		// for (int i = iTmpXIndex; i >= 0; i--) {
		// Cell tmpCell = getCell(i, iTmpYIndex);
		// // if (tmpCell != null) {
		// if (!isBlank(tmpCell)) {
		// if (iTmpXIndex - i < tmpCell.getRowSpan()) {
		// cell = tmpCell;
		// b = true;
		// }
		// break;
		// }
		// }
		// if (b == false) {
		// for (int i = iTmpYIndex; i >= 0; i--) {
		// Cell tmpCell = getCell(iTmpXIndex, i);
		// // if (tmpCell != null) {
		// if (!isBlank(tmpCell)) {
		// if (iTmpYIndex - i < tmpCell.getColSpan()) {
		// cell = tmpCell;
		// }
		// break;
		// }
		// }
		// }
		// }
		// if(cell1 == cell){
		//		  	
		// } else {
		// System.out.println("coordinate:"+coordinate);
		// cell = cell1;
		// }

		return cell;
	}
	/**
	 * 取得当前单元格对象
	 * 
	 * @return 当前单元格对象
	 */
	public Cell getCurrCell() {
		if (rowIndex > -1 && colIndex > -1) {
			return getCell(rowIndex, colIndex);
		} else {
			return null;
		}
	}

	/**
	 * 表格构造函数
	 * @param row
	 *            初始化行数
	 * @param col
	 *            初始化列数
	 */
	public CellSet(int row, int col) {
		this();
		matrix = new Matrix(row, col);
	}

	/**
	 * 取得总列数
	 * @return 总列数
	 */
	public int getColumn() {
		return matrix.getColumn();
	}

	/**
	 * 设置总行数
	 * @return 总行数
	 */
	public int getRow() {
		return matrix.getRow();
	}

	/**
	 * 取得指定位置的单元格
	 * @param coordinate 坐标
	 * @return 指定位置的单元格
	 * @see　Coordinate Cell
	 */
	public Cell getCell(Coordinate coordinate) {
		Cell cell = (Cell) matrix.get(coordinate.getStartRow(), coordinate
				.getStartColumn());

		return cell;
	}

	/**
	 * 取得指定位置的单元格
	 * @param row 指定的行位置
	 * @param col 指定的列位置
	 * @return 指定位置的单元格
	 * @see　Coordinate Cell
	 */
	public Cell getCell(int row, int column) {
		Cell cell = (Cell) matrix.get(row, column);

		return cell;
	}

	/**
	 * 获得单元格填充值
	 * 
	 * @return 单元格填充值
	 */
	public int getCellpadding() {
		return cellpadding;
	}

	/**
	 * 获得单元格间距值
	 * 
	 * @return 单元格间距值
	 */
	public int getCellspacing() {
		return cellspacing;
	}

	/**
	 * 获得用百分比表示的宽度
	 * 
	 * @return 用百分比表示的宽度
	 */ 
	public String getWidth() {
		return width;
	}

	/**
	 * 设置宽度.
	 * 
	 * @param width 宽度
	 */
	public void setWidth(int width) {
		this.width = ""+width;
	}
	/**
	 * 设置用百分比表示的宽度.
	 * 
	 * @param width 用百分比表示的宽度.
	 */
	public void setWidth(String width) {
//		if (width > 100 && width < 0) {
//			throw new RuntimeException("Width的值必须在0到100之间。");
//		}
		this.width = width;
	}

	/**
	 * 设置单元格间距值
	 * 
	 * @param cellspacing 单元格间距值
	 */
	public void setCellspacing(int cellspacing) {
		this.cellspacing = cellspacing;
	}

	/**
	 * 设置单元格填充值
	 * 
	 * @param cellpadding 单元格间距值
	 */
	public void setCellpadding(int cellpadding) {
		this.cellpadding = cellpadding;
	}

	/**
	 * 获得表格的HTML的样式属性
	 * 
	 * @return 表格的HTML的样式属性
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置表格的HTML的样式属性
	 * 
	 * @param s 表格的HTML的样式属性
	 */
	public void setStyle(String s) {
		style = s;
	}

	/**
	 * 复制单元格
	 * 
	 * @param nRowSource 原单元格行位置
	 * @param nColumnSource 原单元格列位置
	 * @param nRowTarget 复制的目录单元格行位置
	 * @param nColumnTarget　复制的目录单元格列位置
	 * @return 复制的单元格对象
	 */
	public Cell duplicateCell(int nRowSource, int nColumnSource,
			int nRowTarget, int nColumnTarget) {
		Cell cellOld = (Cell) matrix.get(nRowTarget, nColumnTarget);
		Cell cellSource = (Cell) matrix.get(nRowSource, nColumnSource);
		if (cellSource != null) {
			Cell cellNew = (Cell) cellSource.clone();
			matrix.set(nRowTarget, nColumnTarget, cellNew);
		} else {
			matrix.set(nRowTarget, nColumnTarget, null);
		}
		return cellOld;
	}

	/**
	 * 设置CellSet中某一个单元格。
	 * 
	 * @param coordinate
	 *            坐标
	 * @param cell
	 *            设置的单元格
	 * @return 此单元中原有存放的单元格。
	 */
	public Cell setCell(Coordinate coordinate, Cell cell) {
		return setCell(coordinate.getStartRow(), coordinate.getStartColumn(),
				cell);
	}

	/**
	 * 设置CellSet中某一个单元格。
	 * 
	 * @param row
	 *            行index
	 * @param col
	 *            列index
	 * @param cell
	 *            设置的单元格
	 * @return 此单元中原有存放的单元格。
	 */
	public Cell setCell(int row, int col, Cell cell) {
		Cell oldCell = (Cell) matrix.set(row, col, cell);
		// if (oldCell == null && cell != null) {
		if (isBlank(oldCell) && !isBlank(cell)) {
			for (int j = col; j < col + cell.getColSpan(); j++) {
				for (int i = row - 1; i >= 0; i--) {

					Cell celltmp = (Cell) matrix.get(i, j);
					// if (celltmp != null) {
					if (!isBlank(celltmp)) {
						int iRowSpan = celltmp.getRowSpan();
						if (row - i < iRowSpan) {
							celltmp.setRowSpan(row - i);
						} else {
							break;
						}
					}
				}
			}

			for (int j = row; j < row + cell.getRowSpan(); j++) {
				for (int i = col - 1; i >= 0; i--) {
					Cell celltmp = (Cell) matrix.get(j, i);
					// if (celltmp != null) {
					if (!isBlank(celltmp)) {
						int iColSpan = celltmp.getColSpan();
						if (col - i < iColSpan) {
							celltmp.setColSpan(col - i);
						} else {
							break;
						}
					}
				}
			}
		}
		return oldCell;
	}

	/**
	 * 智能设置CellSet中某一个单元格。用于在标签中设置的单元格,通过行跨度与列跨度计算取得真实的行列位置
	 * 
	 * @param row
	 *            行index
	 * @param col
	 *            列index
	 * @param cell
	 *            设置的单元格
	 * @return 此单元中原有存放的位置。
	 */
	public Coordinate setCellIntelligent(int row, int col, Cell cell) {
		int realityRow=-1;
		int realityCol=-1;
		
		if(row==0 && col==0){
			realityRow=0;
			realityCol=0;
		}else{
			int tmpRow=0;
			int tmpCol=0;
			for (int i = 0; i<getRow(); i++) {
				realityRow =i;
				if(tmpRow==row){
					if(!isMerge(i))
						break;
				}else {
					for (int j = 0; j < getColumn();j++) {
						Cell celltmp = (Cell) matrix.get(i, j);
						if (!isBlank(celltmp) || isMerge(i,j)) {
							tmpRow ++;
							break;
						}
					}
				}
			}
	
			for (int j = 0; j < getColumn();j++) {
				realityCol =j;
				Cell celltmp = (Cell) matrix.get(realityRow, j);
				if (!isBlank(celltmp)) {
					tmpCol ++;
				}else if(!isMerge(realityRow,j)){
					if(tmpCol==col){
						break;
					}
				}
			}
		}
		setCell(realityRow,realityCol,cell);
		return new Coordinate(realityRow,realityCol);
	}
	/**
	 * 表格增加一列
	 *
	 */
	public void addCol() {
		// addCol(1);
		insertColumn(this.getColumn(), 1);
	}
	
	/**
	 *  表格增加Ｎ列
	 * @param addedColNum 增加的列数
	 */
	public void addCol(int addedColNum) {
		if (matrix == null)
			matrix = new Matrix();
		insertColumn(this.getColumn(), addedColNum);
	}

	/**
	 * 表格增加一行
	 *
	 */
	public void addRow() {
		insertRow(this.getRow(), 1);
		// addRow(1);
	}
	/**
	 *  表格增加Ｎ行
	 * @param addRow 增加的行数
	 */
	public void addRow(int row) {
		if (matrix == null)
			matrix = new Matrix();
		// matrix.addRow(row);
		insertRow(this.getRow(), row);
	}

	/**
	 * 判断某行是否已被合并
	 * 
	 * @param row 行数
	 * @return 是否已被合并
	 */
	public boolean isMerge(int row) {
		boolean isMerged = false;
		
		
		int newColNum = getColumn();
		for (int i = getColumn() - 1; i >= 0; i--) {
			int tmp = 0;
			for (int j = 0; j < getRow(); j++) {
				if (getCell(j, i) != null)
					break;
				tmp ++;
			}
			if(tmp == getRow()) {
				newColNum = i;
			} else {
				break;
			}
		}
		int tmp = 0;
		for (int j = 0; j < newColNum;j++) {
			if(isMerge(row,j))
				tmp ++;
			else 
				break;
		}
		if(tmp == newColNum) {
			isMerged=true;
		}
		return isMerged;

	}
	
	/**
	 * 判断某单元格是否已被合并
	 * 
	 * @param row 单元格所在的行位置
	 * @param column 单元格所在的列位置
	 * @return 单元格是否已被合并
	 */
	public boolean isMerge(int row, int column) {
		boolean isMerged = false;
		Cell cell = (Cell) matrix.get(row, column);
		// if (cell == null) {
		if (isBlank(cell)) {
			if (row > -1 && column > -1) {
				for (int i = row; i >= 0; i--) {
					boolean isBreak = false;
					for (int j = column; j >= 0; j--) {
						Cell tmpCell = getCell(i, j);
						// if (tmpCell != null) {
						if (!isBlank(tmpCell)) {
							if (row - i < tmpCell.getRowSpan()
									&& column - j < tmpCell.getColSpan()) {
								isMerged = true;
								isBreak = true;
							}
							break;
						}
					}
					if (isBreak)
						break;
				}
			}

		}

		return isMerged;

	}

	/**
	 * 当isMerge==true时,取得被归并的单元格
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Cell getMergeCell(int row, int column) {
		Cell mergeCell = null;
		Cell cell = (Cell) matrix.get(row, column);
		// if (cell == null) {
		if (isBlank(cell)) {
			if (row > -1 && column > -1) {
				for (int i = row; i >= 0; i--) {
					boolean isBreak = false;
					for (int j = column; j >= 0; j--) {
						Cell tmpCell = getCell(i, j);
						// if (tmpCell != null) {
						if (!isBlank(tmpCell)) {
							if (row - i < tmpCell.getRowSpan()
									&& column - j < tmpCell.getColSpan()) {
								mergeCell = tmpCell;
								isBreak = true;
							}
							break;
						}
					}
					if (isBreak)
						break;
				}
			}

		}

		return mergeCell;

	}

	/**
	 * 当isMerge==true时,取得被归并的单元格的坐标
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Coordinate getMergeCellCoor(int row, int column) {
		Coordinate mergeCellCoor = null;
		Cell cell = (Cell) matrix.get(row, column);
		// if (cell == null) {
		if (isBlank(cell)) {
			if (row > -1 && column > -1) {
				for (int i = row; i >= 0; i--) {
					boolean isBreak = false;
					for (int j = column; j >= 0; j--) {
						Cell tmpCell = getCell(i, j);
						// if (tmpCell != null) {
						if (!isBlank(tmpCell)) {
							if (row - i < tmpCell.getRowSpan()
									&& column - j < tmpCell.getColSpan()) {
								mergeCellCoor = new Coordinate(i, j);
								isBreak = true;
							}
							break;
						}
					}
					if (isBreak)
						break;
				}
			}

		}

		return mergeCellCoor;

	}
    /**
     * 从beginRow~endRow行中，某标列之后，插入N列
     * @param beginRow 
     * @param endRow
     * @param col 插入列的位置
     * @param insertedColNum 插入列的个数
     */
	public void insertColumn( int col,
			int insertedColNum) {
		insertColumn(0, getRow() - 1, col, insertedColNum);
	}
	
	/**
	 * 某标列之后，插入N列
	 * 
	 * @param col 列位置
	 * @param insertedColNum 插入N列
	 */
	public void insertColumn(int beginRow, int endRow,int col, int insertedColNum) {
		if (col > getColumn()) { //如果超过最大行数,则从当前最大行数进行增加
			System.out.println("col > getColumn()");
			insertColumn(beginRow,endRow,getColumn(), col - getColumn() + insertedColNum);
			return;
		}
		// 矩阵中增加N列
		matrix.insertColumn(beginRow,endRow,col, insertedColNum);
		
		//对增加的列,进行单元格处理,上边的单元格不复制,下边单元格复制
		Map map = new HashMap();
		for (int i = beginRow; i <=endRow; i++) {
			if(i< rowIndex){//上边的单元格不复制,只增加相应的列跨度colspan
//				 col之前列的处理
				for (int j = col - 1; j >= 0; j--) {
					Cell celltmp = (Cell) matrix.get(i, j);
					// if (celltmp != null) {
					if (!isBlank(celltmp)) {
						// 设置前一单元格,colspan 加N,前对应的extcell的扩展区域,增加N列.
						int iColSpan = celltmp.getColSpan();
						if (col - 1 - j < iColSpan) {
							celltmp.setColSpan(celltmp.getColSpan()
									+ insertedColNum);
							if (celltmp.getExtField() != null
									&& map.get(celltmp.getExtField().toString()) == null) {
								Cell extCell = extCS.getCell(celltmp.getExtField()
										.getStartRow(), celltmp.getExtField()
										.getStartColumn());
								if (extCell.getExtField() != null)
									extCell.getExtField().setEndColumn(
											extCell.getExtField().getEndColumn()
													+ insertedColNum);
								map.put(celltmp.getExtField().toString(), "have");
							}
						}

						for (; col - j <= insertedColNum; j--) {
							Cell celltmp1 = (Cell) matrix.get(i, j);
							if (!isBlank(celltmp1)) {
								if (celltmp1.getExtField() != null
										&& map.get(celltmp1.getExtField()
												.toString()) == null) {

									Cell extCell = extCS.getCell(celltmp
											.getExtField().getStartRow(), celltmp
											.getExtField().getStartColumn());
									if (extCell.getExtField() != null
											&& extCell.getExtField().getEndColumn() == col - 1) {
										extCell.getExtField().setEndColumn(
												extCell.getExtField()
														.getEndColumn()
														+ insertedColNum);

										map.put(celltmp1.getExtField().toString(),
												"have");
									}
								}
							}
						}

						break;
					}
				}
			}else { //end <rowIndex  
				//下边单元格复制
				Cell currCell =getCurrCell();
				int iColSpan = 1;
				if(currCell != null)
					iColSpan = currCell.getColSpan();
				
				for(int j=col,x=0;x<insertedColNum; j=j+iColSpan){
					for(int k=iColSpan;k>0;k--,x++){
						Cell celltmp = (Cell) matrix.get(i,col-k);
						if(celltmp != null){
							Cell newCell = (Cell)celltmp.clone();
							matrix.set(i ,j+(iColSpan-k), newCell);
						}
						
					}
				}
			}
		}
	}

	/**
	 * 某标列之后，插入一列
	 * 
	 * @param col
	 *            插入列的位置,
	 */
	public void insertColumn(int col) {
		insertColumn(col, 1);
	}

	/**
	 * 插入Ｎ行
	 * 
	 * @param row 开始行位置
	 * @param insertedRowNum 插入Ｎ行
	 */
	public void insertRow(int row, int insertedRowNum) {

		if (row > getRow()) { //如果超过最大行数,则从当前最大行数进行增加
			System.out.println("row > getRow()");
			insertRow(getRow(), row - getRow() + insertedRowNum);
			return;
		}
		
		//矩阵增加行.
		matrix.insertRow(row, insertedRowNum);

		//对增加的行,进行单元格处理,左边的单元格不复制,右边单元格复制
		Map map = new HashMap();
		for (int i = 0; i < this.getColumn(); i++) {//对列循环
			if(i< colIndex){//左边的单元格不复制,只增加相应的行跨度rowspan
				for (int j = row - 1; j >= 0; j--) { //对原有行循环.
					Cell celltmp = (Cell) matrix.get(j, i);
					// if (celltmp != null) {
					if (!isBlank(celltmp)) {
						int iRowSpan = celltmp.getRowSpan();
						if (row - 1 - j < iRowSpan) {
							celltmp.setRowSpan(celltmp.getRowSpan()
									+ insertedRowNum);
							if (celltmp.getExtField() != null
									&& map.get(celltmp.getExtField().toString()) == null) {
	
								Cell extCell = extCS.getCell(celltmp.getExtField()
										.getStartRow(), celltmp.getExtField()
										.getStartColumn());
								if (extCell.getExtField() != null)
									extCell.getExtField().setEndRow(
											extCell.getExtField().getEndRow()
													+ insertedRowNum);
	
								map.put(celltmp.getExtField().toString(), "have");
							}
	
						}
						for (; row - j <= insertedRowNum; j--) {
							Cell celltmp1 = (Cell) matrix.get(j, i);
							if (!isBlank(celltmp1)) {
								if (celltmp1.getExtField() != null
										&& map.get(celltmp1.getExtField()
												.toString()) == null) {
	
									Cell extCell = extCS.getCell(celltmp
											.getExtField().getStartRow(), celltmp
											.getExtField().getStartColumn());
									if (extCell.getExtField() != null
											&& extCell.getExtField().getEndRow() == row - 1) {
										extCell.getExtField().setEndRow(
												extCell.getExtField().getEndRow()
														+ insertedRowNum);
	
										map.put(celltmp1.getExtField().toString(),
												"have");
									}
								}
							}
						}
						break;
	
					}
				}
			}else { //end <xIndex  
				//右边单元格复制
				Cell currCell =getCurrCell();
				int iRowSpan = 1;
				if(currCell != null)
					iRowSpan = currCell.getRowSpan();
				
				for(int j=row,x=0;x<insertedRowNum; j=j+iRowSpan){
					for(int k=iRowSpan;k>0;k--,x++){
						Cell celltmp = (Cell) matrix.get(row-k, i);
						if(celltmp != null){
							Cell newCell = (Cell)celltmp.clone();
							matrix.set(j+(iRowSpan-k), i, newCell);
						}
						
					}
				}
			}
		}


	}

	/**
	 * 在某行这后插入一行
	 * 
	 * @param row 开始行数
	 */
	public void insertRow(int row) {
		insertRow(row, 1);
	}

	/**
	 * 取得存储单元格的矩阵类
	 * @return 存储单元格的矩阵类
	 */
	public Matrix getMatrix() {
		return matrix;
	}

	/**
	 * 设置存储单元格的矩阵类
	 * @param matrix 存储单元格的矩阵类
	 */
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * 将存储单元格的矩阵类，输出到字符串
	 */
	public String toString() {
		return this.matrix.toString();
	}
	
	/**
	 * 取得当前行位置
	 * @return 当前行位置
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * 设置当前行位置
	 * @param index 当前行位置
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * 取得当前列位置
	 * @return 当前列位置
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * 设置当前列位置
	 * @param index 当前列位置
	 */
	public void setColIndex(int index) {
		colIndex = index;
	}

	/**
	 * 收缩表格最右边的空列
	 * @return 
	 */
	public int trimNullTrailCol() {
		return matrix.trimNullTrailCol();
	}

	/**
	 * 取得扩展后有表格
	 * @return 扩展后有表格
	 */
	public CellSet getExtCS() {
		return extCS;
	}

	/**
	 * 设置扩展后有表格
	 * @param extCS 扩展后有表格
	 */
	public void setExtCS(CellSet extCS) {
		this.extCS = extCS;
	}

	/**
	 * 取得当前单元格的坐标
	 * @return 当前单元格的坐标
	 * @see  Coordinate
	 */
	public Coordinate getCurrCoordinate() {
		return new Coordinate(this.rowIndex, this.colIndex);
	}

	/**
	 * 是否存指定的坐标
	 * @param strCoor 坐标字符串,与excel的单元格位置表达方式一致，例A1
	 * @return 是否存指定的坐标
	 */
	public boolean existCoordinate(String strCoor) {
		boolean isExist = false;
		try {
			Coordinate coordinate = new Coordinate(strCoor);
			if (coordinate.getStartRow() >= 0
					&& coordinate.getStartRow() < getRow()
					&& coordinate.getStartColumn() >= 0
					&& coordinate.getStartColumn() < getColumn()) {
				isExist = true;
			}
		} catch (Exception e) {
		}
		return isExist;
	}

	/**
	 * 判断是否为空格
	 * 
	 * @param cell 单元格对象
	 * @return  是否为空格
	 */
	public boolean isBlank(Cell cell) {
		boolean isBlank = false;
		if (cell == null || cell.isBlank()) {
			isBlank = true;
		}
		return isBlank;
	}

	/**
	 * 重新设置矩阵的行列大小，超过的行或列将去除
	 * 
	 * @param newRowNum 最大行数
	 * @param newColNum 最大列数
	 * @return
	 */
	public boolean setSize(int newRowNum, int newColNum) {
		return matrix.setSize(newRowNum, newColNum);
	}
	

	/**
	 * 去除最后的空行跟空列.
	 *
	 */
	public void trim(){
		matrix.trim();
	}
	
	
	/**
	 * 深度clone
	 */
	 public Object clone() {
	        CellSet result = null;
			try { 
			    result = (CellSet)super.clone();
			    result.matrix = (Matrix)matrix.clone();
			} catch (Exception e) { 
			    // assert false;
			}
	        return result;
	    }

	 /**
	  * 取得表格水平位置
	  * @return 表格水平位置
	  */
	public String getAlign() {
		return align;
	}

	/**
	 * 设置表格水平位置
	 * @param align 表格水平位置
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * 取得表格背景图
	 * @return 表格背景图
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置表格背景图
	 * @param background 表格背景图
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * 取得表格背景色
	 * @return 表格背景色
	 */
	public String getBgcolor() {
		return bgcolor;
	}
	/**
	 * 设置表格背景色
	 * @param bgcolor 表格背景色
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	/**
	 * 取得表格边框大小
	 * @return 表格边框大小
	 */
	public String getBorder() {
		return border;
	}

	/**
	 * 设置表格边框大小
	 * @param border 表格边框大小
	 */
	public void setBorder(String border) {
		this.border = border;
	}

	/**
	 * 取得表格边框颜色
	 * @return 表格边框颜色
	 */
	public String getBordercolor() {
		return bordercolor;
	}

	/**
	 * 设置表格边框颜色
	 * @param bordercolor 表格边框颜色
	 */
	public void setBordercolor(String bordercolor) {
		this.bordercolor = bordercolor;
	}

	/**
	 * 取得表格边高度
	 * @return 表格边高度
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置表格边高度
	 * @param height 表格边高度
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 取得表格样式表
	 * @return 表格样式表
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置表格样式表
	 * @param styleClass 表格样式表
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 取得表格的列定义信息
	 * @return 表格的列定义信息
	 */
	public List getColRangeList() {
		return colRangeList;
	}

	/**
	 * 设置表格的列定义信息
	 * @param colRangeList 表格的列定义信息
	 */
	public void setColRangeList(List colRangeList) {
		this.colRangeList = colRangeList;
	}

	/**
	 * 取得表格的行定义信息
	 * @return 表格的行定义信息
	 */
	public List getRowRangeList() {
		return rowRangeList;
	}

	/**
	 * 设置表格的行定义信息
	 * @param rowRangeList 表格的行定义信息
	 */
	public void setRowRangeList(List rowRangeList) {
		this.rowRangeList = rowRangeList;
	}
	
	/**
	 * 增加一行行定义信息
	 * @param range 一行行定义信息
	 */
	public void addRowRange(Range range) {
		this.rowRangeList.add(range);
	}
	
	/**
	 * 增加一列列定义信息
	 * @param range 一列列定义信息
	 */
	public void addColRange(Range range) {
		this.colRangeList.add(range);
	}

	/**
	 * 取得某行的行定义信息
	 * @param index
	 * @return
	 */
	public Range getRowRangeByIndex(int index){
		Range range =null;
		for(int j=0;j<getColumn();j++){
			Cell cell = getCell(index,j);
			if(cell!=null){
				range = cell.getRow();
				break;
			}
		}
		return range;
	}
	
	public void setRowRangeByIndex(int index,Range range){
		setRowRangeByIndex(index,range,true);
	}
	/**
	 * 设置某行的行定义信息
	 * @param index
	 * @return
	 */
	public void setRowRangeByIndex(int index,Range range,boolean isAll){
		for(int j=0;j<getColumn();j++){
			Cell cell = getCell(index,j);
			if(cell!=null){
				cell.setRow(range);
				if(!isAll)
				break;
			}
		}
	}
	
	
	/**
	 * 取得某列的列定义信息
	 * @param index
	 * @return
	 */
	public Range getColRangeByIndex(int index){
		Range range =null;
		for(int i=0;i<getRow();i++){
			Cell cell = getCell(i,index);
			if(cell!=null){
				range = cell.getCol();
				break;
			}
		}
		return range;
	}
	
	/**
	 * 设置某列的列定义信息
	 * @param index 列位置
	 * @param range  列定义信息
	 */
	public void setColRangeByIndex(int index,Range range){
		setColRangeByIndex(index,range,true);
	}
	/**
	 * 设置某列的列定义信息
	 * @param index 列位置
	 * @param range  列定义信息
	 * @param isAll  是否对此列的所有单元格都设置对应的列定义
	 */
	public void setColRangeByIndex(int index,Range range,boolean isAll){
		for(int i=0;i<getRow();i++){
			Cell cell = getCell(i,index);
			if(cell!=null){
				cell.setCol(range);
				if(!isAll)
					break;
			}
		}
	}
	
	/**
	 * 查找列表头区域
	 * @param cs
	 * @return
	 */
	public Field findColHead(){
		Field field = null;
		int iEndColumn = -1;
		for(int j=0;j<this.getColumn();j++){//取每列的第一个非空列进行判断
			boolean isBreak = false;
			for(int i=0;i<this.getRow();i++){
				Cell cell =this.getCell(i,j);
				if(cell != null){
					if(cell.getCol() != null&&cell.getCol().getAreaType()!=null){
						if(cell.getCol().getAreaType().equals("head")){
							iEndColumn = j;
						}else {
							isBreak = true;
						}
					}
					break;
				}
			}
			if(isBreak)
				break;
		}
		if(iEndColumn>-1){
			field = new Field();
			field.setStartRow(0);
			field.setEndRow(this.getRow()-1);
			field.setStartColumn(0);
			field.setEndColumn(iEndColumn);
		}
		return field;
	}
	/**
	 * 查找行表头区域
	 * @param cs
	 * @return
	 */
	public Field findRowHead(){
		Field field = null;
		int iEndRow = -1;
		for(int i=0;i<this.getRow();i++){//取每行的第一个非空行进行判断
			boolean isBreak = false;
			for(int j=0;j<this.getColumn();j++){
				Cell cell =this.getCell(i,j);
				if(cell != null){
					if(cell.getRow() != null&&cell.getRow().getAreaType()!=null){
						if(cell.getRow().getAreaType().equals("head")){
							iEndRow = i;
						}else {
							isBreak = true;
						}
					}
					break;
				}
			}
			if(isBreak)
				break;
		}
		if(iEndRow>-1){
			field = new Field();
			field.setStartRow(0);
			field.setEndRow(iEndRow);
			field.setStartColumn(0);
			field.setEndColumn(this.getColumn()-1);
		}
		return field;
	}

	/**
	 * 取得冻结表头时的表格的HTML的样式表 
	 * @return　冻结表头时的表格的HTML的样式表 
	 */
	public String getStyleClassScrolling() {
		return styleClassScrolling;
	}

	/**
	 * 设置冻结表头时的表格的HTML的样式表 
	 * @param styleClassScrolling 冻结表头时的表格的HTML的样式表 
	 */
	public void setStyleClassScrolling(String styleClassScrolling) {
		this.styleClassScrolling = styleClassScrolling;
	}

}