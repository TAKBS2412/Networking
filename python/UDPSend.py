import socket

UDP_IP = "127.0.0.1"
UDP_PORT = 2412
MESSAGE = "Hello, world!"

print("UDP target IP: " + UDP_IP)
print("UDP target port: " + str(UDP_PORT))

sock = socket.socket(socket.AF_INET, # Internet
    socket.SOCK_DGRAM) # UDP

while True:
    print("Enter message: ", end="")
    MESSAGE = input()
    
    sock.sendto(MESSAGE.encode('utf-8'), (UDP_IP, UDP_PORT))
    
    if MESSAGE == "":
        break
#     data = s.recv(BUFFER_SIZE)
#     print("Received data:" + str(data))
