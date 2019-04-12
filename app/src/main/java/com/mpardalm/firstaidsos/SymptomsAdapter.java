package com.mpardalm.firstaidsos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mpardalm
 * */

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.SymptonsViewHolder>{

    private ArrayList<Symptom> symptomArrayList;

    SymptomsAdapter(ArrayList<Symptom> symptomArrayList) {
        this.symptomArrayList = symptomArrayList;
    }

    @NonNull
    @Override
    public SymptonsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_sympton, viewGroup, false);

        return new SymptonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptonsViewHolder symptonsViewHolder, int position) {
        Collections.sort(symptomArrayList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Symptom symptom = symptomArrayList.get(position);

        symptonsViewHolder.bindSympton(symptom);
    }

    @Override
    public int getItemCount() {
        return symptomArrayList.size();
    }

    public static class SymptonsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewSymptom;
        CheckBox checkBoxSymptom;

        public SymptonsViewHolder(@NonNull View itemView) {
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
