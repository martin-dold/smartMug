/*
 *  This is the TCP sketch of the smartmug project.
 */

#include <ESP8266WiFi.h>

/* === Global defines === */

/*! @brief Remote TCP port to connect to. */
const uint16_t  remote_port = 8080;
/*! @brief Remote host to connect to. Provided either as IP address or URL. */
const char     *remote_host = "192.168.2.184";
/*! @brief Local TCP port to listen for incomming connections. */
const uint16_t  local_port = 8081;

/* === Global variables === */

/* @brief Use and even initialize WiFiClient class to create TCP connections */
WiFiClient client;

/* @brief Create WiFi server listening on the given port. */
WiFiServer server(local_port);
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

  Serial.print("  Connecting to ");
  Serial.print(remote_host);
  Serial.print(" on port ");
  Serial.println(remote_port);

  if (!client.connect(remote_host, remote_port))
  {
      Serial.println("  connection failed");
      Serial.println("  wait 2 sec...");
      delay(2000);
      return;
  }
  Serial.println("  Connected.");

  Serial.println("  Start TCP listener.");
  // Start the server.
  server.begin();
}

/*!
 * @brief Loop function of this ino sketch to run TCP connection.
 */
void tcp_loop()
{
  // Check if we already established a connection.
  if (!serverRemote)
  {
    // No connection yet. Listen for incoming client requests.
    serverRemote = server.available();
    if (!serverRemote)
    {
      return;
    }

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

  // close the connection:
//  client.stop();
//  Serial.println("Connection closed.");
}


/*!
 * @brief Closes a TCP connection.
 */
void tcp_close()
{
  Serial.println("Closing TCP connection");
  client.stop();
}


/*!
 * @brief Sends data through established TCP connection.
 */
void tcp_send(const uint8_t *data, uint16_t len)
{
  Serial.print("TCP_send(): ");

  if (client.connected())
  {
    for (int i = 0; i < len; ++i)
    {
      // Make debug print of data first.
      Serial.print(data[i], HEX);
      Serial.print(" ");

      // Now actually send data through TCP socket.
      client.write(data[i]);
    }
  }
  else
  {
     Serial.println("cannot send data. No connection.");
  }

  Serial.println("");
}

/* === Local utility functions starting here === */
