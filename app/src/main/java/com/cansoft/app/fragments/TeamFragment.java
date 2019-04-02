package com.cansoft.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.R;
import com.cansoft.app.adapter.AdapterListExpand;
import com.cansoft.app.model.Data;
import com.cansoft.app.model.Member;
import com.cansoft.app.model.Social;
import com.cansoft.app.network.RestClient;
import com.cansoft.app.network.RestClient2;
import com.cansoft.app.util.DataGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {
    private View parent_view;
    LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private AdapterListExpand mAdapter;

    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        parent_view = view.findViewById(android.R.id.content);

        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        /*recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setHasFixedSize(true);

        List<Social> items = DataGenerator.getSocialData(view.getContext());

        //set data and list adapter
        mAdapter = new AdapterListExpand(view.getContext(), items);
        recyclerView.setAdapter(mAdapter);*/

        // on item list clicked
    /*    mAdapter.setOnItemClickListener(new AdapterListExpand.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Social obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
*/
        showBackButtonStatus(false);
        updateUI(view);

        return view;
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
    private void updateUI(final View view){
        RestClient2.getInstance().callRetrofit(view.getContext()).getMembers().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                List<Member> posts = response.body().getData();
                mAdapter = new AdapterListExpand(view.getContext(), posts);
                manager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(mAdapter);



                /*adapter = new HomeNewsAdapter(view.getContext(),posts);
                manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
                recentRecycler.setLayoutManager(manager);
                recentRecycler.setItemAnimator(new DefaultItemAnimator());
                recentRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);}*/

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }



}