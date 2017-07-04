package sergi.crowdbuy;

/**
 * Created by gersoft on 28/03/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import sergi.crowdbuy.adapters.ListMyOffersAdapter;
import sergi.crowdbuy.objects.Offer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyOffersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyOffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOffersFragment extends Fragment {

    ListView listView;
    ListMyOffersAdapter listViewAdapter;
    ArrayList<Offer> myOffersList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyOffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOffersFragment newInstance(String param1, String param2) {
        MyOffersFragment fragment = new MyOffersFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_offers, container, false);

        myOffersList = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.list);
        listViewAdapter = new ListMyOffersAdapter(getActivity(), myOffersList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent = new Intent(getActivity(), OfferActivity.class);
                intent.putExtra("Offer", myOffersList.get(position));
                startActivity(intent);
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("offers");

        Query query = ref.orderByChild("user").startAt(Crowdbuy.uid).endAt(Crowdbuy.uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myOffersList.clear();

                for (DataSnapshot item:dataSnapshot.getChildren()) {

                    Map<String, Object> data = (Map<String, Object>) item.getValue();

                    String title = "", description = "", participants = "", price = "", currency = "";

                    if (data.containsKey("title")) title = (String) data.get("title");
                    if (data.containsKey("description")) description = (String) data.get("description");
                    if (data.containsKey("participants")) participants = (String) data.get("participants");
                    if (data.containsKey("price")) price = (String) data.get("price");
                    if (data.containsKey("currency")) currency = (String) data.get("currency");

                    Log.e("TITLE", title);

                    Offer offer = new Offer(title, description, participants, price, currency);
                    myOffersList.add(offer);
                }

                listViewAdapter.notifyDataSetChanged();
                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
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
