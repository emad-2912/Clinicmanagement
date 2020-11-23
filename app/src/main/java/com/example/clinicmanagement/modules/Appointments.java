package com.example.clinicmanagement.modules;

public class Appointments {

    private int patientId;
    private String dateTime;
    private String time;

    public Appointments(int patientId, String dateTime, String time) {
        this.time = time;
        this.patientId = patientId;
        this.dateTime = dateTime;
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
