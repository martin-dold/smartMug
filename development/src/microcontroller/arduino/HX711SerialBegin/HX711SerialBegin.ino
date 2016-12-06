#include "HX711.h"

HX711 scale;
float result;

void setup() {
  Serial.begin(38400);
  Serial.println("HX711 Demo");

  Serial.println("Initializing the scale");
  // parameter "gain" is ommited; the default value 128 is used by the library
  // HX711.DOUT	- pin #A1
  // HX711.PD_SCK	- pin #A0
  scale.begin(14, 16);

  
  Serial.println("set_scale()");

  scale.set_scale();                      // this value is obtained by calibrating the scale with known weights; see the README for details
  scale.tare();				        // reset the scale to 0

  Serial.println("Place weight!");
  delay(5000);
  Serial.println("get_units(10)");
  result = scale.get_units(10);
  Serial.printf("get_units(10) result: ");
  Serial.println(result);
  result = result / 139;
  Serial.print("get_units(10) result/150: ");
  Serial.println(result);
  Serial.println("Remove weight!");
  delay(5000);

  Serial.print("set_scale() value = ");
  Serial.println(result);
  scale.set_scale(result);

  Serial.println("After setting up the scale:");

  Serial.print("read: \t\t");
  Serial.println(scale.read());                 // print a raw reading from the ADC

  Serial.print("read average: \t\t");
  Serial.println(scale.read_average(20));       // print the average of 20 readings from the ADC

  Serial.print("get value: \t\t");
  Serial.println(scale.get_value(5));		// print the average of 5 readings from the ADC minus the tare weight, set with tare()

  Serial.print("get units: \t\t");
  Serial.println(scale.get_units(5), 1);        // print the average of 5 readings from the ADC minus tare weight, divided
						// by the SCALE parameter set with set_scale

  Serial.println("Readings:");
}

void loop() {
  Serial.print("one reading:\t");
  Serial.print(scale.get_units(), 1);
  Serial.print("\t| average:\t");
  Serial.println(scale.get_units(10), 1);

  scale.power_down();			        // put the ADC in sleep mode
  delay(2000);
  scale.power_up();
}
