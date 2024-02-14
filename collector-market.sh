 #!/bin/bash

 echo "============================"
 echo "PROJECT JAR FILE BUILD START"
 echo "============================"
 ./gradlew clean build -x test

 echo "============================"
 echo "docker-compose.yml EXECUTE"
 echo "DOCKER BUILD START"
 echo "============================"
 docker-compose up --build

 echo "======================"
 echo "DOCKER-BUILD-COMPLETE"
 echo "======================"