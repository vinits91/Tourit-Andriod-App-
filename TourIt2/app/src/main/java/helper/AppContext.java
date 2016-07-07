package helper;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by Vinit on 08-04-2016.
 */
//creating an application context so that the database and googleApp client becomes accessible through entire app
public class AppContext extends Application {
    ArrayList<Attraction> attractions;

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public  GoogleApiClient mGoogleApiClient;

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }


}
