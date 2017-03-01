package com.linkage.rainbow.ui.components.multiSelectBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ListUIBean;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * MultiSelectBox<br>
 * ���ڽ�������ѡ����ı�ǩ���Ը����ģ��������
 * chenwei
 * @version 1.0
 */
public class MultiSelectBox extends ListUIBean{
	/**
	 * ����MultiSelectBox�������ʼģ��
	 */
	public static final String OPEN_TEMPLATE = "multiSelectBox/multiSelectBox";// ��ѡ�������ǩģ��
	/**
	 * ����MultiSelectBox����Ľ���ģ��
	 */
	public static final String CLOSE_TEMPLATE = "multiSelectBox/multiSelectBox-close";// ��ѡ����������ǩģ��

	private String skin = "default";//���Ƥ����Ĭ��Ϊ��ɫ
	private String style ;            //�����������
	private String header;            //���Ĭ����ʾȫѡ���ȫѡ��
	private String selectedList;      //��������ı���Ĭ����ʾѡȡֵ�ĸ���
	private String noneSelectedText; //��������ı���Ĭ����ʾ�ı���Ϣ
	private String checkAllText;     //����ȫѡ�ı���Ϣ
	private String uncheckAllText;   //���÷�ȫѡ�ı���Ϣ
	private String layerWidth;       //���ѡ�����Ŀ��
	private String method;           //�첽load�������ѡֵ����
	private String params;           //����springbena.������ʱ���ݵ����
	private String loadMode;         //���������б�ģʽ
	private String width;
	private String layerHeight;

	/**
	 * �̳и���ClosingUIBean�Ļ���Ϣ<br>
	 * @param stack --ֵջ���� 
	 * @param request --http����
	 * @param response --http�ظ�
	 * @return MultiSelectBox
	 */
	public MultiSelectBox(ValueStack stack, HttpServletRequest request,
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
		if (skin != null) 
			addParameter("skin", findString(skin));
		if (style != null) 
			addParameter("style", findString(style));
		if (header != null) 
			addParameter("header", findString(header));
		if (selectedList != null) 
			addParameter("selectedList", findString(selectedList));
		if (noneSelectedText != null) 
			addParameter("noneSelectedText", findString(noneSelectedText));
		if (checkAllText != null) 
			addParameter("checkAllText", findString(checkAllText));
		if (uncheckAllText != null) 
			addParameter("uncheckAllText", findString(uncheckAllText));
		if (layerWidth != null) 
			addParameter("layerWidth", findString(layerWidth));
		if (method != null)
			addParameter("method", findString(method));
		if (params != null)
			addParameter("params", findString(params));
		if (loadMode != null)
			addParameter("loadMode",  findValue(loadMode,Boolean.class));
		if (width != null)
			addParameter("width",  findString(width));
		if (layerHeight != null)
			addParameter("layerHeight",  findString(layerHeight));
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
	 *        ����ģ���ŵ�Ŀ¼�������ǩ�ĵ��÷�ʽ,һ����ǩ��Ӧһ��Ŀ¼�洢,�����ڱ�ǩ��Ӧģ���Ŀ¼Ϊtemplate/xhtml/date
	 *        һ����ǩ��������ʵ�ַ�ʽ,�����ģ����ʵ��,����template/xhtml/date
	 *        Ŀ¼���ٽ�����Ŀ¼,��:WdatePicker,����ֲ�ͬ��ģ��ʵ��. �ڱ�ǩ����ʱ,����Ҫ�ֱ�����template ��
	 *        openTemplateָ����һ��ģ��,ֻ��Ҫ����templateֵ,������Ŀ¼��� WdatePicker ����.
	 */
	@Override
	public void setTemplate(String template) {
		if (template != null && template.trim().length() > 0) {
			this.template = "multiSelectBox/" + template
					+ "/multiSelectBox";
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}

	public String getNoneSelectedText() {
		return noneSelectedText;
	}

	public void setNoneSelectedText(String noneSelectedText) {
		this.noneSelectedText = noneSelectedText;
	}

	public String getCheckAllText() {
		return checkAllText;
	}

	public void setCheckAllText(String checkAllText) {
		this.checkAllText = checkAllText;
	}

	public String getUncheckAllText() {
		return uncheckAllText;
	}

	public void setUncheckAllText(String uncheckAllText) {
		this.uncheckAllText = uncheckAllText;
	}
	public String getLayerWidth() {
		return layerWidth;
	}
	public void setLayerWidth(String layerWidth) {
		this.layerWidth = layerWidth;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
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

	public String getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(String layerHeight) {
		this.layerHeight = layerHeight;
	}
}
