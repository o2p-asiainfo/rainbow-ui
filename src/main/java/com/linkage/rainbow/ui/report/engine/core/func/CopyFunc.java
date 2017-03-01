package com.linkage.rainbow.ui.report.engine.core.func;

import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.core.FuncParse;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Coordinate;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.DirectionUtil;

/**
 * 单元格复制函数,如小计单元格跟随分组进行复制. 语法： copy(strExp,hostCellExp) <br>
 * 参数说明：<br>
 * strExp 要被复制的内容 <br>
 * hostCellExp 主单元格,即字符串根据此单元格的扩展而扩展
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
public class CopyFunc extends Func {

	public CopyFunc() {

	}

	/**
	 * 单元格复制计算
	 */
	public Object calculate() {
		// System.out.println("aaa");
		if (para.length < 2) {
			throw new ReportException("copy函数缺少必要参数");
		}
		String strExp = para[0];
		String hostCellExp = para[1];
		Coordinate coor = new Coordinate(hostCellExp);
		Cell oldHostCell = oldCS.getCell(coor);
		Cell oldCell = oldCS.getCurrCell();

		int xStart = newCS.getRowIndex();
		int xMax = newCS.getRowExtMax();
		int yStart = newCS.getColExtMax() + 1;
		if (oldCS.getColIndex() == 0) {
			if (xMax + 1 > newCS.getRow() - 1) {
				newCS.addRow();
			}
			xStart = xMax + 1;
			xMax = xMax + 1;
			yStart = 0;
		}
		int yMax = yStart;
		Field xFExtField = oldCS.getFExtField("x");
		Field yFExtField = oldCS.getFExtField("y");
		if (xFExtField != null) {
			if (xFExtField.getEndColumn() > yMax)
				yMax = xFExtField.getEndColumn();
		}
		if (yFExtField != null) {
			if (yFExtField.getEndRow() > xMax)
				xMax = yFExtField.getEndRow();
		}

		if (oldHostCell != null) {

			Field extField = oldHostCell.getExtField();
			FuncParse funcParse = new FuncParse(oldHostCell.getContent()
					.toString());
			String extDirectionExp = "x";
			if (funcParse.getPara() != null) {

				// 扩展方向(X为纵向,Y为横向)
//				extDirectionExp = funcParse.getPara().length > 3 && funcParse.getPara()[3] != null ? funcParse
//						.getPara()[3].trim().toLowerCase() : "x";
			    extDirectionExp = DirectionUtil.getDirection(funcParse.getPara(),3);
			}

			Cell newCell = (Cell) oldCell.clone();
			newCell.setContent(strExp);

			if (extDirectionExp.equals("y")) {
				if (yStart > 0) {
					yMax--;
					yStart--;
				}
				// int iCount = -1;
				for (int i = extField.getStartColumn(); i <= extField
						.getEndColumn(); i++) {
					Cell newTmpCell = newCS.getCell(extField.getStartRow(), i);

					// if(newTmpCell != null){
					if (!newCS.isBlank(newTmpCell)) {

						if (newTmpCell != null
								&& !coor.equals(newTmpCell.getExtField())) {
							i = i + newTmpCell.getColSpan() - 1;
							continue;
						}
						// iCount ++;
						if (xStart >= extField.getStartRow()
								&& xStart <= extField.getEndRow()) {
							int iIndex = i + newTmpCell.getColSpan();

							if (newCS.isBlank(newCS.getCell(extField
									.getStartRow(), iIndex))
									&& !newCS.isMerge(extField.getStartRow(),
											iIndex)
									&& newCS.getFCell(extField.getStartRow(),
											iIndex, "x") == newCS.getFCell(
											extField.getStartRow(), i, "x")) {

							} else {
								newCS
										.insertColumn(iIndex, oldCell
												.getColSpan());
								// if(iCount >0) {
								yMax = yMax + oldCell.getColSpan();
								yStart = yMax;
								// }
								
								// 被复制单元格,对应的后面单元格给予按前一单元格填充.
								fillEntityCell(extField.getStartRow(), iIndex,
										oldCell, "x");
							}
							newCS.setCell(extField.getStartRow(), iIndex,
									newCell);
							if(iIndex >yMax)
								yMax = iIndex;

							i = iIndex + oldCell.getColSpan() - 1;

							// if (extField.getStartRow() + oldCell.getRowSpan()
							// - 1 > xMax) {
							// xMax = extField.getStartRow()
							// + oldCell.getRowSpan() - 1;
							// }
						} else {
							int yCurr = -1;
							boolean isInColSpan = false;
							int cellColNum = newTmpCell.getColSpan();
							for (int k = 0; k < cellColNum; k++) {
								// if (newCS.getCell(
								// extField.getEndRow()+1,i+k) == null) {
								if (newCS.isBlank(newCS.getCell(xStart, i + k))
										&& !newCS.isMerge(xStart, i + k)) {
									yCurr = i + k;
									isInColSpan = true;
									break;
								}
							}
							if (isInColSpan) {
								if (yCurr + newCell.getColSpan() - 1 > extField
										.getEndColumn()) {
									newCS.insertColumn(
											extField.getEndColumn() + 1, yCurr
													+ newCell.getColSpan() - 1
													- extField.getEndColumn());
									yMax = yMax + yCurr + newCell.getColSpan()
											- 1 - extField.getEndColumn();
								}
							} else {
								yCurr = i + newTmpCell.getColSpan();
								newCS.insertColumn(yCurr, oldCell.getColSpan());
								// if(iCount >0) {
								yMax = yMax + oldCell.getColSpan();
								yStart = yMax;
								// }
								i = yCurr + oldCell.getColSpan() - 1;
								
								// 被复制单元格,对应的后面单元格给予按前一单元格填充.
								fillEntityCell(xStart, yCurr, oldCell,
										"x");
							}
							newCS.setCell(xStart, yCurr, newCell);
							if(yCurr >yMax)
								yMax = yCurr;

							// if (xStart + oldCell.getRowSpan() - 1 > xMax) {
							// xMax = xStart + oldCell.getRowSpan() - 1;
							// }

						}

					}
				}
			} else {
				for (int i = extField.getStartRow(); i <= extField.getEndRow(); i++) {
					Cell newTmpCell = newCS.getCell(i, extField
							.getStartColumn());

					if (newTmpCell != null
							&& !coor.equals(newTmpCell.getExtField())) {
						i = i + newTmpCell.getRowSpan() - 1;
						continue;
					}

					// if(newTmpCell != null){
					if (!newCS.isBlank(newTmpCell)) {
						if (yStart >= extField.getStartColumn()
								&& yStart <= extField.getEndColumn()) {
							int iIndex = i + newTmpCell.getRowSpan();

							if (newCS.isBlank(newCS.getCell(iIndex, extField
									.getStartColumn()))
									&& !newCS.isMerge(iIndex, extField
											.getStartColumn())
									&& newCS.getFCell(iIndex, extField
											.getStartColumn(), "y") == newCS
											.getFCell(i, extField
													.getStartColumn(), "y")) {

							} else {
								newCS.insertRow(iIndex, oldCell.getRowSpan());
								xMax = xMax + oldCell.getRowSpan();
								xStart = xMax;
								
								// 被复制单元格,对应的后面单元格给予按前一单元格填充.
								fillEntityCell(iIndex, yStart, oldCell,
										"y");
							}
							newCS.setCell(iIndex, extField.getStartColumn(),
									newCell);


							i = iIndex + oldCell.getRowSpan() - 1;

							if (extField.getStartColumn()
									+ oldCell.getColSpan() - 1 > yMax) {
								yMax = extField.getStartColumn()
										+ oldCell.getColSpan() - 1;
							}

						} else {
							int xCurr = -1;

							boolean isInRowSpan = false;
							for (int k = 0; k < newTmpCell.getRowSpan(); k++) {
								// if (newCS.getCell(i+k,
								// extField.getEndColumn()+1) == null) {
								if (newCS.isBlank(newCS.getCell(i + k, yStart))
										&& !newCS.isMerge(i + k, yStart)) {
									xCurr = i + k;
									isInRowSpan = true;
									break;
								}
							}
							if (isInRowSpan) {
								if (xCurr + newCell.getRowSpan() - 1 > extField
										.getEndRow()) {
									newCS.insertRow(extField.getEndRow() + 1,
											xCurr + newCell.getRowSpan() - 1
													- extField.getEndRow());
									xMax = xMax + xCurr + newCell.getRowSpan()
											- 1 - extField.getEndRow();
								}
							} else {
								xCurr = i + newTmpCell.getRowSpan();
								newCS.insertRow(xCurr, oldCell.getRowSpan());
								xMax = xMax + oldCell.getRowSpan();
								xStart = xMax;
								i = xCurr + oldCell.getRowSpan() - 1;
								
								
								// 被复制单元格,对应的后面单元格给予按前一单元格填充.
								fillEntityCell(xCurr, yStart, oldCell,
										"y");
							}
							newCS.setCell(xCurr, yStart, newCell);


							if (yStart + oldCell.getColSpan() - 1 > yMax) {
								yMax = yStart + oldCell.getColSpan() - 1;
							}
							// i= i+ newTmpCell.getRowSpan() -1;
						}

					}

				}

			}

		}

		newCS.setRowIndex(xStart);
		newCS.setRowExtMax(xMax);
		newCS.setColExtMax(yMax);
		newCS.setColIndex(yStart);
		if (oldCell != null) {
			oldCell.setExtField(new Field(newCS.getRowIndex(), newCS.getColIndex(),
					newCS.getRowIndex(), newCS.getColExtMax()));
		}

		return "OK";
	}

	/**
	 * 被复制单元格,对应的后面单元格给予按前一单元格填充.
	 * 
	 * @param row
	 * @param col
	 * @param extDirection
	 *            方向
	 */
	private void fillEntityCell(int row, int col,Cell cell , String extDirection) {
		
		if(cell != null){
		if (extDirection != null && extDirection.equals("y")) {
			
			for (int j = row; j < row + cell.getRowSpan(); j++) {
				for (int i = col + cell.getColSpan(); i < newCS.getColumn(); i++) {
					Cell fCell = newCS.getCell(j - 1, i);

					if (fCell != null) {
						Field field = fCell.getExtField();
						if (field != null) {
							Cell oldCell = oldCS.getCell(
									field.getStartRow() + 1, field
											.getStartColumn());
							if (oldCell != null) {
								Cell newCell = (Cell) oldCell.clone();
								newCell.setBlank(false);
								newCell.setContent("");
								newCell.setExtField(new Field(field.getStartRow() + 1, field
										.getStartColumn()));
								newCS.setCell(j, i, newCell);
								newCell.setBlank(true);
							}
						}
					}
				}
			}
		} else {
			for (int j = col; j < col +  cell.getColSpan(); j++) {
				for (int i = row + cell.getRowSpan(); i < newCS.getRow(); i++) {
					Cell fCell = newCS.getCell(i, j - 1);
					if (fCell != null) {
						Field field = fCell.getExtField();
						if (field != null) {
							Cell oldCell = oldCS.getCell(field.getStartRow(),
									field.getStartColumn() + 1);
							if (oldCell != null) {
								Cell newCell = (Cell) oldCell.clone();
								newCell.setBlank(false);
								newCell.setContent("");
								newCell.setExtField(new Field(field.getStartRow(),
										field.getStartColumn() + 1));
								newCS.setCell(i, j, newCell);
								newCell.setBlank(true);
							}
						}
					}
				}
			}
		}
	}
	}
	/**
	 * 取得表达式字符串
	 * 
	 * @return 表达式字符串
	 */
    public String getExp(){
    	return "";
    }
}
