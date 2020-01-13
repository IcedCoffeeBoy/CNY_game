#!/bin/bash

# Update git repo and build
UP_TO_DATE=$(git pull | grep "Already up to date.")

if [ "$UP_TO_DATE" = "Already up to date." ]; then
  echo $UP_TO_DATE
else
  echo "pull and build"
  mvn clean package -DskipTests
fi

# Check for jar file and deploy jar file
FILE=./target/CNYBackend.jar
if [ -f "$FILE" ]; then
  echo "$FILE exist, deploying"
else
  echo "CNYBackend.jar not found, proceeding with build"
  mvn clean package -DskipTests
fi
