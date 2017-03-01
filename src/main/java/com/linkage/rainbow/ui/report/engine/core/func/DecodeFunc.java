package com.linkage.rainbow.ui.report.engine.core.func;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;


import org.apache.poi.hssf.util.HSSFColor;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.servlet.ServletUtilities;

import com.ibatis.common.io.ReaderInputStream;

import com.linkage.rainbow.util.StringUtil;
import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.StringUtils;


/**
 * 函数说明：decode函数 <br>
 * 语法： decode(calculateExp,value1,result1,value2,result2,result) <br>
 * 计算表达式,值1,结果值,...,结果值] 采用类oracle decode 的方式.
 * 参数说明：<br>
 * calculateExp 计算表达式  <br>
 * value1 表达式计算结果值1
 * result1 最终返回值1
 * @version 1.0
 * @author 陈亮 2009-08-10
 *         <hr>
 *         修改记录
 *         <hr>
 *         1、修改人员:陈亮 修改时间:2009-08-10<br>
 *         修改内容:新建
 *         <hr>
 *
 */
public class DecodeFunc extends Func {

	
	
	/**
	 * decode函数计算
	 */
	public Object calculate() {
		Object result = null;

		if(para.length<3)
			return "函数没有足够的参数!";
		
		String calculateExp = para[0];
		Object calculateExpResult = null;
		calculateExpResult= getExprCalculate(calculateExp);
		calculateExpResult = resultToStr(calculateExpResult); //转换为字符串
		for(int k=1;k<=para.length-1;k=k+2){
			if(k==para.length-1){
				result= getExprCalculate(para[k]);
			}else{
				Object value = getExprCalculate(para[k]);
				if(value!= null && calculateExpResult != null && value.toString().equals(calculateExpResult)){
					result= getExprCalculate(para[k+1]);
					break;
				}
			}
		}
		
	
		return result;
	}
	
	
	/**
	 * 计算表达式
	 * @param expStr
	 * @return
	 */
	public Object getExprCalculate(String expStr){
		Object result = null;
		if(expStr != null && expStr.trim().length()>0){
			try {
				Expr expr = new Expr(expStr,this.report,this.dsName);
				Object obj = expr.calculate();
				//result = obj != null?obj.toString():null;
				result = obj ;
			} catch (Exception e) {
			}
		}
		return result;
	}
	

	/**
	 * 取得表达式字符串
	 * 
	 * @return 表达式字符串
	 */
	public String getExp(){
		return null;
	}
 
}
