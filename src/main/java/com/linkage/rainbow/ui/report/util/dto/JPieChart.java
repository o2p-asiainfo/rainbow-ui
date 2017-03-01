
package com.linkage.rainbow.ui.report.util.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 饼图
 * @author Administrator
 *
 */
public class JPieChart extends Element{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JPieChart addSlice(String title, Number value) {
		return addSlices(new Slice(title, value));
	}

	public JPieChart addSlices(Slice... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}

	public JPieChart addSlices(List<Slice> values) {
		getValues().addAll(values);
		return this;
	}
	
	public void setPull_outForMax(String pullout){
		for (Object obj : getValues()) {
			if (obj != null) {
				if (obj instanceof Slice) {
					if(((Slice)obj).getValue().doubleValue()==this.getMaxValue()){
						((Slice)obj).setPull_out(pullout);
					}
				}
			}
		}
	}
	
	public String getXML(){
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");      
        xml.append("<pie>");  
        for (Object obj : getValues()) {
        	xml.append("<slice ");
        	xml.append(getAttribute(obj));
        	xml.append(">"+((Slice)obj).getValue());
        	xml.append("</slice>");
        }
        xml.append("</pie>"); 		
		return xml.toString();
	}

	public static class Slice implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String title;	
		private Number value;	
		private String pull_out;	//if this is set to "true", the slice will be pulled out when the chart is first displayed.
		private String color;	
		private String url;
		private String description;
		private String alpha;
		private String label_radius;	//you can set data label's distance from a pie for each slice individualy.
		private String pattern; //if this is set, the slices will be covered with a pattern. the pattern can be provided by a swf, jpg, gif or png file, and the value must be the path to that file. see the included patterns example for more.
		private String pattern_color; //the color of the pattern. the value is a hex color code.
		public Slice(){}
		
		public Slice(String title,Number value){
			this.title=title;
			this.value=value;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

		public Number getValue() {
			return value;
		}

		public void setValue(Number value) {
			this.value = value;
		}

		public String getPull_out() {
			return pull_out;
		}
		public void setPull_out(String pull_out) {
			this.pull_out = pull_out;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAlpha() {
			return alpha;
		}
		public void setAlpha(String alpha) {
			this.alpha = alpha;
		}
		public String getLabel_radius() {
			return label_radius;
		}
		public void setLabel_radius(String label_radius) {
			this.label_radius = label_radius;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public String getPattern_color() {
			return pattern_color;
		}

		public void setPattern_color(String pattern_color) {
			this.pattern_color = pattern_color;
		}
		
	}

}
