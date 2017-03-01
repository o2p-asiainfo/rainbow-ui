package com.linkage.rainbow.ui.report.engine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 属性文件加加载,支持同属性名多个值的获取.
 * getProperty() 根据局性名取得单个值.
 * getPropertyValues() 根据属性名取得多个值.
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
public class Properties {
	private static final String keyValueSeparators = "=: \t\r\n\f";

	private static final String strictKeyValueSeparators = "=:";

	private static final String specialSaveChars = "=: \t\r\n\f#!";

	private static final String whiteSpaceChars = " \t\r\n\f";
	
	private HashMap map = new HashMap();

	/**
	 * 根据路径名加载属性文件,例:myreport/report-config.properties
	 * @param resource
	 * @throws IOException
	 */
	public synchronized void load(String resource) throws IOException {
		InputStream in = Resources.getResourceAsStream(resource);
		load(in);
	}
	
	/**
	 * 根据输入流加载属性文件
	 * @param resource
	 * @throws IOException
	 */
	public synchronized void load(InputStream inStream) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(inStream,
				"GBK"));
		while (true) {
			// Get next line
			String line = in.readLine();
			if (line == null)
				return;

			if (line.length() > 0) {

				// Find start of key
				int len = line.length();
				int keyStart;
				for (keyStart = 0; keyStart < len; keyStart++)
					if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
						break;

				// Blank lines are ignored
				if (keyStart == len)
					continue;

				// Continue lines that end in slashes if they are not comments
				char firstChar = line.charAt(keyStart);
				if ((firstChar != '#') && (firstChar != '!')) {
					while (continueLine(line)) {
						String nextLine = in.readLine();
						if (nextLine == null)
							nextLine = "";
						String loppedLine = line.substring(0, len - 1);
						// Advance beyond whitespace on new line
						int startIndex;
						for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
							if (whiteSpaceChars.indexOf(nextLine
									.charAt(startIndex)) == -1)
								break;
						nextLine = nextLine.substring(startIndex, nextLine
								.length());
						line = new String(loppedLine + nextLine);
						len = line.length();
					}

					// Find separation between key and value
					int separatorIndex;
					for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
						char currentChar = line.charAt(separatorIndex);
						if (currentChar == '\\')
							separatorIndex++;
						else if (keyValueSeparators.indexOf(currentChar) != -1)
							break;
					}

					// Skip over whitespace after key if any
					int valueIndex;
					for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;

					// Skip over one non whitespace key value separators if any
					if (valueIndex < len)
						if (strictKeyValueSeparators.indexOf(line
								.charAt(valueIndex)) != -1)
							valueIndex++;

					// Skip over white space after other separators if any
					while (valueIndex < len) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;
						valueIndex++;
					}
					String key = line.substring(keyStart, separatorIndex);
					String value = (separatorIndex < len) ? line.substring(
							valueIndex, len) : "";

					// Convert then store key and value
					key = loadConvert(key);
					value = loadConvert(value);
					put(key, value);
				}
			}
		}
	}

	/*
	 * Returns true if the given line is a line that must be appended to the
	 * next line
	 */
	private boolean continueLine(String line) {
		int slashCount = 0;
		int index = line.length() - 1;
		while ((index >= 0) && (line.charAt(index--) == '\\'))
			slashCount++;
		return ((slashCount & 1) == 1);
	}
	
	  /*
     * Converts encoded &#92;uxxxx to unicode chars
     * and changes special saved chars to their original forms
     */
    private String loadConvert(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x=0; x<len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value=0;
		    for (int i=0; i<4; i++) {
		        aChar = theString.charAt(x++);
		        switch (aChar) {
		          case '0': case '1': case '2': case '3': case '4':
		          case '5': case '6': case '7': case '8': case '9':
		             value = (value << 4) + aChar - '0';
			     break;
			  case 'a': case 'b': case 'c':
                          case 'd': case 'e': case 'f':
			     value = (value << 4) + 10 + aChar - 'a';
			     break;
			  case 'A': case 'B': case 'C':
                          case 'D': case 'E': case 'F':
			     value = (value << 4) + 10 + aChar - 'A';
			     break;
			  default:
                              throw new IllegalArgumentException(
                                           "Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append((char)value);
                } else {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    
    private synchronized void put(String key, String value) {
    	Object obj = map.get(key);
    	if(obj == null){
    		map.put(key,value);
    	} else {
    		if (obj instanceof String[]) {
				String[] oldValue = (String[]) obj;
				String[] newValue = new String[oldValue.length+1];
				System.arraycopy(oldValue,0,newValue,0,oldValue.length);
				newValue[oldValue.length] = value;
			} else {
				String[] newValue = new String[2];
				newValue[0] = (String)obj;
				newValue[1] = value;
				map.put(key,newValue);
			}
    	}
    }
    
    /**
     * 根据属性key取得属性值
     * @param key
     * @return
     */
    public String getProperty(String key){
    	Object obj = map.get(key);
    	if(obj != null){
    		if (obj instanceof String[]) {
    			String[] value = (String[]) obj;
    			return value[0];
			} else {
				return (String)obj;
			}
    	}
    	return null;
    }
    
    /**
     * 根据属性key取得属性值
     * @param key
     * @return
     */
    public String[] getPropertyValues(String key){
    	Object obj = map.get(key);
    	if(obj != null){
    		if (obj instanceof String[]) {
    			String[] value = (String[]) obj;
    			return value;
			} else {
				String[] newValue = new String[1];
				newValue[0] = (String)obj;
				return newValue;
			}
    	}
    	return null;
    }

    public Set keySet() {
    	return map.keySet();
    
    
    }
    
    public void setProperty(String key,String value){
    	map.put(key,value);
    }

     public static void main(String[] args) {
 		try {
			String resource = "com/linkage/rainbow/ui/report/engine/report-config.properties";
			InputStream in = Resources.getResourceAsStream(resource);
			Properties prop = new Properties();
			prop.load(in); 
			Object obj = prop.getProperty("ibatis_init_class");
			System.out.println(obj);
			
			String values[] = prop.getPropertyValues("ibatis_init_class");
			for(int i=0;i<values.length;i++){
				
				System.out.println(values[i]);
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
