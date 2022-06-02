package com.oriente.aptsample;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.oriente.anno.BindView;
import com.oriente.anno.RouteInfo;

import java.util.List;

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

        List<RouteInfo> sRouterTable = RouterTable.sRouterTable;
        for (RouteInfo routeInfo : sRouterTable) {
            Log.d("alex", routeInfo.name);
        }
    }
}