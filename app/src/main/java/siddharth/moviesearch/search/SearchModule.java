package siddharth.moviesearch.search;

import dagger.Module;
import dagger.Provides;

/**
 * Created by siddharth on 14/5/17.
 */

@Module
public class SearchModule {
    @Provides
    @Search
    public SearchPresenter provideSearchPresenter(SearchPresenterImpl searchPresenter) {
        return searchPresenter;
    }


}
