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


import org.apache.poi.hssf.util.HSSFColor;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.servlet.ServletUtilities;

import com.ibatis.common.io.ReaderInputStream;

import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.util.StringUtils;


/**
 * 函数说明：row函数,取得当前在列表中是第几行,从1开始记 <br>
 * 语法： row() <br>
 * 参数说明：<br>
 * 无参数 <br>
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
public class RowFunc extends Func {

	
	
	/**
	 * row函数计算
	 */
	public Object calculate() {
		//newCS.getRowIndex() 是以0开始计.
		//row函数,取得当前在列表中是第几行,从1开始记
		return this.newCS.getRowIndex()+1;
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
