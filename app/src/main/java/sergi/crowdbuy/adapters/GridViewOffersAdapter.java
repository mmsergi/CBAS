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

public class GridViewOffersAdapter extends ArrayAdapter<Offer> {
    private Context context;
    private ArrayList<Offer> list;

    public GridViewOffersAdapter(Context context, ArrayList<Offer> list) {
        super(context, R.layout.activity_main);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        rowView = inflater.inflate(R.layout.gridview_item_offer, parent, false);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageGridView);
        //imageView.setImageBitmap(redimensionarBitmap(BitmapFactory.decodeFile(list.get(position))));

        Offer offer = list.get(position);

        TextView titleTV = (TextView) rowView.findViewById(R.id.titleTV);
        TextView descriptionTV = (TextView) rowView.findViewById(R.id.descriptionTV);
        TextView priceTV = (TextView) rowView.findViewById(R.id.priceTV);
        TextView peopleTV = (TextView) rowView.findViewById(R.id.peopleTV);

        titleTV.setText(offer.title);
        descriptionTV.setText(offer.description);
        priceTV.setText(offer.price + " " + offer.currency);
        peopleTV.setText(offer.people);

        return rowView;
    }

    public int getCount() {
        if (list.size() <= 0)
            return 0;
        return list.size();
    }

    private Bitmap redimensionarBitmap(Bitmap bitmap) {
        Bitmap retorn;
        int height = bitmap.getHeight();
        int with = bitmap.getWidth();
        Double rHeight;
        Double rWidth = 150.0;
        Double proporcio;

        proporcio = with / rWidth;
        rHeight = height / proporcio;
        retorn = Bitmap.createScaledBitmap(bitmap, rWidth.intValue(),
                rHeight.intValue(), false);
        return retorn;
    }

}
