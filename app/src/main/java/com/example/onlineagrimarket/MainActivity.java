package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartFirebaseLogin();
        addListenerOnButton();

    }

    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            int flag = 0;

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Intent intent = new Intent(MainActivity.this, LoginType.class);
                startActivity(intent);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(MainActivity.this,"verification failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Intent intent = new Intent(MainActivity.this, LoginType.class);
                startActivity(intent);
            }



        };
    }
    String phNumber;
    Button register, login;
    EditText phoneNumber;

    public void addListenerOnButton() {

        final Context context = this;

        login = findViewById(R.id.btn_login);
        phoneNumber = findViewById(R.id.mobile_no);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                phNumber = phoneNumber.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phNumber, 60, TimeUnit.SECONDS,MainActivity.this, mCallback);
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
