package com.example.clinicmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Appoint;
import com.google.android.material.textfield.TextInputEditText;

public class AddAppointment extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

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
        checkForSmsPermission();

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
                    Toast.makeText(getBaseContext(), "تم حذف الحجز", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), Appointments.class));
                } else {
                    Toast.makeText(getBaseContext(), "لايوجد شيء لحذفه", Toast.LENGTH_LONG).show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message ;
                String phone;

                if (ev_name.getText().toString().isEmpty() || ev_date.getText().toString().isEmpty() || ev_time.getText().toString().isEmpty()) {
                    Toast.makeText(AddAppointment.this, "يرجى تعبئة الفارغ", Toast.LENGTH_SHORT).show();
                } else {

                    if (a != null) {
                        phone = "+970" + String.valueOf(access_dateBase.getPhoneByName(ev_name.getText().toString()));
                        message = "المريض: " + ev_name.getText().toString() + " موعد الحجز للعيادة تاريخ: " + ev_date.getText().toString()
                                + " الساعة: " + ev_time.getText().toString();
                        a.setDateTime(ev_date.getText().toString());
                        a.setTime(ev_time.getText().toString());
                        access_dateBase.update_App(a);
                        AlertDialog(phone, message);
                        Toast.makeText(getBaseContext(), "تم التعديل على الحجز", Toast.LENGTH_SHORT).show();



                    } else {
                        int id = access_dateBase.getIdByName(ev_name.getText().toString());
                        if (id != -1) {
                            phone = "+970" + String.valueOf(access_dateBase.getPhoneByName(ev_name.getText().toString()));
                            message = "المريض: " + ev_name.getText().toString() + " موعد الحجز للعيادة تاريخ: " + ev_date.getText().toString()
                                    + " الساعة: " + ev_time.getText().toString();
                            appoint = new Appoint(id, ev_time.getText().toString(), ev_date.getText().toString());
                            access_dateBase.addNewِِِAppointment(appoint);
                            AlertDialog(phone, message);
                            Toast.makeText(getBaseContext(), "تم اضافة حجز جديد", Toast.LENGTH_SHORT).show();


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


    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private void checkForSmsPermission() {
        String TAG="";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, getString(R.string.addapp));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            enableSmsButton();
        }
    }

    private void enableSmsButton() {
        Button smsButton = findViewById(R.id.btn_app_save);
        smsButton.setVisibility(View.VISIBLE);
    }


    public void AlertDialog(String phoneNo , String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.sureLeave)
                .setTitle(R.string.leave)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_off, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendSMS(phoneNo, message);
                        startActivity(new Intent(getBaseContext(), Appointments.class));

                    }
                })
                .setNegativeButton(R.string.no_still, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(getBaseContext(), Appointments.class));

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}