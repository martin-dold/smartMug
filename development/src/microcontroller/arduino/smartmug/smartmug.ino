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
/*! @brief Defines if dummy data shall be sent or not. Set to '1' for sending dummy data. */
#define SEND_DUMMY_DATA       0

/* === Global variables === */

/*! @brief Timer for sending TCP data periodically. */
unsigned long tcpSendTimer;

#if SEND_DUMMY_DATA
/*! @brief Some dummy smartmug data to be send through TCP socket.
 *
 *  The dummy data follows the first version of the smartmug protocol by sending:
 *  TAG:   0x01 (Smartmug sensor data)
 *  LEN:   0x01 (One byte of sensor data)
 *  VALUE: 0xXX (Dummy sensor data from 10, 20, 30, ... 100)
 */
uint8_t tcpDummyData[] = {0x01, 0x01, 0x00};
#else
/*! @brief First weight sensor data package defined by the smartmug group.
 *
 *  The data follows the first version of the smartmug protocol by sending:
 *  TAG:   0x01 (Smartmug sensor data)
 *  LEN:   0x02 (Two bytes of sensor data)
 *  VALUE: two byte weight (in g) as unsigned int with MSB first
 *  EOF:   '\n' (0x0A) to allow easy data reading in Andriod app
 */
uint8_t tcpSensorData[] = {0x01, 0x02, 0x00, 0x00, 0x0A};
#endif

/*! Last weight that was measured. */
int currentWeight;

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

  // Setup the HX711 for weight scaling
  hx711_setup();
  currentWeight = 0;

  // Setup LEDs for operation
  led_setup();

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

  // Handle the HX711
  hx711_loop();

  // Send out TCP data periodically.
  if (millis() - tcpSendTimer > TCP_SEND_TIMEOUT_MS)
  {
    // Restart timer
    tcpSendTimer = millis();

    /* Read sensor data */
    currentWeight = hx711_getWeightAsInt();
    Serial.print("currentWeight: ");
    Serial.println(currentWeight);

    #if SEND_DUMMY_DATA
    tcp_send(tcpDummyData, sizeof(tcpDummyData));
    tcpDummyData[3] += 10;
    if(tcpDummyData[3] == 110)
    {
      tcpDummyData[3] = 0;
    }
    #else
    /* Set current weight (MSB first) */
    tcpSensorData[2] = highByte(currentWeight);
    tcpSensorData[3] = lowByte(currentWeight);
    tcp_send(tcpSensorData, sizeof(tcpSensorData));
    #endif
  }

}


/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
