package com.oracle.webserver_v01servlet;

import java.io.OutputStream;

public class HttpServletResponse {
	private OutputStream bao;

	public OutputStream getBao() {
		return bao;
	}

	public void setBao(OutputStream bao) {
		this.bao = bao;
	}

}