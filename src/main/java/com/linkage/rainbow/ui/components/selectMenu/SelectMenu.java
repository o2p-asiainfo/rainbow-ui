package com.linkage.rainbow.ui.components.selectMenu;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.ListUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * SelectMenu<br>
 * ���ڽ���-������ı�ǩ���Ը����ģ��������
 * chenwei
 * @version 1.0
 */
public class SelectMenu extends ListUIBean{
	/**
	 * ����SelectMenu�������ʼģ��
	 */
	public static final String OPEN_TEMPLATE = "selectMenu/select";// ��-���ǩģ��
	/**
	 * ����SelectMenu����Ľ���ģ��
	 */
	public static final String CLOSE_TEMPLATE = "selectMenu/select-close";// ��-������ǩģ��
	
	private static Log log = LogFactory.getLog(SelectMenu.class);
	private String skin;    //Ƥ��
	private String style ;  //�����-���������ʽ
	private String onchange;//�����-����-�¼�
	private String layerWidth; //������-���б�� ��� 
	private String layerHeight;//������-���б��߶�
	private String method;           //�첽load��-���ѡֵ����
	private String params;           //����springbena.������ʱ���ݵ����
	private String loadMode;         //������-�б�ģʽ
	private String width;            //��������ı�����
	private String headerValue;
	private String emptyOption;
	private String disabled;
	
