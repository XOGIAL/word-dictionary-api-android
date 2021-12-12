package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class WordDetails extends AppCompatActivity {
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        DBHandler dbHandler = new DBHandler(this);
        setContentView(R.layout.activity_word_details);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        Intent intent = getIntent();


        Word word = intent.getParcelableExtra("wordobj");
        Button detailsavebtn = findViewById(R.id.detailsavebtn);

        detailsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    dbHandler.insertfavorite(word);
                    Toast.makeText(context, "Saved to Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });





//        Toast.makeText(this,word.getDefinitions().get(0).definition,Toast.LENGTH_SHORT).show();

        TextView wordtxt = findViewById(R.id.wordtxt);

        wordtxt.setText(word.getWord());




        RecyclerView worddetailview = findViewById(R.id.worddetailview);
        DetailsAdapter adapter = new DetailsAdapter();
        adapter.setdata(getApplicationContext(),word);


        worddetailview.setAdapter(adapter);
        worddetailview.setLayoutManager(new LinearLayoutManager(this));




    }
}