#!/bin/bash
#set -xv

#------------------------------
# functions
#------------------------------
function launch () {
    java -cp lib/*:lib/libjena/*:bin/: $@
}


#------------------------------
# main
#------------------------------
cd "$(dirname $0)"
make
if [ "$?" -eq 0 ]; then
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
        "tp6")
            launch "tp6.MainCt"
            ;;
        "tp7")
            launch "tp7.main.BeingsMain"
            ;;
        *)
            echo "Error: Unknown target"
            ;;
    esac
fi
