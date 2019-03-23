package com.mpardalm.firstaidsos;

public class Symptom {

    private String name;
    private boolean checked;

    public Symptom(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
