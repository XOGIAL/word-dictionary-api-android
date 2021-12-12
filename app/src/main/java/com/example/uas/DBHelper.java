package com.example.uas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "DB", null, 1);

    }
    public static final String table_Favorite = "Favorite";
    public static final String field_word_id = "wordid";
    public static final String field_word_name = "word";


    //table word
    private static final String create_table_Favorite = "Create table if not Exists "+table_Favorite+"" +
            "( "+field_word_id+" integer primary key autoincrement," +
            " "+field_word_name+" TEXT Unique)  ";


    //table definition
    public static final String table_definitionlist = "definitionlist";
    public static final String field_definitionid = "definitionid";
    public static final String field_definitionimg ="definitionimg";
    public static final String field_definitiontype ="definitiontype";
    public static final String field_definitiondesc ="definitiondesc";
    public static final String field_wordid = "wordid";

    private static final String create_table_definitionlist = "create table if not exists "+table_definitionlist+"("+field_definitionid+" integer primary key autoincrement,"+field_word_id+" integer,  " +
            "  "+field_definitionimg+" Text ,   " +
            " "+field_definitiontype+" Text, " +
            ""+field_definitiondesc+" Varchar(255)," +
            "Foreign key ("+field_wordid+") references Favorite(wordid) on update cascade on delete cascade)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_Favorite);
        db.execSQL(create_table_definitionlist);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
