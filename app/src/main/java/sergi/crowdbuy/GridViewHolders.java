package sergi.crowdbuy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sergi.crowdbuy.objects.Offer;

/**
 * Created by gersoft on 05/09/2017.
 */

public class GridViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public ImageView image;
    public Offer offer;

    public GridViewHolders(View itemView, Offer offer) {
        super(itemView);
        itemView.setOnClickListener(this);
        title = (TextView) itemView.findViewById(R.id.title);
        image = (ImageView) itemView.findViewById(R.id.image);
        this.offer = offer;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, OfferActivity.class);
        intent.putExtra("Offer", offer);
        context.startActivity(intent);
    }
}
