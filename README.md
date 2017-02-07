# smartMug
This is the repository for the smartMug project.

# Project Startup and Related Work

At start of the project, the team started research on smart mug applications and required technologies.
Besides others, the task was to address the following two main questions:

- Do applications regarding smart liquid level sensing already exist?
- Which technology can be used for smart liquid level sensing?

With regards to the first question, two interesting papers are found.

The project **"Lover's cup"** [1] connects two spatially seperated cups via wireless network to the internet.
This remote connection "enable people to share the time of drinking with someone they care about in different places.
Using a wireless connection, an otherwise ordinary pair of cups becomes a communication device,
amplifying the social aspect of drinking behavior" [1].
However, the focus of the paper is the social aspect of drinking rather than the technologies required to build the device.

The project **"Wireless Liquid Level Sensing for Restaurant Applications"** [4] is more of interest for the SmartMug project
as it targets a similar application: "Since restaurants often make much of their profits on drinks, it is critical for servers
to offer refills in a timely fashion. We propose wireless liquid level sensing glassware to aid in this task." [4]
Though the motiviation for the project is a more economical aspect, the paper describes
the technologies and especially the liquid level sensing in detail. This project solved the liquid level sensing by
capacitive sensing.
In fact, the paper aims to the same application as the SmartMug.
However, the project was founded in 2002 and technologies vastly enhanced since then. This is the main reason to still
develop the SmartMug using state-of-the-art technologies like low-power optimized wireless modules and smartphones.

With regards to the question about liquid level sensing, the research identified:

- In industry, the focus relies on measuring the liquid flow into or from a container whenever possible rather than measuring the
level of the container itself. Examples are pipets in biology or dosing pumps as medical devices.
- If the liquid level shall be measured within the container, a floating gauge is typically used, e.g. like in car tanks.
However, this solution does not fit to the SmartMug project because of hygienic reasons and general user acceptance as
drinking the liquid in the SmartMug shall be possible and nobody expects a floating gauge in his or her drink.
- An appropriate technology is used by the project **"Wireless Liquid Level Sensing for Restaurant Applications"** [4]
that uses capacitive sensing.
- If the weight of the mug is known in advance, the liquid level can be measured by weighting the mug including the liquid.
The SmartMug project finally used this technology.


# Source Code Documentation
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

### Pin configuration and functions

| Pin           | Function                |
| ------------- |:-----------------------:|
| 13            | RGB-LED Red             |
| 5             | RGB-LED Green           |
| 12            | RGB-LED Blue            |
| 14            | HX711 scale: DOUT       |
| 16            | HX711 scale: PD_SCK     |
| 21            | UART Tx data            |
| 22            | UART Rx data            |


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
The mug publishes its service to the network using [Multicast DNS](#multicast-dns).

The Android App acts as TCP client connecting to the SmartMug.

## Multicast DNS

The SmartMug publishes its service through "Multicast DNS" (mDNS) including the following properties:

- *Service name*: smartmug
- *Service type*: \_smartmug.\_tcp

The Android App is listening for providers of the smartmug service type. Using the identifier read from the SmartMug barcode,
the is able to select the correct mug to connect to out of the list of available mugs within the network.

## SmartMug Protocol

The SmartMug Protocol is invented for bidirectional communication between the SmartMug and the Android App. It is used on top of the TCP protocol to format the byte stream.
It follows the basic principle of < TAG >< LEN >< Value > and is implemented as shown in the following graph:

```
+-----------+------------+----------------+----------------+
|           |            |                |                |
|    TAG    |   Length   |      Value     |  End of Frame  |
|           |            |                |                |
+-----------+------------+----------------+----------------+

   1 byte       1 byte        n bytes           1 byte
```

Mutli-byte fields are transported with most significant byte (MSB) first.

The following tags are currently defined:

- Tag = **0x01**: (SmartMug to Android App)  
  Sensor Data: Weight (in gram)
- Tag = **0x02**: (Android App to SmartMug)  
  LED Set Color using color codes transported as value:  
  - Off:   0
  - Red:   1
  - Green: 2
  - Blue:  3
  - White: 4
- Tag = **0x03**: (Android App to SmartMug)  
  Tare SmartMug scale to '0'

Accordingly, an example of a sensor data frame is:

```
+-----------+------------+----------------+----------------+
|           |            |                |                |
|   0x01    |    0x02    |     0x017A     |      0x0A      |
|           |            |                |                |
+-----------+------------+----------------+----------------+

   Sensor       Length          378g             '\n'
    Data
```

# Outlook

This chapter lists further features that can be added to the project.

## Connect to the Restaurant

A connection between SmartMug and the restaurant would allow the barkeeper to monitor the customers mugs using live data, i.e.
the barkeeper is able to detect when to serve which guest based on the SmartMug data.
Furthermore, this allows the customer to directly place an order to the barkeeper via wireless connection.

## Payment via SmartMug App

Once the connection to the restaurant is established, one may advance the SmartMug App to allow a digital payment method for the drinks.

## Health monitoring

The data the SmartMug App currently collects (sex, weight and total amount of liquid) allows to offer further services like health monitoring.
The SmartMug App can detect when the user did not drink for some hours and can give warnings to ensure that a patient drinks enough liquid during a day.

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

# References

- [1] Lover’s Cups: Drinking Interfaces as New Communication Channels, Chung et al., 2006, [Link](http://dl.acm.org/citation.cfm?id=1125532&dl=ACM&coll=DL&CFID=725409781&CFTOKEN=29915740 "Link to Paper")
- [2] Ultrasonic Sensing Basics for Liquid Level Sensing, Flow Sensing, and Fluid Identification Applications,  
Texas Instruments, 2015, [Link](http://www.ti.com/lit/an/snaa220a/snaa220a.pdf "Link to PDF")
- [3] FDC1004: Basics of Capacitive Sensing and Applications, Texas instruments, 2014, [Link](http://www.ti.com/lit/an/snoa927/snoa927.pdf "Link to PDF")
- [4] Wireless Liquid Level Sensing for Restaurant Applications, Dietz et al., 2002, [Link](http://www.merl.com/publications/docs/TR2002-21.pdf "Link to Paper")
