package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.button.MaterialButton;
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

import javax.annotation.Nullable;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        showUser();
        getData();
        addListenerOnButton();
    }
    private LinearLayout linearLayout;
    private TextView textView;
    String phoneNumber;
    private void getData()
    {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        phoneNumber = sharedpreferences.getString("phoneKey","");

    }
    public void showUser()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String fname = ds.getString("firstName");
                    String lname = ds.getString("lastName");
                    String email = ds.getString("e-mail");
                    String phone = ds.getString("phoneNumber");

                    if(phoneNumber.equals(phone)) {
                        linearLayout = findViewById(R.id.rootLayout2);

                        textView = new TextView(Profile.this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setGravity(Gravity.CENTER);
                        textView.setPadding(50, 15, 0, 15);
                        textView.setText("First Name :" + fname + "\n\nLast Name : " + lname + "\n\nMobile No. : " + phone + "\n\nE-mail : " + email);

                        if(linearLayout!=null)
                            linearLayout.addView(textView);
                    }

                }}
        });
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
                intent = new Intent(Profile.this, LoginType.class);
                startActivity(intent);
                return true;

            case R.id.nav_about :
                intent = new Intent(Profile.this, About.class);
                startActivity(intent);
                return true;


            case R.id.nav_contact:
                intent = new Intent(Profile.this, Contact.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
                return true;

            case R.id.nav_myposts:
                intent = new Intent(Profile.this, MyPosts.class);
                startActivity(intent);
                return true;

            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;


        }return super.onOptionsItemSelected(item);
    }

    Button btn;
    public void addListenerOnButton()
    {
        btn = findViewById(R.id.button11);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,EditProfile.class);
                startActivity(intent);
            }
        });
    }
}
