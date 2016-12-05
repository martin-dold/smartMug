/*! @file  wifi.ino
 *  @brief This is the wifi sketch of the smartmug project.
 *
 *  @defgroup wifi WiFi
 *  @brief Adds WiFi functionality to the smartmug.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup wifi
 *  @{
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

/* === Local/private function prototypes === */
void wifi_scan();
void wifi_connect();
void wifi_printStatus();
void wifi_eventHandler(WiFiEvent_t event);

/* === Public API functions starting here === */

/*!
 * @brief Setup function of this ino sketch to setup WiFi for operation.
 */
void wifi_setup()
{
  Serial.println("###");
  Serial.println("Starting wifi setup.");
  Serial.println("###");

  // Firstly scan the wifi to get a feeling for the smartmug environment.
  wifi_scan();

  // Secondly, connect to the requested smartmug SSID.
  wifi_connect();

  // If we end up here, we are connected to wifi. Print status.
  wifi_printStatus();

  // Finally, register event handler function to monitor wifi status.
  WiFi.onEvent(wifi_eventHandler);
}

/*!
 * @brief Loop function of this ino sketch to run WiFi connection.
 */
void wifi_loop()
{
  /*
   * Currently there is nothing to here. The WiFi lib even performs the
   * reconnect to the SSID in case it was lost. Check wifi_eventHandler().
   */
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
    Serial.println(" was not found! Rebooting...");
    delay(3000);
    ESP.restart();
  }
}/* wifi_scan() */


/*!
 * @brief Connects to the requested SSID as defined in @ref ssid.
 */
void wifi_connect()
{
  int connectionTicks = 0; // counts 500ms ticks
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
    connectionTicks++;
    Serial.print(".");
  }

  Serial.println("");
  Serial.printf("  WiFi connected after %d second(s).\n", (connectionTicks/2));
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

/*!
 * @brief Event handler that is called once a WiFi event occurs.
 */
void wifi_eventHandler(WiFiEvent_t event)
{
  Serial.print("wifi_eventHandler(): ");

  switch(event)
  {
    case WIFI_EVENT_STAMODE_CONNECTED:
        Serial.println("connected.");
        break;
    case WIFI_EVENT_STAMODE_DISCONNECTED:
        Serial.println("lost connection.");
        break;
    case WIFI_EVENT_STAMODE_AUTHMODE_CHANGE:
        Serial.println("authentication mode changed.");
        break;
    case WIFI_EVENT_STAMODE_GOT_IP:
        localIpAddr = WiFi.localIP();
        Serial.print("got IP address ");
        Serial.println(localIpAddr);
        tcp_close();
        break;
    case WIFI_EVENT_STAMODE_DHCP_TIMEOUT:
        Serial.println("DHCP timeout.");
        break;
    default:
        Serial.printf("default case, event id: %d\n", event);
        break;
  }
}

/*!
 * @}
 * @}
 */

