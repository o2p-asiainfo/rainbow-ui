package com.linkage.rainbow.ui.components.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.ui.components.form.Form;
import com.linkage.rainbow.ui.util.ComponentUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 验证组件,用户纯客户端校验<br>
 * 此组件用于输入公共的校验脚本与包含各字段验证组件.
 * <p>
 * @version 1.0
 * <hr>
 */

public class Validators extends ClosingUIBean {
	/**
	 * 定义validator组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "validator/validators";//验证标签模版
	/**
	 * 定义validator组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "validator/validators-close";//验证结束标签模版

	private List<Map> validators = new ArrayList<Map> ();
	
	private String validateAlert ;//验证提示方式
	private String formId; //表单ID
	private String validatorGroup ; //校验组,可按不同组进行校验
	/**
	 * 继承父类ClosingUIBean的基本信息<br>
	 * @param stack --值栈对象 
	 * @param request --http请求
	 * @param response --http回复
	 * @return Validators
	 */
	public Validators(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
 
	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if(validatorGroup != null)
			addParameter("validatorGroup", findString(validatorGroup));
		
		addParameter("tagUtil", new ComponentUtil(request));
		addParameter("faileLabel", "验证失败"); //解决在utf-8环境下乱码问题,模板内文件不显示中文.
		if(formId != null && formId.trim().length()>0)
			addParameter("formId", formId);
		else {
			Form form1 =  (Form)findAncestor(Form.class);
			if (form1 != null) {
                addParameter("formId", form1.getId());
            }else {
            	org.apache.struts2.components.Form form =  (org.apache.struts2.components.Form)findAncestor(org.apache.struts2.components.Form.class);
            	if (form != null) {
                    addParameter("formId", form.getId());
                }
            }
		}
		addParameter("validateAlert", findString(validateAlert));
		
	}
	

	/**
	 * 获取验证标签模版
	 * @param 
	 * @return String 验证标签模版
	 */
	@Override
	public String getDefaultOpenTemplate() {
		
		return OPEN_TEMPLATE;
	}

	/**
	 * 获取验证标签结束模版
	 * @param
	 * @return String 验证标签结束模版
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
	        this.template = "validator/"+template+"/validator-close";
	        this.setOpenTemplate("validator/"+template+"/validators");
    	} else {
    		template = null;
    	}
    }

    /**
     * 获取验证提示方式
     * @return
     */
	public String getValidateAlert() {
		return validateAlert;
	}

	/**
	 * 设置验证提示方式
	 * @param validateAlert
	 */
	public void setValidateAlert(String validateAlert) {
		this.validateAlert = validateAlert;
	}

	/**
	 * 获取表单id
	 * @return
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * 设置表单id
	 * @param formId
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	/**
	 * 增加验证
	 * @param validator
	 */
	public void addValidator(Map validator){
		int iSize = validators.size();
		int iInsertIndex=-1;
		for(int i=0;i<iSize;i++){
			if(validators.get(i)==validator)
				return;
		}
		Map oldValidator = null;
		for(int i=0;i<iSize;i++){
			if(validators.get(i).get("fieldId").equals(validator.get("fieldId"))){
				if(oldValidator == null){
					oldValidator = validators.get(i);
				}
				if(validator.get("validatorType").equals("requiredstring")||validator.get("validatorType").equals("required")){
					iInsertIndex = i;
					break;
				} else {
					iInsertIndex = i+1;
					if(oldValidator != null && validator.get("required")!= null && (Boolean)validator.get("required")){
						Object required = validator.get("required");
						validator.remove("required");
						oldValidator.put("required",required);
					}
				}
			} 
		}
		if(iInsertIndex >-1 && iInsertIndex<iSize)
			validators.add(iInsertIndex,validator);
		else
			validators.add(validator);
		
	}
	
	/**
	 * 获取待验证对象集合
	 * @return
	 */
	public List<Map> getValidators() {
		return validators;
	}

	
   
    
    int currIndex=0;
    
    /**
     * 调用替换特殊字符
     * @param str
     * @param start
     * @return
     */
	public static int findQuotationMark(String str, int start) {
		char escapeChar = '\\';
		return findQuotationMark(str, start, escapeChar);
	}

