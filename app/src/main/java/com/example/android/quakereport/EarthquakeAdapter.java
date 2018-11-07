package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mayank on 11/6/18 at 3:28 AM
 **/
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

   private int resourceId;
    public EarthquakeAdapter(Activity context, int resourceId, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
        this.resourceId=resourceId;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Earthquake earthquake = getItem(position);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        magTextView.setText(earthquake.getMagnitude());



        TextView locOffsetTextView = (TextView) listItemView.findViewById(R.id.loc_offset);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        locOffsetTextView.setText(earthquake.getLocationOffset());

        TextView primaryLocTextView = (TextView) listItemView.findViewById(R.id.Primary_loc);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        primaryLocTextView.setText(earthquake.getPrimaryLocation());



        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        dateTextView.setText(earthquake.getDate());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        timeTextView.setText(earthquake.getTime());


            // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
