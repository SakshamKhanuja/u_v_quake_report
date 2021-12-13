package com.basic.quake_report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Provides {@link EarthquakeViewHolder} to {@link R.id#recycler_earthquake} RecyclerView on demand.
 */
public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    // Stores a list of Earthquakes.
    private ArrayList<Earthquake> mEarthquakeList;

    // Used to provide click facility to Adapter's contents.
    private final EarthquakeListItemClickListener mListItemClickListener;

    // Interface provides click facility to RecyclerView.
    public interface EarthquakeListItemClickListener {

        /**
         * This method gets invoked when user clicks any item in the {@link EarthquakeAdapter}.
         *
         * @param clickedEarthquakeUrl It is the USGS url of the clicked Earthquake.
         */
        void onListItemClick(String clickedEarthquakeUrl);
    }

    /**
     * Initializes Adapter to provide contents of an ArrayList of type {@link Earthquake} to
     * a RecyclerView.
     *
     * @param listItemClickListener Interface provides click facility to list items.
     */
    public EarthquakeAdapter(EarthquakeListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates "layout_list_item.xml".
        return new EarthquakeViewHolder(((LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        // Get Earthquake based on adapter set position.
        Earthquake earthquake = mEarthquakeList.get(position);

        // Binds "earthquake" data to holder at "position".
        holder.setData(earthquake.getMagnitude(), earthquake.getOffset(),
                earthquake.getPrimaryLocation(), earthquake.getDate(), earthquake.getTime());
    }


    @Override
    public int getItemCount() {
        if (mEarthquakeList != null) {
            return mEarthquakeList.size();
        }

        // No data present.
        return 0;
    }

    /**
     * Sets Earthquake Data.
     *
     * @param earthquakes It is the new list of {@link Earthquake} on which this adapter will
     *                    work.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setEarthquakeData(ArrayList<Earthquake> earthquakes) {
        mEarthquakeList = earthquakes;
        notifyDataSetChanged();
    }

    // Caches the views.
    protected class EarthquakeViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        // Used to set background color for magnitude.
        private final Context context;

        // Shows the magnitude of the Earthquake.
        private final TextView magnitude;

        // Shows the location offset of the Earthquake.
        private final TextView offset;

        // Shows the location where Earthquake occurred.
        private final TextView location;

        // Shows the date on which Earthquake occurred.
        private final TextView date;

        // Shows the time of day.
        private final TextView time;

        // Initializes the ViewHolder to send it to the RecyclerView.
        public EarthquakeViewHolder(View itemView) {
            super(itemView);

            // Initialize Context.
            context = itemView.getContext();

            // Attach OnClickListener to "itemView".
            itemView.setOnClickListener(this);

            // Initialize all views.
            magnitude = itemView.findViewById(R.id.magnitude);
            offset = itemView.findViewById(R.id.location_offset);
            location = itemView.findViewById(R.id.primary_location);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }

        /**
         * Finds the background to {@link R.id#magnitude} TextView based on earthquake's magnitude.
         *
         * @return Background color.
         */
        public int getMagnitudeBackground(double magnitude) {
            switch ((int) magnitude) {
                case 0:
                case 1:
                    return R.color.magnitude1;
                case 2:
                    return R.color.magnitude2;
                case 3:
                    return R.color.magnitude3;
                case 4:
                    return R.color.magnitude4;
                case 5:
                    return R.color.magnitude5;
                case 6:
                    return R.color.magnitude6;
                case 7:
                    return R.color.magnitude7;
                case 8:
                    return R.color.magnitude8;
                case 9:
                    return R.color.magnitude9;
                default:
                    return R.color.magnitude10plus;
            }
        }

        /**
         * Binds data to list item View i.e. contents of {@link R.layout#layout_list_item} layout.
         *
         * @param magnitude It is the magnitude of Earthquake.
         * @param offset    It is the exact distance (if available) where earthquake occurred from
         *                  the reported location.
         * @param location  The location where the earthquake occurred.
         * @param date      Date at when the earthquake occurred.
         * @param time      Time during the date when earthquake occurred.
         */
        public void setData(double magnitude, String offset, String location, String date,
                            String time) {
            // Sets magnitude.
            this.magnitude.setText(String.valueOf(magnitude));

            // Sets magnitude background.
            GradientDrawable gradientDrawable = (GradientDrawable) this.magnitude.getBackground();
            gradientDrawable.setColor(ContextCompat.getColor(context,
                    getMagnitudeBackground(magnitude)));

            // Sets offset.
            this.offset.setText(offset);

            // Sets location.
            this.location.setText(location);

            // Sets date.
            this.date.setText(date);

            // Sets time.
            this.time.setText(time);
        }

        @Override
        public void onClick(View v) {
            // Set clicked Earthquake's url to ListItemClickListener.
            mListItemClickListener.onListItemClick(
                    mEarthquakeList.get(getAdapterPosition()).getUrl());
        }
    }
}