	/**
	 * 替换特殊字符
	 * @param str
	 * @param start
	 * @param escapeChar
	 * @return int
	 */
	public static int findQuotationMark(String str, int start, char escapeChar) {
		char quote = str.charAt(start);
		if (quote != '"' && quote != '\'')
			return -1;
		int idx = start + 1;
		for (int len = str.length(); idx < len;) {
			char ch = str.charAt(idx);
			if (ch == escapeChar) {
				idx += 2;
			} else {
				if (ch == quote)
					return idx;
				idx++;
			}
		}
		return -1;
	}
	/**
	 * 检测是否包含特殊字符
	 * @param expStr
	 * @return String
	 */
	protected  String scanId(String expStr) {
		int len = expStr.length();
		int begin = currIndex;
		for (; currIndex < len; currIndex++) {
			char c = expStr.charAt(currIndex);
			if (Character.isSpaceChar(c) || c == '+' || c == '-' || c == '*'
					|| c == '/' || c == '=' || c == '&' || c == '|' || c == '!'
					|| c == '>' || c == '<' || c == '(' || c == ')')
				break;
		}

		return expStr.substring(begin, currIndex);
	}

	/**
	 * 替换验证的特殊字符
	 * @param expStr
	 * @return String
	 */
	protected  String createSign(String expStr) {
		String newExpStr="";
		char c = expStr.charAt(currIndex);
		int match = -1;
		if (c == '"' || c == '\'') {
			match = findQuotationMark(expStr, currIndex);
			if (match == -1) {
				throw new RuntimeException("引号不匹配");
			} else {
				newExpStr += expStr.substring(currIndex + 1, match);
				currIndex = match + 1;
				return newExpStr;
			}
		}

		String id = scanId(expStr);
		if (id.equalsIgnoreCase("and"))
			newExpStr += "&&";
		else if (id.equalsIgnoreCase("or"))
			newExpStr += "||";
		else if (id.equalsIgnoreCase("not"))
			newExpStr += "!";
		else{//为常量
			newExpStr+="$(\"#"+id+"\").get(0).value";
		}
		return newExpStr;
	}
	/**
	 * 创建验证表达式
	 * @param expStr
	 * @return String
	 */
    public synchronized String createExpression(String expStr) {
    	currIndex=0;
    	String newExpStr="";
		int len = expStr.length();
		
		int inBrackets = 0;
		while (currIndex < len) {
			char c = expStr.charAt(currIndex);
			if (Character.isSpaceChar(c)) {
				currIndex++;
				continue;
			}
			switch (c) {
			case '(': // '('
				inBrackets++;
				currIndex++;
				newExpStr+=c;
				continue;

			case ')': // ')'
				if (--inBrackets < 0)
					throw new RuntimeException("括号不匹配，右括号太多！");
				currIndex++;
				newExpStr+=c;
				continue;

			case '+': // '+'
				currIndex++;
				newExpStr+=c;
				break;

			case '-': // '-'
				currIndex++;
				newExpStr+=c;
				break;

			case '*': // '*'
				currIndex++;
				newExpStr+=c;
				break;

			case '/': // '/'
				currIndex++;
				newExpStr+=c;
				break;

			case '=': // '='
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=')
						currIndex++;
				}
				newExpStr+="==";
				break;

			case '!': // '!'
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=') {
						currIndex++;
						newExpStr+="!=";
					}
				}
				newExpStr+=c;
				break;

			case '>': // '>'
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=') {
						currIndex++;
						newExpStr+=">=";
					}
				}
				newExpStr+=c;
				break;

			case '<': // '<'
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=') {
						currIndex++;
						newExpStr+="<=";
					}
				}
				newExpStr+=c;
				break;

			case '&': // '&'
				currIndex++;
				if (currIndex < len && expStr.charAt(currIndex) == '&') {
					currIndex++;
					newExpStr+="&&";
				} else {
					newExpStr+=c;
				}
				break;

			case '|': // '|'
				currIndex++;
				if (currIndex < len && expStr.charAt(currIndex) == '|') {
					currIndex++;
					newExpStr+="||";
				} else {
					throw new RuntimeException("不能识别标识符|");
				}
				break;
			case '%': // '%'
				currIndex++;
				newExpStr+=c;
				break;

			case '.': // '.'
				currIndex++;
				newExpStr+=c;
				break;

			case ',': // ','
				throw new RuntimeException("位置" + currIndex + "不应该出现逗号");

			default:
				newExpStr += createSign(expStr);
				break;
			}
			
		}
		if (inBrackets > 0) {
			throw new RuntimeException("括号不匹配，左括号太多！");
		} else {
			return newExpStr;
		}
	}

    /**
     * 获取验证组合
     * @return String
     */
	public String getValidatorGroup() {
		return validatorGroup;
	}

	/**
	 * 设置验证组合
	 * @param validatorGroup
	 */
	public void setValidatorGroup(String validatorGroup) {
		this.validatorGroup = validatorGroup;
	}

}
