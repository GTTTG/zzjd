package com.oracle.webserver_v01web;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.oracle.webserver_v01servlet.HttpServlet;
import com.oracle.webserver_v01servlet.HttpServletRequest;
import com.oracle.webserver_v01servlet.HttpServletResponse;
import com.oracle.webserver_v01servlet.WebServlet;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 你要下载那个
		String querystring = request.getRequestURI().substring(request.getRequestURI().indexOf("?") + 1);
		String filenme = URLDecoder.decode(querystring.split("=")[1], "utf-8");
		System.out.println(filenme);
		PrintWriter pt = new PrintWriter(response.getBao());
		// 响应行
		pt.println("HTTP/1.1 200 OK");
		// 若干行响应头
		if (filenme.endsWith(".docx")) {
			pt.println(
					"Content-Type:application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8");
		}
		if (filenme.endsWith(".pdf")) {
			pt.println("Content-Type:application/pdf;charset=utf-8");
		}
		// 发送名字
		pt.println("Content-disposition: attachment; filename=\"" + filenme + "\"; filename*=UTF-8''"
				+ URLEncoder.encode(filenme, "UTF-8"));
		// 空白行
		pt.println();
		// 正文
		pt.flush();
		// 发送内容
		InputStream is = new FileInputStream(System.getProperty("user.dir") + "/WebContent/download/" + filenme);
		byte[] bt = new byte[1024];
		int len;
		while ((len = is.read(bt)) != -1) {
			response.getBao().write(bt, 0, len);
		}
		is.close();
	}
}
