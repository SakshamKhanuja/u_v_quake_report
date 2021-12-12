package com.basic.quake_report;

/**
 * Defines Earthquake and its components.
 */
public class Earthquake {
    // Stores the magnitude of the Earthquake.
    private double magnitude;

    /**
     * Stores the location offset - "Near the" / Exact distance from
     * {@link Earthquake#primaryLocation}.
     */
    private String offset;

    // Stores the location where this Earthquake originated.
    private String primaryLocation;

    // Stores on which Date this Earthquake occurred.
    private String date;

    // Stores at when this Earthquake occurred.
    private String time;

    // Stores the USGS url showing more info. about this Earthquake.
    private String url;

    /**
     * Sets the magnitude of the Earthquake.
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Sets the offset of the Earthquake.
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * Sets the location of the Earthquake.
     */
    public void setPrimaryLocation(String primaryLocation) {
        this.primaryLocation = primaryLocation;
    }

    /**
     * Sets the date of the Earthquake.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the time of the Earthquake.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return The magnitude of the Earthquake.
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * @return The exact distance from the primary location of the Earthquake.
     */
    public String getOffset() {
        return offset;
    }

    /**
     * @return The location of the Earthquake.
     */
    public String getPrimaryLocation() {
        return primaryLocation;
    }

    /**
     * @return Date when the Earthquake got hit.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return Time during the day Earthquake got hit.
     */
    public String getTime() {
        return time;
    }

    /**
     * @return USGS url to access more info. about the earthquake.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url Sets the url of the Earthquake.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}