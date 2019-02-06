package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        findViews();
        addListenerOnButton();
    }

    Button btn;
    Spinner commodity, variety, quality, location;
    EditText quantity;

    private void findViews()
    {
        commodity = findViewById(R.id.spinner);
        variety = findViewById(R.id.spinner2);
        quality = findViewById(R.id.spinner3);
        location = findViewById(R.id.spinner4);
        quantity = findViewById(R.id.editText4);
        btn = findViewById(R.id.button3);
    }

    public void addListenerOnButton() {

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String comm = commodity.getSelectedItem().toString().trim();
                String var = variety.getSelectedItem().toString().trim();
                String qual = quality.getSelectedItem().toString().trim();
                String quan = quantity.getText().toString().trim();
                String loc = location.getSelectedItem().toString().trim();

                Intent intent = new Intent(SellPage.this, SellPage2.class);
                intent.putExtra("comm",comm);
                intent.putExtra("var",var);
                intent.putExtra("qual",qual);
                intent.putExtra("quan",quan);
                intent.putExtra("loc",loc);
                startActivity(intent);
            }
        });
    }
}
