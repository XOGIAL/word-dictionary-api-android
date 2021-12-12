package com.example.uas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler {
    DBHelper dbhelper;
    Context context;
    public DBHandler(Context context)
    {
        dbhelper = new DBHelper(context);
        this.context =context;
    }

    public void insertfavorite(Word word)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ArrayList<Definition> definitions = word.definitions;

        Cursor cursor1 = db.rawQuery("select * from Favorite where word like '"+word.getWord()+"'",null);
        cursor1.moveToFirst();

        if(cursor1.getCount()>0)
        {
            if(String.valueOf(cursor1.getString(cursor1.getColumnIndex("word"))).equals(word.getWord()) )
            {
                Toast.makeText(context, "Already Saved", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        db.execSQL("insert into Favorite(word) Values ('"+word.getWord()+"')");

        Cursor cursor = db.rawQuery("select wordid from Favorite where word like '"+word.getWord()+"' ",null);
        cursor.moveToFirst();
        for(int i=0;i<definitions.size();i++)
        {
            String string = word.definitions.get(i).definition;

            string = string.replace("'","`");
            db.execSQL("insert into definitionlist(definitionimg,definitiontype,definitiondesc,wordid) values(" +
                    "'"+word.definitions.get(i).imgurl+"','"+word.definitions.get(i).type+"', '"+string+"', '"+cursor.getInt(0)+"' )");
        }


    }

    public ArrayList<Word> getfavword()
    {
        ArrayList<Word> wordlist = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Favorite",null);
        cursor.moveToFirst();

        for(int i=0;i<cursor.getCount();i++)
        {
            Word word = new Word();
            word.setWord(cursor.getString(cursor.getColumnIndex("word")));
            wordlist.add(word);
            cursor.moveToNext();
        }

        return wordlist;
    }

    public ArrayList<Definition> getdefinition(String word)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ArrayList<Definition> definitionlist = new ArrayList<>();


        Cursor cursor = db.rawQuery("select * from Favorite where word like '"+word+"' ",null);
        cursor.moveToFirst();
        int wordid = cursor.getInt(cursor.getColumnIndex("wordid"));

        cursor = db.rawQuery("select * from definitionlist where wordid like "+wordid+" ",null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++)
        {

            String url = cursor.getString(cursor.getColumnIndex("definitionimg"));
            String type = cursor.getString(cursor.getColumnIndex("definitiontype"));
            String desc = cursor.getString(cursor.getColumnIndex("definitiondesc"));

            Definition definition = new Definition(url,type,desc);
            cursor.moveToNext();

            definitionlist.add(definition);
        }

        return definitionlist;
    }

    public void deletefavorite(String word)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();



        Cursor cursor = db.rawQuery("select * from Favorite where word like '"+word+"' ",null);
        cursor.moveToFirst();
        int wordid = cursor.getInt(cursor.getColumnIndex("wordid"));

        db.execSQL("delete from Favorite where word like '"+word+"' ");
        db.execSQL("delete from definitionlist where wordid like '"+wordid+"' ");
    }
}
