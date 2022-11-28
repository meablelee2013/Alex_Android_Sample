package com.oriente.aptsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.IMyAidlInterface;


public class ClientActivity extends AppCompatActivity {
    IMyAidlInterface myService;

    Button bindBtn, unbindBtn, getDataBtn;
    TextView showDataTv;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        bindBtn = findViewById(R.id.bind);
        unbindBtn = findViewById(R.id.unbind);
        getDataBtn = findViewById(R.id.getData);
        showDataTv = findViewById(R.id.showData);


        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("ClientActivity.onServiceConnected");
                myService = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                System.out.println("ClientActivity.onServiceDisconnected");

            }
        };

        bindBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.example.service", "com.example.service.MyService"));
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        });
        unbindBtn.setOnClickListener(v -> unbindService(serviceConnection));
        getDataBtn.setOnClickListener(v -> {
            try {
                String string = myService.getString();
                showDataTv.setText(string);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}