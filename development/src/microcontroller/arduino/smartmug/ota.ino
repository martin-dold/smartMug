/*
 *  This is the Over-The-Air-Update sketch of the smartmug project.
 */
#include <WiFiUdp.h>
#include <ArduinoOTA.h>

/* === Global defines === */
/*! @brief Port used for OTA. Default is 8266. */
#define OTA_PORT       8266
/*! @brief Host name used for OTA. Default is esp8266-[ChipID] */
#define OTA_HOSTNAME   "smartmug-ota"

/* === Global variables === */

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

void ota_setup()
{
  #ifdef OTA_PORT
  ArduinoOTA.setPort(OTA_PORT);
  #endif

  #ifdef OTA_HOSTNAME
  /* TODO:
   * fix me. We need a unique smartmug ID, either by concatenation
   * with chipID or by own increasing counter.
   */
  //Serial.printf(" ESP8266 Chip id = %08X\n", ESP.getChipId());
  //String hostname = OTA_HOSTNAME + ESP.getChipId();
  //ArduinoOTA.setHostname(hostname.c_str());
  ArduinoOTA.setHostname(OTA_HOSTNAME);
  #endif

  #ifdef OTA_PASSWORD
  // No authentication by default
  // ArduinoOTA.setPassword("admin");
  // Password can be set with it's md5 value as well
  // MD5(admin) = 21232f297a57a5a743894a0e4a801fc3
  // ArduinoOTA.setPasswordHash("21232f297a57a5a743894a0e4a801fc3");
  #endif

  /* ON START */
  ArduinoOTA.onStart([]()
  {
    Serial.println("Start updating");
  });

  /* ON END */
  ArduinoOTA.onEnd([]()
  {
    Serial.println("\nEnd");
  });

  /* ON PROGRESS */
  ArduinoOTA.onProgress([](unsigned int progress, unsigned int total)
  {
    Serial.printf("Progress: %u%%\r", (progress / (total / 100)));
  });

  /* ON ERROR */
  ArduinoOTA.onError([](ota_error_t error)
  {
    Serial.printf("Error[%u]: ", error);
    if (error == OTA_AUTH_ERROR) Serial.println("Auth Failed");
    else if (error == OTA_BEGIN_ERROR) Serial.println("Begin Failed");
    else if (error == OTA_CONNECT_ERROR) Serial.println("Connect Failed");
    else if (error == OTA_RECEIVE_ERROR) Serial.println("Receive Failed");
    else if (error == OTA_END_ERROR) Serial.println("End Failed");
  });

  /* Finally, start the OTA service. */
  ArduinoOTA.begin();

}


void ota_loop()
{
  /* Run the OTA service. */
  ArduinoOTA.handle();
}

/* === Local utility functions starting here === */
