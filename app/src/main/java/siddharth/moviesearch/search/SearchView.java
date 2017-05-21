package siddharth.moviesearch.search;

import java.util.List;

import siddharth.moviesearch.base.BaseView;
import siddharth.moviesearch.data.model.MovieViewModel;

/**
 * Created by siddharth on 14/5/17.
 */

public interface SearchView extends BaseView {

    void showItems(List<MovieViewModel> movieViewModelList);

    void clearList();


}
