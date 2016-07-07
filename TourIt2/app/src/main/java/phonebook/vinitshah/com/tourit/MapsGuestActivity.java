package phonebook.vinitshah.com.tourit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//Map for guest users
public class MapsGuestActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static LatLng crt;
    private static LatLng dstn;
    private String link;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();


        Intent intent = getIntent();
        String[] dstn_arr = new String[2];
        dstn_arr = intent.getStringArrayExtra("dstn");
        dstn = new LatLng(Double.parseDouble(dstn_arr[0]), Double.parseDouble(dstn_arr[1]));


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);

        Location crtLcn = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        crt= new LatLng(crtLcn.getLatitude(),crtLcn.getLongitude());
        System.out.println(crt.toString() + "\n");

        link = "https://maps.googleapis.com/maps/api/directions/json?origin="+
                crt.latitude+","+crt.longitude+"&destination="+dstn.latitude+","+dstn.longitude+
                "&&mode=walking&key=AIzaSyC4VRVwi_X2EWHnx7OBrLlcwf2XLw0Tz4o";
        // System.out.println(link);
        getJson ss= new getJson();
        ss.execute(link);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.beachflag);
        mMap.addMarker(new MarkerOptions().position(crt).title("You are here").icon(icon));
        mMap.addMarker(new MarkerOptions().position(dstn).title(dstn_arr[2]).icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(crt, 13));


        // final LatLng detn = new LatLng(Attr.getLatitude(),Attr.getLongitude());
/*////
////
///
///
        ArrayList points = new ArrayList<LatLng>();
        PolylineOptions polyLineOptions = new PolylineOptions();

        points.add(new LatLng(41.8346737,-87.62673399999));
        points.add(new LatLng(41.8467237,-87.62674669999999));
        points.add(new LatLng(41.8474764,-87.62683559999999));
        points.add(new LatLng(41.848427,-87.62688679999999));
        points.add(new LatLng(41.8526516,-87.62699179999999));
        points.add(new LatLng(41.8527629,-87.62602369999999));
        points.add(new LatLng(41.8527944,-87.6238013));
        points.add(new LatLng(41.867173,-87.62413229999999));
        points.add(new LatLng(41.8672151,-87.62406379999999));
        points.add(new LatLng(41.8745063,-87.6241686));
        points.add(new LatLng(41.8769588,-87.62419169999999));
        points.add(new LatLng(41.8771573,-87.62417679999999));
        points.add(new LatLng(41.880097, -87.62427759999999));
        points.add(new LatLng(41.8817596, -87.62420099999999));
        points.add(new LatLng(41.8825978, -87.62420759999999));


        polyLineOptions.addAll(points);
        polyLineOptions.width(16);
        polyLineOptions.color(Color.RED);

        mMap.addPolyline(polyLineOptions);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.beachflag);


        mMap.addMarker(new MarkerOptions().position(crt).title("from:crrt").icon(icon));
        mMap.addMarker(new MarkerOptions().position(dstn).title("from:crrt").icon(icon));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(crt, 13));
        /////
        ////
      */

        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0, locationListener);

/*
        public void onLocationChanged(Location location) {
            Toast.makeText(this.getBaseContext()," \"Latitude:\" + location.getLatitude() + \", Longitude:\" + location.getLongitude())", Toast.LENGTH_SHORT).show();
        }
*/




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IIT,   13));
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        //
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // log it when the location changes

            Log.i("SuperMap", "Location changed : Lat: "
                    + location.getLatitude() + " Lng: "
                    + location.getLongitude());
            crt = new LatLng(location.getLatitude(),location.getLongitude());
            //   System.out.println(crt.toString() + "\n");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };


    public class getJson extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(args[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            //Do something with the JSON string
            super.onPostExecute(result);
            System.out.println("excute jason");
            //  System.out.println(result);
            new ParserTask().execute(result);

        }

    }


    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(14);
                polyLineOptions.color(Color.BLUE);
            }

            mMap.addPolyline(polyLineOptions);
        }
    }
    private class PathJSONParser {

        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;
            try {
                jRoutes = jObject.getJSONArray("routes");
                /** Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                    /** Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                    .get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat",
                                        Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng",
                                        Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
            return routes;
        }

        /**
         * Method Courtesy :
         * jeffreysambells.com/2010/05/27
         * /decoding-polylines-from-google-maps-direction-api-with-java
         * */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
            locationManager.removeUpdates(locationListener);
        }

        return super.onKeyDown(keyCode, event);
    }
}
