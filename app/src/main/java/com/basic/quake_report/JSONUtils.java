package com.basic.quake_report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Traverses through a String containing JSON data to parse it an ArrayList of type
 * {@link Earthquake}.
 */
public class JSONUtils implements JSONUtilConstants {

    // Contains info. about earthquake.
    private static Earthquake mEarthquake;

    // Setting constructor to avoid.
    private JSONUtils() {
    }

    /**
     * Parses the JSON response to an ArrayList of type {@link Earthquake}.
     *
     * @param jsonResponse Received from USGS.
     * @return An ArrayList of occurred {@link Earthquake}.
     */
    public static ArrayList<Earthquake> getEarthquakes(String jsonResponse) {
        try {
            // Root of response.
            JSONObject root = new JSONObject(jsonResponse);

            // List will contain all occurred Earthquakes.
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            // Traverse to JSONArray with key "features".
            JSONArray arrayFeatures = root.optJSONArray(ARRAY_FEATURES);

            if (arrayFeatures != null) {
                for (int i = 0; i < arrayFeatures.length(); i++) {

                    mEarthquake = new Earthquake();

                    JSONObject earthquakeItem = arrayFeatures.getJSONObject(i);

                    // Traverse to JSONArray with key "properties".
                    JSONObject properties = earthquakeItem.getJSONObject(OBJECT_PROPERTIES);

                    // Sets the "earthquake" magnitude.
                    mEarthquake.setMagnitude(properties.optDouble(VALUE_MAG, FALLBACK_MAG));

                    // Sets offset and primary location.
                    setOffsetAndLocation(properties.optString(VALUE_PLACE, FALLBACK_STRING));

                    // Sets date and time.
                    setDateAndTime(properties.optLong(VALUE_TIME, FALLBACK_TIME));

                    // Sets the url.
                    mEarthquake.setUrl(properties.optString(VALUE_URL));

                    // Add Earthquake to List.
                    earthquakes.add(mEarthquake);
                }
            }
            return earthquakes;
        } catch (JSONException e) {
            // Parse FAILED or data not present to form a JSONObject.
            return null;
        }
    }

    /**
     * Sets offset and primary location of the Earthquake.
     */
    private static void setOffsetAndLocation(String location) {
        // Find first occurrence of " of ".
        int endIndex = location.indexOf(SEQUENCE);

        // Check if offset is available.
        if (endIndex != -1) {
            // Set offset.
            int index = endIndex + 3;
            mEarthquake.setOffset(location.substring(0, index));

            // Set location.
            mEarthquake.setPrimaryLocation(location.substring(index + 1));
        } else {
            // Set default offset.
            mEarthquake.setOffset(DEFAULT_OFFSET);

            // Set location.
            mEarthquake.setPrimaryLocation(location);
        }
    }

    /**
     * Sets date and time of Earthquake.
     *
     * @param time Unix-Timestamp when the earthquake occurred.
     */
    private static void setDateAndTime(long time) {
        // Parse time to custom pattern Date.
        Date date = new Date(time);
        Locale locale = new Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY);

        // Formats the "date" to pattern -> "MMM dd, yyyy".
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, locale);
        mEarthquake.setDate(dateFormat.format(date));

        // Formats the "date" to pattern -> "h:mm a".
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_PATTERN, locale);
        mEarthquake.setTime(timeFormat.format(date));
    }
}