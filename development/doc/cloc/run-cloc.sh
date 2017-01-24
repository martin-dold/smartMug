#!/bin/bash

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


echo -e "Following directories are excluded from cloc count:\n"
cat $EXCLUDE_LIST_FILE
echo -e "\n"

# --- cloc ---
# param #1 cloc-def-lang.txt:          defines the languages to be included in the lines of code counting
# param #2 cloc-exclude-list-file.txt: defines the list of directories to be excluded from calculation (e.g. third party code)
# param #3 root dir:                   dir where cloc starts counting. This should be set to root of git repository.
cloc-1.64.exe --progress-rate=0 --quiet --force-lang-def=$DEF_LANG_FILE --exclude-list-file=$EXCLUDE_LIST_FILE $INPUT_PATH

