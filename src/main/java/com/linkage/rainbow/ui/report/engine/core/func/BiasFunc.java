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




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.servlet.ServletUtilities;

import com.ibatis.common.io.ReaderInputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.util.StringUtils;


/**
 * 函数说明：画表格斜线 <br>
 * 语法： bias(titleExp1,titleExp2,titleExp3) <br>
 * 参数说明：<br>
 * titleExp1 斜线中显示的标题  <br>
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
public class BiasFunc extends Func {

	private static Log log = LogFactory.getLog(BiasFunc.class);
	/**表格斜线图片对象*/
	BufferedImage image;
	/**字体颜色*/
	Color fontColor;
	/**背景颜色*/
	Color backColor;
	/**边框颜色*/
	Color borderColor;
	
	/**
	 * 画表格斜线处理.
	 */
	public Object calculate() {
		
		String result =null; //返回生成图片的路径
		// 取得对应的未扩展前的单元格
		Cell newCell = newCS.getCurrCell();

		
		/***************生成表格斜线图片**********************/
		//取得宽度
		//int width = newCell.getWidth() == 8 ?72:newCell.getWidth()/32;
		int width = 0;
		if( newCell.getWidth()!= null && !newCell.getWidth().equals("")){
			try {
				width =Integer.parseInt(newCell.getWidth());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(width ==0) width=35;

		
		int height = 0;
		if( newCell.getHeight()!= null && !newCell.getHeight().equals("")){
			try {
				height =Integer.parseInt(newCell.getHeight());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(height ==0) //高度按宽度的一定比例
			height=(int)(width/2.5);
		Font font = null;
		if(newCell.getFont() != null){
			font = new Font(newCell.getFont().getFontName(),Font.PLAIN,newCell.getFont().getFontHeight()/20);
			if(newCell.getFontColor() != null)
				fontColor = new Color(newCell.getFontColor().getTriplet()[0],newCell.getFontColor().getTriplet()[1],newCell.getFontColor().getTriplet()[2]);
		}
		else
			font = new Font("宋体",Font.PLAIN,12);
		
		//取得背景色
		if(newCell.getFillBackgroundColor() != null)
			backColor = new Color(newCell.getFillBackgroundColor().getTriplet()[0],newCell.getFillBackgroundColor().getTriplet()[1],newCell.getFillBackgroundColor().getTriplet()[2]);
		else if(newCell.getBgcolor() != null && newCell.getBgcolor().length()>0){
			backColor =StringUtils.getColor(newCell.getBgcolor());
		}
		//取得线条颜色
		if(newCell.getBorderBottomColor() != null)
			borderColor= new Color(newCell.getBorderBottomColor().getTriplet()[0],newCell.getBorderBottomColor().getTriplet()[1],newCell.getBorderBottomColor().getTriplet()[2]);
		else if(newCell.getBorderColor() != null)
			borderColor =StringUtils.getColor(newCell.getBorderColor());
		//如果有记录class,则根据样式文件取得背景色,字体颜色等样式.
		getStytle(newCell);
		
		if(para.length==2)
			createTwo(width,height,font,para[0],para[1]);
		else if(para.length==3)
			createThree(width,height,font,para[0],para[1],para[2]);
		else if(para.length>3){ //超过三个
			if(para.length%2==0){
				String titleLeft[] = new String[para.length/2];
				String titleRight[] = new String[para.length/2];
				System.arraycopy(para,0,titleLeft,0,para.length/2);
				System.arraycopy(para,para.length/2,titleRight,0,para.length/2);
				createTwo(width,height,font,titleLeft,titleRight);
			}else{
				String titleLeft[] = new String[(para.length-1)/2];
				String titleRight[] = new String[(para.length-1)/2];
				System.arraycopy(para,0,titleLeft,0,(para.length-1)/2);
				System.arraycopy(para,(para.length-1)/2+1,titleRight,0,(para.length-1)/2);
				createThree(width,height,font,titleLeft,para[(para.length-1)/2],titleRight);
			}
		}
		String filename = new SU().createJPG(image,"png");
		
		String imgUrl = report.getPara().get("contextPath")
		+ "/servlet/DisplayChart?filename=" + filename;
//		imgUrl = " <img src=\"" + imgUrl + "\" "
//			   + "style=\"width:expression(this.parentElement.offsetWidth+'px');height:expression(this.parentElement.offsetHeight+'px');\" border=0 >";
//		
		imgUrl = " <img id=\""+report.getReportId()+"_cross_head_img\" src=\"" + imgUrl + "\" "
		   + "width=\""+width+"\" height=\""+height+"\" border=0 >";
	
		newCell.setContent(imgUrl);
		/***************生成表格斜线图片end**********************/
		result = imgUrl;
		
		
	
		return result;
	}
	
    private void getStytle(Cell cell){
    	try {
			

	    	if(cell.getStyleClass() != null) {
	    		String realPath = (String)report.getPara().get("realPath");
	    		String contextPath = (String)report.getPara().get("contextPath");
	    		if(report.getPara().get("ext_style") != null){
	    			String extStyles[] = ((String)report.getPara().get("ext_style")).split(",");
	    			for(int i=0;i<extStyles.length;i++){
	    				if(!extStyles[i].startsWith(contextPath)){
	    					extStyles[i] = contextPath + "/"+extStyles[i];
	    				}
	    				String path = realPath+extStyles[i];
	    				File file = new File(path);
	    				FileInputStream fin =  new FileInputStream(file);
	    				DataInputStream in = new DataInputStream(fin);
	    				String sInLine = in.readLine();
	
	    				while(sInLine != null){
//	    					System.out.println(sInLine);
	    					if(sInLine.indexOf(".?"+cell.getStyleClass()+"\\s\\{")>-1) {
//	    						System.out.println("index:"+sInLine);
	    					}
	    					sInLine = in.readLine();
	    				}
	    			}
	    		}
	    	}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
    }

	

	/**
	 * 建立两斜线
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param font 字体
	 * @param title1 标题1
	 * @param title2 标题2
	 */
    public void createTwo(int width,int height,Font font,String title1 ,String title2){
    	String subTitleLeft[] = getSubPara(title1);//
		String subTitleRight[] = getSubPara(title2);//
		createTwo(width,height,font,subTitleLeft,subTitleRight);
    }
	
	/**
	 * 建立两斜线
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param font 字体
	 * @param subTitleLeft[] 左边标题
	 * @param subTitleRight[] 右边标题
	 */
	public void createTwo(int width,int height,Font font,String  subTitleLeft[] ,String subTitleRight[]){
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//设置背景色
		g.setColor(backColor == null?Color.white:backColor); 
		g.fillRect(0, 0, width, height);
		//设备线颜色
		g.setColor(borderColor == null?Color.BLACK:borderColor);
		/***画斜线***/
	
		g.drawLine(0,0 , width,height);//划对角线
		for(int i=0;i<subTitleLeft.length;i++){
			if(i>0){
				g.drawLine(0, 0, width*i/subTitleLeft.length, height); //画斜线
			}	
		}
		for(int i=0;i<subTitleRight.length;i++){
			if(i>0){
				g.drawLine(0, 0, width, height*i/subTitleRight.length);//画斜线
			}
		}
		/***画斜线 end***/
		
		/***画斜线中的标题***/
		//边距
		int iMargin = width/30;
		if(iMargin>5)
			iMargin =5;
		//字体大小
		int iFontSize = font.getSize();
		g.setFont(font);
		g.setColor(fontColor == null?Color.BLACK:fontColor); 

		for(int i=0;i<subTitleLeft.length;i++){//左边标题
			if(i==0){
				//列标题,开始位置为左下角，开始坐标:左边距；图片高度-下边距
				g.drawChars(subTitleLeft[i].toCharArray(),0,subTitleLeft[i].length(),iMargin,height-iMargin);
			}else{
				//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
				//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
				int iDATALength = subTitleLeft[i].length();
				float slope =(float)((float)height/((float)width*(i+0.5f)/subTitleLeft.length));
				
				for(int j=iDATALength-1;j>=0;j--){
					g.drawChars(new char[]{subTitleLeft[i].charAt(j)},0,1,
							(int)((float)width*(i+0.25f)/subTitleLeft.length-((float)iFontSize*(iDATALength-j))),
							(int)((float)height-(float)iMargin*slope-(float)(iFontSize)*slope*(float)(iDATALength-j-1))
					);
				}
			}
		}
		for(int i=0;i<subTitleRight.length;i++){//右边标题
			if(i==subTitleRight.length-1){
				//行标题,结束位置为右上角,开始坐标:图片宽度-(根据字数计算标题总长度+右边距)；上边距+字体大小
				g.drawChars(subTitleRight[i].toCharArray(),0,subTitleRight[i].length(),width-(iFontSize*subTitleRight[i].length()+iMargin),iFontSize);
			}else{
				//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
				//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
				int iDATALength = subTitleRight[i].length();
				float slope =(float)((float)height*(float)(subTitleRight.length-i-0.5f)/(subTitleRight.length)/width);
				
				for(int j=iDATALength-1;j>=0;j--){
					//System.out.println("abcaaa:"+slope+" :"+(int)((float)height-(float)iMargin*slope-(float)(iFontSize)*slope*(float)(iDATALength-j-1)));

					g.drawChars(new char[]{subTitleRight[i].charAt(j)},0,1,
							width-(iFontSize*(iDATALength-j)+iMargin),
							(int)((float)height*(float)(subTitleRight.length-i-0.5)/(subTitleRight.length)+(float)(iFontSize)/6f-(float)(iFontSize)*slope*(float)(iDATALength-j-1))
					);
				}
			}
		}
		/***画斜线中的标题 end***/
	}
	
	/**
	 * 建立三斜线
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param font 字体
	 * @param title1 标题1 
	 * @param title2 标题2
	 * @param title3 标题3
	 */
	public void createThree(int width,int height,Font font,String title1 ,String title2 ,String title3 ){
		String subTitleLeft[] = getSubPara(title1);//
		String subTitleRight[] = getSubPara(title3);//
		createThree(width,height,font,subTitleLeft,title2,subTitleRight);
	}
	/**
	 * 建立三斜线
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param font 字体
	 * @param subTitleLeft[] 左边标题
	 * @param title2 中间标题
	 * @param subTitleRight[] 右边标题
	 */
	public void createThree(int width,int height,Font font,String subTitleLeft[],String title2 ,String subTitleRight[] ){
		if(title2 == null || title2.equals("null") || title2.trim().length()==0){//中间标题为空
			createTwo(width,height,font,subTitleLeft,subTitleRight);
			return;
		}
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//设置背景色
		g.setColor(backColor == null?Color.white:backColor); 
		g.fillRect(0, 0, width, height);
		//设备线颜色
		g.setColor(borderColor == null?Color.BLACK:borderColor);
		
		/***画斜线***/

		for(int i=0;i<subTitleLeft.length;i++){
			g.drawLine(0, 0, (int)(width*(i+1)/(subTitleLeft.length+0.5)), height);
				
		}
		for(int i=0;i<subTitleRight.length;i++){
			g.drawLine(0, 0, width, (int)(height*(i+1)/(subTitleRight.length+0.5)));
				
		}
		/***画斜线 end***/
		
		/***画斜线中的标题***/
		//边距
		int iMargin = width/30;
		if(iMargin>5)
			iMargin =5;
		//字体大小
		int iFontSize = font.getSize();
		g.setFont(font);
		g.setColor(fontColor == null?Color.BLACK:fontColor); 
		float iSubTitleLeftLength=(float)subTitleLeft.length +0.5f;
		for(int i=0;i<subTitleLeft.length;i++){//左边标题
			if(i==0){
				//列标题,开始位置为左下角，开始坐标:左边距；图片高度-下边距
				g.drawChars(subTitleLeft[i].toCharArray(),0,subTitleLeft[i].length(),iMargin,height-iMargin);
			}else{
				//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
				//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
				int iDATALength = subTitleLeft[i].length();
				float slope =(float)((float)height/((float)width*(i+0.5f)/iSubTitleLeftLength));
				
				for(int j=iDATALength-1;j>=0;j--){
					g.drawChars(new char[]{subTitleLeft[i].charAt(j)},0,1,
							(int)((float)width*(i+0.25f)/iSubTitleLeftLength-((float)iFontSize*(iDATALength-j))),
							(int)((float)height-(float)iMargin*slope-(float)(iFontSize)*slope*(float)(iDATALength-j-1))
					);
				}
			}
		}
		float iSubTitleRightLength=(float)subTitleRight.length +0.5f;
		for(int i=0;i<subTitleRight.length;i++){//右边标题
			if(i==subTitleRight.length-1){
				//行标题,结束位置为右上角,开始坐标:图片宽度-(根据字数计算标题总长度+右边距)；上边距+字体大小
				g.drawChars(subTitleRight[i].toCharArray(),0,subTitleRight[i].length(),width-(iFontSize*subTitleRight[i].length()+iMargin),iFontSize);
			}else{
				//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
				//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
				int iDATALength = subTitleRight[i].length();
				float slope =(float)((float)height*(float)(iSubTitleRightLength-i-1)/(iSubTitleRightLength)/width);
				
				for(int j=iDATALength-1;j>=0;j--){
					//System.out.println("abcaaa:"+slope+" :"+(int)((float)height-(float)iMargin*slope-(float)(iFontSize)*slope*(float)(iDATALength-j-1)));

					g.drawChars(new char[]{subTitleRight[i].charAt(j)},0,1,
							width-(iFontSize*(iDATALength-j)+iMargin),
							(int)((float)height*(float)(iSubTitleRightLength-i-1)/(iSubTitleRightLength)+(float)(iFontSize)/6f-(float)(iFontSize)*slope*(float)(iDATALength-j-1))
					);
				}
			}
		}
		//中间标题
		int iDATALength = title2.length();
		for(int i=iDATALength-1;i>=0;i--){
			g.drawChars(new char[]{title2.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin*2),(int)(height-iMargin*((float)height/(float)width)-(iFontSize)*((float)height/(float)width)*(iDATALength-i-1)));
		}
		/***画斜线中的标题 end***/
		
	}
//	
//	
//	/**
//	 * 建立三斜线
//	 * @param width 图片宽度
//	 * @param height 图片高度
//	 * @param font 字体
//	 * @param title1 标题1
//	 * @param title2 标题2
//	 * @param title3 标题3
//	 */
//	public void createTwo_old(int width,int height,Font font,String title1 ,String title2){
//		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.getGraphics();
//		//设置背景色
//		g.setColor(backColor == null?Color.white:backColor); 
//		g.fillRect(0, 0, width, height);
//		//设备线颜色
//		g.setColor(borderColor == null?Color.BLACK:borderColor);
//		/***画斜线***/
//		g.drawLine(0,0 , width,height);
//		/***画斜线 end***/
//		
//		/***画斜线中的标题***/
//		//边距
//		int iMargin = width/30;
//		//字体大小
//		int iFontSize = font.getSize();
//		g.setFont(font);
//		g.setColor(fontColor == null?Color.BLACK:fontColor); 
//
//		//列标题,开始位置为左下角，开始坐标:左边距；图片高度-下边距
//		g.drawChars(title1.toCharArray(),0,title1.length(),iMargin,height-iMargin);
//		//行标题,结束位置为右上角,开始坐标:图片宽度-(根据字数计算标题总长度+右边距)；上边距+字体大小
//		g.drawChars(title2.toCharArray(),0,title2.length(),width-(iFontSize*title2.length()+iMargin),iMargin+iFontSize);
//	}
//	
//	/**
//	 * 建立三斜线
//	 * @param width 图片宽度
//	 * @param height 图片高度
//	 * @param font 字体
//	 * @param title1 标题1
//	 * @param title2 标题2
//	 * @param title3 标题3
//	 */
//	public void createThree_old(int width,int height,Font font,String title1 ,String title2 ,String title3 ){
//		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.getGraphics();
//		//设置背景色
//		g.setColor(backColor == null?Color.white:backColor); 
//		g.fillRect(0, 0, width, height);
//		//设备线颜色
//		g.setColor(borderColor == null?Color.BLACK:borderColor);
//		/***画斜线***/
//		g.drawLine(0,0 , width*3/5,height);
//		g.drawLine(0,0 , width,height*3/5);
//		/***画斜线 end***/
//		
//		/***画斜线中的标题***/
//		//边距
//		int iMargin = width/30;
//		//字体大小
//		int iFontSize = font.getSize();
//		g.setFont(font);
//		g.setColor(fontColor == null?Color.BLACK:fontColor); 
//
//		//列标题,开始位置为左下角，开始坐标:左边距；图片高度-下边距
//		g.drawChars(title1.toCharArray(),0,title1.length(),iMargin,height-iMargin);
//		//行标题,结束位置为右上角,开始坐标:图片宽度-(根据字数计算标题总长度+右边距)；上边距+字体大小
//		g.drawChars(title3.toCharArray(),0,title3.length(),width-(iFontSize*title3.length()+iMargin),iFontSize);
//		//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
//		//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
//		int iDATALength = title2.length();
//		for(int i=iDATALength-1;i>=0;i--){
//			g.drawChars(new char[]{title2.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin),(int)(height-iMargin*((float)height/(float)width)-(iFontSize)*((float)height/(float)width)*(iDATALength-i-1)));
//		}
//		
//	}
//	
//
//	/**
//	 * 建立四斜线
//	 * @param width 图片宽度
//	 * @param height 图片高度
//	 * @param font 字体
//	 * @param title1 标题1
//	 * @param title2 标题2
//	 * @param title3 标题3
//	 * @param title4 标题4
//	 */
//	public void createFour(int width,int height,Font font,String title1 ,String title2 ,String title3,String title4 ){
//		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.getGraphics();
//		//设置背景色
//		g.setColor(backColor == null?Color.white:backColor); 
//		g.fillRect(0, 0, width, height);
//		//设备线颜色
//		g.setColor(borderColor == null?Color.BLACK:borderColor);
//		/***画斜线***/
//		
//		g.drawLine(0,0 , width*1/2,height);
//		g.drawLine(0,0 , width,height);
//		g.drawLine(0,0 , width,height*1/2);
//		/***画斜线 end***/
//		
//		/***画斜线中的标题***/
//		//边距
//		int iMargin = width/30;
//		//字体大小
//		int iFontSize = font.getSize();
//		g.setFont(font);
//		g.setColor(fontColor == null?Color.BLACK:fontColor); 
//
//		//列标题,开始位置为左下角，开始坐标:左边距；图片高度-下边距
//		g.drawChars(title1.toCharArray(),0,title1.length(),iMargin,height-iMargin);
//		//行标题,结束位置为右上角,开始坐标:图片宽度-(根据字数计算标题总长度+右边距)；上边距+字体大小
//		g.drawChars(title4.toCharArray(),0,title4.length(),width-(iFontSize*title4.length()+iMargin),iFontSize);
//		
//		//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
//		//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
//		int iDATALength = title2.length();
//		for(int i=iDATALength-1;i>=0;i--){
//			g.drawChars(new char[]{title2.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin+width*1/4),(int)(height-iMargin*((float)height/((float)width*3/4))-(iFontSize)*((float)height/((float)width*3/4))*(iDATALength-i-1)));
//		}
//		
//		iDATALength = title3.length();
//		for(int i=iDATALength-1;i>=0;i--){
//			g.drawChars(new char[]{title3.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin),(int)(height*3/4-iMargin*((float)height*3/4/(float)width)-(iFontSize)*((float)height*3/4/(float)width)*(iDATALength-i-1)));
//		}
//		
//	}
//	
//	/**
//	 * 建立五斜线
//	 * @param width 图片宽度
//	 * @param height 图片高度
//	 * @param font 字体
//	 * @param title1 标题1
//	 * @param title2 标题2
//	 * @param title3 标题3
//	 * @param title4 标题4
//	 * @param title5 标题5
//	 */
//	public void createFive(int width,int height,Font font,String title1 ,String title2 ,String title3,String title4 ,String title5){
//		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.getGraphics();
//		//设置背景色
//		g.setColor(backColor == null?Color.white:backColor); 
//		g.fillRect(0, 0, width, height);
//		//设备线颜色
//		g.setColor(borderColor == null?Color.BLACK:borderColor);
//		/***画斜线***/
//		
//		g.drawLine(0,0 , width*3/4,height);
//		g.drawLine(0,0 , width*2/5,height);
//		g.drawLine(0,0 , width,height*3/4);
//		g.drawLine(0,0 , width,height*2/5);
//		/***画斜线 end***/
//		
//		/***画斜线中的标题***/
//		//边距
//		int iMargin = width/30;
//		//字体大小
//		int iFontSize = font.getSize();
//		g.setFont(font);
//		g.setColor(fontColor == null?Color.BLACK:fontColor); 
//
//		//列标题,开始位置为左下角，开始坐标:左边距；图片高度-下边距
//		g.drawChars(title1.toCharArray(),0,title1.length(),iMargin,height-iMargin);
//		//行标题,结束位置为右上角,开始坐标:图片宽度-(根据字数计算标题总长度+右边距)；上边距+字体大小
//		g.drawChars(title5.toCharArray(),0,title5.length(),width-(iFontSize*title5.length()+iMargin),iFontSize);
//		
//		//数据标题,按最后一个字存放在右下角，每个字按对角线位置倾斜摆放	
//		//每个字的摆放坐标：图片宽度-(右边字的数量*字体大小+右边距);图片高度-下边距-右边字的数量*(字体大小*斜率)
//		int iDATALength = title2.length();
//
//		
//		for(int i=iDATALength-1;i>=0;i--){
//			g.drawChars(new char[]{title2.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin+width*2/5),(int)(height-iMargin*((float)height/((float)width*3/5))-(iFontSize)*((float)height/((float)width*3/5))*(iDATALength-i-1)));
//		}
//		
//		iDATALength = title3.length();
//		for(int i=iDATALength-1;i>=0;i--){
//			g.drawChars(new char[]{title3.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin),(int)(height-iMargin*((float)height/(float)width)-(iFontSize)*((float)height/(float)width)*(iDATALength-i-1)));
//		}
//		
//		iDATALength = title4.length();
//		for(int i=iDATALength-1;i>=0;i--){
//			g.drawChars(new char[]{title4.charAt(i)},0,1,width-(iFontSize*(iDATALength-i)+iMargin),(int)(height*3/5-iMargin*((float)height*3/5/(float)width)-(iFontSize)*((float)height*3/5/(float)width)*(iDATALength-i-1)));
//		}
//		
//	}
//	
	
	
	
	
	
	
	
    /**
     * 取得表达式字符串
     * @return 表达式字符串
     */
    public String getExp(){
    	return "";
    }
    
    private class SU extends ServletUtilities{

    	public  String createJPG(BufferedImage image,String imgFormat){
            
            ServletUtilities.createTempDir();
            String prefix = "jfreechart-onetime-"; 
            
            File tempFile = null;
            
    		try {
    			if(imgFormat.toUpperCase().equals("JPEG")){
	    			tempFile = File.createTempFile(prefix, ".jpeg", 
	    	                    new File(System.getProperty("java.io.tmpdir")));
	    			//System.out.println("tempFile:"+tempFile);
	    			FileOutputStream fos = new FileOutputStream(tempFile);
	    			BufferedOutputStream bos = new BufferedOutputStream(fos);
	    			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);   		
	    			encoder.encode(image);
	    			bos.close();
    			} else {
    				tempFile = File.createTempFile(prefix, ".png", 
    	                    new File(System.getProperty("java.io.tmpdir")));
    				//System.out.println("tempFile:"+tempFile);
    				FileOutputStream fos = new FileOutputStream(tempFile);
    				EncoderUtil.writeBufferedImage(image, ImageFormat.PNG, fos);
    			}
    			
    		} catch (FileNotFoundException fnfe) {
    			log.error(fnfe.getStackTrace());
    		} catch (IOException ioe) {
    			log.error(ioe.getStackTrace());
    		}
    		//取得对应的未扩展前的单元格
    		Cell newCell = newCS.getCurrCell();
    		if(tempFile!=null)
    		newCell.setImgPath(tempFile.getPath());
            return tempFile.getName();
    	}
    }
    
}
