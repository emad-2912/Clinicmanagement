package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class newPatient extends AppCompatActivity {
    TextInputEditText fullname, dob, phoneNo, address, length, weight, diagnosis, complaint, medicine;
    Button Save;
    RadioButton male, famale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        fullname = findViewById(R.id.fullnametxt);
        dob = findViewById(R.id.dobtxt);
        phoneNo = findViewById(R.id.phonetxt);
        address = findViewById(R.id.addresstxt);
        length = findViewById(R.id.lengthtxt);
        weight = findViewById(R.id.weighttxt);
        diagnosis = findViewById(R.id.diagnosistxt);
        complaint = findViewById(R.id.complainttxt);
        medicine = findViewById(R.id.medicinetxt);
        Save = findViewById(R.id.btn_savePatient);
        male = findViewById(R.id.radio_male);
        famale = findViewById(R.id.radio_female);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname == null || dob == null || phoneNo == null|| address == null){
                    Toast.makeText(newPatient.this, "يرجى تعبئة الفارغ", Toast.LENGTH_LONG).show();
                }else {
                    startActivity(new Intent(newPatient.this, Main_page.class));
                }
            }
        });



    }
}