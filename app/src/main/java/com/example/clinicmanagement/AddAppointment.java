package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddAppointment extends AppCompatActivity {
    TextInputEditText ev_name, ev_date, ev_time;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        ev_name = findViewById(R.id.name_app_txt);
        ev_date = findViewById(R.id.date_app_txt);
        ev_time = findViewById(R.id.time_app_txt);
        save = findViewById(R.id.btn_app_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ev_name.getText().toString() == null || ev_date.getText().toString() == null || ev_time.getText().toString() == null){
                    Toast.makeText(AddAppointment.this, "يرجى تعبئة الفارغ", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });

    }
}