package phonebook.vinitshah.com.tourit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import helper.Attraction;
import helper.AttractionsDBHelper;
import helper.FavoritesDBHelper;
//Display the favourite list for user
public class FavoritesActivity extends AppCompatActivity {

    FavoritesDBHelper favDB;
    AttractionsDBHelper attrDB;
    ListView AttrListView;
    ArrayList<Attraction> favList;
    AttractionAdapter2 AA;
    LayoutInflater inflater;
    String personEmail;
    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("My Favorites");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i("FavoritesActivity", "Favourites of user");
        favList = new ArrayList<Attraction>();
        favDB = new FavoritesDBHelper(this);
        SharedPreferences prefs = getSharedPreferences("tourit", 0);
        String personName = prefs.getString("personName", null);
        personEmail = prefs.getString("personEmail", null);
        Cursor res= favDB.getAllFavorites(personEmail);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            Log.i("FavoritesActivity", "Populating the list based on selection");
            Attraction attraction = new Attraction(res.getInt(0), res.getString(1),
                    res.getString(2),
                    res.getDouble(3), res.getDouble(4),
                    res.getString(5),  res.getString(6), // -------> default values for working hours. have to parse to time format.
                    res.getString(7), res.getString(8),
                    res.getFloat(9),res.getString(10));
            //---->picture value is null as of now.need to change depending on how we gonna store
            favList.add(attraction);
            res.moveToNext();
        }
        AttrListView = (ListView) findViewById(R.id.listView);
        Toast.makeText(getApplicationContext(), "To remove an attraction, long press on attraction.", Toast.LENGTH_LONG).show();


        inflater = getLayoutInflater();
        LayoutInflater inflater =getLayoutInflater();

            AA = new AttractionAdapter2(this, favList);
            AttrListView.setAdapter(AA);


        //on click of attraction attraction details will loads up
        AttrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(FavoritesActivity.this, TabActivity.class);
                Bundle bdl = new Bundle();
                bdl.putSerializable("attraction", favList.get(position));
                intent.putExtras(bdl);
                startActivity(intent);

            }
        });
        //on long click of attraction user can remove an attraction from the list of favourites
        AttrListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Attraction attraction=(Attraction)parent.getItemAtPosition(position);
                favList.remove(position);
                String[] arr=new String[]{attraction.getName()};
                favDB.deleteFavorites(arr);
                AA.notifyDataSetChanged();
                return true;
            }
        });

    }

    //adapter class for favourite list
    public class AttractionAdapter2 extends BaseAdapter {

        private ArrayList<Attraction> mAttr;
        private LayoutInflater mInflater;
       // private Context context;
        //int picId ;
        public AttractionAdapter2(Context context,ArrayList<Attraction> attLst){
            // mInflater = inflater;
            this.mAttr = attLst;
            this.mInflater = LayoutInflater.from(context);
            //this.context = context;
        }

        @Override
        public int getCount() {

            return mAttr.size();
        }

        @Override
        public Object getItem(int position) {
            return mAttr.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }



        public   class ViewHolder
        {
            public ImageView photo;
            public TextView name;
            public TextView dscp;
            public RatingBar rt;
            public int position;

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder ;
            //((BitmapDrawable) holder.photo.getDrawable()).getBitmap().recycle();


            if(convertView == null)
            {
                //   System.out.println("nullnullnullnullnullnullnullnullnullnullnullnullnullnullnull");
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.single_attr, null);
                //slow loading
                holder.photo = (ImageView) convertView.findViewById(R.id.img);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.dscp = (TextView) convertView.findViewById(R.id.dcsp);
                holder.rt = (RatingBar) convertView.findViewById(R.id.ratingBar);
                //  System.out.println("findfindfindfindfindfindfindfindfindfindfindfindfindfindfind");

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }



            // System.out.println(mAttr.get(position).getId());

            String imageTitle = mAttr.get(position).getPicture();
             holder.photo.setImageResource(getResources().getIdentifier(imageTitle, "drawable", getPackageName()));
            //

            holder.name.setText(mAttr.get(position).getName());
            holder.dscp.setText(mAttr.get(position).getDescription());
            holder.rt.setRating(mAttr.get(position).getRating());
            LayerDrawable stars = (LayerDrawable) holder.rt.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.rgb(233, 162, 34), PorterDuff.Mode.SRC_ATOP);
/*
//////////////////////////////////////////////////////////////////////////////////////////////
            new AsyncTask<ViewHolder, Void, Integer>() {
                private ViewHolder v;




                protected Integer doInBackground(ViewHolder... params) {
                    // Bitmap pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.willistower);
                    //String uri = "drawable/"+mAttr.get(position).getPicture();
                   // System.out.println(uri);
                    //int id = Resources.getSystem().getIdentifier(mAttr.get(position).getPicture(), "drawable", "com.example.hao.guestattraction");
                  //  System.out.println(getBaseContext().getPackageName());
                    //  int id = getResources().getIdentifier(mAttr.get(position).getPicture(), "drawable", getPackageName());
                    // System.out.println(id+"0000000000000000000000000000");
                    v = params[0];
                    System.out.println( mAttr.get(position).getId());
                    // v.photo.setImageResource(2130837669);
                    return mAttr.get(position).getId();//.photo.setImageResource(mAttr.get(position).getId();
                    //return mAttr.get(position).getId();
                }

                protected void onPostExecute(Integer result) {
                    //super.onPostExecute(result);
                    //Not work
                    if (v.position == position) {

                    }

////``              setImageBitmap(android.graphics.Bitmap)
                    v.photo.setImageResource(result);
                    // }

                }
            }.execute(holder);
*/

            return convertView;
        }




    }

}
