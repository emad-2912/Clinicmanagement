package com.example.clinicmanagement.recyclers;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.modules.Patient_info;

import java.util.ArrayList;
import java.util.List;

public class PatientsRec extends RecyclerView.Adapter<PatientsRec.View_rec_Holder> {
    List<Patient_info> arrayList;
    Context context;
    OnItemClickOnCar onItemClickOnCar;

    public PatientsRec(List<Patient_info> arrayList, Context context, OnItemClickOnCar onItemClickOnCar) {
        this.arrayList = arrayList;
        this.context = context;
        this.onItemClickOnCar = onItemClickOnCar;
    }

    @NonNull
    @Override
    public View_rec_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.patient, null, false);
        View_rec_Holder viewRecHolder = new View_rec_Holder(v);

        return viewRecHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_rec_Holder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getFullName());
        holder.tv_id.setText(arrayList.get(position).getPatient_id() +
                "");

        holder.patient_info = arrayList.get(position);

    }

    public void setPatient(ArrayList<Patient_info> patient) {

        arrayList = patient;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class View_rec_Holder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_id;
        Patient_info patient_info;

        public View_rec_Holder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_namePatient);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickOnCar.OnClickCar(patient_info);
                    notifyDataSetChanged();

                }
            });
        }
    }

}
