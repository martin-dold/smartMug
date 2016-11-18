/*
 *  This is the TCP sketch of the smartmug project.
 */

#include <ESP8266WiFi.h>

/* === Global defines === */

/*! @brief Remote TCP port to connect to. */
const uint16_t  remote_port = 8080;
/*! @brief Remote host to connect to. Provided either as IP address or URL. */
const char     *remote_host = "192.168.2.184";

/* === Global variables === */

/* @brief Use and even initialize WiFiClient class to create TCP connections */
WiFiClient client;

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
}

/*!
 * @brief Loop function of this ino sketch to run TCP connection.
 */
void tcp_loop()
{

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
      Serial.print(data[i]);

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
