package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Favoritelist_Activity extends AppCompatActivity {
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_favoritelist);
        DBHandler dbHandler = new DBHandler(this);
        ArrayList<Word> wordlist;

        wordlist = dbHandler.getfavword();

        Button explorebtn = findViewById(R.id.explorebtn);

        explorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        RecyclerView favoriterv = findViewById(R.id.favoriterv);
        FavoriteAdapter adapter = new FavoriteAdapter();
        adapter.setdata(context,wordlist);
        favoriterv.setAdapter(adapter);
        favoriterv.setLayoutManager(new LinearLayoutManager(context));
    }
}