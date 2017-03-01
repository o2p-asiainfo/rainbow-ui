package com.linkage.rainbow.ui.components.loadmask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;
import com.linkage.rainbow.ui.components.UIComponentBase;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Date<br>
 * 进度条
 * <p>
 * @version 1.0
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: wanglm7
 *    修改时间: 2013-05-21 14:29:43  <br>       
 *    修改内容: create
 * <hr>
 */
@StrutsTag(name = "loadmask", tldTagClass = "com.linkage.rainbow.ui.views.jsp.ui.loadmask.LoadMaskTag", description = "进度条组件类")
public class LoadMask extends UIComponentBase {
	
	private String skin ;
	
	/**
	 * 定义进度条标签的起始模板
	 */
	public static final String OPEN_TEMPLATE = "loadmask/simple/loadmask";
	/**
	 * 定义进度条标签的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "loadmask/simple/loadmask-close";


	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param ValueStack --值栈对象 
	 * @param HttpServletRequest --http请求
	 * @param HttpServletResponse --http回复
	 * @return Date
	 */
	public LoadMask(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * 获取进度条标签模版
	 * @return String 进度条标签模版
	 */
	@Override
	public String getDefaultOpenTemplate() {
		
		return OPEN_TEMPLATE;
	}

	/**
	 * 获取进度条标签结束模版
	 * @return String 进度条标签结束模版
	 */
	@Override
	protected String getDefaultTemplate() {
		
		return CLOSE_TEMPLATE;
		
	}
	
    /**
     * 指定模板
     * @author 陈亮 2009-02-11
     * @param template 模板名.
     * 例:一模板存放的完整路径是 template/xhtml/date/WdatePicker/date.ftl
     * 其中:
     * template 为模板存放主目录,对应的标签属性是: templateDir ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.templateDir默认值.
     * xhtml    为主题名称,对应的标签属性是: theme ,如果标签没有设置,则取struts.propertites文件中配置的struts.ui.theme默认值.
     * .ftl     为模板文件的后缀名,取struts.propertites文件中配置的struts.ui.templateSuffix默认值.
     * date/WdatePicker/date 为模板名称,
     *          对应的标签属性是: template 与 openTemplate ,如果标签没有设置,则通过此类的getDefaultOpenTemplate() 与getDefaultTemplate()分别取得标签的开始模板与结束模板.
     * 便于模板存放的目录清晰与标签的调用方式,一个标签对应一个目录存储,例日期标签对应模板的目录为template/xhtml/date
     * 一个标签可能有种实现方式,即多个模板来实现,则在template/xhtml/date 目录下再建立子目录,如:WdatePicker,来区分不同的模板实现.
     * 在标签调用时,不需要分别设置template 与 openTemplate指定新一组模板,只需要设置template值,传入子目录名称 WdatePicker 即可.
     */
    @StrutsTagAttribute(description="The template (other than default) to use for rendering the element")
    @Override
    public void setTemplate(String template) {
    	if(template != null && template.trim().length()>0) {
	        this.template = "loadmask/"+template+"/loadmask-close";
	        this.setOpenTemplate("loadmask/"+template+"/loadmask");
    	} else {
    		template = null;
    	}
    	
    }
    
    /**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		
		if (skin != null) {
			addParameter("skin", findString(skin));
		}
		addParameter("tagUtil", new ComponentUtil(request));
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}
	
}
