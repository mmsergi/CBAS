package sergi.crowdbuy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import sergi.crowdbuy.EditOfferActivity;
import sergi.crowdbuy.R;
import sergi.crowdbuy.objects.Offer;

public class ListMyOffersAdapter extends ArrayAdapter<Offer> {

    private Context context;
    private ArrayList<Offer> arrayOffers;

    public ListMyOffersAdapter(Context context, ArrayList<Offer> arrayOffers) {
        super(context, R.layout.activity_main);
        this.context = context;
        this.arrayOffers = arrayOffers;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        rowView = inflater.inflate(R.layout.item_list_my_offer, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.textViewTitle);
        title.setText(arrayOffers.get(position).title);

        TextView description = (TextView) rowView.findViewById(R.id.textViewDescription);
        description.setText(arrayOffers.get(position).description);

        Button button = (Button) rowView.findViewById(R.id.editBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditOfferActivity.class);
                i.putExtra("Offer", arrayOffers.get(position));
                context.startActivity(i);
            }
        });

        return rowView;
    }

    public int getCount() {
        return arrayOffers.size();
    }
}