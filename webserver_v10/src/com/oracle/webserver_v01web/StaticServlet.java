package com.oracle.webserver_v01web;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;

import com.oracle.webserver_v01servlet.HttpServlet;
import com.oracle.webserver_v01servlet.HttpServletRequest;
import com.oracle.webserver_v01servlet.HttpServletResponse;
import com.oracle.webserver_v01servlet.WebServlet;

@WebServlet("/static")
public class StaticServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter pt = new PrintWriter(response.getBao());
		// 响应行
		pt.println("HTTP/1.1 200 OK");
		// 若干行响应头
		pt.println("Content-Type:text/html;charset=utf-8");

		// 空白行
		pt.println();
		// 正文
		Reader re = new FileReader(System.getProperty("user.dir") + "/WebContent" + request.getRequestURI());
		char[] cbuf = new char[1024];
		int len;
		while ((len = re.read(cbuf)) != -1) {
			pt.write(cbuf, 0, len);
			pt.flush();
		}
		re.close();
	}

}
