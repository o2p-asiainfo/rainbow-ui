package com.linkage.rainbow.ui.report.engine.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.engine.util.Properties;
import com.linkage.rainbow.ui.report.engine.core.ReportEngine;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;
//import com.ibatis.sqlmap.engine.scope.RequestScope;

/**
 * 设置ibatis的一些公共的操作
 * 
 * @version 1.0
 * @author 陈亮 2009-06-1
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-06-1<br>
 *         修改内容:新建
 *         <hr>
 * @deprecated
 *
 */

public class IbatisUtil {

	/**
	 * SqlMapClients 存储多个sqlMapClient实例,支持多数据源的查询 getSqlMapInstance()
	 * 获取默认sqlMapClient,名称为default getSqlMapInstance(DBName)
	 * 根据名称取得对应的sqlMapClient
	 * 
	 */
	private static Map sqlMapClients = new HashMap();;

	private static Log log = LogFactory.getLog(IbatisUtil.class);
	Connection conn = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;

	static {
		try {
			init();
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new RuntimeException(
					"Error initializing ReportEngine class. Cause:" + e);
		}
	}

	public static void init() {
			try {
				// 取得报表引擎配置文件
				// String resource = "myreport/report-config.properties";
				// InputStream in = Resources.getResourceAsStream(resource);
				// Properties prop = new Properties();
				// prop.load(in);
				sqlMapClients = new HashMap<String, SqlMapClient>();
				Properties prop = ReportEngine.getProp();

				String ibatis_init_class_array[] = prop
						.getPropertyValues("ibatis_init_class");
				if (ibatis_init_class_array != null) {
					int iLength = ibatis_init_class_array.length;
					for (int i = 0; i < iLength; i++) {
						String ibatis_init_class = ibatis_init_class_array[i];
						if (ibatis_init_class != null) {
							ibatis_init_class = ibatis_init_class.trim();
							String dbSourceName = "default";
							int iIndex = ibatis_init_class.indexOf(":");
							if (iIndex > -1){
								dbSourceName = ibatis_init_class.substring(0,
										iIndex).toLowerCase();
								ibatis_init_class = ibatis_init_class.substring(iIndex+1);
							}

							String className = ibatis_init_class.substring(0,
									ibatis_init_class.lastIndexOf("."));
							String methodName = ibatis_init_class
									.substring(ibatis_init_class
											.lastIndexOf(".") + 1);
							String para[] = null;
							if (methodName.indexOf("(") > -1) {
								methodName = methodName.substring(0, methodName
										.indexOf("("));
								String paraStr = methodName
										.substring(methodName.indexOf("(") + 1);
								if (paraStr.indexOf("") > -1) {
									paraStr = paraStr.substring(0,
											paraStr.indexOf("")).trim();
									if (paraStr.length() > 0) {
										para = paraStr.split(",");
									}
								}
							}

							/** *************动态调用JAVABEAN start****************** */

//							System.out.println("CLASS:" + className
//									+ " METHOD:" + methodName);
							Object obj = Class.forName(className).newInstance();
							Class javaBeanClass = obj.getClass();
							Class parameterClassType[] = new Class[1];
							parameterClassType[0] = String[].class;
							parameterClassType = null;
							java.lang.reflect.Method m = javaBeanClass
									.getDeclaredMethod(methodName,
											parameterClassType);

							Object[] parameterObj = new Object[1];
							parameterObj[0] = para;
							parameterObj = null;
							sqlMapClients.put(dbSourceName, (SqlMapClient) m
									.invoke(obj, parameterObj));
							/** *************动态调用JAVABEAN end ****************** */
						}
					}
				} 
				String ibatis_init_xml_array[] = prop
						.getPropertyValues("ibatis_init_xml");
				if (ibatis_init_xml_array != null) {
					int iLength = ibatis_init_xml_array.length;
					for (int i = 0; i < iLength; i++) {
						String ibatis_init_xml = ibatis_init_xml_array[i];
						if (ibatis_init_xml != null) {
							ibatis_init_xml = ibatis_init_xml.trim();
							String dbSourceName = "default";
							int iIndex = ibatis_init_xml.indexOf(":");
							if (iIndex > -1){
								dbSourceName = ibatis_init_xml.substring(0,
										iIndex).toLowerCase();
								ibatis_init_xml = ibatis_init_xml.substring(iIndex+1);
							}
							
							Reader reader = Resources
									.getResourceAsReader(ibatis_init_xml);
							SqlMapClient sqlMap = SqlMapClientBuilder
									.buildSqlMapClient(reader);
							sqlMapClients.put(dbSourceName, sqlMap);
						}
					}
				}

			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
	} 

	public static SqlMapClient getSqlMapInstance() {

		return (SqlMapClient)sqlMapClients.get("default");
	}
	
	public static SqlMapClient getSqlMapInstance(String dbSourceName) {
		
		if(dbSourceName == null)
			return (SqlMapClient)sqlMapClients.get("default");
		else 
			return (SqlMapClient)sqlMapClients.get(dbSourceName);
	}

	public boolean updateInSession(String sql, String para) throws Exception {
		String dbSourceName = null;
		int iIndex = sql .indexOf(":");
		if(iIndex>-1){
			dbSourceName = sql.substring(0,iIndex);
			sql = sql.substring(iIndex+1);
		}
		return updateInSession(dbSourceName,sql,para);
	}
	/**
	 * 
	 * @param sql
	 * @param para
	 *            "1,2#~#"+acc_numbers+"#~#,\n$~$"
	 * @return
	 * @throws Exception
	 */
	public boolean updateInSession(String dbSourceName,String sql, String para) throws Exception {
		boolean isUpdate = false;
		if (conn == null)
			conn = getSqlMapInstance(dbSourceName).getDataSource().getConnection();
		pstmt = conn.prepareStatement(sql);

		String strParas[] = para.split("$~$");
		if (strParas != null) {
			for (int i = 0; i < strParas.length; i++) {
				String onePara[] = strParas[i].split("#~#");
				String fg[] = para.split(onePara[2]);
				for (int j = 1; j < fg.length; j++) {
					StringUtils.stringReplace(onePara[1], fg[j], fg[0]);
				}
				String acc_number[] = onePara[1].split(fg[0]);

				String idx[] = onePara[0].split(",");
				for (int j = 0; j < acc_number.length; j++) {
					for (int k = 1; k <= idx.length; k++) {
						pstmt.setString(k, acc_number[j]);
					}
					pstmt.executeUpdate();
				}
			}
		}

		return isUpdate;
	}

	/**
	 * 通过ibatis查询返回CachedRowSet
	 * 
	 * @param sqlMapId
	 * @param para
	 * @return
	 */
	public static CachedRowSet queryForRowSet(String sqlMapId, Object para) {
		String dbSourceName = null;
		int iIndex = sqlMapId .indexOf(":");
		if(iIndex>-1){
			dbSourceName = sqlMapId.substring(0,iIndex);
			sqlMapId = sqlMapId.substring(iIndex+1);
		}
		return queryForRowSet(dbSourceName, null,sqlMapId, para);
	}

	public CachedRowSet queryForRowSetInSession(String sqlMapId, Object para) {
		String dbSourceName = null;
		int iIndex = sqlMapId .indexOf(":");
		if(iIndex>-1){
			dbSourceName = sqlMapId.substring(0,iIndex);
			sqlMapId = sqlMapId.substring(iIndex+1);
		}
		return queryForRowSetInSession(dbSourceName,null, sqlMapId, para);
	}

	public CachedRowSet queryForRowSetInSession(CachedRowSet crs,String sqlMapId, Object para) {
		String dbSourceName = null;
		int iIndex = sqlMapId .indexOf(":");
		if(iIndex>-1){
			dbSourceName = sqlMapId.substring(0,iIndex);
			sqlMapId = sqlMapId.substring(iIndex+1);
		}
		return queryForRowSetInSession(dbSourceName,crs,sqlMapId,para);
	}
			
	/**
	 * 通过ibatis查询返回CachedRowSet
	 * 
	 * @param crs
	 * @param sqlMapId
	 * @param para
	 * @return
	 */
	public CachedRowSet queryForRowSetInSession(String dbSourceName,CachedRowSet crs,
			String sqlMapId, Object para) {

		if (crs == null)
			crs = new CachedRowSet();
			SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) getSqlMapInstance(dbSourceName)).getDelegate();
			// 这个类用来存放某个id名的Statement信息
			MappedStatement ms = delegate.getMappedStatement(sqlMapId);
			// sql类就是某一类型 Statement的对应sql拼装解析类
			Sql sql = ms.getSql();
			SessionScope sessionScope = new SessionScope();
			StatementScope statementScope = new StatementScope(sessionScope);
			statementScope.setStatement(ms);
			// 然后调用getSql方法,把参数值数组传进去
			String strsql = sql.getSql(statementScope, para);
//			System.out.println(strsql);
	
