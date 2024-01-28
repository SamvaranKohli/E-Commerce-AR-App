package com.example.furniture.Similar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniture.ItemClickListenser;
import com.example.furniture.R;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImageView;
    TextView mTitle, mPrice, mDisc_price, mDec, Disc_per;

    String mId;

    ItemClickListenser itemClickListenser;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.iv);
        this.mTitle = itemView.findViewById(R.id.title);
        this.mDec = itemView.findViewById(R.id.desc);
        this.mPrice = itemView.findViewById(R.id.price);
        this.mDisc_price = itemView.findViewById(R.id.disc_price);
        this.Disc_per = itemView.findViewById(R.id.price_desc);

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

