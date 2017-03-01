package com.linkage.rainbow.ui.report.engine.rowset;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.engine.core.FuncParse;
import com.linkage.rainbow.ui.report.engine.model.ReportException;

/**
 * 
 * 记录集抽象类
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
public abstract class BaseRowSet implements Serializable, Cloneable {
	
	
	protected String command;
	
	private static Log log = LogFactory.getLog(BaseRowSet.class);

	protected ArrayList params;

	protected int writerCalls;

	protected boolean userCon;

	//数据源JNDI名
	protected String dsn;
	//记录集名称
	protected String dsName;

	public BaseRowSet() {
		params = new ArrayList();
	}

	private void checkParamIndex(int i) {
		if (i < 1) {
			throw new ReportException("Invalid Parameter Index");
		} else {
			return;
		}
	}

	public void clearParameters() {
		params.clear();
	}

	public String getCommand() {
		return command;
	}

	public Object[] getParams() {
		return params.toArray();
	}

	public void setArray(int i, Array array) {
		checkParamIndex(i);
		params.add(i - 1, array);
	}

	public void setByte(int i, byte byte0) {
		checkParamIndex(i);
		params.add(i - 1, new Byte(byte0));
	}

	public void setBytes(int i, byte abyte0[]) {
		checkParamIndex(i);
		params.add(i - 1, abyte0);
	}

	public void setBoolean(int i, boolean b) {
		checkParamIndex(i);
		params.add(i, new Boolean(b));
	}

	public void setCommand(String s) {
		command = new String(s);
		params.clear();
	}

	public void setDate(int i, java.sql.Date date) {
		checkParamIndex(i);
		params.add(i - 1, date);
	}

	public void setDate(int i, java.sql.Date date, Calendar calendar)
			{
		checkParamIndex(i);
		Object aobj[] = new Object[2];
		aobj[0] = date;
		aobj[1] = calendar;
		params.add(i - 1, ((Object) (aobj)));
	}

	/*
	 * public void setDate(int i, java.util.Date date, Calendar calendar)throws
	 * ReportException{ checkParamIndex(i); Object aobj[] = new Object[2]; aobj[0] =
	 * date; aobj[1] = calendar; params.add(i - 1, ((Object) (aobj))); }
	 */
	public void setDouble(int i, double d) {
		checkParamIndex(i);
		params.add(i - 1, new Double(d));
	}

	public void setFloat(int i, float f) {
		checkParamIndex(i);
		params.add(i - 1, new Float(f));
	}

	public void setInt(int i, int j) {
		checkParamIndex(i);
		params.add(i - 1, new Integer(j));
	}

	public void setLong(int i, long l) {
		checkParamIndex(i);
		params.add(i - 1, new Long(l));
	}

	public void setNull(int i, int j, String s) {
		checkParamIndex(i);
		Object aobj[] = new Object[3];
		aobj[0] = null;
		aobj[1] = new Integer(j);
		aobj[2] = new String(s);
		params.add(i - 1, ((Object) (aobj)));
	}

	public void setObject(int i, Object obj) {
		checkParamIndex(i);
		params.add(i - 1, obj);
	}

	public void setObject(int i, Object obj, int j) {
		checkParamIndex(i);
		Object aobj[] = new Object[2];
		aobj[0] = obj;
		aobj[1] = new Integer(j);
		params.add(i - 1, ((Object) (aobj)));
	}

	public void setObject(int i, Object obj, int j, int k) {
		checkParamIndex(i);
		Object aobj[] = new Object[3];
		aobj[0] = obj;
		aobj[1] = new Integer(j);
		aobj[2] = new Integer(k);
		params.add(i - 1, ((Object) (aobj)));
	}

	public void setShort(int i, short word0) {
		checkParamIndex(i);
		params.add(i - 1, new Short(word0));
	}

	public void setString(int i, String s) {
		checkParamIndex(i);
		params.add(i - 1, s);
	}

	public void setTime(int i, Time time) {
		checkParamIndex(i);
		params.add(i - 1, time);
	}

	public void setTime(int i, Time time, Calendar calendar)
			{
		checkParamIndex(i);
		Object aobj[] = new Object[2];
		aobj[0] = time;
		aobj[1] = calendar;
		params.add(i - 1, ((Object) (aobj)));
	}

	/**
	 * 分解参数0
	 * 
	 * @para obj[]参数数组
	 * @para pstat setArray(int i, Array array) setByte(int i, byte byte0)
	 *       setBytes(int i, byte abyte0[]) setCommand(String s) setDate(int i,
	 *       date) setDouble(int i, double d) setFloat(int i, float f)
	 *       setInt(int i, int j) setLong(int i, long l) setNull(int i, int j,
	 *       String s) setObject(int i, Object obj) setObject(int i, Object obj,
	 *       int j) setObject(int i, Object obj, int j, int k) setShort(int i,
	 *       short word0) setString(int i, String s) setTime(int i, Time time)
	 *       setTime(int i, Time time, Calendar calendar)
	 */
	protected void decodeParams(Object obj[], PreparedStatement pstat) throws SQLException
			{
		for (int i = 0; i < obj.length; i++) {
			int iLen;
			try {
				// 如果传进来的参数是一个数组,获取数组下标
				iLen = Array.getLength(obj[i]);
			} catch (IllegalArgumentException _ex) {
				pstat.setObject(i + 1, obj[i]);
				continue;
			}
			Object objTemp[] = (Object[]) obj[i];
			if (iLen == 2) {
				if (objTemp[0] == null) {
					pstat.setNull(iLen,
							Integer.parseInt(objTemp[1].toString()), String
									.valueOf(objTemp[2]));
				} else if ((objTemp[0] instanceof java.util.Date)
						|| (objTemp[0] instanceof Time)
						|| (objTemp[0] instanceof Timestamp)) {
					System.err.println("Detected a Date");
					if (objTemp[1] instanceof Calendar) {
						System.err.println("Detected a Calendar");
						pstat.setDate(i + 1, (java.sql.Date) objTemp[0],
								(Calendar) objTemp[1]);
					} else {
						throw new ReportException("Unable to deduce param type");
					}
				}

			} else if (iLen == 3) {
				pstat.setObject(i + 1, objTemp[0], Integer.parseInt(objTemp[1]
						.toString()), Integer.parseInt(objTemp[2].toString()));
			}
		}
	}

	public void readData(CachedRowSet cachedrowset) {
		com.linkage.rainbow.ui.report.engine.rowset.DataSource dataCon = null;
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		try {
			
			writerCalls = 0;
			userCon = false;
			if (dataCon == null) {
				dataCon = new com.linkage.rainbow.ui.report.engine.rowset.DataSource(dsn);
			}
			connection = dataCon.getConnection();
			//System.out.println(cachedrowset.getCommand());
			if (connection == null || cachedrowset.getCommand() == null) {
				throw new ReportException();
			}
			preparedstatement = connection
					.prepareStatement(cachedrowset.getCommand());
			decodeParams(cachedrowset.getParams(), preparedstatement);
			resultset = preparedstatement.executeQuery();
			cachedrowset.populate(resultset);
			resultset.close();
			preparedstatement.close();
			
		} catch (ReportException ReportException) {
			throw ReportException;
		} catch (Exception e) {
			// Log.warn(BaseRowSet.class,"异常:",e);
			log.error(e.getStackTrace());
		}finally{
			DataSource.close(connection,resultset,null,preparedstatement);
		}
	}

	public boolean reset() {
		writerCalls++;
		return writerCalls == 1;
	}

	public void setCommandPara(String s) {
		Map map = parseCommand(s);
		if (map.get("sql") != null && map.get("sql").toString().trim().length()>0) {
			setCommand(map.get("sql").toString().trim());
		}

		if (map.get("dsn") != null&&map.get("dsn").toString().trim().length()>0) {
			this.dsn = ((String) map.get("dsn")).trim();
		}
	}

	private Map parseCommand(String s) {
		Map map = new HashMap();
		s = trim(s);
		int iIndex = s.indexOf("=\"");
		while (iIndex > -1) {
			String str1 = s.substring(0, iIndex);
			String str2 = s.substring(iIndex + 2);
			String pname = null;
			String pvalue = null;
			int i = str1.lastIndexOf(",");
			if (i > -1) {
				pname = str1.substring(i + 1);
				str1 = str1.substring(0, i);
			} else {
				pname = str1;
				str1 = "";
			}
			i = str2.indexOf("\"");
			if (i > -1) {
				pvalue = str2.substring(0, i);
				str2 = str2.substring(i + 1);
			} else {
				throw new ReportException("非法的表达式");
			}
			map.put(pname.trim(), pvalue.trim());
			s = str1 + str2;
			iIndex = s.indexOf("=\"");
		}
		//System.out.println(s);
		String str[] = s.split(",");
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (str[i] != null && str[i].trim().length() > 0) {
					String str2[] = str[i].split("=");
					if (str2.length == 2) {
						map.put(str2[0].trim(), str2[1].trim());
					}
				}
			}
		}
		return map;
	}

	private String trim(String s) {
		String newstr = "";
		if (s != null) {
			int i = s.indexOf("=");
			int j = s.indexOf("\"");
			while (i > -1 && j > -1 && i < j) {
				String tmp = s.substring(i + 1, j);
				if (tmp == null && tmp.trim().length() == 0) {
					newstr = s.substring(0, i + 1);
					s = s.substring(j);
				} else {
					newstr = s.substring(0, j);
					s = s.substring(j);
				}
				i = s.indexOf("=");
				j = s.indexOf("\"");
			}
		}
		newstr += s;
		return newstr;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

}