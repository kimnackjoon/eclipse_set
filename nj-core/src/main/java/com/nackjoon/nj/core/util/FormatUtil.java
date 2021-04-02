package com.nackjoon.nj.core.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FormatUtil {
	
	public static double toDouble(Object value) {
		return toDouble(value, 0d);
	}
	
	public static double toDouble(Object value, double defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		try {
			return Double.parseDouble(value.toString());
		} catch (final Exception e) {
			return defaultValue;
		}
	}
	
	
	public static Number toNumber(Object value) {
		return toNumber(value, 0);
	}
	
	public static Number toNumber(Object value, Number defaultValue) {
		double d = toDouble(value, toDouble(defaultValue));
		long l = (long)d;
		
		if(d == l) {
			return l;
		} else {
			return d;
		}
	}
	
	public static int toInt(Object value) {
		return toInt(value, 0);
	}
	
	public static int toInt(Object value, int defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		try {
			String v = value.toString();
			v = v.replaceAll("[.].*", "");
			return Integer.parseInt(v);
		} catch (final Exception e) {
			return defaultValue;
		}	
	}
	
	public static long toLong(Object value) {
		return toLong(value,0L);
	}
	
	public static long toLong(Object value, long defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		try {
			return Long.parseLong(value.toString());
		} catch (final Exception e) {
			return defaultValue;
		}
	}
	
	public static float toFloat(Object value) {
		return toFloat(value, 0f);
	}
	
	public static float toFloat(Object value, float defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		try {
			return Float.parseFloat(value.toString());
		} catch (final Exception e) {
			return defaultValue;
		}
	}
	
	public static short toShort(Object value) {
		return toShort(value,(short)0);
	}
	
	public static short toShort(Object value, short defaultValue) {
		if(value==null) {
			return defaultValue;
		}
		
		try {
			return Short.parseShort(value.toString());
		} catch (final Exception e){
			return defaultValue;
		}
	}
	
	public static double round(Object value, int pos) {
		return round(value, 0d, pos);
	}
	
	public static double round(Object value, double defaultValue, int pos) {
		double d = toDouble(value, defaultValue);
		return Math.round(d * Math.pow(10, pos))/Math.pow(10,pos);
	}
	
	public static String toString(Object value) {
		return toString(value, "");
		
	}
	
	public static String toString(Object value, String defaultValue) {
		if(value == null) {
			return defaultValue;
		} else {
			return value.toString();
		}
		
	}
	
	public static String toString(Object value, String defaultValue, int strSize) {
		String returnValue = "";
		if(value == null) {
			returnValue = defaultValue;
		} else {
			returnValue = value.toString();
		}
		
		if(returnValue.length() > strSize) {
			returnValue = returnValue.substring(0, strSize);
		}
		
		return returnValue;
	}
	
	public static String toStringValue(Object value, String defaultValue) {
		String val = toString(value);
		return "".equals(val) ? defaultValue : val;
	}
	
	public static boolean toBoolean(Object value) {
		return toBoolean(value, false);
	}
	
	public static boolean toBoolean(Object value, Boolean defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		try {
			return Boolean.parseBoolean(value.toString());
		} catch (final Exception e) {
			if(value.toString().matches("^(Y|YES|1)$gi")) {
				return true;
			}else if(value.toString().matches("^(N|NO|0)$gi")) {
				return false;
			}
			
			return defaultValue;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static Date toDate(Object value,Date defaultValue) {
		if(value == null) {
			return defaultValue;
		}
	
	
		try {
			return new Date(Date.parse(value.toString()));
		} catch (final Exception e) {
			return defaultValue;
		}
	}
	
	//XSS에 취약한 문자를 대체문자로 전환
	public static String encodeXSS(String value) {
		return FormatUtil.toString(value)
				.replaceAll("\'", "&#39;")
				.replaceAll("\"", "&#34;")
//				.replaceAll("\'", "\\\\\'")
//				.replaceAll("\"", "\\\\\"")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\\r", "")
				.replaceAll("\\n", "<br/>");
	}
	
	public static String encodeXSSHTML(String value) {
		return FormatUtil.toString(value)
				.replaceAll("<script", "&lt;script")
				.replaceAll("</script", "&lt;/script")
				.replaceAll("<iframe", "&lt;iframe")
				.replaceAll("</iframe", "&lt;/iframe")
				.replaceAll("<frame", "&lt;frame")
				.replaceAll("</frame", "&lt;/frame")
				.replaceAll("on\\w+\\s?=\\s?([\'][^\']+[\']|[\"][^\"]+[\"]|\\s?[^\'\"\\s>]+)", "");
	}
	
	//String, Map<String, String>, List<String>, List<Map<String, String>> 형태의 데이터에 대해서 encodeXSS 실행
	@SuppressWarnings("unchecked")
	public static Object encodeObjectForXSS(Object params, String parentKeyName) {
		if(params == null) {
			return params;
		}
		
		if (params instanceof String) {
			if (parentKeyName.endsWith("__HTML")) {
				return encodeXSSHTML(params.toString());
			} else if (parentKeyName.endsWith("__NOTXSS")) {
				return params;
			} else {
				return encodeXSS(params.toString());
			}
		} else if (params instanceof Map) {
			Map<String, Object> paramsMap = (Map<String, Object>)params;
			
			for (String key : paramsMap.keySet()) {
				paramsMap.put(key, encodeObjectForXSS(paramsMap.get(key), key));
			}
		} else if (params instanceof List) {
			List<Object> paramsList = (List<Object>)params;
			
			for (int i = 0; i < paramsList.size(); ++i) {
				paramsList.set(i, encodeObjectForXSS(paramsList.get(i), parentKeyName));
			}
		}
		
		return params;
	}
}
