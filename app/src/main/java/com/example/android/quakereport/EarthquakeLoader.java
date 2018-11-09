package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Mayank on 11/10/18 at 4:41 AM
 **/
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
