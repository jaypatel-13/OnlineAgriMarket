package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    // Access a Cloud Firestore instance from your Activity
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

    void addUser()
    {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("firstName", firstName.getText().toString().trim());
        user.put("lastName", lastName.getText().toString().trim());
        user.put("phoneNumber",phNumber.trim());
        user.put("e-mail", eMail.getText().toString().trim());

// Add a new document with a generated ID
        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.getId());
//                        Toast.makeText(RegistrationPage.this,"User added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
  //                      Toast.makeText(RegistrationPage.this,"Error adding user.", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void startDatabase()
    {
        db = FirebaseFirestore.getInstance();
    }
    private int flag;
    private void StartFirebaseLogin() {
        flag=1;
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegistrationPage.this,"verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                flag = 0;
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
                            startActivity(new Intent(RegistrationPage.this, LoginType.class));
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
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phNumber, 60, TimeUnit.SECONDS,RegistrationPage.this, mCallback);
                if (flag !=0)
                {
                    addUser();
                }
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
