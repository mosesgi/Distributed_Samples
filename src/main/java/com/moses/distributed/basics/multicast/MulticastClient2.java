package com.moses.distributed.basics.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient2 {
	public static void main(String[] args) {
		InetAddress group= null;
		MulticastSocket socket = null;
		try {
			group = InetAddress.getByName("224.3.3.3");
			socket = new MulticastSocket(8888);
			socket.joinGroup(group);
			
			byte[] buf = new byte[256];
			while(true) {
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				socket.receive(dp);
				
				String msg = new String(dp.getData());
				System.out.println("Client2 side recieved: " + msg);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				socket.close();
			}
		}
	}
}
