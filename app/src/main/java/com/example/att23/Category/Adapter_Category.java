package com.example.att23.Category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.att23.ItemClickListenser;
import com.example.att23.Main2Activity;
import com.example.att23.R;

import java.util.ArrayList;

public class Adapter_Category extends RecyclerView.Adapter<holder_category> {

    Context c;
    ArrayList<Model_category> models;

    String[] mImageViews;

    public Adapter_Category(Context c, ArrayList<Model_category> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public holder_category onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, null);
        return new holder_category(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder_category holder, int position) {

        holder.cat_iv.setImageBitmap(models.get(position).getBitmap());
        holder.cat_name.setText(models.get(position).getTitle());

        holder.setItemClickListenser(new ItemClickListenser() {
            @Override
            public void onItemClickListener(View v, int position) {

                String id = models.get(position).getId();

                Intent intent = new Intent(c, Main2Activity.class);
                intent.putExtra("cat_id", id);

                c.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

