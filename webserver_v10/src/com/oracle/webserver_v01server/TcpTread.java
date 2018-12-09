package com.oracle.webserver_v01server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.webserver_v01servlet.HttpServlet;
import com.oracle.webserver_v01servlet.HttpServletRequest;
import com.oracle.webserver_v01servlet.HttpServletResponse;
import com.oracle.webserver_v01servlet.Part;

public class TcpTread extends Thread {
	private Socket baox;

	public TcpTread(Socket baox) {
		super();
		this.baox = baox;
	}
	private static final Logger logger = LogManager.getLogger(TcpTread.class);
	@Override
	public void run() {
		PrintWriter pt = null;
		BufferedReader bf = null;
		try {
			// 请求行
			// 读取请求行 若干行
			bf = new BufferedReader(new InputStreamReader(baox.getInputStream()));
			// 读取请求行
			HttpServletRequest request = new HttpServletRequest();
			String requestLine = bf.readLine();
			// System.out.println(requestLine);
			// 存储请求行
			String[] reqlin = requestLine.split(" ");
			request.setMethod(reqlin[0]);
			logger.debug("");
			request.setRequestURI(reqlin[1]);
			request.setVersion(reqlin[2]);
			// 读取若干行请求头
			String line = "";
			// 存储请求头
			Map<String, String> requestHeader = new HashMap<>();
			while (!(line = bf.readLine()).equals("")) {
				// System.out.println(line);
				requestHeader.put(line.split(":")[0], line.split(":")[1].trim());
			}
			// System.out.println(requestHeader);
			request.setRequestHeader(requestHeader);
			// 空白行
			System.out.println();
			// 消息体
			// 获取参数
			Map<String, String> paraMap = new HashMap<>();

			if ("POST".equals(request.getMethod())) {
				System.out.println(requestHeader.get("Content-Type"));
				if (requestHeader.get("Content-Type").contains("multipart/form-data;")) {
					// 得到boundary
					String boundary = requestHeader.get("Content-Type").split(";")[1].split("=")[1];
					System.out.println(boundary + "=============================");
					int step = 0;// 记录part到那部分
					String partLine;
					Part part = null;
					PrintWriter pw02 = null;
					while ((partLine = bf.readLine()) != null) {
						// 判断是不是boundary
						if (("--" + boundary).equals(partLine)) {
							step = 1;
							System.out.println("=========---------========");
							if (pw02 != null) {
								pw02.close();
							}
							// 创建一个新的part
							part = new Part();
							continue;// 继续下一行
						}
						// 该读头了
						if (step == 1 && !partLine.equals("")) {
							// 兼容IE
							if (partLine.split(":").length == 3) {
								part.getHeaders().put(partLine.split(":")[0],
										(partLine.split(":")[1] + ":" + partLine.split(":")[2]).trim());
							} else {
								part.getHeaders().put(partLine.split(":")[0], partLine.split(":")[1].trim());
							}
							continue;
						}
						if (step == 1 && partLine.equals("")) {
							step = 2;// 读取完毕，该读正文了
							if (part.getHeaders().get("Content-Disposition").contains("; filename=")) {// 是个文件
								part.setField(false);
								// 获取文件名
								String fileName = part.getHeaders().get("Content-Disposition").split(";")[2]
										.split("=")[1].replaceAll("\"", "");
								if (fileName.indexOf('\\') != -1) {
									fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
								}
								pw02 = new PrintWriter(new FileOutputStream("WebContent/upload/" + fileName));
							} else {
								part.setField(true);
							}
							// 把头放置
							String inputName = part.getHeaders().get("Content-Disposition").split(";")[1].split("=")[1]
									.replaceAll("\"", "");
							request.getParts().put(inputName, part);
							continue;
						}
						if (step == 2 && !("--" + boundary).equals(partLine)) {
							pw02.println(partLine);
						}
						if (step == 2 && ("--" + boundary + "--").equals(partLine)) {
							if (pw02 != null) {
								pw02.close();
							}
							break;
						}
					}

				} else {
					char[] cbuf = new char[100];
					int len = bf.read(cbuf);
					if (len != -1) {
						String paramstr = new String(cbuf, 0, len);
						// 分割
						StringTokenizer st = new StringTokenizer(URLDecoder.decode(paramstr, "UTF-8"), "&=");
						while (st.hasMoreTokens()) {
							paraMap.put(st.nextToken(), st.nextToken());
						}
						// System.out.println(paraMap);
						request.setParaMap(paraMap);
					}
				}
			}
			if ("GET".equals(request.getMethod())) {
				String paramstr = request.getRequestURI().indexOf("?") == -1 ? null
						: request.getRequestURI().substring(request.getRequestURI().indexOf("?") + 1);
				if (paramstr != null) {
					StringTokenizer st = new StringTokenizer(URLDecoder.decode(paramstr, "UTF-8"), "&=");
					while (st.hasMoreTokens()) {
						paraMap.put(st.nextToken(), st.nextToken());
					}
				}
				// System.out.println(paraMap);
			}

			/**
			 * 请求结束
			 */
			// 根据用户不同的UrL给出不同的界面
			String requestURL = request.getRequestURI().indexOf("?") == -1 ? request.getRequestURI()
					: request.getRequestURI().substring(0, request.getRequestURI().indexOf("?"));
			HttpServletResponse response = new HttpServletResponse();
			response.setBao(baox.getOutputStream());

			// HttpServlet httpServlet=(HttpServlet)
			// Class.forName(ProUtil.getClassName(requestURL)).newInstance();
			HttpServlet httpServlet = (HttpServlet) (RefletionsUtil.getClass(requestURL)).newInstance();

			httpServlet.service(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pt != null) {
				pt.close();
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}