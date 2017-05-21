package siddharth.moviesearch.search;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import siddharth.moviesearch.MovieApplication;
import siddharth.moviesearch.R;
import siddharth.moviesearch.base.BaseFragment;
import siddharth.moviesearch.data.model.MovieViewModel;


public class SearchFragment extends BaseFragment implements SearchView, MovieAdapter.InteractionListener {

    @Inject
    Context context;
    @Inject
    Resources resources;

    @BindView(R.id.item_list)
    RecyclerView itemList;
    @BindView(R.id.content_progress)
    ProgressBar contentProgress;
    @BindView(R.id.empty_layout)
    FrameLayout emptyLayout;

    private int listType;
    public static final int LIST = 0;
    public static final int GRID = 1;

    private MovieAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(context);
        itemList.setLayoutManager(mLayoutManager);
        adapter = new MovieAdapter(new ArrayList<>(0), context);
        itemList.setAdapter(adapter);
        itemList.addOnScrollListener(setupScrollListener(false, itemList.getLayoutManager()));
        adapter.setListInteractionListener(this);
        return view;
    }


    public void setListType(int listType) {
        this.listType = listType;
        switch (listType) {
            case LIST:
                itemList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            case GRID:
                itemList.setLayoutManager(new GridLayoutManager(context, 2));
        }
        adapter.setListType(listType);
        itemList.setAdapter(adapter);

    }

    public int getListType() {
        return listType;
    }

    @Override
    protected void injectDependencies(MovieApplication application) {
        application.getSearchSubComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void showMessageLayout(boolean show) {
        itemList.setVisibility(show ? View.GONE : View.VISIBLE);
        emptyLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        showMessageLayout(true);
    }


    @Override
    public void showProgress() {
        contentProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        contentProgress.setVisibility(View.GONE);
        adapter.removeLoadingView();

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showItems(List<MovieViewModel> movieViewModels) {
        adapter.addItems(movieViewModels);
    }

    @Override
    public void clearList() {
        adapter.removeAll();
    }

    private EndlessRecyclerViewOnScrollListener setupScrollListener(boolean isTabletLayout,
                                                                    RecyclerView.LayoutManager layoutManager) {
        return new EndlessRecyclerViewOnScrollListener(isTabletLayout ?
                (GridLayoutManager) layoutManager : (LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                adapter.addLoadingView();
                ((SearchActivity) getActivity()).searchPresenter.onReachEndOfPageLoadMore();

            }
        };
    }

    public void setRecyclerViewLayoutManager(int listType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (itemList.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) itemList.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (listType) {
            case GRID:
                mLayoutManager = new GridLayoutManager(context, 2);
                this.listType = GRID;
                break;
            case LIST:
                mLayoutManager = new LinearLayoutManager(context);
                this.listType = LIST;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(context);
                this.listType = LIST;
        }
        itemList.setLayoutManager(mLayoutManager);
        adapter.setListType(listType);
        itemList.setAdapter(adapter);
        itemList.scrollToPosition(scrollPosition);

    }



    @Override
    public void onListClick(MovieViewModel movieViewModel) {
        Toast.makeText(context, movieViewModel.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
