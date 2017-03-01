package com.linkage.rainbow.ui.report.engine.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.Color;
import java.text.*;

/**
 * 字符串处理
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
public final class StringUtils {
	
	private static Log log = LogFactory.getLog(StringUtils.class);

	private StringUtils() {
	}
	/**
	 * char转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(char c) {
		char ac[] = new char[1];
		ac[0] = c;
		return new String(ac);
	}
	/**
	 * double转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(double d) {
		return Double.toString(d);
	}

	/**
	 * float转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(float f) {
		return Float.toString(f);
	}
	/**
	 * int转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(int i) {
		char ac[] = new char[11];
		int j = ac.length;
		boolean flag = i < 0;
		if (!flag)
			i = -i;
		for (; i <= -10; i /= 10)
			ac[--j] = Character.forDigit(-(i % 10), 10);

		ac[--j] = Character.forDigit(-i, 10);
		if (flag)
			ac[--j] = '-';
		return new String(ac, j, ac.length - j);
	}
	/**
	 * long转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(long l) {
		return Long.toString(l, 10);
	}
	/**
	 * object转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(Object obj) {
		return obj != null ? obj.toString() : "";
	}
	/**
	 * boolean转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(boolean flag) {
		return flag ? "true" : "false";
	}

	/**
	 * char数组转为字符串
	 * @param c
	 * @return
	 */
	public static String valueOf(char ac[]) {
		return new String(ac);
	}

	/**
	 * 字符串数组转为字符串
	 * @param c
	 * @return
	 */
	public static String ArrayToString(String[] arr, String sign) {
		String str = "";
		if (sign == null || sign.length() == 0)
			sign = ",";
		if (arr != null && arr.length != 0) {
			for (int i = 0; i < arr.length; i++)
				if (arr[i] != null && arr[i].length() != 0)
					str += sign + arr[i];
			if (str.length() != 0)
				str = str.substring(1);
		}
		return str;
	}
	/**
	 * 将日期格化为yyyy-MM-dd
	 * @param d
	 * @return
	 */
	public static String formatDateYyyymmdd(Date d){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(d == null)
			return "";
		else
			return 
				formatter.format(d);
	}
	/**
	 * 将日期格式化为MM-dd
	 * @param d
	 * @return
	 */
	public static String formatDateToMmdd(Date d){
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		if(d == null)
			return "";
		else
			return 
				formatter.format(d);
	}
	/**
	 * 将日期格式化本地默认格式
	 * @param d
	 * @return
	 */
	public static String formatDate(Date d) {
		if (d == null)
			return "";
		else
			return DateFormat.getDateInstance().format(d);
		// return d.toLocaleString().substring(0,10);
	}
	/**
	 * 将字符转为日期类
	 * @param d
	 * @return
	 */
	public static Date formatStringDate(String d) {
		if (d == null)
			return null;
		else{
			Date date = null;
			try {
				date = DateFormat.getDateInstance().parse(d);
			} catch (Exception e) {
			}
			return date;
		}
		// return d.toLocaleString().substring(0,10);
	}
	/**
	 * 将Integer类型日期转化为YYYY-MM-DD格式
	 * @param i
	 * @return
	 */
	public static String integerDateFormat(Integer i){
		String date="";
		if(i==null || i.toString().length()<8){
			return "";
		}else{
			String temp=i.toString();
			date=temp.substring(0, 4)+"-"+temp.substring(4,6)+"-"+temp.substring(6,8);
		}
		return date;
	}
	/**
	 * 将YYYY-MM-DD格式的字符串转为数字(YYYYMMDD)
	 * 
	 * @param d
	 * @return
	 */
	public static Integer formatDateToInteger(Date d){
		return formatDate(formatDateYyyymmdd(d));
	}
	/**
	 * 将YYYY-MM-DD格式的字符串转为数字(YYYYMMDD)
	 * @param d
	 * @return
	 */
	public static Integer formatDate(String d) {
		Integer iDate = null;
		try {
			if (d != null && d.trim().length()>0) {
				String str[] = d.split("-");
				if (str.length == 3) {
					if(str[1].length()==1)
						str[1] = "0"+str[1];
					String newd = str[0] + str[1] + str[2];
					iDate = Integer.valueOf(newd);
				}
			}
		} catch (Exception e) {
		}
		return iDate;
	}
	/**
	 * 将null串转为""
	 * @param value
	 * @return
	 */
	public static String valueOf(String value) {
		if (value != null && !(value.equals("")))
			return value;
		else
			return "";
	}
	/**
	 * 将null串转为"0"
	 * @param value
	 * @return
	 */
	public static String intValueOf(String value) {
		if (value != null && !(value.equals("")))
			return value;
		else
			return "0";
	}
	/**
	 * 将null转为0
	 * @param value
	 * @return
	 */
	public static Integer intValueOf(Integer value) {
		if (value != null)
			return value;
		else
			return new Integer("0");
	}
	/**
	 * 替换字符串 参数: str 中的 str1 替换成为 str2 生成串 返回
	 */
	public static java.lang.String stringReplace(String str, String str1,
			String str2) {
		StringBuffer strbuff = new StringBuffer();
		try {
			if (str != null && str.length() != 0) {
				int intL = str.indexOf(str1);
				while (intL != -1) {
					strbuff.append(str.substring(0, intL) + str2);
					str = str.substring(intL + str1.length(), str.length());
					intL = str.indexOf(str1);
				}
				if (str != null && str.length() != 0) {
					strbuff.append(str.substring(0, str.length()));
				}
			}
		} catch (Exception ex) {
			log.error(ex.getStackTrace());
//			System.out.print(ex.toString());
		}
		return strbuff.toString();
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			DateFormat df = DateFormat.getDateInstance(i);
			Date d = new Date();
			System.out.println(df.format(d));
		}
	}
	
	/**
	 * 解析颜色
	 * 
	 * @param strColor
	 *            可能是预设颜色,如black,read等,也可通过以#号开头的自定义颜色
	 * @return
	 */
	public static Color getColor(String strColor) {
		return getColor(strColor, null);
	}

	/**
	 * 解析颜色
	 * 
	 * @param strColor
	 *            可能是预设颜色,如black,read等,也可通过以#号开头的自定义颜色
	 * @param v
	 *            默认值
	 * @return
	 */
	public static Color getColor(String strColor, Color v) {
		Color color = null;
		if (strColor != null && strColor.trim().length() > 0) {
			strColor = strColor.trim().toLowerCase();
			if (strColor.startsWith("#")) {
				try {
					int i = Integer.decode(strColor).intValue();
					color = new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF,
							i & 0xFF);
				} catch (Exception e) {
				}
			} else {
				if (strColor.equals("black")) {
					color = Color.black;
				} else if (strColor.equals("blue")) {
					color = Color.blue;
				} else if (strColor.equals("cyan")) {
					color = Color.cyan;
				} else if (strColor.equals("darkGray")) {
					color = Color.darkGray;
				} else if (strColor.equals("gray")) {
					color = Color.gray;
				} else if (strColor.equals("green")) {
					color = Color.green;
				} else if (strColor.equals("lightGray")) {
					color = Color.lightGray;
				} else if (strColor.equals("magenta")) {
					color = Color.magenta;
				} else if (strColor.equals("orange")) {
					color = Color.orange;
				} else if (strColor.equals("pink")) {
					color = Color.pink;
				} else if (strColor.equals("red")) {
					color = Color.red;
				} else if (strColor.equals("white")) {
					color = Color.white;
				} else if (strColor.equals("yellow")) {
					color = Color.yellow;
				}
			}
		}
		if (color == null) {
			color = v;
		}
		return color;
	}

	public static String objToString(Object obj){
		return objToString(obj,null);
	}
	public static String objToString(Object obj,String quotation){
		return StringUtils.objToString(obj,quotation,true);
	}
	
	/**
	 * 对象转为字符串
	 * @param obj
	 * @return
	 */
	public static String objToString(Object obj,String quotation,boolean isRemoveBlank){
		if(quotation ==null)
			quotation="";
		if(obj==null)
			return null;
		if(obj instanceof Object[]){
			return arrayToString((Object[])obj,quotation,isRemoveBlank);
		}
		if (obj instanceof List){
			return listToString((List)obj,quotation);
		}else{
			if(isRemoveBlank&&obj.toString().trim().length()==0)
				return "";
			else
				return quotation+obj.toString()+quotation;
		}
	}
	public static String arrayToString(Object obj[]){
		return arrayToString(obj,null);
	}
	public static String arrayToString(Object obj[],String quotation){
		return arrayToString(obj,quotation,true);
	}
	/**
	 * 数组转为字符串
	 * @param obj
	 * @return
	 */
 	public static String arrayToString(Object obj[],String quotation,boolean isRemoveBlank){
 		StringBuffer strBuf=new StringBuffer();
 		if(quotation ==null)
 			quotation ="";
 		if(obj != null){
	 		int iLength = obj.length;
	 		for(int i=0;i<iLength;i++){
	 			if(isRemoveBlank && (obj[i]==null||obj[i].toString().trim().length()==0)){
	 				continue;
	 			}
	 			if(i>0){
	 				strBuf.append(",");
	 			}
	 			strBuf.append(quotation).append(obj[i]).append(quotation);
	 		}
 		}
 		return strBuf.toString();
 	}
 	
 	/**
 	 * 数组转为字符串,只取唯一值.
 	 * @param obj
 	 * @return
 	 */
 	public static String arrayToStringSet(Object obj[]){
 		StringBuffer strBuf=new StringBuffer();
 		Set set = new HashSet(); 
 		if(obj != null){
	 		int iLength = obj.length;
	 		for(int i=0;i<iLength;i++){
	 			set.add(obj[i]);
	 		}
	 		Object tmp = null;
	 		int i=0;
 			for(Iterator it =  set.iterator();it.hasNext();i++){
 				tmp = it.next();
 				if(i>0){
	 				strBuf.append(",");
	 			}
	 			strBuf.append(tmp);
 			}
 		}
 		return strBuf.toString();
 	}
 	public static String listToString(List list){
 		return listToString(list,null);
 	}
 	public static String listToString(List list,String quotation){
 		StringBuffer strBuf=new StringBuffer();
 		
 		if(list != null){
 			if(quotation ==null)
 	 			quotation ="";
	 		int iLength = list.size();
	 		for(int i=0;i<iLength;i++){
	 			if(i>0){
	 				strBuf.append(",");
	 			}
	 			strBuf.append(quotation).append(list.get(i)).append(quotation);
	 		}
 		}
 		return strBuf.toString();
 	}
 	
 	public static String listToStringSet(List list){
 		StringBuffer strBuf=new StringBuffer();
 		if(list != null){
 			Set set = new HashSet(); 
 			set.addAll(list);
 			Object obj = null;
 			int i=0;
 			for(Iterator it =  set.iterator();it.hasNext();i++){
 				obj = it.next();
 				if(i>0){
	 				strBuf.append(",");
	 			}
	 			strBuf.append(obj);
 			}
 		}
 		return strBuf.toString();
 	}

	
}
