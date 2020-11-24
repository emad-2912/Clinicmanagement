package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Reports extends AppCompatActivity {

    TextView countP, countapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        countP = findViewById(R.id.tv_countP);
        countapp = findViewById(R.id.tv_countapp);

    }
}