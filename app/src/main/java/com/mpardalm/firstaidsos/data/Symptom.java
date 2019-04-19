package com.mpardalm.firstaidsos.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mpardalm
 * */

public class Symptom implements Parcelable {

    private String name;
    private boolean checked;

    Symptom(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }

    private Symptom(Parcel in) {
        name = in.readString();
        checked = in.readByte() != 0;
    }

    public static final Creator<Symptom> CREATOR = new Creator<Symptom>() {
        @Override
        public Symptom createFromParcel(Parcel in) {
            return new Symptom(in);
        }

        @Override
        public Symptom[] newArray(int size) {
            return new Symptom[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
