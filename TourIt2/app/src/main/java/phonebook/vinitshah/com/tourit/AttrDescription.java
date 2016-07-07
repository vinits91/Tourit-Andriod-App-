package phonebook.vinitshah.com.tourit;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import helper.Attraction;


//loads an attraction description based on attraction clicked by user, also supports navigation to that attraction
public class AttrDescription extends AppCompatActivity {
    Attraction Attr;
    TextView name;
    TextView dscp;
    WebView wv;
    Button gotomap;
    boolean gps_enabled=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attr_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i("AttrDescription", "Attraction Description based on attraction clicked by user");
        Intent intent = getIntent();
        Attr = (Attraction) intent.getExtras().getSerializable("Attr");
        final String[] dstn = new String[3];
        dstn[0]= String.valueOf(Attr.getLatitude());
        dstn[1]= String.valueOf(Attr.getLongitude());
        dstn[2]= String.valueOf(Attr.getName());
        toolbar.setTitle(Attr.getName());
        setSupportActionBar(toolbar);

        gotomap =(Button) findViewById(R.id.route);


        dscp = (TextView) findViewById(R.id.desc);
        dscp.setText(Attr.getDescription());
        name = (TextView) findViewById(R.id.Des_name);
        name.setText(Attr.getName());
        wv = (WebView) findViewById(R.id.Des_webView);

        String link =Attr.getVideoLink().toString();
        link.replace("watch?v=","embed/");
        System.out.println(link);


        String frameVideo = "<html><body><iframe width=\"320\" height=\"200\" src=\""+link+"\""+" frameborder=\"0\" allowfullscreen></iframe></body></html>";;


        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.loadData(frameVideo, "text/html", "utf-8");



        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(isOnline() && checkGPS()) {
                    Intent intent = new Intent(AttrDescription.this, MapsGuestActivity.class);
                    intent.putExtra("dstn", dstn);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "No internet connection or location service,please check make sure both are enabled",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    //check for network connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean checkGPS(){
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps_enabled;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        wv = (WebView) findViewById(R.id.Des_webView);

        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
            wv.destroy();
        }

        return super.onKeyDown(keyCode, event);
    }

}
