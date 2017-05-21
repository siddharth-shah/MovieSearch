package siddharth.moviesearch;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by siddharth on 13/5/17.
 */

@Module
public class AndroidModule {

    private MovieApplication application;

    public AndroidModule(MovieApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return application.getResources();
    }


}
