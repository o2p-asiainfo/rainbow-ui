package com.linkage.rainbow.ui.components.tree;

/**
 * TreeNode<br>
 * 继承自接口的树返回类型的demo
 * <p>
 * @version 1.0
 *         <hr>
 */
public class TreeNode implements ITreeNode{

	
	private String text;
	private String node;
	private String f_Node;
	private String url;
	private String have_Sub;
	private String node_Type;
	private String icon;
	private String openIcon;
	/**
	 * 获取节点的文本
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * 设置节点的文本
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取节点id
	 * @return the node
	 */
	public String getNode() {
		return node;
	}
	/**
	 * 设置节点id
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}
	/**
	 * 获取父节点id
	 * @return the f_Node
	 */
	public String getF_Node() {
		return f_Node;
	}
	/**
	 * 设置父节点id
	 * @param node the f_Node to set
	 */
	public void setF_Node(String node) {
		f_Node = node;
	}
	/**
	 * 获取超链接
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置超链接
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 返回是否有子节点
	 * @return the have_sub
	 */
	public String getHave_Sub() {
		return have_Sub;
	}
	/**
	 * 设置是否有子节点
	 * @param have_sub the have_sub to set
	 */
	public void setHave_Sub(String have_Sub) {
		this.have_Sub = have_Sub;
	}
	/**
	 * 获取文字后的图标
	 */
	public String getAfterIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 获取图片前的图标
	 */
	public String getBeforeIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 返回节点类型
	 * @return the node_Type
	 */
	public String getNode_Type() {
		return node_Type;
	}
	/**
	 * 设置节点类型
	 * @param node_Type the node_Type to set
	 */
	public void setNode_Type(String node_Type) {
		this.node_Type = node_Type;
	}
	
	/**
	 * 返回节点图标
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置节点图标
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取节点打开图标
	 * @return the openIcon
	 */
	public String getOpenIcon() {
		return openIcon;
	}
	/**
	 * 设置节点打开图标
	 * @param openIcon the openIcon to set
	 */
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}
	/**
	 * 获取第2个节点的id
	 */
	public String getSecond_Node() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 获取节点状态
	 */
	public String getState() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}
