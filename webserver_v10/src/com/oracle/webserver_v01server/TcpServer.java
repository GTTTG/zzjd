package com.oracle.webserver_v01server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TcpServer {
	private static final Logger logger = LogManager.getLogger(TcpServer.class);

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(808);
			logger.info("服务器启动");
			while (true) {
				// 等顾客来交给包厢服务人员
				Socket baox = serverSocket.accept();
				// 有顾客来启动
				new TcpTread(baox).start();
				logger.debug("有客户端进来了");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
					logger.debug("服务器关闭");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
