package com.linkage.rainbow.ui.components.comboTree;

import java.io.Serializable;

/**
 * ITreeNode<br>
 * 定义节点某个层的接口
 * <p>
 * @version 1.0
 *         修改记录
 */
public interface IComboTreeNode  extends Serializable{
	/**
	 * 获取节点id
	 * @return String
	 */
	public String getId();               //获取节点id
	/**
	 * 获取节点对应的url
	 * @return String
	 */
	public String getUrl();                //获取节点对应的url
	/**
	 * 获取节点文本信息
	 * @return String
	 */
	public String getText();               //获取节点文本信息
	/**
	 * 获取父节点id
	 * @return String
	 */
	public String getF_Node();             //获取父节点id
	/**
	 * 获取节点类型
	 * @return String
	 */
	public String getNode_Type();          //获取节点类型
	/**
	 * 获取节点状态
	 * @return String
	 */
	public String getState();              //获取节点状态
	/**
	 * 获取节点图标
	 * @return String
	 */
	public String getIcon();               //获取节点图标
	/**
	 * 获取节点打开时的图标
	 * @return String
	 */
	public String getOpenIcon();           //获取节点打开时的图标
	/**
	 * 获取节点文本前面图标
	 * @return String
	 */
	public String getBeforeIcon();         //获取节点文本前面图标
	/**
	 * 获取节点文本后面图标
	 * @return String
	 */
	public String getAfterIcon();          //获取节点文本后面图标
	/**
	 * 获取第2个checkbox对应的node id
	 * @return String
	 */
	public String getSecond_Node();         //获取第2个checkbox对应的node id
	/**
	 * 判断节点是否有子节点
	 * @return String
	 */
	public String getHave_Sub();           //判断节点是否有子节点
}
