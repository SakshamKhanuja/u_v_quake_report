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

    // Fallback value applied when "mag" JSON primitive is not found during JSON parsing.
    double FALLBACK_MAG = 0.0;

    // Points to a primitive having key "place".
    String VALUE_PLACE = "place";

    // Points to a primitive having key "time".
    String VALUE_TIME = "time";

    // Fallback value applied when "time" JSON primitive is not found during JSON parsing.
    long FALLBACK_TIME = 0L;

    // Used for formatting earthquake's magnitude.
    String PATTERN_DECIMAL = "0.0";

    // Points to a primitive having key "url".
    String VALUE_URL = "url";

    // Used for setting earthquake's offset.
    String SEQUENCE = " of ";

    // Offset applied when no offset is provided.
    String DEFAULT_OFFSET = "Near The";

    // Fallback value applied when any String JSON primitive is not found during JSON parsing.
    String FALLBACK_STRING = "";

    // Used for formatting earthquake's date.
    String PATTERN_DATE_FORMAT = "MMM dd, yyyy";

    // Used for formatting earthquake's time.
    String PATTERN_TIME_FORMAT = "h:mm a";

    // Used to set Locale to India.
    String LOCALE_LANGUAGE = "eng";

    // Used to set Locale to India.
    String LOCALE_COUNTRY = "IN";
}