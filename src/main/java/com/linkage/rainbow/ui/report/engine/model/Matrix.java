package com.linkage.rainbow.ui.report.engine.model;
import java.io.Serializable;

/**
 * Matrix 矩阵类<br>
 * 矩阵类,实现对矩阵行列的插入，扩展，删除等操作
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
public class Matrix implements Serializable, Cloneable {

	/**行数*/
	int colNum;
    /**列数*/
	int rowNum;
	/**存放数据的数组**/
	Object data[];


    /**
     * 按输入的行、列数，初始化矩阵
     * @param rowNum 初始化矩阵的行大小
     * @param colNum 初始化矩阵的列大小
     */
	public Matrix(int rowNum, int colNum) {
		if (rowNum < 0)
			throw new IllegalArgumentException("number of rows " + rowNum
					+ " <= 0");
		if (colNum < 0) {
			throw new IllegalArgumentException("number of columns " + colNum
					+ " <= 0");
		} else {
			this.rowNum = rowNum;
			this.colNum = colNum;
			data = ((Object[]) (new Object[rowNum][colNum]));
			return;
		}
	}

	/**
	 * 按默认值，初始化矩阵
	 * Matrix(10, 10) {
	 */
	public Matrix() {
		this(10, 10);
	}

	/**
	 * 在列未尾增加N列
	 * @param addedColNum 增加的列数
	 */
	public void addCol(int addedColNum) {
		insertColumn(colNum, addedColNum);
	}

	/**
	 * 在列未尾增加1列
	 *
	 */
	public void addCol() {
		insertColumn(colNum, 1);
	}

	/**
	 * 在列未尾增加N列
	 * @param addedColNum 增加的列数
	 */
	public void addColumn(int addedColNum) {
		insertColumn(colNum, addedColNum);
	}
	/**
	 * 在列未尾增加1列
	 * @param addedColNum 增加的列数
	 */
	public void addColumn() {
		insertColumn(colNum, 1);
	}

	/**
	 * 在列行尾增加1行
	 *
	 */
	public void addRow() {
		insertRow(rowNum, 1);
	}

	/**
	 * 在行未尾增加N行
	 * @param addedRowNum 增加的行数
	 */
	public void addRow(int addedRowNum) {
		insertRow(rowNum, addedRowNum);
	}

	/**
	 * 清空矩阵中所在保存的对象
	 *
	 */
	public void clear() {
		clear(0, rowNum, 0, colNum);
	}

	/**
	 * 清空矩阵中的某些对象
	 * @param beginRow 开始行数
	 * @param endRow 结束行数
	 * @param beginCol 开始列数
	 * @param endCol 结束列数
	 */
	public void clear(int beginRow, int endRow, int beginCol, int endCol) {
		if (beginRow < 0)
			beginRow = 0;
		if (endRow > rowNum)
			endRow = rowNum;
		if (beginCol < 0)
			beginCol = 0;
		if (endCol > colNum)
			endCol = colNum;
		for (int i = beginRow; i < endRow; i++)
			setArrayValue((Object[]) data[i], beginCol, endCol, null);

	}

	/**
	 * 清空矩阵中某些列数据
	 * @param beginCol 开始列
	 * @param endCol 结束列
	 */
	public void clearCol(int beginCol, int endCol) {
		clear(0, rowNum, beginCol, endCol);
	}

	/**
	 * 清空矩阵中某些行数据
	 * @param beginRow 开始行
	 * @param endRow 结束行
	 */
	public void clearRow(int beginRow, int endRow) {
		clear(beginRow, endRow, 0, colNum);
	}


	/**
	 * 对象克隆
	 */
	public Object clone() {
		Matrix m = new Matrix(rowNum, colNum);
//		for (int i = 0; i < rowNum; i++)
//			System.arraycopy(data[i], 0, m.data[i], 0, colNum);

		
//		复制格式至新表格,因扩展时,格式要自动继承前一单元格格式.
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				Cell cell = (Cell)get(i,j);
				if (cell != null) {
					m.set(i,j,cell.clone()) ;
				}
			}
		}
		return m;
	}

	/**
	 * 
	 * @param rowCapacity
	 * @param colCapacity
	 * @return
	 */
	private boolean ensureResize(int rowCapacity, int colCapacity) {
		int oldColCapacity = getCapacity();
		int oldRowCapacity = data.length;
		if (rowCapacity > oldRowCapacity) {
			Object oldData[] = data;
			data = new Object[rowCapacity];
			if (colCapacity <= oldColCapacity) {
				System.arraycopy(((Object) (oldData)), 0, ((Object) (data)), 0,
						rowNum);
				for (int i = rowNum; i < rowCapacity; i++)
					data[i] = ((Object) (new Object[oldColCapacity]));

			} else {
				for (int i = 0; i < rowCapacity; i++) {
					data[i] = ((Object) (new Object[colCapacity]));
					if (i < rowNum)
						System.arraycopy(oldData[i], 0, data[i], 0, colNum);
				}

			}
		} else {
			if (colCapacity <= oldColCapacity) {
				if (rowNum >= rowCapacity)
					return false;
				for (int i = rowNum; i < rowCapacity; i++)
					data[i] = ((Object) (new Object[oldColCapacity]));

			} else {
				int max = rowCapacity <= rowNum ? rowNum : rowCapacity;
				for (int i = 0; i < max; i++) {
					Object oldData = data[i];
					data[i] = ((Object) (new Object[colCapacity]));
					if (i < rowNum)
						System.arraycopy(oldData, 0, data[i], 0, colNum);
				}

			}
		}
		return true;
	}

	/**
	 * 批量设置数组中某些单元的值
	 * @param o 数组对象
	 * @param from 开始index
	 * @param to 结果index
	 * @param val 设置的值
	 */
	private static void setArrayValue(Object o[], int from, int to, Object val) {
		for (int i = from; i < to; i++)
			o[i] = val;

	}

	/**
	 * 取得矩阵中某行某列的数据
	 * @param row 行index
	 * @param col 列index
	 * @return
	 */
	public Object get(int row, int col) {
		if (row < 0 || row >= rowNum) return null;
//			throw new IndexOutOfBoundsException("row[" + row
//					+ "] out of bounds[" + rowNum + "]");
		if (col < 0 || col >= colNum) return null;
//			throw new IndexOutOfBoundsException("col[" + col
//					+ "] out of bounds[" + colNum + "]");
		else
			return ((Object[]) data[row])[col];
	}

	private int getCapacity() {
		return data.length != 0 ? ((Object[]) data[0]).length : colNum;
	}

	/**
	 * 取得总列数
	 * @return
	 */
	public int getColCount() {
		return colNum;
	}
	/**
	 * 取得总列数
	 * @return
	 */
	public int getColumn() {
		return colNum;
	}
	/**
	 * 取得总行数
	 * @return
	 */
	public int getRow() {
		return rowNum;
	}
	/**
	 * 取得总行数
	 * @return
	 */
	public int getRowCount() {
		return rowNum;
	}

	/**
	 * 某标列之后，插入一列 
	 * @param col 插入列的位置
	 */
	public void insertCol(int col) {
		insertColumn(col, 1);
	}

    /**
     * 某列之后，插入N列
     * @param col 插入列的位置
     * @param insertedColNum 插入列的个数
     */
	public void insertCol(int col, int insertedColNum) {
		insertColumn(col, insertedColNum);
	}

	/**
	 * 某标列之后，插入一列
	 * @param col 插入列的位置,
	 */
	public void insertColumn(int col) {
		if (col < 0)
			throw new IndexOutOfBoundsException("col: " + col + ", colNum: "
					+ colNum);
		if (col > colNum)
			col = colNum;
		int moved = colNum - col;
		int oldCapacity = getCapacity();
		int minCapacity = colNum + 1;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			for (int i = 0; i < rowNum; i++) {
				Object oldColData[] = (Object[]) data[i];
				data[i] = ((Object) (new Object[newCapacity]));
				System.arraycopy(((Object) (oldColData)), 0, data[i], 0, col);
				System.arraycopy(((Object) (oldColData)), col, data[i],
						col + 1, moved);
			}

		} else {
			for (int i = 0; i < rowNum; i++) {
				System.arraycopy(data[i], col, data[i], col + 1, moved);
				setArrayValue((Object[]) data[i], col, col + 1, null);
			}

		}
		colNum = minCapacity;
	}
    /**
     * 某列之后，插入N列
     * @param col 插入列的位置
     * @param insertedColNum 插入列的个数
     */
	public void insertColumn(int col, int insertedColNum) {
		insertColumn(0, rowNum - 1, col, insertedColNum);
	}
    /**
     * 从beginRow~endRow行中，某标列之后，插入N列
     * @param beginRow 
     * @param endRow
     * @param col 插入列的位置
     * @param insertedColNum 插入列的个数
     */
	public void insertColumn(int beginRow, int endRow, int col,
			int insertedColNum) {
		if (col < 0)
			throw new IndexOutOfBoundsException("col: " + col + ", colNum: "
					+ colNum);
		if (insertedColNum < 1)
			throw new IllegalArgumentException("insertedColNum: "
					+ insertedColNum);
		if (beginRow < 0)
			throw new IndexOutOfBoundsException("beginRow: " + beginRow
					+ "must be >= 0");
		if (endRow >= rowNum)
			throw new IndexOutOfBoundsException("endRow: " + endRow
					+ ", rowNum: " + rowNum);
		if (endRow < beginRow)
			return;
		if (col > colNum)
			col = colNum;
		int newCol = col + insertedColNum;
		int moved = colNum - col;
		int oldCapacity = getCapacity();
		int minCapacity = colNum + insertedColNum;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			for (int i = 0; i < rowNum; i++) {
				Object oldColData[] = (Object[]) data[i];
				data[i] = ((Object) (new Object[newCapacity]));
				if (i >= beginRow && i <= endRow) {
					System.arraycopy(((Object) (oldColData)), 0, data[i], 0,
							col);
					System.arraycopy(((Object) (oldColData)), col, data[i],
							newCol, moved);
				} else {
					System.arraycopy(((Object) (oldColData)), 0, data[i], 0,
							oldColData.length);
				}
			}

		} else {
			for (int i = 0; i < rowNum; i++) {
				if (i >= beginRow && i <= endRow) {
					System.arraycopy(data[i], col, data[i], newCol, moved);
					setArrayValue((Object[]) data[i], col, newCol, null);
				}
			}

		}
		colNum = minCapacity;
	}

	/**
	 * 某行之后，插入一行
	 * @param row 插入行的位置
	 */
	public void insertRow(int row) {
		insertRow(row, 1);
	}
    /**
     * 某行之后，插入N行
     * @param row 插入行的位置
     * @param insertedRowNum 插入行的个数
     */
	public void insertRow(int row, int insertedRowNum) {
		if (row < 0)
			throw new IndexOutOfBoundsException("row: " + row + ", rowNum"
					+ rowNum);
		if (insertedRowNum < 1)
			throw new IllegalArgumentException("insertedRowNum: "
					+ insertedRowNum);
		if (row > rowNum)
			row = rowNum;
		int newRow = row + insertedRowNum;
		int moved = rowNum - row;
		int oldCapacity = data.length;
		int minCapacity = rowNum + insertedRowNum;
		int colCapacity = getCapacity();
		if (minCapacity > oldCapacity) {
			int newCapacity = oldCapacity * 2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			Object oldData[] = data;
			data = new Object[newCapacity];
			System
					.arraycopy(((Object) (oldData)), 0, ((Object) (data)), 0,
							row);
			System.arraycopy(((Object) (oldData)), row, ((Object) (data)),
					newRow, moved);
			for (int i = row; i < newRow; i++)
				data[i] = ((Object) (new Object[colCapacity]));

		} else {
			System.arraycopy(((Object) (data)), row, ((Object) (data)), newRow,
					moved);
			for (int i = row; i < newRow; i++)
				data[i] = ((Object) (new Object[colCapacity]));

		}
		rowNum = minCapacity;
	}

	public static void main(String args[]) {
		Matrix m = new Matrix(3, 3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				m.set(i, j, i + "," + j);

		}
		System.out.println(m);
		//m.insertColumn(1, 1, 1, 2);
		//System.out.println(m);
		m.insertColumn(1, 2);
		System.out.println(m);
		m.insertRow(2, 2);
		System.out.println(m);
		m.removeColumn(3);
		System.out.println("num = " + m.getRow() + "," + m.getColumn());
		System.out.println("len = " + m.data.length + ","
				+ ((Object[]) m.data[0]).length);
		System.out.println(m);
		m.removeRow(2, 3);
		System.out.println("num = " + m.getRow() + "," + m.getColumn());
		System.out.println("len = " + m.data.length + ","
				+ ((Object[]) m.data[0]).length);
		System.out.println(m);
		Matrix m1 = (Matrix) m.clone();
		System.out.println("clone: ");
		System.out.println(m1);
		m.set(0, 0, "clone");
		System.out.println("set");
		System.out.println(m);
		System.out.println(m1);
		System.out.println(m.toString(true));
		m.trimToSize();
		System.out.println(m.toString(true));
		System.out.println("ensure");
		System.out.println(m.ensureResize(4, 3));
		System.out.println("set size(5,5) = " + m.setSize(5, 5));
		System.out.println(m.toString(true));
		m.set(4, 4, "xxxxx");
		System.out.println(m.toString(true));
		System.out.println(m.toString());
		System.out.println("set size(3,2) = " + m.setSize(3, 2));
		System.out.println(m.toString(true));
		System.out.println(m.toString());
		System.out.println("set size(4,4) = " + m.setSize(4, 4));
		System.out.println(m.toString(true));
		System.out.println(m.toString());
		System.out.println("trimNullTrailCol() = " + m.trimNullTrailCol());
		System.out.println(m.toString(true));
		System.out.println(m.toString());
	}

	/**
	 * 删除某列
	 * @param col
	 */
	public void removeCol(int col) {
		removeColumn(col, col + 1);
	}

	/**
	 * 删除从beginCol~endCol的N个列
	 * @param beginCol
	 * @param endCol
	 */
	public void removeCol(int beginCol, int endCol) {
		removeColumn(beginCol, endCol);
	}

	/**
	 * 删除某列
	 * @param col
	 */
	public void removeColumn(int col) {
		removeColumn(col, col + 1);
	}

	/**
	 * 删除从beginCol~endCol的N个列
	 * @param beginCol
	 * @param endCol
	 */
	public void removeColumn(int beginCol, int endCol) {
		if (beginCol >= endCol)
			return;
		if (beginCol < 0)
			throw new IndexOutOfBoundsException("beginCol: " + beginCol
					+ ", endCol: " + endCol);
		if (endCol > colNum)
			endCol = colNum;
		int moved = colNum - endCol;
		if (moved < 0)
			return;
		int newColNum = colNum - (endCol - beginCol);
		for (int i = 0; i < rowNum; i++)
			if (data[i] != null) {
				if (endCol < colNum)
					System.arraycopy(data[i], endCol, data[i], beginCol, moved);
				setArrayValue((Object[]) data[i], newColNum, colNum, null);
			}

		colNum = newColNum;
	}

	/**
	 * 删除某行
	 * @param row
	 */
	public void removeRow(int row) {
		removeRow(row, row + 1);
	}

	/**
	 * 删除从beginRow~endRow的N个行
	 * @param beginRow
	 * @param endRow
	 */
	public void removeRow(int beginRow, int endRow) {
		if (beginRow >= endRow)
			return;
		if (beginRow < 0)
			throw new IndexOutOfBoundsException("beginRow: " + beginRow
					+ ", endRow" + endRow);
		if (endRow > rowNum)
			endRow = rowNum;
		int moved = rowNum - endRow;
		if (moved < 0) {
			return;
		} else {
			System.arraycopy(((Object) (data)), endRow, ((Object) (data)),
					beginRow, moved);
			int newRowNum = rowNum - (endRow - beginRow);
			setArrayValue(data, newRowNum, rowNum, null);
			rowNum = newRowNum;
			return;
		}
	}

	/**
	 * 设置矩阵中某一个单元的对象。
	 * @param row 行index
	 * @param col 列index
	 * @param element 设置的对象
	 * @return 此单元中原有存放的对象。
	 */
	public Object set(int row, int col, Object element) {
		if (row < 0 || row >= rowNum)
			throw new IndexOutOfBoundsException("row[" + row
					+ "] out of bounds[" + rowNum + "]");
		if (col < 0 || col >= colNum) {
			throw new IndexOutOfBoundsException("col[" + col
					+ "] out of bounds[" + colNum + "]");
		} else {
			Object colData[] = (Object[]) data[row];
			Object o = colData[col];
			colData[col] = element;
			return o;
		}
	}

	/**
	 * 重新设置矩阵的行列大小.
	 * @param newRowNum
	 * @param newColNum
	 * @return
	 */
	public boolean setSize(int newRowNum, int newColNum) {
		boolean b = ensureResize(newRowNum, newColNum);
		if (colNum > newColNum) {
			int min = rowNum > newRowNum ? newRowNum : rowNum;
			for (int i = 0; i < min; i++)
				setArrayValue((Object[]) data[i], newColNum, colNum, null);

		}
		colNum = newColNum;
		if (rowNum > newRowNum)
			setArrayValue(data, newRowNum, rowNum, null);
		rowNum = newRowNum;
		return b;
	}

	public String toString() {
		return toString(false);
	}

	public String toString(boolean all) {
		StringBuffer sb = new StringBuffer(4096);
		int n1 = rowNum;
		if (all)
			n1 = data.length;
		int n2 = colNum;
		if (all && data.length > 0)
			n2 = ((Object[]) data[0]).length;
		for (int row = 0; row < n1; row++) {
			//sb.append(" | ");
//			sb.append("\t");
			Object o[] = (Object[]) data[row];
			if (o == null) {
				sb.append(" ---------------------------- |");
			} else {
				for (int col = 0; col < n2; col++) {
				  if(o[col] instanceof Cell) {
					Cell cell = (Cell)o[col];

					if(cell != null) {
						//sb.append("(").append(row).append(",").append(col).append(")");
						sb.append(cell).append("(").append(cell.getRowSpan()).append(",").append(cell.getColSpan()).append(")");
						sb.append(cell.getExtField());	
						//sb.append("(").append(cell.getGroup()).append(")");	
						//sb.append(" | ");
						sb.append("\t");
					} else {
					  //sb.append("(").append(row).append(",").append(col).append(")");
					  sb.append(o[col]);
					  //sb.append(" | ");
					  sb.append("\t");
					}
				  } else {
					  //sb.append("(").append(row).append(",").append(col).append(")");
					 // sb.append(o[col]==null? "":o[col]);
					  sb.append(o[col]);
					  //sb.append(" | ");
					  sb.append("\t");
				  }
				}

			}
			sb.append("\n");
		}

		return sb.toString();
	}

	/**
	 * 去除最后的空行跟空列.
	 *
	 */
	public void trim(){
		
		int newColNum = colNum;
		int newRowNum = rowNum;
		for (int i = colNum - 1; i >= 0; i--) {
			int tmp = 0;
			for (int j = 0; j < rowNum; j++) {
				if (get(j, i) != null)
					break;
				tmp ++;
			}
			if(tmp == newRowNum) {
				newColNum = i;
			} else {
				break;
			}
		}
		
		for (int i = rowNum - 1; i >= 0; i--) {
			int tmp = 0;
			for (int j = 0; j < newColNum; j++) {
				if (get(i, j) != null)
					break;
				tmp ++;
			}
			if(tmp == newColNum) {
				newRowNum = i;
			} else {
				break;
			}
		}
		if(newRowNum <0)
			newRowNum = 0;
		if(newColNum <0)
			newColNum = 0;
		//如果有最后有空行或空列,去除空行空列.
		if(newColNum != colNum || newRowNum != rowNum){
			setSize(newRowNum,newColNum);
		}
	}
	
	
	public int trimNullTrailCol() {
		int nullColNum = 0x7fffffff;
		for (int i = 0; i < rowNum; i++) {
			int tmp = 0;
			for (int j = colNum - 1; j >= 0; j--) {
				if (get(i, j) != null)
					break;
				tmp++;
			}

			if (tmp < nullColNum)
				nullColNum = tmp;
			if (nullColNum == 0)
				return 0;
		}

		colNum -= nullColNum;
		return nullColNum;
	}

	/**
	 * 收缩至最大行与列
	 *
	 */
	public void trimToSize() {
		Object oldData[] = data;
		data = ((Object[]) (new Object[rowNum][colNum]));
		for (int i = 0; i < rowNum; i++)
			System.arraycopy(oldData[i], 0, data[i], 0, colNum);

	}

}