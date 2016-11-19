/*! @file  smartmug.ino
 *  @brief This is the main() sketch of the smartmug project.
 *
 *  @defgroup arduino Arduino Code
 *  @brief Arduino code of the smartmug firmware.
 *
 *  @defgroup main SmartMug Main
 *  @brief Main entry of the smartmug firmware.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup main
 *  @{
 */


/* === Global defines === */

/*! @brief Enables/disables debug prints. Debug is enabled if this macro is defined. */
#define DEBUG

/*! @brief Timeout that defines the period (in milliseconds) when new TCP data is sent. */
#define TCP_SEND_TIMEOUT_MS   1000

/* === Global variables === */

/*! @brief Timer for sending TCP data periodically. */
unsigned long tcpSendTimer;

/*! @brief Some dummy data to be send through TCP socket. In ASCII this is 0 ... 5. */
uint8_t tcpDummyData[] = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35};


/* === Local/private function prototypes === */

/*!
 * @brief Main setup function of this ino firmware.
 */
void setup()
{
  // Setup serial connection for debugging purpose.
  serial_setup();

  // Setup the wifi connection.
  wifi_setup();

  // Setup the TCP connection.
  tcp_setup();

  // Start a timer for sending TCP data periodically.
  tcpSendTimer = millis();

  Serial.println("\n###");
  Serial.println("Firmware setup complete. Entering main loop.");
  Serial.println("###");
}

/*!
 * @brief Main loop function of this ino firmware.
 */
void loop()
{
  // Handle WiFi connection.
  wifi_loop();

  // Handle TCP connection.
  tcp_loop();

  // Send out TCP data periodically.
  if (millis() - tcpSendTimer > TCP_SEND_TIMEOUT_MS)
  {
    // Restart timer
    tcpSendTimer = millis();

    // For now just send some dummy data.
    tcp_send(tcpDummyData, sizeof(tcpDummyData));
  }

}


/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
