package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Patient_info;
import com.example.clinicmanagement.recyclers.OnItemClickOnCar;
import com.example.clinicmanagement.recyclers.PatientsRec;

import java.util.ArrayList;
import java.util.List;

public class Main_page extends AppCompatActivity {
    Button newPatient, showAllPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPatient = findViewById(R.id.btn_newPatient);
        showAllPatient = findViewById(R.id.btn_showPatient);


        showAllPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page.this, ShowAllPatients.class));
            }
        });

        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page.this, NewPatient.class));
            }
        });

    }
}