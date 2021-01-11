package com.hivetech.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class SplashActivity extends AppCompatActivity {

    boolean permisoLlamadas, permisoMensajes;



    ImageView imgSplash;
    TextView txtSplash;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgSplash = findViewById(R.id.imgSplash);
        txtSplash = findViewById(R.id.txtSplash);

        revisarPermisos();
    }


    private void revisarPermisos(){
        permisoLlamadas = checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        permisoMensajes = checkSelfPermission(SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        if (permisoMensajes && permisoLlamadas){
           startActivityTimer();
        }else{
            requestPermissions(new String[]{CALL_PHONE,SEND_SMS},11);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11){
            revisarPermisos();
        }
    }

    private void startActivityTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },4000);
    }
}