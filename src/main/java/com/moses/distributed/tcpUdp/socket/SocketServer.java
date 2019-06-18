package com.moses.distributed.tcpUdp.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(8888);
			while(true) {
				Socket socket = ss.accept();
				new Thread(()->{
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
						
						while(true) {
							String clientData = br.readLine();
							if(clientData == null) {
								break;
							}
							System.out.println("Server side received data: " + clientData);
							pw.println("Hello client");
							pw.flush();
						}
						
					} catch(IOException e) {
						e.printStackTrace();
					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
