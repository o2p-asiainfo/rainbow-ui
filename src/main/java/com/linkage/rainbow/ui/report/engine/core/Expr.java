package com.linkage.rainbow.ui.report.engine.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.linkage.rainbow.ui.report.engine.core.expr.Add;
import com.linkage.rainbow.ui.report.engine.core.expr.And;
import com.linkage.rainbow.ui.report.engine.core.expr.BigThen;
import com.linkage.rainbow.ui.report.engine.core.expr.CSVariable;
import com.linkage.rainbow.ui.report.engine.core.expr.Constant;
import com.linkage.rainbow.ui.report.engine.core.expr.DBVariable;
import com.linkage.rainbow.ui.report.engine.core.expr.Divide;
import com.linkage.rainbow.ui.report.engine.core.expr.Equals;
import com.linkage.rainbow.ui.report.engine.core.expr.GigEquals;
import com.linkage.rainbow.ui.report.engine.core.expr.In;
import com.linkage.rainbow.ui.report.engine.core.expr.LessEquals;
import com.linkage.rainbow.ui.report.engine.core.expr.LessThen;
import com.linkage.rainbow.ui.report.engine.core.expr.Mod;
import com.linkage.rainbow.ui.report.engine.core.expr.Multiply;
import com.linkage.rainbow.ui.report.engine.core.expr.Not;
import com.linkage.rainbow.ui.report.engine.core.expr.NotEquals;
import com.linkage.rainbow.ui.report.engine.core.expr.Or;
import com.linkage.rainbow.ui.report.engine.core.expr.Subtract;
import com.linkage.rainbow.ui.report.engine.model.CellSet;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.linkage.rainbow.ui.report.engine.util.MathUtil;

