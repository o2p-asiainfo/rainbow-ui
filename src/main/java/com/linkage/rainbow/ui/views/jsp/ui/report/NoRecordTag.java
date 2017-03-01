package com.linkage.rainbow.ui.views.jsp.ui.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import  com.linkage.rainbow.ui.components.report.NoRecord;
import  com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * NoRecordTag查无记录时,显示信息<br>
 * <p>
 * @version 1.0
 * @author 陈亮 2009-07-30
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-07-30<br>
 *         修改内容:新建
 *         <hr>
 */

public class NoRecordTag extends AbstractUITag {
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
	public NoRecordTag(){
		DefaultSettings.setDefaultValue(this,"comm.rp.noRecord");
	}

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new NoRecord(stack, req, res);
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		Object obj =getBodyContent();
		String content = getBody();
		NoRecord comp = (NoRecord) component;
		comp.setNoRecordMsg(content);
		 super.doEndTag();
		 return EVAL_PAGE;
	}
	
	protected void populateParams() {
		
		super.populateParams();

		NoRecord comp = (NoRecord) component;
	}

}
