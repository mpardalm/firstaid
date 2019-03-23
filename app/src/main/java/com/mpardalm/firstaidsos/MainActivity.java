package com.mpardalm.firstaidsos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.mpardalm.firstaidsos.utils.UtilsConstant;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mpardalm
 * */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String DBName= "DB_APP";

    ConectionSQLiteHelper conectionSQLiteHelper;

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

        initComponents();
        initToolbar();
        initAds();
        initDataBase();
        initSymptomList();
        initAdapter();

        cardView.setOnClickListener(this);

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
                if(res == 0)
                    Toast.makeText(getBaseContext(), R.string.at_least_one_symptom, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getBaseContext(), String.valueOf(res), Toast.LENGTH_SHORT).show();
        }
    }

    private void initComponents(){
        toolbar = findViewById(R.id.toolbar);
        cardView = findViewById(R.id.cardButtonSearch);
        symptomsRecView = findViewById(R.id.recViewSymptom);
        mAdViewTop = findViewById(R.id.bannerTop);
        mAdViewBottom = findViewById(R.id.bannerBottom);
    }

    private void initAds(){
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    private void initSymptomList(){
        conectionSQLiteHelper = new ConectionSQLiteHelper(this, DBName, null, 1);
        SQLiteDatabase db = conectionSQLiteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(getString(R.string.select_all) + " " + UtilsConstant.SYMPTOMS_TABLE, null);
        ArrayList<String> symptomNames = new ArrayList<>();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                symptomNames.add(cursor.getString(cursor.getColumnIndex(UtilsConstant.SYMPTOMS_NAME)));
                cursor.moveToNext();
            }
        }
        for(String symptom: symptomNames){
            list.add(new Symptom(symptom, false));
        }
        cursor.close();
    }

    private void initAdapter(){
        SymptomsAdapter symptomsAdapter = new SymptomsAdapter(list);
        symptomsRecView.setAdapter(symptomsAdapter);
        symptomsRecView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(symptomsRecView.getContext(),
                ((LinearLayoutManager) symptomsRecView.getLayoutManager()).getOrientation());
        symptomsRecView.addItemDecoration(dividerItemDecoration);
    }

    private void initDataBase(){
        conectionSQLiteHelper = new ConectionSQLiteHelper(this, DBName, null, 1);

        SQLiteDatabase db = conectionSQLiteHelper.getWritableDatabase();
        conectionSQLiteHelper.onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();

        ArrayList<String> symptomNames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.symptoms_array)));
        for(String symptoms: symptomNames){
            values.put(UtilsConstant.SYMPTOMS_NAME, symptoms);
            db.insert(UtilsConstant.SYMPTOMS_TABLE, UtilsConstant.SYMPTOMS_NAME, values);
        }
        db.close();
    }
}
