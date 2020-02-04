package com.example.onlineagrimarket;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class BuyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        showFeed();
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
                    intent = new Intent(BuyPage.this, LoginType.class);
                    startActivity(intent);
                    return true;

                case R.id.nav_about :
                    intent = new Intent(BuyPage.this, About.class);
                    startActivity(intent);
                    return true;


                case R.id.nav_contact:
                    intent = new Intent(BuyPage.this, Contact.class);
                    startActivity(intent);
                    return true;

                case R.id.nav_profile:
                    intent = new Intent(BuyPage.this, Profile.class);
                    startActivity(intent);
                    return true;

                case R.id.nav_myposts:
                    intent = new Intent(BuyPage.this, MyPosts.class);
                    startActivity(intent);
                    return true;

                case R.id.nav_myhistory:
                    intent = new Intent(BuyPage.this, History.class);
                    startActivity(intent);
                    return true;

                case R.id.nav_logout:
                    SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                    intent = new Intent(BuyPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;


            }return super.onOptionsItemSelected(item);
    }





    private LinearLayout linearLayout;
    private CardView cardView;
    private TextView textView, partView;
    private String isFiltered;
    public String phone;
    Button call;
    int fq=0,tq=0;
    ImageView imgView;

    private void showFeed()
    {
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        isFiltered = sharedpreferences.getString("isFiltered","");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("Feeds").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for ( DocumentSnapshot ds : queryDocumentSnapshots) {
                        String commodity = ds.getString("Commodity");
                        String location = ds.getString("Location");
                        String quality = ds.getString("Quality");
                        String quantity = ds.getString("Quantity");
                        int quan = Integer.parseInt(quantity);
                        String variety = ds.getString("Variety");
                        String name = ds.getString("Seller");
                        String status = ds.getString("Status");
                        phone = ds.getString("Contact");

                        String image = ds.getString("Image");
                        Uri imgUri = Uri.parse(image);

                        if(status.equals("onDeal")) {
                            if (isFiltered.equals("no")) {

                                linearLayout = findViewById(R.id.rootLayout);
                                linearLayout.setPadding(20, 10, 20, 10);

                                cardView = new CardView(BuyPage.this);
                                cardView.setLayoutParams(new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT));
                                cardView.setRadius(20);
                                cardView.setElevation(4);
                                cardView.setCardBackgroundColor(0xFF6B6A6A);

                                imgView = new ImageView(BuyPage.this);
                                imgView.setImageURI(imgUri);
                                imgView.setPadding(0,150,0,0);
                                imgView.setCropToPadding(true);
                                imgView.setLayoutParams(new CardView.LayoutParams(1000, 1000));

                                textView = new TextView(BuyPage.this);
                                textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                textView.setGravity(Gravity.CENTER);
                                textView.setPadding(250, 1000, 0, 15);
                                textView.setTextColor(ColorStateList.valueOf(Color.WHITE));
                                textView.setText("Seller :" + name + "\nCommodity : " + commodity + "\nVariety : " + variety + "\nQuality : " + quality + "\nQuantity : " + quantity + " quintals" + "\nLocation : " + location + "\nContact : " + phone);

                                partView = new TextView(BuyPage.this);
                                partView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                partView.setGravity(Gravity.CENTER);
                                partView.setText(" ");

                                final String ph = phone;
                                call = new Button(BuyPage.this);
                                call.setText("Call Seller");
                                call.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:"+ph));
                                        startActivity(intent);
                                    }
                                });

                                if (linearLayout != null) {
                                    linearLayout.addView(cardView);
                                    cardView.addView(textView);
                                    cardView.addView(call);
                                    cardView.addView(imgView);
                                    linearLayout.addView(partView);
                                }

                            } else {
                                Intent intent = getIntent();

                                String comm = intent.getStringExtra("comm");
                                Log.i("lol", comm);
                                String var = intent.getStringExtra("var");
                                String fquan = intent.getStringExtra("fquan");
                                String tquan = intent.getStringExtra("tquan");
                                String qual = intent.getStringExtra("qual");
                                String loc = intent.getStringExtra("loc");
                                if ((comm.equals(commodity) || comm.equals("Select")) &&
                                        (var.equals(variety) || var.equals("Select")) &&
                                        (qual.equals(quality) || qual.equals("Select")) &&
                                        (loc.equals(location) || loc.equals("Select"))) {
                                    if (fquan.equals("Select") && tquan.equals("Select")) {

                                        linearLayout = findViewById(R.id.rootLayout);
                                        linearLayout.setPadding(20, 10, 20, 10);

                                        cardView = new CardView(BuyPage.this);
                                        cardView.setLayoutParams(new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        cardView.setRadius(20);
                                        cardView.setElevation(4);
                                        cardView.setCardBackgroundColor(0xFF6B6A6A);

                                        imgView = new ImageView(BuyPage.this);
                                        imgView.setImageURI(imgUri);
                                        imgView.setPadding(0,150,0,0);
                                        imgView.setCropToPadding(true);
                                        imgView.setLayoutParams(new CardView.LayoutParams(1000, 1000));

                                        textView = new TextView(BuyPage.this);
                                        textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        textView.setGravity(Gravity.CENTER);
                                        textView.setPadding(250, 1000, 0, 15);
                                        textView.setTextColor(ColorStateList.valueOf(Color.WHITE));
                                        textView.setText("Seller :" + name + "\nCommodity : " + commodity + "\nVariety : " + variety + "\nQuality : " + quality + "\nQuantity : " + quantity + " quintals" + "\nLocation : " + location + "\nContact : " + phone);

                                        partView = new TextView(BuyPage.this);
                                        partView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        partView.setGravity(Gravity.CENTER);
                                        partView.setText(" ");

                                        call = new Button(BuyPage.this);
                                        call.setText("Call Seller");
                                        call.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                intent.setData(Uri.parse("tel:" + phone));
                                                startActivity(intent);
                                            }
                                        });

                                        if (linearLayout != null) {
                                            linearLayout.addView(cardView);
                                            cardView.addView(textView);
                                            cardView.addView(call);
                                            cardView.addView(imgView);
                                            linearLayout.addView(partView);
                                        }
                                    } else {
                                        if(!fquan.equals("Select")) {
                                            fq = Integer.parseInt(fquan);
                                            tq = Integer.parseInt(tquan);
                                        }


                                        if (quan > fq && quan < tq) {
                                            linearLayout = findViewById(R.id.rootLayout);
                                            linearLayout.setPadding(20, 10, 20, 10);
                                            cardView = new CardView(BuyPage.this);
                                            cardView.setLayoutParams(new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            cardView.setRadius(20);
                                            cardView.setElevation(4);
                                            cardView.setCardBackgroundColor(0xFF6B6A6A);

                                            imgView = new ImageView(BuyPage.this);
                                            imgView.setImageURI(imgUri);
                                            imgView.setCropToPadding(true);
                                            imgView.setPadding(0,150,0,0);
                                            imgView.setLayoutParams(new CardView.LayoutParams(1000, 1000));

                                            textView = new TextView(BuyPage.this);
                                            textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            textView.setGravity(Gravity.CENTER);
                                            textView.setPadding(250, 1000, 0, 15);
                                            textView.setTextColor(ColorStateList.valueOf(Color.WHITE));
                                            textView.setText("Seller :" + name + "\nCommodity : " + commodity + "\nVariety : " + variety + "\nQuality : " + quality + "\nQuantity : " + quantity + " quintals" + "\nLocation : " + location + "\nContact : " + phone);

                                            call = new Button(BuyPage.this);
                                            call.setText("Call Seller");
                                            call.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                                    intent.setData(Uri.parse("tel:" + phone));
                                                    startActivity(intent);
                                                }
                                            });

                                            partView = new TextView(BuyPage.this);
                                            partView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            partView.setGravity(Gravity.CENTER);
                                            partView.setText(" ");

                                            if (linearLayout != null) {
                                                linearLayout.addView(cardView);
                                                cardView.addView(textView);
                                                cardView.addView(call);
                                                cardView.addView(imgView);
                                                linearLayout.addView(partView);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
    }

    Button btn;
    public void addListenerOnButton()
    {
        btn = findViewById(R.id.button9);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BuyPage.this, Filter.class);
                startActivity(intent);
            }
        });
    }
}
