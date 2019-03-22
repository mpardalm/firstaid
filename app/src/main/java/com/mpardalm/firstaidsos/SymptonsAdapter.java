package com.mpardalm.firstaidsos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class SymptonsAdapter extends ArrayAdapter<String> {


    private Context context;
    private CheckBox checkBoxSympton;
    private TextView textSympton;

    public SymptonsAdapter(Context context, ArrayList<String> objects) {
        super(context, 0, objects);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.adapter_sympton, parent, false);

        checkBoxSympton = convertView.findViewById(R.id.checkBoxSymton);
        textSympton = convertView.findViewById(R.id.symptonName);

        textSympton.setText(getItem(position));

        return convertView;
    }


}
