package com.linkage.rainbow.ui.report.engine.rowset;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.ClassInfo;

/**
 * 
 * 记录集类
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
public class CachedRowSet extends BaseRowSet implements Serializable, Cloneable {
	private ArrayList cachedRows;// 用于保存记录集,元素为BaseRow对象(里面包含每个字段的值)

	private int cursorPos = 0;// 当前指针位置

	private static Log log = LogFactory.getLog(CachedRowSet.class);
	// private int absolutePos;//绝对定位
	private int numRows;// 总记录数

	private RowSetMetaDataImpl RowSetMDI;// 字段集

	private boolean bHead;// 用于判断是否已经导出字段名 false:还没,true:已导出.



	private String strChar;// 设置哪几个字段的值之前要加(char)2,如:%#身分证#%%#电话号码#%

	private String strHeadHide;// 设置不显示字段;字段#字段;

	private String[][] objColumnName;
	
	
	private BaseRow blankRow = new BaseRow(null);
	boolean isArray = false;
	
	public CachedRowSet() {/**/
		cursorPos = 0;
		numRows = 0;
		RowSetMDI = new RowSetMetaDataImpl();
		cachedRows = new ArrayList();
		this.strHeadHide = "";
		bHead = false;
	}


	
	/**
	 * 指针定位
	 * 
	 * @param i
	 * @return
	 * @throws ReportException
	 */
	public boolean absolute(int i) {
		if (i <= 0 || i > numRows) {
			throw new ReportException("absolute: Invalid cursor operation.");
		}
		cursorPos = i;
		return true;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void close() {
		cursorPos = 0;
		numRows = 0;
		RowSetMDI = null;
		cachedRows = null;
	}

	public void clear() throws Exception {
		cursorPos = 0;
		numRows = 0;
		RowSetMDI = new RowSetMetaDataImpl();
		cachedRows = new ArrayList();
	}

	public void execute() {
		try {
			readData(this);
		} catch (ReportException e) {
			throw e;
		}
	}

	/**
	 * 把一个记录集转化成一个记录缓冲集
	 * 
	 * @param resultset
	 * @throws ReportException
	 */
	public void populate(ResultSet resultset)  throws SQLException{
		populate(resultset, null);
		// setMetaHeadCH(strHeadCH);
	}

	/**
	 * 一个记录集和一个字段字符串,每个字段用一个标签会开.如:setMetaHead("Id#Name#Age","#")
	 * 
	 * @param resultset
	 * @param strHeadCH
	 * @param strTag
	 * @throws ReportException
	 */
	public void populate(ResultSet resultset, String strHeadCH, String strTag)
	 throws SQLException{
		if (!StringUtils.isBlank(strHeadCH)||!StringUtils.isBlank(strTag)) {

			String[] strMetaHead = strHeadCH.split(strTag);
			this.populate(resultset, strMetaHead);
		} else {
			this.populate(resultset, null);
		}
	}

	/**
	 * 把一个记录集转化成一个记录缓冲集,strHeadCH字符串数组是存放中文字段名.
	 * 
	 * @param resultset
	 * @param String[]
	 *            strHeadCH
	 * @throws ReportException
	 */
	public void populate(ResultSet resultset, String[] strHeadCH)  throws SQLException{

		ResultSetMetaData RSMD = resultset.getMetaData();
		initMetaData(RowSetMDI, RSMD, strHeadCH);
		cachedRows = new ArrayList();

		cursorPos = 0;
		numRows = 0;
		int j = RSMD.getColumnCount();
		int i;

		for (i = 0; resultset.next(); i++) {
			cursorPos++;
			numRows ++;
			BaseRow row = new BaseRow(j);
			row.setIndex(cursorPos); //按1开始计
			row.setDs(this);
			isArray = true;
			for (int k = 1; k <= j; k++) {
				Object obj;
				obj = resultset.getObject(k);
				// System.out.print(obj+"\t");
				if (obj == null
						|| obj.toString().trim().equalsIgnoreCase("null")) {
					row.setColumnObject(k, null);
				} else {
					row.setColumnObject(k, obj);
				}
			}
			// System.out.println();
			cachedRows.add(row);
			
		}
		cursorPos = 0;
		numRows = i;
	}

	/**
	 * 一个记录集和一个字段字符串,每个字段用一个标签会开.如:setMetaHead("Id#Name#Age","#")
	 * 
	 * @param resultset
	 * @param strHeadCH
	 * @param strTag
	 * @throws ReportException
	 */
	public void populate(java.util.List list, String strHeadCH, String strTag){
		if (!StringUtils.isBlank(strHeadCH)&&!StringUtils.isBlank(strTag)) {

			String[] strMetaHead = strHeadCH.split(strTag);
			this.populate(list, strMetaHead);
		} else {
			this.populate(list, null);
		}
	}
	public void populate(java.util.List list){
		String[] head =null;
		if(list!=null && list.size()>0){
			Object row = list.get(0);
			if (row instanceof Map) {
				Map map = (Map)row;
				head = (String[]) map.keySet().toArray(new String[map.keySet().size()]);
	        } else {
	        	ClassInfo classCache = ClassInfo.getInstance(row.getClass());
	        	head = classCache.getReadablePropertyNames();
	        }
		}
		this.populate(list, head);
	}
	/**
	 * 把一个List转化成一个记录缓冲集,strHeadCH字符串数组是存放中文字段名.
	 * 
	 * @param resultset
	 * @param String[]
	 *            strHeadCH
	 * @throws ReportException
	 */
	public void populate(java.util.List list, String[] strHeadCH){

		if(strHeadCH != null)
			setMetaHead(strHeadCH);
		
		cachedRows = new ArrayList();
		cursorPos = 0;
		numRows = 0;
		
		int len = list.size();
		if(len>0){
			if(list.get(0) instanceof Object[]){
				isArray = true;
			}else if(list.get(0) instanceof List){
				isArray = true;
			} else{
				isArray = false;
			}
		}
		int i = 0;
		for (; i < len; i++) {
			cursorPos++;
			numRows ++;
			BaseRow row = new BaseRow(list.get(i));
			row.setDs(this);
			row.setIndex(cursorPos); //按1开始计
			cachedRows.add(row);
			
		}
		cursorPos = 0;
		numRows = i;	
	}

	/**
	 * 追加一条记录集
	 * 
	 * @param 一个记录集数组
	 *            Object[]
	 * @return
	 * @throws ReportException
	 */
	public BaseRow add(Object obj) {
		if (obj == null) {
			throw new ReportException("add(Object obj[]):追加出错:所要追加的记录不是有效.");
		}
//		BaseRow row = (BaseRow)blankRow.clone();
//		row.setOrigRow(obj);
		BaseRow row = new BaseRow(obj);
		cachedRows.add(row);
		numRows++;
		return row;
	}

	/**
	 * 两个一条记录值字符串,每个字段用一个标签会开.如:add("Id#Name#Age","#")
	 * 
	 * @param strVal
	 * @param strTag
	 * @throws ReportException
	 */
	public void add(String strVal, String strTag) {
		if (strVal == null || strVal.trim().length() == 0 || strTag == null
				|| strTag.length() == 0) {
			throw new ReportException("add(String,String):Invalid columns Values");
		}
		/*
		 * StringTokenizer strTo=new StringTokenizer(strVal,strTag); String[]
		 * strValues=new String[strTo.countTokens()]; int i=0;
		 * while(strTo.hasMoreTokens()){ strValues[i]=strTo.nextToken(); i++; }
		 */

		String[] strValues = strVal.split(strTag);
		add(strValues);
	}

	/* 追加一个缓冲记录集 */
	public void add(CachedRowSet rowset) {
		if (rowset != null) {
			while (rowset.next()) {
				add(rowset.getRowByBaseRow());
			}
		}
	}

	/**
	 * 追加一条记录集
	 * 
	 * @param 一条记录集BaseRow对象
	 * @return
	 * @throws ReportException
	 */
	public void add(BaseRow baserow) {
		if (baserow == null) {
			throw new ReportException("add(BaseRow baserow):追加出错:所要追加的记录不是有效.");
		}
		cachedRows.add(baserow);
		numRows++;
	}

	/**
	 * 追加一条记录集
	 * 
	 * @param 一条记录List对象.
	 * @return
	 * @throws ReportException
	 */
	public void add(List list) {
		if (list == null || list.size() == 0) {
			throw new ReportException("add(List list):追加出错:所要追加的记录不是有效.");
		}
		add(list.toArray());
	}

	/**
	 * 设置哪几个字段的值之前要加(char)2,如:%#身分证#%%#电话号码#%
	 * 
	 * @param strChar
	 */
	public void setCharacter(String strChar) {
		this.strChar = strChar;
	}

	public void setOjbColumnName(String[][] names) {
		objColumnName = names;
	}

	public String[][] getOjbColumnName() {
		return objColumnName;
	}


	public String toString() {
		StringBuffer strReturn = new StringBuffer();
		strReturn.append("\n英文字段---中文字段\n");
		try {
			int iLen = this.getColumnCount();
			for (int i = 1; i <= iLen; i++) {
				strReturn.append(this.getMetaHead(i) + "---"
						+ this.getMetaHeadCH(i) + "\n");
			}
			strReturn.append("隐藏字段:\n");
			strReturn.append(this.getHindHead());
			strReturn.append("char字段:\n");
			strReturn.append(strChar);
		} catch (ReportException ex) {
			log.error(ex.getStackTrace());
		}
		return strReturn.toString();
	}

	/**
	 * 下移一条记录
	 */
	public boolean next() {
		cursorPos++;
		if (cursorPos <= numRows) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上移一条记录
	 */
	public boolean previous() {
		if (cursorPos >= 1) {
			cursorPos--;
			return true;
		} else {
			return false;
		}
	}

	private Array _getArray(Object i) {
		checkCursor();
		Array array = (Array) this.getCurrentRow().getColumnObject(i);
		return array;
	}
	public Array getArray(int i) {
		return _getArray(getColIdxByName(i));
	}

	public Array getArray(String s) {
		return _getArray(getColIdxByName(s));
	}
	

	private BigDecimal _getBigDecimal(Object i) {
		this.checkCursor();
		Object obj = this.getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return null;
		}
		try {
			return new BigDecimal(obj.toString().trim());
		} catch (Exception e) {
			throw new ReportException("getDouble Failed on value ("
					+ obj.toString().trim() + ") in column " + i);
		}
	}

	public BigDecimal getBigDecimal(int i) {
		return _getBigDecimal(this.getColIdxByName(i));
	}
	public BigDecimal getBigDecimal(String s) {
		return _getBigDecimal(this.getColIdxByName(s));
	}

	private boolean _getBoolean(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return false;
		}
		if (obj instanceof Boolean)
			return ((Boolean) obj).booleanValue();
		try {
			Double double1 = new Double(obj.toString());
			return double1.compareTo(new Double(0.0D)) != 0;
		} catch (NumberFormatException _ex) {
			throw new ReportException("getBoolen Failed on value ("
					+ obj.toString().trim() + ") in column " + i);
		}
	}

	public boolean getBoolean(int i) {
		return _getBoolean(getColIdxByName(i));
	}
	public boolean getBoolean(String s) {
		return _getBoolean(getColIdxByName(s));
	}

	private byte _getByte(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return 0;
		}
		try {
			return (new Byte(obj.toString())).byteValue();
		} catch (NumberFormatException _ex) {
			throw new ReportException("getByte Failed on value (" + obj.toString()
					+ ") in column " + i);
		}
	}
	public byte getByte(int i) {
		return _getByte(getColIdxByName(i));
	}
	public byte getByte(String s) {
		return _getByte(getColIdxByName(s));
	}

	private byte[] _getBytes(Object i) {
		checkCursor();
		return (byte[]) getCurrentRow().getColumnObject(i);
	}

	public byte[] getBytes(int i) {
		return _getBytes(getColIdxByName(i));
	}
	public byte[] getBytes(String s) {
		return _getBytes(getColIdxByName(s));
	}

	private java.sql.Date _getDate(Object i, Calendar calendar) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return null;
		} else {
			obj = convertTemporal(obj, RowSetMDI.getColumnType((Integer)i), 91);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime((java.util.Date) obj);
			calendar.set(1, calendar1.get(1));
			calendar.set(2, calendar1.get(2));
			calendar.set(5, calendar1.get(5));
			return new java.sql.Date(calendar.getTime().getTime());
		}
	}

	
	private java.sql.Date _getDate(Object i) {
		this.checkCursor();
		Object obj = this.getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return null;
		} else {
			switch (RowSetMDI.getColumnType((Integer)i)) {
			case 91: // '['
				return (java.sql.Date) obj;

			case 93: // ']'
			case 0:
				try {
					long l = ((Timestamp) obj).getTime();
					return new java.sql.Date(l);
				} catch (Exception e) {
					throw new ReportException("getDate Failed on value ("
							+ obj.toString().trim() + ") in column " + i);
				}
			case -1:
			case 1: // '\001'
			case 12: // '\f'
				try {
					DateFormat dateformat = DateFormat.getDateInstance();
					return (java.sql.Date) dateformat.parse(obj.toString());
				} catch (ParseException _ex) {
					throw new ReportException("getDate Failed on value ("
							+ obj.toString().trim() + ") in column " + i);
				}
			}
			throw new ReportException("getDate Failed on value ("
					+ obj.toString().trim() + ") in column " + i
					+ " no conversion available");
		}
	}

	public java.sql.Date getDate(int i) {
		return _getDate(getColIdxByName(i));
	}
	public java.sql.Date getDate(int i, Calendar calendar) {
		return _getDate(getColIdxByName(i), calendar);
	}
	public java.sql.Date getDate(String s) {
		return _getDate(getColIdxByName(s));
	}

	public java.util.Date getDate(String s, Calendar calendar)
			{
		return _getDate(getColIdxByName(s), calendar);
	}

	private double _getDouble(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return 0.0D;
		}
		try {
			return (new Double(obj.toString().trim())).doubleValue();
		} catch (NumberFormatException _ex) {
			throw new ReportException("getDouble Failed on value ("
					+ obj.toString().trim() + ") in column " + i);
		}
	}

	public double getDouble(int i) {
		return _getDouble(getColIdxByName(i));
	}
	public double getDouble(String s) {
		return _getDouble(getColIdxByName(s));
	}

	private float _getFloat(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return 0.0F;
		}
		try {
			return (new Float(obj.toString())).floatValue();
		} catch (NumberFormatException _ex) {
			throw new ReportException("getfloat Failed on value ("
					+ obj.toString().trim() + ") in column " + i);
		}
	}

	public float getFloat(int i) {
		return _getFloat(getColIdxByName(i));
	}
	public float getFloat(String s) {
		return _getFloat(getColIdxByName(s));
	}

	public int _getInt(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return 0;
		}
		try {
			return (new Integer(obj.toString().trim())).intValue();
		} catch (NumberFormatException _ex) {
			throw new ReportException("getInt Failed on value (" + obj.toString()
					+ ") in column " + i);
		}
	}

	public int getInt(int i) {
		return _getInt(getColIdxByName(i));
	}
	public int getInt(String s) {
		return _getInt(getColIdxByName(s));
	}

	private long _getLong(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return 0L;
		}
		try {
			return (new Long(obj.toString().trim())).longValue();
		} catch (NumberFormatException _ex) {
			throw new ReportException("getLong Failed on value ("
					+ obj.toString().trim() + ") in column " + i);
		}
	}

	public long getLong(int i) {
		return _getLong(getColIdxByName(i));
	}
	public long getLong(String s) {
		return _getLong(getColIdxByName(s));
	}

	public ResultSetMetaData getMetaData() {
		return RowSetMDI;
	}

	private Object _getObject(Object i) {
		this.checkCursor();
		Object obj = this.getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return obj;
		} else {
			return obj;
		}
	}

	public Object getObject(int i) {
		return _getObject(this.getColIdxByName(i));
	}
	public Object getObject(String s) {
		return _getObject(this.getColIdxByName(s));
	}

	private short _getShort(Object i) {
		this.checkCursor();
		Object obj = this.getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return 0;
		}
		try {
			return (new Short(obj.toString().trim())).shortValue();
		} catch (Exception e) {
			throw new ReportException("getShort Failed on value ("
					+ obj.toString() + ") in column " + i);
		}
	}
	public short getShort(int i) {
		return _getShort(this.getColIdxByName(i));
	}
	public short getShort(String s) {
		return _getShort(this.getColIdxByName(s));
	}

	private Time _getTime(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return null;
		}
		switch (RowSetMDI.getColumnType((Integer)i)) {
		case 92: // '\\'
			return (Time) obj;

		case 93: // ']'
			long l = ((Timestamp) obj).getTime();
			return new Time(l);

		case -1:
		case 1: // '\001'
		case 12: // '\f'
			try {
				DateFormat dateformat = DateFormat.getTimeInstance();
				return (Time) dateformat.parse(obj.toString());
			} catch (ParseException _ex) {
				throw new ReportException("getTime Failed on value ("
						+ obj.toString().trim() + ") in column " + i);
			}
		}
		throw new ReportException("getTime Failed on value ("
				+ obj.toString().trim() + ") in column " + i
				+ "no conversion available");
	}

	private Time _getTime(Object i, Calendar calendar) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return null;
		} else {
			obj = convertTemporal(obj, RowSetMDI.getColumnType((Integer)i), 92);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime((java.util.Date) obj);
			calendar.set(11, calendar1.get(11));
			calendar.set(12, calendar1.get(12));
			calendar.set(13, calendar1.get(13));
			return new Time(calendar.getTime().getTime());
		}
	}

	public Time getTime(int i) {
		return _getTime(getColIdxByName(i));
	}
	public Time getTime(int i, Calendar calendar) {
		
		return _getTime(getColIdxByName(i), calendar);
	}
	public Time getTime(String s) {
		return _getTime(getColIdxByName(s));
	}

	public Time getTime(String s, Calendar calendar) {
		return _getTime(getColIdxByName(s), calendar);
	}

	/**
	 * 通过字段顺序号获取字段值
	 * 
	 * @param int
	 * @return String
	 */
	private String _getString(Object i) {
		checkCursor();
		Object obj = getCurrentRow().getColumnObject(i);
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}
	
	public String getString(int i) {
		return _getString(getColIdxByName(i));
	}
	/**
	 * 通过字段名获取字段值
	 * 
	 * @param String
	 *            字段名
	 * @return
	 */
	public String getString(String s) {
		return _getString(getColIdxByName(s));
	}

	/**
	 * 初值化字段集,保存字段信息
	 * 
	 * @param rsmdi
	 * @param rsmd
	 */
	private void initMetaData(RowSetMetaDataImpl rowMDI,
			ResultSetMetaData rsMD, String strCh[])  throws SQLException{
		int i = rsMD.getColumnCount();
		if (rowMDI.getColumnCount() == 0 || rowMDI.getColumnCount() != i) {
			rowMDI.setColumnCount(i);
		}
		if (strCh != null && strCh.length > 0) {
			int iLen = strCh.length;
			for (int ii = 1; ii <= iLen; ii++) {
				RowSetMDI.setColumnComment(ii, strCh[ii - 1]);
			}
		}
		if (rsMD != null) {
			for (int j = 1; j <= i; j++) {
				rowMDI.setAutoIncrement(j, rsMD.isAutoIncrement(j));
				rowMDI.setCaseSensitive(j, rsMD.isCaseSensitive(j));
				rowMDI.setCatalogName(j, rsMD.getCatalogName(j));
				rowMDI.setColumnDisplaySize(j, rsMD.getColumnDisplaySize(j));
				rowMDI.setColumnLabel(j, rsMD.getColumnLabel(j));
				rowMDI.setColumnName(j, rsMD.getColumnName(j));
				rowMDI.setColumnType(j, rsMD.getColumnType(j));
				rowMDI.setColumnTypeName(j, rsMD.getColumnTypeName(j));
				rowMDI.setCurrency(j, rsMD.isCurrency(j));
				rowMDI.setNullable(j, rsMD.isNullable(j));
				rowMDI.setPrecision(j, rsMD.getPrecision(j));
				rowMDI.setScale(j, rsMD.getScale(j));
				rowMDI.setSchemaName(j, rsMD.getSchemaName(j));
				rowMDI.setSearchable(j, rsMD.isSearchable(j));
				rowMDI.setSigned(j, rsMD.isSigned(j));
				rowMDI.setTableName(j, rsMD.getTableName(j));
			}
		}
	}

	/**
	 * 设置字段名,英文名称
	 * 
	 * @param strHead
	 *            字段名数组
	 */
	public void setMetaHead(String[] strHead) {
		if (strHead == null || strHead.length == 0) {
			throw new ReportException("setMetaHead:Invalid columns name");
		}
		int iLen = strHead.length;

		RowSetMDI.setColumnCount(iLen);
		for (int i = 1; i <= iLen; i++) {
			RowSetMDI.setColumnName(i, strHead[i - 1]);
		}
	}

	/**
	 * 设置字段注解
	 * 
	 * @param strHeadCH
	 */
	public void setMetaHeadCH(String[] strHeadCH) {
		if (strHeadCH == null || strHeadCH.length == 0) {
			throw new ReportException("setMetaHeadCH:Invalid columns Comment");
		}
		int iLen = strHeadCH.length;
		// 如果是导出数据,没以设置英文字段所以要初值化中文字段宽度.
		/*
		 * if(out!=null){ RowSetMDI.setColumnCount(iLen); }else
		 */if (iLen != RowSetMDI.getColumnCount()) {
			throw new ReportException("setMetaHeadCH:字段注解数量与字段名数量不一致");
		}
		for (int i = 1; i <= iLen; i++) {
			RowSetMDI.setColumnComment(i, strHeadCH[i - 1]);
		}
		bHead = false;
	}

	/**
	 * 设置字段名和字段注解
	 * 
	 * @param strHead
	 * @param strHeadCH
	 * @throws ReportException
	 */
	public void setMetaHead(String[] strHead, String[] strHeadCH)
			{
		if (strHead == null || strHead.length == 0) {
			throw new ReportException("setMetaHead:Invalid columns name");
		}
		if (strHeadCH == null || strHeadCH.length == 0) {
			throw new ReportException("setMetaHeadCH:Invalid columns Comment");
		}
		if (strHead.length != strHeadCH.length) {
			throw new ReportException("setMetaHeadCH:字段注解数量与字段名数量不一致");
		}
		int iLen = strHead.length;
		// RowSetMDI=new RowSetMetaDataImpl();
		// 初值化字段个数
		RowSetMDI.setColumnCount(iLen);
		for (int i = 1; i <= iLen; i++) {
			RowSetMDI.setColumnName(i, strHead[i - 1]);
		}
		for (int i = 1; i <= iLen; i++) {
			RowSetMDI.setColumnComment(i, strHeadCH[i - 1]);
		}
		bHead = false;
	}

	/**
	 * 两个字段字符串,每个字段用一个标签会开.如:setMetaHead("Id#Name#Age","编号#姓名#年龄","#")
	 * 
	 * @param strHead
	 * @param strHeadCH
	 * @param strTag
	 */
	public void setMetaHead(String strHead, String strHeadCH, String strTag)
			{
		if (strHead == null || strHead.trim().length() == 0
				|| strHeadCH == null || strHeadCH.trim().length() == 0
				|| strTag == null || strTag.trim().length() == 0) {
			throw new ReportException(
					"setMetaHead(String,String,String):Invalid columns name");
		}
		/*
		 * StringTokenizer strTo=new StringTokenizer(strHead,strTag); String[]
		 * strMetaHead=new String[strTo.countTokens()]; int i=0;
		 * while(strTo.hasMoreTokens()){ strMetaHead[i]=strTo.nextToken(); i++; }
		 * strTo=new StringTokenizer(strHeadCH,strTag); String[]
		 * strMetaHeadCH=new String[strTo.countTokens()]; i=0;
		 * while(strTo.hasMoreTokens()){ strMetaHeadCH[i]=strTo.nextToken();
		 * i++; }
		 */

		String[] strMetaHead = strHead.split(strTag);
		// StringWork strTo=new StringWork();
		String[] strMetaHeadCH = strHeadCH.split(strTag);
		setMetaHead(strMetaHead, strMetaHeadCH);

	}

	/**
	 * 两个字段字符串,每个字段用一个标签会开.如:setMetaHead("Id#Name#Age","#")
	 * 
	 * @param strHead
	 * @param strHeadCH
	 * @param strTag
	 */
	public void setMetaHead(String strHead, String strTag) {
		if (strHead == null || strHead.trim().length() == 0 || strTag == null
				|| strTag.trim().length() == 0) {
			throw new ReportException(
					"setMetaHead(String,String,String):Invalid columns name");
		}

		String[] strMetaHead = strHead.split(strTag);
		setMetaHead(strMetaHead);
	}

	/**
	 * 字段字符串,每个字段用一个标签会开.如:setMetaHead("Id#Name#Age","#")
	 * 
	 * @param strHead
	 * @param strHeadCH
	 * @param strTag
	 */
	public void setMetaHeadCH(String strHeadCH, String strTag)
			{
		if (strHeadCH == null || strHeadCH.trim().length() == 0
				|| strTag == null || strTag.trim().length() == 0) {
			throw new ReportException(
					"setMetaHead(String,String,String):Invalid columns name");
		}
		/*
		 * StringTokenizer strTo=new StringTokenizer(strHeadCH,strTag); String[]
		 * strMetaHead=new String[strTo.countTokens()]; int i=0;
		 * while(strTo.hasMoreTokens()){ strMetaHead[i]=strTo.nextToken(); i++; }
		 */

		String[] strMetaHead = strHeadCH.split(strTag);
		setMetaHeadCH(strMetaHead);
	}

	/**
	 * 设置一些字段在列表或导出EXCEL时不要显示
	 * 
	 * @param rsmd
	 * @throws ReportException
	 */
	public void setHideHead(String strHeadHide) {
		if (StringUtils.isEmpty(strHeadHide)) {
			return;
		}
		this.strHeadHide = strHeadHide;
	}

	/**
	 * 设置字段
	 * 
	 * @param rsmd
	 *            RowSetMetaDataImpl对象
	 * @throws ReportException
	 */
	public void setMetaHead(RowSetMetaDataImpl rsmd) {
		if (rsmd == null) {
			throw new ReportException("setMetaHead:Invalid columns name");
		}
		// RowSetMDI=new RowSetMetaDataImpl();
		RowSetMDI = rsmd;
		bHead = false;
	}

	/**
	 * 获取字段名,根据传进的字段下标值
	 * 
	 * @param i
	 * @return
	 */
	public String getMetaHead(int i) {
		return RowSetMDI.getColumnName(i);
	}

	/**
	 * 返回所有的英文字段名
	 * 
	 * @return
	 * @throws ReportException
	 */
	public String[] getMetaHead() {
		String[] strHead = new String[RowSetMDI.getColumnCount()];
		for (int i = 0; i < strHead.length; i++) {
			strHead[i] = RowSetMDI.getColumnName(i + 1);
		}
		return strHead;
	}

	/**
	 * 获取字段名注解,根据传进的字段下标值
	 * 
	 * @param i
	 * @return
	 */
	public String getMetaHeadCH(int i) {
		return RowSetMDI.getColumnComment(i);
	}

	/**
	 * 获取不显示字段
	 * 
	 * @return String
	 * @throws ReportException
	 */
	public String getHindHead() {
		return strHeadHide;
	}

	/**
	 * 返回所有的中文字段名
	 * 
	 * @return
	 * @throws ReportException
	 */
	public String[] getMetaHeadCH() {
		String[] strHeadCH = new String[RowSetMDI.getColumnCount()];
		for (int i = 0; i < strHeadCH.length; i++) {
			strHeadCH[i] = RowSetMDI.getColumnComment(i + 1);
		}
		return strHeadCH;
	}

	/**
	 * 返回总字段数.
	 * 
	 * @return int
	 * @throws ReportException
	 */
	public int getColumnCount() {
		return RowSetMDI.getColumnCount();
	}

	/**
	 * 返回当行值的哈希表
	 * 
	 * @return
	 * @throws ReportException
	 */
	public Hashtable getRowByHash() {
		Hashtable rowHash = new Hashtable();
		int iLen = this.getColumnCount();
		if (!(this.isAfterLast() && this.isBeforeFirst())) {
			for (int i = 1; i <= iLen; i++) {
				rowHash.put(getMetaHead(i), getObject(i));
			}
		} else {
			for (int i = 1; i <= iLen; i++) {
				rowHash.put(getMetaHead(i), "");
			}
		}
		return rowHash;
	}

	/* 返回当前行 */
	public BaseRow getRowByBaseRow() {
		return this.getCurrentRow();
	}

	public Object getRowByObject() {
		return getCurrentRow().getOrigRow();
	}

	/**
	 * 返回记录行数
	 * 
	 * @return int
	 */
	public int getRow() {
		if (numRows > 0) {
			return numRows;
		} else {
			return 0;
		}
	}

	/**
	 * 返回当前记录行
	 * 
	 * @return
	 */
	public BaseRow getCurrentRow() {
		checkCursor();
		return (BaseRow) cachedRows.get(cursorPos - 1);
	}

	/**
	 * 返回指定字段名称在字段集里的位置
	 * 
	 * @param s
	 * @return
	 * @throws ReportException
	 */
	private Object getColIdxByName(Object s) {
		if(s instanceof String){
			if(isArray){
				int i = RowSetMDI.getColumnCount();
				for (int j = 1; j <= i; j++) {
					String s1 = RowSetMDI.getColumnName(j);
					String s2 = RowSetMDI.getColumnComment(j);
					if (s1 != null
							&& (s.toString().equalsIgnoreCase(s1) || s.toString().equalsIgnoreCase(s2)))
						return j;
				}
				throw new ReportException("找不到字段名为:" + s);
				
			} else {
				return s;
			}
		}else{
			if(isArray){
				return s;
			} else {
				return RowSetMDI.getColumnName((Integer)s);
			}
		}
	}

	public boolean havColName(String s) {
		int i = RowSetMDI.getColumnCount();
		for (int j = 1; j <= i; j++) {
			String s1 = RowSetMDI.getColumnName(j);
			String s2 = RowSetMDI.getColumnComment(j);
			if (s1 != null
					&& (s.equalsIgnoreCase(s1) || s.equalsIgnoreCase(s2)))
				return true;
		}
		return false;
		// throw new ReportException("找不到字段名为:"+s);
	}

	// 判断类型
	private boolean isBinary(int i) {
		switch (i) {
		case -4:
		case -3:
		case -2:
			return true;
		}
		return false;
	}

	private Object convertTemporal(Object obj, int i, int j)
			{
		if (i == j) {
			return obj;
		}
		if (isNumeric(j) || !isString(j) && !isTemporal(j)) {
			throw new ReportException("Datatype Mismatch");
		}
		try {
			switch (j) {
			case 91: // '['
				if (i == 93)
					return new java.util.Date(((Timestamp) obj).getTime());
				else
					throw new ReportException("Data Type Mismatch");

			case 93: // ']'
				if (i == 92) {
					return new Timestamp(((Time) obj).getTime());
				} else {
					return new Timestamp(((java.util.Date) obj).getTime());
				}
			case 92: // '\\'
				if (i == 93) {
					return new Time(((Timestamp) obj).getTime());
				} else {
					throw new ReportException("Data Type Mismatch");
				}

			case -1:
			case 1: // '\001'
			case 12: // '\f'
				return new String(obj.toString());
			}
			throw new ReportException("Data Type Mismatch");
		} catch (NumberFormatException _ex) {
			throw new ReportException("Data Type Mismatch");
		}
	}

	private boolean isNumeric(int i) {
		switch (i) {
		case -7:
		case -6:
		case -5:
		case 2: // '\002'
		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
			return true;

		case -4:
		case -3:
		case -2:
		case -1:
		case 0: // '\0'
		case 1: // '\001'
		default:
			return false;
		}
	}

	private boolean isString(int i) {
		switch (i) {
		case -1:
		case 1: // '\001'
		case 12: // '\f'
			return true;
		}
		return false;
	}

	private boolean isTemporal(int i) {
		switch (i) {
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
			return true;
		}
		return false;
	}

	/**
	 * 判断字段集下标是否超出范围
	 */
	private void checkIndex(int i) {
		if (i < 1 || i > RowSetMDI.getColumnCount())
			throw new ReportException("Invalid column index");
		else
			return;
	}

	/**
	 * 判断当前指针是否超出范围
	 * 
	 * @throws ReportException
	 */
	private void checkCursor() {
		if (isAfterLast() || isBeforeFirst()) {
			throw new ReportException("Invalid cursor position");
		} else {
			return;
		}
	}

	/**
	 * 判断当前指针是否在最后一条记录之后.
	 * 
	 * @return
	 * @throws ReportException
	 */
	public boolean isAfterLast() {
		if ((cursorPos == 0 || cursorPos == 1) && numRows == 0) {
			return true;
		}
		return ((cursorPos > numRows) && (numRows > 0));
	}

	/**
	 * 判断当前指针是否在第一条记录之前.
	 * 
	 * @return
	 * @throws ReportException
	 */
	public boolean isBeforeFirst() {
		if ((cursorPos == 0 || cursorPos == 1) && numRows == 0) {
			return true;
		}
		return ((cursorPos <= 0) && (numRows > 0));
	}

	/**
	 * 判断当前指针是否在第一条记录.
	 * 
	 * @return
	 * @throws ReportException
	 */
	public boolean isFirst() {
		if (cursorPos == 1 && numRows > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断当前指针是否在最后一条记录.
	 * 
	 * @return
	 * @throws ReportException
	 */
	public boolean isLast() {
		if (cursorPos == numRows) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将当前指针移到第一条记录前面
	 * 
	 * @return
	 * @throws ReportException
	 */
	public void beforeFirst() {
		cursorPos = 0;
	}

	/**
	 * 将当前指针移到第一条记录.
	 */
	public boolean first() {
		if (numRows <= 0) {
			throw new ReportException("First: Invalid cursor operation.");
		} else if (numRows > 0) {
			cursorPos = 1;
			return true;
		} else {
			return false;
		}
	}

	public void afterLast() {
		numRows = numRows + 1;
	}

	/**
	 * 将当前指针移到最后一条记录.
	 */
	public boolean last() {
		if (numRows <= 0) {
			throw new ReportException("Last: Invalid cursor operation.");
		} else if (numRows > 0) {
			cursorPos = numRows;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 直接跳转到第几条
	 * @param i
	 * @return
	 */
	public boolean goTo(int i){
		if(i<=0 || i>numRows){
			return false;
		}
		cursorPos = i;
		return true;
	}
	/**
	 * 返回当前记录行位置
	 * 
	 * @return
	 */
	public int getCurrentRowIdx() {
		return cursorPos;
	}
	// public void setGroupCol(String strGroupCol) {
	// String groupCol[] = strGroupCol.split(",");
	// if (groupCol != null) {
	// if (cachedRows != null) {
	// DBGroup group = new DBGroup();
	// group.setValue("0");
	// group.setGroupCol(groupCol);
	// ArrayList[] subGroups = new ArrayList[groupCol.length];
	// this.setRootGroup(group);
	//
	// for (int i = 0; i < groupCol.length; i++) {
	// for (int j = 0; j < numRows; j++) {
	//
	// }
	//
	// }
	// }
	// }
	//
	// }

	public static void main(String[] args) {
		CachedRowSet crs = new CachedRowSet();
		try {
			String strDsSQL = "sql=\"select a.day_id,a.area_name,b.f_name,b.prod_name,sum(a.counts) counts from QZ_YWFZ a,qz_dim_prod b,qz_area c where a.prod_id=b.prod_id and a.area_name=c.area_name and c.area_type=2 and b.f_id<>3 and day_id>20060320 group by a.day_id,a.area_name, b.f_name, b.prod_name\",type=normal,param=\"\",beginrow=null,endrow=null,dsn=mydb,cached=0,group=\"f_name,prod_name#day_id,area_name\", ";

			crs.setCommandPara(strDsSQL);
			crs.execute();

			// crs.setCommand("select b.EXAM_TYPE_NAME,a.KPI_TYPE,a.CYCLE_TYPE,
			// a.kpi_code,a.kpi_type_name from exam_kpi_def a,exam_type_def b
			// where a.exam_type=b.exam_type");
			//			
			// crs.setGroupCols("EXAM_TYPE_NAME,KPI_TYPE,CYCLE_TYPE");
			// crs.execute();

			// while (crs.next()) {
			// System.out.println(crs.getString(1)+"\t"+crs.getString(2)+"\t"+crs.getString(3));
			// }
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 将List转换为RowSet List中一条记录的采用Map保存
	 * 
	 * @param list
	 */
	public void list2cRowSet(List list) {
//		System.out.println("把LIST 转换为 CachedRowSet");

		// 取得记录列表
		Iterator it = list.iterator();

		String name = "", strTmp = "";

		int n = 0, len = 0; // K 用来记录SERV_ID 在数组 arr的位置

		String[] arrCol = null;
		try {
		// 对记录列表进行循环
		while (it.hasNext()) {
			// 取得一条记录
			java.util.HashMap row = (java.util.HashMap) it.next();
			BaseRow baseRow = new BaseRow(row.size());

			

				// 处理CRS 的 列名 begin
				if (n <= 0) {
					arrCol = new String[row.size()];
					len = 0;
					Iterator ite = row.keySet().iterator();

					while (ite.hasNext()) {
						name = (String) ite.next();
						arrCol[len] = name;
						len++;
					}
					this.setMetaHead(arrCol);
				}
				n++;
				// 处理CRS 的 列名 end;

				// 把值放到数组里面去
				for (int i = 0; i < len; i++) {
					Object obj = row.get(arrCol[i]);

					if (obj == null
							|| obj.toString().trim().equalsIgnoreCase("null")) {
//						System.out.println("nul");
						baseRow.setColumnObject(i+1, null);
					} else {
						baseRow.setColumnObject(i+1, obj);
					}
				}
				if (arrCol != null) {
					this.add(baseRow);
				}
	


		}
		
		} catch (Exception ex) {
			log.error(ex.getStackTrace());
		}

	}
	//根据分组字段名,与分组值,取得分组对象.
	public DBGroup getDBGroupByValue(String groupName,Object value){
		List list = getDBGroup(groupName,null,null);//根据分组字段名,取得分组对象列表
		DBGroup group = null;
		int iSize = list.size();
		for(int i=0;i<iSize;i++){//对分组对象列表循环,按分组值查找匹配的分组对象
			DBGroup groupTemp =(DBGroup)list.get(i);
			if(value.equals(groupTemp.getValue())) {
				group = groupTemp ;
				break;
			}
		}
		return group;
	}
	
	public List getDBGroup(String groupName,String showGroupName,String[] drillExpSubPara){
		List groupList = groupsMap.get("groupName");
		if(groupList == null){
			try {
				groupList = new ArrayList();
				Map<Object,DBGroup> groupMap = new HashMap();
				for(int i=0;i<numRows;i++){
					//取得记录集每条记录
					BaseRow row = (BaseRow)cachedRows.get(i);
					Object value = row.getColumnObject(groupName);//记录分组值
					if(value ==null){
						value="";
					}
					DBGroup group = groupMap.get(value);
					if(group == null){
						group = new DBGroup(this,groupName,showGroupName,drillExpSubPara);
						group.setValue(value);
						group.setDsCurrIdx(row.getIndex());
						row.addGroup(group);
						groupList.add(group);
						groupMap.put(value,group);
					}
					group.addRow(row);
					
				}
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
		}
		return groupList;
			
	}
	private Map<Object,List> groupsMap = new HashMap();

	public ArrayList getCachedRows() {
		return cachedRows;
	}

	public void setCachedRows(ArrayList cachedRows) {
		this.cachedRows = cachedRows;
	}

}
