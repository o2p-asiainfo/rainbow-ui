package template.simple.page.simple;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库调用类
 * <p>
 * @version 1.0
 * @author  2010-1-4
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 修改时间:2010-1-4<br>
 * 修改内容:新建
 * <hr>
 */
public class CommonDAO {
	
	private Connection con = null;
	
	private static Log log = LogFactory.getLog(CommonDAO.class);
	public CommonDAO(Connection con){
		this.con = con;
	}
	
	/**
	 * 根据查询sql 获取结果集
	 * @param SQL
	 * @return ResultSet
	 * @throws Exception
	 */
	//获取结果集
	public ResultSet getResultSet(String SQL) throws Exception {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = getStatement(con);
			rs = stmt.executeQuery(SQL);
			return rs;
		} catch (Exception e) {
			log.error(e.getStackTrace());
			return null;
		} finally {
			//closeResultSet(rs);
			//closeStatement(stmt);
			//closeConnection(con);
		}
	}
	
	/**
	 * 执行更新sql语句
	 * @param SQL
	 * @throws Exception
	 */
	public void exeModifySQL(String SQL) throws Exception{
		//ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = getStatement(con);
			con.setAutoCommit(false);
			stmt.executeUpdate(SQL);
			//con.commit();
		} catch (Exception e) {
//			if (con != null) {
//				try {
//					con.rollback();
//				} catch (SQLException e1) {
//				}
//			}
		} finally {
			//closeStatement(stmt);
			//closeConnection(con);
		}
	}
	
	/**
	 * 执行新增语句
	 * @param SQL
	 * @throws Exception
	 */
	public void exeModifySQLBatch(String SQL) throws Exception{
		PreparedStatement pstmt = null;
		try {
			pstmt =  con.prepareStatement(SQL);	
			con.setAutoCommit(false);
	        pstmt.executeBatch();
	        //con.commit();
		} catch (Exception e) {
//			if (con != null) {
//				try {
//					con.rollback();
//				} catch (SQLException e1) {
//				}
//			}
		} finally {
			//closePreparedStatement(pstmt);
			//closeConnection(con);
		}
	}
	
	

	/**
	 * 根据连接创建会话
	 * @param conn
	 * @return Statement
	 */
	private Statement getStatement(Connection conn) {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 关闭回话
	 * @param stmt
	 */
	private void closePreparedStatement(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
		}
	}
	
	/**
	 * 关闭会话
	 * @param stmt
	 */
	private void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * 关闭连接
	 * @param conn
	 */
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * 关闭结果集
	 * @param resultset
	 */
	public void closeResultSet(ResultSet resultset) {
		try {
			if (resultset != null) {
				resultset.close();
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * 关闭回调函数
	 * @param castmt
	 */
	public void closeCallableStatement(CallableStatement castmt) {
		try {
			if (castmt != null) {
				castmt.close();
			}
		} catch (SQLException e) {
		}
	}
	
}
