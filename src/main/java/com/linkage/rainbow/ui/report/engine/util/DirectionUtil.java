package com.linkage.rainbow.ui.report.engine.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.engine.util.Properties;
import com.linkage.rainbow.ui.report.engine.core.ReportEngine;

/**
 * 方向定义,横向/纵向的定义.如值:x,y;-,| 表示x或-表示横向,y或|表示纵向 
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
public class DirectionUtil {
	private static Log log = LogFactory.getLog(DirectionUtil.class);
	//	横向的,多个用,号分隔.例:,x,-,横,
	private static String transverse =",";
	//	纵向的,多个用,号分隔.例:,y,|,纵,
	private static String lengthways =",";
	// 报表引擎初始化
	static {
		try {
			init();
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new RuntimeException(
					"Error initializing ReportEngine class. Cause:" + e);
		}
	}

	/**
	 * 初始化横向纵向的表过字符.
	 * @throws Exception
	 */
	private static void init() throws Exception {
		String direction = ReportEngine.getProp().getProperty("comm.rp.direction");
		if(direction != null && direction.trim().length()>0){
			String directions[] = direction.split(";");
			if(directions != null){
				for(int i=0;i<directions.length;i++){
					String xy[] = directions[i].split(",");
					if(xy != null && xy.length==2){
						transverse +=xy[0]+",";
						lengthways +=xy[1]+",";
					}
				}
			}
		}
		transverse = transverse.toLowerCase();
		lengthways = lengthways.toLowerCase();
//		System.out.println("transverse:"+transverse);
//		System.out.println("lengthways:"+lengthways);
	}
	
	/**
	 * 将横向转为x,纵向转为y
	 * @param para
	 * @return
	 */
	public static String getDirection(String para){
		//x表示横向y表示纵向
		String direction = "y";
		if(para != null && para.trim().length()>0){
			if(lengthways.length()>1 && transverse.length()>1){
				String dirPara = para.trim().toLowerCase();
				if(transverse.indexOf(","+dirPara+",")>-1){//如果为横向
					direction = "x";
				} else {
					direction = "y";
				}
			}
		}
		return direction;
	}
	
	/**
	 * 将横向转为x,纵向转为y
	 * @param para 报表参数
	 * @param idx 参数的位置
	 * @return
	 */
	public static String getDirection(String[] para,int idx){
		//默认前台表过时x表示横向y表示纵向
		//各个处理函数中y表示横向x表示纵向,
		String direction = "y";
		if(para != null && para.length>idx && para[idx] != null && para[idx].trim().length()>0){
			if(lengthways.length()>1 && transverse.length()>1){
				String dirPara = para[idx].trim().toLowerCase();
				if(transverse.indexOf(","+dirPara+",")>-1){//如果为横向
					direction = "x";
				} else {
					direction = "y";
				}
			}
		}
		return direction;
	}
}
