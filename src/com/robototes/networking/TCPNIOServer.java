package com.robototes.networking;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

class TCPNIOServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		Selector selector = Selector.open();
		ServerSocketChannel welcomeSocket = ServerSocketChannel.open();
		welcomeSocket.bind(new InetSocketAddress(2412));
		welcomeSocket.configureBlocking(false);
		welcomeSocket.register(selector, SelectionKey.OP_ACCEPT);
		
		ByteBuffer readBuf = ByteBuffer.allocate(2412);
		ByteBuffer writeBuf = ByteBuffer.allocate(2412);
		int bytesRead = 0;
		System.out.println("Waiting for connection...");
		do {
			selector.select();
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			while(iter.hasNext()) {
				SelectionKey key = iter.next();
				if(key.isAcceptable()) {
					SocketChannel connectionSocket = welcomeSocket.accept();
					if(connectionSocket == null) continue;
					System.out.println("Accepted new connection");
					connectionSocket.configureBlocking(false);
					connectionSocket.register(selector, SelectionKey.OP_READ);
				}
				if(key.isReadable()) {
					SocketChannel client = (SocketChannel) key.channel();
					bytesRead = client.read(readBuf);
					int position = readBuf.position();
					readBuf.flip();
					byte[] message = new byte[position];
					System.arraycopy(readBuf.array(), 0, message, 0, position);
					clientSentence = new String(message);
//					System.out.println("Received: " + readBuf.getDouble());
					readBuf.clear();
					System.out.println("Received: " + clientSentence);
					capitalizedSentence = clientSentence.toUpperCase();
					writeBuf = ByteBuffer.wrap(capitalizedSentence.getBytes());
					client.write(writeBuf);
					writeBuf.clear();
				}
				iter.remove();
			}
			
//		} while(!clientSentence.equals(""));
		} while(bytesRead != -1);
		System.out.println("No data received! Quitting...");
		welcomeSocket.close();
	}
}