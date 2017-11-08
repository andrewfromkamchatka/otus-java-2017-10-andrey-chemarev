package ru.chemarev.andrey;

public class GCStatistics {

    public GCStatistics(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void addObservation(long duration, long fromLastGcTime) {
        count++;
        this.duration += duration;
        vmLifeTime += fromLastGcTime;
    }

    public long getAverageTime() {
        return duration / count;
    }

    public long getAverageTimeInMinute() {
        return calcAverageTimeImMinute(count, duration, vmLifeTime);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getCount() {
        return count;
    }

    public long getDuration() {
        return duration;
    }

    public long getVmLifeTime() {
        return vmLifeTime;
    }

    public static long calcAverageTimeImMinute(long count, long gcTime, long vmTime) {
        return gcTime / (count * vmTime / 60000 + 1 );
    }

    private String name;
    private String type;
    private long count = 0;
    private long duration = 0;
    private long vmLifeTime = 0;
}
