package com.example.furniture;

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

import com.example.furniture.List.Adapter_search;
import com.example.furniture.List.Model_search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class productListing extends AppCompatActivity {

    RecyclerView productListingRecyclerView;
    Adapter_search productListingAdapter;

    Connection connect;
    ImageButton searchButton;
    String searchString;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        // get the search value if, listing page is opened after searching
        Intent intent = getIntent();
        searchString = intent.getStringExtra("key");

        // set xml id
        searchButton = findViewById(R.id.search);

        productListingRecyclerView = findViewById(R.id.rv);
        productListingRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productListingAdapter = new Adapter_search(this, getProductList());
        productListingRecyclerView.setAdapter(productListingAdapter);

        // set search button functionality
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open search page
                Intent myIntent = new Intent(productListing.this, SearchView.class);
                productListing.this.startActivity(myIntent);

            }
        });

    }

    // set product list for recycler view
    private ArrayList<Model_search> getProductList()
    {
        ArrayList<Model_search> models = new ArrayList<>();

        Model_search m;

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {
                String query;

                if(searchString != null)
                {
                    query = "select i.id, i.img1, p_name, company, discount_price, orignal_price, company from images i , products p1 where i.id=p1.id and (p_name LIKE '%" + searchString + "%' or company LIKE '%" + searchString + "%')";
                }
                else
                {
                    query = "select i.id, i.img1, p_name, company, discount_price, orignal_price, company from images i , products p1 where i.id=p1.id";
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
                Toast.makeText(productListing.this, "No connection", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        return  models;
    }
}