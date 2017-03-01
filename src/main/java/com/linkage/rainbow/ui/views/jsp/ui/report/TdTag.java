package com.linkage.rainbow.ui.views.jsp.ui.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import  com.linkage.rainbow.ui.components.report.Td;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * TdTag<br>
 * TdTag 是table的一个列。
 * <p>
 * @version 1.0
 * @author 陈亮 2009-04-8
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-04-8<br>
 *         修改内容:新建
 *         2、修改人员:陈亮 修改时间:2009-04-28<br>
 *         修改内容:增加获取gridid属性
 *         <hr>
 */

public class TdTag extends AbstractUITag {

	/**宽度 */
	protected String width;
	/**高度 */
	protected String height;
	
	/** 表格的HTML的样式属性 */
	protected String style ;
	
	/** 表格的HTML的样式表 */
	protected String styleClass ;
	
	//前影色
	protected String color;
	//背影图
	protected String background;
	//背影色
	protected String bgcolor;
	

	
	/**数据格式**/
	protected String dataFormat;

    /**单元格内容对齐样式 start**/
    protected String align; //水平对齐样式,字符串表示
    protected String valign; //水平对齐样式,字符串表示
	/**
	 * 单元的跨行数，对应HTML中
	 * <TD>的rowspan属性
	 */
    protected String rowspan = "1";
    
	/**
	 * 单元的跨列数，对应HTML中
	 * <TD>的colspan属性
	 */
    protected String colspan = "1";
    
    protected String nowrap; //是否不允许换行的属性，用于标签定义


    /**单元格内容对齐样式 end**/

	/** 扩展方向* */
    protected String extDirection;
	

	/** 单元是否隐藏的属性，即在toHTMLCode生成的HTML文件中不显示 */
	protected String isHidden = "false";

	/** 单元的内容 */
	protected String content;
	
	//链接地址
	protected String link;
	//链接提交窗口
	protected String linkTarget;
	//链接提示
	protected String linkTitle;
	
	protected String drillType ;//钻取方式.
	
	protected String drillTerm; //钻取时的条件
	
	protected String drillAvai;//钻取有效条件判断,当条件为true时,才能钻取
	
	protected String indention;////缩进值
	
	protected String defaultContent;//默认值
	

	/**
	 * 标签的构造函数中,增加根据配置文件初始化默认值
	 * 默认配置文件路径为:com/linkage/rainbow/ui/default.properties
	 * 项目组使用时,修改的配置文件路径为:comm.properties
	 * 1.配置文件中UI组件默认值设置的参数名设置规则为comm.ui.为前缀,
	 *   接下来是标签文件中定义的标签名,
	 *   最后是标签文件中定义的此标签的属性名
	 *   例：
	 *   comm.ui.date.template=WdatePicker
	 * 2.在标签类构造函数中通过调用：
	 * DefaultSettings.setDefaultValue(this,"comm.ui.标签名");
	 * 实现此标签的默认值设置
	 * @author 陈亮 2009-3-18 
	 */
	public TdTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.td");
	}

