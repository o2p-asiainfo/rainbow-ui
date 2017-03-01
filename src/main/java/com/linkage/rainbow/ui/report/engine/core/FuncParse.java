package com.linkage.rainbow.ui.report.engine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.linkage.rainbow.ui.report.engine.core.expr.DBVariable;
import com.linkage.rainbow.ui.report.engine.core.expr.Mod;
import com.linkage.rainbow.ui.report.engine.model.Report;
import com.linkage.rainbow.ui.report.engine.model.ReportException;

/**
 * 函数解析类
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
public class FuncParse {

	/**所有数据集函数定义*/
    static HashMap dsFunMap = new HashMap(20);
    /**所有非数据集函数定义*/
    static HashMap funMap = new HashMap(20);
	
	// 函数表达式
	private String srcContent;

	// 是否为函数表达式
	private boolean isExpr = false;

	// 数据集名称
	private String dsName = null;
	//数据集名称,一些函数可能会调用多个记录集.例:${chart(barlinechart3d,[ana_phs_valid_chart.num,ana_phs_valid_chart.type,ana_phs_valid_chart.mon],[ana_phs_valid.rate,ana_phs_valid.mon],,900,400,false,,月份,[用户数,有效用户占比])}
	private String[] dsNames = null;
	private Set dsNameSet = new java.util.TreeSet();
	// 函数名称
	private String funName = null;

	// 函数参数列表
	private String para[] = null;

	// 函数参数列表,转为表达式
	private String expr = null;

	static 
    {
		loadFuns();
    }
	
	public FuncParse() {

	}
	/**
	 * 根据字符串,解析生成函数
	 * @param srcContent
	 */
	public FuncParse(String srcContent) {

		this.srcContent = srcContent;
		parse();
	}
	/**
	 * 设置要解析的字符串
	 * @param srcContent 要解析的字符串
	 */
	public void setSrcContent(String srcContent) {
		this.srcContent = srcContent;
		clear();
		parse();
	}

	/**
	 * 增加非数据集函数定义
	 * @param funName 非数据集函数名
	 * @param className 非数据集函数的类
	 */
    public static void addFunction(String funName, String className)
    {
        try {
        	 //funName = funName.toLowerCase();
        	 Class funClass = Class.forName(className);
             funMap.put(funName,funClass);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	/**
	 * 增加数据集函数定义
	 * @param funName 数据集函数名
	 * @param className 数据集函数的类
	 */
    public static void addDSFunction(String funName, String className)
    {
        try {
        	 //funName = funName.toLowerCase();
        	 Class funClass = Class.forName(className);
             dsFunMap.put(funName,funClass);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    /**
     * 加载函数定义
     *
     */
    public static void loadFuns()
    {
    	//数据集函数
    	addDSFunction("select", "com.linkage.rainbow.ui.report.engine.core.func.DBSelectFunc"); //数据集列表函数
    	addDSFunction("group", "com.linkage.rainbow.ui.report.engine.core.func.DBGroupFunc");//数据集分组函数
    	addDSFunction("sum", "com.linkage.rainbow.ui.report.engine.core.func.DBSumFunc");//数据集统计函数
    	addDSFunction("count", "com.linkage.rainbow.ui.report.engine.core.func.DBCountFunc");//数据集计数函数
    	addDSFunction("chart", "com.linkage.rainbow.ui.report.engine.core.func.ChartFunc");//图表函数
    	//普通函数
    	addFunction("bias", "com.linkage.rainbow.ui.report.engine.core.func.BiasFunc");//画表格斜线函数
    	addFunction("round", "com.linkage.rainbow.ui.report.engine.core.func.RoundFunc");//四舍五入函数
    	addFunction("decode", "com.linkage.rainbow.ui.report.engine.core.func.DecodeFunc");//decode判断函数
    	addFunction("case", "com.linkage.rainbow.ui.report.engine.core.func.CaseFunc");//case判断函数
    	addFunction("row", "com.linkage.rainbow.ui.report.engine.core.func.RowFunc");//row函数,返回当前表格行数
    	addFunction("col", "com.linkage.rainbow.ui.report.engine.core.func.ColFunc");//col函数,返回当前表格列数
    	addFunction("sum", "com.linkage.rainbow.ui.report.engine.core.func.SumFunc");//画表格斜线函数
    	addFunction("count", "com.linkage.rainbow.ui.report.engine.core.func.CountFunc");//画表格斜线函数
    }
    
    /**
     * 是否为数据集函数
     * @param id 函数名
     * @return 是否为数据集函数
     */
    public static boolean isDSFunName(String id)
    {
        return dsFunMap.containsKey(id);
    }
    
    /**
     * 是否为非数据集函数
     * @param id  函数名
     * @return 是否为非数据集函数
     */
    public static boolean isFunName(String id)
    {
        return funMap.containsKey(id);
    }
    
	/**
	 * 根据函数表达式,生成函数实例.
	 * 
	 * @param funName 函数名称
	 * @param para 函数参数
	 * @param report 报表请求类
	 * @return 函数实例
	 * @throws ReportException
	 */
	public static synchronized Func createInstance(String funName,String para,
			Report report) throws ReportException {
		
		Func myFunc = null;
		try {
	        Class funClass = (Class)funMap.get(funName.toLowerCase());
	        myFunc = (Func)funClass.newInstance();
	        
	        
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (myFunc != null) {
			myFunc.setParameter(para);
			myFunc.setReport(report);

		}else {
			
			throw new ReportException("无此函数:"+funName+".请检查语法是否正确!");
		}

		return myFunc;

	}
    
	/**
	 * 根据函数表达式,生成函数实例.
	 * @param dsName 记录集名称
	 * @param funName 函数名称
	 * @param para 函数参数
	 * @param report 报表请求类
	 * @return 函数实例
	 * @throws ReportException
	 */
	public static synchronized Func createDSInstance(String dsName,String funName,String para,
			Report report) throws ReportException {
		Func myFunc = null;
		try {
	        Class funClass = (Class)dsFunMap.get(funName.toLowerCase());
	        myFunc = (Func)funClass.newInstance();
	        
	        
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (myFunc != null) {
			if (dsName != null && dsName.length() > 0) {
				myFunc.setDsName(dsName);
				myFunc.setDs(report.getDs(dsName));
				myFunc.setDsMap(report.getDsMap());
			}
			myFunc.setReport(report);
			myFunc.setParameter(para);
		}else {
			
			throw new ReportException("无此数据集函数:"+dsName+"."+funName+".请检查语法是否正确!");
		}
		return myFunc;
	}

	/**
	 * 函数表达式解析 函数表述式例子: ${ds1.select(day_id,,FALSE,Y)}
	 * 
	 */
	private void parse() {
		// 判断是否为函数表达式,是以${ 开始 } 结束
//		if (srcContent != null && srcContent.trim().startsWith("${")
//				&& srcContent.trim().endsWith("}")) {
//			isExpr = true;
//			String strFuncExpr = srcContent.substring(
//					srcContent.indexOf("${") + 2, srcContent.lastIndexOf("}"))
//					.trim();
//			expr = strFuncExpr;//.toLowerCase();
			expr = srcContent;
			if (expr.indexOf("(") > -1 && expr.indexOf(")") > -1) {
				String substr = expr.substring(0, expr.indexOf("("));
				if (substr.lastIndexOf(".") > -1) {
					// 记录集名称
					dsName = substr.substring(0, substr.lastIndexOf("."))
							.trim();
					dsNameSet.add(dsName);
					// 函数名称
					funName = substr.substring(substr.lastIndexOf(".") + 1)
							.trim();
				} else {
					// 函数名称
					funName = substr.trim();
				}

				// 函数参数
				String parameter = expr.substring(expr.indexOf("(") + 1,
						expr.lastIndexOf(")")).trim();

				// para = parameter.split(",");
				para = scanParas(parameter);


			}
//		}
		dsNames = new String[dsNameSet.size()];
		dsNameSet.toArray(dsNames);
		isExpr = true;
	}

	/**
	 * 判断是否前后为引号
	 * 
	 * @return
	 */
	private static boolean isForeAftQuot(String str, int index) {
		if (index - 1 >= 0 && index + 1 < str.length()) {
			if (str.charAt(index - 1) == '\'' && str.charAt(index + 1) == '\'') {
				return true;
			}
			if (str.charAt(index - 1) == '\"' && str.charAt(index + 1) == '\"') {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 参数字符串转为参数数组,按,号分隔
	 * @param expStr
	 * @return
	 */
	public String[] scanParas(String expStr) {
		//取得子参数.
		String subPara[] = getSubPara(expStr,false);
		//对子参数进行查找记录集名称.
		for(int i=0;i<subPara.length;i++){
			scanDsName(subPara[i]);
		}
		
		return subPara;
	}
	
	/**
	 * 查找数据集名称
	 * @param expStr 表达式字符串
	 */
	public void scanDsName(String expStr){
		String subPara[] = getSubPara(expStr);
		if(subPara != null && subPara.length>0){
			int iLength = subPara.length;
			if(iLength>1){
				for(int i=0;i<subPara.length;i++){
					scanDsName(subPara[i]);
				}
			} else {
				
				Expr exprTmp = new Expr(subPara[0]);
				List dbVarList = exprTmp.getDbVarList();
				if(dbVarList != null ){
					int iSize = dbVarList.size();
					for(int i=0;i<iSize;i++){
						DBVariable dbVar = (DBVariable)dbVarList.get(i);
						if(dbVar.getDsName() != null && dbVar.getDsName().trim().length()>0){
							// 记录集名称
							if (dsName == null) 
								dsName = dbVar.getDsName();
							dsNameSet.add(dbVar.getDsName());
						}
					}
				}
			}
		}
	}



	private void clear() {

		isExpr = false;

		dsName = null;

		funName = null;

		para = null;

		expr = null;
	}

	/**
	 * 取得记录集名称
	 * @return 记录集名称
	 */
	public String getDsName() {
		return dsName;
	}

	/**
	 * 取得函数名称
	 * @return 函数名称
	 */
	public String getFunName() {
		return funName;
	}

	/**
	 * 是否为表达式
	 * @return 是否为表达式
	 */
	public boolean isExpr() {
		return isExpr;
	}
	
	/**
	 * 取得参数数组
	 * @return 参数数组
	 */
	public String[] getPara() {
		return para;
	}

	/**
	 * 取得要解析的字符串
	 * @return 要解析的字符串
	 */
	public String getSrcContent() {
		return srcContent;
	}
	/**
	 * 取得表达式
	 * @return 表达式
	 */
	public String getExpr() {
		return expr;
	}

	/**
	 * 取得所有数据集名称
	 * @return 所有数据集名称
	 */
	public Set getDsNameSet() {
		return dsNameSet;
	}

	/**
	 * 取得所有数据集名称数组
	 * @return 所有数据集名称数组
	 */
	public String[] getDsNames() {
		return dsNames;
	}

	/**
	 * 取得子参数
	 * @param i
	 * @param j
	 * @return
	 */
	public String getSubPara(int i,int j){
		String subPara = null;
		String paraTmp[] = getSubPara(para.length > i ? para[i]: null);
		// 分组字段
		subPara = paraTmp != null && paraTmp.length>j ?paraTmp[j]:null;
		return subPara;
	}
	

    
    
	
	/**
	 * 取得子参数,如果前后有[]括号,则去除
	 * para 参数字符串
	 */
	public static String[] getSubPara(String para) {
		return getSubPara(para,true);
	}
	
	/**
	 * 取得子参数
	 * para 参数字符串
	 * isMoveBracket 是否前后去除[]括号
	 */
	public static String[] getSubParaMyreport(String para,boolean isMoveBracket) {
		String subParas[] = null;
		ArrayList list = new ArrayList();
		if (para != null && para.trim().length() > 0) {
			para = para.trim();
			try {
				// 去除前后的中括号
				int len = para.length();
//				if (para.indexOf("[") == 0 && para.lastIndexOf("]") == len - 1) {
//					para = para.substring(1, len-1);
//				}
//
//				len = para.length();
				Sign sign = null;
				int inBrackets = 0;
				int currIndex = 0;
				int iStart = 0;
				
				while (currIndex < len) {
					char c = para.charAt(currIndex);

					switch (c) {
					case '[': // '('
						inBrackets++;
						currIndex++;
						continue;
					case ']': // ')'
						if (--inBrackets < 0)
							throw new ReportException("括号不匹配，右括号太多！");
						currIndex++;
						continue;

					case '"': // '"'
						currIndex = Expr.findQuotationMark(para, currIndex); // 查询字符串结束
						if(currIndex == -1)
							throw new ReportException("表达式异常,引号没有正常结束");
						currIndex++;
						break;
					case '\'': // '"'
						currIndex = Expr.findQuotationMark(para, currIndex); // 查询字符串结束
						if(currIndex == -1)
							throw new ReportException("表达式异常,引号没有正常结束");
						currIndex++;
						break;
					case ',':
						if (inBrackets != 0) {
							currIndex++;
						} else {
							String strSubPara = para.substring(iStart,
									currIndex);
							list.add(strSubPara);
							currIndex++;
							iStart = currIndex;
						}
						break;
					default:
						currIndex++;
					}
				}
				String strSubPara = para.substring(iStart,
						currIndex);
				//如果前后有[]括号,则去除.
				if(isMoveBracket){
					if(strSubPara.equals(para) && para.indexOf("[") == 0 && para.lastIndexOf("]") == len - 1){
						return getSubPara(para.substring(1, len-1));
					}
				}
				list.add(strSubPara);
			} catch (Exception e) {
			}
		}
		int iSize = list.size();
		if(iSize >0){
			subParas = new String[iSize];
			list.toArray(subParas);
			
		}
		return subParas;
	}
	
	/**
	 * 取得子参数
	 * expStr 参数字符串
	 * isMoveBracket 是否前后去除[]括号
	 */
    protected static String[]  getSubPara(String expStr,boolean isMoveBracket)
    {
    	if(expStr == null)
    		return null;
    	//如果前后有[]括号,则去除.
		if(isMoveBracket){
			if(expStr.indexOf("[") == 0 && expStr.lastIndexOf("]") == expStr.length() - 1){
				return getSubPara(expStr.substring(1, expStr.length()-1));
			}
		}
    	String subParas[] = null;
		ArrayList list = new ArrayList();
		
        int len;
        int preIndex=0;
        int currIndex=0;
        char c;
        
        for(len = expStr.length(); currIndex < len; currIndex++,preIndex++)
        {
            c = expStr.charAt(currIndex);
            if(!Character.isSpaceChar(c))
                break;
        }
        
        for(;currIndex < len; currIndex++)
        {
            c = expStr.charAt(currIndex);
            switch(c)
            {
            case ',': 
	            String strSubPara =  expStr.substring(preIndex,currIndex);
            	list.add(strSubPara);
            	preIndex = currIndex+1; 
            	break;
            case '(': 
            case '[': 
            	int match = scanParenthesis(expStr, currIndex);
                if(match == -1)
                {
                    throw new ReportException("函数括号不匹配");
                }
                currIndex = match;
                break;
            case 34: // '"'
            case 39: // '\''
            	match  = Expr.scanQuotation(expStr, currIndex);
                if(match < 0)
                	 throw new ReportException("引号不匹配");
                currIndex = match;
                break;
            default:
                if(c == '\\') //转义符
                	currIndex++;
                break;
            }
        }
        if(preIndex<currIndex){
        	String strSubPara =  expStr.substring(preIndex,currIndex);
        	list.add(strSubPara);
        }
        int iSize = list.size();
		if(iSize >0){
			subParas = new String[iSize];
			list.toArray(subParas);
			
		}
		return subParas;
      
    }
    /**
     * 查找括号位置
     * @param str 表达式字符串
     * @param start 开始位置
     * @return 下一个括号位置
     */
    public static int scanParenthesis(String str, int start)
    {
        return scanParenthesis(str, start, '\\');
    }
    /**
     * 查找括号位置
     * @param str 表达式字符串
     * @param start 开始位置
     * @param escapeChar 转义字符
     * @return  下一个括号位置
     */
    public static int scanParenthesis(String str, int start, char escapeChar)
    {
    	char bracket = str.charAt(start);
        if(bracket != '(' && bracket != '[')
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
                i = Expr.scanQuotation(str, i, escapeChar);
                if(i < 0)
                    return -1;
                i++;
                break;

//            case 41: // ')'
//                return i;

            case 35: // '#'
            case 36: // '$'
            case 37: // '%'
            case 38: // '&'
            default:
            	if(bracket=='['&& ch == ']')
                    return i;
	            if(bracket=='('&& ch == ')')
	                return i;
	            
                if(ch == escapeChar)
                    i++;
                i++;
                break;
            }
        }

        return -1;
    }
    
	public static void main(String[] args) {
//		FuncParse f = new FuncParse();
//		f.setSrcContent("${chart(PieChart,标题,[myreport.ds1.area_name,myreport.ds1.counts])}");
//		System.out.println(f.getPara().length);
//		
//		f.setSrcContent("${myreport.ds1.sum(counts,myreport.ds1.area_name=b5&&myreport.ds1.day_id=b4)}");
//		System.out.println(f.getPara().length);
//		
		String a[] = FuncParse.getSubPara("[area_id,area_name]",true);
		for(int i=0;i<a.length;i++)
		System.out.println(a[i]);
	}


}
