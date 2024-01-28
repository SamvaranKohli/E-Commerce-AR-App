package com.example.furniture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchView extends AppCompatActivity {

    Connection connect;
    EditText editText;
    LinearLayout linear;

    ListView listView;

    ArrayList<String> list;

    ArrayAdapter<String> adapter;

    ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        editText = findViewById(R.id.ed);
        linear = findViewById(R.id.lv);
        listView = findViewById(R.id.listview);

        searchButton = findViewById(R.id.search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(SearchView.this, editText.getText(), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(SearchView.this, productListing.class);
                myIntent.putExtra("key", editText.getText().toString());
                SearchView.this.startActivity(myIntent);

            }
        });

        searchView();
    }

    void searchView()
    {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                list = new ArrayList<>();

                if(editText.length() != 0)
                {
                    try {

                        ConnectionHelper connectionHelper = new ConnectionHelper();

                        connect = connectionHelper.connectionclass();

                        if(connect != null)
                        {
                            String query = "select DISTINCT company from products where company LIKE '%" + editText.getText().toString() + "%'";
                            Statement st = connect.createStatement();
                            ResultSet rs = st.executeQuery(query);


                            while(rs.next())
                            {
                                //Toast.makeText(MainActivity.this, rs.getString(1), Toast.LENGTH_SHORT).show();
                                list.add(rs.getString(1));

                                //adapter.getFilter().filter(rs.getString(1));

                            }

                            query = "select DISTINCT p_name from products where p_name LIKE '" + editText.getText().toString() + "%' or p_name LIKE '%" + editText.getText().toString() + "%' or p_name LIKE '%" + editText.getText().toString() + "'";
                            st = connect.createStatement();
                            rs = st.executeQuery(query);


                            while(rs.next())
                            {
                                //Toast.makeText(MainActivity.this, rs.getString(1), Toast.LENGTH_SHORT).show();
                                list.add(rs.getString(1));
                                //adapter.getFilter().filter(rs.getString(1));

                            }
                        }
                        else
                        {
                            Toast.makeText(SearchView.this, "Hello", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception ex) {

                    }
                }
                else
                {
                    list.clear();
                }

                UpdateSearchView();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(SearchView.this, list.get(position), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(SearchView.this, productListing.class);
                myIntent.putExtra("key", list.get(position));
                SearchView.this.startActivity(myIntent);

            }
        });
    }

    void UpdateSearchView()
    {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
