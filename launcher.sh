#!/bin/bash
#set -xv

#------------------------------
# functions
#------------------------------
function launch () {
	str="  Launching $1"
	line="----------------------------------------"
	echo -e "\n$line\n$str\n$line\n"
	java -cp ../lib/*: "$1"
}


#------------------------------
# main
#------------------------------
cd "$(dirname $0)"
make
if [ "$?" -eq 0 ]; then
	cd "$(dirname $0)/bin"
	case $1 in
		"tp1")
			launch "tp1.MainCt" &
			read -r
			launch "tp1.helloCt.HelloCt"
			;;
		"tp2")
			launch "tp2.MainCt" &
			read -r
			launch "tp2.multCt.MultCt" &
			launch "tp2.factCt.FactCt"
			;;
		"tp3")
			launch "tp3.MainCt"
			;;
		*)
			echo "Error: Unknown target"
			;;
	esac
fi
