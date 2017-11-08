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

 -XX:+UseSerialGC
 -XX:+UseParallelGC
 -XX:+UseParNewGC

 -XX:+UseParallelOldGC
 -XX:+UseConcMarkSweepGC
 -XX:+UseG1GC
 */
public class Main {

    public static void main(String[] args) {
        log.info("HW04 started with pid = " + ManagementFactory.getRuntimeMXBean().getName());

        printStatistics();
        subscribeOnGCNotification();
        benchmark();

        log.info("Finished");
    }

    public static void printStatistics() {
        try {
            Path logFilePath = Paths.get("hw04.log");
            Map<String, GCStatistics> statisticsMap = LogParser.parse(logFilePath);

            String template = "\t%-5s \t %-25s \t %5d calls \t %5d ms/min \t %5d ms/call%n";
            System.out.println("\n\t*** GC Statistics ***");
            for (Map.Entry<String, GCStatistics> entry : statisticsMap.entrySet()) {
                GCStatistics stat = entry.getValue();
                System.out.printf(template, stat.getType(), stat.getName(), stat.getCount(),
                        stat.getAverageTimeInMinute(), stat.getAverageTime());
            }
            System.out.println("\t*********************\n");
        } catch (IOException e) {
            log.error("Cannot read log file : {}", e.getMessage());
        }

    }

    public static void subscribeOnGCNotification() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            GCNotificationListener notificationListener = new GCNotificationListener();
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

                if ( i % 31 == 0 ) list.add(temp);
            }

        }
    }

    private static Logger log = LoggerFactory.getLogger(Main.class);
}
