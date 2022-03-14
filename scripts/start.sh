#!/bin/bash

rm /home/ec2-user/deploy.log

BUILD_JAR=$(ls /home/ec2-user/build/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "## Target jar file: $JAR_NAME" >> /home/ec2-user/deploy.log

DEPLOY_PATH=/home/ec2-user/
cp $BUILD_JAR $DEPLOY_PATH

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "## There is no running application" >> /home/ec2-user/deploy.log
else
  echo "## kill -15 $CURRENT_PID" >> /home/ec2-user/deploy.log
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "## Start $DEPLOY_PATH$JAR_NAME" >> /home/ec2-user/deploy.log
nohup java -jar -Dspring.profiles.active=release $DEPLOY_PATH$JAR_NAME > /dev/null 2>&1 &
