package sergi.crowdbuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
}
