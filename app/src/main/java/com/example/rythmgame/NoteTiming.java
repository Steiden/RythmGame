package com.example.rythmgame;

public class NoteTiming {
    private float[] coordinates;
    private long time;

    public NoteTiming(float[] coordinates, long time) {
        this.coordinates = coordinates;
        this.time = time;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public long getTime() {
        return time;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
