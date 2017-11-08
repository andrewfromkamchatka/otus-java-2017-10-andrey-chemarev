package ru.chemarev.andrey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    public static Map<String, GCStatistics> parse(Path path) throws IOException {
        Map<String, GCStatistics> result = new HashMap<>();

        try (BufferedReader br = new BufferedReader( new FileReader( path.toFile() ) )) {
            Pattern pattern = Pattern.compile(OBSERVATION_PATTERN);

            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if ( ! matcher.matches() )
                    continue;

                String gcName = matcher.group(2);

                long duration = Long.valueOf( matcher.group(3) );
                long fromLastGc = Long.valueOf(matcher.group(7));

                GCStatistics statistics;

                if ( result.containsKey(gcName) ) {
                    statistics = result.get(gcName);
                } else {
                    String gcType = matcher.group(1);

                    if ("end of minor GC".equals(gcType))
                        gcType = "Young";
                    else if ("end of major GC".equals(gcType))
                        gcType = "Old";

                    statistics = new GCStatistics(gcName, gcType);
                    result.put(gcName, statistics);
                }

                statistics.addObservation(duration, fromLastGc);
            }
        }

        return result;
    }

    private static String OBSERVATION_PATTERN = ".+; observation; ([\\w\\s]+); ([\\w\\s]+); dur = (\\d+); counts = (\\d+); " +
            "summary = (\\d+); average/min = (\\d+); fromLastGc = (\\d+); endOfGc = (\\d+);";

}
