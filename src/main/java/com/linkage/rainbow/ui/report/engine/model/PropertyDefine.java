package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;

/**
 * 属性值常量定义<br>
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-2
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class PropertyDefine implements Serializable, Cloneable{

	/**行分隔符*/
	public final static String rowBoxOff = new StringBuffer().append((char)2).toString();
	/**列分隔符*/
	public final static String colBoxOff = new StringBuffer().append((char)3).toString();
	
	/**单元格内容对齐样式定义 start**/
    /**
     * 水平位置默认，数字表示
     * general (normal) horizontal alignment
     */

    public final static short  ALIGN_GENERAL              = 0x0;
    
    /**
     * 水平位置默认,Html显示表达
     */
    public final static String  ALIGN_GENERAL_HTML              = "";

    /**
     * 水平位置左对齐，数字表示
     * left-justified horizontal alignment
     */

    public final static short    ALIGN_LEFT                 = 0x1;
    /**
     * 水平位置左对齐,Html显示表达
     */
    public final static String    ALIGN_LEFT_HTML                 = "left";

    /**
     * 水平位置居中，数字表示
     * center horizontal alignment
     */

    public final static short    ALIGN_CENTER               = 0x2;
    /**
     * 水平位置居中,Html显示表达
     */
    public final static String    ALIGN_CENTER_HTML               = "center";

    /**
     * 水平位置右对齐，数字表示
     * right-justified horizontal alignment
     */

    public final static short    ALIGN_RIGHT                = 0x3;
    /**
     * 水平位置右对齐,Html显示表达
     */
    public final static String    ALIGN_RIGHT_HTML                = "right";

    /**
     * 水平位置左右对齐，数字表示
     * fill? horizontal alignment
     */

    public final static short    ALIGN_FILL                 = 0x4;
    /**
     * 水平位置左右对齐,Html显示表达
     */
    public final static String    ALIGN_FILL_HTML                 = "fill";

    /**
     * 水平位置justify对齐，数字表示
     * justified horizontal alignment
     */

    public final static short    ALIGN_JUSTIFY              = 0x5;
    /**
     * 水平位置justify对齐,Html显示表达
     */
    public final static String    ALIGN_JUSTIFY_HTML              = "justify";

    /**
     * 水平位置CENTER_SELECTION对齐，数字表示
     * center-selection? horizontal alignment
     */
    public final static short    ALIGN_CENTER_SELECTION     = 0x6;
    /**
     * 水平位置CENTER_SELECTION对齐,Html显示表达
     */
    public final static String    ALIGN_CENTER_SELECTION_HTML     = "center-across";

    /**
     * 垂直位置顶部对齐，数字表示
     * top-aligned vertical alignment
     */
    public final static short    VERTICAL_TOP               = 0x0;
    /**
     * 垂直位置顶部对齐,Html显示表达
     */
    public final static String    VERTICAL_TOP_HTML               = "top";

    /**
     * 垂直位置居中对齐，数字表示
     * center-aligned vertical alignment
     */
    public final static short    VERTICAL_CENTER            = 0x1;
    /**
     * 垂直位置居中对齐,Html显示表达
     */
    public final static String    VERTICAL_CENTER_HTML            = "";

    /**
     * 垂直位置底部对齐，数字表示
     * bottom-aligned vertical alignment
     */

    public final static short    VERTICAL_BOTTOM            = 0x2;
    /**
     * 垂直位置底部对齐,Html显示表达
     */
    public final static String    VERTICAL_BOTTOM_HTML            = "bottom";

    /**
     * 垂直位置justify对齐，数字表示
     * vertically justified vertical alignment
     */

    public final static short    VERTICAL_JUSTIFY           = 0x3;
    /**
     * 垂直位置justify对齐,Html显示表达
     */
    public final static String    VERTICAL_JUSTIFY_HTML           = "justify";
	
	/**单元格内容对齐样式定义 end**/
    
    /**边框样式定义 start**/

    /**
     * 边框样式定义，无边框，数字表示
     */
    public final static short    BORDER_NONE                = 0x0;
    public final static String    BORDER_NONE_HTML                = "none";

    /**
     *  边框样式定义，细边框，数字表示
     * Thin border
     */
    public final static short    BORDER_THIN                = 0x1;
    public final static String    BORDER_THIN_HTML                = "0.5pt solid";

    /**
     * 边框样式定义，中等边框，数字表示
     * Medium border
     */

    public final static short    BORDER_MEDIUM              = 0x2;
    public final static String    BORDER_MEDIUM_HTML              = "1pt solid";

    /**
     * 边框样式定义，细虚线边框，数字表示
     * dash border
     */

    public final static short    BORDER_DASHED              = 0x3;
    public final static String    BORDER_DASHED_HTML              = "0.5pt dashed";

    /**
     *  边框样式定义，细边框，数字表示
     * dot border
     */

    public final static short    BORDER_HAIR              = 0x4;
    public final static String    BORDER_HAIR_HTML              = "0.5pt solid";

    /**
     * 边框样式定义，粗边框，数字表示
     * Thick border
     */

    public final static short    BORDER_THICK               = 0x5;
    public final static String    BORDER_THICK_HTML               = "1.5pt solid";

    /**
     * 边框样式定义，双线边框，数字表示
     * double-line border
     */

    public final static short    BORDER_DOUBLE              = 0x6;
    public final static String    BORDER_DOUBLE_HTML              = "2pt double";

    /**
     *  边框样式定义，虑点边框，数字表示
     * hair-line border
     */

    public final static short    BORDER_DOTTED                = 0x7;
    public final static String    BORDER_DOTTED_HTML                = "0.5pt dotted";

    /**
     * 边框样式定义，中等虚线边框，数字表示
     * Medium dashed border
     */

    public final static short    BORDER_MEDIUM_DASHED       = 0x8;
    public final static String    BORDER_MEDIUM_DASHED_HTML       = "1pt dashed";

    /**
     * 
     * dash-dot border
     */

    public final static short    BORDER_DASH_DOT            = 0x9;
    public final static String    BORDER_DASH_DOT_HTML            = "0.5pt dashed";

    /**
     * medium dash-dot border
     */

    public final static short    BORDER_MEDIUM_DASH_DOT     = 0xA;
    public final static String    BORDER_MEDIUM_DASH_DOT_HTML     = "1pt dashed";

    /**
     * dash-dot-dot border
     */

    public final static short    BORDER_DASH_DOT_DOT        = 0xB;
    public final static String    BORDER_DASH_DOT_DOT_HTML        = "0.5pt dashed";

    /**
     * medium dash-dot-dot border
     */

    public final static short    BORDER_MEDIUM_DASH_DOT_DOT = 0xC;
    public final static String    BORDER_MEDIUM_DASH_DOT_DOT_HTML = "1pt dashed";

    /**
     * slanted dash-dot border
     */

    public final static short    BORDER_SLANTED_DASH_DOT    = 0xD;
    public final static String    BORDER_SLANTED_DASH_DOT_HTML    = "0.5pt dashed";
    /**边框样式定义 end**/
	
    /* EXCEL边框样式与HTML样式对应关系 
    * BORDER_NONE -->none     
    * BORDER_THIN --> 1pt solid 
    * BORDER_MEDIUM -->1pt solid
    * BORDER_DASHED -->1pt dashed
    * BORDER_DOTTED -->1pt dotted 
    * BORDER_THICK --> 2pt solid 
    * BORDER_DOUBLE --> 2pt double
    * BORDER_HAIR -->1pt solid
    * BORDER_MEDIUM_DASHED -->1pt dashed
    *{BORDER_DASH_DOT  
    * BORDER_MEDIUM_DASH_DOT
    * BORDER_DASH_DOT_DOT
    * BORDER_MEDIUM_DASH_DOT_DOT
    * BORDER_SLANTED_DASH_DOT } -->1pt dashed                
    */
    
    public static String alignNumToHtml(int align){
    	String alignStyle = "";
		//水平对齐样式
		switch (align) {
		case ALIGN_GENERAL: //普通
			break;
		case ALIGN_LEFT: //左对齐
			alignStyle = ALIGN_LEFT_HTML;
			break;
		case ALIGN_CENTER: //居中
			alignStyle = ALIGN_CENTER_HTML;
			break;
		case ALIGN_RIGHT: //右对齐
			alignStyle = ALIGN_RIGHT_HTML;
			break;
		case ALIGN_FILL: //填充
			alignStyle = ALIGN_FILL_HTML;
			break;
		case ALIGN_JUSTIFY: //两端对齐
			alignStyle = ALIGN_JUSTIFY_HTML;
			break;
		case ALIGN_CENTER_SELECTION: //跨列居中
			alignStyle = ALIGN_CENTER_SELECTION_HTML;
			break;
		default:
			
		}
		return alignStyle;
    }
    
    public static int alignHtmlToNum(String align){
    	int alignStyle = 0;
		//水平对齐样式
		if(align!= null){
			if(align.trim().toLowerCase().equals(ALIGN_GENERAL_HTML))
				alignStyle = ALIGN_GENERAL; //普通
			else if(align.trim().toLowerCase().equals(ALIGN_LEFT_HTML)) //左对齐
				alignStyle = ALIGN_LEFT;
			else if(align.trim().toLowerCase().equals(ALIGN_CENTER_HTML)) //居中
				alignStyle = ALIGN_CENTER;
			else if(align.trim().toLowerCase().equals(ALIGN_RIGHT_HTML)) //右对齐
				alignStyle = ALIGN_RIGHT;
			else if(align.trim().toLowerCase().equals(ALIGN_FILL_HTML)) //填充
				alignStyle = ALIGN_FILL;
			else if(align.trim().toLowerCase().equals(ALIGN_JUSTIFY_HTML)) //两端对齐
				alignStyle = ALIGN_JUSTIFY;
			else if(align.trim().toLowerCase().equals(ALIGN_CENTER_SELECTION_HTML)) //跨列居中
				alignStyle = ALIGN_CENTER_SELECTION;
			else{
				alignStyle = ALIGN_GENERAL; //普通
			}
		}
		return alignStyle;
    }
    
    
    public static String valignNumToHtml(int valign){
    	String valignStyle = "";
		//水平对齐样式
    	switch (valign) {
		case VERTICAL_TOP: //顶对齐
			valignStyle=VERTICAL_TOP_HTML;
			break;
		case VERTICAL_CENTER: //居中
			valignStyle=VERTICAL_CENTER_HTML;
			break;
		case VERTICAL_BOTTOM: //底对齐
			valignStyle=VERTICAL_BOTTOM_HTML;
			break;
		case VERTICAL_JUSTIFY: //分散对齐
			valignStyle=VERTICAL_JUSTIFY_HTML;
			break;
		default:
    	}
		return valignStyle;
    }
    
    public static int valignHtmlToNum(String valign){
    	int valignStyle = 0;
		//水平对齐样式
		if(valign!= null){
			if(valign.trim().toLowerCase().equals(VERTICAL_TOP_HTML))
				valignStyle = VERTICAL_TOP; //普通
			else if(valign.trim().toLowerCase().equals(VERTICAL_CENTER_HTML)) //左对齐
				valignStyle = VERTICAL_CENTER;
			else if(valign.trim().toLowerCase().equals(VERTICAL_BOTTOM_HTML)) //居中
				valignStyle = VERTICAL_BOTTOM;
			else if(valign.trim().toLowerCase().equals(VERTICAL_JUSTIFY_HTML)) //居中
				valignStyle = VERTICAL_JUSTIFY;
			else{
			}
		}
		return valignStyle;
    }
}
