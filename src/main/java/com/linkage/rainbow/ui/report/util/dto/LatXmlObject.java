package com.linkage.rainbow.ui.report.util.dto;

import java.util.List;

/**
 * LatXmlObject<br>
 * 该类用于存储维度xml节点信息
 * <p>
 * @version 1.0
 * @author bingel Feb 4, 2010
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class LatXmlObject {
	private String tableName;//表明
	private String primaryKey;//主键
	private List levelList;//层级列表
	private String type;//节点类型
	private int indexCount;//节点数量
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the primaryKey
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * @return the levelList
	 */
	public List getLevelList() {
		return levelList;
	}
	/**
	 * @param levelList the levelList to set
	 */
	public void setLevelList(List levelList) {
		this.levelList = levelList;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the indexCount
	 */
	public int getIndexCount() {
		return indexCount;
	}
	/**
	 * @param indexCount the indexCount to set
	 */
	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}
	
	
}
