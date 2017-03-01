/*
 * @(#)Event.java        2009-6-10
 *
 *
 */

package com.linkage.rainbow.ui.report.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.linkage.rainbow.ui.report.engine.rowset.BaseRow;
import com.linkage.rainbow.ui.report.engine.rowset.DBGroup;
import com.linkage.rainbow.ui.report.engine.util.DirectionUtil;

/**
 * 事件集合类<br>
 * 当单元格扩展时,对其他单元格有影响的作事件通知.
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-10
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class Events implements Serializable, Cloneable{
	/**列表事件，按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象 */
	HashMap selectEvent = new HashMap();
	/**分组事件，按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象*/
	HashMap groupEvent = new HashMap();
	/**交叉分组事件，按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象*/
	HashMap crossEvent = new HashMap();
	
	
//	public void addSelectEvent(String dsName,BaseRow row,boolean isLengthways){
//		Set list = (Set)selectEvent.get(dsName);
//		if(list == null){
//			list = new HashSet();
//			selectEvent.put(dsName,list);
//		}
//		list.add(row);
//	}
	/**
	 * 根据事件源单元格坐标取得事件
	 * @see Event Coordinate
	 */
	public Event getEventBySrcCoordinate(Coordinate srcCoordinate ){
		Event event =null;
		if(groupEvent != null){
			
			for(Iterator i = groupEvent.keySet().iterator();i.hasNext();){
				Set tmpSet = (Set)groupEvent.get(i.next());
				for(Iterator i2 = tmpSet.iterator();i2.hasNext();){
					Event tmpEvent = (Event)i2.next();
					if(srcCoordinate.equals(tmpEvent.getSrcCoordinate())){
						return tmpEvent;	
					}
				}
			}
		}
		if(selectEvent != null){
			for(Iterator i = selectEvent.keySet().iterator();i.hasNext();){
				Set tmpSet = (Set)selectEvent.get(i.next());
				for(Iterator i2 = tmpSet.iterator();i2.hasNext();){
					Event tmpEvent = (Event)i2.next();
					if(srcCoordinate.equals(tmpEvent.getSrcCoordinate())){
						return tmpEvent;	
					}
				}
			}
		}
		return event;
	}
	/**
	 * 添加列表扩展事件
	 * @param dsName 数据集名称
	 * @prarm event 事件对象
	 */
	public void addSelectEvent(String dsName,Event event){
		Set list = (Set)selectEvent.get(dsName);
		if(list == null){
			list = new HashSet();
			selectEvent.put(dsName,list);
		}
		list.add(event);
	}
	
//	public void addGroupEvent(String dsName,DBGroup group,boolean isLengthways){
//		Set list = (Set)groupEvent.get(dsName);
//		if(list == null){
//			list = new HashSet();
//			groupEvent.put(dsName,list);
//		}
//		list.add(group);
//		
//		String groupName = PropertyDefine.colBoxOff+group.getGroupName()+PropertyDefine.colBoxOff;
//		groupEventExtDirection.put(group.getGroupName(),isLengthways);
//		List removeList = new ArrayList();
//		for(Iterator i = list.iterator();i.hasNext();){
//			DBGroup tmpGroup = (DBGroup)i.next();
//			if(tmpGroup != group && groupName.indexOf(tmpGroup.getGroupName())>-1){
//				removeList.add(tmpGroup);
//				groupEventExtDirection.remove(group.getGroupName());
//			}
//		}
//		list.removeAll(removeList);
//		
//		Iterator iterator = list.iterator();
//		for(;iterator.hasNext();){
//			DBGroup groupTmp = (DBGroup)iterator.next();
//			Object obj =groupEventExtDirection.get(groupTmp.getGroupName());
//			Boolean isLengthwaysTmp = (Boolean)groupEventExtDirection.get(groupTmp.getGroupName());
//			if(isLengthwaysTmp != null && isLengthwaysTmp!=isLengthways){
//				crossEvent.put(dsName,true);
//				break;
//			}
//		}
//	}
	
	/**
	 * 增加分组事件
	 * @param dsName 数据集名称
	 * @prarm event 事件对象
	 */
	public void addGroupEvent(String dsName,Event event){
		
		DBGroup group = (DBGroup)event.getEventValue();
		boolean isLengthways = event.isLengthways();
		Set list = (Set)groupEvent.get(dsName);
		if(list == null){
			list = new HashSet();
			groupEvent.put(dsName,list);
		}
		list.add(event);
		
		String groupName = PropertyDefine.colBoxOff+group.getGroupName()+PropertyDefine.colBoxOff;
		
		List removeList = new ArrayList();
		for(Iterator i = list.iterator();i.hasNext();){
			Event tmpEvent = (Event)i.next();
			DBGroup tmpGroup = (DBGroup)event.getEventValue();
			if(tmpGroup != group && groupName.indexOf(tmpGroup.getGroupName())>-1){
				removeList.add(tmpEvent);
			}
		}
		list.removeAll(removeList);
		
		Iterator iterator = list.iterator();
		for(;iterator.hasNext();){
			Event eventTmp = (Event)iterator.next();
			DBGroup groupTmp = (DBGroup)eventTmp.getEventValue();
			Boolean isLengthwaysTmp =eventTmp.isLengthways();
			if(isLengthwaysTmp!=isLengthways){
				crossEvent.put(dsName,true);
				break;
			}
		}
	}
	
//	public void addEvent(String dsName,Object eventObj,boolean isLengthways){
//		if(eventObj instanceof BaseRow){
//			addSelectEvent(dsName,(BaseRow)eventObj,isLengthways);
//		}else if(eventObj instanceof DBGroup){
//			addGroupEvent(dsName,(DBGroup)eventObj,isLengthways);
//		}
//			
//	}
	

