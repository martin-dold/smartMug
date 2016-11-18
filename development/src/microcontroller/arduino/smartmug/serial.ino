/*
 *  This is the serial sketch of the smartmug project.
 */

 #define BAUDRATE  115200

void serial_setup()
{
  Serial.begin(BAUDRATE);

  // Wait for serial port to connect. Needed for Leonardo only
  while (!Serial) { ; }

  Serial.println("");
  Serial.println("Serial connection ready.");
  Serial.println("");
}
