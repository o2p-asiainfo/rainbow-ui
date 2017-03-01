package com.linkage.rainbow.ui.report.engine.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 比较类
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
public class Compare {

	/**
	 * 取得两个数值类中,最大的数值类型,类型按以下分类<br>
	 * 1 Byte Short Integer,2 Long,3 Float Double,4 BigDecimal
	 * @param o1 数值类1
	 * @param o2 数值类2
	 * @return 最大的数值类型, 类型按以下分类1 Byte Short Integer,2 Long,3 Float Double,4 BigDecimal
	 */
	protected static int getMaxNumberType(Object o1, Object o2)
    {
        int type1 = getNumberType(o1);
        int type2 = getNumberType(o2);
        return type1 < type2 ? type2 : type1;
    }

	/**
	 * 取得数值类的类型,按以下分类<br>
	 * 1 Byte Short Integer,2 Long,3 Float Double,4 BigDecimal
	 * @param o 数值类
	 * @return 数值类型, 类型按以下分类1 Byte Short Integer,2 Long,3 Float Double,4 BigDecimal
	 */
	protected static  int getNumberType(Object o)
    {
        if(o instanceof Integer)
            return 1;
        if(o instanceof Long)
            return 2;
        if(o instanceof Double)
            return 3;
        if(o instanceof BigInteger)
            return 4;
        if(o instanceof BigDecimal)
            return 4;
        if(o instanceof Byte)
            return 1;
        if(o instanceof Short)
            return 1;
        if(o instanceof Float)
            return 3;
        else
            throw new RuntimeException();
    }
	/**
	 * 数值类转为int
	 * @param o 数值类
	 * @return
	 */
	protected static int intValue(Object o)
    {
        if(o instanceof Number)
            return ((Number)o).intValue();
        else
            throw new RuntimeException("不能取整数");
    }

	/**
	 * 判断此类是否为数值类
	 * @param o object对象
	 * @return 是否为数值类
	 */
    public static boolean isNumber(Object o)
    {
        return o instanceof Number;
    }

    /**
     * 数值类转为long
     * @param o 数值类
     * @return
     */
    public static long longValue(Object o)
    {
        if(o instanceof Number)
            return ((Number)o).longValue();
        else
            throw new RuntimeException("不能取长整数");
    }  
    /**
     * 比较两个boolean类型
     * @param num1 
     * @param num2
     * @return
     */
    protected static int compare(boolean num1, boolean num2)
    {
        if(num1)
            return !num2 ? 1 : 0;
        return !num2 ? 0 : -1;
    }

    /**
     * 比较两个double类型数值
     * @param num1  double数值1
     * @param num2  double数值2
     * @return
     */
    protected static int compare(double num1, double num2)
    {
        if(num1 > num2)
            return 1;
        return num1 >= num2 ? 0 : -1;
    }

    /**
     * 比较两个long类型数值
     * @param num1  long数值1
     * @param num2  long数值2
     * @return
     */
    protected static int compare(long num1, long num2)
    {
        if(num1 > num2)
            return 1;
        return num1 >= num2 ? 0 : -1;
    }


    /**
     * 比较两个int类型数值
     * @param num1  int数值1
     * @param num2  int数值2
     * @return
     */
    protected static int compare(int num1, int num2)
    {
        if(num1 > num2)
            return 1;
        return num1 >= num2 ? 0 : -1;
    }
    
    /**
     * object对象转为double
     * @param o 
     * @return
     */
    public static double doubleValue(Object o)
    {
        if(o instanceof Number)
            return ((Number)o).doubleValue();
        else
            throw new RuntimeException( "不能取双精度浮点数");
    }
    /**
     * object对象转为BigDecimal
     * @param o 
     * @return
     */
    public static BigDecimal toBigDecimal(Object o)
    {
        if(o instanceof BigDecimal)
            return (BigDecimal)o;
        if(o instanceof BigInteger)
            return new BigDecimal((BigInteger)o);
        else
            return new BigDecimal(doubleValue(o));
    }
    /**
     * 两个数值对象的值是否相等  
     * @param o1   数值对象1
     * @param o2  数值对象2
     * @return 是否相等  
     */
    public static boolean equals(Object o1, Object o2)
    {

        if(o1 == null && o2 == null)
            return true;
        if(o1 == null || o2 == null)
            return false;
        if(o1.getClass().equals(o2.getClass()))
            return o1.equals(o2);
        else {
          if( o1 instanceof Number && o2 instanceof Number){
        	  int type = getMaxNumberType(o1, o2);
              switch(type)
              {
              case 1: 
                  return compare(intValue(o1), intValue(o2))==0?true:false;

              case 2:
                  return compare(longValue(o1), longValue(o2))==0?true:false;

              case 3: 
                  return compare(doubleValue(o1), doubleValue(o2))==0?true:false;

              case 4: 
                  return toBigDecimal(o1).compareTo(toBigDecimal(o2))==0?true:false;

              default:
                  return false;
              }
          } else {
        	  return false;
          }
        }
            
    }
}
