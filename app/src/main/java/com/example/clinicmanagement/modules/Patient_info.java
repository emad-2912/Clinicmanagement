package com.example.clinicmanagement.modules;

import java.io.Serializable;

public class Patient_info implements Serializable {
    private int patient_id;
    private String fullName;
    private String sex;
    private String birthDay;
    private String phonNo;
    private String address;

    public Patient_info() {

    }

    public Patient_info(int patient_id, String fullName, String sex, String birthDay, String phonNo, String address) {
        this.patient_id = patient_id;
        this.fullName = fullName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.phonNo = phonNo;
        this.address = address;
    }
    public Patient_info( String fullName, String sex, String birthDay, String phonNo, String address) {
        this.fullName = fullName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.phonNo = phonNo;
        this.address = address;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhonNo() {
        return phonNo;
    }

    public void setPhonNo(String phonNo) {
        this.phonNo = phonNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
