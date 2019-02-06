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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startDatabase();
        addListenerOnButton();
    //    usr = new User();
    }


//    User usr;
    SharedPreferences sharedpreferences;
    String phNumber;
    Button register, login;
    EditText phoneNumber;
    String firstName, lastName;
    private FirebaseFirestore db;
    private void startDatabase()
    {
        db = FirebaseFirestore.getInstance();
    }
    public void addListenerOnButton() {

        final Context context = this;

        login = findViewById(R.id.btn_login);
        phoneNumber = findViewById(R.id.mobile_no);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                phNumber = phoneNumber.getText().toString();

                db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        int flag = 0 ;
                        for(DocumentSnapshot ds : queryDocumentSnapshots)
                        {
                            String pNumber;
                            pNumber = ds.getString("phoneNumber");
                            if(pNumber.equals(phNumber))
                            {
                                firstName = ds.getString("firstName");
                                lastName = ds.getString("lastName");
/*
                                usr.setFirstname(firstName);
                                usr.setLastname(lastName);
                                usr.setPhoneNumber(phNumber);
  */                              flag = 1;
                                break;
                            }
                        }
                        if(flag == 1)
                        {

                            Intent intent = new Intent(MainActivity.this, LoginType.class);
                            //intent.putExtra("user", String.valueOf(usr));
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Number not registered.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        register = findViewById(R.id.btn_reg);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);

            }

        });
    }
}
