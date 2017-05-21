package siddharth.moviesearch.base;

/**
 * Created by siddharth on 13/5/17.
 */

public interface BasePresenter<T> {
    void bind(T view);

    void unbind();

}
