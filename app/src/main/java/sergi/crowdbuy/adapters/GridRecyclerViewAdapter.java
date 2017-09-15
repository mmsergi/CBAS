package sergi.crowdbuy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sergi.crowdbuy.GridViewHolders;
import sergi.crowdbuy.R;
import sergi.crowdbuy.objects.Offer;

/**
 * Created by gersoft on 05/09/2017.
 */


public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridViewHolders> {

    private List<Offer> itemList;
    private Context context;

    public GridRecyclerViewAdapter(Context context, List<Offer> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public GridViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_grid, null);
        GridViewHolders holder = new GridViewHolders(layoutView, new Offer());
        return holder;
    }

    @Override
    public void onBindViewHolder(GridViewHolders holder, int position) {
        holder.title.setText(itemList.get(position).title);
        holder.image.setImageResource(itemList.get(position).image);
        holder.offer = itemList.get(position);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

