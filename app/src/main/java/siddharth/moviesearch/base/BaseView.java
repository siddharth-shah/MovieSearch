package siddharth.moviesearch.base;

/**
 * Created by siddharth on 13/5/17.
 */

public interface BaseView {
    void showMessageLayout(boolean show);

    void showErrorMessage(String message);

    void showProgress();

    void hideProgress();

    void showEmpty();


}
