package com.robototes.networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class UDPThreadedServer implements Runnable {
	private boolean shouldRun = true;
	private int PORT = 2412;
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private byte[] receiveData = new byte[1024];
	
	private final int QUEUECAPACITY = 20;
	private ArrayBlockingQueue<String> receivedDataQueue = new ArrayBlockingQueue<String>(QUEUECAPACITY);
	
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
//				System.out.println(receiveString);
				if(receivedDataQueue.remainingCapacity() > 0) {
					receivedDataQueue.add(receiveString);
				} else {
//					System.out.println("Not enough space!");
				}
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
	
	// Stops the thread.
	public void stop() {
		shouldRun = false; 
	}
	
	// Reads data from the network (via UDP) and returns it as a String.
	public synchronized String readData() {
		return receivedDataQueue.poll(); // Retrieves and removes from the queue, might return null, must check (from docs).
	}

	public static void main(String... args) {
		UDPThreadedServer threadServer = new UDPThreadedServer();
		Thread newThread = new Thread(threadServer, "UDP Server Thread");
		newThread.start();
		System.out.println(newThread.getName());
		String input = "";
		Scanner scan = new Scanner(System.in);
		do {
			System.out.print("Keep running? ");
			input = scan.nextLine();
			System.out.println(threadServer.readData());
		} while(input.equals("y"));
		scan.close();
		threadServer.stop();
	}
}
