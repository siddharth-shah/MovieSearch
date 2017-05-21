package siddharth.moviesearch.data.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import siddharth.moviesearch.data.model.ConfigurationResponse;
import siddharth.moviesearch.data.model.MovieResponse;

/**
 * Created by siddharth on 13/5/17.
 */

public interface RestApi {

    String QUERY = "query";
    String API_KEY = "api_key";
    String LANGUAGE = "language";
    String PAGE = "page";

    @GET("configuration")
    Observable<ConfigurationResponse> getConfiguration(@Query(API_KEY) String apiKey);

    @GET("search/movie")
    Observable<MovieResponse> getItems(@Query(QUERY) String query,
                                       @Query(API_KEY) String apiKey, @Query(LANGUAGE) String language,
                                       @Query(PAGE) int page);


}
