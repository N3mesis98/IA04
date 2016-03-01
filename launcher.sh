#!/bin/bash
#set -xv

#------------------------------
# functions
#------------------------------
function launch () {
	str="  Launching $1"
	line="----------------------------------------"
	echo -e "\n$line\n$str\n$line\n"
	#java -cp ../lib/jade.jar: $(echo %d | sed 's/.*src\///' | tr / .).%e
	java -cp ../lib/jade.jar: "$1"
}


#------------------------------
# main
#------------------------------
cd "$(dirname $0)/bin"

if [ $# -eq 1 ]; then
	target="$1"
else
	target="tp1"
fi

case $target in
	"tp1")
		launch "tp1.MainCt" &
		read -r
		launch "tp1.helloCt.HelloCt"
		;;
	"tp2")
		launch "tp2.MainCt" &
		read -r
		launch "tp2.factCt.FactCt" &
		launch "tp2.multCt.MultCt"
		;;
	*)
		echo "Error: Unknown target"
		;;
esac
sleep 1
