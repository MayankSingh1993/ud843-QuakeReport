package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mayank on 11/6/18 at 1:21 PM
 **/
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            //Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject root = new JSONObject(earthquakeJSON);

            //Extract “features” JSONArray
            JSONArray featuresJSONArray = root.getJSONArray("features");

//            Loop through each feature in the array

            for (int i = 0; i < featuresJSONArray.length(); i++) {
                //Get earthquake JSONObject at position i
                JSONObject currentEarthquake = featuresJSONArray.getJSONObject(i);
                //Get “properties” JSONObject
                JSONObject properties = currentEarthquake.getJSONObject("properties");
//              Extract “mag” for magnitude
                double mag = properties.getDouble("mag");
//              Extract “place” for location
                DecimalFormat formatter = new DecimalFormat("0.0");
                String magnitude = formatter.format(mag);

                String loc = properties.getString("place");
                String primaryLocation;
                String locationOffset;
                if (loc.contains("of")) {
                    String[] splittedLoc = loc.split("of");

                    locationOffset = splittedLoc[0] + "of";

                    primaryLocation = splittedLoc[1];


                } else {
                    locationOffset = "near the";
                    primaryLocation = loc;

                }


//              Extract “time” for time
//              String time =properties.getString("time");
//              long timeInMilis=Long.parseLong(time);

                long timeInMilis = properties.getLong("time");
                //convert the time in milliseconds into a Date object by calling the Date constructor.
                Date dateObject = new Date(timeInMilis);
                // Format the date string (i.e. "Mar 3, 1984")
                String formattedDate = formatDate(dateObject);
                // Format the time string (i.e. "4:30PM")

                String formattedTime = formatTime(dateObject);
                // Extract the value for the key called "url"
                String url = properties.getString("url");
//              Create Earthquake java object from magnitude, location, and time
                Earthquake earthquake = new Earthquake(magnitude, locationOffset, primaryLocation, formattedDate, formattedTime, url);
//              Add earthquake to list of earthquakes
                earthquakes.add(earthquake);


            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;

    }

    private static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeToDisplay = timeFormat.format(dateObject);
        return timeToDisplay;
    }


    private static String formatDate(Date dateObject) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        String dateToDisplay = dateFormat.format(dateObject);
        return dateToDisplay;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */

    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        Log.i(LOG_TAG, "fetchEarthquakeData is called");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

}
