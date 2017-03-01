package com.linkage.rainbow.ui.components.comboTree;

/**
 * TreeNode<br>
 * 继承自接口的树返回类型的demo
 * <p>
 * @version 1.0
 *         <hr>
 */
public class ComboTreeNode implements IComboTreeNode{

	
	private String text;
	private String id;
	private String f_Node;
	private String have_Sub;

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
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getNode() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getNode_Type() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getOpenIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

	
	
}
