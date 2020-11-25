package com.example.clinicmanagement.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Appoint;
import com.example.clinicmanagement.modules.Patient_info;
import com.example.clinicmanagement.recyclers.AppointmentRec;
import com.example.clinicmanagement.recyclers.OnItemClickOnAppountemnt;


import java.util.ArrayList;
import java.util.List;

public class Appointments extends AppCompatActivity {
    Button addapp;
    RecyclerView recyclerView;
    AppointmentRec patientsRec;
    List<Appoint> appointmentsList;
    Access_DateBase access_dateBase;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        recyclerView = findViewById(R.id.app_rec);
        //searchView = findViewById(R.id.searchpatient);

        access_dateBase = Access_DateBase.getInstance(getApplicationContext());

        access_dateBase.open();
        appointmentsList = access_dateBase.getAllAppointments();
        access_dateBase.close();

        patientsRec = new AppointmentRec(appointmentsList, this, new OnItemClickOnAppountemnt() {
            @Override
            public void OnClick(Appoint appointments) {
                Intent intent = new Intent(getBaseContext(), AddAppointment.class);
                intent.putExtra("app", appointments);

                startActivity(intent);

            }
        });


        RecyclerView.LayoutManager l = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(patientsRec);


        addapp = findViewById(R.id.btn_addapp);
        addapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Appointments.this, AddAppointment.class));
            }
        });


    }

//    private void setSearchView() {
//        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                int searchId;
//                access_dateBase.open();
//                List<Appoint> appoints = access_dateBase.getAllAppointments();
//
////                ArrayList<Appoint> patient_infos = access_dateBase.searchByIDAppointments(query);
////                patientsRec.setPatient(patient_infos);
//                access_dateBase.close();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                access_dateBase.open();
//                ArrayList<Appoint> patient_infos = access_dateBase.searchByIDAppointments(newText);
//                patientsRec.setPatient(patient_infos);
//                access_dateBase.close();
//
//                return false;
//            }
//        });
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(), Main_page.class));
    }
}