/*! @file  led.ino
 *  @brief This is the LED sketch of the smartmug project.
 *
 *  @defgroup led LEDs
 *  @brief Functions LED handling.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup led
 *  @{
 */

/* === Global defines === */

/* === Global variables === */
#define LED_1_PIN_R  13
#define LED_1_PIN_G  12
#define LED_1_PIN_B  15

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

/*!
 * @brief Setup function of this ino sketch to setup HX711 for operation.
 */
void led_setup()
{
  Serial.println("\n###");
  Serial.println("Firmware setup complete. Entering main loop.");
  Serial.println("###");

  pinMode(LED_1_PIN_R,OUTPUT);
  pinMode(LED_1_PIN_G,OUTPUT);
  pinMode(LED_1_PIN_B,OUTPUT);
  return;
}

/*!
 * @brief Loop function of this ino sketch to run HX711.
 */
void led_loop()
{
  /*
   * Currently there is nothing to do here.
   */
  return;
}

void led_set(bool on)
{
  if(on)
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
  else
  {
    digitalWrite(LED_1_PIN_R, LOW);
    digitalWrite(LED_1_PIN_G, LOW);
    digitalWrite(LED_1_PIN_B, LOW);
  }
  return;
}

void led_setColor(int red, int green, int blue)
{
  analogWrite(LED_1_PIN_R, red);
  analogWrite(LED_1_PIN_G, green);
  analogWrite(LED_1_PIN_B, blue);
}

/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
