package com.example.onlineagrimarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import static com.example.onlineagrimarket.MainActivity.MyPREFERENCES;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        findViews();
        addListenerOnButton();
    }

    EditText fname,lname,email;

    public void findViews()
    {
        fname = findViewById(R.id.editText2);
        lname = findViewById(R.id.editText7);
        email = findViewById(R.id.editText9);
    }

    String phoneNumber;
    public void updateData(){


        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        phoneNumber = sharedpreferences.getString("phoneKey","");

        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                String first = fname.getText().toString().trim();
                String last = lname.getText().toString().trim();
                String mail = email.getText().toString().trim();

                for (DocumentSnapshot ds : queryDocumentSnapshots) {


                    String phone = ds.getString("phoneNumber");
                    if(phoneNumber.equals(phone)) {

                        if(first.equals(""))
                            first = ds.getString("firstName");
                        if(last.equals(""))
                            last = ds.getString("lastName");
                        if(mail.equals(""))
                            mail = ds.getString("e-mail");


                        db.collection("Users").document(ds.getId())
                                .update("firstName", first
                                        , "lastName", last
                                        , "e-mail", mail);


                        Intent intent = new Intent(EditProfile.this,Profile.class);
                        startActivity(intent);

                    }
                }}
        });
    }


    Button btn;
    public void addListenerOnButton()
    {
        btn = findViewById(R.id.button8);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

}
