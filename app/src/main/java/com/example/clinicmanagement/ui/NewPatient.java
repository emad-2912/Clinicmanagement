package com.example.clinicmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Patient_case;
import com.example.clinicmanagement.modules.Patient_info;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class NewPatient extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText et_fullname, et_phoneNo, et_address, et_length, et_weight, et_diagnosis, et_complaint, et_medicine;
    private Button Save, et_dob;

    private RadioButton male, famale;
    private Access_DateBase access_dateBase;
    private Patient_info patient_info;
    private Patient_case patient_case;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        access_dateBase = Access_DateBase.getInstance(getBaseContext());
        def();
        et_dob.setOnClickListener(this::onClick);
        access_dateBase.open();
        Intent myIntent = getIntent();
        Patient_info p = (Patient_info) myIntent.getSerializableExtra("p");
        Patient_case c = (Patient_case) myIntent.getSerializableExtra("c");
//        if (p != null) {
        try {
            et_address.setText(p.getAddress());
            et_complaint.setText(c.getComplaint());
            et_diagnosis.setText(c.getDiagnosis());
            et_dob.setText(p.getBirthDay());
            et_fullname.setText(p.getFullName());
            et_length.setText(c.getLength() + "");
            et_medicine.setText(c.getMedicine());
            et_phoneNo.setText(p.getPhonNo());
            et_weight.setText(c.getWeight() + "");
            if (p.getSex().equalsIgnoreCase("male")) {
                male.setChecked(true);
            } else if (p.getSex().equalsIgnoreCase("female")) {
                famale.setChecked(true);
            }
        } catch (Exception e) {
        }


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_fullname.getText().toString() == null || et_dob.getText().toString() == null || et_phoneNo.getText().toString() == null || et_address.getText().toString() == null) {
                    Toast.makeText(NewPatient.this, "يرجى تعبئة الفارغ", Toast.LENGTH_LONG).show();
                } else {

                    if (p != null) {
                        p.setFullName(et_fullname.getText().toString());
                        p.setAddress(et_address.getText().toString());
                        p.setBirthDay(et_dob.getText().toString());
                        p.setPhonNo(et_phoneNo.getText().toString());
                        if (male.isChecked()) {
                            p.setSex("male");
                        } else if (famale.isChecked()) {
                            p.setSex("female");
                        }
                        c.setComplaint(et_complaint.getText().toString());
                        c.setDiagnosis(et_diagnosis.getText().toString());
                        c.setLength(Double.parseDouble(et_length.getText().toString()));
                        c.setMedicine(et_medicine.getText().toString());
                        c.setWeight(Double.parseDouble(et_weight.getText().toString()));


                        access_dateBase.update_Info(p);
                        access_dateBase.update_Case(c);
                        Toast.makeText(getBaseContext(), "تم التعديل على معلومات المريض", Toast.LENGTH_SHORT).show();


                    } else {
                        patient_info = new Patient_info();
                        patient_case = new Patient_case();
                        patient_info.setAddress(et_address.getText().toString());
                        patient_info.setBirthDay(et_dob.getText().toString());
                        patient_info.setFullName(et_fullname.getText().toString());
                        patient_info.setPhonNo(et_phoneNo.getText().toString());
                        if (male.isChecked()) {
                            patient_info.setSex("male");
                        } else if (famale.isChecked()) {
                            patient_info.setSex("female");
                        }
                        if (!et_complaint.getText().toString().isEmpty())
                            patient_case.setComplaint(et_complaint.getText().toString());
                        if (!et_diagnosis.getText().toString().isEmpty())
                            patient_case.setDiagnosis(et_diagnosis.getText().toString());
                        if (!et_length.getText().toString().isEmpty())
                            patient_case.setLength(Double.parseDouble(et_length.getText().toString()));
                        if (!et_weight.getText().toString().isEmpty())
                            patient_case.setWeight(Double.parseDouble(et_weight.getText().toString()));
                        if (!et_medicine.getText().toString().isEmpty())
                            patient_case.setMedicine(et_medicine.getText().toString());

                        addNewPatient(patient_info, patient_case);
                        Toast.makeText(getBaseContext(), "تم اضافة مريض جديد", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(getBaseContext(), ShowAllPatients.class));
                }
            }
        });

    }

    private void def() {
        et_fullname = findViewById(R.id.fullnametxt);
        et_dob = findViewById(R.id.dobtxt);
        et_phoneNo = findViewById(R.id.phonetxt);
        et_address = findViewById(R.id.addresstxt);
        et_length = findViewById(R.id.lengthtxt);
        et_weight = findViewById(R.id.weighttxt);
        et_diagnosis = findViewById(R.id.diagnosistxt);
        et_complaint = findViewById(R.id.complainttxt);
        et_medicine = findViewById(R.id.medicinetxt);
        Save = findViewById(R.id.btn_savePatient);
        male = findViewById(R.id.radio_male);
        famale = findViewById(R.id.radio_female);
    }

    private boolean addNewPatient(Patient_info patient_info, Patient_case patient_case) {
        Log.d("rr", patient_case.getComplaint() + patient_case.getDiagnosis() + patient_case.getWeight() + "ID : " + patient_case.getPatient_id());

        access_dateBase.insertPatient(patient_info, patient_case);


        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        access_dateBase.close();
    }

    @Override
    public void onClick(View v) {

        if (v == et_dob) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            et_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}