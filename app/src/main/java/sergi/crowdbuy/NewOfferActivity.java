package sergi.crowdbuy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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

    private LocationManager locationManager;
    private LocationListener locationListener;

    double latitude, longitude;

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

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.e("Location", "accuracy: " + location.getAccuracy());

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Log.e("Lat, Long", String.valueOf(latitude) + ", " + String.valueOf(longitude));
            }
        };

        if (locationManager.getAllProviders().contains("network")) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else if (locationManager.getAllProviders().contains("gps")) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if (locationManager.getAllProviders().contains("network")) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            } else if (locationManager.getAllProviders().contains("gps")) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

            return;
        }

    }

    public void onClick(View v ){
        switch (v.getId()){
            case R.id.sendBtn:
                uploadNewOffer();
                finish();
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

        // 1. CREATE OFFER IN DATABASE
        DatabaseReference ref = database.getReference("offers").push();

        ref.child("title").setValue(titleET.getText().toString());
        ref.child("description").setValue(descriptionET.getText().toString());
        ref.child("participants").setValue(participantsET.getText().toString());
        ref.child("price").setValue(priceET.getText().toString());
        ref.child("currency").setValue("€");
        ref.child("user").setValue(Uid);

        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation("geo", new GeoLocation(37.7853889, -122.4056973));

        // 2. CREATE CHAT GROUP
        DatabaseReference ref2 = database.getReference("groups").child(ref.getKey());

        ref2.child("members").child(Crowdbuy.email.replace(".","")).setValue(true);

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
