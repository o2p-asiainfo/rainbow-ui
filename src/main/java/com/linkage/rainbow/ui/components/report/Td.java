package com.linkage.rainbow.ui.components.report;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Coordinate;
import com.linkage.rainbow.ui.report.engine.model.Range;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * GridCol<br>
 * GridCol 是Grid的一个列。
 * <p>
 * @version 1.0
 * @author 陈亮 2009-04-8
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-04-8<br>
 *         修改内容:新建
 *         <hr>
 */
@StrutsTag(name="gridCol", tldTagClass="com.linkage.rainbowon.views.jsp.ui.grid.gridColTag", description="Render an HTML Table")
public class Td extends ClosingUIBean{
	/**
	 * 定义td标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "repoprt/td";
	/**
	 * 定义td标签的结束模板
	 */
	public static final String TEMPLATE = "report/td-close";
	
	/**宽度 */
	protected String width;
	/**高度 */
	protected String height;
	
	/** 表格的HTML的样式属性 */
	protected String style;
	
	/** 表格的HTML的样式表 */
	protected String styleClass;
	
	//前影色
	protected String color;
	//背影图
	protected String background;
	//背影色
	protected String bgcolor;
	

	
	/**数据格式**/
	protected String dataFormat ;

    /**单元格内容对齐样式 start**/
    protected String align; //水平对齐样式,字符串表示
    protected String valign; //水平对齐样式,字符串表示
	/**
	 * 单元的跨行数，对应HTML中
	 * <TD>的rowspan属性
	 */
    protected String rowspan ;
    
	/**
	 * 单元的跨列数，对应HTML中
	 * <TD>的colspan属性
	 */
    protected String colspan ;
    
    protected String nowrap ; //是否不允许换行的属性，用于标签定义


    /**单元格内容对齐样式 end**/

	/** 扩展方向* */
	private String extDirection;
	

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
	
	Cell cell = new Cell();
	
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Td
	 */
	public Td (ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
	
	/**
	 * 是否允许标签体内设置属性
	 * @return boolean
	 */
	@Override
    public boolean usesBody() {
        return true;
    }
	
	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	@Override
	public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }
	
	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	/**
	 * 将属性信息设置到模板
	 * @param writer
	 * @return boolean
	 */
	@Override
    public boolean start(Writer writer) {
    	//evaluateParams();
		evaluateExtraParams();
    	return true;
    }
	/**
	 * 形成报表请求类,调用报表引擎,生成报表输出
	 * @param writer
	 * @param body
	 * @return boolean
	 */
	@Override
    public boolean end(Writer writer, String body) {
		if(content != null)
			cell.setContent(findString(content));
        return false;
    }

	/**
	 * 将标签类的信息设置到组件类
	 */
	protected void evaluateExtraParams() {
		if(width != null)
			cell.setWidth(findString(width));
		if(height != null)
			cell.setHeight(findString(height));
		if(style != null)
			cell.setStyle(findString(style));
		if(styleClass != null)
			cell.setStyleClass(findString(styleClass));
		if(align != null)
			cell.setAlign(findString(align));
		if(color != null)
			cell.setColor(findString(color));
		if(background != null)
			cell.setBackground(findString(background));
		if(bgcolor != null)
			cell.setBgcolor(findString(bgcolor));
		if(onclick != null){
			System.out.println("onclick:"+onclick+" findString(onclick):"+findString(onclick));
			cell.setOnclick(findString(onclick));
		}
		if(ondblclick != null)
			cell.setOndblclick(findString(ondblclick));
		if(onmouseout != null)
			cell.setOnmouseout(findString(onmouseout));
		if(onmousedown != null)
			cell.setOnmousedown(findString(onmousedown));
		if(onmouseover != null)
			cell.setOnmouseover(findString(onmouseover));
		
		if(dataFormat != null)
			cell.setDataFormat(findString(dataFormat));
		if(rowspan != null)
			cell.setRowSpan((Integer)findValue(rowspan,Integer.class));
		if(colspan != null)
			cell.setColSpan((Integer)findValue(colspan,Integer.class));
		if(nowrap != null)
			cell.setNoWrap((Boolean)findValue(nowrap,Boolean.class));
		if(extDirection != null)
			cell.setExtDirection(findString(extDirection));
		if(isHidden != null)
			cell.setHidden((Boolean)findValue(isHidden,Boolean.class));
		
		if(link != null)
			cell.setLink(findString(link));
		
		if(linkTarget != null)
			cell.setLinkTarget(findString(linkTarget));
		if(linkTitle != null)
			cell.setLinkTitle(findString(linkTitle));
		
		if(drillType != null)
			cell.setDrillType(findString(drillType));
		if(drillTerm != null)
			cell.setDrillTerm(findString(drillTerm));
		if(drillAvai != null)
			cell.setDrillAvai((Boolean)findValue(drillAvai,Boolean.class));
		if(indention != null)
			cell.setIndention((Integer)findValue(indention,Integer.class));
	
		if(defaultContent != null){
			String tmpContent = findString(defaultContent);
			try {
				cell.setDefaultContent(Double.parseDouble(tmpContent));
			} catch (Exception e) {
				cell.setDefaultContent(tmpContent);
			}
			
		}
			
		
		Tr tr =  (Tr)findAncestor(Tr.class);
		if(tr != null){
			cell.setRow(tr.getRange());
		}
		Table table =  (Table)findAncestor(Table.class);
		if(table != null){
			//当前是第几行,第一行从下标0开始.
			Integer rowIndex = (Integer) (getStack().getContext().get("commReportRowIndex"));
			//当前是第几列,第一列从下标0开始.
			Integer colIndex = (Integer) (getStack().getContext().get("commReportColIndex"));
			Coordinate coordinate = table.getDefCS().setCellIntelligent(rowIndex,colIndex,cell);
			if(coordinate.getStartColumn() < table.getDefCS().getColRangeList().size()){
				cell.setCol((Range)table.getDefCS().getColRangeList().get(coordinate.getStartColumn()));
			}
		}
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
	 * 获取背景色
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
	 * 获取跨列数
	 * @return String
	 */
	public String getColspan() {
		return colspan;
	}

	/**
	 * 设置跨列数
	 * @param colspan
	 */
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	/**
	 * 获取单元的内容
	 * @return Object
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * 获取单元格对象
	 * @return Cell
	 */
	public Cell getCell() {
		return cell;
	}

	/**
	 * 设置单元格对象
	 * @param cell
	 */
	public void setCell(Cell cell) {
		this.cell = cell;
	}

	/**
	 * 获取数据展示格式
	 * @return String
	 */
	public String getDataFormat() {
		return dataFormat;
	}

	/**
	 * 设置数据展示格式
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
	 * 获取钻取方式.
	 * @return String
	 */
	public String getDrillType() {
		return drillType;
	}

	/**
	 * 设置钻取方式.
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
	 * 获取超链接
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
	 * 获取超链接对象
	 * @return String
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * 设置超链接对象
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
	 * @param String
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
	 * 获取鼠标移开对象事件
	 * @return String
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * 设置鼠标移开对象事件
	 * @param onmouseout
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * 获取鼠标移动到对象事件
	 * @return String
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * 设置鼠标移动到对象事件
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
	 * 获取宽度
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
	 * 设置单元格内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * 获取前景色
	 * @return String
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置前景色
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 获取链接提示
	 * @return String
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * 设置链接提示
	 * @param linkTitle
	 */
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * 获取默认值
	 * @return String
	 */
	public String getDefaultContent() {
		return defaultContent;
	}

	/**
	 * 设置默认值
	 * @param defaultContent
	 */
	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
	}


}
