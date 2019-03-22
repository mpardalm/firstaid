package com.mpardalm.firstaidsos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardView cardView;
    ListView symptonsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        cardView = findViewById(R.id.cardButtonSearch);
        symptonsListView = findViewById(R.id.listViewSymptom);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Prueba", Toast.LENGTH_SHORT).show();
            }
        });

        initToolbar();
        ArrayList<String> list = new ArrayList<>();
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");
        list.add("Dolor de Cabeza");

        SymptonsAdapter symptonsAdapter = new SymptonsAdapter(this, list);
        symptonsListView.setAdapter(symptonsAdapter);
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
