package com.mpardalm.firstaidsos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.data.Diagnosis;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
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

        AppCompatImageView diagnosisImage;
        TextView diagnosisName;
        TextView diagnosisDescription;
        TextView diagnosisSymptoms;
        TextView diagnosisRecommendation;
        TextView diagnosisUrgency;
        ProperRatingBar diagnosisSquare;

        public DiagnosisViewHolder(@NonNull View itemView, Context context) {

            super(itemView);

            diagnosisImage = itemView.findViewById(R.id.image_diagnosis);
            diagnosisName = itemView.findViewById(R.id.diagnosis_name);
            diagnosisDescription = itemView.findViewById(R.id.diagnosis_description);
            diagnosisSymptoms = itemView.findViewById(R.id.diagnosis_symptoms);
            diagnosisRecommendation = itemView.findViewById(R.id.diagnosis_recommendation);
            diagnosisSquare = itemView.findViewById(R.id.square);
            diagnosisUrgency = itemView.findViewById(R.id.urgency);

            this.context = context;
        }


        public void bindDiagnosis(final Diagnosis diagnosis){
            Glide.with(context).load(diagnosis.getImage()).into(diagnosisImage);
            diagnosisName.setText(diagnosis.getName());
            diagnosisDescription.setText(diagnosis.getDescription());
            diagnosisRecommendation.setText(diagnosis.getRecommendation());
            diagnosisSymptoms.setText(diagnosis.getSymptoms().toString());
            diagnosisSquare.setEnabled(false);
            diagnosisSquare.setClickable(false);
            switch ((int) diagnosis.getEmergency()){
                case 1:
                    diagnosisSquare.setBackgroundColor(context.getResources().getColor(R.color.green));
                    diagnosisUrgency.setText(diagnosisUrgency.getText().toString().concat(" " + context.getResources().getString(R.string.urgency1)));
                    break;
                case 2:
                    diagnosisSquare.setBackgroundColor(context.getResources().getColor(R.color.orange));
                    diagnosisUrgency.setText(diagnosisUrgency.getText().toString().concat(" " + context.getResources().getString(R.string.urgency2)));
                    break;
                case 3:
                    diagnosisSquare.setBackgroundColor(context.getResources().getColor(R.color.red));
                    diagnosisUrgency.setText(diagnosisUrgency.getText().toString().concat(" " + context.getResources().getString(R.string.urgency3)));
                    break;
            }

        }
    }
}
