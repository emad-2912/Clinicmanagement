package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_page extends AppCompatActivity {
    Button newPatient, showAllPatient, appointments, report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPatient = findViewById(R.id.btn_addapp);
        showAllPatient = findViewById(R.id.btn_showPatient);
        appointments = findViewById(R.id.btn_Appointments);
        report = findViewById(R.id.btn_report);


        showAllPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page.this, ShowAllPatients.class));
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page.this, Appointments.class));

            }
        });

        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page.this, NewPatient.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page.this, Reports.class));
            }
        });

    }
}