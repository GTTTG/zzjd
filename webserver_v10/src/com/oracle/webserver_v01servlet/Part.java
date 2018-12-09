package com.oracle.webserver_v01servlet;

import java.util.HashMap;
import java.util.Map;

public class Part {
	private Map<String, String> headers=new HashMap<>();
	private boolean field;

	public boolean isField() {
		return field;
	}

	public void setField(boolean field) {
		this.field = field;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

}
