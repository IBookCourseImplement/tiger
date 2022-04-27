#!/usr/bin/sh
for file in *.java
do
  java -cp ../bin Tiger "$file"
done