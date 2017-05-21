package siddharth.moviesearch.search;

import siddharth.moviesearch.base.BasePresenter;

/**
 * Created by siddharth on 14/5/17.
 */

interface SearchPresenter extends BasePresenter<SearchView> {

    void getConfiguration();

    void doSearch(String query, int currentPage);

    boolean isQueryValid(String query);

    void onReachEndOfPageLoadMore();

    void resetPresenter(String query);


}
