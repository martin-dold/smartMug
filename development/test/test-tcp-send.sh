#!/bin/bash
red='\e[0;31m'
blue='\e[0;34m'
green='\e[0;32m'
clean='\e[0m' # No Color

# IP configs
REMOTE_PORT_DATA=8080
REMOTE_IP_ADDR=192.168.5.10
# script arguments/parameters
MINPARAMS=1

# ---- start of script ----

echo -e "${green}=============================${clean}"
echo -e "${green} SmartMug TCP test script    ${clean}"
echo -e "${green}=============================${clean}"
echo -e ""

if [ $# -lt "$MINPARAMS" ]
then
  echo
  echo "This script requires at least $MINPARAMS command-line arguments!"
  exit 1
fi

echo -e "${green}Test config:${clean}"
echo -e "Remote TCP address: $REMOTE_IP_ADDR"
echo -e "Remote TCP port: $REMOTE_PORT_DATA"
echo -e ""


# 0x00 - LED OFF
# 0x01 - LED Red
# 0x02 - LED Green
# 0x03 - LED Blue
# 0x04 - LED White

if [ -n "$1" ]; then
  echo -n "Start sending command: "

  if [ "$1" == "-o" ]; then
    echo "LED Off ..."
    echo -e '\x02\x01\x00\x0a' | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser

  elif [ "$1" == "-r" ]; then
    echo "LED RED ..."
    echo -e '\x02\x01\x01\x0a' | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser

  elif [ "$1" == "-g" ]; then
    echo "LED Green ..."
    echo -e '\x02\x01\x02\x0a' | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser

  elif [ "$1" == "-b" ]; then
    echo "LED Blue ..."
    echo -e '\x02\x01\x03\x0a' | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser

  elif [ "$1" == "-w" ]; then
    echo "LED White ..."
    echo -e '\x02\x01\x04\x0a' | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser

  elif [ "$1" == "-tare" ]; then
    echo "Scale tare ..."
    echo -e '\x03\x01\x00\x0a' | nc.traditional -n -q 0 $REMOTE_IP_ADDR $REMOTE_PORT_DATA

  else
    echo "... Unknown command! No data is sent to the smart mug!"
    exit 1
  fi
fi
