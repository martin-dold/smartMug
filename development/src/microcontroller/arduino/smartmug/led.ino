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
#define LED_1_PIN_R   5
#define LED_1_PIN_G  12
#define LED_1_PIN_B  13

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

/*!
 * @brief Setup function of this ino sketch to setup HX711 for operation.
 */
void led_setup()
{
  Serial.println("\n###");
  Serial.println("Starting LED setup.");
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

void led_setRed(bool on)
{
  if(on)
  {
    digitalWrite(LED_1_PIN_R, LOW);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
  else
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
}
void led_setGreen(bool on)
{
  if(on)
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, LOW);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
  else
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
}
void led_setBlue(bool on)
{
  if(on)
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, LOW);
  }
  else
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
}

void led_set(bool on)
{
  if(on)
  {
    digitalWrite(LED_1_PIN_R, LOW);
    digitalWrite(LED_1_PIN_G, LOW);
    digitalWrite(LED_1_PIN_B, LOW);
  }
  else
  {
    digitalWrite(LED_1_PIN_R, HIGH);
    digitalWrite(LED_1_PIN_G, HIGH);
    digitalWrite(LED_1_PIN_B, HIGH);
  }
  return;
}

void led_setColor(int red, int green, int blue)
{
  analogWrite(LED_1_PIN_R, 255 - red);
  analogWrite(LED_1_PIN_G, 255 - green);
  analogWrite(LED_1_PIN_B, 255 - blue);
}

/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
