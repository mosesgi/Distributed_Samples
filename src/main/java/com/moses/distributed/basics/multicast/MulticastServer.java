package com.moses.distributed.basics.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;

public class MulticastServer {
	public static void main(String[] args) {
		MulticastSocket socket = null;
		try {
			InetAddress group = InetAddress.getByName("224.3.3.3");
			socket = new MulticastSocket();
			
			for(int i = 0 ; i<10; i++) {
				String data = "Hello, all clients";
				byte[] bytes = data.getBytes();
				socket.send(new DatagramPacket(bytes,bytes.length,group,8888));
				TimeUnit.SECONDS.sleep(2);
			}
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				socket.close();
			}
		}
		
	}
}
