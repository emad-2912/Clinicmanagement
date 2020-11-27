package com.example.clinicmanagement.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Appoint;
import com.example.clinicmanagement.recyclers.AppointmentRec;
import com.example.clinicmanagement.recyclers.OnItemClickOnAppountemnt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointNow extends AppCompatActivity {
    Button addapp;
    RecyclerView recyclerView;
    AppointmentRec patientsRec;
    List<Appoint> appointmentsList;
    Access_DateBase access_dateBase;
    SearchView searchView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_now);

        recyclerView = findViewById(R.id.app_n_rec);
        searchView = findViewById(R.id.n_searchpatient);

        access_dateBase = Access_DateBase.getInstance(getApplicationContext());

        access_dateBase.open();
        LocalDateTime myDateObj = LocalDateTime.now();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formattedDate = myDateObj.format(myFormatObj);
        appointmentsList = access_dateBase.getAllNowAppointments(formattedDate);
        access_dateBase.close();
        setSearchView();
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


        addapp = findViewById(R.id.btn_n_addapp);
        addapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddAppointment.class));
            }
        });


    }


    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                access_dateBase.open();
                ArrayList<Appoint> patient_infos = access_dateBase.searchByNameAppointments(query);
                patientsRec.setPatient(patient_infos);
                access_dateBase.close();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                access_dateBase.open();
                ArrayList<Appoint> patient_infos = access_dateBase.searchByNameAppointments(newText);
                patientsRec.setPatient(patient_infos);
                access_dateBase.close();
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(), Main_page.class));
    }
}