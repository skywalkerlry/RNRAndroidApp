package com.example.rnrandroidapp;
/**
 * Created by adan on 2014/10/14.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    //辅助类建立时运行该方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE  TABLE pic (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , fileName VARCHAR, description VARCHAR)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}