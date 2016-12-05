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
const uint16_t  local_port = 8081;
/*! @brief States if an remote client is connected to our server port. */
bool remoteConnected;

/* === Global variables === */
/*! @brief Create WiFi server listening on the given port. */
WiFiServer server(local_port);
/*! @brief Create WiFi client that handles client connections received through listening server. */
WiFiClient serverRemote;

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

  Serial.println("  Start TCP listener.");
  remoteConnected = false;
  // Start the server.
  server.begin();
}

/*!
 * @brief Loop function of this ino sketch to run TCP connection.
 */
void tcp_loop()
{
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
      return;
    }

    remoteConnected = true;
    Serial.println("Client connected");
  }

  // Read available bytes.
  while (serverRemote.available())
  {
    char c = serverRemote.read();

    // Print the value (for debugging).
    Serial.write(c);

    // Exit loop if end of line.
    if ('\n' == c)
    {
      break;
    }
  }
}


/*!
 * @brief Closes a TCP connection.
 */
void tcp_close()
{
  Serial.println("Closing TCP connection");
  serverRemote.stop();
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
    for (int i = 0; i < len; ++i)
    {
      // Make debug print of data first.
      Serial.print(data[i], HEX);
      Serial.print(" ");

      // Now actually send data through TCP socket.
      serverRemote.write(data[i]);
    }
  }
  else
  {
    remoteConnected = false;
    Serial.println("cannot send data. No connection.");
  }

  Serial.println("");
}

/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
