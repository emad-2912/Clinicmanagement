package com.example.clinicmanagement;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;




public class SplashActivity extends AppCompatActivity {
    RelativeLayout mySplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2 * 1000);
                    Intent intent = new Intent(SplashActivity.this, Main_page.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                }
            }
        };
        background.start();
    }


}
