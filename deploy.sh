#!/bin/bash

./mvnw clean install

docker build -t pzsp03 .
docker tag pzsp03 jciarka/pzsp03:latest
docker push jciarka/pzsp03:latest

if [[ ! $1 -eq 0 ]] ; then
    docker tag pzsp03 "jciarka/pzsp03:$1"
    docker push "jciarka/pzsp03:$1"
fi
