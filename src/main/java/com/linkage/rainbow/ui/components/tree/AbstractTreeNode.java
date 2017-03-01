package com.linkage.rainbow.ui.components.tree;


/**
 * 树结点抽象类<br>
 * 使用树组件时,生成树结点的数据List内元素一种是Map,Map中的元素按树形组件的规则命名,
 * 另一个方法是List内元素为javaBean,此javaBean实现 com.linkage.rainbow.ui.components.tree.ITreeNode接口.
 * 因接口中定义的属性较多,一般使用时不会用到全部,因此建立此抽象类,抽象类实现ITreeNode接口,
 * 各项目组树结点的javaBean可以继承此抽象类
 * <p>
 * @version 1.0
 * <hr>
 */

public abstract class AbstractTreeNode implements ITreeNode{

	/**
	 * 获取节点id
	 */
	abstract public String getNode();               //获取节点id
	/**
	 * 获取节点文本信息
	 */
	abstract public String getText();               //获取节点文本信息
	/**
	 * 获取父节点id
	 */
	abstract public String getF_Node();             //获取父节点id
	/**
	 * 获取节点对应的url
	 */
	public String getUrl(){return null;}               //获取节点对应的url
	/**
	 * 获取节点类型
	 */
	public String getNode_Type(){return null;} //获取节点类型
	/**
	 * 获取节点状态
	 */
	public String getState(){return null;}              //获取节点状态
	/**
	 * 获取节点图标
	 */
	public String getIcon(){return null;}               //获取节点图标
	/**
	 * 获取节点打开时的图标
	 */
	public String getOpenIcon(){return null;}           //获取节点打开时的图标
	/**
	 * 获取节点文本前面图标
	 */
	public String getBeforeIcon(){return null;}         //获取节点文本前面图标
	/**
	 * 获取节点文本后面图标
	 */
	public String getAfterIcon(){return null;}          //获取节点文本后面图标
	/**
	 * 获取第2个checkbox对应的node id
	 */
	public String getSecond_Node(){return null;}         //获取第2个checkbox对应的node id
	/**
	 * 判断节点是否有子节点
	 */
	public String getHave_Sub(){return null;}           //判断节点是否有子节点
}
