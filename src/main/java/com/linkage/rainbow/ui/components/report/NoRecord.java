package com.linkage.rainbow.ui.components.report;




import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * NoRecord<br>
 * 查无记录时,显示信息.
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
public class NoRecord extends ClosingUIBean {
	/**
	 * 定义无记录显示信息组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "report/no-record";
	/**
	 * 定义无记录显示信息组件的结束模板 
	 */
	public static final String TEMPLATE = "report/no-record";
	String templateShort ;

	String noRecordMsg ;//查无记录时显示信息
	
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return NoRecord
	 */
	public NoRecord(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
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
	 * 是否使用标签体信息
	 * @return boolean
	 */
	@Override
    public boolean usesBody() {
        return true;
    }

	/**
	 * 将参数信息设置到模板
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
		if(noRecordMsg != null){
			Table table =  (Table)findAncestor(Table.class);
			if(table != null){
				table.setNoRecordMsg(findString(noRecordMsg));
			}
		}
        return false;
    }

	/**
	 * 将标签类信息设置到组件类
	 */
	protected void evaluateExtraParams() {

		
	}

	/**
	 * 获取无记录的提示信息
	 * @return String
	 */
	public String getNoRecordMsg() {
		return noRecordMsg;
	}

	/**
	 * 设置无记录的提示信息
	 * @param noRecordMsg
	 */
	public void setNoRecordMsg(String noRecordMsg) {
		this.noRecordMsg = noRecordMsg;
	}



}
