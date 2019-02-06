package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        addListenerOnButton();
    }

    Button btn, btn2;
    public void addListenerOnButton() {

        final Context context = this;

        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(LoginType.this, SellPage.class);
                startActivity(intent);

            }

        });
        btn2 = findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(LoginType.this, BuyPage.class);
                startActivity(intent);

            }

        });
    }
}
