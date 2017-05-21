package siddharth.moviesearch.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by siddharth on 15/5/17.
 */

public class Movie {

    @SerializedName("title")
    String title;
    @SerializedName("overview")
    String overview;
    @SerializedName("poster_path")
    String poserPath;

    public Movie(String title, String overview, String poserPath) {
        this.title = title;
        this.overview = overview;
        this.poserPath = poserPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoserPath() {
        return poserPath;
    }

    public void setPoserPath(String poserPath) {
        this.poserPath = poserPath;
    }
}
