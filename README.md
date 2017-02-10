# SmartMug
This is the repository for the SmartMug project.

The intention of the project is to turn a standard mug into a SmartMug by mounting the developed
SmartMug case on the bottom of the mug.
Using the developed Android App, the user can now connect to his mug.
Once the wireless connection is established, the following services are available:

- Ordering via app: the SmartMug changes its color to signal the waitress
that the user wants a new drink, reorder the same drink, asks for the bill or has no request at all.
- Monitor the current filling level of the mug within the app.
- Play two different drinking games.
- Entering personal data to display user statistics, e.g. blood alcohol level.

# Project Startup and History

This chapter describes initial considerations at project startup that finally lead to the developed SmartMug.
The documentation of the latest release starts in section [Architecture](#architecture)

## Initial Research and Related Work

At start of the project, the team started research on smart mug applications and required technologies.
Besides others, the task was to address the following two main questions:

- Do applications regarding smart liquid level sensing already exist?
- Which technology can be used for smart liquid level sensing?

With regards to the first question, two interesting papers are found.

The project **"Lover's cup"** [1] connects two spatially separated cups via wireless network to the internet.
This remote connection "enable people to share the time of drinking with someone they care about in different places.
Using a wireless connection, an otherwise ordinary pair of cups becomes a communication device,
amplifying the social aspect of drinking behavior" [1].
However, the focus of the paper is the social aspect of drinking rather than the technologies required to build the device.

The project **"Wireless Liquid Level Sensing for Restaurant Applications"** [4] is more of interest for the SmartMug project
as it targets a similar application: "Since restaurants often make much of their profits on drinks, it is critical for servers
to offer refills in a timely fashion. We propose wireless liquid level sensing glassware to aid in this task." [4]
Though the motivation for the project is a more economical aspect, the paper describes
the technologies and especially the liquid level sensing in detail. This project solved the liquid level sensing by
capacitive sensing.
In fact, the paper aims to the same application as the SmartMug.
However, the project was founded in 2002 and technologies vastly enhanced since then. This is the main reason to still
develop the SmartMug using state-of-the-art technologies like low-power optimized wireless modules and smartphones.

With regards to the question about liquid level sensing, the research identified:

- In industry, the focus relies on measuring the liquid flow into or from a container whenever possible rather than measuring the
level of the container itself. Examples are pipette in biology or dosing pumps as medical devices.
- If the liquid level shall be measured within the container, a floating gauge is typically used, e.g. like in car tanks.
However, this solution does not fit to the SmartMug project because of hygienic reasons and general user acceptance as
drinking the liquid in the SmartMug shall be possible and nobody expects a floating gauge in his or her drink.
- An appropriate technology is used by the project **"Wireless Liquid Level Sensing for Restaurant Applications"** [4]
that uses capacitive sensing. Additionally, two application reports give detailed insight into this technology [2] [3].
- If the weight of the mug is known in advance, the liquid level can be measured by weighting the mug including the liquid.
The SmartMug project finally used this technology.

## Hardware and Software Selection
One of the first steps at project startup was to define the required hardware and software components.
Constraints are:

- A wireless protocol that fits for battery powered nodes.
- A wireless protocol that allows a huge number of nodes within network, as there are a lot of mugs within a restaurant or bar.
- A wireless communication module that allows a battery powered application to be attached to the mug.
It should be possible to easily run the previously defined wireless protocol.
- The SmartMug user should easily connect to its mug, even with its own smartphone.

### Wireless Protocol

In the very first evaluation step, the following wireless protocols were considered:

1. IEEE802.11 Wireless LAN 
2. IEEE802.15.4 Zigbee
3. Bluetooth (Low Energy)
4. 6LoWPAN

The Zigbee as well as the 6LoWPAN protocol fit very well with regards to the power consumption constrain:
Both protocols are specialized for battery powered nodes as they both are designed for this application purpose.
Drawback of these protocols is the missing connection to the users smartphone: 
By default, none of these protocols are supported by any state-of-the-art smartphone, that typically supports Wireless LAN and Bluetooth.
Therefore, communication to the smartphone requires a gateway node that forwards messages between the 
SmartMug and the smartphone.
Due to this disadvantage of requiring an additionally device for network translation,
the protocols Bluetooth and Wireless LAN are short-listed.

Finally, the focus changed to Wireless LAN as:

- it is fully supported by almost all smartphones, even older ones,
- it easily allows to run well-known protocols such as TCP/IP or UDP on top for applications data exchange,
- it allows a huge number of mugs to be connected to the same network,
- a lot of today's restaurants and bars already provide a Wireless LAN network to the customers.
This would allow to reuse existing restaurant infrastructure for future SmartMug services directly connected to the bar.
- the universities laboratory provides a Wireless LAN module in-house that states promising power consumption in the documentation.

### Wireless LAN module

As a result, the low-power Wireless module [ESP8266](http://espressif.com/en/products/hardware/esp8266ex/resources "ESP8266") was chosen.
It meets the low-power requirement, has sufficient GPIO pins for the connection to the liquid level sensing unit 
and provides a huge variety of software development kits (SDK), e.g. NodeMCU (Lua-based), Arduino and MicroPython.
A full list of available SDKs is available [here](http://www.mikrocontroller.net/articles/ESP8266 "ESP8266 SDKs").

In the end, Arduino was chosen as it is open-source and provides full library support for Wireless LAN, TCP/IP, Multicast DNS
and even for the HX711 load cell that was chosen for liquid level sensing.

### Smartphone App

To allow the SmartMug user to connect to the mug, a smartphone app shall be developed. 
For the first prototypes, it was defined to develop an app for Android smartphones.
Development of an iPhone app was seen as future extension to the project.


# Source Code Documentation
The source documentation is hosted on GitHub pages.
Click [here](https://martin-dold.github.io/smartMug/ "Source Code Documentation") to browse the source code documentation.


# Architecture
Within this project the following components are involved:

- **Mug**: Usual mug that holds your drink.
- **SmartMug**: Extension to your make your mug smart.
- **Android app**: Connects to your SmartMug and provides the service(s).
- **Wifi access**: SmartMug and Android App require a Wireless LAN network to communicate.

## Liquid Level Sensing

Initially there were two different approaches for liquid level sensing. Finally the decision was pro weight measurent, because:

- Capacitive measurement requires bent or flexible copper plates to be mounted on a circular surface. This material was not available in a suitable amount time. 
- At the beginning the team was not sure, if such a circuit could be developed in time.

The chosen method of liquid level sensing is weight measurement. This task is accomplished by a load cell, an amplifier and an analog-to-digital converter.

- **load cell**: Contains four strain gauges, that build a Wheatstone Bridge. The differential output voltage can be measured by an amplifier.
- **HX711**: Uses a built in amplifier and ADC. No further circuits are required.

## Arduino

Wireless module [ESP8266](http://espressif.com/en/products/hardware/esp8266ex/resources "ESP8266") 
is programmed using open-source Arduino SDK. The firmware is composed of several Arduino files.
Each of them contains a separate software unit/module. 
For a complete list of software modules and its implementation details, check out the Arduino chapter
of the [Source Code Documentation](https://martin-dold.github.io/smartMug/ "Source Code Documentation").

Most important software modules are:

- **Wifi:**  
Uses Arduino ESP8266 Wifi library to establish a Wifi connection.
After startup, the SmartMug scans its environment for the Wireless LAN SSID "*smartmug*". A password for WPA2 is required.
- **TCP:**  
Uses Arduino TCP library to create a server socket and listen on TCP port 8080 for incomming connection requests.
- **HX711:**  
Uses HX711 library to measure the weight using HX711 load cell.
- **Over-the-air Update:**  
Uses Arduino ESP8266 OTA library to provide firmware update over-the-air.

### Pin configuration and functions

The following table lists the pins that are connected to the microcontroller.

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

This section contains a brief overview of the Android App architecture and its main features.
For more details check out the [Source Code Documentation](#source-code-documentation).

- Target API level: 24.
- Android version: 7.
- Architecture of the SmartMug App, first view is Main Activity:
    - Connection to Mug activity -> build connection to SmartMug via manuell input of the IP Adress or via bar code scanner.
    - Statistics activity -> collects statical data of the user.
    - Order activity -> allows to call the waitress, reorder the same drink or ask for the bill.
    - Personal Data activity -> allows the user to enter personal data information to calculate statistics like blood alcohol level.
    - Games activity -> allows to the user to play drinking games.
    - TCPClient class -> create new TCP Connection for sending (e.g. re-order) and receiving (e.g. filling level).
    - MugContent class -> handle received messages from the SmartMug.
    - NSDHelper class -> scans the network for smartmug mug services using mDNS.

## SmartMug case manufacturing

The case manufacturing contains two main tasks namely the case itself and the mounting parts, that are connecting the actual mug with the load cell. All the parts are modelled in SolidWorks 2013 and are printed via the MakerBot Replicator X2 3D-printer.

The case itself has a bottom part, where all the components are mounted. The mounted elements are:
- Load Cell
- Adafruit Huzzah ESP8266 Microcontroller
- Hx711 ADC and Amplifier
- RGB LED
- Battery case for AA batteries.

The second part is a bayonet mount, with a female and a male part. These are used to connect the mug the load cell.

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

The Android App is listening for providers of the smartmug service type. Using the identifier read from the SmartMug bar code,
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

Multi-byte fields are transported with most significant byte (MSB) first.

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

This chapter lists further features and ideas in general that can be added to the project.

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
- [5] Datasheet of Hx711 [Link] (http://www.ti.com/lit/an/snoa927/snoa927.pdf "Link to datasheet")
