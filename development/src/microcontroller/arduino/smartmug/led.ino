/*! @file  led.ino
 *  @brief This is the LED sketch of the smartmug project.
 *
 *  @defgroup led LEDs
 *  @brief Functions for LED handling.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup led
 *  @{
 */

/* === Global defines === */
/*! @brief Pin for RGB-LED color: RED. */
#define LED_1_PIN_R  13
/*! @brief Pin for RGB-LED color: Green. */
#define LED_1_PIN_G  5
/*! @brief Pin for RGB-LED color: Blue. */
#define LED_1_PIN_B  12

/*! @brief RGB-LED color code used within SmartMug protocol representing: LED OFF. */
#define LED_COLOR_OFF    0
/*! @brief RGB-LED color code used within SmartMug protocol representing color: Red. */
#define LED_COLOR_RED    1
/*! @brief RGB-LED color code used within SmartMug protocol representing color: Green. */
#define LED_COLOR_GREEN  2
/*! @brief RGB-LED color code used within SmartMug protocol representing color: Blue. */
#define LED_COLOR_BLUE   3
/*! @brief RGB-LED color code used within SmartMug protocol representing color: White. */
#define LED_COLOR_WHITE  4

/* === Global variables === */
/*! @brief Current color of RGB-LED. */
uint8_t currentColor;

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

/*!
 * @brief Setup function of this ino sketch to setup LEDs for operation.
 */
void led_setup()
{
  Serial.println("\n###");
  Serial.println("Starting LED setup.");
  Serial.println("###");

  pinMode(LED_1_PIN_R,OUTPUT);
  pinMode(LED_1_PIN_G,OUTPUT);
  pinMode(LED_1_PIN_B,OUTPUT);

  led_setOff();
  // Prepare default color RED first TCP connect()
  currentColor = LED_COLOR_RED;
  return;
}

/*!
 * @brief Loop function of this ino sketch to run LED module.
 */
void led_loop()
{
  /*
   * Currently there is nothing to do here.
   */
  return;
}

/*!
 * @brief Set RGB-LED to: Red.
 */
void led_setRed()
{
  digitalWrite(LED_1_PIN_R, LOW);
  digitalWrite(LED_1_PIN_G, HIGH);
  digitalWrite(LED_1_PIN_B, HIGH);
  currentColor = LED_COLOR_RED;
}

/*!
 * @brief Set RGB-LED to: Green.
 */
void led_setGreen()
{
  digitalWrite(LED_1_PIN_R, HIGH);
  digitalWrite(LED_1_PIN_G, LOW);
  digitalWrite(LED_1_PIN_B, HIGH);
  currentColor = LED_COLOR_GREEN;
}

/*!
 * @brief Set RGB-LED to: Blue.
 */
void led_setBlue()
{
  digitalWrite(LED_1_PIN_R, HIGH);
  digitalWrite(LED_1_PIN_G, HIGH);
  digitalWrite(LED_1_PIN_B, LOW);
  currentColor = LED_COLOR_BLUE;
}

/*!
 * @brief Set RGB-LED to: White.
 */
void led_setWhite()
{
  digitalWrite(LED_1_PIN_R, LOW);
  digitalWrite(LED_1_PIN_G, LOW);
  digitalWrite(LED_1_PIN_B, LOW);
  currentColor = LED_COLOR_WHITE;
}

/*!
 * @brief Set RGB-LED to: Off.
 */
void led_setOff()
{
  digitalWrite(LED_1_PIN_R, HIGH);
  digitalWrite(LED_1_PIN_G, HIGH);
  digitalWrite(LED_1_PIN_B, HIGH);
}

/*!
 * @brief Set RGB-LED to latest color value.
 */
void led_turnOnLastColor()
{
  switch(currentColor)
  {
    case LED_COLOR_RED:
      led_setRed();
      break;
    case LED_COLOR_GREEN:
      led_setGreen();
      break;
    case LED_COLOR_BLUE:
      led_setBlue();
      break;
    case LED_COLOR_WHITE:
      led_setWhite();
      break;
    default:
      break;
  }
}

/*! @brief Another function to set RGB-LED.
 *
 * @attention Do not use! Not working properly. 
 */
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
