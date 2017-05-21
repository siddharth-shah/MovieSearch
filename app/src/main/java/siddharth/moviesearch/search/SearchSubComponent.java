package siddharth.moviesearch.search;

import dagger.Subcomponent;

/**
 * Created by siddharth on 14/5/17.
 */
@Search
@Subcomponent(modules = {SearchModule.class})
public interface SearchSubComponent {

    void inject(SearchFragment fragment);

    void inject(SearchActivity searchActivity);

}
