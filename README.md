# smartMug
This is the repository for the smartMug project.

# Source Code Documenation
The source documentation is hosted on GitHub pages.
Click [here](https://martin-dold.github.io/smartMug/ "Source Code Documentation") to browse the source code docu.


# Architecture
Within this project the following components are involved:

- **Mug**: Usual mug that holds your drink.
- **SmartMug**: Extension to your make your mug smart.
- **Android app**: Connects to your SmartMug and provides the service(s).
- **Wifi access**: SmartMug and Android App require a Wireless LAN network to communicate.


# Hardware Selection
**TODO**: describe

- Why WiFi?
- Why Arduino? Why this specific module?
- HX711 vs. capacitive sensing or even as separate chapter?
- ...

## Liquid Level Sensing
**TODO**: describe HX711 vs. capacitive sensing here in a separate chapter?


## Arduino
**TODO**: describe

- basic SW architecture
- Arduino libraries for rapid development (wifi, mdns, ota ...)
- HX711 library
- ...


## Android App
**TODO**: describe

- min. API level
- min. Android version
- basic SW architecture
- Bar code scanner
- TCP client
- statistics
- ...


# Communication

This chapter explains the communication schemes and protocols that are used within this project.

## TCP/IP

The communication between the SmartMug and the Android App is based on TCP/IP using Wireless LAN according to IEEE802.11b/g standard.
Therefore, the Android device and the SmartMug must be connected to the same wireless network to allow communication between both.

The SmartMug acts as TCP server that is continuously waiting for incoming connections right after startup.
Once a TCP connection is established successfully, the mug provides updated liquid level values once a second.
The mug publishes its service to the network using [Mulicast DNS](#multicast-dns).

The Android App acts as TCP client connecting to the SmartMug.

## Mulicast DNS

The SmartMug publishes its service through "Multicast DNS" (mDNS) including the following properties:

- *Service name*: smartmug
- *Service type*: \_smartmug.\_tcp

The Android App is listening for providers of the smartmug service type. Using the identifier read from the SmartMug barcode,
the is able to select the correct mug to connect to out of the list of available mugs within the network.

## SmartMug Protocol
**TODO**

# Outlook

This chapter lists further features that can be added to the project.

## Security

The current security mechanism relies on the encryption mechanism used by the wireless network (e.g. WPA2) both Android app and SmartMug are connected to.
The communication between the SmartMug and the app is neither authenticated nor secured on a higher level.
A new feature might be using a TLS protected channel or (at least) an AES encrypted communication between the devices.
However, it should be considered that TLS is heavy computational work for small microcontrollers.
This holds true especially for battery powered device like the SmartMug.
Computing TLS sessions will surely have an impact on the battery life time of a SmartMug.
Still, AES encryption is state-of-the-art even for small microcontrollers as HW acceleration is feasible and allows to
reduce power consumption for battery powered devices.

Therefore, application layer security using AES encryption appears reasonable.
