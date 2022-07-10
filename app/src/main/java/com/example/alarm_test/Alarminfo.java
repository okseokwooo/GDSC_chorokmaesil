package com.example.alarm_test;

public class Alarminfo{
    int hour;
    int minute;
    String noon;
    String waker;
    String message;

    public Alarminfo(int hour, int minute, String noon, String waker, String message) {
        this.hour = hour;
        this.minute = minute;
        this.noon = noon;
        this.waker = waker;
        this.message = message;
    }
}