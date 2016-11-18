

/*
 *  This is the ADC sketch of the smartmug project.
 */

ADC_MODE(ADC_TOUT);

/* === Global defines === */

/* === Global variables === */

/* === Local/private function prototypes === */

/* === Public API functions starting here === */

void adc_setup()
{

}


void adc_loop()
{
  float voltage = analogRead(A0);
  Serial.print("Voltage: ");
  Serial.println(voltage);
}

/* === Local utility functions starting here === */
