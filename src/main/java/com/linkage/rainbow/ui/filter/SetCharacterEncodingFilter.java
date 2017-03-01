package com.linkage.rainbow.ui.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>服务容器字符集设置</p>
 *
 * @version 	0.0.1
 */
public class SetCharacterEncodingFilter implements Filter{
	protected String encoding = null;
	protected List pathList =new ArrayList();
	protected Map encodingMap =new HashMap();
	protected FilterConfig filterConfig = null;
	protected boolean ignore = true;
	
	/**
	 * 初始化容器的时候，设置容器字符集编码
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        if(this.encoding != null){
        	String encodings[] = encoding.split(";");
        	if(encodings!= null){
        		for(int i=0;i<encodings.length;i++){
        			String encodingOne[] = encodings[i].split(":");
        			if(encodingOne != null&&encodingOne.length>0){
        				if(encodingOne.length==1){
        					addPath("/");
        					encodingMap.put("/",encodingOne[0]);
        				}else{
        					addPath(encodingOne[0]);
        					encodingMap.put(encodingOne[0],encodingOne[1]);     					
						}
        			}
        		}
        	}
        }
        String value = filterConfig.getInitParameter("ignore");
        if (value == null)
            this.ignore = true;
        else if (value.equalsIgnoreCase("true"))
            this.ignore = true;
        else if (value.equalsIgnoreCase("yes"))
            this.ignore = true;
        else
            this.ignore = false;
    }

	private void addPath(String path){
		boolean isAdd =false;
		int iSize = pathList.size();
		for(int i=0;i<iSize;i++){
			String str = (String)pathList.get(i);
			if(path.length()>=str.length()){
				pathList.add(i,path);
				isAdd = true;
			}
		}
		if(!isAdd)
			pathList.add(path);
	}
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);
            if (encoding != null){
        		request.setCharacterEncoding(encoding);
        		response.setCharacterEncoding(encoding);
            }
        }
        chain.doFilter(request, response);
    }
    
    protected String selectEncoding(ServletRequest request) {
        //return (this.encoding);
    	String url = ((HttpServletRequest) request).getServletPath();
    	int iSize = pathList.size();
		for(int i=0;i<iSize;i++){
			String str = (String)pathList.get(i);
			if(url.startsWith(str)){
				return (String)encodingMap.get(str);
			}
		}
		return null;
    }
    
    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }
}
