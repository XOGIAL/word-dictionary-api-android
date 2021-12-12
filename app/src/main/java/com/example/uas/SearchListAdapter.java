package com.example.uas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.searchlistholder> {
    Context context;
    ArrayList<Word> words;


    @NonNull

    @Override
    public searchlistholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchlist_model,parent,false);


        return new searchlistholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.searchlistholder holder, int position) {

        holder.wordname.setText(words.get(position).word);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WordDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("wordobj",words.get(position));

                context.startActivity(intent);
            }
        });

        holder.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbhandler = new DBHandler(context);
                dbhandler.insertfavorite(words.get(position));
                Toast.makeText(context, "Saved to Favorite", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return words.size();
    }

    public class searchlistholder extends RecyclerView.ViewHolder
    {
        TextView wordname;
        Button savebtn;
        public searchlistholder(View itemView) {
            super(itemView);
            wordname = itemView.findViewById(R.id.wordname);
            savebtn = itemView.findViewById(R.id.savebtn);
        }
    }

    public void setdata(Context context,ArrayList<Word> words)
    {
        this.context = context;
        this.words = words;
    }
}
