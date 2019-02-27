package com.example.onlineagrimarket;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

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
                intent = new Intent(SellPage.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(SellPage.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(SellPage.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(SellPage.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(SellPage.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(SellPage.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;


        }return super.onOptionsItemSelected(item);
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
