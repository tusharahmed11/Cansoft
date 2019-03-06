package com.cansoft.app.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.R;
import com.cansoft.app.adapter.StaggeredRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private static final String TAG = "Service";
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout serviceLayout;

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_service, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.service_progress);
        serviceLayout = (LinearLayout) view.findViewById(R.id.service_section);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView  = view.findViewById(R.id.serviceRecycler);
        serviceLayout.setVisibility(View.GONE);

        initImageBitmaps(view);



       new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                serviceLayout.setVisibility(View.VISIBLE);
            }

        }, 2000);

        /*  initImageBitmaps(view);*/



        /*new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(1500);
                    // do the background process or any work that takes time to see progress dialog
                    initImageBitmaps(view);

                }
                catch (Exception e)
                {
                    Log.e("tag",e.getMessage());
                }
                // dismiss the progress dialog
                progressBar.setVisibility(View.GONE);

            }
        }.start();*/

        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);

        showBackButtonStatus(false);


        return view;
    }

    private void initImageBitmaps(View v) {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://cansoft.com/files/2018/09/brand-regina.jpg?id=24705g");
        mNames.add("BRANDING");

        mImageUrls.add("https://cansoft.com/files/2018/09/pexels-photo-114907-1.jpg?id=24478");
        mNames.add("SEO");

        mImageUrls.add("https://cansoft.com/files/2018/09/pexels-photo-251225-1.jpg?id=24469");
        mNames.add("WEB DESIGN");

        mImageUrls.add("https://cansoft.com/files/2019/01/Local-Helping-Local-500x700.jpg");
        mNames.add("SMM");


        mImageUrls.add("https://cansoft.com/files/2018/08/georgie-cobbs-467923-unsplash.jpeg?id=4814");
        mNames.add("SEM");

        mImageUrls.add("https://cansoft.com/files/2018/08/marc-kleen-772579-unsplash.jpeg?id=4861");
        mNames.add("ORM");


        mImageUrls.add("https://cansoft.com/files/2018/08/rawpixel-600782-unsplash-1.jpeg?id=4872");
        mNames.add("SEO AUDIT");

        mImageUrls.add("https://cansoft.com/files/2018/08/john-unwin-604601-unsplash.jpeg?id=4867");
        mNames.add("BUSINESS CONSULTANCY");
        initRecyclerView(v);


    }

    private void initRecyclerView(View v) {
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerview.");
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter =
                new StaggeredRecyclerViewAdapter(v.getContext(), mNames, mImageUrls);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);




       /* new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
        });*/
    }
    protected void makeTransperantStatusBar(boolean isTransperant) {
        if (isTransperant) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }


}
