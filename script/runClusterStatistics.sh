#!/bin/bash

##### OPTIONS
# (required)  path to .clustering file
CLUSTER_FILE_DIRECTORY=$1
OVERALL_STAT_OUTPUT_FILE=$2
CLUSTER_STAT_OUTPUT_FILE=$3


##### VARIABLES
# the name to give to the LSF job (to be extended with additional info)
JOB_NAME="PRIDE-CLUSTER-STATISTICS"
# memory limit in MGb
MEMORY_LIMIT=10000


##### RUN it on the production LSF cluster #####
##### NOTE: you can change LSF group to modify the number of jobs can be run concurrently #####
bsub -M ${MEMORY_LIMIT} -R "rusage[mem=${MEMORY_LIMIT}]" -q production-rh6 -g /pride_cluster_loader -o rwang@ebi.ac.uk -J ${JOB_NAME} java -Xmx${MEMORY_LIMIT}m -jar ${project.build.finalName}.jar ${CLUSTER_FILE_DIRECTORY} ${OVERALL_STAT_OUTPUT_FILE} ${CLUSTER_STAT_OUTPUT_FILE}
