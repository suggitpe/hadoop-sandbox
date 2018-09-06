package org.suggs.sandbox.hadoop.mapreduce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Integer.parseInt;

public class NcdcRecordParser {
    private static final Logger log = LoggerFactory.getLogger(NcdcRecordParser.class);

    private static final int MISSING_TEMPERATURE = 9999;

    private String year;
    private int airTemp;
    private String quality;

    public void parse(String line) {
        year = line.substring(15, 19);
        String airTempString;
        if (line.charAt(87) == '+') {
            airTempString = line.substring(88, 92);
        } else {
            airTempString = line.substring(87, 92);
        }
        log.debug("airTemp: " + line.substring(87, 92));
        airTemp = parseInt(airTempString);
        quality = line.substring(92, 93);
    }

    public boolean isValidTemperature() {
        return airTemp != MISSING_TEMPERATURE && quality.matches("[01459]");
    }

    public String getYear() {
        return year;
    }

    public int getAirTemperature() {
        return airTemp;
    }
}
