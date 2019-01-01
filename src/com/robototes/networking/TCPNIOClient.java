package com.robototes.networking;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class TCPNIOClient {
	public static void main(String argv[]) throws Exception {
		String sentence;
		String serverSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		SocketChannel clientSocket = SocketChannel.open();
		clientSocket.connect(new InetSocketAddress("localhost", 2412));
		ByteBuffer readBuf = ByteBuffer.allocate(2412);
		ByteBuffer writeBuf;
		System.out.print("Enter message: ");
		while(true) {
			sentence = inFromUser.readLine();
			if(sentence.equals("")) break;
			writeBuf = ByteBuffer.wrap(sentence.getBytes());
			clientSocket.write(writeBuf);
			writeBuf.clear();
			
			clientSocket.read(readBuf);
			readBuf.flip();
			serverSentence = new String(readBuf.array());
			readBuf.clear();
			System.out.println("FROM SERVER: " + serverSentence);
			System.out.print("Enter message: ");
		}
		System.out.println("No data sent! Quitting...");
		clientSocket.close();
	}
}