package AdaptersPacakge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import helper.Attraction;
import phonebook.vinitshah.com.tourit.R;

/**
 * Created by Hao on 3/27/2016.
 */
//adapter for attraction list, Used during the list display of attractions.
public class AttractionAdapter extends BaseAdapter {

    private ArrayList<Attraction> mAttr; //list of attractions
    private LayoutInflater mInflater;
    private Context context;
    //int picId ;
    public AttractionAdapter(Context context,ArrayList<Attraction> attLst){
       // mInflater = inflater;
        Log.i("AttractionAdapter","Loading the Adapter for Attactions list");
        this.mAttr = attLst;
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
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

        return position; //get the view by its position to display
    }



    static class ViewHolder
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



        if(convertView == null)
        {
            Log.d("AttractionAdapter","View exist then creating the holder");
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.single_attr, null);
            //setting the list item with photo, description etc.
            holder.photo = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.dscp = (TextView) convertView.findViewById(R.id.dcsp);
            holder.rt = (RatingBar) convertView.findViewById(R.id.ratingBar);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }



        //getting the resource ID of photo and setting it to corresponding holder.
        holder.photo.setImageResource(context.getResources().getIdentifier(mAttr.get(position).getPicture(), "drawable", context.getPackageName()));
        holder.name.setText(mAttr.get(position).getName());
        holder.dscp.setText(mAttr.get(position).getDescription());
        holder.rt.setRating(mAttr.get(position).getRating());
        LayerDrawable stars = (LayerDrawable) holder.rt.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(233, 162, 34), PorterDuff.Mode.SRC_ATOP);
        Log.i("AttractionAdapter", "List view is loaded");
        return convertView;
    }




}
