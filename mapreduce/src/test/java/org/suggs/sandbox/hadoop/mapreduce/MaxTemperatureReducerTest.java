package org.suggs.sandbox.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;
import org.suggs.sandbox.hadoop.mapreduce.MaxTemperatureReducer;

import java.io.IOException;
import java.util.Arrays;

public class MaxTemperatureReducerTest {

    @Test
    public void returnsMaximumIntegerFromValues() throws IOException {
        new ReduceDriver<Text, IntWritable, Text, IntWritable>()
                .withReducer(new MaxTemperatureReducer())
                .withInput(new Text("1950"), Arrays.asList(new IntWritable(10), new IntWritable(5)))
                .withOutput(new Text("1950"), new IntWritable(10))
                .runTest();
    }

}