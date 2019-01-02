package com.robototes.networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPThreadedServer implements Runnable {
	private boolean shouldRun = true;
	private static final int PORT = 2412;
	
	DatagramSocket socket;
	DatagramPacket packet;
	byte[] receiveData = new byte[1024];
	
	public UDPThreadedServer() { //TODO add a parameter for update time/frequency.
		try {
			socket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(shouldRun) {
			try {
				receiveData = new byte[1024];
				packet = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(packet);
				
				receiveData = packet.getData();
				String receiveString = (new String(receiveData)).trim();
				System.out.println(receiveString);
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

	public static void main(String... args) {
		UDPThreadedServer threadServer = new UDPThreadedServer();
		Thread newThread= new Thread(threadServer, "UDP Server Thread");
		newThread.start();
		System.out.println(newThread.getName());
	}
}
