package com.example.onlineagrimarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class BuyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        showFeed();
    }

    private LinearLayout linearLayout;
    private TextView commView,locView, qualView, quanView, varView, partView;
    private void showFeed()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Feeds").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String commodity =  ds.getString("Commodity");
                    String location = ds.getString("Location");
                    String quality = ds.getString("Quality");
                    String quantity = ds.getString("Quantity");
                    String variety = ds.getString("Variety");


                    linearLayout = findViewById(R.id.rootLayout);

                    // Create TextView programmatically.
                    commView = new TextView(BuyPage.this);
                    commView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    commView.setGravity(Gravity.CENTER);
                    commView.setText(commodity);

                    locView = new TextView(BuyPage.this);
                    locView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    locView.setGravity(Gravity.CENTER);
                    locView.setText(location);

                    qualView = new TextView(BuyPage.this);
                    qualView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    qualView.setGravity(Gravity.CENTER);
                    qualView.setText(quality);

                    quanView = new TextView(BuyPage.this);
                    quanView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    quanView.setGravity(Gravity.CENTER);
                    quanView.setText(quantity + "quintals");

                    varView = new TextView(BuyPage.this);
                    varView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    varView.setGravity(Gravity.CENTER);
                    varView.setText(variety);

                    partView = new TextView(BuyPage.this);
                    partView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    partView.setGravity(Gravity.CENTER);
                    partView.setText("=================");
                    // Add TextView to LinearLayout
                }
                if (linearLayout != null) {
                    linearLayout.addView(commView);
                    linearLayout.addView(varView);
                    linearLayout.addView(qualView);
                    linearLayout.addView(quanView);
                    linearLayout.addView(locView);
                    linearLayout.addView(partView);

                }

            }
        });
    }
}
