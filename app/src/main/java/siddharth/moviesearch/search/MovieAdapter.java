package siddharth.moviesearch.search;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import siddharth.moviesearch.R;
import siddharth.moviesearch.data.model.MovieViewModel;

import static siddharth.moviesearch.search.SearchFragment.LIST;

/**
 * Created by siddharth on 17/5/17.
 */

class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MovieViewModel> movieViewModels;


    Context context;

    static final int VIEW_TYPE_LOADING = 0;
    static final int VIEW_TYPE_LIST = 1;
    private int listType;
    private InteractionListener mListInteractionListener;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {

    }

    @ViewType
    private int mViewType;

    MovieAdapter(List<MovieViewModel> movieViewModels, Context context) {
        this.movieViewModels = movieViewModels;
        this.context = context;
        mViewType = VIEW_TYPE_LIST;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case VIEW_TYPE_LIST:
                final LayoutInflater layoutInflater = LayoutInflater.from(context);
                return new MovieViewHolder(listType == LIST ? layoutInflater.
                        inflate(R.layout.movie_item, parent, false) :
                        layoutInflater.inflate(R.layout.movie_item_grid, parent, false));
            case VIEW_TYPE_LOADING:
                return new
                        ProgressBarViewHolder(LayoutInflater.from(context).
                        inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }


    public void setListType(int listType) {
        this.listType = listType;

    }

    public int getListType() {
        return listType;
    }

    @Override
    public int getItemViewType(int position) {
        return movieViewModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_LIST;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return;
        }

        final MovieViewModel movieViewModel = movieViewModels.get(position);

        ((MovieViewHolder) holder).parentView.setOnClickListener(v -> mListInteractionListener
                .onListClick(movieViewModel));
        ((MovieViewHolder) holder).title.setText(movieViewModel.getTitle());
        ((MovieViewHolder) holder).movieDescription.
                setText(movieViewModel.getDescription());
        Picasso.with(context).load(movieViewModel.getImageUrl()).
                fit().placeholder(R.mipmap.place_holder).
                into(((MovieViewHolder) holder).imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        ((MovieViewHolder) holder).progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        ((MovieViewHolder) holder).progressBar.setVisibility(View.GONE);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return movieViewModels.size();
    }

    private void add(MovieViewModel item) {
        add(null, item);
    }

    void add(@Nullable Integer position, MovieViewModel item) {
        if (position != null) {
            movieViewModels.add(position, item);
            notifyItemInserted(position);
        } else {
            movieViewModels.add(item);
            notifyItemInserted(movieViewModels.size() - 1);
        }
    }


    void addItems(List<MovieViewModel> movieViewModels) {
        this.movieViewModels.addAll(movieViewModels);
        notifyItemRangeInserted(getItemCount(), movieViewModels.size() - 1);
    }

    private void remove(int position) {
        if (movieViewModels.size() < position) {
            return;
        }
        movieViewModels.remove(position);
        notifyItemRemoved(position);
    }

    void removeAll() {
        movieViewModels.clear();
        notifyDataSetChanged();
    }

    boolean addLoadingView() {
        if (getItemViewType(movieViewModels.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    boolean removeLoadingView() {
        if (movieViewModels.size() > 1) {
            int loadingViewPosition = movieViewModels.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    boolean isEmpty() {
        return getItemCount() == 0;
    }


    class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        ProgressBarViewHolder(View view) {
            super(view);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.movie_description)
        TextView movieDescription;
        @BindView(R.id.movie_img)
        ImageView imageView;
        @BindView(R.id.image_progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.parent_view)
        CardView parentView;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface InteractionListener {
        void onListClick(MovieViewModel movieViewModel);
    }

    void setListInteractionListener(InteractionListener listInteractionListener) {
        mListInteractionListener = listInteractionListener;
    }
}