#!/bin/bash

idWorkshop=$1

dockerHub=$2


if [ $# -ne '2' ]; then
  echo "Execution ./script [ ID Workshop ] [ Dockerhub ]"
  exit 1;
fi

sed "s/demo/${1}/g" src/main/fabric8/deployment.yml -i 
sed "s/demo/${1}/g" src/main/fabric8/svc.yml -i 
sed "s/dockerhub/${2}/g" pom.xml -i


exit 0