//	public void addEvent(Object eventObj,boolean isLengthways){
//		if(eventObj instanceof BaseRow){
//			BaseRow row = (BaseRow)eventObj;
//			addSelectEvent(row.getDs().getDsName(),row,isLengthways);
//		}else if(eventObj instanceof DBGroup){
//			DBGroup group = (DBGroup)eventObj;
//			addGroupEvent(group.getDs().getDsName(),group,isLengthways);
//		}
//			
//	}
	
	/**
	 * 增加事件
	 * @prarm event 事件对象
	 */
	public void addEvent(Event event){
		Object eventObj = event.getEventValue();
		if(eventObj instanceof BaseRow){//记录集单行事件
			BaseRow row = (BaseRow)eventObj;
			addSelectEvent(row.getDs().getDsName(),event);
		}else if(eventObj instanceof DBGroup){ //分组事件
			DBGroup group = (DBGroup)eventObj;
			addGroupEvent(group.getDs().getDsName(),event);
			Set set = group.getDummyDsNameSet();//对于多数据集关联时,会对分组事件设置虚拟记录集名称.
			if(set !=null){
				for(Iterator i=set.iterator();i.hasNext();){
					String dummyDsName = (String)i.next();
					addGroupEvent(dummyDsName,event);
				}
			}
		}
			
	}
	
	/**
	 * 返回一个克隆对象。
	 */
	public Object clone() {
		Events result = null;
		try { 
		    result = (Events)super.clone();
		    result.selectEvent =(HashMap)selectEvent.clone();
		    for(Iterator i = selectEvent.keySet().iterator();i.hasNext();){
		    	Object key = i.next();
		    	HashSet value = (HashSet)selectEvent.get(key);
				selectEvent.put(key,value.clone());
			}
		    result.groupEvent =(HashMap)groupEvent.clone();
		    for(Iterator i = groupEvent.keySet().iterator();i.hasNext();){
		    	Object key = i.next();
		    	HashSet value = (HashSet)groupEvent.get(key);
		    	groupEvent.put(key,value.clone());
			}
		} catch (Exception e) { 
		    // assert false;
		}
        return result;
	}

	/**
	 * 取得所有分组事件,Map内按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象
	 * @return 所有分组事件,Map内按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象
	 */
	public HashMap getGroupEvent() {
		return groupEvent;
	}

	/**
	 * 取得所有列表事件,Map内按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象
	 * @return 所有列表事件,Map内按数据集名称 ==> 事件对象集合Set,集合Set内为Event对象
	 */ 
	public HashMap getSelectEvent() {
		return selectEvent;
	}
	
	/**
	 * 根据记录集名称，取得对应的分组事件,Iterator内为Event对象
	 * @param dsName 记录集名称
	 * @return 取得对应的分组事件,Iterator内为Event对象
	 */
	public Iterator getGroupEvent(String dsName) {
		Set set = (Set)groupEvent.get(dsName);
		if(set != null)
			return set.iterator();
		return null;
	}

	/**
	 * 根据记录集名称，取得对应的列表事件,Iterator内为Event对象
	 * @param dsName 记录集名称
	 * @return 取得对应的列表事件,Iterator内为Event对象
	 */
	public Iterator getSelectEvent(String dsName) {
		Set set = (Set)selectEvent.get(dsName);
		if(set != null)
			return set.iterator();
		return null;
	}
	
	/**
	 * 判断某记录集数据是否需要交叉显示
	 * @param dsName 记录集名称
	 * @return 某记录集数据是否需要交叉显示
	 */
	public boolean isCross(String dsName){
		if(dsName == null)
			return false;
		return crossEvent.get(dsName)!=null?(Boolean)crossEvent.get(dsName):false;
	}
	
	/**
	 * 根据是否为纵向查找分组事件
	 * @param isLengthways 是否为纵向
	 * @param dsName 数据集名称
	 * @return
	 */
	public List getGroupEventByDirection(boolean isLengthways,String dsName){
		List result = new ArrayList();
		if(groupEvent != null){
			//取得各记录集分组事件
//			for(Iterator i = groupEvent.keySet().iterator();i.hasNext();){
//				String dsNameTmp = (String)i.next();
				Set tmpSet = (Set)groupEvent.get(dsName);
				if(tmpSet != null){
					for(Iterator i2 = tmpSet.iterator();i2.hasNext();){//对某一记录集分组事件循环
						Event tmpEvent = (Event)i2.next();
						
						if(tmpEvent.isLengthways() == isLengthways ){//相同方向的,且数据集名称相同
							//加入到result的事件顺序按坐标位置存放
							if(result.size()==0)
								result.add(tmpEvent);
							else{
								for(int j=0;j<result.size();j++){
									Event preEvent = (Event)result.get(j);
									int iCompare = tmpEvent.getSrcCoordinate().compareTo(preEvent.getSrcCoordinate());
									if(iCompare<0){
										result.add(j,tmpEvent);
										break;
									}else if(iCompare==0){
										if(j==result.size()-1)
											result.add(tmpEvent);
										else
											result.add(j+1,tmpEvent);
										break;
									}else{
										if(j==result.size()-1)
											result.add(tmpEvent);
									}
								}
							}
						}
					}
				}
//			}
		}
		return result;
	}
	/**
	 * 根据方向查找分组事件
	 * @param direction 行方向还是列方向，x:行方向(即横轴方向),y:列方向(即列轴方向)
	 * @return 分组事件
	 */
	public List getGroupEventByDirection(String direction,String dsName){
		String temp = DirectionUtil.getDirection(direction);
		return getGroupEventByDirection(temp.equals("y"),dsName);
	}
	
}
