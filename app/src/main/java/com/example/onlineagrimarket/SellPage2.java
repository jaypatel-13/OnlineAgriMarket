package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SellPage2 extends AppCompatActivity {
    private static final String TAG = "SellPage2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page2);
        getData();
        startDatabase();
        addListenerOnButton();
   //     usr = new User();
    }
    //User usr;
    private FirebaseFirestore db;
    Button btn;
    String commodity, variety, quantity, quality, location;
    String firstName, lastName, phoneNumber;
    private void getData()
    {
        Intent intent = getIntent();
        commodity = intent.getStringExtra("comm");
        variety = intent.getStringExtra("var");
        quality = intent.getStringExtra("qual");
        quantity = intent.getStringExtra("quan");
        location = intent.getStringExtra("loc");


   /*     firstName = usr.getFirstname();
        lastName = usr.getLastname();
        phoneNumber = usr.getPhoneNumber();
    */
    }

    private void startDatabase()
    {
        db = FirebaseFirestore.getInstance();
    }
    void postFeed()
    {
        // Create a new user with a first and last name

        Map<String, Object> feed = new HashMap<>();
        feed.put("Commodity", commodity);
        feed.put("Variety", variety);
        feed.put("Quality",quality);
        feed.put("Quantity",quantity);
        feed.put("Location",location);
        feed.put("Seller",firstName+" "+lastName);
        feed.put("Contact",phoneNumber);



        // Add a new document with a generated ID
        db.collection("Feeds")
                .add(feed)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);

                    }
                });
    }

    public void addListenerOnButton() {

        btn = findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                postFeed();
                Intent intent = new Intent(SellPage2.this, LoginType.class);
                startActivity(intent);

            }
        });
    }
}
