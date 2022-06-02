package com.oriente.aptsample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.oriente.anno.BindView;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.text_dashboard)
    TextView text_dashboard;

    @BindView(R.id.text2)
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        text2.setText("aaaaa");

    }

}