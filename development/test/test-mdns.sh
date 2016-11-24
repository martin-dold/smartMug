#!/bin/bash
red='\e[0;31m'
blue='\e[0;34m'
green='\e[0;32m'
clean='\e[0m' # No Color

# IP configs
REMOTE_PORT_DATA=
REMOTE_IP_ADDR=
LOG=log.txt

# ---- start of script ----

function find_smartmug
{

	timeout 2s avahi-browse -a -p -r > $LOG 2>/dev/null

	array=(`cat $LOG | grep "^=" | grep _smartmug._ | tr ";" "\n"`)

	REMOTE_IP_ADDR=${array[7]}
	REMOTE_PORT_DATA=${array[8]}

	echo "Smartmug found at: $REMOTE_IP_ADDR, $REMOTE_PORT_DATA"
}

echo -e "${green}=============================${clean}"
echo -e "${green} SmartMug mDNS test script    ${clean}"
echo -e "${green}=============================${clean}"
echo -e ""

echo -e "Start finding smartmugs..."
for i in `seq 1 10`;
do
    find_smartmug
done

rm $LOG



