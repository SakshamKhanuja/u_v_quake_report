package com.basic.quake_report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.basic.quake_report.databinding.ActivityMainBinding;
import com.basic.quake_report.utils.JSONUtils;
import com.basic.quake_report.utils.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        EarthquakeAdapter.EarthquakeListItemClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    // Shows messages to the user.
    private Toast mToast;

    // Represents the Loader ID of the Loader which performs background network operations.
    private static final int LOADER_ID = 19;

    /**
     * Provides {@link com.basic.quake_report.EarthquakeAdapter.EarthquakeViewHolder} to
     * RecyclerView on demand.
     */
    private EarthquakeAdapter mAdapter;

    // Performs View Binding.
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate((LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        setContentView(mBinding.getRoot());

        // Link LayoutManager to RecyclerView.
        mBinding.recyclerEarthquake.setLayoutManager(new LinearLayoutManager(this));

        // Optimise RecyclerView.
        mBinding.recyclerEarthquake.setHasFixedSize(true);

        // Link Adapter to RecyclerView.
        mAdapter = (new EarthquakeAdapter(this));
        mBinding.recyclerEarthquake.setAdapter(mAdapter);

        // Starts the background task.
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onListItemClick(String clickedEarthquakeUrl) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(clickedEarthquakeUrl)));
        } catch (ActivityNotFoundException e) {
            showBrowserNotAvailable();
        }
    }

    /**
     * Notify user that there is no Browser installed in their deivice via Toast.
     */
    private void showBrowserNotAvailable() {
        // Removes any previous visible Toasts.
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, getString(R.string.toast_browser), Toast.LENGTH_SHORT);
        mToast.show();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Earthquake>>(this) {

            // Will contain a list of occurred Earthquakes.
            private ArrayList<Earthquake> mEarthquakes = null;

            @Override
            protected void onStartLoading() {
                if (mEarthquakes == null) {
                    // Perform background operation.
                    forceLoad();
                } else {
                    // Cache present, no need to perform background operation.
                    deliverResult(mEarthquakes);
                }
            }

            @Nullable
            @Override
            public ArrayList<Earthquake> loadInBackground() {
                // Downloading earthquake info. here.
                String jsonResponse = NetworkUtils.getEarthquakeInfo();

                // Parsing JSON response to an ArrayList of type Earthquake.
                return JSONUtils.getEarthquakes(jsonResponse);
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Earthquake> data) {
                // Caching the downloaded earthquake info.
                if (data != null && data.size() > 0) {
                    mEarthquakes = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquake>> loader,
                               ArrayList<Earthquake> data) {
        // Hide the progress indicator.
        mBinding.progressBar.setVisibility(View.GONE);

        // Notifying RecyclerView that changes to the adapter are made.
        mAdapter.setEarthquakeData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquake>> loader) {
        //  Method is left EMPTY.
    }
}