package com.linkage.rainbow.ui.components.itemSelector;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ListUIBean;
import org.apache.struts2.util.MakeIterator;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * ItemSelector<br>
 * 左右列表框将标签属性设置给模板的过度类
 * <p>
 */
public class ItemSelector extends ListUIBean {
	/**
	 * 定义itemSelector组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "itemSelector/itemSelector";// 多选下拉框标签模版
	/**
	 * 定义itemSelector组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "itemSelector/itemSelector-close";// 多选下拉框结束标签模版
    
	private String titleL;//左标题
	private String titleR;//右标题
	private String skin;// 组件皮肤：默认为墨绿色
	private String layerWidth;
	private String layerHeight;
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
	private String charTypeValue;
	private String isShowSort="false";//
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return ItemSelector
	 */
	public ItemSelector(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	public void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("tagUtil", new ComponentUtil(request));
		
		addParameter("clearLabel", "清除"); //解决在utf-8环境下乱码问题,模板内文件不显示中文.
		addParameter("allSelectLabel", "全选"); //解决在utf-8环境下乱码问题,模板内文件不显示中文.
		
		if (titleL != null) 
			addParameter("titleL", findString(titleL));
		if (titleR != null) 
			addParameter("titleR", findString(titleR));
		if ( skin != null) 
			addParameter("skin", findString(skin));
		if (layerWidth != null) 
			addParameter("layerWidth", findString(layerWidth));
		//addParameter("layerWidthR", Integer.parseInt(layerWidth.toLowerCase().replaceAll("px", "").trim()));
		if (layerHeight != null) 
			addParameter("layerHeight", findString(layerHeight));
		if (width != null) 
			addParameter("width", findString(width));
		if (height != null) 
			addParameter("height", findString(height));
		if (javascriptL != null) 
			addParameter("javascriptL", findString(javascriptL));
		if (javascriptR != null) 
			addParameter("javascriptR", findString(javascriptR));
		if (charTypeValue != null) 
			addParameter("charTypeValue", findString(charTypeValue));
		if (isShowSort != null) 
			addParameter("isShowSort", findString(isShowSort));
        Object valueL = null;

        if (listL == null) {
            listL = parameters.get("listL");
        }

        if (listL instanceof String) {
        	valueL = findValue((String) listL);
        } else if (listL instanceof Collection) {
        	valueL = listL;
        } else if (MakeIterator.isIterable(listL)) {
        	valueL = MakeIterator.convert(listL);
        }
        if (valueL == null) {
            if (throwExceptionOnNullValueAttribute) {
                // will throw an exception if not found
            	valueL = findValue((listL == null) ? (String) listL : listL.toString(), "listL",
                    "The requested listL key '" + listL + "' could not be resolved as a collection/array/map/enumeration/iterator type. " +
                    "Example: people or people.{name}");
            }
            else {
                // ww-1010, allows value with null value to be compatible with ww
                // 2.1.7 behaviour
            	valueL = findValue((listL == null)?(String) listL:listL.toString());
            }
        }

        if (valueL instanceof Collection) {
            addParameter("listL", valueL);
        } else {
            addParameter("listL", MakeIterator.convert(valueL));
        }

        if (valueL instanceof Collection) {
            addParameter("listLSize", new Integer(((Collection) valueL).size()));
        } else if (valueL instanceof Map) {
            addParameter("listLSize", new Integer(((Map) valueL).size()));
        } else if (value != null && value.getClass().isArray()) {
            addParameter("listLSize", new Integer(Array.getLength(valueL)));
        }

        if (listKeyL != null) {
            addParameter("listKeyL", listKeyL);
        } else if (valueL instanceof Map) {
            addParameter("listKeyL", "key");
        }

        if (listValueL != null) {
            if (altSyntax()) {
                // the same logic as with findValue(String)
                // if value start with %{ and end with }, just cut it off!
                if (listValueL.startsWith("%{") && listValueL.endsWith("}")) {
                	listValueL = listValueL.substring(2, listValueL.length() - 1);
                }
            }
            addParameter("listValueL", listValueL);
        } else if (valueL instanceof Map) {
            addParameter("listValueL", "value");
        }
		
        Object valueR = null;

        if (listR == null) {
            listR = parameters.get("listR");
        }

        if (listR instanceof String) {
            valueR = findValue((String) listR);
        } else if (listR instanceof Collection) {
            valueR = listR;
        } else if (MakeIterator.isIterable(listR)) {
            valueR = MakeIterator.convert(listR);
        }
        if (valueR == null) {
            if (throwExceptionOnNullValueAttribute) {
                // will throw an exception if not found
                valueR = findValue((listR == null) ? (String) listR : listR.toString(), "listR",
                    "The requested listR key '" + listR + "' could not be resolved as a collection/array/map/enumeration/iterator type. " +
                    "Example: people or people.{name}");
            }
            else {
                // ww-1010, allows value with null value to be compatible with ww
                // 2.1.7 behaviour
                valueR = findValue((listR == null)?(String) listR:listR.toString());
            }
        }

        if (valueR instanceof Collection) {
            addParameter("listR", valueR);
        } else {
            addParameter("listR", MakeIterator.convert(valueR));
        }

        if (valueR instanceof Collection) {
            addParameter("listRSize", new Integer(((Collection) valueR).size()));
        } else if (valueR instanceof Map) {
            addParameter("listRSize", new Integer(((Map) valueR).size()));
        } else if (valueR != null && valueR.getClass().isArray()) {
            addParameter("listRSize", new Integer(Array.getLength(valueR)));
        }

        if (listKeyR != null) {
            addParameter("listKeyR", listKeyR);
        } else if (valueR instanceof Map) {
            addParameter("listKeyR", "key");
        }

        if (listValueR != null) {
            if (altSyntax()) {
                // the same logic as with findValue(String)
                // if value start with %{ and end with }, just cut it off!
                if (listValueR.startsWith("%{") && listValueR.endsWith("}")) {
                    listValueR = listValueR.substring(2, listValueR.length() - 1);
                }
            }
            addParameter("listValueR", listValueR);
        } else if (valueR instanceof Map) {
            addParameter("listValueR", "value");
        }
		
	}
	
