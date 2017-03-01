package com.linkage.rainbow.ui.report.util.dto;

import java.util.Arrays;
import java.util.List;

import com.linkage.rainbow.ui.report.util.dto.Graph.Dot;
import com.linkage.rainbow.ui.report.util.dto.Series.Serie;

/**
 * 曲线图
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class JLineChart extends Element{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public JLineChart addSeries(Series... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}
	
	public JLineChart addGuide(Guide... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}
	
	public JLineChart addGraph() {
		return addGraphs(new Graph());
	}

	public JLineChart addGraphs(Graph... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}

	public JLineChart addGraphs(List<Graph> values) {
		getValues().addAll(values);
		return this;
	}
	
	public String getXML(){
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");      
        xml.append("<chart>");  
        boolean isPrintEndGraph = false;
        for (Object obj : getValues()) {
        	if (obj != null) {
        		if(obj instanceof Series){
        			xml.append("<series>");
        			for (Serie ser : ((Series)obj).getValues()){
        				xml.append("<value "+getAttribute(ser)+">"+ser.getValue()+"</value>");
        			}
        			xml.append("</series><graphs>");
        		}else if(obj instanceof Graph){
        			xml.append("<graph  ");
        			xml.append(getAttribute(obj)+">");
        			List list=((Graph)obj).getValues();
        			for (int i = 0; i < list.size(); i++) {
        				xml.append("<value "+getAttribute(list.get(i))+">"+((Dot)list.get(i)).getValue()+"</value>");
					}
        			xml.append("</graph>");
        		}else if(obj instanceof Guide){
        			if(!isPrintEndGraph){
	        			xml.append("</graphs>"); 
	        			isPrintEndGraph = true;
	        			xml.append("<guides>");
        			}
        			Guide guide =(Guide)obj;
        			xml.append("<guide>");
        			if(guide.getStart_value() != null)
        				xml.append("<start_value>").append(guide.getStart_value()).append("</start_value>");    
        			if(guide.getTitle() != null)
        				xml.append("<title>").append(guide.getTitle()).append("</title>"); 
        			if(guide.getColor() != null)
        				xml.append("<color>").append(guide.getColor()).append("</color>"); 
        			if(guide.getInside() != null)
        				xml.append("<inside>").append(guide.getInside()).append("</inside>");
        			if(guide.getBehind() != null)
        				xml.append("<behind>").append(guide.getBehind()).append("</behind>"); 
        			xml.append("</guide>");
        		}
        	}
        }
        if(!isPrintEndGraph)
        	xml.append("</graphs>"); 	
        else
        	xml.append("</guides>"); 	
        xml.append("</chart>");
		return xml.toString();		
	}

	
}
