package com.mpardalm.firstaidsos.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.adapters.DiagnosisAdapter;
import com.mpardalm.firstaidsos.data.Diagnosis;
import com.mpardalm.firstaidsos.data.FireStoreDataBase;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagnosisActivity extends AppCompatActivity {

    private ArrayList<String> listSymptomsName;
    private ArrayList<Diagnosis> listDiagnosis;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recViewDiagnosis)
    RecyclerView diagnosisRecView;

    @BindView(R.id.progress_bar_diagnosis)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        ButterKnife.bind(this);

        listDiagnosis = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);

        listSymptomsName = getIntent().getStringArrayListExtra(getString(R.string.listSymptomsName));
        initToolbar();
        initDB();

    }

    private void initToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initAdapter(){
        DiagnosisAdapter symptomsAdapter = new DiagnosisAdapter(listDiagnosis, this);
        diagnosisRecView.setAdapter(symptomsAdapter);
        diagnosisRecView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(diagnosisRecView.getContext(),
                ((LinearLayoutManager) diagnosisRecView.getLayoutManager()).getOrientation());
        diagnosisRecView.addItemDecoration(dividerItemDecoration);
    }

    private void initDB(){
        FireStoreDataBase.init(this).getDiagnosisDataBase(listSymptomsName, this, progressBar);
    }

    public void setListDiagnosis(ArrayList<Diagnosis> listDiagnosis) {
        this.listDiagnosis = listDiagnosis;
    }
}
