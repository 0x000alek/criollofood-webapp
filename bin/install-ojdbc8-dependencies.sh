#!/bin/sh

mvn install:install-file -Dfile=<ojdbc8_directory_path>/ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=18.3.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=<ojdbc8_directory_path>/ucp.jar -DgroupId=com.oracle.jdbc -DartifactId=ucp -Dversion=18.3.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=<ojdbc8_directory_path>/osdt_core.jar -DgroupId=com.oracle.jdbc -DartifactId=osdt_core -Dversion=18.3.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=<ojdbc8_directory_path>/osdt_cert.jar -DgroupId=com.oracle.jdbc -DartifactId=osdt_cert -Dversion=18.3.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=<ojdbc8_directory_path>/oraclepki.jar -DgroupId=com.oracle.jdbc -DartifactId=oraclepki -Dversion=18.3.0.0 -Dpackaging=jar
