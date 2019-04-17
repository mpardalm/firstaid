package com.mpardalm.firstaidsos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.data.Diagnosis;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by mpardalm
 * */

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.DiagnosisViewHolder>{

    private ArrayList<Diagnosis> diagnosisArrayList;
    private Context context;

    public DiagnosisAdapter(ArrayList<Diagnosis> diagnosisArrayList, Context context){
        this.diagnosisArrayList = diagnosisArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_diagnosis, viewGroup, false);

        return new DiagnosisViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisViewHolder holder, int position) {
        Diagnosis diagnosis = diagnosisArrayList.get(position);

        holder.bindDiagnosis(diagnosis);
    }

    @Override
    public int getItemCount() {
        return diagnosisArrayList.size();
    }

    public static class DiagnosisViewHolder extends RecyclerView.ViewHolder{

        Context context;

        TextView diagnosisName;
        TextView diagnosisDescription;
        TextView diagnosisSymptoms;
        TextView diagnosisRecommendation;
        ProperRatingBar diagnosisSquare;

        public DiagnosisViewHolder(@NonNull View itemView, Context context) {

            super(itemView);

            diagnosisName = itemView.findViewById(R.id.diagnosis_name);
            diagnosisDescription = itemView.findViewById(R.id.diagnosis_description);
            diagnosisSymptoms = itemView.findViewById(R.id.diagnosis_symptoms);
            diagnosisRecommendation = itemView.findViewById(R.id.diagnosis_recommendation);
            diagnosisSquare = itemView.findViewById(R.id.square);

            this.context = context;
        }


        public void bindDiagnosis(final Diagnosis diagnosis){
            diagnosisName.setText(diagnosis.getName());
            diagnosisDescription.setText(diagnosis.getDescription());
            diagnosisRecommendation.setText(diagnosis.getRecommendation());
            diagnosisSymptoms.setText(diagnosis.getSymptoms().toString());
            diagnosisSquare.setEnabled(false);
            diagnosisSquare.setClickable(false);
            switch ((int) diagnosis.getEmergency()){
                case 1:
                    diagnosisSquare.setBackgroundColor(context.getResources().getColor(R.color.cardview_dark_background));
                    break;
                case 2:
                    diagnosisSquare.setBackgroundColor(context.getResources().getColor(R.color.bluePrimary));
                    break;
                case 3:
                    diagnosisSquare.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    break;
            }

        }
    }
}
