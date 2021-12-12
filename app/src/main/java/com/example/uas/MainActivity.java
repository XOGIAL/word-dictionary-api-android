package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Word> wordlist;
    ArrayList<Definition>definitionList;
    RequestQueue requestQueue;
    String searchtxt;
    Context context = this;
    String imgurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchbtn = findViewById(R.id.searchbtn);
        EditText searchedittxt = findViewById(R.id.searchedittxt);


        requestQueue = volley.getMinstance(this).getRequestqueue();
        Word word = new Word();


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordlist = new ArrayList<>();
                definitionList = new ArrayList<>();
                searchtxt = searchedittxt.getText().toString();
                getwords(searchtxt);

            }
        });


        Button favoritemenubtn = findViewById(R.id.favoritemenubtn);

        favoritemenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Favoritelist_Activity.class);
                startActivity(intent);

            }
        });









    }

    private void getwords(String searchtxt)
    {
        String link = "https://myawesomedictionary.herokuapp.com/words?q="+searchtxt;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, link, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Word word = new Word();
                        String wordname = jsonObject.getString("word");


                        JSONArray JsonArray = response.getJSONObject(i).getJSONArray("definitions");
                        definitionList = new ArrayList<>();

                        for(int j=0;j<JsonArray.length();j++)
                        {
                            JSONObject definitions = JsonArray.getJSONObject(j);

                            if(definitions.getString("image_url")!=null)
                            {
                               imgurl = definitions.getString("image_url");
                            }
                            else
                            {
                                imgurl = "null";
                            }

                            String type = definitions.getString("type");
                            String desc = definitions.getString("definition");


                            Definition definition = new Definition(imgurl,type,desc);
                            definitionList.add(definition);
//                            Toast.makeText(MainActivity.this,String.valueOf(definitionList.get(i).imgurl),Toast.LENGTH_SHORT).show();
                        }


                        word.setDefinitions(definitionList);




                        word.setWord(wordname);

                        wordlist.add(word);


//                        Toast.makeText(MainActivity.this,wordname,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this,String.valueOf(wordlist.size()),Toast.LENGTH_SHORT).show();;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                RecyclerView wordrview = findViewById(R.id.wordrview);

                SearchListAdapter adapter= new SearchListAdapter();
                adapter.setdata(context,wordlist);
                wordrview.setAdapter(adapter);
                wordrview.setLayoutManager(new LinearLayoutManager(context));
                adapter.notifyDataSetChanged();

            }
        },new Response.ErrorListener()
    {

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
        requestQueue.add(jsonArrayRequest);
    }
}