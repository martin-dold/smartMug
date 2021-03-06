#!/bin/bash
red='\e[0;31m'
blue='\e[0;34m'
green='\e[0;32m'
clean='\e[0m' # No Color

# IP configs
LOCAL_PORT_DATA=8080

# ---- start of script ----

echo -e "${green}=============================${clean}"
echo -e "${green} SmartMug TCP test script    ${clean}"
echo -e "${green}=============================${clean}"
echo -e ""

echo -e "${green}Test config:${clean}"
echo -e "Local TCP listen port:       \t$LOCAL_PORT_DATA"
echo -e ""

echo -e "Start listening..."
# start linstener for data port
nc -v -n -l -p $LOCAL_PORT_DATA
