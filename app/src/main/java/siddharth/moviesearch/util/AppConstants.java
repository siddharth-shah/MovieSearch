package siddharth.moviesearch.util;

/**
 * Created by siddharth on 13/5/17.
 */

public class AppConstants {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    public static final String API_KEY = "ed75ba81d3a8253393684108406b8e26";
    public static final String LANGUAGE = "en-US";

    public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int CACHE_MAX_AGE = 2; // 2 min
    public static final int CACHE_MAX_STALE = 7; // 7 day

    public static final int CODE_OK = 200;

    public static final String PORTRAIT_XLARGE = "portrait_xlarge";
    public static final String STANDARD_XLARGE = "standard_xlarge";

    public static final int SPLASH_TIMEOUT_SEC = 3 * 1000; //3 sec

    public static final int RECYCLER_VIEW_ITEM_SPACE = 48;

    public static final int API_RETRY_COUNT = 3;
    public static final String IMAGE_SIZE_LOW_DENSITY = "w92";
    public static final String IMAGE_SIZE_MEDIUM_DENSITY = "w154";
    public static final String IMAGE_SIZE_HIGH_DENSITY = "w185";
    public static final String IMAGE_SIZE_XHIGH_DENSITY = "w342";
    public static final String IMAGE_SIZE_XXHIGH_DENSITY = "w500";
    public static final String IMAGE_SIZE_XXXHIGH_DENSITY = "w782";
    public static final String IMAGE_SIZE_XXXXHIGH_DENSITY = "original";
    public static final String IMAGE_SIZE = "image_size";

}
