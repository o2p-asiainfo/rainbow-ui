package com.linkage.rainbow.ui.report.engine.core.func;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.linkage.rainbow.ui.report.engine.core.DBFunc;
import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.FuncParse;
import com.linkage.rainbow.ui.report.engine.core.Sign;
import com.linkage.rainbow.ui.report.engine.core.expr.CSVariable;
import com.linkage.rainbow.ui.report.engine.core.expr.Variable;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.Coordinate;
import com.linkage.rainbow.ui.report.engine.model.Event;
import com.linkage.rainbow.ui.report.engine.model.Events;
import com.linkage.rainbow.ui.report.engine.model.Field;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.CachedRowSet;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.Compare;
import com.linkage.rainbow.ui.report.engine.util.DirectionUtil;
import com.linkage.rainbow.ui.report.engine.util.IbatisUtil;

/**
 * 函数说明：根据条件表达式，从数据集中取出一个字段,并填冲至newCS中. <br>
 * 语法： dsName.select(selectExp,filterExp,drillExp ,orderExp ) <br>
 * 参数说明：<br>
 * selectExp 字段表达式,[ID值,显示值],如果只有ID值则显示时显示ID值. 可以是单个字段,也可是各字段运算的结果.分ID值与显示值主要在于多个记录集关联显示时使用  <br>
 * filterExp 过滤表达式,用于多数据集的关联运算；<br>
 * drillExp 钻取表达式,[钻取方式,[显示路径ibatis sqlMapId],是否钻取条件判断表达式,钻取条件,是否钻取条件判断表达式,钻取条件...,钻取条件] 采用类oracle decode 的方式.
 *          钻取方式:1为在原有页面所在行的下面增加钻取的行.2为只显示钻取后的数据.3前两种方式都支持.
 *          是否钻取条件判断表达式:可以为空.条件判断表达式,如当钻取到最底一层时,不再钻取.
 *          钻取条件:可以为空,如果为空则钻取的条件默认为本分组及所有上级分组,参数名与分组名相同.如果自定义,则可改传递时的参数名,例:"day_id="+day_id+"&area_id="+area_id
 *
 * orderExp 达式结果数据的排序顺序，true为顺序，false为逆序 <br>
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

public class DBSelectFunc extends DBFunc {

	
	private Expr selectExp = null;
	
	private Expr selectExpShowValue = null;


	private boolean isCross = false;

	public DBSelectFunc() {

	}

	private Field groupField = null;
	


	/**
	 * 从数据集中取出一个字段,将数值填充到newCS中.
	 */
	public Object calculate() {
		if(ds == null)
			return "记录集对象:"+dsName+" 为null,请检查报表设置!";
		
		List result =null;		
		String selectExpTmp[] = getSubPara(para.length > 0&&para[0] != null ? para[0]: null);
		String selectExpStr = selectExpTmp != null && selectExpTmp.length>0 && selectExpTmp[0] != null ?selectExpTmp[0].trim():null; //.toLowerCase()
		String selectExpShowValueStr = selectExpTmp != null && selectExpTmp.length>1 && selectExpTmp[1] != null ?selectExpTmp[1].trim():null;

				
//		if(selectExpStr.equals("COMPLETED_DATE"))
//			System.out.println("aaaaa");
		//钻取表达式
		String drillExp =  para.length > 2&&para[2] != null ? para[2].trim() : null;
		String drillExpSubPara[] = getSubPara(drillExp);
//		String drillType = drillExpSubPara != null && drillExpSubPara.length>0?drillExpSubPara[0]:null;
//		String drillTrim = drillExpSubPara != null && drillExpSubPara.length>1?drillExpSubPara[1]:null;
//		String drillAvai= drillExpSubPara != null && drillExpSubPara.length>2?drillExpSubPara[2]:null;//钻取有效条件判断,当条件为true时,才能钻取
		//取得当前分组下记录集
		List groupRows = getGroupEventRows();
		BaseRow row = getSelectEventRows();
		List rows = cross(groupRows,row);
		if(rows != null){
			result = rows ;
		} else {
			result = ds.getCachedRows();
		}
		
		//根据过滤表达式,对结果集进行筛选,取得符合条件的记录.
		result = getFilterResult(result);
		
		if(result != null ){
			int iSize = result.size();
			for(int i=0;i<iSize;i++){
				BaseRow tmpRow = (BaseRow)result.get(i);
				Object value = getExprCalculate(tmpRow,selectExpStr);
				Object showValue = selectExpShowValueStr!= null?getExprCalculate(tmpRow,selectExpShowValueStr):value;
				tmpRow.setValue(value);
				tmpRow.setShowvalue(showValue);
			}
		}
		return result;
	}
	
	/**
	 * 取得分组事件与select选择事件交叉的记录
	 * @param rows
	 * @param row
	 * @return
	 */
	private List cross(List rows,BaseRow row){
		List result =null;
		if(rows ==null && row==null){
			result =null;
		} else if(rows ==null && row!=null){
			result = new ArrayList();
			result.add(row);
		}else if(rows !=null && row==null){
			result = rows;
		} else {
			result = new ArrayList();
			if(rows!=null){
				int iSize = rows.size();
				for(int i=0;i<iSize;i++){
					BaseRow tmp = (BaseRow)rows.get(i);
					if(tmp.getIndex()==row.getIndex()){
						result.add(row);
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 取得分组事件
	 * @return
	 */
	private List getGroupEventRows(){
		List result =null;
		Cell cell = this.newCS.getCurrCell();
		Events events = cell.getEvents();
		Iterator iterator = events.getGroupEvent(this.dsName);
		DBGroup preGroup =null;
		if(iterator != null){
			for(;iterator.hasNext();){
				Event event = (Event)iterator.next();
				DBGroup group = (DBGroup)event.getEventValue();
				if(preGroup ==null){
					preGroup = group;
				} else {
					preGroup = preGroup.cross(group);
				}
			}
		}
		
		if(preGroup != null){
			result = preGroup.getRows();
		}

		return result;
	}
	
	/**
	 * 取得列表事件
	 * @return
	 */
	private BaseRow getSelectEventRows(){
		BaseRow result =null;
		Cell cell = this.newCS.getCurrCell();
		Events events = cell.getEvents();
		Iterator iterator = events.getSelectEvent(this.dsName);
		BaseRow preRow =null;
		if(iterator != null){
			for(;iterator.hasNext();){
				Event event = (Event)iterator.next();
				BaseRow row = (BaseRow)event.getEventValue();
				if(preRow ==null){
					preRow = row;
				} else {
					if(preRow.getIndex()==row.getIndex()){
						continue;
					}else {
						break;
					}
					
				}
			}
		}
		result = preRow;
		return result;
	}
	
	private void setDrillPath(String[] drillExpSubPara){
		//取得钻取的路径
		String drillPath = drillExpSubPara != null && drillExpSubPara.length>1?drillExpSubPara[1]:null;
		if(drillPath != null && drillPath.trim().length()>0){
			CachedRowSet crs = IbatisUtil.queryForRowSet(drillPath,report.getPara());
			try {
				while(crs.next()){
//					System.out.println(crs.getString(1));
					
				}	
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}
	
	
	/**
	 * 设置报表钻取属性
	 * @param newCell 当前单元格
	 * @param drillExpSubPara 关于钻取的所有子参数:[钻取方式,是否钻取条件判断表达式,钻取条件,是否钻取条件判断表达式,钻取条件...,钻取条件] 采用类oracle decode 的方式.
	 * @param group 对应的当前分组
	 */
	private void setDrill(Cell newCell,String[] drillExpSubPara){
		//取得钻取类型
		String drillType = drillExpSubPara != null && drillExpSubPara.length>0?drillExpSubPara[0]:null;
		
		
		//drillType 不为空,则表示可以钻取
		if(drillType != null && drillType.trim().length()>0){
			newCell.setDrillType(drillType); //设置钻取类型
			int iDrillExpSubParaLength = drillExpSubPara.length;
				//按是否钻取、钻取条件匹配的方式设置，最后一个可以只设置钻取条件，表示除前面条件以外，则按最后钻取条件钻取。
				for(int k=1;k<=iDrillExpSubParaLength-1;k=k+2){
					String drillAvai= null;
					String drillTrim = null;
					if(k==iDrillExpSubParaLength-1){
						drillAvai = "true";
						drillTrim =drillExpSubPara[k];
					}else{
						drillAvai= drillExpSubPara[k];//钻取有效条件判断,当条件为true时,才能钻取
						if(k+1 < iDrillExpSubParaLength)
							drillTrim =drillExpSubPara[k+1];
					}
					if(drillAvai != null && drillAvai.trim().length()>0) {
						Object avai = drillAvai.equals("true")?drillAvai:getExprCalculate(drillAvai);
						if(avai != null && avai.toString().trim().toLowerCase().equals("true")){
							newCell.setDrillAvai(true);
							newCell.setDrillTerm(getExprCalculate(drillTrim));
							break;
						}
					}
				}
			
		}
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
	 * 计算表达式
	 * @param expStr
	 * @return
	 */
	public Object getExprCalculate(BaseRow row,String expStr){
		Object result = null;
		if(expStr != null && expStr.trim().length()>0){
			try {
				ds.goTo(row.getIndex());
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
	 * 判断是否为空格
	 * 
	 * @param cell
	 * @return
	 */
	public boolean isBlank(Cell cell) {
		boolean isBlank = false;
		if (cell == null || cell.isBlank()) {
			isBlank = true;
		}
		return isBlank;
	}
	
	/**
	 * 取得表达式字符串
	 * 
	 * @return 表达式字符串
	 */
    public String getExp(){
    	return "";
    }
}
