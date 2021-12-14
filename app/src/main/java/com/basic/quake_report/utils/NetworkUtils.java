package com.basic.quake_report.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.basic.quake_report.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Performs all Network operations to download earthquake data from the USGS' web-servers.
 */
public class NetworkUtils implements NetworkUtilsConstants {

    // Shows change in network state to user.
    private static Toast mToast;

    // Setting constructor private.
    private NetworkUtils() {
    }

    /**
     * Checks whether internet is available to access USGS API.
     *
     * @param context Context accesses ConnectivityManager.
     */
    public static void isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // For Devices running Android API 24 (Nougat) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            connectivityManager.registerDefaultNetworkCallback(
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(@NonNull Network network) {
                            Variables.isNetworkConnected = true;
                        }

                        @Override
                        public void onLost(@NonNull Network network) {
                            Variables.isNetworkConnected = false;
                            // Notify user that network is lost via Toast.
                            showBrowserNotAvailable(context, R.string.toast_no_internet);
                        }
                    });
        } else {
            // For Devices running Android API 23 and lower.
            Variables.isNetworkConnected = (connectivityManager.getActiveNetworkInfo() != null &&
                    connectivityManager.getActiveNetworkInfo().isConnected());
        }
    }

    /**
     * Notify user about network connectivity changes.
     */
    private static void showBrowserNotAvailable(Context context, int message) {
        // Removes any previous visible Toasts.
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Forms a URL to connect to the USGS API Endpoint to download list of occurred earthquakes.
     *
     * @return A URL that points to the 'query' section of the USGS api.
     */
    private static URL makeUrl() {
        try {
            // Initialize a Uri.Builder object for building a valid URL.
            Uri.Builder builder = Uri.parse(DOMAIN).buildUpon()
                    .path(PATH)
                    .appendQueryParameter(PARAMETER_FORMAT_KEY, PARAMETER_FORMAT_VALUE)
                    .appendQueryParameter(PARAMETER_START_KEY, PARAMETER_START_VALUE)
                    .appendQueryParameter(PARAMETER_END_KEY, PARAMETER_END_VALUE)
                    .appendQueryParameter(PARAMETER_MIN_MAG_KEY, PARAMETER_MIN_MAG_VALUE)
                    .appendQueryParameter(PARAMETER_LIMIT_KEY, PARAMETER_LIMIT_VALUE);

            // Forming a URL.
            return new URL(builder.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Unable to form the URL - " + e.getMessage());
            return null;
        }
    }

    /**
     * Connects and retrieves a JSON response containing occurred earthquake info. from USGS Api
     * Endpoint.
     *
     * @return A String containing the JSON response.
     */
    public static String getEarthquakeInfo() {
        // Get URL to form the network connection.
        URL url = makeUrl();

        if (url != null) {
            // Creates a HTTP request.
            HttpURLConnection urlConnection = null;

            // Downloads bytes of data from the USGS web-servers.
            InputStream inputStream = null;

            // Parses downloaded info. to a single String.
            Scanner scanner = null;

            try {
                // Network request set for resource set by URL.
                urlConnection = (HttpURLConnection) url.openConnection();

                // Establish Connection.
                urlConnection.connect();

                // Stores the request response.
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == STATUS_OK) {
                    // Downloading data.
                    inputStream = urlConnection.getInputStream();

                    // Parsing stream of data into a single String.
                    scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");

                    // Returning available data or empty String.
                    return scanner.hasNext() ? scanner.next() : EMPTY_STRING;
                }

                // Request Failed.
                Log.e(TAG, "Request denied - " + responseCode);

            } catch (IOException e) {
                Log.e(TAG, "Connection Failed - " + e.getMessage());
            } finally {
                // Close connection.
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                // Close InputStream to free stream related resources.
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.v(TAG, "Could not close the InputStream - " + e.getMessage());
                    }
                }

                // Close Scanner to free memory resources.
                if (scanner != null) {
                    scanner.close();
                }
            }
        }

        // Returning empty String.
        return EMPTY_STRING;
    }
}