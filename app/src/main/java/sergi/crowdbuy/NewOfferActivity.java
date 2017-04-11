package sergi.crowdbuy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        TextView welcometext = (TextView) findViewById(R.id.welcometext);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String email = intent.getStringExtra("email");

        welcometext.setText(uid + " " + email);

        Button signOutBtn = (Button) findViewById(R.id.signoutBtn);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Uid = user.getUid();
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + Uid);

                } else {
                    // User is signed out
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(NewOfferActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
                // ...
            }
        };

    }


    public void logOut(View view ){
        mAuth.signOut();
    }

    public void sendText(View view){
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
