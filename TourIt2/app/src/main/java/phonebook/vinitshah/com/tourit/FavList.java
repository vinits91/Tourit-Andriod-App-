package phonebook.vinitshah.com.tourit;


import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import AdaptersPacakge.AttractionAdapter;
import helper.AppContext;
import helper.Attraction;

//favourite list of attractions
public class FavList extends AppCompatActivity {
    ListView AttrListView;
    ArrayList<Attraction> AttrList;
    AttractionAdapter AA;
    LayoutInflater inflater;
    boolean gps_enabled=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chicago's Attractions");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("FavList","Loading the list of attractions");
        AttrListView = (ListView) findViewById(R.id.listView);
        AppContext appContext=(AppContext)getApplicationContext();
        AttrList=appContext.getAttractions();

        inflater = getLayoutInflater();
        LayoutInflater inflater =getLayoutInflater();
        AA = new AttractionAdapter(this,AttrList);

        AttrListView.setAdapter(AA);


        AttrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(FavList.this, AttrDescription.class);
                Bundle bdl = new Bundle();
                bdl.putSerializable("Attr", AttrList.get(position));
                intent.putExtras(bdl);
                startActivity(intent);
                
            }
        });


    }
    public  void onedaytrip(View view){
        Log.d("FavList","Generation of 1 day Trip plan");
        if(isOnline() && checkGPS()) {
            Intent intent = new Intent(this, OneDayTrip.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "No internet connection or location service,please check make sure both are enabled",
                    Toast.LENGTH_LONG).show();
        }

    }
    public boolean checkGPS(){
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps_enabled;
    }

    //add check func
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
