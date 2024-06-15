#!/bin/bash
kill -9 $(lsof -t -i :8080)
java -jar /tmp/LHP-SE-April-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &