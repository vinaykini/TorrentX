#!/bin/sh

while read line 
do
  stringarray=($line)
  #echo "Press Enter to start a new Process"
  #read score
  #echo ${stringarray[1]}
  gnome-terminal -e "ssh kphuyal@${stringarray[1]} cd /cise/homes/kphuyal/Documents/test; java PeerProcess ${stringarray[0]}"

done < PeerInfo.cfg



#echo "Press Enter to start a new Process"
#read score
#gnome-terminal -e "ssh kphuyal@lin114-01.cise.ufl.edu cd /cise/homes/kphuyal/Documents/test; java PeerProcess 1001"
#echo "Press Enter to start a new Process"
#read score
#gnome-terminal -e "ssh kphuyal@lin114-02.cise.ufl.edu cd /cise/homes/kphuyal/Documents/test; java PeerProcess 1002"
#echo "Press Enter to start a new Process"
#read score
#gnome-terminal -e "ssh kphuyal@lin114-03.cise.ufl.edu cd /cise/homes/kphuyal/Documents/test; java PeerProcess 1003"
#echo "Press Enter to start a new Process"
#read score
#gnome-terminal -e "ssh kphuyal@lin114-04.cise.ufl.edu cd /cise/homes/kphuyal/Documents/test; java PeerProcess 1004"
#echo "Press Enter to start a new Process"
#read score
#gnome-terminal -e "ssh kphuyal@lin114-05.cise.ufl.edu cd /cise/homes/kphuyal/Documents/test; java PeerProcess 1005"

