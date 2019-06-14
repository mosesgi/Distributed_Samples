package com.moses.distributed.basics.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",8888);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //get data from server
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);	//write data to server
			
			pw.println("Hello server, this is client");
			
			while(true) {
				String serverResponse = br.readLine();
				if(serverResponse == null) {
					break;
				}
				System.out.println("Client received data: " + serverResponse);
			}
			pw.close();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
