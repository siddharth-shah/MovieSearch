package siddharth.moviesearch;

import javax.inject.Singleton;

import dagger.Component;
import siddharth.moviesearch.data.network.ApiModule;
import siddharth.moviesearch.data.network.ClientModule;
import siddharth.moviesearch.search.SearchModule;
import siddharth.moviesearch.search.SearchSubComponent;

/**
 * Created by siddharth on 13/5/17.
 */
@Singleton
@Component(modules = {
        AndroidModule.class,
        ApiModule.class,
        MovieApplicationModule.class,
        ClientModule.class
})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);



    SearchSubComponent plus(SearchModule searchModule);


}
