package sergi.crowdbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import sergi.crowdbuy.objects.Offer;

/**
 * Created by gersoft on 05/07/2017.
 */

public class EditOfferActivity extends AppCompatActivity{

    @BindView(R.id.editTextTitle) EditText etTitle;
    @BindView(R.id.editTextDescription) EditText etDescription;
    @BindView(R.id.editTextParticipants) EditText etParticipants;
    @BindView(R.id.editTextPrice) EditText etPrice;

    Offer offer;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();

        offer = (Offer) getIntent().getSerializableExtra("Offer");

        etTitle.setText(offer.title);
        etDescription.setText(offer.description);
        etParticipants.setText(offer.participants);
        etPrice.setText(offer.price);

    }

        public void onClick(View v ){
        switch (v.getId()){
            case R.id.sendBtn:

                DatabaseReference ref = database.getReference("offers").child(offer.key);

                ref.child("title").setValue(etTitle.getText().toString());
                ref.child("description").setValue(etDescription.getText().toString());
                ref.child("participants").setValue(etParticipants.getText().toString());
                ref.child("price").setValue(etPrice.getText().toString());
                ref.child("currency").setValue("€");
                ref.child("user").setValue(Crowdbuy.uid);

                GeoFire geoFire = new GeoFire(ref);
                geoFire.setLocation("geo", new GeoLocation(37.7853889, -122.4056973));

                finish();
                break;
            case R.id.closeBtn:

                finish();
                break;
            case R.id.removeBtn:

                DatabaseReference myRef = database.getReference("offers").child(offer.key);
                myRef.removeValue();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;

        }
    }
}