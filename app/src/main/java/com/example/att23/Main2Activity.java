package com.example.att23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.att23.List.Adapter_search;
import com.example.att23.List.Model_search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_search adapter;

    Connection connect;

    ImageButton searchbutton;

    String value;
    String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        value = intent.getStringExtra("key");

        cat_id = intent.getStringExtra("cat_id");

        searchbutton = findViewById(R.id.search);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new Adapter_search(this, getMyList());
        recyclerView.setAdapter(adapter);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Main2Activity.this, SearchView.class);
                Main2Activity.this.startActivity(myIntent);

            }
        });

    }

    private ArrayList<Model_search> getMyList()
    {
        ArrayList<Model_search> models = new ArrayList<>();

        Model_search m;

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {
                String query = "null";

                if(value != null)
                {
                    query = "select i.id, i.img1, p_name, company, discount_price, orignal_price, company from images i , products p1 where i.id=p1.id and (p_name LIKE '%" + value + "%' or company LIKE '%" + value + "%')";
                }
                else if(cat_id != null)
                {
                    query = "select i.id, i.img1, p_name, company, discount_price, orignal_price, category_id, company from images i , products p1 where i.id=p1.id and category_id = " + cat_id;
                }

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
                Toast.makeText(Main2Activity.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        return  models;
    }
}