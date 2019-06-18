package com.moses.distributed.rmi.myRmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User_Stub extends User {

	private Socket socket;
	
	public User_Stub() throws  IOException {
		socket = new Socket("localhost", 8888);
	}
	
	public int getAge() throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
		os.writeObject("age");
		os.flush();
		
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		return ois.readInt();
	}
}
