package siddharth.moviesearch.search;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import siddharth.moviesearch.ApplicationComponent;
import siddharth.moviesearch.MovieApplication;
import siddharth.moviesearch.R;
import siddharth.moviesearch.base.BaseActivity;
import siddharth.moviesearch.data.model.MovieViewModel;

import static siddharth.moviesearch.search.SearchFragment.GRID;
import static siddharth.moviesearch.search.SearchFragment.LIST;

public class SearchActivity extends BaseActivity implements SearchView, android.support.v7.widget.SearchView.OnQueryTextListener, TabLayout.OnTabSelectedListener {


    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.main_tabs)
    TabLayout tabs;


    @Inject
    Context context;

    @Inject
    SearchPresenter searchPresenter;

    private PagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie Search");
        fragmentPagerAdapter =
                new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(fragmentPagerAdapter);

        tabs.addOnTabSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        int listType = fragmentPagerAdapter.getItem(0).getListType();
        MenuItem gridlist = menu.findItem(R.id.list_grid_switch);

        if (listType == LIST) {
            gridlist.setIcon(R.drawable.ic_view_list_grid_24dp);
        } else {

            gridlist.setIcon(R.drawable.ic_view_grid_white_24dp);
        }

        return true;


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void injectDependencies(MovieApplication application, ApplicationComponent component) {
        application.getSearchSubComponent().inject(this);

    }

    @Override
    protected void releaseSubComponents(MovieApplication application) {

    }

    @Override
    public void showMessageLayout(boolean show) {
        fragmentPagerAdapter.
                getItem(0).showMessageLayout(show);


    }

    @Override
    public void showErrorMessage(String message) {
        fragmentPagerAdapter.
                getItem(pager.getCurrentItem()).showErrorMessage(message);


    }

    @Override
    public void showProgress() {
        fragmentPagerAdapter.
                getItem(pager.getCurrentItem()).showProgress();
    }

    @Override
    public void hideProgress() {
        fragmentPagerAdapter.
                getItem(0).hideProgress();
    }

    @Override
    public void showEmpty() {
        fragmentPagerAdapter.
                getItem(pager.getCurrentItem()).showEmpty();
    }

    @Override
    public void showItems(List<MovieViewModel> movieViewModelList) {
        fragmentPagerAdapter.
                getItem(pager.getCurrentItem()).showItems(movieViewModelList);
    }

    @Override
    public void clearList() {
        fragmentPagerAdapter.
                getItem(pager.getCurrentItem()).clearList();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (searchPresenter.isQueryValid(query)) {
            searchPresenter.resetPresenter(query);
            searchPresenter.doSearch(query, 1);
        } else {
            Toast.makeText(context, "Enter a valid query", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            fragmentPagerAdapter.getItem(0).setRecyclerViewLayoutManager(LIST);
        } else {
            fragmentPagerAdapter.getItem(0).setRecyclerViewLayoutManager(GRID);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    class PagerAdapter extends FragmentPagerAdapter {
        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        SearchFragment searchFragment;

        @Override
        public SearchFragment getItem(int position) {
            switch (position) {
                case 0:
                    if (searchFragment == null)
                        searchFragment = SearchFragment.newInstance();
                    return searchFragment;

            }
            return null;
        }


        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "List";
            }
            return super.getPageTitle(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchPresenter.bind(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        searchPresenter.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.list_grid_switch) {
            final SearchFragment searchFragment = fragmentPagerAdapter.getItem(0);
            int listType = searchFragment.getListType();
            if (listType == LIST) {
                searchFragment.setRecyclerViewLayoutManager(GRID);
                invalidateOptionsMenu();
            } else {
                searchFragment.setRecyclerViewLayoutManager(LIST);
                invalidateOptionsMenu();
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
