# Vendor Gateway

## Overview

This project implements a **pluggable TCP gateway** for multiple vendors with different message framing protocols. It uses **Netty** for TCP connection handling and **Spring Boot WebFlux** for downstream HTTP service integration. The gateway supports:

- Custom message framing (length-prefixed, comma-delimited, or vendor-specific)
- Heartbeat detection
- Dynamic loading of custom framing classes
- Async routing to backend HTTP services
- Reactive responses using WebFlux
- Logging and simple console output for testing

---

## Architecture


Vendor TCP Client
|
v
Netty TCP Server
|
v
Frame Decoder (pluggable)
|
v
Heartbeat Handler
|
v
Business Router Handler
|
v
HttpServiceClient (WebFlux)
|
v
Backend HTTP Service (can be mocked)


- **Frame Decoder**: dynamically loaded based on a property (`framing.folder` + `framing.class`)  
- **HttpServiceClient**: forwards vendor messages to downstream HTTP service  
- **HeartbeatHandler**: detects idle/stale connections  

---

## Features

- Supports multiple vendors with different framing protocols
- Pluggable decoder interface: `MessageFrameDecoder`
- Default: `LengthFieldBasedFrameDecoder` (4-byte length prefix)
- Optional: `CommaDelimitedFrameDecoder`
- Reactive HTTP backend integration
- Configurable ports via `application.properties`
- Logs all received messages to console for easy testing

---

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# TCP server port
server.port=9000

# Folder where custom framing classes (.class) exist
framing.folder=/path/to/custom/decoders

# Fully qualified class name of decoder to use
framing.class=com.example.vendor.netty.handlers.CommaDelimitedFrameDecoder
Running the Gateway

Build and run

./gradlew clean build
./gradlew run

Start a mock backend (if using WebFlux HTTP client):

python3 mock_server.py
Using the Python Interactive Client

A simple Python client (client.py) is provided for testing. It automatically applies the correct TCP framing.

Run the client:

python3 client.py

Type messages interactively:

ACKHello Vendor
HDRTest Message
REC12345
ENDDone

Type quit to exit the client.

Example session:

> ACKHello Vendor
Response: OK:ACKHello Vendor
> REC{"id":1}
Response: OK:REC{"id":1}
> quit

The client automatically:

Prepends the length prefix (4-byte) for the server

Sends the payload

Receives and prints server responses

Adding a Custom Message Framing

Implement MessageFrameDecoder interface:

public class MyCustomDecoder implements MessageFrameDecoder {
    @Override
    public ChannelInboundHandler getHandler() {
        return this; // your ByteToMessageDecoder
    }
}

Compile and place .class file in the folder specified by framing.folder

Update framing.class property in application.properties

The gateway will dynamically load it on startup.

Testing

Use the interactive Python client to send test messages

Observe server logs to confirm frame parsing

Check HTTP backend responses (mock or real service)

Requirements

Java 24

Gradle 8+ (or use bundled gradlew)

Netty 4.1.x

Spring Boot WebFlux 3.x

Python 3 (for interactive client)

License

This project is provided for demonstration purposes. No license.


This version includes **instructions for running and using the Python interactive client**.  

If you want, I can also create a **minimal `mock_server.py`** snippet to go along with it. Do you want me to do that?