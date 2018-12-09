package com.oracle.webserver_v01web;

import java.io.PrintWriter;

import com.oracle.webserver_v01servlet.HttpServlet;
import com.oracle.webserver_v01servlet.HttpServletRequest;
import com.oracle.webserver_v01servlet.HttpServletResponse;
import com.oracle.webserver_v01servlet.WebServlet;

@WebServlet("/Welcome")
public class WelcomeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// 包厢和顾客
		PrintWriter pt = new PrintWriter(response.getBao());
		// 响应行
		pt.println("HTTP/1.1 200 OK");
		// 若干行响应头
		pt.println("Content-Type:text/html;charset=utf-8");

		// 空白行
		pt.println();
		// 正文
		pt.print("<title>我的第一个web项目</title>");
		pt.print("<b style='color:red'>你好世界</b>");
		// 强制发出
		pt.flush();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doGet(request, response);
	}
}
