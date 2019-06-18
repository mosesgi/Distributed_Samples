package com.moses.distributed.rmi.myRmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server side 
 * @author Moses
 *
 */
public class User_Skeleton extends Thread {
	private UserServer userServer;
	
	public User_Skeleton(UserServer userServer) {
		this.userServer = userServer;
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8888);
		
			Socket socket = serverSocket.accept();
			while(socket != null) {
				ObjectInputStream read = new ObjectInputStream(socket.getInputStream());
				String method = (String) read.readObject();
				if(method.equals("age")) {
					int age = userServer.getAge();
					ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
					outputStream.writeInt(age);
					outputStream.flush();
					read.close();
					socket.close();
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
