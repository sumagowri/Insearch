package com.example.insearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String COMP_REG = "COMP_REG";
    public static final String COLUMN_COMPANY_NAME = "COMPANY_NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_REG_ID = "REG_" + COLUMN_ID;
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    public Database(@Nullable Context context) {
        super(context, "company.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String createTable= "CREATE TABLE " + COMP_REG + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_COMPANY_NAME + " TEXT, " + COLUMN_REG_ID + " INT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PHONE + " INT, " + COLUMN_PASSWORD + " TEXT)";
db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(Company company){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
cv.put(COLUMN_COMPANY_NAME,company.getC_name());
cv.put(COLUMN_REG_ID,company.getReg_id());
cv.put(COLUMN_EMAIL,company.getPhone());
cv.put(COLUMN_PHONE,company.getPhone());
cv.put(COLUMN_PASSWORD,company.getPassword());

long insert=db.insert(COMP_REG,null,cv);

        if (insert==-1){
            return false;
        }else{
            return true;
        }
    }
}
