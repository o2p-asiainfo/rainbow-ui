package com.linkage.rainbow.ui.report.util.dto;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.linkage.rainbow.ui.report.util.dto.JPieChart.Slice;


public abstract class Element implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(Element.class);

	
	private List<Object> values = new ArrayList<Object>();


	public List<Object> getValues() {
		return values;
	}
	
	public String getAttribute(Object obj){
		StringBuffer xml=new StringBuffer();
    	Method [] m=obj.getClass().getDeclaredMethods();
    	for (int i = 0; i <m.length; i++) {
    		try {
    			if(!"getValue".equals(m[i].getName())&&!"getValues".equals(m[i].getName()) && m[i].getName().startsWith("get") && m[i].invoke(obj,null)!=null)
    				xml.append(m[i].getName().substring(3).toLowerCase()+"=\""+m[i].invoke(obj,null).toString()+"\" ");
			} catch (IllegalArgumentException e) {
				log.error(e.getStackTrace());
			} catch (IllegalAccessException e) {
				log.error(e.getStackTrace());
			} catch (InvocationTargetException e) {
				log.error(e.getStackTrace());
			}        	
    	}
    	return xml.toString();
	}

	public double getMaxValue() {
		double max = 0.0;
		for (Object obj : getValues()) {
			if (obj != null) {
				if (obj instanceof Number) {
					max = Math.max(max, ((Number) obj).doubleValue());
				} else if (obj instanceof Slice) {
					max = Math.max(max, ((Slice) obj).getValue() != null ? ((Slice) obj).getValue().doubleValue() : 0);
				}  else if (obj instanceof NullElement) {
					/* No action */
				} else {
					throw new IllegalArgumentException("Cannot process Objects of Class: " + String.valueOf(obj.getClass()));
				}
			}
		}
		return max;
	}

    public double getMinValue() {
        Double min = null;
        for (Object obj : getValues()) {
            if (obj != null) {
                if (obj instanceof Number) {
                    min = nullSafeMin(min, ((Number) obj).doubleValue());
                }else if (obj instanceof Slice) {
                    min = nullSafeMin(min, ((Slice) obj).getValue() != null ? ((Slice) obj).getValue()
                            .doubleValue() : 0);
                } else if (obj instanceof NullElement) {
                    /* No action */
                } else {
                    throw new IllegalArgumentException("Cannot process Objects of Class: " + String.valueOf(obj.getClass()));
                }
            }
        }
        if (null == min) {
            min = 0.0;
        }
        return min;
    }

    private Double nullSafeMin(Double min, double doubleValue) {
        if (null == min) {
            min = doubleValue;
        }
        min = Math.min(min, doubleValue);
        return min;
    }
	
}
