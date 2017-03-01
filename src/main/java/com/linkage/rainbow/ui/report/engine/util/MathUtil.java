package com.linkage.rainbow.ui.report.engine.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Date;

import com.linkage.rainbow.ui.report.engine.model.ReportException;

/**
 * 数学计算类
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
public class MathUtil {

	/**
	 * 10进制转26进制
	 * 
	 * @param i10
	 * @return
	 */
	public static String to26(int i10) {
		String str26 = "";
		if(i10 == 0){
			str26 = "0";
		}
		while (i10 % 26 != 0 || i10 / 26 != 0) {
			if (i10 % 26 == 0) {
				i10 = i10 / 26 - 1;
				str26 = "" + (char) (96 + 26) + str26;
			} else {
				str26 = (char) (96 + i10 % 26) + str26;
				i10 = i10 / 26;
			}
		}
		return str26;
	}

	/**
	 * 10进制转16进制
	 * 
	 * @param i10
	 * @return
	 */
	public static String to16(int i10) {
		String str16 = "";
		if(i10 == 0){
			str16 = "0";
		}
		while (i10 % 16 != 0 || i10 / 16 != 0) {
			if (i10 % 16 == 0) {
				
				str16 = "0"+str16;
				
				i10 = i10 / 16 ;

			} else {
				if(i10 % 16 >9)
					str16 = (char) (96 + i10 % 16-9) + str16;
				else 
				  str16 = i10 % 16 + str16;
				
				i10 = i10 / 16;
			}
		}
		return str16;
	}
	

	/**
	 * 26进制转10进制
	 * 
	 * @param str26
	 * @return
	 */
	public static int to10(String str26) {
		str26 = str26.toLowerCase();

		int i10 = 0;
		char c26[] = str26.toCharArray();
		for (int i = 0; i < c26.length; i++) {
			int iTmp = c26[i] - 96;
			if (iTmp >= 1 && iTmp <= 26) {
				double a = Math.pow(26d, (double) (c26.length - i - 1));
				i10 = i10 + iTmp * Integer.parseInt("" + (long) a);
			} else {
				throw new RuntimeException(str26 + "无法转换至10进制");
			}

		}
		return i10;
	}

	/**
	 * 是否为数字
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isDigit(char c) {
		int i = c;
		if (i >= 48 && i <= 57) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		boolean isNumber = true;
		if (str != null && str.trim().length() > 0) {
			char c[] = str.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] < 48 || c[i] > 57) {
					isNumber = false;
					break;
				}
			}
		} else {
			isNumber = false;
		}
		return isNumber;
	}

	protected static int getMaxNumberType(Object o1, Object o2) {
		int type1 = getNumberType(o1);
		int type2 = getNumberType(o2);
		return type1 < type2 ? type2 : type1;
	}

	protected static int getNumberType(Object o) {
		if (o instanceof Integer)
			return 1;
		if (o instanceof Long)
			return 2;
		if (o instanceof Double)
			return 3;
		if (o instanceof BigInteger)
			return 4;
		if (o instanceof BigDecimal)
			return 4;
		if (o instanceof Byte)
			return 1;
		if (o instanceof Short)
			return 1;
		if (o instanceof Float)
			return 3;
		else
			throw new RuntimeException();
	}

	
	protected static int intValue(Object o) {
		if (o instanceof Number)
			return ((Number) o).intValue();
		else
			throw new RuntimeException("不能取整数");
	}

	/**
	 * 是否为负数
	 * @param n
	 * @return
	 */
	public static boolean isNegative(Number n){
		return n.doubleValue()<0;
	}
	
	/**
	 * 是否为数字类型的类
	 * @param o
	 * @return
	 */
	public static boolean isNumber(Object o) {
		return o instanceof Number;
	}
	
	/**
	 * 是否为数字,如果为string类型的,可转换为数字类型也返回true
	 * @param o
	 * @return
	 */
	public static boolean isNumeric(Object o) {
		boolean isNumeric = false;
		if(o != null){
			if (o instanceof Number)
				isNumeric = true;
			else{
				try {
					Double.parseDouble(o.toString());
					isNumeric = true;
				} catch (Exception e) {
				}
			}
		}
		
		return isNumeric;
	}
	
	/**
	 * object转为long数值
	 * @param o
	 * @return
	 */
	public static long longValue(Object o) {
		if (o instanceof Number)
			return ((Number) o).longValue();
		else
			throw new RuntimeException("不能取长整数");
	}

	/**
	 * object转为double数值
	 * @param o
	 * @return
	 */
	public static double doubleValue(Object o) {
		if(o == null)
			throw new RuntimeException("不能取双精度浮点数");
		if (o instanceof Number)
			return ((Number) o).doubleValue();
		else{
			try {
				return Double.parseDouble(o.toString());
			} catch (Exception e) {
				throw new RuntimeException("不能取双精度浮点数");
			}
		}
			
	}

	/**
	 * object转为BigDecimal数值
	 * @param o
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object o) {
		if (o instanceof BigDecimal)
			return (BigDecimal) o;
		if (o instanceof BigInteger)
			return new BigDecimal((BigInteger) o);
		else
			return new BigDecimal(doubleValue(o));
	}

	/**
	 * 加法运算
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Object add(Object o1, Object o2) {

		if (o1 == null)
			return o2;
		if (o2 == null)
			return o1;
		
		if (isNumber(o1) && isNumber(o2)) {
			int type = getMaxNumberType(o1, o2);
			switch (type) {
			case 1:
				return new Integer(intValue(o1) + intValue(o2));

			case 2:
				return new Long(longValue(o1) + longValue(o2));

			case 3:
				return new Double(doubleValue(o1) + doubleValue(o2));

			case 4:
				return toBigDecimal(o1).add(toBigDecimal(o2));

			default:
				break;
			}
		}
		if(isNumber(o1) && o2 instanceof String && o2.toString().trim().equals("")){
			return o1;
		}
		if(isNumber(o2) && o1 instanceof String && o1.toString().trim().equals("")){
			return o2;
		}
		
		if ((o1 instanceof String) && (o2 instanceof String))
			return (String) o1 + (String) o2;
		
		if ((o1 instanceof String) && isNumber(o2)){
//			NumberFormat nf = NumberFormat.getInstance();
//			nf.setMinimumFractionDigits(0);
//			return o1 + nf.format(o2);
			return o1 + o2.toString();
		}else if(isNumber(o1)&& (o2 instanceof String)){
//			NumberFormat nf = NumberFormat.getInstance();
//			nf.setMinimumFractionDigits(0);
//			return nf.format(o1) + o2;
			return o1.toString() + o2;
		}else if(o1 instanceof String){
			return o1 + o2.toString();
		}
		else{
			return o1.toString() + o2.toString();
		}
//			throw new ReportException(getDataType(o1) + "与" + getDataType(o2)
//					+ "不能相加");
	}

	
	/**
	 * 取得数值类类型
	 * @param o
	 * @return
	 */
	public static String getDataType(Object o) {
		if (o == null)
			return "空对象";
		if (o instanceof String)
			return "字符串";
		if (o instanceof Double)
			return "双精度浮点数";
		if (o instanceof Integer)
			return "整数";
		if (o instanceof Long)
			return "长整数";
		if (o instanceof Boolean)
			return "布尔型";
		if (o instanceof BigDecimal)
			return "长小数";
		if (o instanceof Short)
			return "短整数";
		if (o instanceof Date)
			return "日期";
		if (o instanceof Byte)
			return "字节";
		else
			return o.getClass().getName();
	}

	/**
	 * 等号运算
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equals(Object o1, Object o2) {

		if (o1 == null && o2 == null)
			return true;
		if (o1 == null || o2 == null)
			return false;
		if (o1.getClass().equals(o2.getClass()))
			return o1.equals(o2);
		else
			return compare(o1, o2) == 0;
	}

	/**
	 * 比较大小
	 * 
	 * @param o1
	 * @param o2
	 * @return 0为相等,
	 */
	public static int compare(Object o1, Object o2) {

		if (o1 == null)
			return o2 != null ? -1 : 0;
		if (o2 == null)
			return 1;
		if (isNumber(o1) && isNumber(o2)) {
			int type = getMaxNumberType(o1, o2);
			switch (type) {
			case 1:
				return compare(intValue(o1), intValue(o2));

			case 2:
				return compare(longValue(o1), longValue(o2));

			case 3:
				return compare(doubleValue(o1), doubleValue(o2));

			case 4:
				return toBigDecimal(o1).compareTo(toBigDecimal(o2));

			default:
				break;
			}
		}
		if ((o1 instanceof Boolean) && (o2 instanceof Boolean))
			return compare(((Boolean) o1).booleanValue(), ((Boolean) o2)
					.booleanValue());
		if ((o1 instanceof Date) && (o2 instanceof Date))
			return ((Date) o1).compareTo((Date) o2);
		if ((o1 instanceof String) && (o2 instanceof String))
			return ((String) o1).compareTo((String) o2);
		if (isNumber(o1) || isNumber(o2)) {
			return o1.toString().compareTo(o2.toString());
//			throw new ReportException(getDataType(o1)+"("+o1+")" + "与" + getDataType(o2)+"("+o2+")" 
//					+ "不能比较");
		}else{
			return o1.toString().compareTo(o2.toString());
		}
			//return -1;
	}

	protected static int compare(boolean num1, boolean num2) {
		if (num1)
			return !num2 ? 1 : 0;
		return !num2 ? 0 : -1;
	}

	protected static int compare(double num1, double num2) {
		if (num1 > num2)
			return 1;
		return num1 >= num2 ? 0 : -1;
	}

	protected static int compare(long num1, long num2) {
		if (num1 > num2)
			return 1;
		return num1 >= num2 ? 0 : -1;
	}

	protected static int compare(int num1, int num2) {
		if (num1 > num2)
			return 1;
		return num1 >= num2 ? 0 : -1;
	}

	/**
	 * 求余数
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Object mod(Object o1, Object o2) {
		if (o1 == null || o2 == null)
			return null;
		if (isNumber(o1) && isNumber(o2)) {
			int type = getMaxNumberType(o1, o2);
			switch (type) {
			case 1:
				return new Integer(intValue(o1) % intValue(o2));

			case 2:
				return new Long(longValue(o1) % longValue(o2));

			case 3:
			case 4:
				return new Double(doubleValue(o1) % doubleValue(o2));

			default:
				break;
			}
		}
		throw new ReportException(getDataType(o1) + "与" + getDataType(o2)
				+ "不能求余");
	}

	/**
	 * 乘法运算
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Object multiply(Object o1, Object o2) {
		if (o1 == null || o2 == null)
			return null;
		if (isNumber(o1) && isNumber(o2)) {
			int type = getMaxNumberType(o1, o2);
			switch (type) {
			case 1:
				return new Integer(intValue(o1) * intValue(o2));

			case 2:
				return new Long(longValue(o1) * longValue(o2));

			case 3:
				return new Double(doubleValue(o1) * doubleValue(o2));

			case 4:
				return toBigDecimal(o1).multiply(toBigDecimal(o2));

			default:
				break;
			}
		}
		throw new ReportException(getDataType(o1) + "与" + getDataType(o2)
				+ "不能相乘");
	}

	/**
	 * 负运算
	 * 
	 * @param o
	 * @return
	 */
	public static Object negate(Object o) {
		if (o == null)
			return null;
		if (isNumber(o)) {
			int type = getNumberType(o);
			switch (type) {
			case 1:
				return new Integer(-intValue(o));

			case 2:
				return new Long(-longValue(o));

			case 3:
				return new Double(-doubleValue(o));

			case 4:
				return toBigDecimal(o).negate();

			default:
				break;
			}
		}
		throw new ReportException(getDataType(o) + "不能求负");
	}

	/**
	 * 减法运算
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Object subtract(Object o1, Object o2) {

		if (o1 == null)
			return negate(o2);
		if (o2 == null)
			return o1;
		if (isNumber(o1) && isNumber(o2)) {
			int type = getMaxNumberType(o1, o2);
			switch (type) {
			case 1:
				return new Integer(intValue(o1) - intValue(o2));

			case 2:
				return new Long(longValue(o1) - longValue(o2));

			case 3:
				return new Double(doubleValue(o1) - doubleValue(o2));

			case 4:
				return toBigDecimal(o1).subtract(toBigDecimal(o2));

			default:
				break;
			}
		}
		throw new ReportException(getDataType(o1) + "与" + getDataType(o2)
				+ "不能相减");
	}

	/**
	 * 除法运算
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Object divide(Object o1, Object o2) {
		if (o1 == null || o2 == null)
			return null;
		if (isNumber(o1) && isNumber(o2)) {
			int type = getMaxNumberType(o1, o2);
			if (type < 3) {
				return new Double(doubleValue(o1) / doubleValue(o2));
			} else {
				BigDecimal result = toBigDecimal(o1).divide(toBigDecimal(o2), 20, 4);
				
				result = BigDecimal.valueOf(result.doubleValue());
				return result;
			}
		}
		throw new ReportException(getDataType(o1) + "与" + getDataType(o2)
				+ "不能相除");
	}

	public static Object parse(String text) {
		String s;
		int len;
		char ch0;
		if(text == null)
			return null;
		s = text.trim();
		len = s.length();
		if (len == 0)
			return text;
		ch0 = s.charAt(0);
		if (s.equalsIgnoreCase("null"))
			return null;
		if (s.equalsIgnoreCase("true"))
			return Boolean.TRUE;
		if (s.equalsIgnoreCase("false"))
			return Boolean.FALSE;
		if (len <= 2 || ch0 != '0' || s.charAt(1) != 'X' && s.charAt(1) != 'x') {
			try {
				return Integer.valueOf(s);
			} catch (Exception e) {
				try {
					return new Long(Long.parseLong(s));
				} catch (Exception e1) {
					try {
						return new Double(s);
					} catch (Exception e2) {
						Object date = parseDate(text);
						if(date != null){
							return date;
						} else {
							return text;
						}
					}
				}
			}
		} else {
			return Long.valueOf(s.substring(2), 16);
			
		}
	}

	public static Object parseDate(String text) {
		try {
			return DateUtil.parseDate(text);
		} catch (Exception e) {
			try {
				return DateUtil.parseTime(text);
			} catch (Exception e1) {
				try {
					return DateUtil.parseTimestamp(text);
				} catch (Exception e2) {
					return null;
				}
			}
		}
	}
	
	/**
	 * 四舍五入
	 * @param o1
	 * @return
	 */
	public static Object round(Object o1){
		return round(o1,0);
	}
	/**
	 * 四舍五入
	 * @param o1
	 * @param iPlaces
	 * @return
	 */
	public static Object round(Object o1,int iPlaces){
		
	    NumberFormat nf = NumberFormat.getInstance();
	    if(iPlaces<0){
	    	 nf.setMaximumFractionDigits(0);
			 nf.setMinimumFractionDigits(0);
	    }else {
		    nf.setMaximumFractionDigits(iPlaces);
		    nf.setMinimumFractionDigits(iPlaces);
	    }
	   
		if (isNumber(o1)) {
			if(iPlaces<0){
				Object a = Math.pow(10,-iPlaces);
				Object newObj = divide(o1,Math.pow(10,-iPlaces));
				String str = nf.format(newObj);
				if(!str.equals("0"))
				for(int i=0;i<-iPlaces;i++){
					str+="0";
				}
				
				return toBigDecimal(str);
			}else {
				String str = nf.format(o1);
				return toBigDecimal(str);
			}
			
		} else {
			return o1;
		}
	}

	

	public static void main(String args[]) {
//		for (int i = 0; i < 256; i++) { 
//			if(i==32) 
//				i=32;
//			// System.out.println(MathUtil.to26(i) + "-->"
//			// + MathUtil.to10(MathUtil.to26(i)));
//			System.out.println(i+":"+MathUtil.to16(i));
//			//System.out.println(i+":"+(char)i);
//			// System.out.println(MathUtil.isInteger("0"));
//		}
		
		// System.out.println(MathUtil.isNumber("4532342324143132190"));
//		
//		NumberFormat nf = NumberFormat.getInstance();
//		//nf.setMinimumFractionDigits(0);
//		nf.setMinimumFractionDigits(1);
//		nf.setMaximumFractionDigits(1);
//		Object o1 = new Double(-123434.354);
//		Object o2 = new BigInteger("54535");
//		String str = nf.format(o1);
//		System.out.println(o1+":"+str);
//		 nf = NumberFormat.getInstance();
//			//nf.setMinimumFractionDigits(0);
//			nf.setMinimumIntegerDigits(10);
////			nf.setMaximumFractionDigits(1);
//			String str2 = nf.format(o2);
//		System.out.println(o2+":"+str2);
		
		
		
		Object obj = MathUtil.round(new Integer(323),-2); 
		System.out.println(obj);
		
		
	}

}
