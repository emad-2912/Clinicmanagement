package com.example.clinicmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.databases.Access_DateBase;

public class Reports extends AppCompatActivity {

    TextView countP, countapp;
    Access_DateBase access_dateBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        countP = findViewById(R.id.tv_countP);
        countapp = findViewById(R.id.tv_countapp);

        access_dateBase = Access_DateBase.getInstance(getApplicationContext());
        access_dateBase.open();

        countapp.setText(access_dateBase.count_app() + "");

        countP.setText(access_dateBase.itmes_count() + "");
        access_dateBase.close();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(), Main_page.class));
    }
}