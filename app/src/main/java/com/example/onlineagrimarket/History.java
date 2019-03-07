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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getData();
        showFeed();
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
                intent = new Intent(History.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(History.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(History.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(History.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(History.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_myhistory:
                intent = new Intent(History.this, History.class);
                startActivity(intent);
                return true;


            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(History.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;


        }return super.onOptionsItemSelected(item);
    }



    String phoneNumber;
    private void getData()
    {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        phoneNumber = sharedpreferences.getString("phoneKey","");

    }

    private LinearLayout linearLayout;
    private CardView cardView;
    private TextView textView, partView, posts;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void showFeed() {

        linearLayout = findViewById(R.id.rootLayout3);
        linearLayout.setPadding(20, 0, 20, 0);

        posts = new TextView(History.this);
        posts.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        posts.setText("\nMy History\n");
        posts.setGravity(Gravity.CENTER);
        posts.setTextSize(24);
        linearLayout.addView(posts);

        db.collection("Feeds").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (final DocumentSnapshot ds : queryDocumentSnapshots) {
                    final String commodity = ds.getString("Commodity");
                    final String location = ds.getString("Location");
                    final String quality = ds.getString("Quality");
                    final String quantity = ds.getString("Quantity");
                    final String variety = ds.getString("Variety");
                    String deal = ds.getString("Status");
                    final String phone = ds.getString("Contact");

                    if (deal.equals("doneDeal")) {
                        if (phoneNumber.equals(phone)) {

                            cardView = new CardView(History.this);
                            cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            cardView.setRadius(20);
                            cardView.setElevation(4);
                            cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.DKGRAY));


                            textView = new TextView(History.this);
                            textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextColor(ColorStateList.valueOf(Color.WHITE));
                            textView.setPadding(50, 15, 0, 15);
                            textView.setText("\nCommodity : " + commodity + "\nVariety : " + variety + "\nQuality : " + quality + "\nQuantity : " + quantity + " quintals" + "\nLocation : " + location);

                            partView = new TextView(History.this);
                            partView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            partView.setGravity(Gravity.CENTER);
                            partView.setText(" ");

                            if (linearLayout != null) {
                                linearLayout.addView(cardView);
                                cardView.addView(textView);
                                linearLayout.addView(partView);
                            }
                        }
                    }
                }
            }
        });
    }
}
