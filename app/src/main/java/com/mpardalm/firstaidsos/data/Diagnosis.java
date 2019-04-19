package com.mpardalm.firstaidsos.data;

import java.util.ArrayList;

/**
 * Created by mpardalm
 * */

public class Diagnosis {

    private String image;
    private String description;
    private String name;
    private String recommendation;
    private ArrayList<String> symptoms;
    private long emergency;

    public Diagnosis(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public long getEmergency() {
        return emergency;
    }

    public void setEmergency(long emergency) {
        this.emergency = emergency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
