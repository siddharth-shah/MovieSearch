package siddharth.moviesearch.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import siddharth.moviesearch.data.model.ConfigurationResponse;
import siddharth.moviesearch.data.model.Images;
import siddharth.moviesearch.data.model.Movie;
import siddharth.moviesearch.data.model.MovieResponse;
import siddharth.moviesearch.data.model.MovieViewModel;
import siddharth.moviesearch.data.network.RestApi;
import siddharth.moviesearch.util.AppConstants;

import static siddharth.moviesearch.util.AppConstants.IMAGE_SIZE;

/**
 * Created by siddharth on 14/5/17.
 */

public class SearchPresenterImpl implements SearchPresenter {
    private SearchView view;
    private RestApi api;

    private boolean canLoadMore;
    private int currentPage = 0;
    private String query = "";

    @Inject
    Context context;

    @Inject
    SearchPresenterImpl(RestApi restApi) {
        this.api = restApi;
    }

    @Override
    public void getConfiguration() {
        Observable<ConfigurationResponse> observable =
                api.getConfiguration("ed75ba81d3a8253393684108406b8e26");
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ConfigurationResponse::getImages)
                .map(Images::getPosterSizes)
                .map(HashSet::new)
                .subscribe(this::save);
    }

    private void save(HashSet<String> posterSizes) {
        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("poster_sizes", posterSizes).apply();
    }

    @Override
    public void doSearch(String query, int pageToLoad) {
        if (!canLoadMore) {
            view.hideProgress();
            return;
        }
        view.showProgress();
        final Observable<MovieResponse> observable = api.getItems(query,
                AppConstants.API_KEY, AppConstants.LANGUAGE, currentPage + 1);

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(movieResponse -> {
                    currentPage = movieResponse.getPage();
                    if (currentPage == movieResponse.getTotalPages())
                        canLoadMore = false;
                    return movieResponse;
                })
                .map(MovieResponse::getResults)
                .flatMap(movies -> Observable.
                        from(movies).map(this::convert).toList()
                        .asObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MovieViewModel>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        if (e.getMessage().toLowerCase().contains("unable to resolve host")) {
                            view.showErrorMessage("Internet Issue");
                        }
                    }

                    @Override
                    public void onNext(List<MovieViewModel> movieViewModels) {
                        view.hideProgress();
                        if (movieViewModels.size() == 0) {
                            view.showMessageLayout(true);
                            view.showErrorMessage("Oops! No Results!");

                        } else {
                            view.showMessageLayout(false);
                            view.showItems(movieViewModels);
                        }
                    }

                });


    }

    @Override
    public boolean isQueryValid(String query) {
        return !(query == null || query.isEmpty());
    }

    @Override
    public void onReachEndOfPageLoadMore() {
        doSearch(query, currentPage);
    }

    @Override
    public void resetPresenter(String query) {
        canLoadMore = true;
        currentPage = 0;
        this.query = query;
        view.clearList();
    }


    @Override
    public void bind(SearchView view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;

    }

    private MovieViewModel convert(Movie movie) {
        final String imageSize = PreferenceManager.
                getDefaultSharedPreferences(context).getString(IMAGE_SIZE, "original");
        return new MovieViewModel(movie.getTitle(), movie.getOverview(),
                AppConstants.IMAGE_BASE_URL + imageSize +
                        movie.getPoserPath());

    }
}
