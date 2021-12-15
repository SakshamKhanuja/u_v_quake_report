package com.basic.quake_report.utils;

/**
 * Interface contains all constants to form a URL to access earthquake data from the USGS web
 * servers.
 */
public interface NetworkUtilsConstants {
    // Domain for constructing the USGS API Endpoint.
    String DOMAIN = "https://earthquake.usgs.gov";

    // Path for constructing the USGS API Endpoint.
    String PATH = "fdsnws/event/1/query";

    // Query parameter is used to set the format of data.
    String PARAMETER_FORMAT_KEY = "format";

    // Values sets the format of data be JSON.
    String PARAMETER_FORMAT_VALUE = "geojson";

    // Query parameter is used to set the starting date of the earthquake occurrences.
    String PARAMETER_START_KEY = "starttime";

    // Value sets the starting date to be 11th Dec, 2021.
    String PARAMETER_START_VALUE = "2021-12-11";

    // Query parameter is used to set the ending data of the earthquake occurrences.
    String PARAMETER_END_KEY = "endtime";

    // Value sets the ending data to be 13th Dec, 2021.
    String PARAMETER_END_VALUE = "2021-12-13";

    // Query parameter is used to set the minimum magnitude for earthquakes.
    String PARAMETER_MIN_MAG_KEY = "minmagnitude";

    // Query parameter is used to limit the number of earthquake occurrences returned.
    String PARAMETER_LIMIT_KEY = "limit";

    // Value sets the max number of returned earthquakes to be 100.
    String PARAMETER_LIMIT_VALUE = "100";

    // Query parameter is used to order earthquakes by either magnitude or time.
    String PARAMETER_ORDER_BY_KEY = "orderby";

    // Used for logging.
    String TAG = "NetworkUtils";

    // Represents that the servers in USGS has accepted the app's request to download data.
    int STATUS_OK = 200;

    String EMPTY_STRING = "";
}
