#!/bin/bash

##### OPTIONS
# (required)  path to output code book file
CODE_BOOK=$1


##### VARIABLES
# executable jar file
EXE_JAR=${project.build.finalName}.jar

##### Generate code book
java -cp ${EXE_JAR} uk.ac.ebi.pride.spectracluster.statistics.ClusterStatisticsCodeBookGenerator ${CODE_BOOK}