#!/bin/bash
TOMCAT_PATH="/home/jee/apache-tomcat-7.0.61"
WAR_NAME="testMultipartConfig.war"
SRC_DIR="./../src/main/resources/tomcatConfigs/"

cp $TOMCAT_PATH/conf/server.xml $TOMCAT_PATH/conf/server.xml.bk &&
cp $SRC_DIR/server.xml $TOMCAT_PATH/conf/server.xml