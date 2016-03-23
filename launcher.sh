#!/bin/bash
#set -xv

#------------------------------
# functions
#------------------------------
function launch () {
    java -cp ../lib/*: $@
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
        "tp4")
            launch "tp4.MainCt" &
            read -r
            launch "tp4.analyseCt.AnalyseCt" 27
            ;;
        *)
            echo "Error: Unknown target"
            ;;
    esac
fi
