package com.mpardalm.firstaidsos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mpardalm.firstaidsos.utils.UtilsConstant;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mpardalm
 * */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String DBName= "DB_APP";

    ConectionSQLiteHelper conectionSQLiteHelper;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cardButtonSearch)
    CardView cardView;

    @BindView(R.id.recViewSymptom)
    RecyclerView symptomsRecView;

    @BindView(R.id.bannerTop)
    AdView mAdViewTop;

    @BindView(R.id.bannerBottom)
    AdView mAdViewBottom;

    ArrayList<Symptom> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
