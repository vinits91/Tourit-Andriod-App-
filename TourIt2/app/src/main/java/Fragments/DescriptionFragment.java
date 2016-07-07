package Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import helper.Attraction;
import phonebook.vinitshah.com.tourit.MapsGuestActivity;
import phonebook.vinitshah.com.tourit.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//fragment used to display the Description Tab of an attraction, with video, description and direction to that attraction
public class DescriptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Attraction attraction;
    Button navigate;
    TextView mText;
    TextView desc;
    WebView wv;
    boolean gps_enabled=false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    //Description fragment
    public static DescriptionFragment newInstance(String param1, String param2) {
        Log.d("DescriptionFragment", "Loading Description Fragment");
        DescriptionFragment fragment = new DescriptionFragment();
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

    //gets the attraction selected on Homepage and Display the information about it with navigation support
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DescriptionFragment", "Loading Description Fragment content in the fragment");
        final View view=inflater.inflate(R.layout.fragment_description, container, false);
        wv = (WebView) view.findViewById(R.id.Des_webView);
        attraction = (Attraction) getArguments().getSerializable(
                "attraction");
        mText = (TextView) view.findViewById(R.id.Des_name);
        desc = (TextView) view.findViewById(R.id.des);
        mText.setText(attraction.getName());
        desc.setText(attraction.getDescription());
        String link= attraction.getVideoLink().toString();
        link =link.replace("watch?v=", "embed/");
        String frameVideo = " <iframe width=\"340\" height=\"200\" src=\"" +link+"\""+ " frameborder=\"0\" allowfullscreen></iframe>";
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.loadData(frameVideo, "text/html", "utf-8");

        navigate = (Button) view.findViewById(R.id.route);
        final String[] dstn = new String[3];
        dstn[0]= String.valueOf(attraction.getLatitude());
        dstn[1]= String.valueOf(attraction.getLongitude());
        dstn[2]= String.valueOf(attraction.getName());
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks whether internet is there or not
                if(isOnline()&&checkGPS()) {
                    Intent intent = new Intent(getActivity(), MapsGuestActivity.class);
                    intent.putExtra("dstn", dstn);
                    startActivity(intent);
                }else{
                    Log.d("DescriptionFragment", "No internet Connection");
                    Toast.makeText(getContext(), "No internet connection or location service,please check make sure both are enabled",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    //add check func
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //check location service
    public boolean checkGPS(){
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps_enabled;
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

    public static DescriptionFragment newInstance(Attraction attr) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("attraction", attr);
        fragment.setArguments(bundle);

        return fragment;
    }
}
