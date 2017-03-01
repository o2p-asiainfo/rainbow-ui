package com.linkage.rainbow.ui.report.engine.core.func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.linkage.rainbow.ui.report.engine.core.DBFunc;
import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.FuncParse;
import com.linkage.rainbow.ui.report.engine.core.expr.CSVariable;
import com.linkage.rainbow.ui.report.engine.core.expr.DBVariable;
import com.linkage.rainbow.ui.report.engine.model.Cell;
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
 * 函数说明： 根据分组表达式，从数据集中取出一个分组,并填冲至newCS中. <br>
 * 语法： dsName.group(groupExp,filterExpdrillExp,orderExp) <br>
 * 参数说明：<br>
 * groupExp 选出的分组表达式,[ID值,显示值],如果只有ID值则显示时显示ID值. <br>
 * filterExp 过滤表达式<br>
 * drillExp 钻取表达式,[钻取方式,是否钻取条件判断表达式,钻取条件,是否钻取条件判断表达式,钻取条件...,钻取条件] 采用类oracle decode 的方式.
 *          钻取方式:1为在原有页面所在行的下面增加钻取的行.2为只显示钻取后的数据.3前两种方式都支持.
 *          是否钻取条件判断表达式:可以为空.条件判断表达式,如当钻取到最底一层时,不再钻取.
 *          钻取条件:钻取时需要替换的条件参数,例:"day_id="+day_id+"&area_id="+area_id
 * orderExp 分组表达式结果数据的排序顺序，true为顺序，false为逆序<br>
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
public class DBGroupFunc extends DBFunc {

	/**
	 * 数据集分组函数计算
	 */
	public Object calculate() {
		
		if(ds == null)
			return "记录集对象:"+dsName+" 为null,请检查报表设置!";
		String groupExpTmp[] = getSubPara(para.length > 0&&para[0] != null ? para[0]: null);
		// 分组字段
		//String groupExp = para.length > 0&&para[0] != null ? para[0].trim().toLowerCase() : null;
		String groupExp = groupExpTmp != null && groupExpTmp.length>0 && groupExpTmp[0] != null ?groupExpTmp[0].trim():null;
		
		String groupExpShowValue = groupExpTmp != null && groupExpTmp.length>1 && groupExpTmp[1] != null ?groupExpTmp[1].trim():null;
		
		
       		
		//钻取表达式
		String drillExp =  para.length > 2&&para[2] != null ? para[2].trim() : null;
		String drillExpSubPara[] = getSubPara(drillExp);
		
		List result =null;		
		if (groupExp != null && groupExp.trim().length() > 0) {
			//取得当前分组数据
			
			result = getGroup(groupExp,groupExpShowValue,drillExpSubPara);
			
		}
		//取得多数据源关联,增加外关联数据.
		result = addRelDbVal(result,groupExpShowValue);
		
		//根据过滤表达式,对结果集进行筛选,取得符合条件的记录.
		result = getFilterResult(result);
		//setDrillPath(drillExpSubPara);
		return result;
	}

