package com.linkage.rainbow.ui.report.util.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Graph {

	private String gid;
	private String type;	
	private String title;
	private String color;
	private String balloon_color;
	private String bullet_color;
	private String balloon_text;
	private String bullet;
	private String bullet_size;
	private String line_width;
	private String fill_alpha;
	
	private List<Dot> values = new ArrayList<Dot>();

	public List<Dot> getValues() {
		return values;
	}	
	
	public Graph addDot() {
		return addDots(new Dot());
	}

	public Graph addDots(Dot... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}

	public Graph addDots(List<Dot> values) {
		getValues().addAll(values);
		return this;
	}
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBalloon_color() {
		return balloon_color;
	}
	public void setBalloon_color(String balloon_color) {
		this.balloon_color = balloon_color;
	}
	public String getBullet_color() {
		return bullet_color;
	}
	public void setBullet_color(String bullet_color) {
		this.bullet_color = bullet_color;
	}
	public String getBalloon_text() {
		return balloon_text;
	}
	public void setBalloon_text(String balloon_text) {
		this.balloon_text = balloon_text;
	}
	
	public String getBullet() {
		return bullet;
	}

	public void setBullet(String bullet) {
		this.bullet = bullet;
	}

	public String getBullet_size() {
		return bullet_size;
	}

	public void setBullet_size(String bullet_size) {
		this.bullet_size = bullet_size;
	}

	public String getLine_width() {
		return line_width;
	}

	public void setLine_width(String line_width) {
		this.line_width = line_width;
	}



	/**
	 * �?
	 * @author Administrator
	 *
	 */
	public static class Dot implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String xid;
		private String url;
		private String bullet;
		private String bullet_color;
		private String bullet_size;
		private String description;
		private String value;
		private String pattern; 
		private String pattern_color; 
		private String color;
		private String gradient_fill_colors;
		private String start;
		
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
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getBullet() {
			return bullet;
		}
		public void setBullet(String bullet) {
			this.bullet = bullet;
		}
		public String getBullet_color() {
			return bullet_color;
		}
		public void setBullet_color(String bullet_color) {
			this.bullet_color = bullet_color;
		}
		public String getBullet_size() {
			return bullet_size;
		}
		public void setBullet_size(String bullet_size) {
			this.bullet_size = bullet_size;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
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
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getGradient_fill_colors() {
			return gradient_fill_colors;
		}
		public void setGradient_fill_colors(String gradient_fill_colors) {
			this.gradient_fill_colors = gradient_fill_colors;
		}
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		
	}



	public String getFill_alpha() {
		return fill_alpha;
	}

	public void setFill_alpha(String fill_alpha) {
		this.fill_alpha = fill_alpha;
	}
	
}
