package com.linkage.rainbow.ui.components.tabpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * TabPageDiv<br>
 * EasyUI标签页组件类，主要提供对标签页的属性的操作
 * chenwei
 * @version 1.0
 * <hr>
 */
@StrutsTag(name = "tabpage", tldTagClass = "com.linkage.rainbow.views.jsp.ui.tabpage.TabPageDiv", description = "标签页组件类")
public class TabPageDiv extends UIComponentBase {
	/**
	 * 定义tabpagediv组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "tabpage/tabpagediv";//标签页标签模版
	/**
	 * 定义tabpagediv组件的起始模板
	 */
	public static final String CLOSE_TEMPLATE = "tabpage/tabpagediv-close";//标签页标签模版

	private String title;
	private String href;
	private String pageid;
	private String tabid;
	private String loadMode;
	private String closable;//是否可关闭本标签页
	private String iconCls;
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return TabPageDiv
	 */
	public TabPageDiv(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(title != null)
			addParameter("title",  findString(title));
		if(href!= null)
			addParameter("href",findString(href));
		if(tabid!=null)
			addParameter("tabid",findString(tabid));
		if(pageid!=null)
			addParameter("pageid",findString(pageid));
		if(loadMode != null)
			addParameter("loadMode",findString(loadMode));
		else {
			TabPage parent = (TabPage) findAncestor(TabPage.class);
			addParameter("loadMode",parent.getLoadMode());
		}
		
		if(closable != null)
			addParameter("closable",  findValue(closable,Boolean.class));
		
		if(iconCls != null)
			addParameter("iconCls",  findString(iconCls));
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
		return CLOSE_TEMPLATE;
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
		TabPage parent = (TabPage) findAncestor(TabPage.class);
		template=parent.getTemproot();
		if(template != null && template.trim().length()>0) {
			this.template = "tabpage/"+template+"/tabpagediv-close";
		    this.setOpenTemplate("tabpage/"+template+"/tabpagediv");
	    } else {
	    	template = null;
	    }
	}

	/**
	 * 设置标签页的标题
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**设置标签页的超链接
	 * @param url the url to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * 设置标签页的id
	 * @param pageid the pageid to set
	 */
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	/**
	 * 设置父tab的id
	 * @param tabid the tabid to set
	 */
	public void setTabid(String tabid) {
		TabPage parent = (TabPage) findAncestor(TabPage.class);
		this.tabid = parent.getTabid();
	}

	/**
	 * 根据tab的模式设置当前标签页的模式 是iframe或者是div
	 * @param mode the mode to set
	 */
	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}

	/**
	 * 设置是否有关闭按钮
	 * @param enableTabClose
	 */
	public void setClosable(String closable) {
		this.closable = closable;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
}
