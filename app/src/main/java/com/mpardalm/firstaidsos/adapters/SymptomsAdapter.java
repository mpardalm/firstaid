package com.mpardalm.firstaidsos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.data.Symptom;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mpardalm
 * */

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.SymptomsViewHolder>{

    private ArrayList<Symptom> symptomArrayList;

    public SymptomsAdapter(ArrayList<Symptom> symptomArrayList) {
        this.symptomArrayList = symptomArrayList;
    }

    @NonNull
    @Override
    public SymptomsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_sympton, viewGroup, false);

        return new SymptomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsViewHolder symptomsViewHolder, int position) {
        Symptom symptom = symptomArrayList.get(position);

        symptomsViewHolder.bindSympton(symptom);
    }

    @Override
    public int getItemCount() {
        return symptomArrayList.size();
    }

    public static class SymptomsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewSymptom;
        CheckBox checkBoxSymptom;

        public SymptomsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSymptom = itemView.findViewById(R.id.symptonName);
            checkBoxSymptom = itemView.findViewById(R.id.checkBoxSymton);
        }

        public void bindSympton(final Symptom symptom){
            textViewSymptom.setText(symptom.getName());
            checkBoxSymptom.setChecked(symptom.isChecked());

            checkBoxSymptom.setOnClickListener(v -> symptom.setChecked(!symptom.isChecked()));

            itemView.setOnClickListener(v -> {
                if(symptom.isChecked()) {
                    checkBoxSymptom.setChecked(false);
                    symptom.setChecked(false);
                }else {
                    checkBoxSymptom.setChecked(true);
                    symptom.setChecked(true);
                }
            });
        }
    }

}
