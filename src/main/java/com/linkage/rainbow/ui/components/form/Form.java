package com.linkage.rainbow.ui.components.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.linkage.rainbow.ui.util.ComponentUtil;
import com.linkage.rainbow.util.StringUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.ActionValidatorManager;
import com.opensymphony.xwork2.validator.FieldValidator;
import com.opensymphony.xwork2.validator.Validator;
import org.apache.log4j.Logger; 
/**
 * Form<br>
 * 表单组件，提供表单提交与验证
 * <p>
 * @version 1.0
 * <hr>
 */
@StrutsTag(name = "form", tldTagClass = "com.linkage.rainbowon.views.jsp.ui.form.FormTag", description = "表单组件类")
public class Form extends org.apache.struts2.components.Form {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(com.linkage.rainbow.ui.components.form.Form.class);
	
	/**
	 * 定义表单组件的起始模板
	 */
	public static final String OPEN_TEMPLATE = "form/form";
	
	/**
	 * 定义表单组件的结束模板
	 */
	public static final String CLOSE_TEMPLATE = "form/form-close";

	
	private String validateAlert ;//验证提示方式
	
	/**
	 * 设置表单组件
	 * @param ActionValidatorManager
	 * @return void
	 */
	public void setActionValidatorManager(ActionValidatorManager mgr) {
        this.actionValidatorManager = mgr;
    }
    
    
	/**
	 * 继承父类Form的基本信息<br>
	 * @param stack
	 * @param request
	 * @param response
	 * @return void
	 */
	public Form(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
 

	/**
	 * 将标签类的属性设置予组件类，提供予模板调用<br>
	 * @return void
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("tagUtil", new ComponentUtil(request));
		if (validateAlert != null) 
			addParameter("validateAlert", findString(validateAlert));
	}

	/**
	 * 获取默认模版
	 * @return String 获取默认模版
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	/**
	 * 获取默认模版
	 * @return String 获取默认模版
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
	        this.template = "form/"+template+"/form-close";
	        this.setOpenTemplate("form"+template+"/form");
    	} else {
    		template = null;
    	}
    }

    /**
     * 获取验证提示方式
     * @return String
     */
	public String getValidateAlert() {
		return validateAlert;
	}

	/**
	 * 设置验证提示
	 * @param validateAlert
	 * @return void
	 */
	public void setValidateAlert(String validateAlert) {
		this.validateAlert = validateAlert;
	}

    /**
     * 取得所有验证规则
     * @return List --返回验证对象结果集
     */
    public List getValidators() {
        Class actionClass = (Class) getParameters().get("actionClass");
        if (actionClass == null) {
        	logger.debug("actionClass == null");
            return Collections.EMPTY_LIST;
        }

        List<Validator> all =null;
        if(actionValidatorManager != null){
        	logger.debug("actionValidatorManager.getValidators(actionClass, (String) getParameters().get(\"actionName\"))");
        	all = actionValidatorManager.getValidators(actionClass, (String) getParameters().get("actionName"));
        } else {
        	logger.debug("all = com.opensymphony.xwork2.validator.ActionValidatorManagerFactory.getInstance().getValidators(actionClass, (String) getParameters().get(\"actionName\"));");
        	all = new com.opensymphony.xwork2.validator.DefaultActionValidatorManager().getValidators(actionClass, (String) getParameters().get("actionName"));	
        }
        try {
        	 logger.debug("Validatorall:"+StringUtil.collectionToString(all));
		} catch (Exception e) {
			// TODO: handle exception
		}
       
        List<Validator> validators = new ArrayList<Validator>();
        for (Validator validator : all) {
            if (validator instanceof FieldValidator) {
                FieldValidator fieldValidator = (FieldValidator) validator;
                validators.add(fieldValidator);
            }
        }
        
        try {
       	 logger.debug("FieldValidators:"+StringUtil.collectionToString(all));
		} catch (Exception e) {
			// TODO: handle exception
		}

        return validators;
    }

 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    int currIndex=0;
    
    /**
     * 查询过滤字符 调用待过滤字符串
     * @param str --表达式
     * @param start --起始位置
     * @return int
     */
	public static int findQuotationMark(String str, int start) {
		char escapeChar = '\\';
		return findQuotationMark(str, start, escapeChar);
	}

	/**
	 * 判断是否有验证表达式
	 * @param str --字符串
	 * @param start --起始位置
	 * @param escapeChar --忽略字符
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
	 * 截取验证字符串
	 * @param expStr --验证表达式
	 * @return String --验证结果
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
	 * 创建验证标识
	 * @param expStr --表达式
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
		else  if (id.equalsIgnoreCase("not"))
			newExpStr += "!";
		else{//为常量
			try {
				id = id.replaceAll("\\.","_").replaceAll("\\[","_").replaceAll("\'","").replaceAll("\\]","");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			newExpStr+="$(\"#"+id+"\").get(0).value";
		}
		return newExpStr;
	}

	/**
	 * 验证表达式
	 * @param expStr --表达式
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
    public static void main(String[] args) {
		String id="fieldExpressionValidatorField";
		id = id.replaceAll("\\.","_").replaceAll("\\[","_").replaceAll("\'","").replaceAll("\\]","");
		
		System.out.println(id);
	}
    
    

	
}