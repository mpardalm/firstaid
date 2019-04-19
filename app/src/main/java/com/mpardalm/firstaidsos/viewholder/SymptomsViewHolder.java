package com.mpardalm.firstaidsos.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.data.Symptom;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SymptomsViewHolder extends ChildViewHolder {

    TextView textViewSymptom;
    CheckBox checkBoxSymptom;

    public SymptomsViewHolder(View itemView) {
        super(itemView);
        textViewSymptom = itemView.findViewById(R.id.symptoms_textView);
        checkBoxSymptom = itemView.findViewById(R.id.checkBoxSymton);
    }

    public void bind (Symptom symptom){
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
