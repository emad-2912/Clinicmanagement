package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    Button addnewPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_patients);
        recyclerView = findViewById(R.id.recP);

        infoList = new ArrayList<>();
        access_dateBase = Access_DateBase.getInstance(getApplicationContext());
        access_dateBase.open();

        addnewPatient = findViewById(R.id.btn_addapp);

        infoList = access_dateBase.patientInfos();
        access_dateBase.close();

        addnewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAllPatients.this, NewPatient.class));
            }
        });

        patientsRec = new PatientsRec(infoList, getBaseContext(), new OnItemClickOnCar() {
            @Override
            public void OnClickCar(Patient_info patient_info) {
                Intent intent = new Intent(getBaseContext(), NewPatient.class);
                intent.putExtra("p", patient_info);
                access_dateBase.open();
                Patient_case patient_case = access_dateBase.searchhByIDCase(patient_info.getPatient_id());
                access_dateBase.close();
                intent.putExtra("c", patient_case);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager l = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(patientsRec);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(), Main_page.class));
    }
}