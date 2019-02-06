package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellPage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page2);
    }
    Button btn;
    public void addListenerOnButton() {

        final Context context = this;

        btn = (Button) findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(SellPage2.this, LoginType.class);
                startActivity(intent);

            }

        });
    }
}
