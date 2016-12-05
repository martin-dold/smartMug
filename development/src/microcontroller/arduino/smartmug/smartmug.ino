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

/*! @brief Some dummy smartmug data to be send through TCP socket.
 *
 *  The dummy data follows the first version of the smartmug protocol by sending:
 *  TAG:   0x01 (Smartmug sensor data)
 *  LEN:   0x01 (One byte of sensor data)
 *  VALUE: 0xXX (Dummy sensor data from 10, 20, 30, ... 100)
 */
uint8_t tcpDummyData[] = {0x01, 0x01, 0x00};


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

  // Setup over-the-air update
  ota_setup();

  // Setup multicast DNS
  mdns_setup();

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

  // Handle over-the-air update
  ota_loop();

  // Handle multicast DNS
  mdns_loop();

  // Handle TCP connection.
  tcp_loop();

  // Send out TCP data periodically.
  if (millis() - tcpSendTimer > TCP_SEND_TIMEOUT_MS)
  {
    // Restart timer
    tcpSendTimer = millis();

    // For now just send some dummy data.
    tcp_send(tcpDummyData, sizeof(tcpDummyData));
    tcpDummyData[2] += 10;
    if(tcpDummyData[2] == 110)
    {
      tcpDummyData[2] = 0;
    }
  }

}


/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
