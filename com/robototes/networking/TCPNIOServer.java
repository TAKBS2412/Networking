package com.robototes.networking;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

class TCPNIOServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		ServerSocketChannel welcomeSocket = ServerSocketChannel.open();
		welcomeSocket.socket().bind(new InetSocketAddress(2412));
		SocketChannel connectionSocket = welcomeSocket.accept();
		ByteBuffer readBuf = ByteBuffer.allocate(2412);
		ByteBuffer writeBuf = ByteBuffer.allocate(2412);
		int bytesRead;
		do {
			bytesRead = connectionSocket.read(readBuf);
			readBuf.flip();
			clientSentence = new String(readBuf.array());
			readBuf.clear();
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase();
			writeBuf = ByteBuffer.wrap(capitalizedSentence.getBytes());
			connectionSocket.write(writeBuf);
			writeBuf.clear();
//		} while(!clientSentence.equals(""));
		} while(bytesRead != -1);
		System.out.println("No data received! Quitting...");
		welcomeSocket.close();
	}
}