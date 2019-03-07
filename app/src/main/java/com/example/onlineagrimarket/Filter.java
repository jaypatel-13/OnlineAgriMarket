package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        findViews();
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

        switch(item.getItemId()){
            case R.id.nav_home:
                intent = new Intent(Filter.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(Filter.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(Filter.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(Filter.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(Filter.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_myhistory:
                intent = new Intent(Filter.this, History.class);
                startActivity(intent);
                return true;


            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(Filter.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;


        }return super.onOptionsItemSelected(item);
    }


    Spinner comm, var, qual, fquan, tquan, loc;
    Button btn;

    public void findViews()
    {
        comm = findViewById(R.id.spinner5);
        var = findViewById(R.id.spinner6);
        tquan = findViewById(R.id.spinner7);
        fquan = findViewById(R.id.spinner13);
        qual = findViewById(R.id.spinner8);
        loc = findViewById(R.id.spinner9);
        btn = findViewById(R.id.button10);
    }

    public void addListenerOnButton()
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("isFiltered", "yes");
                editor.apply();

                String commodity = comm.getSelectedItem().toString().trim();
                Log.i("looooool",commodity);
                String variety = var.getSelectedItem().toString().trim();
                String fquantity = fquan.getSelectedItem().toString().trim();
                String tquantity = tquan.getSelectedItem().toString().trim();
                String quality = qual.getSelectedItem().toString().trim();
                String location = loc.getSelectedItem().toString().trim();

                int tq = 0,fq = 0;
                if(!fquantity.equals("Select") && !tquantity.equals("Select")) {
                    tq = Integer.parseInt(tquantity);

                    fq = Integer.parseInt(fquantity);
                }

                if((tq<fq)&& (!fquantity.equals("Select")))
                {
                    Toast.makeText(Filter.this,"Invalid Quantity", Toast.LENGTH_SHORT).show();
                }
                else {
                    intent = new Intent(Filter.this, BuyPage.class);

                    intent.putExtra("comm", commodity);
                    intent.putExtra("var", variety);
                    intent.putExtra("fquan", fquantity);
                    intent.putExtra("tquan", tquantity);
                    intent.putExtra("qual", quality);
                    intent.putExtra("loc", location);

                    startActivity(intent);
                }
            }
        });
    }
}
