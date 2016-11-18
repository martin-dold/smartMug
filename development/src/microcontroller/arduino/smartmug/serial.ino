/*! @file  serial.ino
 *  @brief This is the serial sketch of the smartmug project.
 *
 *  @defgroup serial Serial
 *  @brief Adds serial UART functionality to the smartmug.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup serial
 *  @{
 */

 /*! @brief Defines serial (UART) baudrate. */
 #define BAUDRATE  115200

/*!
 * @brief Setup function of this ino sketch to setup serial port for operation.
 */
void serial_setup()
{
  Serial.begin(BAUDRATE);

  // Wait for serial port to connect. Needed for Leonardo only
  while (!Serial) { ; }

  Serial.println("");
  Serial.println("Serial connection ready.");
  Serial.println("");
}

/*!
 * @}
 * @}
 */
