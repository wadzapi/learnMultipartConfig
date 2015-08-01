#!/bin/bash
TOMCAT_PATH="/home/jee/apache-tomcat-7.0.61"
WAR_NAME="testMultipartConfig.war"
BUILD_DIR="./target"

mvn clean install && \
cp $BUILD_DIR/$WAR_NAME $TOMCAT_PATH/webapps/$WAR_NAME
