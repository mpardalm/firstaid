package com.mpardalm.firstaidsos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.data.BodyPart;
import com.mpardalm.firstaidsos.data.Symptom;
import com.mpardalm.firstaidsos.viewholder.BodyViewHolder;
import com.mpardalm.firstaidsos.viewholder.SymptomsViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MainAdapter extends ExpandableRecyclerViewAdapter<BodyViewHolder, SymptomsViewHolder> {

    public MainAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public BodyViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_body_part, parent, false);
        return new BodyViewHolder(v);
    }

    @Override
    public SymptomsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_symptoms, parent, false);
        return new SymptomsViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(SymptomsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Symptom symptom = (Symptom) group.getItems().get(childIndex);
        holder.bind(symptom);
    }

    @Override
    public void onBindGroupViewHolder(BodyViewHolder holder, int flatPosition, ExpandableGroup group) {
        final BodyPart bodyPart = (BodyPart) group;
        holder.bind(bodyPart);
    }
}
