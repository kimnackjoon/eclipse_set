package com.nackjoon.nj.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.nackjoon.nj.core.model.Paging;

public class PageUtil {
	
	public static Paging getPaging(Map<String, Object> params) {
		return PageUtil.getPaging(params,"","");
	}
	
	public static Paging getPaging(Map<String, Object> params, String suffix) {
		return PageUtil.getPaging(params,"",suffix);
	}
	
	public static Paging getPaging(Map<String, Object> params, String prefix, String suffix) {
		Paging paging = new Paging();

		if (params == null) {
			return paging;
		}

		prefix = (prefix == null ? "" : prefix);
		suffix = (suffix == null ? "" : suffix);

		boolean isPaging = false;

		for (String key : params.keySet()) {
			if (key.toLowerCase().matches("(?i)" + prefix + "(page|no|num)" + suffix)) {
				paging.setPage(FormatUtil.toInt(params.get(key), 1));
				isPaging = true;
			} else if (key.toLowerCase().matches("(?i)" + prefix + "(row|list)(size|count)" + suffix)) {
				paging.setRowCount(FormatUtil.toInt(params.get(key), 10));
				isPaging = true;
			} else if (key.toLowerCase().matches("(?i)" + prefix + "(col|page)(size|count)" + suffix)) {
				paging.setColCount(FormatUtil.toInt(params.get(key), 10));
				isPaging = true;
			} else if (key.toLowerCase().matches("(?i)" + prefix + "(total)(size|count)?" + suffix)) {
				paging.setTotal(FormatUtil.toInt(params.get(key)));
				isPaging = true;
			}
		}

		if (isPaging) {
			paging.init();
		}

		return paging;
	}
	
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();

		if (request == null) {
			return params;
		}

		Map<String, String[]> map = request.getParameterMap();

		for (String key : map.keySet()) {
			if (map.get(key) == null) {
				params.put(key, request.getAttribute(key));
			} else  if (map.get(key).length > 0) {
				params.put(key, map.get(key)[0]);
			} else {
				params.put(key, null);
			}
		}

		return params;
	}
	
	public static String getUri(String url, String search) {
		return getUri(url, search, new HashMap<String, Object>());
	}
	
	public static String getUri(String url, String search, String... params) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		if (params != null) {
			for (int i = 0; i < params.length; i += 2) {
				String paramName = (params[i] == null ? "" : params[i]);
				String paramValue = (i + 1 < params.length ? params[i + 1] : "");
				paramValue = (paramValue == null ? "" : paramValue);

				if (!"".equals(paramName)) {
					paramMap.put(paramName, paramValue);
				}
			}
		}

		return getUri(url, search, paramMap);
	}
	
	public static String getUri(String url, String search, Map<String, Object> params) {
		url = (url == null ? "" : url);
		search = (search == null ? "" : search);

		if (params != null) {
			for (String paramName : params.keySet()) {
				if (paramName == null || "".equals(paramName)) {
					continue;
				}
				
				String paramValue = params.get(paramName) == null ? null : params.get(paramName).toString();
				
				if (search.matches("^.*&?" + paramName + "=.*$")) {
					search = search.replaceAll("&?" + paramName + "=[^&]*", "");
				}
				
				try {
					if (paramValue != null) {
						search += "&" + paramName + "=" + URLEncoder.encode(paramValue, "UTF-8");
					}
				} catch (Exception e) {}
			}
		}

		if (!"".equals(search) && !search.startsWith("?")) {
			search = "?" + search;
		}

		return url + search.replaceFirst("^[?]&", "?");
	}
	
	
	public static String getParamValue(String uri, String key) {
		uri = (uri == null ? "" : uri);
		uri = uri.replaceAll("&&", "&").replaceAll("^&", "").replaceAll("&$", "");

		String search = uri.replaceAll("^.+[?]", "").replaceAll("#.+$", "");

		String[] params = search.split("&");

		for (String param : params) {
			String[] data = param.split("=");

			if (data.length == 2 && data[0].equals(key)) {
				try {
					return URLDecoder.decode(data[1], "UTF-8");
				} catch (UnsupportedEncodingException e) {
					return "";
				}
			}
		}

		return null;
	}
	
	public static HashMap<String, Object> getParams(HashMap<String, Object> params) {
		int rowCount = FormatUtil.toInt(params.get("rowcount"));
		int rowCountReal = rowCount;
		int page = FormatUtil.toInt(params.get("page"));
		int total = FormatUtil.toInt(params.get("total"));
		int rowEnd = rowCount * page;
		
		if (total < rowEnd) {
			rowCountReal -= (rowEnd - total);
			rowEnd = total;
		}
		
		params.put("rowcount", rowCount);
		params.put("rowcountreal", rowCountReal);
		params.put("page", page);
		params.put("total", total);
		params.put("rowend", rowEnd);
		
		return params;
	}
}
