package ru.chemarev.andrey;

import com.sun.management.GarbageCollectionNotificationInfo;

public class Statistics {

    public Statistics(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public void addMeasurement(GarbageCollectionNotificationInfo info) {
        count++;
        summaryTime += info.getGcInfo().getDuration();
        averageTime = summaryTime / count;
        averageTimeInMinute = summaryTime / (info.getGcInfo().getEndTime() / 60000 + 1);
        fromLastGcTime = info.getGcInfo().getEndTime() - fromLastGcTime;
        this.lastInfo = info;
    }

    public void addMeasurement(long duration, long fromLastGcTime) {
        count++;
        summaryTime += duration;
        averageTime = summaryTime / count;
        long vmTime = this.fromLastGcTime + fromLastGcTime;
        averageTimeInMinute = summaryTime / (vmTime  / 60000 + 1);
        this.fromLastGcTime = fromLastGcTime;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }

    public long getAverageTime() {
        return averageTime;
    }

    public long getAverageTimeInMinute() {
        return averageTimeInMinute;
    }

    public long getSummaryTime() {
        return summaryTime;
    }

    public long getFromLastGcTime() {
        return fromLastGcTime;
    }

    public GarbageCollectionNotificationInfo getLastInfo() {
        return lastInfo;
    }

    private String type;
    private String name;
    private long count = 0;
    private long summaryTime = 0;
    private long averageTime = 0;
    private long averageTimeInMinute = 0;
    private long fromLastGcTime = 0;
    private GarbageCollectionNotificationInfo lastInfo;
}
