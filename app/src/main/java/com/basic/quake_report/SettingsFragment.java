package com.basic.quake_report;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

/**
 * Allows user to change "Order By" and "Minimum Magnitude" of Earthquakes grabbed from the USGS
 * web-servers.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener {

    // Notifies user a problem occurred while saving the Minimum Magnitude.
    private Toast mToast;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Inflating and adding PreferenceScreen to the current view hierarchy.
        addPreferencesFromResource(R.xml.pref_settings);

        // Initializing SharedPreference from the PreferenceScreen.
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        // Iterating through all Preferences present in the PreferenceScreen to set summary.
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {

            Preference preference = preferenceScreen.getPreference(i);

            // Attach a OnPreferenceChangeListener only to EditTextAppearance.
            if (preference instanceof EditTextPreference) {
                preference.setOnPreferenceChangeListener(this);
            }

            // Sets summary.
            setPreferenceSummary(preference, sharedPreferences.getString(preference.getKey(),
                    ""));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Registering OnSharedPreferenceChangeListener.
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Un-registering OnSharedPreferenceChangeListener.
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Sets a summary to the preference.
     *
     * @param preference Type of Preference present in the PreferenceScreen.
     * @param value      The new value entered / chosen by the user.
     */
    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof EditTextPreference) {
            // EditTextPreference - "Minimum Magnitude".
            EditTextPreference editTextPreference = (EditTextPreference) preference;

            // Set summary.
            editTextPreference.setSummary(value);
        } else {
            // ListPreference - "Order By".
            ListPreference listPreference = (ListPreference) preference;

            // Get index containing the "value".
            int clickedIndex = listPreference.findIndexOfValue(value);

            // Checks if index is valid.
            if (clickedIndex >= 0) {
                // Get title based on "clickedIndex".
                String title = listPreference.getEntries()[clickedIndex].toString();

                // Set summary.
                listPreference.setSummary(title);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Finds the clicked preference.
        Preference preference = findPreference(key);

        if (preference != null) {
            setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
        }
    }

    /**
     * Shows a Toasts to user when there is a problem setting the minimum magnitude for
     * earthquakes.
     *
     * @param message String resource ID.
     */
    private void showToast(int message) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // Only EditTextPreference setting the Minimum Magnitude is checked.
        if (preference instanceof EditTextPreference) {
            try {
                // Casting the entered input into an integer.
                int minMag = Integer.parseInt(newValue.toString());
                // Checks whether entered magnitude is greater than 8.
                if (minMag > 8) {
                    showToast(R.string.toast_max_min_mag);
                } else {
                    // Entered minimum magnitude is within limits. Value entered can be saved.
                    return true;
                }
            } catch (NumberFormatException e) {
                // User didn't entered an integer.
                showToast(R.string.toast_incorrect_type);
            }
        }

        // Entered value is either invalid, or out of bounds. This value cannot be saved.
        return false;
    }
}