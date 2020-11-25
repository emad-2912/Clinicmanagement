package com.example.clinicmanagement.databases;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class My_DataBase extends SQLiteAssetHelper {
    public final static int DB_VERSION = 10;
    public final static String DB_NAME = "patient2.db";

    public final static String TB_APPOINTMENST = "appointments";

    public final static String APPOINTMENST_CLN1_Forg_APPO_ID = "patientId";
    public final static String APPOINTMENST_CLN2_DATA_TIME = "dateTime";
    public final static String APPOINTMENST_CLN3_TIME = "time";



    public final static String TB_PATIENT_CASE = "patient_case";

    public final static String CASE_CLN1_patient_id = "patient_id";
    public final static String CASE_CLN2_length = "length";
    public final static String CASE_CLN3_weight = "weight";
    public final static String CASE_CLN4_diagnosis = "diagnosis";
    public final static String CASE_CLN5_complaint = "complaint";
    public final static String CASE_CLN6_medicine = "medicine";

    public final static String TB_PATIENT_INFO="patient_info";

    public final static String INFO_CLN1_fullName="fullName";
    public final static String INFO_CLN2_sex="sex";
    public final static String INFO_CLN3_birthDay="birthDay";
    public final static String INFO_CLN4_phonNo="phonNo";
    public final static String INFO_CLN5_address="address";
    public final static String INFO_CLN6_patient_id="patient_id";





    public My_DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

}
