package com.example.uas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsHolder>{

    @NonNull
    Context context;
    Word word;
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_model,parent,false);

        return new DetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {

        holder.typetxt.setText(word.definitions.get(position).type);
        URL url = null;


        try {
            url = new URL(word.definitions.get(position).imgurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(url == null)
        {
            holder.imgview.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            try {
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.imgview.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        holder.deftxt.setText(word.definitions.get(position).definition);
    }

    @Override
    public int getItemCount() {
        return word.definitions.size();
//    return 1;
    }


    public class DetailsHolder extends RecyclerView.ViewHolder
    {
        TextView typetxt;
        TextView deftxt;
        ImageView imgview;
        public DetailsHolder(@NonNull View itemView) {
            super(itemView);
            typetxt = itemView.findViewById(R.id.typetxt);
            deftxt = itemView.findViewById(R.id.definitiontxt);
            imgview = itemView.findViewById(R.id.imgview);
        }
    }

    public void setdata(Context context, Word word)
    {
        this.context = context;
        this.word = word;
    }
}
