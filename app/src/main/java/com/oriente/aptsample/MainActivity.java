package com.oriente.aptsample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oriente.aptsample.aspect.CallerType;
import com.oriente.aptsample.aspect.JavaTargetExample;
import com.oriente.aptsample.aspect.MessageListener;


public class MainActivity extends AppCompatActivity implements MessageListener {
    TextView text_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_dashboard = findViewById(R.id.text_dashboard);

        findViewById(R.id.testText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "double click", Toast.LENGTH_SHORT).show();
                new JavaTargetExample().demonstrateJavaAOP(MainActivity.this);
            }
        });
    }

    @Override
    public void onMessage(String message, CallerType callerType) {
        text_dashboard.append(message + "\n");
    }
}