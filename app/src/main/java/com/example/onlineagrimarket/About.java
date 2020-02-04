package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout linearLayout = findViewById(R.id.rootLayout);

        TextView textView = new TextView(About.this);
        textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(250, 100, 0, 15);
        textView.setText("\n VISION \n "+"\nThe Vision of this project is to ensure fair \n"+"\nprice to the farming community by devising \n" +
                "\tnew techniques and by making use of online market. An application, that serves as a platform for\n" +
                "\tmovement of agricultural products from the farms directly to the manufacturers or retailers. This \n" +
                "\tmobile application provides privilege for both farmers and manufacturers or retailers to buy \n" +
                "\tand sell the required farm products without the involvement of a middleman at its right profitable \n" +
                "\tprice. The agriculture experts shall analyze the product that comes into this platform, approve it\n" +
                "\tand provide ratings based on quality. This makes all the available farm products easily accessible. \n" +
                "\tHence it provides freedom of pricing and freedom of access. Through this we can ensure farmers to \n" +
                "\tmake selling decisions most advantageously.");
        linearLayout.addView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }
    Intent intent;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                intent = new Intent(About.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(About.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(About.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(About.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myhistory:
                intent = new Intent(About.this, History.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(About.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(About.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;


        }return super.onOptionsItemSelected(item);
    }

}
