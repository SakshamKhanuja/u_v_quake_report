package com.basic.quake_report;

/**
 * Interface contains all constants required to traverse through USGS JSON response.
 * <p>
 * It is implemented only by {@link JSONUtils} class.
 */
public interface JSONUtilConstants {
    // Points to JSONArray having key "features".
    String ARRAY_FEATURES = "features";

    // Points to JSONObject having key "properties".
    String OBJECT_PROPERTIES = "properties";

    // Points to a primitive having key "mag".
    String VALUE_MAG = "mag";

    double FALLBACK_MAG = 0.0;

    // Points to a primitive having key "place".
    String VALUE_PLACE = "place";

    // Points to a primitive having key "time".
    String VALUE_TIME = "time";

    long FALLBACK_TIME = 0L;

    // Points to a primitive having key "url".
    String VALUE_URL = "url";

    String SEQUENCE = " of ";

    String DEFAULT_OFFSET = "Near The";

    String FALLBACK_STRING = "";

    String DATE_FORMAT_PATTERN = "MMM dd, yyyy";

    String TIME_FORMAT_PATTERN = "h:mm a";

    String LOCALE_LANGUAGE = "eng";

    String LOCALE_COUNTRY = "IN";
}