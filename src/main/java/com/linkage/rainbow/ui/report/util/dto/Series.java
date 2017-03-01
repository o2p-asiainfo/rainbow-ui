package com.linkage.rainbow.ui.report.util.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Series {

	private List<Serie> values = new ArrayList<Serie>();

	public List<Serie> getValues() {
		return values;
	}
	
	public Series addSerie() {
		return addSeries(new Serie());
	}

	public Series addSeries(Serie... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}

	public Series addSeries(List<Serie> values) {
		getValues().addAll(values);
		return this;
	}	
	
	/**
	 * x��ĵ�
	 * @author Administrator
	 *
	 */
	public static class Serie implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String xid;
		private String show;
		private String event_start;
		private String event_end;
		private String event_text_color;
		private String event_description;
		private String event_color;
		private String event_alpha;
		private String bg_color; 
		private String bg_alpha; 

		
		
		private String value;
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getXid() {
			return xid;
		}
		public void setXid(String xid) {
			this.xid = xid;
		}
		public String getShow() {
			return show;
		}
		public void setShow(String show) {
			this.show = show;
		}
		public String getEvent_start() {
			return event_start;
		}
		public void setEvent_start(String event_start) {
			this.event_start = event_start;
		}
		public String getEvent_end() {
			return event_end;
		}
		public void setEvent_end(String event_end) {
			this.event_end = event_end;
		}
		public String getEvent_text_color() {
			return event_text_color;
		}
		public void setEvent_text_color(String event_text_color) {
			this.event_text_color = event_text_color;
		}
		public String getEvent_description() {
			return event_description;
		}
		public void setEvent_description(String event_description) {
			this.event_description = event_description;
		}
		public String getEvent_color() {
			return event_color;
		}
		public void setEvent_color(String event_color) {
			this.event_color = event_color;
		}
		public String getEvent_alpha() {
			return event_alpha;
		}
		public void setEvent_alpha(String event_alpha) {
			this.event_alpha = event_alpha;
		}
		public String getBg_color() {
			return bg_color;
		}
		public void setBg_color(String bg_color) {
			this.bg_color = bg_color;
		}
		public String getBg_alpha() {
			return bg_alpha;
		}
		public void setBg_alpha(String bg_alpha) {
			this.bg_alpha = bg_alpha;
		}
		
		
	}

}
