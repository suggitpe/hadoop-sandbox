package org.suggs.sandbox.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;
import org.suggs.sandbox.hadoop.mapreduce.MaxTemperatureMapper;

import java.io.IOException;

public class MaxTemperatureMapperTest {

    private static final Text completeRecord = new Text("0043011990999991950051518004+68750+023550FM-12+0382"
            + "99999V0203201N00261220001CN9999999N9-00111+99999999999");
    private static final Text incompleteRecord = new Text("0043011990999991950051518004+68750+023550FM-12+0382"
            + "99999V0203201N00261220001CN9999999N9+99991+99999999999");

    @Test
    public void processValidRecord() throws IOException {
        new MapDriver<LongWritable, Text, Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0), completeRecord)
                .withOutput(new Text("1950"), new IntWritable(-11))
                .runTest();
    }

    @Test
    public void ignoresMissingTemperatureRecord() throws IOException {
        new MapDriver<LongWritable, Text, Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0), incompleteRecord)
                .runTest();
    }
}
