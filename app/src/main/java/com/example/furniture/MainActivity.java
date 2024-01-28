package com.example.furniture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furniture.Similar.Adapter;
import com.example.furniture.Similar.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView imageView[];

    String[] mImagrURL;
    String id;
    String cat_id;

    TextView product_name;
    TextView discounted_price;
    TextView original_price;
    TextView off_percentage;
    TextView company_name;
    TextView product_description;
    TextView specifications;
    TextView dimensions;

    RecyclerView recyclerView;
    Adapter adapter_2;


    LinearLayout layout;
    CardView cardView;
    ImageView arrow;

    LinearLayout layout2;
    CardView cardView2;
    ImageView arrow2;

    Connection connect;

    Button ar_button;
    Button add_to_cart;

    ScrollView scrollView;

    CardView nav_bar;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        Access_Variables();

        image_slider();

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {
                String query = "select * from products where id = " + id;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);


                while(rs.next())
                {
                    Integer original_price_int = Integer.parseInt(rs.getString("orignal_price"));
                    Integer discounted_price_int = Integer.parseInt(rs.getString("discount_price"));

                    int off_percentage_int = ((original_price_int - discounted_price_int) * 100) / original_price_int;

                    product_name.setText(rs.getString("p_name"));
                    original_price.setText("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(original_price_int));
                    discounted_price.setText("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(discounted_price_int));
                    off_percentage.setText(off_percentage_int + " % OFF");
                    company_name.setText("By " + rs.getString("company"));
                    product_description.setText(rs.getString("product_disc"));

                    cat_id = rs.getString("category_id");

                }

                query = "select * from details where id = " + id;
                st = connect.createStatement();
                rs = st.executeQuery(query);

                while(rs.next())
                {
                    specifications.setText("Material        : " + rs.getString("material")
                            + "\nColor             : " + rs.getString("color")
                            + "\nFinish Type  : " + rs.getString("finish")
                            + "\nWarranty       : " + rs.getString("warranty"));

                    dimensions.setText("Length    : " + rs.getString("leng")
                            + "\nBreadth  : " + rs.getString("breath")
                            + "\nHeight    : " + rs.getString("height"));


                }

            }
            else
            {
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        set_similar_products();

        ar_button.setOnClickListener(v -> {

            Intent myIntent = new Intent(MainActivity.this, AR.class);
            myIntent.putExtra("key", id);
            MainActivity.this.startActivity(myIntent);

        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                if(scrollView.getScrollY() >= 1110)
                {
                    nav_bar.setCardBackgroundColor(Color.WHITE);
                    nav_bar.setCardElevation(10);

                }
                else
                {
                    nav_bar.setCardBackgroundColor(Color.TRANSPARENT);
                    nav_bar.setCardElevation(0);
                }

            }
        });


        ADD_TO_CART();

    }

    void ADD_TO_CART()
    {

        int temp = 0;

        String user_id = preferences.getString("Login", "false");

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {

                String query = "select * from cart where user_id = " + user_id;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {
                    if(rs.getString("product_id").equals(id))
                    {
                        add_to_cart.setText("ADDED TO CART");
                        add_to_cart.setBackgroundResource(R.drawable.button_added);
                        add_to_cart.setTextColor(getColor(R.color.black));
                        temp = 1;
                    }
                }
            }
            else
            {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        if(temp == 0) {
            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                        ConnectionHelper connectionHelper = new ConnectionHelper();

                        connect = connectionHelper.connectionclass();

                        if (connect != null)
                        {

                            String query = "insert into cart values(" + user_id + ", " + id + ", 1)";
                            Statement st = connect.createStatement();
                            st.executeUpdate(query);
                            add_to_cart.setText("ADDED TO CART");
                            add_to_cart.setBackgroundResource(R.drawable.button_added);
                            add_to_cart.setTextColor(getColor(R.color.black));

                        } else {
                            Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception ex) {

                    }

                }
            });
        }

    }

    void Access_Variables()
    {
        //textView = findViewById(R.id.tv);
        //textView2 = findViewById(R.id.tv2);

        product_name = findViewById(R.id.prod_name);
        discounted_price = findViewById(R.id.disc_price);
        original_price = findViewById(R.id.org_price);
        off_percentage = findViewById(R.id.off_per);
        company_name = findViewById(R.id.comp_name);
        product_description = findViewById(R.id.prod_desc);
        specifications = findViewById(R.id.specifications);
        dimensions = findViewById(R.id.dimensions);

        add_to_cart = findViewById(R.id.add_to_cart);

        scrollView = findViewById(R.id.sv);
        nav_bar = findViewById(R.id.nav_bar);

        original_price.setPaintFlags(original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        imageView = new ImageView[5];
        mImagrURL=  new String[5];

        layout = findViewById(R.id.showl);
        cardView = findViewById(R.id.prod_det);
        arrow =findViewById(R.id.arrow);

        layout2 = findViewById(R.id.show2);
        cardView2 = findViewById(R.id.prod_des);
        arrow2 =findViewById(R.id.arrow2);

        imageView[0] = findViewById(R.id.iv1);
        imageView[1] = findViewById(R.id.iv2);
        imageView[2] = findViewById(R.id.iv3);
        imageView[3] = findViewById(R.id.iv4);
        imageView[4] = findViewById(R.id.iv5);

        ar_button = findViewById(R.id.ar_button);
    }

    void image_slider()
    {
        ImageView iv;

        ArrayList<Bitmap> bitmapImageDB = new ArrayList<>();

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();



            if(connect != null)
            {
                String query = "select * from images where id = " + id;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {
                    for(int i = 2; i < 7; i++)
                    {
                        byte[] bytesImageDB = rs.getBytes(i);
                        bitmapImageDB.add(BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length));
                    }
                }

            }
            else
            {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        final ViewPager viewPager = findViewById(R.id.vp);
        ImageAdapter adapter = new ImageAdapter(this, bitmapImageDB);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                Integer count = viewPager.getCurrentItem();

                for (int i = 0; i < 5; i++)
                {
                    imageView[i].setImageResource(R.drawable.radio_normal);
                }

                imageView[count].setImageResource(R.drawable.radio_selected);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void set_similar_products()
    {
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(layoutManager);

        adapter_2 = new Adapter(this, getMyList());
        recyclerView.setAdapter(adapter_2);
    }

    private ArrayList<Model> getMyList()
    {
        ArrayList<Model> models = new ArrayList<>();

        Model m;

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();

            connect = connectionHelper.connectionclass();

            if(connect != null)
            {

                String query = "select Top 2 i.id, i.img1, p_name, company, discount_price, orignal_price, category_id, company from images i , products p1 where i.id=p1.id and category_id = " + cat_id + " and p1.id != " + id + " ORDER BY NEWID()";

                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);


                while(rs.next())
                {

                    Integer original_price_int = Integer.parseInt(rs.getString("orignal_price"));
                    Integer discounted_price_int = Integer.parseInt(rs.getString("discount_price"));

                    int off_percentage_int = ((original_price_int - discounted_price_int) * 100) / original_price_int;

                    m = new Model();
                    byte[] bytesImageDB = rs.getBytes("img1");
                    Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
                    m.setBitmap(bitmapImageDB);
                    m.setId(rs.getString("id"));
                    m.setTitle(rs.getString("p_name"));
                    m.setDisc_price("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(discounted_price_int));
                    m.setPrice("₹ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(original_price_int));
                    m.setCompany(rs.getString("company"));
                    m.setDisc_per(off_percentage_int + " % OFF");
                    models.add(m);
                }
            }
            else
            {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

        }

        return  models;
    }

    public void ShowMore(View view)
    {
        arrow2.setImageResource(R.drawable.down);
        TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
        layout2.setVisibility(View.GONE);

        if(layout.getVisibility() == View.GONE)
        {
            arrow.setImageResource(R.drawable.up);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            layout.setVisibility(View.VISIBLE);
        }
        else
        {
            arrow.setImageResource(R.drawable.down);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            layout.setVisibility(View.GONE);
        }
    }

    public void ShowMore2(View view)
    {
        arrow.setImageResource(R.drawable.down);
        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
        layout.setVisibility(View.GONE);

        if(layout2.getVisibility() == View.GONE)
        {
            arrow2.setImageResource(R.drawable.up);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            layout2.setVisibility(View.VISIBLE);
        }
        else
        {
            arrow2.setImageResource(R.drawable.down);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            layout2.setVisibility(View.GONE);
        }
    }
}
