package com.example.furniture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.furniture.List.Adapter_search;
import com.example.furniture.List.Model_search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LandingPage extends AppCompatActivity {

    RecyclerView recyclerView_cat;

    RecyclerView recyclerView_feat;
    Adapter_search adapter_search;

    Connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        recyclerView_feat = findViewById(R.id.feat);
        recyclerView_feat.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView_feat.setNestedScrollingEnabled(false);


        adapter_search = new Adapter_search(this, getMyList_feat());
        recyclerView_feat.setAdapter(adapter_search);

    }

    private ArrayList<Model_search> getMyList_feat()
    {
        ArrayList<Model_search> models = new ArrayList<>();

        Model_search m;

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {

                String query = "select Top 4 i.id, i.img1, p_name, company, discount_price, orignal_price, company from images i , products p1 where i.id=p1.id ORDER BY NEWID()";

                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);


                while(rs.next())
                {

                    Integer original_price_int = Integer.parseInt(rs.getString("orignal_price"));
                    Integer discounted_price_int = Integer.parseInt(rs.getString("discount_price"));

                    int off_percentage_int = ((original_price_int - discounted_price_int) * 100) / original_price_int;

                    m = new Model_search();
                    byte[] bytesImageDB = rs.getBytes("img1");
                    Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
                    m.setBitmap(bitmapImageDB);
                    m.setId(rs.getString("id"));
                    m.setTile(rs.getString("p_name"));
                    m.setDisc_price("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(discounted_price_int));
                    m.setPrice("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(original_price_int));
                    m.setDisc_per(off_percentage_int + " % OFF");
                    m.setCompany(rs.getString("company"));

                    models.add(m);
                }
            }
            else
            {
                Toast.makeText(LandingPage.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        return  models;
    }
}