package ru.chemarev.andrey;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.io.IOException;

public class GCNotificationListener implements NotificationListener {

    public GCNotificationListener(GcLogger gcLogger) {
        this.gcLogger = gcLogger;
    }

    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {

            GarbageCollectionNotificationInfo info =
                    GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            if ( statistics == null ) {
                String type;
                if (info.getGcAction().equals("end of minor GC"))
                    type = "Young";
                else if (info.getGcAction().equals("end of major GC"))
                    type = "Old";
                else
                    type = info.getGcAction();

                statistics = new Statistics(type, info.getGcName());
            }

            statistics.addMeasurement(info);

            try {
                gcLogger.log(statistics);
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }
    }

    private GcLogger gcLogger;
    private Statistics statistics;
    private Logger log = LoggerFactory.getLogger(GCNotificationListener.class);
}
