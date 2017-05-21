package siddharth.moviesearch.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import siddharth.moviesearch.ApplicationComponent;
import siddharth.moviesearch.MovieApplication;

/**
 * Created by siddharth on 13/5/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(MovieApplication.get(this), MovieApplication.getComponent());

        // can be used for general purpose in all Activities of Application
    }

    protected abstract void injectDependencies(MovieApplication application, ApplicationComponent component);

    @Override
    public void finish() {
        super.finish();

        releaseSubComponents(MovieApplication.get(this));
    }

    protected abstract void releaseSubComponents(MovieApplication application);
}
