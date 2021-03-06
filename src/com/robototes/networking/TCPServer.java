package com.robototes.networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(2412);

		Socket connectionSocket = welcomeSocket.accept();
		BufferedReader inFromClient =
		 new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		do {
			clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		} while(!clientSentence.equals(""));
		System.out.println("No data received! Quitting...");
		welcomeSocket.close();
	}
}