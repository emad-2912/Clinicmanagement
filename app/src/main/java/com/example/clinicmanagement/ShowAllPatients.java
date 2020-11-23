package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Patient_case;
import com.example.clinicmanagement.modules.Patient_info;
import com.example.clinicmanagement.recyclers.OnItemClickOnCar;
import com.example.clinicmanagement.recyclers.PatientsRec;

import java.util.ArrayList;
import java.util.List;

public class ShowAllPatients extends AppCompatActivity {
    RecyclerView recyclerView;
    PatientsRec patientsRec;
    List<Patient_info> infoList;
    Access_DateBase access_dateBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_patients);
        recyclerView = findViewById(R.id.recP);

        infoList = new ArrayList<>();
        access_dateBase = Access_DateBase.getInstance(getApplicationContext());
        access_dateBase.open();

        infoList = access_dateBase.patientInfos();
        access_dateBase.close();

        patientsRec = new PatientsRec(infoList, getBaseContext(), new OnItemClickOnCar() {
            @Override
            public void OnClickCar(Patient_info patient_info) {
                startActivity(new Intent(getBaseContext(), NewPatient.class));
            }
        });
        RecyclerView.LayoutManager l = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(patientsRec);

    }
}