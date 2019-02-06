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

public class RegistrationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        findViews();
        StartFirebaseLogin();
        addListenerOnButton();
    }


   // private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private String verificationCode;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    Button btn6, btn7;
    EditText phoneNumber,etOTP,firstName,lastName;
    String phNumber;

    private void findViews()
    {
        btn6=findViewById(R.id.button6);
        btn7=findViewById(R.id.button7);
        phoneNumber=findViewById(R.id.editText3);
        etOTP=findViewById(R.id.editText6);
        firstName = findViewById(R.id.editText);
        lastName = findViewById(R.id.editText8);

    }

    private PhoneAuthProvider.ForceResendingToken force;
    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegistrationPage.this,"verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegistrationPage.this,"verification failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                force = forceResendingToken;
                Toast.makeText(RegistrationPage.this,"Code Sent ",Toast.LENGTH_SHORT).show();
            }
        };
    }


    public void addListenerOnButton() {

        final Context context = this;
        //   btn6=findViewById(R.id.button6);
        btn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                phNumber=phoneNumber.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phNumber, 60, TimeUnit.SECONDS,RegistrationPage.this, mCallback);
            }

        });

        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View ard0){
                String input_code = etOTP.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,input_code);
                //        addUser();

                if(verificationCode.equals(etOTP))
                {
                    startActivity(new Intent(RegistrationPage.this, LoginType.class));
                } else
                {
                    Toast.makeText(RegistrationPage.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