	/**
	 * 增加
	 *
	 */
	private List addRelDbVal(List list,String groupExpShowValue){
		Cell cell = this.newCS.getCurrCell();
		//取得本单元格的同方向分组事件
		Events events = cell.getEvents();
		String extDirectionExp = DirectionUtil.getDirection(cell.getExtDirection());
		boolean isLengthways = extDirectionExp.equals("y")?true:false; //是否为纵向
		List groupEventList = events.getGroupEventByDirection(isLengthways,dsName);
		int iGroupEventListSize = groupEventList.size();
		
		List relDBVarList = cell.getRelDBVar();
		int iRelDBVarListSize = relDBVarList.size();
		
		 
		for(int j=0;j<iRelDBVarListSize;j++){ //按关联的其他数据集进行循环.取得关联的其他数据集字段,取得此字段分组,
			DBVariable dbVar = (DBVariable)relDBVarList.get(j);//取得关联的数据集字段
			List dbVarList = dbVar.getExpr().getDbVarList(); //取得表达式中的其他数据集关联关系,用于下面判断是否存在上级分组
			DBGroup preGroup = null;
			boolean preIsNull= false;
			for(int i=0;i<iGroupEventListSize;i++){//对分组事件循环,看是否有上级分组事件存在.
				Event event = (Event)groupEventList.get(i);
				if(((DBGroup)event.getEventValue()).getValue().toString().equals("2918")){
//					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				}
				for(int k=0;k<dbVarList.size();k++){//对表达式中的其他数据集关联关系循环,如果其他关联关系是否与分组事件存在关系.
					DBVariable dbVarDetail = (DBVariable)dbVarList.get(k); //其他关联关系中的数据集字段,如表达式 PROD_ID4=B1&&BILLING_TYPE_ID=B2 则数据集字段为PROD_ID4 与 BILLING_TYPE_ID
					CSVariable csVariable = (CSVariable)(dbVarDetail.getFSign().getRight()); //其他关联关系中的单元格坐标,如 B1 B2
					if(event.getSrcCoordinate().equals(csVariable.getCoordinate())){//如果分组事件的发起坐标与 其他数据集关联 中的坐标一致,表示有上级分组.
						
						if(preGroup !=null){
							DBGroup groupTmp = preGroup.getDBGroupByValue(dbVarDetail.getColName(),((DBGroup)event.getEventValue()).getValue());
							if(groupTmp == null){ //此关联关系中,例PROD_ID4=B1,PROD_ID4字段没有对应的分组列表.
								preIsNull = true;
								break;
							}else{
								//设置上级分组
								preGroup = groupTmp;
							}
						}else {
							//根据分组字段名,与分组值,取得分组对象.
							DBGroup groupTmp = dbVarDetail.getDs().getDBGroupByValue(dbVarDetail.getColName(),((DBGroup)event.getEventValue()).getValue());
							if(groupTmp == null){ //此关联关系中,例PROD_ID4=B1,PROD_ID4字段没有对应的分组列表.
								preIsNull = true;
								break;
							}else{
								//设置上级分组
								preGroup = groupTmp;
							}
						}
					}
				}
			}
			
			if(!preIsNull){//如果关联关系中,例PROD_ID4=B1,PROD_ID4字段没有对应的分组列表,则不对list原记录集做任何操作.
				List resultGroupList = null;
				if(preGroup!=null){//如果上级分组存在,则根据上级分组取得下级分组列表
					resultGroupList = preGroup.getLowerDBGroup(dbVar.getColName(),groupExpShowValue,null);
				}else {
					resultGroupList = dbVar.getDs().getDBGroup(dbVar.getColName(),groupExpShowValue,null);
				}
				//判断关联的数据集字段对应的分组,是否有存在本单元格没有包含的分组值,如果没有则增加此分组值.此计算类似于数据库的外关联计算
				int iListSize = list.size();
				int iResultGroupListSize = resultGroupList.size();
				Map<String,DBGroup> groupMap = new HashMap();
				for(int i=0;i<iListSize;i++){//本单元格计算出的数据集
					DBGroup groupTmp = (DBGroup)list.get(i);
					if(groupTmp.getValue() != null)
						groupMap.put(groupTmp.getValue().toString(),groupTmp);
				}
				for(int i=0;i<iResultGroupListSize;i++){//对关联的数据字段对应的分组循环判断,是否已被包含,如果没有则增加此分组值
					DBGroup groupTmp = (DBGroup)resultGroupList.get(i);
					if(groupTmp.getValue() != null){
						if(groupMap.get(groupTmp.getValue().toString()) == null){
							groupTmp.addDummyDsName(dsName);//增加设置虚拟记录集名
							list.add(groupTmp);
						}
					}
				}
			}
			
		}
		
		
		return list;
	}
	
	/**
	 * 根据分组表达式,过滤表达式取得分组List
	 * @param groupExp
	 * @param groupExpShowValue
	 * @param drillExpSubPara
	 * @return
	 */
	public List getGroup(String groupExp,String groupExpShowValue,String[] drillExpSubPara){
		List result =new ArrayList();
		Cell cell = this.newCS.getCurrCell();
		Events events = cell.getEvents();
		Iterator iterator = events.getGroupEvent(this.dsName);
		DBGroup preGroup =null;
		boolean isBreak=false;
		if(iterator != null){
			for(;iterator.hasNext();){
				Event event = (Event)iterator.next();
				DBGroup group = (DBGroup)event.getEventValue();
				if(group.getDs().getDsName().equals(this.dsName)){
					if(preGroup ==null){
						preGroup = group;
					} else {
						preGroup = preGroup.cross(group);
					}
				}else {
					isBreak = true;
					break;
				}
			}
		}
		if(!isBreak){
			if(preGroup == null){
				result = ds.getDBGroup(groupExp,groupExpShowValue,drillExpSubPara);
			} else {
				result = preGroup.getLowerDBGroup(groupExp,groupExpShowValue,drillExpSubPara);
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
		return isBlank ;
	}
	
	public static void main(String args[]) {
		// Func f = Func.createInstance("group");
		// System.out.print(f.calculate());
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
