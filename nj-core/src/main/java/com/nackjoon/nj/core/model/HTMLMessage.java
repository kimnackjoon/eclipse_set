package com.nackjoon.nj.core.model;

public class HTMLMessage {

	public static enum TARGET {SELF, NEW, OPENER, OPENER_CLOSE, POPUP, CLOSE, BACK};
	
	public static enum METHOD {GET_REPLACE, GET, POST};
	
	private String message;
	
	private String href;
	
	private TARGET target;
	
	private METHOD method;
	
	public HTMLMessage(String message, String href) {
		this(message, href, null, null);
	}
	
	public HTMLMessage(String message, String href, TARGET target) {
		this(message, href, target, null);
	}
	
	public HTMLMessage(String message, String href, TARGET target,METHOD method) {
		this.message = (message == null ? "" : message);
		this.href = (href == null || "".equals(href) ? "/" : href);
		this.target = (target == null ? TARGET.SELF : target);
		this.method = (method == null ? METHOD.GET_REPLACE : method);
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public TARGET getTargt() {
		return target;
	}
	
	public void setTarget(TARGET target) {
		this.target = target;
	}
	
	public METHOD getMethod() {
		return method;
	}
	
	public void setMethod(METHOD method) {
		this.method = method;
	}
	
	@Override
	public String toString() {
		return "HTMLMessage [message=" + message + ", href=" + href
				+ ", target=" + target + ", method=" + method + "]";
	}
}

