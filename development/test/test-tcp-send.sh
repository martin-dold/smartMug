#!/bin/bash
red='\e[0;31m'
blue='\e[0;34m'
green='\e[0;32m'
clean='\e[0m' # No Color

# IP configs
REMOTE_PORT_DATA=8080
REMOTE_IP_ADDR=192.168.5.10

# ---- start of script ----

echo -e "${green}=============================${clean}"
echo -e "${green} SmartMug TCP test script    ${clean}"
echo -e "${green}=============================${clean}"
echo -e ""

echo -e "${green}Test config:${clean}"
echo -e "Local TCP listen port:       \t$LOCAL_PORT_DATA"
echo -e ""

echo -e "Start sending..."
# 0x00 - LED OFF
# 0x01 - LED Red
# 0x02 - LED Green
# 0x03 - LED Blue
# 0x04 - LED White
echo -e '\x02\x01\x01\x0a' | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser
