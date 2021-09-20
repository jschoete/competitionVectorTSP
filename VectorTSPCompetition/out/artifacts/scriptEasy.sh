#!/bin/bash

#JAVA=java
JAVA=/usr/lib/jvm/java-11-openjdk-11.0.12.0.7-0.el8_4.x86_64/bin/java

$JAVA -jar competitionInstances.jar
for ((n=5; n<=10; n++)); do
  for ((d=10; d<=100; d+=10)); do
    for ((i=0; i<100; i++)); do
      $JAVA -jar competitionNearestNeighbor.jar $n $d $i
    done
    for ((i=0; i<100; i++)); do
      $JAVA -jar competitionRandomInsertion.jar $n $d $i
    done
    for ((i=0; i<100; i++)); do
      $JAVA -jar competitionCheapestInsertion.jar $n $d $i
    done
    for ((i=0; i<100; i++)); do
      $JAVA -jar competitionFlip.jar $n $d $i
    done
    for ((i=0; i<100; i++)); do
      $JAVA -jar competitionBranchAndBound.jar $n $d $i
    done
  done
done

exit 0
