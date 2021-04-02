package com.nackjoon.nj.core.controller;

import java.util.HashMap;


import com.nackjoon.nj.core.model.PageAuth;
import com.nackjoon.nj.core.model.PageProtocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import com.nackjoon.nj.core.model.SiteType;
import com.nackjoon.nj.core.util.FormatUtil;
import com.nackjoon.nj.core.util.RequestUtil;

public class NjBaseController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public HttpServletRequest request;
	
	protected SiteType siteType = SiteType.NORMAL;
	
	protected HashMap<String, PageAuth> pageAuth = new HashMap<String, PageAuth>();
	
	protected PageProtocol pageProtocol = PageProtocol.HTTP;
	
	{
		pageAuth.put("USER", PageAuth.OPEN);
		pageAuth.put("ADMIN", PageAuth.OPEN);
	}
	
	public void loggerInfo(String message) {
		log.info(message);
	}
	
	public void loggerDebug(String message) {
		log.debug(message);
	}
	
	public void loggerWarn(String message) {
		log.warn(message);
	}
	
	public void loggerError(String message) {
		log.error(message);
	}
	
	protected final void addLoggerInfo(Model model, String message) {
		((StringBuffer)model.asMap().get("report")).append(message + "\n");
	}
	
	public SiteType getSiteType() {
		return siteType;
	}
	
	public HashMap<String, PageAuth> getPageAuth(){
		return pageAuth;
	}
	
	public PageProtocol getPageProtocol() {
		return pageProtocol;
	}
	
	public String getUserIp(HttpServletRequest request) {
		return RequestUtil.getUserIp(request);
	}
	
	public String getSettingValue(HashMap<String,Object> settingMap, String settingType, String paramName) {
		if(settingMap == null) {
			return "";
		}
		
		@SuppressWarnings("unchecked")
		HashMap<String,Object> map = (HashMap<String,Object>)settingMap.get("SETTING");
		
		if(map == null) {
			return "";
		}
		
		return FormatUtil.toString(map.get(paramName));
	}
}
