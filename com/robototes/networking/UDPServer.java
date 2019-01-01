package com.robototes.networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
	
	private static final int PORT = 2412; 
	
	public static void main(String args[]) throws Exception {
		DatagramSocket socket = new DatagramSocket(PORT);
		DatagramPacket packet;
		byte[] receiveData = new byte[1024];
		
		while(true) {
			try {
				receiveData = new byte[1024];
				packet = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(packet);
				
				receiveData = packet.getData();
				String receiveString = (new String(receiveData)).trim();
				System.out.println(receiveString.length());
				if(receiveString.equals("")) {
					System.out.println("No data received! Quitting...");
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		socket.close();
	}
}
