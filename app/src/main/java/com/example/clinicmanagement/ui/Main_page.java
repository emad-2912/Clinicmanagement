package com.example.clinicmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clinicmanagement.R;

public class Main_page extends AppCompatActivity {
    Button newPatient, showAllPatient, appointments, report, AppointmentNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPatient = findViewById(R.id.btn_addapp);
        showAllPatient = findViewById(R.id.btn_showPatient);
        appointments = findViewById(R.id.btn_Appointments);
        report = findViewById(R.id.btn_report);
        AppointmentNow = findViewById(R.id.btn_AppointmentNow);


        showAllPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ShowAllPatients.class));
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Appointments.class));

            }
        });

        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), NewPatient.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Reports.class));
            }
        });

        AppointmentNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AppointNow.class));
            }
        });

    }




}