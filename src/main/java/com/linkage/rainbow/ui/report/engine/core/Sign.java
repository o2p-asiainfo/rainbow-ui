package com.linkage.rainbow.ui.report.engine.core;

/**
 * 表达式中一个符号,包括以下几大类:<br>
 * 1.操作符 + - * / = & | !  > < ( ) [ ] { } ' <br>
 * 2.变量,变量主要分单元格变量和数据集变量 <br>
 * 3.常量 <br>
 * @version 1.0
 * @author 陈亮 2009-06-1
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-06-1<br>
 *         修改内容:新建
 *         <hr>
 */
public abstract class Sign {

	/**左边运算树*/
    protected Sign left;
    /**运算优先级*/
    protected int pri;
    /**右边运算*/
    protected Sign right;
    /**上级运算*/
    protected Sign fSign;
    /**计算表达式*/
    protected Expr expr;
    /**
     * 取得上级运算
     * @return 上级运算
     */
    public Sign getFSign() {
		return fSign;
	}

    /**
     * 设置上级运算
     * @param sign 上级运算
     */
	public void setFSign(Sign sign) {
		fSign = sign;
	}

	
	protected Sign()
    {
        pri = 15;
    }

    /**
     * 计算结果值
     * @return 结果值
     */
    public abstract Object calculate();


    /**
     * 取得表达式字符串
     * @return
     */
    public abstract String getExp();

    /**
     * 取得左运算树
     * @return 左运算树
     */
    public Sign getLeft()
    {
        return left;
    }

    /**
     * 取得运算优先级
     * @return 运算优先级
     */
    public int getPri()
    {
        return pri;
    }

    /**
     * 取得右运算树
     * @return 右运算树
     */
    public Sign getRight()
    {
        return right;
    }


    /**
     * 是否为操作符 
     * @return 是否为操作符
     */
    public boolean isOperator()
    {
        return false;
    }

    protected Sign optimize()
    {

        if(left != null)
            left = left.optimize();
        if(right != null)
            right = right.optimize();
        return this;
    }

    /**
     * 调整优先级
     * @param inBrackets
     */
    public void setInBrackets(int inBrackets)
    {
        pri += inBrackets * 16;
    }

    /**
     * 设置左运算树
     * @param node 左运算树
     */
    public void setLeft(Sign node)
    {
        left = node;
    }

    /**
     * 设置右运算树
     * @param node 右运算树
     */
    public void setRight(Sign node)
    {
        right = node;
    }

    /**
     * 取得计算表达式
     * @return 计算表达式
     * @see Expr
     */
	public Expr getExpr() {
		return expr;
	}

	/**
	 * 设置计算表达式
	 * @param expr 计算表达式
	 * @see Expr
	 */
	public void setExpr(Expr expr) {
		this.expr = expr;
	}

    
}
