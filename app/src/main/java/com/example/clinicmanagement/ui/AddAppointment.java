package com.example.clinicmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clinicmanagement.R;
import com.example.clinicmanagement.databases.Access_DateBase;
import com.example.clinicmanagement.modules.Appoint;
import com.example.clinicmanagement.modules.Patient_info;
import com.google.android.material.textfield.TextInputEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAppointment extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    Button btnDatePicker, btnTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;

    //    TextInputEditText ev_date, ev_time;
    Button save, delete;
    Access_DateBase access_dateBase;
    Appoint appoint;
    List<Patient_info> infoList;
    private SearchableSpinner spinner_select;
    String name;
    ArrayAdapter<String> adapter_save_from;
    ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        spinner_select = findViewById(R.id.spinner_selection);
//        ev_date = findViewById(R.id.date_app_txt);
//        ev_time = findViewById(R.id.time_app_txt);
        save = findViewById(R.id.btn_app_save);
        delete = findViewById(R.id.btn_app_delete);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        infoList = new ArrayList<>();
        access_dateBase = Access_DateBase.getInstance(getBaseContext());


        checkForSmsPermission();


        Intent i = getIntent();
        Appoint a = (Appoint) i.getSerializableExtra("app");
        if (a != null) {
//            ev_time.setText(a.getTime());
//            ev_date.setText(a.getDateTime());
            btnDatePicker.setText(a.getDateTime());
            btnTimePicker.setText(a.getTime());
            infoList.clear();
            Patient_info patient_info = new Patient_info();
            patient_info.setFullName(a.getName());
            stringArrayList = new ArrayList<>();

            infoList.add(patient_info);
            stringArrayList.clear();
            stringArrayList.add(a.getName());
            spinner();
        } else {
            access_dateBase.open();
            infoList = access_dateBase.patientInfos();
            access_dateBase.close();
            stringArrayList = new ArrayList<>();
            for (int i1 = 0; i1 < infoList.size(); i1++) {
                stringArrayList.add(infoList.get(i1).getFullName());
            }
            spinner();
        }


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
                String message;
                String phone;

                if (btnDatePicker.getText().toString().isEmpty() || btnDatePicker.getText().toString().isEmpty()) {
                    Toast.makeText(AddAppointment.this, "يرجى تعبئة الفارغ", Toast.LENGTH_SHORT).show();
                } else {

                    if (a != null) {
                        phone = "+970" + String.valueOf(access_dateBase.getPhoneByName(name));
                        message = "المريض: " + name + " تم حجز موعد تاريخ: " + btnDatePicker.getText().toString()
                                + " الساعة: " + btnTimePicker.getText().toString();
                        a.setDateTime(btnDatePicker.getText().toString());
                        a.setTime(btnTimePicker.getText().toString());
                        access_dateBase.update_App(a);
                        AlertDialog(phone, message);
                        Toast.makeText(getBaseContext(), "تم التعديل على الحجز", Toast.LENGTH_SHORT).show();


                    } else {
                        int id = access_dateBase.getIdByName(name);
                        if (id != -1) {
                            phone = "+970" + String.valueOf(access_dateBase.getPhoneByName(name));
                            message = "المريض: " + name + " تم حجز موعد تاريخ: " + btnDatePicker.getText().toString()
                                    + " الساعة: " + btnTimePicker.getText().toString();
                            appoint = new Appoint(id, btnDatePicker.getText().toString(), btnTimePicker.getText().toString());
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


    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
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
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
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
        String TAG = "";
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


    private void AlertDialog(String phoneNo, String message) {
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

    private void spinner() {
        ArrayAdapter<String> adapter_save = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArrayList);
        spinner_select.setAdapter(adapter_save);
        spinner_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                adapter_save_from = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, stringArrayList);
                spinner_select.setAdapter(adapter_save_from);

                spinner_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        name = String.valueOf(adapter_save_from.getItem(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            btnTimePicker.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}