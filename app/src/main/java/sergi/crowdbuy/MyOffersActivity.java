package sergi.crowdbuy;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import sergi.crowdbuy.adapters.ListMyOffersAdapter;
import sergi.crowdbuy.objects.Offer;

/**
 * Created by gersoft on 04/07/2017.
 */

public class MyOffersActivity extends ListActivity{

    ArrayList<Offer> aList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_offers);
        ButterKnife.bind(this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("offers");

        Query query = ref.orderByChild("user").startAt(Crowdbuy.uid).endAt(Crowdbuy.uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()) {
                    aList.clear();

                    Map<String, Object> data = (Map<String, Object>) item.getValue();

                    String title = "", description = "", participants = "", price = "", currency = "";

                    if (data.containsKey("title")) title = (String) data.get("title");
                    if (data.containsKey("description")) description = (String) data.get("description");
                    if (data.containsKey("participants")) participants = (String) data.get("participants");
                    if (data.containsKey("price")) price = (String) data.get("price");
                    if (data.containsKey("currency")) currency = (String) data.get("currency");

                    Log.e("TITLE", title);

                    Offer offer = new Offer(title, description, participants, price, currency);
                    aList.add(offer);

                    ListMyOffersAdapter adapter = new ListMyOffersAdapter(MyOffersActivity.this, aList);
                    MyOffersActivity.this.setListAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
