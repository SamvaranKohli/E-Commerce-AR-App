package com.example.att23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.att23.CartFiles.Adapter_cart;
import com.example.att23.CartFiles.model_cart;
import com.example.att23.Similar.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_cart adapter_2;

    //ImageView imageView;

    Connection connect;
    SharedPreferences preferences;

    TextView total_items;
    TextView total_tems_2;

    TextView cart_total;
    TextView cart_total_2;

    String user_id;

    LinearLayout empty_layout;
    Button go_to_shop;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //imageView = findViewById(R.id.iv);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        user_id = preferences.getString("Login", "false");

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter_2 = new Adapter_cart(this, getMyList());
        recyclerView.setAdapter(adapter_2);

        total_items = findViewById(R.id.total_items);
        total_tems_2  = findViewById(R.id.total_items_2);

        cart_total = findViewById(R.id.cart_total);
        cart_total_2 = findViewById(R.id.cart_total_2);

        empty_layout = findViewById(R.id.empty_layout);
        go_to_shop = findViewById(R.id.go_to_shop);

        checkout = findViewById(R.id.checkout);

        get_total_items();
        get_Cart_total();
    }

    void get_Cart_total()
    {
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {

                String query = "select SUM(discount_price) as total from cart c, products p where c.user_id = " + user_id + " and p.id = c.product_id;";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {
                    cart_total.setText("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(Integer.parseInt(rs.getString("total"))));
                    cart_total_2.setText("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(Integer.parseInt(rs.getString("total"))));
                }

            }
            else {
                Toast.makeText(Cart.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }
    }

    void get_total_items()
    {
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {

                String query = "select COUNT(c.quantity) as total from cart c, products p where c.user_id = " + user_id + " and p.id = c.product_id;";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {
                    total_items.setText(rs.getString("total") + " items");
                    total_tems_2.setText("Price Details (" + rs.getString("total") + " items)");

                    if(Integer.parseInt(rs.getString("total")) == 0)
                    {
                        empty_layout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        go_to_shop.findViewById(View.VISIBLE);
                        checkout.setVisibility(View.GONE);
                    }
                    else
                    {
                        empty_layout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        go_to_shop.setVisibility(View.GONE);
                        checkout.setVisibility(View.VISIBLE);
                    }
                }

            }
            else {
                Toast.makeText(Cart.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }
    }

    private ArrayList<model_cart> getMyList()
    {

        ArrayList<model_cart> models = new ArrayList<>();

        model_cart m;

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {

                String query = "select * from cart c , products p, images i where c.user_id = " + user_id + " and p.id = c.product_id and i.id = c.product_id;";

                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {

                   Integer original_price_int = Integer.parseInt(rs.getString("orignal_price"));
                   Integer discounted_price_int = Integer.parseInt(rs.getString("discount_price"));

                   int off_percentage_int = ((original_price_int - discounted_price_int) * 100) / original_price_int;

                    m = new model_cart();
                    byte[] bytesImageDB = rs.getBytes("img1");
                    Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
                    m.setBitmap(bitmapImageDB);
                    m.setId(rs.getString("id"));
                    m.setTitle(rs.getString("p_name"));
                    m.setDisc_price("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(discounted_price_int));
                    m.setPrice("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(original_price_int));
                    m.setCompany(rs.getString("company"));
                    m.setOff_per(off_percentage_int + " % OFF");
                    m.setQty(rs.getString("quantity"));
                    models.add(m);
                }
            }
            else {
                Toast.makeText(Cart.this, "Hello", Toast.LENGTH_SHORT).show();
             }

        } catch (Exception ex) {

        }

        return  models;

    }
}