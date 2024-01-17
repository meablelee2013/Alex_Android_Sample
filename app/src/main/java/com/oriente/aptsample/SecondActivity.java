package com.oriente.aptsample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.common.AutoServiceUtil2;
import com.example.common.BorrowAction;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text_dashboard).setOnClickListener(v -> {
            BorrowAction service = AutoServiceUtil2.getInstance().getService(BorrowAction.class);
            service.borrow();
            service.verifyBorrow();


        });
    }
}
