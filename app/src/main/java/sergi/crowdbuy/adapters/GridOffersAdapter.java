package sergi.crowdbuy.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sergi.crowdbuy.R;
import sergi.crowdbuy.objects.Offer;

public class GridOffersAdapter extends ArrayAdapter<Offer> {

    private Context context;
    private ArrayList<Offer> list;

    public GridOffersAdapter(Context context, ArrayList<Offer> list) {
        super(context, R.layout.activity_main);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        rowView = inflater.inflate(R.layout.gridview_item_offer, parent, false);

        Offer offer = list.get(position);

        TextView titleTV = (TextView) rowView.findViewById(R.id.titleTV);
        TextView descriptionTV = (TextView) rowView.findViewById(R.id.descriptionTV);

        titleTV.setText(offer.title);
        descriptionTV.setText(offer.description);

        return rowView;
    }

    public int getCount() {
        return list.size();
    }
}