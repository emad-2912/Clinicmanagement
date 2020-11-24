package com.example.clinicmanagement.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.modules.Appoint;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRec extends RecyclerView.Adapter<AppointmentRec.View_rec_Holder> {
    List<Appoint> arrayList;
    Context context;
    OnItemClickOnAppountemnt onItemClickOnAppountemnt;

    public AppointmentRec(List<Appoint> arrayList, Context context, OnItemClickOnAppountemnt onItemClickOnAppountemnt) {
        this.arrayList = arrayList;
        this.context = context;
        this.onItemClickOnAppountemnt = onItemClickOnAppountemnt;
    }

    @NonNull
    @Override
    public View_rec_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.appointment, null, false);
        View_rec_Holder viewRecHolder = new View_rec_Holder(v);

        return viewRecHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_rec_Holder holder, int position) {
        holder.tv_id.setText(arrayList.get(position).getPatientId() + "");
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_date.setText(arrayList.get(position).getDateTime() + "");
        holder.tv_time.setText(arrayList.get(position).getTime() + "");
        holder.appointments = arrayList.get(position);

    }

    public void setPatient(ArrayList<Appoint> patient) {

        arrayList = patient;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class View_rec_Holder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_name;
        TextView tv_date;
        TextView tv_time;
        Appoint appointments;

        public View_rec_Holder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_app_id);
            tv_name = itemView.findViewById(R.id.tv_app_name);
            tv_date = itemView.findViewById(R.id.tv_app_date);
            tv_time = itemView.findViewById(R.id.tv_app_time);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickOnAppountemnt.OnClick(appointments);
//                    onItemClickOnCar.OnClickCar(patient_info);
                    notifyDataSetChanged();

                }
            });
        }
    }

}
