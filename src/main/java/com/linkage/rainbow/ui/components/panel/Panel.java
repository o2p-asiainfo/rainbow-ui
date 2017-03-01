package com.linkage.rainbow.ui.components.panel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.linkage.rainbow.ui.components.UIComponentBase;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * EasyUIPanel<br>
 * EasyUI面板组件类，主要提供对面板的属性的操作
 * chenwei
 * @version 1.0
 */
@StrutsTag(name = "panel", tldTagClass = "com.linkage.rainbow.ui.views.jsp.ui.panel.PanelTag", description = "面板组件类")
public class Panel extends UIComponentBase{
	/**
	 * 定义easyuiPanel组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "panel/easyuiPanel";
	/**
	 * 定义easyuiPanel组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "panel/easyuiPanel-close";
	private String skin;    //组件皮肤：默认为蓝色
	private String width;   //面板宽度
	private String height;  //面板高度
	private String title;   //面板标题
	private String collapsible; //面板是否可折叠
	private String minimizable; //面板是否可最小化
	private String maximizable; //面板是否可最大化
	private String closable;    //面板是否可关闭
	private String tools;       //是否使用面板最定义工具条
	private String iconCls;     //标题栏上图标样式
	private String handlerFunc; //自定义工具条上处理函数(譬如：新增面板、删除面板操作)
	private String fit;         //是否填充以适应其父容器
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return EasyUIPanel
	 */
	public Panel(ValueStack stack, HttpServletRequest request,HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * 将面板类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(skin != null)
			addParameter("skin",  findString(skin));
		if(width != null)
			addParameter("width",  findString(width));
		if(height != null)
			addParameter("height",  findString(height));
		if(title != null)
			addParameter("title",  findString(title));
		if(collapsible != null)
			addParameter("collapsible",  findValue(collapsible,Boolean.class));
		if(minimizable != null)
			addParameter("minimizable",  findValue(minimizable,Boolean.class));
		if(maximizable != null)
			addParameter("maximizable",  findValue(maximizable,Boolean.class));
		if(closable != null)
			addParameter("closable",  findValue(closable,Boolean.class));
		if(tools != null)
			addParameter("tools",  findValue(tools,Boolean.class));
		if(fit != null)
			addParameter("fit",  findValue(fit,Boolean.class));
		if(iconCls != null)
			addParameter("iconCls",  findString(iconCls));
		if(handlerFunc != null)
			addParameter("handlerFunc",  findString(handlerFunc));
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
		if (template != null && template.trim().length() > 0) {
			this.template = "panel/"+template+"/easyuiPanel-close";
	        this.setOpenTemplate("panel/"+template+"/easyuiPanel");
		} else {
			template = null;
		}
	}
	
	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	public String getFit() {
		return fit;
	}

	public void setFit(String fit) {
		this.fit = fit;
	}
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCollapsible() {
		return collapsible;
	}

	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	public String getMinimizable() {
		return minimizable;
	}

	public void setMinimizable(String minimizable) {
		this.minimizable = minimizable;
	}

	public String getMaximizable() {
		return maximizable;
	}

	public void setMaximizable(String maximizable) {
		this.maximizable = maximizable;
	}

	public String getClosable() {
		return closable;
	}

	public void setClosable(String closable) {
		this.closable = closable;
	}

	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getHandlerFunc() {
		return handlerFunc;
	}

	public void setHandlerFunc(String handlerFunc) {
		this.handlerFunc = handlerFunc;
	}

}
