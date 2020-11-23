package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Patient_case;
import com.example.clinicmanagement.modules.Patient_info;
import com.google.android.material.textfield.TextInputEditText;

public class NewPatient extends AppCompatActivity {
    private TextInputEditText et_fullname, et_dob, et_phoneNo, et_address, et_length, et_weight, et_diagnosis, et_complaint, et_medicine;
    private Button Save;
    private RadioButton male, famale;
    private Access_DateBase access_dateBase;
    private Patient_info patient_info;
    private Patient_case patient_case;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        access_dateBase = Access_DateBase.getInstance(getBaseContext());
        def();
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_fullname.getText().toString() == null || et_dob.getText().toString() == null || et_phoneNo.getText().toString() == null || et_address.getText().toString() == null) {
                    Toast.makeText(NewPatient.this, "يرجى تعبئة الفارغ", Toast.LENGTH_LONG).show();
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
                    startActivity(new Intent(NewPatient.this, Main_page.class));
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
        return access_dateBase.insertPatient(patient_info, patient_case);

    }

}