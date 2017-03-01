package com.linkage.rainbow.ui.report.util.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.linkage.rainbow.ui.report.util.dto.Series.Serie;


public class Guide implements Serializable{
	
		
		private String start_value;
		private String title;	
		private String color="#0D8ECF";
		private String inside;
		private String behind = "true";
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getInside() {
			return inside;
		}
		public void setInside(String inside) {
			this.inside = inside;
		}
		public String getStart_value() {
			return start_value;
		}
		public void setStart_value(String start_value) {
			this.start_value = start_value;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getBehind() {
			return behind;
		}
		public void setBehind(String behind) {
			this.behind = behind;
		}

}