	/**
	 * 继承自基类，并重写，用于获取起始模板<br>
	 * @return String
	 */
	public String getDefaultOpenTemplate() {
		
		return OPEN_TEMPLATE;
	}
	
	/**
	 * 指定模板
	 * @author 陈亮 2009-02-11
	 * @param template 模板名. 例:一模板存放的完整路径是
	 *        template/xhtml/date/WdatePicker/date.ftl 其中: template
	 *        为模板存放主目录,对应的标签属性是: templateDir
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
	 *        xhtml 为主题名称,对应的标签属性是: theme
	 *        ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值. .ftl
	 *        为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
	 *        date/WdatePicker/date 为模板名称, 对应的标签属性是: template 与 openTemplate
	 *        ,如果标签没有设置,则通过此类的getDefaultOpenTemplate()
	 *        与getDefaultTemplate()分别取得标签的开始模板与结束模板.
	 *        便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
	 *        一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date
	 *        目录下再建立子目录,如:WdatePicker,来区分不同的模板实现. 在标签调用时,不需要分别设置template 与
	 *        openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
	 */
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "itemSelector/" + template
					+ "/itemSelector";
		}else{
			this.template=this.getDefaultOpenTemplate();
		} 
	}

	/**
	 * 继承自基类，并重写，用于获取结束模板<br>
	 * @return String
	 */
	protected String getDefaultTemplate() {
		// TODO Auto-generated method stub
		return this.template;
	}

	/**
	 * 设置皮肤
	 * @param skin
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 设置布局宽度
	 * @param layerWidth the layerWidth to set
	 */
	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	/**
	 * 设置布局高度
	 * @param layerHeight the layerHeight to set
	 */
	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}

	/**
	 * 设置左右列表框的标识
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置左右列表框的宽度
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置左右列表框的高度
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置左边列表框的js事件
	 * @param javascriptL the javascriptL to set
	 */
	public void setJavascriptL(String javascriptL) {
		this.javascriptL = javascriptL;
	}

	/**
	 * 设置右边列表框的js事件
	 * @param javascriptR the javascriptR to set
	 */
	public void setJavascriptR(String javascriptR) {
		this.javascriptR = javascriptR;
	}

	/**
	 * 设置左边列表框的数据
	 * @param listL the listL to set
	 */
	public void setListL(Object listL) {
		this.listL = listL;
	}

	/**
	 * 设置左边列表框的value
	 * @param listKeyL the listKeyL to set
	 */
	public void setListKeyL(String listKeyL) {
		this.listKeyL = listKeyL;
	}

	/**
	 * 设置左边列表框的文本
	 * @param listValueL the listValueL to set
	 */
	public void setListValueL(String listValueL) {
		this.listValueL = listValueL;
	}

	/**
	 * 设置右边列表框的数据
	 * @param listR the listR to set
	 */
	public void setListR(Object listR) {
		this.listR = listR;
	}

	/**
	 * 设置右边列表框的value
	 * @param listKeyR the listKeyR to set
	 */
	public void setListKeyR(String listKeyR) {
		this.listKeyR = listKeyR;
	}

	/**
	 * 设置右边列表框的text
	 * @param listValueR the listValueR to set
	 */
	public void setListValueR(String listValueR) {
		this.listValueR = listValueR;
	}

	/**
	 * 设置多选下拉框组件返回值是否为字符串类型;true为字符串型(String),false为数值型;默认为（false）
	 * @param charTypeValue the charTypeValue to set
	 */
	public void setCharTypeValue(String charTypeValue) {
		this.charTypeValue = charTypeValue;
	}

	/**
	 * 设置左边列表框的标题
	 * @param titleL the titleL to set
	 */
	public void setTitleL(String titleL) {
		this.titleL = titleL;
	}

	/**
	 * 设置右边列表框的标题
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
