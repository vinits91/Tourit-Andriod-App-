package AdaptersPacakge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import phonebook.vinitshah.com.tourit.R;

/**
 * Created by Vinit on 01-04-2016.
 */
//adapter to display the grid layout of the photo gallery
public class GridViewAdapter extends BaseAdapter {

    // Declare variables
    private Activity activity;
    private String[] filepath;


    private static LayoutInflater inflater = null;

    public GridViewAdapter(Activity a, String[] fpath) {
        Log.d("GridViewAdapter", "Loading the grid layout adapter to display in grid");
        activity = a;
        filepath = fpath;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return filepath.length;

    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("GridViewAdapter", "Displaying images in grid view");
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.gridview_item, null);
        // Locate the imageview in gridview_item.xml
        ImageView image = (ImageView) vi.findViewById(R.id.image);
        // Decode the filepath with BitmapFactory followed by the position
        Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);
        // Set the decoded bitmap into ImageView
        image.setImageBitmap(bmp);
        return vi;
    }
}