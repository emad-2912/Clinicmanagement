package com.example.clinicmanagement.modules;

import java.io.Serializable;

public class Appoint implements Serializable {

    private int patientId;
    private String dateTime;
    private String time;
    private String name;


    public Appoint(int patientId, String dateTime, String time) {
        this.time = time;
        this.patientId = patientId;
        this.dateTime = dateTime;
    }

    public Appoint(int patientId, String dateTime, String time, String name) {
        this.time = time;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
