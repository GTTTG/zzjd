package com.oracle.webserver_v01servlet;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
	private String method;
	private String requestURI;
	private String version;
	private Map<String, Part>parts=new HashMap<>();
	private Map<String, String> requestHeader;
	private Map<String, String> paraMap;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Map<String, Part> getParts() {
		return parts;
	}
	public void setParts(Map<String, Part> parts) {
		this.parts = parts;
	}
	public Map<String, String> getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(Map<String, String> requestHeader) {
		this.requestHeader = requestHeader;
	}
	public Map<String, String> getParaMap() {
		return paraMap;
	}
	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	

}
