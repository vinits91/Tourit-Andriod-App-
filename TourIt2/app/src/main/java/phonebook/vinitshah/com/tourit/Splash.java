package phonebook.vinitshah.com.tourit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import helper.AppContext;
import helper.Attraction;
import helper.AttractionsDBHelper;
import helper.FavoritesDBHelper;


//splash screen display when application loads up, also it defines various needed parameters for application duriung this loading time
public class Splash extends Activity {
    // splash screen delay time
    private static final int SPLASH_DISPLAY_TIME = 5000;
    ArrayList<Attraction> attractions=new ArrayList<Attraction>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(Splash.this, MainActivity.class);
                Splash.this.startActivity(intent);
                inItDB();
                Splash.this.finish();

            }
        }, SPLASH_DISPLAY_TIME);



    }

    //loading the database
    private void inItDB(){
        AttractionsDBHelper dbHelper=new AttractionsDBHelper(this);
        if(!dbHelper.doesDatabaseExist()) {
            dbHelper.initializeDB();
        }
        FavoritesDBHelper favDB = new FavoritesDBHelper(this);
        favDB.initDB();

        Cursor res=dbHelper.getAllAttractions();
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            Attraction attraction = new Attraction(res.getInt(0), res.getString(1),
                    res.getString(2),
                    res.getDouble(3), res.getDouble(4),
                    res.getString(5),  res.getString(6), // -------> default values for working hours. have to parse to time format.
                    res.getString(7), res.getString(8),
                    res.getFloat(9),res.getString(10));//---->picture value is null as of now.need to change depending on how we gonna store
            attractions.add(attraction);
            res.moveToNext();
        }
        AppContext appContext=(AppContext)getApplicationContext();
        appContext.setAttractions(attractions);
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(isSDPresent)
        {

            File wallpaperDirectory = new File("/sdcard/TourIt/");
// have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
// create a File object for the output file
            File outputFile = new File(wallpaperDirectory, "init");
// now attach the OutputStream to the file object, instead of a String representation
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"SD card is not available, Few Functionality might not work.",Toast.LENGTH_LONG).show();
        }


    }


}
