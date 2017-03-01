package com.linkage.rainbow.ui.views.jsp.ui.multiCheckListBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractRequiredListTag;

import com.linkage.rainbow.ui.components.multiCheckListBox.MultiCheckListBox;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * MultiCheckListBoxTag<br>
 * 多选列表标签类提供对多选列表组件类属性的操作
 * <p>
 * @version 1.0
 */
public class MultiCheckListBoxTag extends AbstractRequiredListTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String skin = "blue";//组件皮肤：默认为墨绿色
	private String layerWidth = "21px";//组件宽度：默认为80px
	private String layerHeight="23px";// 组件高度：默认为
	private String rows;// 组件高度：默认为
	private String cols;// 组件高度：默认为
	private String charTypeValue="false";
	private String checkAllValueSetBlank="false";
	private String isCheckAll="false";
	private String javascript;
	
	
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public MultiCheckListBoxTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.multiCheckListBox");
	}
	
	/**
	 * 实现接口类 继承父类的基本信息
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		// TODO Auto-generated method stub
		return new MultiCheckListBox(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		MultiCheckListBox multiCheckListBox = (MultiCheckListBox) component;
		multiCheckListBox.setLayerWidth(layerWidth);
		multiCheckListBox.setLayerHeight(layerHeight);
		multiCheckListBox.setRows(rows);
		multiCheckListBox.setCols(cols);
		multiCheckListBox.setSkin(skin);
		multiCheckListBox.setCharTypeValue(charTypeValue);
		multiCheckListBox.setCheckAllValueSetBlank(checkAllValueSetBlank);
		multiCheckListBox.setIsCheckAll(isCheckAll);
		multiCheckListBox.setJavascript(javascript);
	}

	/**
	 * 设置多选组件的皮肤
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 设置多选下拉框组件下拉框的宽度
	 * @param layerWidth the layerWidth to set
	 */
	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	/**
	 * 设置多选下拉框组件下拉框的高度
	 * @param layerHeight the layerHeight to set
	 */
	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}

	/**
	 * 设置textarea的行数
	 * @param rows the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	/**
	 * 设置textarea的列数
	 * @param cols the cols to set
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * 设置多选下拉框组件返回值是否为字符串类型;true为字符串型(String),false为数值型;默认为（false）
	 * @param charTypeValue the charTypeValue to set
	 */
	public void setCharTypeValue(String charTypeValue) {
		this.charTypeValue = charTypeValue;
	}

	/**
	 * 多选下拉框组件全选时返回值是否为'ALL';true 为'ALL'、false为所有选中值用','隔开的字符串；默认为（true）
	 * @param checkAllValueSetBlank the checkAllValueSetBlank to set
	 */
	public void setCheckAllValueSetBlank(String checkAllValueSetBlank) {
		this.checkAllValueSetBlank = checkAllValueSetBlank;
	}

	/**
	 * 设置默认是否全选
	 * @param isCheckAll the isCheckAll to set
	 */
	public void setIsCheckAll(String isCheckAll) {
		this.isCheckAll = isCheckAll;
	}

	/**
	 * 设置多选下拉框组件checkbox选项的Javascript事件 如：onclick="alert(1);"
	 * @param javascript the javascript to set
	 */
	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}
}
