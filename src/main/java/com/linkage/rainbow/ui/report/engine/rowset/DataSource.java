package com.linkage.rainbow.ui.report.engine.rowset;



import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据源配置信息类
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
public class DataSource {

	private Context ctx;
	
	private static Log log = LogFactory.getLog(DataSource.class);

	private javax.sql.DataSource ds;

	private String jndiName = null;// 数据源的JNDI名
	
	private boolean isDebug = true;

	public DataSource() {
		this.setJndiName("java:comp/env/jdbc/OracleDB");
		init();
	}
	

	public DataSource(String jndiName) {
		this.setJndiName(jndiName);
		init();
	}

	/**
	 * 初始化上下文,并取得数据源
	 *
	 */
	private void init(){
		try {
			if(jndiName != null && jndiName.trim().length()>0) {
			ctx = new InitialContext();
			// 获取连接池对象
			ds = (javax.sql.DataSource) ctx.lookup(jndiName);
			}

		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}
	
	

	
	public void setJndiName(String jndiName) {

		this.jndiName = jndiName;

	}

	public String getJndiName() {
		return this.jndiName;
	}

	private Connection getDebugConnection(){
		Connection conn = null;
//		String user = "place";
//		String password = "place222";
//		String url = "jdbc:oracle:thin:@134.140.33.21:1521:dwts";
		String user = "dxsq";
		String password = "tele";
		String url = "jdbc:oracle:thin:@localhost:1521:dxsq";
		try {

			Properties props = new Properties();
			props.put("user", user);
			props.put("password", password);
			Driver myDriver = (Driver) Class.forName(
					"oracle.jdbc.driver.OracleDriver").newInstance();
			conn = myDriver.connect(url, props);

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return conn;
	}
	public Connection getConnection() throws Exception {
		Connection connection = null;
		try {
			if(isDebug){
				connection =getDebugConnection();
			} else {
				connection = ds.getConnection();
			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
			
		}
		return connection;
	}

	/**
	 * 关闭一个Connection对象
	 * 
	 * @param con
	 * @throws Exception
	 * @return
	 */

	public static void close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}

	/**
	 * 关闭一个PreparedStatement对象
	 * 
	 * @param PreparedStatement
	 * @throws Exception
	 * @return
	 */
	public static void close(PreparedStatement pstat) {
		try {
			if (pstat != null) {
				pstat.close();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 关闭一个Statement对象
	 * 
	 * @param Statement
	 * @throws Exception
	 * @return
	 */
	public static void close(Statement stat) {
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 关闭一个ResultSet对象
	 * 
	 * @param ResultSet
	 * @throws Exception
	 * @return
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 */
	public static void close(Connection con, ResultSet rs, Statement stat,
			PreparedStatement pstat) {
		close(rs);
		close(stat);
		close(pstat);
		close(con);
	}
}
