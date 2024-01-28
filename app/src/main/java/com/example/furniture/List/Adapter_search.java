package com.example.furniture.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniture.ItemClickListenser;
import com.example.furniture.MainActivity;
import com.example.furniture.R;

import java.util.ArrayList;

public class Adapter_search extends RecyclerView.Adapter<Holder_search> {

    Context c;
    ArrayList<Model_search> models;

    String[] mImageViews;

    public Adapter_search(Context c, ArrayList<Model_search> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public Holder_search onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, null);
        return new Holder_search(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_search holder, int position) {

        //holder.mImageView.setImageResource(models.get(position).getImg());

        //holder.row2_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.row2_iv.setImageBitmap(models.get(position).getBitmap());
        holder.title.setText(models.get(position).getTitle());
        holder.disc_price.setText(models.get(position).getDisc_price());
        holder.price.setText(models.get(position).getPrice());
        holder.disc_per.setText(models.get(position).getDisc_per());
        holder.company.setText(models.get(position).getCompany());

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

