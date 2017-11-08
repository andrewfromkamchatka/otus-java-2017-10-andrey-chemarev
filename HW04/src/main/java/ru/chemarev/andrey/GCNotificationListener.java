package ru.chemarev.andrey;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

public class GCNotificationListener implements NotificationListener {

    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {

            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            summaryTime += info.getGcInfo().getDuration();
            collectionCount++;

            log.info("observation; {}; {}; dur = {}; counts = {}; summary = {}; average/min = {}; fromLastGc = {}; endOfGc = {};",
                    info.getGcAction(),
                    info.getGcName(),
                    info.getGcInfo().getDuration(),
                    collectionCount,
                    summaryTime,
                    GCStatistics.calcAverageTimeImMinute(collectionCount, summaryTime, info.getGcInfo().getEndTime()),
                    info.getGcInfo().getEndTime() - lastGcEndTime,
                    info.getGcInfo().getEndTime());

            lastGcEndTime = info.getGcInfo().getEndTime();
        }
    }

    private long summaryTime = 0;
    private long collectionCount = 0;
    private long lastGcEndTime = 0;
    private static Logger log = LoggerFactory.getLogger(GCNotificationListener.class);
}
