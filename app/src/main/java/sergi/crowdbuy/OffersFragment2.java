package sergi.crowdbuy;

/**
 * Created by gersoft on 05/09/2017.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sergi.crowdbuy.adapters.GridOffersAdapter;
import sergi.crowdbuy.adapters.GridRecyclerViewAdapter;
import sergi.crowdbuy.objects.GridObjects;
import sergi.crowdbuy.objects.Offer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OffersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment2 extends Fragment {

    GridView gridView;
    GridOffersAdapter gridViewAdapter;
    RecyclerView recyclerView;

    GridRecyclerViewAdapter gridAdapter;
    ArrayList<GridObjects> gaggeredList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OffersFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers_2, container, false);

        gaggeredList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("offers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectInfoFromDatabase((Map<String,Object>) dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                //Toast.makeText(getActivity(), "databaseError: " + databaseError, Toast.LENGTH_SHORT).show();

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        gaggeredList = getListItemData();

        gridAdapter = new GridRecyclerViewAdapter(getActivity(), gaggeredList);
        recyclerView.setAdapter(gridAdapter);

        return view;
    }

    private ArrayList<GridObjects> getListItemData(){
        ArrayList<GridObjects> listViewItems = new ArrayList<>();

        listViewItems.add(new GridObjects("Alkane", R.drawable.one));
        listViewItems.add(new GridObjects("Ethane", R.drawable.two));
        listViewItems.add(new GridObjects("Alkyne", R.drawable.three));
        listViewItems.add(new GridObjects("Benzene", R.drawable.four));
        listViewItems.add(new GridObjects("Amide", R.drawable.one));
        listViewItems.add(new GridObjects("Amino Acid", R.drawable.two));
        listViewItems.add(new GridObjects("Phenol", R.drawable.three));
        listViewItems.add(new GridObjects("Carbonxylic", R.drawable.four));
        listViewItems.add(new GridObjects("Nitril", R.drawable.one));
        listViewItems.add(new GridObjects("Ether", R.drawable.two));
        listViewItems.add(new GridObjects("Ester", R.drawable.three));
        listViewItems.add(new GridObjects("Alcohol", R.drawable.four));

        return listViewItems;
    }

    private void collectInfoFromDatabase(Map<String,Object> users) {

        //gaggeredList.clear();

        if (users!=null){

            //iterate through each user, ignoring their UID
            for (Map.Entry<String, Object> entry : users.entrySet()){

                String key = entry.getKey().toString();

                //Get user map
                Map singleEntry = (Map) entry.getValue();

                String title = (String) singleEntry.get("title");
                String description = (String) singleEntry.get("description");
                String people = (String) singleEntry.get("participants");
                String price = (String) singleEntry.get("price");
                String currency = (String) singleEntry.get("currency");

                GridObjects offer = new GridObjects();

                Random r = new Random();
                int a = r.nextInt(4) + 1;

                if (a == 1) offer = new GridObjects(title, R.drawable.one);
                if (a == 2) offer = new GridObjects(title, R.drawable.two);
                if (a == 3) offer = new GridObjects(title, R.drawable.three);
                if (a == 4) offer = new GridObjects(title, R.drawable.four);

                gaggeredList.add(offer);
            }

            if (!(gaggeredList.size() == 0)) {
                //gridAdapter.notifyItemRangeChanged(0, gridAdapter.getItemCount());
                gridAdapter.notifyDataSetChanged();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
