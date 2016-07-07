package phonebook.vinitshah.com.tourit;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import helper.Attraction;
import helper.AttractionsDBHelper;
import helper.GPSTracker;

//based on current location of user, the user will get 5 attractions which can be covered in a day
public class OneDayTrip extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<Attraction> AttrList;
    private GoogleMap mMap;
    private String Label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        AttractionsDBHelper dbHelper=new AttractionsDBHelper(this);
        Cursor res=dbHelper.getAllAttractions();
        AttrList = new ArrayList<Attraction>();
        System.out.println("Row count in Activity" + res.getColumnCount());
        res.moveToFirst();

        while(res.isAfterLast() == false) {

            Attraction ChiB = new Attraction(R.drawable.lincolnpark, res.getString(1),
                    res.getString(2),
                    res.getDouble(3), res.getDouble(4),
                    res.getString(5),res.getString(6),
                    res.getString(7), res.getString(8),
                    res.getFloat(9),res.getString(10));
            AttrList.add(ChiB);
            res.moveToNext();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);



    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        GPSTracker gt=new GPSTracker(this.getApplicationContext());
        LatLngBounds.Builder bld = new LatLngBounds.Builder();
        Location crtLcn=gt.getLocation();

        if(crtLcn!=null) {
            LatLng current = new LatLng(crtLcn.getLatitude(), crtLcn.getLongitude());

            mMap.addMarker(new MarkerOptions().position(current).title("You are here"));
            bld.include(current);
        }
        //Location l=mMap.getMyLocation();
        // Add a marker in Sydney and move the camera
        BitmapDescriptor bitmapDescriptorIcon= BitmapDescriptorFactory.fromResource(R.drawable.beachflag);
        Location firstNearest=findNearest(crtLcn,AttrList);
        LatLng first = new LatLng(firstNearest.getLatitude(), firstNearest.getLongitude());
        mMap.addMarker(new MarkerOptions().position(first).icon(bitmapDescriptorIcon).title("1 "+Label));
        bld.include(first);
        Location nextNearest=findNearest(firstNearest,AttrList);
        for(int i=0;i<4;i++){
            LatLng next = new LatLng(nextNearest.getLatitude(), nextNearest.getLongitude());
            int temp=i+2;
            mMap.addMarker(new MarkerOptions().position(next).icon(bitmapDescriptorIcon).title(Integer.toString(temp) + " " + Label));
            bld.include(next);
            nextNearest=findNearest(nextNearest,AttrList);
        }

        LatLngBounds bounds = bld.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
    }
    public Location findNearest(Location from,ArrayList<Attraction>to){
        Location loc;
        double distance=0;
        double distance1=0;
        int id=0;
        loc=new Location(Integer.toString(0));
        loc.setLatitude(to.get(0).getLatitude());
        loc.setLongitude(to.get(0).getLongitude());
        distance=from.distanceTo(loc);
        for(int i=1;i<to.size();i++){

            loc=new Location(Integer.toString(i));
            loc.setLatitude(to.get(i).getLatitude());
            loc.setLongitude(to.get(i).getLongitude());
            distance1=from.distanceTo(loc);
            if(distance1<distance){
                distance=distance1;
                id=i;
            }

        }
        Location nearest=new Location("next");
        Label=to.get(id).getName();
        nearest.setLatitude(to.get(id).getLatitude());
        nearest.setLongitude(to.get(id).getLongitude());
        AttrList.remove(id);
        return nearest;
    }
}
