#!/bin/bash

# --- DOCU ---
# This script internally runs the "pure" cloc script but wraps the output into format to be included into the doxygen documentation,
# i.e. it surrounds the cloc output with doxygen commands. 

MINPARAMS=1
INPUT_PATH=./
EXCLUDE_LIST_FILE=cloc-exclude-list-file.txt
DEF_LANG_FILE=./cloc-def-lang.txt

if [ $# -lt "$MINPARAMS" ]
then
  echo
  echo "This script requires at least $MINPARAMS command-line arguments!"
  exit 1
fi

if [ -n "$1" ]
then
 INPUT_PATH=$1
fi

if [ -n "$2" ]
then
 EXCLUDE_LIST_FILE=$2
fi

if [ -n "$3" ]
then
 DEF_LANG_FILE=$3
fi

echo ""

# give a doxygen heading
echo -e "# Project statistics"
echo -e ""

# tells doxygen to start a code section
echo -e "<pre>"
echo -e ""

echo -e "cloc is run from root of this git repository."
echo -e ""

./run-cloc.sh $INPUT_PATH $EXCLUDE_LIST_FILE $DEF_LANG_FILE

# tells doxygen to end a code section
echo -e "</pre>"
echo -e ""

echo -e "End of automated statistics."
