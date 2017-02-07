/*
 *  This is the multicast DNS sketch of the smartmug project.
 */
#include <ESP8266mDNS.h>

/* === Global defines === */
/*! @brief Service name of the smartmug service. */
#define MDNS_SERVICE_NAME   "smartmug"
/*! @brief Service type (prefix) of the smartmug service.
 *
 * This is combined with @ref MDNS_SERVICE_PROTO.
 */
#define MDNS_SERVICE_TYPE   "smartmug"
/*! @brief Protocol used for the smartmug service. */
#define MDNS_SERVICE_PROTO  "tcp"
/*! @brief Port that offers the service. Keep this define in sync with @ref local_port in tcp.ino! */
#define MDNS_SERVICE_PORT   8080

/* === Global variables === */

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

void mdns_setup()
{
  Serial.println("\n###");
  Serial.println("Starting mDNS setup.");
  Serial.println("###");

  // Set up mDNS responder:
  // - first argument is the domain name, in this example
  //   the fully-qualified domain name is "esp8266.local"
  // - second argument is the IP address to advertise
  //   we send our IP address on the WiFi network
  if (!MDNS.begin(MDNS_SERVICE_NAME)) {
    Serial.println("Error setting up MDNS responder!");
    while(1) {
      delay(1000);
    }
  }
  Serial.println("  mDNS responder started");

  // Add service to MDNS-SD
  MDNS.addService(MDNS_SERVICE_TYPE, MDNS_SERVICE_PROTO, MDNS_SERVICE_PORT);

  Serial.printf("  mDNS provide service: name=%s, type=_%s._%s, port=%d\n", MDNS_SERVICE_NAME, MDNS_SERVICE_TYPE, MDNS_SERVICE_PROTO, MDNS_SERVICE_PORT);
}


void mdns_loop()
{
  /*
   * Currently there is nothing to do here.
   */
  return;
}

/* === Local utility functions starting here === */
