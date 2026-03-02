import socket, struct

HOST = "localhost"
PORT = 9000

print("Interactive Vendor Client")
print("Type messages like:")
print("  ACK hello")
print("  HDR something")
print("  REC data")
print("  END done")
print("Type 'quit' to exit\n")

with socket.create_connection((HOST, PORT)) as s:
    while True:
        line = input("> ").strip()
        if line.lower() == "quit":
            break
        if not line:
            continue

        payload = line.replace(" ", "", 1).encode()  # keeps first space after type
        frame = struct.pack(">I", len(payload)) + payload

        decoded_frame = frame.decode("utf-8")
        print("Sending: "+decoded_frame)
        s.sendall(frame)

        resp = s.recv(4096)
        if not resp:
            print("Server closed connection")
            break
        print("Response:", resp.decode(errors="ignore"))