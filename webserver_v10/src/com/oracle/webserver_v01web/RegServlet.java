package com.oracle.webserver_v01web;

import java.io.PrintWriter;

import com.oracle.webserver_v01servlet.HttpServlet;
import com.oracle.webserver_v01servlet.HttpServletRequest;
import com.oracle.webserver_v01servlet.HttpServletResponse;
import com.oracle.webserver_v01servlet.WebServlet;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pt = new PrintWriter(response.getBao());
		// 响应行
		pt.println("HTTP/1.1 200 OK");
		// 若干行响应头
		pt.println("Content-Type:text/html;charset=utf-8");

		// 空白行
		pt.println();
		// 正文
		pt.print("注册成功");
		pt.flush();
	}

}
