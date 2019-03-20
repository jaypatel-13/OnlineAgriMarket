package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class MyPosts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
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
                intent = new Intent(MyPosts.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(MyPosts.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(MyPosts.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(MyPosts.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(MyPosts.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_myhistory:
                intent = new Intent(MyPosts.this, History.class);
                startActivity(intent);
                return true;


            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(MyPosts.this, MainActivity.class);
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

    Button delete;
    private LinearLayout linearLayout;
    private CardView cardView;
    private TextView textView,textView1, partView, posts, history;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imgView;
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference imgRef;
    private void showFeed()
    {
        linearLayout = findViewById(R.id.rootLayout1);
        linearLayout.setPadding(20,0,20,0);

        posts = new TextView(MyPosts.this);
        posts.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        posts.setText("\nOngoing Deals\n");
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
                    String image = ds.getString("Image");
                    storage = FirebaseStorage.getInstance();
                    storageRef = storage.getReference();
                    imgRef = storageRef.child(phoneNumber + "/" + Uri.parse(image));

                    Log.i("helllo",image);
                    final String phone = ds.getString("Contact");

                    if(deal.equals("onDeal"))
                    {

                        if(phoneNumber.equals(phone)) {


                            cardView = new CardView(MyPosts.this);
                            cardView.setLayoutParams(new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT));
                            cardView.setRadius(20);
                            cardView.setElevation(4);
                            cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.DKGRAY));

                            imgView = new ImageView(MyPosts.this);
                            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(getApplicationContext())
                                            .load(uri)
                                            .into(imgView);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MyPosts.this,"Image can't be loaded",Toast.LENGTH_SHORT).show();

                                }
                            });

                            textView = new TextView(MyPosts.this);
                            textView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextColor(ColorStateList.valueOf(Color.WHITE));
                            textView.setPadding(50, 680, 0, 15);
                            textView.setText("\nCommodity : " + commodity + "\nVariety : " + variety + "\nQuality : " + quality + "\nQuantity : " + quantity + " quintals" + "\nLocation : " + location);

                            partView = new TextView(MyPosts.this);
                            partView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            partView.setGravity(Gravity.CENTER);
                            partView.setText(" ");

                            delete = new Button(MyPosts.this);
                            delete.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            delete.setText("Deal Done");
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    Map<String, Object> feed = new HashMap<>();
                                    feed.put("Status","doneDeal");
                                    db.collection("Feeds").document(ds.getId()).update(feed);

                                    Intent intent = new Intent(MyPosts.this, MyPosts.class);
                                    startActivity(intent);
                                }
                            });

                            if (linearLayout != null) {

                                cardView.addView(imgView);
                                cardView.addView(textView);
                                cardView.addView(delete);
                                linearLayout.addView(cardView);
                                linearLayout.addView(partView);
                            }
                        }
                    }
                }
            }
        });
    }
}
