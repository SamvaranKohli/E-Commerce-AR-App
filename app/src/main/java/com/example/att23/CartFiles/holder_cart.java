package com.example.att23.CartFiles;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.att23.ItemClickListenser;
import com.example.att23.R;

public class holder_cart extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImageView;
    TextView mTitle, mPrice, mDisc_price, off_per, mQty, company;
    ImageButton delete;

    String mId;

    ItemClickListenser itemClickListenser;

    public holder_cart(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.iv);
        this.mTitle = itemView.findViewById(R.id.title);
        this.mDisc_price = itemView.findViewById(R.id.disc_price);
        this.mPrice = itemView.findViewById(R.id.org_price);
        this.company = itemView.findViewById(R.id.company);
        this.mQty = itemView.findViewById(R.id.qty);
        this.off_per = itemView.findViewById(R.id.off_per);
        this.delete = itemView.findViewById(R.id.delete);

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
