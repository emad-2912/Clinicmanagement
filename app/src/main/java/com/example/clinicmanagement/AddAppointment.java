package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Appoint;
import com.google.android.material.textfield.TextInputEditText;

public class AddAppointment extends AppCompatActivity {
    TextInputEditText ev_name, ev_date, ev_time;
    Button save, delete;
    Access_DateBase access_dateBase;
    Appoint appoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ev_name = findViewById(R.id.name_app_txt);
        ev_date = findViewById(R.id.date_app_txt);
        ev_time = findViewById(R.id.time_app_txt);
        save = findViewById(R.id.btn_app_save);
        delete = findViewById(R.id.btn_app_delete);

        Intent i = getIntent();
        Appoint a = (Appoint) i.getSerializableExtra("app");
        if (a != null) {
            ev_time.setText(a.getTime());
            ev_date.setText(a.getDateTime());
            ev_name.setText(a.getName());


        }


        access_dateBase = Access_DateBase.getInstance(getBaseContext());
        access_dateBase.open();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a != null) {
                    access_dateBase.delete_Appointments(a);
                    startActivity(new Intent(getBaseContext(), Appointments.class));
                } else {
                    Toast.makeText(getBaseContext(), "لايوجد شيء لحذفه", Toast.LENGTH_LONG).show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ev_name.getText().toString().isEmpty() || ev_date.getText().toString().isEmpty() || ev_time.getText().toString().isEmpty()) {
                    Toast.makeText(AddAppointment.this, "يرجى تعبئة الفارغ", Toast.LENGTH_SHORT).show();
                } else {

                    if (a != null) {
                        a.setDateTime(ev_date.getText().toString());
                        a.setTime(ev_time.getText().toString());
                        access_dateBase.update_App(a);
                        startActivity(new Intent(getBaseContext(), Appointments.class));

                    } else {
                        int id = access_dateBase.getIdByName(ev_name.getText().toString());
                        if (id != -1) {

                            appoint = new Appoint(id, ev_time.getText().toString(), ev_date.getText().toString());
                            access_dateBase.addNewِِِAppointment(appoint);
                            startActivity(new Intent(getBaseContext(), Appointments.class));

                        } else {
                            Toast.makeText(getBaseContext(), "يرحى التاكد من الاسم المدخل", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        access_dateBase.close();
    }
}