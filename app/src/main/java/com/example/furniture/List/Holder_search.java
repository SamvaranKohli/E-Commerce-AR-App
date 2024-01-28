package com.example.furniture.List;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniture.ItemClickListenser;
import com.example.furniture.R;

public class Holder_search extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView row2_iv;

    String mId;

    TextView title;
    TextView disc_price;
    TextView price;
    TextView disc_per;
    TextView company;

    ItemClickListenser itemClickListenser;

    public Holder_search(@NonNull View itemView) {
        super(itemView);

        this.row2_iv = itemView.findViewById(R.id.iv_);
        this.title = itemView.findViewById(R.id.title);
        this.disc_price = itemView.findViewById(R.id.disc_price);
        this.price = itemView.findViewById(R.id.price);
        this.disc_per = itemView.findViewById(R.id.disc_per);
        this.company = itemView.findViewById(R.id.company);

        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

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

