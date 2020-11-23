package com.example.clinicmanagement.modules;

public class Patient_case {

    private int patient_id;
    private double length ;
    private double weight ;
    private String diagnosis ;
    private String complaint ;
    private String medicine ;

    public Patient_case(int patient_id, double length, double weight, String diagnosis, String complaint, String medicine) {
        this.patient_id = patient_id;
        this.length = length;
        this.weight = weight;
        this.diagnosis = diagnosis;
        this.complaint = complaint;
        this.medicine = medicine;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
