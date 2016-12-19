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
echo -e "${green} SmartMug TCP test script showing sensor data   ${clean}"
echo -e "${green}=============================${clean}"
echo -e ""

echo -e "${green}Test config:${clean}"
echo -e "Remote TCP port: $REMOTE_PORT_DATA"
echo -e "Remote TCP port: $REMOTE_IP_ADDR"
echo -e ""

echo -e "Connecting to remote..."
nc -n -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA | ./protocol-parser/parser
