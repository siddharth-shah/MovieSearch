package siddharth.moviesearch.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by siddharth on 15/5/17.
 */

public class MovieResponse {
    @SerializedName("page")
    int page;
    @SerializedName("results")
    List<Movie> results;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;

    public MovieResponse(int page, List<Movie> results, int totalResults, int totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
