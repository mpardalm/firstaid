package com.mpardalm.firstaidsos.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by mpardalm
 * */

public class FireStoreDataBase {

    private final String TAG = "MPM";

    private static FireStoreDataBase fireStoreDataBase;
    private static Context myContext;

    private FireStoreDataBase(Context context){
        myContext = context;
    }

    public static FireStoreDataBase init(Context context){
        if(fireStoreDataBase == null)
            fireStoreDataBase = new FireStoreDataBase(context);
        return fireStoreDataBase;
    }

    public ArrayList<Symptom> initDataBase(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Symptom> list = new ArrayList<>();

        //get all documentes from symptoms
        db.collection("symptoms")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if(task.getResult().size() > list.size()){
                            list.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                list.add(new Symptom((String) document.get("name"), false));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                    Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    progressBar.setVisibility(View.INVISIBLE);
                });
        return list;
    }
}
