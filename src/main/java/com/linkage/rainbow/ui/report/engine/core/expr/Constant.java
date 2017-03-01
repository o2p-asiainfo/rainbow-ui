package com.linkage.rainbow.ui.report.engine.core.expr;

/**
 * 常量
 * @version 1.0
 * @author 陈亮 2009-06-1
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-06-1<br>
 *         修改内容:新建
 *         <hr>
 *
 */

public class Constant extends Variable
{
	/**常量值**/
    protected Object value;

    /**
     * 根据一对象建立常量对象
     * @param value
     */
    public Constant(Object value)
    {
    	if(value instanceof String )
    		value = removeEscAndQuote((String)value);
        this.value = value;
    }

    /**
     * 返回常量值
     */
    public Object calculate()
    {
        return value;
    }



    /**
     * 取得表达式
     */
    public String getExp()
    {
    	
        return value.toString();
    }

    /**
     * 取得常量值
     */
    public Object getValue()
    {
        return value;
    }

    public static String removeEscAndQuote(String str, char escapeChar)
    {
        if(str == null)
            return null;
        int len = str.length();
        if(len == 0)
            return str;
        char sb[] = new char[len];
        int i = 0;
        int j = 0;
        char ch = str.charAt(i);
        char quote = '\0';
        if(ch == '\'' || ch == '"')
        {
            quote = ch;
            i++;
        }
        for(; i < len; i++)
        {
            ch = str.charAt(i);
            if(ch == escapeChar)
            {
                if(++i == len)
                    break;
                ch = str.charAt(i);
                switch(ch)
                {
                case 116: // 't'
                    sb[j++] = '\t';
                    break;

                case 114: // 'r'
                    sb[j++] = '\r';
                    break;

                case 110: // 'n'
                    sb[j++] = '\n';
                    break;

                default:
                    sb[j++] = ch;
                    break;
                }
            } else
            {
                if(i == len - 1 && ch == quote)
                    break;
                sb[j++] = ch;
            }
        }

        return new String(sb, 0, j);
    }

    public static String removeEscAndQuote(String str)
    {
        return removeEscAndQuote(str, '\\');
    }


}
