package com.linkage.rainbow.ui.views.jsp;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.jsp.StrutsBodyTagSupport;

import com.opensymphony.xwork2.ognl.OgnlValueStack;

/**
 * <p>给活动类里的属性属值</p>
 *
 * @version 	0.0.1
 */
public class SetPropertyTag extends StrutsBodyTagSupport {
    private static final Log log = LogFactory.getLog(SetPropertyTag.class);

    
    private String name;	//值栈中的属性名
    private String value;

    /**
     * 将属性设置到值栈中
     * @return int
     */
    public int doStartTag() throws JspException {
        try {
        	OgnlValueStack stack = (OgnlValueStack) getStack();
        	stack.setValue(name,stack.findValue(value, String.class));

        } catch (Exception e) {
            log.info("set value error! property:'" + name + "': " + e.getMessage());
        }

        return SKIP_BODY;
    }
    
    /**
     * 设置值栈中的属性名
     * @param String
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 设置值栈中的属性值
     * @param String
     */
    public void setValue(String value) {
        this.value = value;
    }
}