//	public int doStartTag() throws JspException {
//		super.doStartTag();
//		
//		return SKIP_BODY;
//		
//	}
	
	/**
	 * 获取内存中的组件类
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new Td(stack, req, res);
	}
	/**
	 * 将标签类的属性设置给组件类
	 */
	protected void populateParams() {
		
		super.populateParams();

		Td comp = (Td) component;
		comp.setWidth(width);
		comp.setHeight(height);
		comp.setStyle(style);
		comp.setStyleClass(styleClass);
		comp.setColor(color);
		comp.setBackground(background);
		comp.setBgcolor(bgcolor);
		comp.setOnclick(onclick);
		comp.setOndblclick(ondblclick);
		comp.setOnmouseover(onmouseover);
		comp.setOnmouseout(onmouseout);
		comp.setOnmousedown(onmousedown);
		comp.setDataFormat(dataFormat);
		comp.setAlign(align);
		comp.setValign(valign);
		comp.setRowspan(rowspan);
		//增加的获取gridid的属性
		comp.setColspan(colspan);

		comp.setNowrap(nowrap);
		
		comp.setExtDirection(extDirection);

		comp.setIsHidden(isHidden);
		
		comp.setLink(link); 
		comp.setLinkTarget(linkTarget);
		comp.setLinkTitle(linkTitle);
		comp.setDrillType(drillType);
		comp.setDrillTerm(drillTerm);
		comp.setDrillAvai(drillAvai);
		comp.setIndention(indention);
		comp.setDefaultContent(defaultContent);
		
		
		
		//设置此列在当前行是第几列,下标从0开始.
		Integer tmp = (Integer) (getStack().getContext().get("commReportColIndex"));
		int count =1;
    	if(tmp!=null){
    		count = count+tmp.intValue();
    	}
    	getStack().getContext().put("commReportColIndex",Integer.valueOf(count));
    	
	}
	/**
	 * 设置单元格的内容
	 * @return int
	 */
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		Object obj =getBodyContent();
		String content = getBody();
		Td comp = (Td) component;
		comp.setContent(content);
		 super.doEndTag();
		 return EVAL_PAGE;
	}

	/**
	 * 获取对齐方式
	 * @return String
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * 设置对齐方式
	 * @param align
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	/**
	 * 获取背景
	 * @return String
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 设置背景
	 * @param background
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * 返回背景色
	 * @return String
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	/**
	 * 设置背景色
	 * @param bgcolor
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	/**
	 * 返回跨行数
	 * @return String
	 */
	public String getColspan() {
		return colspan;
	}

	/**
	 * 设置跨行数
	 * @param colspan
	 */
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	/**
	 * 返回单元格内容
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置单元格内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 返回数据格式 
	 * @return String
	 */
	public String getDataFormat() {
		return dataFormat;
	}

	/**
	 * 设置数据格式
	 * @param dataFormat
	 */
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	/**
	 * 获取钻取有效条件判断,当条件为true时,才能钻取
	 * @return String
	 */
	public String getDrillAvai() {
		return drillAvai;
	}

	/**
	 * 设置钻取有效条件判断,当条件为true时,才能钻取
	 * @param drillAvai
	 */
	public void setDrillAvai(String drillAvai) {
		this.drillAvai = drillAvai;
	}

	/**
	 * 获取钻取时的条件
	 * @return String
	 */
	public String getDrillTerm() {
		return drillTerm;
	}

	/**
	 * 设置钻取时的条件
	 * @param drillTerm
	 */
	public void setDrillTerm(String drillTerm) {
		this.drillTerm = drillTerm;
	}

	/**
	 * 获取钻取方式
	 * @return String
	 */
	public String getDrillType() {
		return drillType;
	}

	/**
	 * 设置钻取方式
	 * @param drillType
	 */
	public void setDrillType(String drillType) {
		this.drillType = drillType;
	}

	/**
	 * 获取扩展方向
	 * @return String
	 */
	public String getExtDirection() {
		return extDirection;
	}

	/**
	 * 设置扩展方向
	 * @param extDirection
	 */
	public void setExtDirection(String extDirection) {
		this.extDirection = extDirection;
	}

	/**
	 * 获取单元格高度
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置单元格高度
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 获取单元是否隐藏的属性，即在toHTMLCode生成的HTML文件中不显示
	 * @return String
	 */
	public String getIsHidden() {
		return isHidden;
	}

	/**
	 * 设置单元是否隐藏的属性，即在toHTMLCode生成的HTML文件中不显示
	 * @param isHidden
	 */
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * 返回超链接
	 * @return String
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置超链接
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 获取连接目标
	 * @return String
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * 设置连接目标
	 * @param linkTarget
	 */
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	/**
	 * 获取是否自动换行
	 * @return String 
	 */
	public String getNowrap() {
		return nowrap;
	}

	/**
	 * 设置是否自动换行
	 * @param nowrap
	 */
	public void setNowrap(String nowrap) {
		this.nowrap = nowrap;
	}

	/**
	 * 获取单击事件
	 * @return String
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * 设置单击事件
	 * @param onclick
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * 获取双击事件
	 * @return String
	 */
	public String getOndblclick() {
		return ondblclick;
	}

	/**
	 * 设置双击事件
	 * @param ondblclick
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * 获取鼠标点击事件
	 * @return String
	 */
	public String getOnmousedown() {
		return onmousedown;
	}

	/**
	 * 设置鼠标点击事件
	 * @param onmousedown
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * 获取鼠标移开单元格事件
	 * @return String
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * 设置鼠标移开单元格事件
	 * @param onmouseout
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * 获取鼠标移到单元格事件
	 * @return String
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * 设置鼠标移到单元格事件
	 * @param onmouseover
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * 获取跨行数
	 * @return String
	 */
	public String getRowspan() {
		return rowspan;
	}

	/**
	 * 设置跨行数
	 * @param rowspan
	 */
	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	/**
	 * 获取样式
	 * @return String
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置样式
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取样式表
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * 设置样式表
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * 获取垂直对齐方式
	 * @return String
	 */
	public String getValign() {
		return valign;
	}

	/**
	 * 设置垂直对齐方式
	 * @param valign
	 */
	public void setValign(String valign) {
		this.valign = valign;
	}

	/**
	 * 获取单元格宽度
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置单元格宽度
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 获取缩进值
	 * @return String
	 */
	public String getIndention() {
		return indention;
	}

	/**
	 * 设置缩进值
	 * @param indention
	 */
	public void setIndention(String indention) {
		this.indention = indention;
	}

	/**
	 * 获取单元格前景色
	 * @return String
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置单元格前景色
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 获取连接提示信息
	 * @return String
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * 设置链接提示信息
	 * @param linkTitle
	 */
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * 获取单元格默认值
	 * @return String
	 */
	public String getDefaultContent() {
		return defaultContent;
	}

	/**
	 * 设置单元格默认值
	 * @param defaultContent
	 */
	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
	}



}
