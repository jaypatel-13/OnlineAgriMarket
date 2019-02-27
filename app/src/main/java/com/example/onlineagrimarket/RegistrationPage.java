package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

public class RegistrationPage extends AppCompatActivity {
    private static final String TAG = "RegistrationPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        findViews();
        StartFirebaseLogin();
        startDatabase();
        addListenerOnButton();
    }

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String verificationCode;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    Button btn6, btn7;
    EditText phoneNumber,etOTP,firstName,lastName,eMail;
    String phNumber;


    private void findViews()
    {
        btn6=findViewById(R.id.button6);
        btn7=findViewById(R.id.button7);
        phoneNumber=findViewById(R.id.editText3);
        etOTP=findViewById(R.id.editText6);
        firstName = findViewById(R.id.editText);
        lastName = findViewById(R.id.editText8);
        eMail = findViewById(R.id.editText5);

    }

    void addUser() {
        DocumentReference docRef = db.collection("Users").document();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("firstName", firstName.getText().toString().trim());
        user.put("lastName", lastName.getText().toString().trim());
        user.put("phoneNumber", phNumber.trim());
        user.put("e-mail", eMail.getText().toString().trim());

        docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot added.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

    }
    private void startDatabase()
    {
        db = FirebaseFirestore.getInstance();
    }
    private void StartFirebaseLogin() {
    //    flag=1;
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
                 super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(RegistrationPage.this,"Code Sent ",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addUser();
                            Toast.makeText(RegistrationPage.this, "You are successfully registered.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationPage.this, MainActivity.class));
                            finish();
                        } else
                        {
                            Toast.makeText(RegistrationPage.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void addListenerOnButton() {

        final Context context = this;
        btn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                phNumber=phoneNumber.getText().toString();
                    db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            int flag = 0;

                            for(DocumentSnapshot ds : queryDocumentSnapshots)
                            {
                                String pNumber;
                                pNumber = ds.getString("phoneNumber");
                                if(pNumber.equals(phNumber))
                                {
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag == 1)
                            {
                                Toast.makeText(RegistrationPage.this,"Number already registered.",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(phNumber, 60, TimeUnit.SECONDS,RegistrationPage.this, mCallback);
                            }
                        }
                    });
            }
        });

        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View ard0){

                String input_code = etOTP.getText().toString().trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,input_code);
                SigninWithPhone(credential);

            }
        });

    }

}
