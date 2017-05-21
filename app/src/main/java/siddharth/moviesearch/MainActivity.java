package siddharth.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import javax.inject.Inject;

import siddharth.moviesearch.base.BaseActivity;
import siddharth.moviesearch.search.SearchActivity;
import siddharth.moviesearch.util.AppConstants;

import static siddharth.moviesearch.util.AppConstants.IMAGE_SIZE;

public class MainActivity extends BaseActivity {
    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(IMAGE_SIZE, imageSizeForTheDevice()).apply();
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void injectDependencies(MovieApplication application, ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void releaseSubComponents(MovieApplication application) {

    }

    private String imageSizeForTheDevice() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
            case DisplayMetrics.DENSITY_MEDIUM:
                return AppConstants.IMAGE_SIZE_LOW_DENSITY;
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_XXHIGH:
                return AppConstants.IMAGE_SIZE_MEDIUM_DENSITY;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return AppConstants.IMAGE_SIZE_XXXHIGH_DENSITY;
            default:
                return "original";
        }
    }
}
