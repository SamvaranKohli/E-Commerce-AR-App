package com.example.att23.CartFiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.att23.Cart;
import com.example.att23.ConnectionHelper;
import com.example.att23.ItemClickListenser;
import com.example.att23.MainActivity;
import com.example.att23.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_cart extends RecyclerView.Adapter<holder_cart> {

    Context c;
    ArrayList<model_cart> models;

    String[] mImageViews;

    Connection connect;

    SharedPreferences preferences;

    String user_id;

    public Adapter_cart(Context c, ArrayList<model_cart> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public holder_cart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rv, null);
        return new holder_cart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder_cart holder, int position) {

        holder.mImageView.setImageBitmap(models.get(position).getBitmap());
        holder.mTitle.setText(models.get(position).getTitle());
        holder.company.setText(models.get(position).getCompany());
        holder.mPrice.setText(models.get(position).getPrice());
        holder.mDisc_price.setText(models.get(position).getDisc_price());
        holder.off_per.setText(models.get(position).getOff_per());
        holder.mQty.setText(models.get(position).getQty());

        preferences = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        user_id = preferences.getString("Login", "false");

        holder.mPrice.setPaintFlags(holder.mPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        String id = models.get(position).getId();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    ConnectionHelper connectionHelper = new ConnectionHelper();

                    connect = connectionHelper.connectionclass();

                    if(connect != null)
                    {

                        String query = "delete from cart where user_id = " + user_id + " and product_id = " + id;
                        Statement st = connect.createStatement();
                        st.executeUpdate(query);

                        Intent i = new Intent(c.getApplicationContext(), Cart.class);
                        ((Activity)c).finish();
                        c.startActivity(i);

                    }
                    else {
                        Toast.makeText(c.getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {

                }

            }
        });


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
