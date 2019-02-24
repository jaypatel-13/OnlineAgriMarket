package com.example.onlineagrimarket;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class BuyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        showFeed();
        toggleMenu();
    }
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;


    private void toggleMenu()
    {
        mDrawer = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            switch(item.getItemId()){
                case R.id.nav_home:
                    Intent intent = new Intent(BuyPage.this, LoginType.class);
                    startActivity(intent);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private LinearLayout linearLayout;
    private CardView cardView;
    private TextView textView,commView,locView, qualView, quanView, varView, partView, contView, sellView;
    private void showFeed()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Feeds").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String commodity = ds.getString("Commodity");
                    String location = ds.getString("Location");
                    String quality = ds.getString("Quality");
                    String quantity = ds.getString("Quantity");
                    String variety = ds.getString("Variety");
                    String name = ds.getString("Seller");
                    String phone = ds.getString("Contact");


                    linearLayout = findViewById(R.id.rootLayout);

                    cardView = new CardView(BuyPage.this);
                    cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    cardView.setRadius(20);
                    cardView.setElevation(4);
                    cardView.setCardBackgroundColor(0xff2ecc71);

                    textView = new TextView(BuyPage.this);
                    textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(50,15,0,15);
                    textView.setText("Seller :" + name + "\nCommodity : " + commodity + "\nVariety : " +variety + "\nQuality : " + quality + "\nQuantity : " + quantity +  " quintals" + "\nLocation : " + location + "\nContact : " + phone);

                    partView = new TextView(BuyPage.this);
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
        });
    }
}
