package com.example.clinicmanagement.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clinicmanagement.modules.Appointments;
import com.example.clinicmanagement.modules.Patient_case;
import com.example.clinicmanagement.modules.Patient_info;

import java.util.ArrayList;

public class Access_DateBase {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static Access_DateBase Instance;

    private Access_DateBase(Context context) {
        this.sqLiteOpenHelper = new My_DataBase(context);
    }

    public static Access_DateBase getInstance(Context context) {
        if (Instance == null)
            Instance = new Access_DateBase(context);

        return Instance;
    }


    public void open() {
        this.sqLiteDatabase = this.sqLiteOpenHelper.getWritableDatabase();


    }

    public void close() {
        if (this.sqLiteDatabase != null)
            this.sqLiteDatabase.close();


    }

    public long itmes_count() {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, My_DataBase.TB_PATIENT_INFO);


    }

    public boolean insertPatient(Patient_info patient_info, Patient_case patient_case) {
        ContentValues contentValues_info = new ContentValues();

        contentValues_info.put(My_DataBase.INFO_CLN1_fullName, patient_info.getFullName());
        contentValues_info.put(My_DataBase.INFO_CLN2_sex, patient_info.getSex());
        contentValues_info.put(My_DataBase.INFO_CLN3_birthDay, patient_info.getBirthDay());
        contentValues_info.put(My_DataBase.INFO_CLN4_phonNo, patient_info.getPhonNo());
        contentValues_info.put(My_DataBase.INFO_CLN5_address, patient_info.getAddress());

        ContentValues contentValues_case = new ContentValues();
        contentValues_case.put(My_DataBase.CASE_CLN1_patient_id, getLastAddedRowId());

        contentValues_case.put(My_DataBase.CASE_CLN2_length, patient_case.getLength());
        contentValues_case.put(My_DataBase.CASE_CLN3_weight, patient_case.getWeight());
        contentValues_case.put(My_DataBase.CASE_CLN4_diagnosis, patient_case.getDiagnosis());
        contentValues_case.put(My_DataBase.CASE_CLN5_complaint, patient_case.getComplaint());
        contentValues_case.put(My_DataBase.CASE_CLN6_medicine, patient_case.getMedicine());


        long resultInfo = sqLiteDatabase.insert(My_DataBase.TB_PATIENT_INFO, null, contentValues_info);
        long resultCase = sqLiteDatabase.insert(My_DataBase.TB_PATIENT_CASE, null, contentValues_case);

        if (resultInfo == -1 && resultCase == -1) {
            return false;

        }
        return true;

    }


    public boolean addNewِِِAppointment(Appointments appointments) {
        ContentValues appointmentsValue = new ContentValues();


        appointmentsValue.put(My_DataBase.APPOINTMENST_CLN1_Forg_APPO_ID, appointments.getPatientId());
        appointmentsValue.put(My_DataBase.APPOINTMENST_CLN2_DATA_TIME, appointments.getDateTime());
        appointmentsValue.put(My_DataBase.APPOINTMENST_CLN3_TIME, appointments.getTime());


        long resultInfo = sqLiteDatabase.insert(My_DataBase.TB_APPOINTMENST, null, appointmentsValue);

        if (resultInfo == -1) {
            return false;

        }
        return true;

    }

    public boolean update_Info(Patient_info patient_info) {
        String args[] = {patient_info.getPatient_id() + ""};
        ContentValues contentValues = new ContentValues();
        contentValues.put(My_DataBase.INFO_CLN1_fullName, patient_info.getFullName());
        contentValues.put(My_DataBase.INFO_CLN2_sex, patient_info.getSex());
        contentValues.put(My_DataBase.INFO_CLN3_birthDay, patient_info.getBirthDay());
        contentValues.put(My_DataBase.INFO_CLN4_phonNo, patient_info.getPhonNo());
        contentValues.put(My_DataBase.INFO_CLN5_address, patient_info.getAddress());


        int x = sqLiteDatabase.update(My_DataBase.TB_PATIENT_INFO, contentValues
                , My_DataBase.TB_PATIENT_INFO + "." + My_DataBase.INFO_CLN6_patient_id + "  =?", args);
        if (x == 0)
            return false;
        return true;


    }

    public boolean update_Case(Patient_case patient_case) {
        String args[] = {patient_case.getPatient_id() + ""};
        ContentValues contentValues = new ContentValues();
        contentValues.put(My_DataBase.CASE_CLN6_medicine, patient_case.getMedicine());
        contentValues.put(My_DataBase.CASE_CLN2_length, patient_case.getLength());
        contentValues.put(My_DataBase.CASE_CLN3_weight, patient_case.getWeight());
        contentValues.put(My_DataBase.CASE_CLN4_diagnosis, patient_case.getDiagnosis());
        contentValues.put(My_DataBase.CASE_CLN5_complaint, patient_case.getComplaint());


        int x = sqLiteDatabase.update(My_DataBase.TB_PATIENT_CASE, contentValues
                , My_DataBase.TB_PATIENT_CASE + "." + My_DataBase.CASE_CLN1_patient_id + "  =?", args);
        if (x == 0)
            return false;
        return true;


    }

    public boolean delete_Appointments(Appointments appointments) {

        String agrs[] = {appointments.getPatientId() + ""};

        int s = sqLiteDatabase.delete(My_DataBase.TB_APPOINTMENST, My_DataBase.TB_APPOINTMENST +
                "." + My_DataBase.APPOINTMENST_CLN1_Forg_APPO_ID + " =? ", agrs);
        if (s == 0)
            return false;
        return true;


    }

    public ArrayList<Patient_info> searchByNamePatientInfos(String name) {

        String a[] = {"%" + name + "%"};

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + My_DataBase.TB_PATIENT_INFO + " where " + My_DataBase.TB_PATIENT_INFO + "." + My_DataBase.INFO_CLN1_fullName
                + " LIKE  ? ", a);
        ArrayList<Patient_info> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(My_DataBase.INFO_CLN6_patient_id));
                String fullName = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN1_fullName));
                String sex = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN2_sex));
                String birthDay = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN3_birthDay));
                String phoneNo = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN4_phonNo));
                String address = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN5_address));
                arrayList.add(new Patient_info(id, fullName, sex, birthDay, phoneNo, address));

            } while (cursor.moveToNext());
            cursor.close();


        }

        return arrayList;
    }

    public ArrayList<Patient_case> searchhByIDCase(int id) {

        String a[] = {id + ""};

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + My_DataBase.TB_PATIENT_CASE + " where " + My_DataBase.TB_PATIENT_CASE + "." + My_DataBase.CASE_CLN1_patient_id
                + " = ? ", a);
        ArrayList<Patient_case> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                int idP = cursor.getInt(cursor.getColumnIndex(My_DataBase.CASE_CLN1_patient_id));
                double length = cursor.getDouble(cursor.getColumnIndex(My_DataBase.CASE_CLN2_length));
                double weight = cursor.getDouble(cursor.getColumnIndex(My_DataBase.CASE_CLN3_weight));
                String diagnosis = cursor.getString(cursor.getColumnIndex(My_DataBase.CASE_CLN4_diagnosis));
                String complaint = cursor.getString(cursor.getColumnIndex(My_DataBase.CASE_CLN5_complaint));
                String medicine = cursor.getString(cursor.getColumnIndex(My_DataBase.CASE_CLN6_medicine));
                arrayList.add(new Patient_case(idP, length, weight, diagnosis, complaint, medicine));

            } while (cursor.moveToNext());
            cursor.close();


        }

        return arrayList;
    }

    public ArrayList<Patient_info> searchByIDPatientInfos(int id) {

        String a[] = {id + ""};

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + My_DataBase.TB_PATIENT_INFO + " where " + My_DataBase.TB_PATIENT_INFO + "." + My_DataBase.INFO_CLN6_patient_id
                + " LIKE  ? ", a);
        ArrayList<Patient_info> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int idP = cursor.getInt(cursor.getColumnIndex(My_DataBase.INFO_CLN6_patient_id));
                String fullName = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN1_fullName));
                String sex = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN2_sex));
                String birthDay = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN3_birthDay));
                String phoneNo = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN4_phonNo));
                String address = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN5_address));
                arrayList.add(new Patient_info(idP, fullName, sex, birthDay, phoneNo, address));

            } while (cursor.moveToNext());
            cursor.close();


        }

        return arrayList;
    }

    public ArrayList<Appointments> searchByIDAppointments(int id) {

        String a[] = {id + ""};

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + My_DataBase.TB_APPOINTMENST +
                " where " + My_DataBase.TB_APPOINTMENST + "." + My_DataBase.APPOINTMENST_CLN1_Forg_APPO_ID
                + " LIKE  ? ", a);
        ArrayList<Appointments> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int idA = cursor.getInt(cursor.getColumnIndex(My_DataBase.APPOINTMENST_CLN1_Forg_APPO_ID));
                String dataTime = cursor.getString(cursor.getColumnIndex(My_DataBase.APPOINTMENST_CLN2_DATA_TIME));
                String time = cursor.getString(cursor.getColumnIndex(My_DataBase.APPOINTMENST_CLN3_TIME));

                arrayList.add(new Appointments(idA, dataTime, time));

            } while (cursor.moveToNext());
            cursor.close();


        }

        return arrayList;
    }

    public ArrayList<Patient_info> patientInfos() {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + My_DataBase.TB_PATIENT_INFO, null);
        ArrayList<Patient_info> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex(My_DataBase.INFO_CLN6_patient_id));
                String name = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN1_fullName));
                String sex = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN2_sex));
                String birthDay = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN3_birthDay));
                String phoneNo = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN4_phonNo));
                String address = cursor.getString(cursor.getColumnIndex(My_DataBase.INFO_CLN5_address));
                arrayList.add(new Patient_info(id, name, sex, birthDay, phoneNo, address));

            } while (cursor.moveToNext());
            cursor.close();


        }

        return arrayList;
    }

    public int getLastAddedRowId() {
        String queryLastRowInserted = "select last_insert_rowid()";

        final Cursor cursor = sqLiteDatabase.rawQuery(queryLastRowInserted, null);
        int _idLastInsertedRow = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    _idLastInsertedRow = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return _idLastInsertedRow;

    }
}
