package com.linkage.rainbow.ui.report.engine.rowset;

import java.io.Serializable;
import java.sql.SQLException;

import javax.sql.RowSetMetaData;

import com.linkage.rainbow.ui.report.engine.model.ReportException;

/**
 * 
 * 字段集信息,扩展类javax.sql.RowSetMetaData没有序列化
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
public class RowSetMetaDataImpl implements Serializable, RowSetMetaData {
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	static final Long serialVersionUID =1234323412341231L;
	
	public RowSetMetaDataImpl() {
	}

	public RowSetMetaDataImpl(int iColumnCount) {
		this.setColumnCount(iColumnCount);
	}

	private void checkColRange(int i) {
		if (i <= 0 || i > colCount)
			throw new ReportException("Invalid column index");
		else
			return;
	}

	public String getCatalogName(int i) {
		checkColRange(i);
		return colInfo[i].catName;
	}

	public String getColumnClassName(int i) {
		return null;
	}

	public int getColumnCount() {
		return colCount;
	}

	public int getColumnDisplaySize(int i) {
		checkColRange(i);
		return colInfo[i].columnDisplaySize;
	}

	public String getColumnLabel(int i) {
		checkColRange(i);
		return colInfo[i].columnLabel;
	}

	public String getColumnName(int i) {
		checkColRange(i);
		return colInfo[i].columnName;
	}

	public String getColumnComment(int i) {
		checkColRange(i);
		return colInfo[i].columnComment;
	}

	public int getColumnType(int i) {
		checkColRange(i);
		return colInfo[i].colType;
	}

	public String getColumnTypeName(int i) {
		checkColRange(i);
		return colInfo[i].colTypeName;
	}

	public int getPrecision(int i) {
		checkColRange(i);
		return colInfo[i].colPrecision;
	}

	public int getScale(int i) {
		checkColRange(i);
		return colInfo[i].colScale;
	}

	public String getSchemaName(int i) {
		checkColRange(i);
		return colInfo[i].schemaName;
	}

	public String getTableName(int i) {
		checkColRange(i);
		return colInfo[i].tableName;
	}

	public boolean isAutoIncrement(int i) {
		checkColRange(i);
		return colInfo[i].autoIncrement;
	}

	public boolean isCaseSensitive(int i) {
		checkColRange(i);
		return colInfo[i].caseSensitive;
	}

	public boolean isCurrency(int i) {
		checkColRange(i);
		return colInfo[i].currency;
	}

	public boolean isDefinitelyWritable(int i) {
		return true;
	}

	public int isNullable(int i) {
		checkColRange(i);
		return colInfo[i].nullable;
	}

	public boolean isReadOnly(int i) {
		checkColRange(i);
		return true;
	}

	public boolean isSearchable(int i) {
		checkColRange(i);
		return colInfo[i].searchable;
	}

	public boolean isSigned(int i) {
		checkColRange(i);
		return colInfo[i].signed;
	}

	public boolean isWritable(int i) {
		checkColRange(i);
		return false;
	}

	public void setAutoIncrement(int i, boolean flag) {
		checkColRange(i);
		colInfo[i].autoIncrement = flag;
	}

	public void setCaseSensitive(int i, boolean flag) {
		checkColRange(i);
		colInfo[i].caseSensitive = flag;
	}

	public void setCatalogName(int i, String s) {
		checkColRange(i);
		colInfo[i].catName = new String(s);
	}

	public void setColumnCount(int i) {
		if (i <= 0)
			throw new ReportException("Invalid column count");
		colCount = i;
		colInfo = new ColInfo[colCount + 1];
		for (int j = 1; j <= colCount; j++)
			colInfo[j] = new ColInfo();
	}

	public void setColumnDisplaySize(int i, int j) {
		checkColRange(i);
		colInfo[i].columnDisplaySize = j;
	}

	public void setColumnLabel(int i, String s) {
		checkColRange(i);
		colInfo[i].columnLabel = new String(s);
	}

	public void setColumnName(int i, String s) {
		checkColRange(i);
		colInfo[i].columnName = new String(s);
	}

	public void setColumnComment(int i, String s) {
		checkColRange(i);
		colInfo[i].columnComment = new String(s);
	}

	public void setColumnType(int i, int j) {
		checkColRange(i);
		colInfo[i].colType = j;
	}

	public void setColumnTypeName(int i, String s) {
		checkColRange(i);
		colInfo[i].colTypeName = new String(s);
	}

	public void setCurrency(int i, boolean flag) {
		checkColRange(i);
		colInfo[i].currency = flag;
	}

	public void setNullable(int i, int j) {
		checkColRange(i);
		colInfo[i].nullable = j;
	}

	public void setPrecision(int i, int j) {
		checkColRange(i);
		colInfo[i].colPrecision = j;
	}

	public void setScale(int i, int j) {
		checkColRange(i);
		colInfo[i].colScale = j;
	}

	public void setSchemaName(int i, String s) {
		checkColRange(i);
		colInfo[i].schemaName = new String(s);
	}

	public void setSearchable(int i, boolean flag) {
		checkColRange(i);
		colInfo[i].searchable = flag;
	}

	public void setSigned(int i, boolean flag) {
		checkColRange(i);
		colInfo[i].signed = flag;
	}

	public void setTableName(int i, String s) {
		checkColRange(i);
		if (s != null)
			colInfo[i].tableName = new String(s);
		else
			colInfo[i].tableName = new String("");
	}

	private int colCount;

	private ColInfo colInfo[];
}