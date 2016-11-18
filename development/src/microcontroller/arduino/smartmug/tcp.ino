/*
 *  This is the TCP sketch of the smartmug project.
 */

#include <ESP8266WiFi.h>

/* === Global defines === */
const uint16_t  remote_port = 8080;
const char     *remote_host = "192.168.2.184"; // ip or dns

/* === Global variables === */

/* @brief Use and even initialize WiFiClient class to create TCP connections */
WiFiClient client;

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

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


void tcp_loop()
{

}

/* === Local utility functions starting here === */
