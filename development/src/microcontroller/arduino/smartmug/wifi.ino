/*
 *  This is the wifi sketch of the smartmug project.
 */

#include <ESP8266WiFi.h>

/* === Global defines === */
/*! @brief SSID the smartmug shall connect to. */
const char* ssid     = "smartmug";
/*! @brief Password of the SSID the smartmug shall connect to. */
const char* password = "smartmug12345";

/* === Global variables === */
/*! @brief Holds the own IP address (asigned by DHCP). */
IPAddress localIpAddr;
/*! @brief Holds the RSSI of the established wifi connection. */
long rssiEstablished;


/* === Public API functions starting here === */

void wifi_setup()
{
  Serial.println("###");
  Serial.println("Starting wifi setup.");
  Serial.println("###");

  // Firstly scan the wifi to get a feeling for the smartmug environment
  wifi_scan();

  // Secondly, connect to the requested smartmug SSID
  wifi_connect();

  // If we end up here, we are connected to wifi. Print status.
  wifi_printStatus();
}

void wifi_loop()
{

}



/* === Local utility functions starting here === */

/*!
 * @brief Scans for wifi networks, checks for @ref ssid and prints the scan results.
 */
void wifi_scan()
{
  bool ssidFound = false;
  long rssiFound = -100;

  // Set WiFi to station mode and disconnect from an AP if it was previously connected
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);

  Serial.println("WiFi scan");

  // WiFi.scanNetworks will return the number of networks found
  int n = WiFi.scanNetworks();
  Serial.print("  scan done. Found networks: ");
  Serial.println(n);
  if (n > 0)
  {
    for (int i = 0; i < n; ++i)
    {
      // Print SSID and RSSI for each network found
      Serial.print("  ");
      Serial.print(i + 1);
      Serial.print(": ");
      Serial.print(WiFi.SSID(i));
      Serial.print(" (");
      Serial.print(WiFi.RSSI(i));
      Serial.print(")");
      Serial.println((WiFi.encryptionType(i) == ENC_TYPE_NONE)?" ":"*");

      // check if we can see the ssid we should connect to
      if(WiFi.SSID(i) == ssid)
      {
        ssidFound = true;
        rssiFound = WiFi.RSSI(i);
      }

      delay(10);
    }
  }

  if(ssidFound)
  {
    Serial.print("  We found the SSID to connect to: ");
    Serial.print(ssid);
    Serial.print(" (RSSI: ");
    Serial.print(rssiFound);
    Serial.println(")");
  }
  else
  {
    Serial.print("  ERROR: ");
    Serial.print(ssid);
    Serial.println(" was not found!");
  }
}/* wifi_scan() */


/*!
 * @brief Connects to the requested SSID as defined in @ref ssid.
 */
void wifi_connect()
{
  Serial.println("");
  Serial.println("WiFi connect");
  Serial.print("  Connecting to SSID: ");
  Serial.println(ssid);
  Serial.print("  "); // just for nice debug print.

  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("  WiFi connected.");
}/* wifi_connect() */


/*!
 * @brief Prints the wifi status information for debugging purpose.
 */
void wifi_printStatus()
{
  Serial.println("");
  Serial.println("WiFi status");

  // Print network name.
  Serial.print("  SSID: ");
  Serial.println(WiFi.SSID());

  // Print WiFi shield IP address.
  localIpAddr = WiFi.localIP();
  Serial.print("  IP Address: ");
  Serial.println(localIpAddr);

  // Print the signal strength.
  rssiEstablished = WiFi.RSSI();
  Serial.print("  Signal strength (RSSI):");
  Serial.print(rssiEstablished);
  Serial.println(" dBm");
}