			 // 取得查询参数
			 ParameterMap paraMap = ms.getParameterMap();
			 // 如果为空取得动态语句的参数map
			 if (paraMap == null)
				 paraMap = statementScope.getDynamicParameterMap();
			 if (paraMap != null) {
				 paraMap.getParameterObjectValues(statementScope,para);
			 }
			 
		Object obj[] = null;
		String paraStr = "";
		if (obj != null) {
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] == null)
					throw new ReportException("SQL语句第" + (i + 1) + "个参数缺少付值:"
							+ strsql);
				paraStr += obj[i] + ",";
//				System.out.println("obj_" + i + "_" + obj[i].getClass() + ":"
//						+ obj[i]);
			}
		}

		try {
			if (conn == null)
				conn = getSqlMapInstance(dbSourceName).getDataSource().getConnection();
			pstmt = conn.prepareStatement(strsql);
			setPstmtPara(pstmt, obj);
			rs = pstmt.executeQuery();
			crs.populate(rs);
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new ReportException("SQL异常." + e.toString() + "\nSQL语句:"
					+ strsql + " \n参数:" + paraStr);
		} finally {
		}

		return crs;
	}
	
//	public CachedRowSet queryForRowSetInSession(String dbSourceName,CachedRowSet crs,
//			String sqlMapId, Object para) {
//
//		if (crs == null)
//			crs = new CachedRowSet();
//
//		ExtendedSqlMapClient extSqlMap = (ExtendedSqlMapClient) getSqlMapInstance(dbSourceName);
//
//		MappedStatement mStmt = extSqlMap.getMappedStatement(sqlMapId);
//
//		RequestScope requestScope = new RequestScope();
//		requestScope.setStatement(mStmt);
//
//		// 取得查询语名
//		String strsql = mStmt.getSql().getSql(requestScope, para);
//		System.out.println("strsql:" + strsql);
//
//		// 取得查询参数
//		ParameterMap paraMap = mStmt.getParameterMap();
//		// 如果为空取得动态语句的参数map
//		if (paraMap == null)
//			paraMap = requestScope.getDynamicParameterMap();
//
//		Object obj[] = null;
//		if (paraMap != null) {
//			obj = paraMap.getParameterObjectValues(requestScope, para);
//		}
//		String paraStr = "";
//		if (obj != null) {
//			for (int i = 0; i < obj.length; i++) {
//				if (obj[i] == null)
//					throw new ReportException("SQL语句第" + (i + 1) + "个参数缺少付值:"
//							+ strsql);
//				paraStr += obj[i] + ",";
//				System.out.println("obj_" + i + "_" + obj[i].getClass() + ":"
//						+ obj[i]);
//			}
//		}
//
//		try {
//			if (conn == null)
//				conn = getSqlMapInstance(dbSourceName).getDataSource().getConnection();
//			pstmt = conn.prepareStatement(strsql);
//			setPstmtPara(pstmt, obj);
//			rs = pstmt.executeQuery();
//			crs.populate(rs);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ReportException("SQL异常." + e.toString() + "\nSQL语句:"
//					+ strsql + " \n参数:" + paraStr);
//		} finally {
//		}
//
//		return crs;
//	}

	/**
	 * 通过ibatis查询返回CachedRowSet
	 * 
	 * @param crs
	 * @param sqlMapId
	 * @param para
	 * @return
	 */
	public static CachedRowSet queryForRowSet(CachedRowSet crs,
			String sqlMapId, Object para) {
		String dbSourceName = null;
		int iIndex = sqlMapId .indexOf(":");
		if(iIndex>-1){
			dbSourceName = sqlMapId.substring(0,iIndex);
			sqlMapId = sqlMapId.substring(iIndex+1);
		}
		return queryForRowSet(dbSourceName,crs,sqlMapId,para);
	}
	/**
	 * 通过ibatis查询返回CachedRowSet
	 * 
	 * @param crs
	 * @param sqlMapId
	 * @param para
	 * @return
	 */
	public static CachedRowSet queryForRowSet(String dbSourceName,CachedRowSet crs,
			String sqlMapId, Object para)  {

		if (crs == null)
			crs = new CachedRowSet();

		SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) getSqlMapInstance(dbSourceName)).getDelegate();
		// 这个类用来存放某个id名的Statement信息
		MappedStatement ms = delegate.getMappedStatement(sqlMapId);
		// sql类就是某一类型 Statement的对应sql拼装解析类
		Sql sql = ms.getSql();
		SessionScope sessionScope = new SessionScope();
		StatementScope statementScope = new StatementScope(sessionScope);
		statementScope.setStatement(ms);
		// 然后调用getSql方法,把参数值数组传进去
		String strsql = sql.getSql(statementScope, para);
