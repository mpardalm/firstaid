package com.mpardalm.firstaidsos.data;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class BodyPart extends ExpandableGroup<Symptom> {

    public BodyPart(String title, List<Symptom> items) {
        super(title, items);
    }
}
