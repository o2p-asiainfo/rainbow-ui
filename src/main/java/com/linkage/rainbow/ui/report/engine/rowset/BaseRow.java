package com.linkage.rainbow.ui.report.engine.rowset;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linkage.rainbow.util.StringUtil;
import com.linkage.rainbow.ui.report.engine.model.ReportException;
import com.linkage.rainbow.ui.report.engine.util.ClassInfo;

/**
 * 
 * 记录集中的一个行对象
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
public class BaseRow implements Serializable, Cloneable {

	private static final Object[] NO_ARGUMENTS = new Object[0];
	private Object origVals;// 用于保存一个记录集的字段值,
	private int index;
	
	private Map groupMap = new HashMap();
	
	private CachedRowSet ds = null;

	
	// 当前值.
	private Object value;
	
	// 当前显示值.
	private Object showvalue;
	
	/**
	 * 取得此记录在记录集中的位置
	 * @return 此记录在记录集中的位置
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置此记录在记录集中的位置
	 * @param index 此记录在记录集中的位置
	 */ 
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 新建一行，根据列数构造
	 * @param colCount
	 */
	public BaseRow(int colCount){
		origVals = new Object[colCount];
		
	}
	
	/**
	 * 新建一行,根据行的字段值构造
	 * @param row
	 */
	public BaseRow(Object row){
		origVals = row;
		
	}
	
	/**
	 * 设置行的字段值
	 * @param row 行的字段值
	 */
	public void setOrigRow(Object row){
		origVals = row;
	}

	/**
	 * 给指定数组下标赋值
	 * 
	 * @param i
	 *            数组下标
	 * @param objColumn
	 */
	public void setColumnObject(int i, Object objColumn) {

			if(origVals instanceof Object[]){
				((Object[])origVals)[i - 1] = objColumn;
			}else if(origVals instanceof List){
				((List)origVals).set(i - 1,objColumn);
			}else if(origVals instanceof Map){
				((Map)origVals).put("#"+i,objColumn);
			}
	}


	/**
	 * 返加一条记录指定字段值
	 * 
	 * @param i
	 * @return Object
	 */
//	public Object getColumnObject(int i) {
//		if(origVals instanceof Object[]){
//			return((Object[])origVals)[i - 1] ;
//		}else if(origVals instanceof List){
//			return ((List)origVals).get(i - 1);
//		}else if(origVals instanceof Map){
//			return ((Map)origVals).get("#"+i);
//		}else {
//			return null;
//		}
//	}
	
	/**
	 * 取得某列的内容
	 * @param key 列名
	 */
	public Object getColumnObject(Object key) {
		if(key instanceof Integer){
			if(origVals instanceof Object[]){
				return((Object[])origVals)[(Integer)key - 1] ;
			}else if(origVals instanceof List){
				return ((List)origVals).get((Integer)key - 1);
			}else if(origVals instanceof Map){
				return ((Map)origVals).get("#"+key);
			}
		}else {
			return getProperty(origVals,key.toString());
		}
		return null;
	}

	/**
	 * 返回一个记录
	 * 
	 * @return Object[]一条记录的每个字段值.
	 */
	public Object getOrigRow() {
		return origVals;
	}
	
	  protected Object getIndexedProperty(Object object, String indexedName) {

		    Object value = null;

		      String name = indexedName.substring(0, indexedName.indexOf('['));
		      int i = Integer.parseInt(indexedName.substring(indexedName.indexOf('[') + 1, indexedName.indexOf(']')));
		      Object list = null;
		      if("".equals(name)) {
		        list = object;        
		      } else {
		        list = getProperty(object, name);
		      }

		      if (list instanceof List) {
		        value = ((List) list).get(i);
		      } else if (list instanceof Object[]) {
		        value = ((Object[]) list)[i];
		      } else if (list instanceof char[]) {
		        value = new Character(((char[]) list)[i]);
		      } else if (list instanceof boolean[]) {
		        value = new Boolean(((boolean[]) list)[i]);
		      } else if (list instanceof byte[]) {
		        value = new Byte(((byte[]) list)[i]);
		      } else if (list instanceof double[]) {
		        value = new Double(((double[]) list)[i]);
		      } else if (list instanceof float[]) {
		        value = new Float(((float[]) list)[i]);
		      } else if (list instanceof int[]) {
		        value = new Integer(((int[]) list)[i]);
		      } else if (list instanceof long[]) {
		        value = new Long(((long[]) list)[i]);
		      } else if (list instanceof short[]) {
		        value = new Short(((short[]) list)[i]);
		      } else {
		        throw new ReportException("The '" + name + "' property of the " + object.getClass().getName() + " class is not a List or Array.");
		      }



		    return value;
		  }

	
	  protected Object getProperty(Object object, String name) {
		  	if(object!=null){
		  		ClassInfo classCache = ClassInfo.getInstance(object.getClass());
			    Object value = null;
			    try {
			     
			      if (name.indexOf('[') > -1) {
			        value = getIndexedProperty(object, name);
			      } else {
			        if (object instanceof Map) {
			          value = ((Map) object).get(name);
			        } else {
			          Method method = classCache.getGetter(name);
			          if (method == null) {
			            throw new NoSuchMethodException("No GET method for property " + name + " on instance of " + object.getClass().getName());
			          }
			          try {
			            value = method.invoke(object, NO_ARGUMENTS);
			          } catch (Throwable t) {
			            throw ClassInfo.unwrapThrowable(t);
			          }
			        }
			      }
			      return value;
			    } catch (ReportException e) {
			      throw e;
			    } catch (Throwable t) {
			      if (object == null) {
			        throw new ReportException("Could not get property '" + name + "' from null reference.  Cause: " + t.toString(),t);
			      } else {
			        throw new ReportException("Could not get property '" + name + "' from " + object.getClass().getName() + ".  Cause: " + t.toString(), t);
			      }
			    }
		  	}
		    return null;
		  }
	  
	  
	  /**
	   * 对应的记录集对象
	   * @return 记录集对象
	   */
	public CachedRowSet getDs() {
		return ds;
	}

	/**
	 * 设置对应记录集对象
	 * @param ds 对应记录集对象
	 */
	public void setDs(CachedRowSet ds) {
		this.ds = ds;
	}
	
	/**
	 * 增加分组对象
	 * @param group
	 * @see DBGroup
	 */
	public void addGroup(DBGroup group){
		groupMap.put(group.getGroupName(),group);
	}

	/**
	 * 取得显示的内容
	 * @return 显示的内容
	 */
	public Object getShowvalue() {
		if(showvalue==null)
			return value;
		return showvalue;
	}

	/**
	 * 设置显示的内容
	 * @param showvalue 显示的内容
	 */
	public void setShowvalue(Object showvalue) {
		this.showvalue = showvalue;
	}

	/**
	 * 取得内容
	 * @return 内容
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置内容
	 * @param value 内容
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public String toString(){
		//return this.ds.getDsName()+".group("+this.getGroupName()+"):"+this.value; 
		return StringUtil.valueOf(getShowvalue()); 
	}
}