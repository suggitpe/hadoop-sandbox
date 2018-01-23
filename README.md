# Hadoop Sandbox
This module focuses on mapreduce on the most ridiculously simple implementation.  It contains:
 - mapper and reducer
 - unit test using mrunit
 - an executor that runs on the local machine to find the max temp by year

## How to run it
You need to set up a few things to enable the example Driver to run properly:
 - set the env variable `HADOOP_HOME=/usr/apps/hadoop`
 - add the following command line args `-conf conf/hadoop-local.xml input/ncdc/micro output`.  The data is already in the project so it will run.
 - run with the following command `hadoop org.suggs.sandbox.hadoop.mapreduce.MaxTemperatureDriver -conf conf/hadoop-local.xml \
                                   input/ncdc/micro output`
 