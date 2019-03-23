package com.mpardalm.firstaidsos.entity;

/**
 * Created by mpardalm
 * */

public class SymptomDB {

    private int id;
    private String symptomName;

    public SymptomDB(int id, String symptomName) {
        this.id = id;
        this.symptomName = symptomName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
}
