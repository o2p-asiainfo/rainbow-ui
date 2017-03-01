package com.linkage.rainbow.ui.report.engine.rowset;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.linkage.rainbow.util.StringUtil;
import com.linkage.rainbow.ui.report.engine.core.Expr;
import com.linkage.rainbow.ui.report.engine.core.Func;
import com.linkage.rainbow.ui.report.engine.core.FuncParse;
import com.linkage.rainbow.ui.report.engine.core.expr.DBVariable;
import com.linkage.rainbow.ui.report.engine.model.Cell;
import com.linkage.rainbow.ui.report.engine.model.PropertyDefine;
import com.linkage.rainbow.ui.report.engine.util.Compare;

/**
 * 记录集分组类
 * 
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
public class DBGroup implements Serializable, Cloneable {


	// 当前分组字段名
	private String groupName;
	//
	private String showGroupName;
	private String[] drillExpSubPara;

	// 分组值.
	private Object value;
	
	// 分组显示值.
	private Object showvalue;
	
	private List rows = new ArrayList();


	private CachedRowSet ds = null;
	//对应记录集中的哪一行.
	private int dsCurrIdx =-1;

	//虚拟记录集名称,用记多数据源判断
	private Set dummyDsNameSet = new HashSet();
	/**
	 * 空构造
	 *
	 */
	public DBGroup() {

	}
	
	/**
	 * 根据记录集，分组名,构造分组
	 * @param ds 记录集对象
	 * @param groupName 分组名
	 * @see CachedRowSet
	 */
	public DBGroup(CachedRowSet ds,String groupName) {
		this.ds=ds;
		this.groupName = groupName;
	}
	
	/**
	 * 根据记录集，分组名,分组显示名，(如AREA_ID,AREA_NAME)构造分组
	 * @param ds 记录集对象
	 * @param groupName 分组名
	 * @param groupName 分组显示名
	 * @see CachedRowSet
	 */
	public DBGroup(CachedRowSet ds,String groupName,String showGroupName) {
		this.ds=ds;
		this.groupName = groupName;
		this.showGroupName = showGroupName;
	}
	/**
	 * 根据记录集，分组名,分组显示名，(如AREA_ID,AREA_NAME),钻取参数表达式构造分组
	 * @param ds 记录集对象
	 * @param groupName 分组名
	 * @param groupName 分组显示名
	 * @param drillExpSubPara 钻取参数表达式
	 * @see CachedRowSet
	 */
	public DBGroup(CachedRowSet ds,String groupName,String showGroupName,String[] drillExpSubPara) {
		this.ds=ds;
		this.groupName = groupName;
		this.showGroupName = showGroupName;
		this.drillExpSubPara = drillExpSubPara;
	}
	
	/**
	 * 取得记录集
	 * @return 记录集
	 */
	public CachedRowSet getDs() {
		return ds;
	}

	/**
	 * 设置记录集
	 * @param ds 记录集
	 */
	public void setDs(CachedRowSet ds) {
		this.ds = ds;
	}

	/**
	 * 取得记录集当前行数
	 * @return 记录集当前行数
	 */
	public int getDsCurrIdx() {
		return dsCurrIdx;
	}

	/**
	 * 设置记录集当前行数
	 * @param dsCurrIdx 记录集当前行数
	 */
	public void setDsCurrIdx(int dsCurrIdx) {
		this.dsCurrIdx = dsCurrIdx;
	}

	/**
	 * 此分组增加对应的行
	 * @param row 行对象
	 * @see  BaseRow
	 */
	public void addRow(BaseRow row){
		if(dsCurrIdx == -1)
			dsCurrIdx = row.getIndex();
		rows.add(row);
	}
	public static void main(String args[]) {

	}


	/**
	 * 取得分组对象
	 * @return 分组对象
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置分组对象
	 * @param value 分组对象
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	
	/**
	 * 取得分组名
	 * @return 分组名
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 设置分组名
	 * @param groupName 分组名
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}


	/**
	 * 取得分组显示内容
	 * @return 分组显示内容
	 */
	public Object getShowvalue() {
		if(showvalue == null){
			if(showGroupName != null && showGroupName.trim().length()>0){
				showvalue = getExprCalculate(showGroupName);
			}else {
				showvalue = value;
			}
		}
		return showvalue ;
	}

	/**
	 * 设置分组显示内容
	 * @param showvalue 分组显示内容
	 */
	public void setShowvalue(Object showvalue) {
		this.showvalue = showvalue;
	}
	
	/**
	 * 设置分组显示内容表达式
	 * @param showvalueExpr 分组显示内容表达式
	 */
	public void setShowvalueExpr(Expr showvalueExpr) {
		this.showvalue = showvalueExpr;
	}

	
	
	
	/**
	 * 计算表达式值
	 * @param expStr 表达式字符串
	 * @return 表达式记算结果
	 */
	public String getExprCalculate(String expStr){
		
		 
		String result = null;
		//如果expStr 不为空,则按表达式计算结果,如果为空,则默认取得本分组加上级分组条件.
		if(expStr != null && expStr.trim().length()>0){
			try {
				ds.goTo(dsCurrIdx);
				Expr expr = new Expr(expStr,ds);
				Object obj = expr.calculate();
				result = obj != null?obj.toString():null;
			} catch (Exception e) {
			}

		} 
		//showvalue=result;
		return result;
	}
	
	/**
	 * 取得此分组下所有行
	 * @return 此分组下所有行
	 */
	   public List getRows() {
		return rows;
	}

	   /**
	    * 设置此分组下所有行
	    * @param rows 此分组下所有行
	    */
	public void setRows(List rows) {
		this.rows = rows;
	}

	/**
	 * 两个分组进行交叉计算
	 * @param other 另一个分组对象
	 * @return 两个分组交叉计算后的分组
	 */
	public final DBGroup cross(DBGroup other){
		if(other.getDs().getDsName().equals(ds.getDsName())){
	        Object rowArray[] = new Object[ ds.getRow()+1];
	        int iThisRowsSize = rows.size();
	        for(int i = 0; i < iThisRowsSize; i++)
	        {
	            BaseRow row = (BaseRow)rows.get(i);
	            rowArray[row.getIndex()] = row;
	        }

	        DBGroup newGroup = new DBGroup(ds,this.groupName+PropertyDefine.colBoxOff+other.getGroupName());
	        int iOtherRowsSize = other.getRows().size();
	        for(int i = 0; i < iOtherRowsSize; i++)
	        {
	        	BaseRow row = (BaseRow)other.getRows().get(i);
	            if(rowArray[row.getIndex()] != null)
	                newGroup.addRow(row);
	        }

	        return newGroup;
		}else{ //非同一个记录集分组交叉
			return null;
		}
	 }

	/**
	 * 取得下级分组
	 * @param groupName
	 * @param showGroupName
	 * @param drillExpSubPara
	 * @return
	 */
	public List getLowerDBGroup(String groupName,String showGroupName,String[] drillExpSubPara){
		List groupList = new ArrayList();
			try {
				Map<Object,DBGroup> groupMap = new HashMap();
			    int iThisRowsSize = rows.size();
		        for(int i = 0; i < iThisRowsSize; i++)
		        {
		            BaseRow row = (BaseRow)rows.get(i);
		            Object value = row.getColumnObject(groupName);//记录分组值
		            if(value ==null){
						value="";
					}
					DBGroup group = groupMap.get(value);
					if(group == null){
						group = new DBGroup(row.getDs(),groupName,showGroupName,drillExpSubPara);
						group.setValue(value);
						group.setDsCurrIdx(row.getIndex());
						row.addGroup(group);
						groupList.add(group);
						groupMap.put(value,group);
					}
					group.addRow(row);
		        }
			} catch (Exception e) {
				// TODO: handle exception
			}
		return groupList;
			
	}
	
	public String toString(){
		//return this.ds.getDsName()+".group("+this.getGroupName()+"):"+this.value; 
		return StringUtil.valueOf(getShowvalue()); 
	}
	
	/**
	 * 设置报表钻取属性
	 * @param newCell 当前单元格
	 * @param drillExpSubPara 关于钻取的所有子参数:[钻取方式,是否钻取条件判断表达式,钻取条件,是否钻取条件判断表达式,钻取条件...,钻取条件] 采用类oracle decode 的方式.
	 */
	public void setDrill(Cell newCell){
		String drillType = drillExpSubPara != null && drillExpSubPara.length>0?drillExpSubPara[0]:null;
		//drillType 不为空,则表示可以钻取
		if(drillType != null && drillType.trim().length()>0){
			newCell.setDrillType(drillType); //设置钻取类型
			int iDrillExpSubParaLength = drillExpSubPara.length;
			
			if(iDrillExpSubParaLength ==1){//如果没设置钻取条件,则按默认条件钻取
				newCell.setDrillAvai(true);
				newCell.setDrillTerm(getExprCalculate(null));
			} else {
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
						String avai = drillAvai.equals("true")?drillAvai:getExprCalculate(drillAvai);
						if(avai != null && avai.trim().toLowerCase().equals("true")){
							newCell.setDrillAvai(true);
							newCell.setDrillTerm(getExprCalculate(drillTrim));
							break;
						}
					}
				}
			}
			
		}
	}

	/**
	 * 根据分组字段名,与分组值,取得分组对象.
	 * @param groupName 分组字段名
	 * @param value 分组值
	 * @return 分组对象
	 */
	public DBGroup getDBGroupByValue(String groupName,Object value){
		List list = getLowerDBGroup(groupName,null,null);//根据分组字段名,取得分组对象列表
		DBGroup group = null;
		int iSize = list.size();
		for(int i=0;i<iSize;i++){//对分组对象列表循环,按分组值查找匹配的分组对象
			DBGroup groupTemp =(DBGroup)list.get(i);
			if(value.equals(groupTemp.getValue())) {
				group = groupTemp ;
				break;
			}
		}
		return group;
	}
	
	/**
	 * 增加虚拟记录集名称,用多数据源判断
	 * @param dummyDsName 虚拟记录集名称
	 */
	public void addDummyDsName(String dummyDsName){
		dummyDsNameSet.add(dummyDsName); 
	}

	/**
	 * 取得虚拟记录集名称Set
	 * @return 虚拟记录集名称Set
	 */
	public Set getDummyDsNameSet() {
		return dummyDsNameSet;
	}

	/**
	 * 设置虚拟记录集名称Set
	 * @param dummyDsNameSet 虚拟记录集名称Set
	 */
	public void setDummyDsNameSet(Set dummyDsNameSet) {
		this.dummyDsNameSet = dummyDsNameSet;
	}
	
	
	
}
