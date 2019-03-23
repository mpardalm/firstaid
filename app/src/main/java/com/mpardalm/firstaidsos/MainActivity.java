package com.mpardalm.firstaidsos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    CardView cardView;
    RecyclerView symptomsRecView;
    AdView mAdViewTop;
    AdView mAdViewBottom;

    ArrayList<Symptom> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        toolbar = findViewById(R.id.toolbar);
        cardView = findViewById(R.id.cardButtonSearch);
        symptomsRecView = findViewById(R.id.recViewSymptom);
        mAdViewTop = findViewById(R.id.bannerTop);
        mAdViewBottom = findViewById(R.id.bannerBottom);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);

        cardView.setOnClickListener(this);

        initToolbar();
        initSymptomList();

        SymptomsAdapter symptomsAdapter = new SymptomsAdapter(list);
        symptomsRecView.setAdapter(symptomsAdapter);
        symptomsRecView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(symptomsRecView.getContext(),
                ((LinearLayoutManager) symptomsRecView.getLayoutManager()).getOrientation());
        symptomsRecView.addItemDecoration(dividerItemDecoration);
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardButtonSearch:
                int res = 0;
                for(Symptom symptom: list){
                    if(symptom.isChecked())
                        res++;
                }
                Toast.makeText(getBaseContext(), String.valueOf(res), Toast.LENGTH_SHORT).show();
        }
    }

    private void initSymptomList(){
        ArrayList<String> symptomNames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.symptoms_array)));
        for(String symptom: symptomNames){
            list.add(new Symptom(symptom, false));
        }
    }
}
