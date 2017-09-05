package sergi.crowdbuy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sergi.crowdbuy.GridViewHolders;
import sergi.crowdbuy.R;
import sergi.crowdbuy.objects.GridObjects;

/**
 * Created by gersoft on 05/09/2017.
 */


public class GridRecyclerViewAdapter  extends RecyclerView.Adapter<GridViewHolders> {

    private List<GridObjects> itemList;
    private Context context;

    public GridRecyclerViewAdapter(Context context, List<GridObjects> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public GridViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_grid, null);
        GridViewHolders rcv = new GridViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(GridViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

