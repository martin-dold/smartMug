/*
 *  This is the wifi sketch of the smartmug project.
 */

#include <ESP8266WiFi.h>

/*! @brief SSID the smartmug shall connect to. */
const char* ssid     = "smartmug";
/*! @brief Password of the SSID the smartmug shall connect to. */
const char* password = "smartmug12345";


/* === Public API functions starting here === */

void wifi_setup()
{
  Serial.println("###");
  Serial.println("Starting wifi setup.");
  Serial.println("###");

  // Firstly scan the wifi to get a feeling for the smartmug environment
  wifi_scan();
}

void wifi_loop()
{

}



/* === Local utility functions starting here === */

/*!
 * @brief Scans for wifi networks and prints the scan results.
 */
void wifi_scan()
{
  bool ssidFound = false;
  int rssiFound = -100;

  // Set WiFi to station mode and disconnect from an AP if it was previously connected
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);

  Serial.println("");
  Serial.println("Wifi scan start");

  // WiFi.scanNetworks will return the number of networks found
  int n = WiFi.scanNetworks();
  Serial.println("scan done");
  if (n == 0)
  {
    Serial.println("no networks found");
  }
  else
  {
    Serial.print(n);
    Serial.println(" networks found");
    for (int i = 0; i < n; ++i)
    {
      // Print SSID and RSSI for each network found
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
  Serial.println("");

  if(ssidFound)
  {
    Serial.print("We found the SSID to connect to: ");
    Serial.print(ssid);
    Serial.print(" (RSSI: ");
    Serial.print(rssiFound);
    Serial.println(")");
  }
  else
  {
    Serial.print("ERROR: ");
    Serial.print(ssid);
    Serial.println("was not found! Stop here.");
    while(1);
  }

  Serial.println("");
}

