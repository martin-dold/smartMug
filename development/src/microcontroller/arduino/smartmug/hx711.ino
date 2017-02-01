/*! @file  hx711.ino
 *  @brief This is the HX711 sketch of the smartmug project.
 *
 *  @defgroup hx711 HX711 scale
 *  @brief Functions for weight scaling.
 *
 *  @addtogroup arduino
 *  @{
 *
 *  @addtogroup hx711
 *  @{
 */
#include "HX711.h"

/* === Global defines === */
/*! @brief Scale value obtained by calibration in the lab. (see hx711_weight_calibration_log.txt) */
#define HX711_SCALE   429.23f
#define PIN_DOUT    14
#define PIN_PD_SCK  16

#define MINIMUM_VALUE (-50)

/* === Global variables === */
/*! @brief Global instance of the HX711 scale. */
HX711 scale;

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

/*!
 * @brief Setup function of this ino sketch to setup HX711 for operation.
 */
void hx711_setup()
{
  Serial.println("\n###");
  Serial.println("Starting HX711 setup.");
  Serial.println("###");

  // parameter "gain" is ommited; the default value 128 is used by the library
  scale.begin(PIN_DOUT, PIN_PD_SCK);
  // this value was obtained by calibrating the scale with known weights
  scale.set_scale(HX711_SCALE);
  // reset the scale to 0
  scale.tare();
  // put the ADC in sleep mode
  scale.power_down();
}

/*!
 * @brief Loop function of this ino sketch to run HX711.
 */
void hx711_loop()
{
  /*
   * Currently there is nothing to do here.
   */
  return;
}

/*!
 * @brief Returns current weight measured as float
 */
float hx711_getWeight()
{
  float ret;

  scale.power_up();
  // ensure the scale is properly powered up.
  delay(1);

  /*
   * Get the average of 5 readings from the ADC minus tare weight,
   * divided by the SCALE parameter set with set_scale().
   */
  ret = scale.get_units(10);
  scale.power_down();

  if((ret < 0 ) && (ret > MINIMUM_VALUE))
  {
    ret = 0;
  }

  return ret;
}

/*!
 * @brief Returns current weight measured as int
 */
int hx711_getWeightAsInt()
{
  int ret = 0;
  float weight = 0.0;
  weight = hx711_getWeight();
  ret = (int)(weight+.5);
  return ret;
}

/* === Local utility functions starting here === */

/*!
 * @}
 * @}
 */
