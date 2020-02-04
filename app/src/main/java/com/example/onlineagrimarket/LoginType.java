package com.example.onlineagrimarket;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class LoginType extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        addListenerOnButton();
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
        switch(item.getItemId())
        {
            case R.id.nav_home:
                intent = new Intent(LoginType.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(LoginType.this, About.class);
                startActivity(intent);
                return true;

            case R.id.nav_contact:
                intent = new Intent(LoginType.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(LoginType.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(LoginType.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_myhistory:
                intent = new Intent(LoginType.this, History.class);
                startActivity(intent);
                return true;

            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(LoginType.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            }return super.onOptionsItemSelected(item);
        }

    Button btn, btn2;

    public void addListenerOnButton() {

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

                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("isFiltered", "no");
                editor.apply();

                Intent intent = new Intent(LoginType.this, BuyPage.class);
                startActivity(intent);

            }

        });
    }
}