package phonebook.vinitshah.com.tourit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
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

import helper.AppContext;
import helper.Attraction;

//used to display attraction list for guest
public class AttrationList extends Activity  {

    ListView AttrListView;
    ArrayList<Attraction> AttrList;
    AttractionAdapter2 AA;
    LayoutInflater inflater;
    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);

        AppContext appContext=(AppContext)getApplicationContext();
        AttrList=appContext.getAttractions();
        AttrListView = (ListView) findViewById(R.id.listView);


        inflater = getLayoutInflater();
        LayoutInflater inflater =getLayoutInflater();
        AA = new AttractionAdapter2(this,AttrList);

        AttrListView.setAdapter(AA);

        Log.i("AttrationList", "Attraction list for guest is loaded");
        AttrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(AttrationList.this, AttrDescription.class);
                Bundle bdl = new Bundle();
                bdl.putSerializable("Attr", AttrList.get(position));
                intent.putExtras(bdl);
                startActivity(intent);
                Toast.makeText(getBaseContext(), AttrList.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public class AttractionAdapter2 extends BaseAdapter {

        private ArrayList<Attraction> mAttr;
        private LayoutInflater mInflater;
        //private Context context;
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
            Log.i("AttrationList", "Loading the list view of attractions for guest");

            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.single_attr, null);
                //Loading the attraction list into respective holders
                holder.photo = (ImageView) convertView.findViewById(R.id.img);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.dscp = (TextView) convertView.findViewById(R.id.dcsp);
                holder.rt = (RatingBar) convertView.findViewById(R.id.ratingBar);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }



            holder.name.setText(mAttr.get(position).getName());
            holder.dscp.setText(mAttr.get(position).getDescription());
            holder.rt.setRating(mAttr.get(position).getRating());
            LayerDrawable stars = (LayerDrawable) holder.rt.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.rgb(233, 162, 34), PorterDuff.Mode.SRC_ATOP);

            new AsyncTask<ViewHolder, Void, Integer>() {
                private ViewHolder v;


/*setImageResource: This does Bitmap reading and decoding on the UI thread, which can cause a latency hiccup.

If that's a concern, consider using setImageDrawable(android.graphics.drawable.Drawable)
 or setImageBitmap(android.graphics.Bitmap) and BitmapFactory instead.
*
* */
                //getting the position of attraction anf loading its description
                protected Integer doInBackground(ViewHolder... params) {

                    String uri = "drawable/"+mAttr.get(position).getPicture();
                    System.out.println(uri);

                    int id = getResources().getIdentifier(mAttr.get(position).getPicture(), "drawable", "com.example.hao.guestattraction");
                    v = params[0];
                    System.out.println( mAttr.get(position).getId());

                    return mAttr.get(position).getId();

                }

                protected void onPostExecute(Integer result) {
                    if (v.position == position) {

                    }

                    v.photo.setImageResource(result);

                }
            }.execute(holder);


            return convertView;
        }




    }


}
