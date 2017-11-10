package ru.chemarev.andrey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GcLogger {

    public GcLogger(Path path) throws IOException {
        writer = new FileWriter(path.toFile(), true);
        buffer = new StringBuffer();
    }

    public void log(Statistics statistics) throws IOException {
        log.info("{}; {}; dur = {}; counts = {}; summary = {}; average/min = {}; endOfGc = {};",
                statistics.getType(),
                statistics.getName(),
                statistics.getLastInfo().getGcInfo().getDuration(),
                statistics.getCount(),
                statistics.getSummaryTime(),
                statistics.getAverageTimeInMinute(),
                statistics.getLastInfo().getGcInfo().getEndTime());

        buffer.append(statistics.getType());
        buffer.append(bufferSeparator);
        buffer.append(statistics.getName());
        buffer.append(bufferSeparator);
        buffer.append(statistics.getLastInfo().getGcInfo().getDuration());
        buffer.append(bufferSeparator);
        buffer.append(statistics.getFromLastGcTime());
        buffer.append(bufferSeparator);
        buffer.append(statistics.getLastInfo().getGcInfo().getEndTime());
        buffer.append(bufferSeparator);

        writer.write(buffer.toString());
        writer.write(System.getProperty("line.separator"));
        writer.flush();
        buffer.setLength(0);
    }

    public static Map<String, Statistics> parse(Path path) throws IOException {
        Map<String, Statistics> result = new HashMap<>();

        String linePatter = "([\\w\\s]+);([\\w\\s]+);(\\d+);(\\d+);(\\d+);";

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            Pattern pattern = Pattern.compile(linePatter);

            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if (!matcher.matches())
                    continue;

                String gcName = matcher.group(2);
                long duration = Long.valueOf(matcher.group(3));
                long fromLastGcTime = Long.valueOf(matcher.group(4));

                Statistics statistics;

                if (result.containsKey(gcName)) {
                    statistics = result.get(gcName);
                } else {
                    String gcType = matcher.group(1);
                    statistics = new Statistics(gcType, gcName);
                    result.put(gcName, statistics);
                }

                statistics.addMeasurement(duration, fromLastGcTime);
            }
        }

        return result;
    }

    public void close() throws IOException {
        writer.close();
    }

    private FileWriter writer;
    private StringBuffer buffer;
    private char bufferSeparator = ';';
    private static Logger log = LoggerFactory.getLogger(GcLogger.class);
}
