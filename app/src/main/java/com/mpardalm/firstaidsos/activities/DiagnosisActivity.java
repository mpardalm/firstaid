package com.mpardalm.firstaidsos.activities;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.adapters.DiagnosisAdapter;
import com.mpardalm.firstaidsos.data.Diagnosis;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagnosisActivity extends AppCompatActivity {

    ArrayList<String> listSymptomsName;
    ArrayList<Diagnosis> listDiagnosis;
    private final String TAG = "MPM";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recViewDiagnosis)
    RecyclerView diagnosisRecView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        ButterKnife.bind(this);

        listDiagnosis = new ArrayList<>();

        listSymptomsName = getIntent().getStringArrayListExtra("listSymptomsName");
        initToolbar();
        inittDB();

    }

    private void initToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initAdapter(){
        DiagnosisAdapter symptomsAdapter = new DiagnosisAdapter(listDiagnosis, this);
        diagnosisRecView.setAdapter(symptomsAdapter);
        diagnosisRecView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(diagnosisRecView.getContext(),
                ((LinearLayoutManager) diagnosisRecView.getLayoutManager()).getOrientation());
        diagnosisRecView.addItemDecoration(dividerItemDecoration);
    }

    private void inittDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<QueryDocumentSnapshot> documentList = new ArrayList<>();

        for(int i=0; i<listSymptomsName.size(); i++) {

            db.collection("diagnosis")
                    .whereArrayContains("symptoms", listSymptomsName.get(i))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if(!documentList.contains(document)){
                                    documentList.add(document);
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for(QueryDocumentSnapshot document : documentList){
                            Diagnosis d = new Diagnosis();
                            d.setDescription((String) document.get("description"));
                            d.setName((String) document.get("name"));
                            d.setRecommendation((String) document.get("recommendation"));
                            d.setSymptoms((ArrayList<String>) document.get("symptoms"));
                            d.setEmergency((Long) document.get("emergency"));
                            listDiagnosis.add(d);
                        }
                        initAdapter();
                    });
        }
    }
}
