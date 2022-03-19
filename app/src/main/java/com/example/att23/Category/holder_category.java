package com.example.att23.Category;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.att23.ItemClickListenser;
import com.example.att23.R;

public class holder_category extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView cat_iv;
    TextView cat_name;

    ItemClickListenser itemClickListenser;

    public holder_category(@NonNull View itemView) {
        super(itemView);

        this.cat_iv = itemView.findViewById(R.id.cat_iv);
        this.cat_name = itemView.findViewById(R.id.cat_name);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        this.itemClickListenser.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListenser(ItemClickListenser ic)
    {
        this.itemClickListenser = ic;
    }
}
