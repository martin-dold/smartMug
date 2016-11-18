/*
 *  This is the wifi sketch of the smartmug project.
 */

#include <ESP8266WiFi.h>


/* === Public API functions starting here === */

void wifi_setup()
{
  Serial.println("Starting wifi setup.");

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
  // Set WiFi to station mode and disconnect from an AP if it was previously connected
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);

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
      delay(10);
    }
  }
  Serial.println("");
}

