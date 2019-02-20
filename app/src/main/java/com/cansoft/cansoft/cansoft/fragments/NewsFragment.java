package com.cansoft.cansoft.cansoft.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;
import com.cansoft.cansoft.cansoft.adapter.AllNewsPaginationAdapter;
import com.cansoft.cansoft.cansoft.model.Post;
import com.cansoft.cansoft.cansoft.network.RestClient;
import com.cansoft.cansoft.cansoft.util.PaginationScrollListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private static final String TAG = "";
    RecyclerView recentRecycler;
    LinearLayoutManager manager;
    AllNewsPaginationAdapter adapter;
    ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES ;
    private int currentPage = PAGE_START;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_news, container, false);
        recentRecycler =(RecyclerView) view.findViewById(R.id.all_news_recycler);
        progressBar = (ProgressBar) view.findViewById(R.id.news_loadmore_progress);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        adapter = new AllNewsPaginationAdapter(view.getContext());
        recentRecycler.setLayoutManager(manager);
        progressBar.setVisibility(View.VISIBLE);
        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);

        recentRecycler.setItemAnimator(new DefaultItemAnimator());

        recentRecycler.setAdapter(adapter);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiprefresh(view);
            }
        });

        updateView(view);
        showBackButtonStatus(false);
        return view;
    }

    private void updateView(final View view) {


        loadFirstPage(view,currentPage);

        recentRecycler.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage(view,currentPage);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return getPages();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadFirstPage(final View view, final int currentPage) {

        RestClient.getInstance().callRetrofit(view.getContext()).getPagePosts(currentPage).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                String totalPages = response.headers().get("X-WP-TotalPages");
                Log.d(TAG, "onResponse: " +totalPages);
                if (totalPages == null){
                    totalPages = "1";
                }
                setPages( Integer.parseInt(totalPages));
                List<Post> posts = response.body();
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    private void loadNextPage(final View view, int pagenumber) {
        RestClient.getInstance().callRetrofit(view.getContext()).getPagePosts(pagenumber).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                adapter.removeLoadingFooter();
                isLoading = false;
                List<Post> posts = response.body();
                progressBar.setVisibility(View.GONE);

                adapter.addAll(posts);
                adapter.notifyDataSetChanged();


                if (currentPage != getPages()) adapter.addLoadingFooter();
                else isLastPage = true;
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

    }
    private void setPages(int number){
        this.TOTAL_PAGES = number;
    }
    private int getPages(){
       return this.TOTAL_PAGES;
    }

    private String getDate(String url) {
        String pattern = "/T.*/gm";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        }
        return pattern;
    }

    protected void makeTransperantStatusBar(boolean isTransperant) {
        if (isTransperant) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public void swiprefresh(final View view){
        RestClient.getInstance().callRetrofit(view.getContext()).getSwipePagePosts(1).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                String totalPages = response.headers().get("X-WP-TotalPages");
                Log.d(TAG, "onResponse: " +totalPages);
                setPages( Integer.parseInt(totalPages));
                List<Post> posts = response.body();
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        recentRecycler.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RestClient.getInstance().callRetrofit(view.getContext()).getSwipePagePosts(currentPage).enqueue(new Callback<List<Post>>() {
                            @Override
                            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                                adapter.removeLoadingFooter();
                                isLoading = false;
                                List<Post> posts = response.body();
                                progressBar.setVisibility(View.GONE);
                                adapter.addAll(posts);
                                adapter.notifyDataSetChanged();

                                if (currentPage != getPages()) adapter.addLoadingFooter();
                                else isLastPage = true;
                                swipeRefresh.setRefreshing(false);

                            }

                            @Override
                            public void onFailure(Call<List<Post>> call, Throwable t) {

                            }
                        });

                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return getPages();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }
    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }

}
