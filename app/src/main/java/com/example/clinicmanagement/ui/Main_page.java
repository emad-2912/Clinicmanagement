package com.example.clinicmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clinicmanagement.R;

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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//         final String TAG = "";
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS)
//                        && grantResults[0] ==
//                        PackageManager.PERMISSION_GRANTED) {
//                    // Permission was granted.
//                } else {
//                    // Permission denied.
//                    Log.d(TAG, getString(R.string.failure_permission));
//                    Toast.makeText(Main_page.this,
//                            getString(R.string.failure_permission),
//                            Toast.LENGTH_SHORT).show();
//                    // Disable the message button.
//                    disableSmsButton();
//                }
//            }
//        }
//    }



}