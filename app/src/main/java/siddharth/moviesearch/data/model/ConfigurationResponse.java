package siddharth.moviesearch.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by siddharth on 15/5/17.
 */

public class ConfigurationResponse {
    @SerializedName("images")
    Images images;

    public ConfigurationResponse(Images images) {
        this.images = images;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}