//		System.out.println(strsql);

		 // 取得查询参数
		 ParameterMap paraMap = ms.getParameterMap();
		 // 如果为空取得动态语句的参数map
		 if (paraMap == null)
			 paraMap = statementScope.getDynamicParameterMap();
		 if (paraMap != null) {
			 paraMap.getParameterObjectValues(statementScope,para);
		 }
		 
		 Object obj[] = null;
		 
		String paraStr = "";
		if (obj != null) {
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] == null)
					throw new ReportException("SQL语句第" + (i + 1) + "个参数缺少付值:"
							+ strsql);
				paraStr += obj[i] + ",";
//				System.out.println("obj_" + i + "_" + obj[i].getClass() + ":"
//						+ obj[i]);
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getSqlMapInstance(dbSourceName).getDataSource().getConnection();
			pstmt = conn.prepareStatement(strsql);
			setPstmtPara(pstmt, obj);
			rs = pstmt.executeQuery();
			crs.populate(rs);
		} catch (Exception e) {
			//System.out.println("****************************");
			//e.printStackTrace();
			throw new ReportException("SQL异常." + e.toString() + "\nSQL语句:"
					+ strsql + " \n参数:" + paraStr);
		} finally {
			close(conn, pstmt, rs);
		}

		return crs;
	}

	/**
	 * 设置PreparedStatement各个参数
	 * 
	 * @param pstmt
	 * @param para
	 */
	public static void setPstmtPara(PreparedStatement pstmt, Object para[])
			throws Exception {
		if (para != null) {
			for (int i = 0; i < para.length; i++) {
				if (para[i] == null) {
					pstmt.setNull(i + 1, Types.NULL);
				} else {
					// boolean isOK = false;
					// if (para[i] instanceof String) {
					// String paramValue = (String) para[i];
					// boolean isOther = false;
					//						
					// //判断是否为Integer
					// try {
					// Integer integerPara = Integer.valueOf(paramValue);
					// isOther = true;
					// para[i] = integerPara;
					// } catch (Exception e) {
					// }
					// if(!isOther){
					// int iLength = paramValue.length();
					// //如果前后有单引号,则去除.
					// if(paramValue.charAt(0)=='\''&&paramValue.charAt(-1)=='\''){
					// paramValue = paramValue.substring(1,iLength-1);
					// }
					// pstmt.setString(i + 1, paramValue);
					// isOK = true;
					// }
					// }
					// if(!isOK){
					if (para[i] instanceof String) {
						String paramValue = (String) para[i];
						pstmt.setString(i + 1, paramValue);
					}
					if (para[i] instanceof Integer) {
						Integer paramValue = (Integer) para[i];
						pstmt.setInt(i + 1, paramValue.intValue());
					} else if (para[i] instanceof Long) {
						Long paramValue = (Long) para[i];
						pstmt.setLong(i + 1, paramValue.longValue());
					} else if (para[i] instanceof Float) {
						Float paramValue = (Float) para[i];
						pstmt.setFloat(i + 1, paramValue.floatValue());
					} else if (para[i] instanceof Double) {
						Double paramValue = (Double) para[i];
						pstmt.setDouble(i + 1, paramValue.doubleValue());
					} else if (para[i] instanceof BigInteger) {
						BigInteger paramValue = (BigInteger) para[i];
						pstmt.setLong(i + 1, paramValue.longValue());
					} else if (para[i] instanceof BigDecimal) {
						BigDecimal paramValue = (BigDecimal) para[i];
						pstmt.setBigDecimal(i + 1, paramValue);
					} else if (para[i] instanceof Byte) {
						Byte paramValue = (Byte) para[i];
						pstmt.setByte(i + 1, paramValue.byteValue());
					} else if (para[i] instanceof Short) {
						Short paramValue = (Short) para[i];
						pstmt.setShort(i + 1, paramValue.shortValue());
					} else if (para[i] instanceof Boolean) {
						Boolean paramValue = (Boolean) para[i];
						pstmt.setBoolean(i + 1, paramValue.booleanValue());
					} else if (para[i] instanceof Date) {
						Date paramValue = (Date) para[i];
						pstmt.setDate(i + 1, new java.sql.Date(paramValue
								.getTime()));
					} else {
						pstmt.setObject(i + 1, para[i]);
					}
				}
				// }
			}
		}
	}

	public static void close(Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (rs != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
		if (rs != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void close() {
		close(conn, pstmt, rs);
		rs = null;
		pstmt = null;
		conn = null;
	}

	public static void main(String[] args) {
		String str = "'dfdsfdsaa'";
		System.out.print(str.substring(1, str.length() - 1));
	}
}
