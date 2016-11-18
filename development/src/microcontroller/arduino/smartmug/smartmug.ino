/*
 *  This is the main() sketch of the smartmug project.
 */

void setup()
{
  // Setup serial connection for debugging purpose.
  serial_setup();

  // Setup the wifi connection.
  wifi_setup();

  // Setup the TCP connection.
  tcp_setup();
}

void loop()
{

}
