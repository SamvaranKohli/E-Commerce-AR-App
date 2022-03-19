package com.example.att23.Similar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.att23.ItemClickListenser;
import com.example.att23.MainActivity;
import com.example.att23.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Holder>{

    Context c;
    ArrayList<Model> models;

    String[] mImageViews;

    public Adapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        //holder.mImageView.setImageResource(models.get(position).getImg());

        holder.mTitle.setText(models.get(position).getTitle());
        holder.mDec.setText(models.get(position).getCompany());
        holder.mPrice.setText(models.get(position).getPrice());
        holder.mDisc_price.setText(models.get(position).getDisc_price());
        holder.mImageView.setImageBitmap(models.get(position).getBitmap());
        holder.Disc_per.setText(models.get(position).getDisc_per());

        holder.mPrice.setPaintFlags(holder.mPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        holder.setItemClickListenser(new ItemClickListenser() {
            @Override
            public void onItemClickListener(View v, int position) {

                String id = models.get(position).getId();

                Intent intent = new Intent(c, MainActivity.class);
                intent.putExtra("id", id);

                c.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

