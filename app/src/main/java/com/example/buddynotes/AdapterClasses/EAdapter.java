package com.example.buddynotes.AdapterClasses;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddynotes.Preview;
import com.example.buddynotes.R;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class EAdapter extends RecyclerView.Adapter<EAdapter.MostViewHolder> {
    ArrayList<EHelperClass> mostLocations;
    String fileExtension = ".pdf";
    public EAdapter(ArrayList<EHelperClass> mostLocations) {
        this.mostLocations = mostLocations;
    }

    @NonNull
    @Override
    public EAdapter.MostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_design,parent,false);
        EAdapter.MostViewHolder mostViewHolder = new EAdapter.MostViewHolder(view);
        return mostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MostViewHolder holder, int position) {

        final EHelperClass EHelperClass = mostLocations.get(position);
        holder.name.setText(EHelperClass.getName());
        holder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondactivity1 = new Intent(holder.preview.getContext(), Preview.class);
                secondactivity1.putExtra("name",EHelperClass.getName());
                secondactivity1.putExtra("url",EHelperClass.getUrl());
                holder.preview.getContext().startActivity(secondactivity1);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mostLocations.size();
    }

    public static class MostViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        Button preview;
        public MostViewHolder(@NonNull View itemView) {
            super(itemView);

            //hooks
            // imageView = itemView.findViewById(R.id.mimage);
            //price = itemView.findViewById(R.id.mprice);
            name = itemView.findViewById(R.id.name);
            preview = itemView.findViewById(R.id.preview);

            //oldprice = itemView.findViewById(R.id.moffer);

        }
    }
}

