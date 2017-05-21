package siddharth.moviesearch;

import android.app.Application;
import android.content.Context;

import siddharth.moviesearch.search.SearchModule;
import siddharth.moviesearch.search.SearchSubComponent;

/**
 * Created by siddharth on 13/5/17.
 */

public class MovieApplication extends Application {

    private static ApplicationComponent component;
    private SearchSubComponent searchSubComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        component = createComponent();
    }

    private ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder().
                androidModule(new AndroidModule(this)).
                build();
    }


    public static MovieApplication get(Context context) {
        return (MovieApplication) context.getApplicationContext();
    }

    public SearchSubComponent getSearchSubComponent() {
        if (searchSubComponent == null) {
            createSearchComponent();
        }
        return searchSubComponent;
    }

    private void createSearchComponent() {
        searchSubComponent = component.plus(new SearchModule());
    }

    public static ApplicationComponent getComponent() {
        return component;
    }


}
