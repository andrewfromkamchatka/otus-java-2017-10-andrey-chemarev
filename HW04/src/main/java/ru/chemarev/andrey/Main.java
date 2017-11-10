package ru.chemarev.andrey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.NotificationEmitter;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 -Xms512m
 -Xmx512m
 */
public class Main {

    public static void main(String[] args) {
        log.info("HW04 started with pid = " + ManagementFactory.getRuntimeMXBean().getName());

        try {
            if (args.length == 1 && (args[0].equals("-s") || args[1].equals("--statistics")))
                printStatistics(gcLogPath);
            else if (args.length == 0) {
                GcLogger gcLogger = new GcLogger(gcLogPath);

                subscribeOnGCNotification(gcLogger);
                benchmark();

                gcLogger.close();
            } else
                log.error("Unknown arguments");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        log.info("Finished");
    }

    public static void printStatistics(Path gcLogPath) {
        try {
            Map<String, Statistics> statisticsMap = GcLogger.parse(gcLogPath);

            String template = "\t%-6s \t %-35s \t %5d calls \t %5d ms/min \t %5d ms/call%n";
            System.out.println("\n\t*** GC Statistics ***");

            for (Map.Entry<String, Statistics> entry : statisticsMap.entrySet()) {
                Statistics stat = entry.getValue();
                System.out.printf(template, stat.getType(), stat.getName(), stat.getCount(),
                        stat.getAverageTimeInMinute(), stat.getAverageTime());
            }

            System.out.println("\t*********************\n");
        } catch (IOException e) {
            log.error("Cannot read log file : {}", e.getMessage());
        }

    }

    public static void subscribeOnGCNotification(GcLogger gcLogger) throws IOException {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            GCNotificationListener notificationListener = new GCNotificationListener(gcLogger);
            emitter.addNotificationListener(notificationListener, null, null);
        }

    }

    public static void benchmark() {
        List<String> list = new ArrayList<>();
        int size = 5 * 1000 * 1000;

        while (true) {

            String[] array = new String[size];

            for (int i = 0; i < size; i++) {
                String temp = new String("");
                array[i] = temp;

                if ( i % 101 == 0 ) list.add(temp); // -XX:+UseSerialGC
//                if ( i % 503 == 0 ) list.add(temp); // -XX:+UseParallelGC -XX:+UseParallelOldGC
//                if ( i % 23 == 0 ) list.add(temp); // -XX:+UseG1GC
//                if ( i % 89 == 0 ) list.add(temp); // -XX:+UseParNewGC
//                if ( i % 59 == 0 ) list.add(temp); // -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
//                if ( i % 41 == 0 ) list.add(temp); // -XX:+UseConcMarkSweepGC -XX:-UseParNewGC

            }

        }
    }

    private static Path gcLogPath = Paths.get("gc-statistics.log");
    private static Logger log = LoggerFactory.getLogger(Main.class);
}