/**
 * 公式表达式
 * 
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
public class Expr implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -658941090755054823L;

	/**
	 * 对应的报表请求类
	 */
	protected Report report;

	/**
	 * 扩展前表格
	 */
	protected CellSet oldCS;

	/**
	 * 扩展后表格
	 */
	protected CellSet newCS;

	/**
	 * 数据集名称
	 */
	protected String dsName;

	/**
	 * 数据集
	 */
	protected CachedRowSet ds;

	/**
	 * 表达式字符串
	 */
	protected String expStr;

	/**
	 * 解析表达式字符串后,生成的计算树的根结点
	 */
	protected Sign root;

	/**
	 * 解析表达式字符串的当前位置
	 */
	protected int currIndex;

	/**
	 * 前一计算符号
	 */
	protected Sign preSign;

	/**所有单元格变量*/
	protected List csVarList = new ArrayList();
	/**所有数据记录集变量*/
	protected List dbVarList = new ArrayList();
	/**所有常量*/
	protected List constantList = new ArrayList();
	/**所有普通函数*/
	protected List funcList = new ArrayList();
	/**所有数据库函数*/
	protected List dbFuncList = new ArrayList();
	
	/**转意字符*/
	public static char escapeChar = '\\';

	/**
	 * 根据表达式字符串,报表请求类生成表达式对象
	 * @param expStr  表达式字符串
	 * @param report 报表请求类
	 * @throws ReportException 报表异常
	 */
	public Expr(String expStr,Report report) throws ReportException {
		this.report = report;
		this.oldCS = report.getDefCS();
		this.newCS = report.getNewCS();
		this.expStr = expStr;

		create();

	}

	/**
	 * 根据表达式字符串,报表请求类,数据集名称生成表达式对象
	 * @param expStr  表达式字符串
	 * @param report 报表请求类
	 * @param dsName 数据集名称
	 * @throws ReportException 报表异常
	 */
	public Expr(String expStr, Report report, String dsName)
			throws ReportException {
		this.report = report;
		this.oldCS = report.getDefCS();
		this.newCS = report.getNewCS();
		this.expStr = expStr;
		this.dsName = dsName;
		this.ds = report.getDs(dsName);
		create();

	}
	/**
	 * 根据表达式字符串,报表请求类,数据集生成表达式对象
	 * @param expStr  表达式字符串
	 * @param report 报表请求类
	 * @param crs 数据集
	 * @throws ReportException 报表异常
	 */
	public Expr(String expStr,String dsName,CachedRowSet crs) throws ReportException {

		this.expStr = expStr;
		this.dsName = dsName;
		this.ds = crs;
		create();

	}
	
	/**
	 * 根据表达式字符串,数据集名称生成表达式对象
	 * @param expStr  表达式字符串
	 * @param crs 数据集
	 * @throws ReportException 报表异常
	 */
	public Expr(String expStr,CachedRowSet crs) throws ReportException {

		this.expStr = expStr;
		this.ds = crs;
		create();

	}
	
	/**
	 * 根据表达式字符串生成表达式对象
	 * @param expStr  表达式字符串
	 * @throws ReportException 报表异常
	 */
	public Expr(String expStr) throws ReportException {
		this.expStr = expStr;
		create();
	}

	/**
	 * 解析表达式字符串
	 * @throws ReportException 报表异常
	 */
	protected void create() throws ReportException {
		int len = expStr.length();
		Sign sign = null;
		int inBrackets = 0;
		while (currIndex < len) {
			char c = expStr.charAt(currIndex);
			if (Character.isSpaceChar(c)) {
				currIndex++;
				continue;
			}
			sign = null;
			switch (c) {
			case '(': // '('
				inBrackets++;
				currIndex++;
				continue;

			case ')': // ')'
				if (--inBrackets < 0)
					throw new ReportException("括号不匹配，右括号太多！");
				currIndex++;
				continue;

			case '+': // '+'
				sign = new Add();
				currIndex++;
				break;

			case '-': // '-'
				sign = new Subtract();
				currIndex++;
				break;

			case '*': // '*'
				sign = new Multiply();
				currIndex++;
				break;

			case '/': // '/'
				sign = new Divide();
				currIndex++;
				break;

			case '=': // '='
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=')
						currIndex++;
				}
				sign = new Equals();
				break;

			case '!': // '!'
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=') {
						sign = new NotEquals();
						currIndex++;
					}
				}
				if (sign == null)
					sign = new Not();
				break;

			case '>': // '>'
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=') {
						sign = new GigEquals();
						currIndex++;
					}
				}
				if (sign == null)
					sign = new BigThen();
				break;

			case '<': // '<'
				currIndex++;
				if (currIndex < len) {
					if (expStr.charAt(currIndex) == '=') {
						sign = new LessEquals();
						currIndex++;
					}
				}
				if (sign == null)
					sign = new LessThen();
				break;

			case '&': // '&'
				currIndex++;
				if (currIndex < len && expStr.charAt(currIndex) == '&') {
					sign = new And();
					currIndex++;
				} else {
					sign = new And();
				}
				break;

			case '|': // '|'
				currIndex++;
				if (currIndex < len && expStr.charAt(currIndex) == '|') {
					sign = new Or();
					currIndex++;
				} else {
					throw new ReportException("不能识别标识符|");
				}
				break;
			case '%': // '%'
				sign = new Mod();
				currIndex++;
				break;

			case '.': // '.'
				currIndex++;
				break;

			case ',': // ','
				throw new ReportException("位置" + currIndex + "不应该出现逗号");

			default:
				sign = createSign();
				break;
			}
			sign.setInBrackets(inBrackets);
			sign.setExpr(this);
			preSign = sign;
			if (root == null) {
				root = sign;
			} else {
				Sign right = root;
				Sign parent = null;
				for (; right != null && right.getPri() < sign.getPri(); right = right
						.getRight())
					parent = right;

				sign.setLeft(right);
				if(right != null) 
					right.setFSign(sign);
				if (parent != null){
					parent.setRight(sign);
					sign.setFSign(parent);
				}
				else
					root = sign;
			}
		}
		if (inBrackets > 0) {
			throw new ReportException("括号不匹配，左括号太多！");
		} else {
			return;
		}
	}
	
	private boolean isDBCol(String str){
		boolean isDBCol = false;
		try {
			if(ds != null && ds.havColName(str)){//如果为记录集字段，转为数据记录集变量
				return true;
			}
		} catch (Exception e) {
		}
		
		return isDBCol;
	}

	/**
	 * 查找下一个引号
	 * @param str 字符串
	 * @param start 开始字符
	 * @return 下一个引号位置
	 */
	public static int findQuotationMark(String str, int start) {
		return findQuotationMark(str, start, escapeChar);
	}
	/**
	 * 查找下一个引号
	 * @param str 字符串
	 * @param start 开始字符
	 * @param escapeChar 转义字符
	 * @return 下一个引号位置
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
	 * 查找下一个符号
	 * @return 下一个符号
	 */
	protected String scanId() {
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
	 * 建立一个符号
	 * @return
	 */
	protected Sign createSign() {
		char c = expStr.charAt(currIndex);
		int match = -1;
		if (c == '"' || c == '\'') {
			match = findQuotationMark(expStr, currIndex);
			if (match == -1) {
				throw new ReportException("引号不匹配");
			} else {
				String str = expStr.substring(currIndex + 1, match);
				currIndex = match + 1;
				Constant constant =new Constant(str);
				addConstant(constant);
				return constant;
			}
		}

		String id = scanId();
		if (id.equalsIgnoreCase("and"))
			return new And();
		if (id.equalsIgnoreCase("or"))
			return new Or();
		if (id.equalsIgnoreCase("not"))
			return new Not();
		if (id.equalsIgnoreCase("in"))
			return new In();
		int index = id.lastIndexOf('.');
		if (index > 0 && !Character.isDigit(id.charAt(0))) {		
			String dsName = id.substring(0, index);
			String colName = id.substring(index+1);
			Sign sign = createDSSign(dsName,colName);
			if(sign != null)
                return sign;
            else
                throw new ReportException("有不可识别的数据集函数" + colName);
		}

		if (id.charAt(0) == '$'||id.charAt(0) == '@') {
			id = id.substring(1);
			Constant constant =null;
			if(report != null)
				constant = new Constant(report.getPara(id));
			else 
				constant = new Constant(null);
			addConstant(constant);
			return constant;
		} 
		else if (oldCS != null && oldCS.existCoordinate(id)) {
			CSVariable csv = new CSVariable(id);
			addCSVar(csv);
			return csv;
		}else if(isDBCol(id)){//是否为记录集字段
			DBVariable dbVariable = new DBVariable(this.dsName, id, ds); 	
			addDBVar(dbVariable);
			return dbVariable;
		}else if(isHaveBracket()){//是否为函数
			Func func= FuncParse.createInstance(id,scanParameter(),report);
            if(func != null)
            {
            	func.setDs(ds);
            	func.setDsName(dsName);
                addFunc(func);
                return func;
            } else
            {
            	Constant constant = new Constant(id);
    			addConstant(constant);
    			return constant;
            }
        }  
		else {//为常量
			Object value = MathUtil.parse(id);
			Constant constant = new Constant(value);
			addConstant(constant);
			return constant;
		}
	}

	/**
	 * 建立数据集变量
	 * @param ds 数据集名称
	 * @param id 字段名称
	 * @return 数据集变量
	 */
    protected Sign createDSSign(String ds, String id)
    {
        int colIndex=0;
        if(id.charAt(0) == '#'){
	        try {
	        	colIndex = Integer.parseInt(id.substring(1));
			} catch (Exception e) {
				 throw new ReportException("数据集列索引格式不正确");
			}
	        
        }
       

        if(isHaveBracket())
        {
            if(FuncParse.isDSFunName(id))
            {
//            	Func func= FuncParse.createDSInstance(ds+"."+id+"("+scanParameter()+")",report);
            	Func func= FuncParse.createDSInstance(ds,id,scanParameter(),report);
//                if(func != null)
//                	func.setParameter(scanParameter());
            	addDBFunc(func);
                return func;
            }
        } else
        {
        	
			DBVariable dbVariable = new DBVariable(ds, id, report);
			addDBVar(dbVariable);
			return dbVariable;
        }
        return null;
    }
    
    /**
     * 查找函数变量
     * @return 函数变量
     */
    protected String scanParameter()
    {
        int len;
        char c;
        for(len = expStr.length(); currIndex < len; currIndex++)
        {
            c = expStr.charAt(currIndex);
            if(!Character.isSpaceChar(c))
                break;
        }

        if(currIndex == len)
            throw new ReportException("函数缺少参数");
        c = expStr.charAt(currIndex);
        if(c != '(')
            throw new ReportException("函数缺少参数");
        int match = scanParenthesis(expStr, currIndex);
        if(match == -1)
        {
            throw new ReportException("函数括号不匹配");
        } else
        {
            String param = expStr.substring(currIndex + 1, match);
            currIndex = match + 1;
            return param;
        }
    }
    
    /**
     * 查找下一个圆括号
     * @param str 字符串
     * @param start 开始位置
     * @return 下一个圆括号的位置
     */
    public static int scanParenthesis(String str, int start)
    {
        return scanParenthesis(str, start, '\\');
    }
    /**
     * 查找下一个圆括号
     * @param str 字符串
     * @param start 开始位置
     * @param escapeChar 转义字符
     * @return 下一个圆括号的位置
     */
    public static int scanParenthesis(String str, int start, char escapeChar)
    {
        if(str.charAt(start) != '(')
            return -1;
        int len = str.length();
        for(int i = start + 1; i < len;)
        {
            char ch = str.charAt(i);
            switch(ch)
            {
            case 40: // '('
                i = scanParenthesis(str, i, escapeChar);
                if(i < 0)
                    return -1;
                i++;
                break;

            case 34: // '"'
            case 39: // '\''
                i = scanQuotation(str, i, escapeChar);
                if(i < 0)
                    return -1;
                i++;
                break;

            case 41: // ')'
                return i;

            case 35: // '#'
            case 36: // '$'
            case 37: // '%'
            case 38: // '&'
            default:
                if(ch == escapeChar)
                    i++;
                i++;
                break;
            }
        }

        return -1;
    }


    /**
     * 查找下一个引号
     * @param str 字符串
     * @param start 开始位置
     * @return 下一个引号的位置
     */
    public static int scanQuotation(String str, int start)
    {
        return scanQuotation(str, start, '\\');
    }
    /**
     * 查找下一个引号
     * @param str 字符串
     * @param start 开始位置
     * @param escapeChar 转义字符
     * @return 下一个引号的位置
     */
    public static int scanQuotation(String str, int start, char escapeChar)
    {
        char quote = str.charAt(start);
        if(quote != '"' && quote != '\'')
            return -1;
        int idx = start + 1;
        for(int len = str.length(); idx < len;)
        {
            char ch = str.charAt(idx);
            if(ch == escapeChar)
            {
                idx += 2;
            } else
            {
                if(ch == quote)
                    return idx;
                idx++;
            }
        }

        return -1;
    }

    
    private boolean isHaveBracket()
    {
        int i = currIndex;
        int len;
        for(len = expStr.length(); i < len && Character.isSpaceChar(expStr.charAt(i)); i++);
        if(i >= len)
            return false;
        else
            return expStr.charAt(i) == '(';
    }
    
    /**
     * toString,输出此表达式的计算结果
     */
	public String toString() {
		
		//return expStr;
		Object result = calculate();
		if(result!=null) 
			return result.toString();
		else 
			return null;
	}

	/**
	 * 设置表达式结果
	 * @return 表达式结果
	 */
	public Object calculate() {
		if (root == null)
			return null;

		return root.calculate();
	}

	/**
	 * 增加数据集变量
	 * @param dbVar 数据集变量
	 */
	public void addDBVar(DBVariable dbVar) {
		dbVarList.add(dbVar);
	}
	/**
	 * 取得所有数据集变量
	 * @return 数据集变量
	 */
	public List getDbVarList() {
		return dbVarList;
	}

	/**
	 * 增加单元格变量
	 * @param csVar 单元格变量
	 */
	public void addCSVar(CSVariable csVar) {
		if (csVar != null) {
			int iSize = csVarList.size();
			boolean isAdd = false;
			for (int i = 0; i < iSize; i++) {
				CSVariable oldCSVar = (CSVariable) csVarList.get(i);
				if (csVar.getCoordinate().getStartRow() < oldCSVar
						.getCoordinate().getStartRow()) {
					csVarList.add(i, csVar);
					isAdd = true;
					break;
				}
				if (csVar.getCoordinate().getStartRow() == oldCSVar
						.getCoordinate().getStartRow()
						&& csVar.getCoordinate().getStartColumn() < oldCSVar
								.getCoordinate().getStartColumn()) {
					csVarList.add(i, csVar);
					isAdd = true;
					break;
				}
			}
			if(!isAdd){
				csVarList.add(csVar);
			}
		}
	}


	/**
	 * 取得所有单元格变量
	 * @return 单元格变量
	 */
	public List getCsVarList() {
		return csVarList;
	}

	/**
	 * 增加常量
	 * @param constant 常量
	 */ 
	public void addConstant(Constant constant) {
		constantList.add(constant);
	}
	
	/**
	 * 取得所有函数
	 * @return 所有函数
	 */
	public List getFuncList() {
		return funcList;
	}

	/**
	 * 增加一个函数
	 * @param func 函数
	 */
	private void addFunc(Func func) {
		funcList.add(func);
	}
	
	/**
	 * 增加数据集函数
	 * @param func 数据集函数
	 */
	private void addDBFunc(Func func) {
		dbFuncList.add(func);
	}
	
	/**
	 * 取得所有常量
	 * @return 所有常量
	 */
	public List getConstantList() {
		return constantList;
	}
	
	/**
	 * 取得数据集
	 * @return 数据集
	 */
	public CachedRowSet getDs() {
		return ds;
	}

	/**
	 * 取得数据集名称
	 * @return 数据集名称
	 */
	public String getDsName() {
		return dsName;
	}

	/**
	 * 取得扩展后表格
	 * @return 扩展后表格
	 */
	public CellSet getNewCS() {
		return newCS;
	}

	/**
	 * 取得扩展前表格
	 * @return 扩展前表格
	 */
	public CellSet getOldCS() {
		return oldCS;
	}

	/**
	 * 取得报表请求类
	 * @return 报表请求类
	 */
	public Report getReport() {
		return report;
	}

	public static void main(String args[]) {
		Expr e = new Expr( "\"area_id=\"+area_id+\"&stat=\"+stat",new Report());

		Object o = e.calculate();
		System.out.print(o);
	}

	/**
	 * 取得计算树的根结点
	 * @return 计算树的根结点
	 */
	public Sign getRoot() {
		return root;
	}

	/**
	 * 设置计算树的根结点
	 * @param root 计算树的根结点
	 */
	public void setRoot(Sign root) {
		this.root = root;
	}

	/**
	 * 取得表达式字符串
	 * @return 表达式字符串
	 */
	public String getExpStr() {
		return expStr;
	}

	/**
	 * 设置表达式字符串
	 * @param expStr 表达式字符串
	 */
	public void setExpStr(String expStr) {
		this.expStr = expStr;
	}

	/**
	 * 设置记录集
	 * @param ds 记录集
	 */
	public void setDs(CachedRowSet ds) {
		this.ds = ds;
		if(ds!=null)
			setDsName(ds.getDsName());
	}

	/**
	 * 设置记录集名称
	 * @param dsName 记录集名称
	 */
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	/**
	 * 取得所有数据集函数
	 * @return 所有数据集函数
	 */
	public List getDbFuncList() {
		return dbFuncList;
	}
	
	
}