	public String getHeaderValue() {
		return headerValue;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public String getEmptyOption() {
		return emptyOption;
	}

	public void setEmptyOption(String emptyOption) {
		this.emptyOption = emptyOption;
	}

	/**
	 * �̳и���ClosingUIBean�Ļ���Ϣ<br>
	 * @param stack --ֵջ���� 
	 * @param request --http����
	 * @param response --http�ظ�
	 * @return MultiCheckListBox
	 */
	public SelectMenu(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	/**
	 * ����ǩ�����������������࣬�ṩ��ģ�����<br>
	 * @return void
	 */
	public void evaluateExtraParams() {
		super.throwExceptionOnNullValueAttribute = false;
		super.evaluateExtraParams();
		addParameter("tagUtil", new ComponentUtil(request));
		if (style != null) 
			addParameter("style", findString(style));
		if (skin != null) 
			addParameter("skin", findString(skin));
		if (onchange != null) 
			addParameter("onchange", findString(onchange));
		if (layerWidth != null) 
			addParameter("layerWidth", findString(layerWidth));
		if (method != null) 
			addParameter("method", findString(method));
		if (layerHeight != null) 
			addParameter("layerHeight", findString(layerHeight));
		if (params != null) 
			addParameter("params", findString(params));
		if (loadMode != null) 
			addParameter("loadMode",  findValue(loadMode,Boolean.class));
		if (width != null)
			addParameter("width",findString(width));
		if (emptyOption != null) 
			addParameter("emptyOption",  findValue(emptyOption,Boolean.class));
		if (headerValue != null)
			addParameter("headerValue",findString(headerValue));
		if (disabled != null)
			addParameter("disabled",findString(disabled));
		
		
		java.util.List list = (java.util.List)getParameters().get("list");
		
		String paras = (String)getParameters().get("params");
		List<Object> returnList = new ArrayList<Object>();
		String listKeyStr = (String)getParameters().get("listKey");
		String listValueStr = (String)getParameters().get("listValue");
		
		if (StringUtils.isNotEmpty(paras) && paras.indexOf(":")!= -1){
			String[] paraStr = paras.split(":");
			if (list!=null && list.size()>0){
				Map<String,Object> map = null;
				for (int i=0;i<list.size();i++){
					 map = new HashMap<String,Object>();
					if (list.get(0) instanceof Map) { //Map�ṹ
						Map newmap = (Map)list.get(i);
						String paraValue = newmap.get(paraStr[0]).toString();
						if (StringUtils.isNotEmpty(paraValue) && paraValue.equals(paraStr[1])){
							map.put(listKeyStr, newmap.get(listKeyStr));
							map.put(listValueStr, newmap.get(listValueStr));
							returnList.add(map);
						}
					}else{ //javaBean
						try {
							Object obj = (Object)list.get(i);
							Class cls = obj.getClass();
							String paraMethodName = "get"+this.toUpperCaseFirstOne(paraStr[0]);
							String keyMethodName = "get"+this.toUpperCaseFirstOne(listKeyStr);
							String valMethodName = "get"+this.toUpperCaseFirstOne(listValueStr);
							Method meth3 = cls.getMethod(paraMethodName, new Class[] {});
							Object obj3 = meth3.invoke(obj, new Object[] {});
							
							Method meth1 = cls.getMethod(keyMethodName, new Class[] {});
							Object obj1 = meth1.invoke(obj, new Object[] {});
							Method meth2 = cls.getMethod(valMethodName, new Class[] {});
							Object obj2 = meth2.invoke(obj, new Object[] {});
							String key =obj1.toString(); 
							String value =obj2.toString();
							String paravalue = obj3.toString();
							if (StringUtils.isNotEmpty(paravalue) && paravalue.equals(paraStr[1])){
								map.put(listKeyStr, key);
								map.put(listValueStr, value);
								returnList.add(map);
							}
						} catch (Exception e) {
							log.error(e.getStackTrace());
						}
					}
				}
			}
			addParameter("list",returnList);
		}
		//System.out.println("&&&&&&&&&&&[namevalue]&&&&&&&&&&&&&&"+getParameters().get("nameValue"));
	}
	
	public  String toUpperCaseFirstOne(String s){        
		if(Character.isUpperCase(s.charAt(0)))            
			return s;        
		else            
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();    
	}
	/**
	 * ָ��ģ��
	 * @author ���� 2009-02-11
	 * @param template ģ����. ��:һģ���ŵ�����·����
	 *        template/xhtml/date/WdatePicker/date.ftl ����: template
	 *        Ϊģ������Ŀ¼,��Ӧ�ı�ǩ������: templateDir
	 *        ,����ǩû������,��ȡstruts.propertites�ļ������õ�struts.ui.templateDirĬ��ֵ.
	 *        xhtml Ϊ�������,��Ӧ�ı�ǩ������: theme
	 *        ,����ǩû������,��ȡstruts.propertites�ļ������õ�struts.ui.themeĬ��ֵ. .ftl
	 *        Ϊģ���ļ��ĺ�׺��,ȡstruts.propertites�ļ������õ�struts.ui.templateSuffixĬ��ֵ.
	 *        date/WdatePicker/date Ϊģ�����, ��Ӧ�ı�ǩ������: template �� openTemplate
	 *        ,����ǩû������,��ͨ������getDefaultOpenTemplate()
	 *        ��getDefaultTemplate()�ֱ�ȡ�ñ�ǩ�Ŀ�ʼģ�������ģ��.
	 *        ����ģ���ŵ�Ŀ¼�������ǩ�ĵ��÷�ʽ,һ���ǩ��Ӧһ��Ŀ¼�洢,�����ڱ�ǩ��Ӧģ���Ŀ¼Ϊtemplate/xhtml/date
	 *        һ���ǩ��������ʵ�ַ�ʽ,�����ģ��4ʵ��,����template/xhtml/date
	 *        Ŀ¼���ٽ�b��Ŀ¼,��:WdatePicker,4��ֲ�ͬ��ģ��ʵ��. �ڱ�ǩ����ʱ,����Ҫ�ֱ�����template ��
	 *        openTemplateָ����һ��ģ��,ֻ��Ҫ����templateֵ,������Ŀ¼��� WdatePicker ����.
	 */
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "selectMenu/" + template
					+ "/select";
			
		} else {
			template = null;
		}
	}
	
	/**
	 * �̳��Ի��࣬����д�����ڻ�ȡ��ʼģ��<br>
	 * @return String
	 */
	@Override
	protected String getDefaultTemplate() {
		return OPEN_TEMPLATE;
	}
	
	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getLayerWidth() {
		return layerWidth;
	}

	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	public String getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getLoadMode() {
		return loadMode;
	}

	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
