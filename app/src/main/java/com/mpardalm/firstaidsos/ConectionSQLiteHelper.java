package com.mpardalm.firstaidsos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpardalm.firstaidsos.utils.UtilsConstant;

import static com.mpardalm.firstaidsos.utils.UtilsConstant.CREATE_TABLE_SYMPTOMS;

/**
 * Created by mpardalm
 * */

public class ConectionSQLiteHelper extends SQLiteOpenHelper {

    public ConectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SYMPTOMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UtilsConstant.SYMPTOMS_TABLE);
        onCreate(db);
    }
}
