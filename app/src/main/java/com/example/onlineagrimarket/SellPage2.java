package com.example.onlineagrimarket;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.compiler.PluginProtos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class SellPage2 extends AppCompatActivity {
    private static final String TAG = "SellPage2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page2);
        findViews();
        getData();
        startDatabase();
        addListenerOnButton();
    }
    private FirebaseFirestore db;
    Button btn, btn5;
    String commodity, variety, quantity, quality, location;
    SharedPreferences sharedpreferences;
    String firstName, lastName, phoneNumber;
    ImageView imgView;

    private void findViews()
    {
        imgView = findViewById(R.id.imageView3);
        btn = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);


    }
    private void getData()
    {
        Intent intent = getIntent();
        commodity = intent.getStringExtra("comm");
        variety = intent.getStringExtra("var");
        quality = intent.getStringExtra("qual");
        quantity = intent.getStringExtra("quan");
        location = intent.getStringExtra("loc");

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        firstName = sharedpreferences.getString("fnameKey","");
        lastName = sharedpreferences.getString("lnameKey","");
        phoneNumber = sharedpreferences.getString("phoneKey","");


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
                intent = new Intent(SellPage2.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(SellPage2.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(SellPage2.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(SellPage2.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(SellPage2.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_myhistory:
                intent = new Intent(SellPage2.this, History.class);
                startActivity(intent);
                return true;



            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(SellPage2.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;


        }return super.onOptionsItemSelected(item);
    }
    ProgressBar progressBar;
    private void startDatabase()
    {
        db = FirebaseFirestore.getInstance();
    }
    void postFeed()
    {

        DocumentReference docRef = db.collection("Feeds").document();

        Map<String, Object> feed = new HashMap<>();
        feed.put("Commodity", commodity);
        feed.put("Variety", variety);
        feed.put("Quality",quality);
        feed.put("Quantity",quantity);
        feed.put("Location",location);
        feed.put("Seller",firstName+" "+lastName);
        feed.put("Contact",phoneNumber);
        feed.put("Status","onDeal");
        feed.put("Image",imgUri.toString());

        docRef.set(feed).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SellPage2.this,"Data Posted.",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference mountainsRef = storageRef.child(phoneNumber +"/" +imgUri.toString());

        // Get the data from an ImageView as bytes
        imgView.setDrawingCacheEnabled(true);
        imgView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                progressBar = findViewById(R.id.progressBar);

                double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                int currentprogress = (int) progress;
                progressBar.setProgress(currentprogress);
                //progressBar.animate();
                if (currentprogress==100)
                {
                    Intent intent = new Intent(SellPage2.this, LoginType.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }


    Uri imgUri;
    private static final int PICK_IMAGE = 100;
    private void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imgUri = data.getData();
            imgView.setCropToPadding(true);
            imgView.setImageURI(imgUri);
        }
    }

    public void addListenerOnButton() {


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                postFeed();
            }
        });
    }
}
