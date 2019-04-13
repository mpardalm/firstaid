package com.mpardalm.firstaidsos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mpardalm.firstaidsos.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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

    private final String TAG = "MPM";

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

    @BindView(R.id.selectSymptoms)
    TextView selectSymptoms;

    @BindView(R.id.no_internet_image)
    ImageView noInternetImage;

    @BindView(R.id.no_internet_text)
    TextView noInternetText;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ArrayList<Symptom> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();

        cardView.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Utils.init(this).checkInternetConnecction())
            normalShow();
        else
            showNoInternet();
    }

    private void normalShow(){
        noInternetImage.setVisibility(View.INVISIBLE);
        noInternetText.setVisibility(View.INVISIBLE);
        mAdViewBottom.setVisibility(View.VISIBLE);
        mAdViewTop.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        symptomsRecView.setVisibility(View.VISIBLE);
        selectSymptoms.setVisibility(View.VISIBLE);
        initDataBase();
        initAds();
    }

    private void showNoInternet(){
        noInternetImage.setVisibility(View.VISIBLE);
        noInternetText.setVisibility(View.VISIBLE);
        mAdViewBottom.setVisibility(View.INVISIBLE);
        mAdViewTop.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);
        symptomsRecView.setVisibility(View.INVISIBLE);
        selectSymptoms.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardButtonSearch:
                ArrayList<String> listSymptomsName = new ArrayList<>();
                for(Symptom symptom: list){
                    if(symptom.isChecked())
                        listSymptomsName.add(symptom.getName());
                }
                if(listSymptomsName.size() == 0)
                    Toast.makeText(getBaseContext(), R.string.at_least_one_symptom, Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(this, DiagnosisActivity.class);
                    intent.putExtra("listSymptomsName", listSymptomsName);
                    startActivity(intent);
                }

        }
    }

    private void initAds(){
        MobileAds.initialize(this, getString(R.string.id_APP_AdMob));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("A8AFC23C5A106DBDF865F401C14CE9E1")
                .build();

        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);

        mAdViewTop.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
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
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //get all documentes from symptoms
        db.collection("symptoms")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if(task.getResult().size() > list.size()){
                            list.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                list.add(new Symptom((String) document.get("name"), false));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                    Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    progressBar.setVisibility(View.INVISIBLE);
                    initAdapter();
                });
    }
}
