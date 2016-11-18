#!/bin/bash
red='\e[0;31m'
blue='\e[0;34m'
green='\e[0;32m'
clean='\e[0m' # No Color

# IP configs
REMOTE_PORT_DATA=8081
REMOTE_IP_ADDR=192.168.2.100

# ---- start of script ----

echo -e "${green}=============================${clean}"
echo -e "${green} SmartMug TCP test script    ${clean}"
echo -e "${green}=============================${clean}"
echo -e ""

echo -e "${green}Test config:${clean}"
echo -e "Local TCP listen port:       \t$LOCAL_PORT_DATA"
echo -e ""

echo -e "Start sending..."
echo -e "123" | nc.traditional -n -w 2 -v -v $REMOTE_IP_ADDR $REMOTE_PORT_DATA
