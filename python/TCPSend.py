#!/usr/bin/env python
 
import socket


TCP_IP = '127.0.0.1'
TCP_PORT = 2412
BUFFER_SIZE = 1024

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))

while True:
    print("Enter message: ", end="")
    MESSAGE = input()
    if MESSAGE == "":
        break
    
    s.send(MESSAGE.encode('utf-8'))
    
    data = s.recv(BUFFER_SIZE)
    print("Received data:" + str(data))

s.close()