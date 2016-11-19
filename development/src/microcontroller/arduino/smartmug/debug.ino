/*! @file  debug.ino
 *  @brief This is the debug sketch of the smartmug project.
 *
 *  @defgroup debug Debug Prints
 *  @brief Debug print macros provided for smartmug firmware development.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup debug
 *  @{
 */

/* === Global defines === */

#ifdef DEBUG
  /* If enabled, redirect debug prints to serial/UART. */

  /*! @brief Prints debugging data to the serial port. */
  #define DEBUG_PRINT(...)    Serial.print(__VA_ARGS__)
  /*! @brief Prints debugging data to the serial port followed by carriage return and newline characters. */
  #define DEBUG_PRINTLN(...)  Serial.println(__VA_ARGS__)
  /*! @brief Prints debugging data to the serial port. */
  #define DEBUG_PRINTF(...)   Serial.printf(__VA_ARGS__)
#else
  /* If disabled, skip debug prints at all. */
  #define DEBUG_PRINT(...)
  #define DEBUG_PRINTLN(...)
  #define DEBUG_PRINTF(...)
#endif

/*!
 * @}
 * @}
 */
