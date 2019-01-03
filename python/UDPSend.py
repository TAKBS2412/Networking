import time
import socket

x = 0

UDP_IP = "127.0.0.1"
UDP_PORT = 2412
UDP_SEND_RATE_SECS = 1.0
MESSAGE = "Hello, " + str(x)

print("UDP target IP: " + UDP_IP)
print("UDP target port: " + str(UDP_PORT))

sock = socket.socket(socket.AF_INET, # Internet
    socket.SOCK_DGRAM) # UDP

while True:
    start_time = time.time()
#     print(start_time)
    #print("Enter message: ", end="")
    #MESSAGE = input()
    
    print("Message: " + MESSAGE)
    sock.sendto(MESSAGE.encode('utf-8'), (UDP_IP, UDP_PORT))
    
    if MESSAGE == "":
        break
    
    x += 1
    
    MESSAGE = "Hello, " + str(x)
    # See https://github.com/frcteam195/FRC2017/blob/master/2017Robot/src/Subsystems/AutoSelectionUDPReceiver.h
    time_elapsed_secs = (time.time() - start_time) #% (60.0 * 1000)
    if time_elapsed_secs < UDP_SEND_RATE_SECS:
        time.sleep(UDP_SEND_RATE_SECS - time_elapsed_secs)
    
#     data = s.recv(BUFFER_SIZE)
#     print("Received data:" + str(data))
