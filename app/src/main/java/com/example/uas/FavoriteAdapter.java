package com.example.uas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewholder> {
    Context context;
    ArrayList<Word> wordlist;
    DBHandler dbHandler;

    @NonNull

    @Override
    public viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favoriterv_model,parent,false);
        dbHandler = new DBHandler(context);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  FavoriteAdapter.viewholder holder, int position) {
        holder.wordtxt.setText(wordlist.get(position).getWord());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WordDetails.class);

                wordlist.get(position).setDefinitions(dbHandler.getdefinition(wordlist.get(position).getWord()));

                intent.putExtra("wordobj",wordlist.get(position));

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(context);

                dbHandler.deletefavorite(wordlist.get(position).getWord());
                wordlist.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordlist.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView wordtxt;
        Button deletebtn;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            wordtxt = itemView.findViewById(R.id.favoritewordtxt);
            deletebtn = itemView.findViewById(R.id.deletebtn);
        }
    }

    public void setdata(Context context, ArrayList<Word> wordlist)
    {
        this.context = context;
        this.wordlist = wordlist;
    }
}


