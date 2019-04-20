package com.mpardalm.firstaidsos.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mpardalm.firstaidsos.R;
import com.mpardalm.firstaidsos.activities.DiagnosisActivity;
import com.mpardalm.firstaidsos.activities.MainActivity;

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

    public void initDataBaseSymptoms(ProgressBar progressBar, MainActivity activity){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Symptom> bodyList = new ArrayList<>();
        ArrayList<Symptom> armsList = new ArrayList<>();
        ArrayList<Symptom> headList = new ArrayList<>();
        ArrayList<Symptom> legsList = new ArrayList<>();

        //get all documentes from symptoms
        db.collection(myContext.getResources().getString(R.string.symptoms))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String bodyPart = (String) document.get(myContext.getResources().getString(R.string.body_part));
                            switch (bodyPart){
                                case "Cabeza":
                                    headList.add(new Symptom((String) document.get(myContext.getResources().getString(R.string.name)), false));
                                    break;
                                case "Cuerpo":
                                    bodyList.add(new Symptom((String) document.get(myContext.getResources().getString(R.string.name)), false));
                                    break;
                                case "Brazos":
                                    armsList.add(new Symptom((String) document.get(myContext.getResources().getString(R.string.name)), false));
                                    break;
                                case "Piernas":
                                    legsList.add(new Symptom((String) document.get(myContext.getResources().getString(R.string.name)), false));
                            }
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                    Collections.sort(bodyList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    Collections.sort(headList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    Collections.sort(armsList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    Collections.sort(legsList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    progressBar.setVisibility(View.INVISIBLE);
                    activity.setBodyList(bodyList);
                    activity.setHeadList(headList);
                    activity.setBodyArms(armsList);
                    activity.setBodyLegs(legsList);
                    activity.initAdapter();
                });
    }

    public void getDiagnosisDataBase(ArrayList<String> listSymptomsName, DiagnosisActivity activity, ProgressBar progressBar){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Diagnosis> listDiagnosis = new ArrayList<>();
        ArrayList<QueryDocumentSnapshot> documentList = new ArrayList<>();

        //get all documentes from symptoms
        /*
        * FireStore doesn't allow OR query, so need to be done in each call
        * */
        for(int i=0; i<listSymptomsName.size(); i++) {

            db.collection(myContext.getResources().getString(R.string.diagnosis))
                    .whereArrayContains(myContext.getResources().getString(R.string.symptoms), listSymptomsName.get(i))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if(!documentList.contains(document)){
                                    documentList.add(document);
                                    Diagnosis d = new Diagnosis();
                                    d.setImage((String) document.get(myContext.getResources().getString(R.string.image)));
                                    d.setDescription((String) document.get(myContext.getResources().getString(R.string.description)));
                                    d.setName((String) document.get(myContext.getResources().getString(R.string.name)));
                                    d.setRecommendation((String) document.get(myContext.getResources().getString(R.string.recommendation)));
                                    d.setSymptoms((ArrayList<String>) document.get(myContext.getResources().getString(R.string.symptoms)));
                                    d.setEmergency((Long) document.get(myContext.getResources().getString(R.string.emergency)));
                                    listDiagnosis.add(d);
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        activity.setListDiagnosis(listDiagnosis);
                        progressBar.setVisibility(View.INVISIBLE);
                        activity.initAdapter();
                    });
        }
    }
}
