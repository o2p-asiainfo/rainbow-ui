package com.linkage.rainbow.ui.views.jsp.ui.itemSelector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractListTag;

import com.linkage.rainbow.ui.components.itemSelector.ItemSelector;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * ItemSelectorTag<br>
 * 左右列表框标签类提供对左右列表框组件类属性的操作
 * <p>
 * @version 1.0
 */
public class ItemSelectorTag extends AbstractListTag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titleL;//左标题
	private String titleR;//右标题
	private String skin;//组件皮肤：默认为墨绿色
	private String layerWidth;//组件宽度：默认为80px
	private String layerHeight;// 组件高度：默认为
	private String width;
	private String height;
	private Object listL;
	private String listKeyL;
	private String listValueL;
	private Object listR;
	private String listKeyR;
	private String listValueR;
	private String javascriptL;
	private String javascriptR;
	private String charTypeValue;//默认返回值为数值型
	private String isShowSort;//默认不显示排序按钮
	
	
	
	/**
	 * 调用配置文件 设置默认值
	 */
	public ItemSelectorTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.itemSelector");
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
		return new ItemSelector(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();

		ItemSelector itemSelector = (ItemSelector) component;
		itemSelector.setTitleL(titleL);
		itemSelector.setTitleR(titleR);
		itemSelector.setLayerWidth(layerWidth);
		itemSelector.setLayerHeight(layerHeight);
		itemSelector.setWidth(width);
		itemSelector.setHeight(height);	
		itemSelector.setSkin(skin);
		itemSelector.setJavascriptL(javascriptL);
		itemSelector.setJavascriptR(javascriptR);
		itemSelector.setListL(listL);
		itemSelector.setListKeyL(listKeyL);
		itemSelector.setListValueL(listValueL);
		itemSelector.setListR(listR);
		itemSelector.setListKeyR(listKeyR);
		itemSelector.setListValueR(listValueR);
		itemSelector.setCharTypeValue(charTypeValue);
		itemSelector.setIsShowSort(isShowSort);
	}

	/**
	 * 设置皮肤
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 设置左、右两个选项栏的高度的宽度
	 * @param layerWidth the layerWidth to set
	 */
	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	/** 
	 * 设置左、右两个选项栏的高度的高度
	 * @param layerHeight the layerHeight to set
	 */
	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}

	/**
	 * 设置选项框的宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置选项框的高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置左列表框的脚本
	 * @param javascriptL the javascriptL to set
	 */
	public void setJavascriptL(String javascriptL) {
		this.javascriptL = javascriptL;
	}

	/**
	 * 设置右列表框的脚本
	 * @param javascriptR the javascriptR to set
	 */
	public void setJavascriptR(String javascriptR) {
		this.javascriptR = javascriptR;
	}

	/**
	 * 设置左列表框的数据集
	 * @param listL the listL to set
	 */
	public void setListL(Object listL) {
		this.listL = listL;
	}

	/**
	 * 设置左列表框的value
	 * @param listKeyL the listKeyL to set
	 */
	public void setListKeyL(String listKeyL) {
		this.listKeyL = listKeyL;
	}

	/**
	 * 设置左列表框的text
	 * @param listValueL the listValueL to set
	 */
	public void setListValueL(String listValueL) {
		this.listValueL = listValueL;
	}

	/**
	 * 右列表框的数据集
	 * @param listR the listR to set
	 */
	public void setListR(Object listR) {
		this.listR = listR;
	}

	/**
	 * 右列表框的value
	 * @param listKeyR the listKeyR to set
	 */
	public void setListKeyR(String listKeyR) {
		this.listKeyR = listKeyR;
	}

	/**
	 * 右列表框的text
	 * @param listValueR the listValueR to set
	 */
	public void setListValueR(String listValueR) {
		this.listValueR = listValueR;
	}

	/**
	 * 左右列表框组件返回值是否为字符串类型;true为字符串型(String),false为数值型;默认为（false）
	 * @param charTypeValue the charTypeValue to set
	 */
	public void setCharTypeValue(String charTypeValue) {
		this.charTypeValue = charTypeValue;
	}

	/**
	 * 设置左列表框的标题
	 * @param titleL the titleL to set
	 */
	public void setTitleL(String titleL) {
		this.titleL = titleL;
	}

	/**
	 * 设置右列表框的标题
	 * @param titleR the titleR to set
	 */
	public void setTitleR(String titleR) {
		this.titleR = titleR;
	}

	/**
	 * 设置是否显示排序按钮
	 * @param isShowSort the isShowSort to set
	 */
	public void setIsShowSort(String isShowSort) {
		this.isShowSort = isShowSort;
	}
	
}
