/*! @file  debug.ino
 *  @brief This is the debug sketch of the smartmug project.
 *
 *  @defgroup debug Debug Prints
 *  @brief Debug print macros provided for smartmug firmware development.
 *  @attention Debugging macros are available only if @ref DEBUG is enabled.
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

  /*! @brief Prints debugging data to the serial port. See @ref DEBUG. */
  #define DEBUG_PRINT(...)    Serial.print(__VA_ARGS__)
  /*! @brief Prints debugging data to the serial port followed by carriage return and newline characters. See @ref DEBUG. */
  #define DEBUG_PRINTLN(...)  Serial.println(__VA_ARGS__)
  /*! @brief Prints debugging data to the serial port. See @ref DEBUG. */
  #define DEBUG_PRINTF(...)   Serial.printf(__VA_ARGS__)
#else
  /* If disabled, skip debug prints at all. */

  /*! @brief Prints debugging data to the serial port. See @ref DEBUG. */
  #define DEBUG_PRINT(...)
  /*! @brief Prints debugging data to the serial port followed by carriage return and newline characters. See @ref DEBUG. */
  #define DEBUG_PRINTLN(...)
  /*! @brief Prints debugging data to the serial port. See @ref DEBUG. */
  #define DEBUG_PRINTF(...)
#endif

/*!
 * @}
 * @}
 */
