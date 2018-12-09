package com.oracle.webserver_v01servlet;

import java.io.PrintWriter;

public class HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equals("POST")) {
			doPost(request, response);

		} else if (request.getMethod().equals("GET")) {
			doGet(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 包厢和顾客
		PrintWriter pt = new PrintWriter(response.getBao());
		// 响应行
		pt.println("HTTP/1.1 500 InternalServerError");
		// 若干行响应头
		pt.println("Content-Type:text/html;charset=utf-8");

		// 空白行
		pt.println();
		// 正文
		pt.print("<b style='color:red'>出错了</b>");
		// 强制发出
		pt.flush();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 包厢和顾客
		PrintWriter pt = new PrintWriter(response.getBao());
		// 响应行
		pt.println("HTTP/1.1 500 InternalServerError");
		// 若干行响应头
		pt.println("Content-Type:text/html;charset=utf-8");

		// 空白行
		pt.println();
		// 正文
		pt.print("<b style='color:red'>出错了</b>");
		// 强制发出
		pt.flush();
	}

}
