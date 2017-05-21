package siddharth.moviesearch.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by siddharth on 15/5/17.
 */

public class Images {

    @SerializedName("poster_sizes")
    List<String> posterSizes;

    public Images(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }
}
