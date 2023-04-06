package com.example.kauppalista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private Button addButton, listOrderTimeButton, listOrderAlphabetButton, listOriginalButton;
    private ArrayList<Product> data;
    private EditText editNewProductName;
    private TextView orderText;
    private int whichOrder = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        orderText = findViewById(R.id.orderTextView);
        addButton = findViewById(R.id.addButton);
        editNewProductName = findViewById(R.id.editTextNewProduct);
        listOrderAlphabetButton = findViewById(R.id.listAlphabetOrderBtn);
        listOrderTimeButton = findViewById(R.id.listTimeOrderBtn);
        listOriginalButton = findViewById(R.id.listOriginalOrderBtn);
        context = MainActivity.this;

        Storage s = Storage.getInstance();
        s.loadProducts(context);
        data = s.getProducts();

        recyclerView = findViewById(R.id.rvProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProductListAdapter(getApplicationContext(), data, this));

        addButton.setOnClickListener(view -> {
            if(editNewProductName.getText().toString().isEmpty()){
                editNewProductName.setTextColor(Color.RED);
                editNewProductName.setText("Lisää nimi!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editNewProductName.setText("");
                        editNewProductName.setTextColor(Color.BLACK);
                    }
                }, 1500);
            }else {
                s.addProduct(new Product(editNewProductName.getText().toString()));
                editNewProductName.setText("");
                checkOrder();
                recyclerView.getAdapter().notifyDataSetChanged();
                s.saveProducts(context);
            }
        });


        // Products in alphabetical order
        listOrderAlphabetButton.setOnClickListener(view -> {
            whichOrder = 0;
            Collections.sort(data, new Product.NameComparator());
            recyclerView.getAdapter().notifyDataSetChanged();
            orderText.setText("Järjestys: Aakkosjärjestys");
        });

        // Most recently added product is shown first --> time order
        listOrderTimeButton.setOnClickListener(view -> {
            whichOrder = 1;
            Collections.sort(data, new Product.TimeComparator());
            recyclerView.getAdapter().notifyDataSetChanged();
            orderText.setText("Järjestys: Aikajärjestys");
        });

        // Back to original order (most recent to last)
        listOriginalButton.setOnClickListener(view -> {
            whichOrder = 2;
            Collections.sort(data, new Product.OriginalComparator());
            recyclerView.getAdapter().notifyDataSetChanged();
            orderText.setText("Järjestys: Alkuperäinen");
        });
    }

    public void checkOrder(){
        if (whichOrder == 0){
            Collections.sort(data, new Product.NameComparator());
        }else if(whichOrder == 1){
            Collections.sort(data, new Product.TimeComparator());
        }else if(whichOrder == 2){
            Collections.sort(data, new Product.OriginalComparator());
        }
    }
}
