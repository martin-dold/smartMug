/*! @file  tcp.ino
 *  @brief This is the TCP sketch of the smartmug project.
 *
 *  @defgroup tcp TCP
 *  @brief Adds TCP functionality to the smartmug.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup tcp
 *  @{
 */

#include <ESP8266WiFi.h>

/* === Global defines === */
/*! @brief Local TCP port to listen for incomming connections. */
const uint16_t  local_port = 8080;
/*! @brief States if an remote client is connected to our server port. */
bool remoteConnected;

/* === Global variables === */
/*! @brief Create WiFi server listening on the given port. */
WiFiServer server(local_port);
/*! @brief Create WiFi client that handles client connections received through listening server. */
WiFiClient serverRemote;
/*! @brief Data array for received SmartMug protocol frames. */
uint8_t rxData[50];

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

/*!
 * @brief Setup function of this ino sketch to setup TCP socket for operation.
 */
void tcp_setup()
{
  Serial.println("\n###");
  Serial.println("Starting TCP setup.");
  Serial.println("###");

  Serial.print("  Start TCP listener on port ");
  Serial.println(local_port);
  remoteConnected = false;
  // Start the server.
  server.begin();

  for(int i = 0; i < sizeof(rxData); i++)
  {
    rxData[i] = 0;
  }
}

/*!
 * @brief Loop function of this ino sketch to run TCP connection.
 */
void tcp_loop()
{
  int rxDataCount = 0;
  // Check if we already established a connection.
  if (!remoteConnected)
  {
    // No connection yet. Listen for incoming client requests.
    serverRemote = server.available();
    if (!serverRemote)
    {
      /* No connection available.
       * Therefore, skip reading data below and return here.
       */
      led_setOff();
      return;
    }

    remoteConnected = true;
    Serial.println("Client connected");
    // Turn on using the color that was set by the user/app
    led_turnOnLastColor();
  }

  // Read available bytes.
  while (serverRemote.available())
  {
    uint8_t b = serverRemote.read();
    rxData[rxDataCount] = b;
    rxDataCount++;

    // Print the value (for debugging).
    Serial.write(b);

    // Exit loop if end of line.
    if (0x0A == b)
    {
      break;
    }

    if(rxDataCount >= sizeof(rxData))
    {
      // Stop reading to not overflow our RX buffer.
      break;
    }
  }

  if(rxDataCount <= sizeof(rxData))
  {
    // New data is available
    parseTcpRxData(rxData, rxDataCount);
  }
}


/*!
 * @brief Closes a TCP connection.
 */
void tcp_close()
{
  Serial.println("Closing TCP connection");
  serverRemote.stop();
  remoteConnected = false;
}


/*!
 * @brief Sends data through established TCP connection.
 * @param data Pointer to the data to be send.
 * @param len  Length of the data to be send.
 */
void tcp_send(const uint8_t *data, uint16_t len)
{
  Serial.print("TCP_send(): ");

  if (serverRemote.connected())
  {
    /* Firstly, send the whole data in single call. */
    int bytesWritten = serverRemote.write(data, len);
    /* Afterwards, make debug print of written data bytes. */
    for (int i = 0; i < bytesWritten; ++i)
    {
      Serial.print(data[i], HEX);
      Serial.print(" ");
    }
    Serial.println("");
  }
  else
  {
    remoteConnected = false;
    Serial.println("cannot send data. No connection.");
  }
}

/* === Local utility functions starting here === */

/*!
 * @brief Parses given data for a SmartMug protocol frame.
 */
void parseTcpRxData(uint8_t *data, uint16_t len)
{
  /* TAG 0x02 = LED set Color. */
  if(rxData[0] == 0x02)
  {
    if(rxData[1] == 0x01)
    {
      if(rxData[2] == 0x00)
      {
        Serial.println("led_setOff()");
        led_setOff();
      }
      else if(rxData[2] == 0x01)
      {
        Serial.println("led_setRed()");
        led_setRed();
      }
      else if(rxData[2] == 0x02)
      {
        Serial.println("led_setGreen()");
        led_setGreen();
      }
      else if(rxData[2] == 0x03)
      {
        Serial.println("led_setBlue()");
        led_setBlue();
      }
      else if(rxData[2] == 0x04)
      {
        Serial.println("led_setWhite()");
        led_setWhite();
      }
    }
  }
  /* TAG 0x03 = Tare weight. */
  else if(rxData[0] == 0x03)
  {
    if(rxData[1] == 0x01)
    {
      if(rxData[2] == 0x00)
      {
        Serial.println("hx711_tare()");
        hx711_tare();
      }
    }
  }

  /* Clear rx data buffer after reading. */
  memset(rxData, 0, sizeof(rxData));
}

/*!
 * @}
 * @}
 */
