package phonebook.vinitshah.com.tourit;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import helper.AppContext;
import helper.Attraction;
import helper.FavoritesDBHelper;

//Home page of Tourit which will give the functionality to view the list of attractions and its details
//it also give you the following functionality
// 1. open a camera to take photo of your memory
// 2. View Photos which you have clicked through TourIt
// 3. Open the video gallery
// 4. make a video of your memory using Google photos
// 5. Random one day trip plan of 5 location based on your current location
// 6. Share Tourit with others
// 7. share Attraction with others
public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private static final String FILE_TYPE = "images/*";
    TextView userName;
    TextView email;
    private ImageSwitcher sw;
    private ImageButton b1,b2;
    static int counter =0;
    FavoritesDBHelper favDB;
    static String imageTitle;
    TextView nameText;
    String personEmail;
    ImageView photo;
    ArrayList<Attraction> attractions;
    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;
    Animation animAlpha;
    Animation animScale;
    boolean gps_enabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("HomePage", "Loading the attraction DB");
        favDB = new FavoritesDBHelper(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("tourit", 0);
        String personName = prefs.getString("personName", null);
        personEmail = prefs.getString("personEmail", null);
        //String personId = prefs.getString("personId", null);

        //Uri uri=Uri.parse(personPhoto);


        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {
                        SharedPreferences prefs = getSharedPreferences("tourit", 0);
                        String personPhoto = prefs.getString("personPhoto", null);
                        if(personPhoto!=null) {
                            URL myUrl = new URL(personPhoto);
                            InputStream inputStream = (InputStream) myUrl.getContent();
                            Drawable drawable = Drawable.createFromStream(inputStream, null);
                            photo.setImageDrawable(drawable);
                        }else{
                            photo.setImageResource(R.drawable.default_avatar);;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
        Log.i("HomePage", "Animation for Like and Dislike buttons.");
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);


        View hView =  navigationView.getHeaderView(0);
        userName=(TextView)hView.findViewById(R.id.userName);
        email=(TextView)hView.findViewById(R.id.email);
        photo=(ImageView)hView.findViewById(R.id.imageView);
        userName.setText(personName);
        email.setText(personEmail);
        AppContext appContext=(AppContext)getApplicationContext();
        attractions=appContext.getAttractions();
        b1 = (ImageButton) findViewById(R.id.like);
        b2 = (ImageButton) findViewById(R.id.dislike);
        nameText=(TextView)findViewById(R.id.name);
        //registering image switcher
        sw = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        sw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                Log.d("HomePage", "Image switcher to navigate between images");
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        //loading an animations
        final Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        sw.setInAnimation(in);
        sw.setOutAnimation(out);
        if(counter < attractions.size()-1) {
            nameText.setText(attractions.get(counter).getName());
            imageTitle = attractions.get(counter).getPicture();
            sw.setImageResource(getResources().getIdentifier(imageTitle, "drawable", getPackageName()));
        }

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attraction atr = attractions.get(counter);
                Intent intent = new Intent(HomePage.this, TabActivity.class);
                intent.putExtra("attraction", atr);
                startActivity(intent);
            }

        });
    }


    //on click of heart button the attraction will get added to fav. list
    public void likeAction(View view){
        view.startAnimation(animScale);
        if(counter < attractions.size()-1){
            favDB.insertFavorites(personEmail, attractions.get(counter).getId(),attractions.get(counter).getName());
            counter ++;
            nameText.setText(attractions.get(counter).getName());
            imageTitle = attractions.get(counter).getPicture();
            sw.setImageResource(getResources().getIdentifier(imageTitle, "drawable", getPackageName()));
        }
        else{
            favDB.insertFavorites(personEmail, attractions.get(counter).getId(),attractions.get(counter).getName());
            counter=0;
            nameText.setText(attractions.get(counter).getName());
            imageTitle = attractions.get(counter).getPicture();
            sw.setImageResource(getResources().getIdentifier(imageTitle, "drawable", getPackageName()));
            Toast.makeText(getApplicationContext(), "You have viewed all Attractions.", Toast.LENGTH_LONG).show();
        }


    }
    //on click of Cross button the attraction will get changed
    public void dislikeAction(View view){
        view.startAnimation(animAlpha);
        if(counter < attractions.size()-1){
            counter ++;
            nameText.setText(attractions.get(counter).getName());
            imageTitle = attractions.get(counter).getPicture();
            sw.setImageResource(getResources().getIdentifier(imageTitle, "drawable", getPackageName()));
        }
        else{
            counter=0;
            nameText.setText(attractions.get(counter).getName());
            imageTitle = attractions.get(counter).getPicture();
            sw.setImageResource(getResources().getIdentifier(imageTitle, "drawable", getPackageName()));
            Toast.makeText(getApplicationContext(), "You have viewed all Attractions.", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomePage.this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            //to share an attraction with others
            case R.id.menu_item_share:

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("image/*");

                // For a file in shared storage.  For data in private storage, use a ContentProvider.
                imageTitle = attractions.get(counter).getPicture();
                int ident=getResources().getIdentifier(imageTitle, "drawable", getPackageName());
                Uri path = Uri.parse("android.resource://"+getPackageName()+"/" + ident);
                shareIntent.putExtra(Intent.EXTRA_STREAM, path);
                String shareBody = imageTitle+": \n"+attractions.get(counter).getDescription()+"\n\nDownload TourIt, an easy way to plan your trip in Chicago.";
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TourIt, a Trip made Easy !!");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(

                        shareIntent,

                        "Share Via"));

                break;

        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    //side menu options to invoke respective functionality
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        //opens an camera to take memories
        if (id == R.id.nav_camera) {

            PackageManager packageManager=getApplicationContext().getPackageManager();
            if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1888);
            }else{
                Toast.makeText(this, "Please enabled Camera to use this feature",
                        Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_gallery) {
            //opens a photo gallery of TourIt
            Attraction atr = attractions.get(counter);
            Intent intent=new Intent(this,TabActivity.class);
            intent.putExtra("tab",1);
            intent.putExtra("def",0);
            intent.putExtra("attraction", atr);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            //opens a Video gallary
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "content://media/internal/images/media"));
            startActivity(intent);
        } else if (id == R.id.onedaytrip) {
            //opens a one day trip plan of 5 locations based on user's current location
            if(isOnline()&&checkGPS()) {
                Intent intent = new Intent(this, OneDayTrip.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "No internet connection or location service,please check make sure both are enabled",
                        Toast.LENGTH_LONG).show();
            }
        }
        else if (id == R.id.nav_video) {
            //create a video of your photo memories through google photos
            String desc="Create a Video of your memories through Google Photos, Its an easiest way to create and share your memories with others. For more information click Ok.";
            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            View promptsView = li.inflate(R.layout.dialogbox, null);
            //builder is used to prompt dialog box.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePage.this);
            alertDialogBuilder.setTitle("Google Photos");
            alertDialogBuilder.setView(promptsView);

            TextView text = (TextView) promptsView.findViewById(R.id.text);
            text.setText(desc);


            ImageView image = (ImageView) promptsView.findViewById(R.id.image);
            image.setImageResource(R.drawable.gphotos);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final String appPackageName = "com.google.android.apps.photos&hl=en"; // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();




        } else if (id == R.id.favourite) {
            //opens a Favourite list of attraction
            Intent intent = new Intent(HomePage.this, FavoritesActivity.class);
            // Bundle bdl = new Bundle();
            //  bdl.putSerializable("AttrList", AttrList);
            // intent.putExtras(bdl);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            //logout from Gmail
            AppContext appContext = (AppContext)getApplicationContext();
            Auth.GoogleSignInApi.signOut(appContext.getmGoogleApiClient());
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();

        } else if (id == R.id.nav_share) {
            //share TourIt with others
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Download TourIt, an easy way to plan your trip";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TourIt, a Trip made Easy !!");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    //called when user takes phot and stores an image in to TourIt folder of sdcard
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1888 && resultCode == RESULT_OK) {
            if (data != null) {
                Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                if(isSDPresent) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    String fileName = currentDateandTime + ".jpg";
                    File direct = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TourIt");
                    if (!direct.exists()) {
                        File wallpaperDirectory = new File("/sdcard/TourIt/");
                        wallpaperDirectory.mkdirs();
                    }

                    // get the photo from intent
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    File file = new File(new File("/sdcard/TourIt/"), fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File f = new File(file.getPath());
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                        this.sendBroadcast(mediaScanIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"SD card is not available, This Functionality might not work.",Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume(){
        super.onResume();
        AppContext appContext=(AppContext)getApplicationContext();
        attractions=appContext.getAttractions();
    }

}
