package com.example.clinicmanagement.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinicmanagement.R;

import java.util.Locale;

public class Main_page extends AppCompatActivity {
    Button newPatient, showAllPatient, appointments, report, AppointmentNow;
    TextView btn_english, btn_arbice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPatient = findViewById(R.id.btn_addapp);
        showAllPatient = findViewById(R.id.btn_showPatient);
        appointments = findViewById(R.id.btn_Appointments);
        report = findViewById(R.id.btn_report);
        AppointmentNow = findViewById(R.id.btn_AppointmentNow);

        btn_arbice = findViewById(R.id.btn_arbice);
        btn_english = findViewById(R.id.btn_english);

        btn_english.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setLocale("en");
                getSharedPreferences("lang", MODE_PRIVATE).edit().putString("lan", "en").apply();

                startActivity(new Intent(getBaseContext(), Main_page.class));
            }
        });


        btn_arbice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setLocale("ar");
                getSharedPreferences("lang", MODE_PRIVATE).edit().putString("lan", "ar").apply();

                startActivity(new Intent(getBaseContext(), Main_page.class));
            }
        });

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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.sureLeave)
                .setTitle(R.string.leave)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_off, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton(R.string.no_still, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String lang) {

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            conf.setLocale(new Locale(lang.toLowerCase()));

        } else {
            conf.locale = new Locale(lang.toLowerCase());

        }

        res.updateConfiguration(conf, dm);
    }


}