package sergi.crowdbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewOfferActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Uid = user.getUid();
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + Uid);

                } else {
                    // User is signed out
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(NewOfferActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }
        };

    }

    public void onClick(View v ){
        switch (v.getId()){
            case R.id.sendBtn:
                uploadNewOffer();
            case R.id.closeBtn:
                finish();
        }
    }

    public void uploadNewOffer(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        EditText titleET = (EditText) findViewById(R.id.editTextTitle);
        EditText descriptionET = (EditText) findViewById(R.id.editTextDescription);
        EditText participantsET = (EditText) findViewById(R.id.editTextParticipants);
        EditText priceET = (EditText) findViewById(R.id.editTextPrice);

        DatabaseReference myRef = database.getReference("offers").child(Uid).push();

        myRef.child("title").setValue(titleET.getText().toString());
        myRef.child("description").setValue(descriptionET.getText().toString());
        myRef.child("minpeople").setValue(participantsET.getText().toString());
        myRef.child("estimated price").setValue(priceET.getText().toString());
        myRef.child("currency").setValue("€");

        DatabaseReference geoRef = myRef.child("geo");
        GeoFire geoFire = new GeoFire(geoRef);
        geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
