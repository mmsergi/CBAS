package sergi.crowdbuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import sergi.crowdbuy.objects.Offer;

public class OfferActivity extends AppCompatActivity {

    Offer offer;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDescription) TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        ButterKnife.bind(this);

        offer = (Offer) getIntent().getSerializableExtra("Offer");

        tvTitle.setText(offer.title);
        tvDescription.setText(offer.description);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("groups").child(offer.key);
                ref.child("members").child(Crowdbuy.email.replace(".","")).setValue(true);

                finish();
                break;
            default:
                break;
        }
    }
}
